<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
<head>
<title>Title</title>
<%@ include file="/component/lay/pageGrid/resource.jsp"%>
<style type="text/css">
#mydb {
	border: none;
	font-size: 16px;
	text-align: left;
	margin-top: 30px;
	margin-left: 40px;
	margin-right: 40px;
	margin-bottom: 30px;
}

#mydb tr td {
	height: 40px;
}

#mytitle {
	border: none;
	font-size: 20px;
	text-align: left;
	margin-top: 30px;
	margin-left: 40px;
	margin-right: 40px;
	margin-bottom: 30px;
}
</style>
</head>
<body>
	<input type=hidden id="order_number" value="${order_number}">
	<div>
		<table id="mytitle"></table>
		<table id="mydb"></table>
	</div>
</body>

<script type="text/javascript">
	$(function() {
		var order_number = $("#order_number").val();
		$.ajax({
			url: "/admin/order/lookupship",
			data: {"order_number":order_number},
			type: "POST",
			success: function (data) {
				//debugger;
				var data = JSON.parse(data);
				$("#mytitle").empty();
				$("#mydb").empty();
				if (data.code == 0){
					$("#mytitle").append(
							"<tr>"+
								"<td width='1000px'>"+data.info.fast_name+':'+data.info.fast_waybill+"</td>"+
							"</tr>");
					var info = data.info.arrLogistics;
					for (var i=0; i < info.length; i++) {
						var context = info[i].context;
						var time = info[i].time;
						$("#mydb").append(
								"<tr>"+
									"<td width='800px'>"+info[i].context+"</td>"+
									"<td width='200px'>"+info[i].time+"</td>"+
								"</tr>");
					}
					$('#mylookupship').modal('show');
				}else{
					art.dialog({
	    			    title: '提示',
	    			    content: info.desc,
	    			    time:2000
	    			});
				}
				
			},
			error: function () {
				art.dialog({
    			    title: '提示',
    			    content: "请求失败，请检查网络",
    			    time:2000
    			});
			}
		}); 
	});
</script>
</html>
