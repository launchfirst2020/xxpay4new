package org.xxpay.task.reconciliation.channel.alipay;

import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayDataDataserviceBillDownloadurlQueryModel;
import com.alipay.api.request.AlipayDataDataserviceBillDownloadurlQueryRequest;
import com.alipay.api.response.AlipayDataDataserviceBillDownloadurlQueryResponse;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xxpay.core.common.constant.PayConstant;
import org.xxpay.core.common.util.DateUtil;
import org.xxpay.core.common.util.FileUtils;
import org.xxpay.core.common.util.MyLog;
import org.xxpay.core.entity.AlipayTradeDetails;
import org.xxpay.core.entity.CheckBatch;
import org.xxpay.task.reconciliation.channel.BaseBill;
import org.xxpay.task.reconciliation.entity.ReconciliationEntity;
import org.xxpay.task.reconciliation.nc.entity.HospitalBean;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;


@Service
public class AlipayBillService extends BaseBill {

    private static final MyLog _log = MyLog.getLog(AlipayBillService.class);

    @Autowired
    private AlipayProperties alipayProperties;

    @Override
    public String getChannelName() {
        return null;
    }

    @Override
    public JSONObject downloadBill(JSONObject param, CheckBatch batch) {
        JSONObject map = new JSONObject();
        map.put(PayConstant.RETURN_PARAM_RETCODE, PayConstant.RETURN_VALUE_SUCCESS);
        String billDate = DateUtil.date2Str(batch.getBillDate(), DateUtil.FORMAT_YYYY_MM_DD);
        String payParam = param.getString("payParam");
        AlipayConfig alipayConfig = new AlipayConfig(payParam);
        AlipayClient client = new DefaultAlipayClient(alipayConfig.getReqUrl(), alipayConfig.getAppId(),
                alipayConfig.getPrivateKey(), AlipayConfig.FORMAT, AlipayConfig.CHARSET,
                alipayConfig.getAlipayPublicKey(), AlipayConfig.SIGNTYPE);
        AlipayDataDataserviceBillDownloadurlQueryRequest request = new AlipayDataDataserviceBillDownloadurlQueryRequest();//创建API对应的request类
        AlipayDataDataserviceBillDownloadurlQueryModel model = new AlipayDataDataserviceBillDownloadurlQueryModel();
        model.setBillDate(billDate);
        model.setBillType("trade");
        request.setBizModel(model);
        AlipayDataDataserviceBillDownloadurlQueryResponse response = null;//通过alipayClient调用API，获得对应的response类
        try {
            response = client.execute(request);
        } catch (AlipayApiException e) {
            _log.error(e, "");
            map.put("errDes", "调用支付宝异常");
            map.put(PayConstant.RETURN_PARAM_RETCODE, PayConstant.RETURN_VALUE_FAIL);
            return map;
        }
        String billDownloadUrl = response.getBillDownloadUrl();
        _log.info("支付宝对账文件下载路径:{}", billDownloadUrl);
        if(StringUtils.isBlank(billDownloadUrl)) {
            _log.info("支付宝对账文件URL为空");
            map.put("errDes", "支付宝对账文件URL为空");
            map.put(PayConstant.RETURN_PARAM_RETCODE, PayConstant.RETURN_VALUE_FAIL);
            return map;
        }
        // 下载文件
        String filePath = alipayProperties.getBillPath() + billDate + ".zip";
        try {
            File file = FileUtils.saveFile(billDownloadUrl, new File(filePath));
            List<String> filePathList = FileUtils.unZipFiles(file, alipayProperties.getBillPath() + billDate + File.separator);
            System.out.println(filePathList);
            map.put("bill", bill2Reconciliation(filePathList, batch));
        } catch (IOException e) {
            _log.error(e, "");
            map.put("errDes", "生成对账文件异常");
            map.put(PayConstant.RETURN_PARAM_RETCODE, PayConstant.RETURN_VALUE_FAIL);
        }
        return map;
    }


