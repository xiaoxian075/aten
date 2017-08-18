<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>代码生成工具</title>
<link rel="stylesheet" type="text/css" href="/include/code/index.css" />
<script type="text/javascript" src="/include/code/jquery-1.7.js"></script>
<script type="text/javascript"
	src="/include/common/js/project.plugin.js"></script>
<script type="text/javascript" src="/include/common/js/validate.js"></script>
<script type="text/javascript" src="/include/code/code.js"></script>
</head>
<body>
	<form id="tbForm" action="" method="post">
		<div class="navbar-top">
			<div class="top">
				<b class="top-left f_left"> 代码生成工具 </b>
			</div>

		</div>
		<div class="db_code">
			<div class="db_msg">
				<span>表名：</span><input id="table_name" type="text" name="table_name"
					value="${table_name}" /> <span>表主键：</span><input id="table_key"
					type="text" name="table_key" value="${table_key}" /> <input
					class="btn select" value="查    询" onclick="selectTb();"
					type="button"> <span id="selError" class="errorSpan"></span>
			</div>

			<div class="db_msg">
				<span>勾选需生成的页面或类：<label><input class="all_file_type"
						type="checkbox" />全选</label>
				</span>&nbsp;&nbsp;&nbsp;&nbsp; <label><input class="file_type"
					name="file_common_page" type="checkbox" />共用页面</label> <label><input
					class="file_type" name="file_insert_page" type="checkbox" />添加页面</label> <label><input
					class="file_type" name="file_update_page" type="checkbox" />修改页面</label> <label><input
					class="file_type" name="file_list_page" type="checkbox" />列表页面</label> <label><input
					class="file_type" name="file_model" type="checkbox" />model实体类</label> <label><input
					class="file_type" name="file_sql" type="checkbox" />sql文件</label> <label><input
					class="file_type" name="file_controller" type="checkbox" />controller</label>
				<label><input class="file_type" name="file_service"
					type="checkbox" />service接口</label> <label><input
					class="file_type" name="file_service_impl" type="checkbox" />service实现类</label>
				<label><input class="file_type" name="file_dao"
					type="checkbox" />dao接口</label>
			</div>

			<div class="db_msg">
				<span>勾选模块方法：<label><input class="all_method_name"
						type="checkbox" />全选</label>
				</span>&nbsp;&nbsp;&nbsp;&nbsp; <label><input class="method_name"
					name="method_list" type="checkbox" />列表</label> <label><input
					class="method_name" name="method_insert" type="checkbox" />添加</label> <label><input
					class="method_name" name="method_update" type="checkbox" />修改</label> <label><input
					class="method_name" name="method_delete" type="checkbox" />单个删除</label> <label><input
					class="method_name" name="method_deleteAll" type="checkbox" />批量删除</label>
				<label><input class="method_name" name="method_sort"
					type="checkbox" />排序</label>
			</div>

			<div class="db_msg">
				<span>列表显示方式：</span> <label><input name="list_style"
					type="radio" value="" checked />table表</label> <label><input
					name="list_style" type="radio" value="" />tree表</label>
			</div>

			<div class="list_div">
				<table id="db_table" class="db_table" border="0" cellspacing="0"
					cellpadding="0">
					<tr>
						<th>行号</th>
						<th>表字段</th>
						<th>字段说明</th>
						<th>字段类型</th>
						<th>验证格式</th>
						<th>是否必填</th>
						<th>字段长度</th>
						<th style="display:none;">提示验证信息</th>
						<th style="display:none;">默认值(格式：key:value|key:value)</th>
						<th>添加/修改字段选择</th>
						<th>列表选择字段显示</th>
						<th>搜索字段选择</th>

					</tr>
					<c:set var="num" value="0" />
					<c:if test="${!empty tbList}">
						<c:forEach items="${tbList}" var="item" varStatus="status">


							<tr>
								<td>${status.count}</td>
								<td
									<c:if test="${item['column_name']==table_key}">class="key"</c:if>>
									${item['column_name']} <input
									name="tableFieldList[${num}].field_name" type="hidden"
									value="${item['column_name']}" />
								</td>
								<td><input name="tableFieldList[${num}].field_note"
									type="text" value="${item.column_comment}" class="field_note" /></td>
								<td><c:if test="${item['column_name']==table_key}">
										<select name="tableFieldList[${num}].field_type"
											class="field_type">
											<option value="tbkey" selected="selected">表主键</option>
										</select>
									</c:if> <c:if test="${item['column_name']!=table_key}">
										<select name="tableFieldList[${num}].field_type"
											class="field_type">
											<option value="text" selected>文本框</option>
											<option value="hidden">隐藏域</option>
											<option value="textarea">文本域</option>
											<option value="select">select框</option>
											<option value="radio" style="display:none;">radio框</option>
											<option value="imgcontrol">图片控件</option>
											<option value="ueditor">编辑器</option>
										</select>
									</c:if></td>
								<td><select name="tableFieldList[${num}].data_type"
									class="data_type">
										<option value="">请选择</option>
										<option value="rmb">金额</option>
										<option value="int">整型</option>
								</select></td>
								<td><c:if test="${item['null']=='N'}">
										<input name="tableFieldList[${num}].is_must" class="is_must"
											type="checkbox" checked="checked" value="yes" />
									</c:if> <c:if test="${item['null']=='Y'}">
										<input name="tableFieldList[${num}].is_must" class="is_must"
											type="checkbox" value="no" />
									</c:if></td>
								<td><input class="field_length"
									name="tableFieldList[${num}].field_length"
									value="${item['data_length']}" style="width:60px;" /></td>
								<td style="display:none;"><input
									name="tableFieldList[${num}].tip_msg" type="text"
									class="tip_msg" value="-" /></td>
								<td style="display:none;"><input
									name="tableFieldList[${num}].default_value"
									class="default_value" type="text" value="" /></td>

								<td><input name="tableFieldList[${num}].add_edit_field"
									class="add_edit_field" type="checkbox" value="yes" checked /> <input
									name="tableFieldList[${num}].add_edit_field_sort"
									class="add_edit_field_sort" type="text" value="0"
									style="width:30px;" /></td>
								<td><input name="tableFieldList[${num}].show_list_field"
									class="show_list_field" type="checkbox" value="yes" checked />
									<input name="tableFieldList[${num}].show_list_field_sort"
									class="show_list_field_sort" type="text" value="0"
									style="width:30px;" /></td>
								<td><input name="tableFieldList[${num}].search_list_field"
									class="search_list_field" type="checkbox" value="yes" checked />
									<input name="tableFieldList[${num}].search_list_field_sort"
									class="search_list_field_sort" type="text" value="0"
									style="width:30px;" /></td>

							</tr>
							<c:set var="num" value="${num+1}" />
						</c:forEach>
					</c:if>
					<c:if test="${empty tbList}">
						<tr>
							<td colspan="12">暂无数据</td>
						</tr>
					</c:if>
				</table>
			</div>

			<div class="db_msg">
				<li style="display:none;"><span>代码生成根路径：</span><input
					type="text" name="project_path" style="width:300px;"
					value="D:\eneon3\jewelry_admin" /></li>
				<li><span>对应的类名：</span><input type="text" name="class_name"
					value="Test" /></li>
				<li><span>模块名称：</span><input type="text" name="model"
					value="测试" /></li>
				<li><span>功能描述：</span><input type="text" name="function"
					style="width:350px;" value="测试类" /> &nbsp;<input class="btn select"
					value="代码生成" onclick="codeBuild();" type="button"></li>
			</div>

		</div>
	</form>
</body>
</html>