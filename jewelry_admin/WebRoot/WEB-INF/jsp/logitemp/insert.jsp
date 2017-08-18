<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<html>
<head>
<meta charset="utf-8">
<title>运费管理</title>
<!--cbq style-->
<link rel="stylesheet" type="text/css"
	href="/include/admin/css/freightManagement.css">

<script type="text/javascript">
/* 		function checkNumber(number) {
			number = trim(number);
		
			if (number=="") {
				art.dialog({
    			    title: '提示',
    			    content: "运费模板名称不能为空",
    			    time:2000
    			});
				return false;
			}
				
		}
		
		function trim(s){
		    return s.replace(/(^\s*)|(\s*$)/g, "");
		} */
</script>
</head>
<body>
	<div class="fm_wrap">
		<div class="fm_containner">
			<form id="">
				<div class="table_div fm_containner_table">
					<table width="100%">
						<tbody>
							<!--  运费模板编号  -->
							<!--
            <tr>
              <td class="td_left"><span class="must_span">*</span>运费模板编号：</td>
              <td class="td_right_two">
                <input class="text validate fm_cell2" type="text" name="fmNumber" isrequired="yes" tipmsg="运费模板编号" mindatalength="6" maxlength="32" maxdatalength="32" datatype="jsLetterNum" value="" id="FM_TemplateCode" placeholder="请输入"/>
                <input type="hidden" name="old_mana_name" value="">
              </td>
            </tr>
			-->
							<!--  运费模板名称  -->
							<tr>
								<td class="td_left"><span class="must_span">*</span>运费模板名称：</td>
								<td class="td_right_two"><input class="text fm_cell2"
									type="text" name="fmNumber" value="" id="FM_TemplateTitle"
									placeholder="请输入" maxlength="15"
									onchange="checkNumber(this.value);" /></td>
							</tr>
							<!--  设置宝贝地址  -->
							<tr>
								<td class="td_left"><span class="must_span">*</span>宝贝地址：</td>
								<td class="td_right_two" id="FM_Address">
									<div id="area_id_div" tipmsg="请选择地区!"></div> <input class=""
									changetip="area_id_div" type="hidden" id="area_id"
									name="parent_area_id_s" value="${parent_area_id_s}" />
								</td>
							</tr>

							<!--  设置发货时间  -->
							<tr>
								<td class="td_left"><span class="must_span">*</span>发货时间：</td>
								<td class="td_right_two"><select class="validate fm_cell2"
									id="FM_DeliveryTime" name="role_code" type="select" />
									<option value="0">请选择</option> </select></td>
							</tr>
							<!--  是否包邮  -->
							<tr>
								<td class="td_left"><span class="must_span">*</span>是否包邮：</td>
								<td class="td_right_two" id="bearFreight"><label
									class="fm_cell4 selected"><input name="bearFreight"
										type="radio" value="0" checked="checked"
										onchange="selectIsMail(this)" />买家承担运费 </label> <label
									class="fm_cell4"><input name="bearFreight" type="radio"
										value="1" onchange="selectIsMail(this)" />卖家承担运费 </label></td>
							</tr>
							<!--  记价方式设置  -->
							<tr>
								<td class="td_left"><span class="must_span">*</span>计价方式：</td>
								<td class="td_right_two" id="fmPricingWays"><label
									class="fm_cell6 selected"><input name="fmPricingWays"
										type="radio" value="0" checked="checked"
										onchange="selectPricingWays()" />按件数 </label> <!-- <label class="fm_cell6"><input name="fmPricingWays" type="radio" value="1" onchange="selectPricingWays(this)" />按重量 </label> 
									<label class="fm_cell6"><input name="fmPricingWays" type="radio" value="2" onchange="selectPricingWays(this)" />按体积</label> -->
								</td>
							</tr>
							<!-- 运送方式设置  -->
							<tr>
								<td class="td_left"><span class="must_span">*</span>运送方式：</td>
								<td class="td_right_two" id="fmtplType"><label
									class="fm_cell6"><input name="fmtplType" type="radio"
										value="2" onchange="selectShippingWays(this)" />平邮 </label> <label
									class="fm_cell6"><input name="fmtplType" type="radio"
										value="0" onchange="selectShippingWays(this)" />快递 </label> <label
									class="fm_cell6"><input name="fmtplType" type="radio"
										value="1" onchange="selectShippingWays(this)" />ems </label></td>
							</tr>

							<!-- 设置邮费  -->
							<tr>
								<td class="td_left"></td>
								<td class="td_right_two fm_addArea_container"
									id="FM_addAreaCont">
									<!-- 
                <div id="fm_set_Piece" class="fm_addArea_content">
                  <div class="list_div fm_addArea_title fm_blue">
            
                    <div class="fm_setPrice_info">
                      <label>
                        默认运费<input type="text" name="firstPiece"><span>件内</span><input type="text" name="firstPrice">元,
                        每增加  <input type="text" name="addPiece"><span>件，</span>增加运费<input type="text" name="addPrice">元
                      </label>                    
                    </div>
                  </div>
                  <div class="list_div fm_addArea_list">
                    <table class="fm_setPrice_table" border="0" cellspacing="0" cellpadding="0">
                      <tbody>
                        <tr>
                           <th width="3%">
                             <input class="all" type="checkbox" />
                           </th>
                           <th width="15%">指定地区</th>
                           <th width="10%">首件</th>
                           <th width="10%">首费</th>
                           <th width="10%">续件</th>
                           <th width="10%">续费</th>
                           <th width="15%">操作</th>
                       </tr>
                        <tr>
                          <td>
                            <input class="ids" type="checkbox" value="10011">
                          </td>
                          <td>未添加地区</td>
                          <td>
                            <input type="text" name="fmFirstPiece" value="" />
                          </td>
                          <td>
                            <input type="text" name="fmFirstPrice" value="" />
                          </td>
                           <td>
                            <input type="text" name="fmAddPiece" value="" />
                          </td>
                          <td>
                            <input type="text" name="fmAddPrice" value="" />
                          </td>                                             
                          <td class="list_td_left">
                            <input class="btn ol_colorbtn ol_bluebtn" type="button" value="选择地区" onclick="">
                            <input class="btn ol_colorbtn ol_redbtn" type="button" value="删除" onclick="">
                          </td>
                        </tr>     
                      </tbody>
                    </table>
                  </div> 
                  <div class="batchDiv">
                    <span class="batch_span"><input class="all" type="checkbox">全选</span>
                    <input class="btn ol_colorbtn ol_bluebtn" type="button" value="批量删除" onclick=""/>
                    <input class="btn ol_colorbtn ol_bluebtn" type="button" value="批量设置运费" onclick=""/>
                    <input class="btn ol_colorbtn ol_bluebtn fm_set_up_freight" type="button" value="为指定城市添加运费" onclick="addAreaList(this,addAreaHead('件'),addAreaList())"/>
                  </div>
                </div>-->
								</td>
							</tr>

							<tr>
								<td></td>
								<td>
									<div class="row50">
										<input type="button" value="新增运费模板" class="btn operbtn"
											onclick="SubmitTemplate()"> <input type="button"
											class="btn return" onclick="returnGo('/admin/logitemp/list')"
											value="返回列表">
									</div>
								</td>
							</tr>
						</tbody>
					</table>
				</div>

			</form>
		</div>
		<div class="fm_opp hide"></div>
		<!-- 批量设置运费弹窗-->
		<div id="volumeShipping" class="dialog-div hide">
			<div class="fm_addArea_container">
				<div class="fm_setPrice_info">
					<label>默认运费<input type="text" name="firstPiece" value="1"><span>件内</span><input
						type="text" name="firstPrice" value="">元, 每增加 <input
						type="text" name="addPiece" value="1" readonly><span>件，</span>增加运费<input
						type="text" name="addPrice" value="">元 ;
					</label>
				</div>
			</div>
			<div class="row50">
				<input type="button" value="保存" class="btn operbtn"
					onclick="saveVolumeShipping(this)" /> <input type="button"
					value="关闭" class="btn return" onclick="closeVolumeShipping()" />
			</div>
		</div>

		<!--地区弹窗-->
		<div id="area" class="dialog-areas hide" mode="">
			<a class="ks-ext-close" role="button"
				href="javascript:void(&quot;关闭&quot;)" tabindex="0"
				onclick="HideArea()"> <span class="ks-ext-close-x">关闭</span>
			</a>
			<div class="">
				<div class="ks-contentbox">
					<div class="ks-stdmod-header" id="ks-dialog-header1834">
						<div class="title">选择地区</div>
					</div>
					<div class="ks-stdmod-body">
						<ul id="J_CityList">
							<!--      <li class="pro_1 hidden">
				<div class=" dcity clearfix">
				  <div class="ecity gcity">
					<span class="group-label">
					  <label style="position: relative;">
						<input type="checkbox" id="J_Group_1" class="J_Group" value="1">
						 <img src="/include/admin/image/interChecked.png" alt="" class="indeterminate hide" style="" />
					  </label>
					  <label for="J_Group_1">华南</label>
					</span>
				  </div>
					<div class="province-list">
					  <div class="ecity" citysnum="1" selectednum="0">
						  <span class="gareas">
							<label style="position: relative;">
							  <input type="checkbox" class="J_Province" id="J_Province_1_1" value="1_1">
							  <img src="/include/admin/image/interChecked.png" alt="" class="indeterminate hide" style="" />
							</label>
							<label for="J_Province_1_1">
							  北京<span class="checkedNum txtRed"></span>
							</label>
							<span onclick="ShowCounty(this)" class="trigger"></span>
						  </span>
						  <div class="citys">
							  <span class="areas">
								<label style="position: relative;">
								  <input type="checkbox" class="J_City" id="J_City_1_1_1" value="1_1_1">
								  <img src="/include/admin/image/interChecked.png" alt="" class="indeterminate hide" style="" />
								</label>
								<label for="J_City_1_1_1">1111</label>
							  </span>                                                                                                                 
							  <p style="text-align: right;">
								  <input type="button" class="close_button" onclick="HideCounty(this)" value="关闭">
							  </p>
						  </div>

					  </div>

					</div>
				</div>
			  </li> 
	-->
						</ul>
						<div class="btns">
							<button class="FM_Submit btn ol_colorbtn ol_bluebtn"
								type="button" onclick="AreaSure()">确定</button>
							<button class="FM_Cancel btn ol_colorbtn ol_redbtn" type="button"
								onclick="HideArea()">取消</button>
						</div>
					</div>
					<div class="ks-stdmod-footer"></div>
				</div>
			</div>
		</div>

	</div>

	<!-- <script type="text/javascript" src="include/common/js/fm.js"></script> -->
	<script type="text/javascript" src="/include/admin/js/fm.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			//地区级联
			$("#area_id_div").cascadeSel({
				url : "/admin/area/getList",
				name : "area_id",
				init_id : "${cfg_top_area}",
				li_id : "area_id",
				li_name : "area_name",
				showLevel:"2"
			});
		})
		//提交表单
		SubmitTemplate = function() {
			var number = $("#FM_TemplateTitle").val();
			number = number.replace(/(^\s*)|(\s*$)/g, "");
			if (number == "") {
				art.dialog({
					title : '提示',
					content : "运费模板名称不能为空",
					time : 2000
				});
				return false;
			}
	
			var is_pass_check = checkSubmitData();
			//验证通过，提交表单
			if (CheckInput()) {
				if (is_pass_check) {
					//alert("验证通过");
					// if (!CheckInput())
					//     return;
					//var code = $('#FM_TemplateCode').val();//模板编号
					var fareTemplate = submitDataObj(0, 0, 0);
					var dataInfo = JSON.stringify(fareTemplate);
					$.ajax({
						url : "/admin/logitemp/insertone",
						data : {
							"data" : dataInfo
						},
						type : "POST",
						success : function(data) {
							console.log(data);
							//  console.log(data.code)
							if (data.code == 0) {
								art.dialog({
									title : '提示',
									content : "添加成功！！",
									time : 2000
								});
								returnGo('/admin/logitemp/list')
							} else {
								console.log(data.desc)
							}
						},
						error : function() {
							art.dialog({
								title : '提示',
								content : "添加失败！",
								time : 2000
							});
						}
					});
				} else {
					goErrorPos();
				}
			}
		}
	</script>