    @Override
    public JSONObject downloadBill(JSONObject param, CheckBatch batch, HospitalBean hbean) {
        JSONObject map = new JSONObject();
        map.put(PayConstant.RETURN_PARAM_RETCODE, PayConstant.RETURN_VALUE_SUCCESS);
        String billDate = DateUtil.date2Str( batch.getBillDate(), DateUtil.FORMAT_YYYY_MM_DD);
        String payParam = param.getString("payParam");
        AlipayConfig alipayConfig = new AlipayConfig(payParam);

        AlipayClient client = new DefaultAlipayClient(alipayConfig.getReqUrl(), alipayConfig.getAppId(),
                alipayConfig.getPrivateKey(), AlipayConfig.FORMAT, AlipayConfig.CHARSET,
                alipayConfig.getAlipayPublicKey(), AlipayConfig.SIGNTYPE);
        AlipayDataDataserviceBillDownloadurlQueryRequest request = new AlipayDataDataserviceBillDownloadurlQueryRequest();//创建API对应的request类
        AlipayDataDataserviceBillDownloadurlQueryModel model = new AlipayDataDataserviceBillDownloadurlQueryModel();
        //交易日期格式约定yyyy-MM-dd
        model.setBillDate(billDate);
        model.setBillType("trade");
        request.setBizModel(model);
        request.putOtherTextParam("app_auth_token", alipayConfig.getAppAuthToken());
        AlipayDataDataserviceBillDownloadurlQueryResponse response = null;//通过alipayClient调用API，获得对应的response类
        try {
            response = client.execute(request);
        } catch (AlipayApiException e) {
            _log.error(e, "");
            map.put("errDes", "调用支付宝异常");
            map.put(PayConstant.RETURN_PARAM_RETCODE, PayConstant.RETURN_VALUE_FAIL);
            return map;
        }
        String billDownloadUrl = response.getBillDownloadUrl();
        _log.info("支付宝对账文件下载路径:{}", billDownloadUrl);
        if(StringUtils.isBlank(billDownloadUrl)) {
            _log.info("支付宝对账文件URL为空");
            map.put("errDes", "支付宝对账文件URL为空");
            map.put(PayConstant.RETURN_PARAM_RETCODE, PayConstant.RETURN_VALUE_FAIL);
            return map;
        }
        // 下载文件
        String filePath = alipayProperties.getBillPath() + billDate + File.separator + batch.getAreaCode() + ".zip";
        try {
            File file = FileUtils.saveFile(billDownloadUrl, new File(filePath));
            List<String> filePathList = FileUtils.unZipFiles(file, alipayProperties.getBillPath() + billDate + File.separator + batch.getAreaCode() + File.separator);
            System.out.println(filePathList);
            map.put("bill", bill2Reconciliation(filePathList, batch, hbean));
        } catch (IOException e) {
            _log.error(e, "");
            map.put("errDes", "生成对账文件异常");
            map.put(PayConstant.RETURN_PARAM_RETCODE, PayConstant.RETURN_VALUE_FAIL);
        }
        return map;
    }

    /**
     * 将微信数据转为对账统一对象格式
     * @param filePathList
     * @return
     */
    List<ReconciliationEntity> bill2Reconciliation(List<String> filePathList, CheckBatch batch) {
        List<ReconciliationEntity> reconciliationEntityList = new LinkedList<>();
        if(CollectionUtils.isEmpty(filePathList)) return reconciliationEntityList;
        for(String filePath : filePathList) {
            if(filePath.contains("汇总")) {
                // 处理汇总数据
                try {
                    List<List<String>> csvList = FileUtils.readCSVFile(filePath, "GBK");
                    for(int i=0; i < csvList.size(); i++) {
                        List<String> strList = csvList.get(i);
                        if(i > 4 && strList.size() > 10) {
                            if(strList.get(0).contains("合计")) {

                                // 设置批次数据
                                batch.setBankTradeCount(Integer.parseInt(strList.get(2).trim()));
                                batch.setBankTradeAmount(new BigDecimal(strList.get(4).trim()).multiply(new BigDecimal(100)).longValue());
                                batch.setBankRefundAmount(null);
                                batch.setBankFee(new BigDecimal(strList.get(9).trim()).multiply(new BigDecimal(100)).longValue());

                                break;
                            }
                        }
                    }
                } catch (IOException e) {
                    _log.error(e, "");
                }
            }else {
                // 处理明细数据
                try {
                    List<List<String>> csvList = FileUtils.readCSVFile(filePath, "GBK");
                    for(int i=0; i < csvList.size(); i++) {
                        List<String> strList = csvList.get(i);
                        if(i > 4 && strList.size() > 20) {
                            //
                            ReconciliationEntity entity = new ReconciliationEntity();
                            // 设置支付时间
                            entity.setOrderTime(DateUtil.str2date(strList.get(4).trim()));
                            // 设置流水号
                            entity.setBankTrxNo(strList.get(0).trim());
                            entity.setBankOrderNo(strList.get(0).trim());
                            // 设置订单状态(默认全部是success)
                            entity.setBankTradeStatus("success");
                            // 设置账单金额(元转分)
                            entity.setBankAmount(new BigDecimal(strList.get(11).trim()).multiply(new BigDecimal(100)).longValue());
                            // 设置手续费(元转分)
                            entity.setBankFee(new BigDecimal(strList.get(22).trim()).multiply(new BigDecimal(100)).longValue());
                            reconciliationEntityList.add(entity);
                        }
                    }
                } catch (IOException e) {
                    _log.error(e, "");
                }
            }
        }
        return reconciliationEntityList;
    }


