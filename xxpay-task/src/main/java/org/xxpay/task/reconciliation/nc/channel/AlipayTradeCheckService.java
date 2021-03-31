package org.xxpay.task.reconciliation.nc.channel;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xxpay.core.entity.AlipayTradeDetails;
import org.xxpay.task.common.service.RpcCommonService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AlipayTradeCheckService implements TradeCheckService {

    @Autowired
    private RpcCommonService rpcCommonService;

    @Override
    public int insertBatch(List<Object> list) {
        if (CollectionUtils.isEmpty(list)) {
            return 0;
        }

        //collection.stream()
        //    .filter(obj -> obj instanceof ScheduleIntervalContainer)
        //    .map(obj -> (ScheduleIntervalContainer) obj)

        List<AlipayTradeDetails> aliDetails = list.stream().filter(c -> c instanceof AlipayTradeDetails).map(c -> (AlipayTradeDetails)c).collect(Collectors.toList());
        int result = rpcCommonService.rpcBankTradeDetailService.insertAlipayBatch(aliDetails);
        return result;
    }

}
