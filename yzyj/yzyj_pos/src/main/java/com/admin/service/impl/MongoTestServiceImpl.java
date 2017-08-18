package com.admin.service.impl;

import com.admin.model.MongoTest;
import com.admin.service.AbstractBaseMongoService;
import com.admin.service.MongoTestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("mongoTestService")
public class MongoTestServiceImpl extends AbstractBaseMongoService<MongoTest> implements MongoTestService {

    @Autowired
    private EhCacheCacheManager cacheManager;

    //将缓存保存进serviceCache，讲后面的值作为缓存的key
    //@Cacheable 支持如下几个参数：
    //value：缓存位置名称，不能为空，如果使用EHCache，就是ehcache.xml中声明的cache的name
    //key：缓存的key，默认为空，既表示使用方法的参数类型及参数值作为key，支持SpEL
    //condition：触发条件，只有满足条件的情况才会加入缓存，默认为空，既表示全部都加入缓存，支持SpEL


    /**
     * @CachEvict 的作用 主要针对方法配置，能够根据一定的条件对缓存进行清空
     * @CacheEvict 支持如下几个参数：
        value：缓存位置名称，不能为空，同上
        key：缓存的key，默认为空，同上
        condition：触发条件，只有满足条件的情况才会清除缓存，默认为空，支持SpEL
        allEntries：true表示清除value中的全部缓存，默认为false
     * @param userName
     * @return
     */
   //@Cacheable(value = "serviceCache", key = "'ofu_'+#userName",condition="满足条件才去执行缓存数据不满则执行方法体")
    public Map<String, MongoTest> queryOfrosters(String userName) {
        Map<String, MongoTest> map = new HashMap<String, MongoTest>();
        try {
            Query query = new Query(Criteria.where("userName").is(userName));
            List<MongoTest> list = find(query);
            for (int i = 0; list != null && i < list.size(); i++) {
                map.put(list.get(i).getLoginName(), list.get(i));
            }
            MongoTest mongoTest=new MongoTest();
            mongoTest.setUserName(userName);
            mongoTest.setNick("dasda");
            mongoTest.setHasyn(321);
            mongoTest.setHasyn(1);
            mongoTest.setLoginName("zhuoqh");
            mongoTest.setPicHead("com/pic");
            mongoTest.setNickName("zhuoqh");
            save(mongoTest);
            Cache c=cacheManager.getCache("serviceCache");
            System.out.println(c);
            System.out.println(c.getName());
            System.out.println(c.get("ofu_"+userName));
        } catch (Exception e) {
        }
        return map;
    }

    @CacheEvict(value="serviceCache",key="'ofu_'+#loginName")
    public String addOfroster(String loginName,String userName) {
//		List<Ofroster> list=ofrosterMapper.queryOfrostersByName(loginName);
//		for (int i = 0; list!=null&&i < list.size(); i++) {
//			String s=list.get(i).getUserName();
//			if(userName!=null&&s!=null&&s.indexOf(userName)>=0){
//				flag=true;
//				break;
//			}
//		}
        List<MongoTest> list=queryOfrosters(loginName, new String[]{userName});
        if(list==null||list.size()<=0){
            MongoTest mongoTest=new MongoTest();
            mongoTest.setLoginName(loginName);
            mongoTest.setUserName(userName);
            save(mongoTest);
            Cache c=cacheManager.getCache("serviceCache");
            c.evict("ofu_"+userName);
        }

        return "添加成功";
    }

    @Override
    //@CacheEvict(value="serviceCache",key="'ofu_'+#loginName")
    public String updBaseInfo(String loginName, String nickName) {
        if(nickName==null) return "修改失败";
        Query query=new Query(Criteria.where("loginName").is(loginName));
        MongoTest mongoTest  = findOne(query);
       List<MongoTest> mongoTest1 =  find(query,MongoTest.class);
        List<MongoTest> mongoTestList = find(query);

        if(mongoTest == null){
            return "修改失败";
        }
        Update update=new Update();
        if(!StringUtils.isEmpty(nickName)){
            update.set("nickName",nickName);
        }
        if(!StringUtils.isEmpty(loginName)){
            update.set("loginName",loginName);
        }
        update(query, update);
        return "修改成功";
    }

    @Override
    public String updRemarkName(String loginName, String remarkName, String userName) {
        return null;
    }

    @Override
    public String removeOfroster(MongoTest mongoTest) {
        return null;
    }

    @Override
    public Map<String, MongoTest> findOfRoster(String loginName) {
        return null;
    }

    @Override
    public String addOfroster(MongoTest ofr) {
        return null;
    }

    @Override
    public String removeOfrosters(List<MongoTest> ofroster) {
        return null;
    }

    @Override
    public Map<String, MongoTest> queryOfrostersB(String userName) {
        return null;
    }

    @Override
    public String listHeadPic(String loginName, String loginNames) {
        return null;
    }

    @Override
    public List<MongoTest> queryOfrosters(String logiName, String[] loginNames) {
        return null;
    }
}
