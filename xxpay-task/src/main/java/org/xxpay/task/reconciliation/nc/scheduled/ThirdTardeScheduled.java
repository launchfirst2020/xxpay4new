package org.xxpay.task.reconciliation.nc.scheduled;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.xxpay.core.common.util.DateUtil;
import org.xxpay.core.common.util.MyLog;
import org.xxpay.core.common.util.MySeq;
import org.xxpay.core.common.util.XXPayUtil;
import org.xxpay.core.entity.ChannelTask;
import org.xxpay.core.entity.CheckBatch;
import org.xxpay.core.entity.MchHospital;
import org.xxpay.service.dao.mapper.CheckBatchMapper;
import org.xxpay.task.common.service.RpcCommonService;
import org.xxpay.task.reconciliation.channel.BillInterface;
import org.xxpay.task.reconciliation.nc.channel.TradeCheckService;
import org.xxpay.task.reconciliation.nc.entity.HospitalBean;
import org.xxpay.task.reconciliation.service.ReconciliationService;
import org.xxpay.task.reconciliation.util.SpringUtil;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ThirdTardeScheduled {

    private static final MyLog _log = MyLog.getLog(ThirdTardeScheduled.class);

    @Autowired
    private RpcCommonService rpcCommonService;

    @Autowired
    private CheckBatchMapper checkBatchMapper;

    @Autowired
    private ReconciliationService reconciliationService;

    @Scheduled(cron="0 37 16 ? * *")   // 每日10:15执行
    public void settDailyThirdDetailsTask() {
        _log.info("执行对账,开始...");

        byte status  = 1;
        List<ChannelTask> cts =  rpcCommonService.rpcChannelTaskService.selectByStatus(status);

        //删除支付宝流水记录
        rpcCommonService.rpcBankTradeDetailService.removeAlipayDetails();
        //删除微信流水记录
        rpcCommonService.rpcBankTradeDetailService.removeWxpayDetails();

        Map<String, ChannelTask> handleMap = new HashMap<String, ChannelTask>();
        for (ChannelTask task : cts) {

            String channelMchId = task.getChannelMchId();
            // 判断是否执行过对账
            if(handleMap.get(channelMchId) != null) continue;
            handleMap.put(channelMchId, task);

            if(StringUtils.isBlank(channelMchId)
                    || StringUtils.isBlank(task.getParam())) continue;

            Date billDate = DateUtil.addDay(new Date(), -31);
            // 检测是否对过账
            boolean checked = reconciliationService.isChecked(channelMchId, billDate);
            if (checked) {
                _log.info("账单日[" + DateUtil.date2Str(billDate) + "],渠道商户ID[" + channelMchId + "],已经对过账，不能再次发起自动对账。");
                continue;
            }

            // 2.下载对账文件
            String channelType = task.getChannelType();
            BillInterface billInterface = null;
            try {
                billInterface = (BillInterface) SpringUtil.getBean(channelType.toLowerCase() +  "BillService");
            }catch (Exception e) {
                _log.error(e, e.getMessage());
            }
            if(billInterface == null) {
                _log.info(channelType.toLowerCase() +  "BillService" + "没有对应的实现");
                continue;
            }

            CheckBatch batch = new CheckBatch();
            batch.setBatchNo(MySeq.getCheck());
            batch.setBillDate(billDate);
            batch.setChannelMchId(channelMchId);
            batch.setBillType((byte) 1);
            batch.setBankType(task.getChannelType());
            batch.setAreaCode(task.getAreaCode());

            MchHospital mchHospital = rpcCommonService.rpcMchHospitalService.findByMchId(task.getMchId());
            if (mchHospital == null) {
                _log.info(task.getMchId() +  "找不到对应的对应的医院");
                continue;
            }

            HospitalBean hbean = new HospitalBean(String.valueOf(mchHospital.getHospitalId()), mchHospital.getHospitalName());
            JSONObject param = new JSONObject();
            param.put("payParam", task.getParam());
            JSONObject retObj = billInterface.downloadBill(param, batch, hbean);
            List<Object> bankList = null;
            if(XXPayUtil.isSuccess(retObj)) {
                bankList = retObj.getObject("bill", List.class);
            }

            if(CollectionUtils.isEmpty(bankList)) {
                _log.info("channelMchId={},银行侧对账数据为空.不用对账", task.getChannelMchId());
                continue;
            }

            //rpcCommonService.rpcBankTradeDetailService.insertAlipayBatch(bankList);
            TradeCheckService tradeCheckService = null;
            try {
                tradeCheckService = (TradeCheckService) SpringUtil.getBean(channelType.toLowerCase() +  "TradeCheckService");
            }catch (Exception e) {
                _log.error(e, e.getMessage());
            }
            if(tradeCheckService == null) {
                _log.info(channelType.toLowerCase() +  "TradeCheckService" + "没有对应的实现");
                continue;
            }
            tradeCheckService.insertBatch(bankList);

            //插入账单批次信息
            checkBatchMapper.insertSelective(batch);
        }

        _log.info("执行对账,结束。");
    }

}
