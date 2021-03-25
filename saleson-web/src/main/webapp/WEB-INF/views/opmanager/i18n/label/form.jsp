<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" 	uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="op" 	uri="/WEB-INF/tlds/functions" %>
<%@ taglib prefix="fn" 	uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="page" tagdir="/WEB-INF/tags/page" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="shop"	uri="/WEB-INF/tlds/shop" %>

<div class="location">
    <a href="#"></a> &gt;  <a href="#"></a> &gt; <a href="#" class="on"></a>
</div>

<form:form modelAttribute="label" method="post" enctype="multipart/form-data">
    <form:hidden path="labelType" value="ITEM" /> <!-- 상품 라벨 고정 (다른 타입의 라벨 추가 사용시 hidden 제거) -->

    <h3><span>라벨 관리</span></h3>
    <div class="board_write">
        <table class="board_write_table">
            <caption>라벨 관리</caption>
            <colgroup>
                <col style="width:150px;">
                <col style="width:auto;">
            </colgroup>
            <tbody>
                <!-- 다른 타입의 라벨 추가 사용시 주석 해제 -->
                <%--<tr>
                    <td class="label">라벨 타입</td>
                    <td>
                        <div>
                            <form:select path="labelType" title="라벨 타입">
                                <c:forEach var="list" items="${labelTypes}">
                                    <form:option value="${list.code}">${list.title}</form:option>
                                </c:forEach>
                            </form:select>
                        </div>
                    </td>
                </tr>--%>
                <tr>
                    <td class="label">
                        <p>
                            라벨 이미지
                            <span class="require">*</span>
                        </p>
                        <p>(auto * 40px)</p>
                    </td>
                    <td>
                        <div>
                            <button type="button" id="add_detail_image_file" style="display:none" class="table_btn"><span>+ ${op:message('M00984')}</span></button> <!-- 이미지추가 -->

                            <p class="text-info text-sm">
                                * 라벨 이미지는 1개만 등록이 가능합니다.<br/>
                                * 등록 / 수정시 이미지가 존재할 경우 이미지가 교체가 됩니다.
                            </p>

                            <p style="margin:10px 0;">
                                <input type="file" id="imageFile" name="imageFile" />
                            </p>

                            <p id="item_details_images">
                                <c:if test="${!empty label.image}">
                                    <img src="${label.imageSrc}" class="label_image" alt="${label.description}" />
                                   <%-- <a href="javascript:deleteImage();" class="delete_item_image"><img src="/content/opmanager/images/icon/icon_x.gif" alt="" /></a>--%>
                                    <input type="hidden" id="image" name="image" value="${label.image}" />
                                </c:if>
                            </p>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="label">라벨 설명 <span class="require">*</span></td>
                    <td>
                        <div>
                            <form:input path="description" title="라벨 설명" class="form-block required" maxlength="200" />
                        </div>
                    </td>
                </tr>
            </tbody>
        </table>
    </div> <!-- // board_write -->

    <!-- 버튼시작 -->
    <div class="btn_center">
        <button type="submit" class="btn btn-active"><span>${op:message('M00101')}</span></button> <!-- 저장 -->
        <a href="javascript:Link.list('${requestContext.managerUri}/label/list')" class="btn btn-default"><span>${op:message('M00037')}</span></a> <!-- 취소 -->
    </div>
    <!-- 버튼 끝-->

</form:form>

<script type="text/javascript">
    $(function() {
        // validator
        $('#label').validator(function() {
            // 이미지 첨부 검사
            if (($('#image').val() == null || $('#image').val() == '') && $('#imageFile').val() == '') {
                alert('라벨 이미지를 첨부해주세요.');
                return false;
            }

            if (!confirm('라벨 정보를 저장하시겠습니까?')) {
                return false;
            }
        });
    });

    /*function deleteImage() {
        var confirmMessage = '라벨 이미지를 삭제하시겠습니까?';

        if (confirm(confirmMessage)) {
            var param = {
                "id" : '${label.id}'
            };

            $.post('/opmanager/label/delete-image', param, function(response) {
                Common.responseHandler(response, function() {
                    $('#item_details_images').empty();
                });
            });
        }
    }*/
</script>