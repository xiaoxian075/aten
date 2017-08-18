<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<html>
<head>
	<title>订单列表</title>

	<style>
		.goodsh{
			border:none;
			/*font-size:10px;*/
			text-align:center;
			margin-top:5px;
			margin-left:15px;
			margin-right:40px;
			margin-bottom:30px;
		}
		.goodsh tr td {
			height:40px;
		}
		.goodsh td {
			padding-right: 10px;
		}

		.goodtb{
			border:none;
			table-layout:fixed;
			width:100%;
			text-align:left;
			margin-top:0px;
			margin-left:40px;
			margin-right:40px;
			margin-bottom:0px;
		}
		.goodtb tr {
			/*background-color:#ebebeb; */
		}
		.goodtb td {
			height:40px;
			padding-right: 10px;
		}

		.goodtr{
			/*border:none;*/
			table-layout:fixed;
			width:100%;
			text-align:center;
			margin-top:0px;
			margin-left:15px;
			margin-right:40px;
			margin-bottom:20px;
		}
		.goodtr tr {
			/*background-color:#FBFBFB; */
		}
		.goodtr tr td {
			height:120px;
			word-wrap:break-word;
			overflow:hidden;
			border:1px solid #dddddd;
		}

		.goodtrh{
			/*border:none;*/
			/*table-layout:fixed;*/
			width: 100%;
			text-align:center;
			margin-top:0px;
			margin-left:15px;
			margin-right:40px;
			margin-bottom:20px;
		}
		.goodtrh .payTitle{
			display: inline-block;
			width: 100px;
			text-align: right;
		}
		.goodtrh th {
			height: 38px;
			border: 0px;

		}
		.goodtrh .orderclass{
			margin-right: 30px;
		}
		.goodtrh tr td {
			height:98px;
			word-wrap:break-word;
			overflow:hidden;
		}
		.contentHeaderDiv{
			width: 100%;
			position: relative;
			height: 50px
		}
		.contentHeaderSpan{
			position: absolute;
			left: 0px;
			bottom: 0px;
			display: inline-block;
			width: 100%;
			height: 75%;
			background-color: #f5f5f5;
			text-align: left;
			line-height: 38px;
			padding-left: 10px;
		}

		/* 		.goodimg{
                    width:150px;
                    height:40px;
                    cursor:pointer;
                } */
	</style>

	<script type="text/javascript">
        $(function () {
            // 时间设置
/*             $('#start_time,#end_time').datetimepicker({
                format:'Y-m-d'
            }); */
            $("#start_time").datetimepicker({
    			format:'Y-m-d',
    			language: 'zh',
    			minTime: true,
    			maxTime: true,
    			timepicker:false,
    			onSelectDate: function () {
    				var starttime=$("#start_time").val();
    				$("#end_time").datetimepicker({
    					minDate: starttime,
    					maxDate: false,
    				});
    			},
    		});
    		
    		$("#end_time").datetimepicker({
    			format:'Y-m-d',
    			language: 'zh',
    			minTime: true,
    			maxTime: true,
    			timepicker:false,
    			onSelectDate: function () {
    				var endtime=$("#end_time").val();
    				$("#start_time").datetimepicker({
    					minDate: false,
    					maxDate: endtime,
    				});
    			},
    		});
        });
        function resetpage() {
            $("#dpage").val("0");
            $("#dcount").val("0");
        }
        function showsendgoods(order_id) {
            var myUrl = "/iframe/sendgoods?order_id="+order_id;
            parent.layer.open({
                type: 2,
                title: '填写物流信息',
                shadeClose: false,
                shade: [0.3, '#000'],
                maxmin: true, //开启最大化最小化按钮
                area: ['500px', '300px'],
                content: myUrl
            });
        }

        function lookupship(order_number) {
            var myUrl = "/iframe/lookupship?order_number="+order_number;
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

        function editprice(obj,order_id,order_state) {
            if (order_state=='3') {	//订单预售单不让改价格
                art.dialog({
    			    title: '提示',
    			    content: "订金支付订单 不能修改价格",
    			    time:2000
    			});
                return;
            }
            var id = "."+order_id+"-goodinput";
            if (obj.name === "1") {	//置为修改状态
                // $(id).attr("disabled",false);*****
                $(id).removeAttr("readonly");//去除input元素的readonly属性
                $(id).css('border','1px solid #aaa');


                obj.name = "2";
                obj.value = "保存价格";
            } else if (obj.name ==="2") {	//提交状态
/*                 if (!confirm("确定要保存修改吗？")) {
                    $("#formsh").submit();
                    return;
                } */
            
                art.dialog({
                	id:"myDialog",
            	    title: '提示',
            	    content: "确定要保存修改吗？",
            	    okValue: '确认',
            	    cancelValue: '取消',
            	    width:300,
            	    time:2000,
            	    ok: function () {
            	    	editprice2(id,order_id);
            	    },cancel: function () {
            	    	$("#formsh").submit();
            	    }
            	});
            }
        }
		function editprice2(id,order_id) {
                $(id).css('border','0px solid #aaa');
                $(id).attr("readonly","readonly")//将input元素设置为readonly 　

                var goodjson = JSON.parse("{}");
                goodjson["order_id"]=order_id;

                //获取运费
                var id2 = "#"+order_id+"-trans_exp";
                var trans_exp = $(id2)[0].value;
                var mm = parseInt(trans_exp);
                if (mm>999999.99 || mm<0) {
                	art.dialog({
        			    title: '提示',
        			    content: "金额不在有效范围内",
        			    time:2000
        			});
                    window.setTimeout( function () {
                    	$("#formsh").submit();
                    }, 3000 );
                    return false;
                }
                goodjson["trans_exp"]=trans_exp;

                goodjson["arr_detail"] = new Array();
                //获取每项商品的价格
                var id3 = "input[id$='-"+order_id+"-total_amount'";	//$表示结尾匹配
                var listinput = $(id3);
                for (var i=0; i<listinput.length; i++) {
                    var detail_id = listinput[i].name;
                    var sale_price = listinput[i].value;
                    var mm = parseInt(sale_price);
                    if (mm>999999.99 || mm<0) {
                    	art.dialog({
            			    title: '提示',
            			    content: "金额不在有效范围内",
            			    time:2000
            			});
                        window.setTimeout( function () {
                        	$("#formsh").submit();
                        }, 3000 );
                        return false;
                    }
                    var node = JSON.parse("{}");
                    node["detail_id"] = detail_id;
                    node["total_amount"] = sale_price;
                    goodjson.arr_detail.push(node);
                }

                goodjson = JSON.stringify(goodjson);

                $.ajax({
                    url: "/admin/order/changeprice",
                    data: {"data":goodjson},
                    type: "POST",
                    success: function (data) {
                        var info = JSON.parse(data)
                        if (info.code == 0){
                            art.dialog({
                			    title: '提示',
                			    content: info.desc,
                			    time:2000
                			});
                            
                            window.setTimeout( function () {
                            	$("#formsh").submit();
                            }, 3000 );
                        }else{
                            art.dialog({
                			    title: '提示',
                			    content: info.desc,
                			    time:2000
                			});
                            window.setTimeout( function () {
                            	$("#formsh").submit();
                            }, 3000 );
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

                obj.name = "1";
                obj.value = "修改价格";
            
        }



        function lookupgoods(order_id) {
            $("#od_id").val(order_id);
            $("#ordertl").submit();
        }

        function fclear() {
            $("#order_number").val("");
            $("#order_type").val("100");
            $("#order_state").val("100");
            $("#start_time").val("");
            $("#end_time").val("");
            $("#is_bill").val("100");
            $("#formsh").submit();
        }
        
        function checkMoney(obj){
        	var money = obj.value;
        	if (money=="") {
        		art.dialog({
    			    title: '提示',
    			    content: "金额不能为空",
    			    time:1500
    			});
        		//obj.value = "0.00";
        		return;
        	}

        	if(!checkAmount(money)) {
        		art.dialog({
    			    title: '提示',
    			    content: "格式不对,请重新输入",
    			    time:2000
    			});
        		//obj.value = "0.00";
        		return false;
        	}else{
        		return true;
        	}
        }
        
        function checkAmount(money){
        	if (money=="") {
        		return false;
        	}
        	//验证是否RMB正则表达式
        	var reg = /^(\d){1,8}(\.)?(\d(\d)?)?$/;
        	if(!reg.test(money) || money<0 || money>999999.99) {
        		return false;
        	}else{
        		return true;
        	}
        }

	</script>

</head>
<body>
<form id="formsh" action="/admin/order/list" method="post" >
	<input type="hidden" id="dpage" name="dpage" <c:if test="${!empty page}">value="${page.current_s}"</c:if> />
	<input type="hidden" id="dcount" name="dcount" <c:if test="${!empty page}">value="${page.pagesize_s}"</c:if> />
	<table class=goodsh>  <!-- searchTable goodsh -->
		<tr>
			<td>
				订单号：<input type="text" id="order_number" name="order_number" value="${order_number}" onchange="resetpage()"/>
			</td>
			<td>
				订单类型：
				<select id="order_type" name="order_type" style="width:120px" onchange="resetpage()">
					<option value="100">全部</option>
					<option value="1" <c:if test="${order_type==1}">selected="selected"</c:if>>正常订单</option>
					<option value="2" <c:if test="${order_type==2}">selected="selected"</c:if>>全额预售订单</option>
					<option value="3" <c:if test="${order_type==3}">selected="selected"</c:if>>订金支付订单</option>
					<%-- 	                  	<option value="4" <c:if test="${order_type==4}">selected="selected"</c:if>>积分订单</option>  --%>
				</select>
			</td>
			<td>
				订单状态：
				<select id="order_state" id="order_state" name="order_state" style="width:150px" onchange="resetpage()">
					<option value="100">全部</option>
					<option value="0" <c:if test="${order_state==0}">selected="selected"</c:if>>未付款</option>
					<option value="1" <c:if test="${order_state==1}">selected="selected"</c:if>>等待卖家发货</option>
					<option value="2" <c:if test="${order_state==2}">selected="selected"</c:if>>等待买家确认收货</option>
					<option value="3" <c:if test="${order_state==3}">selected="selected"</c:if>>交易成功</option>
					<option value="5" <c:if test="${order_state==5}">selected="selected"</c:if>>交易关闭</option>
					<option value="6" <c:if test="${order_state==6}">selected="selected"</c:if>>未付款前交易关闭</option>
				</select>
			</td>
			<%-- 	             <td width="400px">
                                 订单时间:
                                  <input type="date" id="start_time" name="start_time" value="${start_time}" onchange="resetpage()" />
                                  -
                                  <input type="date" id="end_time" name="end_time" value="${end_time}" onchange="resetpage()" />
                              </td> --%>
			<td>
				订单时间:
				<input  class="text w120" type="text" id="start_time"  name="start_time" maxlength='20' value="${start_time}" onchange="resetpage()" />
				-
				<input  class="text w120" type="text" id="end_time" name="end_time" maxlength='20' value="${end_time}" onchange="resetpage()" />
			</td>
			<td>
				是否索要发票：
				<select id="is_bill" name="is_bill" style="width:70px" onchange="resetpage()">
					<option value="100">全部</option>
					<option value="0" <c:if test="${is_bill==0}">selected="selected"</c:if>>否</option>
					<option value="1" <c:if test="${is_bill==1}">selected="selected"</c:if>>是</option>
				</select>
			</td>
			<td>
				<input class="btn ol_colorbtn ol_greenbtn" type="submit" value="搜索" />
				<input class="btn ol_colorbtn ol_bredbtn" type="button" value="清空" onclick="fclear();"/>
			</td>
		</tr>
	</table>
</form>
<div style="margin-top: -20px">
	<table class="goodtrh" border="1">
		<tr bgColor="#f5fafe" style="border: solid 1px #eee;">
			<th colspan="2">商品信息</th>
			<th>单价(元)</th>
			<th>退款/售后</th>
			<th>总价(元)</th>
			<th>订单金额(元)</th>
			<th>实付金额(元)</th>
			<th>交易状态</th>
			<th>订单类型</th>
			<!-- <th>会员账户</th> -->
			<th>操作</th>
		</tr>
		<c:if test="${!empty orderVo}">
			<!-- 				<input type="checkbox" />全选 -->
			<c:forEach items="${orderVo}" var="item" varStatus="status">

				<tbody>
				<tr valign="bottom">
						<%-- <td width="20px"><input type="checkbox" value="${item.order.order_id}"/></td> --%>
					<td colspan="10" style="border: 0px;height: auto">
						<div class="contentHeaderDiv">
									<span class="contentHeaderSpan">
									    <span class="orderclass">会员账户：${item.order.account_name}</span>
										<span class="orderclass">订单时间：${fn:substring(item.order.create_time, 0, 19)}</span>
										<span class="orderclass">订单号：${item.order.order_number}</span>
										<span class="orderclass">是否索要发票：<c:choose><c:when test="${item.order.is_bill==1}">是</c:when><c:otherwise>否</c:otherwise></c:choose></span>
									</span>
						</div>
					</td>
					<!-- <td width="300px" colspan ="2">订单时间:${fn:substring(item.order.create_time, 0, 19)}</td>
						<td width="300px" colspan ="3">订单号:${item.order.order_number}</td>
						<td width="300px">是否索要发票:<c:choose><c:when test="${item.order.is_bill==1}">是</c:when><c:otherwise>否</c:otherwise></c:choose></td>
				  		<td width="630px"></td> -->
				</tr>
				</tbody>

				<c:if test="${!empty item.detail}">
					<c:forEach items="${item.detail}" var="item2" varStatus="status2">
						<c:set var="rowsnum" scope="session" value="${item.detail.size()}"/>

						<tr>
							<td width="60px" style="border-right: 0px"><span style="display: inline-block;border: 1px solid #eee"><img class="img img_wh80" src="${ossImgServerUrl}${item2.goods_img}"/></span></td>
								<%-- <td width="220px"><div style="width:200px">${item2.goods_name}</div></td> --%>
							<td width="220px" style="border-left: 0px;border-right: 0px;text-align: left;padding:0px 10px"><div style="height: 100%;padding-top: 20px"><div>${item2.goods_name}</div><div style="margin-top: 5px">规格：${item2.sku_name}</div></div></td>
							<td width="100px" style="border-left: 0px;border-right: 0px;">
								<fmt:formatNumber value="${item2.sale_price*0.01}" pattern="##.##" minFractionDigits="2" /><br>
								×${item2.num}
							</td>
							<td width="100px" style="border-left: 0px;border-right: 0px;">
							<c:choose>
								<c:when test="${item2.state==4}">退款中</c:when>
								<c:when test="${item2.state==5}">退款成功</c:when>
								<c:when test="${item2.state==6}">未付款前关闭交易</c:when>
								<c:when test="${item2.state==7}">退货退款中 </c:when>
								<c:when test="${item2.state==8}">退货退款成功</c:when>
								<c:when test="${item2.state==9}">售后中</c:when>
								<c:when test="${item2.state==10}">售后成功</c:when>
								<c:otherwise>-</c:otherwise>
							</c:choose>
							</td>
							<td width="150px" style="border-left: 0px;border-right: 0px;"><input class="${item.order.order_id}-goodinput" type="text" name="${item2.detail_id}" id="${item2.detail_id}-${item.order.order_id}-total_amount" value=<fmt:formatNumber value="${item2.total_amount*0.01}" pattern="##.##" minFractionDigits="2" /> readonly style="width:60px;border:0px;"  onchange="checkMoney(this);"/></td>
							<c:if test="${status2.index==0}" >

								<%-- <c:set var="order_amount" scope="session" value="${item.order.order_amount+item.order.trans_exp}"/> --%>
								<td width="100px" rowspan="${rowsnum}"><fmt:formatNumber value="${item.order.order_amount*0.01}" pattern="##.##" minFractionDigits="2" /></td>

								<!-- **************** -->
								<!-- <td width="100px" rowspan="${rowsnum}"><fmt:formatNumber value="${item.order.discount_amount*0.01}" pattern="##.##" minFractionDigits="2" /></td> -->
								<!-- <td width="100px" rowspan="${rowsnum}"> -->
								<!-- <input class="${item.order.order_id}-goodinput" type="text" id="${item.order.order_id}-trans_exp" value=<fmt:formatNumber value="${item.order.trans_exp*0.01}" pattern="##.##" minFractionDigits="2" /> disabled style="width:60px;" /> -->
								<!-- </td> -->
								<!-- ************************ -->
								<td width="150px" rowspan="${rowsnum}">
		  		                  <span style="text-align: left;display: inline-block">
									  <div class="payTitle">优惠金额：</div> <fmt:formatNumber value="${item.order.discount_amount*0.01}" pattern="##.##" minFractionDigits="2" /><br>
						  		      <div class="payTitle">运费：</div><input class="${item.order.order_id}-goodinput" type="text" id="${item.order.order_id}-trans_exp" value=<fmt:formatNumber value="${item.order.trans_exp*0.01}" pattern="##.##" minFractionDigits="2" /> readonly style="width:60px;border:0px;" onchange="checkMoney(this);"/><br>
						  	          <div class="payTitle"><c:if test="${item.order.is_change_price==1}">(改)</c:if>实付金额： </div> <fmt:formatNumber value="${item.order.pay_amount*0.01}" pattern="##.##" minFractionDigits="2" />
		  		                  </span>
								</td>

								<td width="150px" rowspan="${rowsnum}">
									<c:choose>
										<c:when test="${item.order.order_state==0}">未付款</c:when>
										<c:when test="${item.order.order_state==1}">等待卖家发货</c:when>
										<c:when test="${item.order.order_state==2}">等待买家确认收货</c:when>
										<c:when test="${item.order.order_state==3}">交易成功</c:when>
										<c:when test="${item.order.order_state==5}">交易关闭</c:when>
										<c:when test="${item.order.order_state==6}">未付款前交易关闭</c:when>
									</c:choose>

								</td>
								<td width="150px" rowspan="${rowsnum}">
									<c:choose>
										<c:when test="${item.order.order_type==1}">正常订单</c:when>
										<c:when test="${item.order.order_type==2}">全额预售订单</c:when>
										<c:when test="${item.order.order_type==3}">订金支付订单</c:when>
							<%-- 			<c:when test="${item.order.order_type==4}">积分订单</c:when> --%>
									</c:choose>
								</td>
								<!-- <td width="200px" rowspan="${rowsnum}">${item.order.account_name}</td> -->
								<td width="150px" rowspan="${rowsnum}">
									<c:if test="${item.order.order_state==1}"><input class="btn ol_colorbtn ol_bluebtn" type="button" value="发货" onclick="showsendgoods('${item.order.order_id}');" style="width:53px;height:30px"/><br></c:if>
									<c:if test="${item.order.order_state==0}"><input class="btn ol_colorbtn ol_bluebtn" type="button" name="1" value="修改价格" onclick="editprice(this,'${item.order.order_id}','${item.order.order_type}');" style="width:80px;height:30px"/><br></c:if>
									<%-- <c:if test="${item.order.order_state>1 && item.order.order_state!=6}"><input class="btn ol_colorbtn ol_bluebtn" type="button" value="查看物流" onclick="lookupship('${item.order.order_number}');" style="width:80px;height:30px;"/><br></c:if> --%>
									<c:if test="${item.order.is_send==1}"><input class="btn ol_colorbtn ol_bluebtn" type="button" value="查看物流" onclick="lookupship('${item.order.order_number}');" style="width:80px;height:30px;"/><br></c:if>
									<input class="btn return" type="button" value="订单详情" onclick="lookupgoods('${item.order.order_id}');" style="width:80px;height:30px;margin-top: 10px"/>
								</td>
							</c:if>
						</tr>
					</c:forEach>
				</c:if>

			</c:forEach>
		</c:if>
		<c:if test="${empty orderVo}"><table class="goodtr"><tr><td style="padding-left:30px;" width="1500px">暂无数据</td></tr></table></c:if>


	</table>
</div>

<div>


	<!-- ************************** -->


</div>

<form id="ordertl" action="/admin/order/orderdetail" method="post" >
	<input type="hidden" name="order_id" id = "od_id" />
</form>


<div class="page_contain">
	<%@ include file="/WEB-INF/common/pagelist2.jsp"%>
</div>
<%-- 	  <%@ include file="/WEB-INF/common/list_hidden_value.jsp"%> --%>

</body>
</html>

