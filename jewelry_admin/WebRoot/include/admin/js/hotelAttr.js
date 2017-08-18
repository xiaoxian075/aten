// 创建属性展示面板以及属性可选面板
function initAttrPanel (options) {
	options = options || {};
    this.opts = $.extend({}, this.demoOption, options);
    if (options.customUrl) {
    	this.opts.customOption.url = options.customUrl;
    	// 获取自由属性并处理成字典
	    this._customAttrHandle(this.opts.customOption);
    } else if (this.opts.customOption.url) {
    	// 获取自由属性并处理成字典
	    this._customAttrHandle(this.opts.customOption);
    }
    
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
	    	this._onlyReadAttrPanel(this.mustList, this.opts.el, this.opts.appendType);
	        // 可选属性初始渲染需要根据自由属性是否存在
	        this._onlyReadAttrPanel(this.otherList,  this.opts.el, this.opts.appendType, 1);
	    }
    } else {
    	if (options.el) {
	    	this._showAttrPanel(this.mustList, this.opts.el, this.opts.appendType);
	        // 可选属性初始渲染需要根据自由属性是否存在
	        this._showAttrPanel(this.otherList,  this.opts.el, this.opts.appendType, 1);
	    }
	    if (options.btnEl) {
	    	this._initOptionPanel(this.opts.btnEl);
	    }
    }
}
// 内部示例配置
initAttrPanel.prototype.demoOption = {
	// 必选属性展示定位容器
	el: '', // $('#attrPanelPosition'),
	// 相对定位容器的操作方法
	appendType: 'before',
	// 弹框事件绑定容器
	btnEl: '', // $('#openOptionPanel'),
	checkOptionAttr: [],
	// 属性类型显示文本
	attrTypeName: '屬性',
	// 是否只读，只读只显示内容(不带操作功能)
	onlyRead: false,
	onlyReadSpace: ' ',  //' | ',
	alreadyShowAttrNum: 0,
    pageOption: {
        // 是否启动分页
        pageBar: true,
        // 页面行数
        pageSize: 2,
        // 当前页数
        curPage: 1,
        firstText: '<<',
        lastText: '>>',
        // 显示的分页数个数
        maxShowLength: 5
    },
    tableOption: {
		tableEven:"#FFFFFF",
		tableOdd:"#FFFCF5",
		tableTrFirst:"#FFFFFF",
		hoverColor:"#FFFFCC",	
    },
    optionPanel: {
    	searchData: [],
    	curTableData: []
    },
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
        //     return data;
        // },
        // 失败方法
        // errFn: function (err) {
        //     return err;
        //}
    },
    customOption: {
        // 自由属性请求地址
        url: '', // '/admin/wineshop/customAttrList',
        // 列表数据
        data: '',
        // 传入的数据
        props: {},
        // 主键
        primaryKey: 'brand_id',
        // 字典，方便索引
        dictionary: {}
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
        //     return data;
        // },
        // 失败方法
        // errFn: function (err) {
        //     return err;
        //}
    }
}


//==================================请求数据处理开始================================================
//自由属性处理
initAttrPanel.prototype._customAttrHandle = function (opts) {
	if (opts) {
        if (opts.url) {
            this._getCustomAttr(opts.url);
        } else if (opts.props && opts.props.length) {
        	this.customList = this.opts.customOption.data = opts.props;
        	this.dictionary = this.opts.customOption.dictionary = this._customAttrDictionary(opts.props, opts.primaryKey || 'id');
        }
    }
}
//自由属性生成字典
initAttrPanel.prototype._customAttrDictionary = function (list, key) {
	var data = {};
	$.each(list, function (index, item) {
		data[item[key]] = index; // 'custom-index-' + index;
	})
	return data;
}
//请求自由属性
initAttrPanel.prototype._getCustomAttr = function (url) {
    var self = this;
    $.ajax({
        url: url,
        async: false,
        success: function (res) {
        	if (res.list && res.list.length) {
        		var newList = [];
        		$.each(res.list, function (index, item) {
        			var singleObj = {};
        			singleObj.brand_id = item.brand_id;
        			singleObj.value = [];
        			singleObj.text = [];
        			$.each(item.attrValueDtoList, function (index1, item1) {
        				singleObj.value.push(item1.attr_value_id);
        				singleObj.text.push(item1.custom_attr_value);
        			}) 
        			newList.push(singleObj);
        		});
        		self.customList = self.opts.customOption.data = newList;
        		self.dictionary = self.opts.customOption.dictionary = self._customAttrDictionary(newList, self.opts.customOption.primaryKey || 'id');
        	}
        },
        error: function (err) {
            console.log(err);
            return false;
        }
    })
}


