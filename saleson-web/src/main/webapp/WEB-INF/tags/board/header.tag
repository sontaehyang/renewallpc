<%@ tag pageEncoding="utf-8" %>
<%@ taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core"%> 
<%@ taglib prefix="fn" 		uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" 	uri="http://www.springframework.org/tags"%>


<script type="text/javascript" src="<c:url value="/content/modules/op.board.js" />"></script>
<script type="text/javascript">
Board.init('${boardContext.boardBaseUri}');
</script>

<c:if test="${boardContext.boardCfg.useSocial == '1'}">
<script type="text/javascript">
Common.dynamic.script(url("/content/modules/op.social.js"));
</script>
</c:if>



${boardContext.boardCfg.boardHeader}

<c:if test="${requestContext.opmanagerPage == false}">
	<c:choose>
		<c:when test="${boardContext.boardCode == 'notice'}">
			<div class="location">
				<a href="/">Home</a> &gt;  <a href="/csia-board/notice">알림마당</a> &gt; <a href="/csia-board/notice" class="on">공지사항</a>
			</div>
			
			<h3><img src="/content/images/news/content03_h3_01.gif" alt="공지사항" /></h3>
		</c:when>
		
		<c:when test="${boardContext.boardCode == 'prevention'}">
			<div class="location">
				<a href="/">Home</a> &gt;  <a href="#">안전사고 예방</a> &gt; <a href="/csia-board/prevention?category=1" class="on">예방자료실</a>
			</div>	
			<h3><img src="/content/images/accident/content02_h3_05.gif" alt="예방자료실" /></h3>
		</c:when>
		
		<c:when test="${boardContext.boardCode == 'notification'}">
			<div class="location">
				<a href="/">Home</a> &gt;  <a href="/csia-board/notice">알림마당</a> &gt; <a href="/csia-board/notification?category=1" class="on">알림자료실</a>
			</div>
			
			<h3><img src="/content/images/news/content02_h3_02.gif" alt="알림자료실" /></h3>
		</c:when>
		
		<c:when test="${boardContext.boardCode == 'refer'}">
			<div class="location">
				<a href="/">Home</a> &gt;  <a href="#">안전사고 예방</a> &gt; <a href="/csia-board/refer?category=1" class="on">참고사이트</a>
			</div>	
			
			<h3><img src="/content/images/accident/content02_h3_06.gif" alt="참고사이트" /></h3>
		</c:when>
		
		<c:when test="${boardContext.boardCode == 'download'}">
			<div class="location">
				<a href="/">Home</a> &gt;  <a href="#">정보마당</a> &gt; <a href="/csia-board/download" class="on">양식 다운로드</a>
			</div>
					
			<h3><img src="/content/images/information/content04_h3_01.gif" alt="양식 다운로드" /></h3>
		</c:when>
		
		<c:when test="${boardContext.boardCode == 'irmanage'}">
			<div class="location">
				<a href="/">Home</a> &gt;  <a href="#">공제회 소개</a> &gt; <a href="/csia-board/irmanage" class="on">IR소개</a>
			</div>
			
			<h3><img src="/content/images/intro/content05_h3_07.gif" alt="IR소개" /></h3>
		</c:when>
		
		<c:when test="${boardContext.boardCode == 'ebook'}">
			<div class="location">
				<a href="/">Home</a> &gt;  <a href="/board/notice">알림마당</a> &gt; <a href="/csia-board/ebook" class="on">e-book</a>
			</div>
			
			<h3><img src="/content/images/news/content02_h3_06.gif" alt="e-book" /></h3>
		</c:when>
		
		<c:when test="${boardContext.boardCode == 'faq'}">
			<div class="location">
				<a href="/">Home</a> &gt;  <a href="/csia-board/notice">알림마당</a> &gt; <a href="/csia-board/faq" class="on">FAQ</a>
			</div>
			
			<h3><img src="/content/images/news/content03_h3_05.gif" alt="FAQ" /></h3>
		</c:when>
		
		<c:when test="${boardContext.boardCode == 'movie'}">
			<div class="location">
				<a href="/">Home</a> &gt;  <a href="#">정보마당</a> &gt; <a href="/csia-board/movie/list" class="on">홍보영상</a>
			</div>	
				
			<h3><img src="/content/images/information/content04_h3_03.gif" alt="홍보영상" /></h3>
		</c:when>
		
		<c:when test="${boardContext.boardCode == 'webzine'}">
			<div class="location">
				<a href="/">Home</a> &gt;  <a href="/csia-board/notice">알림마당</a> &gt; <a href="/csia-board/webzine?category=1" class="on">웹진</a>
			</div>
			
			<h3><img src="/content/images/news/content02_h3_03.gif" alt="웹진" /></h3>
		</c:when>
		
		<c:when test="${boardContext.boardCode == 'qna'}">
			<div class="location">
				<a href="/">Home</a> &gt;  <a href="/csia-board/notice">알림마당</a> &gt; <a href="/csia-board/qna" class="on">질의응답</a>
			</div>
			<h3><img src="/content/images/news/content02_h3_04.gif" alt="질의응답" /></h3>
		</c:when>
		

	</c:choose>
</c:if>
