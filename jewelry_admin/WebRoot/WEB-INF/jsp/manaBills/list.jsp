<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
  <head>
    <title>账单列表管理</title>
	  <script type="text/javascript">
          $(function () {
              // 时间设置
              
              $("#start_date_s").datetimepicker({
					format:'Y-m-d',
					language: 'zh',
					minTime: true,
					maxTime: true,
					timepicker:false,
					onSelectDate: function () {
						var starttime=$("#start_date_s").val();
						$("#end_date_s").datetimepicker({
							minDate: starttime,
							maxDate: false,
						});
					},
				});
				
				$("#end_date_s").datetimepicker({
					format:'Y-m-d',
					language: 'zh',
					minTime: true,
					maxTime: true,
					timepicker:false,
					onSelectDate: function () {
						var endtime=$("#end_date_s").val();
						$("#start_date_s").datetimepicker({
							minDate: false,
							maxDate: endtime,
						});
					},
				});
          });
	  </script>
  </head>
  <body>
  <form action="/admin/manabills/list" method="post" >
  	  <div class="searchDiv">
  	  		<table class="searchTable">
  	  			<tr>
					<input type="hidden" id="parameter_id" name="parameter_id" value="${parameter_id}"/>
					<td>账单日期:</td>
					<td>
						<input type="text" id="start_date_s" name="start_date_s" value="${start_date_s}"/> -
						<input type="text" id="end_date_s" name="end_date_s" value="${end_date_s}"/>
					</td>
		  	  		<td>
		  	  			 <input class="btn ol_colorbtn ol_greenbtn" type="button" value="查询" onclick="searchInfo('/admin/manabills/list');"/>
			  	  		 <input class="btn ol_colorbtn ol_bredbtn" type="button" value="清空" onclick="clearSearch('/admin/manabills/list');"/>
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

		  				<th width="10%">账单生成日期</th>
		  			
		  				<th width="10%">交易笔数</th>
		  			
		  				<th width="10%">总交易金额（元）</th>
		  			
		  				<th width="10%">账单金额（元）</th>
		  			
		  				<th width="10%">应分成金额（元）</th>
		  				
		  			    <th width="5%">操作</th>
	  			</tr>
	  			<c:if test="${!empty manaBillsList}">
	  				<c:forEach items="${manaBillsList}" var="item" varStatus="status">
	  					<tr>
							<td>${item.settle_date}</td>
							<td>${item.trade_num}</td>
							<td>${item.trade_amount}</td>
							<td>${item.bill_amount}</td>
							<td>${item.divided_amount}</td>

		  					<td>
		  						<input class="btn ol_colorbtn ol_bluebtn" type="button" value="查看" onclick="goInfo('/admin/billflow/list','${item.settle_date}');"/>
		  					</td>
	  					</tr>
	  				</c:forEach>
	  			</c:if>
	  			<c:if test="${empty manaBillsList}"><td colspan="6">暂无数据</td></c:if>
	  		</table>
	  </div>
	  <div class="page_contain">
	  		<%@ include file="/WEB-INF/common/pagelist.jsp"%>
	  </div>
	  <%@ include file="/WEB-INF/common/list_hidden_value.jsp"%>
  </form>		
  </body>
 </html> 

