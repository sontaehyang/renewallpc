<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" 		uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" 	uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions"%>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page"%>
<%@ taglib prefix="module" 	tagdir="/WEB-INF/tags/modules"%>


<div class="bd-callout bd-callout-info" style="position: relative; width: 300px; margin: 100px auto;">
	<h4>IP 등록 인증</h4>

	<p>사용자 인증 후 사용이 가능합니다. <br />인증코드를 입력해 주세요.</p>


	<form id="form">
		<div class="form-group">
			<label for="ip">인증코드</label>
			<input type="password" id="ip" name="ip" maxlength="30" class="form-control" placeholder="인증코드">
		</div>


		<button type="submit" class="btn btn-info btn-submit">등록</button>
	</form>
</div>


<script>
	$(function() {
		$('#ip').focus();
		// 등록
		$('.btn-submit').on('click', function() {
			var $ip = $('#ip');

			if ($.trim($ip.val()) == '') {
				alert('인증코드를 입력해 주세요.');
				$ip.focus();
				return false;
			}

			var params = {
				'mode': 'LOGIN',
				'authCode': $ip.val()
			};

			$.post('/demo/allow-ip', params, function(response) {
				if (!response.isSuccess) {
					alert(response.errorMessage);
					$ip.val('');
					return;
				}

				location.reload();
			});
		});
		$('#form').on('submit', function() {
			return false;
		})


	});

</script>