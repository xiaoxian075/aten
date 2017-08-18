<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<title>修改测试</title>
<script type="text/javascript">
    	$(function() {
    		getParent("1111111111",CBProvince);
    		//地区级联
            /*$("#area_id_div").cascadeSel({
                url:"/admin/area/getList",
                name:"area_id",
                init_id:"${cfg_top_area}",
                li_id:"area_id",
                li_name:"area_name",
            });*/
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
    	function countyChange() {
    		var provice = $("#iden_province").find("option:selected").text();
    		var city = $("#iden_city").find("option:selected").text();
    		var county = $("#iden_county").find("option:selected").text();
    		var str = provice + city + county;
    		$("#label_area").html(str);
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
	<form id="validateForm" action="/admin/identify/update" method="post">
		<div class="opercontent">
			<div class="table_div">
				<table width="100%">
					<tr>
						<td class="td_left"><span class="must_span">*</span>鉴定机构编号<span
							class="sp_span">:</span></td>
						<td class="td_right_two"><input class="text validate"
							type="text" name="iden_number" isrequired="yes" tipmsg="鉴定机构编号"
							maxlength='32' maxdatalength='32' value="${identify.iden_number}" />
						</td>
					</tr>
					<tr>
						<td class="td_left"><span class="must_span">*</span>鉴定机构名称<span
							class="sp_span">:</span></td>
						<td class="td_right_two"><input class="text validate "
							type="text" name="iden_name" isrequired="yes" tipmsg="鉴定机构名称"
							maxlength='32' maxdatalength='32' value="${identify.iden_name}" />
						</td>
					</tr>
					<tr>

						<td class="td_left"><span class="must_span">*</span>归属地<span
							class="sp_span">:</span></td>
						<td><label id="label_area">${identify.iden_province}${identify.iden_city}${identify.iden_county}</label>
							<select type="select" class="validate" name="iden_province"
							id="iden_province" tipmsg="省份" style="width:100px"
							onchange="proviceChange(this);"></select> <select type="select"
							class="validate" name="iden_city" id="iden_city" tipmsg="城市"
							style="width:120px" onchange="cityChange(this);"></select> <select
							type="select" class="validate" name="iden_county"
							id="iden_county" tipmsg="区县" style="width:120px"
							onchange="countyChange(this);"></select></td>
					</tr>
					<tr>
						<td class="td_left"><span class="must_span">*</span>联系人<span
							class="sp_span">:</span></td>
						<td class="td_right_two"><input class="text validate "
							type="text" name="iden_contacts" isrequired="yes" tipmsg="联系人"
							maxlength='64' maxdatalength='64'
							value="${identify.iden_contacts}" /></td>
					</tr>
					<tr>
						<td class="td_left"><span class="must_span">*</span>联系方式<span
							class="sp_span">:</span></td>
						<td class="td_right_two"><input class="text validate "
							type="text" name="iden_contacts_way" isrequired="yes"
							tipmsg="联系方式" maxlength='20' maxdatalength='20'
							value="${identify.iden_contacts_way}" /></td>
					</tr>
					<tr>
						<td class="td_left">排序<span class="sp_span">:</span></td>
						<td class="td_right_two"><input class="text validate "
							type="text" name="sort" tipmsg="排序" maxlength='11'
							maxdatalength='11' value="${identify.sort}" /></td>
					</tr>
					<tr>
						<td class="td_left"><span class="must_span">*</span>是否启用<span
							class="sp_span">:</span></td>
						<td class="td_right_two"><select type="select"
							class="validate" id="status" tipmsg="状态">
								<c:choose>
									<c:when test="${identify.status==1}">
										<option selected="selected" value="1">启用</option>
									</c:when>
									<c:otherwise>
										<option value="1">启用</option>
									</c:otherwise>
								</c:choose>
								<c:choose>
									<c:when test="${identify.status==0}">
										<option selected="selected" value="0">禁用</option>
									</c:when>
									<c:otherwise>
										<option value="0">禁用</option>
									</c:otherwise>
								</c:choose>
						</select></td>
					</tr>
					<c:if test="${identify.iden_id!=null}">
						<input type="hidden" name="iden_id" value="${identify.iden_id}" />
					</c:if>

					<%@ include file="/WEB-INF/common/search_hidden_field.jsp"%>
				</table>
			</div>
			<div class="row50 operbtndiv">
				<input type="button" value="修改鉴定机构" class="btn operbtn"
					onclick="submitData();" /> <input type="button" class="btn return"
					onclick="returnGo('/admin/identify/list')" value="返回列表" />
			</div>
		</div>
	</form>
</body>
</html>

