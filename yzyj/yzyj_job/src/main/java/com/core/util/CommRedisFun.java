package com.core.util;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;

import java.util.*;

/**
 * Created by Administrator on 2016/9/22.
 */
public class CommRedisFun {
    /**
     * 设置 KEY 过期时间
     * @param key
     * @param seconds
     * @return
     */
    public static Integer setKeyExpire(String key,Integer seconds){
        Integer state = 0;
        Jedis jedis = null;
        try{
            jedis = JedisPoolUtil.getJedis();
            if( jedis != null){
                jedis.expire(key,seconds);
                state = 1;
            }

        }catch (Exception e ){

        }finally {
            JedisPoolUtil.returnRes(jedis);
        }
        return state;
    }
    /**
     * 设置 KEY 过期时间
     * @param key
     * @param seconds
     * @return
     */
    public static Integer setKeyExpire(String key,String val,Integer seconds){
        Integer state = 0;
        Jedis jedis = null;
        try{
            jedis = JedisPoolUtil.getJedis();
            if( jedis != null){
                jedis.setex(key,seconds,val);
                state = 1;
            }

        }catch (Exception e ){

        }finally {
            JedisPoolUtil.returnRes(jedis);
        }
        return state;
    }
    /**
     * 设置 KEY 过期时间
     * @param key
     * @param seconds
     * @return
     */
    public static Integer setKeyExpire(String key,HashMap map,Integer seconds){
        Integer state = 0;
        Jedis jedis = null;
        try{
            jedis = JedisPoolUtil.getJedis();
            if( jedis != null){
                Pipeline pipeline = jedis.pipelined();
                Iterator iter = map.entrySet().iterator();
                while (iter.hasNext()) {
                    Map.Entry entry = (Map.Entry) iter.next();
                    String field = entry.getKey().toString();
                    String val = entry.getValue().toString();
                    pipeline.hset(key,field,val);
                }
                pipeline.expire(key,seconds);
                pipeline.sync();
                state = 1;
            }

        }catch (Exception e ){

        }finally {
            JedisPoolUtil.returnRes(jedis);
        }
        return state;
    }

    /**
     * 检查连接
     * @return
     */
    public static Integer checkConnect(){
        Integer state = 0;
        Jedis jedis = null;
        try{
            jedis = JedisPoolUtil.getJedis();
            if( jedis != null){
                state = 1;
            }
        }catch (Exception e ){

        }finally {
            JedisPoolUtil.returnRes(jedis);
        }
        return state;
    }
    /**
     * 检查重复请求
     */
    public static synchronized Integer checkRepeat(String key,String field){
        Integer state = 99;
        Jedis jedis = null;
        try{
            jedis = JedisPoolUtil.getJedis();
            if(jedis == null){
                state = 99;
                return state;
            }
            if("1".equals(jedis.hget(key,field))){
                state = 1;//已存在
            }else{
                jedis.hset(key,field,"1");
                state = 2;//不存在
            }
        }catch (Exception e ){
            e.printStackTrace();
        }finally {
            JedisPoolUtil.returnRes(jedis);
        }
        return state;
    }

    /**
     * 删除 H 字段
     */
    public static synchronized Integer delHField(String key,String field){
        Integer state = 1;
        Jedis jedis = null;
        try{
            jedis = JedisPoolUtil.getJedis();
            jedis.hdel(key,field);
        }catch (Exception e ){
            e.printStackTrace();
        }finally {
            JedisPoolUtil.returnRes(jedis);
        }
        return state;
    }
    public static synchronized Integer delInKey(String keys){
        Integer state = 0;
        Jedis jedis = null;
        try{
            jedis = JedisPoolUtil.getJedis();
            Set set = jedis.keys(keys);
            Iterator<String> it=set.iterator() ;
            while(it.hasNext()){
                String key = it.next();
                jedis.del(key);
            }
        }catch (Exception e ){
            e.printStackTrace();
        }finally {
            JedisPoolUtil.returnRes(jedis);
        }
        return state;
    }

    /**
     * 批量设置值
     * @return
     */
    public static synchronized Integer batchHSetKey(List<HashMap> list){
        Integer state = 0;
        Jedis jedis = null;
        try{
            jedis = JedisPoolUtil.getJedis();
            Pipeline pipeline = jedis.pipelined();
            for (HashMap<String,String> map:list){
                pipeline.hset(map.get("key"),map.get("field"),map.get("value"));
            }
            pipeline.sync();
        }catch (Exception e ){
            e.printStackTrace();
        }finally {
            JedisPoolUtil.returnRes(jedis);
        }
        return state;
    }
    /**
     * Redis 库 , 删除对象
     * @return
     */
    public static synchronized Integer delKey(String key){
        Integer state = 0;
        Jedis jedis = null;
        try{
            jedis = JedisPoolUtil.getJedis();
            jedis.del(key);
            state = 1;
        }catch (Exception e ){
            e.printStackTrace();
        }finally {
            JedisPoolUtil.returnRes(jedis);
        }
        return state;
    }
    /**
     * Redis 库 , 获取对象
     * @return
     */
    public static synchronized String getKey(String key){
        String retStr = "";
        Jedis jedis = null;
        try{
            jedis = JedisPoolUtil.getJedis();
            retStr = jedis.get(key);
        }catch (Exception e ){
            e.printStackTrace();
        }finally {
            JedisPoolUtil.returnRes(jedis);
        }
        return retStr;
    }

    public static synchronized String getHKey(String key,String field){
        String retStr = "";
        Jedis jedis = null;
        try{
            jedis = JedisPoolUtil.getJedis();
            retStr = jedis.hget(key,field);
        }catch (Exception e ){
            e.printStackTrace();
        }finally {
            JedisPoolUtil.returnRes(jedis);
        }
        return retStr;
    }
    /**
     * Redis 库 , 获取对象
     * @return
     */
    public static synchronized Map getHGetAll(String key){
        Map map = new HashMap();
        Jedis jedis = null;
        try{
            jedis = JedisPoolUtil.getJedis();
            map = jedis.hgetAll(key);
        }catch (Exception e ){
            e.printStackTrace();
        }finally {
            JedisPoolUtil.returnRes(jedis);
        }
        return map;
    }
    /**
     * Redis 库 , 获取对象
     * @return
     */
    public static synchronized List getHvals(String key){
        List list = new ArrayList();
        Jedis jedis = null;
        try{
            jedis = JedisPoolUtil.getJedis();
            list = jedis.hvals(key);
        }catch (Exception e ){
            e.printStackTrace();
        }finally {
            JedisPoolUtil.returnRes(jedis);
        }
        return list;
    }

    /**
     * 模糊查找 , 获取 key
     * @param keys
     * @return
     */
    public static synchronized List getKeys(String keys){
        List list = new ArrayList();
        Jedis jedis = null;
        try{
            jedis = JedisPoolUtil.getJedis();
            Set set = jedis.keys(keys);
            Iterator<String> it=set.iterator() ;
            while(it.hasNext()){
                String key = it.next();
                list.add(key);
            }
        }catch (Exception e ){
            e.printStackTrace();
        }finally {
            JedisPoolUtil.returnRes(jedis);
        }
        return list;
    }

    /**
     * Redis 库 , 获取对象
     * @return
     */
    public static synchronized Integer setHKey(String key,String field,String value){
        Integer state = 0;
        Jedis jedis = null;
        try{
            jedis = JedisPoolUtil.getJedis();
            jedis.hset(key,field,value);
            state = 1;
        }catch (Exception e ){
            e.printStackTrace();
        }finally {
            JedisPoolUtil.returnRes(jedis);
        }
        return state;
    }


}
