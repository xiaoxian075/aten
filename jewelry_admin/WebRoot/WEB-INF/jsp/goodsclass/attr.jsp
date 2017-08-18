<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<style type="text/css">
.option_attr_panel {
	
}

.must_attr_panel label.checked {
	padding: 0 10px 0 5px;
	margin: 0 5px 5px 0;
	background: #9d0697;
	border-radius: 5px;
	color: #fff;
	cursor: pointer;
	line-height: 28px;
	max-width: 100px;
	display: inline-block;
	overflow: hidden;
	text-overflow: ellipsis;
	white-space: nowrap;
}

.must_attr_panel label.uncheck {
	padding: 0 10px 0 5px;
	margin: 0 5px 5px 0;
	background: #DDD;
	border-radius: 5px;
	color: #000;
	cursor: pointer;
	line-height: 28px;
	max-width: 100px;
	display: inline-block;
	overflow: hidden;
	text-overflow: ellipsis;
	white-space: nowrap;
}

.must_attr_panel label input.checkboxpicker, .must_attr_panel label input.radiopicker
	{
	margin-right: 5px;
	vertical-align: middle;
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

/* 查询面板样式 */
.option_search_panel_content {
	line-height: 30px;
}

.option_search_panel_content .searchInput {
	border-radius: 3px;
	width: 60%;
	border: 1px solid #3FA4ED;
	line-height: 25px;
	height: 25px;
	text-indent: 10px;
	margin-left: 10px;
	background-image: url('/include/admin/image/search_icon.png');
	background-size: 20px 20px;
	background-repeat: no-repeat;
	background-position: center right;
}

.option_search_panel_content .searchBtn {
	border-radius: 3px;
	border: 1px solid #3FA4ED;
	line-height: 25px;
	margin-left: 10px;
	outline: none;
	cursor: pointer;
	background-color: #3FA4ED;
}

/* 表格面板样式 */
.option_table_panel_content {
	margin: 10px auto;
	text-align: center;
}

.option_table_panel_content table {
	border-collapse: collapse;
}

.option_table_panel_content th, .option_table_panel_content td {
	border-color: #999;
	padding: 5px 0;
}

.option_table_panel_content tr.hasBeChecked {
	color: #999;
}

/* 分页面板样式 */
.option_page_panel_content {
	text-align: center;
	padding: 10px 0;
}

.pagination_content {
	display: inline-block;
	margin: 0 auto;
	text-align: center;
	line-height: 20px;
	text-align: center;
}

.pagination_content span {
	display: inline-block;
	color: #3FA4ED;
	padding: 0 10px;
	height: 20px;
	cursor: pointer;
	border: 1px solid #DDD;
	border-radius: 3px;
	margin: 0 5px;
}

.pagination_content span.page_first, .pagination_content span.page_last
	{
	/* display: none; */
	
}

.pagination_content span.page_prev, .pagination_content span.page_next {
	display: none;
}

.pagination_content span.is_active {
	color: #999;
	background: #eee;
}
</style>
<script type="text/javascript" src="/include/admin/js/hotelAttr.js"></script>

<tr id="attrPanelPosition"></tr>
<tr>
	<td class="td_left"><c:if test="${empty showModel}">
			<input type="button" value="新增${attrTypeName}属性"
				class="btn ol_greenbtn attrbtn addHotelAttr" id="openOptionPanel" />
		</c:if></td>
	<td class="td_right_two"></td>
</tr>
<script>
	$(function(){
		var abc = new initAttrPanel({
			// 展示面板定位容器
			el: $('#attrPanelPosition'),
			// 展示面板插入方式
			appendType: 'before',
			// 自定义提示文本内容
			attrTypeName: '${attrTypeName}',
			// 可选面板显隐绑定事件容器(弹出框触发容器)
			btnEl: $('#openOptionPanel'),
			// 只读模式，全部以文本展示
			onlyRead: '${showModel}',
			// 所有属性的接口地址
			attrUrl: '${attrUrl}',
			// 自由属性的接口地址(选中后的值)
			customUrl: $('#option_id').length && $('#option_id').val() ? '${customAttrUrl}?id=' + $('#option_id').val() : ''
		});
	});
	</script>


