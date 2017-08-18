package com.admin.service;

import com.mongodb.WriteResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
/**
 * service查询基类
 * @param <T>
 */
public abstract class AbstractBaseMongoService<T> {

	@Autowired
	private MongoTemplate mongoTemplate;
	/**
	 * 
	 * @param objectId
	 * @return
	 */
	protected T getByObjectId(String objectId){
		T t=mongoTemplate.findById(objectId, getEntityClass());
		return t;
	}
	/**
	 * 
	 * @param t
	 */
	protected void save(T t){
		mongoTemplate.save(t);
	}
	
	/**
	 * 
	 * @param query
	 * @return
	 */
	protected T findOne(Query query){
		return mongoTemplate.findOne(query, this.getEntityClass());  
	}
	
	/**
	 * 
	 * @param query
	 * @return
	 */
	protected <E> E findOne(Query query,Class<E> c){
		return mongoTemplate.findOne(query, c);  
	}
	
	/**
	 * 
	 * @param query
	 * @return
	 */
	protected List<T> find(Query query){
		return mongoTemplate.find(query, this.getEntityClass());  
	}
	
	/**
	 * 
	 * @param query
	 * @return
	 */
	protected <E> List<E> find(Query query,Class<E> c){
		return mongoTemplate.find(query,c);  
	}
	/**
	 * 
	 * @param query
	 * @param update
	 */
	protected T update(Query query, Update update) {  
		WriteResult wr=mongoTemplate.updateMulti(query, update, this.getEntityClass());
		return null;
	} 
	
	/**
	 * 
	 * @param query
	 * @param update
	 */
	protected boolean updateN(Query query, Update update,Class c) {  
		WriteResult wr=mongoTemplate.updateMulti(query, update, c);
		return wr.getN()==1;
	}
	/**
	 * 
	 * @param t
	 * @return 
	 */
	protected WriteResult removeOne(T t){
		return mongoTemplate.remove(t);
	}
	
	
	/**
	 * 
	 * @return
	 */
	protected WriteResult removeMany(Query query){
		return mongoTemplate.remove(query,getEntityClass());
	}
	
	/**
	 * 
	 * @return
	 */
	protected WriteResult removeMany(Query query,Class c){
		return mongoTemplate.remove(query,c);
	}
	
	/** 
	  * 获取需要操作的实体类class 
	  *  
	  * @return 
	  */  
	protected Class<T> getEntityClass() {  
	    return getSuperClassGenricType(getClass(), 0);  
	} 
	
	protected long count(Query query) {
		return mongoTemplate.count(query, getEntityClass());
	}
	
	@SuppressWarnings("all")  
	private Class getSuperClassGenricType(Class clazz, int index) {  
	    Type genType = clazz.getGenericSuperclass();// 得到泛型父类  
	    // 如果没有实现ParameterizedType接口，即不支持泛型，直接返回Object.class  
	   if (!(genType instanceof ParameterizedType)) {  
	       return Object.class;  
	   }  
	   // 返回表示此类型实际类型参数的Type对象的数组,数组里放的都是对应类型的Class, 如BuyerServiceBean extends  
	   // DaoSupport<Buyer,Contact>就返回Buyer和Contact类型  
	   Type[] params = ((ParameterizedType) genType).getActualTypeArguments();  
	   if (index >= params.length || index < 0) {  
	       throw new RuntimeException("你输入的索引"+ (index < 0 ? "不能小于0" : "超出了参数的总数"));  
	   }  
	   if (!(params[index] instanceof Class)) {  
	       return Object.class;  
	   }  
	        return (Class) params[index];  
	}

}
