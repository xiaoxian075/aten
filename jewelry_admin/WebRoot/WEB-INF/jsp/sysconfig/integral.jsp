<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<html>
<head>
<title>积分设置</title>
</head>
<body>
	<form id="validateForm" action="/admin/sysconfig/updateIntegral"
		method="post">
		<div class="opercontent">
			<div class="table_div">
				<table width="100%">
					<tr>
						<td class="td_left"><span style="font-size: 18px">每日签到</span></td>
						<td></td>
					</tr>
					<tr>
						<input type="hidden" name="sign_id"
							value="${cfg_sign_integral.var_id}">
						<td class="td_left">每天签到奖励<span class="must_span">*</span></td>
						<td class="td_right_two"><input class="text validate"
							type="text" name="sign_value" isrequired="yes" tipmsg="积分" dataType="jsInt"
							maxlength="8" maxdatalength="8"
							value="${cfg_sign_integral.var_value}" /> 积分</td>
					</tr>
					<tr>
						<td class="td_left"><span style="font-size: 18px">分享送积分</span></td>
						<td></td>
					</tr>
					<tr>
						<input type="hidden" name="share_id"
							value="${cfg_share_integral.var_id}">
						<td class="td_left">分享商品页面到第三方平台（如微信、微博）<span
							class="must_span">*</span></td>
						<td class="td_right_two"><input class="text validate"
							type="text" name="share_value" isrequired="yes" tipmsg="积分" dataType="jsInt"
							maxlength="8" maxdatalength="8"
							value="${cfg_share_integral.var_value}" /> 积分</td>
					</tr>
				</table>
			</div>
			<div class="row50 operbtndiv">
				<input type="button" value="保存" class="btn operbtn"
					onclick="submitData();" />
				<%--<input type="button" class="btn return" onclick="returnGo('/admin/sysconfig/integral')" value="返回列表"/>--%>
			</div>
		</div>
	</form>
</body>
</html>