</body>
</html>
ctAreasObj(as_id) delectArr.push(obj); console.log(delectArr); }
$(this).parents('tr.setAreaList').remove(); }) if($('.fm_addArea_list
tbody tr').length<2){ $('.fm_addArea_list tbody').empty() $('.batch_span
input[name="chkSetAreaList"]').prop('checked',false)
$('label[name="chkSetAreaList"]').hide(); } } }
$(document).ready(function(){ //地区级联 $("#area_id_div").cascadeSel({
url:"/admin/area/getList", name:"area_id", init_id:"${cfg_top_area}",
li_id:"area_id", li_name:"area_name", }); }) var sendTime = new
Array();//发货时间 var areaArr = {};//地区选择 $.ajax({ url:
"/admin/logitemp/getinit", data: "", type: "POST", success: function
(data) { console.log(data); if (data.code == 0){ //
console.log(data.info); var sendTimeList="";
$.each(data.info.sendtime,function(i,item){ sendTimeList+="
<option value='"+item.id+"'>"+item.name+"</option>
"; }) $('#FM_DeliveryTime').append(sendTimeList); var region = ""; var
privinces1 = new Array(); var privinces2 = new Array(); var privinces3 =
new Array(); var privinces4 = new Array(); var privinces5 = new Array();
var privinces6 = new Array(); var privinces7 = new Array(); var
privinces0 = new Array(); var regions = new Array(); var regionArr1={};
var regionArr2={}; var regionArr3={}; var regionArr4={}; var
regionArr5={}; var regionArr6={}; var regionArr7={}; var regionArr0={};

var rProvincesArr1 = new Array(), rProvincesArr2 = new Array(),
rProvincesArr3 = new Array(), rProvincesArr4 = new Array(),
rProvincesArr5 = new Array(), rProvincesArr6 = new Array(),
rProvincesArr7 = new Array(), rProvincesArr0 = new Array();
$.each(data.info.logiArea,function(i,p_item){ // console.log(items.name)
var privincesArr={}; var pCitysArr = new Array(); var citys = new
Array(); region = p_item.region; privincesArr =
pushArr(privincesArr,'area_id','area_name',p_item.area_id,p_item.area_name)
//privincesArr['area_id'] = p_item.area_id; //privincesArr['area_name']
= p_item.area_name; $.each(p_item.child_area,function(i,c_item){ var
citysArr = {}; citysArr['prev_id'] = { 'id': p_item.area_id, 'name':
p_item.area_name } privincesArr[p_item.area_id] =
pushRArr(pCitysArr,c_item.area_id); citysArr =
pushArr(citysArr,'area_id','area_name',c_item.area_id,c_item.area_name)
// citysArr['area_id'] = item.area_id; // citysArr['area_name'] =
item.area_name; citys.push(citysArr); }) privincesArr['citys'] = citys;
switch(region){ case "1": privincesArr['prev_id'] = { 'id': 1, 'name':
"华东" } regionArr1[1]=pushRArr(rProvincesArr1,p_item.area_id); regionArr1
= pushArr(regionArr1,'region_id','region_name',"1","华东")
if(privincesArr.length!==0){ privinces1.push(privincesArr); } break;
case "2": privincesArr['prev_id'] = { 'id': 2, 'name': "华北" }
regionArr2[2]=pushRArr(rProvincesArr2,p_item.area_id); regionArr2 =
pushArr(regionArr2,'region_id','region_name',"2","华北")
if(privincesArr.length!==0){ privinces2.push(privincesArr); //
console.log(privincesArr) } break; case "3": privincesArr['prev_id'] = {
'id': 3, 'name': "华中" }
regionArr3[3]=pushRArr(rProvincesArr3,p_item.area_id); regionArr3 =
pushArr(regionArr3,'region_id','region_name',"3","华中")
if(privincesArr.length!==0){ privinces3.push(privincesArr); } break;
case "4": privincesArr['prev_id'] = { 'id': 4, 'name': "华南" }
regionArr4[4]=pushRArr(rProvincesArr4,p_item.area_id); regionArr4 =
pushArr(regionArr4,'region_id','region_name',"4","华南")
if(privincesArr.length!==0){ privinces4.push(privincesArr); } break;
case "5": privincesArr['prev_id'] = { 'id': 5, 'name': "东北" }
regionArr5[5]=pushRArr(rProvincesArr5,p_item.area_id); regionArr5 =
pushArr(regionArr5,'region_id','region_name',"5","东北")
if(privincesArr.length!==0){ privinces5.push(privincesArr); } break;
case "6": privincesArr['prev_id'] = { 'id': 6, 'name': "西北" }
regionArr6[6]=pushRArr(rProvincesArr6,p_item.area_id); regionArr6 =
pushArr(regionArr6,'region_id','region_name',"6","西北")
if(privincesArr.length!==0){ privinces6.push(privincesArr); } break;
case "7": privincesArr['prev_id'] = { 'id': 7, 'name': "西南" }
regionArr7[7]=pushRArr(rProvincesArr7,p_item.area_id); regionArr7 =
pushArr(regionArr7,'region_id','region_name',"7","西南")
if(privincesArr.length!==0){ privinces7.push(privincesArr); } break;
default: privincesArr['prev_id'] = { 'id': 0, 'name': "其他" }
regionArr0[0]=pushRArr(rProvincesArr0,p_item.area_id); regionArr0 =
pushArr(regionArr0,'region_id','region_name',"0","其他")
if(privincesArr.length!==0){ privinces0.push(privincesArr); } break; }

//成功后储存到本地中 //var str=JSON.stringify(sendTime) ;
//localStorage.setItem('newsList',str); }) regionArr1["provinces"] =
privinces1; regions.push(regionArr1); regionArr2["provinces"] =
privinces2; regions.push(regionArr2); regionArr3["provinces"] =
privinces3; regions.push(regionArr3); regionArr4["provinces"] =
privinces4; regions.push(regionArr4); regionArr5["provinces"] =
privinces5; regions.push(regionArr5); regionArr6["provinces"] =
privinces6; regions.push(regionArr6); regionArr7["provinces"] =
privinces7; regions.push(regionArr7); if(!privinces0){
regionArr0["provinces"] = privinces0; regions.push(regionArr0); }


areaArr['areas'] = regions; // areaList = areaArr; console.log(areaArr)
// console.log(JSON.stringify(sendTime)) //
console.log(JSON.stringify(areaArr)) } }, error: function () {
alert("失败"); } }); //对应子集组合 pushRArr = function(arr,value){
arr.push(value) return arr; } //设置对象 pushArr =
function(myobj,idkey,namekey,id,name){ myobj[idkey] = id; myobj[namekey]
= name; return myobj; } areaContent = function(){ var html=""; // var
areaList = JSON.stringify(areaArr) // console.log('111'+areaList.areas)
$.each(areaArr.areas,function(i,r_item){ // console.log("111"+r_item)
html+='
<li class="pro_'+r_item.region_id+' hidden">'; html+='
	<div class=" dcity clearfix">
		'; html+='
		<div class="ecity gcity">
			'; html+=' <span class="group-label">'; html+=' <label
				style="position: relative;">'; html+=' <input
					type="checkbox" id="J_Group_'+r_item.region_id+'" class="J_Group"
					value="'+r_item.region_id+'">'; html+=' <img
					src="/include/admin/image/interChecked.png" alt=""
					class="indeterminate hide" style="" />'; html+='
			</label>'; html+=' <label for="J_Group_'+r_item.region_id+'">'+r_item.region_name+'
			</label>'; html+='
			</span>'; html+='
		</div>
		'; html+='
		<div class="province-list">
			'; $.each(r_item.provinces,function(j,p_item){ html+='
			<div class="ecity" as_id="1" rea_id="0">
				'; html+=' <span class="gareas">'; html+=' <label
					style="position: relative;">'; html+=' <input
						type="checkbox" class="J_Province"
						id="J_Province_'+r_item.region_id+'_'+p_item.area_id+'"
						value="'+r_item.region_id+'_'+p_item.area_id+'">'; html+='
						<img src="/include/admin/image/interChecked.png" alt=""
						class="indeterminate hide" style="" />'; html+='
				</label>'; html+=' <label
					for="J_Province_'+r_item.region_id+'_'+p_item.area_id+'">';
						html+=' '+p_item.area_name+'<span class="checkedNum txtRed"></span>';
						html+='
				</label>'; html+=' <span onclick="ShowCounty(this)" class="trigger"></span>';
					html+='
				</span>'; html+='
				<div class="citys">
					'; $.each(p_item.citys,function(j,c_item){ html+=' <span
						class="areas">'; html+=' <label style="position: relative;">';
							html+=' <input type="checkbox" class="J_City"
							id="J_City_'+r_item.region_id+'_'+p_item.area_id+'_'+c_item.area_id+'"
							value="'+r_item.region_id+'_'+p_item.area_id+'_'+c_item.area_id+'">';
							html+=' <img src="/include/admin/image/interChecked.png" alt=""
							class="indeterminate hide" style="" />'; html+='
					</label>'; html+=' <label
						for="J_City_'+r_item.region_id+'_'+p_item.area_id+'_'+c_item.area_id+'">'+c_item.area_name+'</label>';
						html+='
					</span>'; }) html+='
					<p style="text-align: right;">
						'; html+=' <input type="button" class="close_button"
							onclick="HideCounty(this)" value="关闭">'; html+='
					</p>
					'; html+='
				</div>
				'; html+='
			</div>
			'; }) html+='
		</div>
		'; html+='
	</div>'; html+='
</li>
'; }) return html; } //创建提交的数据对象 submitDataObj =
function(ship_id,com_id,goods_id){ var name =
$("#FM_TemplateTitle").val();//模板名 var shopAddr =
$("#area_id_div>select:last-child").val();//宝贝地址 var dispatchTimeId =
$("#FM_DeliveryTime").val();//发货时间 var dispatchTime =
$("#FM_DeliveryTime").find('option[value="'+dispatchTimeId+'"]').text();
var isInclPostage = $("input[name='bearFreight']:checked").val();//包邮
var valuationModel = $("#fmPricingWays
input[name='fmPricingWays']:checked").val();//计价方式 var plType =
$("#fmtplType input[name='fmtplType']:checked").val();//快递方式 var
modifyTime = CurrTime(); var carryModes = {}; var regionArr = new
Array(); // if(CheckInput()){ //快递的记录数 var carryArr = new Array();
if(!delectArr){ carryArr = delectArr; } if(isInclPostage === "0"){ var
postMode ={}; var postInput =
$("#FM_theDefaultShipping").find(".fm_setPrice_info").find("input[type='text']");
postMode.as_id = parseInt($("#FM_theDefaultShipping").attr('as_id'));
postMode.ship_id =
parseInt($("#FM_theDefaultShipping").attr('ship_id'));
postMode.express_start = parseFloat(postInput.eq(0).val());
postMode.express_plus = parseFloat(postInput.eq(2).val());
postMode.express_price = parseFloat(postInput.eq(1).val());
postMode.express_priceplus = parseFloat(postInput.eq(3).val());
postMode.reach_area = new Array(); postMode.default_ship = "1";
carryArr.push(postMode); }else{ } if ($("#FM_Tbody>tr").length > 0) {
$("tbody[mode='post']").find("tr").not(':first-child').each(function ()
{ var inputs = $(this).find(":text"); var carryMode = {}; var regionStr
= $(this).find("input[name='inclpost']").val(); var regionNameStr =
$(this).find('.area-group p').text(); // alert(regionNameStr )
carryMode.as_id = parseInt($(this).attr('as_id')); carryMode.ship_id =
parseInt($(this).attr('ship_id')); carryMode.express_start =
parseFloat(inputs.eq(0).val()); carryMode.express_plus =
parseFloat(inputs.eq(2).val()); carryMode.reach_area =
regionMg(regionStr,regionNameStr); carryMode.express_price =
parseFloat(inputs.eq(1).val()); carryMode.express_priceplus =
parseFloat(inputs.eq(3).val()); carryMode.default_ship = "0";
carryArr.push(carryMode); }); } // } //carryModes.areas = carryArr // }
//模板对象 var fareTemplate = { "ship_id":ship_id, "com_id":com_id,
"goods_id":goods_id, "ship_name": name, "start_area": shopAddr,
"send_time_id":dispatchTimeId, "send_time": dispatchTime,
"valuation_mode":valuationModel, "express_id_str":plType,
"free_ship":isInclPostage, "tem_modify_time":modifyTime,
"area_info":carryArr }; console.log(fareTemplate) return fareTemplate; }
//提交表单 SubmitTemplate = function() { var is_pass_check =
checkSubmitData(); //验证通过，提交表单 if(CheckInput()){ if(is_pass_check){
//alert("验证通过"); // if (!CheckInput()) // return; //var code =
$('#FM_TemplateCode').val();//模板编号 var fareTemplate =
submitDataObj(0,0,0); var dataInfo = JSON.stringify(fareTemplate);
$.ajax({ url: "/admin/logitemp/insertone", data: {"data":dataInfo},
type: "POST", success: function (data) { console.log(data); //
console.log(data.code) if (data.code == 0){ alert("添加成功！");
returnGo('/admin/logitemp/list') }else{ console.log(data.desc) } },
error: function () { alert("添加失败！"); } }); }else{ goErrorPos(); } } }
</script>
</body>
</html>
l+='
</span>
'; }) html+='
<p style="text-align: right;">
	'; html+=' <input type="button" class="close_button"
		onclick="HideCounty(this)" value="关闭">'; html+='
</p>
'; html+='
</div>
'; html+='
</div>
'; }) html+='
</div>
'; html+='
</div>
'; html+='
</li>
'; }) return html; } regionMg = function(str){ var areaArr = new
Array(); var regionArr = new Array(); var regionObj = {}; areaArr =
str.split('|'); $.each(areaArr,function(i,item){ var codeArr = new
Array(); codeArr = item.split('_'); regionObj.rea_id = "0";
regionObj.as_id = "0"; regionObj.end_area = codeArr[codeArr.length-1];
regionArr.push(regionObj) }) // console.log(regionArr) return regionArr;
} //提交表单 SubmitTemplate = function() { submitData(); // if
(!CheckInput()) // return; //var code =
$('#FM_TemplateCode').val();//模板编号 var name =
$("#FM_TemplateTitle").val();//模板名 var shopAddr =
$("#area_id_div>select:last-child").val();//宝贝地址 var dispatchTime =
$("#FM_DeliveryTime").val();//发货时间 var isInclPostage =
$("input[name='bearFreight']:checked").val();//包邮 var valuationModel =
$("#fmPricingWays input[name='fmPricingWays']:checked").val();//计价方式 var
plType = $("#fmtplType input[name='fmtplType']:checked").val();//快递方式

