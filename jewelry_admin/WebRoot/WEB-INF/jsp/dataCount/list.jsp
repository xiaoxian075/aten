<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
  <head>
    <title>平台数据统计管理</title>
  </head>
  <body>
  <form action="/admin/dataCount/list" method="post" >
  	  <div class="searchDiv">
  	  		<table class="searchTable">
  	  			<tr>
		  	  		<td>统计日期:</td>
		  	  		<td>
		  	  			<input class="text w130" type="text" id="statistics_date_s" name="statistics_date_s" maxlength='20'
						maxdatalength='20' value="${statistics_date_s}" />
		  	  		</td>
		  	  		<td>
		  	  			 <input class="btn ol_colorbtn ol_greenbtn" type="button" value="查询" onclick="searchInfo('/admin/dataCount/list');"/>
			  	  		 <input class="btn ol_colorbtn ol_bredbtn" type="button" value="清空" onclick="clearSearch('/admin/dataCount/list');"/>
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
	  				
		  				<th width="6%">统计日期</th>
		  			
		  				<th width="6%">订单成交量</th>
		  			
		  				<th width="10%">成交总金额</th>
		  			
		  				<th width="10%">成交人数</th>
		  			
		  				<th width="10%">平台日活跃数</th>
		  			
		  				<th width="10%">平台客单价</th>
		  			
		  				<th width="10%">平台累计会员数</th>
		  			
		  				<th width="10%">平台新增会员数</th>
		  			
		  				<th width="10%">平台vip会员数</th>
		  			
		  				<th width="10%">平台新增vip会员数</th>
	  			</tr>
	  			<c:if test="${!empty dataCountList}">
	  				<c:forEach items="${dataCountList}" var="item" varStatus="status">
	  					<tr>
		  					<td>${item.dc_id}</td>
		  					
		  					<td>${item.statistics_date}</td>
		  					
		  					<td>${item.order_deal_count}</td>
		  					
		  					<td>${item.deal_total_amount}</td>
		  					
		  					<td>${item.deal_man_num}</td>
		  					
		  					
		  					<td>${item.plat_activity_num}</td>
		  					
		  					
		  					<td>${item.avg_price}</td>
		  					
		  					<td>${item.plat_all_member_count}</td>
		  					
		  					
		  					<td>${item.plat_inc_member_count}</td>
		  					
		  					
		  					<td>${item.plat_vip_member_count}</td>
		  					
		  					<td>${item.plat_vip_inc_member_count}</td>
		  					
	  					</tr>
	  				</c:forEach>
	  			</c:if>
	  			<c:if test="${empty dataCountList}"><td colspan="11">暂无数据</td></c:if>
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

