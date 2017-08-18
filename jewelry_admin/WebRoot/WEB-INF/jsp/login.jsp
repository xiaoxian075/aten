<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>系统登录页面</title>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<link rel="stylesheet" type="text/css"
	href="/include/admin/css/login.css" />
<!--[if lt IE 9]>
	  <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
	  <script src="https://oss.maxcdn.com/libs/respond.js/1.3.0/respond.min.js"></script>
	<![endif]-->
</head>
<script type="text/javascript">
      function changeImage(img){
          img.src=img.src+"?"+new Date().getTime();
      }
  </script>
<body>
	<div class="loginWraper">
		<form action="/loginAccount" method="post">
			<div id="loginform" class="loginBox">
				<table class="lg_tb">
					<tr>
						<td class="lg_td_left">账户:</td>
						<td class="lg_td_right"><input type="text " isrequired='yes'
							class="txt_name " name="user_name" /></td>
					</tr>
					<tr>
						<td class="lg_td_left">密码:</td>
						<td class="lg_td_right"><input type="password"
							isrequired='yes' class="txt_name" name="pass_word" /></td>
					</tr>
					<tr>
						<td class="lg_td_left">验证码:</td>
						<td class="lg_td_right"><input type="text" class="txt_name"
							isrequired='yes' style="width:160px;" name="inputCode" /> <img
							src="/getCheckCode" onclick="changeImage(this)" alt="换一张"
							style="cursor:hand;position:absolute;position: absolute;bottom:95px;" /><br />
						</td>
					</tr>
					<tr>
						<td class="lg_msg" colspan="2">${loginMsg}</td>
					</tr>
					<tr>
						<td colspan="2" class="login_td"><input class="login_btn btn"
							name="" value=" 登    录 " type="submit"> <input
							class="cancel_btn btn" name="" value=" 取    消 " type="reset">
						</td>
					</tr>
				</table>
			</div>
		</form>
	</div>

</body>
</html>
