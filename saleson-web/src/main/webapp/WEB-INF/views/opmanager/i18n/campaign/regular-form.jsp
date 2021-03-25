<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="page" tagdir="/WEB-INF/tags/page" %>
<%@ taglib prefix="op" uri="/WEB-INF/tlds/functions" %>

<div class="location">
    <a href="#"></a> &gt; <a href="#"></a> &gt; <a href="#" class="on"></a>
</div>

<h3><span>${op:message('MENU_13202')}</span></h3>
<form:form modelAttribute="campaignRegular" method="post" action="/opmanager/campaign/regular/create">

    <div id="campaignBatchDto">
        <input type="hidden" name="searchWhere" value="${campaign.id == null ? campaignBatchDto.where : campaign.searchWhere}"/>
        <input type="hidden" name="query" value="${campaign.id == null ? campaignBatchDto.query : campaign.query}"/>
        <input type="hidden" name="groupCode" value="${campaign.id == null ? campaignBatchDto.groupCode : campaign.groupCode}"/>
        <input type="hidden" name="levelId" value="${campaign.id == null ? campaignBatchDto.levelId : campaign.levelId}"/>
        <input type="hidden" name="startOrderAmount1" value="${campaign.id == null ? campaignBatchDto.startOrderAmount1 : campaign.startOrderAmount1}"/>
        <input type="hidden" name="endOrderAmount1" value="${campaign.id == null ? campaignBatchDto.endOrderAmount1 : campaign.endOrderAmount1}"/>
        <input type="hidden" name="startOrderAmount2" value="${campaign.id == null ? campaignBatchDto.startOrderAmount2 : campaign.startOrderAmount2}"/>
        <input type="hidden" name="endOrderAmount2" value="${campaign.id == null ? campaignBatchDto.endOrderAmount2 : campaign.endOrderAmount2}"/>
        <input type="hidden" name="startCartAmount" value="${campaign.id == null ? campaignBatchDto.startCartAmount : campaign.startCartAmount}"/>
        <input type="hidden" name="endCartAmount" value="${campaign.id == null ? campaignBatchDto.endCartAmount : campaign.endCartAmount}"/>
        <input type="hidden" name="cartCount" value="${campaign.id == null ? campaignBatchDto.cartCount : campaign.cartCount}"/>
        <input type="hidden" name="lastLoginDateType" value="${campaign.id == null ? campaignBatchDto.lastLoginDateType : campaign.lastLoginDateType}"/>
        <input type="hidden" name="lastLoginDate" value="${campaign.id == null ? campaignBatchDto.lastLoginDate : campaign.lastLoginDate}"/>
    </div>

    <input type="hidden" name="campaignRegularId" value="${campaignRegular.id}" />

    <table class="board_write_table">
        <colgroup>
            <col style="width: 200px;"/>
            <col style="width: auto;"/>
        </colgroup>
        <tr>
            <td class="label">제목</td>
            <td>
                <div>
                    <form:input path="title" class="full" title="${op:message('M00275')}" maxlength="255"/>
                </div>
            </td>
        </tr>
        <tr>
            <td class="label">발송구분</td>
            <td>
                <div>
                    <form:radiobutton path="messageType" value="0" label="일반" checked="true"/>
                    <form:radiobutton path="messageType" value="1" label="광고"/>
                </div>
            </td>
        </tr>
        <tr>
            <td class="label">총 발송대상</td>
            <td>
                <div>
                    <p class="text-info text-sm">
                        * 문자 / PUSH 수신동의 여부에 따라 발송 건수가 달라질 수 있습니다.
                    </p>

                    <input type="text" value="${groupName}" disabled />
                    <input type="text" value="${levelName}" disabled />
                    발송대상 : ${op:numberFormat(campaignUserCount)}건
                </div>
            </td>
        </tr>
        <tr>
            <td class="label">발송수단</td>
            <td>
                <div>
                    <p class="text-info text-sm receiveYn">
                        * 문자 발송대상 : ${op:numberFormat(smsReceiveCount)}건
                    </p>

                    <form:radiobutton path="sendType" value="0" label="문자" checked="true"/>
                    <form:radiobutton path="sendType" value="1" label="PUSH"/>
                    <form:radiobutton path="sendType" value="2" label="PUSH 우선 (PUSH 수신거부자는 문자로 발송) "/>
                </div>
            </td>
        </tr>
        <tr>
            <td class="label">발송주기</td>
            <td>
                <div>
                    <form:radiobutton path="sendCycle" value="month" label="매월" checked="true" />
                    <select id="sendDate" name="sendDate">
                        <c:forEach begin="1" end="31" step="1" var="i">
                            <c:if test="${i < 10 }">
                                <option value="0${i}" label="0${i}" ${op:selected(campaignRegular.sendDate, i)}/>
                            </c:if>
                            <c:if test="${i >= 10 }">
                                <option value="${i}" label="${i}" ${op:selected(campaignRegular.sendDate, i)}/>
                            </c:if>
                        </c:forEach>
                    </select>일&nbsp;

                    <form:radiobutton path="sendCycle" value="week" label="매주" />
                    <select id="sendDay" name="sendDay">
                        <c:forEach items="${weekDays}" var="i">
                            <option value="${i.value}" label="${i.label}" ${op:selected(campaignRegular.sendDay, i.value)}>${i.label}</option>
                        </c:forEach>
                    </select>&nbsp;

                    <form:radiobutton path="sendCycle" value="daily" label="매일" />
                </div>
                <div>
                    발송시간 :
                    <select id="sendTime" name="sendTime">
                        <c:forEach begin="0" end="23" var="i">
                            <c:if test="${i < 10}">
                                <c:set var="i">0${i}</c:set>
                            </c:if>
                            <option value="${i}" label="${i}시" ${op:selected(campaignRegular.sendTime, i)}/>

                        </c:forEach>
                    </select>
                </div>
            </td>
        </tr>
        <tr>
            <td class="label">발송기간</td>
            <td>
                <div>
                    <span class="datepicker">
                        <form:input path="startSendDate" class="datepicker" maxlength="8" title="정기발송시작일"/>
                    </span>
                    <span class="wave">~</span>
                    <span class="datepicker">
                        <form:input path="endSendDate" class="datepicker" maxlength="8" title="정기발송종료일"/>
                    </span>
                </div>
            </td>
        </tr>
        <tr>
            <td class="label">
                발송문구
            </td>
            <td>
                <div>
                    <p class="pb5">
                        <button type="button" class="btn btn-dark-gray btn-sm" id="campaignSendList">이전 발송 메시지 불러오기</button>
                        <button type="button" class="btn btn-default btn-sm" id="urlCode"
                                onclick="window.open('/opmanager/campaign/code-list/','code-list','width=500,height=500');">대체코드</button>
                    </p>
                    <form:textarea path="content" cssClass="required _filter content" title="내용" style="height:150px;"/>
                </div>
                <div>
                    <p class="text-info text-sm">
                        * 이미지파일은 200kb 이하, JPG,JPEG 파일만 가능합니다.
                    </p>
                    <input type="file" id="file" />

                    <img id="foo" src="${campaignRegular.imageUrl}" class="item_image size-100 "/>
                    <form:hidden path="imageUrl" value="${campaignRegular.imageUrl}"/>
                </div>
            </td>
        </tr>

        <c:set var="urlIndex" value="0"/>

        <tr>
            <td class="label">
                URL 입력
            </td>
            <td class="ga">
                <div>
                    <p class="text-info text-sm">
                        * URL 추가 버튼을 클릭하여 입력항목을 추가할 수 있습니다. (최대 5개)
                    </p>
                    <button type="button" class="btn btn-default btn-sm" id="addUrl">URL 추가</button>
                </div>

                <div id="campaignUrlArea">
                    <c:forEach var="url" items="${campaignRegular.urlList}" varStatus="i">
                        <div>
                            <span>{${url.changeCode}}</span>
                            <input type="text" class="form-half" title="URL" name="campaign_url" value="${url.contents}" />
                            <input type="hidden" name="campaign_eventcode" value="${url.eventCode}"/>
                            <span>[${url.eventCode}]</span>
                        </div>
                    </c:forEach>
                </div>
            </td>
        </tr>
        <tr>
            <td class="label">
                쿠폰발행
            </td>
            <td>
                <div>
                    <p class="text-info text-sm">
                        * 쿠폰 발급 대상이 전체인 경우 조회 가능합니다. <br/>
                        * 회원그룹/등급 기준으로 캠페인 발송대상을 선택한 경우 쿠폰 발급대상[회원]이 '회원 등급별' 이고, 회원 그룹/등급이 일치하는 쿠폰만 조회 가능합니다.
                    </p>
                    <p class="pb5">
                        <button type="button" class="btn btn-dark-gray btn-sm" id="couponList">쿠폰선택</button>
                        <form:hidden path="couponId"/>
                        <div id="couponInfo">${couponName != '' ? couponName : ''}</div>
                    </p>
                </div>
            </td>
        </tr>
        <tr>
            <td class="label">
                GA 캠페인 설정
            </td>
            <td class="ga">
                <div>
                    <p>
                        <span>utm_medium</span>
                        <input type="text" title="utm_medium" name="utmMedium" id="utmMedium" value="${campaignRegular.utmMedium}" class="form-half" placeholder="매체 및 광고형태 (예시: event)"/>
                    </p>
                    <p class="pt5">
                        <span>utm_campaign</span>
                        <input type="text" title="utm_campaign" id="utmCampaign" name="utmCampaign" value="${campaignRegular.utmCampaign}" class="form-half" placeholder="목적 (예시 : 202010-mms-event)"/>
                    </p>
                    <p class="pt5">
                        <span>utm_item</span>
                        <input type="text" title="utm_item" id="utmItem" name="utmItem" value="${campaignRegular.utmItem}" class="form-half" placeholder="유료 키워드 설정 (예시: naver)"/>
                    </p>
                    <p class="pt5">
                        <span>utm_content</span>
                        <input type="text" title="utm_content" id="utmContent" name="utmContent" value="${campaignRegular.utmContent}" class="form-half" placeholder="유료 광고일 경우 구분 수단"/>
                    </p>
                </div>

            </td>
        </tr>
    </table>

    <br/>

    <div class="btn_all">
        <div class="btn_left mb0"></div>
        <div class="btn_center mr150">
            <button type="submit" class="btn btn-active">${op:message('M00101')}</button>
            <button type="button" class="btn btn-success " id="inputMessage" data-toggle="modal">
                테스트발송
            </button>
            <a type="button" class="btn btn-default" onclick="history.go(-1);">취소</a>
        </div>
    </div>

    <div class="modal fade" id="gridSystemModal" tabindex="-1" role="dialog" aria-labelledby="gridSystemModalLabel">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="gridSystemModalLabel">테스트 발송</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                </div>
                <div class="modal-body" style="padding:0">
                    <table class="board_view op-ums-send-test-table">
                        <input type="hidden" id="applicationNo" name="applicationNo" />
                        <input type="hidden" id="uuid" name="uuid" />
                        <input type="hidden" id="deviceType" name="deviceType" />
                        <input type="hidden" id="pushToken" name="pushToken" />
                        <colgroup>
                            <col style="width: 100px;"/>
                            <col style="width: auto;"/>
                        </colgroup>
                        <tr>
                            <td class="label">
                                메시지
                            </td>
                            <td>
                                <div>
                                    <textarea id="previewContent" name="previewContent" title="미리보기" readonly style="height: 200px;"></textarea>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="label">전화번호</td>
                            <td>
                                <div>
                                    <p class="text-info text-sm">
                                        * 하이픈(-) 없이 입력하세요. <br />
                                        * 테스트 발송시 요금이 부과됩니다.
                                    </p>
                                    <input type="text" id="phoneNumber" name="phoneNumber" class="_number" maxlength="11" />
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="label">
                                푸시 정보
                            </td>
                            <td>
                                <div>
                                    <p class=" text-info text-sm pb5">
                                        * Push 수신이 가능한 회원을 조회해 주세요.
                                        <button type="button" class="btn btn-dark-gray btn-sm" id="applicationInfo">조회</button></p>
                                    아이디
                                    <input type="hidden" id="userId" name="userId" />
                                    <input type="text" id="loginId" name="loginId" readonly /> &nbsp;


                                    이름
                                    <input type="text" id="userName" name="userName" readonly />
                                </div>
                            </td>
                        </tr>
                    </table>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">취소</button>
                    <button type="button" class="btn btn-active" id="sendTest">테스트 발송</button>
                </div>
            </div><!-- /.modal-content -->
        </div><!-- /.modal-dialog -->
    </div>

