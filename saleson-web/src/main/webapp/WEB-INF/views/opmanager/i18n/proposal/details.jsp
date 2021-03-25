<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" 		uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" 	uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions"%>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page"%>
<%@ taglib prefix="module" 	tagdir="/WEB-INF/tags/modules"%>

	<c:choose>
		<c:when test="${proposal.statusCode == '1'}">
			<c:set var="statusIcon" value="white orange" />
			<c:set var="statusAlt" value="신청중" />
		</c:when>
		<c:when test="${proposal.statusCode == '8'}">
			<c:set var="statusIcon" value="blue" />
			<c:set var="statusAlt" value="확인중" />
		</c:when>
		<c:when test="${proposal.statusCode == '7'}">
			<c:set var="statusIcon" value="purple" />
			<c:set var="statusAlt" value="반송" />
		</c:when>
		<c:when test="${proposal.statusCode == '9' && proposal.activeStatusCode == '1'}">
			<c:set var="statusIcon" value="white" />
			<c:set var="statusAlt" value="진행예정" />
		</c:when>
		<c:when test="${proposal.statusCode == '9' && proposal.activeStatusCode == '2'}">
			<c:set var="statusIcon" value="yellow" />
			<c:set var="statusAlt" value="진행중" />
		</c:when>
		<c:when test="${proposal.statusCode == '9' && proposal.activeStatusCode == '3'}">
			<c:set var="statusIcon" value="gray" />
			<c:set var="statusAlt" value="마감" />
		</c:when>
	</c:choose>
	
	
		<p class="guide">NGO단체에서 등록한 기부 사연을 관리합니다. 신청 건에 대해서 내용을 검토 후 확인 처리하실 수 있습니다.</p>
		<h3><span>기부사연 등록</span></h3>
		
			
		<div class="board_view">
			<div class="title_wrap">
				<h4 class="title">${ proposal.title }</h4>
				<p class="info info_tooltip">
					<span class="state">상태 : <span class="icon ${ statusIcon }">${ statusAlt }</span></span>
				</p>
				<c:if test="${proposal.statusCode == '7'}">
					<div class="tooltip_wrap reject_cause">
						<a href="#" class="icon_reject tooltip">반송</a>
						<div class="tooltip_area">
							<div>
								<p>반송사유</p>
								<p>${proposal.approvalRemark}</p>
								<a href="#" class="close"><img src="/content/images/common/icon_close2.png" alt="툴팁 닫기" /></a> 
							</div>
						</div>
					</div> 
				</c:if>
			</div>
			<div class="info">
				<p class="detail">
					<span>${ proposal.userName }</span>
					<span>목표금액 :  <fmt:formatNumber value="${ proposal.targetAmount }" pattern="#,###" /> 원</span>
					<span>모금기간 : ${ op:formatDate(proposal.startDate,'-') } ~ ${ op:formatDate(proposal.endDate,'-') }</span>
					<span>등록일 : ${ fn:substring(proposal.creationDate, 0, 10)}</span> 
				</p>
			</div>
			<div class="text">
				<c:forEach items="${ uploadFiles }" var="uploadFile" varStatus="i">
					<p class="img">
						<img src="/upload/proposal/${ proposal.proposalId }/${ uploadFile.fileId }.${ uploadFile.fileType}">
					</p>
					<p class="hidden">
						${uploadFile.fileDescription}
					</p>
				</c:forEach>
			</div>
			<p class="btns">
			
				<span class="right">
					
					<c:if test="${proposalApprovalInfo.approvalButtonView}">
						<a href="javascript:accept('${proposal.proposalId}')" class="btn green"><img src="/content/opmanager/images/icon/icon_check.png" alt="" /> ${proposalApprovalInfo.approvalButtonText}</a>
						<a href="javascript:reject('${proposal.proposalId}')" class="btn purple"><img src="/content/opmanager/images/icon/icon_info2.png" alt="" /> 반송</a>
					</c:if>
			
					<a href="javascript:list('${requestContext.prevPageUrl}')" class="btn gray">목록</a>
					
				</span>
			</p>
		</div>					
					
					
<script type="text/javascript">
$(function(){
	$("#submitForm").on('click',function(){
		if (confirm("삭제하시겠습니까?")) {
		}else{  return false; }
	});
});

function list(previousUrl) {
	if (previousUrl == '') {
		location.href = '/ngomanager/proposal/list';
	} 
	
	location.href = url(previousUrl);
	
}	
</script>	

<script type="text/javascript">
function accept(proposalId) {
	var statusCode = "${proposal.statusCode}";
	var approvalStatusCode = "${proposalApprovalInfo.approvalStatusCode}";
	
	var approvalMessage = "";
	
	if (statusCode == '1') {
		if (approvalStatusCode == "8") {
			approvalMessage = '확인요청 하시겠습니까?';
		} else if (approvalStatusCode == "9") {
			approvalMessage = '확인처리 하시겠습니까?';
		}
	} else if (statusCode == '8') {
		approvalMessage = '${proposal.updatedUsername}님의 확인요청 건\n확인처리 하시겠습니까?';
	}
	
	
	Common.confirm(approvalMessage, function() {
		$.post(url("/opmanager/proposal/accept-proposal"), {'proposalId' : proposalId}, function(response) {
			Common.responseHandler(response, function() {
				alert('정상적으로 처리 되었습니다. ');
				location.reload();
			});
		});
	});
}

function reject(proposalId) {
	Common.popup(url("/opmanager/proposal/reject-proposal?proposalId=" + proposalId), 'reject', 450, 300);
}

$(function(){
	$('.board_view .icon_reject').click(function(){
		$(this).next('.tooltip_area').toggle();
		return false;
	});
	$('.tooltip_area .close').click(function(){
		$(this).closest('.tooltip_area').toggle();
		return false;
	});
});

</script>
