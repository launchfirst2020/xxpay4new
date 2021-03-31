package org.xxpay.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xxpay.core.entity.ChannelTask;
import org.xxpay.core.service.IChannelTaskService;
import org.xxpay.service.dao.mapper.ChannelTaskMapper;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

@Service
public class ChannelTaskServiceImpl extends ServiceImpl<ChannelTaskMapper, ChannelTask> implements IChannelTaskService {

    @Autowired
    private ChannelTaskMapper channelTaskMapper;

    @Override
    public List<ChannelTask> selectByStatus(Byte status) {
        return channelTaskMapper.selectChannelTaskByStatus(status);
    }

}
