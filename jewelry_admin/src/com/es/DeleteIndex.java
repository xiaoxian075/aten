package com.es;

import com.communal.constants.EsContant;

public class DeleteIndex {

	public static void main(String[] args){
		System.out.println(new EsClient().deleteEsIndex("index"));
	}
	
	
	/**
	 * @author linjunqin
	 * @Description  删除索引
	 * @param
	 * @date 2017年7月14日 下午4:16:51 
	 */
	public static void deleteIndex(){
		new EsClient().deleteEsIndex(EsContant.GOODSINDEX);
		new EsClient().deleteEsIndex(EsContant.CATATTRINDEX);
	}
	
}
