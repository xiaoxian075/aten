<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
  <head>
    <title>意见反馈管理</title>
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
  <form action="/admin/feedback/list" method="post" >
  	  <div class="searchDiv">
  	  		<table class="searchTable">
  	  			<tr>
					<input type="hidden" id="parameter_id" name="parameter_id" value="${parameter_id}"/>
					<td>反馈时间:</td>
					<td>
						<input type="text" id="start_date_s" name="start_date_s" value="${start_date_s}"/> -
						<input type="text" id="end_date_s" name="end_date_s" value="${end_date_s}"/>
					</td>
		  	  		<td>
		  	  			 <input class="btn ol_colorbtn ol_greenbtn" type="button" value="查询" onclick="searchInfo('/admin/feedback/list');"/>
			  	  		 <input class="btn ol_colorbtn ol_bredbtn" type="button" value="清空" onclick="clearSearch('/admin/feedback/list');"/>
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

		  				<th width="15%">会员帐号</th>
		  			
		  				<th width="15%">昵称</th>
		  			
		  				<th width="15%">反馈意见</th>
		  			
		  				<th width="15%">反馈时间</th>
	  			</tr>
	  			<c:if test="${!empty feedbackList}">
	  				<c:forEach items="${feedbackList}" var="item" varStatus="status">
	  					<tr>
							<td>${item.account_id}</td>
							<td>${item.nick_name}</td>
							<td><div style="width: 500px;word-wrap: break-word;margin: 0 auto;text-align:left;">${item.fb_content}</div></td>
							<td>${item.fb_time}</td>
	  					</tr>
	  				</c:forEach>
	  			</c:if>
	  			<c:if test="${empty feedbackList}"><td colspan="4">暂无数据</td></c:if>
	  		</table>
	  </div>
	  <div class="page_contain">
	  		<%@ include file="/WEB-INF/common/pagelist.jsp"%>
	  </div>
	  <%@ include file="/WEB-INF/common/list_hidden_value.jsp"%>
  </form>		
  </body>
 </html> 

