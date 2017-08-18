// 创建属性展示面板以及属性可选面板
function initAttrPanel (options) {
	options = options || {};
    this.opts = $.extend({}, this.demoOption, options);
        
    if (options.attrUrl) {
    	this.opts.attrOption.url = options.attrUrl;
    	// 获取所有的初始属性并归类
	    this._initAttrHandle(this.opts.attrOption);
    } else if (this.opts.attrOption.url) {
    	// 获取所有的初始属性并归类
	    this._initAttrHandle(this.opts.attrOption);
    }
    if (options.onlyRead) {
    	if (options.el) {
    		if (this.mustList.length) {
    			this._onlyReadAttrPanel(this.mustList, this.opts.el, this.opts.appendType);
    		}
    		if (this.otherList.length) {
		        // 可选属性初始渲染需要根据自由属性是否存在
		        this._onlyReadAttrPanel(this.otherList,  this.opts.el, this.opts.appendType, 1);
    		}
	    }
    } else {
    	if (options.el) {
    		if (this.mustList.length) {
    			this._showAttrPanel(this.mustList, this.opts.el, this.opts.appendType);
    		}
    		if (this.otherList.length) {
		        // 非必選屬性初始渲染
		        this._showAttrPanel(this.otherList,  this.opts.el, this.opts.appendType, 1);
    		}
	    }
    }
}

// 生成随机UUID
function randomId() {
	var num=""; 
	for(var i=0;i<10;i++) { 
		num += Math.floor(Math.random()*10); 
	} 
    return num;
}

var propsData = {
    "attrVoList": [{
        "attr_id": "54",
        "data": [{
            "attr_id": "54",
            "attr_name": "",
            "attr_value": "吊坠",
            "attr_value_ico": "",
            "attr_value_id": "54",
            "sort_no": "0",
            "state": "100"  // 100表示选中
        }],
        "is_alisa": "1",
        "is_custom_value": "1", // 是否可编辑，是否可新增
        "is_index": "0",
        "is_must": "0",
        "show_type": "1",   // 0文字，1图片
        "text": "款式",
        "type": "1",        // 展示类型
        "unit": ""
    }]
}


// 内部示例配置
initAttrPanel.prototype.demoOption = {
	// 必选属性展示定位容器
	el: '', // $('#attrPanelPosition'),
	// 相对定位容器的操作方法
	appendType: 'before',
	checkOptionAttr: [],
	// 是否只读，只读只显示内容(不带操作功能)
	onlyRead: false,
	onlyReadSpace: ' ',  // ' | ',
	alreadyShowAttrNum: 0,
    attrOption: {
        // 自由属性请求地址
        url: '', // '/admin/wineshop/hotelAttrList',
        // 列表数据
        data: {
        	mustList: [],
            otherList: []
        },
        // 传入的数据
        props: {},
        // 主键
        primaryKey: 'is_must'
        // 请求参数
        // params: {},
        // 请求方式
        // type: 'get',
        // 返回类型
        // dataType: 'josn',
        // 返回数据的列表key
        // responseKey: 'list',
        
        // 成功方法
        // okFn: function (data) {
        // return data;
        // },
        // 失败方法
        // errFn: function (err) {
        // return err;
        // }
    },
    customOption: {
        // 列表数据
        data: '',
        // 传入的数据
        props: {},
        // 主键
        primaryKey: 'brand_id',
        // 字典，方便索引
        dictionary: {}
    }
}


// ==================================请求数据处理开始================================================
// 初始属性处理
initAttrPanel.prototype._initAttrHandle = function (opts) {
    // 开发使用
    // opts.props = propsData.attrVoList;
    // // 对从外部传入的数据进行归类处理
    // this.opts.attrOption.data = this._typeHandle(opts.props, opts.primaryKey
	// || 'id');
    // // 把归类后的数据合并到闭包作用域this的根目录下
    // $.extend(true, this, this.opts.attrOption.data);



    if (opts) {
        if (opts.url) {
        	// 外部传入url则通过url获取所有的初始属性
            this._getInitAttr(opts.url);
        } else if (opts.props && opts.props.length) {
        	// 对从外部传入的数据进行归类处理
            this.opts.attrOption.data = this._typeHandle(opts.props, opts.primaryKey || 'id');
            // 把归类后的数据合并到闭包作用域this的根目录下
            $.extend(true, this, this.opts.attrOption.data);
        }
    }
}
// 对返回的全部属性进行归类处理
initAttrPanel.prototype._typeHandle = function (list, key) {
    var data = {
        mustList: [],
        otherList: []
    };
    $.each(list, function (index, item) {
        if (item[key] === '1') {
            data.mustList.push(item);
        } else {
        	// 此逻辑判断没有属性项则不插入数组列表
//        	if (item.data && item.data.length) {
        		data.otherList.push(item);
//        	}
        }
    })
    return data;
}
// 请求初始属性
initAttrPanel.prototype._getInitAttr = function (url) {
    var self = this;
    $.ajax({
        url: url,
        cache: true,
        async: false,
        success: function (res) {
        	// 对获取到的数据进行归类处理
            self.opts.attrOption.data = self._typeHandle(res.attrVoList, self.opts.attrOption.primaryKey || 'is_must');
            // 把归类后的数据合并到闭包作用域this的根目录下
            $.extend(true, self, self.opts.attrOption.data);
        },
        error: function (err) {
          /* console.log(err); */
            return false;
        }
    })
}
// ==================================请求数据处理结束================================================


