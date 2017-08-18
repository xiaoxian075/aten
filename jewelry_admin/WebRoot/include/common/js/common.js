//传递参数

function goInfo(){ 
    var len= arguments.length; 
    if(3 == len){ 
    	goThreeInfo(arguments[0],arguments[1],arguments[2]); 
    }else if(2 == len){ 
    	goTwoInfo(arguments[0],arguments[1]);
    }else{
    	goOneInfo(arguments[0]);
    }
} 

function goThreeInfo(url,name,para){
	if(name!=null){
		$("#"+name).val(para);
	}
	if(url!=null){
		document.forms[0].action=url;
	}
	document.forms[0].submit();
}

function goTwoInfo(url,id){
	goInfo(url,"parameter_id",id);
}

function goOneInfo(url){
	goInfo(url,null,null);
}


//状态修改
function stateInfo(url,id) {
	$("#parameter_id").val(id);
	document.forms[0].action=url;
	document.forms[0].submit();
}

//公用信息修改
function commonInfo(url,id,msg) {
	$("#parameter_id").val(id);
	art.dialog({
	    title: '友情提示',
	    content: msg,
	    okValue: '确认',
	    cancelValue: '取消',
	    width:300,
	    ok: function () {
	        document.forms[0].action=url;
			document.forms[0].submit();
	    },cancel: function () {}
	});
}

//公用批量信息修改
function commonBatchInfo(url,msg) {
	var ids_str ="";
	$(".ids").each(function(){
		if(this.checked){
			ids_str+=$(this).val()+",";
		}
	});
	ids_str = deleteLastChar(ids_str, ",");
	if(ids_str==""){
		art.dialog({
		    title: '友情提示',
		    content: "请选择需要操作选项！",
		    time:2000
		});	
		return;
	}
	$("#parameter_id").val(ids_str);
	//提交表单
	art.dialog({
	    title: '友情提示',
	    content: msg,
	    okValue: '确认',
	    cancelValue: '取消',
	    width:300,
	    ok: function () {
	        document.forms[0].action=url;
			document.forms[0].submit();
	    },cancel: function () {}
	});
}
//清空并搜索
function clearSearch(url){
	$(".searchTable").find("input[type='text']").val("");
	$(".searchTable").find("select").val("");
	$("#area_id").val("");//所属地区
	$("#cat_id").val("");//所属分类
	document.forms[0].action=url;
	document.forms[0].submit();
}

//公用添加
function addInfo(url) {
	document.forms[0].action=url;
	document.forms[0].submit();
}
function addInfo(url,id) {
	$("#parameter_id").val(id);
	document.forms[0].action=url;
	document.forms[0].submit();
}

//公用修改
function editInfo(url,id) {
	$("#parameter_id").val(id);
	document.forms[0].action=url;
	document.forms[0].submit();
}

//公用删除
function delInfo(url,id){
	delPointInfo(url,"parameter_id",id);
}
//公用指定名称删除
function delPointInfo(url,name,id){
	$("#"+name).val(id);
	art.dialog({
	    title: '友情提示',
	    content: '确认要删除该条数据？',
	    okValue: '确认',
	    cancelValue: '取消',
	    width:300,
	    ok: function () {
	        document.forms[0].action=url;
			document.forms[0].submit();
	    },cancel: function () {}
	});
}

//公用排序
function sortInfo(url) {
	var sort_id_str ="";
	var sort_val_str ="";
	$(".sort_id").each(function(){
		if($(this).val()!=""){
			sort_id_str+=$(this).val()+",";
			sort_val_str+=$(this).siblings(".sort_val").val()+",";
		}
	});
	sort_id_str = deleteLastChar(sort_id_str, ",");
	sort_val_str = deleteLastChar(sort_val_str, ",");
	//alert(sort_id_str+"==="+sort_val_str);
	if(sort_id_str=="") return;
	$("#sort_id").val(sort_id_str);
	$("#sort_val").val(sort_val_str);
	document.forms[0].action=url;
	document.forms[0].submit();
}

//测试
function test(url){
	document.forms[0].action=url;
	document.forms[0].submit();	
}

//搜索
function searchInfo(url){ 
	$("#current_s").val(1);
	document.forms[0].action=url;
	document.forms[0].submit();	
} 

//返回列表
function returnGo(reUrl){
	document.forms[0].action=reUrl;
	document.forms[0].submit();
}

//删除最后一个字符
function deleteLastChar(str,los){
	if(str.length>0){
		if((str.substring(str.length-1,str.length))==los){
			str = str.substring(0,str.length-1);
		}
	}
	return str;
}

//分页显示行数提交JS
function pageSelSubmit(){
	document.forms[0].submit();
}
//分页点击页数提交JS
function pageClickSubmit(current_val){
	$("#current_s").val(current_val);
	document.forms[0].submit();
}
function pageClickSubmit2(dpage,dcount){
	$("#dpage").val(dpage);
	$("#dcount").val(dcount);
	document.forms[0].submit();
}

//
function alertTip(msg){
	art.dialog({
	    title: '友情提示',
	    content: msg,
	    time:2000
	});
}



//页面初始化加载的判断
$(function(){
	// 查看大图
	$('.img_height120,.img180,.img').click(function() {
		  var src = $(this).attr('src');
		  $('.look_big_pic img').attr('src', src);
		  $('.look_big_pic').show();
	});
	// 关闭查看大图
	$('.look_big_pic .close_btn').click(function() {
		  $('.look_big_pic').hide();
	});
	//排序值的验证,不完善后面再整理
	$(".sort_no,.sort_val").on("blur", function(){
		var num_value=$(this).val();
		num_value = $.trim(num_value);
	    var re =/^(0|([1-9]\d*))$/;
		if (!re.test(num_value)){
			art.dialog({
			    title: '友情提示',
			    content: '请输入正确的整数!',
			    time:2000
			});
			$(this).val("0");
			return false;
		}
		return true;	
	});
	
});