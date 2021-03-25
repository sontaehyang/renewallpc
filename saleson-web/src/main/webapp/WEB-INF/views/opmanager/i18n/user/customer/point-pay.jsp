<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions" %>

			<!-- 본문 -->
		<div class="popup_wrap">
			<h1 class="popup_title">${op:message('M00826')}</h1> <!-- 포인트 지급 -->
			<form:form modelAttribute="point" id="point" action="/opmanager/user/point-setting" method="post" >
				<c:forEach items="${userIds}" var="id">
					<input type="hidden" name="userIds" value="${id }">
				</c:forEach>
				<div class="popup_contents">
					<h2>¶ ${op:message('M00826')}</h2> <!-- 포인트 지급 -->
					<table cellpadding="0" cellspacing="0" summary="" class="board_list_table">
						<caption>${op:message('M00246')} 지급</caption>
						<colgroup>
							<col style="width:35%;" />
							<col style="width:*" />
						</colgroup>
						<tbody>
							<tr>
								<th scope="row">선택한 회원수</th> <!-- 아이디 --> <!-- 이메일 -->
								<td class="tleft">
									<c:if test="${not empty userIds}">
										<div>${fn:length(userIds)}명 선택</div>
									</c:if>
								</td>
							</tr>
							<tr>
								<th scope="row">${op:message('M01106')}</th> <!-- 지급 포인트 -->
								<td class="tleft">
									<div>
										<input type="text" name="point" class="required _number full" title="${op:message('M01106')}" style="width:200px;" /> P 
								 	</div>
								</td>
							</tr>
							<tr>
								<th scope="row">${op:message('M01107')}</th> <!-- 포인트 지급 내용 -->
								<td class="tleft">
									<div class="mt10">
										<textarea name="reason" cols="30" rows="10" title="${op:message('M01107')}" style="width:350px;"></textarea>
									</div>
								</td>
							</tr>
						</tbody>
					</table>
					 
					<p class="popup_btns">
						<button type="submit" class="btn btn-active">${op:message('M00101')}</button> <!-- 저장 -->
						<a href="javascript:self.close();" class="btn btn-default">${op:message('M00037')}</a> <!-- 취소 -->
					</p>	 
				</div><!--//popup_contents E-->
			</form:form>
			<a href="javascript:self.close();" class="popup_close">창 닫기</a>
		</div>
		
	<script type="text/javascript">
		$(function(){
			$("#point").validator(function(){
				var message = "${op:message('M00246')}를 지급하시겠습니까?";	// 포인트를 지급하시겠습니까?
				Common.confirm(message, function(){
					$("#point").submit();
				});
				return false;
			});
		});
	</script>