<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div class="row50">
	<input type="hidden" name="parent_menu_id" value="${treeBean.up_id}" />
	<p>
	<div class="f_left tb_name">父级菜单名称<span class="sp_span">:</span></div>
	<div class="f_left tb_value">
		<input class="text" type="text" value="${parent_name}" readonly="true" />
	</div>
	</p>
</div>


<div class="row50">
	<p>
	<div class="f_left tb_name">
		菜单名称<span class="must_span">*</span>
	</div>
	<div class="f_left tb_value">
		<input class="text validate" type="text" name="menu_name"
			isrequired="yes" tipmsg="菜单名称" maxlength="20" maxdatalength="20"
			value="${sysmenu.menu_name}" />
	</div>
	</p>
</div>

<div class="row50">
	<p>
	<div class="f_left tb_name">
		菜单地址<span class="must_span">*</span>
	</div>
	<div class="f_left tb_value">
		<input class="text validate" type="text" name="menu_url"
			isrequired="yes" tipmsg="菜单地址" maxlength="60" maxdatalength="60"
			value="${sysmenu.menu_url}" />
	</div>
	</p>
</div>
<div class="row50">
	<p>
	<div class="f_left tb_name">
		打开方式<span class="must_span">*</span>
	</div>
	<div class="f_left tb_value">
		<select class="validate" name="target" isrequired="yes" type="select"
			tipmsg="打开方式">
			<option value="">请选择</option>
			<option value="_self"
				<c:if test="${'_self'==sysmenu.target}"> selected</c:if>>当前窗口打开</option>
						<option value="_blank" <c:if test="${'_blank'==sysmenu.target}"> selected</c:if>>新窗口打开</option>
					  </select>
 				</div>
 			</p>
 		</div>
 		<div class="row50">
 			<p>
 				<div class="f_left tb_name" >是否显示<span class="must_span">*</span></div>
 				<div  class="f_left tb_value">
 					<select class="validate"  name="is_show"  isrequired="yes" type="select" tipmsg="是否显示" widthtip="70">
						<option value="">请选择</option>
						<option value="1" <c:if test="${1==sysmenu.is_show}"> selected</c:if>>正常</option>
						<option value="0" <c:if test="${0==sysmenu.is_show}"> selected</c:if>>禁用</option>
				</select>
 				</div>
 			</p>
 		</div>
 		
 		<div class="row50">
 			<p>
 				<div class="f_left tb_name" >排序<span class="must_span">*</span></div>
 				<div  class="f_left tb_value">
 					<input class="text validate sort_no" name="sort_no" type="text" isrequired="yes" widthtip="100" maxlength="6" 
 					datatype="jsInt" tipmsg="排序"   value="${sysmenu.sort_no}"/>
 				</div>
 			</p>
 		</div>
 		<div class="row50">
 			<p>
 				<div class="f_left tb_name" >备注<span class="sp_span">:</span></div>
 				<div  class="f_left tb_value">
 						<textarea class="validate" name="note" rows="3" cols="40" type="text" maxdatalength="100" >${sysmenu.note}</textarea>
 				</div>
 			</p>
 		</div>
 		<input  type="hidden" id="id" name="plat_role"  value="0"/> <!-- 运营商 -->
 		<input  type="hidden" id="id" name="id"  value="${treeBean.id}"/> 
		<input  type="hidden" id="up_id" name="up_id"  value="${treeBean.up_id}"/> 
		<input  type="hidden" id="back_sel_id" name="back_sel_id" value="${treeBean.back_sel_id}"/> 