package org.xxpay.task.reconciliation.nc.channel;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xxpay.core.entity.WeixinTradeDetails;
import org.xxpay.task.common.service.RpcCommonService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class WxpayTradeCheckService implements TradeCheckService {

    @Autowired
    private RpcCommonService rpcCommonService;

    @Override
    public int insertBatch(List<Object> list) {
        if (CollectionUtils.isEmpty(list)) {
            return 0;
        }

        List<WeixinTradeDetails> wxpayDetails = list.stream().filter(c -> c instanceof WeixinTradeDetails).map(c -> (WeixinTradeDetails)c).collect(Collectors.toList());
        int result = rpcCommonService.rpcBankTradeDetailService.insertWxpayBatch(wxpayDetails);
        return result;
    }

}
