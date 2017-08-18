package com.communal.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.*;


/**
 * <p>
 * Map取值方法,其中取得多种值,避免null值转换
 * </p>
 * @author hailan
 *
 */
public class MapGetterTool {
    private static final Logger LOGGER = LoggerFactory.getLogger(MapGetterTool.class);
    private Map<String,Object> map;

    public MapGetterTool(Map<String,Object> map){
        if(map == null){
            map = new HashMap<>();
        }
        this.map = map;
    }
    public Map<String,Object> getMap(){
        return map;
    }
    /**
     * <p>
     * 根据Key返回一个Double型
     * </p>
     * @param key
     * @return Double
     */
    public void setMapVal(String key, Object val){
        if(map != null){
            map.put(key,val);
        }
    }
    /**
     * <p>
     * 根据Key返回一个Double型
     * </p>
     * @param key
     * @return Double
     */
    public Double getDouble(String key){
        if(map.get(key)!=null){
            if(map.get(key) instanceof Double){
                return (Double)map.get(key);
            }else{
                return 0.0;
            }
        }else{
            return 0.00;
        }
    }

    /**
     * <p>
     * 根据Key返回一个String
     * </p>
     * @param key
     * @return String
     */
    public String getString(String key){
        if(map.get(key)!=null){
            if(map.get(key) instanceof String){
                return (String)map.get(key);
            }else{
                return map.get(key).toString();
            }
        }else{
            return "";
        }
    }
    public Long getLong(String key){
        if(map.get(key)!=null){
            if(map.get(key) instanceof Long){
                return (Long)map.get(key);
            }else{
                return Long.parseLong(map.get(key)+"");
            }
        }else{
            return null;
        }
    }
    public Long getLongDefault(String key){
        try{
            if(map.get(key)!=null){
                if(map.get(key) instanceof Long){
                    return (Long)map.get(key);
                }else{
                    return Long.parseLong(map.get(key)+"");
                }
            }
        }catch (Exception e){
            LOGGER.error("e",e);
        }
        return 0l;
    }
    /**
     * <p>
     * 根据Key返回一个Date
     * </p>
     * @param key
     * @return Date
     */
    public Date getDate(String key){
        if(map.get(key)!=null){
            if(map.get(key) instanceof Date){
                return (Date)map.get(key);
            }else{
                return null;
            }
        }else{
            return null;
        }
    }

    /**
     * <p>
     * 根据Key返回一个Date
     * </p>
     * @param key
     * @return Date
     */
    public List getList(String key){
        if(map.get(key)!=null){
            if(map.get(key) instanceof List){
                return (List)map.get(key);
            }else{
                return new ArrayList();
            }
        }else{
            return new ArrayList();
        }
    }

    /**
     * <p>
     * 根据Key返回一个Integer
     * </p>
     * @param key
     * @return Integer
     */
    public Integer getInteger(String key){
        if(map.get(key)!=null){
            if(map.get(key) instanceof Integer){
                return (Integer)map.get(key);
            }else{
                return FunUtil.stringToInteger(map.get(key)+"");
            }
        }else{
            return 0;
        }
    }
    public Integer getInteger2(String key){
        if(map.get(key)!=null){
            if(map.get(key) instanceof Integer){
                return (Integer)map.get(key);
            }else{
                return FunUtil.stringToInteger(map.get(key)+"");
            }
        }else{
            return null;
        }
    }
    /**
     * <p>
     * 根据一个Key返回一个Map<String,String>
     * </p>
     * @param key
     * @return Map<String,String>
     */
    @SuppressWarnings("unchecked")
    public Map<String,Object> getMap(String key){
        if(map.get(key)!=null){
            if(map.get(key) instanceof Map){
                return (Map<String,Object>)map.get(key);
            }else{
                return null;
            }
        }else{
            return null;
        }
    }

    /**
     * <p>
     * 根据key返回BigDecimal
     * 如果为null,则返回 new BigDecimal(0)
     * </p>
     * @param key
     * @return BigDecimal
     */
    public BigDecimal getBigDecimal(String key){
        if(map.containsKey(key)){
            if(map.get(key) instanceof BigDecimal){
                return (BigDecimal)map.get(key);
            }else{
                return new BigDecimal(map.get(key)+"");
            }
        }else{
            return new BigDecimal(0);
        }
    }

}
