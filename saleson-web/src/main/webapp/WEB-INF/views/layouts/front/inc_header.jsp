<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions"%>
<%@ taglib prefix="sec" 	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" 	uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" 	uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="seo" 	tagdir="/WEB-INF/tags/seo" %>

<div class="header_top">
	<div class="inner">
		<div class="hdt_menu">
			<a href="/" class="on">SHOP</a>
			<a href="#">COMPANY</a>
		</div><!--// hdt_menu -->
	
		<!--  로그인시 class:on 삭제시 장바구니 개수 가려짐 -->
		<ul class="util_menu on"> 
			<sec:authorize access="hasRole('ROLE_USER')">
				<li><a href="/op_security_logout" title="로그아웃">로그아웃</a></li>
				<li><a href="/mypage/order" title="마이페이지">마이페이지</a></li>
			</sec:authorize>
			
			<sec:authorize access="!hasRole('ROLE_USER')">
				<li><a href="/users/login" title="로그인">로그인</a></li>
				<li><a href="/users/join-us" title="회원가입">회원가입</a></li>
			</sec:authorize> 

			<li><a href="/cart" class="cart" title="장바구니">장바구니 <span id="header_cart_quantity">0</span></a></li>
			<li><a href="/inquiry" title="문의">1:1문의</a></li>
		</ul>
		
		<sec:authorize access="hasRole('ROLE_USER')">
			<c:if test="${not empty requestContext.user.userName}">
				<p class="greetings"><span>${requestContext.user.userName}</span>님 반갑습니다.</p>
			</c:if>
		</sec:authorize>
	</div><!--// inner E-->
</div><!-- // header_top E -->

<div class="header_middle">
	<div class="inner">
		<h1 class="logo"><a href="/" title="리뉴올PC"><img src="/content/images/common/renewallpc_logo_eng.png" alt="Re.New.All PC 리뉴올 PC 되살리다.새것처럼.모든것을."></a></h1>

		<div class="hd_banner" id="op-top-banner">
		</div><!--// hd_banner -->

		<div class="header_search">
			<jsp:include page="./saleson_query.jsp" />
		</div><!--//header_search E-->
	</div><!--// inner E-->
</div><!--// header_middle -->

<div class="hd_btm">
	<div class="inner">
		<jsp:include page="./saleson_all_categories.jsp" />

		<div class="hd_feature">
			<a href="/pages/battle_grounds_pc">리그오브레전드</a>
			<a href="/pages/overwatch">로스트아크</a>
			<a href="/pages/high_professional">배틀그라운드</a>
			<a href="/pages/body_monitor">본체+모니터</a>
			<a href="/pages/renewallpc">노트북</a>
			<a href="/pages/cash_special">현금특가</a>
		</div><!--// hd_feature -->

		<div class="hd_ico_menu">
			<a href="/event/spot" class="ico_ts">월간특가</a>
			<a href="/pages/dealer_shop" class="ico_ds">딜러샵</a>
		</div><!--// hd_ico_menu -->
	</div><!--// inner -->
</div><!--// hd_btm -->

<script type="text/javascript">
	$(function() {
		Shop.getTopBanner();
	});
</script>


