package org.xxpay.task.reconciliation.channel.wxpay;

import com.alibaba.fastjson.JSONObject;
import com.github.binarywang.wxpay.bean.result.WxPayBillInfo;
import com.github.binarywang.wxpay.bean.result.WxPayBillResult;
import com.github.binarywang.wxpay.config.WxPayConfig;
import com.github.binarywang.wxpay.exception.WxPayException;
import com.github.binarywang.wxpay.service.WxPayService;
import com.github.binarywang.wxpay.service.impl.WxPayServiceImpl;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;
import org.xxpay.core.common.constant.PayConstant;
import org.xxpay.core.common.util.DateUtil;
import org.xxpay.core.common.util.MyLog;
import org.xxpay.core.entity.CheckBatch;
import org.xxpay.core.entity.WeixinTradeDetails;
import org.xxpay.task.common.service.RpcCommonService;
import org.xxpay.task.reconciliation.channel.BaseBill;
import org.xxpay.task.reconciliation.entity.ReconciliationEntity;
import org.xxpay.task.reconciliation.nc.entity.HospitalBean;
import org.xxpay.task.reconciliation.util.SpringUtil;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * @author: dingzhiwei
 * @date: 18/1/18
 * @description:
 */
@Service
public class WxpayBillService extends BaseBill {

    private static final MyLog _log = MyLog.getLog(WxpayBillService.class);

    @Override
    public String getChannelName() {
        return null;
    }

    @Override
    public JSONObject downloadBill(JSONObject param, CheckBatch batch) {
        rpcCommonService = (RpcCommonService) SpringUtil.getBean("rpcCommonService");
        String logPrefix = "【微信对账下载】";
        JSONObject map = new JSONObject();
        map.put(PayConstant.RETURN_PARAM_RETCODE, PayConstant.RETURN_VALUE_SUCCESS);
        try{
            String billDate = DateUtil.date2Str(batch.getBillDate(), DateUtil.FORMAT_YYYY_MM_DD2);
            WxPayConfig wxPayConfig = WxpayUtil.getWxPayConfig(param.getString("payParam"));
            WxPayService wxPayService = new WxPayServiceImpl();
            wxPayService.setConfig(wxPayConfig);
            WxPayBillResult wxPayBillResult;
            try {
                wxPayBillResult = wxPayService.downloadBill(billDate, "ALL", null, null);
                map.put("bill", bill2Reconciliation(wxPayBillResult, batch));
                _log.info("{} >>> 下载成功", logPrefix);
            } catch (WxPayException e) {
                if("NO Bill Exist".equalsIgnoreCase(e.getReturnMsg()) || "Bill Creating".equalsIgnoreCase(e.getReturnMsg())) {
                    map.put("bill", new ArrayList<>());
                    map.put("errDes", e.getReturnMsg());
                }else {
                    _log.error(e, "下载失败");
                    //出现业务错误
                    _log.info("{}下载返回失败", logPrefix);
                    _log.info("err_code:{}", e.getErrCode());
                    _log.info("err_code_des:{}", e.getErrCodeDes());
                    map.put("errDes", e.getErrCodeDes());
                    map.put(PayConstant.RETURN_PARAM_RETCODE, PayConstant.RETURN_VALUE_FAIL);
                }

            }
        }catch (Exception e) {
            _log.error(e, "微信对账下载异常");
            map.put("errDes", "微信对账下载异常");
            map.put(PayConstant.RETURN_PARAM_RETCODE, PayConstant.RETURN_VALUE_FAIL);
        }
        return map;
    }