// ==================================展示面板模块处理开始================================================
// 展示面板下拉列表选择文本赋值
initAttrPanel.prototype._selText = function (event) {
    var value = $(this).val();
    var text = $(this).find("option:selected").text();
    $(this).parent("td").find(".slef_para_value").val(text);
}
// 特殊选框赋值
initAttrPanel.prototype._specialInputCheckText = function (event) {
    // 针对多填框的选中取消进行特殊处理
    if ($(this).parents('td').hasClass('multiFillBlock')) {
        if (this.checked) {
            var createNewInput = $(this).parent()[0].outerHTML;
            $(this).parent().after(createNewInput);
            $(this).parent().parent().find('div:eq(-1) input').val('uuid+' + randomId());
            // $(this).parent().parent().find('div:eq(-1)
			// input[type=text]').val('');
        } else {
            // 因为选中则新增一条，所以不会出现取消时不存在复选框的情况
            $(this).parent().remove();
        }
    }

    event.data.self._specialInputSetSubValue($(this).parents('td'));
    
    event.data.self._specialInputSetInputCss($(this).parents('td'));
}
// 特殊选框value文本框的失焦事件
initAttrPanel.prototype._specialValueInputBlur = function (event) {
    if (!this.value) {
        this.value = $(this).data('default')
    }

    // 针对多选框的文本框进行特殊处理(此处的value设置由uuid方法生成)
    // if ($(this).parent().hasClass('multiFillBlock')) {
    // // 复选框不存在value时将文本框的内容赋值给复选框
    // if (!$(this).prev().val()) {
    // $(this).prev().val(this.value);
    // }
    // }

    if ($(this).prev()[0].checked) {
    	event.data.self._specialInputSetSubValue($(this).parents('td'));
    } else {
        // 未选中的值
    }    
}
// 特殊选框重新生成提交数据 $el:特殊框完整td的容器
initAttrPanel.prototype._specialInputSetSubValue = function ($el) {
	var checkInputs = $el.find('input:checked');
    var texts = [], values = [];
    $.each(checkInputs, function (index, item) {
        values.push($(item).val());
        if ($(item).next().is('input')) {
            texts.push($(item).next().val());
        } else if ($(item).next().is('span')) {
        	texts.push($(item).next().html());
        } else {
            texts.push($(item).parent().text());
        }
    })
    $el.find('.slef_para_id').val(values.join('###'));
    $el.find('.slef_para_value').val(texts.join('###'));
}
// 特殊选框重新生成样式 $el:特殊框完整td的容器
initAttrPanel.prototype._specialInputSetInputCss = function ($el) {
	// 选中的样式处理
    var inputs = $el.find('.inputSpecialBlock input[type!=text]');
    $.each(inputs, function (index, item) {
    	if (item.checked) {
    		$(item).parent().removeClass('uncheck').addClass('checked');
    	} else {
    		$(item).parent().removeClass('checked').addClass('uncheck');
    	}
    })
}
// 特殊选框span标签点击添加新属性值事件
initAttrPanel.prototype._specialInputAddNewInput = function (event) {
    // 获取添加按钮前一个项的html代码并插入在按钮之前
    var createNewInputStr = $(this).prev()[0].outerHTML;
    $(this).before(createNewInputStr);
    $(this).prev().removeClass('checked').addClass('uncheck multiFillBlock').removeAttr('title').find('input');
    // 设置新增复选框的value值
    $(this).prev().find('input:eq(0)').val('uuid+' + randomId());
    // 删除新增项底下的span标签，置空默认值，清除隐藏类名，设置值为空字符串
    $(this).prev().find('span').remove();
    $(this).prev().find('input[type=text]').attr('data-default', '').removeClass('hide').val('');
    // 字符串中选中icon则不新增删除icon
    if (createNewInputStr.indexOf('<icon class="removeInput">X</icon>') < 0) {
    	$(this).prev().find('input[type=text]').after('<icon class="removeInput">X</icon>');
    }
    return false;
}
// 特殊选框icon标签点击删除新属性值事件
initAttrPanel.prototype._specialInputRemoveInput = function (event) {
	var $parentTd = $(this).parents('td');
	$(this).parent().remove();
	if ($(this).parent().find('input')[0].checked) {
		event.data.self._specialInputSetSubValue($parentTd);
	}
}
// 特殊选框span标签双击事件
initAttrPanel.prototype._specialInputTextDblClick = function (event) {
    $(this).next('input').removeClass('hide');
    $(this).remove();
}
// 对象转换成字符串
initAttrPanel.prototype._objToStr = function (obj) {
    var attrStr = '';
    $.each(obj, function (key, value) {
    attrStr += ' ' + key + '="';
    if ($.isArray(value)) {
    	attrStr += value.join(' ');
    } else {
    	attrStr += value;
    }
    	attrStr += '"';
    })
    return attrStr;
}
// 下拉列表字符串处理
initAttrPanel.prototype._getSelectHtml = function (list, domAttrObj, value) {
	var self = this;
    var html = '', text = '', domAttrObj = domAttrObj || {};
    domAttrObj.type = 'select';
    domAttrObj.isrequired = domAttrObj.is_must ? 'yes' : 'no';
    html += '<select ' + self._objToStr(domAttrObj) + '>';
    html += '<option value="">请选择</option>';
    $.each(list, function (index, item) {
		var selectedStatus = '';
		if (item.state == '100') {
			selectedStatus = 'selected';
			text.push(item.attr_value);
		}
    	html += '<option value="' + item.attr_value_id + '" ' + selectedStatus + ' >' + item.attr_value + '</option>';
    })
    html += '</select>';
    return {
		htmlStr: html,
		text: text
	};
}
// 单、复选框字符串处理
initAttrPanel.prototype._getSpecialInputHtml = function (list, type, domAttrObj, isAlisa, isAdd) {
    var self = this;
    var html = '', text = [], value = [];
    html += '<div id="div_' + list[0].attr_id + '" tipmsg="' + domAttrObj.tipmsg + '" style="display: inline-block">';
  $.each(list, function(index, item) {
    var isValidate='';
    if(index == 0){
    	domAttrObj.class.push('validate');
    	domAttrObj.isrequired = domAttrObj.is_must ? 'yes' : 'no';
    	domAttrObj.changeTip = 'div_' + item.attr_id;
    } else {
    	if (domAttrObj.class.length > 2) {
    		domAttrObj.class.slice(0,2);
    		delete domAttrObj.isrequired;
    		delete domAttrObj.changetip;
    	}
    }
    domAttrObj.value = item.attr_value_id;
    domAttrObj.type = type;
    domAttrObj.name = item.attr_id;
    var checkStatus = '';
	
    if (item.state === '100') {
		checkStatus = true;
		text.push(item.attr_value);
		value.push(item.attr_value_id);
    }
    if (isAlisa === 'multiFillBlock') {
        html += '<div title="' + item.attr_value + '" class="checked multiFillBlock inputSpecialBlock"><input '+ self._objToStr(domAttrObj) + ' checked /><input type="text" value="' + item.attr_value + '" data-default="' + item.attr_value + '" /></div>';
    } else if (isAlisa === '1') {
        html += '<div title="' + item.attr_value + '" class="' + (checkStatus ? 'checked' : 'uncheck') + ' inputSpecialBlock"><input '+ self._objToStr(domAttrObj) + (checkStatus ? ' checked' : '') + ' /><span>' + item.attr_value + '</span><input type="text" value="' + item.attr_value + '" class="hide" data-default="' + item.attr_value + '" /></div>';
    } else {
        html += '<label title="' + item.attr_value + '" class="' + (checkStatus ? 'checked' : 'uncheck') + ' inputSpecialBlock"><input '+ self._objToStr(domAttrObj) + (checkStatus ? ' checked' : '') + ' />' + item.attr_value + '</label>';
    }
  })
  if (isAlisa === 'multiFillBlock') {
    domAttrObj.value = '';
    html += '<div class="uncheck multiFillBlock inputSpecialBlock"><input '+ self._objToStr(domAttrObj) + ' /><input type="text" value="" data-default=""  /></div>';
  }
  if (isAdd === '1') {
    html += '<span class="addNewInput">+</span>';
  }
  html += '</div>';
  return {
	htmlStr: html,
	text: text,
	value: value
  };
}
// 时间控件与日期控件
initAttrPanel.prototype._getTimeDatePicker = function (list, type, domAttrObj) {
	var self = this;
  var html = '', domAttrObj = domAttrObj || {};
  domAttrObj.type = 'text';
  if ($.isarray(list)) {
    // 数组类型配置参数
    $.each(list, function(index, item) {
    	domAttrObj.id = domAttrObj.name = item.attr_id;
      if (index > 1) {
        // 多个控件同行
        html += ' - ';
      }
      html += '<input ' + self._objToStr(domAttrObj) + '>';
    })
  } else {
    // 对象类型配置参数
    html += '<input ' + self._objToStr(domAttrObj) + '>';
  }
  return html;
}
// 只读方式渲染
initAttrPanel.prototype._onlyReadAttrPanel = function (attrList, el, appendType, attrType) {
	var html = '',
	self = this,
	randomNum = self.randomNum = self.randomNum ? self.randomNum : Math.floor(Math.random() * 1000),
	appendType = appendType || 'html',
	className = ['jewelry_must_attr_panel', 'only_read_panel', 'jewelry_must_attr_panel_' + randomNum];
	$.each(attrList, function (index, item) {
    	if (!item.data || !item.data.length) {
    		return;
    	}
        var curText = [];
        item.data.map(function (item, index) {
            if (item.state === '100') {
                curText.push(item.text);
            }
        })
    	// 左侧部分
    	html += '<tr class="' + className.join(' ') + '" data-id="' + item.attr_id + '" data-type="' + (attrType ? 'option' : 'must') + '"><td class="td_left"> ' + item.text + ':</td>';
    	// 右侧部分
    	html += '<td class="td_right_two">';
    	
    	switch (Number(item.type)) {
	        case 0:
	            // 输入框的文本显示
	            html += '<span class="only_read_span">' + curText[0] + '</span>';
	            break;
	        case 1:
	            // 下拉列表
	        	html += '<span class="only_read_span">' + curText[0] + '</span>';
	            break;
	        case 2:
	            // 复选框
	        	var checkedText = curText;
	        	// 新版只读模式
	        	var spanStrArr = []
	        	checkedText.map(function (item, index) {
	        		spanStrArr.push('<span class="only_read_span" title="' + item + '">' + item + '</span>');
	        	})
	        	html += spanStrArr.join(self.opts.onlyReadSpace);
	        	// 旧只读模式
	        	// html += '<span>' + checkedText.join(self.opts.onlyReadSpace)
				// + '</span>';
	            break;
	        case 3:
	            // 单选框
	        	var checkedText = curText;
	        	// 新版只读模式
	        	var spanStrArr = []
	        	checkedText.map(function (item, index) {
	        		spanStrArr.push('<span class="only_read_span" title="' + item + '">' + item + '</span>');
	        	})
	        	html += spanStrArr.join(self.opts.onlyReadSpace);
	        	// 旧只读模式
	        	// html += '<span>' + checkedText.join(self.opts.onlyReadSpace)
				// + '</span>';
	            break;
	        // case 4:
	        // // 文本域
	        // html += '<span class="only_read_span">' + (custom && custom.text
			// ? custom.text : '') + '</span>';
	        // break;
            case 5:
                // 复选框
                var checkedText = curText;
                // 新版只读模式
                var spanStrArr = []
                checkedText.map(function (item, index) {
                    spanStrArr.push('<span class="only_read_span" title="' + item + '">' + item + '</span>');
                })
                html += spanStrArr.join(self.opts.onlyReadSpace);
                // 旧只读模式
                // html += '<span>' + checkedText.join(self.opts.onlyReadSpace)
				// + '</span>';
                break;
//	        case 6:
//	            // 下拉时间控件
//	        	var timeText = custom && custom.text ? custom.text : '';
//	        	html += '<span class="only_read_span">' + timeText.join('-') + '</span>';
//	            break;
//	        case 7:
//	            // 日期控件
//	        	var dateText = custom && custom.text ? custom.text : '';
//	        	html += '<span class="only_read_span">' + dateText.join('   --   ') + '</span>';
//	            break;
//	        case 8:
//	            // 三级联动
//	        	var addrText = custom && custom.text ? custom.text : '';
//	        	html += '<span class="only_read_span">' + addrText.join(' -- ') + '</span>';
//	            break;
//	        case 9:
//	            // 文件上传控件
//	            break;
	    }
    	html += '</td></tr>';
    })
    // html字符串渲染成Dom
    if (appendType != 'before' && appendType != 'after') {
    	html = '<table><tbody>' + html + '</tbody></table>';
    }
    if (attrType && appendType == 'html') {
    	appendType = 'append';
    }
    el[appendType](html);
}

