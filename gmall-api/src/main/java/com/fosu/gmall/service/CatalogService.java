package com.fosu.gmall.service;


import com.fosu.gmall.bean.PmsBaseCatalog1;
import com.fosu.gmall.bean.PmsBaseCatalog2;
import com.fosu.gmall.bean.PmsBaseCatalog3;

import java.util.List;

public interface CatalogService{
    List<PmsBaseCatalog1> getPmsCatalog1();
    List<PmsBaseCatalog2> getPmsCatalog2(String catalog1Id);
    List<PmsBaseCatalog3> getPmsCatalog3(String catalog2Id);
}