    @Override
    public JSONObject downloadBill(JSONObject param, CheckBatch batch, HospitalBean hbean) {
        rpcCommonService = (RpcCommonService) SpringUtil.getBean("rpcCommonService");
        String logPrefix = "【微信对账下载】";
        JSONObject map = new JSONObject();
        map.put(PayConstant.RETURN_PARAM_RETCODE, PayConstant.RETURN_VALUE_SUCCESS);
        try{
            String billDate = DateUtil.date2Str(batch.getBillDate(), DateUtil.FORMAT_YYYY_MM_DD2);
            WxPayConfig wxPayConfig = WxpayUtil.getWxPayConfig(param.getString("payParam"));
            WxPayService wxPayService = new WxPayServiceImpl();
            wxPayService.setConfig(wxPayConfig);
            WxPayBillResult wxPayBillResult;
            try {
                wxPayBillResult = wxPayService.downloadBill(billDate, "ALL", null, null);
                map.put("bill", bill2Reconciliation(wxPayBillResult, batch, hbean));
                _log.info("{} >>> 下载成功", logPrefix);
            } catch (WxPayException e) {
                if("NO Bill Exist".equalsIgnoreCase(e.getReturnMsg()) || "Bill Creating".equalsIgnoreCase(e.getReturnMsg())) {
                    map.put("bill", new ArrayList<>());
                    map.put("errDes", e.getReturnMsg());
                }else {
                    _log.error(e, "下载失败");
                    //出现业务错误
                    _log.info("{}下载返回失败", logPrefix);
                    _log.info("err_code:{}", e.getErrCode());
                    _log.info("err_code_des:{}", e.getErrCodeDes());
                    map.put("errDes", e.getErrCodeDes());
                    map.put(PayConstant.RETURN_PARAM_RETCODE, PayConstant.RETURN_VALUE_FAIL);
                }

            }
        }catch (Exception e) {
            _log.error(e, "微信对账下载异常");
            map.put("errDes", "微信对账下载异常");
            map.put(PayConstant.RETURN_PARAM_RETCODE, PayConstant.RETURN_VALUE_FAIL);
        }
        return map;
    }

    /**
     * 将微信数据转为对账统一对象格式
     * @param wxPayBillResult
     * @return
     */
    List<ReconciliationEntity> bill2Reconciliation(WxPayBillResult wxPayBillResult, CheckBatch batch) {
        List<ReconciliationEntity> reconciliationEntityList = new LinkedList<>();
        List<WxPayBillInfo> wxPayBillInfos = wxPayBillResult.getBillInfoList();
        if(CollectionUtils.isEmpty(wxPayBillInfos)) return reconciliationEntityList;
        // 设置批次数据
        batch.setBankTradeCount(Integer.parseInt(wxPayBillResult.getTotalRecord().trim()));
        batch.setBankTradeAmount(new BigDecimal(wxPayBillResult.getTotalFee().trim()).multiply(new BigDecimal(100)).longValue());
        batch.setBankRefundAmount(new BigDecimal(wxPayBillResult.getTotalRefundFee().trim()).multiply(new BigDecimal(100)).longValue());
        batch.setBankFee(new BigDecimal(wxPayBillResult.getTotalPoundageFee().trim()).multiply(new BigDecimal(100)).longValue());

        for(WxPayBillInfo info : wxPayBillInfos) {
            ReconciliationEntity entity = new ReconciliationEntity();
            // 设置支付时间
            entity.setOrderTime(DateUtil.str2date(info.getTradeTime()));
            // 设置微信流水号
            entity.setBankTrxNo(info.getTransactionId());
            entity.setBankOrderNo(info.getTransactionId());
            // 设置微信订单状态(默认全部是success)
            entity.setBankTradeStatus(info.getTradeState());
            // 设置微信账单金额(元转分)
            entity.setBankAmount(new BigDecimal(info.getTotalFee().trim()).multiply(new BigDecimal(100)).longValue());
            // 设置手续费(元转分)
            entity.setBankFee(new BigDecimal(info.getPoundage().trim()).multiply(new BigDecimal(100)).longValue());
            reconciliationEntityList.add(entity);
        }
        return reconciliationEntityList;
    }

