<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() +  path+"/";
%>
<!DOCTYPE html>
<html>
<style>
    html { overflow-x:hidden; }
</style>
<jsp:include page="/WEB-INF/views/admin/common/head.jsp"/>
<body style="background-color: #fff;">
<div class="breadcrumbs" id="breadcrumbs" style="margin-top:5px;">
    <ul class="breadcrumb">
        <li>
            <span class="glyphicon glyphicon-home"></span>
            <a href="#" target="main" style="padding-left:5px;margin-left:5px;">首页</a>
        </li>
        <li>
            <a href="#">商户管理</a>
        </li>
        <li class="active">商户列表</li>
    </ul>
</div>
<div class="row" style="overflow-y:auto;">
    <div class="col-xs-12">
        <div class="box">
            <div class="box-body">
                <form class="form-horizontal" role="form">
                    <div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">订单编号</label>
                            <div class="col-sm-3">
                                <input type="text" value="${detail.orderNumber}"  class="form-control input-sm" disabled="disabled">
                            </div>
                            <label class="col-sm-2 control-label">下单时间</label>
                            <div class="col-sm-3">
                                <input type="text" value="${detail.createTime }"  class="form-control input-sm" disabled="disabled">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">商户号</label>
                            <div class="col-sm-3">
                                <input type="text" value="${detail.lklMerchantCode}"  class="form-control input-sm" disabled="disabled">
                            </div>
                            <label class="col-sm-2 control-label">终端号</label>
                            <div class="col-sm-3">
                                <input type="text" value="${detail.lklTerminalCode }"  class="form-control input-sm" disabled="disabled">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">商户名称</label>
                            <div class="col-sm-3">
                                <input type="text" value="${detail.merchantName }"  class="form-control input-sm" disabled="disabled">
                            </div>
                            <label class="col-sm-2 control-label">商户云账号</label>
                            <div class="col-sm-3">
                                <input type="text" value="${detail.merchantYunId }"  class="form-control input-sm" disabled="disabled">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">付款方云账号</label>
                            <div class="col-sm-3">
                                <input type="text"  value="${detail.yunId }"  class="form-control input-sm" disabled="disabled">
                            </div>
                            <label class="col-sm-2 control-label">付款金额</label>
                            <div class="col-sm-3">
                                <input type="text"  value="${detail.totalFee }"  class="form-control input-sm" disabled="disabled">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">交易卡号</label>
                            <div class="col-sm-3">
                                <input type="text"  value="${detail.cardNo}"  class="form-control input-sm" disabled="disabled">
                            </div>
                            <label class="col-sm-2 control-label">订单类型</label>
                            <div class="col-sm-3">
                                <input type="text"  value="${detail.orderType }"  class="form-control input-sm" disabled="disabled">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">支付状态</label>
                            <div class="col-sm-3">
                                <input type="text"  value="${detail.payState}"  class="form-control input-sm" disabled="disabled">
                            </div>
                            <label class="col-sm-2 control-label">支付时间</label>
                            <div class="col-sm-3">
                                <input type="text"  value="${detail.payTime }"  class="form-control input-sm" disabled="disabled">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">支付方式</label>
                            <div class="col-sm-3">
                                <input type="text"  value="${detail.payType}"  class="form-control input-sm" disabled="disabled">
                            </div>
                            <label class="col-sm-2 control-label">拉卡拉交易类型</label>
                            <div class="col-sm-3">
                                <input type="text"  value="${detail.tradeType}"  class="form-control input-sm" disabled="disabled">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">拉卡拉参考号</label>
                            <div class="col-sm-3">
                                <input type="text"  value="${detail.refernumber}"  class="form-control input-sm" disabled="disabled">
                            </div>
                            <label class="col-sm-2 control-label">拉卡拉凭证号</label>
                            <div class="col-sm-3">
                                <input type="text"  value="${detail.systraceno }"  class="form-control input-sm" disabled="disabled">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">拉卡拉批次号</label>
                            <div class="col-sm-3">
                                <input type="text"  value="${detail.batchbillno}"  class="form-control input-sm" disabled="disabled">
                            </div>
                            <label class="col-sm-2 control-label">手续费</label>
                            <div class="col-sm-3">
                                <input type="text"  value="${detail.counterFee }"  class="form-control input-sm" disabled="disabled">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">商户代理人</label>
                            <div class="col-sm-3">
                                <input type="text"  value="${detail.realName}"  class="form-control input-sm" disabled="disabled">
                            </div>
                        </div>
                        <div class="form-group">
                            <div class="col-sm-offset-4 col-sm-3">
                                <button type="button" class="btn btn-default" style="background-color:#f4f4f4;" type="button" value="返回列表" onclick="history.go(-1)" >返回列表</button>
                            </div>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
</body>
</html>
<script type="text/javascript">
</script>
