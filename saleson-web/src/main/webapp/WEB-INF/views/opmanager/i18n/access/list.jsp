<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions" %>

	<h3><span>관리자 접속IP관리</span></h3>
	<div class="board_list">
		<div class="text2">
			&nbsp;
		</div>
		<form id="listForm">

			<table class="board_list_table">
				<colgroup>
					<col style="width:5%;"/>
					<col style="width:20%;"/>
					<col style="width:20%;"/>
					<col style="width:20%;"/>
					<col style="width:20%;"/>
				</colgroup>
				<thead>
				<tr>
					<%--<th><input type="checkbox" id="check_all" title="체크박스"/></th>--%>
					<th>번호</th>
					<th>접근 타입</th>
					<th>IP 주소</th>
					<th>등록일</th>
					<th></th>
				</tr>
				</thead>
				<tbody>
				<c:forEach items="${list}" var="item" varStatus="i">
					<tr>
							<%--<td><input type="checkbox" name="id" value="${item.allowIpId}"/></td>--%>
						<td>${pagination.itemNumber - i.count}</td>
						<td>${item.accessType eq '1'? '관리자':'판매자'}</td>
						<td class="tleft">${item.remoteAddr }</td>
						<td>${op:date(item.createdDate)}</td>
						<td>
							<c:if test="${item.allowIpId ne 1}">
								<a href="#" class="delete_item btn btn-gradient btn-xs" style="margin-top: 1px;" data-id="${item.allowIpId}">
									삭제
								</a>
							</c:if>
						</td>

					</tr>

				</c:forEach>
				</tbody>
			</table>
		</form>
		<%--<div class="board_guide">--%>
		<%--<p class="total">전체 : <em>${count}</em></p>--%>
		<%--</div>--%>
		<p class="btns">
			<a href="/opmanager/access/write" class="btn btn-active btn-sm"><span class="glyphicon glyphicon-plus"></span> 등록</a>
		</p>
		<page:pagination-manager />
	</div>

<script type="text/javascript">
    $(function () {

        // 삭제
        $('.delete_item').on('click', function (e) {
            e.preventDefault();
            var url = '/opmanager/access/delete';
            var data = {
                id: $(this).data('id')
            }
            if (confirm('해당 IP를 삭제하시겠습니까?')) {

                $.post(url, data, function (response) {

                    Common.responseHandler(response, function (response) {

                        if (response.isSuccess) {
                            alert(response.data);
                        } else {
                            alert(response.errorMessage);
                        }
                        location.reload();

                    });

                });
            }
        });

    });
</script>