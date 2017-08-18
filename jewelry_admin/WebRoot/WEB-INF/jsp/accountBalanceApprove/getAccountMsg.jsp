<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script src='/include/common/js/jquery-3.2.1.min.js'
	type='text/javascript'></script>
<script type="text/javascript">
	function search() {
		var login_name = $("#search_login_name").val();
		if (login_name==null || login_name=='') {
			art.dialog({
			    title: '友情提示',
			    content: "请输入会员账号",
			    time:2000
			});
			return;
		}
		$.ajax({
   			data:{"login_name":login_name},
   			type:"POST",
   			dataType:"json",
   			url:"/admin/user/get",
   			error:function(data){
   			},
   			success:function(data) {
   				var code = data.code;
   				var desc = data.desc;
   				if (code!=0) {
   					return;
   				}
   				var info = data.info;
   				if (info==null){
   					art.dialog({
					    title: '友情提示',
					    content: "所查询的会员不存在",
					    time:2000
					});
   				}else{
   					cbOk(info);	//回调
   				}
   			}
   		});
	}
	
	function clearSearch() {
		$("#search_login_name").val("");
	}
	
	function cbOk(info) {
		clean();
		$("#login_name").html(info.login_name);
		$("#nick_name").html(info.nick_name);
		if (info.sex==1)
			$("#sex").html("男");
		else if (info.sex==2) 
			$("#sex").html("女");
		else if(info.sex==3){
			$("#sex").html("保密");
		}
		$("#birthday").html(info.birthday);
		if(info.lev==1){
			$("#lev").html("普通会员");
		}else if(info.lev==2){
			$("#lev").html("vip会员");
		}
		$("#balance").html("￥" + (info.balance*0.01).toFixed(2));
		
		$("#recharge").show();
		$("#deduction").show();
		
		$("#recharge").click(function(){
			editInfo('/admin/accountBalanceApprove/recharge',info.login_name);
		});
		
		$("#deduction").click(function(){
			editInfo('/admin/accountBalanceApprove/deduction',info.login_name);
		});
	}
	
	function clean() {
		$("#login_name").html("");
		$("#nick_name").html("");
		$("#sex").html("");
		$("#birthday").html("");
		$("#lev").html("");
		$("#balance").html("");
	}
</script>
	
<style>
	.accountmsg tr td {
		padding:10px;
	}
</style>

</head>
<body>
	<form action="" method="post">
		<div class="searchDiv">
			<div class="table_div">
				<table width="100%">
					<tr>
						<td class="td_left"><label>会员账户<span class="sp_span">:</span></label></td>
						<td class="td_right_two"><input class="text validate"
							type="text" id="search_login_name" maxlength='32' /> <input
							class="btn ol_colorbtn ol_greenbtn" type="button" value="搜索"
							id="mysearch" onclick="search();" /> <input
							class="btn ol_colorbtn ol_bredbtn" type="button" id="myclear"
							value="清空" onclick="clearSearch();" /></td>
					</tr>
					<tr>
						<td class="td_left"></td>
						<td class="td_right_two">
							<table class="accountmsg">
								<tr>
									<td align="right">会员账户<span class="sp_span">:</span></td>
									<td align="left"><label id="login_name"></label></td>
								</tr>
								<tr></tr>
								<tr>
									<td align="right">昵称<span class="sp_span">:</span></td>
									<td align="left"><label id="nick_name"></label></td>
								</tr>
								<tr></tr>
								<tr>
									<td align="right">性别<span class="sp_span">:</span></td>
									<td align="left"><label id="sex"></label></td>
								</tr>
								<tr></tr>
								<tr>
									<td align="right">生日<span class="sp_span">:</span></td>
									<td align="left"><label id="birthday"></label></td>
								</tr>
								<tr></tr>
								<tr>
									<td align="right">会员等级<span class="sp_span">:</span></td>
									<td align="left"><label id="lev"></label></td>
								</tr>
								<tr></tr>
								<tr>
									<td align="right">余额<span class="sp_span">:</span></td>
									<td align="left"><label id="balance"></label> <input
										class="btn ol_colorbtn ol_bluebtn" type="button" value="充值"
										id="recharge" style="display:none"/> <input class="btn ol_colorbtn ol_bluebtn"
										type="button" value="扣减" id="deduction" style="display:none"/></td>
								</tr>
								<tr></tr>
							</table>
						</td>
					</tr>
				</table>
			</div>
		</div>
		<%@ include file="/WEB-INF/common/list_hidden_value.jsp"%>
	</form>
</body>
</html>