<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<title>供应商管理</title>
</head>
<body>
	<form action="/admin/supply/list" method="post">
		<div class="list_oper_div">
			<input class="btn ol_btn" type="button" value="新增供应商"
				onclick="addInfo('/admin/supply/add');" /> <input class="btn ol_btn"
				type="button" value="排序" onclick="sortInfo('/admin/supply/sort');" />
		</div>
		<div class="searchDiv">
			<table class="searchTable">
				<tr>
					<td>供应商名称:</td>
					<td><input type="text" name="supply_name_vague_s"
						value="${supply_name_vague_s}" /></td>
					<td><input class="btn ol_colorbtn ol_greenbtn" type="button"
						value="搜索" onclick="searchInfo('/admin/supply/list');" /> <input
						class="btn ol_colorbtn ol_bredbtn" type="button" value="清空"
						onclick="clearSearch('/admin/supply/list');" /></td>
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

					<th width="10%">排序</th>

					<th width="10%">供应商名称</th>

					<th width="10%">联系人</th>

					<th width="10%">联系电话</th>

					<th width="10%">有效期</th>

					<th width="10%">状态</th>

					<th width="10%">操作</th>
				</tr>
				<c:if test="${!empty supplyList}">
					<c:forEach items="${supplyList}" var="item" varStatus="status">
						<tr>
							<td><input class="ids" type="checkbox"
								value="${item.supply_id}" /></td>

							<td><input class="sort_id" type="hidden"
								value="${item.supply_id}" /> <input class="sort_val" type="text"
								value="${item.sort_no}" maxlength="6" /></td>

							<td>${item.supply_name}</td>

							<td>${item.supply_contacts}</td>

							<td>${item.supply_contacts_phone}</td>

							<td>${item.valid_time_start}~ ${item.valid_time_end}</td>

							<td><c:if test="${item.state==1}">
									<span class="span_green">已启用</span>
								</c:if> <c:if test="${item.state==0}">
									<span class="span_red">已禁用</span>
								</c:if></td>

							<td><input class="btn ol_colorbtn ol_bluebtn" type="button"
								value="查看"
								onclick="editInfo('/admin/supply/view','${item.supply_id}');" />
								<input class="btn ol_colorbtn ol_bluebtn" type="button"
								value="修改"
								onclick="editInfo('/admin/supply/edit','${item.supply_id}');" />
								<c:if test="${item.state==1}">
									<input class="btn ol_colorbtn ol_redbtn" type="button"
										value="禁用"
										onclick="commonInfo('/admin/supply/editState','${item.supply_id}','确定禁用该供应商？');" />
								</c:if> <c:if test="${item.state==0}">
									<input class="btn ol_colorbtn ol_greenbtn" type="button"
										value="启用"
										onclick="commonInfo('/admin/supply/editState','${item.supply_id}','确定启用该供应商？');" />
								</c:if> <input class="btn ol_colorbtn ol_redbtn" type="button"
								value="删除"
								onclick="delInfo('/admin/supply/delete','${item.supply_id}');" />
							</td>
						</tr>
					</c:forEach>
				</c:if>
				<c:if test="${empty supplyList}">
					<td colspan="8">暂无数据</td>
				</c:if>
			</table>
		</div>
		<div class="batchDiv">
			<span class="batch_span"><input class="all" type="checkbox" />全选</span>
			<input class="btn ol_colorbtn ol_redbtn" type="button" value="批量删除"
				onclick="commonBatchInfo('/admin/supply/batchdelete','确定删除供应商？');" />
			<input class="btn ol_colorbtn ol_greenbtn" type="button" value="批量启用"
				onclick="commonBatchInfo('/admin/supply/batchenablestate','确定启用供应商？');" />
			<input class="btn ol_colorbtn ol_bluebtn" type="button" value="批量禁用"
				onclick="commonBatchInfo('/admin/supply/batchlimitstate','确定禁用供应商？');" />
		</div>
		<div class="page_contain">
			<%@ include file="/WEB-INF/common/pagelist.jsp"%>
		</div>
		<%@ include file="/WEB-INF/common/list_hidden_value.jsp"%>
	</form>
</body>
</html>