    List<WeixinTradeDetails> bill2Reconciliation(WxPayBillResult wxPayBillResult, CheckBatch batch,  HospitalBean hbean) {
        List<WeixinTradeDetails> reconciliationEntityList = new LinkedList<WeixinTradeDetails>();
        List<WxPayBillInfo> wxPayBillInfos = wxPayBillResult.getBillInfoList();
        if(CollectionUtils.isEmpty(wxPayBillInfos)) return reconciliationEntityList;
        // 设置批次数据
        batch.setBankTradeCount(Integer.parseInt(wxPayBillResult.getTotalRecord().trim()));
        batch.setBankTradeAmount(new BigDecimal(wxPayBillResult.getTotalFee().trim()).multiply(new BigDecimal(100)).longValue());
        batch.setBankRefundAmount(new BigDecimal(wxPayBillResult.getTotalRefundFee().trim()).multiply(new BigDecimal(100)).longValue());
        batch.setBankFee(new BigDecimal(wxPayBillResult.getTotalPoundageFee().trim()).multiply(new BigDecimal(100)).longValue());

        String billDate = DateUtil.date2Str( batch.getBillDate(), DateUtil.FORMAT_YYYY_MM_DD2);

        for(WxPayBillInfo info : wxPayBillInfos) {
            WeixinTradeDetails entity = new WeixinTradeDetails();
            // 设置支付时间
            entity.setTradetime(info.getTradeTime().trim());
            // 设置微信流水号
            entity.setWxofficialaccount(info.getAppId().trim());
            entity.setMerchantid(info.getMchId().trim());
            entity.setSpecmchid(info.getSubMchId().trim());
            entity.setDevice(info.getDeviceInfo().trim());
            entity.setChannelorderno(info.getTransactionId().trim());
            entity.setPayorderid(info.getOutTradeNo().trim());
            entity.setChanneluser(info.getOpenId().trim());
            entity.setTradetype(info.getTradeType().trim());
            entity.setTradestatus(info.getTradeState().trim());
            entity.setPayingbank(info.getBankType().trim());
            entity.setCurrency(info.getFeeType().trim());
            entity.setReceivableamount(new BigDecimal(info.getTotalFee().trim()).multiply(new BigDecimal(100)).longValue());
            entity.setVoucheramount(new BigDecimal(info.getCouponFee().trim()).multiply(new BigDecimal(100)).longValue());
            entity.setChannelrefundno(info.getRefundId().trim());
            entity.setRefundorderid(info.getOutRefundNo().trim());
            entity.setRefundamount(new BigDecimal(info.getSettlementRefundFee().trim()).multiply(new BigDecimal(100)).longValue());
            entity.setRechargerefuamount(new BigDecimal(info.getCouponRefundFee().trim()).multiply(new BigDecimal(100)).longValue());
            entity.setRefundtype(info.getRefundChannel().trim());
            entity.setRefundstatus(info.getRefundState().trim());
            entity.setSubject(info.getBody().trim());
            entity.setMchdatapackage(info.getAttach().trim());
            entity.setFee(new BigDecimal(info.getPoundage().trim()).multiply(new BigDecimal(100)).longValue());
            entity.setFeeate(info.getPoundageRate().trim());
            entity.setOrderamount(new BigDecimal(info.getTotalAmount().trim()).multiply(new BigDecimal(100)).longValue());
            entity.setApplyrefundamount(new BigDecimal(info.getAppliedRefundAmount().trim()).multiply(new BigDecimal(100)).longValue());
            entity.setFeeremark(info.getFeeRemark().trim());

            entity.setRemark(null);
            entity.setTradedate(billDate);
            entity.setAreacode(batch.getAreaCode());
            entity.setBatchno(batch.getBatchNo());
            entity.setHospitalid(hbean.getHospitalId());
            entity.setHospitalname(hbean.getHospitalName());
            entity.setCreatetime(new Date());
            entity.setUpdatetime(new Date());
            reconciliationEntityList.add(entity);
        }
        return reconciliationEntityList;
    }


}
