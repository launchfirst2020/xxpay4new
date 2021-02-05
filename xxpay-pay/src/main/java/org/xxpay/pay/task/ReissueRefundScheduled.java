package org.xxpay.pay.task;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.xxpay.core.common.constant.MchConstant;
import org.xxpay.core.common.constant.PayConstant;
import org.xxpay.core.common.util.DateUtils;
import org.xxpay.core.common.util.MyLog;
import org.xxpay.core.entity.MchRefundOrder;
import org.xxpay.core.entity.RefundOrder;
import org.xxpay.pay.service.RpcCommonService;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ReissueRefundScheduled extends ReissuceBase {

    private static final MyLog _log = MyLog.getLog(ReissueRefundScheduled.class);

    @Autowired
    private RpcCommonService rpcCommonService;

    /**
     *  商户退款中订单批量补单任务
     */
    @Scheduled(cron="0 0/10 * * * ?") //每10分钟执行一次
    public void refundCallBack() {
        String logPrefix = "【商户退款补单】";
        // 支付补单开关
        if(!isExcuteReissueTask(reissueRefundTaskSwitch, reissueRefundTaskIp)) {
            _log.info("{}当前机器不执行商户退款补单任务", logPrefix);
            return;
        }
        int pageIndex = 1;
        int limit = 50;
        // 查询比当前时间小10分钟,状态为处理中的订单
        Date updateTimeEnd = new Date(System.currentTimeMillis() - 24 * 60 * 60 * 1000);
        boolean flag = true;
        _log.info("{}开始查询需要处理的退款订单,查询订单创建时间<={}", logPrefix, DateUtils.getTimeStr(updateTimeEnd, "yyyy-MM-dd HH:mm:ss"));
        // 循环查询所有
        while (flag) {
            //商户退款中订单
            LambdaQueryWrapper<MchRefundOrder> queryWrapper = new LambdaQueryWrapper<MchRefundOrder>().eq(MchRefundOrder::getStatus, MchConstant.MCH_REFUND_STATUS_ING);
            queryWrapper.ge(MchRefundOrder::getUpdateTime, updateTimeEnd);
            queryWrapper.orderByDesc(MchRefundOrder::getCreateTime);
            IPage page = new Page((pageIndex - 1) * limit, limit);
            IPage<MchRefundOrder> pageList = rpcCommonService.rpcMchRefundOrderService.selectPage(page, queryWrapper);
            List<MchRefundOrder> mchRefundOrders = pageList.getRecords();
            if (CollectionUtils.isEmpty(mchRefundOrders)) {
                _log.info("{}不存在商户退款中订单", logPrefix);
                flag = false;
                return;
            }
            List<String> mchRefundOrderIds = mchRefundOrders.stream().map(MchRefundOrder::getMchRefundOrderId).collect(Collectors.toList());
            _log.info("{}查询需要处理商户退款订单,共:{}条", logPrefix, mchRefundOrders == null ? 0 : mchRefundOrders.size());

            List<RefundOrder> refundOrders = rpcCommonService.rpcRefundOrderService.list(new LambdaQueryWrapper<RefundOrder>().in(RefundOrder::getMchRefundNo, mchRefundOrderIds));
            if (CollectionUtils.isEmpty(refundOrders)) {
                _log.info("支付退款订单中找不到对应的商户退款中订单");
                continue;
            }

            List<String> mchRefundNos = refundOrders.stream().filter(c -> c.getStatus() == PayConstant.REFUND_STATUS_SUCCESS).map(RefundOrder::getMchRefundNo).collect(Collectors.toList());
            _log.info("处理商户退款中的订单号: {}", Arrays.asList(mchRefundNos));
            if (CollectionUtils.isEmpty(mchRefundNos)) {
                _log.info("支付退款订单中找不到对应的商户退款中订单");
                continue;
            }
            List<MchRefundOrder> mros = new ArrayList<MchRefundOrder>();
            mchRefundNos.stream().forEach(c -> {
                MchRefundOrder m = new MchRefundOrder();
                m.setMchRefundOrderId(c);
                m.setStatus(MchConstant.MCH_REFUND_STATUS_SUCCESS);
                m.setUpdateTime(new Date());
                m.setRemark("=refundCallBack=");
                mros.add(m);
            });
            _log.info("===========================================================待更新的商户退款表记录数: {}", CollectionUtils.isEmpty(mros) ? 0 : mros.size());
            //批量更新商户退款
            boolean result = rpcCommonService.rpcMchRefundOrderService.updateBatchById(mros);
            _log.info("===================================处理结果================================result:{}", result);

            pageIndex++;
            if(mchRefundOrders.size() < limit) {
                flag = false;
            }
        }
        _log.info("{}本次商户退款中订单处理完成,", logPrefix);
    }

}
