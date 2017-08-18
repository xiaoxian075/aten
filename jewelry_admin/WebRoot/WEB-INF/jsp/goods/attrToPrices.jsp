<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/7/2
  Time: 16:43
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<!-- 根据属性以及型号新增价格--模板 -->

<style type="text/css">
button {
	margin-right: 20px;
	margin: 20px 0px;
}

.createtable {
	width: 800px;
}

.checkboxInput {
	margin-left: 10px;
}

.createtable table {
	background: rgba(7, 17, 27, 0.2);
}

.createtable table td {
	background: #FFF;
}

.createtable #tableChecbox td {
	padding: 5px 10px;
	height: 30px;
}

.createtable #tableChecbox .checkboxInput {
	width: 100px;
}

.createtable #tableChecbox .showContent {
	margin-right: 20px;
}

.createtable .unit {
	color: rgba(7, 17, 27, 0.5);
	display: inline-block;
	width: 40px;
	text-align: center;
}

.createtable #tableShow th {
	width: 14%;
	background: #e7e7e7;
	height: 44px;
	text-align: center;
}

.createtable #tableShow td {
	width: 14%;
	background: #FFF;
	height: 44px;
	text-align: center;
	padding: 0px 4px;
}

.input {
	width: 90px;
	height: 30px;
	padding: 0px;
	box-sizing: border-box;
}

.setInitData {
	width: 80px;
	margin-right: 20px;
	margin-bottom: 20px;
}

#setInit {
	text-align: right;
	margin-top: 20px;
}
</style>

<tr class="goldPrice">
	<td class="td_left">当日金价：</td>
	<td colspan="2" class="td_right_two"><input disabled="true"
		type="text" name="gold_price"> 元/克</td>
</tr>
<tr class="handworkPrice">
	<td class="td_left">手工费：</td>
	<td colspan="2" class="td_right_two"><input type="text"
		name="manual_fee" onchange="manualChange()"> 元/克</td>
</tr>
<tr>
	<td class="td_left"></td>
	<td colspan="2" class="td_right_two">
		<div class="createtable" id="createtable">
			<div id="tableChecbox">
				<table width="100%" border="1" cellspacing="1" cellpadding="0">
					<tbody id="hehe"></tbody>
				</table>
			</div>


			<div id="tableShow" style="display: none;">
				<div id="setInit">
					<span>商品价格：</span><input type="text" id="price" class="setInitData">
					<span>数量：</span><input type="text" id="count" class="setInitData">
					<input type="button" value="一键设置" class="setInitData"
						onclick="setInit()">
				</div>
				<table width="100%" border="1" cellspacing="1" cellpadding="0"
					id="tableAll">
					<thead id="tablehead">

					</thead>
					<tbody class="tableBoty" id="tableBody">
					</tbody>
				</table>
			</div>
			<div id="hiddenSelectedInputID"></div>
			<div id="hiddenInputID"></div>
		</div>
	</td>
</tr>

