package org.xxpay.service.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.xxpay.core.entity.WeixinTradeDetails;

import java.util.List;

/**
 * <p>
 * 微信交易流水表 Mapper 接口
 * </p>
 *
 * @author andy
 * @since 2021-03-29
 */
public interface WeixinTradeDetailsMapper extends BaseMapper<WeixinTradeDetails> {

    /**
     * 插入微信流水记录
     * @param weixinTradeDetails
     * @return
     */
    int insertBatch(List<WeixinTradeDetails> weixinTradeDetails);

    /**
     * 删除微信流水记录
     * @return
     */
    int removeAll();

}
