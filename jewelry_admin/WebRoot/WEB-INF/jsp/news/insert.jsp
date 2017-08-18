<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<html>
<head>
<title>新增资讯</title>
</head>
<body>
	<form id="validateForm" action="/admin/news/insert" method="post">
		<div class="opercontent">
			<%@ include file="/WEB-INF/jsp/news/common.jsp"%>
			<div class="row50 operbtndiv">
				<input type="hidden" name="token" value="${token}"> <input
					type="button" value="新增资讯" class="btn operbtn"
					onclick="submitData();" /> <input type="button" class="btn return"
					onclick="returnGo('/admin/news/list')" value="返回列表" />
			</div>
		</div>
	</form>
</body>
</html>

