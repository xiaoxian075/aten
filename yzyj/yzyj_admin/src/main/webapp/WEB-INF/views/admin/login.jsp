<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>欢迎登录后台管理系统</title>
    <link href="<%=basePath%>css/stylelogin.css" rel="stylesheet" type="text/css" />
    <script language="JavaScript" src="<%=basePath%>js/jquery.js"></script>
    <script src="<%=basePath%>js/cloud.js" type="text/javascript"></script>
    <script language="javascript">
        $(function(){
            $('.loginbox').css({'position':'absolute','left':($(window).width()-692)/2});
            $(window).resize(function(){
                $('.loginbox').css({'position':'absolute','left':($(window).width()-692)/2});
            })
        });
    </script>

</head>

<body style="background-color: #1c77ac; background-image: url(<%=basePath%>images/light.png); background-repeat: no-repeat; background-position: center top; overflow: hidden;">
<div id="mainBody">
    <div id="cloud1" class="cloud"></div>
    <div id="cloud2" class="cloud"></div>
</div>


<div class="logintop">
    <span>欢迎登录后台管理界面平台</span>
</div>

<div class="loginbody">

    <span class="systemlogo"></span>

    <div class="loginbox loginbox2">
        <form class="form-horizontal" action="<%=basePath%>rest/admin/login" method="post"
              name="userform" onSubmit="return check()">
            <ul>
                <li>
                    <input name="username" id="username" value="" type="text"
                           class="loginuser" />
                </li>
                <li>
                    <input name="password" id="password" type="password" value=""
                           class="loginpwd" />
                </li>
                <li>
                    <button type="submit" class="btn btn-primary">登录</button>
                </li>
                <li>
                    <c:if test="${not empty error}">
                        <span style="color:red">${error}</span>
                    </c:if>
                </li>
            </ul>
        </form>
    </div>
</div>
<div class="loginbm">
    版权所有2017
</div>
<script type="text/javascript">
    (function ($) {
        $(document).ready(function() {
            if ($.isFunction($.bootstrapIE6)) $.bootstrapIE6($(document));
        });
    })(jQuery);
    function check()
    {
        if (document.userform.username.value == "")
        {
            alert("请填写用户名！");
            document.userform.username.focus();
            return (false);
        }


        if (document.userform.password.value == "")
        {
            alert("请填写密码！");
            document.userform.password.focus();
            return (false);
        }

        if ((document.userform.password.value.length < 1 )||(document.userform.password.value.length >20 ))
        {
            alert("请正确填写密码！");
            document.userform.password.focus();
            return (false);
        }
    }
    function refreshCode(){
        document.getElementById("code").src="RandomCode?qs="+Math.random();
    }
</script>
</body>
</html>