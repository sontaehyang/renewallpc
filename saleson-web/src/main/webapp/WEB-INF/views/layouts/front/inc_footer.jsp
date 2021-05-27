<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" 	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions"%>
<%@ taglib prefix="naverPay"	tagdir="/WEB-INF/tags/naverPay" %>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page" %>

<div class="footer_top">
	<div class="inner">
		<div class="ft_info">
			<div class="ft_bd">
			
				<div class="">
					<a href="#" class="acti">공지사항</a>
				</div>
				<!--// 각 최소4개씩 출력 -->
				<div class="ft_bd_cont" style="display:block;">
					<ul id="op-main-notice"></ul>
					<a href="/notice/list" class="top_link" title="공지사항 더보기">MORE</a>
				</div>
			
			
				<!-- div class="ft_bd_nav">
					<a href="#" class="acti">공지사항</a>
					<a href="#">자주하는 질문(FAQ)</a>
				</div>
				<div class="ft_bd_cont" style="display:block;">
					<ul id="op-main-notice"></ul>
					<a href="/notice/list" class="top_link" title="공지사항 더보기">MORE</a>
				</div>
				<div class="ft_bd_cont">
					<ul id="op-main-faq"></ul>
					<a href="/faq" class="top_link" title="자주하는 질문(FAQ) 더보기">MORE</a>
				</div-->
			</div><!--// ft_bd -->

			<div class="ft_bank">
			
				<div class="">
					<a href="#" class="acti">자주하는 질문(FAQ)</a>
				</div>
				<div class="ft_bd_cont" style="display:block;">
					<ul id="op-main-faq"></ul>
					<a href="/faq" class="top_link" title="자주하는 질문(FAQ) 더보기">MORE</a>
				</div>
		
				<!-- p class="tit">온라인입금 계좌</p>
				<p class="logo"><img src="/content/images/common/sh_logo.png" alt="신한은행"></p>
				<ul class="txt">
					<li>신한은행</li>
					<li>140-012-890215</li>
					<li>㈜월드와이드메모리</li>
				</ul -->
			</div><!--// ft_bank -->

			<div class="ft_sc">
				<p class="tit">고객서비스센터</p>
				<dl>
					<dt>1544-2432</dt>
					<dd>상담시간 : 평일 오전 9시 ~ 오후 7시(주말, 공휴일 휴무) <br>점심시간 : 오후 12시 30분 ~ 오후 1시 30분</dd>
				</dl>
				<a href="https://www.cjlogistics.com" class="btm_link" target="_blank">배송위치 조회하기</a>
			</div><!--// ft_sc -->
		</div><!--// ft_info -->
	</div><!-- // inner E -->
</div><!-- // footer_top E -->

<div class="footer_bottom">
	<div class="inner">
		<div class="ft_menu">
			<a href="/pages/about_us" title="회사소개">회사소개</a>
			<a href="/pages/etc_clause" title="이용약관">이용약관</a>
			<a href="/pages/etc_protect" title="개인정보취급방침">개인정보취급방침</a>
			<a href="#" title="이용안내">이용안내</a>
			<a href="/notice/list" title="고객센터">고객센터</a>
			<a href="javascript:Common.popup('/store-inquiry/inquiry', 'store-inquiry', 680, 572)" title="입점 및 제휴문의">입점 및 제휴문의</a>
		</div><!--// ft_menu -->

		<ul class="ft_cont">
			<li>
				<p>${shopContext.config.companyName}</p>
				<p>대표이사. ${shopContext.config.bossName}</p>
				<p>대표전화. ${shopContext.config.telNumber}</p>
				<p>팩스. ${shopContext.config.faxNumber}</p>
			</li>
			<li>
				<p>주소. ${shopContext.config.address}&nbsp;${shopContext.config.addressDetail}</p>
				<p>사업자등록번호. ${shopContext.config.companyNumber}</p>
				<p>통신판매업신고. ${shopContext.config.mailOrderNumber}</p>
			</li>
			<li>
				<p>개인정보관리책임자. ${shopContext.config.adminName}</p>
				<p>이메일. <a href="mailto:${shopContext.config.adminEmail}" target="_blank">${shopContext.config.adminEmail}</a></p>
			</li>
		</ul> <!--// ft_cont -->
		<p class="copyright">COPYRIGHT &copy; RE.NEW.ALL PC. ALL RIGHT RESERVED.</p>
		<p class="ft_logo"><img src="/content/images/common/ft_logo.png" alt="U+, 공정거래위원회, 현금영수증, KG Inicis, CJ대한통운, 바른서비스, KSCI, Microsoft, 공식판매"></p>
	</div><!-- // inner E -->
</div><!-- // footer_bottom E -->


<naverPay:wcslog-footer />
<page:javascript>
	<script type="text/javascript">
		$(function(){
			Shop.writeVisitorLog();

			// 공지사항
			bindFooterContents('notice');

			// FAQ
			bindFooterContents('faq');
		});

		function bindFooterContents(contentsCode) {
			$.post('/main/' + contentsCode, function (html) {
				$('#op-main-' + contentsCode).empty().append(html);
			}, 'html');
		}
	</script>
</page:javascript>


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