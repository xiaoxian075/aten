// 创建tab页切换一个闭包  
(function($) {  
	  // 插件的定义  
	  $.fn.projectTab = function(options) {   
	  	  //获取设置插件的选项	 
	      var opts = $.extend({}, $.fn.projectTab.defaults, options);
	      //初始化方法
	      var tab_li=$(this).find("."+opts.indexId).find("li");
	      var tab_div=$(this).find("."+opts.indexDiv);
	      this.each(function(){    
	      	   	tab_li.each(function(i){
	      	   		$(this).click(function(){
	      	   		     var index=$(this).index();//获取当前点击的li索引 	
	      	   		     tab_li.removeClass(opts.selected);//去除所有样式
		      	   		 $(this).addClass(opts.selected);//为当前选择框增加样式
		      	   		 var displayIndexStr=opts.displayIndex;//获取设置全显DIV的值		      	   		 
	      	   			 if(displayIndexStr!=null&&displayIndexStr.indexOf(index)>-1){
	      	   			  	 tab_div.css("display","block");
	      	   			 }else{
		      	   			 tab_div.css("display","none");//隐藏所有的tabdiv  	      	   			   			 
		      	   			 tab_div.eq(index).css("display","block")//显示对应的tabdiv
		      	   		}
	      	   		});	      	   
	      	   });
		  }); 
	  };  
	  // 插件的defaults默认配置  
	  $.fn.projectTab.defaults = {  
	  	 //选择的样式名称
	  	 selected:"selected",
	  	 //显示所有DIV的索引, 如果有多个值以,隔开
	  	 displayIndex:"0",
	  	 indexId:"tabbar",
	  	 indexDiv:"tabdiv"
	  };	
// 闭包结束  
})(jQuery);   



// 创建table隔行变色一个闭包  
(function($) {  
	  // 插件的定义  
	  $.fn.projectTable = function(options) {   
	  	  //获取设置插件的选项	 
	      var opts = $.extend({}, $.fn.projectTable.defaults, options); 
	      //定义全局的背景颜色变化  
	      var tr_backcolor="";	 
	      //初始化方法
	      this.each(function(){  
	      	   //定义表格的变量  
	      	   var $table=$(this);
	      	   //初始化表格颜色
	      	   $table.find("tr:gt(0)").each(function(i){
 		      	   if(i%2==0){
 		      	   		$(this).css("background",opts.even_color);
 		      	   }else{
 		      	   		$(this).css("background",opts.odd_color);
 		      	   }
		       });
		       //鼠标移动隔行变色
	      	   $(this).find("tr:gt(0)").each(function(){		 		      	 		      	 
					  $(this).hover(
						  function () {
							  tr_backcolor=$(this).css("background-Color"); //得到行本身颜色							  
						      $(this).css("background",opts.float_color);
						  },function () {
						      $(this).css("background",tr_backcolor);
					      }
					  );
		 	   });
		 	   //选中checkbox变色
	      	   if(opts.open_check_color==true){
	      		   $(this).find("tr:gt(0)").each(function(){
	      		    	 $(this).find("td").eq(0).find("input:checkbox").click(function(){	
				 	   		  var check_tr=$(this).parent("td").parent("tr");	 	   				
				 		      if(this.checked){
				 		      	   check_tr.css("background",opts.check_color);
				 		      }else{
				 		      	   var tr_index=check_tr.index();
				 		      	   if(tr_index%2==0){
				 		      	   		check_tr.css("background",opts.odd_color);
				 		      	   }else{
				 		      	   		check_tr.css("background",opts.even_color);
				 		      	   }
				 		      }
				 		      tr_backcolor=check_tr.css("background-Color");
				 	   });
	      		   })
			 	   //当选中第一个checkbox全选变色
	      		   //var $ftr = $(this).find("tr").eq(0).find("input:checkbox").eq(0);
	      		   $(".all").click(function(){	
			 		   	  //alert(this.checked);
			 		      if(this.checked){
			 		      	   $table.find("tr:gt(0)").css("background",opts.check_color);
			 		      	   $table.find("tr:gt(0)").each(function(){
			 		      		   $tr = $(this);
			 		      		   $tr.find("td").eq(0).find("input:checkbox").each(function(){
			 		      			   $(this).prop("checked",true);  
			 		      		   });
			 		      	   });
			 		      	   $(".all").prop("checked",true);
			 		      }else{
			 		      	   $table.find("tr:gt(0)").each(function(i){
			 		      		   $tr = $(this);
			 		      		   $tr.find("td").eq(0).find("input:checkbox").each(function(){
			 		      			   $(this).prop("checked",false);
			 		      		   });
			 		      		   //取消选中时颜色 
			 		      		   if(i%2==0){
				 		      	   		$(this).css("background",opts.even_color);
				 		      	   }else{
				 		      	   		$(this).css("background",opts.odd_color);
				 		      	   }
			 		      	   });
			 		      	   $(".all").prop("checked",false);
			 		      }
			 	   });
			 	   //查找当前表格下是否存在已选中的checkbox框
	      		   var ids = ".ids";
	      		   $(ids).click(function(){
		      		    var length = $(ids).length;
		      		    var checkLength=0;
		      		 	$table.find(ids).each(function(i){
		      		    	if(this.checked){
			      	   			checkLength+=1;
			      	   		}
		      		    });
		      	   		//判断是否全选
		      	   		if(length==checkLength){
		      	   			$(".all").prop("checked",true);
		      	   		}else{
		      	   			$(".all").prop("checked",false);
		      	   		}
	      		   });
	      	   }
		  }); 
	  };
	  // 插件的defaults默认配置  
	  $.fn.projectTable.defaults = {  
	  	  even_color:"#FFFFFF",//偶数行颜色
	  	  odd_color:"#FAFAFA", //奇数行颜色 ===#F2F2F2	   
	  	  float_color:"#3399ff", //鼠标移动tr上颜色=== #FFFFF0,#cfecff
	  	  check_color:"#FFFFE0",  //checkbox选中的颜色 === #FFFFCC
	  	  open_check_color:true,
	  	  add_checkbox:".all"
	  };
// 闭包结束  
})(jQuery);   


