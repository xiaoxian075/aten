<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<html>
<head>
<title>新增记录分成比例记录流</title>


</head>
<body>
	<form id="validateForm" action="/admin/dividerecord/insert"
		method="post">
		<div class="opercontent">
			<%@ include file="common.jsp"%>
			<div class="row50 operbtndiv">
				<input type="button" value="新增记录分成比例记录流" class="btn operbtn"
					onclick="submitData();" /> <input type="button" class="btn return"
					onclick="returnGo('/admin/dividerecord/list')" value="返回列表" />
			</div>
		</div>
	</form>
</body>
</html>

