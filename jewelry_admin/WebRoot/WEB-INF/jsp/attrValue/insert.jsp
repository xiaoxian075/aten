<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<html>
<head>
<title>添加属性值</title>
<%@ include file="/WEB-INF/common/img_control.jsp"%>
</head>
<body>
	<form id="validateForm" action="/admin/attrvalue/insert" method="post">
		<div class="opercontent">
			<%@ include file="/WEB-INF/jsp/attrValue/common.jsp"%>
			<input type="hidden" name="attr_id" value="${attr_id}" />
			<div class="row50 operbtndiv">
				<input type="button" value="新增属性值" class="btn operbtn"
					onclick="submitData();" /> <input type="button" class="btn return"
					onclick="goInfo('/admin/attrvalue/list/${attr_id}')" value="返回列表" />
			</div>
		</div>
	</form>
</body>
</html>

