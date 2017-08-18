package com.aten.function;

import java.util.HashMap;
import java.util.List;
import com.aten.model.orm.Sysconfig;
import com.aten.service.SysconfigService;

public class SysconfigFuc extends SpringContextFuc{
	
	private static SysconfigService sysconfigService = (SysconfigService) getContext().getBean("sysconfigService");
	
	public static void main(String[] args){
		List<Sysconfig> list = sysconfigService.getList(null);
		System.out.println(getSysValue("cfg-sms-username"));
	}
	
	/**
	 * @author linjunqin
	 * @Description 根据参数名称获取参数值
	 * @param
	 * @date 2017-2-10 上午9:35:19
	 */
	public static String getSysValue(String key){
		HashMap<String, Object> paraMap = new HashMap<String, Object>();
		paraMap.put("var_name", key);
		List<Sysconfig> list = sysconfigService.getList(paraMap);
		if(list!=null && list.size()>0){
			return list.get(0).getVar_value();
		}
		return null;
	}
	
	
}
