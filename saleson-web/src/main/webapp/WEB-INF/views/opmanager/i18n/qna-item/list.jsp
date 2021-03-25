<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" 	uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="op" 	uri="/WEB-INF/tlds/functions" %>
<%@ taglib prefix="fn" 	uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="page" tagdir="/WEB-INF/tags/page" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="module" 	tagdir="/WEB-INF/tags/modules" %>
<%@ taglib prefix="shop"	uri="/WEB-INF/tlds/shop" %>

	<div class="location">
		<a href="#"></a>&gt;  <a href="#"></a> &gt; <a href="#" class="on"></a>
	</div>

	<h3>상품문의</h3>
	<form:form modelAttribute="qna" method="get" enctype="multipart/form-data">
		<div class="board_write">
			<table class="board_write_table" summary="${op:message('M00459')}">
				<caption>${op:message('M00458')}</caption>
				<colgroup>
					<col style="width:150px;" />
					<col style="width:*;" />
				</colgroup>

				<tbody>

				<tr>
					<div>
						<td class="label">${op:message('M00460')}</td> <!-- 문의유형 -->
						<td>
							<div>
								<form:select path="qnaGroup" class="required form-control03">
									<form:option value="" label="전체" />
									<c:forEach items="${qnaGroups}" var="group">
										<form:option value="${group.id}" label="${group.label}" />
									</c:forEach>
								</form:select>
							</div>
						</td>
					</div>
				</tr>

				<tr>
					<div>
						<td class="label">답변상태</td>
						<td>
							<div>
								<form:radiobutton path="answerCount" value="0" checked="checked" label="${op:message('M00039')}" />
								<form:radiobutton path="answerCount" value="1" label="${op:message('M00463')}" />
								<form:radiobutton path="answerCount" value="2" label="${op:message('M00464')}" />
							</div>
						</td>
					</div>
				</tr>
				<tr>
					<td class="label">${op:message('M01630')}</td> <!-- 판매자명 -->
					<td>
						<div>
							<select id="sellerId" name="sellerId" title="${op:message('M01630')}"> <!-- 판매자선택 -->
								<option value="0">${op:message('M00039')}</option> <!-- 전체 -->
								<c:forEach items="${sellerList}" var="list" varStatus="i">
									<c:choose>
										<c:when test="${qna.sellerId == list.sellerId}">
											<c:set var='selected' value='selected'/>
										</c:when>
										<c:otherwise>
											<c:set var='selected' value=''/>
										</c:otherwise>
									</c:choose>
									<option value="${list.sellerId}" ${selected}>${list.sellerName}</option>
								</c:forEach>
							</select>
							<a href="javascript:Common.popup('/opmanager/seller/find?statusCode=2', 'find_seller', 800, 500, 1)" class="btn btn-dark-gray btn-sm"><span class="glyphicon glyphicon-search"></span> 검색</a>
						</div>
					</td>
				</tr>
				<tr>
					<div>
						<td class="label">${op:message('M00276')}</td> <!-- 작성일 -->
						<td>
							<div>
								<span class="datepicker"><form:input type="text" path="searchStartDate" class="datepicker _number" title="${op:message('M00507')}" /></span> <!-- 시작일 -->
								<span class="wave">~</span>
								<span class="datepicker"><form:input type="text" path="searchEndDate" class="datepicker _number" title="${op:message('M00509')}" /></span> <!-- 종료일 -->
								<span class="day_btns">
									<a href="javascript:;" class="table_btn today">${op:message('M00026')}</a>   <!-- 오늘 -->
									<a href="javascript:;" class="table_btn week-1">${op:message('M00027')}</a> <!-- 1주일 -->
									<a href="javascript:;" class="table_btn month-1">${op:message('M00029')}</a>  <!-- 한달 -->
									<a href="javascript:;" class="table_btn month-3">${op:message('M00030')}</a>  <!-- 3달 -->
									<a href="javascript:;" class="table_btn clear">${op:message('M00039')}</a> <!-- 전체 -->
								</span>
							</div>
						</td>
					</div>
				</tr>

				<tr>
					<td class="label">키워드검색</td> <!-- 검색구분 -->
					<td>
						<div>
							<form:select path="where" title="${op:message('M00468')}"> <!-- 키워드선택 -->
								<form:option value="ITEM_NAME" label="상품명" /> <!-- 상품명 -->
								<form:option value="EMAIL" label="${op:message('M00081')}" /> <!-- 아이디 -->
								<form:option value="USER_NAME" label="${op:message('M00005')}" /> <!-- 이름 -->
								<!-- 2014.12.28 -->
								<form:option value="SUBJECT" label="제목" /> <!-- 제목 -->
								<form:option value="QUESTION" label="내용" /> <!-- 문의내용 -->
							</form:select>
							<form:input type="text" path="query" class="three" title="${op:message('M00021')}" /> <!-- 검색어 입력 -->
						</div>
					</td>
				</tr>
				</tbody>
			</table>
		</div> <!-- // board_write -->

		<!-- 버튼시작 -->
		<div class="btn_all">
			<div class="btn_left">
				<button type="button" class="btn btn-dark-gray btn-sm" onclick="location.href='/opmanager/qna-item/list'"><span class="glyphicon glyphicon-repeat"></span> ${op:message('M00047')}</button> <!-- 초기화 -->
			</div>
			<div class="btn_right">
				<button type="submit" class="btn btn-dark-gray btn-sm"><span class="glyphicon glyphicon-search"></span> ${op:message('M00048')}</button> <!-- 검색 -->
			</div>
		</div>
		<!-- 버튼 끝-->
		<div class="count_title mt20">
			<h5>
				전체 : ${itemListCount} 건 조회
			</h5>
			<span>출력수
				<form:select path="itemsPerPage" title="${op:message('M00239')}"> <!-- 화면출력 -->
					<form:option value="10" label="${op:message('M00240')}" />  <!-- 10개 출력 -->
					<form:option value="20" label="${op:message('M00241')}" />  <!-- 20개 출력 -->
					<form:option value="50" label="${op:message('M00242')}" />  <!-- 50개 출력 -->
					<form:option value="100" label="${op:message('M00243')}" /> <!-- 100개 출력 -->
				</form:select>
			</span>
		</div>
	</form:form>

	<!-- 2015.1.6 -->
	<form action="/opmanager/qna-item/list" method="post" id="listForm">
		<div class="board_write">
			<table class="board_list_table" summary="${op:message('M00273')}"> <!-- 주문내역 리스트 -->
				<caption>${op:message('M00273')}</caption>
				<colgroup>
					<col style="width:30px;" />
					<col style="width:30px;" />
					<col style="width:150px;" />
					<%--<col style="width:50px;" />	--%>
					<%--<col style="width:120px;" />					--%>
					<%--<col style="width:110px;" />--%>
					<col style="width:auto;" />
					<col style="width:160px;" />
					<col style="width:100px;" />
					<col style="width:130px;" />
					<col style="width:100px;" />
					<col style="width:auto;" />
				</colgroup>
				<thead>
				<tr>
					<th scope="col"><input type="checkbox" title="${op:message('M00169')}" /></th> <!-- 체크박스 -->
					<th scope="col">${op:message('M00200')}</th> <!-- 순번 -->
					<th scope="col">문의유형</th>	<!-- 문의유형 -->
					<%--<th scope="col">${op:message('M00659')}</th> <!-- 상품이미지 --> 					--%>
					<%--<th scope="col">${op:message('M00019')}</th> <!-- 상품번호 --> --%>
					<%--<th scope="col">상품명</th> <!-- 상품명 --> --%>
					<th scope="col">제목</th> <!-- 제목 -->
					<th scope="col">작성자/아이디</th> <!-- 작성자/아이디 -->
					<th scope="col">${op:message('M01630')}</th> <!-- 판매자 -->
					<th scope="col">작성일</th> <!-- 작성일 -->
					<th scope="col">답변상태</th> <!-- 답변상태 -->
				</tr>
				</thead>
				<tbody>
				<c:forEach items="${itemQnaLists}" var="item" varStatus="i">
					<tr>
						<td>
							<input type="checkbox" name="id" value="${item.qnaId}" title=${op:message('M00169')} />
						</td>
						<td>${pagination.itemNumber - i.count}</td> <!-- 순번 -->
						<td>
							${item.qnaGroup}
						</td>
						<td class="tex_l">
							<a href="javascript:qnaDetail('${item.qnaId}');">
								<c:if test="${!empty item.itemUserCode}">
									[${item.itemUserCode}]<br/>
								</c:if>
								${item.subject}
							</a> <!-- 제목 -->
						</td>
						<td>
							<c:choose>
								<c:when test="${item.userId > 0}"> <!-- 이름/이메일 -->
									<a href="javascript:Common.popup('/opmanager/user/popup/details/${item.userId}', '/user/popup/details', 1100, 800 ,1, 0, 0)">${item.userName}(${item.email})</a>
								</c:when>
								<c:otherwise> -	</c:otherwise>
							</c:choose>
						</td>	<!-- 작성자 아이디 -->
						<td>${item.sellerName}</td> <!-- 작성일 -->
						<td>${op:date(item.createdDate)}</td> <!-- 작성일 -->
						<td>${item.answerCount > 0 ? op:message('M00463') : op:message('M00464')}</td> <!-- 답변완료 --> <!-- 답변대기 -->
					</tr>
				</c:forEach>
				</tbody>
			</table>
		</div><!--// board_write E-->

		<c:if test="${empty itemQnaLists}">
			<div class="no_content">
					${op:message('M00473')} <!-- 데이터가 없습니다. -->
			</div>
		</c:if>

		<div class="btn_all">
			<div class="btn_left">
				<a id="delete_list_data" href="#" class="btn btn-default btn-sm"><span>선택 삭제</span></a>
			</div>
		</div>

		<page:pagination-manager /><br/>
	</form>
	<br/>

	<div style="display: none;">
		<span id="today">${today}</span>
		<span id="week">${week}</span>
		<span id="month1">${month1}</span>
		<span id="month3">${month3}</span>
	</div>

	<iframe id="downloadFrame" name="downloadFrame" style="display: none;"></iframe>

