<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<html>
<head>
<title>修改首页分类</title>
</head>
<body>
	<form id="validateForm" action="/admin/commpara/update" method="post">
		<div class="opercontent">
			<%@ include file="/WEB-INF/jsp/commpara/common.jsp"%>
			<div class="row50 operbtndiv">
				<input type="button" value="修改字典" class="btn operbtn"
					onclick="submitData();" /> <input type="button" class="btn return"
					onclick="returnGo('/admin/commpara/list')" value="返回列表" />
			</div>
		</div>
	</form>
</body>
</html>

