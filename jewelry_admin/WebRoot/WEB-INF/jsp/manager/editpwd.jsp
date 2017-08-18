<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<html>
<head>
<title>修改密码</title>
</head>
<body>
	<script type="text/javascript">
//    $(function () {
//        $("#newpwd2").blur(function () {
//            if($("#newpwd1").val()!=$("#newpwd2").val()){
//                art.dialog({
//                    title: '友情提示',
//                    content: '新密码与确认新密码不一致,请重新输入！',
//                    time:2000
//                });
//            }
//        });
//    });
</script>
	<form id="validateForm" action="/admin/manager/updatepwd" method="post">
		<div class="opercontent">
			<div class="table_div">
				<table width="100%">
					<tr>
						<td class="td_left">用户名<span class="sp_span">:</span></td>
						<td class="td_right_two">${mana_name} <input type="hidden"
							name="mana_id" value="${manager.mana_id}" />
						</td>
					</tr>

					<tr>
						<td class="td_left">旧密码<span class="must_span">*</span></td>
						<td class="td_right_two"><input class="text validate"
							type="password" name="old_password" isrequired="yes"
							tipmsg="旧密码不能为空!" maxlength="20" value="" /></td>
					</tr>

					<tr>
						<td class="td_left">新密码<span class="must_span">*</span></td>
						<td class="td_right_two"><input class="text validate"
							type="password" name="new_password" id="newpwd1" isrequired="yes"
							tipmsg="新密码不能为空!" maxlength="20" value="" /></td>
					</tr>

					<tr>
						<td class="td_left">新密码确认<span class="must_span">*</span></td>
						<td class="td_right_two"><input class="text validate"
							type="password" name="confirm_password" id="newpwd2"
							isrequired="yes" tipmsg="新密码确认不能为空!" maxlength="20" value="" /></td>
					</tr>
				</table>
			</div>

			<div class="row50 operbtndiv">
				<input type="button" value="修改密码" class="btn operbtn"
					onclick="goInfo('/admin/manager/updatepwd');" />
			</div>
		</div>

	</form>
</body>

</html>

