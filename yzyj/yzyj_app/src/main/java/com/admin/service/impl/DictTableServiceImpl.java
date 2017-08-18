package com.admin.service.impl;

import com.admin.dao.DictTableDaoMapper;
import com.admin.model.BaseExample;
import com.admin.model.DictTable;
import com.admin.service.DictTableService;
import com.core.generic.GenericDao;
import com.core.generic.GenericServiceImpl;
import com.core.mybatis.DataTablesPage;
import com.core.util.CommConstant;
import com.core.util.CommDictList;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/4/15.
 */
@Service
public class DictTableServiceImpl extends GenericServiceImpl<DictTable, String> implements DictTableService {
    @Resource
    private DictTableDaoMapper dictTableDaoMapper;
    @Override
    public GenericDao<DictTable, String> getDao() {
        return dictTableDaoMapper;
    }


    public List<DictTable> selectByExampleAndPage(DataTablesPage<DictTable> page, BaseExample baseExample){
        return dictTableDaoMapper.selectByExampleAndPage(page,baseExample);
    }
    public List<DictTable> selectByExample(BaseExample baseExample){
        return dictTableDaoMapper.selectByExample(baseExample);
    }

    public List<DictTable> selectList(String querytable){
        return getDictList(querytable);
    }
    private List<DictTable> getDictList(String querytable){
        List<DictTable> retList = null;
        try{
            if(CommConstant.dictMapList.size() == 0){
                initDictData();
            }
            retList = CommConstant.dictMapList.get(querytable);
        }catch (Exception e ){
            e.printStackTrace();
        }
        return retList;
    }

    public void initDictData(){
        try{
            BaseExample baseExample = new  BaseExample();
            baseExample.setOrderByClause("dict_table_bh , dict_bh ");
            List<DictTable>  list = dictTableDaoMapper.selectByExample(baseExample);
            String tablebh = "";
            List<DictTable>  subList = new ArrayList() ;
            for(DictTable vo : list){
                if(tablebh.equals(vo.getDictTableBh())){
                    subList.add(vo);
                }else{
                    //根据表保存至map中
                    if(subList.size() > 0){
                        CommConstant.dictMapList.put(tablebh,subList);
                    }
                    tablebh = vo.getDictTableBh();
                    subList = null;
                    subList = new ArrayList() ;
                    subList.add(vo);
                }
            }
            if(subList.size() > 0){
                CommConstant.dictMapList.put(tablebh,subList);
            }
            list = null;
            subList = null;
            /**
             * 初始化静态参数
             */
            initConstantsParameter();
        }catch (Exception e ){
            e.printStackTrace();
        }
    }

    /**
     * 初始参数
     */
    public void initConstantsParameter(){
        try{
        }catch (Exception e ){
            e.printStackTrace();
        }
    }

}
