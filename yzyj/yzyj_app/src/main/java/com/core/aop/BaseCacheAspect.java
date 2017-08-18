package com.core.aop;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;

/**
 * BaseAspect 基础缓存切面
 *
 * @author luocj
 * @since 2015-11-11 19:12
 */
public class BaseCacheAspect {

    protected final Logger log = LoggerFactory.getLogger(getClass());

    private int port = 6379;
    private String host = "192.168.13.28";
    private Jedis jedis = new Jedis(host, port);
    protected String cacheKey;


    public void clear() {
        log.debug("cacheKey:{}, cache clear", cacheKey);
        this.jedis.flushDB();
    }

    public void evict(String key) {
        log.debug("cacheKey:{}, evict key:{}", cacheKey, key);
        this.jedis.del(key);
    }

    public void put(String key,Object object){
        //jedis.set(key.getBytes(), SerializationUtil.serialize(object));
    }

    public Object get(String key){
        byte[] bytes = jedis.get(key.getBytes());
        //return  SerializationUtil.deserialize(bytes);
        return null;
    }


}
