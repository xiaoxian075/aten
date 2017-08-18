<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<html>
<head>
<title>添加系统地区管理</title>
</head>
<body>
	<form id="validateForm" action="/admin/area/insert" method="post">
		<div class="opercontent">
			<%@ include file="/WEB-INF/jsp/area/common.jsp"%>
			<div class="row50 operbtndiv">
				<input type="button" value="新增地区" class="btn operbtn"
					onclick="submitData();" /> <input type="button" class="btn return"
					onclick="returnGo('/admin/area/list')" value="返回列表" />
			</div>
		</div>
	</form>
</body>
</html>

