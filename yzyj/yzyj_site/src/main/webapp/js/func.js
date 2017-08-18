var baseUrl = '/yffs_site/rest/';
var imgVideoBaseUrl = '/yffs_site/upload/propaganda/';
var advertUrl = '/yffs_site/upload/advert/';

// 强制转换成网格时间格式 MM/dd/YYYY
function dateFormat(str){
	var t_arr = str ? str.toString().split('-') : [];
	if(t_arr.length > 1){
		// YYYY-MM-DD
		return fix(t_arr[2], 2) + '-' + t_arr[0] + '-' + fix(t_arr[1], 2);
		// MM/dd/YYYY
		// return t_arr[0] + '/' + fix(t_arr[1], 2) + '/' + fix(t_arr[2], 2);
	}else{
		if(str){
			var curTime = str ? new Date(str) : new Date();
			// YYYY-MM-DD
			return curTime.getFullYear() + '-' + fix(curTime.getMonth() + 1, 2) + '-' + fix(curTime.getDate(), 2);
			// MM/dd/YYYY
			// return  curTime.getFullYear() + '/' + fix(curTime.getMonth() + 1, 2) + '/' + fix(curTime.getDate(), 2);
		}else{
			return '';
		}
	}
}
// 强制转化成两位整数，服务于dateFormat函数
function fix(str, fixLen) {
	str = ('' + str) || str.toString();
	var str_len = str.length;
	if(str_len < fixLen){
		return (new Array(fixLen - str_len + 1)).join('0') + str;
	}else{
		return str;
	}
}
function GetQueryString(name)
{
     var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
     var r = window.location.search.substr(1).match(reg);
     if(r!=null)return  unescape(r[2]); return null;
}