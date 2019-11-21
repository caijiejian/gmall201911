package com.fosu.gmall.manage.serviceImpl;

import com.alibaba.dubbo.config.annotation.Service;
import com.fosu.gmall.bean.PmsBaseAttrValue;
import com.fosu.gmall.manage.mapper.PmsBaseAttrValueMapper;
import com.fosu.gmall.service.AttrValueService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service
public class AttrValueServiceImpl implements AttrValueService {
    @Autowired
    PmsBaseAttrValueMapper pmsBaseAttrValueMapper;

    @Override
    public List<PmsBaseAttrValue> getAttrValueList(String attrId) {
        PmsBaseAttrValue pmsBaseAttrValue = new PmsBaseAttrValue();
        pmsBaseAttrValue.setAttrId(attrId);
        return pmsBaseAttrValueMapper.select(pmsBaseAttrValue);
    }
}
