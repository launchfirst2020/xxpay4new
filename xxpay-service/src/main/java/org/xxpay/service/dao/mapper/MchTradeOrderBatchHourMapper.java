package org.xxpay.service.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.xxpay.core.entity.MchTradeOrderBatchHour;

import java.util.List;
import java.util.Map;

public interface MchTradeOrderBatchHourMapper extends BaseMapper<MchTradeOrderBatchHour> {

    //查找支付趋势数据
    List<MchTradeOrderBatchHour> selectPayTrend(Map conditon);

    //查询交易日小时时段的跑批数据
    List<MchTradeOrderBatchHour> selectHourBatch(Map<String, Object> paramMap);

    //查询小时时段跑批数据
    int insertHourBatch(List<MchTradeOrderBatchHour> list);
}
