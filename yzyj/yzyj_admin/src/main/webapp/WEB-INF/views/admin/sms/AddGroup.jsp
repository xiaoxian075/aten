<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script type="text/javascript" src="<%=basePath%>assets/js/ajaxfileupload.js"></script>
<style>
    .col-sm-3 img{max-width:50px;_width:expression(this.width > 50 ? "50px" : this.width);}
</style>
<%--<script type="text/javascript" src="<%=basePath%>assets/plugins/selectize.js/examples/js/jquery.js"></script>--%>
<link href="<%=basePath%>assets/file/css/fileinput.min.css" media="all" rel="stylesheet" type="text/css" />
<script src="<%=basePath%>assets/file/js/fileinput.min.js" type="text/javascript"></script>
<script src="<%=basePath%>assets/file/js/fileinput_locale_zh.js" type="text/javascript"></script>
<div class="modal-content" id="brand-content">
    <div class="modal-header">
        <button type="button" class="close"
                data-dismiss="modal" aria-hidden="true">
            &times;
        </button>
        <h4 class="modal-title" id="myModalLabel">
            添加 群组
        </h4>
    </div>
    <div class="modal-body">
        <form class="form-horizontal" role="form" id="addForm" method="POST" enctype="multipart/form-data" action="<%=basePath%>rest/admin/sms/addGroup">
            <div class="form-group" id="mcDiv">
                <div class="group">
                    <label class="col-sm-3 control-label">输入群组名称</label>
                    <div class="col-sm-9">
                        <input type="text" class="form-control" name="accName" id="addName" maxlength="20" style="width:200px;" />
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <input type="button" id="brand-close" class="btn btn-default" data-dismiss="modal" value="关闭" />
                <input type="button" class="btn btn-info" id="brand-save" value="提交" onclick="mySubmit();"/>
            </div>
        </form>
    </div>
    <script>
        function mySubmit() {
            var accName = $("#addName").val();
            if(accName==""||accName==null){
                alert("请填写群名");
                return false;
            }

            $.ajax({
                type: "POST",
                url: "<%=basePath%>rest/admin/sms/addGroup",
                traditional: true,
                dataType: "json",
                data: {"accName":accName},
                cache: false,
                success: function (data) {
                    if (data.code==0) {
                        alert("添加成功");
                        $("#brand-close").click();
                        table.ajax.reload();
                    } else {
                        alert(data.desc);
                    }


                },
                error: function (error) {
                    alert("失败");
                }
            });
        }
//        $('#addForm').submit(function () {
//            var accName = $("#addName").val();
//            if(accName==""||accName==null){
//                alert("请填写群名");
//                return false;
//            }
//
//            if (isActive!=1) {
//                alert("群名已存在");
//                return false;
//            }
//
//            return true;
//        });
        <%--function checkName() {--%>
            <%--var groupName = $("#addName").val();--%>
            <%--$.ajax({--%>
                <%--type: "POST",--%>
                <%--url: "<%=basePath%>rest/admin/sms/checkGroupName",--%>
                <%--traditional: true,--%>
                <%--dataType: "json",--%>
                <%--data: {"groupName":groupName},--%>
                <%--cache: false,--%>
                <%--success: function (data) {--%>
                    <%--if (data.code==0) {--%>
                        <%--isActive = 1;--%>
                    <%--} else {--%>
                        <%--isActive = 0;--%>
                        <%--alert(data.desc);--%>
                    <%--}--%>


                <%--},--%>
                <%--error: function (error) {--%>
                    <%--alert("失败");--%>
                <%--}--%>
            <%--});--%>
        <%--}--%>
    </script>

</div>
