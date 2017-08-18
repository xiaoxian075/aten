<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div class="table_div">
	<table width="100%">
		<tr >
			<td class="td_left">地区名称<span class="must_span">*</span></td>
			<td class="td_right_two">

				<input  class="text validate" type="text" name="area_name" isrequired='yes'    tipmsg="地区名称"
						maxlength='50'   maxdatalength='50'/>
			</td>
		</tr>
		<tr >
			<td class="td_left">行政编码<span class="must_span">*</span></td>
			<td class="td_right_two"> 

				<input  class="text validate" type="text" name="xz_code" isrequired='yes'  datatype="jsLetterOrNum"  tipmsg="行政编码"
						maxlength='20'   maxdatalength='20'/>
			</td>
		</tr>
		<%--<tr >--%>
			<%--<td class="td_left">邮政编码<span class="must_span">*</span></td>--%>
			<%--<td class="td_right_two">--%>

				<%--<input  class="text validate" type="text" name="post_code" isrequired='yes'    tipmsg="邮政编码"--%>
						<%--maxlength='20'   maxdatalength='20'/>--%>
			<%--</td>--%>
		<%--</tr>--%>
		<tr>
			<td class="td_left">所属区域<span class="must_span">*</span></td>
			<td class="td_right_two">
				<select  class="validate" name="region"  type="select"  isrequired='yes'  setwidth="180"  tipmsg="所属区域">
					<option value="">请选择</option>
					<c:if test="${!empty cfg_area_region}">
					<c:forEach items="${cfg_area_region}" var="item" varStatus="status">
						<option value="${item.para_key}" >${item.para_name}</option>
					</c:forEach>
					</c:if>
				</select>

			</td>
		</tr>
		<tr>
			<td class="td_left">上级地区<span class="must_span">*</span></td>
			<td class="td_right_two">
				<div id="area_id_div" tipmsg="地区" setwidth="200" setheight="25"></div>
				<input  class="validate"  changetip="area_id_div" type="hidden"  id="area_id"  name="parent_area_id"  isrequired='yes'   />
			</td>
		</tr>
		<tr>
			<td class="td_left">状态<span class="must_span">*</span></td>
			<td class="td_right_two">
				<select class="validate"  name="state" isrequired='yes' type="select" tipmsg="是否显示" widthtip="100">
					<option value="">请选择</option>
					<option value="0" >禁用</option>
					<option value="1">启用</option>
				</select>
			</td>
		</tr>

		<%@ include file="/WEB-INF/common/search_hidden_field.jsp"%>
	</table>
</div>

<script type="text/javascript">
    $(document).ready(function(){
        //地区级联
        $("#area_id_div").cascadeSel({
            url:"/admin/area/normalList",
            name:"area_id",
            init_id:"${cfg_top_area}",
            li_id:"area_id",
            li_name:"area_name",
        });
    });
</script>
