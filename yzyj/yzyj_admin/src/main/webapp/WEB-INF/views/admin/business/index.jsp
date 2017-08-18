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
                <a href="#">商户管理</a>
            </li>
            <li class="active">商户列表</li>
        </ul>
    </div>
    <input type="hidden" id="agentUnique" value="${info.agentUnique}" />
    <div id="dataTables-example_wrapper" class="dataTables_wrapper form-inline dt-bootstrap no-footer">
        <div class="row">
            <div class="col-xs-12">
                <div class="dataTables_length" id="dataTables-example_length">
                    <label>商家编码:</label>
                    <label>
                        <input type="search" class="form-control input-sm" placeholder="商家编码" aria-controls="dataTables-example" name="merchantNo" value=""/>
                    </label>
                    <label>商家名称:</label>
                    <label>
                        <input type="search" class="form-control input-sm" placeholder="商家名称" aria-controls="dataTables-example" name="merchantName" value=""/>
                    </label>
                    <label>商家云帐号:</label>
                    <label>
                        <input type="search" class="form-control input-sm" placeholder="商户云帐号" aria-controls="dataTables-example" name="yunPayAccount" value=""/>
                    </label>
                    <label>时间:</label>
                    <label>
                        <input name="sdate" id="stratTime" size="9" class="form-control input-sm"  placeholder="起止时间" type="text" onFocus="WdatePicker({maxDate:'#F{$dp.$D(\'endTime\',{M:3});}'})" />
                    </label>&nbsp;-&nbsp;
                    <label>
                        <input  name="edate" id="endTime" size="10" class="form-control input-sm"  placeholder="结束时间" type="text"   onFocus="WdatePicker({minDate:'#F{$dp.$D(\'stratTime\',{d:0});}'})" />
                    </label>
                    <label>代理人:</label>
                    <label>
                        <input type="search" class="form-control input-sm" placeholder="代理人" aria-controls="dataTables-example" name="agencyName" value=""/>
                    </label>
                    <button id="message-query" type="button" class="btn btn-default" style="background-color:#f4f4f4;" ><i class="icon-search"></i> 查询 </button>
                    <button type="button" class="btn btn-default" style="background-color:#f4f4f4;" onclick="add()">添加商户</button>
                    <button type="button" class="btn btn-default" style="background-color:#f4f4f4;" onclick="importExcel()">导入商户</button>
                </div>
            </div>
        </div>
    </div>
    <input type="hidden" id="msg" value="${msg}" />
    <div class="row" style="overflow-y:auto;">
        <div class="col-xs-12">
            <div class="box">
                <div class="box-body">
                    <div id="DataTables_Table_0_wrapper" class="dataTables_wrapper" role="grid">
                        <table id="user-table" class="table table-bordered table-hover" style="overflow-y:auto; border-spacing: 0px;">
                            <thead>
                                <tr role="row" style="background-color: #f5f5f5;">
                                    <th >商户编码</th>
                                    <th >商户名称</th>
                                    <th >法人姓名</th>
                                    <th >营业执照号</th>
                                    <th >商户联系人姓名</th>
                                    <th >账户号</th>
                                    <th >账户名称</th>
                                    <th >云支付账号</th>
                                    <th >添加时间</th>
                                    <th >代理人</th>
                                    <th >操作</th>
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
        var table = $.businessList.initTables("user-table", null);
        var msg = $("#msg").val();
        if(msg != ""){
            alert(msg);
        }
        $('#viewdetail').on('hide.bs.modal', function () {
            table.ajax.reload();
        })
    });

    function add(){
        window.location="<%=basePath%>rest/admin/business/editBusiness";
    };
    function edit(id){
        window.location="<%=basePath%>rest/admin/business/editBusiness?id="+id;
    };
    function reApproval(id,state){
        var content;
        if (state == '1' || state == '4') {
            content = "确认要提交审批?"
        }
        var r = confirm(content);
        if (r) {
            $.ajax({
                type: "POST",
                url: "<%=basePath%>rest/admin/business/editApprovalStatus",
                traditional: true,
                data: {id: id, status: state},
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
    };
    function importExcel(){
        $.ajax({
            type: "GET",
            url: "<%=basePath%>rest/admin/business/showBrand",
            traditional: true,
            dataType: "html",
            cache: false,
            success: function (data) {
                $("#brand-content").replaceWith(data);
                $("#brand-edit").modal("show");
            },
            error: function (error) {
                alert("导入失败");
            }
        });
    }
</script>