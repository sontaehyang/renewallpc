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


<div class="location">
    <a href="#"></a> &gt;  <a href="#"></a> &gt; <a href="#" class="on"></a>
</div>

<div class="item_list">

    <h3><span>SNS 연동 관리</span></h3>
    <form:form modelAttribute="displaySnsParam" method="get">
        <div class="board_write">
            <table class="board_write_table" summary="SNS 연동 관리">
                <caption>SNS 연동 관리</caption>
                <colgroup>
                    <col style="width: 150px;">
                    <col style="width: auto;">
                    <col style="width: 150px;">
                    <col style="width: auto;">
                </colgroup>
                <tbody>
                <tr>
                    <td class="label">${op:message('M00011')}</td> <!-- 검색구분 -->
                    <td colspan="3">
                        <div>
                            <form:select path="where" title="상세검색 선택">
                                <form:option value="SNS_TOKEN">TOKEN</form:option>
                            </form:select>
                            <form:input path="query" class="w360" title="${op:message('M00022')}" /> <!-- 검색어 -->
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="label">SNS 종류</td>
                    <td>
                        <div>
                            <p>
                                <form:radiobutton path="snsType" value="" label="전체" checked="checked"/>
                                <form:radiobutton path="snsType" value="yotube" label="yotube"  />
                                <form:radiobutton path="snsType" value="facebook" label="facebook"  />
                                <form:radiobutton path="snsType" value="instagram" label="instagram" />
                            </p>
                        </div>
                    </td>
                    <td class="label">등록일</td>
                    <td>
                        <div>
                            <span class="datepicker"><form:input path="searchStartDate" maxlength="8" class="datepicker" title="${op:message('M00024')}" /><!-- 주문일자 시작일 --></span>
                            <span class="wave">~</span>
                            <span class="datepicker"><form:input path="searchEndDate" maxlength="8" class="datepicker" title="${op:message('M00025')}" /><!-- 주문일자 종료일 --></span>
                            <span class="day_btns">
                                <a href="javascript:;" class="btn_date clear">전체</a>
                                <a href="javascript:;" class="btn_date today">${op:message('M00026')}</a><!-- 오늘 -->
                                <a href="javascript:;" class="btn_date week-1">${op:message('M00027')}</a><!-- 1주일 -->
                                <a href="javascript:;" class="btn_date day-15">${op:message('M00028')}</a><!-- 15일 -->
                                <a href="javascript:;" class="btn_date month-1">${op:message('M00029')}</a><!-- 한달 -->
                                <a href="javascript:;" class="btn_date month-3">${op:message('M00030')}</a><!-- 3개월 -->
                                <a href="javascript:;" class="btn_date year-1">${op:message('M00031')}</a><!-- 1년 -->
                            </span>
                        </div>
                    </td>
                </tr>
                </tbody>
            </table>
        </div>

        <div class="btn_all">
            <div class="btn_left">
                <button type="button" class="btn btn-dark-gray btn-sm" onclick="location.href='/opmanager/display/sns/list';"><span class="glyphicon glyphicon-repeat"></span> ${op:message('M00047')}</button> <!-- 초기화 -->
            </div>
            <div class="btn_right">
                <button type="submit" class="btn btn-dark-gray btn-sm"><span class="glyphicon glyphicon-search"></span> ${op:message('M00048')}</button> <!-- 검색 -->
            </div>
        </div>

        <div class="count_title mt20">
            <h5>
                ${op:message('M00039')} : ${op:numberFormat(pagination.totalItems)} ${op:message('M00743')}
            </h5> <!-- 전체 --> <!-- 건 조회 -->
            <span>
                <span>${op:message('M00052')} <!-- 출력수 --></span>
                <form:select path="itemsPerPage" title="출력수 선택">
                    <form:option value="10" label="10" />
                    <form:option value="50" label="50" />
                    <form:option value="100" label="100" />
                    <form:option value="500" label="500" />
                </form:select>
            </span>
        </div>
    </form:form>

    <form id="checkedDeleteForm" method="post" action="/opmanager/display/sns/checked-delete">
        <div class="board_write">
            <table class="board_list_table" summary="처리내역 리스트">
                <caption>처리내역 리스트</caption>
                <colgroup>
                    <col style="width:50px;">
                    <col style="width:120px;">
                    <col style="width:auto;">
                    <col style="width:100px;">
                    <col style="width:70px;">
                </colgroup>
                <thead>
                <tr>
                    <th scope="col"><input type="checkbox" class="delete_all" title="체크박스"></th>
                    <th scope="col">SNS 종류</th>
                    <th scope="col">TOKEN</th>
                    <th scope="col">${op:message('M00202')}</th>  <!-- 등록일 -->
                    <th scope="col">${op:message('M00087')}</th>  <!-- 수정 -->
                </tr>
                </thead>
                <tbody class="sortable">
                <c:forEach items="${list}" var="list" varStatus="i">
                    <tr>
                        <td><input type="checkbox" name="snsIds" title="체크박스" value="${list.snsId}"></td>
                        <td>${list.snsType}</td>
                        <td class="tex_l"><a href="/opmanager/display/sns/edit/${list.snsId}">${list.snsToken}</a></td>
                        <td>${op:date(list.createdDate)}</td>
                        <td><a href="/opmanager/display/sns/edit/${list.snsId}" class="btn btn-gradient btn-xs">${op:message('M00087')}</a></td> <!-- 수정 -->
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>

        <c:if test="${empty list}">
            <div class="no_content">
                ${op:message('M00473')} <!-- 데이터가 없습니다. -->
            </div>
        </c:if>


        <div class="btn_all">
            <div class="btn_left mb0">
                <button type="button" class="btn btn-default btn-sm checked_delete">${op:message('M01034')}</button> <!-- 일괄삭제 -->

                <button type="button" id="change_ordering" class="btn btn-dark-gray btn-sm" onclick="changeListOrdering()">${op:message('M00791')}</button> <!-- 정렬순서변경 -->
                <div class="board_guide ml10" style="float: right;">
                    <p class="tip"></p>
                </div>
            </div>
            <div class="btn_right mb0">
                <a href="/opmanager/display/sns/create" class="btn btn-active btn-sm"><span class="glyphicon glyphicon-plus"></span> ${op:message('M00088')}</a> <!-- 등록 -->
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
        if (isPosibleToChangeOrdering()) {
            // drag sortable
            $( ".sortable" ).sortable({
                placeholder: "sortable-placeholder"
            });
            $( ".sortable" ).disableSelection();

            $('#change_ordering, #change_ordering2').css({'background-color': '#25a5dc', 'border': '1px solid #25a5dc'}).show();
            $('.tip').append("Tip. 목록을 드래그 하여 정렬순서를 변경하세요.");
        } else {
            $('#change_ordering, #change_ordering2').css({'background-color': '#c34e00', 'border': '1px solid #c34e00'}).show();
        }

        $(".delete_all").on("click",function(){
            var flag = $(this).prop("checked");
            $("input[name='snsIds']").each(function(){
                $(this).prop("checked",flag);
            });
        });

        $(".checked_delete").on("click",function(){
            Common.confirm(Message.get("M00196"),function(){	// 삭제하시겠습니까?
                if($("input[name='snsIds']:checked").size() > 0){
                    $("#checkedDeleteForm").submit();
                }
            });
        });

        $('#itemsPerPage').on("change", function() {
            $('#displaySnsParam').submit();
        });

        Common.DateButtonEvent.set('.day_btns > a[class^=btn_date]', '', 'input[name="searchStartDate"]' , 'input[name="searchEndDate"]');
    });


    //순서변경 가능여부 체크
    function isPosibleToChangeOrdering() {
        // 순서 변경이 가능한 조건인지 체크.
        var $query = $('#query');
        var $snsType = $('#snsType1');

        if ($query.val() != '' || $snsType.prop('checked') == true) {
            return false;
        }

        return true;
    }

    // 순서변경
    function changeListOrdering() {
        if (!isPosibleToChangeOrdering()) {
            if (confirm(Message.get("M00298") + '\n' + Message.get("M00299"))) { // '현재 검색 조건으로는 순서 정렬이 불가능합니다. 순서 변경이 가능한 조건으로 다시 검색하시겠습니까?'
                $('#query').val('');
                $('#snsType2').prop('checked', true);

                $('#displaySnsParam').submit();
            }
            return;
        }

        var $form = $('#checkedDeleteForm');

        if (confirm(Message.get("M01483"))) { // 순서를 변경하시겠습니까?
            $('.delete_all').click();
            $('#orderBy').val("ORDERING").prop('selected', true);
            $('#sort').val("ASC").prop('selected', true);

            var param = $form.serialize();
            var startOrdering = ((Number('${pagination.currentPage}') - 1) * Number($('#itemsPerPage').val())) + 1;

            param = param + '&startOrdering=' + startOrdering;

            $.post('/opmanager/display/sns/list/change-ordering', param, function(response) {
                Common.responseHandler(response, function(){
                    $('.delete_all').click();
                    alert(Message.get("M01221"));	// 처리되었습니다.
                    location.reload();
                });
            });
        }
    }

</script>