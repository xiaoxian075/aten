<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!-- 显示条数 -->
显示
<select class="select" name="pagesize_s" onChange="pageSelSubmit();">
	<option value="10"
		<c:if test="${page.pagesize_s==10}"> selected</c:if>>10</option>
	<option value="20" <c:if test="${page.pagesize_s==20}"> selected</c:if>>20</option>
	<option value="30" <c:if test="${page.pagesize_s==30}"> selected</c:if>>30</option>
	<option value="40" <c:if test="${page.pagesize_s==40}"> selected</c:if>>40</option>
	<option value="50" <c:if test="${page.pagesize_s==50}"> selected</c:if>>50</option>
	<option value="60" <c:if test="${page.pagesize_s==60}"> selected</c:if>>60</option>
	<option value="100" <c:if test="${page.pagesize_s==100}"> selected</c:if>>100</option>
</select>
条	