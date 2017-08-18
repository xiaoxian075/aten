/**
 * Created by huangdw on 2015/12/9.
 */
//日期格式化

Date.prototype.format = function(format){
    var o = {
        "M+" : this.getMonth()+1, //month
        "d+" : this.getDate(), //day
        "h+" : this.getHours(), //hour
        "m+" : this.getMinutes(), //minute
        "s+" : this.getSeconds(), //second
        "q+" : Math.floor((this.getMonth()+3)/3), //quarter
        "S" : this.getMilliseconds() //millisecond
    }

    if(/(y+)/.test(format)) {
        format = format.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length));
    }

    for(var k in o) {
        if(new RegExp("("+ k +")").test(format)) {
            format = format.replace(RegExp.$1, RegExp.$1.length==1 ? o[k] : ("00"+ o[k]).substr((""+ o[k]).length));
        }
    }
    return format;
}

/**
 * 设置未来(全局)的AJAX请求默认选项
 * 主要设置了AJAX请求遇到Session过期的情况
 */
//$.ajaxSetup({
//    complete: function(xhr,status) {
//        if('parsererror' == status) {
//            redirectLogin();
//        }else if('success' == status && 'html'== this.dataType){
//            if(-1 != xhr.responseText.indexOf("This is the login page")){
//                redirectLogin();
//            }
//        }
//
//    }
//});

/**
 * 在页面中任何嵌套层次的窗口中获取顶层窗口
 * @return 当前页面的顶层窗口对象
 */
function getTopWinow(){
    var p = window;
    while(p != p.parent){
        p = p.parent;
    }
    return p;
}

/**
 * 获取上下文路径
 * @returns {*}
 */
function getWebroot(){
    var webroot=document.location.href;
    webroot=webroot.substring(webroot.indexOf('//')+2,webroot.length);
    webroot=webroot.substring(webroot.indexOf('/')+1,webroot.length);
    webroot=webroot.substring(0,webroot.indexOf('/'));
    return webroot;
}

/**
 * 页面跳转
 */
function redirectLogin(){
    var top = getTopWinow();
    var webroot = getWebroot();
    alert('由于你长时间没有操作,请你重新登录!');
    top.location.href = webroot+'/rest/admin/login';
}