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
                <a href="#">设备管理</a>
            </li>
            <li class="active">POS机管理</li>
        </ul>
    </div>
    <input type="hidden" id="agentUnique" name="agentUnique" value="${info.agentUnique}" />
    <div id="dataTables-example_wrapper" class="dataTables_wrapper form-inline dt-bootstrap no-footer">
        <div class="row">
            <div class="col-xs-12">
                <div class="dataTables_length" id="dataTables-example_length">
                        <label>
                            <input type="search" class="form-control input-sm" placeholder="设备编码、云支付账号" aria-controls="dataTables-example" name="queryBody" id="queryBody" value=""/>
                        </label>
                    <button id="message-query" type="button" class="btn btn-default" style="background-color:#f4f4f4;" ><i class="icon-search"></i> 查询 </button>
                </div>
            </div>
        </div>
    </div>
    <div id="dataTables-example_wrapper1" class="dataTables_wrapper form-inline dt-bootstrap no-footer">
        <div class="row">
            <div class="col-xs-12">
                <div class="dataTables_length" id="dataTables-example_length1">
                    <button id="message-query1" type="button"  onclick="oneKey(1)" class="btn btn-success btn-xs"> 一键启用 </button>
                    <button id="message-query2" type="button" onclick="oneKey(0)" class="btn btn-danger btn-xs"> 一键禁用 </button>
                </div>
            </div>
        </div>
    </div>
    <div class="row" style="overflow-y:auto;">
        <div class="col-xs-12">
            <div class="box">
                <div class="box-body">
                    <div id="DataTables_Table_0_wrapper" class="dataTables_wrapper" role="grid">
                        <table id="user-table" class="table table-bordered table-hover" style="overflow-y:auto; border-spacing: 0px;">
                            <thead>
                                <tr role="row" style="background-color: #f5f5f5;">
                                    <th >代理人</th>
                                    <th >设备编码</th>
                                    <th >激活码</th>
                                    <th >拉卡拉设备编码</th>
                                    <th >拉卡拉激活码</th>
                                    <th >拉卡拉商户号</th>
                                    <th >拉卡拉终端号</th>
                                    <th >设备名称</th>
                                    <th >设备类型</th>
                                    <th >商家姓名</th>
                                    <th >商家云支付账号</th>
                                    <th >商家联系电话</th>
                                    <th >添加时间</th>
                                    <th >状态</th>
                                    <th style="width:150px;">操作</th>
                                </tr>
                            </thead>
                            <tbody role="alert" aria-live="polite" aria-relevant="all">

                            </tbody>
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
    $(function () {
        $.init.initTemplate('tpl');
        var table = $.deviceList.initTables("user-table", null);
        $('#viewdetail').on('hide.bs.modal', function () {
            table.ajax.reload();
        })
    });
    function add(agentUnique){
        window.location="<%=basePath%>rest/admin/deviceList/edit?agentUnique="+agentUnique;
    };
    function edit(id){
        window.location="<%=basePath%>rest/admin/deviceList/edit?id="+id;
    };
    function del(deviceUnique){
        if (confirm("确定要删除数据吗?删除后将无法还原。")) {
            $.ajax({
                url: "<%=basePath%>rest/admin/deviceList/delete",
                data: {deviceUnique:deviceUnique},
                type: "post",
                dataType: "json",
                success: function (json) {
                    alert(json.message);
                    if(json.success) {
                        table.ajax.reload();
                    }
                },
                error: function (error) {
                    alert("删除失败");
                }
            });
        }
    }
    function editSatuts(id,state){

        var content;
        if (state == '1') {
            content = "是否启用当前用户"
        } else {
            content = "是否禁用当前用户"
        }
        var r = confirm(content);
        if (r) {
            $.ajax({
                type: "POST",
                url: "<%=basePath%>rest/admin/deviceList/editStatus",
                traditional: true,
                data: {id: id, state: state},
                dataType: "json",
                cache: false,
                success: function (data) {
                    if (data.success) {
                        alert("操作成功");
                        table.ajax.reload();
                    }
                },
                error: function (error) {
                    alert("操作失败");
                }
            });
        }
    }

    function oneKey(state){
        var content;
        if (state == '1') {
            content = "是否启用所有设备"
        } else {
            content = "是否禁用所有设备"
        }
        var r = confirm(content);
        if (r) {
            $.ajax({
                type: "POST",
                url: "<%=basePath%>rest/admin/deviceList/oneKey",
                traditional: true,
                data: {state: state},
                dataType: "json",
                cache: false,
                success: function (data) {
                    if (data.success) {
                        alert(data.message);
                        table.ajax.reload();
                    }else{
                        alert(data.message);
                    }
                },
                error: function (error) {
                    alert(data.message);
                }
            });
        }
    }
</script>