// 渲染必选属性
// attrList执行的属性列表，el定位的容器，appendType填充方式，attrType有则为可选属性
initAttrPanel.prototype._showAttrPanel = function (attrList, el, appendType, attrType) {
    var html = '',
    	self = this,
    	randomNum = self.randomNum = self.randomNum ? self.randomNum : Math.floor(Math.random() * 1000),
		appendType = appendType || 'html',
		className = ['jewelry_attr_panel', !attrType ? 'jewelry_must_attr_panel' : 'jewelry_other_attr_panel' , 'jewelry_attr_panel_' + randomNum];
    $.each(attrList, function (index, item) {
    	if (!item.data || !item.data.length) {
    		item.data = [{
    			attr_id: item.attr_id,
    			attr_value_id: 'uuid+' + randomId(),
    			attr_value: ''
    		}];
    	}
		
    	var domAttrObj = {};
    	if (!attrType) {
    		domAttrObj.is_must = 1;
    	} 
    	
    	// 左侧部分
    	html += '<tr class="' + className.join(' ') + '" data-id="' + item.attr_id + '" data-type="' + (attrType ? 'option' : 'must') + '"><td class="td_left ' + (item.type == 5 ? 'multiFillBlock' : '') + '"> ' + item.text + (domAttrObj.is_must ? '<span class="must_span">*</span>' : ':') + '</td>';
    	// 右侧部分
    	html += '<td class="td_right_two ' + (item.type == 5 ? 'multiFillBlock' : '') + '">';
    	domAttrObj.class = ['validate'];
    	
    	switch (Number(item.type)) {
	        case 0:
	            // 输入框
	            domAttrObj.type = 'text';
	            domAttrObj.isrequired = domAttrObj.is_must ? 'yes' : 'no';
	            domAttrObj.id = item.attr_id;
	            domAttrObj.tipmsg = '请输入' + item.text;
	            domAttrObj.name = 'customAttrList[' + self.opts.alreadyShowAttrNum + '].custom_attr_value';
	            $.each(item.data, function(index_1, item_1) {
	            	if (item_1.state == 100) {
	            		domAttrObj.value = item_1.attr_value;
	            	}
	            })
            	domAttrObj.value = domAttrObj.value || '';
            	
	            html += '<input ' + self._objToStr(domAttrObj) + '>';
	
	            html += '<input type="hidden" name="customAttrList[' + self.opts.alreadyShowAttrNum + '].attr_id" value="' + item.attr_id + '###' + item.text + '"/>';
	            html += '<input type="hidden" name="customAttrList[' + self.opts.alreadyShowAttrNum + '].attr_value_id" value="' + 'uuid+' + randomId() + '"/>';
	            html += '<input type="hidden" name="customAttrList[' + self.opts.alreadyShowAttrNum + '].attr_type" value="' + item.show_type + '"/>';
	            break;
	        case 3:
	            // 下拉列表
	            domAttrObj.class.push('selectpicker');
	            domAttrObj.id = 'sel_' + item.attr_id;
	            domAttrObj.changetip = 'sel_' + item.attr_id;
	            if (item.maxlength) {
	            	domAttrObj.maxlength = item.maxlength;
	                domAttrObj.maxdatalength = item.maxlength;
	            }
	            if (item.placeholder) {
	            	domAttrObj.placeholder = item.placeholder;
	            }
	            domAttrObj.name = 'customAttrList[' + self.opts.alreadyShowAttrNum + '].attr_value_id';
	            domAttrObj.tipmsg =  item.text ;
				var selectHtmlObj = self._getSelectHtml(item.data, domAttrObj);
	            html += selectHtmlObj.htmlStr;
	            
	            html += '<input type="hidden" name="customAttrList[' + self.opts.alreadyShowAttrNum + '].attr_id" value="' + item.attr_id + '###' + item.text + '"/>';
	            html += '<input type="hidden" class="slef_para_value" name="customAttrList[' + self.opts.alreadyShowAttrNum + '].custom_attr_value" value="' +  selectHtmlObj.text + '"/>';
	            html += '<input type="hidden" name="customAttrList[' + self.opts.alreadyShowAttrNum + '].attr_type" value="' + item.show_type + '"/>';
	            break;
	        case 2:
	            // 复选框
	            domAttrObj.class.push('checkboxpicker');
	            domAttrObj.tipmsg =  item.text ;
	            domAttrObj.name = 'customAttrList[' + self.opts.alreadyShowAttrNum + '].attr_value_id';
				var specialHtmlObj = self._getSpecialInputHtml(item.data, 'checkbox', domAttrObj, item.is_custom_value, item.is_custom_value);
	            html += specialHtmlObj.htmlStr;
	
	            html += '<input type="hidden" name="customAttrList[' + self.opts.alreadyShowAttrNum + '].attr_id" value="' + item.attr_id + '###' + item.text + '"/>';
	            html += '<input type="hidden" class="slef_para_id" name="customAttrList[' + self.opts.alreadyShowAttrNum + '].attr_value_id" value="' +  (specialHtmlObj.value && specialHtmlObj.value.length ? specialHtmlObj.value.join('###') : '') + '"/>';
	            html += '<input type="hidden" class="slef_para_value" name="customAttrList[' + self.opts.alreadyShowAttrNum + '].custom_attr_value" value="' +  (specialHtmlObj.text && specialHtmlObj.text.length ? specialHtmlObj.text.join('###') : '') + '"/>';
	            html += '<input type="hidden" name="customAttrList[' + self.opts.alreadyShowAttrNum + '].attr_type" value="' + item.show_type + '"/>';
	            break;
	        case 1:
	            // 单选框
	            domAttrObj.class.push('radiopicker');
	            domAttrObj.tipmsg =  item.text ;
	            domAttrObj.name = 'customAttrList[' + self.opts.alreadyShowAttrNum + '].attr_value_id';
	           /* console.log('custom', custom) */
	            var specialHtmlObj = self._getSpecialInputHtml(item.data, 'radio', domAttrObj, item.is_custom_value, item.is_custom_value);
				html += specialHtmlObj.htmlStr;
	
	            html += '<input type="hidden" name="customAttrList[' + self.opts.alreadyShowAttrNum + '].attr_id" value="' + item.attr_id + '###' + item.text + '"/>';
	            html += '<input type="hidden" class="slef_para_id" name="customAttrList[' + self.opts.alreadyShowAttrNum + '].attr_value_id" value="' +  (specialHtmlObj.value && specialHtmlObj.value.length ? specialHtmlObj.value[0] : '') + '"/>';
	            html += '<input type="hidden" class="slef_para_value" name="customAttrList[' + self.opts.alreadyShowAttrNum + '].custom_attr_value" value="' +  (specialHtmlObj.text && specialHtmlObj.text.length ? specialHtmlObj.text[0] : '') + '"/>';
	            html += '<input type="hidden" name="customAttrList[' + self.opts.alreadyShowAttrNum + '].attr_type" value="' + item.show_type + '"/>';
	            break;
	        // case 4:
	        // // 文本域
	        // domAttrObj.type = 'text';
	        // if (item.maxlength) {
	        // domAttrObj.maxlength = item.maxlength;
	        // domAttrObj.maxdatalength = item.maxlength;
	        // }
	        // if (item.rows) {
	        // domAttrObj.rows = item.rows;
	        // }
	        // if (custom && custom.value) {
	        // domAttrObj.value = custom.value;
	        // }
	        // if (item.cols) {
	        // domAttrObj.cols = item.cols;
	        // }
	        // domAttrObj.class.push('textareapicker');
	        // html += '<textarea ' + self._objToStr(domAttrObj) + '>' +
			// (custom.value ? custom.value : '') + '</textarea>';
	        // break;
            case 5:
                // 多填框 multiFillBlock
                domAttrObj.class.push('checkboxpicker');
                domAttrObj.tipmsg =  item.text ;
                domAttrObj.name = 'customAttrList[' + self.opts.alreadyShowAttrNum + '].attr_value_id';
    
				var specialHtmlObj = self._getSpecialInputHtml(item.data, 'checkbox', domAttrObj, 'multiFillBlock');
                html += specialHtmlObj.htmlStr;
    
                html += '<input type="hidden" name="customAttrList[' + self.opts.alreadyShowAttrNum + '].attr_id" value="' + item.attr_id + '###' + item.text + '"/>';
                html += '<input type="hidden" class="slef_para_id" name="customAttrList[' + self.opts.alreadyShowAttrNum + '].attr_value_id" value="' +  (specialHtmlObj.value && specialHtmlObj.value.length ? specialHtmlObj.value.join('###') : '') + '"/>';
                html += '<input type="hidden" class="slef_para_value" name="customAttrList[' + self.opts.alreadyShowAttrNum + '].custom_attr_value" value="' +  (specialHtmlObj.text && specialHtmlObj.text.length ? specialHtmlObj.text.join('###') : '') + '"/>';
                html += '<input type="hidden" name="customAttrList[' + self.opts.alreadyShowAttrNum + '].attr_type" value="' + item.show_type + '"/>';
                break;


            /*
			 * // =====================预留功能===================== case 6: //
			 * 下拉时间控件 domAttrObj.class.push('timepicker'); html +=
			 * self._getTimeDatePicker(item.data && item.data.length ? item.data :
			 * item, 'timepicker', domAttrObj); break; case 7: // 日期控件
			 * domAttrObj.class.push('datepicker'); html +=
			 * self._getTimeDatePicker(item.data && item.data.length ? item.data :
			 * item, 'datepicker', domAttrObj); break; case 8: // 三级联动 html += '<div
			 * id="area_div_' + domAttrObj.id + '" class="addrpicker area_div_' +
			 * newEl + '" tipmsg="' + (domAttrObj.tipmsg ? domAttrObj.tipmsg :
			 * '请选择地区!') + '" setwidth="' + (domAttrObj.setwidth ?
			 * domAttrObj.setwidth : '200') + '" setheight="' +
			 * (domAttrObj.setheight ? domAttrObj.setheight : '25') + '"></div>'
			 * domAttrObj.type = 'hidden'; domAttrObj.changetip = 'area_div_' +
			 * domAttrObj.id; domAttrObj.class.push('addrInput'); delete
			 * domAttrObj.tipmsg; html += '<input ' +
			 * self._objToStr(domAttrObj) + '>'; break; case 9: // 文件上传控件 break; //
			 * =====================预留功能=====================
			 */
	    }
    	html += '</td></tr>';
    	self.opts.alreadyShowAttrNum = self.opts.alreadyShowAttrNum + 1;
    })
    // html字符串渲染成Dom
    if (appendType != 'before' && appendType != 'after') {
    	html = '<table><tbody>' + html + '</tbody></table>';
    }
    if (attrType && appendType == 'html') {
    	appendType = 'append';
    }
    el[appendType](html);
	if ($('.' + className.join('.')).find('.selectpicker') && $('.' + className.join('.')).find('.selectpicker').length) {
        $('.' + className.join('.')).off('change', '.selectpicker', {self: self}, self._selText).on('change', '.selectpicker', {self: self}, self._selText);
    }

    if ($('.' + className.join('.')).find('.checkboxpicker') && $('.' + className.join('.')).find('.checkboxpicker').length) {
        $('.' + className.join('.')).off('change', '.checkboxpicker', {self: self}, self._specialInputCheckText).on('change', '.checkboxpicker', {self: self}, self._specialInputCheckText);
    }
    if ($('.' + className.join('.')).find('.radiopicker') && $('.' + className.join('.')).find('.radiopicker').length) {
        $('.' + className.join('.')).off('change', '.radiopicker', {self: self}, self._specialInputCheckText).on('change', '.radiopicker', {self: self}, self._specialInputCheckText);
    }
    $('.' + className.join('.')).off('click', 'span.addNewInput', {self: self}, self._specialInputAddNewInput).on('click', 'span.addNewInput', {self: self}, self._specialInputAddNewInput);
    $('.' + className.join('.')).off('click', 'icon.removeInput', {self: self}, self._specialInputRemoveInput).on('click', 'icon.removeInput', {self: self}, self._specialInputRemoveInput);
//	    $('.' + className.join('.')).off('dblclick', 'div.inputSpecialBlock span', {self: self}, self._specialInputTextDblClick).on('dblclick', 'div.inputSpecialBlock span', {self: self}, self._specialInputTextDblClick);
    $('.' + className.join('.')).off('click', 'div.inputSpecialBlock span', {self: self}, function () {
        $(this).prev().click();
    }).on('click', 'div.inputSpecialBlock span', {self: self}, function () {
        $(this).prev().click();
    });
    $('.' + className.join('.')).off('blur', 'div.inputSpecialBlock input[type=text]', {self: self}, self._specialValueInputBlur).on('blur', 'div.inputSpecialBlock input[type=text]', {self: self}, self._specialValueInputBlur);
	    


    /*
	 * // =====================预留功能===================== // 日期事件绑定 if ($('.' +
	 * className.join('.')).find('.datepicker') && $('.' +
	 * className.join('.')).find('.datepicker').length) { $('.' +
	 * className.join('.')).find('.datepicker').datetimepicker({ timepicker:
	 * false, format: 'Y-m-d' }); } // 时间控件绑定 if ($('.' +
	 * className.join('.')).find('.timepicker') && $('.' +
	 * className.join('.')).find('.timepicker').length) { $('.' +
	 * className.join('.')).find('.timepicker').datetimepicker({ timepicker:
	 * false, format: 'Y-m-d' }); } //地区三级联动控件绑定,
	 * name、init_id、li_id、li_name等参数不知道具体含义 if ($('.' +
	 * className.join('.')).find('.addrpicker') && $('.' +
	 * className.join('.')).find('.addrpicker').length) { $('.' +
	 * className.join('.')).find('.addrpicker').cascadeSel({
	 * url:"/admin/area/normalList", name:"area_id", init_id:"",
	 * li_id:"area_id", li_name:"area_name", }); } //
	 * =====================预留功能=====================
	 */
/*
 * }
 * //==================================展示面板模块处理结束================================================ <<<<<<<
 * .mine ; } if ($('.' + className.join('.')).find('.radiopicker') && $('.' +
 * className.join('.')).find('.radiopicker').length) { $('.' +
 * className.join('.')).off('change', '.radiopicker', {self: self},
 * self._specialInputCheckText).on('change', '.radiopicker', {self: self},
 * self._specialInputCheckText); } $('.' + className.join('.')).off('click',
 * 'span.addNewInput', {self: self}, self._specialInputAddNewInput).on('click',
 * 'span.addNewInput', {self: self}, self._specialInputAddNewInput); $('.' +
 * className.join('.')).off('click', 'icon.removeInput', {self: self},
 * self._specialInputRemoveInput).on('click', 'icon.removeInput', {self: self},
 * self._specialInputRemoveInput); $('.' + className.join('.')).off('dblclick',
 * 'div.inputSpecialBlock span', {self: self},
 * self._specialInputTextDblClick).on('dblclick', 'div.inputSpecialBlock span',
 * {self: self}, self._specialInputTextDblClick); $('.' +
 * className.join('.')).off('blur', 'div.inputSpecialBlock input[type=text]',
 * {self: self}, self._specialValueInputBlur).on('blur', 'div.inputSpecialBlock
 * input[type=text]', {self: self}, self._specialValueInputBlur);
 */

    /*
	 * // =====================预留功能===================== // 日期事件绑定 if ($('.' +
	 * className.join('.')).find('.datepicker') && $('.' +
	 * className.join('.')).find('.datepicker').length) { $('.' +
	 * className.join('.')).find('.datepicker').datetimepicker({ timepicker:
	 * false, format: 'Y-m-d' }); } // 时间控件绑定 if ($('.' +
	 * className.join('.')).find('.timepicker') && $('.' +
	 * className.join('.')).find('.timepicker').length) { $('.' +
	 * className.join('.')).find('.timepicker').datetimepicker({ timepicker:
	 * false, format: 'Y-m-d' }); } //地区三级联动控件绑定,
	 * name、init_id、li_id、li_name等参数不知道具体含义 if ($('.' +
	 * className.join('.')).find('.addrpicker') && $('.' +
	 * className.join('.')).find('.addrpicker').length) { $('.' +
	 * className.join('.')).find('.addrpicker').cascadeSel({
	 * url:"/admin/area/normalList", name:"area_id", init_id:"${cfg_top_area}",
	 * li_id:"area_id", li_name:"area_name", }); } //
	 * =====================预留功能=====================
	 */
/*
 * }
 * //==================================展示面板模块处理结束================================================ ; }
 * if ($('.' + className.join('.')).find('.radiopicker') && $('.' +
 * className.join('.')).find('.radiopicker').length) { $('.' +
 * className.join('.')).off('change', '.radiopicker', {self: self},
 * self._specialInputCheckText).on('change', '.radiopicker', {self: self},
 * self._specialInputCheckText); } $('.' + className.join('.')).off('click',
 * 'span.addNewInput', {self: self}, self._specialInputAddNewInput).on('click',
 * 'span.addNewInput', {self: self}, self._specialInputAddNewInput); $('.' +
 * className.join('.')).off('click', 'icon.removeInput', {self: self},
 * self._specialInputRemoveInput).on('click', 'icon.removeInput', {self: self},
 * self._specialInputRemoveInput); $('.' + className.join('.')).off('dblclick',
 * 'div.inputSpecialBlock span', {self: self},
 * self._specialInputTextDblClick).on('dblclick', 'div.inputSpecialBlock span',
 * {self: self}, self._specialInputTextDblClick); $('.' +
 * className.join('.')).off('blur', 'div.inputSpecialBlock input[type=text]',
 * {self: self}, self._specialValueInputBlur).on('blur', 'div.inputSpecialBlock
 * input[type=text]', {self: self}, self._specialValueInputBlur);
 */

    /*
	 * // =====================预留功能===================== // 日期事件绑定 if ($('.' +
	 * className.join('.')).find('.datepicker') && $('.' +
	 * className.join('.')).find('.datepicker').length) { $('.' +
	 * className.join('.')).find('.datepicker').datetimepicker({ timepicker:
	 * false, format: 'Y-m-d' }); } // 时间控件绑定 if ($('.' +
	 * className.join('.')).find('.timepicker') && $('.' +
	 * className.join('.')).find('.timepicker').length) { $('.' +
	 * className.join('.')).find('.timepicker').datetimepicker({ timepicker:
	 * false, format: 'Y-m-d' }); } //地区三级联动控件绑定,
	 * name、init_id、li_id、li_name等参数不知道具体含义 if ($('.' +
	 * className.join('.')).find('.addrpicker') && $('.' +
	 * className.join('.')).find('.addrpicker').length) { $('.' +
	 * className.join('.')).find('.addrpicker').cascadeSel({
	 * url:"/admin/area/normalList", name:"area_id", init_id:"${cfg_top_area}",
	 * li_id:"area_id", li_name:"area_name", }); } //
	 * =====================预留功能=====================
	 */
}
// ==================================展示面板模块处理结束================================================
