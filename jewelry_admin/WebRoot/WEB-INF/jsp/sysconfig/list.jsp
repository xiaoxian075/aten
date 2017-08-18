<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<title>系统设置管理</title>
<style type="text/css">
.list_div_ul {
	height: 28px;
	padding-top: 6px;
}

.list_div_ul  a {
	padding: 3px 8px 3px 8px;
	font-weight: 600;
	font-size: 16px;
}

.list_div_ul .li_a {
	color: red;
}
</style>
</head>
<body>
	<form action="/admin/sysconfig/list" method="post">
		<div class="list_oper_div">
			<input class="btn ol_btn" type="button" value="新增变量"
				onclick="addInfo('/admin/sysconfig/add');" /> <input
				class="btn ol_btn" type="button" value="排序"
				onclick="sortInfo('/admin/sysconfig/sort');" />
		</div>
		<div class="searchDiv"></div>
		<div class="show_line">
			<%@ include file="/WEB-INF/common/pageshowrow.jsp"%>
		</div>
		<div class="list_div">
			<ul class="list_div_ul">
				<c:if test="${!empty commparaList}">
					<c:forEach items="${commparaList}" var="item">
						<a <c:if test="${item.para_key==var_type_s}"> class="li_a" </c:if>
							href="/admin/sysconfig/list?var_type_s=${item.para_key}">${item.para_name}</a>
					</c:forEach>
				</c:if>
			</ul>
			<table id="list_table" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<th width="5%">排序</th>
					<th width="15%">变量名称</th>
					<th width="15%">变量值</th>
					<th width="50%">变量说明</th>
					<th width="15%">操作</th>
				</tr>
				<c:if test="${!empty sysconfigList}">
					<c:forEach items="${sysconfigList}" var="item">
						<tr>
							<td><input class="sort_id" type="hidden"
								value="${item.var_id}" /> <input class="sort_val" type="text"
								value="${item.sort_no}" maxlength="5" style="width:50px;" /></td>
							<td>${item.var_name}</td>
							<td><c:if test="${var_type_s=='0'}">
									<!-- 文本框 -->
		  							${item.var_value}
		  						</c:if> <c:if test="${var_type_s=='1'}">
									<!-- 编辑框 -->
		  							-
		  						</c:if> <c:if test="${var_type_s=='2'}">
									<!-- 图片控件 -->
									<img class="img180" src="${ossImgServerUrl}${item.var_value}" />
								</c:if> <c:if test="${var_type_s=='3'}">
									<!-- 文本域-->
		  							${item.var_value}
		  						</c:if> <c:if test="${var_type_s=='4'}">
									<!-- select框 -->
									<c:if test="${item.var_value=='0'}">是</c:if>
									<c:if test="${item.var_value=='1'}">否</c:if>
								</c:if></td>
							<td>${item.var_desc}</td>

							<td><input class="btn ol_colorbtn ol_bluebtn" type="button"
								value="修改"
								onclick="goInfo('/admin/sysconfig/edit','${item.var_id}');" /> <input
								class="btn ol_colorbtn ol_redbtn" type="button" value="删除"
								onclick="delInfo('/admin/sysconfig/delete','${item.var_id}');" />
							</td>
						</tr>
					</c:forEach>
				</c:if>
				<c:if test="${empty sysconfigList}">
					<td colspan="5">暂无数据</td>
				</c:if>
			</table>
		</div>
		<input type="hidden" name="var_type_s" value="${var_type_s}" />
		<div class="page_contain">
			<%@ include file="/WEB-INF/common/pagelist.jsp"%>
		</div>
		<%@ include file="/WEB-INF/common/list_hidden_value.jsp"%>
	</form>
</body>
</html>
