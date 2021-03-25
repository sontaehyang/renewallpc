<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions" %>
<%@ taglib prefix="shop"	uri="/WEB-INF/tlds/shop" %>

<p class="text-info text-sm">
	* 아래 정보는 리뉴올PC에 저장된 템플릿 정보 입니다.
	<br/>
	* 템플릿 등록 및 수정은 '알림톡 설정' 에서 하시면 됩니다.
</p>
<br/>
<div>
	<button type="button" class="btn btn-default btn-sm" id="showAlimTalkTemplate">알림톡 템플릿 조회</button>
</div>
<br/>

<table class="board_write_table">

	<colgroup>
		<col style="width: 150px;" />
		<col style="width: auto;" />
	</colgroup>
	<tbody>
		<tr>
			<td class="label">승인 코드</td>
			<td>
				<div>
					${ums.detailList[detailListIndex].applyCode}
				</div>
			</td>
		</tr>
		<tr>
			<td class="label">템플릿 제목</td>
			<td>
				<div>
					${ums.detailList[detailListIndex].title}
				</div>
			</td>
		</tr>
		<tr>
			<td class="label">발송 문구</td>
			<td>
				<div>
					${op:nl2br(ums.detailList[detailListIndex].message)}
				</div>
			</td>
		</tr>
		<tr>
			<td class="label">버튼</td>
			<td>
				<c:set var="templateButtons" value="${alimTalkButtons}" scope="request"/>
				<c:set var="detailListIndex" value="${detailListIndex}" scope="request"/>
				<c:set var="templateButtonTypeList" value="${templateButtonTypeList}" scope="request"/>

				<jsp:include page="alim-talk-template-button.jsp"/>
			</td>
		</tr>
	</tbody>
</table>
