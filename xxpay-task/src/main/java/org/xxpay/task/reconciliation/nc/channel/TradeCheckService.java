package org.xxpay.task.reconciliation.nc.channel;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface TradeCheckService {

    int insertBatch(List<Object> list);

}
