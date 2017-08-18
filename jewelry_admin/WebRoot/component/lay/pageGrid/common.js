//关闭iframe
function closeWindow(){
    var  frameindex= parent.layer.getFrameIndex(window.name);
    layui.use(['form','layer'], function() {
        parent.layer.close(frameindex);
    });
}



//重写alert
window.alert = function(msg, callback){
	parent.layer.alert(msg, function(index){
		parent.layer.close(index);
		if(typeof(callback) === "function"){
			callback(index);
		}
	});
};

//重写confirm式样框
window.confirm = function(msg, callback){
	parent.layer.confirm(msg, {btn: ['确定','取消']},
	function(){//确定事件
		if(typeof(callback) === "function"){
			callback("ok");
		}
	});
};

//选择一条记录
function getSelectedRow(table_id) {
    var checked=$("#"+table_id+" .layui-form-checked");
    if(checked.length==0){
    	alert("请选择一条记录");
    	return ;
    }
    var selectedIDs = [];
    for(var i=0;i<checked.length;i++){
        var _this=$(checked[i]).prev();
        selectedIDs.push($(_this).attr("primary"));

	}
    if(selectedIDs.length > 1){
    	alert("只能选择一条记录");
    	return ;
    }
    return selectedIDs[0];
}

//选择多条记录
function getSelectedRows(table_id) {
    var checked=$("#"+table_id+" .layui-form-checked");
    if(checked.length==0){
    	alert("请选择一条记录");
    	return ;
    }
    var selectedIDs = [];
    for(var i=0;i<checked.length;i++){
        var _this=$(checked[i]).prev();
        selectedIDs.push($(_this).attr("primary"));

    }
    return selectedIDs;
}

//跳转到添加页面
function toAddPage(url){
    //var ids= getSelectedRows(table_id);
    toAddPage('/sys/menu/add')
    $("body").load(url);
}
//跳转到修改页面
function toEditPage(table_id,url){
    var id=getSelectedRow(table_id);
    if(id!=null){
        $("body").load("/sys/menu/edit/"+id);
    }
}

$(function () {
    $(".layui-btn-primary").click(function () {
        $(".layui-form-item input").val("");
        $(".layui-input-group button")[0].click();
    });

});