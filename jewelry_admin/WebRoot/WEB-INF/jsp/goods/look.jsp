<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<html>
<head>
<title>查看商品</title>
</head>
<body>
	<form id="validateForm" method="post">
		<div class="opercontent">
			<%@ include file="/WEB-INF/jsp/goods/commonLook.jsp"%>
			<div class="row50 operbtndiv">
				<input type="button" class="btn return" onclick="returnGo('/admin/goods/list')" value="返回列表" />
			</div>
		</div>
	</form>
</body>
</html>

