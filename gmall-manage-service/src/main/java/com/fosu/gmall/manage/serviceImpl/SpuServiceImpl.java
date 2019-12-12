package com.fosu.gmall.manage.serviceImpl;

import com.alibaba.dubbo.config.annotation.Service;
import com.fosu.gmall.bean.*;
import com.fosu.gmall.manage.mapper.*;
import com.fosu.gmall.service.SpuService;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class SpuServiceImpl implements SpuService {

    @Autowired
    PmsProductInfoMapper pmsProductInfoMapper;
    @Autowired
    PmsBaseSaleAttrMapper pmsBaseSaleAttrMapper;
    @Autowired
    PmsProductSaleAttrMapper pmsProductSaleAttrMapper;
    @Autowired
    PmsProductImageMapper pmsProductImageMapper;
    @Autowired
    PmsProductSaleAttrValueMapper pmsProductSaleAttrValueMapper;
    @Autowired
    PmsBaseAttrValueMapper pmsBaseAttrValueMapper;

    @Override
    public List<PmsProductInfo> getSpuList(String catalog3Id) {
        return pmsProductInfoMapper.selectAll();
    }

    @Override
    public List<PmsBaseSaleAttr> getBaseSaleAttrList() {
        return pmsBaseSaleAttrMapper.selectAll();
    }

    @Override
    public int saveSpuInfo(PmsProductInfo pmsProductInfo) {
        int i = pmsProductInfoMapper.insertSelective(pmsProductInfo);
        String id = pmsProductInfo.getId();
        List<PmsProductSaleAttr> spuSaleAttrList = pmsProductInfo.getSpuSaleAttrList();
        for (PmsProductSaleAttr pmsProductSaleAttr:spuSaleAttrList) {
            pmsProductSaleAttr.setProductId(id);
            pmsProductSaleAttrMapper.insertSelective(pmsProductSaleAttr);

            List<PmsProductSaleAttrValue> spuSaleAttrValueList = pmsProductSaleAttr.getSpuSaleAttrValueList();
            for(PmsProductSaleAttrValue pmsProductSaleAttrValue:spuSaleAttrValueList){
                pmsProductSaleAttrValue.setProductId(id);
                pmsProductSaleAttrValueMapper.insertSelective(pmsProductSaleAttrValue);
            }
        }

        List<PmsProductImage> spuImageList = pmsProductInfo.getSpuImageList();
        for(PmsProductImage pmsProductImage:spuImageList){
            pmsProductImage.setProductId(id);
            pmsProductImageMapper.insertSelective(pmsProductImage);
        }
        return 1;
    }

    @Override
    public List<PmsProductSaleAttr> getSpuSaleAttrList(String spuId) {
        Example example = new Example(PmsProductSaleAttr.class);
        example.createCriteria().andEqualTo("productId",spuId);
        List<PmsProductSaleAttr> pmsProductSaleAttrs = pmsProductSaleAttrMapper.selectByExample(example);
        
        for(PmsProductSaleAttr pmsProductSaleAttr:pmsProductSaleAttrs){
            Example example1 = new Example(PmsProductSaleAttr.class);
            example1.createCriteria().andEqualTo("saleAttrId",pmsProductSaleAttr.getSaleAttrId()).andEqualTo("productId",spuId);
            List<PmsProductSaleAttrValue> pmsProductSaleAttrValues = pmsProductSaleAttrValueMapper.selectByExample(example1);
            pmsProductSaleAttr.setSpuSaleAttrValueList(pmsProductSaleAttrValues);
        }
        return pmsProductSaleAttrs;
    }

    @Override
    public List<PmsProductImage> getSpuImageList(String spuId) {
        Example example = new Example(PmsProductImage.class);
        example.createCriteria().andEqualTo("productId",spuId);
        return pmsProductImageMapper.selectByExample(example);
    }

    @Override
    public List<PmsProductSaleAttr> getSpuSaleAttrListCheckBySku(String productId, String skuId) {
//        Example example = new Example(PmsProductSaleAttr.class);
//        example.createCriteria().andEqualTo("productId",productId);
//        List<PmsProductSaleAttr> pmsProductSaleAttrs = pmsProductSaleAttrMapper.selectByExample(example);
//        for(PmsProductSaleAttr pmsProductSaleAttr:pmsProductSaleAttrs){
//            String saleAttrId = pmsProductSaleAttr.getSaleAttrId();
//            Example example1 = new Example(PmsProductSaleAttrValue.class);
//            example1.createCriteria().andEqualTo("saleAttrId", saleAttrId).andEqualTo("productId",productId);
//            List<PmsProductSaleAttrValue> pmsProductSaleAttrValues = pmsProductSaleAttrValueMapper.selectByExample(example1);
//            pmsProductSaleAttr.setSpuSaleAttrValueList(pmsProductSaleAttrValues);
//        }
        List<PmsProductSaleAttr> pmsProductSaleAttrs = pmsProductSaleAttrMapper.getSpuSaleAttrListCheckBySku(productId, skuId);
        return pmsProductSaleAttrs;
    }
}
