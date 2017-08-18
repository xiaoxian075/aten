<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="/WEB-INF/common/timepicker.jsp"%>
<script type="text/javascript">
          $(function () {
              // 时间设置
              $('#start_date_s,#end_date_s').datetimepicker({
                  lang:'ch',
                  timepicker:true,
                  format:'Y-m-d H:i',
                  formatDate:'Y-m-d H:i',
                  minDate:new Date(),
              });
          });
	  </script>
<script type="text/javascript">
    function goodsSubmitData() {
    	if($("input[name='sale_mode']:checked").val()==1){
    		$("#fixed_price").removeClass("validate");
    	}
    	var check = true;//true
    	check = checkSubmitData();
    	if(!check){
    		return;
    	}
    	
        if(sortPriceArray.length){
          $.each(sortPriceArray, function( index, val ) {
                if(val == 0){
                  alertTip("商品规格金额不能为空");
                  check = false;
                  return;
                }
          });
          if(check != false){
        	  $.each(sortCountArray, function( index, val ) {
                  if(val == 0){
                    alertTip("商品规格数量不能为空");
                    check = false;
                    return;
                  }
              });
          }
		if(check==true){
	  		 var minGoodsPrice = sortPriceArray[0];
             var maxGoodsPrice = sortPriceArray[sortPriceArray.length-1];
             var fixed_goodsPrice = 0;
             var radioDataSale = $('input:radio[name="sale_mode"]:checked').val();
             if(radioDataSale == '0'){
                 fixed_goodsPrice = parseFloat($("input[name='fixed_price_1']").val());
             }else{
                 var radioDataPresale = $('input:radio[name="presale_model"]:checked').val();
                 if(radioDataPresale == '1'){
                     fixed_goodsPrice = parseFloat($("input[name='fixed_price_a']").val());
                 }else{
                     fixed_goodsPrice = parseFloat($("input[name='fixed_price_f']").val());
                 }
             }

             if(minGoodsPrice == maxGoodsPrice){
                 if(fixed_goodsPrice != minGoodsPrice){
              	   check = false;
                     alertTip("商品金额应该是售价:"+minGoodsPrice+"元")
                     return;
                 }
             }else{
                 if(fixed_goodsPrice < minGoodsPrice || fixed_goodsPrice > maxGoodsPrice){
              	   check = false;
                     alertTip("商品价格介于售价:"+minGoodsPrice+"元---"+maxGoodsPrice+"元之间的价格")
                     return ;
                 }
             }
         }
        }else{
        	check = false;
        	alertTip("请选择规格值");
        	return;
        }
        if (check) {
            $("#validateForm").submit();
        };
    }
