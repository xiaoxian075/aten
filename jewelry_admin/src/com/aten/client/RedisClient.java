package com.aten.client;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

import com.aten.function.SpringContextFuc;
import com.aten.model.bean.ExpireTimeBean;
import com.communal.util.JsonUtil;

public class RedisClient extends SpringContextFuc {

	//主要操作字符串
	private static StringRedisTemplate stringRedisTemplate = (StringRedisTemplate) getContext()
			.getBean("stringRedisTemplate");
	//主要操作Map,Object对象
	@SuppressWarnings("rawtypes")
	private static RedisTemplate redisTemplate = (RedisTemplate) getContext()
			.getBean("redisTemplate");
	
	public static void main(String[] args) {
			System.out.println(getStr("/admin/ad/insert"));
			//updateStr("/admin/attr/list", "首页 > 商品管理 > 属性管理");
	}

	/**
	 * @author linjunqin
	 * @Description: 存储字符串,并有时间限制
	 * @param tags
	 * @date 2017-2-1 下午9:11:37
	 */
	public static void addStr(String key, String value,ExpireTimeBean etb) {
		stringRedisTemplate.opsForValue().set(key, value.toString());
		expire(key,etb);
	}
	
	/**
	 * @author linjunqin
	 * @Description: 存储字符串
	 * @param tags
	 * @date 2017-2-1 下午9:11:37
	 */
	public static void addStr(String key, String value) {
		addStr(key, value,null);
	}
	
	/**
	 * @author linjunqin
	 * @Description redis数据更新的接口
	 * @param increValue 递增值
	 * @param key标识
	 * @date 2017-2-22 上午10:17:08
	 */
	public static void updateStr(String key,String increValue){
		String oldValue = getStr(key);
		if(oldValue==null){
			addStr(key,increValue);
		}else{
			String newValue = String.valueOf(Integer.parseInt(oldValue)+Integer.parseInt(increValue));
			addStr(key,newValue);
		}
	}
	
	/**
	 * @author linjunqin
	 * @Description: 根据key取得缓字符串
	 * @param tags
	 * @date 2017-2-1 下午11:20:44
	 */
	public static String getStr(String key){  
        return stringRedisTemplate.boundValueOps(key).get();  
    }  
	
	/**
	 * @author linjunqin
	 * @Description: 存储Map数据
	 * @param tags
	 * @date 2017-2-1 下午9:14:07
	 */
	@SuppressWarnings("unchecked")
	public static  void addMap(String key,HashMap<String,String> hMap){
		redisTemplate.opsForHash().putAll(key, hMap);  
	}
	
	/**
	 * @author linjunqin
	 * @Description: 存储对象转json格式数据
	 * @param tags
	 * @date 2017-2-1 下午10:21:40
	 */
	public static void addObjJson(String key, Object obj){
		String jsonStr = JsonUtil.object2json(obj);
		addStr(key,jsonStr);
	}
	
	/**
	 * @author linjunqin
	 * @Description: 存储HashMap转json格式数据
	 * @param tags
	 * @date 2017-2-1 下午10:23:46
	 */
	public static void addMapJson(String key, HashMap<String,Object> hMap){
		String jsonStr = JsonUtil.map2json(hMap);
		addStr(key,jsonStr);
	}
	
	
	/**
	 * @author linjunqin
	 * @param <T>
	 * @Description: 存储List对象转json格式数据
	 * @param tags
	 * @date 2017-2-1 下午10:28:41
	 */
	public static <T> void addListJson(String key,List<T> objList){
		String jsonStr = JsonUtil.list2json(objList);
		addStr(key,jsonStr);
	}
	
	/**
	 * @author linjunqin
	 * @Description: 读取key
	 * @param tags
	 * @date 2017-2-1 下午9:45:12
	 */
	public static HashMap<String,String> getMap(String key){  
        BoundHashOperations<String, String,String> boundHashOperations = redisTemplate.boundHashOps(key);   
        return (HashMap<String,String>) boundHashOperations.entries();  
    }  
	
	/**
	 * @author linjunqin
	 * @Description: 添加set集合
	 * @param tags
	 * @date 2017-2-1 下午10:07:14
	 */
	public static void addSet(String key, String... values) { 
		for(String value:values){
			redisTemplate.boundSetOps(key).add(value);
		}
    }  
  
   /**
	* @author linjunqin
	* @Description: 删除set集合中的对象 
	* @param tags
	* @date 2017-2-1 下午10:08:20
	*/
    public static void delSet(String key, String... values) {  
    	for(String value:values){
    		redisTemplate.boundSetOps(key).remove(value); 
		}
    }  
	
    /**
	 * @author linjunqin
	 * @Description: 删除Map中的某个字段
	 * @param tags
	 * @date 2017-2-1 下午10:34:11
	 */
    public static void delMapField(String key, String... fields){  
        BoundHashOperations<String, String, ?> boundHashOperations = redisTemplate.boundHashOps(key);
        for(String field:fields){
        	boundHashOperations.delete(field);  
        }
    }  
	
    /**
	 * @author linjunqin
	 * @Description: 根据key精确匹配删除删除缓存
	 * @param tags
	 * @date 2017-2-1 下午10:58:50
	 */
    @SuppressWarnings("unchecked")  
    public static void delByKey(String... keys){  
    	for(String key:keys){
    		redisTemplate.delete(key);
		}
    }  
      
   /**
	 * @author linjunqin
	 * @Description: 设置缓存的失效时间 
	 * @param tags
	 * @date 2017-2-1 下午11:23:16
	 */
    public static void expire(String key, ExpireTimeBean etb) {  
        if(etb!=null && etb.getTime() > 0){  
            redisTemplate.expire(key, etb.getTime(), TimeUnit.SECONDS);  
        }  
    }  
    
    public static void expire(String key, int expireTime) {  
        if(expireTime> 0){  
            redisTemplate.expire(key, expireTime, TimeUnit.SECONDS); 
        }  
    }  
}
