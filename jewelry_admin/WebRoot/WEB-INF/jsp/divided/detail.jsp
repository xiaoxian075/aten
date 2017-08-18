<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
  <head>
    <title>结算分成详情</title>
	  <script type="text/javascript">
          $(function () {
              // 时间设置

              $("#push_time_start_s").datetimepicker({
                  format:'Y-m-d',
                  language: 'zh',
                  minTime: true,
                  maxTime: true,
                  timepicker:false,
                  onSelectDate: function () {
                      var starttime=$("#push_time_start_s").val();
                      $("#push_time_end_s").datetimepicker({
                          minDate: starttime,
                          maxDate: false,
                      });
                  },
              });

              $("#push_time_end_s").datetimepicker({
                  format:'Y-m-d',
                  language: 'zh',
                  minTime: true,
                  maxTime: true,
                  timepicker:false,
                  onSelectDate: function () {
                      var endtime=$("#push_time_end_s").val();
                      $("#push_time_start_s").datetimepicker({
                          minDate: false,
                          maxDate: endtime,
                      });
                  },
              });
          });
	  </script>
  </head>
  <body>
  <form action="/admin/divided/detail" method="post" >
	  <div class="list_oper_div">
		  <input class="btn return" type="button" value="返回分成列表"
				 onclick="goInfo('/admin/divided/list');" />
	  </div>
  	  <div class="searchDiv">
  	  		<table class="searchTable">
  	  			<tr>
					<input type="hidden" name="parameter_id" value="${parameter_id}"/>
		  	  		<td>订单时间:</td>
					<td>
					<td class="td_right_two">
					 <input class="text w130" type="text"
							id="push_time_start_s" name="start_date_s" maxlength='20'
							maxdatalength='20' value="${start_date_s}" />
					- <input
							class="text w130" type="text" id="push_time_end_s"
							name="end_date_s" maxlength='20' maxdatalength='20'
							value="${end_date_s}" />
					<td>
						<input class="btn ol_colorbtn ol_greenbtn" type="button" value="查询" onclick="goInfo('/admin/divided/detail');"/>
						<input class="btn ol_colorbtn ol_bredbtn" type="button" value="清空" onclick="clearSearch('/admin/divided/detail');"/>
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
					<th width="10%">订单号</th>
					<th width="10%">订单时间</th>
					<th width="10%">会员账户</th>
					<th width="10%">会员等级</th>
					<th width="10%">订单金额(元)</th>
					<th width="10%">收益金额(元)</th>
	  			</tr>
	  			<c:if test="${!empty dividedList}">
	  				<c:forEach items="${dividedList}" var="item" varStatus="status">
	  					<tr>
							<td>${item.order_number}</td>
							<td>${item.order_time}</td>
							<td>${item.login_name}</td>
							<td>${item.account_level}</td>
							<td>${item.order_amount}</td>
							<td>${item.divided_amount}</td>
	  					</tr>
	  				</c:forEach>
	  			</c:if>
	  			<c:if test="${empty dividedList}"><td colspan="6">暂无数据</td></c:if>
	  		</table>
	  </div>
	  <div class="page_contain">
	  		<%@ include file="/WEB-INF/common/pagelist.jsp"%>
	  </div>
	  <%@ include file="/WEB-INF/common/list_hidden_value.jsp"%>
  </form>		
  </body>
 </html> 

