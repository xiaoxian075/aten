<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<title>系统设置</title>
<style type="text/css">
body {
	background: #eee;
}

.table_div.sys_attr {
	margin: 0px;
	padding: 10px;
	background-color: #eee;
	width: 100%;
}

.content {
	height: 700px;
	background-color: #fff;
	color: rgba(7, 17, 27, 0.8);
	font-size: 18px;
	box-sizing: content-box;
	border-radius: 5px;
	margin-right: 10px;
}

.content button {
	width: 70px;
	height: 25px;
	border-radius: 3px;
	outline: none;
	border: 0px;
	color: #fff;
	background-color: rgb(31, 145, 230);
}

.content .num {
	margin: 0px 40px;
}

.title {
	text-align: right;
}

.ban {
	display: inline-block;
	width: 300px;
}

.entry {
	/*outline: none;*/
	width: 70px;
	height: 20px;
	display: none;
	margin-left: 20px;
	border-radius: 2px;
	border: 1px solid #ccc;
}

.submit, .cancel, .entry {
	display: none;
	margin-left: 10px;
}
</style>
</head>
<body class="">
	<div class="sys_attr table_div">
		<div class="content">
			<table cellspacing="15">
				<tr class="title">
					<td>订单配置：</td>
					<td></td>
				</tr>
				<tr>
					<td></td>
					<td>单个操作员单次可认领订单数： <span class="num"><span
							class="num-content">${cfg_order_claim}</span></span> <input
						name="cfg_order_claim" value="${cfg_order_claim}" maxlength="6"
						class="entry" onkeyup="javascript:RepNumber(this)">
						<button class="changeBtn">修改配置</button>
						<button class="submit">提交</button>
						<button class="cancel">取消</button>
					</td>
				</tr>
				<tr>
					<td>分成利润配置：</td>
					<td></td>
				</tr>
				<tr>
					<td></td>
					<td>酒店负责人分成利润： <span class="num"><span
							class="num-content">${cfg_charge_divide}</span>%</span> <input
						name="cfg_charge_divide" value="${cfg_charge_divide}"
						maxlength="6" class="entry" onkeyup="javascript:RepNumber(this)">
						<button class="changeBtn">修改配置</button>
						<button class="submit">提交</button>
						<button class="cancel">取消</button>
					</td>
				</tr>
				<tr>
					<td></td>
					<td></td>
				</tr>
				<tr>
					<td></td>
					<td>酒店代理人分成利润： <span class="num"><span
							class="num-content">${cfg_agent_divide}</span>%</span> <input
						name="cfg_agent_divide" value="${cfg_agent_divide}" maxlength="6"
						class="entry" onkeyup="javascript:RepNumber(this)">
						<button class="changeBtn">修改配置</button>
						<button class="submit">提交</button>
						<button class="cancel">取消</button>
					</td>
				</tr>
				<tr>
					<td>订单时间配置：</td>
					<td></td>
				</tr>
				<tr>
					<td></td>
					<td>已付款的订单，酒店超时未确认时间设定： <span class="num"><span
							class="num-content">${cfg_order_overtime}</span>分钟</span> <input
						name="cfg_order_overtime" value="${cfg_order_overtime}"
						maxlength="6" class="entry" onkeyup="javascript:RepNumber(this)">
						<button class="changeBtn">修改配置</button>
						<button class="submit">提交</button>
						<button class="cancel">取消</button>
					</td>

				</tr>
				<tr>
					<td></td>
					<td></td>
				</tr>
				<tr>
					<td></td>
					<td>待支付订单超时未支付，订单自动关闭时间设定： <span class="num"><span
							class="num-content">${cfg_order_autoclose}</span>分钟</span> <input
						name="cfg_order_autoclose" value="${cfg_order_autoclose}"
						maxlength="6" class="entry" onkeyup="javascript:RepNumber(this)">
						<button class="changeBtn">修改配置</button>
						<button class="submit">提交</button>
						<button class="cancel">取消</button>
					</td>
				</tr>
				<tr>
					<td>周边半径配置：</td>
					<td></td>
				</tr>
				<tr>
					<td></td>
					<td><span class="ban">根据酒店经纬度(高德)，关联酒店周边信息，并给出酒店
							位置与周边信息距离。关联半径设定：</span> <span class="num"><span
							class="num-content">${cfg_peripheral_radius}</span>KM</span> <input
						name="cfg_peripheral_radius" value="${cfg_peripheral_radius}"
						maxlength="6" class="entry" onkeyup="javascript:RepNumber(this)">
						<button class="changeBtn">修改配置</button>
						<button class="submit">提交</button>
						<button class="cancel">取消</button></td>
				</tr>
			</table>
		</div>
		<script type="text/javascript">

    $(document).ready(function(){
      $(".changeBtn").click(function(){
        var index = $(this).parents("tr").index();
        index = (index + 1)/2-1
        console.log(index);
         $(".num").eq(index).css("display","none");
             $(this).css("display","none");
             $(".submit").eq(index).css("display","inline-block");
             $(".cancel").eq(index).css("display","inline-block");
             $(".entry").eq(index).css("display","inline-block");
              var numdata =  $(".num-content").eq(index).html();
            $(".entry").eq(index).val(numdata);
      });

      $(".submit").click(function(){
             var index = $(this).parents("tr").index();
             index = (index + 1)/2-1
             $(this).css("display","none");
             $(".cancel").eq(index).css("display","none");
             $(".changeBtn").eq(index).css("display","inline-block");
             $(".entry").eq(index).css("display","none");
             $(".num").eq(index).css("display","inline-block");
            
             // -----------------------------------------------------
             var value = $(".entry").eq(index).val();
             var name = $(".entry").eq(index).attr('name');
             // 这里给你预留了name, name的值你自己填一下，另外底下返回结果暂时使用is作为状态值
             $.ajax({
			  type: 'POST',
			  url: '/admin/sysconfig/sysupdate',
			  data: {cfg_id: name, cfg_value: value},
			  success: function(data){
			  	if(data == 'ok') {
			  		art.dialog({
					    title: '友情提示',
					    content: '修改成功',
					    time:2000
					});
			  		$(".num-content").eq(index).html(value);
			  	} else {
			  		art.dialog({
					    title: '友情提示',
					    content: '修改失败',
					    time:2000
					});
			  	}
              },
              error: function(data){
              	art.dialog({
				    title: '友情提示',
				    content: '网络繁忙',
				    time:2000
				});
              },
			  dataType: 'text'
			});
           });
      $(".cancel").click(function(){
        var index = $(this).parents("tr").index();
        index = (index + 1)/2-1;
        $(this).css("display","none");
        $(".submit").eq(index).css("display","none");
        $(".changeBtn").eq(index).css("display","inline-block");
        $(".entry").eq(index).css("display","none");
        $(".num").eq(index).css("display","inline-block");
      });
    });

    function RepNumber(obj) {
            var reg = /^[\d]+$/g;
             if (!reg.test(obj.value)) {
                 var txt = obj.value;
                 txt.replace(/[^0-9]+/, function (char, index, val) {//匹配第一次非数字字符
                    obj.value = val.replace(/\D/g, "");//将非数字字符替换成""
                    art.dialog({
					    title: '友情提示',
					    content: '请输入纯数字！',
					    time:2000
					});
                    var rtextRange = null;
                    if (obj.setSelectionRange) {
                        obj.setSelectionRange(index, index);
                    } else {//支持ie
                        rtextRange = obj.createTextRange();
                        rtextRange.moveStart('character', index);
                        rtextRange.collapse(true);
                        rtextRange.select();
                    }
                })
             }
         }
  </script>
	</div>
	admin/sysconfig/sysupdate
</body>
</html>
