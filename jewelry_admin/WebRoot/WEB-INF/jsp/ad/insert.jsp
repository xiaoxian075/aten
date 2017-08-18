<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<html>
<head>
<title>新增广告</title>
<%@ include file="/WEB-INF/common/img_control.jsp"%>
<%@ include file="/WEB-INF/common/timepicker.jsp"%>
</head>
<body>
	<form id="validateForm" action="/admin/ad/insert" method="post">
		<div class="opercontent">
			<%@ include file="/WEB-INF/jsp/ad/common.jsp"%>
			<div class="row50 operbtndiv">
				<input type="button" value="新增广告" class="btn operbtn"
					onclick="submitData();" /> <input type="button" class="btn return"
					onclick="returnGo('/admin/ad/list')" value="返回列表" />
			</div>
		</div>
	</form>
</body>
</html>

