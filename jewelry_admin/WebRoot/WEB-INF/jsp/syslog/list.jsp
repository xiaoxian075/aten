<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<title>系统日志管理</title>
<%@ include file="/WEB-INF/common/timepicker.jsp"%>
<script type="text/javascript">
          $(function () {
              // 时间设置
				$("#start_time_s").datetimepicker({
					format:'Y-m-d',
					language: 'zh',
					minTime: true,
					maxTime: true,
					timepicker:false,
					onSelectDate: function () {
						var starttime=$("#start_time_s").val();
						$("#end_time_s").datetimepicker({
							minDate: starttime,
							maxDate: false,
						});
					},
				});
				
				$("#end_time_s").datetimepicker({
					format:'Y-m-d',
					language: 'zh',
					minTime: true,
					maxTime: true,
					timepicker:false,
					onSelectDate: function () {
						var endtime=$("#end_time_s").val();
						$("#start_time_s").datetimepicker({
							minDate: false,
							maxDate: endtime,
						});
					},
				});
          });
	  </script>
</head>
<body>
	<form action="/admin/syslog/list" method="post">
		<div class="searchDiv">
			<table class="searchTable">
				<tr>
					<td>用户名:</td>
					<td><input type="text" class="ordertxt"
						name="back_name_vague_s" value="${back_name_vague_s}" /></td>
					<td>操作时间:</td>
					<td><input type="text" class="ordertxt" id="start_time_s"
						name="start_time_s" value="${start_time_s}" /> - <input
						type="text" class="ordertxt" id="end_time_s" name="end_time_s"
						value="${end_time_s}" /></td>
					<td><input class="btn ol_colorbtn ol_greenbtn" type="button"
						value="查询" onclick="goInfo('/admin/syslog/list');" /> <input
						class="btn ol_colorbtn ol_bredbtn" type="button" value="清空"
						onclick="clearSearch('/admin/syslog/list');" /></td>
				</tr>
			</table>
		</div>
		<div class="show_line">
			<%@ include file="/WEB-INF/common/pageshowrow.jsp"%>
		</div>
		<div class="list_div">
			<table id="list_table" border="0" cellspacing="0" cellpadding="0">
				<tr>

					<th width="10%">用户名</th>

					<th width="10%">操作时间</th>

					<th width="10%">ip地址</th>

					<th width="10%">操作方法</th>

				</tr>
				<c:if test="${!empty syslogList}">
					<c:forEach items="${syslogList}" var="item" varStatus="status">
						<tr>

							<td>${item.back_name}</td>

							<td>${item.in_date}</td>

							<td>${item.ip}</td>

							<td>${item.content}</td>

						</tr>
					</c:forEach>
				</c:if>
				<c:if test="${empty syslogList}">
					<td colspan="10">暂无数据</td>
				</c:if>
			</table>
		</div>
		<div class="page_contain">
			<%@ include file="/WEB-INF/common/pagelist.jsp"%>
		</div>
		<%@ include file="/WEB-INF/common/list_hidden_value.jsp"%>
	</form>
</body>
</html>

