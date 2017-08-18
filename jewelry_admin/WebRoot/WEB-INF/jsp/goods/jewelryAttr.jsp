<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<style type="text/css">
.option_attr_panel {
	
}

.jewelry_attr_panel .inputSpecialBlock.checked {
	padding: 7px 10px 7px 5px;
	margin: 0 5px 5px 0;
	background: #9d0697;
	border-radius: 5px;
	color: #fff;
	cursor: pointer;
	/*line-height: 28px;*/
	/*max-width: 100px;*/
	display: inline-block;
	overflow: hidden;
	text-overflow: ellipsis;
	white-space: nowrap;
}

.jewelry_attr_panel .inputSpecialBlock.uncheck {
	padding: 7px 10px 7px 5px;
	margin: 0 5px 5px 0;
	background: #DDD;
	border-radius: 5px;
	color: #000;
	cursor: pointer;
	/*line-height: 28px;*/
	/*max-width: 100px;*/
	display: inline-block;
	overflow: hidden;
	text-overflow: ellipsis;
	white-space: nowrap;
}

.jewelry_attr_panel .td_right_two span.addNewInput {
	margin: 0;
	margin-bottom: 7px;
	padding: 5px;
	vertical-align: bottom;
	display: inline-block;
	cursor: pointer;
	border: 1px solid #ccc;
	border-radius: 3px;
}

.jewelry_attr_panel .td_right_two icon.removeInput {
	border: 1px solid #fff;
	padding: 0 3px;
	font-size: 12px;
	border-radius: 50%;
	margin-left: 5px;
}

.jewelry_attr_panel .inputSpecialBlock .hide {
	display: none;
}
/* 多填框 */
.jewelry_attr_panel .inputSpecialBlock input.checkboxpicker,
	.jewelry_attr_panel .inputSpecialBlock input.radiopicker {
	margin-right: 5px;
	vertical-align: middle;
}
.jewelry_attr_panel .inputSpecialBlock input[type=text].checkboxpicker,
.jewelry_attr_panel .inputSpecialBlock input[type=text].radiopicker,
.jewelry_attr_panel .inputSpecialBlock input[type=text],
.jewelry_attr_panel .inputSpecialBlock input[type=text] {
	padding: 0!important;
}


.jewelry_attr_panel .td_left.multiFillBlock {
	vertical-align: initial;
}

.jewelry_attr_panel .td_right_two.multiFillBlock {
	padding-top: 0;
}

.jewelry_attr_panel .td_right_two.multiFillBlock .inputSpecialBlock.multiFillBlock
	{
	display: block;
}

/* 只读面板 */
.only_read_panel td.td_right_two span.only_read_span {
	padding: 0 5px;
	border: 1px solid #DDD;
	margin: 0 5px 0 0;
	border-radius: 5px;
	color: #000;
	line-height: 28px;
	/* max-width: 200px; */
	display: inline-block;
	overflow: hidden;
	text-overflow: ellipsis;
	white-space: nowrap;
}
</style>
<script type="text/javascript" src="/include/admin/js/jewelryAttr.js"></script>

<tr id="attrPanelPosition"></tr>
<script>
	$(function(){
		var abc = new initAttrPanel({
			// 展示面板定位容器
			el: $('#attrPanelPosition'),
			// 展示面板插入方式
			appendType: 'before',
			// 只读模式，全部以文本展示
			// onlyRead: '${showModel}',
      // 所有属性的接口地址
      attrUrl: '${attrUrl}'
		});
	});
	</script>


