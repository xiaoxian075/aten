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
}

.mytable {
	/*border:1px solid #dddddd;*/
	font-size: 14px;
	text-align: center;
	margin-top: 5px;
	margin-left: 15px;
	margin-right: 40px;
	margin-bottom: 20px;
	width: 88%;
}

.mytable tr td {
	border: 1px solid #dddddd;
	height: 40px;
}
.mytable tr th {
	border: 1px solid #dddddd;
	height: 40px;
}

.mytable2 {
	font-size: 14px;
	text-align: center;
	margin-top: 30px;
	margin-left: 40px;
	margin-right: 40px;
	margin-bottom: 0px;
}

.mytable2 tr td {
	border: 0;
	height: 40px;
}
.mytable2 tr th {
	border: 0;
	height: 40px;
}

.goodimg {
	width: 150px;
	height: 40px;
	cursor: pointer;
}

.goodimg2 {
	width: 400px;
	height: 200px;
	cursor: pointer;
}
</style>

<script type="text/javascript">
		function rback() {
  			$("#refundlist").submit();
		}
		
		function grant(refund_id) {
			var grant_tag = $('input:radio:checked').val();
			var record_explain = $("#grantreason").val();
			
			if (grant_tag!='1' && grant_tag!='2') {
				art.dialog({
	  			    title: '提示',
	  			    content: "请选择同意或拒绝",
	  			    time:2000
	  			});
				return;
			}
			record_explain = record_explain.replace(/(^\s*)|(\s*$)/g, "");
			if (record_explain=="undefined" || record_explain=='') {
				art.dialog({
	  			    title: '提示',
	  			    content: "请输入审批理由",
	  			    time:2000
	  			});
				return;
			}
			if (record_explain.length>150) {
				art.dialog({
	  			    title: '提示',
	  			    content: "审批理由过长,请控制在150个字符内",
	  			    time:2000
	  			});
				return;
			}
			
			var data = {
					"refund_id":refund_id,
					"grant_tag":grant_tag,
					"record_explain":record_explain
			}
			
			$.ajax({
				url: "/admin/order/refundgrant",
				data: data,
				type: "POST",
				success: function (data) {
					var info = JSON.parse(data)
					if (info.code == 0){
						art.dialog({
			  			    title: '提示',
			  			    content: info.desc,
			  			    time:2000
			  			});
						$("#refundlist").submit();
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
			
			$("#refund_ok").attr("disabled",true);
		}
		
		function lookuporder(order_id) {
			$("#od_id").val(order_id);
			$("#ordertl").submit();
		}
	</script>
<!--   	private OrderRefundNode refund;
	private OrderDetailNode detail;
	private List<OrderRefundLogsNode> arrlogs; -->
</head>
<body>
	<table class="mytable">
		<tr>
			<td colspan="5" style="border:0px;text-align:left;font-size:20px;">退款/售后信息</td>
		</tr>
		<tr bgcolor="#f5fafe">
			<th width="300px">退款/售后单号</th>
			<th width="300px">申请时间</th>
			<th width="300px">退款金额（元）</th>
			<th width="300px">服务类型</th>
			<th width="300px">状态</th>
		</tr>
		<tr>
			<td>${refundVo.refund.refund_id}</td>
			<td>${fn:substring(refundVo.refund.refund_time, 0, 19)}</td>
			<td><fmt:formatNumber value="${refundVo.refund.refund_amount*0.01}" pattern="##.##" minFractionDigits="2" /></td>
			<td><c:if test="${refundVo.refund.refund_type==0}">退款</c:if> <c:if
					test="${refundVo.refund.refund_type==1}">退货</c:if> <c:if
					test="${refundVo.refund.refund_type==2}">售后订单</c:if></td>
			<td><c:if test="${refundVo.refund.refund_state==1}">待商家同意</c:if>
				<c:if test="${refundVo.refund.refund_state==2}">取消申请 </c:if> <c:if
					test="${refundVo.refund.refund_state==3}">同意申请</c:if> <c:if
					test="${refundVo.refund.refund_state==4}">已退货</c:if> <c:if
					test="${refundVo.refund.refund_state==5}">拒绝申请</c:if> <c:if
					test="${refundVo.refund.refund_state==6}">退货时间超时</c:if> <c:if
					test="${refundVo.refund.refund_state==7}">同意退款</c:if></td>
		</tr>
	</table>

	<table class="mytable">
		<tr>
			<td colspan="5" style="border:0px;text-align:left;font-size:20px;">商品信息</td>
		</tr>
		<tr bgcolor="#f5fafe">
			<th width="600px" colspan="2">商品名称</th>
			<th width="300px">数量</th>
			<th width="300px">单价（元）</th>
			<th width="300px">小计（元）</th>
		</tr>
		<tr>
			<td width="100px" style="padding: 10px 0px;border-right: 0px">
			    <span style="border: 1px solid #eee;display: inline-block;">
			       <img class="img180" style="width: 90px" src="${ossImgServerUrl}${refundVo.detail.goods_img}" />
			    </span>
			</td>
			<td width="300px" align="left" style="border-left: 0px">${refundVo.detail.goods_name}<div>规格：${refundVo.detail.sku_name}</div></td>
			<td width="300px">${refundVo.detail.num}</td>
			<td width="300px"><fmt:formatNumber value="${refundVo.detail.sale_price*0.01}" pattern="##.##" minFractionDigits="2" /></td>
			<td width="300px"><fmt:formatNumber
					value="${refundVo.detail.total_amount*0.01}" pattern="##.##"
					minFractionDigits="2" /></td>
		</tr>
	</table>

<%-- 	<table class="mytable">
		<tr>
			<td colspan="5" style="border:0px;text-align:left;font-size:20px;">订单基本信息</td>
		</tr>
		<tr>
			<td width="300px">订单号</td>
			<td width="300px">订单时间</td>
			<td width="300px">订单状态</td>
			<td width="300px">会员账户</td>
			<td width="300px">订单总金额</td>
		</tr>
		<tr>
			<td>${refundVo.detail.goods_name}</td>
			<td>${refundVo.detail.create_time}</td>
			<td><c:if test="${refundVo.detail.state==0}">未付款（等待买家付款）</c:if>
				<c:if test="${refundVo.detail.state==1}">等待卖家发货(买家已付款)</c:if> <c:if
					test="${refundVo.detail.state==2}">等待买家确认收货（卖家已发货）</c:if> <c:if
					test="${refundVo.detail.state==3}">交易成功</c:if> <c:if
					test="${refundVo.detail.state==4}">退款中</c:if> <c:if
					test="${refundVo.detail.state==5}">退款成功（交易关闭</c:if> <c:if
					test="${refundVo.detail.state==6}">未付款前，关闭交易</c:if> <c:if
					test="${refundVo.detail.state==7}">退货退款中</c:if> <c:if
					test="${refundVo.detail.state==8}">退货退款成功</c:if> <c:if
					test="${refundVo.detail.state==9}">售后中</c:if> <c:if
					test="${refundVo.detail.state==10}">售后成功</c:if></td>
			<td>${refundVo.refund.account_id}</td>
			<td><fmt:formatNumber value="${refundVo.detail.total_amount*0.01}" pattern="##.##" minFractionDigits="2" /></td>
		</tr>
	</table> --%>
	<table class="mytable">
		<tr>
			<td colspan="5" style="border:0px;text-align:left;font-size:20px;">订单基本信息</td>
		</tr>
		<tr bgcolor="#f5fafe">
			<th width="300px">订单号</th>
			<th width="300px">订单时间</th>
			<th width="300px">订单状态</th>
			<th width="300px">会员账户</th>
			<th width="300px">订单总金额</th>
		</tr>
		<tr>
			<td><a href="#" onclick="lookuporder('${refundVo.order.order_id}')"><font color="#FF0000">${refundVo.order.order_number}</font></a></td>
			<td>${fn:substring(refundVo.order.create_time, 0, 19)}</td>
			<td><c:if test="${refundVo.order.order_state==0}">未付款</c:if>
				<c:if test="${refundVo.order.order_state==1}">等待卖家发货</c:if>
					<c:if test="${refundVo.order.order_state==2}">等待买家确认收货</c:if>
					<c:if test="${refundVo.order.order_state==3}">交易成功</c:if>
					<c:if test="${refundVo.order.order_state==4}">退款中</c:if>
					<c:if test="${refundVo.order.order_state==5}">退款成功</c:if>
					<c:if test="${refundVo.order.order_state==6}">未付款前关闭交易</c:if>
					<c:if test="${refundVo.order.order_state==7}">退货退款中</c:if>
					<c:if test="${refundVo.order.order_state==8}">退货退款成功</c:if> 
					<c:if test="${refundVo.order.order_state==9}">售后中</c:if> 
					<c:if test="${refundVo.order.order_state==10}">售后成功</c:if></td>
			<td>${refundVo.account.login_name}</td>
			<td><fmt:formatNumber value="${refundVo.order.pay_amount*0.01}" pattern="##.##" minFractionDigits="2" /></td>
		</tr>
	</table>	

<%-- 	<table class="mytable">
		<tr>
			<td colspan="2" style="border:0px;text-align:left;font-size:20px;">交互详情</td>
		</tr>
		<tr bgcolor="#f5fafe">
			<th width="200px">卖家/买家</th>
			<th width="600px">上传图片</th>
			<th width="500px">说明</th>
			<th width="200px">时间</th>
		</tr>
		<c:if test="${!empty refundVo.arrlogs}">
			<c:forEach items="${refundVo.arrlogs}" var="item" varStatus="status">
				<tr>
					<td width="200px"><c:if test="${item.type==1}">卖家</c:if> <c:if
							test="${item.type==2}">买家</c:if></td>
					<td width="600px"><c:set var="arrimg" scope="session" value="${item.refund_img}" /> 
						<c:forEach items="${arrimg}" var="item2" varStatus="status2">
							<span style="border: 1px solid #eee;display: inline-block;">
							   <img class="img img180" style="width: auto;" src="${ossImgServerUrl}${item2}" />
							 </span>
						</c:forEach>
						</td>
					<td width="500px" align="left" style="padding: 10px">${item.record_explain}</td>
					<td width="200px">${fn:substring(item.record_time, 0, 19)}</td>
				</tr>
			</c:forEach>
		</c:if>
	</table> --%>



	<table class="mytable" border="1" align="center" rules="all" width="100%" height="auto" cellpadding="10">
		<tr bgcolor="#f5fafe">
			<th colspan="2" align="left" style="padding-left: 10px;">售后信息</th>

		</tr>
		<c:if test="${!empty refundVo.arrlogs}">
			<c:forEach items="${refundVo.arrlogs}" var="item" varStatus="status">
				<tr align="left">
					<td width="90px" style="padding: 10px;border-right: 0px">
				       <div style="height: 100%">
						   <span style="border: 1px solid #eee;display: inline-block;">
<%-- 							  <img class="img180" style="width: 90px" src="${ossImgServerUrl}${refundVo.account.head_pic}" /> --%>
							  <c:if test="${item.type==1}"><img class="img180" style="width: 90px" src="${ossImgServerUrl}${refundVo.seller_pic}" /></c:if>
							  <c:if test="${item.type==2}">
							  	<c:choose>
								  	<c:when test="${refundVo.account.head_pic==null}">
								  		<img class="img180" style="width: 90px" src="${ossImgServerUrl}${cfgDefaultHeaderIng}" />
								  	</c:when>
								  	<c:otherwise>
								  		<img class="img180" style="width: 90px" src="${ossImgServerUrl}${refundVo.account.head_pic}" />
								  	</c:otherwise>
							  	</c:choose>
							  </c:if>
						   </span>
					   </div>
					</td>
					<td style="padding: 10px 0px;border-left: 0px">
						<div style="position:relative">
							<c:if test="${item.type==1}"><span class="name">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;商家：${refundVo.seller_name}</span></c:if>
							<c:if test="${item.type==2}"><span class="name">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;会员：${refundVo.account.nick_name}</span></c:if>
							<span style=" position:absolute;right:10px;">${fn:substring(item.record_time, 0, 19)}</span>
						</div>
						<div style="clear:both">原因说明：${item.record_explain}</div>
						<div style="margin-top:10px;">图片说明：
							<c:forEach items="${item.refund_img}" var="item2" varStatus="status2">
								 <span style="display:inline-block;">
							 		<img class="img180" style="width: 90px" src="${ossImgServerUrl}${item2}" />
					       		</span>
							</c:forEach>
						</div>
		
					</td>
				</tr>
			</c:forEach>
		</c:if>
		
	</table>



	<table class="mytable2">
		<tr>
			<td colspan="2" style="border:0px;text-align:left;font-size:20px;">申请理由</td>
		</tr>
		<tr>
			<td width="100px">申批意见：</td>
			<td width="1400px">
				<label><input name="grant"type="radio" value="1"  />同意</label>
				<label><input name="grant"type="radio" value="2"  />拒绝</label>
			 </td>
		</tr>
		<tr>
			<td width="100px">申批理由：</td>
			<td width="1400px"><textarea id="grantreason" rows="5" cols="100"></textarea></td>
		</tr>
		<tr>
			<td width="750px" style="text-align:center;padding-top: 20px" colspan="2">
			  <input type="button" id="refund_ok" value="确定" onclick="grant('${refundVo.refund.refund_id}')" class="btn operbtn" style="width: 50Px" />
			   <input type="button" value="返回" onclick="rback()" class="btn return" style="width: 50Px"/>
			</td>
		</tr>
	</table>


	<form id="refundlist" action="/admin/order/refundlist">
	</form>
	
  <form id="ordertl" action="/admin/order/orderdetail" method="post" >
  	<input type="hidden" name="order_id" id = "od_id" />
  </form>	
</body>
</html>
