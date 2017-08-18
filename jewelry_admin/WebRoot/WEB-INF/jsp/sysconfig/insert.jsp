<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<html>
<head>
<title>新增变量</title>
<%@ include file="/WEB-INF/common/img_control.jsp"%>
<%@ include file="/WEB-INF/common/ueditor.jsp"%>
</head>
<body>
	<form id="validateForm" action="/admin/sysconfig/insert" method="post">
		<div class="opercontent">
			<%@ include file="/WEB-INF/jsp/sysconfig/common.jsp"%>
			<div class="row50 operbtndiv">
				<input type="button" value="新增变量" class="btn operbtn"
					onclick="submitData();" /> <input type="button" class="btn return"
					onclick="returnGo('/admin/sysconfig/list')" value="返回列表" />
			</div>
		</div>
	</form>
</body>
</html>

