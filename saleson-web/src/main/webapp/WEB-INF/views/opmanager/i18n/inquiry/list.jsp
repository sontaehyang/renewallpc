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

<h3>1:1 문의</h3>
<form:form modelAttribute="inquiryParam" method="get">
    <div class="board_write">
        <table class="board_write_table">
            <caption>${op:message('M00458')}</caption>
            <colgroup>
                <col style="width:150px;" />
                <col style="width:*;" />
            </colgroup>
            <tbody>
            <tr>
                <td class="label">답변상태</td>
                <td>
                    <div>
                        <form:radiobutton path="answerFlag" value="3" checked="checked" label="${op:message('M00039')}" />
                        <form:radiobutton path="answerFlag" value="1" label="${op:message('M00463')}" />
                        <form:radiobutton path="answerFlag" value="0" label="${op:message('M00464')}" />
                    </div>
                </td>
            </tr>

            <tr>
                <td class="label">문의 유형</td>
                <td>
                    <div>
                        <form:select path="inquiryType">
                            <form:option value="">전체</form:option>
                            <form:option value="item">상품 문의</form:option>
                            <form:option value="order">주문 및 배송문의</form:option>
                            <form:option value="custom">팀/단체 및 커스텀 문의</form:option>
                            <form:option value="etc">기타</form:option>
                        </form:select>
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
											<a href="#" class="btn_date today">${op:message('M00026')}</a>
											<a href="#" class="btn_date week">${op:message('M00222')}</a>
											<a href="#" class="btn_date month1">${op:message('M00029')}</a>
											<a href="#" class="btn_date month3">세달</a>
											<a href="#" class="btn_date all">${op:message('M00039')}</a>
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
                            <form:option value="userEmail" label="이메일" /> <!-- 아이디 -->
                            <form:option value="userName" label="${op:message('M00005')}" /> <!-- 이름 -->
                            <!-- 2014.12.28 -->
                            <form:option value="inquirySubject" label="제목" /> <!-- 제목 -->
                            <form:option value="inquiryContent" label="내용" /> <!-- 문의내용 -->
                            <form:option value="telNumber" label="연락처" /> <!-- 연락처 -->
                        </form:select>
                        <form:input type="text" path="query" class="three" title="${op:message('M00021')} " />  <!-- 검색어 입력 -->
                    </div>
                </td>
            </tr>
            </tbody>
        </table>
    </div> <!-- // board_write -->

    <!-- 버튼시작 -->
    <div class="btn_all">
        <div class="btn_left">
            <button type="button" class="btn btn-dark-gray btn-sm" onclick="location.href='/opmanager/inquiry/list'"><span>${op:message('M00047')}</span></button> <!-- 초기화 -->
        </div>
        <div class="btn_right">
            <button type="submit" class="btn btn-dark-gray btn-sm"><span>${op:message('M00048')}</span></button> <!-- 검색 -->
        </div>
    </div>
    <!-- 버튼 끝-->
    <div class="count_title mt20">
        <h5>전체 : ${inquiryCount} 건 조회</h5>
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
<form action="/opmanager/inquiry/list" method="get" id="listForm">
    <input type="hidden" value="${list.qnaId}">
    <div class="board_write">
        <table class="board_list_table" summary="${op:message('M00273')}"> <!-- 주문내역 리스트 -->
            <caption>${op:message('M00273')}</caption>
            <colgroup>
                <col style="width:3%;" />
                <col style="width:3%;" />
                <col style="width:8%;" />
                <col style="width:10%;" />
                <col style="width:20%;" />
                <col style="width:25%;" />
                <col style="width:10%;" />
                <col style="width:10%;" />
                <col style="width:10%;"/>
            </colgroup>
            <thead>
            <tr>
                <th scope="col"><input id="allCheck" type="checkbox" title="${op:message('M00169')}" /></th> <!-- 체크박스 -->
                <th scope="col">${op:message('M00200')}</th> <!-- 순번 -->
                <th scope="col">회원 구분</th>
                <th scope="col">문의 유형</th> <!-- 문의 유형 -->
                <th scope="col">상품명</th>  <!-- 작성자/이메일 -->
                <th scope="col">내용</th> <!-- 제목 -->
                <th scope="col">연락처</th> <!-- 연락처 -->
                <th scope="col">작성일</th> <!-- 작성일 -->
                <th scope="col">답변상태</th> <!-- 답변상태 -->
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${inquiryList}" var="list" varStatus="i">
                <tr>
                    <td>
                        <input type="checkbox" id="inquiryIds" name="inquiryIds" value="${list.inquiryId}" title=${op:message('M00169')} />
                    </td>
                    <td>${pagination.itemNumber - i.count}</td>
                    <td>
                        <c:choose>
                            <c:when test="${list.userEmail != null && list.userEmail != '' }">
                                회원
                            </c:when>
                            <c:otherwise>
                                비회원
                            </c:otherwise>
                        </c:choose>
                    </td>
                    <td>
                        <c:choose>
                            <c:when test="${list.inquiryType == 'etc'}">
                                기타
                            </c:when>
                            <c:when test="${list.inquiryType == 'item'}">
                                상품 문의
                            </c:when>
                            <c:when test="${list.inquiryType == 'order'}">
                                주문 및 배송 문의
                            </c:when>
                            <c:when test="${list.inquiryType == 'custom'}">
                                팀 단체 및 커스텀 문의
                            </c:when>
                            <c:otherwise> - </c:otherwise>
                        </c:choose>
                    </td>
                    <td>
                        <c:choose>
                            <c:when test="${list.itemCode != null && list.itemCode != ''}">
                                <a href="javascript:window.open('/products/view/${list.itemCode} ', 'newWindow');">${list.itemName } (${list.itemCode})</a>
                            </c:when>
                            <c:otherwise> - </c:otherwise>
                        </c:choose>
                    </td>
                    <td style="text-align:center;">
                        <a href="view/${list.inquiryId }">${op:strcut(list.inquiryContent, 20) }</a> <!-- 내용 -->
                    </td>
                    <td>
                            ${list.telNumber }
                    </td>
                    <td>${op:date(list.createdDate)}</td>
                    <td>${list.answerFlag == 1 ? op:message('M00463') : op:message('M00464')}</td> <!-- 답변완료 --> <!-- 답변대기 -->

                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div><!--// board_write E-->

    <c:if test="${empty inquiryList}">
        <div class="no_content">
                ${op:message('M00473')} <!-- 데이터가 없습니다. -->
        </div>
    </c:if>

    <div class="btn_all">
        <div class="btn_left">
            <a href="javascript:deleteInquiry();" class="btn btn-default btn-sm">
                <span>일괄 삭제</span>
            </a>
        </div>
    </div>

    <page:pagination-manager /><br/>
</form><br/>
<span style="color:red;">
			* 상품명 클릭 시 해당 상품 페이지로 이동 됩니다. <br/>
			* 내용 클릭 시 답변 페이지로 이동 됩니다.
		</span>


<div style="display: none;">
    <span id="today">${today}</span>
    <span id="week">${week}</span>
    <span id="month1">${month1}</span>
    <span id="month3">${month3}</span>
</div>

<script type="text/javascript">

    $(function() {

        searchDate();

    });

    /**
     * 조회 기간 설정
     * @return
     */
    function searchDate()
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

    function deleteInquiry() {
        if (confirm("삭제시 복구 되지 않습니다. 문의를 삭제하시겠습니까?")) {

            var inquiryIds = [];
            $('input:checkbox[name="inquiryIds"]:checked').each(function(i) {
                inquiryIds[i] = $(this).val();
            });

            location.href = '/opmanager/inquiry/delete?inquiryIds='+inquiryIds;
        }
    }

    $("#allCheck").on('click', function(){

        if($(this).prop("checked")) {
            $('input:checkbox[name="inquiryIds"]').prop("checked",true);
        } else {
            $('input:checkbox[name="inquiryIds"]').prop("checked",false);
        }

    });

</script>
