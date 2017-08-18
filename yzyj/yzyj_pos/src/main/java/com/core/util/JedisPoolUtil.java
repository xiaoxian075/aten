package com.core.util;

/**
 * Created by Administrator on 2016/9/20.
 */

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class JedisPoolUtil {
    private static String ip ;
    private static Integer port ;
    private static String pwd ;
    private static JedisPool pool;

    /**
     * 建立连接池 真实环境，一般把配置参数缺抽取出来。
     *
     */
    private static void createJedisPool() {

        // 建立连接池配置参数
        JedisPoolConfig config = new JedisPoolConfig();

        // 设置最大连接数
        config.setMaxTotal(100);

        // 设置最大阻塞时间，记住是毫秒数milliseconds
        config.setMaxWaitMillis(1000);

        // 设置空间连接
        config.setMaxIdle(5);

        //检查参数
        CheckParameter();
        // 创建连接池
        pool = new JedisPool(config, ip, port,10000,pwd);

    }
    private static void CheckParameter(){
        try{
            if(ip == null){
                ip = CommDictList.getDictVal("static_redis","one_ip");
            }
            if(port == null){
                port = FunUtil.stringToInteger(CommDictList.getDictVal("static_redis","one_port"));
            }
            if(pwd == null){
                pwd =  CommDictList.getDictVal("static_redis","one_pwd");
            }

        }catch (Exception e ){
            e.printStackTrace();
        }
    }
    /**
     * 在多线程环境同步初始化
     */
    private static synchronized void poolInit() {
        if (pool == null) {
            createJedisPool();
        }
    }

    /**
     * 重新连接
     */
    public static synchronized void recCnnection(){
        try{
            Integer state  = 0;
            if(!CommDictList.getDictVal("static_redis","one_ip").equals(ip)){
                state = 1;
                ip =  CommDictList.getDictVal("static_redis","one_ip");
            }
            if(!CommDictList.getDictVal("static_redis","one_port").equals(port+"")){
                state = 1;
                port = FunUtil.stringToInteger(CommDictList.getDictVal("static_redis","one_port"));
            }
            if(!CommDictList.getDictVal("static_redis","one_pwd").equals(pwd)){
                state = 1;
                pwd =  CommDictList.getDictVal("static_redis","one_pwd");
            }
            if(state == 1){
                if(pool != null){
                    pool.close();
                    pool =null;
                }
                createJedisPool();
            }
        }catch (Exception e ){

        }
    }
    /**
     * 获取一个jedis 对象
     *
     * @return
     */
    public static Jedis getJedis() {
        if (pool == null) {
            poolInit();
        }
        Jedis jedis = null;
        try{
            jedis = pool.getResource();
        }catch (Exception e ){

        }
        return jedis;
    }

    /**
     * 归还一个连接
     *
     * @param jedis
     */
    public static void returnRes(Jedis jedis) {
        pool.returnResource(jedis);
    }

}