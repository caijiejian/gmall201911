package com.fosu.gmall.manage.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.fosu.gmall.bean.PmsBaseAttrInfo;
import com.fosu.gmall.bean.PmsBaseAttrValue;
import com.fosu.gmall.service.AttrInfoService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin
public class AttrInfoController {

    @Reference
    AttrInfoService attrInfoService;

    @RequestMapping("attrInfoList")
    public List<PmsBaseAttrInfo> attrInfoList(String catalog3Id){
        List<PmsBaseAttrInfo> pmsBaseAttrInfo = attrInfoService.getAttrInfoList(catalog3Id);
        return pmsBaseAttrInfo;
    }

    @RequestMapping("saveAttrInfo")
    public int saveAttrInfo(@RequestBody PmsBaseAttrInfo pmsBaseAttrInfo){
        int result = attrInfoService.saveAttrInfo(pmsBaseAttrInfo);
        return result;
    }


}
