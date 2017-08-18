<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,Chrome=1" />
    <meta http-equiv="X-UA-Compatible" content="IE=9" />
    <meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="">
    <title>云智设备管理系统</title>
    <link href="<%=basePath%>css/bootstrap/bootstrap.min.css" rel="stylesheet">
    <!--[if lte IE 6]>
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/bootstrap/bootstrap-ie6.css">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/bootstrap/ie.css">
    <![endif]-->

    <link href="<%=basePath%>css/bootstrap/metisMenu.min.css" rel="stylesheet">
    <link href="<%=basePath%>css/bootstrap/timeline.css" rel="stylesheet">
    <link href="<%=basePath%>css/bootstrap/admin.css" rel="stylesheet">
    <link href="<%=basePath%>css/bootstrap/morris.css" rel="stylesheet">
    <link href="<%=basePath%>css/bootstrap/font-awesome.min.css" rel="stylesheet" type="text/css">
    <!--[if IE]>
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/bootstrap/jr.css">
    <![endif]-->
    <!--[if lt IE 9]>
    <script src="<%=basePath%>js/bootstrap/html5shiv.min.js"></script>
    <script src="<%=basePath%>js/bootstrap/respond.min.js"></script>

    <![endif]-->
</head>
<body>
<!-- 头部 -->
<div id="wrapper" >
    <nav class="navbar navbar-default navbar-static-top" role="navigation" id="jrnavbar" style="margin-bottom: 0;background: #438eb9;">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" style="color: #fff;font-size: 24px;"><small>云智设备管理系统</small></a>
        </div>
        <ul class="nav navbar-top-links navbar-right">
            <li class="dropdown">
                <i style="color: #FFF;" class="fa fa-user fa-fw"></i>
                <span style="color: #FFF;">${userInfo.username}  用户欢迎您</span>
            </li>
            <li class="dropdown">
                <i style="color: #FFF;" class="fa fa-tasks fa-fw"></i>
                <span style="color: #FFF;">登录时间: <fmt:formatDate value="${userInfo.lastTime}" pattern="yyyy-MM-dd HH:mm" type="both"/> </span>
            </li>
            <li class="dropdown"><a href="<%=basePath%>rest/admin/logout" style="color: #FFF;"> <i class="fa fa-user fa-fw"></i> <span>退出系统</span> </a></li>
        </ul>

        <div class="navbar-default sidebar" role="navigation">
            <div class="sidebar-nav navbar-collapse">
                <ul class="nav" id="side-menu">

                <c:forEach items="${menuVoList}" var="menu" varStatus="it">
                    <li>
                        <a href="#">
                            <i class="fa fa-sitemap fa-fw"></i> ${menu.name}<span class="fa arrow"></span>
                        </a>
                        <c:if test="${not empty menu.children}">
                            <ul class="nav nav-second-level">
                            <c:forEach items="${menu.children}" var="childmenu">
                                <li>
                                    <a href="<%=basePath%>${childmenu.url}" target="main">${childmenu.name}</a>
                                </li>
                            </c:forEach>
                        </c:if>
                        </ul>
                    </li>
                </c:forEach>
                </ul>
            </div>
        </div>
    </nav>
    <div id="page-wrapper" >
        <iframe id="mainframe" style="margin-left:-5px;margin-right:5px;overflow-x:hidden; " name="main" marginheight=0 frameborder="0"  width="100%" ></iframe>
    </div>
</div>
<script src="<%=basePath%>js/bootstrap/jquery-1.12.0.min.js"></script>
<script src="<%=basePath%>js/bootstrap/bootstrap.min.js"></script>
<!--[if lte IE 6]>
<script type="text/javascript" src="<%=basePath%>js/bootstrap-ie.js"></script>
<![endif]-->
<script src="<%=basePath%>js/bootstrap/metisMenu.min.js"></script>
<script src="<%=basePath%>js/bootstrap/admin.js"></script>
<script>
    //注意：下面的代码是放在和iframe同一个页面调用,放在iframe下面
    $("#mainframe").load(function () {
        var mainheight = $("#wrapper").height() - 55;
        $(this).height(mainheight);
    });
</script>

</body>
</html>