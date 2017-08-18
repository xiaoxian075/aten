<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<html>
<head>
<title>商品发布</title>
<%@ include file="/WEB-INF/common/img_control.jsp"%>
<%@ include file="/WEB-INF/common/ueditor.jsp"%>
</head>
<body>

	<form id="validateForm" action="/admin/goods/insert" method="post">
		<div class="opercontent">
			<%@ include file="/WEB-INF/jsp/goods/common.jsp"%>
			<div class="row50 operbtndiv">
				<input type="button" value="发布商品" class="btn operbtn" onclick="goodsSubmitData()" />
				<input type="button" class="btn return" onclick="returnGo('/admin/goods/list')" value="返回列表" />
			</div>
		</div>
	</form>
</body>
</html>
