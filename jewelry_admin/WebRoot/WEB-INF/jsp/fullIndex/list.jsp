<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<title>索引全量更新记录管理</title>
</head>
<body>
	<form action="/admin/fullIndex/list" method="post">
		<div class="list_oper_div">
			<input class="btn ol_btn" type="button" value="创建分类属性索引"
				onclick="addInfo('/admin/fullIndex/createCatattrIndex');" /> <input
				class="btn ol_btn" type="button" value="创建商品索引"
				onclick="addInfo('/admin/fullIndex/createGoodsIndex');" />
		</div>
		<div class="show_line">
			<%@ include file="/WEB-INF/common/pageshowrow.jsp"%>
		</div>
		<div class="list_div">
			<table id="list_table" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<th width="10%">模块</th>

					<th width="10%">版本</th>

					<th width="10%">操作人</th>

					<th width="10%">操作时间</th>

					<th width="10%">是否使用版本</th>

					<th width="10%">操作</th>
				</tr>
				<c:if test="${!empty fullIndexList}">
					<c:forEach items="${fullIndexList}" var="item" varStatus="status">
						<tr>
							<td>${item.module}</td>

							<td>${item.index_version}</td>

							<td>${item.oper_man}</td>

							<td>${item.oper_time}</td>

							<td><c:if test="${item.use_version==0}">
									<span class="span_red">否</span>
								</c:if> <c:if test="${item.use_version==1}">
									<span class="span_green">是</span>
								</c:if></td>

							<td><c:if test="${item.use_version==0}">
									<input class="btn ol_colorbtn ol_bluebtn" type="button"
										value="启用"
										onclick="commonInfo('/admin/fullIndex/updateUse','${item.full_index_id}','确定启用该索引？');" />
								</c:if> <input class="btn ol_colorbtn ol_redbtn" type="button"
								value="删除"
								onclick="delInfo('/admin/fullIndex/delete','${item.full_index_id}');" />
							</td>
						</tr>
					</c:forEach>
				</c:if>
				<c:if test="${empty fullIndexList}">
					<td colspan="6">暂无数据</td>
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

