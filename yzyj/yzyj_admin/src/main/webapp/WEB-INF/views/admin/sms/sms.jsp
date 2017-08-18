<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:include page="/WEB-INF/views/admin/common/head.jsp"/>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() +  path+"/";
%>
<!DOCTYPE html>
<html>
<style>
    html { overflow-x:hidden; }
    .goodsh{
        border:none;
        /*font-size:10px;*/
        text-align:left;
        margin-top:5px;
        margin-left:15px;
        margin-right:40px;
        margin-bottom:30px;
    }
    .goodsh tr td {
        height:80px;
    }
    .goodsh td {
        padding-right: 10px;
    }

    .a-upload {
        padding: 4px 10px;
        height: 40px;
        line-height: 20px;
        position: relative;
        cursor: pointer;
        color: #888;
        background: #fafafa;
        border: 1px solid #ddd;
        border-radius: 4px;
        overflow: hidden;
        display: inline-block;
        *display: inline;
        *zoom: 1
    }

    .a-upload  input {
        position: absolute;
        font-size: 100px;
        right: 0;
        top: 0;
        opacity: 0;
        filter: alpha(opacity=0);
        cursor: pointer
    }

    .a-upload:hover {
        color: #444;
        background: #eee;
        border-color: #ccc;
        text-decoration: none
    }

    .file {
        position: relative;
        display: inline-block;
        //background: #D0EEFF;
        border: 1px solid #99D3F5;
        border-radius: 4px;
        padding: 4px 12px;
        overflow: hidden;
        color: #1E88C7;
        text-decoration: none;
        text-indent: 0;
        line-height: 20px;
    }
    .file input {
        position: absolute;
        font-size: 100px;
        right: 0;
        top: 0;
        opacity: 0;
    }
    .file:hover {
        background: #AADFFD;
        border-color: #78C3F3;
        color: #004974;
        text-decoration: none;
    }
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
            <a href="#">短信服务</a>
        </li>
        <li class="active">短信群发</li>
    </ul>
