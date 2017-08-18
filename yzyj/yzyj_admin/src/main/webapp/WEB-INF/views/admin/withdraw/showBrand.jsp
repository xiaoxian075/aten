<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:include page="/WEB-INF/views/admin/common/head.jsp"/>
<div class="modal-content" id="brand-content">
    <div class="modal-header">
        <button type="button" class="close"
                data-dismiss="modal" aria-hidden="true">
            &times;
        </button>
        <h4 class="modal-title" id="myModalLabel">
            导入提现
        </h4>
    </div>
    <div class="modal-body">
        <form class="form-horizontal" role="form" id="editForm">
            <div class="form-group" id="mcDiv">
                <div class="group">
                    <label class="col-sm-3 control-label">导入文件：</label>
                        <input id="file1" type="file" name="file1" class="file-loading" onchange="fileType(this)">
                    <div class="col-sm-9">
                    </div>
                </div>
            </div>
        </form>
        <%--<a href="<%=basePath%>picCommon/download/商户模板.xls">下载导入模板</a>--%>
        <%--<span style="color: red">请确保商户云付通账号填写正确</span>--%>
        <div class="modal-footer">
            <button type="button" class="btn btn-info" id="brand-save"> 提交 </button>
            <button type="button" class="btn btn-default"
                    data-dismiss="modal">关闭
            </button>
        </div>
    </div>
    <script>
        $(function(){
            $("#file1").fileinput({
                language: 'zh', //设置语言
                allowedFileExtensions: ['xls', 'xlsx'],//接收的文件后缀
                showUpload: false, //是否显示上传按钮
                showCaption: false,//是否显示标题
                overwriteInitial: true,
                browseClass: "btn btn-primary", //按钮样式
                previewFileIcon: "<i class='glyphicon glyphicon-king'></i>",
                maxFileSize: 5000,
                maxFilesNum: 1
            });
        })
        $("#brand-save").click(function(){
            if($("#file1").val()==""){
                alert("请选择导入excel文件");
                return false;
            }
            $.ajaxFileUpload({
                url: '<%=basePath%>rest/admin/withdraw/upExcelSendMoneyRead',
                fileElementId: 'file1',
                type: "POST",
                dataType : 'json',
                success: function (data) {
                    if (data.success) {
                        alert("导入成功");
                        $("#brand-content").modal("hide");
                        location.href = '<%=basePath%>rest/admin/withdraw/posIndex'
                    }else{
                        alert(data.message);
                        $("#brand-content").modal("hide");
                        location.href = '<%=basePath%>rest/admin/withdraw/posIndex'
                    }
                },
                error: function (error) {
                    alert("导入失败");
                }
            });
        });

        function fileType(obj){
            var fileType = obj.value.substr(obj.value.lastIndexOf(".")).toLowerCase();//获得文件后缀名
            if(fileType != '.xlsx' && fileType != '.xls'){
                alert("请上传excel文件");
                $("#file1").val('');
                return false;
            }
        }
    </script>
</div>
