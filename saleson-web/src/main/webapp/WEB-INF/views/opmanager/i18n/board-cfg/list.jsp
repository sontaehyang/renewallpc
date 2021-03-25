<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<h3><span>게시판 설정</span></h3>
<form:form modelAttribute="searchParam" cssClass="opmanager-search-form clear" method="post">
	<fieldset>
		<legend class="hidden">게시판 설정</legend>
		<form:hidden path="itemsPerPage"/>
		<div class="board_write">
			<table class="board_write_table">
				<colgroup>
					<col style="width: 150px;" />
					<col style="width: auto;" />
					<col style="width: 150px;" />
					<col style="width: auto;" />
				</colgroup>
				<tbody>
					<tr>
						<td class="label">${op:message('M00011')}</td>
						<td colspan="3">
							<div>
								<form:select path="where" title="${op:message('M00211')} ">
									<form:option value="SUBJECT">게시판명</form:option>
									<form:option value="BOARD_CODE">게시판 코드</form:option>
								</form:select>
								<form:input path="query" cssClass="optional seven _filter" title="${op:message('M00211')} " />
						 	</div>
						</td> 
					</tr>
				</tbody> 
			</table>
		</div> <!--// board_write E-->
	
		<div class="btn_all">
			<div class="btn_left">
				<button type="button" class="btn btn-dark-gray btn-sm" onclick="location.href='/opmanager/board-cfg/list';"><span>${op:message('M00047')}</span></button> <!-- 초기화 -->
			</div>
			<div class="btn_right">
				<button type="submit" class="btn btn-dark-gray btn-sm"><span>${op:message('M00048')}</span></button>
			</div>
		</div>	<!--// btn_all E-->
	</fieldset>
</form:form>

<div class="count_title mt20">
	<h5>
		전체 : ${op:numberFormat(totalCount)}개 │
	</h5>	 
	<span>
		<select name="displayCount" id="displayCount" title="${op:message('M00239')} ">
			<option value="10">${op:message('M00240')}</option>
			<option value="20">${op:message('M00241')}</option>
			<option value="50">${op:message('M00242')}</option>
			<option value="100">${op:message('M00243')}</option>
		</select>
	</span>
</div>
		

