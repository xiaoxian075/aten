package com.aten.function;

import java.util.List;
import com.aten.model.orm.Commpara;
import com.aten.service.CommparaService;


public class CommparaFuc extends SpringContextFuc{
	
	private static CommparaService commparaService = (CommparaService) getContext().getBean("commparaService");

	
	/**
	 * @author linjunqin
	 * @Description 根据字符串获取系统的参数列表
	 * @param
	 * @date 2017-1-11 下午5:46:07
	 */
	public static List<Commpara> getParaList(String paraStr){
		List<Commpara> commparaList = commparaService.getCommparaListByPara(paraStr);
		return commparaList;
	}
	
	/**
	 * @author linjunqin
	 * @Description 根据参数编码与参数值 获取参数名
	 * @param
	 * @date 2017-5-10 下午7:26:42
	 */
	public static String getParaName(String paraStr,String para_key){
		String para_name="";
		List<Commpara> paraList = getParaList(paraStr);
		for(int i=0;i<paraList.size();i++){
			Commpara commpara  = paraList.get(i);
			if(commpara.getPara_key().equals(para_key)){
				para_name = commpara.getPara_name();
			}
		}
		return para_name;
	}
	
	
	
	public static void main(String[] args) {
		
	}
	
}
