package com.fosu.gmall.manage.serviceImpl;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.fosu.gmall.bean.PmsSkuAttrValue;
import com.fosu.gmall.bean.PmsSkuImage;
import com.fosu.gmall.bean.PmsSkuInfo;
import com.fosu.gmall.bean.PmsSkuSaleAttrValue;
import com.fosu.gmall.manage.mapper.PmsSkuAttrValueMapper;
import com.fosu.gmall.manage.mapper.PmsSkuImageMapper;
import com.fosu.gmall.manage.mapper.PmsSkuInfoMapper;
import com.fosu.gmall.manage.mapper.PmsSkuSaleAttrValueMapper;
import com.fosu.gmall.service.SkuServcie;
import com.fosu.gmall.util.RedisUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ResponseStatus;
import redis.clients.jedis.Jedis;
import tk.mybatis.mapper.entity.Example;

import java.util.List;
import java.util.UUID;

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
    @Autowired
    RedisUtil redisUtil;

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

    public PmsSkuInfo getSkuInfoFromDb(String skuId) {
        PmsSkuInfo pmsSkuInfo = pmsSkuInfoMapper.selectByPrimaryKey(skuId);

//        String id = pmsSkuInfo.getId();
        Example example = new Example(PmsSkuSaleAttrValue.class);
        example.createCriteria().andEqualTo("skuId",skuId);
        List<PmsSkuSaleAttrValue> pmsSkuSaleAttrValues = pmsSkuSaleAttrValueMapper.selectByExample(example);
        pmsSkuInfo.setSkuSaleAttrValueList(pmsSkuSaleAttrValues);

        Example example1 = new Example(PmsSkuAttrValue.class);
        example1.createCriteria().andEqualTo("skuId",skuId);
        List<PmsSkuAttrValue> pmsSkuAttrValues = pmsSkuAttrValueMapper.selectByExample(example1);
        pmsSkuInfo.setSkuAttrValueList(pmsSkuAttrValues);

        Example example2 = new Example(PmsSkuImage.class);
        example2.createCriteria().andEqualTo("skuId",skuId);
        List<PmsSkuImage> pmsSkuImages = pmsSkuImageMapper.selectByExample(example2);
        pmsSkuInfo.setSkuImageList(pmsSkuImages);

        return pmsSkuInfo;
    }

    @Override
    public PmsSkuInfo getSkuInfo(String skuId){
        PmsSkuInfo skuInfo=null;
        Jedis jedis = redisUtil.getJedis();
        String k = "sku:"+skuId+":info";
        String skuJson = jedis.get(k);
        if(StringUtils.isBlank(skuJson)){
            // 设置分布式锁
            String token = UUID.randomUUID().toString();
            String OK = jedis.set("sku:" + skuId + ":lock", token, "nx", "px", 10*1000);// 拿到锁的线程有10秒的过期时间
            if(StringUtils.isNotBlank(OK)&&OK.equals("ok")){
                skuInfo = getSkuInfoFromDb(skuId);
                if(skuInfo!=null) {
                    skuJson = JSON.toJSONString(skuInfo);
                    jedis.set(k, skuJson);
                }else{
                    jedis.setex(k,60*3,JSON.toJSONString(""));
                }
                String lockToken = jedis.get("sku:" + skuId + ":lock");
                if(StringUtils.isNotBlank(lockToken)&&lockToken.equals(token)){
                    jedis.del("sku:" + skuId + ":lock");
                }
            }else{
                return getSkuInfo(skuId);
            }
        }else{
            skuInfo = JSON.parseObject(skuJson,PmsSkuInfo.class);
        }
        jedis.close();
        return skuInfo;
    }

    @Override
    public List<PmsSkuInfo> getSkuInfos(String productId) {
        Example example = new Example(PmsSkuInfo.class);
        example.createCriteria().andEqualTo("productId",productId);
        return pmsSkuInfoMapper.selectByExample(example);
    }

    @Override
    public List<PmsSkuInfo> getSkuSaleAttrValueListBySpu(String productId) {
        return pmsSkuInfoMapper.selectSkuSaleAttrValueListBySpu(productId);
    }
}
