<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
  <head>
    <title>商品点击统计管理</title>
  </head>
  <body>
  <form action="/admin/goodsClickCount/list" method="post" >
  	  <div class="searchDiv">
  	  		<table class="searchTable">
  	  			<tr>
		  	  		<td>统计日期:</td>
		  	  		<td>
		  	  			<input class="text w130" type="text" id="statistics_date_s" name="statistics_date_s" maxlength='20'
						maxdatalength='20' value="${statistics_date_s}" />
		  	  		</td>
		  	  		<td>
		  	  			 <input class="btn ol_colorbtn ol_greenbtn" type="button" value="查询" onclick="searchInfo('/admin/goodsClickCount/list');"/>
			  	  		 <input class="btn ol_colorbtn ol_bredbtn" type="button" value="清空" onclick="clearSearch('/admin/goodsClickCount/list');"/>
		  	  		</td>
	  	  		</tr>
	  	  	</table>
  	  </div>	
  	  <div class="show_line">
  	  		<%@ include file="/WEB-INF/common/pageshowrow.jsp"%>
  	  </div> 
	  <div class="list_div">
	  		<table  id="list_table"  border="0" cellspacing="0" cellpadding="0">
	  			<tr>
	  					<th width="5%">ID</th>
	  					
		  				<th width="10%">统计日期</th>
		  			
		  				<th width="10%">累计商品名称</th>
		  			
		  				<th width="10%">累计点击量</th>
		  			
		  				<th width="10%">当日商品名称</th>
		  			
		  				<th width="10%">当日点击量</th>
		  			
	  			</tr>
	  			<c:if test="${!empty goodsClickCountList}">
	  				<c:forEach items="${goodsClickCountList}" var="item" varStatus="status">
	  					<tr>
		  					<td>${item.gcc_id}</td>
		  					
		  					<td>${item.statistics_date}</td>
		  					
		  					<td>${item.total_goods_name}</td>
		  					
		  					<td>${item.total_click_count}</td>
		  					
		  					<td>${item.today_goods_name}</td>
		  					
		  					<td>${item.today_click_count}</td>
		  					
	  					</tr>
	  				</c:forEach>
	  			</c:if>
	  			<c:if test="${empty goodsClickCountList}"><td colspan="6">暂无数据</td></c:if>
	  		</table>
	  </div>
	  <div class="page_contain">
	  		<%@ include file="/WEB-INF/common/pagelist.jsp"%>
	  </div>
	  <%@ include file="/WEB-INF/common/list_hidden_value.jsp"%>
	  
<script type="text/javascript"/>
	   $("#statistics_date_s").datetimepicker({
			format:'Y-m-d',
			language: 'zh',
			minTime: true,
			maxTime: true,
			timepicker:false
		});
</script>
	  
  </form>		
  </body>
 </html> 

