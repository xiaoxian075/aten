<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div class="row50">
	<p>
	<div class="f_left tb_name">
		权限名称<span class="must_span">*</span>
	</div>
	<div class="f_left tb_value">
		<input class="text validate" type="text" name="power_name"
			isrequired="yes" tipmsg="权限名称" maxlength="30"
			value="${power.power_name}" />
	</div>
	</p>
</div>

<div class="row50">
	<p>
	<div class="f_left tb_name">
		平台类型<span class="must_span">*</span>
	</div>
	<div class="f_left tb_value">
		<select class="validate" id="plat_role" name="plat_role"
			isrequired="yes" type="select" tipmsg="平台类型" widthtip="70">
			<option value="">请选择</option>
			<option value="0"
				<c:if test="${power.plat_role==0}"> selected</c:if>>运营商</option>
					</select>
 				</div>
 			</p>
 		</div>

 		<div class="row50">
 			<p>
 				<div class="f_left tb_name" >所属菜单<span class="must_span">*</span></div>
 				<div  class="f_left tb_value">
 					<div id="menu_id_div" tipmsg="所属菜单" setwidth="200" setheight="25">请先选择平台类型</div>
	 				<input class="validate" changetip="menu_id_div" type="hidden" isrequired="yes" id="menu_id"
	 			 		 name="menu_id"  value="${power.menu_id}"/>	
 				</div>
 			</p>
 		</div>

 		<div class="row50">
 			<p>
 				<div class="f_left tb_name" >权限地址<span class="must_span">*</span></div>
 				<div  class="f_left tb_value">
 					<input  class="text validate" type="text" name="url"   isrequired="yes"   tipmsg="权限地址" 
 					maxlength="100" maxdatalength="100"   value="${power.url}"/>
 				</div>
 			</p>
 		</div>
 		
 		<div class="row50">
 			<p>
 				<div class="f_left tb_name" >是否控制权限<span class="must_span">*</span></div>
 				<div  class="f_left tb_value">
 					<select class="validate" name="is_control_power" isrequired="yes" type="select" tipmsg="是否控制权限" widthtip="70">
						<option value="">请选择</option>
						<option value="1" <c:if test="${1==power.is_control_power}"> selected</c:if>>是</option>
						<option value="0" <c:if test="${0==power.is_control_power}"> selected</c:if>>否</option>
					</select>
 				</div>
 			</p>
 		</div>
 		
 		<div class="row50">
 			<p>
 				<div class="f_left tb_name" >导航名称<span class="sp_span">:</span></div>
 				<div  class="f_left tb_value">
 					<input  class="text " type="text"  name="path_name" 
 					maxlength="30"   maxdatalength="30"  value="${power.path_name}"/>
 				</div>
 			</p>
 		</div>
 		
 		<div class="row50">
 			<p>
 				<div class="f_left tb_name" >备注<span class="sp_span">:</span></div>
 				<div  class="f_left tb_value">
 					<textarea class="validate" name="note" rows="3" cols="40" type="text" maxdatalength="150" >${power.note}</textarea>
 				</div>
 			</p>
 		</div>

		<input  type="hidden" name="power_id"   value="${power.power_id}"/>
		<%@ include file="/WEB-INF/common/search_hidden_field.jsp"%>