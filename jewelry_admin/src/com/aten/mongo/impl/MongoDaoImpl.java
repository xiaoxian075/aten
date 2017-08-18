package com.aten.mongo.impl;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;
import com.aten.model.bean.MongoBean;
import com.aten.mongo.MongoDao;

@Repository
public class MongoDaoImpl<T, PK> implements MongoDao<T, PK> {

	private static Logger logger = Logger.getLogger(MongoDaoImpl.class);

	@Autowired
	private MongoTemplate mongoTemplate;

	/**
	 * @author linjunqin
	 * @Description 批量插入数据,若_id已存在则不在插入
	 * @param
	 * @date 2017-2-5 上午11:26:27
	 */
	public void insertBacthList(List<T> list) {
		mongoTemplate.insertAll(list);
	}

	/**
	 * @author linjunqin
	 * @Description 批量插入数据,效率慢
	 * @param
	 * @date 2017-2-5 上午11:27:34
	 */
	public void insertList(List<T> list) {
		for (T t : list) {
			insert(t);
		}
	}

	/**
	 * @author linjunqin
	 * @Description 插入数据
	 * @param
	 * @date 2017-2-5 下午12:04:21
	 */
	public void insert(T t) {
		mongoTemplate.save(t);
	}

	/**
	 * @author linjunqin
	 * @Description 通过主键删除单条数据,主键必须有值，否则不成功！
	 * @param
	 * @date 2017-2-6 下午4:18:28
	 */
	public void removeOne(T t) {
		mongoTemplate.remove(t);
	}

	/**
	 * @author linjunqin
	 * @Description 清空数据
	 * @param
	 * @date 2017-2-6 下午4:18:39
	 */
	public void removeAll(T t) {
		mongoTemplate.dropCollection(t.getClass());
	}

	/**
	 * @author linjunqin
	 * @Description 通过对象的方式删除数据
	 * @param
	 * @date 2017-2-6 下午4:39:51
	 */
	public void removeByTerm(T t) {
		removeByConditionMoreTerm(t, null, null);
	}

	/**
	 * @author linjunqin
	 * @Description 通过HashMap方式删除数据
	 * @param
	 * @date 2017-2-6 下午4:38:58
	 */
	public void removeByTerm(HashMap<String, String> mongoMap) {
		removeByConditionMoreTerm(null, mongoMap, null);
	}

	/**
	 * @author linjunqin
	 * @Description 通过List搜索 删除数据
	 * @param
	 * @date 2017-2-6 下午4:41:16
	 */
	public void removeByTerm(List<MongoBean> mongoList) {
		removeByConditionMoreTerm(null, null, mongoList);
	}

	/**
	 * @author linjunqin
	 * @Description 按条件删除数据
	 * @param
	 * @date 2017-2-6 下午4:34:20
	 */
	public void removeByConditionMoreTerm(T t,
			HashMap<String, String> mongoMap, List<MongoBean> mongoList) {
		Query query = getQuery(t, mongoMap, mongoList);
		mongoTemplate.remove(query, t.getClass());
	}

	/**
	 * @author linjunqin
	 * @Description 根据主键更新数据
	 * @param 主键:_id
	 * @date 2017-2-6 下午5:35:54
	 */
	public void update(T t, PK id) {
		Criteria criteria = Criteria.where("id").is(id);
		Query query = new Query(criteria);
		// 更新数据处理
		Update update = setUpdateVal(t);
		mongoTemplate.updateFirst(query, update, t.getClass());
	}

	/**
	 * @author linjunqin
	 * @Description 根据条件修改文档,可修改多条
	 * @param	T:修改内容   HashMap<String, String> 传参
	 * @date 2017-2-6 下午4:19:00
	 */
	@SuppressWarnings("unchecked")
	public void updateByTrem(T t ,HashMap<String, String> mongoMap) {
		// 查询结果
		Query query = getQueryByMap(new Query(),mongoMap);
		// 更新数据处理
		Update update = setUpdateVal(t);
		// 执行更新
		mongoTemplate.findAndModify(query, update, t.getClass());
	}

	/**
	 * @author linjunqin
	 * @Description 设置更新的值
	 * @param
	 * @date 2017-2-7 上午9:01:49
	 */
	private Update setUpdateVal(T t) {
		Update update = new Update();
		Class<?> cls = t.getClass();
		Field[] fields = cls.getDeclaredFields();
		for (int i = 0; i < fields.length; i++) {
			Field f = fields[i];
			f.setAccessible(true);
			try {
				// 移除序列化号不加入更新
				if (f.getName() != "serialVersionUID" && f.get(t) != null) {
					String key = f.getName();
					String value = f.get(t).toString();
					update.set(key, value);
				}
			} catch (IllegalArgumentException e) {
				logger.info("参数不合法！");
			} catch (IllegalAccessException e) {
				logger.info("反射异常！");
			}
		}
		return update;
	}

	/**
	 * @author linjunqin
	 * @Description 通过主键获取一条文档记录
	 * @param
	 * @date 2017-2-6 下午4:13:47
	 */
	@SuppressWarnings("unchecked")
	public T findOneById(T t, PK id) {
		Query query = new Query();
		Criteria criteria = Criteria.where("_id").is(id);
		query.addCriteria(criteria);
		return (T) mongoTemplate.findOne(query, t.getClass());
	}

	/**
	 * @author linjunqin
	 * @Description 通过条件获取一条文档数据，多条情况下取第一条
	 * @param
	 * @date 2017-2-6 下午4:55:55
	 */
	public T findOneById(T t, HashMap<String, String> mongoMap,
			List<MongoBean> mongoList) {
		Query query = new Query();
		List<T> mList = find(t, mongoMap, mongoList);
		if (mList.isEmpty())
			return null;
		return mList.get(0);
	}

