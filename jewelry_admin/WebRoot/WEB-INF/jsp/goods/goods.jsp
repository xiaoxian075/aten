<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/7/2
  Time: 16:43
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<style type="text/css">
.patternName {
	margin-right: 30px;
}

.pre_sale {
	width: 800px;
	height: 220px;
	background-color: #eee;
	margin-left: 200px;
	padding: 20px 30px;
}

#all_sale_container {
	height: 160px;
}

.pre_sale .title {
	display: inline-block;
	width: 120px;
	text-align: right;
}

.pre_sale .prePrice, .preTime {
	width: 320px;
	border-radius: 5px;
	border: 1px solid #ccc;
}

.pre_sale .selectTime {
	/*width: 93px;*/
	height: 28px;
	margin-right: 20px;
}

.pre_sale .afterTime {
	width: 93px;
}

.pre_sale .count {
	width: 110px;
	height: 18px;
	border-radius: 5px;
	border: 1px solid #ccc;
}

#presale_container input {
	padding-left: 10px;
}

#all_sale_container input {
	margin-bottom: 10px;
}

#presale_container .pre_sale_chose {
	width: 556px;
	margin-left: 100px;
	margin-bottom: 20px;
	text-align: center;
}

.pre_sale_chose .presaleName {
	margin-right: 20px;
}

#some_sale_container .some_sale_price {
	width: 100%;
	height: 60px;
	padding: 10px 0px;
}

#some_sale_container .some_sale_prices {
	display: inline-block;
	vertical-align: bottom;
}

#some_sale_container .some_sale_one {
	margin-right: 30px;
}

#some_sale_container .some_sale_two {
	/*padding-top: 10px;*/
	
}

#some_sale_container .some_sale_time {
	width: 100px;
	margin: 5px;
}

#some_sale_container .stepNum {
	display: inline-block;
	width: 20px;
	height: 20px;
	border-radius: 10px;
	background-color: rgb(0, 110, 177);
	color: #fff;
	line-height: 20px;
	text-align: center;
}

#some_sale_container .stepStrip {
	display: inline-block;
	width: 235px;
	height: 3px;
	background-color: rgb(0, 110, 177);
	vertical-align: middle;
}

#some_sale_container .stepStrip2 {
	display: inline-block;
	width: 365px;
	height: 3px;
	background-color: rgb(0, 110, 177);
	vertical-align: middle;
}

#some_sale_container .selectProportion {
	width: 80px;
}

#some_sale_container .proportion {
	margin-top: 15px;
	margin-bottom: 20px;
	position: relative;
}

#some_sale_container .weiProportion {
	margin-left: 95px;
}

#some_sale_container .fahuo {
	position: absolute;
	right: 145px;
}

#some_sale_container .weiDate {
	width: 130px;
}
</style>

<c:set var="fixed_price" scope="session" value="${goods.fixed_price}" />
<tr>
	<td class="td_left">售卖方式:</td>
	<td colspan="2" class="td_right_two"><c:choose>
			<c:when test="${goods!=null}">
				<input type="radio" name="sale_mode" value="0"
					<c:if test="${goods.sale_mode==0}">checked</c:if>
					onchange="changePattern()">
				<span class="patternName">一口价模式</span>
				<input type="radio" name="sale_mode" value="1"
					<c:if test="${goods.sale_mode==1}">checked</c:if>
					onchange="changePattern()">
				<span class="patternName">预售模式</span>
			</c:when>
			<c:otherwise>
				<input type="radio" name="sale_mode" value="0" checked
					onchange="changePattern()">
				<span class="patternName">一口价模式</span>
				<input type="radio" name="sale_mode" value="1"
					onchange="changePattern()">
				<span class="patternName">预售模式</span>
			</c:otherwise>
		</c:choose></td>
</tr>
<tr id="onesale_container">
	<td class="td_left">商品金额<span class="must_span">*</span></td>
	<td class="td_right_two">
		<input type="text" class="validate" id="fixed_price" isrequired="yes" dataType="jsRmb" tipmsg="商品金额"  name="fixed_price_1" maxlength='10' maxdatalength='10'<c:if test="${goods!=null}">value="${fixed_price}"</c:if>/><span>(元)</span>
	</td>
