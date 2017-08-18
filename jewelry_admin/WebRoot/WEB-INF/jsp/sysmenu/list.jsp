<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<html>
<head>
<title>菜单管理</title>
<link rel="stylesheet" type="text/css"
	href="/include/admin/css/treeTable.css" />
<script type="text/javascript" src="/include/admin/js/treeTable.js"></script>
<script type="text/javascript">
    	$(document).ready(function(){ 
     		$("#menuTable").treeTable({
     			//请求列表url
		    	url:"/admin/sysmenu/getChildList?pr=0"
     		});		
    	});
    </script>
</head>
<body>
	<form id="treeFrom" action="/admin/sysmenu/list" method="post">
		<div id="menuTable" class="treediv"></div>
		<input type="hidden" id="sort_id" name="sort_id" value="" /> <input
			type="hidden" id="sort_val" name="sort_val" value="" /> <input
			type="hidden" id="id" name="id" value="${treeBean.id}" /> <input
			type="hidden" id="up_id" name="up_id" value="${treeBean.up_id}" /> <input
			type="hidden" id="back_sel_id" name="back_sel_id"
			value="${treeBean.back_sel_id}" />

	</form>
</body>
</html>
