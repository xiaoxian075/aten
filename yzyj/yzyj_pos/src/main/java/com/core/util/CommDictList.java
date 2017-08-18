package com.core.util;

import com.admin.model.DictTable;

import java.util.List;

/**
 * Created by Administrator on 2016/7/15.
 */
public class CommDictList {


    public static List<DictTable> getDictOptionList (String table){
        List<DictTable> list = null;
        try{
            list = CommConstant.dictMapList.get(table);
        }catch (Exception e ){
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 获取单条字典
     * 应用于:默认值配置
     */
    public static DictTable getDictTable (String table, String val){
        DictTable dictTable = new DictTable();
        try{
            dictTable.setDictBh("");
            dictTable.setDictMc("");
            List<DictTable> list = CommConstant.dictMapList.get(table);
            if(list.size()>0){
                for(DictTable vo : list){
                    if(val.equals(vo.getDictBh())){
                        dictTable = vo;
                    }
                }
            }
        }catch (Exception e ){
            e.printStackTrace();
        }
        return dictTable;
    }

    /**
     * 获取字典名称
     */
    public static String getDictMc (String table,String val){
        String retDictMc = "";
        try{
            retDictMc = getDictTable(table,val).getDictMc();
        }catch (Exception e ){
            e.printStackTrace();
        }
        return retDictMc;
    }
    /**
     * 获取字典值
     */
    public static String getDictVal (String table,String mc){
        String retDictVal = "";
        try{
            List<DictTable> list = CommConstant.dictMapList.get(table);
            if(list.size()>0){
                for(DictTable vo : list){
                    if(mc.equals(vo.getDictMc())){
                        retDictVal = vo.getDictBh();
                        if(retDictVal == null){
                            retDictVal = "";
                        }
                        return retDictVal;
                    }
                }
            }
        }catch (Exception e ){
            e.printStackTrace();
        }
        return retDictVal;
    }

    /**
     * 获取字典值
     */
    public static String getDictConstantVal(String dictMc){
        String retDictVal = "";
        try{
            retDictVal = CommConstant.dictConstantMap.get(dictMc);
            if(retDictVal == null){
                retDictVal = "";
            }
        }catch (Exception e ){
            e.printStackTrace();
        }
        return retDictVal;
    }

    public static String getDictOptionHtml(String table, String val){
        StringBuffer stringBuffer = new StringBuffer();
        try{
            String str = "";

            List<DictTable> list= CommConstant.dictMapList.get(table);
            for (int i = 0; i < list.size(); i++) {
                DictTable vo =  list.get(i);
                if (vo.getDictBh().equals(val)) {
                    //字典代码等于转进来的值 为选中
                    str = " selected ";
                }else {
                    str = "";
                }
                stringBuffer.append("<option " + str + " value='" + vo.getDictBh() + "'>"  + vo.getDictMc() + "</option>");
            }
        }catch (Exception e ){
            e.printStackTrace();
        }
        return stringBuffer.toString();
    }


}
