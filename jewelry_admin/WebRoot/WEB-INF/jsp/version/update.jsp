<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<html>
  <head>
    <title>修改版本</title>
  </head>
  <body>
  <form id="validateForm" action="/admin/version/update" method="post">
	  <div class="opercontent">
	  		<%@ include file="/WEB-INF/jsp/version/common.jsp"%>
	  		<div class="row50 operbtndiv">
	  			<input type="hidden" name="token" value="${token}">
	  			<input type="button" value="修改版本" class="btn operbtn" onclick="wsSubmit();"/>
	  			<input type="button" class="btn return" onclick="returnGo('/admin/version/list')" value="返回列表"/>
	  		</div>
	</div>
  </form>	
</body>
</html> 

