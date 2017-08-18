<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<title>地区管理</title>
</head>
<body>
	<form action="/admin/area/list" method="post">
		<div class="list_oper_div">
			<input class="btn ol_btn" type="button" value="新增地区"
				onclick="addInfo('/admin/area/add');" />
		</div>
		<div class="searchDiv">
			<table class="searchTable">
				<tr>
					<td>地区名称:</td>
					<td><input type="text" name="area_name_vague_s"
						value="${area_name_vague_s}" /></td>
					<td>地区行政编码:</td>
					<td><input type="text" name="xz_code_vague_s"
						value="${xz_code_vague_s}" /></td>
					<td>状态:</td>
					<td><select name="state_s" type="select" value="${state_s}">
							<option value="">请选择</option>
							<option value="1"
								<c:if test="${state_s==1}"> selected</c:if>>启用</option>
                        <option value="0" <c:if test="${state_s==0}"> selected</c:if>>禁用</option>
                    </select>
                </td>
                <td>行政等级:</td>
                <td>
                    <select name="area_level_s" value="${area_level_s}" type="select" >
                        <option value="">请选择</option>
                 <c:if test="${!empty areaLevelList}">
                      <c:forEach items="${areaLevelList}" var="item" varStatus="status">
                            <option value="${item.para_key}" <c:if test="${item.para_key==area_level_s}">selected</c:if>>${item.para_name}</option>
                        </c:forEach>
                    </select>
                    </c:if>
                </td>
                <td>上级地区:</td>
                <td>
                    <div id="area_id_div" tipmsg="请选择地区!" setwidth="200" setheight="25"></div>
                    <input class="" changetip="area_id_div" type="hidden"  id="area_id"  name="parent_area_id_s"  value="${parent_area_id_s}"/>
                </td>
                <td>
                    <input class="btn ol_colorbtn ol_greenbtn" type="button" value="查询" onclick="searchInfo('/admin/area/list');"/>
                    <input class="btn ol_colorbtn ol_bredbtn" type="button" value="清空" onclick="clearSearch('/admin/area/list');"/>
                </td>
            </tr>
        </table>
    </div>
    <div class="show_line">
        <%@ include file="/WEB-INF/common/pageshowrow.jsp"%>
    </div>
    <div class="list_div">
        <table id="list_table"  border="0" cellspacing="0" cellpadding="0">
            <tr>
                <th width="3%">
                    <input class="all" type="checkbox" />
                </th>
                <th width="10%">地区名称</th>
                <th width="10%">地区行政编码</th>
                <th width="10%">行政级别</th>
                <th width="10%">上级地区名称</th>
                <th width="10%">状态</th>
                <th width="16%">操作</th>
            </tr>
<c:if test="${!empty areaList}">
    <c:forEach items="${areaList}" var="item"  varStatus="status">
                    <tr>
                        <td>
                            <input class="ids" type="checkbox" value="${item.area_id}"/>
                        </td>
                        <td>${item.area_name}</td>
                        <td>${item.xz_code}</td>
                        <td>
                            <c:if test="${item.area_level==1}">省级</c:if>
                            <c:if test="${item.area_level==2}">市级</c:if>
                            <c:if test="${item.area_level==3}">区级</c:if>
                            <c:if test="${item.area_level==4}">县级</c:if>

                        </td>
                        <td>${item.parent_area_name}</td>
                        <td>
                            <c:if test="${item.state==1}"><span class="span_green">已启用</span></c:if>
                            <c:if test="${item.state==0}"><span class="span_red">已禁用</span></c:if>
                        </td>
                        <td class="list_td_left">
                            <input class="btn ol_colorbtn ol_bluebtn" type="button" value="查看" onclick="editInfo('/admin/area/view','${item.area_id}');"/>
                            <input class="btn ol_colorbtn ol_bluebtn" type="button" value="修改" onclick="editInfo('/admin/area/edit','${item.area_id}');"/>
                            <input class="btn ol_colorbtn ol_redbtn" type="button" value="删除" onclick="commonInfo('/admin/area/delete','${item.area_id}','确定删除${item.area_name}地区？（同步删除下级地区）');"/>
                            <!-- 状态操作 -->
                            <c:if test="${item.state==1}">
                                <input class="btn ol_colorbtn ol_redbtn" type="button" value="禁用" onclick="commonInfo('/admin/area/limitState','${item.area_id}','确定禁用${item.area_name}地区？（同步禁用下级地区）');"/>
                            </c:if>
                            <c:if test="${item.state==0}">
                                <input class="btn ol_colorbtn ol_greenbtn" type="button" value="启用" onclick="commonInfo('/admin/area/enableState','${item.area_id}','确定启用${item.area_name}地区？（同步启用上下级地区）');"/>
                            </c:if>

                        </td>
                    </tr>
    </c:forEach>
</c:if>
            <c:if test="${empty areaList}"><td colspan="7">暂无数据</td></c:if>
        </table>
    </div>
    <div class="batchDiv">
        <span class="batch_span"><input class="all" type="checkbox" />全选</span>
        <input class="btn ol_colorbtn ol_bredbtn" type="button" value="批量删除" onclick="commonBatchInfo('/admin/area/batchDelete','确定批量删除所选地区？（同步删除下级地区）');"/>
        <input class="btn ol_colorbtn ol_bredbtn" type="button" value="批量禁用" onclick="commonBatchInfo('/admin/area/batchLimitState','确定批量禁用所选地区？（同步禁用下级地区）');"/>
        <input class="btn ol_colorbtn ol_greenbtn" type="button" value="批量启用" onclick="commonBatchInfo('/admin/area/batchEnableState','确定批量启用所选地区？（同步启用上下级地区）');"/>


    </div>
    <div class="page_contain">
        <%@ include file="/WEB-INF/common/pagelist.jsp"%>
    </div>
    <%@ include file="/WEB-INF/common/list_hidden_value.jsp"%>
</form>
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
</body>
</html>

