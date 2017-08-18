<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<div class="table_div">
	<table width="100%">
		<tr>
			<td class="td_left">角色编码<span class="must_span">*</span></td>
			<td >
				<c:if test="${empty is_update}">
					<input  class="text validate" type="text" name="role_code"  isrequired="yes" datatype="jsLetterOrNum"  tipmsg="角色编码" 
 					maxlength="15"  maxdatalength='15'  value="${role.role_code}"/>
 					<input  class="validate" type="hidden" name="old_role_code" value="${role.role_code}"/>
				</c:if>
				<c:if test="${!empty is_update}">
					${role.role_code}
 					<input  class="validate" type="hidden" name="role_code" value="${role.role_code}"/>
 					<input  class="validate" type="hidden" name="old_role_code" value="${role.role_code}"/>
				</c:if>
			</td>
		</tr>
		<tr>
			<td class="td_left">角色名称<span class="must_span">*</span></td>
			<td >
				<input  class="text validate" type="text" name="role_name"  isrequired="yes"  tipmsg="角色名称" 
 					maxlength="16"  maxdatalength='16'  value="${role.role_name}"/>
			</td>
		</tr>

		<tr>
			<td class="td_left">状态<span class="must_span">*</span></td>
			<td >
 				<select class="validate selectClass" <c:if test="${role.role_code=='syscode'}">disabled="disabled"</c:if>  name="state"  isrequired="yes" type="select" tipmsg="状态" widthtip="70">
					<option value="">请选择</option>
					<option value="1" <c:if test="${role.state==1}"> selected</c:if>>启用</option>
					<option value="0" <c:if test="${role.state==0}"> selected</c:if>>禁用</option>
				</select>
			</td>
		</tr>
		<tr>
			<td class="td_left">角色权限:</td>
			<td >				
				<c:if test="${!empty sysmenuOneList}">
					<c:forEach items="${sysmenuOneList}" var="one">
					<div class="one_menu_div">
							<div class="menu_name_div">
								<label><input id="${one.menu_id}" class="one_box menu_box" type="checkbox" value="${one.menu_id}"/><lable>${one.menu_name}</lable></label>
							</div>
							
							<c:if test="${!empty sysmenuTwoList}">
								<c:forEach items="${sysmenuTwoList}" var="two">
									<!-- 二级菜单 -->	
									<c:if test="${one.menu_id==two.parent_menu_id}">
										<div class="two_menu_div">
											<div class="menu_name_div">
												<label><input id="${one.menu_id}${two.menu_id}" class="two_box menu_box" name="two_box"  value="${two.menu_id}" type="checkbox"/>
												<lable>${two.menu_name}</lable></label>
											</div>
											<ul >
												<c:if test="${!empty sysmenuThreeList}">
													<c:forEach items="${sysmenuThreeList}" var="three">
													 	 <c:if test="${three.parent_menu_id==two.menu_id}">
															<div class="three_menu_div">
																<li>
																	<div class="three_left">
																		<label><input id="${one.menu_id}${two.menu_id}${three.menu_id}" class="three_box menu_box" value="${three.menu_id}" type="checkbox"/><lable>${three.menu_name}</lable></label>
																	</div>  
																	<div class="three_right">
																	
																		<!--权限循环 -->	
																		<c:if test="${!empty powerList}">
																			<c:forEach items="${powerList}" var="power">
																				<%-- <c:if test="${(power.menu_id).indexOf(three.menu_id)>-1}"> --%>
																				 <c:if test="${fn:contains(power.menu_id, three.menu_id)}">
																				
																						
																						 	<label title="${power.power_name}" class="power_li"><input id="${one.menu_id}${two.menu_id}${three.menu_id}${power.power_id}" value="${power.power_id}" class="four_box  power_box" type="checkbox"/>${power.power_name}</label>
																				
																				</c:if>
																	      	</c:forEach>
																	     </c:if>
																	      
																	      
																	</div>      
																	<div class="clear"></div>                             
																</li> 
															</div>
														 </c:if>
													</c:forEach>
												</c:if>
											</ul>
										</div>
									</c:if>
								</c:forEach>
							</c:if>
							
						</div>
					</c:forEach>
				</c:if>
			</td>
		</tr>
		<tr  style="display:none;">
			<td class="td_left">备注:</td>
			<td >
				<textarea class="validate" name="note" rows="3" cols="40" type="text" maxdatalength="200" >${role.note}</textarea>
			</td>
		</tr>
		
	</table>
</div>

<input  type="hidden" id="menu_right" name="menu_right"  value="${role.menu_right}"/>
<input  type="hidden" id="power_right" name="power_right"  value="${role.power_right}"/>
<!-- 运营商角色 -->
<input  type="hidden" name="plat_role"  value="${role.plat_role}"/>
<input  type="hidden" name="is_sys"  value="${role.is_sys}"/>
<%@ include file="/WEB-INF/common/search_hidden_field.jsp"%>