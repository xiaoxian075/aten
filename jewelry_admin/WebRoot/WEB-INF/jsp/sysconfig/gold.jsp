<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<title>黄金设置</title>
</head>
<body>
	<form id="validateForm" action="/admin/sysconfig/updateGold"
		method="post">
		<div class="opercontent">
			<div class="table_div">
				<table width="100%">
					<tr>
						<input type="hidden" name="price_id"
							value="${cfg_gold_price.var_id}">
						<td class="td_left">当日金价<span class="must_span">*</span></td>
						<td class="td_right_two"><input class="text validate"
							type="text" name="price_value" isrequired="yes" tipmsg="当日金价" dataType="jsRmb"
							maxlength="10" maxdatalength="10"
							value="${cfg_gold_price.var_value}" /> 元/克</td>
					</tr>
					<%--<tr>--%>
						<%--<input type="hidden" name="manual_id"--%>
							<%--value="${cfg_gold_manual.var_id}">--%>
						<%--<td class="td_left">手工费<span class="must_span">*</span></td>--%>
						<%--<td class="td_right_two"><input class="text validate"--%>
							<%--type="text" name="manual_value" isrequired="yes" tipmsg="手工费"--%>
							<%--maxlength="30" maxdatalength="30"--%>
							<%--value="${cfg_gold_manual.var_value}" /> 元/克</td>--%>
					<%--</tr>--%>
					<tr>
						<input type="hidden" name="gold_id" value="${cfg_gold_id.var_id}">
						<td class="td_left">关联商品分类<span class="must_span">*</span></td>
						<td class="td_right_two">
							<input class="text validate"
								   type="text" name="gold_value" isrequired="yes" tipmsg="关联商品分类" maxlength="12" maxdatalength="12"
								   value="${cfg_gold_id.var_value}" />
						</td>

                    </td>
                </tr>
            </table>
        </div>
        <div class="row50 operbtndiv">
            <input type="button" value="保存并更新历史数据" class="btn operbtn" onclick="submitData();"/>
            <!-- <input type="button" class="btn operbtn"  onclick="updateHistoryGold();" value="更新历史数据"/> -->
        </div>
    </div>
</form>
</body>
</html>

