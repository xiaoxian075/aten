<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isErrorPage="true"%>
<%@page import="java.io.PrintStream"%>
<%@page import="java.io.ByteArrayOutputStream"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>页面正在修复中</title>
</head>

<body style="text-align:center">
	<div style="margin:200 auto;">
		<img src="/include/admin/image/500.jpg" />
	</div>


	<div style="display:none;">
		<%  //此处输出异常信息  
      exception.printStackTrace();  
  
      ByteArrayOutputStream ostr = new ByteArrayOutputStream();  
      exception.printStackTrace(new PrintStream(ostr));  
      out.print(ostr);  
  %>
	</div>
</body>
</html>
