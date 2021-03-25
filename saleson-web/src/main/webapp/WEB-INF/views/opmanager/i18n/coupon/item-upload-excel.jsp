<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions" %>
<%@ taglib prefix="shop"	uri="/WEB-INF/tlds/shop" %>

<style>
    .popup_contents h2 {margin-top: 0; margin-bottom: 5px;}
    #result {padding: 15px 10px 30px 10px; height: 165px; overflow-y: auto; margin-bottom: 30px; border: 1px dotted #ccc; color: #444; background:#ffffdd}
    #result p.upload_file {font-weight: bold; font-size: 13px; color: #222; text-decoration: underline; margin-bottom: 10px;}
    #result p.sheet {padding-top: 15px;}
    #result p.error-cell {color: #dc4618; padding: 0 0 5px 10px; font-size: 11px;}
    #result span {display: inline-block; font-weight: bold; width: 150px; color: #000;}
    .table_btn {position: absolute; top: 77px; right: 18px;}
    .board_write_table {margin-top: 10px;}
</style>
<div class="popup_wrap">
    <h1 class="popup_title">엑셀 업로드</h1>



    <div class="popup_contents">

        <form id="itemExcelForm" name="itemExcelForm" enctype="multipart/form-data">

            <h2>Upload</h2>

            <table class="board_write_table" summary="엑셀 업로드">
                <caption>엑셀등록 </caption>
                <colgroup>
                    <col style="width: 100px" />
                    <col style="" />
                </colgroup>
                <tbody>
                <tr>
                    <td class="label">${op:message('M01070')}</td> <!-- 파일 -->
                    <td>
                        <div>

                            <p class="default_file"><input type="file" class="upload" id="uploadInputBox" name="file" /></p>
                        </div>
                    </td>
                </tr>
                </tbody>
            </table>
            <div class="tip mt10">
                - <strong>Excel</strong>${op:message('M01420')}<br/> <!-- 파일(.xlsx)만 등록할 수 있습니다. -->
                - 미등록 상품이 포함될 경우 업로드가 취소됩니다.
            </div>

            <p class="popup_btns">
                <button type="button" class="btn btn-active" onclick="javascript:sendAjaxData();">${op:message('M00088')}</button> <!-- 등록 -->
                <a href="javascript:closeDownloadPopup();" class="btn btn-default">${op:message('M00037')}</a> <!-- 취소 -->
            </p>
        </form>
        <form id="sample-download" action="${requestContext.managerUri}/coupon/sample-download-excel" method="post">
            <p class="popup_btns">
                <button type="button" class="table_btn" id="sample"><span>${op:message('M01280')}</span></button> <!-- 샘플 다운로드 -->
            </p>
        </form>
    </div>
</div>

<iframe id="downloadFrame" name="downloadFrame" style="display: none;"></iframe>

<script>
    $(function() {
        $('#sample').on('click', function() {
            $('#sample-download').submit();
            $('#loading-dimmed').hide();
        });
    });

    function sendAjaxData() {

        var formData = new FormData(),
            url = '${requestContext.managerUri}/coupon/item-upload-excel',
            chosenItemSize = opener.$('.op-chose-item').size(),
            file = $('#uploadInputBox')[0].files[0];

        formData.append('url', url);
        formData.append('chosenItemSize', chosenItemSize);
        formData.append('file', file);

        $.ajax({
            url : url,
            type : "POST",
            async : true,
            data : formData,
            processData: false,
            contentType: false,
            cache: false,
            success : function (response) {
                opener.$('#op-chosen-item-list').append(response);
                closeDownloadPopup();
            },
            error : function (response) {
                alert('상품 업로드에 실패하였습니다.');
            }
        });
    }

    function closeDownloadPopup(){
        self.close();
    }
</script>