</script>
<style>
.notEdit{
    width: 100%;
    position: absolute;
    top: 0px;
    bottom: 0px;
    z-index: 9999;
}
</style>
<div class="table_div" style="height: auto;position: relative;">
	<table width="100%">
		<tr>
			<td class="td_left"><b class="line_title">商品所属分类:</b></td>
			<td class="td_right_two">${catName}</td>
			<input type="hidden" name="catName" value="${catName}" />
		</tr>
		<tr>
			<td class="line_title_td"><b class="line_title">商品基本信息</b></td>
		</tr>
		<tr>
			<td class="td_left">商品编码<span class="must_span">*</span></td>
			<td class="td_right_two"><input class="text validate"
				type="text" name="goods_number" isrequired="yes" tipmsg="商品编码" 
				maxlength='60' maxdatalength='60' value="${goods.goods_number}" style="width:200px;"/>
		</tr>
		<tr>
			<td class="td_left">商品名称<span class="must_span">*</span></td>
			<td class="td_right_two"><input class="text validate"
				type="text" name="goods_name" isrequired="yes" tipmsg="商品名称"
				maxlength='60' maxdatalength='60' value="${goods.goods_name}" style="width:400px;"/> <input
				name="cat_id" value="${catId}" type="hidden"></td>
		</tr>
		<tr>
			<td class="td_left">所属品牌<span class="must_span">*</span></td>
			<td class="td_right_two"><select name="brand_id"
				class="validate selectClass" isrequired="yes" type="select"
				tipmsg="所属品牌">
					<c:if test="${empty goods.brand_id}">
                 		<option value="">请选择</option>
                 	</c:if>
					<c:forEach items="${brandList}" var="brandList">
						<option value="${brandList.brand_id}"
							<c:if test="${goods.brand_id==brandList.brand_id}">selected="selected"</c:if>>${brandList.brand_name}</option>
                    </c:forEach>
                </select>
            </td>
        </tr>
        <tr>
            <td class="td_left">所属供应商<span class="must_span">*</span></td>
            <td class="td_right_two">
                <select name="supply_id"  class="validate selectClass" isrequired="yes" type="select" tipmsg="所属供应商">
                    <c:if test="${empty goods.supply_id}">
                 		<option value="">请选择</option>
                 	</c:if>
                    <c:forEach items="${supplyList}" var="supplyList">
                        <option value="${supplyList.supply_id}" <c:if test="${goods.supply_id==supplyList.supply_id}">selected="selected"</c:if>>${supplyList.supply_name}</option>
                   	</c:forEach>
                </select>
            </td>
        </tr>
        <tr>
            <td class="td_left">鉴定机构<span class="must_span">*</span></td>
            <td class="td_right_two">
                <select name="appraisal_id"  class="validate selectClass" isrequired="yes" type="select" tipmsg="鉴定机构">
                    <c:if test="${empty goods.appraisal_id}">
                 		<option value="">请选择</option>
                 	</c:if>
                    <c:forEach items="${appraisalList}" var="appraisalList">
                        <option value="${appraisalList.appraisal_id}" <c:if test="${goods.appraisal_id==appraisalList.appraisal_id}">selected="selected"</c:if>>${appraisalList.appraisal_name}</option>
                    </c:forEach>
                </select>
            </td>
        </tr>
        <tr>
            <td class="td_left">推荐分成收益比例</td>
            <td class="td_right_two">
                <input  style="width: 120px"  class="text " type="text" name="divide_rate" isrequired="yes" tipmsg="推荐分成收益比例"
                       maxlength='60' maxdatalength='60' value="${divide_rate}"/><span>%</span>
            </td>
           <input type="hidden" id="divideRateId"  value="${divide_rate}" />
        </tr>
        <%@ include file="/WEB-INF/jsp/goods/goods.jsp" %>
        <tr>
            <td class="line_title_td"><b class="line_title">商品属性信息</b></td>
        </tr>
       	<c:set var="attrUrl" value="/admin/goods/getAttrByCatId?goods_id=${goods.goods_id}&catId=${catId}"/>
         <%@ include file="/WEB-INF/jsp/goods/jewelryAttr.jsp" %>
        <tr>
            <td class="line_title_td"><b class="line_title">商品规格信息</b></td>
        </tr>
        <%@ include file="/WEB-INF/jsp/goods/attrToPrices.jsp" %>
        <tr>
            <td class="td_left">商品数量<span class="sp_span">:</span></td>
            <td class="td_right_two">
                <input class="text " type="text" name="total_stock" isrequired="yes" tipmsg="商品数量"
                       maxlength='60' maxdatalength='60' value="${goods.total_stock}"/>
            </td>
        </tr>
         <tr>
            <td class="td_left">商品图片<span class="must_span">*</span></td>
            <td class="td_right_two">
                <c:set var="more_img_name" value="show_imgs"/>
                <c:set var="more_img_url" value="${goods.show_imgs}"/>
                <c:set var="more_img_tip" value="请上传至少一张图片!"/>
                <%@ include file="/WEB-INF/common/more_image_show.jsp" %>
            </td>
        </tr> 
        <tr>
            <td class="td_left">商品介绍<span class="must_span"></span></td>
            <td class="td_right_two">
                <div class="ueditor_content">
                    <textarea id="goods_detail" name="goods_detail">${goods.goods_detail}</textarea>
                    <script>
                        var editor = new UE.ui.Editor();
                        UE.getEditor('goods_detail');
                    </script>
                </div>
            </td>
        </tr>
        <tr>
            <td class="line_title_td"><b class="line_title">商品其它信息</b></td>
        </tr>
        <tr>
            <td class="td_left">选择物流模版<span class="must_span">*</span></td>
            <td class="td_right_two">
                <select class="validate selectClass" name="ship_template" isrequired="yes"  type="select" tipmsg="选择物流模版" widthtip="100">
                 	<c:if test="${empty goods.ship_template}">
                 		<option value="">请选择</option>
                 	</c:if>
                    <c:forEach items="${shipTemplateList}" var="shipTemplateList">
                        <option value="${shipTemplateList.ship_id}" <c:if test="${goods.ship_template==shipTemplateList.ship_id}">selected="selected"</c:if>>${shipTemplateList.ship_name}</option>
					</c:forEach>
				</select>
			</td>
		</tr>
		<%-- <tr>
            <td class="td_left">物流体积（m3）</td>
            <td class="td_right_two">
                <input class="text " type="text" name="volume" isrequired="yes" tipmsg="物流体积" value="${goods.volume}"/>
            </td>
        </tr>
        <tr>
            <td class="td_left">物流重量（kg）</td>
            <td class="td_right_two">
                <input class="text " type="text" name="weight" isrequired="yes" tipmsg="物流重量" value="${goods.weight}"/>
            </td>
        </tr> --%>
		<tr>
			<td class="td_left">包装售后<span class="sp_span">:</span></td>
			<td class="td_right_two">
				<div class="ueditor_content">
					<textarea id="customer_service" name="customer_service">${goods.customer_service}</textarea>
					<script>
                        var editor = new UE.ui.Editor();
                        UE.getEditor('customer_service');
                    </script>
				</div>
			</td>
		</tr>
		<%-- <tr>
            <td class="td_left">库存计数<span class="must_span">*</span></td>
            <td class="td_right_two">
                <div id="div_deposit_policy2" tipmsg="库存计数" style="display: inline-block">
                    <c:if test="${!empty stockTypeList}">
                        <c:forEach items="${stockTypeList}" var="item" varStatus="status">
                            <label><input name="stock_type" class="redioType validate" isrequired="yes"
                                          changetip="div_deposit_policy2" value="${item.para_key}" type="radio"
                                          <c:if test="${item.para_key==goods.stock_type}">checked</c:if>
                            />${item.para_name}</label>
                        </c:forEach>
                    </c:if>
                </div>
            </td>
        </tr>  --%>
		<tr>
			<td class="td_left">发布时间<span class="must_span">*</span></td>
			<td class="td_right_two">
				<div id="div_deposit_policy1" tipmsg="发布时间"
					style="display: inline-block">
					<br> <label><input name="state"
						class="redioType validate" isrequired="yes"
						changetip="div_deposit_policy1" value="1" type="radio"
						<c:if test="${goods.state==1}">checked</c:if> />立即发布</label> </br> <br> <label><input
						name="state" class="redioType validate" isrequired="yes"
						changetip="div_deposit_policy1" value="2" type="radio"
						<c:if test="${goods.state==2}">checked</c:if> />选择时间</label> <input
						type="text" id="start_date_s" name="in_date"
						value="${goods.in_date}" />
					<!--  style="display: none;" -->
					</br> <br> <label><input name="state"
						class="redioType validate" isrequired="yes"
						changetip="div_deposit_policy1" value="0" type="radio"
						<c:if test="${goods.state==0}">checked</c:if> />暂不发布</label> </br>
				</div>
			</td>
		</tr>
		<c:if test="${goods.goods_id!=null}">
			<input type="hidden" name="goods_id" value="${goods.goods_id}" />
		</c:if>
		
		<%@ include file="/WEB-INF/common/search_hidden_field.jsp"%>
	</table>
	<div class="notEdit"></div>
</div>
<script>
	$('input[name="state"]').on('change', function () {
		if (this.value == 2) {
			$(this).parent().next().show();
		} else {
			$(this).parents('div').find('input[name=in_date]').hide();
		}
	})
	
	$('input[name="divide_rate"]').blur(function(){
		var divideRate = $('input[name="divide_rate"]').val();
		if (isNaN(divideRate)) {
			alertTip("推荐成分比例输入错误");
			$('input[name="divide_rate"]').val($("#divideRateId").val());
			return false;
		} else {
			if (divideRate < 0) {
				alertTip("推荐成分比例不能为负数");
				$('input[name="divide_rate"]').val($("#divideRateId").val());
				return false;
			}
			if(divideRate>100){
				alertTip("请输入1-100之间的数");
				$('input[name="divide_rate"]').val($("#divideRateId").val());
				return false;
			}
		}
		
	})
</script>