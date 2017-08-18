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
            添加会员
        </h4>
    </div>
    <div class="modal-body">
        <form class="form-horizontal" role="form" id="addForm" method="POST" enctype="multipart/form-data" action="<%=basePath%>rest/admin/sms/addAccount">
            <div class="form-group" >
                <div class="group">
                    <label class="col-sm-3 control-label">姓名</label>
                    <div class="col-sm-9">
                        <input type="text" class="form-control" name="accName" id="addName" maxlength="20" style="width:200px;" placeholder="姓名，20个字符以内"/>
                    </div>
                </div>
            </div>
            <div class="form-group" id="mcDiv">
                <div class="group">
                    <label class="col-sm-3 control-label">性别</label>
                    <div class="col-sm-9">
                        <input name="addSex" type="radio" value=1 /><label>男</label>
                        <input name="addSex" type="radio" value=2 /><label>女</label>
                    </div>
                </div>
            </div>

            <div class="form-group" >
                <div class="group">
                    <label class="col-sm-3 control-label">手机号</label>
                    <div class="col-sm-9">
                        <input type="text" class="form-control" id="phone" name="phone" maxlength="16" style="width:200px;" placeholder="手机号，16个字符以内" />
                    </div>
                </div>
            </div>

            <div class="form-group" >
                <div class="group">
                    <label class="col-sm-3 control-label">所属群组</label>

                    <div class="col-sm-9">
                        <%--<select class="form-control input-sm" name="groupId" id="addGroupId" style="width:200px;">--%>
                        <%--</select>--%>
                            <input type="hidden" name="groupId" value="${groupId}" />
                            <input type="hidden" name="groupName" value="${groupName}" />
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
        $(function() {
            $("#phone").keyup(function(){
                $(this).val($(this).val().replace(/[^0-9+]/g,''));
            }).bind("paste",function(){  //CTR+V事件处理  
                $(this).val($(this).val().replace(/[^0-9+]/g,''));
            }).css("ime-mode", "disabled"); //CSS设置输入法不可用
        });

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
