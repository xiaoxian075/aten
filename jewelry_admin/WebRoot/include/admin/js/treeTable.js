var inz_id="";
var $this;
//树表的插件闭包
(function($) {
	// 插件的定义  
	$.fn.treeTable = function(options) {
		opts = $.extend({}, $.fn.treeTable.defaults, options);
		inz_id = opts.inz_id;
		var url = opts.url;
		var para = {up_id:inz_id};
		//初始化方法
		this.each(function() {
			$this=$(this);
			//初始化加载数据  
			var idstr = $("#back_sel_id").val();
			if (idstr == "") {
				var jsonData = getChildList(url,para);
				//填充数据
				showData(opts,jsonData);
			} else {
				backSelectList(url,idstr);
			}
			
		});
	};

	// 插件的defaults默认配置  
	$.fn.treeTable.defaults = {
			 //初始值
		     inz_id:"1111111111",
		     //id标识
		     id:"menu_id",
		     //父标识
		     fid:"parent_menu_id",
		     //字段名称
		     fieldName:"menu_name",
		     //排序值 
		     sort:"sort_no",
		     //添加页面url
		     addUrl:"/admin/sysmenu/add",
		     //编辑页面url
		     editUrl:"/admin/sysmenu/edit",
		     //删除页面url
		     deleteUrl:"/admin/sysmenu/delete",
		     //排序url
		     sortUrl:"/admin/sysmenu/sort",
		     //请求列表url
		     url:"/admin/sysmenu/getChildList"
	};
	
})(jQuery);
// 闭包结束  

//获取下一级列表
function loadNextLevel(url,id, obj, e) {
	var target = getEventTarget(e);
	var tagName = target.tagName.toLowerCase();
	if (tagName != 'li' && tagName != 'span')
		return;
	// 设置li背景颜色
	$(obj).closest(".contentDiv").find("li").removeClass("libackground");
	$(obj).addClass("libackground");
	// 清除某id后的全部元素
	$(obj).closest(".contentDiv").nextAll().remove();
	// 获取分类名称
	var cat_name = $(obj).find(".contxt").html();
	var para = {up_id:id};
	var jsonData = getChildList(url,para);
	showData(opts,jsonData);
	// 获取被选中的ID串
	var catIdStr = inz_id + ",";
	$this.find(".libackground").each(function() {
		catIdStr += $(this).attr("id") + ",";
	});
	catIdStr = deleteLastChar(catIdStr, ",");
	$("#back_sel_id").val(catIdStr);
	
}

//根据条件获取列表
function getChildList(url,para) {
	var json = "";
	$.ajax({
		type : "get",
		data:para,
		url : url,
		datatype : "json",
		async : false,
		success : function(data) {
			json = data;
		}
	});
	return json;
}



//构造Table列表
function showData(opts,data) {
	//定义传值 
	var id =opts.id;
	var fid = opts.fid;
	var sort = opts.sort;
	var fieldName = opts.fieldName;
	var addUrl = opts.addUrl;
	var editUrl =opts.editUrl;
	var deleteUrl =opts.deleteUrl;	
	var sortUrl =opts.sortUrl;	
	var url =opts.url;	
	
	var strTable = "";
	strTable += "<div class='contentDiv'>";
	strTable += "<h3 class='contitle'>" + data.title + "</h3>";
	strTable += "<ul class='licontent'>";
	var len =data.list.length;
	for ( var i = 0; i < len; i++) {
		var jo = data.list[i];
		strTable += "<li id='" + eval("jo."+id) + "' onclick='loadNextLevel(\""+url+"\",\""
				+ eval("jo."+id) + "\",this,event);'>"
		strTable += "<input type='text' class='sortno chb_val' value='"
				+ eval("jo."+sort) + "'>";
		strTable += "<input type='hidden' class='chb_id' value='"
				+ eval("jo."+id) + "'>";
		strTable += "<span class='contxt'>" + eval("jo."+fieldName) + "</span>";
		strTable += "<span class='operbt'><img class='operimg' title='修改' src='/include/admin/image/edit.png' onclick='edit(\""+editUrl+"\",\""
				+ eval("jo."+id) + "\");'/>";
		strTable += "<img class='operimg' title='删除' src='/include/admin/image/delete.png' onclick='del(\""+deleteUrl+"\",\""
				+ eval("jo."+id) + "\");'/>";
		strTable+="</span>";
		strTable += "<div class='clear'></div>";
	}
	strTable += "</ul><h3 class='bottomoper'>";
	strTable += "<img class='operimg' title='添加' src='/include/admin/image/add.png' onclick='add(\""+addUrl+"\",\""
			+ data.title_id +"\");'/>";
	strTable += "<img class='operimg' title='排序'  src='/include/admin/image/bj.gif' onclick='sort(\""+sortUrl+"\");'/>";
	strTable += "</h3></div>";
	//添加到容器里
	$this.append(strTable);
}

//新增
function add(addurl,id) {
	$("#up_id").val(id);
	$("#treeFrom").attr("action", addurl).submit();
}

//编辑分类
function edit(editurl,id) {
	$("#id").val(id);
	$("#treeFrom").attr("action", editurl).submit();
}
// 分类排序
function sort(sorturl) {
	var chb_id_str ="";
	var chb_val_str ="";
	$(".chb_id").each(function(){
		chb_id_str+=$(this).val()+",";
		chb_val_str+=$(this).siblings(".chb_val").val()+",";
	});
	chb_id_str = deleteLastChar(chb_id_str, ",");
	chb_val_str = deleteLastChar(chb_val_str, ",");
	//alert(chb_id_str+"==="+chb_val_str);
	$("#sort_id").val(chb_id_str);
	$("#sort_val").val(chb_val_str);
	$("#treeFrom").attr("action",sorturl).submit();
}
// 删除分类
function del(delurl,id) {
	art.dialog({
	    title: '友情提示',
	    content: '确认要删除该条数据？',
	    okValue: '确认',
	    cancelValue: '取消',
	    width:300,
	    ok: function () {
	    	$("#id").val(id);
	    	$("#treeFrom").attr("action", delurl).submit();
	    },cancel: function () {}
	});
}

//分类回选列表
function backSelectList(url,idStr) {
	var ids = idStr.split(",");
	for ( var i = 0; i < ids.length; i++) {
		var para = {up_id:ids[i]};
		var jsonData = getChildList(url,para);
		//填充数据
		showData(opts,jsonData);
		$("#" + ids[i]).addClass("libackground");
	}
}

//事件判断
function getEventTarget(e) {
	e = e || window.event;
	return e.target || e.srcElement;
}