</tr>
<c:choose>
	<c:when test="${goods==null}">
		<tr id="presale_container" style="display: none;">
			<td colspan="2">
				<div class="pre_sale_chose">
					<input type="radio" name="presale_model" value="1" checked="true"
						onchange="changePsPattern()"> <span class="presaleName">全额预售</span>
					<input type="radio" name="presale_model" value="2"
						onchange="changePsPattern()"> <span class="presaleName">定金预售</span>
				</div>
				<div class="pre_sale" id="all_sale_container"
					style="display: block;">
					<span class="title"><span style="color:#FF0000;">*</span>预售金额 ：</span>
					<input type="text" placeholder="请输入" name="fixed_price_a"
						class="prePrice" style="width:120px"/><span>(元)</span><br> <span class="title"><span
						style="color:#FF0000;">*</span>预售结束时间：</span> <input type="text"
						placeholder="请输入" id="setDate" name="full_presale_endtime" 
						class="preTime"><span>必须是当前时间的后一天</span><br> <span class="title"><span
						style="color:#FF0000;">*</span>发货时间：</span> <select class="selectTime"
						name="full_send_time_type">
						<option value="0"
							<c:if test="${fullSales.full_send_time_type==0}">selected="selected"</c:if>>预售时间结束</option>
								   <option value="1" <c:if test="${fullSales.full_send_time_type==1}">selected="selected"</c:if>>支付成功后</option>
							   </select>
							   <input type="text" placeholder="请输入" name="full_send_day_num" class="afterTime" > 天内<br>
					
					   
					   
					   <span class="title"><span style="color:#FF0000;">*</span>每人限购数量：</span>
						<input type="text" placeholder="请输入" name="full_limit_buy_num" class="count" > 件
					   
				   </div>
				   
				   <div class="pre_sale" id="some_sale_container" style="display: block;">
					   <span class="title"><span style="color:#FF0000;">*</span>预售金额：</span>
					   <input type="text" placeholder="请输入" name="fixed_price_f" class="prePrice" style="width:120px"><span>(元)</span><br>
			
					   <div class="some_sale_price">
					   	  <div class="some_sale_prices some_sale_one">
					   	  	<span class="text">预售持续至尾款支付前一自然日(含)</span> 				           
					   	  </div>
					   	  <div class="some_sale_prices some_sale_two">
					   	  	<span>尾款支付开始时间</span> <input type="text" name="presale_endtime" id="weiDate"  class="some_sale_time weiDate" />
					   	  	<div class="text">持续<span class="text" style="color: red">${pre_hold_time}</span>日</div>
					   	  </div>
					   	  <div class="some_sale_prices some_sale_three">
					   	  	<span class="text">尾款支付</span><span class="text" style="color: red">${pre_send_time}</span><span class="text">日内可发货</span>
					   	  </div>
					   	  <input name="pre_send_time" type="hidden" value="${pre_send_time}"/>
					   	  <input name="pre_hold_time" type="hidden" value="${pre_hold_time}"/>
					   </div> 
					   <div>
					   	  <span class="stepNum">1</span><span class="stepStrip"></span><span class="stepNum">2</span><span class="stepStrip2"></span><span class="stepNum">3</span>
					   </div>
					    <div class="proportion">
					        <span>预付款：</span>
					   	    <select class="selectProportion"  onchange="setWeiProportion(this.value)" name="pre_send_time_type">
							    	<option value="0">请选择</option>
							    <c:forEach items="${payment}" var="payment">
	                        		<option value="${payment.para_key}" >${payment.para_name}</option>
	                    		</c:forEach>
						    </select>
						     <span>%</span>
						    <c:set var="finalPayment" scope="session" value="${100-advanceSale.pre_send_time_type}"/>
						    <span class="weiProportion">尾款：</span><span class="finalPayment">${finalPayment}</span><span>%</span>
						    <span class="fahuo">发货</span>
					   </div>
					
					   <span class="title"><span style="color:#FF0000;">*</span>每人限购数量：</span>
					   <input type="text" placeholder="请输入" name="pre_limit_buy_num" class="count" > 件  
				   </div>
				</td>
			</tr>
			</c:when>
		
		<c:otherwise>
		<tr id="presale_container" style="display: none;">
			<td colspan="2" >
			    <div class="pre_sale_chose">
			       <input type="radio" name="presale_model" value="1" <c:if test="${goods.presale_model==1}">checked</c:if> onchange="changePsPattern()">
			       <span class="presaleName">全额预售</span>
			       <input type="radio"  name="presale_model" value="2" <c:if test="${goods.presale_model==2}">checked</c:if> onchange="changePsPattern()">
			       <span class="presaleName">定金预售</span>
			    </div>
			    
				<div class="pre_sale" id="all_sale_container" style="display: block;">
				   <span class="title"><span style="color:#FF0000;">*</span>预售金额：</span>
				   <input type="text" placeholder="请输入" name="fixed_price_a" class="prePrice" style="width:120px" <c:if test="${goods!=null&&goods.presale_model==1}">value="${fixed_price}"</c:if>/><span>(元)</span><br>
				   <span class="title"><span style="color:#FF0000;">*</span>预售结束时间：</span>
				   <input type="text" placeholder="请输入" id="setDate" <c:if test="${fullSales!=null}">value="${fullSales.full_presale_endtime}"</c:if> name="full_presale_endtime" class="preTime" ><span>必须是当前时间的后一天</span><br>
				   <span class="title"><span style="color:#FF0000;">*</span>发货时间：</span>
			   		<c:choose>
			   			<c:when test="${fullSales!=null}">
			   				<select class="selectTime" name="full_send_time_type">
							   <option value="0" <c:if test="${fullSales.full_send_time_type==0}">selected="selected"</c:if>>预售时间结束</option>
							   <option value="1" <c:if test="${fullSales.full_send_time_type==1}">selected="selected"</c:if>>支付成功后</option>
						   </select>
						   <input type="text" placeholder="请输入" name="full_send_day_num" class="afterTime" <c:if test="${fullSales!=null}">value="${fullSales.full_send_day_num}"</c:if>> 天内<br>
						</c:when>
						<c:otherwise>
							<select class="selectTime" name="full_send_time_type">
							   <option value="0" selected="selected">预售时间结束</option>
							   <option value="1">支付成功后</option>
						   </select>
						   <input type="text" placeholder="请输入" name="full_send_day_num" class="afterTime" <c:if test="${fullSales!=null}">value="${fullSales.full_limit_buy_num}"</c:if>> 天内<br>
						</c:otherwise>
				 	</c:choose>
				   
				   <span class="title"><span style="color:#FF0000;">*</span>每人限购数量：</span>
					<input type="text" placeholder="请输入" name="full_limit_buy_num" class="count" <c:if test="${fullSales!=null}">value="${fullSales.full_limit_buy_num}"</c:if>> 件
				   
			   </div>
			   
			   <div class="pre_sale" id="some_sale_container" style="display: block;">
				   <span class="title"><span style="color:#FF0000;">*</span>预售金额：</span>
				   <input type="text" placeholder="请输入" name="fixed_price_f" class="prePrice" style="width:120px" <c:if test="${goods!=null&&goods.presale_model==2}">value="${fixed_price}"</c:if>/><span>(元)</span><br>
		
				   <div class="some_sale_price">
				   
				   	  <div class="some_sale_prices some_sale_one">
				   	  	<span class="text">预售持续至尾款支付前一自然日(含)</span> 				           
				   	  </div>
				   	  <div class="some_sale_prices some_sale_two">
				   	  	<span>尾款支付开始时间</span> <input type="text" name="presale_endtime" id="weiDate"  class="some_sale_time weiDate" <c:if test="${advanceSale!=null}">value="${advanceSale.presale_endtime}"</c:if>>
				   	  	<div class="text">持续<span class="text" style="color: red">${pre_hold_time}</span>日</div>
				   	  </div>
				   	  <div class="some_sale_prices some_sale_three">
				   	  	<span class="text">尾款支付</span><span class="text" style="color: red">${pre_send_time}</span><span class="text">日内可发货</span>
				   	  </div>
				   	  <input name="pre_send_time" type="hidden" value="${pre_send_time}"/>
				   	  <input name="pre_hold_time" type="hidden" value="${pre_hold_time}"/>
				   </div> 
				   <div>
				   	  <span class="stepNum">1</span><span class="stepStrip"></span><span class="stepNum">2</span><span class="stepStrip2"></span><span class="stepNum">3</span>
				   </div>
				    <div class="proportion">
				        <span>预付款：</span>
				   	    <select class="selectProportion"  onchange="setWeiProportion(this.value)" name="pre_send_time_type">
						    	<option value="0">请选择</option>
						    <c:forEach items="${payment}" var="payment">
                        		<option value="${payment.para_key}" <c:if test="${payment.para_key==advanceSale.pre_send_time_type}">selected="selected"</c:if>>${payment.para_name}</option>
                    		</c:forEach>
					    </select>
					     <span>%</span>
					    <c:set var="finalPayment" scope="session" value="${100-advanceSale.pre_send_time_type}"/>
					    <span class="weiProportion">尾款：</span><span class="finalPayment">${finalPayment}</span><span>%</span>
					    <span class="fahuo">发货</span>
				   </div>
				
				   <span class="title"><span style="color:#FF0000;">*</span>每人限购数量：</span>
				   <input type="text" placeholder="请输入" name="pre_limit_buy_num" class="count" <c:if test="${advanceSale!=null}">value="${advanceSale.pre_limit_buy_num}"</c:if>> 件  
			   </div>
			</td>
		</tr>
		</c:otherwise>
		</c:choose>
		<link rel="stylesheet" type="text/css" href="/component/datetimepicker/jquery.datetimepicker.css"/ >
        <script src="/component/datetimepicker/build/jquery.datetimepicker.full.js"></script>
		<script type="text/javascript">
		$(function(){
			$('#setDate,#weiDate').datetimepicker({
              lang:'ch',
              timepicker:false,
              format:'Y-m-d',
              formatDate:'Y-m-d',
              /* minDate:new Date(), */
              /*minDate:'%Y-%m-{%d+1}',*/
              minDate:0
           });
			changePattern();
			changePsPattern();
			yz();
			// $('#setDate').val(getdate());




        });
			function changePattern() {
				var radioData = $('input:radio[name="sale_mode"]:checked').val();
				if (radioData == '0') {
				     $("#onesale_container").removeAttr("style"); 
				     $("#presale_container").css('display','none');

				 }else{

				     $("#onesale_container").css('display','none'); 
				     $("#presale_container").css('display','block');
				     $("#presale_container").removeAttr("style");

				 }	
			}
			function changePsPattern () {
				var radioData = $('input:radio[name="presale_model"]:checked').val();
			    if (radioData == '1') {

			        $("#all_sale_container").removeAttr("style"); 
			    	$("#some_sale_container").css('display','none'); 
			    }else{

			        $("#all_sale_container").css('display','none'); 
			    	$("#some_sale_container").removeAttr("style"); 
			    }	
			   
			}
			function setWeiProportion(data){
			   var str = 100-data;
			   $('.finalPayment').html(str);
			}
			function yz(){
				$('.prePrice').change(function(){
                        var prePrice = $(this).val()
						if (isNaN(prePrice)) {
							alertTip("请输入非负数");
							$(this).val('');
							return false;
						} else {
							if (prePrice < 0) {
								alertTip("请输入非负数");
								$(this).val('');
								return false;
							}
						}
                 });
				
				//---------------------------
				$("input[name='full_send_day_num']").change(function(){
					var afterTime = $(this).val();
					var re = /^[1-9]+\d*$/;
					var res = re.test(afterTime) 
					if (!res) {
						alertTip("请输入正常符合实际的数据");
						$(this).val('7');
						return false;
					}
					if(afterTime>100){
						alertTip("请输入正常符合实际的数据,不超过100");
						$(this).val('1');
						return false;
					}
				});
				
				// -----------------------
				
				$("input[name='full_limit_buy_num']").change(function(){
					var count = $(this).val();
					var re = /^[1-9]+\d*$/;
					var res = re.test(count) 
					if (!res) {
						alertTip("请输入正常符合实际的数据");
						$(this).val('1');
						return false;
					}
					if(count>100){
						alertTip("请输入正常符合实际的数据,不超过100");
						$(this).val('1');
						return false;
					}
				});
				
				
				// --------------
				$("input[name='pre_limit_buy_num']").change(function(){
					var precount = $(this).val();
				 	var re = /^[1-9]+\d*$/;
					var res = re.test(precount) 
					if (!res) {
						alertTip("请输入正常符合实际的数据");
						$(this).val('1');
						return false;
					}
					if(precount>100){
						alertTip("请输入正常符合实际的数,不超过100");
						$(this).val('1');
						return false;
					}
					
				});
				
			}
		</script>

