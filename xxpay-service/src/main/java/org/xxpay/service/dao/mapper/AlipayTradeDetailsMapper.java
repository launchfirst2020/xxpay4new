package org.xxpay.service.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.xxpay.core.entity.AlipayTradeDetails;

import java.util.List;

/**
 * <p>
 * 支付宝交易流水表 Mapper 接口
 * </p>
 *
 * @author andy
 * @since 2021-03-29
 */
public interface AlipayTradeDetailsMapper extends BaseMapper<AlipayTradeDetails> {

    /**
     * 批量插入支付宝交易记录
     * @param alipayTradeDetails
     * @return
     */
    int insertBatch(List<AlipayTradeDetails> alipayTradeDetails);

    /**
     * 删除支付宝流水记录
     * @return
     */
    int removeAll();

}
