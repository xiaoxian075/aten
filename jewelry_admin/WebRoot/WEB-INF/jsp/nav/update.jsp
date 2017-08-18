<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<html>
<head>
<title>修改导航</title>
<%@ include file="/WEB-INF/common/img_control.jsp"%>

</head>
<body>
	<form id="validateForm" action="/admin/nav/update" method="post">
		<div class="opercontent">
			<%@ include file="/WEB-INF/jsp/nav/common.jsp"%>
			<div class="row50 operbtndiv">
				<input type="button" value="修改导航" class="btn operbtn"
					onclick="submitData();" /> <input type="button" class="btn return"
					onclick="returnGo('/admin/nav/list')" value="返回列表" />
			</div>
		</div>
	</form>
</body>
</html>

