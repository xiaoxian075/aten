<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
  <head>
    <title>修改会员提现帐单</title>
  </head>
  <body>
  <form id="validateForm" action="/admin/accountwithdrawbill/update" method="post">
	  <div class="opercontent">
		  <div class="table_div">
			  <table width="100%">
				  <tr>
					  <td class="td_left"><span style="font-size: 20px;">会员信息</span></td>
				  </tr>

				    <tr>
						<td class="td_left">会员账号：</td>
						<td class="td_right_two">${account.login_name}</td>
					</tr>
					<tr>
						<td class="td_left">当前可提现收益：</td>
						<td class="td_right_two">${account.format_earnings}</td>
					</tr>
					<tr>
						<td class="td_left">提现金额：</td>
						<td class="td_right_two">${accountWithdrawBill.amount}</td>
					</tr>
					<tr>
						<td class="td_left">提现时间：</td>
						<td class="td_right_two">${accountWithdrawBill.create_time}</td>
					</tr>
					<tr>
						<td class="td_left">银行开户行：</td>
						<td class="td_right_two">${accountWithdrawBill.opening_bank}</td>
					</tr>
					<tr>
						<td class="td_left">银行卡号：</td>
						<td class="td_right_two">${accountWithdrawBill.card_no}</td>
					</tr>
				  <tr><td class="td_left"></td><td class="td_right_two"></td></tr>
				  <tr>
					  <td class="td_left"><span style="font-size: 20px;">审批意见</span></td>
				  </tr>

				  <tr>
					  <td class="td_left">是否同意<span class="sp_span">:</span></td>
					  <td class="td_right_two">
						  <input type="radio" value="1" name="audit_state"  checked="checked">同意
						  <input type="radio" value="2"  name="audit_state" >不同意
				  </tr>
				  <tr>
					  <td class="td_left">备注<span class="sp_span">:</span></td>
					  <td class="td_right_two">
						<textarea  name="audit_note" cols="35" rows="5"  maxlength='100' maxdatalength='100' ></textarea>
					  </td>

				  </tr>



				  <c:if test="${accountWithdrawBill.id!=null}">
					  <input type="hidden" name="id" value="${accountWithdrawBill.id}"/>
					  <input type="hidden" name="parameter_id" value="${accountWithdrawBill.id}"/>
				  </c:if>

				  <%@ include file="/WEB-INF/common/search_hidden_field.jsp"%>
			  </table>
		  </div>
	  		<div class="row50 operbtndiv">
	  			<input type="hidden" name="token" value="${token}">
	  			<input type="button" value="提交" class="btn operbtn" onclick="submitData();"/>
	  			<input type="button" class="btn return" onclick="returnGo('/admin/accountwithdrawbill/list')" value="返回列表"/>
	  		</div>
	</div>
  </form>	
</body>
</html> 

