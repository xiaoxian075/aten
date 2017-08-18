package com.aten.service;

import com.aten.model.orm.Cat;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public  interface CommonService<T, PK> {
	
	/**
	 * @author linjunqin
	 * @Description 通用新增
	 * @param
	 * @date 2017-3-27 上午8:33:52
	 */
	public void insert(T t);
	
	/**
	 * @author linjunqin
	 * @Description 通用新增并返回主键
	 * @param
	 * @date 2017-3-27 上午8:34:02
	 */
	public int insertGetPk(T t);
	
	/**
	 * @author linjunqin
	 * @Description 通用更新
	 * @param
	 * @date 2017-3-27 上午8:34:39
	 */
	public void update(T  t);
	
	/**
	 * @author linjunqin
	 * @Description 通过ID删除数据
	 * @param
	 * @date 2017-4-7 下午12:03:04
	 */
	public void deleteOne(String id);
	
	/**
	 * @author linjunqin
	 * @Description 通用删除
	 * @param
	 * @date 2017-3-27 上午8:34:57
	 */
	public void delete(String[] ids);
	
	/**
	 * @author linjunqin
	 * @Description 通用获取条件查询的条数
	 * @param
	 * @date 2017-3-27 上午8:35:11
	 */
	public int getCount(Map<String, Object> paraMap);
	
	/**
	 * @author linjunqin
	 * @Description 通用获取条件查询的数据,返回对象类型列表
	 * @param
	 * @date 2017-3-27 上午8:35:11
	 */
	public List<T> getList(Map<String, Object> paraMap);
	
	
	/**
	 * @author linjunqin
	 * @Description 用获取条件查询的数据,返回HashMap Object类型列表
	 * @param
	 * @date 2017-3-27 上午8:37:12
	 */
	public List<HashMap<String, Object>> getObjMapList(Map<String, String> paraMap);
	
	/**
	 * @author linjunqin
	 * @Description 批量更新
	 * @param
	 * @date 2017-3-27 上午8:36:58
	 */
	public void updateBatch(List<Map<String, String>> mapList);
	
	/**
	 * @author linjunqin
	 * @Description 根据主键获取对象
	 * @param
	 * @date 2017-3-27 上午8:36:44
	 */
	public T get(PK id);
	
	/**
	 * @author linjunqin
	 * @Description 根据条件获取对象,注意只能返回一条数据,使用时需注意
	 * @param
	 * @date 2017-4-7 上午11:56:44
	 */
	public T get(HashMap<String,String> paraMap);

}
