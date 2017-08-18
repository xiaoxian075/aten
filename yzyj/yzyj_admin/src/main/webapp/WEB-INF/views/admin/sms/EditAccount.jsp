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
            修改会员
        </h4>
    </div>
    <div class="modal-body">
        <form class="form-horizontal" role="form" id="addForm" method="POST" enctype="multipart/form-data" action="<%=basePath%>rest/admin/sms/editAccount">
            <input type="hidden" name="id" value="${smsAccount.id}" />
            <div class="form-group" >
                <div class="group">
                    <label class="col-sm-3 control-label">姓名</label>
                    <div class="col-sm-9">
                        <input type="text" class="form-control" name="accName" id="addName" maxlength="20" value="${smsAccount.name}" style="width:200px;"/>
                    </div>
                </div>
            </div>
            <div class="form-group" id="mcDiv">
                <div class="group">
                    <label class="col-sm-3 control-label">性别</label>
                    <div class="col-sm-9">
                        <input name="addSex" type="radio" value=1 <c:if test="${smsAccount.sex==1}">checked="true"</c:if> /><label>男</label>
                        <input name="addSex" type="radio" value=2 <c:if test="${smsAccount.sex==2}">checked="true"</c:if> /><label>女</label>
                    </div>
                </div>
            </div>

            <div class="form-group" >
                <div class="group">
                    <label class="col-sm-3 control-label">手机号</label>
                    <div class="col-sm-9">
                        <input type="text" class="form-control" name="phone" maxlength="16" value="${smsAccount.phone}" style="width:200px;"/>
                    </div>
                </div>
            </div>

            <div class="form-group" >
                <div class="group">
                    <label class="col-sm-3 control-label">所属群组</label>

                    <div class="col-sm-9">
                        <%--<select class="form-control input-sm" name="groupId" id="addGroupId" style="width:200px;">--%>
                        <%--</select>--%>
                        <%--<input type="hidden" name="groupId" value="${smsAccount.id}" />--%>
                        <%--<input type="hidden" name="groupName" value="${smsAccount.name}" />--%>
                        <label style="color:#356635;font-size:14px">${groupName}</label>
                    </div>
                </div>
            </div>



            <div class="modal-footer">
                <button type="button" class="btn btn-default"
                        data-dismiss="modal">关闭
                </button>
                <button type="submit" class="btn btn-info" id="brand-save">
                    提交
                </button>
            </div>
        </form>
    </div>
    <script>
        $('#addForm').submit(function () {
            var accName = $("#addName").val();
            if(accName==""||accName==null){
                alert("请填写姓名");
                return false;
            }

            var sex = $("input[name='addSex']:checked").val();
            if(sex==""||sex==null){
                alert("请选择性别");
                return false;
            }

            var phone = $("input[name='phone']").val();
            if(phone==""||phone==null){
                alert("请填写手机号");
                return false;
            }

            return true;
        });
    </script>

</div>
