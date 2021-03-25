<%@page contentType="text/html;charset=UTF-8"%>
<%@page pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 	uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="op" 	uri="/WEB-INF/tlds/functions" %>
<%@ taglib prefix="fn" 	uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="page" tagdir="/WEB-INF/tags/page" %>
<%@ taglib prefix="module" 	tagdir="/WEB-INF/tags/modules"%>


<div class="location">
    <a href="#"></a> &gt;  <a href="#"></a> &gt; <a href="#" class="on"></a>
</div>


<div class="board_write">
    <form:form modelAttribute="configGoogleAnalytics" method="post" enctype="multipart/form-data">
        <h3><span>GoogleAnalytics 설정</span></h3>
        <table class="board_write_table pg-info" summary="PG 정보">
            <caption>GoogleAnalytics 설정</caption>
            <colgroup>
                <col style="width:170px;" />
                <col style="width: auto;" />
            </colgroup>
            <tbody>
                <tr>
                    <td class="label">범용 사이트 태그<span class="require">*</span></td>
                    <td>
                        <div>
                            <form:radiobutton path="commonTrackingFlag" value="false" cssClass="required" title="범용 사이트 태그" label="미적용"/>
                            <form:radiobutton path="commonTrackingFlag" value="true" cssClass="required" title="범용 사이트 태그" label="적용" />
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="label">추적 ID</td>
                    <td>
                        <div>
                            <form:input path="measurementId" class="form-block" title="추적 ID" />
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="label">전자상거래<span class="require">*</span></td>
                    <td>
                        <div>
                            <form:radiobutton path="ecommerceTrackingFlag" value="false" cssClass="required" title="전자상거래" label="미적용"/>
                            <form:radiobutton path="ecommerceTrackingFlag" value="true" cssClass="required" title="전자상거래" label="적용" />
                        </div>
                    </td>
                </tr>
                <tr class="ecommerce-tracking">
                    <td class="label">통화 종류</td>
                    <td>
                        <div>
                            <form:input path="currency" class="form-block" title="통화 종류" maxlength="3"/>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="label">통계<span class="require">*</span></td>
                    <td>
                        <div>
                            <form:radiobutton path="statisticsFlag" value="false" cssClass="required" title="통계" label="미적용"/>
                            <form:radiobutton path="statisticsFlag" value="true" cssClass="required" title="통계" label="적용" />
                        </div>
                    </td>
                </tr>
                <tr class="statistics-param">
                    <td class="label">Profile</td>
                    <td>
                        <div>
                            <form:input path="profile" class="form-block" title="Profile" maxlength="10"/>
                            <p class="tip">
                                Google Analytics > 애널리틱스 계정 > 속성 및 앱 > 속성보기</br>
                                에서 적용할 profile을 적어 주시면 됩니다. (ex. XXXXXXXXX)
                            </p>
                        </div>
                    </td>
                </tr>
                <tr class="statistics-param">
                    <td class="label">Auth Json 파일</td>
                    <td>
                        <div>
                            <c:if test="${not empty configGoogleAnalytics.authFile}">
                                등록되어 있습니다.
                            </c:if>
                            <input type="file" name="authJsonFile">
                            <p class="tip">
                                Google APIs > 사용자 인증정보 > 서비스 계정 생성 후 json 파일을 업로드 해주시면 됩니다.
                            </p>
                        </div>
                    </td>
                </tr>
            </tbody>
        </table>

        <div class="tex_c mt20">
            <button type="submit" class="btn btn-active">저장</button>
        </div>

    </form:form>

</div><!--//board_write E-->

<page:javascript>
<script type="text/javascript">

    var $FORM = $('#configGoogleAnalytics');

    $(function() {

        var confirmMessage = 'GoogleAnalytics 설정을 저장 하시겠습니까?';


        $FORM.validator(function() {

            if ($FORM.find('input[name=ecommerceTrackingFlag]:checked').val() == 'true') {

                if ($FORM.find('input[name=currency]').val() == '') {
                    alert('통화 종류를 입력해 주세요.');
                    $FORM.find('input[name=currency]').focus();
                    return false;
                }

            }

            if ($FORM.find('input[name=commonTrackingFlag]:checked').val() == 'true') {

                if ($FORM.find('input[name=measurementId]').val() == '') {
                    alert('추적 ID를 입력해 주세요.');
                    $FORM.find('input[name=measurementId]').focus();
                    return false;
                }

            }

            if ($FORM.find('input[name=statisticsFlag]:checked').val() == 'true') {

                if ($FORM.find('input[name=profile]').val() == '') {
                    alert('Profile를 입력해 주세요.');
                    $FORM.find('input[name=profile]').focus();
                    return false;
                }
            }

            if (!confirm(confirmMessage)) {
                return false;
            }

        });
    });


</script>
</page:javascript>