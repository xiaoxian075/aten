<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<title>资讯管理</title>
</head>
<body>
	<form action="/admin/news/list" method="post">
		<div class="list_oper_div">
			<input class="btn ol_btn" type="button" value="新增资讯"
				onclick="addInfo('/admin/news/add');" /> <input class="btn ol_btn"
				type="button" value="排序" onclick="sortInfo('/admin/news/sort');" />
		</div>
		<div class="searchDiv">
			<table class="searchTable">
				<tr>
					<td>资讯标题:</td>
					<td><input type="text" name="news_title_vague_s"
						value="${news_title_vague_s}" /></td>
					<td>所属分类:</td>
					<td>
						<div id="cat_id_div" tipmsg="所属分类" setwidth="200" setheight="25"></div>
						<input changetip="cat_id_div" type="hidden" id="cat_id"
						name="the_cat_s" value="${the_cat_s}" />
					</td>
					<td class="td_left">发布时间:</td>
					<td class="td_right_two"><input class="text w130" type="text"
						id="push_time_start_s" name="push_time_start_s" maxlength='20'
						maxdatalength='20' value="${push_time_start_s}" /> - <input
						class="text w130" type="text" id="push_time_end_s"
						name="push_time_end_s" maxlength='20' maxdatalength='20'
						value="${push_time_end_s}" />
					</td>
					<td><input class="btn ol_colorbtn ol_greenbtn" type="button"
						value="搜索" onclick="searchInfo('/admin/news/list');" /> <input
						class="btn ol_colorbtn ol_bredbtn" type="button" value="清空"
						onclick="clearSearch('/admin/news/list');" /></td>
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
					
					<th width="5%">资讯ID</th>

					<th width="10%">资讯标题</th>

					<th width="10%">所属分类</th>

					<th width="10%">作者</th>

					<th width="10%">发布时间</th>

					<th width="10%">状态</th>

					<th width="10%">操作</th>
				</tr>
				<c:if test="${!empty newsList}">
					<c:forEach items="${newsList}" var="item" varStatus="status">
						<tr>
							<td><input class="ids" type="checkbox"
								value="${item.news_id}" /></td>

							<td><input class="sort_id" type="hidden"
								value="${item.news_id}" /> <input class="sort_val" type="text"
								value="${item.sort_no}" maxlength="6" /></td>
								
							<td>${item.news_id}</td>

							<td>${item.news_title}</td>

							<td class="td_left">${item.the_cat}</td>

							<td>${item.news_author}</td>

							<td>${item.issue_time}</td>

							<td><c:if test="${item.state==2}">
									<span class="span_blue">定时发布</span>
								</c:if> <c:if test="${item.state==1}">
									<span class="span_green">已发布</span>
								</c:if> <c:if test="${item.state==0}">
									<span class="span_red">待发布</span>
								</c:if></td>

							<td><input class="btn ol_colorbtn ol_bluebtn" type="button"
								value="查看"
								onclick="goInfo('/admin/news/view','${item.news_id}');" /> <input
								class="btn ol_colorbtn ol_bluebtn" type="button" value="修改"
								onclick="editInfo('/admin/news/edit','${item.news_id}');" /> <input
								class="btn ol_colorbtn ol_redbtn" type="button" value="删除"
								onclick="delInfo('/admin/news/delete','${item.news_id}');" /> <c:if
									test="${item.state==0}">
									<input class="btn ol_colorbtn ol_greenbtn" type="button"
										value="发布"
										onclick="commonInfo('/admin/news/pushState','${item.news_id}','确定发布该资讯？');" />
								</c:if></td>
						</tr>
					</c:forEach>
				</c:if>
				<c:if test="${empty newsList}">
					<td colspan="9">暂无数据</td>
				</c:if>
			</table>
		</div>
		<div class="batchDiv">
			<span class="batch_span"><input class="all" type="checkbox" />全选</span>
			<input class="btn ol_colorbtn ol_redbtn" type="button" value="批量删除"
				onclick="commonBatchInfo('/admin/news/batchDelete','确定删除资讯？');" />
		</div>
		<div class="page_contain">
			<%@ include file="/WEB-INF/common/pagelist.jsp"%>
		</div>
		<%@ include file="/WEB-INF/common/list_hidden_value.jsp"%>
	</form>
	<script type="text/javascript">
    	// 时间设置
		/* $('#push_time_start_s,#push_time_end_s').datetimepicker({
			format:'Y-m-d H:i',
			language: 'zh',
		}); */
		
		$("#push_time_start_s").datetimepicker({
			format:'Y-m-d',
			language: 'zh',
			minTime: true,
			maxTime: true,
			timepicker:false,
			onSelectDate: function () {
				var starttime=$("#push_time_start_s").val();
				$("#push_time_end_s").datetimepicker({
					minDate: starttime,
					maxDate: false,
				});
			},
		});
		
		$("#push_time_end_s").datetimepicker({
			format:'Y-m-d',
			language: 'zh',
			minTime: true,
			maxTime: true,
			timepicker:false,
			onSelectDate: function () {
				var endtime=$("#push_time_end_s").val();
				$("#push_time_start_s").datetimepicker({
					minDate: false,
					maxDate: endtime,
				});
			},
		});
		
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