<!--// 퀵메뉴지점 팝업추가 -->
<div id="popup_001" class="needpopup">
	<div class="tabs_me_pop01">
		<ul class="mmu">
			<li><a href="#" data-needpopup-show="#popup_001" class="kk001 on">영등포점</a></li>
			<li><a href="#" data-needpopup-show="#popup_002" class="kk002">킨텍스점</a></li>
			<li><a href="#" data-needpopup-show="#popup_003" class="kk003">연수점</a></li>
			<li><a href="#" data-needpopup-show="#popup_003" class="kk004">지점안내</a></li>
		</ul>
	</div>
	<div class="pop_sc_t01">
		<div id="video_slide1" class="embed-container1">
			<iframe id="player1" src="https://www.youtube.com/embed/nzPsrVgLY04" title="YouTube video player" frameborder="0" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture" allowfullscreen></iframe>
		</div>
		<div class="event_list_t01">
			<h2>이마트 진행중인 행사</h2>
			<p><a href="https://blog.naver.com/PostList.nhn?blogId=kaceroo4&from=postList&categoryNo=10&parentCategoryNo=10#category-name" target="_blank"><img src="http://bonoyahana.esellersimg.co.kr/homepage/pop_menu/button.jpg" alt="이마트 이벤트"></a></p>
		</div>
	</div>
	</div>
	
	<div id="popup_002" class="needpopup">
	<div class="tabs_me_pop01">
		<ul class="mmu">
			<li><a href="#" data-needpopup-show="#popup_001" class="kk001">영등포점</a></li>
			<li><a href="#" data-needpopup-show="#popup_002" class="kk002 on">킨텍스점</a></li>
			<li><a href="#" data-needpopup-show="#popup_003" class="kk003">연수점</a></li>
			<li><a href="#" data-needpopup-show="#popup_004" class="kk004">지점안내</a></li>
		</ul>
	</div>
	<div class="pop_sc_t01">
		<div id="video_slide2" class="embed-container2">
			<iframe id="player2" src="https://www.youtube.com/embed/O-XQl5ZBzQk" title="YouTube video player" frameborder="0" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture" allowfullscreen></iframe>
		</div>
		<div class="event_list_t01">
			<h2>이마트 진행중인 행사</h2>
			<p><a href="https://blog.naver.com/PostList.nhn?blogId=kaceroo4&from=postList&categoryNo=10&parentCategoryNo=10#category-name" target="_blank"><img src="http://bonoyahana.esellersimg.co.kr/homepage/pop_menu/button.jpg" alt="이마트 이벤트"></a></p>
		</div>
	</div>
	</div>
	
	<div id="popup_003" class="needpopup">
	<div class="tabs_me_pop01">
		<ul class="mmu">
			<li><a href="#" data-needpopup-show="#popup_001" class="kk001">영등포점</a></li>
			<li><a href="#" data-needpopup-show="#popup_002" class="kk002">킨텍스점</a></li>
			<li><a href="#" data-needpopup-show="#popup_003" class="kk003 on">연수점</a></li>
			<li><a href="#" data-needpopup-show="#popup_004" class="kk004">지점안내</a></li>
		</ul>
	</div>
	<div class="pop_sc_t01">
		<div id="video_slide3" class="embed-container3">
			<iframe id="player3" src="https://www.youtube.com/embed/xgjK2AC-jCk" title="YouTube video player" frameborder="0" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture" allowfullscreen></iframe>
		</div>
		<div class="event_list_t01">
			<h2>이마트 진행중인 행사</h2>
			<p><a href="https://blog.naver.com/PostList.nhn?blogId=kaceroo4&from=postList&categoryNo=10&parentCategoryNo=10#category-name" target="_blank"><img src="http://bonoyahana.esellersimg.co.kr/homepage/pop_menu/button.jpg" alt="이마트 이벤트"></a></p>
		</div>
	</div>
	</div>
	
	<div id="popup_004" class="needpopup">
	<div class="tabs_me_pop01">
		<ul class="mmu">
			<li><a href="#" data-needpopup-show="#popup_001" class="kk001">영등포점</a></li>
			<li><a href="#" data-needpopup-show="#popup_002" class="kk002">킨텍스점</a></li>
			<li><a href="#" data-needpopup-show="#popup_003" class="kk003">연수점</a></li>
			<li><a href="#" data-needpopup-show="#popup_004" class="kk004 on">지점안내</a></li>
		</ul>
	</div>
	<div class="pop_sc_t01 layer-ms010204">
		<!--p class="layer-title">지점안내</p-->
		<div class="layer-content">
			<dl class="sub-layout">
				<dt>
					<p class="tit">본사</p>
					<p class="txt">리뉴올PC 본사에서도 직접 구매하세요!</p>
					<p class="img"><img src="/content/images/common/main-layer-ms010204-map.jpg"></p>
					<dl class="info">
						<dt>경기도 고양시 덕양구 화랑로139</dt>
						<dd>상담시간 : 매일 AM 10:00 ~ PM 10:00 (공휴일 휴무) <br>점심시간 : PM 12:30 ~ PM 01:30</dd>
						<dd class="tel">02-334-3989</dd>
					</dl>
					<a href="http://kko.to/e1F-lywD0" target="_blank" class="btn-map" title="본사 지도 새창으로 바로가기">지도보기</a>
				</dt>
				<dd>
					<p class="tit">이마트 영등포점</p>
					<dl class="info">
						<dt>서울 영등포구 영중로 15 타임스퀘어</dt>
						<dd>상담시간 : 매일 AM 10:00 ~ PM 10:00 (둘째,넷째 일요일 휴무)</dd>
						<dd class="tel">02-2633-2432</dd>
					</dl>
					<a href="http://kko.to/K_Er2ywDT" target="_blank" class="btn-map" title="이마트 영등포점 지도 새창으로 바로가기">지도보기</a>
					<p class="tit">이마트 킨텍스점</p>
					<dl class="info">
						<dt>경기 고양시 일산서구 킨텍스로 171</dt>
						<dd>상담시간 : 매일 AM 10:00 ~ PM 10:00 (둘째,넷째 수요일 휴무)</dd>
						<dd class="tel">031-924-2432</dd>
					</dl>
					<a href="http://kko.to/rl7Sly6DH" target="_blank" class="btn-map" title="이마트 킨텍스점 지도 새창으로 바로가기">지도보기</a>
					<p class="tit">이마트 연수점</p>
					<dl class="info">
						<dt>인천 연수구 경원대로 184</dt>
						<dd>상담시간 : 매일 AM 10:00 ~ PM 10:00 (둘째,넷째 수요일 휴무)</dd>
						<dd class="tel">032-811-2432</dd>
					</dl>
					<a href="http://kko.to/I4nPly6Do" target="_blank" class="btn-map" title="이마트 연수점 지도 새창으로 바로가기">지도보기</a>
				</dd>
			</dl><!--// sub-layout -->
			<p class="btm-txt">지금 리뉴올PC를 방문해주세요! <br>친절하게 상담해드립니다!</p>
		</div>
	</div>
