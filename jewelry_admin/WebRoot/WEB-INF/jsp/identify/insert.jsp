<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<meta charset="UTF-8">
<title>新增测试</title>
<% request.setCharacterEncoding("UTF-8"); %>
<script type="text/javascript">
    	$(function() {
    		//$("#iden_province").append("<option value='"+0+"'>"+"请选择"+"</option>");
    		//$("#iden_city").append("<option value='"+0+"'>"+"请选择"+"</option>");
    		//$("#iden_county").append("<option value='"+0+"'>"+"请选择"+"</option>");
    		getParent("1111111111",CBProvince);
    	});
    	
    	function proviceChange() {
    		$("#iden_city").empty();
    		$("#iden_county").empty();
    		var param = $("#iden_province").val();
    		if (param!=null && param!='0')
    			getParent(param,CBCity);
    	}
    	function cityChange() {
    		$("#iden_county").empty();
    		var param = $("#iden_city").val();
    		if (param!=null && param!='0')
    			getParent(param,CBCount);
    	}
    	
    	
    	function CBProvince(info) {
    		$("#iden_province").empty();
    		$("#iden_province").append("<option value='"+0+"'>"+"请选择"+"</option>");
    		for (var i=0; i < info.length; i++) {
    			$("#iden_province").append("<option value='"+info[i].area_id+"'>"+info[i].area_name+"</option>");
    		}
    	}
    	function CBCity(info) {
    		$("#iden_city").empty();
    		$("#iden_city").append("<option value='"+0+"'>"+"请选择"+"</option>");
    		for (var i=0; i < info.length; i++) {
    			$("#iden_city").append("<option value='"+info[i].area_id+"'>"+info[i].area_name+"</option>");
    		}
    	}
    	function CBCount(info) {
    		$("#iden_county").empty();
    		$("#iden_county").append("<option value='"+0+"'>"+"请选择"+"</option>");
    		for (var i=0; i < info.length; i++) {
    			$("#iden_county").append("<option value='"+info[i].area_id+"'>"+info[i].area_name+"</option>");
    		}
    	}
    	
    	function getParent(param,cbOk) {
    		$.ajax({
    			data:{"parent_area_id":param},
    			type:"POST",
    			dataType:"json",
    			url:"/admin/identify/getparentarea",
    			error:function(data){
    				alert("异常");
    			},
    			success:function(data) {
    				var code = data.code;
    				var desc = data.desc;
    				if (code!=0) {
    					alert(code+":"+desc);	//回调
    					return;
    				}
    				
    				var info = data.info;
    				cbOk(info);	//回调
    			}
    		});
    	}
    </script>
</head>
<body>
	<form id="validateForm" action="/admin/identify/insert" method="post">
		<div class="opercontent">
			<div class="table_div">
				<table width="100%">
					<tr>
						<td class="td_left"><span class="must_span">*</span>鉴定机构编号<span
							class="sp_span">:</span></td>
						<td class="td_right_two"><input class="text validate"
							type="text" name="iden_number" isrequired="yes" tipmsg="鉴定机构编号"
							maxlength='32' maxdatalength='32' /></td>
					</tr>
					<tr>
						<td class="td_left"><span class="must_span">*</span>鉴定机构名称<span
							class="sp_span">:</span></td>
						<td class="td_right_two"><input class="text validate "
							type="text" name="iden_name" isrequired="yes" tipmsg="鉴定机构名称"
							maxlength='32' maxdatalength='32' /></td>
					</tr>
					<tr>
						<td class="td_left"><span class="must_span">*</span>归属地<span
							class="sp_span">:</span></td>
						<td><select type="select" class="validate"
							name="iden_province" id="iden_province" isrequired="yes"
							tipmsg="省份" style="width:100px" onchange="proviceChange(this);"></select>
							<select type="select" class="validate" name="iden_city"
							id="iden_city" isrequired="yes" tipmsg="城市" style="width:120px"
							onchange="cityChange(this);"></select> <select type="select"
							class="validate" name="iden_county" id="iden_county"
							isrequired="yes" tipmsg="区县" style="width:120px"></select></td>
					</tr>
					<tr>
						<td class="td_left"><span class="must_span">*</span>联系人<span
							class="sp_span">:</span></td>
						<td class="td_right_two"><input class="text validate "
							type="text" name="iden_contacts" isrequired="yes" tipmsg="联系人"
							maxlength='64' maxdatalength='64' /></td>
					</tr>
					<tr>
						<td class="td_left"><span class="must_span">*</span>联系方式<span
							class="sp_span">:</span></td>
						<td class="td_right_two"><input class="text validate "
							type="text" name="iden_contacts_way" isrequired="yes"
							tipmsg="联系方式" maxlength='20' maxdatalength='20' /></td>
					</tr>
					<tr>
						<td class="td_left">排序<span class="sp_span">:</span></td>
						<td class="td_right_two"><input class="text validate "
							type="text" name="sort" tipmsg="排序" maxlength='11'
							maxdatalength='11' /></td>
					</tr>
					<tr>
						<td class="td_left"><span class="must_span">*</span>是否启用<span
							class="sp_span">:</span></td>
						<td class="td_right_two"><select type="select"
							class="validate" name="status" tipmsg="状态">
								<option value="1">启用</option>
								<option value="0">禁用</option>
						</select></td>
					</tr>
					<c:if test="${identify.iden_id!=null}">
						<input type="hidden" name="iden_id" value="${identify.iden_id}" />
					</c:if>

					<%@ include file="/WEB-INF/common/search_hidden_field.jsp"%>
				</table>
			</div>
			<div class="row50 operbtndiv">
				<input type="button" value="新增测试" class="btn operbtn"
					onclick="submitData();" /> <input type="button" class="btn return"
					onclick="returnGo('/admin/identify/list')" value="返回列表" />
			</div>
		</div>
	</form>
</body>
</html>

