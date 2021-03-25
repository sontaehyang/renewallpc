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

<div class="board_write">
    <h3><span>판매처 리스트</span></h3>
    <form:form modelAttribute="storeDto" method="get" enctype="multipart/form-data">
        <input type="hidden" name="target" value="${target}" />
        <table class="board_write_table" summary="판매처 리스트">
            <caption>판매처 리스트</caption>
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
                                <form:option value="NAME">판매처명</form:option>
                                <form:option value="ADDRESS">주소</form:option>
                                <form:option value="TEL_NUMBER">전화번호</form:option>
                            </form:select>
                            <form:input path="query" class="w360" title="상세검색 입력" />
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="label">시/도</td>
                    <td>
                        <div>
                            <form:select path="sido" title="시/도">
                                <form:option value="" label="전체" />
                                <c:forEach items="${sidoList}" var="code">
                                    <form:option value="${code.value}" label="${code.label}" />
                                </c:forEach>
                            </form:select>
                        </div>
                    </td>
                    <td class="label">백화점</td>
                    <td>
                        <div>
                            <form:radiobutton path="storeType" label="전체" value="" checked="checked" />
                            <c:forEach items="${storeTypes}" var="type" varStatus="i">
                                <form:radiobutton path="storeType" label="${type.title}" value="${type.code}" />
                            </c:forEach>
                        </div>
                    </td>
                </tr>
            </tbody>
        </table>

        <!-- 버튼시작 -->
        <div class="btn_all">
            <div class="btn_left">
                <button type="button" class="btn btn-dark-gray btn-sm" onclick="location.href='/opmanager/store/list'"><span class="glyphicon glyphicon-repeat"></span> ${op:message('M00047')}</button> <!-- 초기화 -->
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
            <table class="board_list_table" summary="판매처 리스트">
                <caption>판매처 리스트</caption>
                <colgroup>
                    <col style="width:4%;" />
                    <col style="width:15%;" />
                    <col style="width:10%;" />
                    <col style="width:auto;" />
                    <col style="width:8%;" />
                    <col style="width:8%;" />
                    <col style="width:8%;" />
                    <col style="width:13%;" />
                    <col style="width:10%;" />
                </colgroup>
                <thead>
                    <tr>
                        <th scope="col"><input type="checkbox" id="check_all" title="${op:message('M00169')}" /></th>
                        <th scope="col">판매처명</th>
                        <th scope="col">시/도</th>
                        <th scope="col">주소</th>
                        <th scope="col">백화점</th>
                        <th scope="col">전화번호</th>
                        <th scope="col">영업시간</th>
                        <th scope="col">등록일</th>
                        <th scope="col">${op:message('M00087')}/${op:message('M00074')}</th> <!-- 수정/삭제 -->
                     </tr>
                </thead>
                <tbody>
                    <c:forEach items="${pageContent.content}" var="store" varStatus="i" >
                        <tr>
                            <td><input type="checkbox" name="id" id="check" value="${store.id}" title=${op:message('M00169')} /></td>
                            <td class="tleft">
                                <div>
                                    <a href="/opmanager/store/edit/${store.id}">${store.name}</a>
                                </div>
                            </td>
                            <td>${store.sido}</td>
                            <td class="tleft">
                                <c:if test="${!empty store.newPost}">[${store.newPost}]</c:if> ${store.address} ${store.addressDetail}
                            </td>
                            <td>${store.storeType.title}</td>
                            <td>${store.telNumber}</td>
                            <td>
                                ${fn:substring(store.startTime, 0, 2)}:${fn:substring(store.startTime, 2, 4)} ~
                                ${fn:substring(store.endTime, 0, 2)}:${fn:substring(store.endTime, 2, 4)}
                            </td>
                            <td>${store.createdDateTime}</td>
                            <td>
                                <a href="/opmanager/store/edit/${store.id}" class="btn btn-gradient btn-xs">${op:message('M00087')}</a>
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
                <a id="delete_list_data" href="#" class="btn btn-default btn-sm">${op:message('M01034')}</a> <!-- 일괄삭제 -->
            </div>
            <div class="btn_right mb0">
                <a href="/opmanager/store/create" class="btn btn-active btn-sm"><span class="glyphicon glyphicon-plus"></span> 등록</a>
            </div>
        </div>

        <page:pagination-jpa />
    </form>
</div>

<script type="text/javascript">
    $(function() {
        // 목록데이터 - 삭제처리
        $('#delete_list_data').on('click', function() {
            Common.updateListData("/opmanager/store/delete-list", "선택된 판매처를 삭제하시겠습니까?");
        });

        // 목록데이터 - 선택 삭제처리
        $('.delete_item').on('click', function(e) {
            e.preventDefault();
            $('#check_all').prop("checked", false);

            $(this).closest("table").find('input[name=id]:enabled').prop("checked", false);
            $(this).closest("tr").find('input[name=id]').prop("checked", true);

            Common.updateListData("/opmanager/store/delete-list", "선택된 판매처를 삭제하시겠습니까?");
        });
    });
</script>