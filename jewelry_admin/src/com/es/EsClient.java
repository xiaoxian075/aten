package com.es;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexResponse;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsRequest;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsResponse;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.IndicesAdminClient;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.metrics.min.InternalMin;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.SortOrder;

import com.aten.model.orm.Goods;
import com.communal.util.JsonUtil;

import oracle.net.aso.l;

import org.elasticsearch.action.admin.indices.mapping.put.PutMappingRequest;
import org.elasticsearch.client.Requests;
import org.elasticsearch.common.xcontent.XContentBuilder;

public class EsClient {

	// transport客户端
	private TransportClient client;
	

	public EsClient(){
		this.client = EsInit.getClient();
	}
	
	/**
	 * @author linjunqin
	 * @Description 获取客户端
	 * @param
	 * @date 2017年7月10日 上午9:13:11
	 */
	public TransportClient getClient() {
		return this.client ;
	}

	/**
	 * @author linjunqin
	 * @Description 判断集群中index是否存在
	 * @param
	 * @return 存在（true）、不存在（false）
	 * @date 2017年7月10日 上午9:15:18
	 */
	public boolean indexExists(String index) {
		IndicesExistsRequest request = new IndicesExistsRequest(index);
		IndicesExistsResponse response = client.admin().indices().exists(request).actionGet();
		if (response.isExists()) {
			return true;
		}
		return false;
	}

	/**
	 * @author linjunqin
	 * @Description 创建索引结构
	 * @param
	 * @date 2017年7月10日 上午10:40:47
	 */
	public void createIndexMapping(String index, String type, XContentBuilder builder) {
		CreateIndex(index);
		// 创建索引结构
		PutMappingRequest mappingRequest = Requests.putMappingRequest(index).source(builder).type(type);
		new EsClient().getClient().admin().indices().putMapping(mappingRequest).actionGet();
		System.out.println("创建索引结构成功！");
	}

