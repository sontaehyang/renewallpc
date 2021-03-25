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
    <h1 class="popup_title">Batch Job</h1> <!-- Batch Job -->
    <div class="popup_contents">
        <form:form id="listForm" modelAttribute="batchJob" method="post">
            <input type="hidden" id="batchJobId" name="batchJobId" value="<c:out escapeXml="true" value="${param.batchJobId}" />" />
            <div class="board_write">
                <h3><span>Batch Job</span></h3>
                <table class="board_list_table" summary="Batch Job"> <!-- Batch Job -->
                    <caption>Batch Job</caption>
                    <colgroup>
                        <col style="width:30%;" />
                        <col style="width:30%;" />
                        <col style="width:10%;" />
                        <col style="width:15%;" />
                        <col style="width:15%;" />
                        <col style="width:10%;" />
                    </colgroup>
                    <thead>
                    <tr>
                        <th scope="col">작업명</th> 		 	 <!-- 작업명 -->
                        <th scope="col">작업실행 메서드명</th> <!-- 작업실행 메서드명 -->
                        <th scope="col">트리거 종류<br>(1:심플, 2:크론)</th>  <!-- 트리거 종류 -->
                        <th scope="col">반복 시간</th> 	 <!-- 반복 시간 -->
                        <th scope="col">반복 표현식</th> 	 <!-- 반복 표현식 -->
                        <th scope="col">배치 상태<br>(1:실행중, 2:정지)</th>  <!-- 배치 상태 -->
                    </tr>
                    </thead>
                    <tbody>
                        <tr>
                            <td>
                                <form:input path="jobName" class="required" title="" maxlength="100" style="width:250px;"/>
                            </td>
                            <td>
                                <form:input path="jobMethod" class="required" title="" maxlength="100" style="width:300px;"/>
                            </td>
                            <td>
                                <form:select path="triggerType" class="input-txt" style="width:50px;">
                                    <form:option value="1" selected="selected" label="1" />
                                    <form:option value="2"  label="2" />
                                </form:select>
                            </td>
                            <td>
                                <form:input path="triggerRepeatSeconds" class="required" title="" maxlength="100" style="width:200px;"/>
                            </td>
                            <td>
                                <form:input path="triggerCronExpression" class="required" title="" maxlength="100" style="width:200px;"/>
                            </td>
                            <td>
                                <form:select path="batchStatus" class="input-txt" style="width:50px;">
                                    <form:option value="1" selected="selected" label="1" />
                                    <form:option value="2" label="2" />
                                </form:select>
                            </td>
                        </tr>
                    </tbody>
                </table>
            </div><!--// board_write E-->

            <div class="btn_all">
                <div class="btn_right mb0">

                </div>
            </div>
        </form:form>
    </div>
</div>

<script type="text/javascript">
    var $form = $('#listForm');

    $(document).ready(function() {

        $($form).validator(function() {

        });

        var $btn_right = $('.btn_right');

        if ($('#batchJobId').val()) {
            $btn_right.append('<button type="button" id="update_list_data" onclick="updateData()" class="btn btn-default btn-sm">수정</button>');
        } else {
            $btn_right.append('<button type="button" id="update_list_data" onclick="createData()" class="btn btn-default btn-sm">등록</button>');
        }


    });

    // 목록데이터 - 수정처리
    function updateData() {
        if (!textValidation()) {
            return;
        }

        if (!confirm("선택된 데이터를 전송하시겠습니까?")) {
            $form.find('table input, table select').prop('disabled', false);
            return;
        }

        $.post("/opmanager/batch-job/update", $form.serialize(), function(response) {
            Common.responseHandler(response, function(response){
                opener.location.reload();
                location.reload();
            });
        });

    }


    function createData() {
        if (!textValidation()) {
            return;
        }

        if (!confirm("선택된 데이터를 등록하시겠습니까?")) {
            $form.find('table input, table select').prop('disabled', false);
            return;
        }

        $.post("/opmanager/batch-job/create", $form.serialize(), function(response) {
            Common.responseHandler(response, function(response){
                opener.location.reload();
                location.reload();
            });
        });

    }

    function textValidation() {
        var jobName 			= $('input[name=jobName]').val();
        var jobMethod 			= $('input[name=jobMethod]').val();
        var triggerType 		= $('input[name=triggerType]').val();
        var triggerRepeatSeconds = $('input[name=triggerRepeatSeconds]').val();
        var triggerCronExpression = $('input[name=triggerCronExpression]').val();
        var batchStatus 	= $('input[name=batchStatus]').val();

        var booleanVal = true;

        if (jobName == '') {
            alert("작업명을 입력하세요");
            booleanVal = false;
        } else if(jobMethod == ''){
            alert("메서드명을 입력하세요");
            booleanVal = false;
        } else if(triggerType == ''){
            alert("트리거 종류를 입력하세요. 예) 심플:1, 크론:2");
            booleanVal = false;
        } else if(triggerRepeatSeconds == '' && triggerCronExpression == ''){
            alert("반복 시간 또는 표현식을 입력하세요");
            booleanVal = false;
        } else if(batchStatus == ''){
            alert("배치 상태를 입력하세요. 예) 실행중:1, 정지:2");
            booleanVal = false;
        }

        return booleanVal;
    }


</script>