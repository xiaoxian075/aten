package com.aten.service.impl;

import com.aten.dao.CatDao;
import com.aten.function.CatFuc;
import com.aten.function.SysconfigFuc;
import com.aten.model.orm.Cat;
import com.aten.model.orm.CatRate;
import com.aten.model.orm.DivideRecord;
import com.aten.model.orm.PreGoodscat;
import com.aten.service.CatService;
import com.aten.service.PreGoodscatService;
import com.aten.service.PreService;
import com.communal.constants.Constant;
import com.communal.util.ChineseToEnglishUtil;
import com.communal.util.RandomCharUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Service("preService")
@Transactional
public class PreServiceImpl extends CommonServiceImpl<Cat, String> implements PreService {

    private CatDao catDao;
    @Autowired
    private CatService catService;
    @Autowired
    private PreGoodscatService preGoodscatService;

    @Autowired
    public PreServiceImpl(CatDao catDao) {
        super(catDao);
        this.catDao = catDao;
    }

    @Override
    public void saveInfo(HttpServletRequest request, Cat cat) {
        String uuid = RandomCharUtil.getNumberRand();
        cat.setCat_id(uuid);
        cat.setModule("pre");
        cat.setIs_sys("0");
        cat.setWord_index(ChineseToEnglishUtil.getPinYinHeadChar(cat.getCat_name()));
        //上级分类
        String parent_id = SysconfigFuc.getSysValue("cfg_pre_top");
        String parent_cat_id = cat.getParent_cat_id();
        if (!"".equals(parent_cat_id) && parent_cat_id.length() >= 10) {
            parent_id = parent_cat_id.substring(parent_cat_id.length() - 10, parent_cat_id.length());
        }
        cat.setParent_cat_id(parent_id);

        Cat parent_cat = catService.get(parent_id);
        if (parent_cat != null) {
            //级别串
            cat.setLevel_cat(parent_cat.getLevel_cat() + "," + uuid);
            //级别
            cat.setCat_level(Integer.parseInt(parent_cat.getCat_level()) + 1 + "");
        }
        catService.insert(cat);

        //保存关联的数据
        SaveRelationDate(cat);
    }

    public void SaveRelationDate(Cat cat) {
        //添加前台分类关联的商品分类
        String[] catIds = cat.getCatIds();
        if (catIds != null && catIds.length > 0) {
            for (String catId : catIds) {
                PreGoodscat preGoodcat = new PreGoodscat();
                preGoodcat.setCat_id(catId);
                preGoodcat.setPrecat_id(cat.getCat_id());
                preGoodscatService.insert(preGoodcat);
            }
        }

    }

    @Override
    public void updateInfo(HttpServletRequest request, Cat cat) {
        //上级分类

        //cat.setParent_cat_id(cat.getParent_cat_id());

        //Cat parent_cat = catService.get(cat.getParent_cat_id());
        //级别串
      //  cat.setLevel_cat(parent_cat.getLevel_cat() + "," + cat.getCat_id());
        //级别
       // cat.setCat_level(Integer.parseInt(parent_cat.getCat_level()) + 1 + "");
        cat.setWord_index(ChineseToEnglishUtil.getPinYinHeadChar(cat.getCat_name()));
        String precat_id = cat.getCat_id();
        //先删除关联属性
        preGoodscatService.deletePreCat(precat_id);
        SaveRelationDate(cat);
        this.catService.update(cat);
        //修改下级串
        catService.setLevel(cat.getCat_id());
    }

}




