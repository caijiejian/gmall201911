package com.fosu.gmall.manage.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.fosu.gmall.bean.*;
import com.fosu.gmall.manage.util.PmsUploadUtil;
import com.fosu.gmall.service.SpuService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@CrossOrigin
public class SpuController {

    @Reference
    SpuService spuService;

    @RequestMapping("spuList")
    public List<PmsProductInfo> spuList(String catalog3Id){
        List<PmsProductInfo> spuList = spuService.getSpuList(catalog3Id);
        return spuList;
    }

    @RequestMapping("baseSaleAttrList")
    public List<PmsBaseSaleAttr> baseSaleAttrList(){
        List<PmsBaseSaleAttr> baseSaleAttrList = spuService.getBaseSaleAttrList();
        return baseSaleAttrList;
    }

    @RequestMapping("saveSpuInfo")
    public String saveSpuInfo(@RequestBody PmsProductInfo pmsProductInfo){
        spuService.saveSpuInfo(pmsProductInfo);
        return "success";
    }

    @RequestMapping("fileUpload")
    public String fileUpload(@RequestParam("file") MultipartFile multipartFile){
        String imgUrl = PmsUploadUtil.uploadImage(multipartFile);
        return imgUrl;
    }

    @RequestMapping("spuSaleAttrList")
    public List<PmsProductSaleAttr> spuSaleAttrList(String spuId){
        List<PmsProductSaleAttr> spuSaleAttrList = spuService.getSpuSaleAttrList(spuId);
        return spuSaleAttrList;
    }

    @RequestMapping("spuImageList")
    public List<PmsProductImage> spuImageList(String spuId){
        List<PmsProductImage> spuImageList = spuService.getSpuImageList(spuId);
        return spuImageList;
    }
}