// 创建弹出层绝对居中一个闭包  
(function($) {  
	  // 插件的定义  
	  $.fn.popup= function(options) {   
	  	  // 获取设置插件的选项	 
	      var opts = $.extend({}, $.fn.popup.defaults, options);
	      // 私有方法
	      var pm =popupMethod;
	      // 初始化方法
	      this.each(function(){    
	      	  // 获取需要弹出层控件的ID
	      	  var popup_id = $(this).attr("id");
	      	  // 创建一个父DIV框
	      	  var pId = opts.parent_id;
	      	  // 创建容器到BODY 
	      	  if($("#"+pId).length == 0){
	      	  	  $("<div id='"+pId+"' class='"+pId+"'></div>").appendTo($("#"+opts.out_div));	
	      	 	  var this_width =$("#"+popup_id).innerWidth();
	      	 	  var parent_width=this_width+35;
	      	 	  var contant_width=this_width+3;
	      	 	  // 往容器中加入头部
	      	 	   $("#"+pId).append("<div style='padding:5px 1px 2px 5px;'><div style='width:90%;float:left;' id='"+opts.parent_title+"'><b>"+opts.pop_title+"</b></div><div style='float:right;'><img id='"+opts.cover_img+"'  src='"+opts.cover_imgpath+"' style='cursor:pointer;'/></div><p class='clear'></p><div>");
	      	 	  // 将搜索框放入容器中
	      	 	  $("#"+pId).append("<div id='contant_div' style='width:"+contant_width+"px;background:#FFFFFF;margin:0px 10px 10px 10px;padding:6px;'></div>");
	      	 	  $("#contant_div").append($(this));
	      	  }else{
	      	  	  $("#"+pId).show();	
	      	  }
	      	  // 搜索框显示
	      	  $(this).css({"display":"block"});
      	 	  // 初始化宽度
	      	  $("#"+pId).css({"width":parent_width+"px","background":"#CAD9E6","border":"1px solid #3D596F"});
      	 	  
	          // 获取窗体的宽度与高度
    	      var _scrollHeight = $(document).scrollTop(),//获取当前窗口距离页面顶部高度
    	   	  _documentHeight =  $(document).height(),//获取当前文档的高度
			  _windowHeight = $(window).height(),//获取当前窗口高度
			  _windowWidth = $(window).width(),//获取当前窗口宽度
			  _popupHeight = $(this).height(),//获取弹出层高度
			  _popupWeight = $(this).width();//获取弹出层宽度
			  var _posiTop = (_windowHeight - _popupHeight)/2 + _scrollHeight+opts.move_top;
			  var _posiLeft = (_windowWidth - _popupWeight)/2+opts.move_left;
	          if($("#"+opts.parent_cover).length == 0){
		          // 新建一个DIV遮盖层
		          $("<div id='"+opts.parent_cover+"' class='cover'></div>").appendTo($("#"+opts.out_div));	
	   	   		  $("#"+opts.parent_cover).height(_documentHeight);
	   	   		  $("#"+opts.parent_cover).width("100%");
	   	   		  $("#"+opts.parent_cover).css({"opacity":opts.opacity, background: "black","position":"absolute","left":"0px","top":"0px","z-index":opts.cover_index});
	   	   		  $("#"+opts.parent_cover).fadeIn("slow");
	   	   		  // 设置position
				  $("#"+opts.parent_id).css({"left": _posiLeft + "px","top":_posiTop + "px","display":"block","position":"absolute","z-index":opts.cover_index+1});
	          }else{
	          	  $("#"+opts.parent_cover).show();
	          }
		  	  // 渲染方法
		  	  pm._close_cover(popup_id,opts);
		  }); 
	  };  
	  // 插件的defaults默认配置  
	  $.fn.popup.defaults = {  
	     opacity:"0.3",
	     parent_id:"divPanel",
	 	 parent_title:"divTitle",
	 	 parent_cover:"pCover",
	 	 out_div:"listform",
	 	 pop_title:"搜索框",
	 	 otherMethod:"",
	 	 cover_index:"8888",
	 	 cover_img:"p_c_img",
	 	 cover_imgpath:"/include/common/images/closecover.png",
	 	 move_left:0,
	 	 move_top:0
	  };	
	  
	 //私有方法 
     var popupMethod={
     	  _close_cover:function(popup_id,opts){
     	  	  $("#"+opts.cover_img).click(function(){
     	  	        //获取需要隐藏的规格项
     	  	  		$("#"+opts.parent_id).hide();
     	  	  		$("#"+opts.parent_cover).hide();
	 		 		$("#"+popup_id).css({"display":"none"});
     	  	  }); 
     	  	  if(opts.otherMethod!=""){
				   eval(opts.otherMethod);
			  }
          }
     };
     
     // 关闭层  
	 $.fn.ccover= function(options) {
	 	//获取设置插件的选项	 
	    var opts = $.extend({}, $.fn.popup.defaults, options);
	    var popup_id = $(this).attr("id");
        //获取需要隐藏的规格项
	  	$("#"+opts.parent_id).hide();
	  	$("#"+opts.parent_cover).hide();
		$("#"+popup_id).css({"display":"none"});
		if(opts.otherMethod!=""){
			eval(opts.otherMethod);
		}
	 }  
// 闭包结束  
})(jQuery); 


 // 创建弹出提示层一个闭包  
