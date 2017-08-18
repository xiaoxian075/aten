<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() +  path+"/";
%>
<!DOCTYPE html>
<html>
<style>
    html { overflow-x:hidden; }
</style>
<jsp:include page="/WEB-INF/views/admin/common/head.jsp"/>
<body style="background-color: #fff;">
<div class="breadcrumbs" id="breadcrumbs" style="margin-top:5px;">
    <ul class="breadcrumb">
        <li>
            <span class="glyphicon glyphicon-home"></span>
            <a href="#" target="main" style="padding-left:5px;margin-left:5px;">首页</a>
        </li>
        <li>
            <a href="#">系统管理</a>
        </li>
        <li class="active">权限管理</li>
    </ul>
</div>
<div class="row" style="overflow-y:auto;">
    <div class="col-xs-12">
        <div class="box">
            <div class="box-body">
                <div id="DataTables_Table_0_wrapper" class="dataTables_wrapper" role="grid">
                    <table class="table table-bordered table-hover text-center">
                        <tr>
                            <td>
                                <div class="col-sm-10">
                                    <label>角色名称</label>
                                    <select id="roleSelect" size="5" class="form-control" name="roleId" style="height: auto">
                                        <c:forEach items="${roleList}" var="role">
                                            <option value="${role.id}">${role.roleName}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </td>
                            <td >
                                <div class="zTreeDemoBackground left" >
                                    <label class="control-label bolder blue">菜单</label>
                                    <ul id="menuZtree" class="ztree"></ul>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td></td>
                            <td>
                                <input type="button" class="btn btn-primary" id="checkAll"
                                       value="全选"/>
                                <input type="button" class="btn btn-primary" id="checkOther"
                                       value="反选"/>
                                <input type="button" class="btn btn-primary" id="checkNone"
                                       value="全否"/>
                            </td>
                        </tr>
                        <tr>
                            <td colspan="2">
                                <input type="button" class="btn btn-success btn-rf-save"
                                       value="保存"/>
                            </td>
                        </tr>
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
<div id="brand-edit" class="modal fade" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content" id="brand-content">
        </div>
    </div>
</div>
<!-- /.main-container-inner -->
<a href="#" id="btn-scroll-up" class="btn-scroll-up btn btn-sm btn-inverse">
    <i class="icon-double-angle-up icon-only bigger-110"></i>
</a>

<!-- /.main-container -->
<script id="tpl" type="text/x-handlebars-template">
    {{#each func}}
    <button type="button" class="btn btn-{{this.type}} btn-xs" onclick="{{this.fn}}">{{this.name}}</button>
    {{/each}}
</script>
<script>
    var treeId = "menuZtree";
    var treeObj;
    function checkOrCancelNode(treeObj, nodes, checked) {
        for (var i = 0, l = nodes.length; i < l; i++) {
            treeObj.checkNode(nodes[i], checked, true);
        }
    }
    function getTreeObjectById(treeId) {
        return $.fn.zTree.getZTreeObj(treeId);
    }

    $('#checkAll').click(function () {
        treeObj = getTreeObjectById(treeId);
        if (treeObj) {
            treeObj.checkAllNodes(true);
        }
    });
    $('#checkNone').click(function () {
        treeObj = getTreeObjectById(treeId);
        if (treeObj) {
            treeObj.checkAllNodes(false);
        }
    });
    $('#checkOther').click(function () {
        var treeObj = getTreeObjectById(treeId);
        if (treeObj) {
            //获取已选中的节点
            var checkNodes = treeObj.getCheckedNodes(true);
            //获取未选中的节点
            var unCheckNodes = treeObj.getCheckedNodes(false);
            checkOrCancelNode(treeObj, checkNodes, false);
            checkOrCancelNode(treeObj, unCheckNodes, true);
        }
    });
    //双击select下拉列表选项
    $('#roleSelect').on('dblclick', function () {
        var roleid = $('#roleSelect').val();
        $.menutree.initMenuTree(roleid);
    })
    $(".btn-rf-save").click(function () {
        var roleid = $('#roleSelect').val();
        if ('' == roleid || undefined == roleid||roleid==null) {
            alert("请选择一个角色");
            return;
        }
        var r = confirm("是否给选中的角色授予选中的权限")
        if (r) {
            //获取选中节点
            var checkNodes = getTreeObjectById("menuZtree").getCheckedNodes(true);
            var nodeid = [];
            for (var i = 0, l = checkNodes.length; i < l; i++) {
                nodeid.push(checkNodes[i].id);
            }
            $.ajax({
                type: "POST",
                url: "<%=basePath%>rest/admin/auth/auth",
                traditional: true,
                data: {roleId: roleid, menuIds: nodeid},
                dataType: "json",
                cache: false,
                success: function (data) {
                    if (data.success) {
                        alert(data.message);
                    }
                },
                error: function (error) {
                    alert(111);
                }
            });
        }
    })
</script>