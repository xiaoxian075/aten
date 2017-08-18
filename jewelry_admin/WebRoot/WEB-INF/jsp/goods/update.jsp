<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<html>
<head>
<title>修改商品</title>
</head>
<body>
	<form id="validateForm" action="/admin/goods/update" method="post">
		<div class="opercontent">
			<%@ include file="/WEB-INF/jsp/goods/common.jsp"%>
			<div class="row50 operbtndiv">
				<input type="button" value="修改商品" class="btn operbtn"
					onclick="goodsSubmitData()"  /> <input type="button" class="btn return"
					onclick="returnGo('/admin/goods/list')" value="返回列表" />
			</div>
		</div>
	</form>
</body>
</html>