</div>
<div id="dataTables-example_wrapper" class="dataTables_wrapper form-inline dt-bootstrap no-footer">
    <div class="row">
        <div class="col-xs-12">
            <div class="dataTables_length" id="dataTables-example_length">

                <table class="goodsh">
                    <tr>
                        <td style="width:150px">选择主题：</td>
                        <td style="width:300px">
                            <select class="form-control input-sm" name="title" id="title" style="width:300px" onchange="getSmsModel();">
                                <option value=0 selected="selected">选择主题</option>
                            </select>
                        </td>
                    </tr>

                    <tr>
                        <td>发送内容：</td>
                        <td>
                            <textarea name="content" id="content" rows="5" disabled="true" style="width:500px;resize:none;" onkeyup="this.value=this.value.replace(/[^\r\n0-9\,+]/g,'');"></textarea>
                        </td>
                    </tr>


                   <tr>
                        <td>导入方式：</td>
                        <td>
                            <input name="type" type="radio" class="inType" value="1" /><label>输入号码</label>
                            <input name="type" type="radio" class="inType" value="2" /><label>文件导入</label>
                            <input name="type" type="radio" class="inType" value="3" /><label>通讯录</label>
                        </td>
                    </tr>

                    <tr>
                        <td style="width:150px"><div id="myType1"></div></td>
                        <td style="width:300px"><div id="myType2"></div></td>
                    </tr>

                    <tr>
                        <td>发送号码：</td>
                        <td>
                            <textarea name="mobile" id="mobile" rows="10" style="width:800px;resize:none;" placeholder="输入发送手机号（多个手机号使用半角逗号进行分隔），号码不可超过1000个"></textarea>
                        </td>
                    </tr>

                    <tr>
                        <td>发送方式：</td>
                        <td>
                            <input name="sendType" type="radio" class="sendType" value=1 /><label>立即发送</label>
                            <input name="sendType" type="radio" class="sendType" value=2 /><label>定时发送</label>
                        </td>
                    </tr>

                    <tr>
                        <td style="width:150px"></td>
                        <td style="width:300px"><div id="myType3"></div></td>
                    </tr>

                    <tr>
                        <td style="text-align:right;"><input id="message-summit" type="button" class="btn btn-sm btn-success" value="提交" onclick="mysubmit();"/></td>
                        <td style="text-align:left;"><input id="message-reset" type="button" class="btn btn-default" value="重置" onclick="myReset();"/></td>
                    </tr>
               </table>

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
        $("#mobile").keyup(function(){
            $(this).val($(this).val().replace(/[^0-9,+]/g,''));
        }).bind("paste",function(){  //CTR+V事件处理  
            $(this).val($(this).val().replace(/[^0-9,+]/g,''));
        }).css("ime-mode", "disabled"); //CSS设置输入法不可用

        $(".inType").click(function(){
            var type = $("input[name='type']:checked").val();
            if (type==1) {
                $("#myType1").html("");
                $("#myType2").html("");
            } else if (type==2) {
                var str = "<a href='javascript:;' class='file'>选择文件<input class='a-upload' type='file' id='file1' name='file1' class='file' onchange='fileType(this)' /></a><span><label id='myShowFile'></label></span><br>";
                //var str = "<input type='file' id='file1' name='file1' class='file' onchange='fileType(this)' /><span><label id='myShowFile'></label></span><br>";
                str += "1.可使用txt、xls文件进行导入<br>";
                str += "2.点此下载  <a href='<%=basePath%>/picCommon/download/短信群发.txt' download='msg_model.txt'>txt导入模板</a>、<a href='<%=basePath%>/picCommon/download/短信群发.xls' download='msg_model.xls'>xls导入模板</a><br><br>";
                $("#myType1").html("");
                $("#myType2").html(str);
            } else if (type==3) {
                var str = "<select class='form-control input-sm' name='groupId' id='groupId' style='width:300px' onchange='getGroupMobile();'><option value=0 selected='selected'>选择主题</option></select>";
                $("#myType1").html("选择群组:");
                $("#myType2").html(str);
                initAllGroup();
            }
        });



        $(".sendType").click(function(){
            var type = $("input[name='sendType']:checked").val();
            if (type==1) {
                $("#myType3").html("");
            } else if (type==2) {
                var str = "<input name='sendTime' id='sendTime' size='19' class='form-control input-sm'  placeholder='选择时间' type='text' onFocus='myTime();' />";
                $("#myType3").html(str);
            }
        });

        init();
    });

    function myTime() {
        WdatePicker({ dateFmt: 'yyyy-MM-dd HH:mm:ss',minDate:'%y-%M-%d %H:%m:%s'});
    }

    function init() {
        $.ajax({
            type: "POST",
            url: "<%=basePath%>rest/admin/sms/signList",
            traditional: true,
            dataType: "json",
            data: {},
            cache: false,
            success: function (data) {
                //data = JSON.parse(data);
                if (data.code==0) {
                    var info =  data.t;
                    for (var i=0; i < info.length; i++) {
                        $("#title").append("<option value='"+info[i].id+"'>"+info[i].title+"</option>");
                    }
                    getSmsModel();
                } else {
                    alert(data.desc);
                }
            },
            error: function (error) {
                alert("初始化失败");
            }
        });
    }

    function fileType(obj){
        var fileType = obj.value.substr(obj.value.lastIndexOf(".")).toLowerCase();//获得文件后缀名
        if(fileType != '.txt' && fileType != '.xls'){
            alert("请上传txt或xls文件");
            $("#file1").val('');
            return false;
        }

        $("#myShowFile").html(obj.value);

        $.ajaxFileUpload({
            url: '<%=basePath%>rest/admin/sms/uploadMobileFile',
            fileElementId: 'file1',
            type: "POST",
            dataType : 'json',
            success: function (data) {
                if (data.code==0) {
                    var info = data.t;
                    var count = info.length;
                    var text = $("#mobile").val();
                    if (count>0) {
                        if (text.length>0)
                            text += ",";
                        text += info[0];
                        for (var i=1; i < count; i++) {
                            text += ","+info[i];
                        }
                    }
                    $("#mobile").val(text);
                }else{
                    alert(data.desc);
                }
                return true;
            },
            error: function (error) {
                alert("失败");
                return false;
            }
        });
        return true;
    }

    function myReset() {
        $("#title").val(0);
        $("#content").val("");
        $("#mobile").val("");
        $("input:radio").attr("checked",false);
        $("#myType1").html("");
        $("#myType2").html("");
        $("#myType3").html("");
    }

    function getSmsModel() {
        var id = $("#title").val();
        if (id==0)
            return;

        $.ajax({
            type: "POST",
            url: "<%=basePath%>rest/admin/sms/getSmsModel",
            traditional: true,
            dataType: "json",
            data: {"id":id},
            cache: false,
            success: function (data) {
                //data = JSON.parse(data);
                if (data.code==0) {
                    var smsModel =  data.t;
                    $("#content").val(smsModel.sign+smsModel.content);
                } else {
                    alert(data.desc);
                }
            },
            error: function (error) {
                alert("初始化失败");
            }
        });
    }

    function mysubmit() {
        var title = $("#title").val();
        if (title==0) {
            alert("请选择主题");
            return;
        }

        var content = $("#content").val();
        if (content.length==0) {
            alert("发送内容不能为空");
            return;
        }

        var mobile = $("#mobile").val();
        if (mobile.length==0) {
            alert("发送号码不能为空");
            return;
        }

        var sendType=$("input:radio[name='sendType']:checked").val();
        if (typeof(sendType) == "undefined") {
            alert("请选择发送方式");
            return;
        }

        var sendTime = "";
        if (sendType==2) {
            sendTime = $("#sendTime").val();
        }

        $.ajax({
            type: "POST",
            url: "<%=basePath%>rest/admin/sms/batchSendSms",
            traditional: true,
            dataType: "json",
            data: {"content":content,"mobile":mobile,"sendType":sendType,"sendTime":sendTime},
            cache: false,
            success: function (data) {
                if (data.code==0) {
                    alert("成功");
                    myReset();
                } else {
                    alert(data.desc);
                }
            },
            error: function (error) {
                alert("初始化失败");
            }
        });
    }
    function initAllGroup() {
        $.ajax({
            type: "POST",
            url: "<%=basePath%>rest/admin/sms/getAllGroup",
            traditional: true,
            dataType: "json",
            data:{"state":0},
            cache: false,
            success: function (data) {
                if (data.code==0) {
                    var info = data.t;
                    for (var i=0; i < info.length; i++) {
                        var id = info[i].id;
                        var name = info[i].name;
                        $("#groupId").append("<option value="+id+">"+name+"</option>");
                    }
                }
            },
            error: function (error) {
                alert("error");
            }
        });
    }

    function getGroupMobile(){
        var groupId = $("#groupId").val();
        if(groupId==0){
            alert("请选择群组");
            return false;
        }
        $.ajax({
            type: "POST",
            url: "<%=basePath%>rest/admin/sms/getAllAccount",
            traditional: true,
            dataType: "json",
            data: {"id":groupId},
            cache: false,
            success: function (data) {
                if (data.code==0) {
                    var info = data.t;
                    var count = info.length;
                    var text = $("#mobile").val();
                    if (count>0) {
                        if (text.length>0)
                            text += ",";
                        text += info[0].phone;
                        for (var i=1; i < count; i++) {
                            text += ","+info[i].phone;
                        }
                    }
                    $("#mobile").val(text);
                }else{
                    alert(data.desc);
                }
            },
            error: function (error) {
                alert("失败");
            }
        });
    }
</script>