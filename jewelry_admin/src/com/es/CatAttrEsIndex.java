package com.es;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;

import com.aten.function.SpringContextFuc;
import com.aten.model.orm.AttrValue;
import com.aten.model.orm.CatAttr;
import com.aten.service.AttrValueService;
import com.aten.service.CatAttrService;
import com.communal.constants.EsContant;
import com.communal.util.JsonUtil;
import com.communal.util.StringUtil;


public class CatAttrEsIndex extends SpringContextFuc{
	
	private static CatAttrService catAttrService = (CatAttrService) getContext().getBean("catAttrService");
	private static AttrValueService attrValueService = (AttrValueService) getContext().getBean("attrValueService");
	
	public static void main(String[] args){
		//创建索引结构
		createCatAttrMapping(EsContant.CATATTRINDEX,EsContant.CATATTRTYPE);
		//创建索引数据
		createCatAttrIndex(EsContant.CATATTRINDEX,EsContant.CATATTRTYPE);

	}
	
	
	/**
	 * @author linjunqin
	 * @Description  创建分类属性映射数据结构
	 * @param
	 * @date 2017年7月10日 上午10:28:07 
	 */
	public static void createCatAttrMapping(String index, String type) {
	    try {
	        // 使用XContentBuilder创建分类属性Mapping
	        XContentBuilder builder = 
	        		XContentFactory.jsonBuilder()
	                            .startObject()
	                                .field("properties")
	                                    .startObject()
		                                    .field("identId")
		                                        .startObject()
		                                            .field("index", "not_analyzed")
		                                            .field("type", "integer")
		                                        .endObject()
	                                        .field("attrName")
	                                            .startObject()
	                                                .field("index", "not_analyzed")
	                                                .field("type", "string")
	                                            .endObject()
	                                        .field("attrId")
	                                            .startObject()
	                                                .field("index", "not_analyzed")
	                                                .field("type", "string")
	                                            .endObject()
	                                        .field("showType")
	                                            .startObject()
	                                                .field("index", "not_analyzed")
	                                                .field("type", "string")
	                                            .endObject()    
	                                        .field("attrCatId")
	                                            .startObject()
	                                                .field("index", "not_analyzed")
	                                                .field("type", "string")
	                                            .endObject()    
	                                        .field("attrValueList")
	                                            .startObject()
	                                                .field("index", "not_analyzed")
	                                                .field("type", "string")
	                                            .endObject()  
	                                    .endObject()
	                            .endObject();
	        //创建索引结构
	        new EsClient().createIndexMapping(index, type, builder);
	    } catch (ElasticsearchException e) {
	        e.printStackTrace();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}
	
	/**
	 * @author linjunqin
	 * @Description  创建分类属性索引
	 * @param
	 * @date 2017年7月10日 上午11:02:13 
	 */
	public static void createCatAttrIndex(String index,String type){
		try {
			HashMap<String, Object> paraMap = new HashMap<String, Object>();
	    	paraMap.put("is_index", "1");//查询出支持搜索的文档
	    	paraMap.put("is_key", "1");//关键属性搜索
	    	List<CatAttr> catAttrList = catAttrService.getList(null);
	    	for(CatAttr catAttr : catAttrList){
	    		// 根据属性标识获取属性列表
	    		paraMap.clear();
	    		paraMap.put("attr_id", catAttr.getAttr_id());
	    		List<AttrValue> attrValueList =  attrValueService.getList(paraMap);
	    		for(AttrValue attrValue : attrValueList){
	    			attrValue.setAttr_value_id(StringUtil.flushTenLeft(attrValue.getAttr_value_id()));
	    		}
	    		// 使用XContentBuilder创建一个catAttr Source
		        XContentBuilder builder = 
		            XContentFactory.jsonBuilder()
		                            .startObject()
		                            	.field("attrName",catAttr.getAttr_name())
		                                .field("showType", catAttr.getShow_type())
		                                .field("attrId",catAttr.getAttr_id())
		                                .field("attrCatId",catAttr.getCat_id())
		                                .field("identId", catAttr.getIdent_id())
		                                .field("attrValueList", JsonUtil.object2json(attrValueList))
		                            .endObject();
		        //创建文档
		        new EsClient().createEsDoc(index, type, catAttr.getIdent_id(), builder);
	    	}
	    	System.out.println("分类属性索引创建成功！");
	    } catch (ElasticsearchException e) {
	        e.printStackTrace();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}
	
	
}
