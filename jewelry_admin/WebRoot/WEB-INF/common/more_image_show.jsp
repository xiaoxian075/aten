<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page import="com.communal.constants.Constant"%>
<c:if test="${empty more_img_num}">
	<c:set var="more_img_num" value="5" />
</c:if>

<!-- 初始个数 -->
<c:forEach var="index" begin="1" end="${more_img_num}" step="1">
	<c:set var="is_flag" value="0" />
	<!-- 默认无回选值 -->
	<c:set var="default_img" value="${noPicture}" />
	<!-- 已选值个数 -->
	<c:if test="${!empty more_img_url}">
		<c:forEach var="imgUrl" items="${fn:split(more_img_url,',')}"
			varStatus="status">
			<c:if test="${index==status.count}">
				<c:set var="is_flag" value="1" />
				<c:set var="default_img" value="${ossImgServerUrl}${imgUrl}" />
				<div class="detail_img_list">
					<img class="img_height120 separate more_detail_image"
						id="img_detail_${index}" src="${default_img}" /> <input
						type="hidden" id="hidden_detail_${index}" class="more_image_list"
						value="${default_img}" />
					<div id="fileQueue_detail_${index}"></div>
					<div id="more_image_div" setwidth="260" setheight="25"
						tipmsg="${more_img_tip}">
						<table>
							<tr>
								<td style="padding-left:4px;">
									<div style="position: relative;" id="file_detail_${index}"></div>
								</td>
								<td style="padding-left:3px;"><a id="cancel_${index}"
									onclick="cancelMoreImg('${index}');"
									class="imgCancel uploadify-cancelbutton"
									href="javascript:void(0)">取消</a></td>
							</tr>
						</table>
					</div>
					<script>image_more_upload("detail_${index}");</script>
				</div>
			</c:if>
		</c:forEach>
	</c:if>
	<!-- 判断是否已回选 -->
	<c:if test="${is_flag==0}">
		<div class="detail_img_list">
			<img class="img_height120 separate more_detail_image"
				id="img_detail_${index}" src="${default_img}" /> <input
				type="hidden" id="hidden_detail_${index}" class="more_image_list"
				value="${default_img}" />
			<div id="more_image_div" setwidth="260" setheight="25"
				tipmsg="${more_img_tip}">
				<table>
					<tr>
						<td style="padding-left:4px;"><div style="position: relative;"
								id="file_detail_${index}"></div></td>
						<td style="padding-left:3px;"><a id="cancel_${index}"
							onclick="cancelMoreImg('${index}');"
							class="imgCancel uploadify-cancelbutton"
							href="javascript:void(0)">取消</a></td>
					</tr>
				</table>
			</div>
			<script>image_more_upload("detail_${index}");</script>
		</div>
	</c:if>
</c:forEach>
<input class="text validate" isrequired="yes" maxdatalength="300"
	changetip="more_image_div" type="hidden" id="more_image"
	name="${more_img_name}" value="${more_img_url}" />
	
<div class="clear" style="padding-top:5px;color:#585858;">
<img style="width:22px;height:20px;" src="/include/admin/image/tip.png"/>
<b>${more_img_proposal}</b>
</div>