package org.xxpay.task.settlement.scheduled;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.xxpay.core.common.util.BizSequenceUtils;
import org.xxpay.core.common.util.MyLog;
import org.xxpay.core.entity.MchTradeOrderBatch;
import org.xxpay.core.entity.MchTradeOrderBatchHour;
import org.xxpay.task.common.service.RpcCommonService;

import java.text.SimpleDateFormat;
import java.util.*;

@Component
public class TradeBatchScheduled {

    private static final MyLog _log = MyLog.getLog(TradeBatchScheduled.class);

    @Autowired
    private RpcCommonService rpcCommonService;

    @Scheduled(cron="0 12 2 * * ?")
    public void batchDailyCollectTask() {
        _log.info("开始商户交易订单数据按天跑批");

        //获得昨天的日期
        Calendar cal=Calendar.getInstance();
        cal.add(Calendar.DATE,-1);
        Date day = cal.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String yesterday = sdf.format(day);

        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("payDate", yesterday);
        List<MchTradeOrderBatch> batchs = rpcCommonService.rpcMchTradeOrderBatchService.selectDailyBatch(paramMap);
        if (CollectionUtils.isEmpty(batchs)) {
            _log.info("商户交易订单数据按天跑批数据为空");
            return;
        }
        for (MchTradeOrderBatch batch : batchs) {
            String batchId = BizSequenceUtils.getInstance().generateBizSeqNo("BTD");
            batch.setBatchId(batchId);
        }
        int result = rpcCommonService.rpcMchTradeOrderBatchService.insertDailyBatch(batchs);

        _log.info("结束商户交易订单数据按天跑批, 插入数据条数: {}", result);
    }

    @Scheduled(cron="0 32 2 * * ?")
    public void batchHourCollectTask() {

        _log.info("开始商户交易订单数据按小时跑批");
        //获得昨天的日期
        Calendar cal=Calendar.getInstance();
        cal.add(Calendar.DATE,-1);
        Date day = cal.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String yesterday = sdf.format(day);

        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("payDate", yesterday);
        List<MchTradeOrderBatchHour> batchs = rpcCommonService.rpcMchTradeOrderBatchHourService.selectHourBatch(paramMap);
        if (CollectionUtils.isEmpty(batchs)) {
            _log.info("商户交易订单数据按小时跑批数据为空");
            return;
        }
        for (MchTradeOrderBatchHour batch : batchs) {
            String batchId = BizSequenceUtils.getInstance().generateBizSeqNo("BTH");
            batch.setBatchId(batchId);
        }
        int result = rpcCommonService.rpcMchTradeOrderBatchHourService.insertHourBatch(batchs);
        _log.info("结束商户交易订单数据按时跑批, 插入数据条数: {}", result);
    }
}
