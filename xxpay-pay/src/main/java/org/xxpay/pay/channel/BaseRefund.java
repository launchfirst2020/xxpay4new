package org.xxpay.pay.channel;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.xxpay.core.common.constant.MchConstant;
import org.xxpay.core.entity.PayOrder;
import org.xxpay.core.entity.PayPassage;
import org.xxpay.core.entity.RefundOrder;
import org.xxpay.pay.service.RpcCommonService;

/**
 * @author: dingzhiwei
 * @date: 17/12/24
 * @description:
 */
@Component
public abstract class BaseRefund extends BaseService implements RefundInterface {

    @Autowired
    public RpcCommonService rpcCommonService;

    @Autowired
    public PayConfig payConfig;

    public abstract String getChannelName();

    public JSONObject refund(RefundOrder refundOrder) {
        return null;
    }

    public JSONObject query(RefundOrder refundOrder) {
        return null;
    }

    /**
     * 获取三方支付配置信息
     * 如果是平台账户,则使用平台对应的配置,否则使用商户自己配置的渠道
     * @param refundOrder
     * @return
     */
    public String getRefundParam(RefundOrder refundOrder) {

        return null; //TODO 根据支付通道获取参数
    }

    /** 获取接口主发起方的支付参数信息 **/
    public JSONObject getMainPayParam(PayOrder payOrder) {

        if(MchConstant.MCH_TYPE_PRIVATE == payOrder.getMchType()){ //私有商户获取
            PayPassage mchPassage = rpcCommonService.rpcPayPassageService.getById(payOrder.getMchPassageId());
            return JSONObject.parseObject(mchPassage.getMchParam());
        }else{ //获取服务商通道
            PayPassage isvPassage = rpcCommonService.rpcPayPassageService.getById(payOrder.getIsvPassageId());
            return JSONObject.parseObject(isvPassage.getIsvParam());

        }
    }

    /** 获取子商户的支付参数信息 **/
    public JSONObject getSubMchPayParam(PayOrder payOrder) {
        if(MchConstant.MCH_TYPE_PRIVATE == payOrder.getMchType()){ //私有商户 没有子商户配置信息
            return null;
        }else{
            PayPassage mchPassage = rpcCommonService.rpcPayPassageService.getById(payOrder.getMchPassageId());
            return JSONObject.parseObject(mchPassage.getMchParam());
        }
    }






}