(function($) {  
	  // 插件的定义  
	  $.fn.lighttip= function(options) {   
	  	  //获取设置插件的选项	 
	      var opts = $.extend({}, $.fn.lighttip.defaults, options);
	      //初始化方法
	      this.each(function(){   	       	 
	          $(this).hover(function() {	
	          	    var alt=$(this).attr("alt");          
	          		var X = $(this).offset().top-5;
					var Y = $(this).offset().left+25;					
	        	 	$("<div id='showtip' class='showtip'></div>").appendTo("body");
	        	 	$("#showtip").html(alt);
	        	 	var _tipHeight=$("#showtip").height(),
	        	 	_tipWidth=$("#showtip").width();	        	 	
	        	 	$("#showtip").css({"left": Y + "px","top":X + "px","position":"absolute"});	        	 	
					
			   }, function() {
					$("#showtip").remove();
			  });
		  }); 
	  };  
	  // 插件的defaults默认配置  
	  $.fn.lighttip.defaults = {  
	 
	  };	
// 闭包结束  
})(jQuery); 




//表格td合并闭包
(function($) {
	//合并上下栏位(colIdx)
	$.fn.rowspan = function(colIdx) {
		return this.each(function() {
			var that;
			$('tr', this).each(
					function(row) {
						var thisRow = $('td:eq(' + colIdx + '),th:eq(' + colIdx
								+ ')', this);
						if ((that != null)
								&& ($(thisRow).html() == $(that).html())) {
							rowspan = $(that).attr("rowSpan");
							if (rowspan == undefined) {
								$(that).attr("rowSpan", 1);
								rowspan = $(that).attr("rowSpan");
							}
							rowspan = Number(rowspan) + 1;
							$(that).attr("rowSpan", rowspan);
							$(thisRow).remove(); 
							//$(thisRow).hide();
						} else {
							that = thisRow;
						}
						that = (that == null) ? thisRow : that;
					});
			
		});
	}



	//合并左右栏位
	$.fn.colspan = function(rowIdx) {
		return this.each(function() {
			var that;
			$('tr', this).filter(":eq(" + rowIdx + ")").each(function(row) {
				$(this).find('th,td').each(function(col) {
					if ((that != null) && ($(this).html() == $(that).html())) {
						colspan = $(that).attr("colSpan");
						if (colspan == undefined) {
							$(that).attr("colSpan", 1);
							colspan = $(that).attr("colSpan");
						}
						colspan = Number(colspan) + 1;
						$(that).attr("colSpan", colspan);
						$(this).remove();
					} else {
						that = this;
					}
					that = (that == null) ? this : that;
				});
			});
		});
	}
	
	//去除合并
	$.fn.RevertTable=function(){
	    $("tr",this).each(function(trindex,tritem){
	        $(tritem).find("td").each(function(tdindex,tditem){
	            var rowspanCount=$(tditem).attr("rowspan");
	            var colspanCount=$(tditem).attr("colspan");
	            var value=$(tditem).html();
	            var newtd="<td>"+value+"</td>";
	            if(rowspanCount>1){
	                var parent=$(tditem).parent("tr")[0];
	                while(rowspanCount-->1){
	                    $($(parent).next()[0].children[tdindex]).before(newtd);
	                    parent=$(parent).next();
	                }
	                $(tditem).attr("rowspan",1);
	            }
	            if(colspanCount>1){
	                while(colspanCount-->1){
	                    $(tditem).after(newtd);
	                }
	                $(tditem).attr("colspan",1);
	            }
	        });
	    });
	}	
	//闭包结束
})(jQuery);

