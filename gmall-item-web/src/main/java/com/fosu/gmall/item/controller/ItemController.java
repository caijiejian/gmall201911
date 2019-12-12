package com.fosu.gmall.item.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.fosu.gmall.bean.PmsProductSaleAttr;
import com.fosu.gmall.bean.PmsSkuInfo;
import com.fosu.gmall.bean.PmsSkuSaleAttrValue;
import com.fosu.gmall.service.SkuServcie;
import com.fosu.gmall.service.SpuService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class ItemController {

    @Reference
    SkuServcie skuServcie;
    @Reference
    SpuService spuService;

    @RequestMapping("{skuId}.html")
    public String index(@PathVariable String skuId, ModelMap modelMap){
        //modelMap、Model、map底层都是BangDingAwareMap；modelAndView--》public ModelAndView
        PmsSkuInfo skuInfo = skuServcie.getSkuInfo(skuId);
        modelMap.addAttribute("skuInfo",skuInfo);

        List<PmsProductSaleAttr> spuSaleAttrListCheckBySku = spuService.getSpuSaleAttrListCheckBySku(skuInfo.getProductId(), skuInfo.getId());
        modelMap.addAttribute("spuSaleAttrListCheckBySku",spuSaleAttrListCheckBySku);

        Map<String,String> skuSaleAttrHash  = new HashMap<>();
        List<PmsSkuInfo> skuInfos = skuServcie.getSkuSaleAttrValueListBySpu(skuInfo.getProductId());
        for(PmsSkuInfo pmsSkuInfo:skuInfos){
            String k = "";
            String v = pmsSkuInfo.getId();
            List<PmsSkuSaleAttrValue> skuSaleAttrValueList = pmsSkuInfo.getSkuSaleAttrValueList();
            for(PmsSkuSaleAttrValue pmsSkuSaleAttrValue:skuSaleAttrValueList){
                k += pmsSkuSaleAttrValue.getSaleAttrValueId() + "|";
            }
            skuSaleAttrHash.put(k,v);
        }
        String skuSaleAttrHashJsonStr = JSON.toJSONString(skuSaleAttrHash);
        modelMap.put("skuSaleAttrHashJsonStr",skuSaleAttrHashJsonStr);

        return "item";
    }
}
