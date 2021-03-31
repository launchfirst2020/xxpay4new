package org.xxpay.core.entity;

import com.baomidou.mybatisplus.annotation.TableName;

import java.util.Date;

/**
 * <p>
 * 支付宝交易流水表
 * </p>
 *
 * @author andy
 * @since 2021-03-29
 */
@TableName("t_alipay_trade_details")
public class AlipayTradeDetails extends BaseModel {

    private static final long serialVersionUID = 1L;

    private String channelorderno;

    private String payorderid;

    private String bustype;

    private String subject;

    private String ordercreatetime;

    private String orderendtime;

    private String storeno;

    private String storename;

    private String operatorname;

    private String device;

    private String accountnofrom;

    private Long orderamount;

    private Long amount;

    private Long alipayredenvelope;

    private Long jifenbao;

    private Long alipaydiscount;

    private Long discountamount;

    private Long juanhexiao;

    private String juan;

    private String redenvelopecons;

    private Long cardconsamount;

    private String refundbatchno;

    private Long servicecharge;

    private Long profit;

    private String tradedate;

    private Integer areacode;

    private String batchno;

    private String hospitalid;

    private String hospitalname;

    private String remark;

    private Date createtime;

    private Date updatetime;

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
    public String getBustype() {
        return bustype;
    }

    public void setBustype(String bustype) {
        this.bustype = bustype;
    }
    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
    public String getOrdercreatetime() {
        return ordercreatetime;
    }

    public void setOrdercreatetime(String ordercreatetime) {
        this.ordercreatetime = ordercreatetime;
    }
    public String getOrderendtime() {
        return orderendtime;
    }

    public void setOrderendtime(String orderendtime) {
        this.orderendtime = orderendtime;
    }
    public String getStoreno() {
        return storeno;
    }

    public void setStoreno(String storeno) {
        this.storeno = storeno;
    }
    public String getStorename() {
        return storename;
    }

    public void setStorename(String storename) {
        this.storename = storename;
    }
    public String getOperatorname() {
        return operatorname;
    }

    public void setOperatorname(String operatorname) {
        this.operatorname = operatorname;
    }
    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }
    public String getAccountnofrom() {
        return accountnofrom;
    }

    public void setAccountnofrom(String accountnofrom) {
        this.accountnofrom = accountnofrom;
    }
    public Long getOrderamount() {
        return orderamount;
    }

    public void setOrderamount(Long orderamount) {
        this.orderamount = orderamount;
    }
    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }
    public Long getAlipayredenvelope() {
        return alipayredenvelope;
    }

    public void setAlipayredenvelope(Long alipayredenvelope) {
        this.alipayredenvelope = alipayredenvelope;
    }
    public Long getJifenbao() {
        return jifenbao;
    }

    public void setJifenbao(Long jifenbao) {
        this.jifenbao = jifenbao;
    }
    public Long getAlipaydiscount() {
        return alipaydiscount;
    }

    public void setAlipaydiscount(Long alipaydiscount) {
        this.alipaydiscount = alipaydiscount;
    }
    public Long getDiscountamount() {
        return discountamount;
    }

    public void setDiscountamount(Long discountamount) {
        this.discountamount = discountamount;
    }
    public Long getJuanhexiao() {
        return juanhexiao;
    }

    public void setJuanhexiao(Long juanhexiao) {
        this.juanhexiao = juanhexiao;
    }
    public String getJuan() {
        return juan;
    }

    public void setJuan(String juan) {
        this.juan = juan;
    }
    public String getRedenvelopecons() {
        return redenvelopecons;
    }

    public void setRedenvelopecons(String redenvelopecons) {
        this.redenvelopecons = redenvelopecons;
    }
    public Long getCardconsamount() {
        return cardconsamount;
    }

    public void setCardconsamount(Long cardconsamount) {
        this.cardconsamount = cardconsamount;
    }
    public String getRefundbatchno() {
        return refundbatchno;
    }

    public void setRefundbatchno(String refundbatchno) {
        this.refundbatchno = refundbatchno;
    }
    public Long getServicecharge() {
        return servicecharge;
    }

    public void setServicecharge(Long servicecharge) {
        this.servicecharge = servicecharge;
    }
    public Long getProfit() {
        return profit;
    }

    public void setProfit(Long profit) {
        this.profit = profit;
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

    public Integer getAreacode() {
        return areacode;
    }

    public void setAreacode(Integer areacode) {
        this.areacode = areacode;
    }

    public String getBatchno() {
        return batchno;
    }

    public void setBatchno(String batchno) {
        this.batchno = batchno;
    }

    @Override
    public String toString() {
        return "AlipayTradeDetails{" +
            "channelorderno=" + channelorderno +
            ", payorderid=" + payorderid +
            ", bustype=" + bustype +
            ", subject=" + subject +
            ", ordercreatetime=" + ordercreatetime +
            ", orderendtime=" + orderendtime +
            ", storeno=" + storeno +
            ", storename=" + storename +
            ", operatorname=" + operatorname +
            ", device=" + device +
            ", accountnofrom=" + accountnofrom +
            ", orderamount=" + orderamount +
            ", amount=" + amount +
            ", alipayredenvelope=" + alipayredenvelope +
            ", jifenbao=" + jifenbao +
            ", alipaydiscount=" + alipaydiscount +
            ", discountamount=" + discountamount +
            ", juanhexiao=" + juanhexiao +
            ", juan=" + juan +
            ", redenvelopecons=" + redenvelopecons +
            ", cardconsamount=" + cardconsamount +
            ", refundbatchno=" + refundbatchno +
            ", servicecharge=" + servicecharge +
            ", profit=" + profit +
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
