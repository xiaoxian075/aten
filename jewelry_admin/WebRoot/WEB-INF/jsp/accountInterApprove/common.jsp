<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ include file="/WEB-INF/common/img_control.jsp"%>
<script type="text/javascript">
	function search() {
		var login_name = $("#login_name").val();
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
		$("#login_name").val("");
		$("#msg").html("");
	}
	
	
	function cbOk(info) {
		$("#msg").html("会员"+info.login_name+"，当前积分"+info.integral+"。");
	}
	
</script>
<div class="table_div">
	<table width="100%">
		<tr>
			<td class="td_left">会员账户<span class="must_span">*</span></td>
			<td class="td_right_two"><input class="text validate"
				isrequired="yes" datatype="jsInt" tipmsg="会员账户" type="text"
				id="login_name" name="login_name" maxlength='15' /> <input
				class="btn ol_colorbtn ol_greenbtn" type="button" value="搜索"
				id="mysearch" onclick="search();" /> <input
				class="btn ol_colorbtn ol_bredbtn" type="button" id="myclear"
				value="清空" onclick="clearSearch();" /></td>
		</tr>
		<tr></tr>
		<tr>
			<td class="td_left"></td>
			<td class="td_right_two"><span id="msg"></span></td>
		</tr>
		<tr>
			<td class="td_left">增减类型<span class="must_span">*</span></td>
			<td class="td_right" colspan="3"><select name="io_type" class="validate" widthtip="100" isrequired="yes" type="select" tipmsg="增减类型">
					<option value="" selected>请选择</option>
					<option value="2">增加</option>
					<option value="1">减少</option>
			</select></td>
		</tr>
		<tr>
			<td class="td_left">积分值<span class="must_span">*</span></td>
			<td class="td_right_two"><input class="text validate"
				type="text" name="inter_value" isrequired="yes" datatype="jsInt"
				tipmsg="积分值" maxlength='6' maxdatalength='6' value="" /></td>
		</tr>
		<tr>
			<td class="td_left">备注<span class="sp_span">:</span></td>
			<td class="td_right" colspan="3"><textarea class="validate"
					name="submitter_note" rows="3" cols="40" type="text"
					maxlength='50' maxdatalength='100'></textarea></td>
		</tr>
		<%@ include file="/WEB-INF/common/search_hidden_field.jsp"%>
	</table>
</div>
