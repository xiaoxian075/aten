<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<title>积分商品管理</title>
</head>
<body>
	<form action="/admin/integral/list" method="post">
		<div class="list_oper_div">
			<input class="btn ol_btn" type="button" value="新增积分商品"
				onclick="addInfo('/admin/integral/add');" /> <input
				class="btn ol_btn" type="button" value="排序"
				onclick="sortInfo('/admin/integral/sort');" /> <input
				class="btn ol_btn" type="button" value="会员积分查询"
				onclick="addInfo('/admin/accountInterApprove/add');" /> <input
				class="btn ol_btn" type="button" value="积分调整审批页"
				onclick="addInfo('/admin/accountInterApprove/list');" />
		</div>
		<div class="searchDiv">
			<table class="searchTable">
				<tr>
					<td>礼品编码:</td>
					<td><input type="text" name="integral_number_vague_s"
						value="${integral_number_vague_s}" /></td>
					<td>礼品名称:</td>
					<td><input type="text" name="integral_goods_name_vague_s"
						value="${integral_goods_name_vague_s}" /></td>
					<td><input class="btn ol_colorbtn ol_greenbtn" type="button"
						value="查询" onclick="goInfo('/admin/integral/list');" /> <input
						class="btn ol_colorbtn ol_bredbtn" type="button" value="清空"
						onclick="clearSearch('/admin/integral/list');" /></td>
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

					<th width="5%">排序</th>
					
					<th width="10%">礼品编码</th>

					<th width="10%">礼品名称</th>

					<th width="10%">使用积分值</th>

					<th width="10%">库存</th>

					<th width="10%">状态</th>

					<th width="15%">操作</th>
				</tr>
				<c:if test="${!empty integralList}">
					<c:forEach items="${integralList}" var="item" varStatus="status">
						<tr>
							<td><input class="ids" type="checkbox"
								value="${item.integral_id}" /></td>

							<td><input class="sort_id" type="hidden"
								value="${item.integral_id}" /> <input class="sort_val"
								type="text" value="${item.sort_no}" maxlength="6" /></td>
								
							<td>${item.integral_number}</td>

							<td>${item.integral_goods_name}</td>

							<td>${item.integral_value}</td>

							<td>${item.stock}</td>

							<td><c:if test="${item.is_up==0}">
									<span class="span_red">未上架</span>
								</c:if> <c:if test="${item.is_up==1}">
									<span class="span_green">已上架</span>
								</c:if></td>

							<td><input class="btn ol_colorbtn ol_bluebtn" type="button"
								value="查看"
								onclick="goInfo('/admin/integral/view','${item.integral_id}');" />
							<c:if test="${item.is_up==0}">
								<input class="btn ol_colorbtn ol_bluebtn" type="button"
								value="修改"
								onclick="editInfo('/admin/integral/edit','${item.integral_id}');" />
							</c:if> 
								<c:if test="${item.is_up==1}">
									<input class="btn ol_colorbtn ol_redbtn" type="button"
										value="下架"
										onclick="commonInfo('/admin/integral/limitState','${item.integral_id}','确定下架该礼品？');" />
								</c:if> <c:if test="${item.is_up==0}">
									<input class="btn ol_colorbtn ol_greenbtn" type="button"
										value="上架"
										onclick="commonInfo('/admin/integral/enableState','${item.integral_id}','确定上架该礼品？');" />
								</c:if> <input class="btn ol_colorbtn ol_redbtn" type="button"
								value="删除"
								onclick="delInfo('/admin/integral/delete','${item.integral_id}');" />
								<input class="btn ol_colorbtn ol_bluebtn" type="button"
								value="查看兑换"
								onclick="goInfo('/admin/orderIntegral/list','${item.integral_id}');" />
							</td>
						</tr>
					</c:forEach>
				</c:if>
				<c:if test="${empty integralList}">
					<td colspan="8">暂无数据</td>
				</c:if>
			</table>
		</div>
		<div class="batchDiv">
			<span class="batch_span"><input class="all" type="checkbox" />全选</span>
			<input class="btn ol_colorbtn ol_redbtn" type="button" value="批量删除"
				onclick="commonBatchInfo('/admin/integral/batchDelete','确定删除积分商品？');" />
			<input class="btn ol_colorbtn ol_greenbtn" type="button" value="批量上架"
				onclick="commonBatchInfo('/admin/integral/batchEnableState','确定上架该积分商品？');" />
			<input class="btn ol_colorbtn ol_bluebtn" type="button" value="批量下架"
				onclick="commonBatchInfo('/admin/integral/batchLimitState','确定下架该积分商品？');" />
		</div>
		<div class="page_contain">
			<%@ include file="/WEB-INF/common/pagelist.jsp"%>
		</div>
		<%@ include file="/WEB-INF/common/list_hidden_value.jsp"%>
	</form>
</body>
</html>

