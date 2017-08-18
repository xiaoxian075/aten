<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<title>资讯分类管理</title>
</head>
<body>
	<form action="/admin/newscat/list" method="post">
		<div class="list_oper_div">
			<input class="btn ol_btn" type="button" value="新增资讯分类"
				onclick="addInfo('/admin/newscat/add');" /> <input
				class="btn ol_btn" type="button" value="排序"
				onclick="sortInfo('/admin/newscat/sort');" />
		</div>
		<div class="searchDiv">
			<table class="searchTable">
				<tr>
					<td>资讯分类名称:</td>
					<td><input type="text" name="cat_name_vague_s"
						value="${cat_name_vague_s}" /></td>
					<td class="td_left">所属分类:</td>
					<td class="td_right_two">
						<div id="cat_id_div" tipmsg="上级分类" setwidth="200" setheight="25"></div>
						<input changetip="cat_id_div" type="hidden" id="cat_id"
						name="parent_cat_id_s" value="${parent_cat_id_s}" />
					</td>
					<td><input class="btn ol_colorbtn ol_greenbtn" type="button"
						value="搜索" onclick="searchInfo('/admin/newscat/list');" /> <input
						class="btn ol_colorbtn ol_bredbtn" type="button" value="清空"
						onclick="clearSearch('/admin/newscat/list');" /></td>
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
					
					<th width="5%">资讯分类ID</th>

					<th width="10%">资讯分类名称</th>

					<th width="10%">所属分类</th>

					<th width="10%">是否启用</th>

					<th width="10%">操作</th>
				</tr>
				<c:if test="${!empty newscatList}">
					<c:forEach items="${newscatList}" var="item" varStatus="status">
						<tr>
							<td><input class="ids" type="checkbox"
								value="${item.cat_id}" /></td>

							<td><input class="sort_id" type="hidden"
								value="${item.cat_id}" /> <input class="sort_val" type="text"
								value="${item.sort_no}" maxlength="6" /></td>
								
							<td>${item.cat_id}</td>

							<td>${item.cat_name}</td>


							<td class="td_left">${item.parent_cat_id}</td>


							<td><c:if test="${item.state==1}">
									<span class="span_green">已启用</span>
								</c:if> <c:if test="${item.state==0}">
									<span class="span_red">已禁用</span>
								</c:if></td>

							<td class="td_left"><input class="btn ol_colorbtn ol_bluebtn" type="button"
								value="修改"
								onclick="editInfo('/admin/newscat/edit','${item.cat_id}');" /> <!-- 状态操作 -->
								<c:if test="${item.state==1}">
									<input class="btn ol_colorbtn ol_redbtn" type="button"
										value="禁用"
										onclick="commonInfo('/admin/newscat/limitState','${item.cat_id}','确定禁用该资讯分类？');" />
								</c:if> 
								<c:if test="${item.state==0}">
									<input class="btn ol_colorbtn ol_greenbtn" type="button"
										value="启用"
										onclick="commonInfo('/admin/newscat/enableState','${item.cat_id}','确定启用该资讯分类？');" />
								</c:if> 
								<c:if test="${item.is_sys==0}">
									<input class="btn ol_colorbtn ol_redbtn" type="button" value="删除" onclick="delInfo('/admin/newscat/delete','${item.cat_id}');" />
								</c:if> 
							</td>
						</tr>
					</c:forEach>
				</c:if>
				<c:if test="${empty newscatList}">
					<td colspan="7">暂无数据</td>
				</c:if>
			</table>
		</div>
		<div class="batchDiv">
			<span class="batch_span"><input class="all" type="checkbox" />全选</span>
			<input class="btn ol_colorbtn ol_redbtn" type="button" value="批量删除"
				onclick="commonBatchInfo('/admin/newscat/delete','确定删除资讯分类？');" /> <input
				class="btn ol_colorbtn ol_greenbtn" type="button" value="批量启用"
				onclick="commonBatchInfo('/admin/newscat/batchEnableState','确定启用资讯分类？');" />
			<input class="btn ol_colorbtn ol_bluebtn" type="button" value="批量禁用"
				onclick="commonBatchInfo('/admin/newscat/batchLimitState','确定禁用资讯分类？');" />
		</div>
		<div class="page_contain">
			<%@ include file="/WEB-INF/common/pagelist.jsp"%>
		</div>
		<%@ include file="/WEB-INF/common/list_hidden_value.jsp"%>
	</form>
	<script type="text/javascript">
    $(document).ready(function(){
    	$("#cat_id_div").cascadeSel({
            url:"/admin/newscat/normalList",
            name:"cat_id",
            init_id:"${cfg_news_cat}"
        });
    });
</script>
</body>
</html>