<div class="board_write">
	<form id="listForm">
		<table class="board_list_table">
			<colgroup>
				<col style="width:60px" />
				<col style="width:140px" />
				<col style="" />
				<col style="width:90px" />
				<col style="width:90px" />
				<col style="width:90px" />
				<col style="width:90px" />
				<col style="width:90px" />
				<col style="width:120px" />
				<col style="width:130px" />
				<col style="width:140px" />			
				<col style="width:130px" />
				<col style="width:140px" />
			</colgroup>
			<thead>
				<tr>
					<th scope="col">순번</th>
					<th scope="col">게시판 코드</th>
					<th scope="col">게시판명</th>
					<!--
					<th scope="col">목록 보기 권한</th>
					<th scope="col">내용 보기 권한</th>
					<th scope="col">글쓰기 권한</th>
					-->
					<th scope="col">접근권한</th>
					<th scope="col">댓글권한</th>
					<th scope="col">읽기권한</th>
					<th scope="col">쓰기권한</th>
					<th scope="col">댓글기능</th>
					<th scope="col">페이지당 게시글 수</th>
					<th scope="col">NEW 아이콘 일 수</th>
					<th scope="col">HOT 아이콘 표시 조회수</th>
					<th scope="col">게시판 생성일</th>
					<th scope="col">수정/삭제</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${list}" var="board" varStatus="i">
					<tr>
						<td>
							${pagination.itemNumber - i.count}
						</td>
						<td>
							${board.boardCode}
						</td>
						<td>
							<a href="/opmanager/board-cfg/edit/${board.boardCode}?url=${requestContext.currentUrl}">${board.subject}</a>
						</td>
						<td>
							<c:choose>
								<c:when test="${board.listAuthority == 'ANONYMOUS'}">
									일반
								</c:when>
								<c:when test="${board.listAuthority == 'ROLE_USER'}">
									회원
								</c:when>
								<c:when test="${board.listAuthority == 'ROLE_OPMANAGER'}">
									관리자
								</c:when>
							</c:choose>
						</td>
						<td>
							<c:choose>
								<c:when test="${board.replyAuthority == 'ANONYMOUS'}">
									일반
								</c:when>
								<c:when test="${board.replyAuthority == 'ROLE_USER'}">
									회원
								</c:when>
								<c:when test="${board.replyAuthority == 'ROLE_OPMANAGER'}">
									관리자
								</c:when>
							</c:choose>
						</td>
						<td>
							<c:choose>
								<c:when test="${board.readAuthority == 'ANONYMOUS'}">
									일반
								</c:when>
								<c:when test="${board.readAuthority == 'ROLE_USER'}">
									회원
								</c:when>
								<c:when test="${board.readAuthority == 'ROLE_OPMANAGER'}">
									관리자
								</c:when>
							</c:choose>
						</td>
						<td>
							<c:choose>
								<c:when test="${board.writeAuthority == 'ANONYMOUS'}">
									일반
								</c:when>
								<c:when test="${board.writeAuthority == 'ROLE_USER'}">
									회원
								</c:when>
								<c:when test="${board.writeAuthority == 'ROLE_OPMANAGER'}">
									관리자
								</c:when>
							</c:choose>
						</td>
						<td>
							<c:choose>
								<c:when test="${board.useComment == '0'}">
									미사용
								</c:when>
								<c:when test="${board.useComment == '1'}">
									사용
								</c:when>
							</c:choose>
						</td>
						<td>
							${board.pageSize}개
						</td>
						<td>
							${board.showNewIcon}일
						</td>
						<td>
							<fmt:formatNumber value="${board.showHotIcon}"/>회
						</td>
						<td>
							${op:date(board.creationDate)} 
						</td>
						<td>
							<a href="/opmanager/board-cfg/edit/${board.boardCode}?url=${requestContext.currentUrl}" class="btn btn-gradient btn-xs">수정</a>
							<a href="javascript:deleteCheck('${board.boardCode}')" class="btn btn-gradient btn-xs">삭제</a>
						</td>
				</c:forEach>
			</tbody>
		</table>			
	</form>
	 
	<c:if test="${empty list}">
		<div class="no_content">
			설정된 게시판이 없습니다.
		</div>
	</c:if>
</div><!--//board_write E-->

<div class="btn_all">
	<div class="btn_right">
		<a href="/opmanager/board-cfg/create?url=${requestContext.currentUrl}" class="btn btn-active btn-sm">게시판 등록</a>
	</div>
</div>	<!--// btn_all E-->	
	
<page:pagination-manager />

<div style="display: none;">
	<span id="today">${today}</span>
	<span id="week">${week}</span>
	<span id="month1">${month1}</span>
	<span id="month2">${month2}</span>
</div>
	
<script type="text/javascript">
$(function() {
	$('#searchParam').validator(function(selector) {});
	serachDate();
	displayChange();
	displaySelected();
});

/**
 * 조회 기간 설정
 * @return
 */
function serachDate()
{
	$(".btn_date").on('click',function(){

		var $id = $(this).attr('class').replace('btn_date ','');		// id[0] : type, id[1] : value

		if ($id == 'all') {
			
			$("input[type=text]",$(this).parent().parent()).val('');
			
		} else {

			var today = $("#today").text();

			var date1 = '';
			var date2 = '';

			if ($id == 'today') {
				date1 = today;
				date2 = today;
			} else {
				date1 = $("#"+$id).text();
				date2 = today;
			}

			$("input[type=text]",$(this).parent().parent()).eq(0).val(date1);
			$("input[type=text]",$(this).parent().parent()).eq(1).val(date2);
		}
	});
}

function displayChange() {
	$("#displayCount").on('change', function(){
		$("#itemsPerPage").val($(this).val());
		$('#searchParam').submit();
	});
}

function displaySelected(){
	$("#displayCount").val($("#itemsPerPage").val());
}

function deleteCheck(boardCode) {
	if(confirm('삭제 하시겠습니까?')) {
		location.replace("/opmanager/board-cfg/delete/" + boardCode);  
	} else {
		return;
	}
}
</script>