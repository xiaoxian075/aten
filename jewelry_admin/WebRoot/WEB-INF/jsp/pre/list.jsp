<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<title>前台分类管理</title>
<script type="text/javascript">
        $(document).ready(function(){
            //分类级联
            $("#cat_id_div").cascadeSel({
                url:"/admin/cat/normalList",
                name:"cat_id",
                init_id:"${cfg_pre_top}"
            });
        });

    </script>
</head>
<body>
	<form action="/admin/pre/list" method="post">
		<div class="list_oper_div">
			<input class="btn ol_btn" type="button" value="新增前台分类"
				onclick="addInfo('/admin/pre/add');" />
            <input
                    class="btn ol_btn" type="button" value="排序"
                    onclick="sortInfo('/admin/pre/sort');" />
		</div>
		<div class="searchDiv">
			<table class="searchTable">
				<tr>
                    <td>前台分类id:</td>
                    <td><input type="text" name="cat_id_vague_s"
                               value="${cat_id_vague_s}" /></td>
					<td>前台分类名称:</td>
					<td><input type="text" name="cat_name_vague_s"
						value="${cat_name_vague_s}" /></td>
					<td>所属分类:</td>
					<td>
						<div id="cat_id_div" tipmsg="所属分类" setwidth="200" setheight="25"></div>
						<input class="validate" changetip="cat_id_div" type="hidden"
						id="cat_id" name="parent_cat_id_s" value="${parent_cat_id_s}" />
					</td>
					<td>状态:</td>
					<td><select class="validate" name="state_s" type="select"
						tipmsg="请选择是否显示" widthtip="100">
							<option value="">请选择</option>
							<option value="0"
								<c:if test="${state_s==0}"> selected</c:if>>已禁用</option>
                        <option value="1" <c:if test="${state_s==1}"> selected</c:if>>已启用</option>
                    </select>
                </td>
                <td>
                    <input class="btn ol_colorbtn ol_greenbtn" type="button" value="查询" onclick="goInfo('/admin/pre/list');"/>
                    <input class="btn ol_colorbtn ol_bredbtn" type="button" value="清空" onclick="clearSearch('/admin/pre/list');"/>
                </td>
            </tr>
        </table>
    </div>
    <div class="show_line">
        <%@ include file="/WEB-INF/common/pageshowrow.jsp"%>
    </div>
    <div class="list_div">
        <table  id="list_table"  border="0" cellspacing="0" cellpadding="0">
            <tr>
                <th width="3%">
                    <input class="all" type="checkbox" />
                </th>
                <th width="5%">排序</th>
                <th width="10%">分类id</th>

                <th width="10%">分类名称</th>

                <th width="10%">所属分类</th>

                <th width="10%">状态</th>

                <th width="10%">操作</th>
            </tr>
            <c:if test="${!empty preList}">
                <c:forEach items="${preList}" var="item" varStatus="status">
                    <tr>
                        <td>
                            <input class="ids" type="checkbox" value="${item.cat_id}"/>
                        </td>
                        <td>
                            <input class="sort_id" type="hidden" value="${item.cat_id}"/>
                            <input class="sort_val sort_no" type="text" value="${item.sort_no}" maxlength="6" />
                        </td>

                        <td>${item.cat_id}</td>
                        <td style="text-align: left;padding-left: 10px;">${item.cat_name}</td>

                        <td style="text-align: left;padding-left: 10px;">
                                ${item.parent_level_name}
                        </td>
                        <td>
                            <c:if test="${item.state==1}"><span class="span_green">已启用</span></c:if>
                            <c:if test="${item.state==0}"><span class="span_red">已禁用</span></c:if>
                        </td>

                        <td>
                            <input class="btn ol_colorbtn ol_bluebtn" type="button" value="查看" onclick="editInfo('/admin/pre/info','${item.cat_id}');"/>
                            <input class="btn ol_colorbtn ol_bluebtn" type="button" value="修改" onclick="editInfo('/admin/pre/edit','${item.cat_id}');"/>
                            <c:if test="${item.state==1}">
                                <input class="btn ol_colorbtn ol_redbtn" type="button" value="禁用" onclick="commonInfo('/admin/pre/limitState','${item.cat_id}','确定禁用${item.cat_name},并同步禁用其下级分类？');"/>
                            </c:if>
                            <c:if test="${item.state==0}">
                                <input class="btn ol_colorbtn ol_greenbtn" type="button" value="启用" onclick="commonInfo('/admin/pre/enableState','${item.cat_id}','确定启用${item.cat_name},并同步启用其下级分类？');"/>
                            </c:if>
                            <input class="btn ol_colorbtn ol_redbtn" type="button" value="删除" onclick="commonInfo('/admin/pre/delete','${item.cat_id}','确定删除   ${item.cat_name},并同步删除其下级分类？');"/>
                        </td>
                    </tr>
                </c:forEach>
            </c:if>
            <c:if test="${empty preList}"><td colspan="7">暂无数据</td></c:if>
        </table>
    </div>
    <div class="batchDiv">
        <span class="batch_span"><input class="all" type="checkbox" />全选</span>
        <input class="btn ol_colorbtn ol_redbtn" type="button" value="批量删除" onclick="commonBatchInfo('/admin/pre/delete','确定批量删除,并同步删除其下级分类？');"/>
        <input class="btn ol_colorbtn ol_greenbtn" type="button" value="批量启用" onclick="commonBatchInfo('/admin/pre/batchEnableState','确定批量启用,并同步启用其下级分类？');"/>
        <input class="btn ol_colorbtn ol_redbtn" type="button" value="批量禁用" onclick="commonBatchInfo('/admin/pre/batchLimitState','确定要批量禁用,并同步禁用其下级分类？');"/>
    </div>
    <div class="page_contain">
        <%@ include file="/WEB-INF/common/pagelist.jsp"%>
    </div>
    <%@ include file="/WEB-INF/common/list_hidden_value.jsp"%>
</form>
</body>
</html>