//初始属性处理
initAttrPanel.prototype._initAttrHandle = function (opts) {
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
//对返回的全部属性进行归类处理
initAttrPanel.prototype._typeHandle = function (list, key) {
    var data = {
        mustList: [],
        otherList: []
    };
    $.each(list, function (index, item) {
        if (item[key] == 1) {
            data.mustList.push(item);
        } else {
        	// 此逻辑判断非必选没有属性项则不插入可选属性数组列表
        	if (item.attrValueList.length) {
        		data.otherList.push(item);
        	}
        	// 此逻辑没判断非必选没有属性项问题
//        	data.otherList.push(item);
        }
    })
    return data;
}
//请求初始属性
initAttrPanel.prototype._getInitAttr = function (url) {
    var self = this;
    $.ajax({
        url: url,
        async: false,
        success: function (res) {
        	// 对获取到的数据进行归类处理
            self.opts.attrOption.data = self._typeHandle(res.list, self.opts.attrOption.primaryKey || 'id');
            // 把归类后的数据合并到闭包作用域this的根目录下
            $.extend(true, self, self.opts.attrOption.data);
        },
        error: function (err) {
            console.log(err);
            return false;
        }
    })
}
//==================================请求数据处理结束================================================


//==================================展示面板模块处理开始================================================
//可选属性面板点击保存后执行的操作
initAttrPanel.prototype._updateShowPanelAttrStatus = function (removeArr, addArr) {
	var self = this;
	var optionAttrArr = $('.must_attr_panel[data-type=option]');
	$.each(removeArr, function (index, item) {
		// 清除展示面板的属性项
		$('.must_attr_panel[data-type=option][data-id=' + item + ']').remove();
		// 对可选属性状态进行更新
		$.each(self.otherList, function (index1, item1) {
			if (item1.brand_id == item) {
				delete item1.checked;
			}
		})
	})
	// 新增属性项到展示面板
	self._showAttrPanel(self.otherList,  self.opts.el, self.opts.appendType, 2);
	
}
//展示面板下拉列表选择文本赋值
initAttrPanel.prototype._selText = function () {
    var value = $(this).val();
    var text = $(this).find("option:selected").text();
    $(this).parent("td").find(".slef_para_value").val(text);
}
//特殊选框赋值
initAttrPanel.prototype._specialInputCheckText = function () {
    var checkInputs = $(this).parents('td').find('input:checked');
    var texts = [], values = [];
    $.each(checkInputs, function (index, item) {
    	console.log(item)
        values.push($(item).val());
        texts.push($(item).parent().text());
    })
    $(this).parents('td').find('.slef_para_id').val(values.join('###'));
    $(this).parents('td').find('.slef_para_value').val(texts.join('###'));
    
    // 选中的样式处理
    var inputs = $(this).parents('td').find('input');
    $.each(inputs, function (index, item) {
    	if (item.checked) {
    		$(item).parent().removeClass('uncheck').addClass('checked');
    	} else {
    		$(item).parent().removeClass('checked').addClass('uncheck');
    	}
    })
}
//对象转换成字符串
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
//下拉列表字符串处理
initAttrPanel.prototype._getSelectHtml = function (list, domAttrObj, value) {
	var self = this;
    var html = '', domAttrObj = domAttrObj || {};
    domAttrObj.type = 'select';
    domAttrObj.isrequired = 'yes';
    html += '<select ' + self._objToStr(domAttrObj) + '>';
    html += '<option value="">请选择</option>';
    $.each(list, function (index, item) {
    	html += '<option value="' + item.attr_value_id + '"' + (item.attr_value_id == value ? ' selected' : '') + '>' + item.attr_value + '</option>';
    })
    html += '</select>';
    return html;
}
//单、复选框字符串处理
initAttrPanel.prototype._getSpecialInputHtml = function (list, type, domAttrObj, value) {
	var self = this;
  var html = '';
  html += '<div id="div_' + list[0].brand_id + '" tipmsg="' + domAttrObj.tipmsg + '" style="display: inline-block">';
  $.each(list, function(index, item) {
    var isValidate='';
    if(index == 0){
    	domAttrObj.class.push('validate');
    	domAttrObj.isrequired = 'yes';
    	domAttrObj.changeTip = 'div_' + item.brand_id;
    } else {
    	if (domAttrObj.class.length > 2) {
    		domAttrObj.class.slice(0,2);
    		delete domAttrObj.isrequired;
    		delete domAttrObj.changetip;
    	}
    }
    domAttrObj.value = item.attr_value_id;
    domAttrObj.type = type;
    domAttrObj.name = item.brand_id;
    var checkStatus = '';
    if (value && typeof value == 'string') {
    	if (item.attr_value_id == value) {
    		checkStatus = true;
    	}
    } else if (value && value.length) {
    	if (value.indexOf(item.attr_value_id) > -1) {
    		checkStatus = true;
    	}
    }
    html += '<label title="' + item.attr_value + '" class="' + (checkStatus ? 'checked' : 'uncheck') + '"><input '+ self._objToStr(domAttrObj) + (checkStatus ? ' checked' : '') + ' />' + item.attr_value + '</label>';
  })
  html += '</div>';
  return html;
}
//时间控件与日期控件
initAttrPanel.prototype._getTimeDatePicker = function (list, type, domAttrObj) {
	var self = this;
  var html = '', domAttrObj = domAttrObj || {};
  domAttrObj.type = 'text';
  if ($.isarray(list)) {
    // 数组类型配置参数
    $.each(list, function(index, item) {
    	domAttrObj.id = domAttrObj.name = item.brand_id;
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
//只读方式渲染
initAttrPanel.prototype._onlyReadAttrPanel = function (attrList, el, appendType, attrType) {
	var html = '',
	self = this,
	randomNum = self.randomNum = self.randomNum ? self.randomNum : Math.floor(Math.random() * 1000),
	appendType = appendType || 'html',
	className = ['must_attr_panel', 'only_read_panel', 'must_attr_panel_' + randomNum];
	$.each(attrList, function (index, item) {
    	if (!item.attrValueList || !item.attrValueList.length) {
    		return;
    	}
    	
    	if (attrType == 1) {
    		// 初始可选类别属性，需要验证是否有自由属性值，有则添加，无则过滤
    		if (!self.dictionary || !self.dictionary.hasOwnProperty(item.brand_id)) {
    			return;
    		}
    	}
    	var custom = ''
    	if (self.dictionary && self.dictionary.hasOwnProperty(item.brand_id)) {
        	// 从字典中找出自由属性的对应索引值，根据索引值获取配置
    		custom = self.customList[self.dictionary[item.brand_id]];
        }
    	// 左侧部分
    	html += '<tr class="' + className.join(' ') + '" data-id="' + item.brand_id + '" data-type="' + (attrType ? 'option' : 'must') + '"><td class="td_left"> ' + item.brand_name + ':</td>';
    	// 右侧部分
    	html += '<td class="td_right_two">';
    	
    	switch (Number(item.option_type)) {
	        case 0:
	            // 输入框的文本显示
	            html += '<span class="only_read_span">' + (custom && custom.text ? custom.text : '') + '</span>';
	            break;
	        case 1:
	            // 下拉列表
	        	html += '<span class="only_read_span">' + (custom && custom.text ? custom.text : '') + '</span>';
	            break;
	        case 2:
	            // 复选框
	        	var checkedText = custom && custom.text ? custom.text : '';
	        	//新版只读模式
	        	var spanStrArr = []
	        	checkedText.map(function (item, index) {
	        		spanStrArr.push('<span class="only_read_span" title="' + item + '">' + item + '</span>');
	        	})
	        	html += spanStrArr.join(self.opts.onlyReadSpace);
	        	//旧只读模式
	        	//html += '<span>' + checkedText.join(self.opts.onlyReadSpace) + '</span>';
	            break;
	        case 3:
	            // 单选框
	        	var checkedText = custom && custom.text ? custom.text : '';
	        	//新版只读模式
	        	var spanStrArr = []
	        	checkedText.map(function (item, index) {
	        		spanStrArr.push('<span class="only_read_span" title="' + item + '">' + item + '</span>');
	        	})
	        	html += spanStrArr.join(self.opts.onlyReadSpace);
	        	//旧只读模式
	        	//html += '<span>' + checkedText.join(self.opts.onlyReadSpace) + '</span>';
	            break;
	        case 4:
	            // 文本域
	        	html += '<span class="only_read_span">' + (custom && custom.text ? custom.text : '') + '</span>';
	            break;
	        case 6:
	            // 下拉时间控件
	        	var timeText = custom && custom.text ? custom.text : '';
	        	html += '<span class="only_read_span">' + timeText.join('-') + '</span>';
	            break;
	        case 7:
	            // 日期控件
	        	var dateText = custom && custom.text ? custom.text : '';
	        	html += '<span class="only_read_span">' + dateText.join('   --   ') + '</span>';
	            break;
	        case 8:
	            // 三级联动
	        	var addrText = custom && custom.text ? custom.text : '';
	        	html += '<span class="only_read_span">' + addrText.join(' -- ') + '</span>';
	            break;
	        case 9:
	            // 文件上传控件
	            break;
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

//渲染必选属性
//attrList执行的属性列表，el定位的容器，appendType填充方式，attrType有则为可选属性
initAttrPanel.prototype._showAttrPanel = function (attrList, el, appendType, attrType) {
    var html = '',
    	self = this,
    	randomNum = self.randomNum = self.randomNum ? self.randomNum : Math.floor(Math.random() * 1000),
		appendType = appendType || 'html',
		className = ['must_attr_panel', 'must_attr_panel_' + randomNum];
    $.each(attrList, function (index, item) {
    	if (!item.attrValueList || !item.attrValueList.length) {
    		return;
    	}
    	
    	if (attrType == 1) {
    		// 初始可选类别属性，需要验证是否有自由属性值，有则添加，无则过滤
    		if (!self.dictionary || !self.dictionary.hasOwnProperty(item.brand_id)) {
    			return;
    		} else {
    			item.is_must = 1;
    			self.otherList[index].checked = true;
    			if (!self.checkOptionAttr) {
    				self.checkOptionAttr = [];
    			}
    			self.checkOptionAttr.push(item.brand_id);
    		}
    	} else if (attrType == 2) {
    		// 可选面板新增属性到展示面板，需要判断属性是否已存在展示面板，存在则过滤，不存在则新增
    		if (!self.checkOptionAttr || !self.checkOptionAttr.length || self.checkOptionAttr.indexOf(item.brand_id) < 0) {
    			// 不存在新增到展示面板的可选属性数组或者当前属性项不在新增可选数组，则过滤掉操作
    			return;
    		} else {
    			// 已经存在展示面板的属性项过滤操作
    			if ($('.' + className.join('.') + '[data-type=option][data-id=' + item.brand_id + ']').length) {
    				return;
    			} else {
    				self.otherList[index].checked = true;
    			}
    		}
    	}
    	var custom = ''
    	if (self.dictionary && self.dictionary.hasOwnProperty(item.brand_id)) {
        	// 从字典中找出自由属性的对应索引值，根据索引值获取配置
    		custom = self.customList[self.dictionary[item.brand_id]];
        }
    	var domAttrObj = {};
    	item.is_must = 1;
    	
    	// 左侧部分
    	html += '<tr class="' + className.join(' ') + '" data-id="' + item.brand_id + '" data-type="' + (attrType ? 'option' : 'must') + '"><td class="td_left"> ' + item.brand_name + (item.is_must == '1' ? '<span class="must_span">*</span>' : ':') + '</td>';
    	// 右侧部分
    	html += '<td class="td_right_two">';
    	domAttrObj.class = ['validate'];
    	
    	switch (Number(item.option_type)) {
	        case 0:
	            // 输入框
	            domAttrObj.type = 'text';
	            domAttrObj.isrequired = 'yes';
	            domAttrObj.id = item.attrValueList[0].brand_id;
	            domAttrObj.tipmsg = '请输入' + item.brand_name;
	            domAttrObj.name = 'customAttrList[' + self.opts.alreadyShowAttrNum + '].custom_attr_value';
	            if (custom) {
	            	domAttrObj.value = custom.value[0];
	            }
	            html += '<input ' + self._objToStr(domAttrObj) + '>';
	
	            html += '<input type="hidden" name="customAttrList[' + self.opts.alreadyShowAttrNum + '].brand_id" value="' + item.brand_id + '"/>';
	            html += '<input type="hidden" name="customAttrList[' + self.opts.alreadyShowAttrNum + '].attr_value_id" value="' + item.attrValueList[0].attr_value_id + '"/>';
	            html += '<input type="hidden" name="customAttrList[' + self.opts.alreadyShowAttrNum + '].attr_type" value="' + item.attr_type + '"/>';
	            break;
	        case 1:
	            // 下拉列表
	            domAttrObj.class.push('selectpicker');
	            domAttrObj.id = 'sel_' + item.brand_id;
	            domAttrObj.changetip = 'sel_' + item.brand_id;
	            if (item.maxlength) {
	            	domAttrObj.maxlength = item.maxlength;
	                domAttrObj.maxdatalength = item.maxlength;
	            }
	            if (item.placeholder) {
	            	domAttrObj.placeholder = item.placeholder;
	            }
	            domAttrObj.name = 'customAttrList[' + self.opts.alreadyShowAttrNum + '].attr_value_id';
	            domAttrObj.tipmsg =  item.brand_name ;
	            html += self._getSelectHtml(item.attrValueList, domAttrObj, custom && custom.value ? custom.value[0] : '');
	            
	            html += '<input type="hidden" name="customAttrList[' + self.opts.alreadyShowAttrNum + '].brand_id" value="' + item.brand_id + '"/>';
	            html += '<input type="hidden" class="slef_para_value" name="customAttrList[' + self.opts.alreadyShowAttrNum + '].custom_attr_value" value="' +  (custom && custom.text ? custom.text[0] : '') + '"/>';
	            html += '<input type="hidden" name="customAttrList[' + self.opts.alreadyShowAttrNum + '].attr_type" value="' + item.attr_type + '"/>';
	            break;
	        case 2:
	            // 复选框
	            domAttrObj.class.push('checkboxpicker');
	            domAttrObj.tipmsg =  item.brand_name ;
	            domAttrObj.name = 'customAttrList[' + self.opts.alreadyShowAttrNum + '].attr_value_id';
	
	            html += self._getSpecialInputHtml(item.attrValueList, 'checkbox', domAttrObj, custom && custom.value ? custom.value : '');
	
	            html += '<input type="hidden" name="customAttrList[' + self.opts.alreadyShowAttrNum + '].brand_id" value="' + item.brand_id + '"/>';
	            html += '<input type="hidden" class="slef_para_id" name="customAttrList[' + self.opts.alreadyShowAttrNum + '].attr_value_id" value="' +  (custom && custom.value ? custom.value.join('###') : '') + '"/>';
	            html += '<input type="hidden" class="slef_para_value" name="customAttrList[' + self.opts.alreadyShowAttrNum + '].custom_attr_value" value="' +  (custom && custom.text ? custom.text.join('###') : '') + '"/>';
	            html += '<input type="hidden" name="customAttrList[' + self.opts.alreadyShowAttrNum + '].attr_type" value="' + item.attr_type + '"/>';
	            break;
	        case 3:
	            // 单选框
	            domAttrObj.class.push('radiopicker');
	            domAttrObj.tipmsg =  item.brand_name ;
	            domAttrObj.name = 'customAttrList[' + self.opts.alreadyShowAttrNum + '].attr_value_id';
	            console.log('custom', custom)
	            html += self._getSpecialInputHtml(item.attrValueList, 'radio', domAttrObj, custom && custom.value ? custom.value : '');
	
	            html += '<input type="hidden" name="customAttrList[' + self.opts.alreadyShowAttrNum + '].brand_id" value="' + item.brand_id + '"/>';
	            html += '<input type="hidden" class="slef_para_id" name="customAttrList[' + self.opts.alreadyShowAttrNum + '].attr_value_id" value="' +  (custom && custom.value ? custom.value[0] : '') + '"/>';
	            html += '<input type="hidden" class="slef_para_value" name="customAttrList[' + self.opts.alreadyShowAttrNum + '].custom_attr_value" value="' +  (custom && custom.text ? custom.text[0] : '') + '"/>';
	            html += '<input type="hidden" name="customAttrList[' + self.opts.alreadyShowAttrNum + '].attr_type" value="' + item.attr_type + '"/>';
	            break;
	        case 4:
	            // 文本域
	            domAttrObj.type = 'text';
	            if (item.maxlength) {
	            	domAttrObj.maxlength = item.maxlength;
	                domAttrObj.maxdatalength = item.maxlength;
	            }
	            if (item.rows) {
	            	domAttrObj.rows = item.rows;
	            }
	            if (custom && custom.value) {
	            	domAttrObj.value = custom.value;
	            }
	            if (item.cols) {
	            	domAttrObj.cols = item.cols;
	            }
	            domAttrObj.class.push('textareapicker');
	            html += '<textarea ' + self._objToStr(domAttrObj) + '>' + (custom.value ? custom.value : '') + '</textarea>';
	            break;
	        case 6:
	            // 下拉时间控件
	            domAttrObj.class.push('timepicker');
	            html += self._getTimeDatePicker(item.attrValueList && item.attrValueList.length ? item.attrValueList : item, 'timepicker', domAttrObj);
	            break;
	        case 7:
	            // 日期控件
	            domAttrObj.class.push('datepicker');
	            html += self._getTimeDatePicker(item.attrValueList && item.attrValueList.length ? item.attrValueList : item, 'datepicker', domAttrObj);
	            break;
	        case 8:
	            // 三级联动
	            html += '<div id="area_div_' + domAttrObj.id + '" class="addrpicker area_div_' + newEl + '" tipmsg="' + (domAttrObj.tipmsg ? domAttrObj.tipmsg : '请选择地区!') + '" setwidth="' + (domAttrObj.setwidth ? domAttrObj.setwidth : '200') + '" setheight="' + (domAttrObj.setheight ? domAttrObj.setheight : '25') + '"></div>'
	            domAttrObj.type = 'hidden';
	            domAttrObj.changetip = 'area_div_' + domAttrObj.id;
	            domAttrObj.class.push('addrInput');
	            delete domAttrObj.tipmsg;
	            html += '<input ' + self._objToStr(domAttrObj) + '>';
	            break;
	        case 9:
	            // 文件上传控件
	            break;
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
    	$('.' + className.join('.')).find('.selectpicker').off('change', self._selText).on('change', self._selText);
    }
    if ($('.' + className.join('.')).find('.checkboxpicker') && $('.' + className.join('.')).find('.checkboxpicker').length) {
    	$('.' + className.join('.')).find('.checkboxpicker').off('change', self._specialInputCheckText).on('change', self._specialInputCheckText);
    }
    if ($('.' + className.join('.')).find('.radiopicker') && $('.' + className.join('.')).find('.radiopicker').length) {
    	$('.' + className.join('.')).find('.radiopicker').off('change', self._specialInputCheckText).on('change', self._specialInputCheckText);
    }
    // 日期事件绑定
    if ($('.' + className.join('.')).find('.datepicker') && $('.' + className.join('.')).find('.datepicker').length) {
    	$('.' + className.join('.')).find('.datepicker').datetimepicker({
            timepicker: false,
            format: 'Y-m-d'
        });
    }
    // 时间控件绑定
    if ($('.' + className.join('.')).find('.timepicker') && $('.' + className.join('.')).find('.timepicker').length) {
    	$('.' + className.join('.')).find('.timepicker').datetimepicker({
            timepicker: false,
            format: 'Y-m-d'
        });
    }
  //地区三级联动控件绑定, name、init_id、li_id、li_name等参数不知道具体含义
    if ($('.' + className.join('.')).find('.addrpicker') && $('.' + className.join('.')).find('.addrpicker').length) {
    	$('.' + className.join('.')).find('.addrpicker').cascadeSel({
    		url:"/admin/area/normalList",
            name:"area_id",
            init_id:"${cfg_top_area}",
            li_id:"area_id",
            li_name:"area_name",
        });
    }
}
//==================================展示面板模块处理结束================================================



//==================================可选属性面板处理开始================================================
initAttrPanel.prototype._initOptionPanel = function (btnEl) {
	var self = this;
	self.cacheCheckedArr = self.checkOptionAttr ? self.checkOptionAttr.join('&').split('&') : [];
    var contentHtml = '<div class="option_attr_panel">';
    var searchHtml = self._getSearchHtml();
    var pageHtml = self._getPaginationHtml(self.searchData);
    var tableHtml = self._getOptionHtml(self.curTableData);
    contentHtml += searchHtml + tableHtml + pageHtml;
    contentHtml += '</div>';
    self.dialog = art.dialog({
    	id: 'option_attr_panel_' + self.randomNum,
        lock: true,
        drag: true,
        fixed:true,
        show: false,
        okValue: '保存',
        cancelValue: '取消',
        content: contentHtml,
        title: '选择关联'+self.opts.attrTypeName ,
        ok: function () {
        	self._optionPanelSave()
        	this.hidden();
            return false;
        },
        cancel: function () {
        	this.hidden();
            return false;
        }
    }).hidden();
    $('.option_attr_panel').find('.pagination_content span').off('click', self, self._paginationClick).on('click', self, self._paginationClick);
    $('.option_attr_panel').find('.option_search_panel_content .searchBtn').off('click', self, self._attrSearch).on('click', self, self._attrSearch);
    $('.option_attr_panel').find('.option_table_panel_content .attrCheck').off('click', self, self._checkChange).on('click', self, self._checkChange);
    btnEl.on('click', self, self._openDialog);
}
initAttrPanel.prototype._openDialog = function (e) {
	var self = e.data;
	self.cacheCheckedArr = self.checkOptionAttr ? self.checkOptionAttr.join('&').split('&') : [];
	self.dialog.visible();
}
// 可选属性面板点击确定是执行的保存选中项，并区分成清掉的属性数组以及新增的属性数组
initAttrPanel.prototype._optionPanelSave = function () {
	var self = this;
	var addAttrList = [],
		removeAttrList = [],
		checkedList = self.checkOptionAttr || [],
		cacheCheckedList = self.cacheCheckedArr || [];
	// 查询去掉的属性
	$.each(checkedList, function (index, item) {
		if (cacheCheckedList.indexOf(item) < 0) {
			removeAttrList.push(item);
		}
	})
	// 查询新增的属性
	$.each(cacheCheckedList, function (index, item) {
		if (checkedList.indexOf(item) < 0) {
			addAttrList.push(item);
		}
	})
	self.checkOptionAttr = self.cacheCheckedArr;
	self._updateShowPanelAttrStatus(removeAttrList, addAttrList);
}
//本地查询
initAttrPanel.prototype._attrSearch = function (e) {
	var self = e.data;
   var $el = $(this).parents('.option_search_panel_content');
   var searchText = $el.find('.searchInput').val();
   self.searchData = [];
   if (!searchText || !searchText.trim()) {
	   self.searchData = self.otherList;
   } else {
	   $.each(self.otherList, function (index, item) {
	       if (item.brand_name.indexOf(searchText) > -1 || item.note.indexOf(searchText) > -1 || item.option_type_name.indexOf(searchText) > -1) {
	    	   self.searchData.push(item);
	       }
	   })
   }
   self._updatePaginationHtml(self.searchData, $(this).parents('.option_attr_panel').find('.pagination_content'));
}

//酒店可选属性 头部筛选渲染
initAttrPanel.prototype._getSearchHtml = function () {
	var self = this;
	var html = '<div id="option_search_panel_content" class="option_search_panel_content"><label for="searchInput">' + self.opts.attrTypeName + '属性: <input type="text" class="searchInput" name="searchInput" placeholder="请输入内容" /></label><button class="searchBtn">搜索</button></div>';
	self.searchData = self.otherList;
	return html;
}

//酒店可选属性复选框功能

initAttrPanel.prototype._checkChange = function (e) {
	var self = e.data;
	var _this = this
	var $el = $(_this).parents('.option_table_panel_content');
	var checkboxs = $el.find('.attrCheck').slice(1);
	
	// 全选框点击的操作
	if (_this.id && _this.id == 'allCheck') {
		$.each(checkboxs, function(index, item) {
			item.checked = _this.checked;
			// 执行选中属性的缓存
			var index = self.cacheCheckedArr.indexOf(item.value);
			if (index > -1 && !_this.checked) {
				self.cacheCheckedArr.splice(index, 1);
			} else if (index < 0 && _this.checked) {
				self.cacheCheckedArr.push(item.value);
			}
		})
	} else {
		// 执行选中属性的缓存
		var index = self.cacheCheckedArr.indexOf(_this.value);
		if (index > -1 && !_this.checked) {
			self.cacheCheckedArr.splice(index, 1);
		} else if (index < 0 && _this.checked) {
			self.cacheCheckedArr.push(_this.value);
		}
		// 根据当前状态处理全选框
		var isAllCheck = true;
		$.each(checkboxs, function(index, item) {
			if (!item.checked) {
				isAllCheck = false;
			}
		})
		$el.find('#allCheck')[0].checked = isAllCheck;
	}
}

//更新表格内的酒店可选属性

initAttrPanel.prototype._updateOptionHtml = function (attrConfig, $el) {
	var self = this;
	var html = '',
		checkedArr = self.cacheCheckedArr || [];
	// 表格头部
	html += '<thead><tr><th width="50px"><input type="checkbox" id="allCheck" class="attrCheck"></th><th width="100px">' + self.opts.attrTypeName + '名称</th></tr></thead>';
	// 表格内容
	html += '<tbody>';
	// 存在渲染数据	
	if (attrConfig.length) {
		$.each(attrConfig, function(index, item) {
			html += '<tr class="' + (item.checked ? 'hasBeChecked' : '') + '">';
			html += '<td><input type="checkbox" class="attrCheck" data-index="' + index + '" value="' + item.brand_id + '" ' + (checkedArr.indexOf(item.brand_id) > -1 ? 'checked' : '') + '></td>';
			html += '<td>' + item.brand_name + '</td>';
			html += '</tr>';
		})
	} else {
		html += '<tr><td colspan="4" align="center">暂无数据</td>'
	}
	
	html += '</tbody>';
	$el.html(html);
	
	if ($el.find('.attrCheck').length > 1 && $el.find('.attrCheck').length - $el.find('.attrCheck:checked').length === 1) {
		//表格内数据重新渲染后根据复选框状态及个数判断是否需要选中全选复选框，判断条件	1.复选框个数大于2(表示存在可渲染数据)	2.判断总的复选框个数比选中的复选框个数多1(表示出了全选复选框外其他复选框都已选中)
		$el.find('.attrCheck').eq(0).attr('checked', true);
	}
	//	重新绑定复选框事件	
	$el.find('.attrCheck').off('click', self, self._checkChange).on('click', self, self._checkChange);
}
//酒店可选属性配置渲染到表格以供操作(新增到必选属性)

initAttrPanel.prototype._getOptionHtml = function (attrConfig, el, appendType) {
	var self = this;
	attrConfig = attrConfig;
	var html = '',
		checkedArr = self.cacheCheckedArr || [],
		// 随机数，用来添加唯一类名
		randomNum = Math.floor(Math.random() * 1000),
		// 插入方式，有append，after，before，prepend,html
		appendType = appendType || 'before',
		// 外部传入的定位容器，必须是id名称，能带#最好
		el = el ? (el.indexOf('#') > -1 ? el : '#' + el) : '',
		// 容器的类名
		commonEl = 'option_table_panel_content',
		newEl = commonEl + '_' + randomNum,
		classEl = '.' + newEl;
	// 外围框架开始
	html += '<div id="' + newEl + '" class="' + commonEl + ' ' + newEl + '"><table border="1" cellspacing="0">';
	// 表格头部
	html += '<thead><tr><th width="50px"><input type="checkbox" id="allCheck" class="attrCheck"></th><th width="100px">' + self.opts.attrTypeName + '</th></tr></thead>';
	// 表格内容
	html += '<tbody>';
	// 存在渲染数据	
	if (attrConfig.length) {
		$.each(attrConfig, function(index, item) {
			html += '<tr class="' + (item.checked ? 'hasBeChecked' : '') + '">';
			html += '<td><input type="checkbox" class="attrCheck" data-index="' + index + '" value="' + item.brand_id + '" ' + (checkedArr.indexOf(item.brand_id) > -1 ? 'checked' : '') + '></td>';
			html += '<td>' + item.brand_name + '</td>';
			html += '</tr>';		
		})
	} else {
		html += '<tr><td colspan="4" align="center">暂无数据</td>'
	}
	
	html += '</tbody>';
	html += '</table></div>';
	if (!appendType || !el) {
		return html;
	} else {
		$(el)[appendType](html);
		return '';
	}
}

//分页点击

initAttrPanel.prototype._paginationClick = function (e) {
	var self = e.data;
	var $el = $(this).parent('.pagination_content');
	// 分页第一个页数
	var firstNum = $el.find('.page_num').eq(0).data('index');
	// 分页最后一个页数
	var lastNum = $el.find('.page_num').eq(-1).data('index');
	// 不可操作的忽略
	if (this.disabled) {
		return
	} else if ($(this).hasClass('page_first')) {
		// 总数小于最大展示数或者第一个分页数是1的忽略
		if (self.totalPage <= self.maxShowLength || firstNum == 1) {
			return;
		} else {
			// 统一先清除选中的样式
			$el.find('.page_num').removeAttr('disabled').removeClass('is_active');
			// 第一分页数大于最大展示数的，直接对分页数进行修改
			if (firstNum > self.maxShowLength) {
				for (var i = 0; i < self.maxShowLength; i++) {
					$el.find('.page_num').eq(i).data('index', firstNum - self.maxShowLength + i).html(firstNum - self.maxShowLength + i);
				}
			} else {
				// 第一分页数小于最大展示数，表明部分分页数重叠，则第一分页数从1开始
				for (var i = 0; i < self.maxShowLength; i++) {
					$el.find('.page_num').eq(i).data('index', i + 1).html(i + 1);
					// 对当前页数进行样式处理
					if (self.curPage == i + 1) {
						$el.find('.page_num').eq(i).attr('disabled', true).addClass('is_active');
					}
				}
			}
		}
		//      self.curTableData = optionPanelSearchAttrArr.slice(1, end)
	} else if ($(this).hasClass('page_last')) {
		// 总数小于最大展示数或者最后分页数是总数的忽略
		if (self.totalPage <= self.maxShowLength || lastNum == self.totalPage) {
			return;
		} else {
			// 统一先清除选中的样式
			$el.find('.page_num').removeAttr('disabled').removeClass('is_active');
			// 最后分页数与总数的差距小于最大展示数，表明部分分页数重叠
			if (self.totalPage - lastNum < self.maxShowLength) {
				// 重新设置第一分页的起始值的前已分页数
				firstNum = self.totalPage - self.maxShowLength;
				for (var i = 0; i < self.maxShowLength; i++) {
					$el.find('.page_num').eq(i).data('index', firstNum + i + 1).html(firstNum + i + 1);
					// 对当前页数进行样式处理
					if (self.curPage == firstNum + i + 1) {
						$el.find('.page_num').eq(i).attr('disabled', true).addClass('is_active');
					}
				}
			} else {
				// 最后分页数与总数差距大于最大展示数，则分页数不重叠，直接修改分页数
				for (var i = 0; i < self.maxShowLength; i++) {
					$el.find('.page_num').eq(i).data('index', lastNum + i + 1).html(lastNum + i + 1);
				}
			}
		}
	} else if ($(this).hasClass('page_prev')) {} else if ($(this).hasClass('page_next')) {} else if ($(this).hasClass('page_num')) {
		// 统一先清除选中的样式
		$el.find('.page_num.is_active').removeAttr('disabled').removeClass('is_active');
		// 对当前页数进行样式处理
		$(this).attr('disabled', true).addClass('is_active');
		// 调整当前页数的数值
		self.curPage = $(this).data('index');
		var startIndex = self.pageSize * (self.curPage - 1);
		var endIndex = self.pageSize * self.curPage;
		// 获取当前表格需要的数据
		self.curTableData = self.searchData.slice(startIndex, endIndex);
		// 利用html赋值的方式进行内容更新
		self._updateOptionHtml(self.curTableData, $('.option_table_panel_content').find('table'));
	}
}

//分页逻辑判断与渲染

initAttrPanel.prototype._paginationHtmlHandle = function (totalPage, el, appendType) {
	var self = this;
	var html = '',
		firstText = self.opts.pageOption.firstText || '<<',
		lastText = self.opts.pageOption.lastText || '>>',
		// 随机数，用来添加唯一类名
		randomNum = Math.floor(Math.random() * 1000),
		// 插入方式，有append，after，before，prepend
		appendType = appendType || '',
		// 外部传入的定位容器，必须是id名称，能带#最好
		el = el ? (el.indexOf('#') > -1 ? el : '#' + el) : '',
		// 容器的类名
		commonEl = 'option_page_panel_content',
		newEl = commonEl + '_' + randomNum,
		classEl = '.' + newEl;
	html += '<div id="' + newEl + '" class="' + commonEl + ' ' + newEl + '"><div class="pagination_content">';
	if (totalPage > self.maxShowLength) {
		html += '<span class="page_first">' + firstText + '</span><span class="page_prev"><</span>';
	}
	var lastShowPage = totalPage <= self.maxShowLength ? totalPage : self.maxShowLength;
	for (var i = 1; i <= lastShowPage; i++) {
		html += '<span';
		if (i == 1) {
			html += ' class="page_num is_active" disabled ';
		} else {
			html += ' class="page_num"';
		}
		html += ' data-index="' + i + '">' + i + '</span>';
	}
	if (totalPage > self.maxShowLength) {
		html += '<span class="page_next">></span><span class="page_last">' + lastText + '</span>';
	}
	html += '</div></div>';
	if (!appendType || !el) {
		return html;
	} else {
		$(el)[appendType](html);
		return '';
	}
}
// 更新切换分页后重新调整分页容器的内容
initAttrPanel.prototype._updatePaginationHtml = function (attrConfig, $el) {
	var self = this;
	var firstText = self.opts.pageOption.firstText || '<<';
	var lastText = self.opts.pageOption.lastText || '>>';
	attrConfig = attrConfig;
	self.totalCount = attrConfig.length;
	self.totalPage = Math.ceil(self.totalCount / self.pageSize);
	self.curPage = 1;
	var html = '';
	if (self.totalPage <= 1) {
		self.curTableData = attrConfig;
	} else {
		var startIndex = self.pageSize * (self.curPage - 1);
		var endIndex = self.pageSize * self.curPage;
		if (endIndex > self.totalCount) {
			endIndex = self.totalCount;
		}
		self.curTableData = attrConfig.slice(startIndex, endIndex);
	}

	var totalPage = self.totalPage;
	// 直接搬迁使用
	if (totalPage > self.maxShowLength) {
		html += '<span class="page_first">' + firstText + '</span><span class="page_prev"><</span>';
	}
	var lastShowPage = totalPage <= self.totalPage ? totalPage : self.totalPage;
	for (var i = 1; i <= lastShowPage; i++) {
		html += '<span';
		if (i == 1) {
			html += ' class="page_num is_active" disabled ';
		} else {
			html += ' class="page_num"';
		}
		html += ' data-index="' + i + '">' + i + '</span>';
	}
	if (totalPage > self.totalPage) {
		html += '<span class="page_next">></span><span class="page_last">' + lastText + '</span>';
	}
	$el.html(html);
	//	分页容器内容更新后继续更新渲染最新数据到表格中	
	self._updateOptionHtml(self.curTableData, $('.option_table_panel_content').find('table'));
	//	重新绑定分页span事件
	$el.find('span').off('click', self, self._paginationClick).on('click', self, self._paginationClick);
}

//分页入口

initAttrPanel.prototype._getPaginationHtml = function (attrConfig, el, appendType) {
	var self = this;
	attrConfig = attrConfig;
	self.pageSize = self.opts.pageOption.pageSize || 3;
	self.totalCount = attrConfig.length;
	self.maxShowLength = self.opts.pageOption.maxShowLength || 10;
	self.totalPage = Math.ceil(self.totalCount / self.pageSize);
	self.curPage = 1;
	var html = '';
	if (self.totalPage <= 1) {
		self.curTableData = attrConfig;
	} else {
		var startIndex = self.pageSize * (self.curPage - 1);
		var endIndex = self.pageSize * self.curPage;
		if (endIndex > self.totalCount) {
			endIndex = self.totalCount;
		}
		self.curTableData = attrConfig.slice(startIndex, endIndex);
	}
	html += self._paginationHtmlHandle(self.totalPage);
	return html;
}

//==================================可选属性面板处理结束================================================