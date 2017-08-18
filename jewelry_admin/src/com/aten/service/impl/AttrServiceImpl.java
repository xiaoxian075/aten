package com.aten.service.impl;

import com.aten.model.orm.*;
import com.aten.service.AttrValueService;
import com.aten.service.CatAttrService;
import com.aten.service.CatService;
import com.communal.util.Query;
import com.communal.util.StringUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.aten.dao.AttrDao;
import com.aten.dao.CustomAttrDao;
import com.aten.function.SysconfigFuc;
import com.aten.service.AttrService;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("all")
@Service("attrService")
@Transactional
public class AttrServiceImpl extends CommonServiceImpl<Attr, String> implements AttrService {

    private AttrDao attrDao;
    @Resource
    private CatService catService;
    @Autowired
    private AttrValueService attrValueService;
    @Autowired
    private CatAttrService catAttrService;

    @Autowired
    public AttrServiceImpl(AttrDao attrDao) {
        super(attrDao);
        this.attrDao = attrDao;
    }

    @Override
    public List<Attr> queryList(Query query) {
        return attrDao.queryList(query);
    }

    public Map getAttrByCatId(Cat cat, String goods_id) {
        Map map = new HashMap();
        String[] arrayStr = cat.getLevel_cat().split(",");
        /*List<String> list = java.util.Arrays.asList(arrayStr);*/
        //取出数组最后一个元素
        List lastList = new ArrayList<>();
        lastList.add(arrayStr[arrayStr.length - 1]);
        List<AttrVo> attrVoList = attrDao.getAttrByCatId(lastList);
        List<AttrVo> skuList = attrDao.getAttrBySku(lastList);

        //mybatis特性：同样的sql语句出来的对象只有一份
        //此处作深拷贝
        if (!StringUtil.isEmpty(goods_id)) {
            for (AttrVo v : skuList) {
                List<AttrValue> newData = new ArrayList<AttrValue>();
                List<AttrValue> oldList = v.getData();
                if (!StringUtil.isNullOrEmpty(oldList)) {
                    for (AttrValue av : oldList) {
                        newData.add(new AttrValue(av.getAttr_value_ico(), av.getSort_no(), av.getAttr_value_id(), av.getAttr_id(), av.getAttr_value(), av.getAttr_name(), av.getState()));
                    }
                    v.setData(newData);
                }
            }
        }

        map.put("attrVoList", attrVoList);
        map.put("skuList", skuList);
        return map;
    }

    //禁用
    @Override
    public void limitState(String parameter_id) {
        String[] ids = parameter_id.split(",");
        if (ids != null) {
            for (String id : ids) {
                //检查是否已被商品分类引用
                if (!checkAttr(id)) {
                    Attr attr = get(id);
                    attr.setState("0");
                    attr.setAttr_id(id);
                    update(attr);
                    //禁用关联的属性值
                    updateAttrValues(id, "0");
                }

            }
        }

    }

    //启用
    @Override
    public void enableState(String parameter_id) {
        String[] ids = parameter_id.split(",");
        if (ids != null) {
            for (String id : ids) {
                Attr attr = get(id);
                attr.setState("1");
                attr.setAttr_id(id);
                update(attr);
                //启用关联的属性值
                updateAttrValues(id, "1");
            }
        }
    }

    @Override
    public void batchdelete(String[] ids) {

        //删除关联的属性值
        if (ids != null) {
            for (String id : ids) {
                //检查是否已被商品分类引用
                if (!checkAttr(id)) {
                    deleteOne(id);
                    deleteAttrValue(id);
                }
            }

        }

    }

    public boolean checkAttr(String attr_id) {
        List<CatAttr> list=catAttrService.findByAttrId(attr_id);
        if(list!=null&&list.size()>0){
            return true;
        }
        return false;
    }

    private void deleteAttrValue(String attr_id) {
        List<AttrValue> attrValueList = attrValueService.findByAttrId(attr_id);
        for (AttrValue attrValue : attrValueList) {
            attrValueService.deleteOne(attrValue.getAttr_value_id());
        }
    }

    private void updateAttrValues(String attr_id, String state) {
        List<AttrValue> attrValueList = attrValueService.findByAttrId(attr_id);
        for (AttrValue attrValue : attrValueList) {
            attrValue.setState(state);
            attrValueService.update(attrValue);
        }
    }

}




