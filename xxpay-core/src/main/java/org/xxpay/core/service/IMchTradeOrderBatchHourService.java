package org.xxpay.core.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.xxpay.core.entity.MchTradeOrderBatchHour;

import java.util.List;
import java.util.Map;

public interface IMchTradeOrderBatchHourService  extends IService<MchTradeOrderBatchHour> {

    /** 添加商户 **/
    void add(MchTradeOrderBatchHour mchTradeOrderBatchHour);

    List<MchTradeOrderBatchHour> selectPayTrend(Map conditon);

    //查询交易日小时时段数据
    List<MchTradeOrderBatchHour> selectHourBatch(Map<String, Object> paramMap);

    //插入交易日小时时段数据
    int insertHourBatch(List<MchTradeOrderBatchHour> list);
}
