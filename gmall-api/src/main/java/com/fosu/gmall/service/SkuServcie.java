package com.fosu.gmall.service;

import com.fosu.gmall.bean.PmsSkuInfo;

public interface SkuServcie {
    void saveSkuInfo(PmsSkuInfo pmsSkuInfo);
    PmsSkuInfo getSkuInfo(String skuId);
}
