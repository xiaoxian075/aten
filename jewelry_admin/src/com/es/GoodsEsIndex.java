package com.es;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.admin.indices.mapping.put.PutMappingRequest;
import org.elasticsearch.client.Requests;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import com.aten.function.SpringContextFuc;
import com.aten.model.orm.Cat;
import com.aten.model.orm.Goods;
import com.aten.model.orm.GoodsActivity;
import com.aten.model.orm.GoodsActivityMap;
import com.aten.service.CatService;
import com.aten.service.GoodsActivityMapService;
import com.aten.service.GoodsActivityService;
import com.aten.service.GoodsService;
import com.communal.constants.EsContant;
import com.communal.util.DateUtil;
import com.communal.util.StringUtil;


public class GoodsEsIndex extends SpringContextFuc {

	private static GoodsService goodsService = (GoodsService) getContext().getBean("goodsService");
	private static CatService catService = (CatService) getContext().getBean("catService");
	private static GoodsActivityMapService goodsActivityMapService = (GoodsActivityMapService) getContext().getBean("goodsActivityMapService");
	private static GoodsActivityService goodsActivityService = (GoodsActivityService) getContext().getBean("goodsActivityService");
	
	public static void main(String[] args){
		//创建索引结构
		createGoodsMapping(EsContant.GOODSINDEX,EsContant.GOODS);
		//创建索引数据
		createGoodsIndex(EsContant.GOODSINDEX,EsContant.GOODS);
	}
	
	
	/**
	 * @author linjunqin
	 * @Description  新增商品索引结构名称
	 * @param  index 索引名
	 * @param  type 表名
	 * @date 2017年7月9日 下午12:56:48 
	 */
	public static void createGoodsMapping(String index, String type) {
	    try {
	        // 使用XContentBuilder创建Mapping
	        XContentBuilder builder = 
	            XContentFactory.jsonBuilder()
	                            .startObject()
	                                .field("properties")
	                                    .startObject()
		                                    .field("goodsName")
		                                		.startObject()
				                                	.field("type", "string")
				                                	.field("store", "yes")
				                                	.field("index","analyzed")
				                                	.field("analyzer", "ik_max_word")//ik_pinyin_analyzer  ik_smart  pinyin ik_max_word
				                                	//.field("nalyzer","pinyin")
				                                .endObject()
	                                        .field("fixedPrice")
	                                            .startObject()
	                                                .field("index", "not_analyzed")
	                                                .field("type", "text")
	                                            .endObject()
	                                         .field("rangeFixedPrice")
	                                            .startObject()
	                                                .field("index", "not_analyzed")
	                                                .field("type", "text")
	                                                .field("fielddata",true)
	                                            .endObject()
	                                        .field("listImg")
	                                            .startObject()
	                                                .field("index", "not_analyzed")
	                                                .field("type", "text")
	                                            .endObject()
	                                        .field("goodsId")
	                                            .startObject()
	                                                .field("index", "not_analyzed")
	                                                .field("type", "text")
	                                            .endObject()  
	                                        .field("catId")
	                                            .startObject()
	                                                .field("index", "not_analyzed")
	                                                .field("type", "text")
	                                            .endObject()  
	                                        .field("catIdStr")
	                                            .startObject()
	                                                .field("index", "not_analyzed")
	                                                .field("type", "text")
	                                            .endObject()  
	                                        .field("avIdStr")
	                                            .startObject()
	                                                .field("index", "not_analyzed")
	                                                .field("type", "text")
	                                            .endObject()  
	                                         .field("totalStock")
	                                            .startObject()
	                                                .field("index", "not_analyzed")
	                                                .field("type", "text")
	                                            .endObject()  
	                                         .field("totalSales")
	                                            .startObject()
	                                                .field("index", "not_analyzed")
	                                                .field("type", "text")
	                                                .field("fielddata",true)
	                                            .endObject()  
	                                         .field("inDate")
	                                            .startObject()
	                                                .field("index", "not_analyzed")
	                                                .field("type", "text")
	                                                .field("fielddata",true)
	                                            .endObject()  
	                                          .field("activityState")
	                                            .startObject()
	                                                .field("index", "not_analyzed")
	                                                .field("type", "string")
	                                            .endObject()  
	                                          .field("goodsState")
	                                            .startObject()
	                                                .field("index", "not_analyzed")
	                                                .field("type", "string")
	                                            .endObject()
	                                    .endObject()
	                            .endObject();
	        System.out.println(builder.string());           
	        //创建索引结构
	        new EsClient().createIndexMapping(index, type, builder);
	        System.out.println("创建商品索引结构成功！");
	    } catch (ElasticsearchException e) {
	        e.printStackTrace();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}
	
	/**
	 * @author linjunqin
	 * @Description 创建商品索引
	 * @param
	 * @date 2017年7月9日 下午1:05:42 
	 */
	public static void createGoodsIndex(String index, String type) {

	    try {
	    	HashMap<String, Object> paraMap = new HashMap<String, Object>();
	    	paraMap.put("is_del", "1");//未删除数据
	    	paraMap.put("state","1");//状态正常显示的数据
	    	paraMap.put("info_state","1");//信息正常的数据
	    	paraMap.put("audit_state","1");//审核通过的数据
	    	List<Goods> goodsList =  goodsService.getList(paraMap);
	    	//创建索引
	    	for(Goods goods : goodsList){
	    		if(goods.getGoods_id().equals("573")){
	    			System.out.println(33131);
	    		}
	    		//根据分类标识获取分类ID级联串
	    		Cat cat = catService.get(goods.getCat_id());
	    		String goods_id = goods.getGoods_id();
	    		paraMap.put("goods_id", goods_id);
	    		String avIdStr= goodsService.getAvIdstr(paraMap);
	    		//获取商品属性列表
	    		// 使用XContentBuilder创建一个doc source
		        XContentBuilder builder = 
		            XContentFactory.jsonBuilder()
		                            .startObject()
		                            	.field("goodsName",goods.getGoods_name())
		                                .field("fixedPrice", String.valueOf(goods.getFixed_price()))
		                                .field("rangeFixedPrice", StringUtil.flushPrice(goods.getFixed_price()))
		                                .field("listImg", goods.getList_img())
		                                .field("goodsId",goods.getGoods_id())
		                                .field("catIdStr", cat.getLevel_cat())
		                                .field("catId", cat.getCat_id())
		                                .field("avIdStr", avIdStr)
		                                .field("totalStock",goods.getTotal_stock())
		                                .field("totalSales", goods.getTotal_sales()==null?0:goods.getTotal_sales())
		                                .field("inDate",DateUtil.getTime(goods.getIn_date()))
		                                .field("activityState",checkGoodsActivity(goods))
		                                .field("goodsState",checkGoodsState(goods))
		                            .endObject();
		        //创建文档
		        new EsClient().createEsDoc(index, type, goods.getGoods_id(), builder);
	    	}
	    	System.out.println("商品索引创建成功!");
	    } catch (ElasticsearchException e) {
	        e.printStackTrace();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}
	
	
	/**
	 * @author linjunqin
	 * @Description  验证商品是否存在活动，并返回活动编码
	 * @param
	 * @date 2017年7月19日 下午10:09:12 
	 */
	public static String checkGoodsActivity(Goods goods){
		String activityState="0";
		//验证该商品是否存在活动
		HashMap<String, Object> activityMap = new HashMap<String, Object>();
		//预售模式
		if(goods.getSale_mode().equals("1")){
			if(goods.getPresale_model().equals("1")){
				activityState="11";//全额预售
			}else if(goods.getPresale_model().equals("2")){
				activityState="12";//定金预售
			}
		}else{
			activityMap.put("goods_id", goods.getGoods_id());
			List<GoodsActivityMap> goodsActivityMapList = goodsActivityMapService.getList(activityMap);
			//0:无活动 1: 限时折扣 比例%  2.黄金特惠 减金额（小于商品价格）3：专减手工费   11:全额预售  12:订金预售
			if(goodsActivityMapList!=null && goodsActivityMapList.size()>0){
				GoodsActivityMap gam = goodsActivityMapList.get(0);
				GoodsActivity goodsActivity = goodsActivityService.get(gam.getActivity_id());
				if(goodsActivity!=null && goodsActivity.getActivity_state().equals("1")){//当前活动在运行中
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					//判断时间
					try {
						if(DateUtil.compareDateTime(sdf.parse(goodsActivity.getStart_time()),new Date())==1 
								&& DateUtil.compareDateTime(sdf.parse(goodsActivity.getEnd_time()),new Date())==1){
							activityState = goodsActivity.getActivity_type();
						}
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
		return activityState;
	}
	
	/**
	 * @author linjunqin
	 * @Description  商品下架状态
	 * @param
	 * @date 2017年7月19日 下午10:45:07 
	 */
	public static String checkGoodsState(Goods goods){
		String goodsState="1";
		if(goods!=null){
			if(goods.getTotal_stock().equals("0")){
				goodsState="4";//已售罄
			}else{
				goodsState = goods.getState();
			}
		}
		return goodsState;
	}
	
}
