package org.xxpay.core.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;

/**
 * <p>
 * 微信交易流水表
 * </p>
 *
 * @author andy
 * @since 2021-03-29
 */
@TableName("t_weixin_trade_details")
public class WeixinTradeDetails extends BaseModel {

    private static final long serialVersionUID = 1L;

    private String tradetime;

    private String wxofficialaccount;

    private String merchantid;

    private String specmchid;

    private String device;

    private String channelorderno;

    private String payorderid;

    private String channeluser;

    private String tradetype;

    private String tradestatus;

    private String payingbank;

    private String currency;

    private Long receivableamount;

    private Long voucheramount;

    private String channelrefundno;

    private String refundorderid;

    private Long refundamount;

    private Long rechargerefuamount;

    private String refundtype;

    private String refundstatus;

    private String subject;

    private String mchdatapackage;

    private Long fee;

    private String feeate;

    private Long orderamount;

    private Long applyrefundamount;

    private String feeremark;

    private String tradedate;

    private Integer areacode;

    private String batchno;

    private String hospitalid;

    private String hospitalname;

    private String remark;

    private Date createtime;

    private Date updatetime;

    public Integer getAreacode() {
        return areacode;
    }

    public void setAreacode(Integer areacode) {
        this.areacode = areacode;
    }

    public String getTradetime() {
        return tradetime;
    }

    public void setTradetime(String tradetime) {
        this.tradetime = tradetime;
    }
    public String getWxofficialaccount() {
        return wxofficialaccount;
    }

    public void setWxofficialaccount(String wxofficialaccount) {
        this.wxofficialaccount = wxofficialaccount;
    }
    public String getMerchantid() {
        return merchantid;
    }

    public void setMerchantid(String merchantid) {
        this.merchantid = merchantid;
    }
    public String getSpecmchid() {
        return specmchid;
    }

    public void setSpecmchid(String specmchid) {
        this.specmchid = specmchid;
    }
    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }
    public String getChannelorderno() {
        return channelorderno;
    }

    public void setChannelorderno(String channelorderno) {
        this.channelorderno = channelorderno;
    }
    public String getPayorderid() {
        return payorderid;
    }

    public void setPayorderid(String payorderid) {
        this.payorderid = payorderid;
    }
    public String getChanneluser() {
        return channeluser;
    }

    public void setChanneluser(String channeluser) {
        this.channeluser = channeluser;
    }
    public String getTradetype() {
        return tradetype;
    }

    public void setTradetype(String tradetype) {
        this.tradetype = tradetype;
    }
    public String getTradestatus() {
        return tradestatus;
    }

    public void setTradestatus(String tradestatus) {
        this.tradestatus = tradestatus;
    }
    public String getPayingbank() {
        return payingbank;
    }

    public void setPayingbank(String payingbank) {
        this.payingbank = payingbank;
    }
    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
    public Long getReceivableamount() {
        return receivableamount;
    }

    public void setReceivableamount(Long receivableamount) {
        this.receivableamount = receivableamount;
    }
    public Long getVoucheramount() {
        return voucheramount;
    }

    public void setVoucheramount(Long voucheramount) {
        this.voucheramount = voucheramount;
    }
    public String getChannelrefundno() {
        return channelrefundno;
    }

    public void setChannelrefundno(String channelrefundno) {
        this.channelrefundno = channelrefundno;
    }
    public String getRefundorderid() {
        return refundorderid;
    }

    public void setRefundorderid(String refundorderid) {
        this.refundorderid = refundorderid;
    }
    public Long getRefundamount() {
        return refundamount;
    }

    public void setRefundamount(Long refundamount) {
        this.refundamount = refundamount;
    }
    public Long getRechargerefuamount() {
        return rechargerefuamount;
    }

    public void setRechargerefuamount(Long rechargerefuamount) {
        this.rechargerefuamount = rechargerefuamount;
    }
    public String getRefundtype() {
        return refundtype;
    }

    public void setRefundtype(String refundtype) {
        this.refundtype = refundtype;
    }
    public String getRefundstatus() {
        return refundstatus;
    }

    public void setRefundstatus(String refundstatus) {
        this.refundstatus = refundstatus;
    }
    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
    public String getMchdatapackage() {
        return mchdatapackage;
    }

    public void setMchdatapackage(String mchdatapackage) {
        this.mchdatapackage = mchdatapackage;
    }
    public Long getFee() {
        return fee;
    }

    public void setFee(Long fee) {
        this.fee = fee;
    }
    public String getFeeate() {
        return feeate;
    }

    public void setFeeate(String feeate) {
        this.feeate = feeate;
    }
    public Long getOrderamount() {
        return orderamount;
    }

    public void setOrderamount(Long orderamount) {
        this.orderamount = orderamount;
    }
    public Long getApplyrefundamount() {
        return applyrefundamount;
    }

    public void setApplyrefundamount(Long applyrefundamount) {
        this.applyrefundamount = applyrefundamount;
    }
    public String getFeeremark() {
        return feeremark;
    }

    public void setFeeremark(String feeremark) {
        this.feeremark = feeremark;
    }
    public String getTradedate() {
        return tradedate;
    }

    public void setTradedate(String tradedate) {
        this.tradedate = tradedate;
    }
    public String getHospitalid() {
        return hospitalid;
    }

    public void setHospitalid(String hospitalid) {
        this.hospitalid = hospitalid;
    }
    public String getHospitalname() {
        return hospitalname;
    }

    public void setHospitalname(String hospitalname) {
        this.hospitalname = hospitalname;
    }
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }
    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    public String getBatchno() {
        return batchno;
    }

    public void setBatchno(String batchno) {
        this.batchno = batchno;
    }

    @Override
    public String toString() {
        return "WeixinTradeDetails{" +
            "tradetime=" + tradetime +
            ", wxofficialaccount=" + wxofficialaccount +
            ", merchantid=" + merchantid +
            ", specmchid=" + specmchid +
            ", device=" + device +
            ", channelorderno=" + channelorderno +
            ", payorderid=" + payorderid +
            ", channeluser=" + channeluser +
            ", tradetype=" + tradetype +
            ", tradestatus=" + tradestatus +
            ", payingbank=" + payingbank +
            ", currency=" + currency +
            ", receivableamount=" + receivableamount +
            ", voucheramount=" + voucheramount +
            ", channelrefundno=" + channelrefundno +
            ", refundorderid=" + refundorderid +
            ", refundamount=" + refundamount +
            ", rechargerefuamount=" + rechargerefuamount +
            ", refundtype=" + refundtype +
            ", refundstatus=" + refundstatus +
            ", subject=" + subject +
            ", mchdatapackage=" + mchdatapackage +
            ", fee=" + fee +
            ", feeate=" + feeate +
            ", orderamount=" + orderamount +
            ", applyrefundamount=" + applyrefundamount +
            ", feeremark=" + feeremark +
            ", tradedate=" + tradedate +
            ", areacode=" + areacode +
            ", batchno=" + batchno +
            ", hospitalid=" + hospitalid +
            ", hospitalname=" + hospitalname +
            ", remark=" + remark +
            ", createtime=" + createtime +
            ", updatetime=" + updatetime +
        "}";
    }
}
