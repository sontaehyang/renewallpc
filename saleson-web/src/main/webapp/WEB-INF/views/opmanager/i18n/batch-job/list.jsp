<%@ page language="java" contentType="text/html; charset=utf-8"	pageEncoding="utf-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="page" tagdir="/WEB-INF/tags/page"%>
<%@ taglib prefix="op" uri="/WEB-INF/tlds/functions"%>
<%@ taglib prefix="page" tagdir="/WEB-INF/tags/page" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="module" 	tagdir="/WEB-INF/tags/modules" %>
<%@ taglib prefix="shop"	uri="/WEB-INF/tlds/shop" %>

<div class="location">
    <a href="#"></a> &gt;  <a href="#"></a> &gt; <a href="#" class="on"></a>
</div>

<form:form modelAttribute="batchJobParam" method="get">
    <h3><span>Batch Job</span></h3>
    <div class="board_write">
        <table class="board_write_table" summary="Batch Job">
            <colgroup>
                <col style="width:150px;" />
                <col style="width:400px;" />
                <col style="width:150px;" />
                <col style="width:*;" />
                <col style="width:150px;" />
                <col style="width:*;" />
            </colgroup>
            <tbody>
            <tr>
                <td class="label">작업 검색</td> <!-- 작업 검색 -->
                <td>
                    <div>
                        <form:select path="searchType" title="작업 검색" style="width:80px;"> <!-- 작업 검색 -->
                            <form:option value="ID">메서드명</form:option> <!-- 메서드명 -->
                            <form:option value="LABEL">작업명</form:option> <!-- 작업명 -->
                        </form:select>
                        <form:input type="text" path="query" class="three" title="검색어 입력" style="width:250px;" placeholder="Search.."/> <!-- 검색어 입력 -->
                    </div>
                </td>

                <td class="label">트리거 종류</td> <!-- 트리거 종류 -->
                <td>
                    <div>
                        <form:select path="triType" title="트리거 종류" style="width:80px;"> <!-- 트리거 종류 -->
                            <form:option value="">전체</form:option> <!-- 전체 -->
                            <form:option value="1">심플</form:option> <!-- 심플 -->
                            <form:option value="2">크론</form:option> <!-- 크론 -->
                        </form:select>
                    </div>
                </td>
                <td class="label">배치 상태</td> <!-- 배치 상태 -->
                <td>
                    <div>
                        <form:select path="batchType" title="배치 상태" style="width:80px;"> <!-- 배치 상태 -->
                            <form:option value="">전체</form:option> <!-- 전체 -->
                            <form:option value="1">실행중</form:option> <!-- 싫행중 -->
                            <form:option value="2">정지</form:option> <!-- 정지 -->
                        </form:select>
                    </div>
                </td>
            </tr>
            </tbody>
        </table>
    </div>

    <!-- 버튼시작 -->
    <div class="btn_all">
        <div class="btn_left">
            <button type="button" class="btn btn-dark-gray btn-sm" onclick="location.href='/opmanager/batch-job/list';"><span class="glyphicon glyphicon-repeat"></span> ${op:message('M00047')}</button> <!-- 초기화 -->
        </div>
        <div class="btn_right">
            <button type="submit" class="btn btn-dark-gray btn-sm"><span class="glyphicon glyphicon-search"></span> 검색</button> <!-- 검색 -->
        </div>
    </div>
    <!-- 버튼 끝-->

    <div class="count_title mt20">
        <h5>
                ${op:message('M00039')} : ${batchJobList.size()} ${op:message('M00743')}
        </h5>	 <!-- 전체 -->   <!-- 건 조회 -->
    </div>
</form:form>

<form id="listForm">
    <div class="board_write">
        <table class="board_list_table" summary="배치 작업">
            <caption>배치 작업</caption>
            <colgroup>
                <col style="width:5%;" />
                <col style="width:1%;" />
                <col style="width:15%;" />
                <col style="width:20%;" />
                <col style="width:5%;" />
                <col style="width:15%;" />
                <col style="width:20%;" />
                <col style="width:5%;" />
                <col style="width:15%;" />
                <col style="width:10%;" />
                <col style="width:5%;" />
            </colgroup>
            <thead>
            <tr>
                <th scope="col"><input type="checkbox" name="tempId2" id="check_all" title="${op:message('M00169')}" /></th>  <!-- 체크박스 -->
                <th scope="col"></th> 		 	     <!-- 작업ID -->
                <th scope="col">작업명</th> 		 	 <!-- 작업명 -->
                <th scope="col">작업실행 메서드명</th> <!-- 작업실행 메서드명 -->
                <th scope="col">트리거 종류</th>     	 <!-- 트리거 종류 -->
                <th scope="col">반복 시간</th> 	 <!-- 반복 시간 -->
                <th scope="col">반복 표현식</th> 	 <!-- 반복 표현식 -->
                <th scope="col">배치 상태</th> 		 <!-- 배치 상태 -->
                <th scope="col">마지막 실행일</th> 	 <!-- 마지막 실행일 -->
                <th scope="col">배치 적용여부</th> 	 <!-- 배치 적용여부 -->
                <th scope="col">관리</th> 	 		 <!-- 관리 -->
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${batchJobList}" var="list" varStatus="i">
                <tr>
                    <td><input type="checkbox" name="id" id="check" value="${list.id}" title="${op:message('M00169')}" /></td>
                    <td><input type="hidden" name="batchJobId" id="batchJobId" value="${list.batchJobId}" /></td>
                    <td>${list.jobName}</td>
                    <td>${list.jobMethod}</td>
                    <td>${list.triggerType}</td>
                    <td>${list.triggerRepeatSeconds}</td>
                    <td>${list.triggerCronExpression}</td>
                    <td>${list.batchStatus}</td>
                    <td>${list.batchExcuteDate}</td>
                    <td>${list.batchApplyFlag}</td>
                    <td>
                        <button type="button" class="btn btn-gradient btn-xs" onclick="fnBatchJobUpdate('${list.batchJobId}')">${op:message('M00087')}</button> <!-- 수정 -->
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div><!--// board_write E-->

    <c:if test="${empty batchJobList}">
        <div class="no_content">
                ${op:message('M00473')} <!-- 데이터가 없습니다. -->
        </div>
    </c:if>

    <div class="btn_all">
        <div class="btn_left mb0">
            <a id="delete_list_data" href="#" class="btn btn-default btn-sm"><span>${op:message('M00576')}</span></a> <!-- 선택삭제 -->
        </div>
        <div class="btn_right mb0">
            <a href="javascript:fnBatchJobCreate();" class="btn btn-active btn-sm"><span class="glyphicon glyphicon-plus"></span> ${op:message('M00088')}</a> <!-- 신규등록 -->
        </div>
    </div>

</form>



<script type="text/javascript">
    $(function() {

        //데이터 출력량 적용
        $('#itemsPerPage').on("change", function(){
            $('#messageParam').submit();
        });

        //목록데이터 - 삭제처리
        $('#delete_list_data').on('click', function() {
            Common.updateListData("/opmanager/batch-job/delete", Message.get("M00306"));	// 선택된 데이터를 삭제하시겠습니까?
        });

    });

    function fnBatchJobUpdate(batchJobId, jobName) {
        Common.popup('/opmanager/batch-job/detail?batchJobId=' + batchJobId, 'update', 1250, 310, 1);
    }

    function fnBatchJobCreate(batchJobId) {
        Common.popup('/opmanager/batch-job/create', 'create', 1250, 310, 1);
    }
</script>