<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<script src="/component/lay/pageGrid/treeTool.js"></script>
<div class="table_div">
	<table width="100%">
		<tr>
			<td class="td_left">部门编号<span class="must_span">*</span></td>
			<td class="td_right_two"><input class="text validate"
				type="text" name="org_code" isrequired='yes' id="orgCode" datatype="jsLetterOrNum"
				tipmsg="部门编号" maxlength='20' maxdatalength='20'
				value="${organize.org_code}" /> <span id="code_wrong"
				style="display:none;color:red">* 部门名编号已存在,请重新输入</span></td>
		</tr>
		<tr>
			<td class="td_left">部门名称<span class="must_span">*</span></td>
			<td class="td_right_two"><input class="text validate"
				type="text" name="org_name" isrequired='yes' tipmsg="部门名称"
				maxlength='30' maxdatalength='30' value="${organize.org_name}" /></td>
		</tr>
		<%--<tr >--%>
		<%--<td class="td_left">上级部门编号<span class="must_span">*</span></td>--%>
		<%--<td class="td_right_two">--%>
		<%--<input class="text validate" type="text" id="parentCode" style="background-color: #dbdbdb" readonly="readonly" name="parent_org_code"--%>
		<%--maxlength='22' maxdatalength='22' value="${organize.parent_org_code}"/>--%>
		<%--</td>--%>
		<%--</tr>--%>

		<tr>
			<td class="td_left">上级部门<span class="sp_span">:</span></td>
			<td class="td_right_two">
				<%--<input class="text validate" type="text" style="background-color: #dbdbdb" readonly="readonly"--%>
				<%--id="parentName" name="parent_org_name"--%> <%--maxlength='30' maxdatalength='30' value="${organize.parent_org_name}"/>--%>
				<input value="${organize.parent_org_name}"
				valueId="${organize.parent_org_id}" id="parent" cyType="treeTool"
				cyProps="url:'/admin/organize/select',name:'parent_org_id'"
				onclick="openZtree(this)" placeholder="请选择上级菜单" class="text" />
			</td>
		</tr>
		<%--<tr>--%>
		<%--<td class="td_left" ></td>--%>
		<%--<td class="td_right_two">--%>
		<%--<div class="zTreeDemoBackground left">--%>
		<%--<ul id="treeDemo" class="ztree" style="border:1px solid #999;width:252px;height:300px;overflow: auto;"></ul>--%>
		<%--</div>--%>
		<%--</td>--%>
		<%--</tr>--%>

		<c:if test="${organize.org_id!=null}">
			<input id="orgId" type="hidden" name="org_id"
				value="${organize.org_id}" />
		</c:if>
		<input type="hidden" name="parameter_id" value="${parameter_id}">
		<%--<input id="parent_org_id" type="hidden" name="parent_org_id" value="${organize.parent_org_id}"/>--%>
		<%@ include file="/WEB-INF/common/search_hidden_field.jsp"%>
	</table>
</div>

