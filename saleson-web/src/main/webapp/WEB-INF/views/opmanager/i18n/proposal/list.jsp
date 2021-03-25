<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" 		uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" 	uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions"%>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page"%>
<%@ taglib prefix="module" 	tagdir="/WEB-INF/tags/modules"%>



			<p class="guide">NGO단체에서 등록한 기부 사연을 관리합니다. 신청 건에 대해서 내용을 검토 후 확인 처리하실 수 있습니다.</p>
			
			<h3><span>기부사연</span></h3>
			<div class="donation_list">
				<div class="sort_area">
					<form:form modelAttribute="proposalSearchParam" action="/opmanager/proposal/list" method="post" class="board_search">
						<fieldset>
							<legend class="hidden">검색</legend>
							<div class="left">
								<span>
									<form:radiobutton path="statusCode" value="1" label="신청중" />
									<form:radiobutton path="statusCode" value="8" label="확인중" />
									<form:radiobutton path="statusCode" value="" label="전체"  />
								</span>
								
								<span>|</span>
								
								<span>기간</span>
								<span class="datepicker"><form:input path="startDate" maxlength="8" class="term optional _date" title="등록일 검색 시작일" /></span>
								<span class="wave">~</span>
								<span class="datepicker"><form:input path="endDate" maxlength="8" class="term optional _date" title="등록일 검색 종료일" /></span>
								
							</div>
							
							<div class="right">
								<form:select path="where" title="검색조건">
									<form:option value="TITLE">제목</form:option>
									<form:option value="CONTENT">제목+내용</form:option>
								</form:select>
								<form:input path="query" class="input_txt optional _filter"  title="검색" />
								<button type="submit"><span class="icon_search">검색</span></button>
							</div>
						</fieldset>
					</form:form>
				</div>
				<ul class="list">
					<c:forEach items="${ list }" var="list" varStatus="i">
						<c:choose>
							<c:when test="${list.statusCode == '1'}">
								<c:set var="statusIcon" value="white" />
								<c:set var="statusAlt" value="신청중" />
							</c:when>
							<c:when test="${list.statusCode == '8'}">
								<c:set var="statusIcon" value="blue" />
								<c:set var="statusAlt" value="확인중" />
							</c:when>
							<c:when test="${list.statusCode == '7'}">
								<c:set var="statusIcon" value="purple" />
								<c:set var="statusAlt" value="반송" />
							</c:when>
							<c:when test="${list.statusCode == '9' && list.activeStatusCode == '1'}">
								<c:set var="statusIcon" value="white" />
								<c:set var="statusAlt" value="진행예정" />
							</c:when>
							<c:when test="${list.statusCode == '9' && list.activeStatusCode == '2'}">
								<c:set var="statusIcon" value="yellow" />
								<c:set var="statusAlt" value="진행중" />
							</c:when>
							<c:when test="${list.statusCode == '9' && list.activeStatusCode == '3'}">
								<c:set var="statusIcon" value="gray" />
								<c:set var="statusAlt" value="마감" />
							</c:when>
						</c:choose>
						
						<li>
							<a href="<c:url value="/opmanager/proposal/details/${ list.proposalId }?url=${requestContext.currentUrl}"/>">
								<c:forEach items="${ list.proposalFiles }" var="file" varStatus="i">
									<c:if test="${!empty file.fileName && file.fileName != null}">
										<img src="<c:url value="/upload/proposal/${ list.proposalId }/thumb/${ list.thumNamilSize }_${ file.fileId }.${ file.fileType}"/>" alt="${ file.fileDescription }" />
									</c:if>
								</c:forEach>
								<c:if test="${empty list.proposalFiles }">
									<img src="<c:url value="/content/images/common/thumb_donation.png"/>" alt="" />
								</c:if>
							</a>
							<p class="writer">${ list.userName }</p>
							<div class="tooltip_wrap reject_cause">
								<span class="icon image ${ statusIcon } tooltip" tabindex="0">${ statusAlt }</span>
								<c:if test="${list.statusCode == '7'}">
									<div class="tooltip_area">
										<div>
											<p>반송사유</p>
											<p>${list.approvalRemark}</p>
										</div>
									</div>
								</c:if>
							</div>
							<p class="period">${op:formatDate(list.startDate,'-') } ~ ${op:formatDate(list.endDate,'-')}</p>
							<p class="title"><a href="<c:url value="/opmanager/proposal/details/${ list.proposalId }?url=${requestContext.currentUrl}"/>">${ list.title }</a></p>
							<p class="goal">
								<span>목표 모금액</span>
								<span class="amount"><em><fmt:formatNumber value="${ list.targetAmount }" pattern="#,###" /></em>원</span>
							</p>
						</li>
					</c:forEach>
				</ul>
				<c:if test="${ empty list }">
					<div class="no_content">
						<p>등록된 내용이 없습니다.</p>
					</div>
				</c:if>
				<div class="board_guide">
					<p class="total">전체 : <em>${op:numberFormat(pagination.totalItems)}</em></p>
				</div>
				<page:pagination-manager/>
				<p class="btns">
					<a href="javascript:fn_donationExcelDownload();" class="btn orange">엑셀파일 다운로드</a>
				</p>
			</div>
			
<script type="text/javascript">
	
	$(function() {
		EventHandler.calendarStartDateAndEndDateVaild();
		$('input[name=statusCode]').on("click", function() {
			$('#proposalSearchParam').submit();
		});
	});	

	function fn_donationExcelDownload(){
		location.href = "/opmanager/proposal/proposal-download-excel?"+$("#proposalSearchParam").serialize();
	}
	$(function(){
		EventHandler.calendarStartDateAndEndDateVaild();
	});

	$("#proposalSearchParam").validator(function() {
	});
</script>
