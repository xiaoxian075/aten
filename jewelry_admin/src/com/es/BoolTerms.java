package com.es;

import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.QueryBuilder;

public class BoolTerms {
	
	private BoolQueryBuilder boolQueryBuilder;  

	/**
	 * @author linjunqin
	 * @Description  构造初始条件
	 * @param
	 * @date 2017年7月9日 下午4:28:39 
	 */
	public BoolTerms(){
		boolQueryBuilder = QueryBuilders.boolQuery(); 
	}
	
	/**
	 * @author linjunqin
	 * @Description  获取拼接的搜索组合
	 * @param
	 * @date 2017年7月9日 下午4:58:00 
	 */
	public BoolQueryBuilder getBoolQuery(){
		return boolQueryBuilder;
	}
	
	
	/**
	 * @author linjunqin
	 * @Description  完全匹配的模式检索,不带分析器
	 * @param
	 * @date 2017年7月9日 下午4:33:30 
	 */
	public BoolTerms getDocEqualFiled(String field,String value){
		boolQueryBuilder.must(QueryBuilders.termQuery(field,value));
		return this;
	}
	
	/**
	 * @author linjunqin
	 * @Description  一个字段匹配多个值   测试有问题,暂不使用
	 * @param
	 * @date 2017年7月9日 下午6:03:44 
	 */
	public BoolTerms getDocTermsFiled(String field,String... value){
		boolQueryBuilder.must(QueryBuilders.termQuery(field,value));
		return this;
	}
	
	
	/**
	 * @author linjunqin
	 * @Description  单个搜索,可带分析器搜索,默认IK搜索
	 * @param
	 * @date 2017年7月9日 下午4:42:41 
	 */
	public BoolTerms getDocMatchFiled(String field,String value){
		boolQueryBuilder.must(QueryBuilders.matchQuery(field,value).analyzer("ik_smart").analyzer("pinyin"));//指定分词器 ik_smart  ik_max_word ik_pinyin_analyzer
		return this;
	}
	
	
	/**
	 * @author linjunqin
	 * @Description  模糊搜索
	 * @param
	 * @date 2017年7月9日 下午5:52:40 
	 */
	public BoolTerms getDocVagueFiled(String field,String value){
		boolQueryBuilder.must(QueryBuilders.wildcardQuery(field,"*"+value+"*"));
		return this;
	}
	
	/**
	 * @author linjunqin
	 * @Description  模糊查询
	 * @param
	 * @date 2017年7月10日 上午8:23:37 
	 */
	@SuppressWarnings("deprecation")
	protected BoolTerms getDocFuzzyQuery(String field,String value) { 
        boolQueryBuilder.must(QueryBuilders.fuzzyQuery(field, value));
        return this;
    } 
	
	
	/**
	 * @author linjunqin
	 * @Description  多字段搜索  "text", "field1", "field2".. 相当于或搜索语句
	 * @param	要注意前面是值   后面是字段组
	 * @date 2017年7月9日 下午5:32:08 
	 */
	public BoolTerms getDocMultiMatchFiled(String value,String... field){
		boolQueryBuilder.must(QueryBuilders.multiMatchQuery(value,field).analyzer("ik_max_word"));//指定分词器 ik_smart  ik_max_word ik_pinyin_analyzer
		return this;
	}
	
	/**
	 * @author linjunqin
	 * @Description  根据ids获取文档
	 * @param
	 * @date 2017年7月9日 下午5:43:43 
	 */
	public BoolTerms getDocIdsQuery(String... ids) {  
		boolQueryBuilder.must(QueryBuilders.idsQuery().addIds(ids));
        return this;  
    }  
	
	/**
	 * @author linjunqin
	 * @Description  查询所有文档
	 * @param
	 * @date 2017年7月9日 下午5:49:40 
	 */
	protected BoolTerms getDocMatchAllQuery() {  
		boolQueryBuilder.must(QueryBuilders.matchAllQuery());
        return this;  
    }  
	
	/**
	 * @author linjunqin
	 * @Description  子文档查询  暂不清楚用法
	 * @param
	 * @date 2017年7月9日 下午6:09:25 
	 */
	public BoolTerms getDocChildQuery() {
        QueryBuilder queryBuilder = QueryBuilders.hasChildQuery("sonDoc", QueryBuilders.termQuery("name", "vini"), null);
        boolQueryBuilder.must(queryBuilder);
        return this;  
    }
	

    /**
	 * @author linjunqin
	 * @Description  范围查询
	 * @param
	 * @date 2017年7月9日 下午6:16:47 
	 */
    public BoolTerms getDocRangeQuery(String startPos,String endPos) {
    	QueryBuilder queryBuilder = QueryBuilders.rangeQuery("user")
                .from(startPos)
                .to(endPos)
                .includeLower(true)     // 包含上界
                .includeUpper(true);      // 包含下届
        boolQueryBuilder.must(queryBuilder);
        return this;  
    }
    
	/**
	 * @author linjunqin
	 * @Description  自定义扩展查询条件
	 * @param
	 * @date 2017年7月9日 下午6:25:47 
	 */
	public BoolTerms getDocCustomQuery(QueryBuilder queryBuilder){
		 boolQueryBuilder.must(queryBuilder);
	     return this;  
	}
	
	/**
	 * @author linjunqin
	 * @Description  嵌套查询, 内嵌文档查询
	 * @param
	 * @date 2017年7月9日 下午6:37:56 
	 */
	public BoolTerms getDocNestedQuery() {
        QueryBuilder queryBuilder = QueryBuilders.nestedQuery("location", 
                QueryBuilders.boolQuery()
                    .must(QueryBuilders.matchQuery("location.lat", 0.962590433140581))
                    .must(QueryBuilders.rangeQuery("location.lon").lt(36.0000).gt(0.000)),null);
        boolQueryBuilder.must(queryBuilder);//.scoreMode("total"); // max, total, avg or none  
        return this;
    }
	
	
	
}
