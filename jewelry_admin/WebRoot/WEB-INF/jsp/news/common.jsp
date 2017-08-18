<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="/WEB-INF/common/timepicker.jsp"%>
<div class="table_div">
	<table width="100%">
		<tr>
			<td class="td_left">资讯标题<span class="must_span">*</span></td>
			<td class="td_right_two"><input class="text validate"
				type="text" name="news_title" isrequired='yes' tipmsg="资讯标题"
				maxlength='200' maxdatalength='200' value="${news.news_title}" style="width:400px;"/></td>
		</tr>
		<tr>
			<td class="td_left">资讯封面<span class="must_span">*</span></td>
			<td class="td_right_two">
					<c:set var="one_img_name" value="news_picture" /> 
					<c:set var="one_img_url" value="${news.news_picture}" /> 
					<c:set var="one_img_tip" value="资讯封面" /> 
					<c:set var="one_img_proposal" value="推荐尺寸1080*380,图片大小不能超过2M"/>
					<%@ include file="/WEB-INF/common/one_image_show.jsp"%>
			</td>
		</tr>
		<tr>
			<td class="td_left">作者</td>
			<td class="td_right_two"><input class="text " type="text"
				name="news_author" tipmsg="作者" maxlength='30' maxdatalength='30'
				value="${news.news_author}" /></td>
		</tr>
		<tr>
			<td class="td_left">所属分类<span class="must_span">*</span></td>
			<td class="td_right_two">
				<div id="cat_id_div" tipmsg="所属分类" setwidth="200" setheight="25"></div>
				<input class="validate" isrequired="yes" changetip="cat_id_div"
				type="hidden" id="cat_id" name="the_cat" value="${news.the_cat}" />
			</td>
		</tr>
		<tr>
			<td class="td_left">资讯内容：</td>
			<td class="td_right_two"><textarea type="textarea" tipmsg="资讯内容" id="news_detail_textarea"
					name="news_detail">${news.news_detail}</textarea> <script
					type="text/javascript">
		 			var editor =new UE.ui.Editor();
		 			editor.render("news_detail_textarea"); 
		 			/* UE.getEditor('news_detail_textarea'); */
 			    </script></td>
		</tr>
		<tr>
			<td class="td_left">发布时间<span class="must_span">*</span></td>
			<td class="td_right_two"><label><input id="stateflag"
					name="state" type="radio" value="1"
					<c:if test='${news.state==1}'>checked="checked"</c:if>
					<c:if test='${empty news.state}'>checked="checked"</c:if>
					onchange="selectState(this)" />立即发布 </label><br> <label><input
					name="state" type="radio" value="2"
					<c:if test='${news.state==2}'>checked="checked"</c:if>
					onchange="selectState(this)" />选择时间 </label> <input class="text"
				type="text" id="issue_time" name="issue_time" isrequired='yes'
				tipmsg="发布时间" maxlength='20' maxdatalength='20'
				value="${news.issue_time}" /> <br> <label><input
					name="state" type="radio" value="0"
					<c:if test='${news.state==0}'>checked="checked"</c:if>
					onchange="selectState(this)" />暂不发布 </label></td>
		</tr>
		<tr>
			<td class="td_left">排序<span class="must_span">*</span></td>
			<td class="td_right" colspan="3"><input
				class="text validate sort_no" type="text" name="sort_no"
				isrequired="yes" datatype="jsInt" widthtip="100" tipmsg="排序"
				maxlength='6' maxdatalength='6' value="${news.sort_no}" /></td>
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
