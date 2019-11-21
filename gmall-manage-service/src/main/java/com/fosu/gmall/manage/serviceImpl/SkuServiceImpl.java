package com.fosu.gmall.manage.serviceImpl;

import com.alibaba.dubbo.config.annotation.Service;
import com.fosu.gmall.bean.PmsSkuAttrValue;
import com.fosu.gmall.bean.PmsSkuImage;
import com.fosu.gmall.bean.PmsSkuInfo;
import com.fosu.gmall.bean.PmsSkuSaleAttrValue;
import com.fosu.gmall.manage.mapper.PmsSkuAttrValueMapper;
import com.fosu.gmall.manage.mapper.PmsSkuImageMapper;
import com.fosu.gmall.manage.mapper.PmsSkuInfoMapper;
import com.fosu.gmall.manage.mapper.PmsSkuSaleAttrValueMapper;
import com.fosu.gmall.service.SkuServcie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

@Service
public class SkuServiceImpl implements SkuServcie {

    @Autowired
    PmsSkuInfoMapper pmsSkuInfoMapper;

    @Autowired
    PmsSkuAttrValueMapper pmsSkuAttrValueMapper;
    @Autowired
    PmsSkuSaleAttrValueMapper pmsSkuSaleAttrValueMapper;
    @Autowired
    PmsSkuImageMapper pmsSkuImageMapper;
    @Override
    public void saveSkuInfo(PmsSkuInfo pmsSkuInfo) {
        pmsSkuInfoMapper.insertSelective(pmsSkuInfo);
        String skuId = pmsSkuInfo.getId();

        List<PmsSkuAttrValue> skuAttrValueList = pmsSkuInfo.getSkuAttrValueList();
        for(PmsSkuAttrValue pmsSkuAttrValue:skuAttrValueList){
            pmsSkuAttrValue.setSkuId(skuId);
            pmsSkuAttrValueMapper.insertSelective(pmsSkuAttrValue);
        }
        List<PmsSkuSaleAttrValue> skuSaleAttrValueList = pmsSkuInfo.getSkuSaleAttrValueList();
        for(PmsSkuSaleAttrValue pmsSkuSaleAttrValue:skuSaleAttrValueList){
            pmsSkuSaleAttrValue.setSkuId(skuId);
            pmsSkuSaleAttrValueMapper.insertSelective(pmsSkuSaleAttrValue);
        }
        List<PmsSkuImage> skuImageList = pmsSkuInfo.getSkuImageList();
        for(PmsSkuImage pmsSkuImage:skuImageList){
            pmsSkuImage.setSkuId(skuId);
            pmsSkuImageMapper.insertSelective(pmsSkuImage);
        }

    }
}
