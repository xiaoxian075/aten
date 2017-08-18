<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!-- 分页插件 -->
<div class="list_page">
	<span class="lp_span">显示 ${page.pageStart} 到 ${page.pageTop} ，共
		${page.totalCount} 条</span>

	<div class="pagelist">

		<c:if test="${page.minCurrentPage>1}">
			<a href="###" onclick="pageClickSubmit(1);">首页</a>
			<span>&nbsp;&nbsp;...</span>
		</c:if>

		<c:forEach var="i" begin="${page.minCurrentPage}"
			end="${page.current_s-1}" step="1">
			<a href="###" onclick="pageClickSubmit(${i});">${i}</a>
		</c:forEach>
		<!-- 当前页 -->
		<a href="###" class="page_current"
			onclick="pageClickSubmit(${page.current_s});">${page.current_s}</a>

		<c:forEach var="i" begin="${page.current_s+1}"
			end="${page.maxCurrentPage}" step="1">
			<a href="###" onclick="pageClickSubmit(${i});">${i}</a>
		</c:forEach>

		<c:if test="${page.maxCurrentPage<page.totalPage}">
			<span>&nbsp;...&nbsp;</span>
			<a href="###" onclick="pageClickSubmit(${page.totalPage});">未页</a>
		</c:if>
	</div>
</div>
<input type="hidden" id="current_s" name="${page.current_name}"
	value="${page.current_s}" />
