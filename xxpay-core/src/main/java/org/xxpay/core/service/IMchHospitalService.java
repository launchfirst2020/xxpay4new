package org.xxpay.core.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import org.xxpay.core.entity.MchHospital;

/**
 * 商户与医院对应的关系
 */
public interface IMchHospitalService extends IService<MchHospital> {

    IPage<MchHospital> selectPage(IPage page, MchHospital mchHospital);

}

