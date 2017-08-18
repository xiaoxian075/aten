<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<title>鉴定机构管理</title>
</head>
<body>
	<form action="/admin/appraisal/list" method="post">
		<div class="list_oper_div">
			<input class="btn ol_btn" type="button" value="新增鉴定机构"
				onclick="addInfo('/admin/appraisal/add');" /> <input
				class="btn ol_btn" type="button" value="排序"
				onclick="sortInfo('/admin/appraisal/sort');" />
		</div>
		<div class="searchDiv">
			<table class="searchTable">
				<tr>
					<td>鉴定机构名称:</td>
					<td><input type="text" name="appraisal_name_vague_s"
						value="${appraisal_name_vague_s}" /></td>
					<td><input class="btn ol_colorbtn ol_greenbtn" type="button"
						value="搜索" onclick="searchInfo('/admin/appraisal/list');" /> <input
						class="btn ol_colorbtn ol_bredbtn" type="button" value="清空"
						onclick="clearSearch('/admin/appraisal/list');" /></td>
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

					<th width="10%">鉴定机构名称</th>

					<th width="10%">归属地</th>

					<th width="10%">联系人</th>

					<th width="10%">联系电话</th>

					<th width="10%">状态</th>

					<th width="10%">操作</th>
				</tr>
				<c:if test="${!empty appraisalList}">
					<c:forEach items="${appraisalList}" var="item" varStatus="status">
						<tr>
							<td><input class="ids" type="checkbox"
								value="${item.appraisal_id}" /></td>

							<td><input class="sort_id" type="hidden"
								value="${item.appraisal_id}" /> <input class="sort_val"
								type="text" value="${item.sort_no}" maxlength="6" /></td>

							<td>${item.appraisal_name}</td>

							<td>${item.the_area}</td>

							<td>${item.appraisal_contacts}</td>

							<td>${item.contacts_way}</td>

							<td><c:if test="${item.state==1}">
									<span class="span_green">已启用</span>
								</c:if> <c:if test="${item.state==0}">
									<span class="span_red">已禁用</span>
								</c:if></td>

							<td><input class="btn ol_colorbtn ol_bluebtn" type="button"
								value="查看"
								onclick="editInfo('/admin/appraisal/view','${item.appraisal_id}');" />
								<input class="btn ol_colorbtn ol_bluebtn" type="button"
								value="修改"
								onclick="editInfo('/admin/appraisal/edit','${item.appraisal_id}');" />
								<input class="btn ol_colorbtn ol_redbtn" type="button"
								value="删除"
								onclick="delInfo('/admin/appraisal/delete','${item.appraisal_id}');" />
							</td>
						</tr>
					</c:forEach>
				</c:if>
				<c:if test="${empty appraisalList}">
					<td colspan="8">暂无数据</td>
				</c:if>
			</table>
		</div>
		<div class="batchDiv">
			<span class="batch_span"><input class="all" type="checkbox" />全选</span>
			<input class="btn ol_colorbtn ol_redbtn" type="button" value="批量删除"
				onclick="commonBatchInfo('/admin/appraisal/batchDelete','确定删除鉴定机构？');" />
		</div>
		<div class="page_contain">
			<%@ include file="/WEB-INF/common/pagelist.jsp"%>
		</div>
		<%@ include file="/WEB-INF/common/list_hidden_value.jsp"%>
	</form>
</body>
</html>

