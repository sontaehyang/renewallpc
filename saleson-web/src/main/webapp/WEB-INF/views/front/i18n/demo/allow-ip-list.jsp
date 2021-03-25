<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" 		uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" 	uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions"%>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page"%>
<%@ taglib prefix="module" 	tagdir="/WEB-INF/tags/modules"%>


<div class="alert alert-primary" role="alert">
	<h4>IP 등록</h4>
	<div class="remote-address">접속 IP : ${remoteAddress}</div>
</div>

<div class="row">
	<div class="col-8">
		<table class="table">
			<thead class="thead-inverse">
			<tr>
				<th>순번</th>
				<th>IP</th>
				<th>업체명</th>
				<th>등록자</th>
				<th>등록일</th>
				<th>삭제</th>
			</tr>
			</thead>

			<tbody>
			<c:forEach items="${list}" var="code" varStatus="i">
				<tr>
					<td>${i.count}</td>
					<td>${code.ip}</td>
					<td>${code.companyName}</td>
					<td>${code.name}</td>
					<td>${code.createdDate}</td>
					<td><button type="button" class="btn btn-sm btn-delete" data-ip="${code.ip}"> 삭제 </button></td>
				</tr>
			</c:forEach>

			</tbody>

		</table>

	</div>
	<div class="col-4">


		<div class="bd-callout bd-callout-info">
			<h4>IP 등록</h4>

			<p>SalesOn(saleson.onlinepowers.com)에 접속할 아이피를 입력 후 등록할 수 있습니다.</p>


			<form id="form">
				<div class="form-group">
					<label for="ip">IP address</label>
					<input type="text" id="ip" name="ip" value="${remoteAddress}" maxlength="20" class="form-control" placeholder="IP address">
				</div>
				<div class="form-group">
					<label for="companyName">업체명</label>
					<input type="text" id="companyName" maxlength="20" name="companyName" class="form-control" placeholder="Company name">
				</div>

				<button type="submit" class="btn btn-info btn-submit">등록</button>
				<button type="button" class="btn btn-danger btn-logout">로그아웃</button>
			</form>
		</div>

	</div>
</div>





<script>
	$(function() {
		// 등록
		$('.btn-submit').on('click', function() {
			var $ip = $('#ip');
			var $companyName = $('#companyName');

			if ($.trim($ip.val()) == '') {
				alert('IP를 입력해 주세요.');
				$ip.focus();
				return false;
			}

			if ($.trim($companyName.val()) == '') {
				alert('업체명을 입력해 주세요.');
				$companyName.focus();
				return false;
			}

			var params = {
				'mode': 'INSERT',
				'codeType': 'ALLOW_IP',
				'language': 'ko',
				'id': $ip.val(),
				'label': $companyName.val()
			};

			$.post('/demo/allow-ip', params, function(response) {
				if (!response.isSuccess) {
					alert(response.errorMessage);
					return;
				}

				alert('등록되었습니다.');
				location.reload();
			});
		});
		$('#form').on('submit', function() {
			return false;
		})


		// 삭제
		$('.btn-delete').on('click', function() {
			if (!confirm('삭제하시겠습니까?')) {
				return;
			}

			var ip = $(this).data("ip");
			var params = {
				'mode': 'DELETE',
				'id': ip
			};

			$.post('/demo/allow-ip', params, function(response) {
				if (!response.isSuccess) {
					alert(response.errorMessage);
					return;
				}

				alert('삭제되었습니다.');
				location.reload();
			});

		});

		// 로그아웃
		$('.btn-logout').on('click', function() {


			var params = {
				'mode': 'LOGOUT'
			};

			$.post('/demo/allow-ip', params, function(response) {

				location.reload();
			});

		});
	});

</script>
