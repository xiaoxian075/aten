<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<html>
<head>
<title>查看品牌</title>
<%@ include file="/WEB-INF/common/img_control.jsp"%>
<%@ include file="/WEB-INF/common/ueditor.jsp"%>
</head>
<body>
	<form id="validateForm" action="/admin/brand/update" method="post">
		<div class="opercontent">
			<%@ include file="/WEB-INF/jsp/brand/viewcommon.jsp"%>
			<div class="row50 operbtndiv">
				<input type="button" class="btn return"
					onclick="returnGo('/admin/brand/list')" value="返回列表" />
			</div>
		</div>
	</form>
</body>
</html>

