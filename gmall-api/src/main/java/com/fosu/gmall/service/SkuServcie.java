package com.fosu.gmall.service;

import com.fosu.gmall.bean.PmsSkuInfo;

import java.util.List;

public interface SkuServcie {
    void saveSkuInfo(PmsSkuInfo pmsSkuInfo);
    PmsSkuInfo getSkuInfo(String skuId);

    List<PmsSkuInfo> getSkuInfos(String productId);

    List<PmsSkuInfo> getSkuSaleAttrValueListBySpu(String productId);
}
