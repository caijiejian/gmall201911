package com.fosu.gmall.service;

import com.fosu.gmall.bean.PmsBaseAttrInfo;

import java.util.List;

public interface AttrInfoService {
    List<PmsBaseAttrInfo> getAttrInfoList(String catalog3Id);

    int saveAttrInfo(PmsBaseAttrInfo pmsBaseAttrInfo);
}
