<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
  <head>
    <title>修改系统地区管理</title>
  </head>
  <body>
  <form id="validateForm" action="/admin/area/update" method="post">
	  <div class="opercontent">
		  <div class="table_div">
			  <table width="100%">
				  <tr >
					  <td class="td_left">地区名称<span class="must_span">*</span></td>
					  <td class="td_right_two">

						  <input  class="text validate" type="text" name="area_name" isrequired='yes'  value="${area.area_name}"  tipmsg="请输入地区名称"
								  maxlength='50'   maxdatalength='50'/>
					  </td>
				  </tr>
				  <tr >
					  <td class="td_left">行政编码<span class="must_span">*</span></td>
					  <td class="td_right_two">

						  <input  class="text validate" type="text" name="xz_code" isrequired='yes'  value="${area.xz_code}"   tipmsg="请输入行政编码"
								  maxlength='20'   maxdatalength='20'/>
					  </td>
				  </tr>
				  <tr>
					  <td class="td_left">所属区域<span class="must_span">*</span></td>
					  <td class="td_right_two">
						  <select  class="validate" name="region"  type="select"  isrequired='yes'    tipmsg="请选择所属区域">
							  <option value="">请选择</option>
							  <c:if test="${!empty cfg_area_region}">
							  <c:forEach items="${cfg_area_region}" var="item" varStatus="status">
								  <option <c:if test="${item.para_key==area.region}">selected="selected"</c:if> value="${item.para_key}" >${item.para_name}</option>
							  </c:forEach>
						  </select>
						  </c:if>
					  </td>
				  </tr>
			  </table>
			  <input  type="hidden" name="area_id" value="${area.area_id}"/>
			  <input  type="hidden" name="parameter_id" value="${area.area_id}"/>
		  </div>
	  		<div class="row50 operbtndiv">
	  			<input type="button" value="修改地区" class="btn operbtn" onclick="submitData();"/>
	  			<input type="button" class="btn return" onclick="returnGo('/admin/area/list')" value="返回列表"/>
	  		</div>
		  <%@ include file="/WEB-INF/common/search_hidden_field.jsp"%>
	</div>
  </form>	
</body>
</html>

