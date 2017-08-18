var setting = {
    view: {
        selectedMulti: false
    },
    check: {
        enable: true
    },
    data: {
        simpleData: {
            enable: true
        }
    },
    callback: {
        onCheck: onCheck,
        onClick: onCheck
    }
};
function onCheck(event, treeId, treeNode) {
    /*获取选中的部门编号*/
    $("#parentCode").val(treeNode.code);
    $("#parent_org_id").val(treeNode.id);
    $("#parentName").val(treeNode.name);
}
var zNodes;
$.ajax({
    type: "post",
    url: "/admin/organize/getAllOrg",
    async: false,
    dataType: "json",
    ContentType: "application/json; charset=utf-8",
    success: function(data){
       zNodes=data;
    }
});
// var root={"id":"1111111111","pId":"0000000000","code":"yszb","name":"云商珠宝","open":"true"}
//     zNodes.push(root);
$(document).ready(function(){
    // /*设置上级部门默认值  云返酒店*/
    // if($("#parentName").val().length==0||$("#parentCode").val().length==0){
    //     $("#parentName").val("云商珠宝");
    //     $("#parentCode").val("yszb");
    // }
    /*ztree初始化-------start*/
    $.fn.zTree.init($("#treeDemo"), setting, zNodes);
    var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
    var checkId=$("#parent_org_id").val();
   if(checkId==""||checkId==undefined) {
       checkId=1111111111;
   }
    var node = treeObj.getNodeByParam("id", checkId);
    treeObj.selectNode(node);

});
