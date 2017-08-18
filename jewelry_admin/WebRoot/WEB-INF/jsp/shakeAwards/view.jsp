<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>8"%>
<html>
  <head>
    <title>详情</title>
	  <script>
          $(document).ready(function () {
              if($("[name='prize_type']").val()==1){
                  $("#coupon_id").show();
              }
              if($("[name='prize_type']").val()==0){
                  $("#give_integral").show();
              }
          });
          $(function () {
              $("[name='prize_type']").change(function () {
                  //如果是优惠券
                  if($("[name='prize_type']").val()==1){
                      $("#coupon_id").show();
                      $("#give_integral").hide();
                      $("[name='give_integral']").val("")
                      $("[name='coupon_id']").val("")
                  }
                  if($("[name='prize_type']").val()==0){
                      $("#coupon_id").hide();
                      $("#give_integral").show();
                      $("[name='give_integral']").val("")
                      $("[name='coupon_id']").val("")
                  }
                  if($("[name='prize_type']").val()==""){
                      $("#coupon_id").hide();
                      $("#give_integral").hide();
                      $("[name='give_integral']").val("")
                      $("[name='coupon_id']").val("")
                  }
              });
          })
	  </script>
  </head>
  <body>
  <form id="validateForm" action="/admin/shakeawards/view" method="post">
	  <input type="hidden" name="parameter_id" value="${parameter_id}">
	  <div class="opercontent">
  <div class="table_div">
	  <table width="100%">

		  <tr>
			  <td class="td_left">奖项名称<span class="must_span">*</span></td>
			  <td class="td_right_two">
				 ${shakeAwards.awards_name}
			  </td>
		  </tr>
		  <tr>
			  <td class="td_left">奖品类型<span class="must_span">*</span></td>
			  <td class="td_right_two">
				  <select class="validate " name="prize_type" isrequired="yes" type="select" disabled="disabled" tipmsg="奖品类型" widthtip="70">
					  <option value="">请选择</option>
					  <option value="1" <c:if test="${shakeAwards.prize_type==1}"> selected</c:if>>优惠券</option>
					  <option value="0" <c:if test="${shakeAwards.prize_type==0}"> selected</c:if>>积分</option>
				  </select>
			  </td>
		  </tr>
		  <tr id="give_integral" style="display: none">
			  <td class="td_left">赠送积分值:</td>
			  <td class="td_right_two">
				  <span name="give_integral">${shakeAwards.give_integral}</span>
			  </td>
		  </tr>
		  <tr id="coupon_id" style="display: none">
			  <td class="td_left">赠送优惠卷:</td>
			  <td class="td_right_two">
				  <select class="" name="coupon_id"  type="select" disabled="disabled">
					  <option value="">请选择</option>
					  <c:if test="${!empty couponList}">
						  <c:forEach items="${couponList}" var="item" varStatus="status">
							  <option value="${item.coupon_id}" <c:if test="${shakeAwards.coupon_id==item.coupon_id}"> selected</c:if>>${item.coupon_name}</option>
						  </c:forEach>
					  </c:if>
				  </select>
			  </td>
		  </tr>
		  <tr>
			  <td class="td_left">奖项级别<span class="must_span">*</span></td>
			  <td class="td_right_two">
				  <select  class="validate" name="awards_level"  type="select"  isrequired='yes' widthtip="70"  disabled="disabled"  tipmsg="奖项级别">
					  <option value="">请选择</option>
					  <c:if test="${!empty cfg_awards_level}">
						  <c:forEach items="${cfg_awards_level}" var="item" varStatus="status">
							  <option value="${item.para_key}"
									  <c:if test="${shakeAwards.awards_level==item.para_key}">selected="selected"</c:if>>
									  ${item.para_name}</option>
						  </c:forEach>
					  </c:if>
				  </select>
			  </td>
		  </tr>
		  <%--<tr>--%>
		  <%--<td class="td_left">奖项级别名称<span class="must_span">*</span></td>--%>
		  <%--<td class="td_right_two">--%>
		  <%--<input class="text validate" type="text" name="awards_level_name" isrequired='yes' tipmsg="奖项级别名称"--%>
		  <%--maxlength='30' maxdatalength='30' value="${shakeAwards.awards_level_name}"/>--%>
		  <%--</td>--%>
		  <%--</tr>--%>
		  <tr>
			  <td class="td_left">中奖概率<span class="must_span">*</span></td>
			  <td class="td_right_two">
				  ${shakeAwards.awards_probability}
			  </td>
		  </tr>
		  <tr>
			  <td class="td_left">奖品数量<span class="must_span">*</span></td>
			  <td class="td_right_two">
				 ${shakeAwards.awards_num}
			  </td>
		  </tr>
		  <c:if test="${shakeAwards.awards_id!=null}">
			  <input type="hidden" name="awards_id" value="${shakeAwards.awards_id}"/>
		  </c:if>

	  </table>
  </div>
  <div class="row50 operbtndiv">
	  <input type="hidden" name="token" value="${token}">
	  <input type="button" class="btn return" onclick="returnGo('/admin/shakeawards/list',${parameter_id})" value="返回列表"/>
  </div>
  </div>
  </form>
  </body>
</html> 