var carryModes = {}; var regionArr = new Array(); if(CheckInput()){
//快递的记录数 var carryArr = new Array(); var postMode ={}; var postInput =
$("#FM_theDefaultShipping").find(".fm_setPrice_info").find("input[type='text']");
postMode.as_id = "0"; postMode.ship_id = "0"; postMode.express_start =
parseFloat(postInput.eq(0).val()); postMode.express_plus =
parseFloat(postInput.eq(2).val()); postMode.express_price =
parseFloat(postInput.eq(1).val()); postMode.express_priceplus =
parseFloat(postInput.eq(3).val()); postMode.reach_area ="";
postMode.default_ship = "1"; carryArr.push(postMode); if
($("#FM_Tbody>tr").length > 0) {
$("tbody[mode='post']").find("tr").not(':first-child').each(function ()
{ var inputs = $(this).find(":text"); var carryMode = {}; var regionStr
= $(this).find("input[name='inclpost']").val(); carryMode.as_id = "0";
carryMode.ship_id = "0"; carryMode.express_start =
parseFloat(inputs.eq(0).val()); carryMode.express_plus =
parseFloat(inputs.eq(2).val()); carryMode.reach_area =
regionMg(regionStr); carryMode.express_price =
parseFloat(inputs.eq(1).val()); carryMode.express_priceplus =
parseFloat(inputs.eq(3).val()); carryMode.default_ship = "0";
carryArr.push(carryMode); }); } //carryModes.areas = carryArr // }
//模板对象 var fareTemplate = { "ship_id":0, "com_id":0, "goods_id":0,
"ship_name": name, "start_area": shopAddr, "send_time_id":dispatchTime,
"send_time": 0, "valuation_mode":valuationModel,
"express_id_str":plType, "free_ship":isInclPostage, "tem_modify_time":0,
"area_info":carryArr }; console.log(fareTemplate) $.ajax({ url:
"/admin/logitemp/insertone", data: fareTemplate, type: "POST", success:
function (data) { console.log(data); // console.log(data.code) if
(data.code == 0){ alert("添加成功！"); returnGo('/admin/logitemp/list')
}else{ console.log(data.desc) } }, error: function () { alert("添加失败！");
} }); } } })
</script>
</body>
</html>


}else{ console.log(data.desc) } }, error: function () { alert("添加失败！");
} }); } } })
</script>
</body>
</html>


