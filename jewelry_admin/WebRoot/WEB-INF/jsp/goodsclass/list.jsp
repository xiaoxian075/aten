<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<title>商品分类管理</title>
<%--<script src="/component/lay/pageGrid/treeTool.js"></script>--%>
<script type="text/javascript">
        $(document).ready(function () {
            //分类级联
            $("#cat_id_div").cascadeSel({
                url: "/admin/cat/normalList",
                name: "cat_id",
                init_id: "${cfg_goods_top}"
            });
        });

    </script>
</head>
<body>
	<form action="/admin/goodsclass/list" method="post">
		<div class="list_oper_div">
			<input class="btn ol_btn" type="button" value="新增商品分类"
				onclick="addInfo('/admin/goodsclass/add');" />
            <input
                    class="btn ol_btn" type="button" value="排序"
                    onclick="sortInfo('/admin/goodsclass/sort');" />
		</div>
		<div class="searchDiv">
			<table class="searchTable">
				<tr>
					<td>分类名称:</td>
					<td><input type="text" name="cat_name_vague_s"
						value="${cat_name_vague_s}" /></td>
					<td>所属分类:</td>
					<td>
						<div id="cat_id_div" tipmsg="所属分类" setwidth="200" setheight="25"></div>
						<input class="validate" changetip="cat_id_div" type="hidden"
						id="cat_id" name="parent_cat_id_s" value="${parent_cat_id_s}" /> <%--<input  id="cat" value="${parent_name}" onclick="openZtree(this)" placeholder="请选择"--%>
						<%--cyType="treeTool" cyProps="url:'/admin/goodsclass/select',name:'parent_cat_id_s',value:'${parent_cat_id_s}'"/>--%>


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
                    <input class="btn ol_colorbtn ol_greenbtn" type="button" value="查询"
                           onclick="goInfo('/admin/goodsclass/list');"/>
                    <input class="btn ol_colorbtn ol_bredbtn" type="button" value="清空"
                           onclick="clearSearch('/admin/goodsclass/list');"/>
                </td>
            </tr>
        </table>
    </div>
    <div class="show_line">
        <%@ include file="/WEB-INF/common/pageshowrow.jsp" %>
    </div>
    <div class="list_div">
        <table id="list_table" border="0" cellspacing="0" cellpadding="0">
            <tr>
                <th width="3%">
                    <input class="all" type="checkbox"/>
                </th>
                <th width="5%">排序</th>

                <th width="10%">分类ID</th>

                <th width="10%">分类名称</th>

                <th width="20%">所属分类</th>

                <th width="5%">分成比例(百分比)</th>
                <th width="5%">手工费(元)</th>

                <th width="5%">状态</th>

                <th width="10%">操作</th>
            </tr>
            <c:if test="${!empty goodsclassList}">
                <c:forEach items="${goodsclassList}" var="item" varStatus="status">
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
                                ${item.divide_rate}%
                        </td>
                        <td>
                                ${item.manual_fee}元
                        </td>
                        <td>
                            <c:if test="${item.state==1}"><span class="span_green">已启用</span></c:if>
                            <c:if test="${item.state==0}"><span class="span_red">已禁用</span></c:if>
                        </td>

                        <td>
                            <input class="btn ol_colorbtn ol_bluebtn" type="button" value="查看"
                                   onclick="editInfo('/admin/goodsclass/info','${item.cat_id}');"/>
                            <input class="btn ol_colorbtn ol_bluebtn" type="button" value="修改"
                                   onclick="editInfo('/admin/goodsclass/edit','${item.cat_id}');"/>
                            <c:if test="${item.state==1}">
                                <input class="btn ol_colorbtn ol_redbtn" type="button" value="禁用"
                                       onclick="commonInfo('/admin/goodsclass/limitState','${item.cat_id}','确定禁用${item.cat_name},并同步禁用其下级分类？');"/>
                            </c:if>
                            <c:if test="${item.state==0}">
                                <input class="btn ol_colorbtn ol_greenbtn" type="button" value="启用"
                                       onclick="commonInfo('/admin/goodsclass/enableState','${item.cat_id}','确定启用${item.cat_name},并同步启用其下级分类？');"/>
                            </c:if>
                            <input class="btn ol_colorbtn ol_redbtn" type="button" value="删除"
                                   onclick="commonInfo('/admin/goodsclass/delete','${item.cat_id}','确定删除   ${item.cat_name},并同步删除其下级分类？');"/>
                        </td>
                    </tr>
                </c:forEach>
            </c:if>
            <c:if test="${empty goodsclassList}">
                <td colspan="12">暂无数据</td>
            </c:if>
        </table>
    </div>
    <div class="batchDiv">
        <span class="batch_span"><input class="all" type="checkbox"/>全选</span>
        <input class="btn ol_colorbtn ol_redbtn" type="button" value="批量删除"
               onclick="commonBatchInfo('/admin/goodsclass/delete','确定批量删除,并同步删除其下级分类？');"/>
        <input class="btn ol_colorbtn ol_greenbtn" type="button" value="批量启用"
               onclick="commonBatchInfo('/admin/goodsclass/batchEnableState','确定批量启用,并同步启用其下级分类？');"/>
        <input class="btn ol_colorbtn ol_redbtn" type="button" value="批量禁用"
               onclick="commonBatchInfo('/admin/goodsclass/batchLimitState','确定要批量禁用,并同步禁用其下级分类？');"/>
    </div>
    <div class="page_contain">
        <%@ include file="/WEB-INF/common/pagelist.jsp" %>
    </div>
    <%@ include file="/WEB-INF/common/list_hidden_value.jsp" %>
</form>
</body>
</html>