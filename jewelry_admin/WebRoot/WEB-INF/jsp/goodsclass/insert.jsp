<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<html>
<head>
<title>新增商品分类</title>
<%@ include file="/WEB-INF/common/img_control.jsp"%>
</head>
<body>
	<form id="validateForm" action="/admin/goodsclass/insert" method="post">
		<div class="opercontent">
			<%@ include file="/WEB-INF/jsp/goodsclass/common.jsp"%>
			<div class="row50 operbtndiv">
				<input type="button" value="保存" class="btn operbtn"
					onclick="submitData();" /> <input type="button" class="btn return"
					onclick="returnGo('/admin/goodsclass/list')" value="返回列表" />
			</div>
		</div>
	</form>
</body>
</html>

