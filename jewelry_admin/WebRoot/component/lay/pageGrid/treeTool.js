/**
 * Created by 陈熠 on 2017/6/21
 * email   :  228112142@qq.com
 */
(function ($) {
    var cyProps = {};
    /* 入口函数 */
    $.fn.treeTool = function () {
        debugger;
        $(this).attr("readonly", "readonly");
        $(this).attr("style", "padding-right: 30px;");
        //为该组件添加清空按钮
        // $(this).after('<span class="tree-clear-btn"><i>x</i></span>');
        var _id = $(this).attr("id");
        if ($(this).attr("value") == "") {
            $(this).attr("valueId", "");
        }
        var _value = $(this).attr("valueId") || "";
        $("#" + _id + "_id").remove();
        //$("#treeLayer").remove();
        $(".layui-layer-molv").remove();
        cyProps = $(this).attr("cyProps");
        if (!cyProps) {
            return
        }
        cyProps = cyProps ? cyProps : "";
        //将表格参数转为json
        cyProps = eval("({" + cyProps + "})");
        $(this).after('<input value="' + _value + '"  style="display: none" type="text" id="' + _id + '_id"  name="' + cyProps.name + '"  class="layui-input">' +
            '<div id="treeLayer" style="display: none;padding:10px;"> ' +
            '<ul id="zTree" class="ztree"></ul> ' +
            '</div>');
        $.ajax({
            type: "post",
            url: cyProps.url,
            contentType: "application/json",
            async: false,
            dataType: "json",
            success: function (R) {
                if (R.code == 0) {
                    ztree = $.fn.zTree.init($("#zTree"), setting, R.page);
                    var node = ztree.getNodeByParam("id", cyProps.value);
                    if (node != null) {
                        ztree.selectNode(node);
                        $(this).val(node.name);
                    }

                } else {
                    alert(R.msg);
                }
            },
            error: function () {
                alert("系统错误");
            }
        });
    };


})(jQuery);
var setting = {
    data: {
        simpleData: {
            enable: true
        },
        key: {
            url: "nourl"
        }
    }
};
var ztree;
/**菜单列表*/
function openZtree(obj) {
    debugger;
    var _id = $(obj).attr("id");
    $("#" + _id + "_id").remove();
    $(".layui-layer-molv").remove();
    $("#treeLayer").remove();
    var cyProps = $(obj).attr("cyProps");
    if (!cyProps) {
        return
    }
    cyProps = cyProps ? cyProps : "";
    //将表格参数转为json
    cyProps = eval("({" + cyProps + "})");
    //是否有默认值
    if ($(obj).attr("value") == "") {
        $(obj).attr("valueId", "");
    }
    var _value = $(obj).attr("valueId") || "";
    $(obj).after('<input type="text" id="' + _id + '_id"  style="display: none" name="' + cyProps.name + '" value="' + _value + '" class="layui-input">' +
        '<div id="treeLayer" style="display: none;padding:10px;"> ' +
        '<ul id="zTree" class="ztree"></ul> ' +
        '</div>');
    $.ajax({
        type: "post",
        url: cyProps.url,
        contentType: "application/json",
        async: false,
        dataType: "json",
        success: function (R) {
            if (R.code == 0) {
                ztree = $.fn.zTree.init($("#zTree"), setting, R.page);
                var node = ztree.getNodeByParam("id", _value);
                if (node != null) {
                    ztree.selectNode(node);
                    $(obj).val(node.name);
                }
            } else {
                alert(R.msg);
            }
        },
        error: function () {
            alert("系统错误");
        }
    });
    layer.open({
        type: 1,
        offset: '50px',
        skin: 'layui-layer-molv',
        title: "请选择",
        area: ['300px', '400px'],
        shade: 0,
        shadeClose: false,
        content: jQuery("#treeLayer"),
        btn: ['确定', '取消'],
        btn1: function (index) {
            debugger;
            var node = ztree.getSelectedNodes();
            if (node.length > 0) {
                $("#" + _id + "_id").val(node[0].id);
                $("#" + _id).val(node[0].name);
                $(obj).attr("valueId", node[0].id);
            }
            //选择上级菜单
            layer.close(index);
        }
    });
}

$(document).ready(function () {
    $("[cyType='treeTool']").click(function () {
        var obj = $(this);
        openZtree(obj);
    });
    $(".tree-clear-btn i").click(function () {
        debugger;
        var treeTool = $($(this).parent()).find("[cyType='treeTool']");
        $(treeTool).attr("value", "");
        $(treeTool).attr("valueId", "");

    });
    //下拉树查询
    var treeTools = $("[cyType='treeTool']");
    for (var i = 0; i < treeTools.length; i++) {
        $(treeTools[i]).treeTool();
    }


});