package org.xxpay.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.xxpay.core.entity.AlipayTradeDetails;
import org.xxpay.core.entity.WeixinTradeDetails;
import org.xxpay.core.service.IBankTradeDetailService;
import org.xxpay.service.dao.mapper.AlipayTradeDetailsMapper;
import org.xxpay.service.dao.mapper.WeixinTradeDetailsMapper;

import java.util.LinkedList;
import java.util.List;

@Service
public class BankTradeDetailServiceImpl implements IBankTradeDetailService {

    private final static int batchSize = 100;

    @Autowired
    private AlipayTradeDetailsMapper alipayTradeDetailsMapper;

    @Autowired
    private WeixinTradeDetailsMapper weixinTradeDetailsMapper;

    @Override
    public int insertAlipayBatch(List<AlipayTradeDetails> details) {

        if (CollectionUtils.isEmpty(details)) {
            return 0;
        }

        int result = 0;
        List<AlipayTradeDetails> newList = new LinkedList<AlipayTradeDetails>();
        for (AlipayTradeDetails detail : details) {
            newList.add(detail);
            if (newList.size() > batchSize) {
                alipayTradeDetailsMapper.insertBatch(newList);
                result += newList.size();
                newList = new LinkedList<>();
            }
        }
        if (newList.size() > 0) {
            alipayTradeDetailsMapper.insertBatch(newList);
            result += newList.size();
        }

        return result;
    }

    @Override
    public int insertWxpayBatch(List<WeixinTradeDetails> details) {
        if (CollectionUtils.isEmpty(details)) {
            return 0;
        }

        int result = 0;
        List<WeixinTradeDetails> newList = new LinkedList<WeixinTradeDetails>();
        for (WeixinTradeDetails detail : details) {
            newList.add(detail);
            if (newList.size() > batchSize) {
                weixinTradeDetailsMapper.insertBatch(newList);
                result += newList.size();
                newList = new LinkedList<>();
            }
        }
        if (newList.size() > 0) {
            weixinTradeDetailsMapper.insertBatch(newList);
            result += newList.size();
        }

        return result;
    }

    @Override
    public int removeAlipayDetails() {
        return alipayTradeDetailsMapper.removeAll();
    }

    @Override
    public int removeWxpayDetails() {
        return weixinTradeDetailsMapper.removeAll();
    }
}
