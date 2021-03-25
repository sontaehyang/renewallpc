<%@ tag pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%> 
<%@ attribute name="id" required="true" %>


<script type="text/javascript">
var lang = Common.isUndefined(OP_LANGUAGE) || OP_LANGUAGE == '' ? 'ko' : OP_LANGUAGE;

//추가 글꼴 목록
//var aAdditionalFontSet = [["MS UI Gothic", "MS UI Gothic"], ["Comic Sans MS", "Comic Sans MS"],["TEST","TEST"]];
var aDefaultFontSet = [];
if (lang == 'ja') {
	aDefaultFontSet = [["MS PGothic", "MS PGothic"]];
}


nhn.husky.EZCreator.createInIFrame({
	oAppRef: editors,
	elPlaceHolder: "${id}",
	sSkinURI: '/content/modules/smarteditor2_3_10/SmartEditor2Skin_' + lang + '.html',	
	htParams : {
		bUseToolbar : true,				// 툴바 사용 여부 (true:사용/ false:사용하지 않음)
		bUseVerticalResizer : true,		// 입력창 크기 조절바 사용 여부 (true:사용/ false:사용하지 않음)
		bUseModeChanger : true,			// 모드 탭(Editor | HTML | TEXT) 사용 여부 (true:사용/ false:사용하지 않음)
		aDefaultFontList : aDefaultFontSet,
		//aAdditionalFontList : aAdditionalFontSet,		// 추가 글꼴 목록
		fOnBeforeUnload : function(){
			//alert("완료!");
		}
	}, //boolean
	fOnAppLoad : function(){

		//예제 코드
		//editors.getById["ir1"].exec("PASTE_HTML", ["로딩이 완료된 후에 본문에 삽입되는 text입니다."]);
	},
	fCreator: "createSEditor2"
});
</script>