//通用select Ajax获取数据  插件 
//select 的插件闭包
(function($) {
	// select插件的定义  
	$.fn.select = function(options) {
		var opts = $.extend({}, $.fn.select.defaults, options);
		var show_id =opts.show_id;
		var key = opts.key;
		var value = opts.value;
		var remove_ids = opts.remove_ids;
		//初始化方法
		this.each(function() {
			$(this).change(function(){
				  if($(this).val()=="") return;
				  var data = getAjaxList(opts,$(this).val());
				  var len =data.list.length;
				  //添加到另一个select
				  if(show_id!=''){
					  var $show_id = $("#"+show_id);
					  //移除底下子集关联的select为空
					  if(remove_ids!=""){
						  var remove_id = remove_ids.split(",");
						  for(var i=0;i<remove_id.length;i++){
							  $("#"+remove_id[i]).empty();
							  $("#"+remove_id[i]).append("<option value=''>请选择</option>");
						  }
					  }
					  //清空
					  $show_id.empty();
					  //添加选择项
					  $show_id.append("<option value=''>请选择</option>");
					  for ( var i = 0; i < len; i++) {
						  var jo = data.list[i];
						  $show_id.append("<option value='"+eval("jo."+key)+"'>"+eval("jo."+value)+"</option>");
					  }
				  }
			});
		});
	};

	// select插件的defaults默认配置  
	$.fn.select.defaults = {
		 //初始值
	     url:"",
	     show_id:"",
	     key:"merchant_id",
	     value:"mer_name",
	     remove_ids:""
	};
	
	
	//AjAX请求
	function getAjaxList(opts,id){
		var url = opts.url;
		var para = {id:id};
		var json = "";
		$.ajax({
			type : "post",
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
	
})(jQuery);
// 闭包结束  


//级联select的插件闭包
(function($) {
	// 级联select插件
	$.fn.cascadeSel = function(options) {
		//debugger;
		var opts = $.extend({}, $.fn.cascadeSel.defaults, options);
		//初始化加载数据
		var init_id = opts.init_id;
		var selHideName = opts.name;
		$this =$(this);
		//获取隐藏域的值
		if($("#"+selHideName).val()!=""){
			var backSelId = init_id+","+$("#"+selHideName).val();
			var backSelIds = backSelId.split(",");
			for(var i = 0;i < backSelIds.length; i++) {
				if(i<opts.showLevel){
					//回选数据
					var id = backSelIds[i];
					if(id!=""){
						var data = getAjaxList(opts,id);
						showSelect($this,data,opts,id);
						$("#sel_"+id).val(backSelIds[i+1]);//根据值选中
					}
				}
			}
		}else{
			var data = getAjaxList(opts,init_id);
			showSelect($this,data,opts,init_id);
		}
		//初始化方法
		this.each(function() {
			selectRender($this,data,opts,init_id);
		});
	};
	
	// 级联select插件的defaults默认配置  
	$.fn.cascadeSel.defaults = {
			 //初始值
		     url:"",
		     init_id:"1111111111",
		     showLevel:"10",//根据情况自己配置
		     name:"",
		     li_id:"cat_id",
		     li_name:"cat_name",
		     range_val:""
	};
	
	//渲染select的选择事件
	function selectRender($this,data,opts,sel_up_id){
		$("#"+sel_up_id).change(function(){
			  // 清除后面的元素
			  $(this).nextAll().remove();
			  var length = $(this).parent("div").find("select").length;
			  if(length<opts.showLevel){
				  var id =$(this).find("option:selected").val();
				  if(id!="") {
					  var data = getAjaxList(opts,id);
					  showSelect($this,data,opts,id);
				  }
				  selOption(opts,$this);
			  }
		});
	}
	
	//将选中的值存入select中
	function selOption(opts,$this){
		//存入隐藏域中的值
		var selHideName = opts.name;
		var $selHideName = $("#"+selHideName);
		var hideVal="";
		//找出select被选中的值
		$this.find("select").each(function(){
			var sel_val =$(this).find("option:selected").val();
			if(sel_val!=""){
				hideVal+=sel_val+",";
			}
		});
		hideVal = deleteLastChar(hideVal, ",");
		$selHideName.val(hideVal);
	}
	
	//显示select
	function showSelect($this,data,opts,up_id){
		var len = data.list.length;
		var sel_up_id = "sel_"+up_id;
		var opts_li_id = opts.li_id;
		var opts_li_name = opts.li_name;
		if(len>0){
			var selstr ="<select class='select' id='"+sel_up_id+"'><option value=''>请选择</option>";
			for ( var i = 0; i < len; i++) {
				  var jo = data.list[i];
				  selstr+="<option value='"+eval("jo."+opts_li_id)+"'>"+eval("jo."+opts_li_name)+"</option>";
			 }
			selstr+="</select>";
			$this.append(selstr);
		}
		//渲染方法
		selectRender($this,data,opts,sel_up_id);
	}
	
	
	//AjAX请求
	function getAjaxList(opts,id){
		var url = opts.url;
		var para = {id:id};
		var json = "";
		$.ajax({
			type : "post",
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
	
})(jQuery);
// 闭包结束  
