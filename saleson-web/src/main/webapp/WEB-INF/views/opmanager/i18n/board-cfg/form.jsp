<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions" %>
<%@ taglib prefix="daum"	tagdir="/WEB-INF/tags/daum" %>

<style>
input[type=text].full {
	width: 100%;
}
</style>

<h3><span>게시판 설정</span></h3>

<form:form modelAttribute="boardCfg" method="post">
	<form:hidden path="groupCode" value="hsp" />
	<form:hidden path="language" value="ko" />
	<form:hidden path="boardTemplate" value="front" />
	<form:hidden path="boardLayout" value="community" />
	<form:hidden path="boardSkin" value="image-list" />
	<form:hidden path="useCategory" value="0" />
	<form:hidden path="useNotice" value="0" />
	<form:hidden path="useReply" value="0" />
	<form:hidden path="useSearch" value="0" />
	<form:hidden path="blockSize" value="10" />
	<div class="board_write">
		<table class="board_write_table" summary="게시판설정에 관련한 정보를 입력하는 칸입니다.">
			<caption>게시판설정</caption>
			<colgroup>
				<col style="width:15%;" />
				<col style="width:35%;" />
				<col style="width:15%;" />
				<col style="width:35%;" />
			</colgroup>
			<tbody>
			<tr>
				<td class="label">코드 <span class="require">*</span></td>
		  		<td colspan="3">
		  			<div>
		  				<c:if test="${empty boardCfg.boardCode}">
		  					<input type="hidden" id="availabilitySuccessBoardCode" />
		  					<input type="hidden" name="type" value="create" />
		  					<form:input path="boardCode" title="게시판 코드" class="required" maxlength="20" style="ime-mode:disabled" onkeyup="this.value=this.value.replace(/[^a-zA-Z]/g,'')" />
							<button type="button" onclick="checkBoard()" id="boardCheckBtn" class="btn btn-dark-gray btn-sm">중복검사</button>
							<input type="hidden" name="boardCheck" id="boardCheck" value="0" />
		  				</c:if>
		  				<c:if test="${!empty boardCfg.boardCode}">
		  					${boardCfg.boardCode}
		  					<form:hidden path="boardCode" />
		  					<input type="hidden" name="type" value="edit" />
		  				</c:if>
		  			</div>
		  		</td>
			</tr>
			<!--  -->
			<tr>
				<td class="label">게시판명 <span class="require">*</span></td>
		  		<td colspan="3">
		  			<div>
		  				<form:input path="subject" title="게시판명" class="required full" maxlength="240" />
		  			</div>
		  		</td>
			</tr>
			
			<%-- 
			<tr>
				<td class="label">게시판 상단 컨텐츠 <span class="require">*</span></td>
		  		<td colspan="3">
		  			<div>
		  				<form:textarea path="boardHeader" title="게시판 상단 컨텐츠" maxlength="1900" />
		  			</div>
		  		</td>
			</tr>
			--%>
			<tr>
				<td class="label">게시판 하단 컨텐츠 <span class="require">*</span></td>
		  		<td colspan="3">
		  			<div>
		  				<form:textarea path="boardFooter" title="게시판 하단 컨텐츠" maxlength="1900" />
		  			</div>
		  		</td>
			</tr>
			<tr>
				<td class="label">접근 권한 <span class="require">*</span></td>
		  		<td>
		  			<div>
		  				<form:select path="listAuthority" title="접근 권한">
		  					<form:option label="일반" value="ANONYMOUS" />
		  					<form:option label="회원" value="ROLE_USER" />
		  					<form:option label="관리자" value="ROLE_OPMANAGER" />
		  				</form:select>
		  			</div>
		  		</td>
		  		<td class="label">댓글 권한<span class="require">*</span></td>
				<td colspan="3">
		  			<div>
		  				<form:select path="replyAuthority" title="쓰기 권한">
		  					<form:option label="일반" value="ANONYMOUS" />
		  					<form:option label="회원" value="ROLE_USER" />
		  					<form:option label="관리자" value="ROLE_OPMANAGER" />
		  				</form:select>
		  			</div>
		  		</td>
			</tr>
			<tr>
				<td class="label">읽기 권한<span class="require">*</span></td>
				<td>
		  			<div>
		  				<form:select path="readAuthority" title="읽기 권한">
		  					<form:option label="일반" value="ANONYMOUS" />
		  					<form:option label="회원" value="ROLE_USER" />
		  					<form:option label="관리자" value="ROLE_OPMANAGER" />
		  				</form:select>
		  			</div>
		  		</td>
		  		<td class="label">쓰기 권한<span class="require">*</span></td>
				<td>
		  			<div>
		  				<form:select path="writeAuthority" title="쓰기 권한">
		  					<form:option label="일반" value="ANONYMOUS" />
		  					<form:option label="회원" value="ROLE_USER" />
		  					<form:option label="관리자" value="ROLE_OPMANAGER" />
		  				</form:select>
		  			</div>
		  		</td>
			</tr>
			<tr>
				<td class="label">제목 길이 <span class="require">*</span></td>
		  		<td>
		  			<div>
		  				<form:input path="subjectLength" title="제목 길이" class="required _number" maxlength="2" />
		  			</div>
		  		</td>
				<td class="label">내용 길이 <span class="require">*</span></td>
		  		<td>
		  			<div>
		  				<form:input path="contentLength" title="내용 길이" class="required _number" maxlength="3" />
		  			</div>
		  		</td>
			</tr>
			<tr>
				<td class="label">NEW 아이콘 표시 일 수 <span class="require">*</span></td>
		  		<td>
		  			<div>
		  				<form:input path="showNewIcon" title="NEW 아이콘 표시 일 수" class="required _number" maxlength="3" />
		  			</div>
		  		</td>
				<td class="label">HOT 아이콘 표시 조회수 <span class="require">*</span></td>
		  		<td>
		  			<div>
		  				<form:input path="showHotIcon" title="HOT 아이콘 표시 일 수" class="required _number" maxlength="10" />
		  			</div>
		  		</td>
			</tr>
			<tr>
				<td class="label">첨부파일 개수 <span class="require">*</span></td>
		  		<td>
		  			<div>
		  				<form:select path="uploadCount" title="첨부파일 개수">
		  					<c:forEach begin="1" end="5" step="1" var="count">
		  						<form:option value="${count}" label="${count}개" />
		  					</c:forEach>
		  				</form:select>
		  			</div>
		  		</td>
				<td class="label">첨부파일 제한 용량(MB) <span class="require">*</span></td>
		  		<td>
		  			<div>
		  				<form:select path="uploadSize" title="첨부파일 제한 용량">
		  					<c:forEach begin="1" end="10" step="1" var="size">
		  						<form:option value="${size}" label="${size}MB" />
		  					</c:forEach>
		  				</form:select>
		  			</div>
		  		</td>
			</tr>
			<tr>
				<td class="label">페이지당 게시글 수 <span class="require">*</span></td>
		  		<td>
		  			<div>
		  				<form:input path="pageSize" title="게시글 수" class="required _number" maxlength="2" />
		  			</div>
		  		</td>
		  		<td class="label">댓글 기능 <span class="require">*</span></td>
		  		<td>
		  			<div>
		  				<form:select path="useComment" title="댓글 기능">
		  					<form:option label="미사용" value="0" />
		  					<form:option label="사용" value="1" />
		  				</form:select>
		  			</div>
		  		</td>
			</tr>
			<tr>
				<td class="label">첨부파일 허용 확장자 <span class="require">*</span></td>
		  		<td colspan="3">
		  			<div>
		  				<p>
		  					<form:radiobutton path="uploadExtension" label="*.jpg;*.jpeg;*.gif;*.bmp;*.png" value="0" checked="true" />
		  				</p>
		  				<p>
		  					<form:radiobutton path="uploadExtension" label="*.doc;*.docx;*.xls;*xlsx;*.ppt;*.pptx;*.pdf;*.tif;*.tiff;*.hwp;*.zip" value="1" />
		  				</p>
		  				<p>
		  					<form:radiobutton path="uploadExtension" label="*.jpg;*.jpeg;*.gif;*.bmp;*.png;*.doc;*.docx;*.xls;*xlsx;*.ppt;*.pptx;*.pdf;*.tif;*.tiff;*.hwp;*.zip" value="2" />
		  				</p>
		  			</div>
		  		</td>
			</tr>
			</tbody>
		</table>						 
	</div> <!--// board_write E-->
	<div class="btn_center_end">
		<button type="submit" class="btn btn-active">저장</button>
		<button type="button" onclick="javascript:Link.list('${requestContext.prevPageUrl}')" class="btn btn-default">목록</button>	
	</div>
