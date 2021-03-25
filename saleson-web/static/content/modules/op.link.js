/**
 * 페이지 링크 (op.link.js)
 *
 * 검색조건 유지가 필요한 경우 아래와 같이 링크를 걸어줌.
 * 
 * <pre>
 * 	op.common.js 에서 해당 파일을 로드하므로 
 *  <head></head>에 따로 js를 등록하지 않는다.
 * </pre>
 * 
 * <example>
 * 1. 목록 --> 내용보기, 등록/수정 폼 
 * 		<a href="javascript:Link.view('/test/view')">내용보기</a>
 * 
 * 2. 내용보기, 등록/수정 폼 --> 목록
 * 		<a href="javascript:Link.list('/test/list')">목록</a>
 * 
 * </example>
 * 
 * @author skc@onlinepowers.com
 * @date 2014-07-02
 */


var Link = {
	is: function(str) {
		if (!Common.isUndefined(str) && str != "") {
			return true;
		}
		return false;
	} 	
};


Link.view = function(url, blank) {
	
	
	if (blank != undefined && blank == 1) {
		openNewWindow = window.open('about:blank');
		
		if (Link.is(RequestContext.currentUrl)) {
			openNewWindow.location.href = url + '?url=' + RequestContext.currentUrl;
		} else {
			openNewWindow.location.href = url;
		}
		
		return;
	}
		
	if (Link.is(RequestContext.currentUrl)) {
		location.href = url + '?url=' + RequestContext.currentUrl;
	} else {
		location.href = url;
	}

};

Link.list = function(url) {
	if (Link.is(RequestContext.prevPageUrl)) {
		location.href =  RequestContext.prevPageUrl;
	} else {
		location.href =  url;
	}
};	