	/**
	 * @author linjunqin
	 * @Description 通过集名称获取集下的所有数据
	 * @param T
	 *            实体对象
	 * @date 2017-2-6 下午4:47:07
	 */
	public List<T> findAll(T t) {
		return (List<T>) mongoTemplate.findAll(t.getClass());
	}

	/**
	 * @author linjunqin
	 * @Description 根据搜索条件获取文档列表
	 * @param t
	 *            :实体对象 mongoMap:map传值
	 * @date 2017-2-6 下午4:07:49
	 */
	@SuppressWarnings("unchecked")
	public List<T> find(T t, HashMap<String, String> mongoMap) {
		Query query = getQuery(t, mongoMap, null);
		return ((List<T>) mongoTemplate.find(query, t.getClass()));
	}

	/**
	 * @author linjunqin
	 * @Description 根据搜索条件获取文档列表
	 * @param t
	 *            :实体对象
	 * @date 2017-2-6 下午4:49:37
	 */
	@SuppressWarnings("unchecked")
	public List<T> find(T t) {
		Query query = getQuery(t, null, null);
		return ((List<T>) mongoTemplate.find(query, t.getClass()));
	}

	/**
	 * @author linjunqin
	 * @Description 根据搜索条件获取文档列表
	 * @param t
	 *            :实体对象 mongoList:传值 主要用于不规则条件搜索
	 * @date 2017-2-6 下午4:49:37
	 */
	@SuppressWarnings("unchecked")
	public List<T> find(T t, List<MongoBean> mongoList) {
		Query query = getQuery(t, null, mongoList);
		return ((List<T>) mongoTemplate.find(query, t.getClass()));
	}

	/**
	 * @author linjunqin
	 * @Description 根据搜索条件获取文档列表
	 * @param t
	 *            :实体对象 mongoMap:指定字段查询 mongoList:传值 主要用于不规则条件搜索
	 * @date 2017-2-6 下午4:49:37
	 */
	@SuppressWarnings("unchecked")
	public List<T> find(T t, HashMap<String, String> mongoMap,
			List<MongoBean> mongoList) {
		Query query = getQuery(t, null, mongoList);
		return ((List<T>) mongoTemplate.find(query, t.getClass()));
	}

	/**
	 * @author linjunqin
	 * @Description 获取搜索条件的方法
	 * @param
	 * @date 2017-2-6 下午4:05:24
	 */
	private Query getQuery(T t, HashMap<String, String> mongoMap,
			List<MongoBean> mongoList) {
		Query query = new Query();
		// 实体对象条件
		if (t != null) {
			query = getQueryByBean(query, t);
		}
		// map条件
		if (mongoMap != null) {
			query = getQueryByMap(query, mongoMap);
		}
		// list条件
		if (mongoList != null) {
			query = getQueryByList(query, mongoList);
		}
		return query;
	}

	/**
	 * @author linjunqin
	 * @Description 通过Bean获取搜索条件
	 * @param
	 * @date 2017-2-6 下午3:58:23
	 */
	private Query getQueryByBean(Query query, T t) {
		Class<?> cls = t.getClass();
		Field[] fields = cls.getDeclaredFields();
		for (int i = 0; i < fields.length; i++) {
			Field f = fields[i];
			f.setAccessible(true);
			try {
				// 移除序列化号不加入搜索
				if (f.getName() != "serialVersionUID" && f.get(t) != null) {
					String key = f.getName();
					String value = f.get(t).toString();
					// System.out.println("属性名:" + key + " 属性值:" + value);
					Criteria criteria = Criteria.where(key).is(value);
					query.addCriteria(criteria);
				}
			} catch (IllegalArgumentException e) {
				logger.info("参数不合法！");
			} catch (IllegalAccessException e) {
				logger.info("反射异常！");
			}
		}
		return query;
	}

	/**
	 * @author linjunqin
	 * @Description 通过Map获取搜索条件
	 * @param
	 * @date 2017-2-6 下午3:56:02
	 */
	private Query getQueryByMap(Query query, HashMap<String, String> mongoMap) {
		if (mongoMap.get("start") != null) {
			query.skip(Integer.parseInt(mongoMap.get("start")));
			mongoMap.remove("start");
		}
		if (mongoMap.get("limit") != null) {
			query.limit(Integer.parseInt(mongoMap.get("limit")));
			mongoMap.remove("limit");
		}
		// 迭代Map循环,获取精确搜索的条件
		Iterator<Map.Entry<String, String>> entries = mongoMap.entrySet()
				.iterator();
		while (entries.hasNext()) {
			// 获取实体
			Map.Entry<String, String> entry = entries.next();
			// System.out.println("Key = " + entry.getKey() + ", Value = " +
			// entry.getValue());
			// 精确搜索
			Criteria criteria = Criteria.where(entry.getKey()).is(
					entry.getValue());
			query.addCriteria(criteria);
		}
		return query;
	}

	/**
	 * @author linjunqin
	 * @Description 获取 mongoList的中搜索数据
	 * @param
	 * @date 2017-2-6 下午3:52:25
	 */
	private Query getQueryByList(Query query, List<MongoBean> mongoList) {
		for (MongoBean mb : mongoList) {
			String type = mb.getType();
			String name = mb.getName();
			String value = mb.getValue();
			// 1:模糊搜索
			if (type != null && type.equals("1")) {
				Criteria criteria = Criteria.where(name).regex("^" + value);
				query.addCriteria(criteria);
			}
		}
		return query;
	}

}
