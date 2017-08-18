<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<html>
<head>
<title>售后管理</title>

 <style>
    	/*.goodsh{*/
    		/*border:none;*/
    		/*font-size:14px;*/
    		/*text-align:center;*/
    		/*margin-top:5px;*/
    		/*margin-left:40px;*/
    		/*margin-right:40px;*/
    		/*margin-bottom:30px;*/
    	/*}*/
    	/*.goodsh tr {*/
    		/*background-color:#dff0d8; */
    	/*}*/
    	/*.goodsh tr td {*/
    		/*height:60px;*/
    	/*}*/
    	/**/
     	/*.goodtb{*/
    		/*border:none;*/
    		/*text-align:center;*/
    		/*margin-top:0px;*/
    		/*margin-left:40px;*/
    		/*margin-right:40px;*/
    		/*margin-bottom:0px;*/
    	/*}*/
    	/*.goodtb tr {*/
    		/*background-color:#ebebeb; */
    	/*}*/
    	/*.goodtb tr td {*/
    		/*height:40px;*/
    	/*}*/
    	/**/
    	/*.goodtr{*/
    		/*!*border:none;*!*/
    		/*text-align:center;*/
			/*margin-top:0px;*/
    		/*margin-left:40px;*/
    		/*margin-right:40px;*/
    		/*margin-bottom:40px;*/
    	/*}*/
    	/*.goodtr tr {*/
    		/*background-color:#FBFBFB; */
    	/*}*/
    	/*.goodtr tr td {*/
    		/*height:80px;*/
    	/*}*/
    	/**/
		/*.goodimg{*/
			/*width:150px;*/
			/*height:40px;*/
			/*cursor:pointer;*/
		/*}*/

		.list_div table th{
			height: 20px;
			border-left: 0px;
			border-right: 0px
		}
		.list_div table td{
			/*height: 50px;*/
			background-color: #fff;
		}
        .conHeaderDiv{
           height: 100%;
           text-align: left;
           position: relative;
        }
        .conHeaderSpan{
            position: absolute;
		    bottom: 0px;
		    left: 0px;
		    display: inline-block;
		    height: 40px;
		    width: 100%;
		    background-color: #f5f5f5;
		    line-height: 40px;
        }
		.contentHeader{
			margin-right: 20px;
			margin-left: 10px;
		}
    </style>

<script type="text/javascript">
  	function resetpage() {
  		$("#dpage").val("0");
  		$("#dcount").val("0");
  	}
	function fclear() {
		$("#refund_id").val("");
		$("#detail_id").val("");
		$("#refund_type").val("100");
		$("#refund_state").val("100");
		$("#formsh").submit();
	}
	
	function lookupdetail(refund_id) {
		$("#detail_refund_id").val(refund_id);
		$("#hiddendetailform").submit();
	}
	
	function apliback(refund_id) {
		$("#grant_refund_id").val(refund_id);
		$("#hiddengrantform").submit();
	}
	
    function lookupship(refund_number) {
        var myUrl = "/iframe/lookupship?order_number="+refund_number;
        parent.layer.open({
            type: 2,
            title: '物流信息',
            shadeClose: false,
            shade: [0.3, '#000'],
            maxmin: true, //开启最大化最小化按钮
            area: ['1000px', '450px'],
            content: myUrl
        });
    }
  </script>

