<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<title>品牌管理</title>
</head>
<body>
	<form action="/admin/brand/list" method="post">
		<div class="list_oper_div">
			<input class="btn ol_btn" type="button" value="新增品牌"
				onclick="addInfo('/admin/brand/add');" /> <input class="btn ol_btn"
				type="button" value="排序" onclick="sortInfo('/admin/brand/sort');" />
		</div>
		<div class="searchDiv">
			<table class="searchTable">
				<tr>
					<td>品牌名称:</td>
					<td><input type="text" name="brand_name_vague_s"
						value="${brand_name_vague_s}" /></td>
					<td><input class="btn ol_colorbtn ol_greenbtn" type="button"
						value="搜索" onclick="searchInfo('/admin/brand/list');" /> <input
						class="btn ol_colorbtn ol_bredbtn" type="button" value="清空"
						onclick="clearSearch('/admin/brand/list');" /></td>
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

					<th width="10%">品牌名称</th>

					<th width="10%">品牌logo</th>

					<th width="10%">状态</th>

					<th width="20%">操作</th>
				</tr>
				<c:if test="${!empty brandList}">
					<c:forEach items="${brandList}" var="item" varStatus="status">
						<tr>
							<td><input class="ids" type="checkbox"
								value="${item.brand_id}" /></td>

							<td><input class="sort_id" type="hidden"
								value="${item.brand_id}" /> <input class="sort_val" type="text"
								value="${item.sort_no}" maxlength="6" /></td>

							<td>${item.brand_name}</td>

							<td><img class="img180"
								src="${ossImgServerUrl}${item.brand_logo}" /></td>

							<td><c:if test="${item.state==1}">
									<span class="span_green">已启用</span>
								</c:if> <c:if test="${item.state==0}">
									<span class="span_red">已禁用</span>
								</c:if></td>

							<td><input class="btn ol_colorbtn ol_bluebtn" type="button"
								value="修改"
								onclick="editInfo('/admin/brand/edit','${item.brand_id}');" /> <!-- 状态操作 -->
								<c:if test="${item.state==1}">
									<input class="btn ol_colorbtn ol_redbtn" type="button"
										value="禁用"
										onclick="commonInfo('/admin/brand/limitState','${item.brand_id}','确定禁用该品牌？');" />
								</c:if> <c:if test="${item.state==0}">
									<input class="btn ol_colorbtn ol_greenbtn" type="button"
										value="启用"
										onclick="commonInfo('/admin/brand/enableState','${item.brand_id}','确定启用该品牌？');" />
								</c:if> <input class="btn ol_colorbtn ol_redbtn" type="button"
								value="删除"
								onclick="delInfo('/admin/brand/delete','${item.brand_id}');" />
							</td>
						</tr>
					</c:forEach>
				</c:if>
				<c:if test="${empty brandList}">
					<td colspan="6">暂无数据</td>
				</c:if>
			</table>
		</div>
		<div class="batchDiv">
			<span class="batch_span"><input class="all" type="checkbox" />全选</span>
			<input class="btn ol_colorbtn ol_redbtn" type="button" value="批量删除"
				onclick="commonBatchInfo('/admin/brand/batchDelete','确定删除商品品牌？');" />
			<input class="btn ol_colorbtn ol_greenbtn" type="button" value="批量启用"
				onclick="commonBatchInfo('/admin/brand/batchEnableState','确定启用商品品牌？');" />
			<input class="btn ol_colorbtn ol_bluebtn" type="button" value="批量禁用"
				onclick="commonBatchInfo('/admin/brand/batchLimitState','确定禁用商品品牌？');" />
		</div>
		<div class="page_contain">
			<%@ include file="/WEB-INF/common/pagelist.jsp"%>
		</div>
		<%@ include file="/WEB-INF/common/list_hidden_value.jsp"%>
	</form>
</body>
</html>