</form:form>

<page:javascript>
    <script type="text/javascript">

        var UPLOAD_FILE_INFO = '';
        var MMS_IMAGE_UPLOAD = '/opmanager/campaign/create/mms-image-url';

        var PREFIX_EVENT_VIEW = '${prefixEventView}';
        var URL_EVENT_CODES = [];
        var $CAMPAIGN_URL_AREA = $('#campaignUrlArea');

        // 이미지 미리보기
        function readURL(input) {
            if (input.files && input.files[0]) {
                var reader = new FileReader();
                reader.onload = function (e) {
                    $('#foo').attr('src', e.target.result);
                };
                reader.readAsDataURL(input.files[0]);
            }
        }

        $(function () {

            initUrlEventCodes();

            sendTypeTargetCount();

            var sendCycle = '${campaignRegular.sendCycle}';
            if (sendCycle == 'month' || sendCycle == '') {
                $('#sendDate').attr('disabled', false);
                $('#sendDay').attr('disabled', true);

            } else if (sendCycle == 'week') {
                $('#sendDate').attr('disabled', true);
                $('#sendDay').attr('disabled', false);

            } else if (sendCycle == 'daily') {
                $('#sendDay').attr('disabled', true);
                $('#sendDate').attr('disabled', true);
            }

            var startSendDateTime = '${campaignRegular.startSendDate}';
            var endSendDateTime = '${campaignRegular.endSendDate}';

            $('#startSendDate').val(startSendDateTime.substring(0, 8));
            $('#endSendDate').val(endSendDateTime.substring(0, 8));

            $('#addUrl').on('click', function () {
                var template = $('#addUrlTemplate').html(),
                    maxButton = 5,
                    length = $CAMPAIGN_URL_AREA.find('input[type=text]').length,
                    index = length;

                if (maxButton == length) {
                    alert('URL 입력란은 최대' + maxButton + '개 입니다.');
                    return false;
                }

                template = template.replaceAll('{{changeCode}}', 'url_' + index)
                        .replaceAll('{{eventCode}}', URL_EVENT_CODES[index])
                        .replaceAll('{{urlValue}}', 'https://');

                $CAMPAIGN_URL_AREA.append(template);

                var content = $('#content').val();
                content +='{url_' + index + '}';
                $('#content').val(content);

            });

            $("#campaignRegular").validator(function () {

                var campaignRegularId = $('input[name=campaignRegularId]').val();
                if (campaignRegularId != '') {
                    $('#campaignRegular').attr('action', '/opmanager/campaign/regular/create/' + campaignRegularId);
                }

                if (!checkDate($('#startSendDate').val(), $('#startSendDate').val(), $('#sendTime').val())) {
                    return false;
                }

                if ($('input[name=searchQuery]').val() == '') {
                    $('input[name=searchWhere]').val('');
                }

                makeUrlAddForm();
            });


            $('#content').on('keyup', function () {
                showTextByte($(this));
            });

            $('#inputMessage').on('click', function () {
                var content = $('#content').val();
                var urlLength = $('#campaignUrlArea input').length;

                for (let i = 0; i < urlLength; i++) {
                    var id = 'url_' + i,
                        tag = '{' + id + '}',
                        selector = 'input[name=campaign_eventcode]';

                    var value = $(selector).eq(i).val();
                    value = PREFIX_EVENT_VIEW +'/'+ value;
                    content = content.replaceAll(tag, value);
                }

                $('#previewContent').val(content);
            });


            $('#sendTest').on('click', function () {

                if (!$('#sendType1').is(':checked')) {
                    if ($('#pushToken').val() == '') {
                        alert('푸시 정보 조회를 해주세요.');
                        return false;
                    }
                }

                if ($('input[name=phoneNumber]').eq(0).val() == '') {
                    alert('전화번호를 입력해주세요.');
                    return false;
                }

                // 테스트 발송 시 DB에 campaign url 정보가 없어서 샘플화면에 변환된 url을 발송
                var content = $('#content').val();
                $('#content').val($('#previewContent').val())

                var params = $('#campaignRegular').serialize();
                $.post('/opmanager/campaign/create/send-test', params, function (response) {
                    if (response.isSuccess) {
                        alert('발송되었습니다.');
                    } else {
                        alert(response.errorMessage);
                    }
                });

                $('#content').val(content);
            });

            $('input[name=sendType]').change(function() {
                sendTypeTargetCount();
            });


            $('input[name=sendCycle]').on('change', function () {
                if ($(this).val() == 'month') {
                    $('#sendDate').attr('disabled', false);
                    $('#sendDay').attr('disabled', true);

                } else if ($(this).val() == 'week') {
                    $('#sendDate').attr('disabled', true);
                    $('#sendDay').attr('disabled', false);

                } else if ($(this).val() == 'daily') {
                    $('#sendDay').attr('disabled', true);
                    $('#sendDate').attr('disabled', true);
                }
            });

            $('#campaignSendList').on('click', function () {
                Common.popup('/opmanager/campaign/create/send-message-list', '', 800, 850, 1);
            });

            $('#applicationInfo').on('click', function () {
                Common.popup('/opmanager/campaign/create/application-info', 'push_info', 770, 800, 1);
            });

            $('#couponList').on('click', function () {
                var startSendDate = $('input[name=startSendDate]').val(),
                    endSendDate = $('input[name=endSendDate]').val(),
                    sendTime = $('#sendTime').val(),
                    levelId = $('input[name=levelId]').val(),
                    url = '/opmanager/coupon/campaign-list/regular?levelId=' + levelId + '&sendDate=' + startSendDate + sendTime + '&endSendDate=' + endSendDate + sendTime;

                if (!checkDate(startSendDate, endSendDate, sendTime)) {
                    return false;
                }

                Common.popup(url, '', 1100, 850, 1);
            });

            $('#file').on('change', function(e) {
                var files = e.target.files;
                var file = files[0];
                var reader = new FileReader();
                var fileExt = e.target.value;
                var fileSize = file.size;
                var maxSize = 200 * 1024;  // 200KB

                if(fileSize > maxSize) {
                    alert("파일크기는 200KB 이내로 등록 가능합니다.");
                    return false;
                }

                // 확장자 체크
                fileExt = fileExt.slice(fileExt.indexOf(".") + 1).toLowerCase();
                if(fileExt != "jpg" && fileExt !="jpeg"){
                    alert("이미지 파일(jpg, jpeg) 만 등록 가능합니다.");
                    return false;
                }

                reader.onload = function (e) {
                    UPLOAD_FILE_INFO = e.target.result;
                    setMmsImagePath();
                    $('#foo').attr('src', UPLOAD_FILE_INFO);
                };
                reader.onerror = function(error){
                    alert('Error');
                };

                reader.readAsDataURL(file);

            });

            $('#inputMessage').on('click', function () {
                if ($('#content').val() == '') {
                    alert('발송문구를 입력해주세요.');
                    return false;
                }

                $('#gridSystemModal').modal();
            });
        });

        function setMmsImagePath() {

            var base64 = UPLOAD_FILE_INFO;

            if (base64 != '') {

                var url = MMS_IMAGE_UPLOAD,
                    param = {
                        'encodingFile': base64
                    };

                $.post(url, param, function (response) {
                    Common.responseHandler(response, function () {
                        var data = response.data;
                        $('#imageUrl').val(data);
                    });
                }, 'json');
            }
        }

        // 푸시 정보 콜백
        function handleFindPushCallback(response) {
            var phoneNumber = response.phoneNumber.replaceAll('-', '');

            $('#phoneNumber').val(phoneNumber);
            $('#userId').val(response.userId);
            $('#loginId').val(response.loginId);
            $('#userName').val(response.userName);
            $('#pushToken').val(response.pushToken);
            $('#applicationNo').val(response.applicationNo);
            $('#uuid').val(response.uuid);
            $('#deviceType').val(response.deviceType);
        }

        // 발송 메시지 콜백
        function handleFindCampaigncallback (response, urlList) {
            $('#campaignUrlArea').empty();

            $('#title').val(response.title);
            $('#content').val(response.content);
            var template = $('#addUrlTemplate').html(),
                urlLength = response.urlLength;

            for (let i = 0; i < urlLength; i++) {
                template = $('#addUrlTemplate').html().replaceAll('{{changeCode}}', 'url_' + i)
                    .replaceAll('{{eventCode}}', URL_EVENT_CODES[i]);

                $CAMPAIGN_URL_AREA.append(template);

                $('input[name=campaign_url]').eq(i).val(urlList[i]);
            }

            $('#previewContent').val('');
        }

        function makeUrlAddForm() {
            var template = $('#urlTemplate').html(),
                $form = $('#campaignUrlArea');

            var html = '';

            $CAMPAIGN_URL_AREA.find('input[type=text]').each(function (i) {
                var $button = $CAMPAIGN_URL_AREA.find('input[type=text]').eq(i);
                var url = $button.val();
                var changeCode = "url_" + i;
                var rawhtml = template;

                rawhtml = rawhtml.replaceAll('{{buttonIndex}}', i)
                    .replaceAll('{{changeCode}}', changeCode)
                    .replaceAll('{{url}}', url)
                    .replaceAll('{{eventCode}}', URL_EVENT_CODES[i]);

                html += rawhtml + '\n';
            });

            $form.html(html);
        }

        function showTextByte($_this) {
            var text = $_this.val();
            var stringByteLength = (function (s, b, i, c) {
                for (b = i = 0; c = s.charCodeAt(i++); b += c >> 11 ? 3 : c >> 7 ? 2 : 1) ;
                return b
            })(text);

            if (stringByteLength == 0) {
                $_this.parent().find('span.add-text-view').remove();
                return;
            }

            var pattern = /{[a-zA-Z0-9_-]+}/gi;
            var matchTexts = text.match(pattern);
            var matchTextDefaultLength = 8;

            if (matchTexts) {
                text = text.replace(pattern, '');
                var replaceStringByteLength = (function (s, b, i, c) {
                    for (b = i = 0; c = s.charCodeAt(i++); b += c >> 11 ? 3 : c >> 7 ? 2 : 1) ;
                    return b
                })(text);

                stringByteLength = replaceStringByteLength + (matchTextDefaultLength * matchTexts.length);
            }

            var messageType = "SMS"
            if (stringByteLength > 90 && stringByteLength < 2000) {
                messageType = "LMS";
            } else if (stringByteLength > 2000) {
                messageType = "LMS";
                alert("2000Bytes를 초과할 수 없습니다.");
            }

            $_this.parent().find('span.add-text-view').remove();
            $_this.parent().append('<span class="add-text-view">' + stringByteLength + '/2000 Bytes (' + messageType + ')</span>');
        }

        function initUrlEventCodes() {
            <c:if test="${not empty eventCodes}">
                <c:forEach var="code" items="${eventCodes}">
                    URL_EVENT_CODES.push('${code}');
                </c:forEach>
            </c:if>
        }

        function checkDateTimelength(date) {
            if (date < 10) {
                date = '0' + date;
            }
            return date;
        }

        function sendTypeTargetCount() {
            if ($('#sendType1').is(':checked')) {
                $('.receiveYn').text('* 문자 발송대상 : ${op:numberFormat(smsReceiveCount)}건');
            } else if ($('#sendType2').is(':checked')) {
                $('.receiveYn').text('* PUSH 발송대상 : ${op:numberFormat(pushReceiveCount)}건');
            } else {
                $('.receiveYn').text('* 문자, PUSH 발송대상 : ${op:numberFormat(smsPushReceiveCount)}건');
            }
        }

        function getSystemDate() {
            var systemDate = new Date();
            var systemNowYear = systemDate.getFullYear().toString();
            var systemNowMonth = (systemDate.getMonth() + 1).toString();
            var systemNowDay = systemDate.getDate().toString();
            var hour = systemDate.getHours();

            systemNowDay = checkDateTimelength(systemNowDay);
            systemNowMonth = checkDateTimelength(systemNowMonth);
            hour = checkDateTimelength(hour);

            return systemNowYear + systemNowMonth + systemNowDay + hour;
        }

        function checkDate(startDate, endDate, time) {
            if (startDate === '' || endDate === '') {
                alert("정기발송 기간을 입력해주세요.");
                return false;
            }

            if (startDate > endDate && endDate != '') {
                alert("정기발송기간 범위를 다시 입력해주세요.");
                return false;
            }

            var systemDate = getSystemDate();
            var checkDate = startDate + time;
            if (checkDate < systemDate || checkDate == systemDate) {
                alert('정기발송일시를 현재일시 이후로 설정해주세요.');
                return false;
            }

            return true;
        }

    </script>
    <script type="text/html" id="urlTemplate">
        <input type="hidden" name="urlList[{{buttonIndex}}].changeCode" value="{{changeCode}}"/>
        <input type="hidden" name="urlList[{{buttonIndex}}].contents" value="{{url}}"/>
        <input type="hidden" name="urlList[{{buttonIndex}}].eventCode" value="{{eventCode}}"/>
    </script>
    <script type="text/html" id="addUrlTemplate">
        <div>
            <span>{{{changeCode}}}</span>
            <input type="text" class="form-half" title="URL" name="campaign_url" value="{{urlValue}}"/>
            <input type="hidden" name="campaign_eventcode" value="{{eventCode}}"/>
            <span>[{{eventCode}}]</span>
        </div>
    </script>

</page:javascript>