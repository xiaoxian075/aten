<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<html>
<head>
<title>新增部门</title>
<%--<link rel="stylesheet" href="/component/ztree/demo.css" type="text/css">--%>
<%--<link rel="stylesheet" href="/component/ztree/zTreeStyle/zTreeStyle.css" type="text/css">--%>
<%--<script type="text/javascript" src="/component/ztree/jquery.ztree.core.js"></script>--%>
<%--<script type="text/javascript" src="/include/admin/js/organize.js"></script>--%>

</head>
<body>
	<form id="validateForm" action="/admin/organize/insert" method="post">
		<div class="opercontent">
			<%@ include file="/WEB-INF/jsp/organize/common.jsp"%>
			<div class="row50 operbtndiv">
				<input type="button" value="新增部门" class="btn operbtn"
					onclick="submitData();" /> <input type="button" class="btn return"
					onclick="returnGo('/admin/organize/list')" value="返回列表" />
			</div>
		</div>
	</form>
</body>
</html>

