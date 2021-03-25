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

<h3><span></span></h3>

<div class="board_write">
	<form:form modelAttribute="config" method="post" enctype="multipart/form-data">
		<table class="board_write_table pg-info">
			<caption>GoogleAnalytics 설정</caption>
			<colgroup>
				<col style="width:170px;" />
				<col style="width: auto;" />
			</colgroup>
			<tbody>
				<tr class="statistics-param">
					<td class="label">&nbsp;</td>
					<td>
						<div>
							<p class="tip">
								1. Google Analytics 에서 통계에 적용할 Profile 정보를 입력해 주세요.<br/>
								* Google Analytics > 애널리틱스 계정 > 속성 및 앱 > 속성보기 에서 적용할 profile을 적어 주시면 됩니다. (ex. XXXXXXXXX)
							</p>
						</div>
					</td>
				</tr>
				<tr class="statistics-param">
					<td class="label">Profile<span class="require">*</span></td>
					<td>
						<div>
							<form:input path="profile" class="form-block required" title="Profile" maxlength="10"/>
						</div>
					</td>
				</tr>
				<tr class="statistics-param">
					<td class="label">&nbsp;</td>
					<td>
						<div>
							<p class="tip">
								2. Google API 에서 서비스 계정(Service Acount) 생성을 합니다.<br/>
								* Google Developer Console > 사용자 인증정보 > 서비스 계정 생성<br/>
								3. 생성된 서비스 계정의 키를 발급 후 다운로드 후 등록 하시면 됩니다.<br/>
								* 생성된 서비스 계정 > 키 생성후 다운로드 (JSON)
							</p>
						</div>
					</td>
				</tr>
				<tr class="statistics-param">
					<td class="label">Auth Json 파일<span class="require">*</span></td>
					<td>
						<div>
							<c:if test="${not empty config.authFile}">
								등록되어 있습니다.
							</c:if>
							<input type="file" name="authJsonFile" class="required" title="Auth Json 파일">
							<p class="tip">
								Google APIs > 사용자 인증정보 > 서비스 계정 생성 후 json 파일을 업로드 해주시면 됩니다.
							</p>
						</div>
					</td>
				</tr>
				<tr class="statistics-param">
					<td class="label">&nbsp;</td>
					<td>
						<div>
							<p class="tip">
								4. 생성된 서비스 계정을 Google Analytics 사용자로 등록을 합니다.<br/>
								* 서비스 계정의 이메일을 등록 하시면 됩니다.
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

	$(function() {

		var confirmMessage = 'GoogleAnalytics 설정을 저장 하시겠습니까?';


		$('#config').validator(function() {

			if (!confirm(confirmMessage)) {
				return false;
			}

		});

    });

</script>
</page:javascript>