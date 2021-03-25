<%@ tag pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%> 
<%@ attribute name="editorId" required="false"%>
<%@ attribute name="content" required="false"%>
<%@ attribute name="width" required="false"%>
<%@ attribute name="height" required="false"%>
<%@ attribute name="css" required="false"%>
<%@ attribute name="movie" required="false"%>

<c:set var="editorStyle" value="" />

<c:if test="${!empty width}">
	<c:set var="editorStyle"> style="width: ${width}px"</c:set>
</c:if>

<c:if test="${empty height}">
	<c:set var="height">400</c:set>
</c:if>

<div class="editorMain" ${editorStyle}>
	<div id="${editorId}-toolbar" class="editorToolbar" style="height: 27px;">
		<div class="editorGroup">
			<a href="javascript:;" class="editorButton" onclick="executeCommand('${editorId}', 'bold',0);" style="background-color: transparent;" title="Bold"></a>
			<a href="javascript:;" class="editorButton" onclick="executeCommand('${editorId}', 'italic',0);" style="background-position: -24px center;" title="Italic" ></a>
			<a href="javascript:;" class="editorButton" onclick="executeCommand('${editorId}', 'underline',0);" style="background-position: -48px center;" title="Underline" ></a>
			<a href="javascript:;" class="editorButton" onclick="executeCommand('${editorId}', 'strikethrough',0);" style="background-position: -72px center;" title="Strikethrough" ></a>
			<div class="editorDivider"></div>
		</div>
	
		<div class="editorGroup">
			<a href="javascript:;" class="editorButton" onclick="Div_Show('${editorId}', 'font_face')" style="background-position: -144px center;" title="Font"></a>
			<a href="javascript:;" class="editorButton" onclick="Div_Show('${editorId}', 'font_size')" style="background-position: -168px center;" title="Font Size"></a>
			<div class="editorDivider"></div>
		</div>
		
		<div class="editorGroup">
			<a href="javascript:;" class="editorButton" onclick="Div_Show('${editorId}', 'font_color');" style="background-position: -216px center;" title="Font Color"></a>
			<a href="javascript:;" class="editorButton" onclick="Div_Show('${editorId}', 'font_bg');" style="background-position: -240px center;" title="Text Highlight Color"></a>
			<div class="editorDivider"></div>
		</div>

	
		<div class="editorGroup">
			<a href="javascript:;" class="editorButton" onclick="executeCommand('${editorId}', 'insertunorderedlist',0);" title="Bullets" style="background-position: -288px center;"></a>
			<a href="javascript:;" class="editorButton" onclick="executeCommand('${editorId}', 'insertorderedlist',0);" title="Numbering" style="background-position: -312px center; background-color: transparent;"></a>
			<div class="editorDivider"></div>
		</div>

		<div class="editorGroup">
			<a href="javascript:;" class="editorButton" onclick="executeCommand('${editorId}', 'outdent',0);" title="Outdent" style="background-position: -336px center;"></a>
			<a href="javascript:;" class="editorButton" onclick="executeCommand('${editorId}', 'indent',0);" title="Indent" style="background-position: -360px center;"></a>
			<div class="editorDivider"></div>
		</div>

		<div class="editorGroup">
			<a href="javascript:;" class="editorButton" onclick="executeCommand('${editorId}', 'justifyleft',0);" title="Align Text Left" style="background-position: -384px center;"></a>
			<a href="javascript:;" class="editorButton" onclick="executeCommand('${editorId}', 'justifycenter',0);" title="Center" style="background-position: -408px center;"></a>
			<a href="javascript:;" class="editorButton" onclick="executeCommand('${editorId}', 'justifyright',0);" title="Align Text Right" style="background-position: -432px center;"></a>
			<!--  
			<a href="javascript:;" class="editorButton" onclick="executeCommand('${editorId}', 'justifyfull',0);" title="Justify" style="background-position: -456px center;"></a>
			-->
			<div class="editorDivider"></div>
		</div>
		
		<div class="editorGroup">
			<a href="javascript:;" class="editorButton" onclick="executeCommand('${editorId}', 'hr',0);" title="Insert Horizontal Rule" style="background-position: -528px center; background-color: transparent;"></a>
			<!--  
			<a href="javascript:;" class="editorButton" title="CMS Menu Link" style="background-image: url(&quot;/content/modules/editor/images/../images/menulink.gif&quot;); background-color: transparent;"></a>
			-->
			<a href="javascript:;" class="editorButton" onclick="Div_Show('${editorId}', 'img');" title="Insert Image" style="background-image: url(&quot;/content/modules/editor/images/../images/imageUpload.gif&quot;);"></a>
			<a href="javascript:;" class="editorButton" onclick="Div_Show('${editorId}', 'table');" title="Insert Table" style="background-image: url(&quot;/content/modules/editor/images/../images/table.gif&quot;);"></a>
			<a href="javascript:;" class="editorButton" onclick="Div_Show('${editorId}', 'link');" title="Insert Hyperlink" style="background-position: -576px center; background-color: transparent;"></a>
			<div class="editorDivider"></div>
		</div>
	
		<div class="editorGroup">
			<input type="checkbox" id="${editorId}isHtml"  value="0" style="display: none;"/>
			<a href="javascript:;" class="editorButton" title="Show Source" style="background-position: -744px center; background-color: transparent;" onclick="fn_ShowHTML('${editorId}')" ></a>
		</div>
	</div>

	<div class="editor_iframes" style="position:relative">
		<iframe id="${editorId}font_face" 	frameborder="0" scrolling="no" style="position:absolute;display:none;margin-top:-3px;"></iframe>
		<iframe id="${editorId}font_size" 	frameborder="0" scrolling="no" style="position:absolute;display:none;margin-top:-3px;"></iframe>
		<iframe id="${editorId}emot" 		frameborder="0" scrolling="no" style="position:absolute;display:none;margin-top:-3px;"></iframe>
		<iframe id="${editorId}font_color" 	frameborder="0" scrolling="no" style="position:absolute;display:none;margin-top:-3px;"></iframe>
		<iframe id="${editorId}font_bg" 		frameborder="0" scrolling="no" style="position:absolute;display:none;margin-top:-3px;"></iframe>
		<iframe id="${editorId}img" 			frameborder="0" scrolling="no" style="position:absolute;display:none;margin-top:-3px;"></iframe>
		<iframe id="${editorId}link" 		frameborder="0" scrolling="no" style="position:absolute;display:none;margin-top:-3px;"></iframe>
		<iframe id="${editorId}table" 		frameborder="0" scrolling="no" style="position:absolute;display:none;margin-top:-3px;"></iframe>
	</div>

	<div class="editor-content-area">
		<!-- 
		<div id="${editorId}LetterUp">
			<iframe id="${editorId}rLetterUp" name="rLetterUp" width="100%" style="height:0" frameborder="0" scrolling="no"></iframe>      
		</div>
		 -->
		<div id="${editorId}HtmlArea" style="padding-left: 5px">
	    	<iframe id="${editorId}rHtmlArea" frameborder="0" style="width:100%;height:${height}px;background:#fff;;padding:0;margin:0"></iframe>
		</div>
		<div id="${editorId}TextArea" class="hide" style="padding-left: 5px">
			<textarea id="${editorId}rTextArea" class="borderNone" style="width:100%;height:${height}px;border:0;margin:0;background:#FFF; font: 14px 'Courier New',Monospace;"></textarea>
		</div>
	</div>

	<textarea id="${editorId}" class="${editorId}" name="${editorId}" rows="" cols="" class="required" title="내용" style="display:none">${fn:replace(content, '&quot;', '\'')}x</textarea>
	
	<div id="${editorId}_temp_file_ids"></div>
</div>
<script type="text/javascript">initEdit('${editorId}', '${css}');</script>
<!-- 
<script type="text/javascript">
	$(function() {
		alert($('.editor-buttons-area').width());
		$('#${editorId}rHtmlArea').width($('.editor-buttons-area').width()-8);
	});
</script>
 -->


<script type="text/javascript">
var editor_button_over_status = 0;
	$(function() {
		$('.editorButton').bind('mouseover', function(){
			$(this).css('background-color', '#fff');
		}).bind('mouseout', function() {
			$(this).css('background-color', 'transparent');
		});
		
		
		$('.editor-buttons-area, .editorToolbar').mouseover(function(){
			editor_button_over_status = 1;
		}).mouseout(function(){
			editor_button_over_status = 0;
		});
		$('.editor-content-area iframe').contents().find('body').click(function(){
			$('.editor_iframes iframe').hide();	
		});
		
		$('body').click(function(){
			if (editor_button_over_status == 0) {
				$('.editor_iframes iframe').hide();	
			}
		});
	});
</script>

