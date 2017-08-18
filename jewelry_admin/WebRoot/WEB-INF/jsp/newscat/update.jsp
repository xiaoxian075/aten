<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<html>
<head>
<title>修改测试</title>
</head>
<body>
	<form id="validateForm" action="/admin/newscat/update" method="post">
		<div class="opercontent">
			<%@ include file="/WEB-INF/jsp/newscat/common.jsp"%>
			<div class="row50 operbtndiv">
				<input type="hidden" name="token" value="${token}"> <input
					type="button" value="修改资讯分类" class="btn operbtn"
					onclick="submitData();" /> <input type="button" class="btn return"
					onclick="returnGo('/admin/newscat/list')" value="返回列表" />
			</div>
		</div>
	</form>
</body>
</html>

