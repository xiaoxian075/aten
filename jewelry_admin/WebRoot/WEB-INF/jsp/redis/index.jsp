<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>更新redis缓存</title>
<style type="text/css">
.redis_ul {
	margin: 10px 0px 0px 10px;
}

.redis_li {
	height: 50px;
	border: 1px solid #e1e2e3;
	font-size: 16pt;
	font-weight: 600;
	text-align: center;
	padding: 25px 25px 0px 25px;
	cursor: pointer;
	background: #F0F0F0;
	float: left;
	margin-right: 10px;
	margin-bottom: 10px;
}

.redis_li:hover {
	color: #FFA042;
}
</style>
</head>
<body>
	<form id="validateForm" action="" method="post">
		<div class="list_div">
			<table id="list_table">
				<tr>
					<th>缓存管理</th>
					<th>操作</th>
				</tr>	
				<tr>
					<td>系统导航管理</td>
					<td>
						<ul class="redis_ul">
							<!-- <li class="redis_li" onclick="goInfo('/admin/redis/validate');">更新验证缓存</li> -->
							<li class="redis_li" onclick="goInfo('/admin/redis/nav');">更新系统导航缓存</li>
						</ul>
					</td>
				</tr>
				<tr>
					<td>地区管理</td>
					<td>
						<ul class="redis_ul">
							<li class="redis_li" onclick="goInfo('/admin/redis/area');">更新地区缓存</li>
							<li class="redis_li" onclick="goInfo('/admin/redis/levelArea');">更新级联地区缓存</li>
						</ul>
					</td>
				</tr>
				<tr>
					<td>分类管理</td>
					<td>
						<ul class="redis_ul">
							<li class="redis_li" onclick="goInfo('/admin/redis/cat');">更新分类缓存</li>
						</ul>
					</td>
				</tr>
				<tr style="display:none;" >
					<td>商品管理</td>
					<td>
						<ul class="redis_ul">
							<li class="redis_li" onclick="goInfo('/admin/redis/attr');">更新商品属性缓存</li>
							<li class="redis_li"  onclick="goInfo('/admin/redis/attrValue');">更新商品属性值缓存</li>
						</ul>
					</td>
				</tr>
				<tr>
					<td>数据字典管理</td>
					<td>
						<ul class="redis_ul">
							<li class="redis_li" onclick="goInfo('/admin/redis/commparaList');">更新数据字典列表缓存</li>
							<li class="redis_li" onclick="goInfo('/admin/redis/commpara');">更新数据字典键值缓存</li>
						</ul>
					</td>
				</tr>
				<tr>
					<td>系统设置管理</td>
					<td>
						<ul class="redis_ul">
							<li class="redis_li" onclick="goInfo('/admin/redis/sysconfig');">更新系统设置缓存</li>
						</ul>
					</td>
				</tr>
				<tr>
					<td>前台分类管理</td>
					<td>
						<ul class="redis_ul">
							<li class="redis_li" onclick="goInfo('/admin/redis/precatStr');">更新前台分类缓存</li>
							<li class="redis_li" onclick="goInfo('/admin/redis/precatList');">更新前台分类列表缓存</li>
						</ul>
					</td>
				</tr>
				<tr style="display:none;">
					<td>索引管理</td>
					<td>
						<ul class="redis_ul">
							<li class="redis_li" onclick="goInfo('/admin/redis/deleteIndex');">删除索引</li>
							<li class="redis_li" onclick="goInfo('/admin/redis/catAttrIndex');">更新分类属性索引</li>
							<li class="redis_li" onclick="goInfo('/admin/redis/goodsIndex');">更新商品索引</li>
						</ul>
					</td>
				</tr>
			</table>
		</div>
	</form>
</body>
</html>
