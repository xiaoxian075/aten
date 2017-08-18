<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<html>
<head>
<title>订单详情</title>
<style>
.mytablesubmit {
	font-size: 24px;
	text-align: center;
	margin-bottom: 60px;
}

.mytable {
	/*border:1px solid #dddddd;*/
	font-size: 14px;
	text-align: center;
	margin-top: 15px;
	margin-left: 15px;
	/*margin-right: 40px;*/
	margin-bottom: 60px;
	width: 100%;
}
.mytable .payTitle{
	width: 200px;
	text-align: right;
	display: inline-block;
	margin-bottom: 10px;
}

.mytable tr {
	/*border:1px solid #dddddd;*/
	
}

.mytable tr td {
	border: 1px solid #dddddd;
	/*height: 38px;*/
}
.mytable th{
	height: 30px;
	line-height: 30px;
	border: solid 1px #ddd;
	background-color: #f5fafe;
}
.orderInforCDontent{
	display: inline-block;
	width: 82%;
	vertical-align: top;
	margin-bottom: 8px;
}
.goodimg {
	width: 150px;
	height: 40px;
	cursor: pointer;
}
.wuliuTable{
  width: 100%;
}
.wuliuTable tr td{
   /*padding-right: 10px;*/
   border: 0px;
   padding-bottom: 10px;
   height: 20px; 
}
.wuliuTable div{
	height: 100%;
}
</style>

	<script type="text/javascript">
	$(function() {
		var order_number = $("#order_number").html();
		$.ajax({
			url: "/admin/order/lookupship",
			data: {"order_number":order_number},
			type: "POST",
			success: function (data) {
				//debugger;
				var data = JSON.parse(data);
				//$("#mytitle").empty();
				$("#mydb").empty();
				if (data.code == 0){
/* 					$("#mytitle").append(
							"<tr>"+
								"<td width='1000px'>"+data.info.fast_name+':'+data.info.fast_waybill+"</td>"+
							"</tr>"); */
					var info = data.info.arrLogistics;
					for (var i=0; i < info.length; i++) {
						var context = info[i].context;
						var time = info[i].time;
						$("#mydb").append(
								"<tr>"+
									"<td style='width: 175px;'><div style='height: 100%;padding-left: 30px'>"+info[i].time+"</div></td>"+
									"<td><div style='height: 100%'>>"+info[i].context+"</div></td>"+
								"</tr>");
					}
				}else {
					$("#mydb").append(
							"<tr>"+
								"<td style='width: 175px;'><div style='height: 100%;padding-left: 30px'>"+data.desc+"</div></td>"+
							"</tr>");
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

</head>
<body>

    <table class="mytable">
		<tr>
			<th width="500px" align="left" style="padding:0px 10px">订单信息</th>
			<td rowspan="2" style="text-align: left;padding: 10px">
			    <div style="font-size: 15px;margin-bottom: 10px">物流信息：</div>
			    <table id="mydb" border="0" class="wuliuTable" cellpadding="0">
			      <!--  <tr>
			    	 <td style=" width: 175px;">
			    	      <div style="height: 100%;padding-left: 30px">2017-07-27 16:43:46<div>
			    	 </td>
			    	 <td>
			    	     <div style="height: 100%">客户签收人: 已签收，签收人凭取货码签收已签收感谢使用圆通速递，期待再次为您服务</div>
			    	 </td>
			       </tr>
			       <tr>
			    	 <td style=" width: 175px;">
			    	     <div style="height: 100%;padding-left: 30px">2017-07-27 16:43:46</div>
			    	 </td>
			    	 <td>
			    	    <div style="height: 100%">客户签收人: 已签收，签收人凭取货码签收已签收感谢使用圆通速递，期待再次为您服务</div>
			    	 </td>
			       </tr>
			       <tr>
			    	 <td style=" width: 175px;">
			    	    <div style="height: 100%;padding-left: 30px">2017-07-27 16:43:46</div>
			    	 </td>
			    	 <td>
			    	     <div style="height: 100%;">客户签收人: 已签收，签收人凭取货码签收已签收感谢使用圆通速递，期待再次为您服务待再次为您服务期待再次为您服务客户 </div>
			    	 </td>
			       </tr>
 -->
			    </table>
			</td>
		</tr>
		<tr>
			<td  style="text-align:left;padding: 10px">

				<sapn style="text-align: left; display: inline-block;height: 100%;width:100%">
					<div class="payTitle" style="width:85px;" > 订单号： </div><span id="order_number" class="orderInforCDontent">${orderVo.order.order_number}</span><br>
					<div class="payTitle" style="width:85px;" > 会员账号： </div><span class="orderInforCDontent">${orderVo.order.account_name}</span><br>
					<div class="payTitle" style="width:85px;">订单类型：</div>
					<span class="orderInforCDontent">
					    <c:if test="${orderVo.order.order_type==1}">正常订单</c:if>
				        <c:if test="${orderVo.order.order_type==2}">全额预售订单</c:if>
			        	<c:if test="${orderVo.order.order_type==3}">订金支付订单</c:if>
			     	</span><br>					
			     	<div class="payTitle" style="width:85px;">订单状态：</div>
						<span class="orderInforCDontent">	
							<c:if test="${orderVo.order.order_state==0}">未付款</c:if>	
							<c:if test="${orderVo.order.order_state==1}">等待卖家发货</c:if>	
							<c:if test="${orderVo.order.order_state==2}">等待买家确认收货</c:if>	
							<c:if test="${orderVo.order.order_state==3}">交易成功</c:if>	
							<c:if test="${orderVo.order.order_state==5}">交易关闭</c:if>	
							<c:if test="${orderVo.order.order_state==6}">末付款前交易关闭</c:if>
						</span><br>
					<c:if test="${!empty orderVo.order.pay_time}">
						<div class="payTitle" style="width:85px;">支付方式：</div><span class="orderInforCDontent">${orderVo.order.payway_name}</span>
					</c:if>
					<div class="payTitle" style="width:85px;" > 订单总金额：</div>
					<span class="orderInforCDontent"><fmt:formatNumber value="${orderVo.order.pay_amount*0.01}" pattern="##.##" minFractionDigits="2" />元</span><br>
					
					<div class="payTitle" style="width:85px;" > 订单时间：</div><span class="orderInforCDontent">${fn:substring(orderVo.order.create_time, 0, 19)}</span><br>
					<c:if test="${!empty orderVo.order.pay_time}">
						<div class="payTitle" style="width:85px;" > 付款时间：</div><span class="orderInforCDontent">${fn:substring(orderVo.order.pay_time, 0, 19)}</span><br>
					</c:if>
					<c:if test="${!empty orderVo.order.send_time}">
						<div class="payTitle" style="width:85px;" > 发货时间：</div><span class="orderInforCDontent">${fn:substring(orderVo.order.send_time, 0, 19)}</span><br>
					</c:if>
					
					
					<c:if test="${orderVo.order.order_type==3}">
					    <div class="payTitle" style="width:85px;">阶段1：</div>
					     <span class="orderInforCDontent">
							 支付定金:<fmt:formatNumber  value="${orderVo.depositbook.deposit_amount*0.01}" pattern="##.##" minFractionDigits="2" />元
							 &nbsp;&nbsp;<c:if test="${orderVo.depositbook.pay_state==0}">末付订金</c:if>
							 &nbsp;&nbsp;<c:if test="${orderVo.depositbook.pay_state>0}">已付订金</c:if>
							 支付时间： ${fn:substring(orderVo.depositbook.deposit_pay_time, 0, 19)}
				        </span>

						<div class="payTitle" style="width:85px;">阶段2：</div>
						<span class="orderInforCDontent">
							 支付尾款:<fmt:formatNumber value="${orderVo.depositbook.retainage*0.01}" pattern="##.##" minFractionDigits="2" />元
							 &nbsp;&nbsp;<c:if test="${orderVo.depositbook.pay_state<=1}">末付尾款</c:if>
							 &nbsp;&nbsp;<c:if test="${orderVo.depositbook.pay_state==2}">已付尾款</c:if>
							 截止时间：${fn:substring(orderVo.depositbook.retainage_pay_end_time, 0, 19)}
				        </span>

					</c:if>

				</sapn>
			</td>
		</tr>
	</table>

	<table class="mytable">
		<tr>
			<td colspan="4" style="border:0px;text-align:left;font-size:20px;">商品信息</td>
		</tr>
		<tr>
			<th width="300px" colspan="2">商品名称</th>
			<th width="300px">状态</th>
			<th width="300px">数量</th>
			<th width="300px">单价(元)</th>
			<th width="300px">小计(元)</th>
		</tr>

		<c:forEach items="${orderVo.detail}" var="item" varStatus="status">
			<tr>
				<td width="80px" style="padding: 10px;border-right: 0px">
					<span style="display: inline-block;border: 1px solid #eee"><img class="img img_wh80 " src="${ossImgServerUrl}${item.goods_img}" /></span>
				</td>
				<td width="320px" style="border-left: 0px;text-align: left;padding: 10px"><div style="height: 100%">${item.goods_name}</div><div>规格：${item.sku_name}</div></td>
				<td width="280px">
					<c:if test="${item.state==0}">未付款</c:if>
					<c:if test="${item.state==1}">等待卖家发货</c:if>
					<c:if test="${item.state==2}">等待买家确认收货</c:if>
					<c:if test="${item.state==3}">交易成功</c:if>
					<c:if test="${item.state==4}">退款中</c:if>
					<c:if test="${item.state==5}">退款成功</c:if>
					<c:if test="${item.state==6}">未付款前关闭交易 </c:if>
					<c:if test="${item.state==7}">退货退款中</c:if>
					<c:if test="${item.state==8}">退货退款成功</c:if>
					<c:if test="${item.state==9}">售后中</c:if>
					<c:if test="${item.state==10}">售后成功</c:if>
				</td>
				<td width="200px">${item.num}</td>
				<td width="300px"><fmt:formatNumber value="${item.sale_price*0.01}" pattern="##.##" minFractionDigits="2" /></td>
				<td width="280px"><fmt:formatNumber value="${item.total_amount*0.01}" pattern="##.##" minFractionDigits="2" /></td>
			</tr>
		</c:forEach>
		<tr>
			<td colspan="6" style="text-align:right;padding-top: 10px;border-bottom: 0px">
				<sapn style="text-align: left; display: inline-block;margin-right: 20px">
					<span style="margin-right: 20px">收货人姓名：${orderVo.express.consignee} </span>
					<span style="margin-right: 20px">联系电话：${orderVo.express.consignee_mobile}</span>
					<span>收货地址：${orderVo.express.consignee_address}</span>

				</sapn>
			</td>
		</tr>
		<tr>
			<td colspan="6" style="text-align:right;padding: 10px;border-top: 0px">
				<sapn style="text-align: left; display: inline-block;margin-right: 20px">
					<div class="payTitle">商品金额：</div><fmt:formatNumber value="${orderVo.order.pay_amount*0.01 - orderVo.order.trans_exp*0.01}" pattern="##.##" minFractionDigits="2" />元<br>
					<div class="payTitle">优惠金额：</div><fmt:formatNumber value="${orderVo.order.discount_amount*0.01}" pattern="##.##" minFractionDigits="2" />元<br>
					<div class="payTitle">运费：</div><fmt:formatNumber value="${orderVo.order.trans_exp*0.01}" pattern="##.##" minFractionDigits="2" />元<br>
					<div class="payTitle" style="font-weight:bold;">订单总金额：</div><span style="font-weight:bold;"><fmt:formatNumber value="${orderVo.order.pay_amount*0.01}" pattern="##.##" minFractionDigits="2" />元</span>
				</sapn>
			</td>
		</tr>

	</table>












	<!-- <table class="mytable">
		<tr>
			<td colspan="5" style="border:0px;text-align:left;font-size:20px;">订单基本信息</td>
		</tr>
		<tr>
			<th width="300px">订单号</th>
			<th width="300px">订单时间</th>
			<th width="300px">订单类型</th>
			<th width="300px">会员账号</th>
			<th width="300px">订单总金额（元）</th>
			<th width="300px">支付方式</th>
		</tr>
		<tr>
			<td>${orderVo.order.order_number}</td>
			<td>${fn:substring(orderVo.order.create_time, 0, 19)}</td>
			<td>
				<c:if test="${orderVo.order.order_type==1}">正常订单</c:if>
				<c:if test="${orderVo.order.order_type==2}">全额预售订单</c:if> 
				<c:if test="${orderVo.order.order_type==3}">订金支付订单</c:if> 
<%-- 				<c:if test="${orderVo.order.order_type==4}">积分订单</c:if> --%>
			</td>
			<td>${orderVo.order.account_name}</td>
			<td><fmt:formatNumber value="${orderVo.order.pay_amount*0.01}"
					pattern="##.##" minFractionDigits="2" /></td>
			<td>支付方式</td>
		</tr>

		<c:if test="${orderVo.order.order_type==3}">
			<tr>
				<td width="1250px" colspan="6" style="text-align:left;padding-left: 20px"><span>
						阶段1：支付定金&nbsp;&nbsp;&nbsp;<fmt:formatNumber
							value="${orderVo.depositbook.deposit_amount*0.01}"
							pattern="##.##" minFractionDigits="2" />&nbsp;&nbsp;&nbsp;
				</span> <span> <c:if test="${orderVo.depositbook.pay_state==0}">末付订金</c:if>
						<c:if test="${orderVo.depositbook.pay_state>0}">已付订金</c:if>
				</span> <span> &nbsp;&nbsp;&nbsp;支付时间： </span> <span>
						${orderVo.depositbook.deposit_pay_time} </span> <br> <span>
						阶段2：支付尾款&nbsp;&nbsp;&nbsp;<fmt:formatNumber
							value="${orderVo.depositbook.retainage*0.01}" pattern="##.##"
							minFractionDigits="2" />&nbsp;&nbsp;&nbsp;
				</span> <span> <c:if test="${orderVo.depositbook.pay_state<=1}">末付尾款</c:if>
						<c:if test="${orderVo.depositbook.pay_state==2}">已付尾款</c:if>
				</span> <span> &nbsp;&nbsp;&nbsp;剩余时间： </span> <span>
						${orderVo.depositbook.retainage_pay_time} </span></td>
			</tr>
		</c:if>
	</table>


	<table class="mytable">
		<tr>
			<td colspan="4" style="border:0px;text-align:left;font-size:20px;">商品信息</td>
		</tr>
		<tr>
			<th width="300px" colspan="2">商品名称</th>
			<th width="300px">状态</th>
			<th width="300px">数量</th>

			<th width="300px">单价</th>

			<th width="300px">小计</th>
		</tr>

		<c:forEach items="${orderVo.detail}" var="item" varStatus="status">
			<tr>
				<td width="80px" style="padding: 10px;border-right: 0px">
					<span style="display: inline-block;border: 1px solid #eee"><img class="img img_wh80 " src="${ossImgServerUrl}${item.goods_img}" /></span>
				</td>
				<td width="320px" style="border-left: 0px;text-align: left;padding: 10px"><div style="height: 100%">${item.goods_name}</div></td>
				<td width="280px">状态</td>
				<td width="200px">${item.num}</td>
				<td width="300px"><fmt:formatNumber
						value="${item.sale_price*0.01}" pattern="##.##"
						minFractionDigits="2" /></td>

				<td width="280px"><fmt:formatNumber
						value="${item.total_amount*0.01}" pattern="##.##"
						minFractionDigits="2" /></td>
			</tr>
		</c:forEach>
		<tr>
			<td colspan="6" style="text-align:right;padding: 10px">
				<sapn style="text-align: left; display: inline-block;margin-right: 20px">
					<span style="margin-right: 20px">收货人姓名：${orderVo.express.consignee} </span>
					<span style="margin-right: 20px">联系电话：${orderVo.express.consignee_mobile}</span>
					<span>收货地址：${orderVo.express.consignee_address}</span>

				</sapn>
			</td>
		</tr>
		<tr>
			<td colspan="6" style="text-align:right;padding: 10px">
				<sapn style="text-align: left; display: inline-block;margin-right: 20px">
					<div class="payTitle">商品金额：</div><fmt:formatNumber value="${orderVo.order.order_amount*0.01}" pattern="##.##" minFractionDigits="2" /><br>
					<div class="payTitle">优惠金额：</div><fmt:formatNumber value="${orderVo.order.discount_amount*0.01}" pattern="##.##" minFractionDigits="2" /><br>
					<div class="payTitle">运费：</div><fmt:formatNumber value="${orderVo.order.trans_exp*0.01}" pattern="##.##" minFractionDigits="2" /><br>
					<div class="payTitle">订单总金额：</div><fmt:formatNumber value="${orderVo.order.pay_amount*0.01}" pattern="##.##" minFractionDigits="2" />
				</sapn>
			</td>
		</tr>

	</table>
 -->
	<%--<table class="mytable">--%>
		<%--<tr>--%>
			<%--<td colspan="2" style="border:0px;text-align:left;font-size:20px;">收货人信息</td>--%>
		<%--</tr>--%>
		<%--<tr>--%>
			<%--<td width="300px">收货人姓名</td>--%>
			<%--<td width="1200px">${orderVo.express.consignee}</td>--%>
		<%--</tr>--%>
		<%--<tr>--%>
			<%--<td width="300px">联系电话</td>--%>
			<%--<td width="950px">${orderVo.express.consignee_mobile}</td>--%>
		<%--</tr>--%>
		<%--<tr>--%>
			<%--<td width="300px">收货地址</td>--%>
			<%--<td width="950px">${orderVo.express.consignee_address}</td>--%>
		<%--</tr>--%>
	<%--</table>--%>

	<%--<table class="mytable">--%>
		<%--<tr>--%>
			<%--<td colspan="2" style="border:0px;text-align:left;font-size:20px;">发票信息</td>--%>
		<%--</tr>--%>
		<%--<tr>--%>
			<%--<td width="300px">开具类型</td>--%>
			<%--<td width="1200px"><c:if test="${orderVo.auxiliary!=null}">--%>
					<%--<c:if test="${orderVo.auxiliary.invoice_open_type==0}">普通增值税发票</c:if>--%>
					<%--<c:if test="${orderVo.auxiliary.invoice_open_type==1}">传用增值税发票</c:if>--%>
				<%--</c:if></td>--%>
		<%--</tr>--%>
		<%--<tr>--%>
			<%--<td width="300px">发票类型</td>--%>
			<%--<td width="950px"><c:if test="${orderVo.auxiliary!=null}">--%>
					<%--<c:if test="${orderVo.auxiliary.invoice_type==0}">个人</c:if>--%>
					<%--<c:if test="${orderVo.auxiliary.invoice_type==1}">企业</c:if>--%>
				<%--</c:if></td>--%>
		<%--</tr>--%>
		<%--<tr>--%>
			<%--<td width="300px">发票抬头</td>--%>
			<%--<td width="950px"><c:if test="${orderVo.auxiliary!=null}">--%>
	 				<%--${orderVo.auxiliary.invoice_header}--%>
	 			<%--</c:if></td>--%>
		<%--</tr>--%>
		<%--<tr>--%>
			<%--<td width="300px">电话号码</td>--%>
			<%--<td width="950px"><c:if test="${orderVo.auxiliary!=null}">--%>
	 				<%--${orderVo.auxiliary.invoice_phone}--%>
	 			<%--</c:if></td>--%>
		<%--</tr>--%>
		<%--<tr>--%>
			<%--<td width="300px">电子邮箱</td>--%>
			<%--<td width="950px"><c:if test="${orderVo.auxiliary!=null}">--%>
	 				<%--${orderVo.auxiliary.invoice_email}--%>
	 			<%--</c:if></td>--%>
		<%--</tr>--%>
	<%--</table>--%>

	<form action="/admin/order/list">
		<table class="mytablesubmit">
			<tr>
				<td width="1250px">
					<input style="width: 60px" class="btn return" type="submit" value="返回" /></td>
			</tr>
		</table>
	</form>
</body>
</html>


