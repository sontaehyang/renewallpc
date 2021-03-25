<%@ page language="java" contentType="text/html; charset=utf-8"	pageEncoding="utf-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="page" tagdir="/WEB-INF/tags/page"%>
<%@ taglib prefix="op" uri="/WEB-INF/tlds/functions"%>

<style type="text/css">
    .board_list_table th {
        text-align: center;
    }
</style>

<div class="popup_wrap">
    <h1 class="popup_title">댓글 관리</h1>
    <div class="popup_contents">
        <form:form id="listForm" modelAttribute="featuredReplyParam" method="post">
            <div class="board_write">
                <h3><span>댓글 관리</span></h3>
                <table class="board_list_table" summary="reply">
                    <caption>댓글 관리</caption>
                    <colgroup>
                        <col style="width:30px;" />
                        <col style="width:100px;" />
                        <col style="width:auto;" />
                        <col style="width:50px;" />
                        <col style="width:100px;" />
                    </colgroup>
                    <thead>
                    <tr>
                        <th scope="col"><input type="checkbox" id="check_all" title="${op:message('M00169')}" /></th>
                        <th scope="col">유저명</th>
                        <th scope="col">댓글 내용</th>
                        <th scope="col">상태</th>
                        <th scope="col">등록 날짜</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="reply" items="${replyList}">
                        <tr>
                            <td>
                                <input type="checkbox" class="ids" name="ids" value="${reply.id}" title="${op:message('M00169')}" />
                            </td>
                            <td>${reply.userName}</td>
                            <td>${reply.replyContent}</td>
                            <td class="dataStatus">${reply.dataStatus eq '0' ? '공개' : '비공개'}</td>
                            <td>${op:datetime(reply.created)}</td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div><!--// board_write E-->

            <c:if test="${not empty replyList}">
            <div class="btn_all">
                <div class="btn_left mb0">
                    <button type="button" class="btn btn-default btn-sm" onclick="updateDisplayReply('0')">공개</button>
                    <button type="button" class="btn btn-default btn-sm" onclick="updateDisplayReply('1')">비공개</button>
                </div>
            </div>
            </c:if>
        </form:form>
    </div>
</div>

<c:if test="${empty replyList}">
    <div class="no_content">
        ${op:message('M00473')} <!-- 데이터가 없습니다. -->
    </div>
</c:if>

<page:pagination-manager/>

<script type="text/javascript">
    $(document).ready(function() {

        $('#check_all').on('click', function() {
            var checkStatus = $(this).is(':checked');
            $("input:checkbox[name='ids']").prop('checked', checkStatus);
        });

    });

    function updateDisplayReply(dataStatus) {

        if ($('.ids:checked').length <= 0) {
            alert(processingDataStatus + ' 할 댓글을 선택해주세요.');
            return false;
        }

        var processingDataStatus = dataStatus == '0' ? '공개' : '비공개';

        if (confirm('선택된 댓글을 ' + processingDataStatus + ' 처리 하시겠습니까?')) {
            jQuery.ajaxSettings.traditional = true;

            var ids = new Array();



            var repetitionStatus = false;
            $('.ids:checked').each(function() {
                var changeData = $(this).val();
                var replyStatus = $(this).parent().siblings('.dataStatus').text();

                if (processingDataStatus == replyStatus) {
                    repetitionStatus = true;
                    return false;
                }

                ids.push(changeData);
            });

            if (repetitionStatus) {
                alert(processingDataStatus + ' 댓글을 정확히 선택해주세요.');
                return false;
            }


            var param = {
                'dataStatus': dataStatus,
                'ids': ids
            };


            $.post('/opmanager/featured/display-reply', param, function(response) {
                if (response.isSuccess) {
                    $('.ids:checked').each(function() {
                        var tagDataStatusText = $(this).parent().siblings('.dataStatus');

                        if (dataStatus == '0') {
                            tagDataStatusText.text('공개');
                        } else {
                            tagDataStatusText.text('비공개');
                        }

                    });
                }
            });
        }
    }



</script>