<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div class="table_div">
	<table width="100%">
		<tr>
			<td class="td_left">页面标题<span class="must_span">*</span></td>
			<td class="td_right_two">
				<input  class="text validate" type="text" name="pageTitle"  isrequired='yes'  tipmsg="页面标题"
		 		  maxlength='60'  maxdatalength='60'    value="${customizedPage.pageTitle}"/>
			</td>
		</tr>
		<tr>
			<td class="td_left">页面类型<span class="must_span">*</span></td>
			<td class="td_right_two">
                <select id="page_type" name="pageType" type="select" class="validate" isrequired='yes' tipmsg="页面类型">
                    <option value="">请选择</option>
                    <option value="10" <c:if test="${customizedPage.pageType==10}"> selected</c:if>>app原生页面</option>
                    <option value="11" <c:if test="${customizedPage.pageType==11}"> selected</c:if>>h5代码页面</option>
                </select>
            </td>
		</tr>

<c:if test="${customizedPage.pageUnique!=null}">
	<input type="hidden" name="pageUnique" value="${customizedPage.pageUnique}"/>
</c:if>

<%@ include file="/WEB-INF/common/search_hidden_field.jsp"%>
	</table>
</div>
<script type="text/javascript">
    $(function () {
        var isEdit="${isEdit}";
        if(isEdit=="true"){
            $("#page_unique").attr("disabled","disabled");
        }
    });
</script>
