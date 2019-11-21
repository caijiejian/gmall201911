package com.fosu.gmall.manage.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.fosu.gmall.bean.PmsBaseAttrValue;
import com.fosu.gmall.service.AttrValueService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin
public class AttrValueController {

    @Reference
    AttrValueService attrValueService;

    @RequestMapping("getAttrValueList")
    public List<PmsBaseAttrValue> getAttrValueList(String attrId){
        List<PmsBaseAttrValue> pmsBaseAttrValues=attrValueService.getAttrValueList(attrId);
        return pmsBaseAttrValues;
    }
}