</div>

<style>
	.needpopup-opened,.needpopup-opened body{overflow:hidden}
	.needpopup-opened.needpopup-scrolled,.needpopup-opened.needpopup-scrolled body{height:auto}
	.needpopup-opened.needpopup-scrolled body{position:fixed;width:100%}
	.needpopup_wrapper{position:fixed;z-index:20001;top:0;left:0;visibility:hidden;overflow:auto;
		-webkit-box-sizing:border-box;box-sizing:border-box;width:100%;height:100%;padding:0;
		-webkit-transition:opacity .3s ease;transition:opacity .3s ease;opacity:0;background:#000;background: rgba(0,0,0,0.5);}
	.needpopup-opened .needpopup_wrapper{visibility:visible;opacity:1}
	.needpopup-overflow .needpopup_wrapper{padding:10px}
	.needpopup{position:relative;z-index:9999;top:50%;left:50%;display:none;-webkit-box-sizing:border-box;box-sizing:border-box;width:550px;max-width:100%;
		margin-left:-275px;padding:0;-webkit-transform:scale(.1,.1);-ms-transform:scale(.1,.1);transform:scale(.1,.1);opacity:0;background:#fff}
	.needpopup.opened{-webkit-transition:opacity .5s ease,-webkit-transform .5s ease;transition:opacity .5s ease,transform .5s ease;-webkit-transform:scale(1,1);-ms-transform:scale(1,1);transform:scale(1,1);opacity:1}
	.needpopup.stacked{top:0!important;margin-top:0!important}
	.needpopup-overflow .needpopup{left:0;width:auto;margin-left:0}
	.needpopup_remover{position:fixed;z-index:9999;top:30px;right:30px;font-size:40px;line-height:.5;color:#fff;text-decoration:none}
	.needpopup_remover:hover{color:#ccc}
	.needpopup_remover:before{content:''; width:70px; height:70px; display: block; background:url('https://www.renewallpc.co.kr/content/mobile/images/common/btn_close.png') center 50% no-repeat; z-index:0;}
	.needpopup .needpopup_remover{top:0;right:0;font-size:20px;line-height:.5;color:#000}
	.needpopup .needpopup_remover:hover{color:#ccc}
	
	.needpopup{max-height: 90vh;}
	.pop_sc_t01{max-height: calc(90vh - 70px); overflow: hidden; overflow-y: auto;}
	.tabs_me_pop01{height: 70px;}
	.embed-container1, .embed-container2, .embed-container3, .embed-container4 {position: relative; padding: 20px; padding-bottom: 56.25%; height: 0; overflow: hidden; max-width: 100%;} 
	.embed-container1 iframe, .embed-container1 object, .embed-container1 embed { position: absolute; top: 0; left: 0; width: 100%; height: 100%; }
	.embed-container2 iframe, .embed-container2 object, .embed-container2 embed { position: absolute; top: 0; left: 0; width: 100%; height: 100%; }
	.embed-container3 iframe, .embed-container3 object, .embed-container3 embed { position: absolute; top: 0; left: 0; width: 100%; height: 100%; }
	.embed-container4 iframe, .embed-container4 object, .embed-container4 embed { position: absolute; top: 0; left: 0; width: 100%; height: 100%; }
	
	.tabs_me_pop01 .mmu{margin:0; padding: 15px 0 0 15px; }
	.tabs_me_pop01 .mmu>li{display: inline-block;}
	.tabs_me_pop01 .mmu>li>a{display: block; padding: 0 15px; background-color: #fff; height: 40px; line-height: 40px; color: #202020; font-weight: 500; text-decoration: none; font-size: 18px; text-align: center;}
	.tabs_me_pop01 .mmu>li>a.on, .tabs_me_pop01 .mmu>li>a:hover{ background-color: #202020; color: #fff;}
	
	.event_list_t01{margin: 0 20px;}
	.event_list_t01>p{padding: 5px 0 20px 0;}
	.event_list_t01>h2{font-size: 24px; margin-bottom: 0; font-weight: 500; color: #1f1f1f;}
	.event_list_t01>ul{padding: 0; margin: 0;} 
	.event_list_t01>ul:after{content:''; display:block; clear:both;} 
	.event_list_t01>ul>li{float: left; width: 49%; list-style: none;}
	.event_list_t01>ul>li:first-child {margin-right: 1%;}
	.event_list_t01>ul>li:nth-child(even) {margin-left: 1%;}
	.event_list_t01 img {max-width: 100%;}

</style>
<!-- partial -->
<script src="/content/js/needpopup.js"></script>
<script>  
	needPopup.config.custom = {
		'removerPlace': 'outside',
		'closeOnOutside': false,
		onShow: function() {
			console.log('needPopup is shown');
		},
		onHide: function() {
			console.log('needPopup is hidden');
			
		}
	};
	needPopup.init();
	
	

	
$(function(){
	var embed = $('#player1'); //동영상 코드
	$('.kk001').on('click', function(){ //레이어 닫을때
		$(this).parents('.needpopup').hide();
		$('.embed-container1').empty();
		$('.embed-container1').append(embed);
	})
	$('.needpopup_wrapper').on('click', function(){ //레이어 닫을때
		$(this).parents('.needpopup').hide();
		$('.embed-container1').empty();
		$('.embed-container1').append(embed);
	})
});
	
$(function(){
	var embed = $('#player2'); //동영상 코드
	$('.kk002').on('click', function(){ //레이어 닫을때
		$(this).parents('.needpopup').hide();
		$('.embed-container2').empty();
		$('.embed-container2').append(embed);
	})
	$('.needpopup_wrapper').on('click', function(){ //레이어 닫을때
		$(this).parents('.needpopup').hide();
		$('.embed-container2').empty();
		$('.embed-container2').append(embed);
	})
});
	
$(function(){
	var embed = $('#player3'); //동영상 코드
	$('.kk003').on('click', function(){ //레이어 닫을때
		$(this).parents('.needpopup').hide();
		$('.embed-container3').empty();
		$('.embed-container3').append(embed);
	})
	$('.needpopup_wrapper').on('click', function(){ //레이어 닫을때
		$(this).parents('.needpopup').hide();
		$('.embed-container3').empty();
		$('.embed-container3').append(embed);
	})
});
	
$(function(){
	var embed = $('#player4'); //동영상 코드
	$('.kk004').on('click', function(){ //레이어 닫을때
		$(this).parents('.needpopup').hide();
		$('.embed-container4').empty();
		$('.embed-container4').append(embed);
	})
	$('.needpopup_wrapper').on('click', function(){ //레이어 닫을때
		$(this).parents('.needpopup').hide();
		$('.embed-container4').empty();
		$('.embed-container4').append(embed);
	})
});


//$(function(){
	//var embed = $('#player'); //동영상 코드
	//$('.kk001').on('click', function(){ //레이어 닫을때
		//$(this).parents('.needpopup').hide();
		//$('.embed-container').empty();
		//$('.embed-container').append(embed);
	//})
	//$('.needpopup_wrapper').on('click', function(){ //레이어 닫을때
		//$(this).parents('.needpopup').hide();
		//$('.embed-container').empty();
		//$('.embed-container').append(embed);
	//})
//});
</script>