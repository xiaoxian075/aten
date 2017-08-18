<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<title>部门管理</title>
<script src="/component/lay/pageGrid/treeTool.js"></script>
</head>
<body>
	<form action="/admin/organize/list" method="post">
		<div class="list_oper_div">
			<input class="btn ol_btn" type="button" value="新增部门"
				onclick="addInfo('/admin/organize/add');" />
		</div>
		<div class="searchDiv">
			<table class="searchTable">
				<tr>
					<td>部门名称:</td>
					<td><input type="text" name="org_name_s" value="${org_name_s}" /></td>
					<td>部门编号:</td>
					<td><input type="text" name="org_code_s" value="${org_code_s}" /></td>
					<td>上级:</td>
					<td>
						<div>
							<input value="${parent_org_name}" valueId="${parent_org_id_s}"
								id="parent" cyType="treeTool"
								cyProps="url:'/admin/organize/select',name:'parent_org_id_s'"
								onclick="openZtree(this)" placeholder="请选择上级菜单"
								class="layui-input" />
						</div>
					</td>
					<td><input class="btn ol_colorbtn ol_greenbtn" type="button"
						value="查询" onclick="goInfo('/admin/organize/list');" /> <input
						class="btn ol_colorbtn ol_bredbtn" type="button" value="清空"
						onclick="clearSearch('/admin/organize/list');" /></td>
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

					<th width="10%">部门名称</th>

					<th width="10%">部门编号</th>

					<th width="10%">上级部门</th>

					<th width="10%">上级部门编号</th>
					<th width="5%">操作</th>
				</tr>
				<c:if test="${!empty organizeList}">
					<c:forEach items="${organizeList}" var="item" varStatus="status">
						<tr>
							<td><input class="ids" type="checkbox"
								value="${item.org_id}" /></td>

							<td>${item.org_name}</td>

							<td>${item.org_code}</td>

							<td>${item.parent_org_name}</td>

							<td>${item.parent_org_code}</td>

							<td><input class="btn ol_colorbtn ol_bluebtn" type="button"
								value="修改"
								onclick="editInfo('/admin/organize/edit','${item.org_id}');" />
								<input class="btn ol_colorbtn ol_redbtn" type="button"
								value="删除"
								onclick="commonInfo('/admin/organize/delete','${item.org_id}','确认删除${item.org_name}？（同步删除下级部门）');" />
							</td>
						</tr>
					</c:forEach>
				</c:if>
				<c:if test="${empty organizeList}">
					<td colspan="6">暂无数据</td>
				</c:if>
			</table>
		</div>
		<div class="batchDiv">
			<span class="batch_span"><input class="all" type="checkbox" />全选</span>
			<input class="btn ol_colorbtn ol_redbtn" type="button" value="批量删除"
				onclick="commonBatchInfo('/admin/organize/batchDelete','确定删除所选部门？（同步删除下级部门）');" />
		</div>
		<div class="page_contain">
			<%@ include file="/WEB-INF/common/pagelist.jsp"%>
		</div>
		<%@ include file="/WEB-INF/common/list_hidden_value.jsp"%>
	</form>
</body>
</html>

