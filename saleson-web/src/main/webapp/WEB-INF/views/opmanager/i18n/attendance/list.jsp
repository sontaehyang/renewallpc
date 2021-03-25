<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" 		uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" 	uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" 	uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" 	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions"%>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page"%>
<%@ taglib prefix="module" 	tagdir="/WEB-INF/tags/modules"%>
<%@ taglib prefix="shop" 	uri="/WEB-INF/tlds/shop" %>


	<div class="location">
		<a href="#"></a> &gt;  <a href="#"></a> &gt; <a href="#" class="on"></a>
	</div>	

	<div class="item_list">
			
		<h3><span>출석체크 관리</span></h3>
		<form:form modelAttribute="attendanceParam" method="get">
			<div class="board_write">								
				<table class="board_write_table" summary="관련상품등록">
					<caption>관련상품등록 </caption>
					<colgroup>
                        <col style="width:170px;" />
                        <col style="width:auto;" />
                        <col style="width:170px;" />
                        <col style="width:auto;" />
					</colgroup>
					<tbody>
						<tr>
                            <td class="label">이벤트 코드</td>    <!-- 검색구분 -->
                            <td>
                                <div>
                                    <form:input path="eventCode" class="w360" title="이벤트 코드"/>
                                </div>
                            </td>

                            <td class="label">연속출석 사용여부</td>
                            <td>
                                <div>
                                    <p>
                                        <form:radiobutton path="continueYn" value="" checked="checked" label="전체" />
                                        <form:radiobutton path="continueYn" value="Y" label="사용" />
                                        <form:radiobutton path="continueYn" value="N" label="미사용" />
                                    </p>
                                </div>
                            </td>
						</tr>
						<tr>
                            <td class="label">연월</td>
                            <td colspan="3">
                                <div>
                                    <form:input path="startYear" maxlength="4" class="optional _filter _number form-sm" value="${attendanceParam.startYear}" />년
                                    <form:input path="startMonth" maxlength="2" class="optional _filter _number form-xs" value="${attendanceParam.startMonth}" />월
                                    ~
                                    <form:input path="endYear" maxlength="4" class="optional _filter _number form-sm" value="${attendanceParam.endYear}" />년
                                    <form:input path="endMonth" maxlength="2" class="optional _filter _number form-xs" value="${attendanceParam.endMonth}" />월
                                </div>
                            </td>
						</tr>
					</tbody>
				</table>
				
			</div>
			
			<div class="btn_all">
				<div class="btn_left">
					<button type="button" class="btn btn-dark-gray btn-sm" onclick="location.href='/opmanager/attendance/list';"><span class="glyphicon glyphicon-repeat"></span> ${op:message('M00047')}</button> <!-- 초기화 -->
				</div>
				<div class="btn_right">
					<button type="submit" class="btn btn-dark-gray btn-sm"><span class="glyphicon glyphicon-search"></span> ${op:message('M00048')}</button> <!-- 검색 -->
				</div>
			</div>
		</form:form>
		
		<div class="count_title mt20">
			<h5>
				${op:message('M00039')} : ${attendanceCount} ${op:message('M00743')}
			</h5>	 <!-- 전체 -->   <!-- 건 조회 --> 
		</div>
		
		<form id="checkedDeleteForm" method="post" action="/opmanager/attendance/checked-delete">
			<input type="hidden" name="id" value="" />
			<div class="board_write">
				<table class="board_list_table" summary="처리내역 리스트">
					<caption>처리내역 리스트</caption>
					<colgroup>
						<col style="width:25px;">
						<col style="width:100px;">
                        <col style="width:70px;">
                        <col style="width:70px;">
                        <col style="width:70px;">
                        <col style="width:70px;">
						<col style="width:70px;">
						<col style="width:70px;">
                        <col style="width:70px;">
					</colgroup>
					<thead>
						<tr>
							<th scope="col"><input type="checkbox" class="delete_all" title="체크박스"></th>
                            <th scope="col">연</th>
                            <th scope="col">월</th>
                            <th scope="col">출석일수</th>
                            <th scope="col">연속출석 사용여부</th>
                            <th scope="col">이벤트 코드</th>
                            <th scope="col">등록자</th>
							<th scope="col">${op:message('M00202')}</th>  <!-- 등록일 -->
                            <th scope="col">수정</th>
						</tr>
					</thead>
					<tbody class="sortable">
						<c:forEach items="${attendanceList}" var="attendance" varStatus="attendanceIndex">
                            <c:set var="yearMonth" value="${attendance.year}${attendance.month}"/>
							<tr>
                                <c:forEach items="${attendance.attendanceConfigs}" var="attendanceConfig" varStatus="attendanceConfigIndex">
                                    <tr>
                                        <c:if test="${attendanceConfigIndex.first}">
                                            <c:choose>
                                                <c:when test="${yearMonth > shop:todayTime('yyyyMM')}">
                                                    <td rowspan="${fn:length(attendance.attendanceConfigs)}"><input type="checkbox" name="ids" title="체크박스" value="${attendance.attendanceId}"></td>
                                                </c:when>
                                                <c:otherwise>
                                                    <td rowspan="${fn:length(attendance.attendanceConfigs)}"><input type="checkbox" name="ids" title="체크박스" disabled="disabled" value="${attendance.attendanceId}"></td>
                                                </c:otherwise>
                                            </c:choose>
                                            <%--<td rowspan="${fn:length(attendance.attendanceConfigs)}"><input type="checkbox" name="ids" title="체크박스" value="${attendance.attendanceId}"></td>--%>
                                            <td rowspan="${fn:length(attendance.attendanceConfigs)}">${attendance.year}</td>
                                            <td rowspan="${fn:length(attendance.attendanceConfigs)}">${attendance.month}</td>
                                        </c:if>
                                        <td>${attendanceConfig.days}</td>
                                        <td>${attendanceConfig.continueYn}</td>
                                        <td>${attendanceConfig.eventCode}</td>
                                        <c:if test="${attendanceConfigIndex.first}">
                                            <td rowspan="${fn:length(attendance.attendanceConfigs)}">${attendanceConfig.createdBy}</td>
                                            <td rowspan="${fn:length(attendance.attendanceConfigs)}">${op:date(attendanceConfig.createdDate) }</td>
                                            <td rowspan="${fn:length(attendance.attendanceConfigs)}">
                                                <c:choose>
                                                    <c:when test="${yearMonth > shop:todayTime('yyyyMM')}">
                                                        <a href="javascript:Link.list('/opmanager/attendance/edit?attendanceId=${attendance.attendanceId}')" class="table_btn">수정</a>
                                                    </c:when>
                                                    <c:otherwise>
                                                        수정불가
                                                    </c:otherwise>
                                                </c:choose>
                                            </td>
                                        </c:if>
                                    </tr>
                                </c:forEach>
							</tr>
						</c:forEach>
					</tbody>
				</table>				 
			</div>
			
			<c:if test="${empty attendanceList}">
				<div class="no_content">
					${op:message('M00473')} <!-- 데이터가 없습니다. -->
				</div>
			</c:if>
				
			
			<div class="btn_all">
				<div class="btn_left mb0">
					<button type="button" class="btn btn-default btn-sm checked_delete">${op:message('M01034')}</button> <!-- 일괄삭제 -->
				</div>
				<div class="btn_right mb0">
					<a href="/opmanager/attendance/create" class="btn btn-active btn-sm"><span class="glyphicon glyphicon-plus"></span> ${op:message('M00088')}</a> <!-- 등록 -->
				</div>
			</div>
			<page:pagination-manager />
		</form>	
					
	</div> <!-- // item_list02 -->


<style>
td {background: #fff;}
.sortable-placeholder td {
	height: 70px; 
	background: #d6eafd url("/content/styles/ui-lightness/images/ui-bg_diagonals-thick_20_666666_40x40.png") 50% 50% repeat;
	opacity: 0.5;
}
#change_ordering, #change_ordering2 {
	display: none;
}
#change_ordering2 {
	position:fixed; right:0; bottom: 166px; z-index: 1000;
}
</style>


<script type="text/javascript">
$(function(){

	$(".delete_all").on("click",function(){
		var flag = $(this).prop("checked");
		$("input[name='ids']").each(function(){
		    if ($(this).attr("disabled") != 'disabled') {
                $(this).prop("checked",flag);
            }
		});
	});		
	
	$(".checked_delete").on("click",function(){
		Common.confirm(Message.get("M00196"),function(){	// 삭제하시겠습니까?
			if($("input[name='ids']:checked").size() > 0){
				$("#checkedDeleteForm").submit();
			}
		});
	});
	
	$("#reset").on("click",function(){
		$("input").each(function(){
			$(this).val('');
		});
	});
});

</script>