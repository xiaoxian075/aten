<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
    <title>修改优惠券</title>
</head>
<body>
<form id="validateForm" action="/admin/coupon/update" method="post">
    <div class="opercontent">
        <div class="table_div">
            <table width="100%">
                <tr>
                    <td class="td_left">标题<span class="must_span">*</span></td>
                    <td class="td_right_two">
                        <input  class="text validate"   type="text" name="pageTitle"  tipmsg="标题" value="${info.pageTitle}"/>
                    </td>
                </tr>
                <tr>
                    <td class="td_left">类型<span class="must_span">*</span></td>
                    <td class="td_right_two">
                        <select name="pageType" type="select"  class="validate" isrequired='yes'  tipmsg="类型" >
                            <option value="">请选择</option>
                        </select>
                    </td>
                </tr>
            </table>
        </div>
        <div class="row50 operbtndiv">
            <input type="hidden" name="token" value="${token}">
            <input type="button" value="保存" class="btn operbtn" onclick="submitData();"/>
            <input type="button" class="btn return" onclick="returnGo('/admin/customizedPage/list')" value="返回列表"/>
        </div>
    </div>
</form>
</body>
</html>


