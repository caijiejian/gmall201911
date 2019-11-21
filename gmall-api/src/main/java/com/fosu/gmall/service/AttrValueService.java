package com.fosu.gmall.service;

import com.fosu.gmall.bean.PmsBaseAttrValue;

import java.util.List;

public interface AttrValueService {
    List<PmsBaseAttrValue> getAttrValueList(String attrId);
}