<script type="text/javascript">
	var showTable = {
		'show' : [ {
			'text' : '商品价格(元)',
			'name' : 'price',
			'type' : '0',
			'isChange' : '1'
		}, {
			'text' : '手工费(元)',
			'name' : 'handPrice',
			'type' : '1',
			'isChange' : '1'
		}, {
			'text' : '售价(元)',
			'name' : 'sellPrice',
			'type' : '1',
			'isChange' : '0'
		}, {
			'text' : '数量',
			'name' : 'count',
			'type' : '0',
			'isChange' : '0'
		} ]

	};
	$(function() {

		var dataArray = new Array();
		var str = JSON.stringify(dataArray);
		localStorage.setItem("saveShowData", str);
		getData();
		flagCome = 1;
		zhongIndex = '';
		lastZhongLength = 0;
		chooseZhongLength = 0;
		sortPriceArray = new Array();
        sortCountArray = new Array();

	});
	function getData() {
		var catId = $('input[name="cat_id"]').val();
		$.post('${attrUrl}', function(data) {
			isGold = data.isGold;
			isManualFee = data.isManualFee;
			gold_price = data.gold_price
			manual_fee = data.manual_fee
			config = data;
			if (isGold === '0') {
				$(".goldPrice").css('display', 'none');
			} else {
				$(".goldPrice").removeAttr("style");
				
				$("input[name='gold_price']").val(gold_price);
				
			}
			if(isManualFee == '0'){
				$(".handworkPrice").css('display', 'none');
			}else{
				$(".handworkPrice").removeAttr("style");
				$("input[name='manual_fee']").val(manual_fee);
			}

			createChoseTable();
		});

		var goods_id = $("input[name='goods_id']").val();
		$.post("/admin/goods/getGoodsAttr", {
			goods_id : goods_id
		}, function(data) {
			if (data.code === 0) {
				huiData = data.info
			} else {
				huiData = new Array()
			}
		});
	}
	function createChoseTable() {
		var stres = '';
		var dataArray = config.skuList;
		for (var i = 0; i < dataArray.length; i++) {
			var dataObj = dataArray[i];
			if (dataObj.data.length > 0) {
				dataObj.data.push('');
			} else {
				dataObj.data = [ '' ]
			}

			// 记录重量是在第几条
			if (dataObj.text === '重量') {
				zhongIndex = i;
			    zhongIndex = parseInt(zhongIndex)
			}

			if (dataObj.is_custom_value === '1') {
				if (dataObj.is_must == 1) {
                  var str1 = '<tr>' + '<td style="background-color: #eee">'+ dataObj.text + '<span class="must_span">*</span></td>' + '</tr>';
				}else{
                   var str1 = '<tr>' + '<td style="background-color: #eee">'+ dataObj.text + '<span class="must_span"></span></td>' + '</tr>';
				}
				
				var str2 = '';
				for (var z = 0; z < dataObj.data.length; z++) {

					var daobj = dataObj.data[z];
					var dShow = daobj.attr_value;
					var checkboxStr = ''

					var daattrid = daobj.attr_value_id;
					// console.log('-----',daattrid)
					if (!daattrid) {
						daattrid = uuid();
					}
					if (!dShow) {
						checkboxStr = '<input type="checkbox" id="'
								+ dataObj.attr_id
								+ '###'
								+ daattrid
								+ '" name="selected'
								+ dataObj.attr_id
								+ '" value="" onchange="createTable('
								+ i
								+ ','
								+ z
								+ ')">'
								+ '<input type="text" class="checkboxInput" value="" onchange="createTable('+ i+ ','+ z+')">'

					} else {
						if (daobj.state === '100') {
							checkboxStr = '<input type="checkbox" checked id="'
									+ dataObj.attr_id
									+ '###'
									+ daattrid
									+ '"  name="selected'
									+ dataObj.attr_id
									+ '" value="" onchange="createTable('
									+ i
									+ ','
									+ z
									+ ')">'
									+ '<input type="text" class="checkboxInput" value='
									+ dShow
									+ ' onchange="createTable('+ i+ ','+ z+')">'

						} else {
							checkboxStr = '<input type="checkbox" id="'
									+ dataObj.attr_id
									+ '###'
									+ daattrid
									+ '"  name="selected'
									+ dataObj.attr_id
									+ '" value="" onchange="createTable('
									+ i
									+ ','
									+ z
									+ ')">'
									+ '<input type="text" class="checkboxInput" value='
									+ dShow
									+ ' onchange="createTable('+i+','+z+')">'

						}

					}

					var s = '<tr>' + '<td>' + checkboxStr + '</td>' + '</tr>';
					str2 += s;
				}
				var str3 = '<tr class="hidden'+dataObj.attr_id+'" style="display:none">'
						+ '<td></td>' + '</tr>';
				var str = str1 + str2 + str3;
				stres += str;
			} else {

				var str1 = '<tr>' + '<td style="background-color: #eee">'
						+ dataObj.text + '<span class="must_span">*<span></td>' + '</tr>';
				// var str3 = '';
				// for (var j=0 ;j<dataObj.data.length;j++) {
				// var daobj = dataObj.data[z];
				// console.log('++++++++++++',dataObj)
				dataObj.data.pop();
				var str2 = '';
				for (var k = 0; k < dataObj.data.length; k++) {
					var objD = dataObj.data[k];
					if (objD.state === '100') {
						var s2 = '<input type="checkbox" checked id="'
								+ dataObj.attr_id
								+ '###'
								+ objD.attr_value_id
								+ '" name="selected'
								+ dataObj.attr_id
								+ '" value="'
								+ objD.attr_value
								+ '" onchange="createTable('+i+','+k+')"><span class="showContent">'
								+ objD.attr_value + '</span>';

					} else {
						var s2 = '<input type="checkbox" id="'
								+ dataObj.attr_id
								+ '###'
								+ objD.attr_value_id
								+ '" name="selected'
								+ dataObj.attr_id
								+ '" value="'
								+ objD.attr_value
								+ '" onchange="createTable('+i+','+k+')"><span class="showContent">'
								+ objD.attr_value + '</span>';

					}

					str2 += s2;
				}
				var s3 = '<tr><td>' + str2 + '</td></tr>';
				str2 += s3;

				// }
				str = str1 + str2;
				stres += str;
			}
		}

		$("#hehe").html(stres);

		//--------------------设置属性------------------------
		var showdataArray = showTable.show;
		var showStr = '';
		// for (var i = 0; i < showdataArray.length; i++) {
		// 	var showObj = showdataArray[i]
		// 	if (showObj.type === '0' && showObj.name !== 'handPrice') {
		// 		var showS = '<span>'
		// 				+ showObj.text
		// 				+ '：</span><input type="text" id="'+showObj.name+'" class="setInitData">';
		// 		showStr += showS;
		// 	}

		// }
		if (isGold == 0) {
            showStr += '<span>商品价格(元)：</span><input type="text" id="price" class="setInitData">';
        }
        showStr += '<span>数量：</span><input type="text" id="count" class="setInitData">';

		showStr += '<input type="button" value="一键设置" class="setInitData" onclick="setInit()">';
		$("#setInit").html(showStr);

		createTable()

	}
	function createTableHead() {

		var selecteddataArray = config.skuList;
		var str1 = '';
		headerText = ''
		// headerTextId = ''
		for (var i = 0; i < selecteddataArray.length; i++) {
			var obj = selecteddataArray[i];
			var s1 = '<th>' + obj.text + '</th>';
			str1 += s1;
			if (i === 0) {
				headerText += obj.text
			} else {
				headerText += '###' + obj.text
			}

		}

		var showdataArray = showTable.show;
		var str2 = '';
		for (var i = 0; i < showdataArray.length; i++) {
			var obj = showdataArray[i];
			var s2 = '<th>' + obj.text + '</th>';
			str2 += s2;
			// headerText += '###'+obj.text
		}
		var str = '<tr>' + str1 + str2 + '</tr>';

		$("#tablehead").html(str);

	}
	function createTable(i, z) {

      var isFromCreat = arguments[2] ? arguments[2] : 1;
      if (isFromCreat != 1) {
         if ( $(isFromCreat).next().val() == '') {
         	return
         }
      }

		var dataArray = config.skuList;

		if (i == 0 || i > 0) {
			var dataArr = dataArray[i].data;
			$("input[name='selected" + dataArray[i].attr_id + "']").each(
					function() {
						if ($(this).get(0).checked) {
							$(this).parents("tr").append()
							return true;
						} else {
							if (dataArray[i].is_custom_value === '1'
									&& $(this).next().val() === '') {
								$(this).parents("tr").eq(0).remove();
							}
						}
					});

			// console.log(dataArr)
			var s = '<tr>'
					+ '<td>'
					+ '<input type="checkbox" id="'
					+ dataArray[i].attr_id
					+ '###'
					+ uuid()
					+ '" name="selected'
					+ dataArray[i].attr_id
					+ '" value="" onchange="createTable('
					+ i
					+ ','
					+ z
					+ ',this)">'
					+ '<input type="text" class="checkboxInput" onchange="createTable('+i+','+z+')">' + '</td>' + '</tr>';
			var classStr = '.hidden' + dataArray[i].attr_id
			$(classStr).before(s)
		}
		var choseDataArray = new Array()
		var dataAllIdArray = new Array()
		headerTextId = ''
		for (var i = 0; i < dataArray.length; i++) {
			var dataObject = dataArray[i];
			dataObject.choseData = [];
			dataIdArray = new Array()
			$('input[name="selected' + dataObject.attr_id + '"]:checked').each(
					function() {
						if (dataObject.is_custom_value == '1') {
							var str = $(this).next().val();

							var isIn = $.inArray(str, dataObject.choseData);
							if (isIn != -1) {
								alertTip('同属性数据不能重复！');
								return false;
							}
						} else {
							var str = $(this).val()
						}

						if (str) {
							dataObject.choseData.push(str);
						}

						dataIdArray.push($(this).attr("id"))
					});
			dataAllIdArray.push(dataIdArray)
			choseDataArray.push(dataObject.choseData);
			// console.log(dataIdArray)
			// console.log(choseDataArray)
			// -------------------------------------------------------------------------

			var textIdString = dataObject.attr_id + "###" + dataObject.text
			var vauleString = ''

			var idString = ''
			var vuauleId = ''

			for (var c = 0; c < dataIdArray.length; c++) {
				if (c === 0) {
					idString += dataIdArray[c].split("###")[1]
					vauleString += dataObject.choseData[c]
				} else {
					idString += "###" + dataIdArray[c].split("###")[1]
					vauleString += "###" + dataObject.choseData[c]
				}

			}

			headerTextId += '<input type="hidden" name="customSkuAttrList['+i+'].attr_id" value="'+ textIdString+'">'
			headerTextId += '<input type="hidden" name="customSkuAttrList['+i+'].custom_attr_value" value="'+ vauleString+'">'
			headerTextId += '<input type="hidden" name="customSkuAttrList['+i+'].attr_value_id" value="'+ idString+'">'

		}
		$('#hiddenSelectedInputID').html(headerTextId)

		var flag = 0;
		for (var j = 0; j < dataArray.length; j++) {
			var dataObject = dataArray[j];
			if (dataObject.choseData.length) {
				flag++;
			}
			if (flag === dataArray.length) {
				$("#tableShow").css('display', 'block');
				$("input[name='total_stock']").attr("readonly", "readonly");

			} else {
				$("#tableShow").css('display', 'none');
				$("input[name='total_stock']").removeAttr("readonly");
			}
		}

		// -----------------------------生成展示表格----------------------------
		dataIDarray = printResult(Zuhe(dataAllIdArray));
		//console.log("******",dataIDarray)
		var data = printResult(Zuhe(choseDataArray));
		var stres = '';
		for (var i = 0; i < data.length; i++) {
			var tdStr = '';
			var idName = ''
			for (var j = 0; j < data[i].length; j++) {

				if (j === 0) {
					idName += dataIDarray[i][j].split('###')[1];
				} else {
					idName += '#' + dataIDarray[i][j].split('###')[1];
				}

				var t = " <td>" + data[i][j] + "</td>";
				tdStr += t;
			}
			var showdataArray = showTable.show;
			var tdStr1 = ''
			for (var s = 0; s < showdataArray.length; s++) {
				var showObject = showdataArray[s];
				if (showObject.type == '0') {
                  
                        if (showObject.isChange == '1' && isGold == 1) {
                        	 var zhongLiang = 1;
	                        if (zhongIndex !== '') {
	                            zhongLiang = data[i][zhongIndex] // 获取重量
	                            zhongLiang = parseFloat(zhongLiang)
	                        }
	                        let shangpinPrice = zhongLiang*gold_price
	                        var t1 = "<td><input type='text' name='"
	                        + showObject.name
	                        + "' onblur='setsellPrice(this)' class='input' value='"+shangpinPrice.toFixed(2)+"' readonly='readonly'></td>";


                        }else{
                        	  var t1 = "<td><input type='text' name='"
                        + showObject.name
                        + "' onblur='setsellPrice(this)' class='input'></td>";
                        }
					
				} else {


                     // 手工费  == 1
                      var manualPrice = $("input[name='manual_fee']").val();
                        var zhongLiang = 1;
                        if (zhongIndex !== '') {
                            zhongLiang = data[i][zhongIndex] // 获取重量
                            zhongLiang = parseFloat(zhongLiang)
                        }
                        manualPrice = manualPrice*zhongLiang
                    if(showObject.isChange == '1'){
                        var manualPrice = $("input[name='manual_fee']").val();
                        var zhongLiang = 1;
                        if (zhongIndex !== '') {
                            zhongLiang = data[i][zhongIndex] // 获取重量
                            zhongLiang = parseFloat(zhongLiang)
                        }
                        manualPrice = manualPrice*zhongLiang
                        if(!manualPrice){
                            manualPrice = 0;
                        }
                        if(isManualFee == 0){
                            manualPrice = 0;
                            var t1 = "<td>"+manualPrice+"</td>";
                        }else{
                            var t1 = "<td><input type='text' name='"
                                + showObject.name
                                + "' onchange='setsellPrice(this)' class='input' value='"+manualPrice.toFixed(2)+"'></td>";
                        }

                    }else{
                    	if (isGold == 1) {
                    		let allPrice = gold_price*zhongLiang + manualPrice;
                            var t1 = "<td>"+allPrice.toFixed(2)+"</td>";
                    	}else{
                    		var t1 = "<td>0</td>";
                    	}
                        
					

					}



				}
				tdStr1 += t1;

			}
			var str = "<tr id='"+idName+"'>" + tdStr + tdStr1 + "</tr>";
			stres += str;
		}

		createTableHead();
		var createobj = $(stres);
		$(".tableBoty").html(createobj);
		for (var i = 0; i < choseDataArray.length; i++) {
			$("#tableAll").rowspan(i);
		}
		if (flag === dataArray.length) {

			flagCome++;
			if (flagCome === 2) {
				manualChange();
			}
			 huiDataFuntion()
		}
		 if (zhongIndex !== '') {
			chooseZhongLength = choseDataArray[zhongIndex].length;
			if (chooseZhongLength !== lastZhongLength) {
	             recoveryData();
			}else{
				readData();
			}
	        lastZhongLength = chooseZhongLength;
		 }else{
		 	recoveryData();
		 }
		
		

	}
	function huiDataFuntion() {
		var indexNum = 0
		$("#tableBody").find("tr").each(function() {
			//console.log('huiData',huiData)

			var tdArr = $(this).children();
			var id = $(this).attr("id");

			var dataArr = config.skuList;
			var j = dataArr.length;
			for (var i = 0; i < huiData.length; i++) {
				var thisObj = huiData[i]
				// if (indexNum === 0) {
				// 	console.log('ccc', thisObj.attr_id)
				// }
				if (id === thisObj.attr_id) {
					var sku_id = thisObj.sku_id.toString()
					var goods_id = thisObj.goods_id.toString()
					$(this).addClass(sku_id+'*'+goods_id)
				    if (flagCome == 2) {
				    	tdArr.eq(j).find('input').val(thisObj.price);
						if (isManualFee == 0) {
							tdArr.eq(j + 1).html(0);
						} else {
							tdArr.eq(j + 1).find('input').val(thisObj.manual_fee);
						}
						tdArr.eq(j + 2).html(thisObj.sale_price);
						tdArr.eq(j + 3).find('input').val(thisObj.stock);
				    }
				}
			}
			indexNum++;
		});
		 if (flagCome == 2) {
		   readData();
		}
	}

	function readData() {
		var dataArray = new Array();
		var newDataArray = new Array();
		var showClassNameArray = new Array()
		var idIndex = 0;
		var numDataInt = 0;
		var skuArray = new Array()
		var goodsArray = new Array()
		$("#tableBody").find("tr").each(function() {
			var tdArr = $(this).children();
			var id = $(this).attr("id");
			var className = $(this).attr("class");
			if(className){
				var trClass = className.split('*')
				skuArray.push(trClass[0])
				goodsArray.push(trClass[1])
			}else{
				skuArray.push(0)
				goodsArray.push(0)
			}

	
			var array = new Array();
			array.push(id);
			var dataArr = config.skuList;
			for (var i = 0; i < dataArr.length; i++) {
				array.push(tdArr.eq(i).html());
			}
			var j = dataArr.length
			var showdataArray = showTable.show;
			for (var i = 0; i < showdataArray.length; i++) {
				var dataObj = showdataArray[i]
				if (dataObj.type == '0') {
					array.push(tdArr.eq(j + i).find('input').val());
					if (i == showdataArray.length - 1) {
						var numData = tdArr.eq(j + i).find('input').val()
						if (numData) {
							numDataInt += parseInt(numData)
						}
					}
				} else {
					
					if (dataObj.isChange == 1 && isManualFee == 1) {
                        array.push(tdArr.eq(j + i).find('input').val());
					}else{
						array.push(tdArr.eq(j + i).html());
					}

				}
			}

			// array.push(tdArr.eq(j+2).html());
			// array.push(tdArr.eq(j+3).find('input').val());

			dataArray.push(array);
			var newArray = array.slice(0);
			newArray.shift();
			newDataArray.push(newArray)
			// -----------------------########################--------------------------------------------
			var showClassName = ''

			for (var k = 0; k < newArray.length; k++) {
				if (k === 0) {
					showClassName += newArray[k]
				} else {
					var s = newArray[k];
					if (s) {
						showClassName += '###' + newArray[k]
					} else {
						showClassName += '###0'
					}
				}
			}

			// var dataIDString = ''
			// for (var i = 0; i < dataIDarray[idIndex].length; i++) {
			// 	dataIDString += dataIDarray[idIndex][i]+"###"
			// }
			// console.log(dataIDString)
			// showClassName = dataIDString+showClassName
			// idIndex++;
			showClassNameArray.push(showClassName);

		});

		$("input[name='total_stock']").val(numDataInt);
		// console.log('numDataInt',numDataInt)

		var hiddenInputStr = ''
		for (var i = 0; i < showClassNameArray.length; i++) {
			hiddenInputStr += '<input type="hidden" name="customSkuList['+i+'].attr_value" value="'+ showClassNameArray[i]+'">';

			var idArr = dataIDarray[i];
			var idString = ''
			var vuauleId = ''

			for (var c = 0; c < idArr.length; c++) {
				if (c === 0) {
					idString += idArr[c].split("###")[0]
					vuauleId += idArr[c].split("###")[1]
				} else {
					idString += "###" + idArr[c].split("###")[0]
					vuauleId += "###" + idArr[c].split("###")[1]
				}

			}

			
             
			hiddenInputStr += '<input type="hidden" name="customSkuList['+i+'].attr_value_id" value="'+ vuauleId+'">'
			hiddenInputStr += '<input type="hidden" name="customSkuList['+i+'].sku_id" value="'+ skuArray[i]+'">'
			hiddenInputStr += '<input type="hidden" name="customSkuList['+i+'].goods_id" value="'+ goodsArray[i]+'">'
			// hiddenInputStr += '<input type="hidden" name="customSkuList['+i+'].attr_id" value="'+ idString+'">'

			 //hiddenInputStr += '<input type="hidden" name="customSkuList['+i+'].attr_value_name" value="'+ headerText+'">'
		}

		var flag0 = 0;
		var dataArray1 = config.skuList;
		for (var j = 0; j < dataArray1.length; j++) {
			var dataObject = dataArray1[j];
			if (dataObject.choseData.length) {
				flag0++;
			}
			if (flag0 === dataArray1.length) {

				$('#hiddenInputID').html(hiddenInputStr);
			}
		}

		var str = JSON.stringify(dataArray);
		localStorage.setItem("saveShowData", str);

        var dataArr = config.skuList;
        var j = dataArr.length;
        sortPriceArray = new Array();
        sortCountArray = new Array();
        for (var dd = 0;dd<newDataArray.length;dd++){
            var priceStr = newDataArray[dd][j+2];
            var countStr = newDataArray[dd][j+3];
            sortPriceArray.push(parseFloat(priceStr));
            if(!countStr){
            	countStr = 0;
            }
            sortCountArray.push(parseFloat(countStr));
		}
        sortPriceArray.sort(function(a,b){
             return a-b});
       
		return newDataArray;
	}
	
	function setInit() {

		var handPrice = $('#handPrice').val();
		var sellPrice = $('#sellPrice').val();

		var count = $('#count').val();
		var price = $('#price').val();
		
		/*if (isGold == 0) {
			if(price == ''){
				alertTip("一键设置价格不能为空！");
				return false;
		   }
		}*/
		if(count == '' && price == ''){
			alertTip("一键设置数据不能为空！");
			return false;
		}
		
		if (isNaN(count)) {
			alertTip("数量请输入非负整数");
			$('#count').val(1);
			return false;
		}else{
			if(count>1000){
				alertTip("请输入1-1000之间的数");
				$('#count').val(1);
				return false;
			}
		}
		
		if (isGold == 0) {
			if (isNaN(price)) {
			alertTip("商品价格请输入非负数");
			$('#price').val(1);
			return false;
		} else {
			if (price < 0) {
				alertTip("商品价格请输入非负数");
				$('#price').val(1);
				return false;
			}
			if(price>999999){
				alertTip("请输入1-999999之间的数");
				$('#price').val(1);
				return false;
			}
		}
		}
		var manual = $("input[name='manual_fee']").val();
		if (!manual) {
			manual_fee = manual;
		}
		// $("input[name='total_stock']")
		$("#tableBody").find("tr").each(
				function() {
					var tdArr = $(this).children();
					var dataArr = config.skuList;
					for (var i = 0; i < dataArr.length; i++) {
						tdArr.eq(i).html();
					}
					var j = dataArr.length
					if(isGold == 0 && price != ''){
						tdArr.eq(j).find('input').val(price);  // 设置价格
					}

					if (isManualFee === '0') {
						tdArr.eq(j + 1).html('0');
						if (price) {
							tdArr.eq(j + 2).html(parseFloat(price).toFixed(2));
						}
					} else {
						var g = tdArr.eq(j + 1).find('input').val(); // 获取手工费
						if (g) {
							manual_fee = g;
						}
						var zhongLiang = 1;
						if (zhongIndex !== '') {
							zhongLiang = tdArr.eq(zhongIndex).html() 
						    zhongLiang = parseFloat(zhongLiang)
						
						}
						// var da = parseFloat(price) + (parseFloat(manual_fee))* (zhongLiang)
						var da = parseFloat(price) + (parseFloat(manual_fee))
						if (da) {
							tdArr.eq(j + 2).html(da.toFixed(2)); // 设置售价
						}

					}
					if(count != ''){
						tdArr.eq(j + 3).find('input').val(count); // 设置数量
					}

					//              var showdataArray = showTable.show;
					//  for (var s = 0; s < showdataArray.length; s++) {
					// 	var dataObj = showdataArray[s]
					// 	if (dataObj.type == '0') {

					// 	}else{

					// 	}
					// }	        

					readData();
				});
	}
	function manualChange() {

		var manualPrice = $("input[name='manual_fee']").val();
		// var zhongLiang = 1;
		// if (zhongIndex) {
		//     zhongLiang = 
		// }
		if (isNaN(manualPrice)) {
			alertTip("请输入正确的手工费");
			$("input[name='manual_fee']").val(manual_fee);
			return false;
		} else {
			if (manualPrice < 0) {
				alertTip("请输入正确的手工费");
				$("input[name='manual_fee']").val(manual_fee);
				return false;
			}
			if(manualPrice>10000){
				alertTip("手工费不能大于10000");
				$("input[name='manual_fee']").val(manual_fee);
				return false;
			}
		}

		$("#tableBody").find("tr").each(
				function() {
					var tdArr = $(this).children();
					var dataArr = config.skuList;

					var j = dataArr.length

					if (isManualFee == '0') {
						tdArr.eq(j + 1).html('0');
					} else {
						var p = tdArr.eq(j).find('input').val(); //获取商品价格
						var zhongLiang = 1;
						if (zhongIndex !== '') {
							zhongLiang = tdArr.eq(zhongIndex).html() // 获取重量
							zhongLiang = parseFloat(zhongLiang)
						}
                        var manual = parseFloat(manualPrice)* zhongLiang  // 手工费 * 重量
						tdArr.eq(j + 1).find('input').val(manual.toFixed(2)); // 手工费设置
                      
						// var da = parseFloat(p) + (parseFloat(manualPrice))* (zhongLiang)
						var da = parseFloat(p) + manual
						if (da) {
							tdArr.eq(j + 2).html(da.toFixed(2));  // 售价设置
						}

					}

					readData();
				});
	}
	function recoveryData() {
		var isFromCreat = arguments[0] ? arguments[0] : 11;
		if (isFromCreat==2) {
           return
		}
		$("#tableBody")
				.find("tr")
				.each(
						function() {

							var tdArr = $(this).children();
							var id = $(this).attr("id");
							var array = new Array();
							var dataArr = config.skuList;
							for (var i = 0; i < dataArr.length; i++) {
								array.push(tdArr.eq(i).html());
							}
							var j = dataArr.length
							var optionss = localStorage.getItem("saveShowData");

							if (optionss) {
								var arr = JSON.parse(optionss)

								for (var i = 0; i < arr.length; i++) {

									if (arr[i][0] === id) {
										var showdataArray = showTable.show;
										for (var s = 0; s < showdataArray.length; s++) {
											var dataObj = showdataArray[s]
											if (dataObj.type == '0') { // 可设置
												tdArr.eq(j + s).find('input').val(arr[i][j + s + 1]);
											} else {
												if (isManualFee === '1' && dataObj.isChange == '1') { // 手工费
													tdArr.eq(j + s).find('input').val(arr[i][j + s + 1]);											
												}else{
													if (dataObj.isChange == '1') {
                                                         tdArr.eq(j + s).html(0);
													}else{
														 tdArr.eq(j + s).html(arr[i][j + s + 1]);
													}
                                                 
												}
												
											}
										}
									}
								}
							}

						});
		readData();
	}
	function setsellPrice(obj) {

        
		if (isNaN($(obj).val())) {
			alertTip("数量请输入非负整数");
			recoveryData();
			return false;
		}
		// manualPrice = $(obj).parent().next().html()
		// if (manualPrice) {
		// 	manual_fee = manualPrice
		// }else{
		//     manualPrice = $("input[name='manual_fee']").val();
		// }
		// manualPrice = $("input[name='manual_fee']").val();
		var inputName = $(obj).attr("name");
		// ----------------------商品价格-----------------------------------------------
		if (inputName === 'price') {
			if($(obj).val() == ''){
            alertTip("商品价格不能为空");
            recoveryData();
			return false;
           }

			if ($(obj).val() < 1 || $(obj).val() >999999) {
				alertTip("请输入1-999999之间的数");
				recoveryData();
				return false;
			}
			if (isManualFee === '0') {
				var da = $(obj).val()  // 商品价格
				$(obj).parent().next().html('0') // 设置手工费
				$(obj).parent().next().next().html(parseFloat(da).toFixed(2)) //设置售价
			} else {
				var tdArr = $(obj).parent().parent().children();
				var zhongLiang = 1;
				if (zhongIndex !== '') {
					zhongLiang = tdArr.eq(zhongIndex).html() // 获取重量
					 zhongLiang = parseFloat(zhongLiang)
				}
                manualPrice = $("input[name='manual_fee']").val();
                var manual = parseFloat($(obj).parent().next().find('input').val())  // 手工费 * 重量
				var da = parseFloat($(obj).val()) + parseFloat(manual)
				 
				//$(obj).parent().next().find('input').val(manual)  // 设置手工费
				$(obj).parent().next().next().html(da.toFixed(2)) //设置售价
			}
		}
		// ---------------------------数量------------------------------------------
		if (inputName === 'count') {
			if($(obj).val() == ''){
             alertTip("商品数量不能为空");
             recoveryData();
			 return false;
           }
			if ($(obj).val() < 1 || $(obj).val() >10000) {
				alertTip("请输入1-10000之间的数");
				recoveryData();
				 return false;
			}
		}
		// ---------------------------手工费------------------------------------
		if (inputName === 'handPrice') {
			if ($(obj).val() < 1 || $(obj).val() >10000) {
				alertTip("请输入1-10000之间的数");
				recoveryData();
				 return false;
			}
			if (isManualFee === '0') {
				var da = $(obj).val('0')
				$(obj).parent().next().html(
						parseFloat($(obj).parent().prev().find('input').val()))
			} else {
				var tdArr = $(obj).parent().parent().children();
				var zhongLiang = 1;
				if (zhongIndex !== '') {
					zhongLiang = tdArr.eq(zhongIndex).html()
					 zhongLiang = parseFloat(zhongLiang)
				}
				// var da = parseFloat($(obj).val())
				// 		* (zhongLiang)
				// 		+ parseFloat($(obj).parent().prev().find('input').val())

				var da = parseFloat($(obj).val())+ parseFloat($(obj).parent().prev().find('input').val())

				if (da) {
					$(obj).parent().next().html(da.toFixed(2))  // 设置售价
				}
			}
		}

		readData();
	}
	jQuery.fn.rowspan = function(colIdx) {
		return this
				.each(function() {
					var that;
					$('tr', this)
							.each(
									function(row) {
										$('td:eq(' + colIdx + ')', this)
												.filter(':visible')
												.each(
														function(col) {
															if (that != null
																	&& $(this)
																			.html() == $(
																			that)
																			.html()) {
																rowspan = $(
																		that)
																		.attr(
																				"rowSpan");
																if (rowspan == undefined) {
																	$(that)
																			.attr(
																					"rowSpan",
																					1);
																	rowspan = $(
																			that)
																			.attr(
																					"rowSpan");
																}
																rowspan = Number(rowspan) + 1;
																$(that)
																		.attr(
																				"rowSpan",
																				rowspan);
																$(this).hide();
															} else {
																that = this;
															}
														});
									});
				});
	}
	//接受可变长数组参数
	function Zuhe(arr) {
		arguments = arr;
		var heads = arguments[0];
		for (var i = 1, len = arguments.length; i < len; i++) {
			if (arguments[i].length) {
				heads = addNewType(heads, arguments[i]);
			}
		}
		return heads;
	};

	function addNewType(heads, choices) {
		var result = [];
		for (var i = 0, len = heads.length; i < len; i++) {
			for (var j = 0, lenj = choices.length; j < lenj; j++) {

				result.push(heads[i] + '_' + choices[j]);
			}
		}
		return result;
	};

	//打印结果的函数
	function printResult(result) {
		var lastResult = new Array()
		var heheA = new Array()
		for (var i = 0, len = result.length; i < len; i++) {
			lastResult.push(result[i].split("_"));
		}
		return lastResult;
	}

	function uuid() {
		var num=""; 
		for(var i=0;i<10;i++) { 
			num += Math.floor(Math.random()*10); 
		} 
		return 'uuid+' + num;
	}
	function sortNumber(id) {
		var re = id.split('#').sort()
		var reStr = ''
		for (var i = 0; i < re.length; i++) {
			if (i === 0) {
				reStr += re[i]
			} else {
				reStr += '#' + re[i]
			}
		}

		return reStr
	}
</script>