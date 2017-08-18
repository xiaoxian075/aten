package com.aten.service;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;

import com.aten.model.bean.CustomAttrListBean;
import com.aten.model.bean.CustomSkuAttrListBean;
import com.aten.model.bean.CustomSkuListBean;
import com.aten.model.orm.AdvancelSale;
import com.aten.model.orm.FullSales;
import com.aten.model.orm.Goods;
import com.communal.node.ReqMsg;
import com.communal.util.Query;

public interface GoodsService  extends CommonService<Goods, String>{
	
	public ReqMsg insertGoods(Goods goods,
			AdvancelSale advancelSale,
			FullSales fullSales,
			CustomAttrListBean customAttrList,
			CustomSkuListBean customSkuList,
			CustomSkuAttrListBean customSkuAttrListBean);
	
	
	public ReqMsg updateGoods(
			Goods goods, 
			AdvancelSale advancelSale, 
			FullSales fullSales,
			CustomAttrListBean customAttrList, 
			CustomSkuListBean customSkuList,
			CustomSkuAttrListBean customSkuAttrListBean);
	
	public boolean updateGoodsPublicIsdel(String goods_id, int is_del);
	public boolean batchUpdateGoodsPublicIsdel(List<BigInteger> arr_goodsid, int is_del);

	public boolean updateGoodsPublicState(BigInteger goods_id, int state);
	public boolean batchUpdateGoodsPublicState(List<BigInteger> arr_goodsid, int state);

	/**
	 * @author linjunqin
	 * @Description  通过传入goods_id获取对应的自定义属性值串 
	 * @param
	 * @date 2017年7月10日 下午5:46:52 
	 */
	public String getAvIdstr(HashMap<String, Object> paraMap);

    List<Goods> queryList(Query query);

    boolean checkGoodsClass(String cat_id);
    
}
