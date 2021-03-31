package org.xxpay.service.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.xxpay.core.entity.ChannelTask;

import java.util.List;

public interface ChannelTaskMapper extends BaseMapper<ChannelTask> {

    List<ChannelTask> selectChannelTaskByStatus(Byte status);

}
