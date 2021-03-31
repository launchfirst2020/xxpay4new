package org.xxpay.core.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.xxpay.core.entity.ChannelTask;

import java.util.List;

public interface IChannelTaskService  extends IService<ChannelTask> {

    List<ChannelTask> selectByStatus(Byte status);

}
