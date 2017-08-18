<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<style type="text/css">
	.hp_txt{
		margin-bottom:5px;
	}
</style>

<div id="hotel_policy_div">
		<c:if test="${empty is_update}"><div>
			<span class="ponum">1、</span><input type="text" class="hp_txt validate txtpoi" isrequired='yes'  tipmsg="更新日志" maxlength='120'  maxdatalength="120"/>	   
			</div>
		</c:if>
		<c:if test="${!empty is_update}">
			<c:if test="${!empty policyArray}">
				<c:set var="num" value="0"/>
			     <c:forEach items="${policyArray}" var="pc">
			     	 <c:set var="num" value="${num+1}"/>	
			     	 <div> 
			          	<span class="ponum">${num}、</span>
			          	<span><input type="text" class="hp_txt validate txtpoi" isrequired='yes'
			            	tipmsg="更新日志" maxlength='120' value="${pc}"  maxdatalength="120"/></span>
			            <c:if test="${num!=1}">	
			            	<img style="cursor:pointer" src="/include/admin/image/delete.png" onclick="delpo(this);">
			            </c:if>
			          </div>   
			      </c:forEach>
			</c:if>
		</c:if>
</div>		   

<script type="text/javascript">
	function delpo(obj){
		
		$(obj).parent("div").remove();
		$("#hotel_policy_div").find(".ponum").each(function(i){
			$(this).html(i+1+"、");
		});
	}
	function addPolicy(){
		$this = $("#hotel_policy_div");
		var num = $this.find(".hp_txt").length;
		if(num<8){
			var policyStr="<div><span class='ponum'>"+(num+1)+"、</span>";
			policyStr+="<input type='text' class='hp_txt validate txtpoi' isrequired='yes' "+
			" tipmsg='更新日志' maxlength='120'  maxdatalength='120'/>";
			policyStr+="&nbsp;&nbsp;<img style='cursor:pointer' src='/include/admin/image/delete.png' onclick='delpo(this);'></div>";
			$this.append(policyStr);
		}
	}
</script>

 		   
<input id="update_logs" type="hidden" name="update_logs" value="${version.update_logs}"/>