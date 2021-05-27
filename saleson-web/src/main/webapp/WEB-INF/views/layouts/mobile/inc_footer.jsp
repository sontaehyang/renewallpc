<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" 	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions" %>
<%@ taglib prefix="naverPay"	tagdir="/WEB-INF/tags/naverPay" %>

<div class="layer_pop_bg"></div>

<!-- footer : s -->
<div id="footer_wrap">
	<div class="foot_menu">
		<a href="/m/pages/about_us">회사소개</a>
		<a href="/m/pages/etc_clause">이용약관</a>
		<a href="/m/notice/list">고객센터</a>
		<a href="#">쇼핑몰이용안내</a>
		<a href="javascript:Mobile.moveToWebsite()">PC버전</a>
	</div>
	<dl class="ft_info">
		<dt>리뉴올PC 고객서비스센터</dt>
		<dd>상담시간 : 평일 오전 9시 ~ 오후 7시(주말, 공휴일 휴무) <br>점심시간 : 오후 12시 30분 ~ 오후 1시 30분</dd>
		<dd><a href="tel:1544-2432" class="ft_tel">1544-2432</a></dd>
	</dl>
	<!--dl class="ft_info fti02">
		<dt>온라인 입금계좌</dt>
		<dd class="ex"><span>신한은행</span>140-012-890215</dd>
		<dd><span>예금주</span>㈜월드와이드메모리</dd>
	</dl-->
	<dl class="ft_info fti03">
		<dt>
			<p class="ft_logo">Re.New.All PC</p>
		</dt>
		<dd class="txt">
			<p>${shopContext.config.companyName}</p>
			<p>대표이사. ${shopContext.config.bossName}</p>
			<p>대표전화. ${shopContext.config.telNumber}</p>
			<p>팩스. ${shopContext.config.faxNumber}</p>
			<p>주소. ${shopContext.config.address}&nbsp;${shopContext.config.addressDetail}</p>
			<p>사업자등록번호. ${shopContext.config.companyNumber}</p>
			<p>통신판매업신고. ${shopContext.config.mailOrderNumber}</p>
			<p>개인정보관리책임자. ${shopContext.config.adminName}</p>
			<p>이메일. <a href="mailto:${shopContext.config.adminEmail}" target="_blank">${shopContext.config.adminEmail}</a></p>
		</dd>
		<dd>
			<a href="/m/pages/etc_protect" class="btn_smu01">개인정보취급방침</a>
			<a href="/m/store-inquiry/inquiry" class="btn_smu02">입점문의</a>
			<a href="/m/pages/dealer_shop" class="btn_smu03">딜러샵</a>
		</dd>
		<dd class="copy">COPYRIGHT &copy; RE.NEW.ALL PC. ALL RIGHT RESERVED</dd>
	</dl>
	<div class="btn_scroll_top">
		<button type="button">
			<img src="/content/mobile/images/common/ico_scroll_top.png" alt="ico_scroll_top">
		</button>
	</div>
	<div class="foot_menu_m fixed_bottom">
		<!--푸터 고정-->
		<ul class="ft_fix">
			<li><a href="#" class="side_menu_btn"><span>카테고리</span></a></li>
			<li class="search"><a href="javascript:;"><span>검색</span></a></li>
			<li><a href="/m"><span>홈</span></a></li>
			<li><a href="/m/mypage"><span>마이페이지</span></a></li>
			<li <%--class="is_in"--%>><a href="/m/cart"><span>장바구니</span></a></li>
		</ul>
	</div>
</div>



<!-- 공통 적용 스크립트, 전환페이지 설정값보다 항상 하단에 위치해야함 --> 
<script type="text/javascript" src="//wcs.naver.net/wcslog.js"> </script> 
<script type="text/javascript"> 
if (!wcs_add) var wcs_add={};
wcs_add["wa"] = "s_47fe469c5bc6";
if (!_nasa) var _nasa={};
if(window.wcs){
wcs.inflow();
wcs_do(_nasa);
}
</script>