<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<html>
  <head>
    <title>修改摇一摇优惠卷奖项设置</title>
  </head>
  <body>
  <form id="validateForm" action="/admin/shakeawards/update" method="post">
	  <div class="opercontent">
	  		<%@ include file="/WEB-INF/jsp/shakeAwards/common.jsp"%>
	  		<div class="row50 operbtndiv">
	  			<input type="hidden" name="token" value="${token}">
	  			<input type="button" value="修改" class="btn operbtn" onclick="submitData();"/>
	  			<input type="button" class="btn return" onclick="returnGo('/admin/shakeawards/list',${parameter_id})" value="返回列表"/>
	  		</div>
	</div>
  </form>	
</body>
</html> 