	/**
	 * @author linjunqin
	 * @Description 创建索引
	 * @param
	 * @date 2017年7月10日 上午9:26:25
	 */
	public void CreateIndex(String index) {
		try {
			IndicesAdminClient adminClient = client.admin().indices();
			if (indexExists(index)) {
				return;
			}
			adminClient.prepareCreate(index)
					// .setSettings(Settings.builder().put("index.number_of_shards",
					// index.getNumber_of_shards()).put("index.number_of_replicas",
					// index.getNumber_of_replicas()))
					// .addMapping(index.getType(), index.getFieldJson())
					.get();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @author linjunqin
	 * @param <T>
	 * @Description 调用es API 创建索引文档
	 * @param <T>
	 * @date 2017年7月7日 下午2:47:41
	 */
	public <T> void createEsDoc(String index, String type, String id, T t) {
		CreateIndex(index);
		try {
			IndexResponse indexResponse = this.client.prepareIndex().setIndex(index).setType(type).setId(id) // 如果没有设置id，则ES会自动生成一个id
					.setSource(JsonUtil.bean2json(t)).get();
			System.out.println("索引:" + index + "===" + "类型:" + type + "===" + "文档编号:" + id + "===" + "状态:"
					+ indexResponse.status());
		} catch (ElasticsearchException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @author linjunqin
	 * @Description TODO
	 * @param
	 * @date 2017年7月10日 上午8:44:55
	 */
	public <T> void createEsDoc(String index, String type, String id, XContentBuilder builder) {
		//CreateIndex(index);
		try {
			IndexResponse indexResponse = new EsClient().getClient().prepareIndex().setIndex(index).setType(type)
					.setId(id) // 如果没有设置id，则ES会自动生成一个id
					.setSource(builder.string()).get();
			System.out.println("索引:" + index + "===" + "类型:" + type + "===" + "文档编号:" + id + "===" + "状态:"
					+ indexResponse.status());
		} catch (ElasticsearchException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @author linjunqin
	 * @param <T>
	 * @Description 修改文档
	 * @param
	 * @date 2017年7月7日 下午3:44:03
	 */
	public <T> void updateEsDoc(String index, String type, String id, T t) {
		try {
			UpdateResponse updateResponse = this.client.prepareUpdate().setIndex(index).setType(type).setId(id)
					.setDoc(JsonUtil.bean2json(t)).get();
			System.out.println(updateResponse.status());
		} catch (ElasticsearchException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @author linjunqin
	 * @Description 删除的索引名
	 * @param
	 * @date 2017年7月7日 下午3:35:33
	 */
	public boolean deleteEsIndex(String index) {
		try {
			DeleteIndexResponse deleteIndexResponse = this.client.admin().indices().prepareDelete(index).get();
			return deleteIndexResponse.isAcknowledged();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * @author linjunqin
	 * @Description 删除一条数据
	 * @param
	 * @date 2017年7月7日 下午3:35:45
	 */
	public boolean deleteEsDoc(String index, String type, String id) {
		try {
			DeleteResponse deleteResponse = this.client.prepareDelete().setIndex(index).setType(type).setId(id).get();
			if (deleteResponse.status().equals("OK")) {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * @author linjunqin
	 * @Description 根据ID返回对象数据
	 * @param
	 * @date 2017年7月7日 下午3:45:43
	 */
	public String get(String index, String type, String id) {
		GetResponse getResponse = this.client.prepareGet() // 准备进行get操作,此时还有真正地执行get操作.(与直接get的区别)
				.setIndex(index).setType(type).setId(id).get();
		System.out.println(getResponse.getSourceAsString());
		return getResponse.getSourceAsString();
	}

	/**
	 * @author linjunqin
	 * @Description 使用min聚合查询某个字段上最小的值。
	 * @param
	 * @date 2017年7月7日 下午4:38:44
	 */
	public void min(String index, String type, String fieldName) {
		SearchResponse response = this.client.prepareSearch(index)
				.addAggregation(AggregationBuilders.min("min").field(fieldName)).get();
		InternalMin min = response.getAggregations().get("min");
		System.out.println(min.getValue());
	}

	/**
	 * @author linjunqin
	 * @Description es 查询过滤所有的字段
	 * @param
	 * @date 2017年7月7日 下午1:19:53
	 */
	public void queryAll(String index, String type) {
		// QueryBuilder queryBuilder =
		// QueryBuilders.termQuery("area_name","莆田");//普通搜索
		// QueryBuilder queryBuilder =
		// QueryBuilders.multiMatchQuery("area_name", "莆","田");
		/*
		 * QueryBuilder queryBuilder =
		 * QueryBuilders.rangeQuery("area_level").gt("1")//大于 1 .lt("1") //小于 2
		 * .includeLower(true) //包括下界 .includeUpper(true); //包括上界;
		 */ // QueryBuilder queryBuilder
			// =QueryBuilders.commonTermsQuery("area_name", "莆田");//
			// QueryBuilder queryBuilder =
			// QueryBuilders.boolQuery().must(queryBuilder).must(queryBuilder);//
		QueryBuilder queryBuilder = QueryBuilders.prefixQuery("area_name", "莆");// 匹配前缀
		// QueryBuilder queryBuilder =
		// QueryBuilders.wildcardQuery("area_name","莆田*");//支持* 任意字符串
		// QueryBuilder queryBuilder =
		// QueryBuilders.wildcardQuery("area_name","ctr?");//？任意一个字符
		// QueryBuilders.existsQuery("address");// exist query 查询字段不为null的文档
		// 查询字段不为null的数据
		// QueryBuilders.fuzzyQuery("area_name",
		// "tel").fuzziness(Fuzziness.ONE);//分词模糊匹配fuzziness 的含义是检索的term
		// 前后增加或减少n个单词的匹配查询，
		SearchResponse response = client.prepareSearch(index).setTypes(type).setSearchType(SearchType.DEFAULT)
				.setQuery(queryBuilder)
				// .setFrom(0)
				.setSize(5)// 分页
				.addSort("area_level", SortOrder.DESC)// 排序 降序
				.setExplain(true) // explain为true表示根据数据相关度排序，和关键字匹配最高的排在前面
				.execute().actionGet();
		// 获取命中数
		// System.out.println(response.getHits().totalHits());
		// 获取响应字符串
		// System.out.println(response.toString());
		// 遍历查询结果输出相关度分值和文档内容
		SearchHits searchHits = response.getHits();
		int i = 0;
		for (SearchHit searchHit : searchHits) {
			// System.out.println(searchHit.getScore());
			System.out.println(i + "===" + searchHit.getSourceAsString());
			i++;
		}
	}

	// 初始方法
	public static void main(String[] args) {

	/*	new EsClient().queryAll("yszb", "goods");
		;*/

		List<String> list = new ArrayList<>();
		list.add("111");
		list.add("2");
		list.add("3");
		list.add("4");
		list.add("5");
		list.add("6");
		 /* List<Goods> goodsList = goodsService.getList(null);*/
		  for(String goods : list){ 
			  new EsClient().createEsDoc("yszb","goods","111",goods);
		  //new EsClient().deleteEsDoc("yzsb", "goods", "100"); //
			  new EsClient().deleteEsIndex("yzsb");
		  
		  }
		
		// json转对象
		/*
		 * String goodsJson = new EsClient().get("yzsb", "goods", "3");
		 * JSONObject jsonObject = JSONObject.fromObject(goodsJson); GoodsPublic
		 * goods2 = (GoodsPublic)
		 * JSONObject.toBean(jsonObject,GoodsPublic.class);
		 * System.out.println(goods2+"==="+goods2.getGoods_id());
		 */

		// 找出最小值
		/*
		 * new EsClient().min("yzsb", "goods", "goods_id"); IndicesAdminClient
		 * indicesAdminClient = ElasticFactory.getClient().admin().indices();
		 * AnalyzeRequestBuilder request = new
		 * AnalyzeRequestBuilder(indicesAdminClient,"cloud_repair","中华人民共和国国歌");
		 * // request.setAnalyzer("ik"); request.setTokenizer("ik"); //
		 * Analyzer（分析器）、Tokenizer（分词器） List listAnalysis =
		 * request.execute().actionGet().getTokens();
		 * System.out.println(listAnalysis);
		 */
		// listAnalysis中的结果就是分词的结果
		// IndexResponse indexResponse = new
		// EsClient().client.prepareIndex("index").
		// http://blog.csdn.net/u012285326/article/details/62036593 dsl语法
		// http://blog.csdn.net/fenglailea/article/details/55506775/
		// elasitcsearch 5.0
		/*
		 * new EsClient().client.admin().indices()
		 * .prepareCreate("index").setSettings("{\"index\" : " +
		 * "{\"analysis\" : " + "{\"analyzer\" : " + "{\"ik_analyzer\" : " +
		 * "{\"tokenizer\" : \"ik_max_word\", " +
		 * "\"filter\" : [\"my_pinyin\",\"word_delimiter\"]}}," +
		 * "\"filter\" : {\"my_pinyin\" : " +
		 * "{\"type\" : \"pinyin\", \"first_letter\" : \"prefix\",\"padding_char\" : \" \" }}}}}"
		 * ).get();
		 */
		// System.out.println("索引创建成功");

		/*
		 * String content = "我是中国人，我爱中国。"; AnalyzeResponse response = new
		 * EsClient().client.admin().indices() .prepareAnalyze(content)//内容
		 * .setAnalyzer("ik_smart")//指定分词器 ik_smart ik_max_word
		 * .execute().actionGet();//执行 List<AnalyzeToken> tokens =
		 * response.getTokens(); String result =
		 * JSONArray.fromObject(tokens).toString(); System.out.println(result);
		 */

	}

	public static void queryAllT(String index, String type) {
		// QueryBuilder queryBuilder =
		// QueryBuilders.termQuery("area_name","莆田");//普通搜索
		// QueryBuilder queryBuilder = QueryBuilders.multiMatchQuery("莆田",
		// "area_name","area_id");//模糊搜索 ，Object text, String... fieldNames
		/*
		 * QueryBuilder queryBuilder =
		 * QueryBuilders.rangeQuery("area_level").gt("1")//大于 1 .lt("1") //小于 2
		 * .includeLower(true) //包括下界 .includeUpper(true); //包括上界;
		 */ // QueryBuilder queryBuilder
			// =QueryBuilders.commonTermsQuery("area_name", "莆田");//
			// QueryBuilder queryBuilder =
			// QueryBuilders.boolQuery().must(queryBuilder).must(queryBuilder);//
			// QueryBuilder queryBuilder =
			// QueryBuilders.prefixQuery("area_name","莆");//匹配前缀
			// QueryBuilder queryBuilder =
			// QueryBuilders.wildcardQuery("area_name","莆田*");//支持* 任意字符串
			// QueryBuilder queryBuilder =
			// QueryBuilders.wildcardQuery("area_name","ctr?");//？任意一个字符
			// QueryBuilders.existsQuery("address");// exist query 查询字段不为null的文档
			// 查询字段不为null的数据
			// QueryBuilders.fuzzyQuery("area_name",
			// "tel").fuzziness(Fuzziness.ONE);//分词模糊匹配fuzziness 的含义是检索的term
			// 前后增加或减少n个单词的匹配查询，
			// QueryBuilders.fuzzyQuery("name", "葫芦3582"); 模糊查询
			// QueryBuilder queryBuilder = QueryBuilders.boolQuery();//完全搜索
			// QueryBuilder queryBuilder = QueryBuilders.wildcardQuery("catId",
			// "aba12321312");//不分词支持完整搜索
			// QueryBuilder queryBuilder1 =
			// QueryBuilders.matchQuery("goodsName", "男鞋子");

		// QueryBuilder boolQueryBuilder =
		// QueryBuilders.boolQuery().filter(queryBuilder).filter(queryBuilder1);
		// 搜索过滤
		// boolQueryBuilder.must();//and 搜索
		// boolQueryBuilder.should();//or 搜索
		// boolQueryBuilder.must(queryBuilder).must(queryBuilder1).should();

		BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
		boolQueryBuilder = new BoolTerms()
				// .getDocEqualFiled("goodsName", "男士短袖1")
				// .getDocMatchFiled("goodsName", "nan")
				// .getDocMultiMatchFiled("潮男","note","catId")
				// .idsQuery("1")
				// .matchAllQuery()
				// .getDocVagueFiled("note","精品")
				// .getDocVagueFiled("note", "黄金")
				// .getDocTermsFiled("note", "珍珠")//周大神速 珍珠 男子鞋跑步鞋
				.getDocFuzzyQuery("note", "精品").getBoolQuery();

		// 设置高亮字段
		HighlightBuilder hBuilder = new HighlightBuilder();
		hBuilder.preTags("<h2>");
		hBuilder.postTags("</h2>");
		hBuilder.field("note"); // 设置高亮显示的字段

		SearchResponse response = new EsClient().getClient().prepareSearch(index).setTypes(type)
				.setSearchType(SearchType.DEFAULT).setQuery(boolQueryBuilder).highlighter(hBuilder)
				// .setFrom(0)
				.setSize(20)// 分页
				// .addSort("catId", SortOrder.DESC)//排序 降序
				.setExplain(true) // explain为true表示根据数据相关度排序，和关键字匹配最高的排在前面
				.execute().actionGet();
		// 获取命中数
		// System.out.println(response.getHits().totalHits());
		// 获取响应字符串
		// System.out.println(response.toString());
		// 遍历查询结果输出相关度分值和文档内容
		SearchHits searchHits = response.getHits();
		System.out.println("返回条数：= " + searchHits.getTotalHits());
		// 命中的记录数
		long totalHits = response.getHits().totalHits();
		int i = 0;
		for (SearchHit searchHit : searchHits) {
			System.out.println(totalHits + "===" + searchHit.getScore());
			// String goodsName = (String)
			// searchHit.getSource().get("goodsName");
			// System.out.println(goodsName);
			System.out.println(i + "===" + searchHit.getSourceAsString());
			i++;

			System.out.println("String方式打印文档搜索内容:");
			System.out.println(searchHit.getSourceAsString());
			System.out.println("Map方式打印高亮内容");
			System.out.println(searchHit.getHighlightFields().get("note"));
			System.out.println("遍历高亮集合，打印高亮片段:");
			Text[] text = searchHit.getHighlightFields().get("note").getFragments();
			for (Text str : text) {
				System.out.println(str.string());
			}

		}
	}
}
