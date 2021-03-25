<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions"%>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page"%>


<div class="location">
    <a href="#"></a> &gt;  <a href="#"></a> &gt; <a href="#" class="on"></a>
</div>

<c:set var="isPopup" value="${target == 'popup'}" />

<div class="${isPopup ? 'popup_wrap' : 'board_write'}">
    <c:choose>
        <c:when test="${isPopup}">
            <h1 class="popup_title">카테고리 필터 관리</h1>
        </c:when>
        <c:otherwise>
            <h3><span>카테고리 필터 관리</span></h3>
        </c:otherwise>
    </c:choose>
    <div class="${isPopup ? 'popup_contents' : ''}">
        <form:form modelAttribute="filterGroupDto" method="get" enctype="multipart/form-data">
            <input type="hidden" name="target" value="${target}" />
            <table class="board_write_table" summary="카테고리 필터 관리">
                <caption>카테고리 필터 관리</caption>
                <colgroup>
                    <col style="width:150px;" />
                    <col style="width:auto;" />
                    <col style="width:150px;" />
                    <col style="width:auto;" />
                </colgroup>
                <tbody>
                    <tr>
                        <td class="label">${op:message('M00011')}</td>    <!-- 검색구분 -->
                        <td colspan="3">
                            <div>
                                <form:select path="where" title="상세검색 선택">
                                    <form:option value="GROUP_LABEL">필터 그룹명</form:option>
                                    <form:option value="GROUP_DESCRIPTION">상세 설명</form:option>
                                </form:select>
                                <form:input path="query" class="w360" title="상세검색 입력" />
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td class="label">필터 타입</td>
                        <td colspan="3">
                            <div>
                                <form:radiobutton path="filterType" label="전체" value="" checked="checked" />
                                <c:forEach items="${filterTypes}" var="type" varStatus="i">
                                    <form:radiobutton path="filterType" label="${type.title}" value="${type.code}" />
                                </c:forEach>
                            </div>
                        </td>
                    </tr>
                </tbody>
            </table>

            <!-- 버튼시작 -->
            <div class="btn_all">
                <div class="btn_left">
                    <button type="button" class="btn btn-dark-gray btn-sm" onclick="location.href='/opmanager/categories-filter/list?target=${target}';"><span class="glyphicon glyphicon-repeat"></span> ${op:message('M00047')}</button> <!-- 초기화 -->
                </div>
                <div class="btn_right">
                    <button type="submit" class="btn btn-dark-gray btn-sm"><span class="glyphicon glyphicon-search"></span>${op:message('M00048')}</button> <!-- 검색 -->
                </div>
            </div>
        </form:form>

        <div class="count_title mt20">
            <h5>
                ${op:message('M00039')} : ${op:numberFormat(pageContent.totalElements)} ${op:message('M00743')}
            </h5>	 <!-- 전체 -->   <!-- 건 조회 -->
        </div>

        <form id="listForm">
            <div class="board_write">
                <table class="board_list_table" summary="카테고리 필터 관리">
                    <caption>카테고리 필터 관리</caption>
                    <colgroup>
                        <col style="width:8%;" />
                        <col style="width:15%;" />
                        <col style="width:15%;" />
                        <col style="width:auto;" />
                        <col style="width:13%;" />
                        <col style="width:11%;" />
                    </colgroup>
                    <thead>
                        <tr>
                            <th scope="col"><input type="checkbox" id="check_all" title="${op:message('M00169')}" /></th>
                            <th scope="col">필터 그룹명</th>
                            <th scope="col">필터 설명</th>
                            <th scope="col">필터 옵션</th>
                            <th scope="col">등록일</th>
                            <th scope="col">${op:message('M00087')}/${op:message('M00074')}</th> <!-- 수정/삭제 -->
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${pageContent.content}" var="group" varStatus="i" >

                            <c:set var="codeString" value=""/>
                            <c:forEach items="${group.codeList}" var="code" varStatus="j">
                                <c:set var="codeString" value="${codeString}${code.label}"/>
                                <c:if test="${!j.last}">
                                    <c:set var="codeString" value="${codeString}, "/>
                                </c:if>

                            </c:forEach>

                            <tr id="group_${group.id}">
                                <td><input type="checkbox" name="id" id="check" value="${group.id}" title=${op:message('M00169')} /></td>
                                <td class="tleft">
                                    <div>
                                        <a href="/opmanager/categories-filter/edit/${group.id}?target=${target}">${group.label}</a>
                                    </div>

                                    <span class="filter_group_info" style="display:none;">
										<span class="id">${group.id}</span>
										<span class="label">${group.label}</span>
                                        <span class="codeString">${codeString}</span>
                                        <span class="filterType">${group.filterType}</span>
									</span>
                                </td>
                                <td>${group.description}</td>
                                <td>
                                    ${codeString}
                                </td>
                                <td>${group.createdDateTime}</td>
                                <td>
                                    <a href="/opmanager/categories-filter/edit/${group.id}?target=${target}" class="btn btn-gradient btn-xs">${op:message('M00087')}</a>
                                    <button type="button" class="btn btn-gradient btn-xs delete_item">${op:message('M00074')}</button>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div><!--// board_write E-->

            <c:if test="${empty pageContent.content}">
                <div class="no_content">
                    ${op:message('M00473')} <!-- 데이터가 없습니다. -->
                </div>
            </c:if>

            <div class="btn_all">
                <div class="btn_left mb0">
                    <c:if test="${isPopup}">
                        <a id="apply_list_data" href="#" class="btn btn-default btn-sm">적용</a>
                    </c:if>
                    <a id="delete_list_data" href="#" class="btn btn-default btn-sm">${op:message('M01034')}</a> <!-- 일괄삭제 -->
                </div>
                <div class="btn_right mb0">
                    <a href="/opmanager/categories-filter/create?target=${target}" class="btn btn-active btn-sm"><span class="glyphicon glyphicon-plus"></span> 등록</a>
                </div>
            </div>

            <page:pagination-jpa />
        </form>

        <%--<div class="board_guide">
            <br/>
            <p class="tip">[안내]</p>
            <p class="tip">
                * 안내 메시지
            </p>
        </div>--%>
    </div>
