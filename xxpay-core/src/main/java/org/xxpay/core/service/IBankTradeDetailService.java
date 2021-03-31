package org.xxpay.core.service;

import org.xxpay.core.entity.AlipayTradeDetails;
import org.xxpay.core.entity.WeixinTradeDetails;

import java.util.List;

public interface IBankTradeDetailService {

    int insertAlipayBatch(List<AlipayTradeDetails> details);

    int insertWxpayBatch(List<WeixinTradeDetails> details);

    int removeAlipayDetails();

    int removeWxpayDetails();

}
