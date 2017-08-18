<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<title>修改分类</title>
</head>
<body>
	<form id="validateForm" action="/admin/commpara/preupdate"
		method="post">
		<div class="opercontent">
			<input type="hidden"
				   name="para_code" value="${commpara.para_code}" />

			<div class="row50">
				<p>
				<div class="f_left tb_name">
					首页分类名称<span class="must_span">*</span>
				</div>
				<div class="f_left tb_value">
					<input class="text validate" type="text" name="para_name"
						isrequired="yes" tipmsg="首页分类名称" maxlength="16" maxdatalength='16'
						value="${commpara.para_name}" />
				</div>
				</p>
			</div>

			<div class="row50">
				<p>
				<div class="f_left tb_name">
					分类编码<span class="must_span">*</span>
				</div>
				<div class="f_left tb_value">
					<input class="text validate" type="text" name="para_key"
						isrequired="yes" tipmsg="分类编码" maxlength="16" maxdatalength='16'
						value="${commpara.para_key}" /> <input type="hidden"
						name="old_para_key" value="${commpara.para_key}" />
				</div>
				</p>
			</div>

			<div class="row50">
				<p>
				<div class="f_left tb_name">
					排序<span class="must_span">*</span>
				</div>
				<div class="f_left tb_value">
					<input class="text validate sort_no" style="width: 260px"
						type="text" name="sort_no" isrequired="yes" datatype="jsInt"
						widthtip="100" tipmsg="排序" maxlength="6" maxdatalength='6'
						value="${commpara.sort_no}" />
				</div>
				</p>
			</div>

			<div class="row50">
				<p>
				<div class="f_left tb_name">
					状态<span class="must_span">*</span>
				</div>
				<div class="f_left tb_value">
					<select class="validate select selectClass" name="state"
						isrequired="yes" type="select" tipmsg="状态" widthtip="70">
						<option value="">请选择</option>
						<option value="1"
							<c:if test="${commpara.state==1}"> selected</c:if>>启用</option>
					  <option value="0" <c:if test="${commpara.state==0}"> selected</c:if>>禁用</option>
				  </select>
			  </div>
			  </p>
		  </div>

		  <input  type="hidden" name="para_id"  value="${commpara.para_id}"/>
		  <%@ include file="/WEB-INF/common/search_hidden_field.jsp"%>
		  <div class="row50 operbtndiv">
	  			<input type="button" value="修改首页分类" class="btn operbtn" onclick="submitData();"/>
	  			<input type="button" class="btn return" onclick="returnGo('/admin/commpara/pre')" value="返回列表"/>
	  		</div>
	</div>
  </form>	
</body>
</html> 

