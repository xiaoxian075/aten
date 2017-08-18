<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<title>兑换记录管理</title>
</head>
<body>
	<form action="/admin/orderIntegral/list" method="post">
		<div class="list_oper_div">
			<input type="button" class="btn return" onclick="returnGo('/admin/integral/list')" value="返回列表" />
		</div>
		<div class="searchDiv">
			</td>
			<table class="searchTable">
				<tr>
					<td>兑换编号:</td>
					<td><input type="text" name="order_number_vague_s"
						value="${order_number_vague_s}" /></td>
					<td>礼品名称:</td>
					<td><input type="text" name="integral_goods_name_vague_s"
						value="${integral_goods_name_vague_s}" /></td>
					<td class="td_left">兑换时间:</td>
					<td class="td_right_two"><input class="text w130" type="text"
						id="push_time_start_s" name="push_time_start_s" maxlength='20'
						maxdatalength='20' value="${push_time_start_s}" /> - <input
						class="text w130" type="text" id="push_time_end_s"
						name="push_time_end_s" maxlength='20' maxdatalength='20'
						value="${push_time_end_s}" /></td>
					<td><input class="btn ol_colorbtn ol_greenbtn" type="button"
						value="查询" onclick="goInfo('/admin/orderIntegral/list');" /> <input
						class="btn ol_colorbtn ol_bredbtn" type="button" value="清空"
						onclick="clearSearch('/admin/orderIntegral/list');" />

						<input class="btn ol_colorbtn ol_bluebtn" type="button" value="导出"
							   onclick="goInfo('/admin/orderIntegral/export');"/>
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

					<th width="10%">兑换编号</th>

					<th width="10%">礼品名称</th>

					<th width="10%">使用积分值</th>

					<th width="10%">兑换时间</th>

					<th width="10%">会员账户</th>

					<th width="10%">操作</th>
				</tr>
				<c:if test="${!empty orderIntegralList}">
					<c:forEach items="${orderIntegralList}" var="item"
						varStatus="status">
						<tr>
							<td><input class="ids" type="checkbox" value="${item.ex_id}" />
							</td>

							<td>${item.order_number}</td>

							<td>${item.integral_goods_name}</td>

							<td>${item.use_integral}</td>

							<td>${item.exchange_time}</td>

							<td>${item.login_name}</td>

							<td><input class="btn ol_colorbtn ol_bluebtn" type="button"
								value="查看"
								onclick="addInfo('/admin/orderIntegral/view','${item.ex_id}');" />
							</td>
						</tr>
					</c:forEach>
				</c:if>
				<c:if test="${empty orderIntegralList}">
					<td colspan="7">暂无数据</td>
				</c:if>
			</table>
		</div>
		<div class="batchDiv">
			<span class="batch_span"><input class="all" type="checkbox" />全选</span>
		</div>
		<div class="page_contain">
			<%@ include file="/WEB-INF/common/pagelist.jsp"%>
		</div>
		<%@ include file="/WEB-INF/common/list_hidden_value.jsp"%>
	</form>
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
</body>
</html>

