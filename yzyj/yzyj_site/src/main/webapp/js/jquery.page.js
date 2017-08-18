//分页插件
(function($){
	var ms = {
		init:function(obj,args){
			ms.fillHtml(obj,args);
			ms.bindEvent(obj,args);
		},
		//填充html
		fillHtml:function(obj,args){
			obj.empty();//移除当前div的内容
			//上一页
			if(args.current > 1){
				obj.append('<a href="javascript:;" class="firstPage">«</a>');
				obj.append('<a href="javascript:;" class="prevPage">‹</a>');
			}else{
				obj.remove('.firstPage');
				obj.remove('.prevPage');
			}
			//中间页码
			if(args.current != 1 && args.current >= 4 && args.pageCount != 4){
				//超过3格就显示1
				// obj.append('<a href="javascript:;" class="tcdNumber">'+1+'</a>');
			}
			if(args.current-2 > 2 && args.current <= args.pageCount && args.pageCount > 5){
				//显示左边的...号
				// obj.append('<span>...</span>');
			}
			var start = args.current -2,end = args.current+2;
			if((start > 1 && args.current < 4)||args.current == 1){
				//最少的情况下显示4
				// end++;
			}
			if(args.current > args.pageCount-4 && args.current >= args.pageCount){
				//最少情况下显示4
				// start--;
			}
			for (;start <= end; start++) {
				if(start <= args.pageCount && start >= 1){
					if(start != args.current){
						obj.append('<a href="javascript:;" class="tcdNumber">'+ start +'</a>');
					}else{
						obj.append('<span class="current">'+ start +'</span>');
					}
				}
			}
			if(args.current + 2 < args.pageCount - 1 && args.current >= 1 && args.pageCount > 5){
				//显示右边的...号
				// obj.append('<span>...</span>');
			}
			if(args.current != args.pageCount && args.current < args.pageCount -2  && args.pageCount != 4){
				//显示大的页数
				// obj.append('<a href="javascript:;" class="tcdNumber">'+args.pageCount+'</a>');
			}
			//下一页
			if(args.current < args.pageCount){
				obj.append('<a href="javascript:;" class="nextPage">›</a>');
				obj.append('<a href="javascript:;" class="finallyPage">»</a>');
			}else{
				obj.remove('.nextPage');
				obj.remove('.finallyPage');
			}
		},
		//绑定事件
		bindEvent:function(obj,args){
			obj.on("click","a.tcdNumber",function(){
				var current = parseInt($(this).text());
				ms.fillHtml(obj,{"current":current,"pageCount":args.pageCount});
				if(typeof(args.backFn)=="function"){
					args.backFn(current);
				}
			});
			//上一页
			obj.on("click","a.prevPage",function(){
				var current = parseInt(obj.children("span.current").text());
				ms.fillHtml(obj,{"current":current-1,"pageCount":args.pageCount});
				if(typeof(args.backFn)=="function"){
					args.backFn(current-1);
				}
			});
			//下一页
			obj.on("click","a.nextPage",function(){
				var current = parseInt(obj.children("span.current").text());
				ms.fillHtml(obj,{"current":current+1,"pageCount":args.pageCount});
				if(typeof(args.backFn)=="function"){
					args.backFn(current+1);
				}
			});
			obj.on("click","a.firstPage",function(){
				var current = args.current;
				ms.fillHtml(obj,{"current":current,"pageCount":args.pageCount});
				if(typeof(args.backFn)=="function"){
					args.backFn(current);
				}
			});
			obj.on("click","a.finallyPage",function(){
				var current = args.pageCount;
				ms.fillHtml(obj,{"current":current,"pageCount":args.pageCount});
				if(typeof(args.backFn)=="function"){
					args.backFn(current);
				}
			});
		}
	}
	$.fn.createPage = function(options){
		var args = $.extend({
			pageCount : 10,
			current : 1,
			backFn : function(){}
		},options);
		ms.init(this,args);
	}
})(jQuery);
