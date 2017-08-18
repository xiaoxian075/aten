<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="/WEB-INF/common/timepicker.jsp"%>
<style>
 	.ueditor_spaicle img {
 		max-width: 800px;
 		display: block;
 	}
</style>
<div class="table_div">
	<table width="100%">
		<tr>
			<td class="td_left">资讯标题:</td>
			<td class="td_right_two">${news.news_title}</td>
		</tr>
		<tr>
			<td class="td_left">资讯封面:</td>
			<td class="td_right_two"><c:set var="one_img_url"
					value="${news.news_picture}" /> <%@ include
					file="/WEB-INF/common/one_image_view.jsp"%>
			</td>
		</tr>
		<tr>
			<td class="td_left">作者:</td>
			<td class="td_right_two">${news.news_author}</td>
		</tr>
		<tr>
			<td class="td_left">所属分类:</td>
			<td class="td_right_two">${news.the_cat}</td>
		</tr>
		<tr>
			<td class="td_left">资讯内容:</td>
			<td class="td_right_two ueditor_spaicle">${news.news_detail}</td>
		</tr>
		<tr>
			<td class="td_left">创建时间:</td>
			<td class="td_right_two">${news.create_time}</td>
		</tr>
		<tr>
			<td class="td_left">排序:</td>
			<td class="td_right" colspan="3">${news.sort_no}</td>
		</tr>

		<c:if test="${news.news_id!=null}">
			<input type="hidden" name="news_id" value="${news.news_id}" />
		</c:if>

		<%@ include file="/WEB-INF/common/search_hidden_field.jsp"%>
	</table>
</div>
<script type="text/javascript">
    $(function () {
    	// 时间设置
		$('#issue_time').datetimepicker({
			minDate:new Date(),
			format:'Y-m-d H:i:s'
		});
		
		$("#cat_id_div").cascadeSel({
            url:"/admin/newscat/normalList",
            name:"cat_id",
            init_id:"${cfg_news_cat}"
        });
    });
        
    function selectState(obj){
       var data = $(obj).val();
        if(data == 2){
            $('#issue_time').addClass("validate");
        }else{
            $('#issue_time').removeClass("validate");
        }
    }
</script>
