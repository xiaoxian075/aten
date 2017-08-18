<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<html>
<head>
<title>分类管理</title>
<link rel="stylesheet" type="text/css"
	href="/include/admin/css/treeTable.css" />
<script type="text/javascript" src="/include/admin/js/treeTable.js"></script>
<script type="text/javascript">
    	$(document).ready(function(){ 
     		$("#catTable").treeTable({
     			 inz_id:"${cfg_top_cat}",
     			 //id标识
			     id:"cat_id",
			     //父标识
			     fid:"parent_cat_id",
			     //字段名称
			     fieldName:"cat_name",
			     //排序值 
			     sort:"sort_no",
			     //添加页面url
			     addUrl:"/admin/cat/add",
			     //编辑页面url
			     editUrl:"/admin/cat/edit",
			     //删除页面url
			     deleteUrl:"/admin/cat/delete",
			     //排序url
			     sortUrl:"/admin/cat/sort",
			     //请求列表url
			     url:"/admin/cat/getChildList"
     		});		
    	});
    </script>
</head>
<body>
	<form id="treeFrom" action="/admin/sysmenu/list" method="post">
		<div id="catTable" class="treediv"></div>
		<input type="hidden" id="sort_id" name="sort_id" value="" /> <input
			type="hidden" id="sort_val" name="sort_val" value="" /> <input
			type="hidden" id="id" name="id" value="${treeBean.id}" /> <input
			type="hidden" id="up_id" name="up_id" value="${treeBean.up_id}" /> <input
			type="hidden" id="back_sel_id" name="back_sel_id"
			value="${treeBean.back_sel_id}" />

	</form>
</body>
</html>
