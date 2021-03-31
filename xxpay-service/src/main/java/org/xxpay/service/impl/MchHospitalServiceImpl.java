package org.xxpay.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xxpay.core.entity.MchHospital;
import org.xxpay.core.service.IMchHospitalService;
import org.xxpay.service.dao.mapper.MchHospitalMapper;

@Service
public class MchHospitalServiceImpl extends ServiceImpl<MchHospitalMapper, MchHospital> implements IMchHospitalService {

    @Autowired
    private MchHospitalMapper mchHospitalMapper;

    @Override
    public IPage<MchHospital> selectPage(IPage page, MchHospital mchHospital) {
        //LambdaQueryWrapper<MchInfo> queryWrapper = getQueryWrapper(mchInfo);
        //        queryWrapper.orderByDesc(MchInfo::getCreateTime);
        //        return page(page, queryWrapper);
        LambdaQueryWrapper<MchHospital> queryWrapper = getQueryWrapper(mchHospital);
        queryWrapper.orderByDesc(MchHospital::getCreateTime);
        return page(page, queryWrapper);
    }

    @Override
    public MchHospital findByHospitalId(Long hospitalId) {
        return mchHospitalMapper.selectById(hospitalId);
    }

    @Override
    public MchHospital findByMchId(Long mchId) {
        MchHospital mchHospital = new MchHospital();
        mchHospital.setMchId(mchId);
        LambdaQueryWrapper<MchHospital> queryWrapper = getQueryWrapper(mchHospital);
        return getOne(queryWrapper);
    }

    /** 生成[wrapper]查询条件 **/
    private LambdaQueryWrapper<MchHospital> getQueryWrapper(MchHospital mchHospital){

        LambdaQueryWrapper<MchHospital> queryWrapper = new LambdaQueryWrapper();

        if(mchHospital != null) {
            if(mchHospital.getHospitalId() != null) queryWrapper.eq(MchHospital::getHospitalId, mchHospital.getHospitalId());
            if(StringUtils.isNotEmpty(mchHospital.getHospitalName())) queryWrapper.eq(MchHospital::getHospitalName, mchHospital.getHospitalName());
            //if(StringUtils.isNotEmpty(mchInfo.getLoginMobile())) queryWrapper.eq(MchInfo::getLoginMobile, mchInfo.getLoginMobile());
            if(mchHospital.getProvinceCode() != null) queryWrapper.eq(MchHospital::getProvinceCode, mchHospital.getProvinceCode());
            if(mchHospital.getCityCode() != null) queryWrapper.eq(MchHospital::getCityCode, mchHospital.getCityCode());
            if(mchHospital.getAreaCode() != null) queryWrapper.eq(MchHospital::getAreaCode, mchHospital.getAreaCode());
            if(mchHospital.getStatus() != null) queryWrapper.eq(MchHospital::getStatus, mchHospital.getStatus());

        }
        return queryWrapper;
    }
}
