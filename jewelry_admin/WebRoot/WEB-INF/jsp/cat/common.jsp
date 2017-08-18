<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div class="table_div">
	<table width="100%">
		<tr>
			<input type="hidden" name="parent_cat_id" value="${treeBean.up_id}" />
			<td class="td_left">父级分类名称<span class="sp_span">:</span></td>
			<td class="td_right" colspan="3"><input class="text" type="text"
				value="${parent_name}" readonly="true" /></td>
		</tr>

		<tr>
			<td class="td_left">分类名称<span class="must_span">*</span></td>
			<td class="td_right" colspan="3"><input class="text validate"
				type="text" name="cat_name" isrequired="yes" tipmsg="分类名称"
				maxlength='30' maxdatalength="30" value="${cat.cat_name}" /></td>
		</tr>

		<tr>
			<td class="td_left">分类图片<span class="sp_span">:</span></td>
			<td colspan="3" class="td_right" style="width:200px;"><c:set
					var="one_img_name" value="cat_img" /> <c:set var="one_img_url"
					value="${cat.cat_img}" /> <c:set var="one_img_tip" value="请上传分类图片!" />
				<c:set var="one_img_must" value="no" /> <%@ include
					file="/WEB-INF/common/one_image_show.jsp"%>
			</td>

		</tr>

		<tr>
			<td class="td_left">分类英文名称<span class="must_span">*</span></td>
			<td class="td_right" colspan="3"><input class="text validate"
				type="text" name="en_name" isrequired="yes" tipmsg="分类英文名称"
				maxlength='30' maxdatalength="30" value="${cat.en_name}" /></td>
		</tr>

		<tr>
			<td class="td_left">首字母<span class="must_span">*</span></td>
			<td class="td_right" colspan="3"><input class="text validate"
				type="text" name="word_index" tipmsg="首字母" isrequired="yes"
				maxlength='1' maxdatalength="1" value="${cat.word_index}" /></td>
		</tr>

		<tr>
			<td class="td_left">排序<span class="must_span">*</span></td>
			<td class="td_right" colspan="3"><input
				class="text validate sort_no" type="text" name="sort_no"
				isrequired="yes" datatype="jsInt" widthtip="100" tipmsg="排序"
				value="${cat.sort_no}" /></td>
		</tr>

		<tr>
			<td class="td_left">是否显示<span class="must_span">*</span></td>
			<td class="td_right" colspan="3"><select class="validate"
				name="is_show" isrequired="yes" type="select" tipmsg="是否显示"
				widthtip="70">
					<option value="">请选择</option>
					<option value="1"
						<c:if test="${1==cat.is_show}"> selected</c:if>>正常</option>
						<option value="0" <c:if test="${0==cat.is_show}"> selected</c:if>>禁用</option>
				</select>
			</td>
		</tr>
		
		<tr>
			<td class="td_left">备注<span class="sp_span">:</span></td>
			<td  class="td_right" colspan="3">
				<textarea class="validate" name="note" rows="3" cols="40" type="text" maxdatalength="100" >${cat.note}</textarea>
			</td>
		</tr>
		
	</table>
</div>

<input  type="hidden" name="is_sys"  value="0"/> 
<input  type="hidden" id="id" name="id"  value="${treeBean.id}"/> 
<input  type="hidden" id="up_id" name="up_id"  value="${treeBean.up_id}"/> 
<input  type="hidden" id="back_sel_id" name="back_sel_id" value="${treeBean.back_sel_id}"/> 
