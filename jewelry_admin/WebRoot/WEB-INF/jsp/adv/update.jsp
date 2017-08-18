<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<html>
<head>
<title>修改广告位</title>
</head>
<body>
	<form id="validateForm" action="/admin/adv/update" method="post">
		<div class="opercontent">
			<%@ include file="/WEB-INF/jsp/adv/common.jsp"%>
			<div class="row50 operbtndiv">
				<input type="button" value="修改广告位" class="btn operbtn"
					onclick="submitData();" /> <input type="button" class="btn return"
					onclick="returnGo('/admin/adv/list')" value="返回列表" />
			</div>
		</div>
	</form>
</body>
</html>

