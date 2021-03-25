<%@page contentType="text/html;charset=UTF-8" %>
<%@page pageEncoding="UTF-8" %>
<%@ taglib prefix="op" uri="/WEB-INF/tlds/functions" %>
<!doctype html>
<html lang="ko" class="opmanager">
<head>
	<meta charset="UTF-8"/>
	<title>관리자페이지</title>
	<meta http-equiv="X-UA-Compatible" content="IE=Edge"/>
	<meta name="viewport" content="width=device-width, initial-scale=1.0"/>

	<link rel="stylesheet" type="text/css" href="/content/opmanager/css/ui-lightness/jquery-ui-1.10.3.custom.min.css"/>
	<link rel="stylesheet" type="text/css" href="/content/opmanager/css/bootstrap/css/bootstrap.css"/>
	<link rel="stylesheet" type="text/css" href="/content/opmanager/css/opmanager.css">
	<link rel="stylesheet" type="text/css" href="/content/opmanager/css/opmanager_print.css" media="print"/>

	<script type="text/javascript" src="/content/modules/jquery/jquery-1.11.0.min.js"></script>
	<script type="text/javascript" src="/content/modules/spin.min.js"></script>
	<script type="text/javascript" src="/content/modules/op.common.js" charset="utf-8"></script>

	<meta name="_csrf" content="${_csrf.token}"/>
	<meta name="_csrf_header" content="${_csrf.headerName}"/>
</head>
<body>
<div class="popup_wrap">
	<h1 class="popup_title">동영상 ${op:message('M00088')} <!-- 등록 --></h1>

	<div class="popup_contents">
		<table class="board_write_table" summary="동영상 등록">
			<caption>동영상 등록</caption>
			<colgroup>
				<col style="width: 80px"/>
				<col style=""/>
			</colgroup>
			<tbody>
			<tr class="content_url">
				<td class="label">내용</td>
				<td colspan="3">
					<div>
						<input type="text" name="url" class="nine"/>
					</div>
				</td>
			</tr>
			<tr class="content_url">
				<td class="label">width</td>
				<td>
					<div>
						<input type="text" name="width" class="nine _number" value="400"/>
					</div>
				</td>
				<td class="label">height</td>
				<td>
					<div>
						<input type="text" name="height" class="nine _number" value="300"/>
					</div>
				</td>
			</tr>
			<tr class="content_source" style="display: none;'">
				<td class="label">내용</td>
				<td colspan="3">
					<div>
						<input type="text" name="source" class="nine"/>
					</div>
				</td>
			</tr>
			</tbody>
		</table>

		<p class="popup_btns">
			<button type="button" class="btn btn-active">${op:message('M00088')}</button> <!-- 등록 -->
			<a href="javascript:self.close();" class="btn btn-default">${op:message('M00037')}</a> <!-- 취소 -->
		</p>

	</div>
</div>

<iframe src="" id="frame_action" name="frame_action"
        style="display:none;width: 1000px;height: 800px; border:1px solid red"></iframe>

<script type="text/javascript">
	$(function () {

		$('button.btn-active').on('click', function () {

			var width = $('input[name="width"]').val();
			var height = $('input[name="height"]').val();
			var url = $('input[name="url"]').val();

			var inputData = "";

			// 소스코드 변경
			var sendUrl = url.replace('https://youtu.be/', 'https://www.youtube.com/embed/')

			inputData += '<div style="width:' + width + 'px; height=' + height + 'px;">';
			inputData += '<iframe width="' + width + '" height="' + height + '" src="' + sendUrl + '" frameborder="0" allowfullscreen></iframe>';
			inputData += '</div>';

			opener.nhn.husky.PopUpManager.setCallback(window, 'PASTE_HTML', [inputData]);

			self.close();

		});
	});

</script>

</body>
</html>