<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!-- 地图经纬度 -->
<div class="list_div" style="width:360px;margin-left:0px;">
	<table id="list_table" border="0" cellspacing="0" cellpadding="0">
		<tr>
			<th style="padding-left:10px">地图名称</th>
			<th style="padding-left:10px">经度</th>
			<th style="padding-left:10px">纬度</th>
		</tr>
		<c:if test="${!empty mapList}">
			<c:forEach items="${mapList}" var="item" varStatus="status">
				<!-- 更新页面 -->
				<c:if test="${!empty locationList}">
					<c:forEach items="${locationList}" var="location">
						<c:if test="${item.para_key==location.the_map}">
							<tr>
								<td>${item.para_name}</td>

								<td>${location.longitude}</td>

								<td>${location.latitude}</td>
							</tr>
						</c:if>
					</c:forEach>
				</c:if>
			</c:forEach>
		</c:if>
	</table>
</div>
