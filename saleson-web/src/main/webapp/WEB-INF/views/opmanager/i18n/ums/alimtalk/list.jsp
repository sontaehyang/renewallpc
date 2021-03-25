<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions" %>
<%@ taglib prefix="sec" 	uri="http://www.springframework.org/security/tags"%>

<!-- 본문 -->
<div class="popup_wrap">
    <h1 class="popup_title">알림톡 조회</h1>
    <div class="popup_contents pb0">
        <form id="listForm">
            <table class="board_list_table">
                <colgroup>
                    <col style="width:200px;" />
                    <col style="width:auto;" />
                    <col style="width:200px;" />
                    <col style="width:200px;" />
                    <col style="width:100px;" />
                    <col style="width:100px;" />
                </colgroup>
                <thead>
                <tr>
                    <th scope="col">템플릿 코드</th>
                    <th scope="col">템플릿 이름</th>
                    <th scope="col">템플릿 상태</th>
                    <th scope="col">승인 코드</th>
                    <th scope="col">검수 상태</th>
                    <th scope="col">관리</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="list" items="${pageContent.content}" varStatus="i">
                    <tr>
                        <td>${list.templateCode}</td>
                        <td><a href="/opmanager/ums/alimtalk/${list.templateCode}/edit/${list.applyCode}">${list.title}</a></td>
                        <td>${list.statusTitle}(${list.status})</td>
                        <td>${list.applyCode}</td>
                        <td>${list.inspStatusTitle}(${list.inspStatus})</td>
                        <td>
                            <!-- 검수상태가 승인(APR)일 경우에만 노출  -->
                            <c:if test="${list.inspStatus == 'APR'}">
                                <a href="/opmanager/ums/alimtalk/${list.templateCode}/select/${list.applyCode}" class="btn btn-gradient btn-xs">선택</a>
                            </c:if>
                            <a href="/opmanager/ums/alimtalk/${list.templateCode}/edit/${list.applyCode}" class="btn btn-gradient btn-xs">수정</a>
                            <!-- 검수상태가 승인(APR)이 아닌 경우에만 노출  -->
                            <c:if test="${list.inspStatus != 'APR'}">
                                <a href="javascript:deleteAlimTalk('${list.applyCode}');" class="delete_item btn btn-gradient btn-xs" style="margin-top: 1px;">삭제</a>
                            </c:if>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </form>

        <c:if test="${empty pageContent.content}">
            <div class="no_content">
                알림톡 설정 정보가 없습니다.
            </div>
        </c:if>

        <div class="btn_all">
            <div class="btn_right">
                <a href="/opmanager/ums/alimtalk/${templateCode}/create" class="btn btn-active btn-sm"><span class="glyphicon glyphicon-plus"></span> 등록</a>
            </div>
        </div>

        <page:pagination-jpa />
    </div><!--//board_write E-->

    <a href="#" class="popup_close">창 닫기</a>
</div>

<page:javascript>
    <script type="text/javascript">
        function deleteAlimTalk(applyCode) {
            if (confirm('해당 알림톡 정보를 삭제하시겠습니까?')) {
                $.post("/opmanager/ums/alimtalk/delete/" + applyCode, {}, function (response) {
                    Common.responseHandler(response, function(){
                        location.reload();
                    });
                });
            }
        }
    </script>
</page:javascript>