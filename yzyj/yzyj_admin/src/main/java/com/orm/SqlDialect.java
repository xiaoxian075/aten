package com.orm;

import com.alibaba.druid.util.StringUtils;

/**
 * 功能描述：SQL
 * 作者： luocj
 * 时间：2015/11/13.
 */
public class SqlDialect extends Dialect {

    @Override
    public String getLimitString(String sql, int offset, int limit) {
        sql = sql.trim();

        StringBuffer pagingSelect = new StringBuffer(sql.length() + 100);
        pagingSelect.append("SELECT TOP "+limit+" * FROM ( ");
        pagingSelect.append("       SELECT ROW_NUMBER() OVER (ORDER BY id) AS RowNumber,* FROM ");
        pagingSelect.append("        (").append(dealSQL(sql,offset + limit)).append(" ) t ");
        pagingSelect.append(") A ");
        pagingSelect.append(" WHERE RowNumber > " + offset + " and RowNumber <= " + (offset + limit));

        return pagingSelect.toString();
    }
    private String dealSQL(String sql,Integer limit){
        StringBuffer stringBuffer=new StringBuffer();
        try{
            if(!StringUtils.isEmpty(sql)){
                Integer index = 0;
                if((index = sql.toUpperCase().indexOf("SELECT")) != -1){
                    stringBuffer.append(sql.substring(0,index+6));
                    stringBuffer.append(" TOP "+limit).append(" ");
                    stringBuffer.append(sql.substring(index+6,sql.length()));
                }
                index = null;
            }
        }catch (Exception e ){
            e.printStackTrace();
        }
        return stringBuffer.toString();
    }
    @Override
    public String getCountString(String querySelect) {
        return OraclePageHepler.getCountString(querySelect);
    }
}