<script type="text/javascript">
    var $qnaTypeOptions;
    $(function() {
        $qnaTypeOptions = $('<select />').append($('#qnaType option').clone());
        //목록데이터 - 삭제처리
        $('#delete_list_data').on('click', function() {
            Common.updateListData("/opmanager/qna-item/delete", "삭제된 게시글을 복구할 수 없습니다. 정말로 삭제하시겠습니까?");// 선택된 데이터를 삭제하시겠습니까?

        });

        $('#itemsPerPage').on("change", function(){
            $('#qna').submit();
        });

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

                $("input[type=text]", $(this).parent().parent()).eq(0).val(date1);
                $("input[type=text]", $(this).parent().parent()).eq(1).val(date2);

            }
        });

        $('#qnaGroup').on('change', function() {
            var type = $(this).val();
            $('#qnaType').find('option').not(':first-child').remove();
            $option = $qnaTypeOptions.find('.qna_type_' + type).show().clone();
            $('#qnaType').append($option).val("0");
        });
    });

    //엑셀 다운로드 팝업.
    function downloadExcel() {
        if (confirm("엑셀파일로 다운로드 받으시겠습니까?")) {
            $('#listForm').submit();

            // 다운로드 체크.
            $.cookie('DOWNLOAD_STATUS', 'in progress', {path:'/'});
            checkDownloadStatus();
        } else {
            return false;
        }
    }

    //다운로드 체크
    function checkDownloadStatus() {
        if ($.cookie('DOWNLOAD_STATUS') == 'complete') {
            Common.loading.hide();
            return;
        } else {
            setTimeout("checkDownloadStatus()", 1000);
        }
    }

    function qnaDetail(qnaId) {
        $("#qna").attr("action", "/opmanager/qna-item/view/"+qnaId).submit();
    }

    //판매자명 검색기능 추가 2017-03-10 yulsun.yoo
    function sellerSeller(sellerId) {
        $('#sellerId').val(sellerId);
    }
</script>

<script type="text/javascript">
    $(function() {
        Common.DateButtonEvent.set('.day_btns > a[class^=table_btn]', '', 'input[name="searchStartDate"]' , 'input[name="searchEndDate"]');
    });
</script>