    List<AlipayTradeDetails> bill2Reconciliation(List<String> filePathList, CheckBatch batch, HospitalBean hbean) {
        List<AlipayTradeDetails> reconciliationEntityList = new LinkedList<AlipayTradeDetails>();
        if(CollectionUtils.isEmpty(filePathList)) return reconciliationEntityList;

        String billDate = DateUtil.date2Str( batch.getBillDate(), DateUtil.FORMAT_YYYY_MM_DD2);

        //银行收款总金额
        long receiveTradeAmt = 0;
        //银行退款总金额
        long refundTradeAmt = 0;
        for(String filePath : filePathList) {
            if(filePath.contains("汇总")) {
                // 处理汇总数据
                try {
                    List<List<String>> csvList = FileUtils.readCSVFile(filePath, "GBK");
                    for(int i=0; i < csvList.size(); i++) {
                        List<String> strList = csvList.get(i);
                        if(i > 4 && strList.size() > 10) {
                            if(strList.get(0).contains("合计")) {
                                // 设置批次数据
                                batch.setBankTradeCount(Integer.parseInt(strList.get(2).trim()) + Integer.parseInt(strList.get(3).trim()));
                                batch.setBankTradeReceiveCount(Integer.parseInt(strList.get(2).trim()));
                                batch.setBankTradeRefundCount(Integer.parseInt(strList.get(3).trim()));
                                //batch.setBankTradeAmount(new BigDecimal(strList.get(4).trim()).multiply(new BigDecimal(100)).longValue());
                                //batch.setBankRefundAmount(null);
                                batch.setBankFee(new BigDecimal(strList.get(9).trim()).multiply(new BigDecimal(100)).longValue());
                                batch.setHospitalId(Long.valueOf(hbean.getHospitalId()));
                                batch.setHospitalName(hbean.getHospitalName());
                                break;
                            }
                        }
                    }
                } catch (IOException e) {
                    _log.error(e, "");
                }
            }else {
                // 处理明细数据
                try {
                    List<List<String>> csvList = FileUtils.readCSVFile(filePath, "GBK");
                    for(int i=0; i < csvList.size(); i++) {
                        List<String> strList = csvList.get(i);
                        if(i > 4 && strList.size() > 20) {
                            //
                            AlipayTradeDetails entity = new AlipayTradeDetails();

                            entity.setChannelorderno(strList.get(0).trim());
                            entity.setPayorderid(strList.get(1).trim());
                            entity.setBustype(strList.get(2).trim());
                            entity.setSubject(strList.get(3).trim());
                            entity.setOrdercreatetime(strList.get(4).trim());
                            entity.setOrderendtime(strList.get(5).trim());
                            entity.setStoreno(strList.get(6).trim());
                            entity.setStorename(strList.get(7).trim());
                            entity.setOperatorname(strList.get(8).trim());
                            entity.setDevice(strList.get(9).trim());
                            entity.setAccountnofrom(strList.get(10).trim());
                            entity.setOrderamount(convert(strList.get(11).trim()));
                            entity.setAmount(convert(strList.get(12).trim()));
                            entity.setAlipayredenvelope(convert(strList.get(13).trim()));
                            entity.setJifenbao(convert(strList.get(14).trim()));
                            entity.setAlipaydiscount(convert(strList.get(15).trim()));
                            entity.setDiscountamount(convert(strList.get(16).trim()));
                            entity.setJuanhexiao(convert(strList.get(17).trim()));
                            entity.setJuan(strList.get(18).trim());
                            entity.setRedenvelopecons(strList.get(19).trim());
                            entity.setCardconsamount(convert(strList.get(20).trim()));
                            entity.setRefundbatchno(strList.get(21).trim());
                            entity.setServicecharge(convert(strList.get(22).trim()));
                            entity.setProfit(convert(strList.get(23).trim()));
                            entity.setRemark(null);
                            entity.setTradedate(billDate);
                            entity.setAreacode(batch.getAreaCode());
                            entity.setBatchno(batch.getBatchNo());
                            entity.setHospitalid(hbean.getHospitalId());
                            entity.setHospitalname(hbean.getHospitalName());
                            entity.setCreatetime(new Date());
                            entity.setUpdatetime(new Date());
                            reconciliationEntityList.add(entity);

                            if ("交易".equals(strList.get(2).trim()) || convert(strList.get(12).trim()) > 0) {
                                receiveTradeAmt += convert(strList.get(12).trim());
                            }

                            if ("退款".equals(strList.get(2).trim()) || convert(strList.get(12).trim()) < 0) {
                                refundTradeAmt += Math.abs(convert(strList.get(12).trim()));
                            }
                        }
                    }
                } catch (IOException e) {
                    _log.error(e, "");
                }
            }
            batch.setBankTradeAmount(receiveTradeAmt);
            batch.setBankRefundAmount(refundTradeAmt);
        }
        return reconciliationEntityList;
    }

    private Long convert(String amt) {
        if (StringUtils.isBlank(amt)) {
            return 0l;
        }
        return new BigDecimal(amt).multiply(new BigDecimal(100)).longValue();
    }


}
