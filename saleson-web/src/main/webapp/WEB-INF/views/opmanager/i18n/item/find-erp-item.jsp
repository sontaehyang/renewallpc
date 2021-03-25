<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions" %>
<%@ taglib prefix="shop"	uri="/WEB-INF/tlds/shop" %>

    <div class="popup_contents">
        <div class="item_list">
            <h3><span>관리코드를 입력하세요</span></h3>
            <div>
                <input type="text" id="itemCode" title="관리코드" class="full required"/>
            </div>
        </div>

        <div class="btn_center">
            <button type="button" class="btn btn-default op-erp-add"><span>추가</span></button>
            <button type="button" class="btn btn-default op-erp-cancel"><span>취소</span></button>
        </div>
    </div>

<script type="text/javascript">
    // ERP 연동 (조회)
    $(function() {
        // 추가
        $('.op-erp-add').on('click', function () {
            var itemCode = $('#itemCode').val();

            if (itemCode == null || itemCode == '') {
                alert('관리코드를 입력하세요.');
                return false;
            }

            $.post('/opmanager/item/find-erp-item', {'itemCode': itemCode}, function(response) {
                Common.responseHandler(response, function () {
                    if (response.data != null) {
                        opener.$('#stockCode').val(response.data.itemCode);
                        opener.$('#itemName').val(response.data.itemName);

                        alert('등록되었습니다.');
                        self.close();
                    } else {
                        alert('없는 관리코드입니다.');
                    }
                });
            }, 'json');
        });

        $('.op-erp-cancel').on('click', function () {
            self.close();
        });
    });
</script>