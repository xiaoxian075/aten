<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<script type="text/javascript">
    function openIframe(url){
        parent.layer.open({
            type: 2,
            title: '添加',
            shadeClose: false,
            shade: [0.3, '#000'],
            maxmin: true, //开启最大化最小化按钮
            area: ['600px', '420px'],
            content: url
        });
    }
    $(document).ready(function(){
        //分类级联
        $("#cat_id_div").cascadeSel({
            url:"/admin/cat/normalList",
            name:"cat_id",
            init_id:"${cfg_pre_top}"
        });
//        var isEdit=$("#isEdit").val();
//        if(isEdit=="true"){
//            $("#cat_id_div").find("select").attr("disabled","disabled");
//        }
    });
</script>
<div class="table_div">
	<input type="hidden" name="cat_id" value="${cat.cat_id}" /> <input
		type="hidden" id="isEdit" value="${isEdit}" />
	<table width="100%">
		<tr>
			<td class="td_left">前台分类名称<span class="must_span">*</span></td>
			<td class="td_right" colspan="3"><input class="text validate"
				type="text" name="cat_name" isrequired="yes" tipmsg="前台分类名称"
				maxlength='16' maxdatalength="16" value="${cat.cat_name}" /></td>
		</tr>
		<tr>
			<td class="td_left">分类图片<span class="sp_span">:</span></td>
			<td colspan="3" class="td_right" style="width:200px;">
				<c:set var="one_img_name" value="cat_img" /> 
				<c:set var="one_img_url"  value="${cat.cat_img}" />
				<c:set var="one_img_tip" value="请上传分类图片!" />
				<c:set var="one_img_must" value="no" /> 
				<c:set var="one_img_proposal" value="推荐尺寸:240*240,图片大小不能大于2M"/>
				<%@ include file="/WEB-INF/common/one_image_show.jsp"%>
			</td>

		</tr>
		<tr>
			<td class="td_left">排序<span class="must_span">*</span></td>
			<td class="td_right" colspan="3"><input
				class="text validate sort_no" type="text" name="sort_no" maxlength='6' maxdatalength="6"
				isrequired="yes" datatype="jsInt" widthtip="100" tipmsg="排序"
				value="${cat.sort_no}" /></td>
		</tr>
		<c:if test="${!isEdit }">
		<tr>
			<td class="td_left">所属分类<span class="sp_span">:</span></td>
			<td class="td_right_two">
				<div id="cat_id_div" tipmsg="所属分类" setwidth="200" setheight="25"></div>
				<input class="validate" changetip="cat_id_div" type="hidden"
				id="cat_id" name="parent_cat_id" value="${cat.parent_cat_id}" />
			</td>
		</tr>
		</c:if>
		<c:if test="${isEdit }">
		<tr>
			<td class="td_left">所属分类<span class="sp_span">:</span></td>
			<td class="td_right_two">
				${cat.parent_cat_id}
			</td>
		</tr>
		</c:if>
		<tr>
			<td class="td_left">关联商品分类<span class="sp_span">:</span></td>
			<td class="td_right" colspan="3"><input type="button" value="添加"
				class="btn operbtn" onclick="openIframe('/iframe/goodsclassList')" />
				<lable id="cats"> <c:if test="${!empty preGoodsList}">
					<c:forEach items="${preGoodsList}" var="item" varStatus="status">
						<span class="cat_checked" checked_id="${item.cat_id}">
							${item.cat_name} </span>
						<input type="hidden" name="catIds" value="${item.cat_id}">
					</c:forEach>
				</c:if> </lable></td>
		</tr>

		<tr>
			<td class="td_left">是否启用<span class="must_span">*</span></td>
			<td class="td_right" colspan="3"><select class="validate"
				name="state" isrequired="yes" type="select" tipmsg="是否启用"
				widthtip="70">
					<option value="">请选择</option>
					<option value="1"
						<c:if test="${1==cat.state}"> selected</c:if>>启用</option>
						<option value="0" <c:if test="${0==cat.state}"> selected</c:if>>禁用</option>
				</select>
			</td>
		</tr>

		<%@ include file="/WEB-INF/common/search_hidden_field.jsp"%>
	</table>
</div>