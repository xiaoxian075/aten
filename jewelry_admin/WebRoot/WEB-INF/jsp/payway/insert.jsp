<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<html>
<head>
<title>新增支付方式</title>


</head>
<body>
	<form id="validateForm" action="/admin/payway/insert" method="post">
		<div class="opercontent">
			<%@ include file="/WEB-INF/jsp/payway/common.jsp"%>
			<div class="row50 operbtndiv">
				<input type="button" value="新增支付方式" class="btn operbtn"
					onclick="submitData();" /> <input type="button" class="btn return"
					onclick="returnGo('/admin/payway/list')" value="返回列表" />
			</div>
		</div>
	</form>
</body>
</html>

