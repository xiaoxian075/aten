<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<title>广告管理</title>
</head>
<body>
	<form action="/admin/ad/list" method="post">
		<div class="list_oper_div">
			<input class="btn ol_btn" type="button" value="添加广告"
				onclick="addInfo('/admin/ad/add','${adv_code}');" /> <input
				class="btn ol_btn" type="button" value="排序"
				onclick="sortInfo('/admin/ad/sort');" /> <input class="btn return"
				type="button" value="返回列表" onclick="goInfo('/admin/adv/list');" />
		</div>
		<div class="searchDiv"></div>
		<div class="list_div">
			<table id="list_table" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<th width="3%"><input class="all" type="checkbox" /></th>

					<th width="5%">排序</th>

					<th width="10%">广告名称</th>

					<th width="10%">广告图片</th>

					<th width="10%">广告跳转类型</th>

					<th width="10%">链接地址</th>

					<th width="10%">开始时间</th>

					<th width="10%">结束时间</th>

					<th width="10%">时间状态</th>

					<th width="10%">状态</th>

					<th width="10%">操作</th>
				</tr>
				<c:if test="${!empty adList}">
					<c:forEach items="${adList}" var="item" varStatus="status">
						<tr>

							<td><input class="ids" type="checkbox" value="${item.ad_id}" />
							</td>

							<td><input class="sort_id" type="hidden"
								value="${item.ad_id}" /> <input class="sort_val" type="text"
								value="${item.sort_no}" maxlength="6" /></td>

							<td>${item.ad_name}</td>

							<td><c:if test="${!empty item.img_path}">
									<img class="img180" src="${ossImgServerUrl}${item.img_path}" />
								</c:if> <c:if test="${empty item.img_path}">
									<img class="img180" src="${ossImgServerUrl}${noPicture}" />
								</c:if></td>

							<td>
								${item.ad_type_name}
							</td>

							<td>${item.link_url}</td>

							<td>${item.start_time}</td>

							<td>${item.end_time}</td>

							<td><c:if test="${item.time_state==0}">
									<span >未开始</span>
								</c:if> <c:if test="${item.time_state==1}">
									<span class="span_green">进行中</span>
								</c:if> <c:if test="${item.time_state==2}">
									<span class="span_red">已过期</span>
								</c:if></td>

							<td><c:if test="${item.state==1}">
									<span class="span_green">启用</span>
								</c:if> <c:if test="${item.state==0}">
									<span class="span_red">禁用</span>
								</c:if></td>

							<td><input class="btn ol_colorbtn ol_bluebtn" type="button"
								value="修改" onclick="editInfo('/admin/ad/edit','${item.ad_id}');" />
								<input class="btn ol_colorbtn ol_redbtn" type="button"
								value="删除"
								onclick="delInfo('/admin/ad/delete','${item.ad_id}');" /></td>

						</tr>
					</c:forEach>
				</c:if>
				<c:if test="${empty adList}">
					<td colspan="11">暂无数据</td>
				</c:if>
			</table>
		</div>
		<div class="batchDiv">
			<span class="batch_span"><input class="all" type="checkbox" />全选</span>
			<input class="btn ol_colorbtn ol_redbtn" type="button" value="批量删除"
				onclick="commonBatchInfo('/admin/ad/batchDelete','确定批量删除广告？');" />
		</div>
		<div class="page_contain">
			<%@ include file="/WEB-INF/common/pagelist.jsp"%>
		</div>
		<%@ include file="/WEB-INF/common/list_hidden_value.jsp"%>
		<%@ include file="/WEB-INF/common/search_hidden_field.jsp"%>
		<input type="hidden" name="adv_id" value="${adv_id}" />
	</form>
</body>
</html>

