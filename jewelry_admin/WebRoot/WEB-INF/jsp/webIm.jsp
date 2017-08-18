<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!doctype html>
<html>
<head>
<title>客服工作台</title>
</head>
<body>
	<div style="margin:0px auto;width:900px; height:800px;margin-top:50px;">
		<iframe frameborder="0" width="900" height="800" marginheight="0"
			marginwidth="0" scrolling="no"
			src="${h5_server}/rest/web/chat/index.html?un=${un}&pw=${pw}"></iframe>
	</div>
</body>
</html>
