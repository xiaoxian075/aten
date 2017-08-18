package com.aten.function;

import java.lang.reflect.Field;

import org.springframework.data.mongodb.core.query.Criteria;

public class ObjToMapFuc {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		/* Class<?> cls = t.getClass();  
	        Field[] fields = cls.getDeclaredFields();  
	        for(int i=0; i<fields.length; i++){  
	            Field f = fields[i];  
	            f.setAccessible(true);  
	            try {
	            	//移除序列化号不加入搜索
					if(f.getName()!="serialVersionUID" && f.get(t)!=null){
						String key = f.getName();
		            	String value = f.get(t).toString();
						//System.out.println("属性名:" + key + " 属性值:" + value);
						Criteria criteria = Criteria.where(key).is(value);  
			            query.addCriteria(criteria);  
					}
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}  
	        }   */
	}

}
