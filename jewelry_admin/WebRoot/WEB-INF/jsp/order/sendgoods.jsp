<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
<head>
<title>Title</title>
<style>
.goodsh {
	border: none;
	font-size: 16px;
	text-align: center;
	margin: 30px 50px;

}

.goodsh tr td {
	height: 60px;
	text-align: left;
	padding-right: 10px;

}
.clearBlueBtn{
	background-color: #337AB7;
	border: 1px solid #797979;
	color: #fff;
	width: 56px;
	border-radius: 2px;
	height: 29px;
	font-size: 15px;
    margin-right: 15px;
}
	.subitGrayBtn{
		background-color: #e6e6e6;
		border: 1px solid #ddd;
		color: #333;
		width: 56px;
		border-radius: 2px;
		height: 29px;
		font-size: 15px;
	}
	
	select {  
        position:absolute;
        left:0px;
        top:0px;
        width:100%;
        height:30px;
        opacity:0;
		font-size: 15px;
    }
#fast_code{
	padding: 3px;
    padding-bottom: 7px;
    font-size: 15px;
}
	#mycli,#mycli2 {
	    padding:3px 12px;
	  /*background:#04B5AF;*/
	    /*color:#fff;*/
	    border-radius:3px;
	    box-shadow:0 1px 1px #ddd;
	    cursor:pointer;border:0px;
	    border-bottom-style:none;
	    border-top-style:none;
	    border-left-style:none;
	    border-right-style:none;
	}
}
</style>
<%@ include file="/component/lay/pageGrid/resource.jsp"%>
</head>
<body>
	<div>
		<form id="myForm" action="#">
			<input type=hidden id="order_id" value="${order_id}">
			<table class="goodsh">
				<tr>
					<td width="100px" style="text-align:right;">物流承运商:</td>
					<td width="100px"><select id="fast_code" ></select></td>
				</tr>
				<tr>
					<td width="100px" style="text-align:right;">物流单号:</td>
					<td width="100px">
					<input type="text" id="fast_waybill"
						maxlength="31"  style="width:200px;height: 30px"/></td>
				</tr>
				<tr>
					<td width="100px" colspan="2" style="text-align: center" >
						<input id="mycli" type="button" value="确定" onclick="myClick(this)" class=clearBlueBtn />
						<input id="mycli2" type="reset" value="清空" class="subitGrayBtn" />
					</td>
				</tr>
			</table>
		</form>
	</div>
</body>

<script type="text/javascript">
	$(function() {
		$.ajax({
			url: "/admin/fastmail/getall",
			data: {},
			type: "POST",
			success: function (data) {
				data = JSON.parse(data)
				if (data.code == 0){
					var info = data.info;
					for (var i=0; i < info.length; i++) {
						 $("#fast_code").append("<option value='"+info[i].fast_code+"'>"+info[i].fast_name+"</option>");
					}
				}else{
					
				}
			},
			error: function () {
				alert('请求失败，请检查网络', function(index){});
			}
		});
	});
	function myClick(obj) {
		var order_id = $('#order_id').val();
  		var fast_code = $('#fast_code').val();
  		var fast_waybill = $('#fast_waybill').val();
  		
  		if (fast_waybill=='') {
  			alert("物流单号不能为空", function(index){});
  			return;
  		}
  		var data = {
  				"order_id":order_id,
  				"fast_code":fast_code,
  				"fast_waybill":fast_waybill
  		}
		$.ajax({
			url: "/admin/order/sendgoods",
			data: data,
			type: "POST",
			success: function (data) {
				var info = JSON.parse(data)
				if (info.code == 0){
					  alert(info.desc, function(index){
                          //刷新父级页面
                          parent.location.reload();
                      });
				}else{
					alert(info.desc, function(index){
                        //刷新父级页面
                        parent.location.reload();
                    });
				}
				
			},
			error: function () {
				alert('请求失败，请检查网络', function(index){
                    //刷新父级页面
                    parent.location.reload();
                });
				$("#mycli").attr("disabled", false);
			}
		});
  		$("#mycli").attr("disabled", true); 
	}
</script>
</html>

