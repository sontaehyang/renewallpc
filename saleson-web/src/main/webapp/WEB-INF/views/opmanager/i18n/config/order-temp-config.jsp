<%@page contentType="text/html;charset=UTF-8"%>
<%@page pageEncoding="UTF-8"%>
<%@ page session="false" %>
<%@ taglib prefix="c" 	uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="op" 	uri="/WEB-INF/tlds/functions" %>
<%@ taglib prefix="fn" 	uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="page" tagdir="/WEB-INF/tags/page" %>


		<div class="location">
			<a href="#"></a> &gt;  <a href="#"></a> &gt; <a href="#" class="on"></a>
		</div>
					
		<form:form modelAttribute="config" method="post">

		<h3><span>임시 주문 정보 설정</span></h3>
		<div class="board_write">
			<table class="board_write_table" summary="임시 주문 정보 설정">
				<caption>임시 주문 정보 설정</caption>
				<colgroup>
					<col style="width:250px;" />
					<col style="" />
				</colgroup>
				<tbody>
					<tr>
					 	<td class="label">임시 주문정보 유지기간 설정</td>
					 	<td>
					 		<div>
					 			<p class="text-info text-sm">* 최대 60일</p>
					 			<form:input path="retentionPeriod" cssClass="form-sm text-center _number" maxlength="2" />일
							</div>
					 	</td>	
					</tr>
				</tbody>					  
			</table>								 				 			
		</div> <!-- // board_write -->

		<!-- 버튼시작 -->		 
		<div class="tex_c mt20">
			<button type="submit" class="btn btn-active"><span>${op:message('M00101')}</span></button>				 
		</div>			 
		<!-- 버튼 끝-->
		
		</form:form>
		
		
<script type="text/javascript">
$(function() {
	// validator
	$('#config').validator(function() {

		var retentionPeriod = Number($('input[name="retentionPeriod"]').val());
		if (retentionPeriod > 60) {
			alert('임시 주문정보는 최대 60일까지 유지할 수 있습니다.');
			$('input[name="retentionPeriod"]').val('60');
			return false;
		}
		
	});
});

</script>	