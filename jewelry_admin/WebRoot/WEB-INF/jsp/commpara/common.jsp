<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div class="row50">
	<p>
	<div class="f_left tb_name">
		字典编码<span class="must_span">*</span>
	</div>
	<div class="f_left tb_value">
		<input class="text validate" type="text" name="para_code" datatype="jsLetterOrNum"
			isrequired="yes" tipmsg="字典编码" maxlength="20" maxdatalength='20'
			value="${commpara.para_code}" /> <input type="hidden"
			name="old_para_code" value="${commpara.para_code}" />
	</div>
	</p>
</div>

<div class="row50">
	<p>
	<div class="f_left tb_name">
		字典值名称<span class="must_span">*</span>
	</div>
	<div class="f_left tb_value">
		<input class="text validate" type="text" name="para_name"
			isrequired="yes" tipmsg="字典值名称" maxlength="16" maxdatalength='16'
			value="${commpara.para_name}" />
	</div>
	</p>
</div>

<div class="row50">
	<p>
	<div class="f_left tb_name">
		字典值<span class="must_span">*</span>
	</div>
	<div class="f_left tb_value">
		<input class="text validate" type="text" name="para_key"
			isrequired="yes" tipmsg="字典值" maxlength="16" maxdatalength='16'
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
		<input class="text validate sort_no" type="text"
			name="sort_no" isrequired="yes" datatype="jsInt" widthtip="100"
			tipmsg="排序" maxlength="6" maxdatalength='6'
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
				<select class="validate select " name="state"
					isrequired="yes" type="select" tipmsg="状态" widthtip="70">
						<option value="">请选择</option>
						<option value="1" <c:if test="${commpara.state==1}"> selected</c:if>>启用</option>
						<option value="0" <c:if test="${commpara.state==0}"> selected</c:if>>禁用</option>
					</select>
 				</div>
 			</p>
 		</div>

<input  type="hidden" name="para_id"  value="${commpara.para_id}"/>
<%@ include file="/WEB-INF/common/search_hidden_field.jsp"%>