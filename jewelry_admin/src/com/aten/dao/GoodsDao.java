package com.aten.dao;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.aten.model.orm.Goods;
import com.communal.util.Query;

public interface GoodsDao extends CommonDao<Goods, String>{

	int updateOneIsdel(Map<String,Object> param);
	
	int updateOneState(Map<String, Object> param);
	
	int updateGoodsPrice(Map<String, Object> param);
	
	/**
	 * @author linjunqin
	 * @Description  通过传入goods_id获取对应的自定义属性值串 
	 * @param
	 * @date 2017年7月10日 下午5:46:52 
	 */
	public String getAvIdstr(HashMap<String, Object> paraMap);

    List<Goods> queryList(Query query);

    List<Goods> checkGoodsClass(String cat_id);
    
    int updateGoodsInfo(Goods goods);

	int getcountByShipTemplate(BigInteger ship_template);
}
