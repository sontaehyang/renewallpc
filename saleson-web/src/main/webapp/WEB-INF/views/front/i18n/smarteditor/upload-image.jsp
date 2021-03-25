<%@page contentType="text/html;charset=UTF-8"%>
<%@page pageEncoding="UTF-8"%>
<%@ taglib prefix="op" 	uri="/WEB-INF/tlds/functions" %>
<!doctype html>
<html lang="ko" class="opmanager">
<head>
<meta charset="UTF-8" />
<title>이미지등록</title>
<meta http-equiv="X-UA-Compatible" content="IE=Edge" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<link rel="stylesheet" type="text/css" href="/content/opmanager/css/ui-lightness/jquery-ui-1.10.3.custom.min.css" />
<link rel="stylesheet" type="text/css" href="/content/opmanager/css/bootstrap/css/bootstrap.css" />
<link rel="stylesheet" type="text/css" href="/content/opmanager/css/opmanager.css">
<link rel="stylesheet" type="text/css" href="/content/opmanager/css/opmanager_print.css" media="print" />

<script type="text/javascript" src="/content/modules/jquery/jquery-1.11.0.min.js"></script>
<script type="text/javascript" src="/content/modules/spin.min.js"></script>
<script type="text/javascript" src="/content/modules/op.common.js" charset="utf-8"></script>
<script type="text/javascript" src="/content/modules/op.file.js" charset="utf-8"></script>

<meta name="_csrf" content="${_csrf.token}"/>
<meta name="_csrf_header" content="${_csrf.headerName}"/>
</head>
<body>
<div class="popup_wrap">
	<h1 class="popup_title">${op:message('M00738')} <!-- 등록 --></h1>
	
	<div class="popup_contents">
		<div id="pop_container2" class="pop_container2">
			<form id="editor_upimage" name="editor_upimage" action="/smarteditor/upload-image" method="post" target="frame_action" enctype="multipart/form-data" onsubmit="return false;">
	        <input type="hidden" name="token" id="token"  value="${token}"/>
            <input type="hidden" name="_csrf" value="${_csrf.token}"/>

                <p class="text-info text-sm">
                    * 여러 개의 이미지 파일을 드래그하거나 선택한 후 한 번에 등록할 수 있습니다.<br>
		      		* <strong>5MB</strong>${op:message('M01073')} <!-- 이하의 이미지 파일(jpg, jpeg, gif, png)만 등록할 수 있습니다. -->
		      	</p>
	
				<table class="board_write_table" summary="이미지등록">
					<caption>이미지등록 </caption>
					<colgroup>
						<col style="width: 80px" />
						<col style="" />
					</colgroup>
					<tbody>
						<tr>
							<td class="label">${op:message('M01070')} <!-- 파일 --></td>
							<td>
								<div>
									<input type="file" class="upload" id="uploadInputBox" name="file[]" multiple="multiple" accept=".gif, .png, .jpg, .jpeg" />
									
								</div>
							</td>
						</tr>
					</tbody>
				</table>
				
				<p class="popup_btns">
					<button type="submit" id="btn_confirm" class="btn btn-active">${op:message('M00088')}</button> <!-- 등록 -->
					<a href="javascript:self.close();" id="btn btn-default" class="btn btn-default">${op:message('M00037')}</a> <!-- 취소 -->
				</p>
			</form>
		</div>
	</div>
    <iframe id="frame_action" name="frame_action" style="display:none;width: 1000px;height: 800px; border:1px solid red"></iframe>
</div>

<script type="text/javascript" src="/content/modules/smarteditor2_3_10/photo_uploader/popup/jindo.min.js" charset="utf-8"></script>
<script type="text/javascript" src="/content/modules/smarteditor2_3_10/photo_uploader/popup/jindo.fileuploader.js" charset="utf-8"></script>
<script type="text/javascript" src="/content/modules/smarteditor2_3_10/photo_uploader/popup/attach_photo.js" charset="utf-8"></script>
<script type="text/javascript" src="/content/modules/op.common.js" charset="utf-8"></script>
<script type="text/javascript">
$(function(){
	var token = opener.parent.document.getElementById("token").value;
	$('#token').val(token);
});

function pasteHtmlToEditor(oFileInfo){
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