<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<html>
  <head>
    <title>修改摇一摇优惠卷中奖记录</title>
  </head>
  <body>
  <form id="validateForm" action="/admin/shakewinningrecord/update" method="post">
	  <div class="opercontent">
	  		<%@ include file="/WEB-INF/jsp/shakeWinningRecord/common.jsp"%>
	  		<div class="row50 operbtndiv">
	  			<input type="hidden" name="token" value="${token}">
	  			<input type="button" value="修改摇一摇优惠卷中奖记录" class="btn operbtn" onclick="submitData();"/>
	  			<input type="button" class="btn return" onclick="returnGo('/admin/shakewinningrecord/list')" value="返回列表"/>
	  		</div>
	</div>
  </form>	
</body>
</html> 

