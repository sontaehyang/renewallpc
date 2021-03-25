<%@page contentType="text/html;charset=UTF-8"%>
<%@page pageEncoding="UTF-8"%>
<%@ taglib prefix="op" 	uri="/WEB-INF/tlds/functions" %>
<!doctype html>
<html lang="ko" class="opmanager">
<head>
<meta charset="UTF-8" />
<title>관리자페이지</title>
<meta http-equiv="X-UA-Compatible" content="IE=Edge" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<link rel="stylesheet" type="text/css" href="/content/styles/opmanager_base.css" />
<link rel="stylesheet" type="text/css" href="/content/styles/ui-lightness/jquery-ui-1.10.3.custom.min.css" />
<link rel="stylesheet" type="text/css" href="/content/styles/opmanager.css">
<link rel="stylesheet" type="text/css" href="/content/styles/opmanager_print.css" media="print" />

<script type="text/javascript" src="/content/scripts/jquery/jquery-1.11.0.min.js"></script>
<script type="text/javascript" src="/content/scripts/spin.min.js"></script>
<script type="text/javascript" src="/content/scripts/op.common.js" charset="utf-8"></script>
<script type="text/javascript" src="/content/scripts/op.file.js" charset="utf-8"></script>


</head>
<body>
<div class="popup_wrap">
	<h1 class="popup_title">CTP ${op:message('M00088')} <!-- 등록 --></h1>
	
	<div class="popup_contents">
		<form id="editor_upimage" name="editor_upimage" action="/editor-upload/smarteditor/upload-ctp" method="post" target="frame_action" enctype="multipart/form-data">
      

	      	<p class="dsc" id="info">
	      		<strong>3MB</strong>이하의 이미지 파일(jpg, gif, png)과<br /> html(ctp) 파일만 등록할 수 있습니다.<br><br />
	      	</p>

			<table class="board_write_table" summary="관련상품등록">
				<caption>관련상품등록 </caption>
				<colgroup>
					<col style="width: 80px" />
					<col style="" />
				</colgroup>
				<tbody>
					<tr>
						<td class="label">${op:message('M01070')} <!-- 파일 --></td>
						<td>
							<div>
								<button type="button" id="add_file" style="display:none" class="table_btn"><span>+ ${op:message('M01071')}</span></button> <!-- 파일추가 -->
								<p class="default_file"><input type="file" class="upload" id="uploadInputBox" name="file[]" multiple="multiple" /></p>
								
								<div id="multiple_files">
								
								</div>
							</div>
						</td>
					</tr>
				</tbody>
			</table>
			
			<p class="popup_btns">
				<button type="submit" class="btn btn-active">${op:message('M00088')}</button> <!-- 등록 -->
				<a href="javascript:self.close();" class="btn btn-default">${op:message('M00037')}</a> <!-- 취소 -->
			</p>

		</form>
	</div>				
</div>

<iframe src="" id="frame_action" name="frame_action" style="display:none;width: 1000px;height: 800px; border:1px solid red"></iframe>

<script type="text/javascript">
$(function(){

	// multiple file is not support
	if (!File.isSupportMultiple) {
		// 파일 추가
		$('#add_file').css('margin-bottom', '10px').show().on("click", function() {
			var html = '<p><input type="file" name="file[]" multiple="multiple" /> <a href="javascript:deleteFile()" class="delete_detail_image_file">[' + Message.get("M00074") + ']</a></p>';  // 삭제
			$('#multiple_files').append(html);
		});
		
		// 추가항목 삭제
		$('#multiple_files').on('click', '.delete_detail_image_file', function(e) {
			e.preventDefault();
			$(this).parent().remove();
		});
	}
});

function setCTPToEditor(oFileInfo){
	if (!!opener && !!opener.nhn && !!opener.nhn.husky && !!opener.nhn.husky.PopUpManager) {
		//스마트 에디터 플러그인을 통해서 넣는 방법 (oFileInfo는 Array)
		//opener.nhn.husky.PopUpManager.setCallback(window, 'SET_CTP', [oFileInfo]);
		//본문에 바로 tag를 넣는 방법 (oFileInfo는 String으로 <img src=....> )
		opener.nhn.husky.PopUpManager.setCallback(window, 'SET_CONTENTS', [""]);
		opener.nhn.husky.PopUpManager.setCallback(window, 'PASTE_HTML', [oFileInfo]);
		//opener.nhn.husky.PopUpManager.setCallback(window, 'PASTE_HTML', "텍스트");
		
		self.close();
	}
}
</script>

</body>
</html>