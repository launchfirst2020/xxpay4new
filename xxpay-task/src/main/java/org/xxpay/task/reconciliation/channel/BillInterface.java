package org.xxpay.task.reconciliation.channel;

import com.alibaba.fastjson.JSONObject;
import org.xxpay.core.entity.CheckBatch;
import org.xxpay.task.reconciliation.nc.entity.HospitalBean;

import java.util.List;

/**
 * @author: dingzhiwei
 * @date: 18/1/18
 * @description:
 */
public interface BillInterface {

    JSONObject downloadBill(JSONObject param, CheckBatch batch);

    JSONObject downloadBill(JSONObject param, CheckBatch batch, HospitalBean hbean);

}