</form:form>

<script type="text/javascript">
var boardCodeCheck = '';
$(function() {
	$('#boardCfg').validator(function() {
		if ($("#boardCode").val() != '') {
			if($('input[name=type]').val() == 'create' && ($("#boardCheck").val() == 0 || boardCodeCheck != $("#boardCode").val())) {
				alert("게시판 코드 중복검사를 해주세요.");
				$("#boardCode").focus();
				return false;
			}
			
			if ($('#pageSize').val() < 1 || $('#pageSize').val() > 99) {
				alert('게시글 수 항목의 값을 1이상 100이하에 맞게 입력하세요.');
				return false;
			}

			if (!confirm('설정 하시겠습니까?')) {
				return false;
			}	
		}
	});
});

/* boardCode 중복 체크 */
function checkBoard() {
	var type = $("input[name=type]").val();
	var value = $("#boardCode").val();

	if (value != '') {
		var params = {
			'type' : type,
			'value' : value
		};
		
		$.post("/opmanager/board-cfg/user-availability-check-boardCode", params, function(response){
			Common.responseHandler(response, function(response) {
				$("#boardCheck").val('1');
				alert("사용 가능한 게시판 코드입니다.");
				boardCodeCheck = value;
			});
		}, 'json').error(function(e){
			alert(e.message);
		});
	} else {
		alert("게시판 코드 항목을 입력해 주세요.");
		return false;
	}
}
</script>