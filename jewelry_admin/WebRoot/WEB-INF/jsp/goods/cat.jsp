<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<html>
<head>
<title>选择类目</title>
<%@ include file="/WEB-INF/common/img_control.jsp"%>
<%@ include file="/WEB-INF/common/ueditor.jsp"%>
	<style>
		.opercontent select{
			border: 1px solid #ddd;
		}

		.opercontent option{
			padding: 5px;

		}
	</style>
</head>
<body>
	<form id="validateForm" action="/admin/goods/addGoodsJsp" method="post">
		<div class="opercontent">
			<table style="height: 500px; margin: 15px 5px;">
				<tr style="height: 500px;width: 100%;">
					<td><select size="5" id="pid" name="pid"
						style="height: 500px; width: 300px;overflow: auto;margin-right: 15px">
							<c:forEach items="${catList}" var="catList">
								<option value="${catList.cat_id}">${catList.cat_name}</option>
							</c:forEach>
					</select></td>
					<td>
						<div id="idIIDiv">
							<select size="5" id="idII" name="pid"
								style="height: 500px; width: 300px;overflow: auto;margin-right: 15px">
							</select>
						</div>
					</td>
					<td>
						<div id="idIIIDiv">
							<select size="5" name="pId" id="idIII"
								style="height: 500px;  width: 300px;overflow: auto;margin-right: 80px;margin-right: 40px">
								<%--<c:forEach items="${catList}" var="catList">--%>
								<%--<option value="${catList.cat_id}">${catList.cat_name}</option>--%>
								<%--</c:forEach>--%>
							</select>
						</div>
					</td>
				</tr>
				<tr>
					<td style="padding-top:10px;">
						<b>您当前的选择是:</b>
						<font id="pFont"></font>
						<font id="fontII"></font>
						<font id="fontIII"></font>
					</td>
				</tr>
			</table>
			<div class="row50 operbtndiv">
				<input type="button" value="开始发布商品" class="btn operbtn"
					onclick="submitData();" />
			</div>
		</div>
		<input type="hidden" id="catId" name="catId" /> 
		<input type="hidden" id="catName" name="catName" />
	</form>
	<script>
    $('#pid').click(function () {
//        $(this).children().css("background-color","#FF0000");
//        $(this).find("option:selected").css("background-color","#000");
        $('#idIII').html('');
        var pid = $('#pid').val();
        $.ajax({
            type : "post",
            dataType : "json",
            async : false,
            url: "/admin/goods/getCatByPid",
            data:{pid:pid},
            success: function (data) {
                var html='';
                for (var i=0;i<data.length;i++){
                    html+='<option value="'+data[i].cat_id+'">'+data[i].cat_name+'</option>'
                }
            	$("#idII").html(html)
                $("#pFont").html($("#pid").find("option:selected").text()+" > ")
                $("#fontII").html('');
                $("#fontIII").html('');

                $("input[name='catId']").val("");
            },
            error: function (error) {
            	alert("请选择类目");
            }
        });
    })
    
    $('#idII').click(function () {
        var pid = $('#idII').val();
        $.ajax({
            type : "post",
            dataType : "json",
            async : false,
            url: "/admin/goods/getCatByPid",
            data:{pid:pid},
            success: function (data) {
                var html='';
                for (var i=0;i<data.length;i++){
                    html+='<option value="'+data[i].cat_id+'">'+data[i].cat_name+'</option>'
                }
                $("input[name='catId']").val("");
                $("#idIII").html(html)
                $("#fontII").html($("#idII").find("option:selected").text()+" > ")
                $("#fontIII").html('');
            },
            error: function (error) {
            	 alert("请选择类目");
            }
        });
    })
    
    $('#idIII').click(function () {
        var pid = $('#idIII').val();
        $("input[name='catId']").val(pid);
        var catName=$("#pid").find("option:selected").text()+" > "+$("#idII").find("option:selected").text()+" > "+$("#idIII").find("option:selected").text();
        $("input[name='catName']").val(catName);
        $("#fontIII").html($("#idIII").find("option:selected").text())
    })
    
    function submitData(){
        var catId = $("input[name='catId']").val();
        var catName = $("input[name='catName']").val();
        if (catId!=''&&catId!=null){
            $("#catId").val(catId);
            $("#catName").val(catName);
            $("#validateForm").submit();
        }else{
            alert("请选择类目");
        }
    }
</script>
</body>
</html>


