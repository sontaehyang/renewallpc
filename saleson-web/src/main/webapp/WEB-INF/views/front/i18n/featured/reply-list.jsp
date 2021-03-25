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

<c:if test="${featured.replyUsedFlag eq 'Y'}">
    <div>
        <div class="op-event-title" id="reply">
            <p>댓글</p>
        </div>

        <br/>
        <div style="text-align: center; ">
            <input type="text" id="replyContent" style="width: 80%"/>
            <button type="button" id="replySubmit" class="btn btn-default btn-sm">댓글 등록</button>
        </div>
        <br/>

        <table class="board-list">
            <colgroup>
                <col style="width: 30px;"> <!-- 번호 -->
                <col style="width: auto;"> <!-- 댓글 내용 -->
                <col style="width: 100px;"> <!-- 작성자 -->
                <col style="width: 100px;"> <!-- 작성일 -->
            </colgroup>
            <thead>
            <tr>
                <th scope="col">번호</th>
                <th scope="col">댓글 내용</th>
                <th scope="col">작성자</th>
                <th scope="col">작성일</th>
            </tr>
            </thead>
            <tbody id="tbody">
            <c:forEach var="reply" items="${replyList}" varStatus="i">
                <tr>
                    <td>${pagination.itemNumber - i.count}</td>
                    <td>${reply.replyContent}</td>
                    <td>${reply.userName}</td>
                    <td>${reply.created}</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
    <c:if test="${empty replyList}">
        <div class="no_content">
            등록된 댓글이 없습니다.
        </div>
    </c:if>
</c:if>

<c:if test="${featured.replyUsedFlag eq 'Y'}">
    <page:pagination-manager/>
</c:if>

<script type="text/javascript">
    $(document).ready(function() {

        $('#replySubmit').on('click', function() {

            var replyContent = $('#replyContent').val().trim();

            if (replyContent == "") {
                alert("댓글을 입력해주세요.");
                return false;
            }

            $.ajax({
                url: '/featured/write-reply',
                type: 'POST',
                data: {
                    'page': 1,
                    'replyContent': replyContent,
                    'featuredUrl': '${featuredUrl}',
                    'featuredId': '${featured.featuredId}'
                },
                success: function(response) {
                    if (response.isSuccess) {

                        alert('등록 되었습니다.');
                        //console.log("response.data: ", response.data);

                        $('#replyContent').val('');

                        var featuredReply = response.data[0];
                        var appendLocation = $('#tbody');
                        var appendData = '';

                        appendData =  "<tr>";
                        appendData += "	    <td>" + ($('#tbody tr').length + 1) + "</td>";
                        appendData += "     <td>" + featuredReply.replyContent + "</td>";
                        appendData += "	    <td>" + featuredReply.userName + "</td>";
                        appendData += "	    <td>" + featuredReply.created + "</td>";
                        appendData += "</tr>";

                        appendLocation.prepend(appendData);

                        if ($('#tbody tr').length > 0) {
                            $('.no_content').css('display', 'none');
                        }

                        paginationFeaturedReply(1);

                    } else {
                        alert(response.errorMessage);
                    }
                }

            });

        });
    });

</script>