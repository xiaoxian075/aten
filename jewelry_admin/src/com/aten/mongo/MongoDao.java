package com.aten.mongo;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import com.aten.model.bean.MongoBean;

public interface MongoDao<T, PK> {
	
	/**
	 * @author linjunqin
	 * @Description 批量插入数据,若_id已存在则不在插入
	 * @param
	 * @date 2017-2-5 上午11:26:27
	 */
	public void insertBacthList(List<T> list);

	/**
	 * @author linjunqin
	 * @Description 批量插入数据,效率慢
	 * @param
	 * @date 2017-2-5 上午11:27:34
	 */
	public void insertList(List<T> list);

	/**
	 * @author linjunqin
	 * @Description 插入数据
	 * @param
	 * @date 2017-2-5 下午12:04:21
	 */
	public void insert(T t);

	/**
	 * @author linjunqin
	 * @Description 通过主键删除单条数据,主键必须有值，否则不成功！
	 * @param
	 * @date 2017-2-6 下午4:18:28
	 */
	public void removeOne(T t);

	/**
	 * @author linjunqin
	 * @Description 清空数据
	 * @param
	 * @date 2017-2-6 下午4:18:39
	 */
	public void removeAll(T t);

	/**
	 * @author linjunqin
	 * @Description 通过对象的方式删除数据
	 * @param
	 * @date 2017-2-6 下午4:39:51
	 */
	public void removeByTerm(T t);

	/**
	 * @author linjunqin
	 * @Description 通过HashMap方式删除数据
	 * @param
	 * @date 2017-2-6 下午4:38:58
	 */
	public void removeByTerm(HashMap<String, String> mongoMap);

	/**
	 * @author linjunqin
	 * @Description 通过List搜索 删除数据
	 * @param
	 * @date 2017-2-6 下午4:41:16
	 */
	public void removeByTerm(List<MongoBean> mongoList);

	/**
	 * @author linjunqin
	 * @Description 按条件删除数据
	 * @param
	 * @date 2017-2-6 下午4:34:20
	 */
	public void removeByConditionMoreTerm(T t,
			HashMap<String, String> mongoMap, List<MongoBean> mongoList);

	/**
	 * @author linjunqin
	 * @Description 根据主键更新数据
	 * @param
	 * @date 2017-2-6 下午5:35:54
	 */
	public void update(T t, PK id);

	/**
	 * @author linjunqin
	 * @Description 根据条件修改文档,可修改多条
	 * @param	T:修改内容   HashMap<String, String> 传参
	 * @date 2017-2-6 下午4:19:00
	 */
	public void updateByTrem(T t,HashMap<String, String> mongoMap);

	/**
	 * @author linjunqin
	 * @Description 通过主键获取一条文档记录
	 * @param
	 * @date 2017-2-6 下午4:13:47
	 */
	public T findOneById(T t, PK id);

	/**
	 * @author linjunqin
	 * @Description 通过条件获取一条文档数据，多条情况下取第一条
	 * @param
	 * @date 2017-2-6 下午4:55:55
	 */
	public T findOneById(T t, HashMap<String, String> mongoMap,
			List<MongoBean> mongoList);

	/**
	 * @author linjunqin
	 * @Description 通过集名称获取集下的所有数据
	 * @param T  实体对象
	 * @date 2017-2-6 下午4:47:07
	 */
	public List<T> findAll(T t);
	
	/**
	 * @author linjunqin
	 * @Description 根据搜索条件获取文档列表
	 * @param t
	 *            :实体对象 mongoMap:map传值
	 * @date 2017-2-6 下午4:07:49
	 */
	public List<T> find(T t, HashMap<String, String> mongoMap);

	/**
	 * @author linjunqin
	 * @Description 根据搜索条件获取文档列表
	 * @param t
	 *            :实体对象
	 * @date 2017-2-6 下午4:49:37
	 */
	public List<T> find(T t);

	/**
	 * @author linjunqin
	 * @Description 根据搜索条件获取文档列表
	 * @param t
	 *            :实体对象 mongoList:传值 主要用于不规则条件搜索
	 * @date 2017-2-6 下午4:49:37
	 */
	public List<T> find(T t, List<MongoBean> mongoList);

	/**
	 * @author linjunqin
	 * @Description 根据搜索条件获取文档列表
	 * @param t
	 *            :实体对象 mongoMap:指定字段查询 mongoList:传值 主要用于不规则条件搜索
	 * @date 2017-2-6 下午4:49:37
	 */
	public List<T> find(T t, HashMap<String, String> mongoMap,
			List<MongoBean> mongoList);
	
}
