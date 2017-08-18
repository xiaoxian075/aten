<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<title>物流模板管理</title>
</head>
<body>
	<form action="/admin/logitemp/list" method="post">
		<div class="list_oper_div">
			<input class="btn ol_btn" type="button" value="新增运费模板"
				onclick="addInfo('/admin/logitemp/insert');" />
		</div>
		<div class="searchDiv">
			<table class="searchTable">
				<tr>
					<td>运费模板名称:</td>
					<td><input type="text" name="ship_name_s"
						value="${ship_name_s}" /></td>
					<td><input class="btn ol_colorbtn ol_greenbtn" type="button"
						value="搜索" onclick="searchInfo('/admin/logitemp/list');" /> <input
						class="btn ol_colorbtn ol_bredbtn" type="button" value="清空"
						onclick="clearSearch('/admin/logitemp/list');" /></td>
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
					<th width="10%">运费模板编号</th>
					<th width="10%">运费模板名称</th>
					<th width="9%">计价方式</th>
					<th width="7%">运送方式</th>
					<th width="7%">是否包邮</th>
					<th width="20%">宝贝地址</th>
					<th width="10%">发货时间</th>
					<th width="7%">状态</th>
					<th width="10%">操作</th>
				</tr>
				<c:if test="${!empty shiptemplateList}">
					<c:forEach items="${shiptemplateList}" var="item"
						varStatus="status">
						<tr>
							<td><input class="ids" type="checkbox"
								value="${item.ship_id}" /></td>

							<%-- 		  					<td>
		  						<input class="sort_id" type="hidden" value="${item.ship_id}"/>
	  							<input class="sort_val" type="text" value="${item.sort_no}" maxlength="6" />
		  					</td> --%>

							<td>${item.ship_id}</td>
							<td>${item.ship_name}</td>
							<td><c:if test="${item.valuation_mode==0}">
									<span class="span_green">按件数</span>
								</c:if> <c:if test="${item.valuation_mode==1}">
									<span class="span_red">按重量</span>
								</c:if> <c:if test="${item.valuation_mode==2}">
									<span class="span_red">按体积</span>
								</c:if></td>
							<td><c:if test="${item.express_id_str=='0'}">
									<span class="span_green">快递</span>
								</c:if> <c:if test="${item.express_id_str=='1'}">
									<span class="span_red">EMS</span>
								</c:if> <c:if test="${item.express_id_str=='2'}">
									<span class="span_red">平邮</span>
								</c:if></td>
							<td><c:if test="${item.free_ship==0}">
									<span class="span_green">否</span>
								</c:if> <c:if test="${item.free_ship==1}">
									<span class="span_red">是</span>
								</c:if></td>
							<td>${item.start_area}</td>
							<td>${item.send_time}</td>
							<td><c:if test="${item.state==1}">
									<span class="span_green">已启用</span>
								</c:if> <c:if test="${item.state==0}">
									<span class="span_red">已禁用</span>
								</c:if></td>

							<td><input class="btn ol_colorbtn ol_bluebtn" type="button"
								value="修改"
								onclick="editInfo('/admin/logitemp/update','${item.ship_id}');" />
								<c:if test="${item.state==1}">
									<input class="btn ol_colorbtn ol_redbtn" type="button"
										value="禁用"
										onclick="commonInfo('/admin/logitemp/limitState','${item.ship_id}','确定禁用该运费模板？');" />
								</c:if> <c:if test="${item.state==0}">
									<input class="btn ol_colorbtn ol_greenbtn" type="button"
										value="启用"
										onclick="commonInfo('/admin/logitemp/enableState','${item.ship_id}','确定启用运费模板？');" />
								</c:if> <input class="btn ol_colorbtn ol_redbtn" type="button"
								value="删除"
								onclick="delInfo('/admin/logitemp/delete','${item.ship_id}');" />
							</td>
						</tr>
					</c:forEach>
				</c:if>
				<c:if test="${empty shiptemplateList}">
					<td colspan="10">暂无数据</td>
				</c:if>
			</table>
		</div>
		<div class="batchDiv">
			<span class="batch_span"><input class="all" type="checkbox" />全选</span>
			<input class="btn ol_colorbtn ol_redbtn" type="button" value="批量删除"
				onclick="commonBatchInfo('/admin/logitemp/batchdelete','确定删除？');" />
			<input class="btn ol_colorbtn ol_greenbtn" type="button" value="批量启用"
				onclick="commonBatchInfo('/admin/logitemp/batchenablestate','确定启用？');" />
			<input class="btn ol_colorbtn ol_bluebtn" type="button" value="批量禁用"
				onclick="commonBatchInfo('/admin/logitemp/batchlimitstate','确定禁用？');" />
		</div>
		<div class="page_contain">
			<%@ include file="/WEB-INF/common/pagelist.jsp"%>
		</div>
		<%@ include file="/WEB-INF/common/list_hidden_value.jsp"%>
	</form>
</body>
</html>

