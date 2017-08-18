<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<html>
<head>
<title>修改分类</title>
<%@ include file="/WEB-INF/common/img_control.jsp"%>
</head>
<body>
	<form id="validateForm" action="/admin/pre/update" method="post">
		<div class="opercontent">
			<%@ include file="/WEB-INF/jsp/pre/common.jsp"%>
			<div class="row50 operbtndiv">
				<input type="button" value="修改分类" class="btn operbtn"
					onclick="submitData();" /> <input type="button" class="btn return"
					onclick="returnGo('/admin/pre/list')" value="返回列表" />
			</div>
		</div>
	</form>
</body>
</html>

