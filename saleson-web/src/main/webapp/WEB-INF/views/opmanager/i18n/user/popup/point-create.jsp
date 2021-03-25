<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions" %>
	<c:set var="title">${op:message('M00246')}</c:set>
	<c:if test="${pointType == 'shipping'}">
		<c:set var="title">배송비쿠폰</c:set>
	</c:if>
	
	<c:set var="unit">원</c:set>
	<c:if test="${pointType == 'shipping'}">
		<c:set var="unit">장</c:set>
	</c:if>
	
	<!-- 본문 -->
	<div class="popup_wrap">
		<h1 class="popup_title">${title} 지급/차감</h1>
		<form:form modelAttribute="point" id="pointForm" method="post" enctype="multipart/form-data">
			<div class="popup_contents">
				<h2>¶ ${title} 지급/차감</h2>
				<table cellpadding="0" cellspacing="0" summary="" class="board_list_table">
					<caption>${title} 지급/차감</caption>
					<colgroup>
						<col style="width:35%;" />
						<col style="width:*" />
					</colgroup>
					<tbody>
						<tr>
							<th scope="row">대상회원</th>
							<td class="tleft">
								<div>아이디 : ${user.email} (${user.userName})</div>
							</td>
						</tr>
						<tr>
							<th scope="row">적립형태</th>
							<td class="tleft">
								<div>
									<input type="radio" name="mode" id="point_plus" value="1" class="required" checked="checked" /> <label for="point_plus">지급</label>
									<input type="radio" name="mode" id="point_minus" value="2" class="required" /> <label for="point_minus">차감</label>
								</div>
							</td>
						</tr>
						<tr>
							<th scope="row">지급/차감(${unit})</th>
							<td class="tleft">
								<div>
									<input type="text" name="point" class="required _number full" maxlength="8" title="지급/차감 금액" style="width:200px;" /> ${unit}
							 	</div>
							</td>
						</tr>
						<tr>
							<th scope="row">${title} 지급/차감 내용</th>
							<td class="tleft">
								<div class="mt10">
									<textarea name="reason" cols="30" rows="10" title="${title} 지급/차감 내용" class="required" style="width:350px;"></textarea>
								</div>
							</td>
						</tr>
					</tbody>
				</table>
				 
				<p class="popup_btns">
					<button type="submit" class="btn btn-active">저장</button>
					<a href="javascript:self.close();" class="btn btn-default">취소</a>
				</p>	 
			</div><!--//popup_contents E-->
		</form:form>
		<a href="javascript:self.close();" class="popup_close">창 닫기</a>
	</div>
	
<script type="text/javascript">
	$(function() {
		$("#pointForm").validator(function(){
			var point = $("input[name='point']").val();
			if (point.length > 8) {
			    alert("지급/차급 범위를 초과하셨습니다.");
			}

		});
	});
</script>