</head>
<body>
	<div class="searchDiv">
		<form id="formsh" action="/admin/order/refundlist" method="post">
			<input type="hidden" id="dpage" name="dpage"
				<c:if test="${!empty page}">value="${page.current_s}"</c:if> /> <input
				type="hidden" id="dcount" name="dcount"
				<c:if test="${!empty page}">value="${page.pagesize_s}"</c:if> />
			<table class="searchTable">
				<tr>
					<td width="">退款/售后单号：<input type="text" id="refund_id"
						name="refund_id" value="${refund_id}" onchange="resetpage()" />
					</td>
					<td width="">订单号：<input type="text" id="detail_id"
						name="detail_id" value="${detail_id}" onchange="resetpage()" />
					</td>
					<td width="">服务类型： <select id="refund_type"
						name="refund_type" style="width:120px" onchange="resetpage()">
							<option value="100">全部</option>
							<option value="0"
								<c:if test="${refund_type==0}">selected="selected"</c:if>>退款</option>  
	                  	<option value="1" <c:if test="${refund_type==1}">selected="selected"</c:if>>退货</option>  
	                  	<option value="2" <c:if test="${refund_type==2}">selected="selected"</c:if>>售后订单</option>          
	              	</select>
	 	  		</td>
	 	  		<td width="300px">
	 	  			订单状态：
	  	  			<select id="refund_state" name="refund_state" style="width:120px" onchange="resetpage()">
	                  	<option value="100">全部</option>
	                  	<option value="1" <c:if test="${refund_state==1}">selected="selected"</c:if>>待商家同意</option>  
	                  	<option value="2" <c:if test="${refund_state==2}">selected="selected"</c:if>>取消申请 </option>  
	                  	<option value="3" <c:if test="${refund_state==3}">selected="selected"</c:if>>同意申请</option> 
	                  	<option value="4" <c:if test="${refund_state==4}">selected="selected"</c:if>>已退货</option> 
	                  	<option value="5" <c:if test="${refund_state==5}">selected="selected"</c:if>>拒绝申请</option> 
	                  	<option value="6" <c:if test="${refund_state==6}">selected="selected"</c:if>>退货时间超时</option> 
	                  	<option value="7" <c:if test="${refund_state==7}">selected="selected"</c:if>>同意退款</option>        
	              	</select>
	             </td>
	 	  		<td width="100px">
	 	  			 <input class="btn ol_colorbtn ol_greenbtn" type="submit" value="搜索" />
	 	  			 <input class="btn ol_colorbtn ol_bredbtn" type="button" value="清空" onclick="fclear();"/>
	 	  		</td>
	  		</tr>
	  	</table>	
	</form>
	</div>

	 <div class="list_div">
		<table id="list_table" class="tableClass" border="0" cellspacing="0" cellpadding="0">
			<tbody>
 			<tr>
		  		<th width="200px" colspan="2" style="border-left: 1px solid #eee">商品信息</th>
		  		<th width="150px">服务类型</th>
		  		<th width="250px">状态</th>
		  		<th width="200px">商品金额（元）</th>
		  		<th width="220px">退款金额（元）</th>
		  		<th width="250px">申请时间</th>
		  		<th width="250px" style="border-right: 1px solid #eee">操作</th>
 			</tr>

 		<c:choose>
			<c:when test="${!empty refundList}">

			 		<c:forEach items="${refundList}" var="item" varStatus="status">
			 		    <tr>
			 		    	<td colspan="8" style="border:0px;padding: 0px;height: 50px;width: 100px">
			 		    		<div class="conHeaderDiv">
			 		    			<span class="conHeaderSpan">
			 		    				<span class="contentHeader">退款/售后单号：${item.refund.refund_id}</span>
										<span class="contentHeader">订单号：${item.detail.order_number}</span>
								  		<span class="contentHeader">会员账户：${item.refund.account_name}</span>
			 		    			</span>
			 		    		</div>
			 		    	</td>
			 		    </tr>
			 			<tr style="background: #fff">
						    <td style="width: 100px;border-right: 0px;padding: 10px 3px;">
						    	 <span style="border: 1px solid #eee;display: inline-block;">
							       <img class="img180" style="width: 90px" src="${ossImgServerUrl}${item.detail.goods_img}" />
							     </span>
						    </td>
							<td style="text-align: left;width: 250px;border-left: 0px">
								<div style="height: 100%;padding-top: 20px">
									<div>${item.detail.goods_name}</div>
									<div style="margin-top: 5px">规格：${item.detail.sku_name}</div>
								</div>
							</td>

					  		<td>
					  			<c:if test="${item.refund.refund_type==0}">退款</c:if>
					  			<c:if test="${item.refund.refund_type==1}">退货</c:if>
					  			<c:if test="${item.refund.refund_type==2}">售后订单</c:if>    
					  		</td>
					  		<td>
					  			<c:if test="${item.refund.refund_state==1}">待商家同意</c:if>
	                  			<c:if test="${item.refund.refund_state==2}">取消申请 </c:if>
	                  			<c:if test="${item.refund.refund_state==3}">同意申请</c:if>
	                  			<c:if test="${item.refund.refund_state==4}">已退货</c:if>
	                  			<c:if test="${item.refund.refund_state==5}">拒绝申请</c:if>
	                  			<c:if test="${item.refund.refund_state==6}">退货时间超时</c:if>
	                  			<c:if test="${item.refund.refund_state==7}">同意退款</c:if>
					  		</td>
					  		<td><fmt:formatNumber value="${item.detail.total_amount*0.01}" pattern="##.##" minFractionDigits="2" /></td>
					  		<td><fmt:formatNumber value="${item.refund.refund_amount*0.01}" pattern="##.##" minFractionDigits="2" /></td>
					  		<td>${fn:substring(item.refund.refund_time, 0, 19)}</td>
					  		<td>
					  			<c:if test="${item.refund.refund_state==1 || item.refund.refund_state==4}"><input class="btn ol_colorbtn ol_bluebtn" type="button" value="售后处理" onclick="apliback('${item.refund.refund_id}')"/></c:if>
					  			<c:if test="${item.refund.refund_state==4}"><input class="btn ol_colorbtn ol_bluebtn" type="button" value="查看物流" onclick="lookupship('${item.refund.refund_id}')"/></c:if>
					  			<input class="btn ol_colorbtn ol_bluebtn" type="button" value="查看详情" onclick="lookupdetail('${item.refund.refund_id}')"/>
					  		</td>
					  	</tr>
				 	</c:forEach>
			</c:when>
			<c:otherwise>
				<tr><td colspan="9">暂无数据</td></tr>
			</c:otherwise>
		</c:choose>
		</tbody>
		</table>
	</div>

	<form id="hiddendetailform" action="/admin/order/refunddetailpage" method="post" >
		<input type="hidden" id="detail_refund_id" name="refund_id" />
	</form>
	<form id="hiddengrantform" action="/admin/order/refundgrantpage" method="post" >
		<input type="hidden" id="grant_refund_id" name="refund_id" />
	</form>


 	  <div class="page_contain">
	  		<%@ include file="/WEB-INF/common/pagelist2.jsp"%>
	  </div>

  </body>
 </html>


