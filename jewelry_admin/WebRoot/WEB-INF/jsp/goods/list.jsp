<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<title>商品列表</title>
<script type="text/javascript">
          $(document).ready(function(){
              //分类级联
              $("#cat_id_div").cascadeSel({
                  url:"/admin/cat/normalList",
                  name:"cat_id",
                  init_id:"${cfg_goods_top}"
              });
          });

	  </script>
</head>
<body>
	<form action="/admin/goods/list" method="post">
		<div class="searchDiv">
			<table class="searchTable">
				<tr>
					<td>商品名称:</td>
					<td><input type="text" name="goods_name_vague_s" value="${goods_name_vague_s}" /></td>
					<td>所属分类:</td>
					<td>
						<div id="cat_id_div" tipmsg="所属分类" setwidth="200" setheight="25"></div>
						<input class="validate" changetip="cat_id_div" type="hidden" id="cat_id" name="parent_cat_id_s" value="${parent_cat_id_s}" />
					</td>
					<%-- <td>状态:</td>
					<td>
						<select id="state_s" name="state_s">
		                  	<option value="100">全部</option>
		                  	<option value="1" <c:if test="${order_type==1}">selected="selected"</c:if>>正常订单</option>  
		                  	<option value="2" <c:if test="${order_type==2}">selected="selected"</c:if>>全额预售订单</option>  
		                  	<option value="3" <c:if test="${order_type==3}">selected="selected"</c:if>>订金支付订单</option>  
		              	</select>
	 	  			</td> --%>
					<td>
						<input class="btn ol_colorbtn ol_greenbtn" type="button" value="搜索" onclick="searchInfo('/admin/goods/list');" /> 
						<input class="btn ol_colorbtn ol_bredbtn" type="button" value="清空" onclick="clearSearch('/admin/goods/list');" />
					</td>
				</tr>
			</table>
		</div>
		<div class="show_line">
			<%@ include file="/WEB-INF/common/pageshowrow.jsp"%>
		</div>
		<div class="list_div">
			<table id="list_table" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<th width="3%"><input class="all" type="checkbox" /></th>
					<th>商品ID</th>
					<th>商品编码</th>
					<th width="20%">商品名称</th>
					<th>所属分类</th>
					<th>价格</th>
					<th>库存</th>
					<th>发布时间</th>
					<th>最后编辑时间</th>
					<th>状态</th>
					<th>操作</th>
				</tr>
				<c:if test="${!empty goodsList}">
					<c:forEach items="${goodsList}" var="item" varStatus="status">
						<tr>
							<td><input class="ids" type="checkbox"
								value="${item.goods_id}" />
							</td>
							<td>${item.goods_id}</td>
							<td>${item.goods_number}</td>
							<td style="text-align:left">
								<img class="img img_wh801 separate" src="${ossImgServerUrl}${item.list_img}" /> <span class="commodityName">${item.goods_name}</span>
							</td>
							<td class="list_td_left">${item.cat_name}</td>
							<td>${item.fixed_price}</td>
							<td>${item.total_stock}</td>
							<c:choose>
								<c:when test="${empty item.in_date}">
									<td>-</td>
								</c:when>
								<c:otherwise>
									<td>${item.in_date}</td>
								</c:otherwise>
							</c:choose>
							<td>${item.add_time}</td>
							<td>
								<c:if test="${item.state==0}"><span class="span_pansy">待发布</span></c:if> 
								<c:if test="${item.state==1}"><span class="span_green">已上架</span></c:if> 
								<c:if test="${item.state==2}"><span class="span_blue">定时上架</span></c:if> 
								<c:if test="${item.state==3}"><span class="span_red">已下架</span></c:if>
							</td>
							<td>
								<input class="btn ol_colorbtn ol_greenbtn" type="button" value="查看" onclick="editInfo('/admin/goods/lookDetail','${item.goods_id}');" /> 
								<input class="btn ol_colorbtn ol_bluebtn" type="button" value="修改" onclick="editInfo('/admin/goods/edit','${item.goods_id}');" /> 
								<c:if test="${item.state==1}">
									<input class="btn ol_colorbtn ol_redbtn" type="button" value="下架" onclick="commonInfo('/admin/goods/limitState','${item.goods_id}','确定下架该商品？');" />
								</c:if> 
								<c:if test="${item.state==0}">
									<input class="btn ol_colorbtn ol_greenbtn" type="button" value="上架" onclick="commonInfo('/admin/goods/enableState','${item.goods_id}','确定上架该商品？');" />
								</c:if> 
								<c:if test="${item.state==3}">
									<input class="btn ol_colorbtn ol_greenbtn" type="button" value="上架" onclick="commonInfo('/admin/goods/enableState','${item.goods_id}','确定上架该商品？');" />
								</c:if> 
								<input class="btn ol_colorbtn ol_redbtn" type="button" value="删除" onclick="delInfo('/admin/goods/delete','${item.goods_id}','确定删除该商品？');" />
							</td>
						</tr>
					</c:forEach>
				</c:if>
				<c:if test="${empty goodsList}">
					<td colspan="11">暂无数据</td>
				</c:if>
			</table>
		</div>
		<div class="batchDiv">
			<span class="batch_span"><input class="all" type="checkbox" />全选</span>
			<input class="btn ol_colorbtn ol_redbtn" type="button" value="批量删除"
				onclick="commonBatchInfo('/admin/goods/batchDelete','确定批量删除商品?');" />
			<input class="btn ol_colorbtn ol_greenbtn" type="button" value="批量上架"
				onclick="commonBatchInfo('/admin/goods/batchEnableState','确定批量上架商品?');" />
			<input class="btn ol_colorbtn ol_bluebtn" type="button" value="批量下架"
				onclick="commonBatchInfo('/admin/goods/batchLimitState','确定批量下架商品?');" />
		</div>
		<div class="page_contain">
			<%@ include file="/WEB-INF/common/pagelist.jsp"%>
		</div>
		<%@ include file="/WEB-INF/common/list_hidden_value.jsp"%>
	</form>
</body>
</html>

