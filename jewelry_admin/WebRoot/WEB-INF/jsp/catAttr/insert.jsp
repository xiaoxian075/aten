<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<html>
<head>
<title>新增分类属性映射表</title>


</head>
<body>
	<form id="validateForm" action="/admin/catattr/insert" method="post">
		<div class="opercontent">
			<%@ include file="common.jsp"%>
			<div class="row50 operbtndiv">
				<input type="button" value="新增分类属性映射表" class="btn operbtn"
					onclick="submitData();" /> <input type="button" class="btn return"
					onclick="returnGo('/admin/catattr/list')" value="返回列表" />
			</div>
		</div>
	</form>
</body>
</html>