</div>

<script type="text/javascript">
    let target = '${target}';

    $(function() {
        // 목록데이터 - 삭제처리
        $('#delete_list_data').on('click', function() {
            Common.updateListData("/opmanager/categories-filter/delete-list", "선택된 필터를 삭제 하시겠습니까?\n카테고리와 상품에 선택되어 있는 필터 정보도 초기화 됩니다.");
        });

        // 목록데이터 - 선택 삭제처리
        $('.delete_item').on('click', function(e) {
            e.preventDefault();
            $('#check_all').prop("checked", false);

            $(this).closest("table").find('input[name=id]:enabled').prop("checked", false);
            $(this).closest("tr").find('input[name=id]').prop("checked", true);

            Common.updateListData("/opmanager/categories-filter/delete-list", "선택된 필터를 삭제 하시겠습니까?\n카테고리와 상품에 선택되어 있는 필터 정보도 초기화 됩니다.");
        });

        $('#apply_list_data').on('click', function() {
            var $form = $('#listForm');
            var $ids = $form.find('input[name=id]:checked');

            for (var i=0; i<$ids.length; i++) {
                var id = $ids.eq(i).val();

                appFilterGroup(id);
            }
        });
    });

    function appFilterGroup(id) {
        var $group = $('#group_'+id);

        if ($group.length > 0) {
            var group = {
                'id' : $group.find('.id').text(),
                'label' : $group.find('.label').text(),
                'codeString' : $group.find('.codeString').text(),
                'filterType' : $group.find('.filterType').text()
            };

            var areaId = '${param.areaId}';

            opener.appFilterGroup(areaId, group);
        }
    }
</script>