t>
</body>
</html>


onsole.log(fareTemplate) // $.ajax({ // url: "Create", // data:
fareTemplate, // type: "POST", // success: function (data) { //
alert(data.msg); // if (data.isok) // location.href = "index"; // }, //
error: function () { // alert("添加失败！"); // } // }); } })
</script>
</body>
</html>


}, // error: function () { // alert("添加失败！"); // } // }); } })
</script>
</body>
</html>


/html> >
</body>
</html>


e: name, ShopAddr: shopAddr, DispatchTime: dispatchTime, IsInclPostage:
isInclPostage, ValuationModel: valuationModel, plType: plType //
IsInclPostageByif: isInclPostageByif, // InclPostageProvisos:
inclPostages, // CarryModes: carryModes }; console.log(fareTemplate) //
$.ajax({ // url: "Create", // data: fareTemplate, // type: "POST", //
success: function (data) { // alert(data.msg); // if (data.isok) //
location.href = "index"; // }, // error: function () { //
alert("添加失败！"); // } // }); } })
</script>
</body>
</html>






// }); } })
</script>
</body>
</html>


y>
</html>


List"/>'; list+='
</th>
'; list+='
<th width="15%">指定地区</th>
'; list+='
<th width="10%">首'+(name+unit)+'</th>
'; list+='
<th width="10%">首费</th>
'; list+='
<th width="10%">续'+(name+unit)+'</th>
'; list+='
<th width="10%">续费</th>
'; list+='
<th width="15%">操作</th>
'; list+='
</tr>
'; return list; } addAreaList = function(num){ var list=""; list+='
<tr class="setAreaList">
	'; list+='
	<td>'; list+=' <input class="ids" type="checkbox"
		value="setAreaList" name="chkSetAreaList">'; list+='
	</td>'; list+='
	<td class="cell-area">'; list+='
		<div class="area-group">
			'; list+='
			<p class="area add_region_range" id="express'+num+'">未添加地区</p>
			'; list+='
		</div>'; list+=' <input type="hidden" value="" name="inclpost">';
		list+='
	</td>'; list+='
	<td>'; list+=' <input class="input-text " type="text"
		aria-label="首件" maxlength="6" autocomplete="off" value="1"
		data-field="start" name="fmfirstPiece">'; list+='
	</td>'; list+='
	<td>'; list+=' <input class="input-text " type="text"
		aria-label="首费" maxlength="6" autocomplete="off" value=" "
		data-field="postage" name="fmfirstPrice">'; list+='
	</td>'; list+='
	<td>'; list+=' <input class="input-text " type="text"
		aria-label="续件" maxlength="6" autocomplete="off" value="1"
		data-field="plus" name="fmaddPiece">'; list+='
	</td>'; list+='
	<td>'; list+=' <input class="input-text " type="text"
		aria-label="续费" maxlength="6" autocomplete="off" value=" "
		data-field="postageplus" name="fmaddPrice">'; list+='
	</td> '; list+='
	<td class="list_td_left">'; list+=' <input
		class="btn ol_colorbtn ol_bluebtn" type="button" value="选择地区"
		onclick="CheckArea(this)">'; list+=' <input
		class="btn ol_colorbtn ol_redbtn" type="button" value="删除"
		onclick="delectList(this)">'; list+='
	</td>'; list+='
</tr>
'; return list; } $.ajax({ url: "/admin/logitemp/getinit", data: "",
type: "POST", success: function (data) { alert(data.msg); if (data.isok)
location.href = "index"; }, error: function () { alert("失败"); } }); })
</script>
</body>
</html>

alue="删除" onclick="delectList(this)">'; list+='
</td>
'; list+='
</tr>
'; return list; } })
</script>
</body>
</html>

<td class="list_td_left">'; list+=' <input
	class="btn ol_colorbtn ol_bluebtn" type="button" value="选择地区"
	onclick="CheckArea(this)">'; list+=' <input
	class="btn ol_colorbtn ol_redbtn" type="button" value="删除"
	onclick="delectList(this)">'; list+='
</td>
'; list+='
</tr>
'; return list; } })
</script>
</body>
</html>

</tr>
'; return list; } })
</script>
</body>
</html>


ript>
</body>
</html>


+='
</tr>
'; return list; } })
</script>
</body>
</html>

</tr>
'; return list; } })
</script>
</body>
</html>





tr> '; return list; } })
</script>
</body>
</html>





body>
</html>





})
</script>
</body>
</html>





