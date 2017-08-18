<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
  <head>
    <title>修改分类</title>
    <%@ include file="/WEB-INF/common/img_control.jsp"%>
      <script>
          $(document).ready(function(){
              //分类级联
              $("#cat_id_div").cascadeSel({
                  url:"/admin/cat/normalList",
                  name:"cat_id",
                  init_id:"${cfg_pre_top}"
              });

            $("#cat_id_div").find("select").attr("disabled","disabled");

          });
      </script>
  </head>
  <body>
  <form id="validateForm" action="/admin/pre/info" method="post">
	  <div class="opercontent">
          <div class="table_div">
              <input type="hidden"  name="cat_id" value="${cat.cat_id}"/>
              <input type="hidden"  id="isEdit" value="${isEdit}"/>
              <table width="100%">
                  <tr>
                      <td class="td_left">前台分类名称:</td>
                      <td  class="td_right" colspan="3">
                        ${cat.cat_name}
                      </td>
                  </tr>
                  <tr>
                      <td class="td_left">分类图片:</td>
                      <td colspan="3" class="td_right" style="width:200px;">
                          <c:set var="one_img_url" value="${cat.cat_img}"/>
                          <%@ include file="/WEB-INF/common/one_image_view.jsp"%>
                      </td>

                  </tr>
                  <tr>
                      <td class="td_left">排序:</td>
                      <td  class="td_right" colspan="3">
                          ${cat.sort_no}
                      </td>
                  </tr>

                  <tr >
                      <td class="td_left">所属分类:</td>
                      <td class="td_right_two">
                        ${cat.parent_name}
                      </td>
                  </tr>

                  <tr>
                      <td class="td_left">关联商品分类:</td>
                      <td  class="td_right" colspan="3">
                          <lable id="cats">
                              <c:if test="${!empty preGoodsList}">
                                  <c:forEach items="${preGoodsList}" var="item" varStatus="status">
							<span class="cat_checked" checked_id="${item.cat_id}">
								${item.cat_name}
							</span>
                                      <input type="hidden" name="catIds" value="${item.cat_id}">
                                  </c:forEach>
                              </c:if>

                          </lable>
                      </td>
                  </tr>

                  <tr>
                      <td class="td_left">是否启用:</td>
                      <td  class="td_right" colspan="3">
                          <c:if test="${1==cat.state}"><span style="color:green">启用</span></c:if>
                          <c:if test="${0==cat.state}"><span style="color:red">禁用</span></c:if>
                      </td>
                  </tr>
                  <%@ include file="/WEB-INF/common/search_hidden_field.jsp"%>

              </table>
          </div>
	  		<div class="row50 operbtndiv">
	  			<input type="button" class="btn return" onclick="returnGo('/admin/pre/list')" value="返回列表"/>
	  		</div>
	</div>
  </form>	
</body>
</html> 

