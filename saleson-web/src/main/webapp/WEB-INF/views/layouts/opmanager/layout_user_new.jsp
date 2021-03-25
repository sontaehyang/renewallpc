<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions" %>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page"%>
<!doctype html>
<html lang="ja" class="opmanager">
<head>
<tiles:insertAttribute name="head"></tiles:insertAttribute>

<style type="text/css">
	body, ul, p{margin:0; padding:0;  font-family: 'Nanum Gothic';}
	li{list-style:none;}
	#pop_header{position:relative;  margin:0 auto; padding:30px 30px 15px 30px; background:#424759; }
	.admin_info{padding:0 0 10px 0; }
	.admin_info:after{content:''; display:block; clear:both; }
	.admin_info .name{float:left; display:block; color:#fff; font-size:25px;  font-weight:bold;  position:relative; top:-7px; }
	.admin_info  .control{float:left; margin-left:10px; height:25px;}
	.admin_info  .control a{display:inline-block; } 
	.login_info{float:left; margin-left:60px; }
	.login_info span{display:inline-block; color:#c7c8ce; line-height:20px; font-size:13px;}
	.login_info span.member{padding:0 5px;  border:1px solid #888b96; border-radius:3px; font-size:12px;}
	.login_info span.last_login{padding:0 5px 0 6px;  }
	.login_info span.last_info{
		padding-left:10px; 
		background:url('/content/images/common/icon_bar2.gif') no-repeat 0 center;
	}
	.info_list{width:100%; border-left:1px solid #5d6170; }
	.info_list ul{margin:0; padding:0;}
	.info_list ul:after{content:''; display:block; clear:both; }
	.info_list ul li{float:left; width:25%;  } 
	.info_list ul li a{display:block;  padding:12px 20px;  border:1px solid #5d6170; border-left:0;  font-size:13px;} 
	.info_list ul li a:after{content:''; display:block; clear:both; }
	.info_list ul li .name{display:block; float:left; width:50%;  color:#c7c8ce; }
	.info_list ul li p{display:block; float:right; width:50%;  color:#81bdf0; text-align:right; } 
	.info_list ul li p span:first-child{font-size:18px; }
	.btn_close{position:absolute; top:20px; right:20px; z-index:100;}
	.left_content {
		width:50%;
		float:left;
	}
	
	.right_content {
		width:50%;
		float:right;
	}
	
	.popup_contents h3 {
		display: inline;
		border-left: 0px;
		padding-left: 0px;
	}
	
</style>
</head>
<body>
<div class="popup_wrap">
	<!--h1 class="popup_title">${op:message('M00831')}</h1> <!-- 회원정보관리 -->
	<div id="pop_header">
			<div class="admin_info">
				<span class="name">${user.userName}</span>
				<div class="control">
					<a href="javascript:;" id="passwordChange"><img src="/content/opmanager/images/icon/icon_rock2.png" alt=""></a>
					<a href="javascript:;" onclick="Common.popup('/opmanager/user/popup/claim-write/${userId}', 'claim_write', 500, 600, 1)"><img src="/content/opmanager/images/icon/icon_comment.png" alt=""></a>
					<a href="javascript:;" onclick="Common.popup('/opmanager/user/popup/send-sms/${userId}', 'send-sms', 500, 600, 1)"><img src="/content/opmanager/images/icon/icon_mobile.png" alt=""></a>
					<a href="javascript:;" onclick="alert('기능 준비중 입니다.');"><img src="/content/opmanager/images/icon/icon_sms2.png" alt=""></a>
				</div>
				<div class="login_info">
					<span class="member">${user.userDetail.userlevel.levelName}</span>
					<span class="last_login">최종 로그인 : ${op:datetime(user.loginDate)} </span>
					<span class="last_info">최종 정보수정 : ${op:datetime(user.updatedDate)}</span>
				</div> <!--// login_info E-->
			</div><!--// admin_info E-->
	
			<div class="info_list">
				<ul>
					<li>
						<a>
							<span class="name">사용가능 ${op:message('M00246')}</span>
							<p>
								<span>${op:numberFormat(avilablePoint)}</span>
								<span>P</span>
							</p>
						</a>
					</li>
					<li>
						<a>
							<span class="name">보유한 쿠폰</span>
							<p>
								<span>${op:numberFormat(couponCount.downloadCouponCount)}</span>
								<span>장</span>
							</p>
						</a>
					</li>
					<li>
						<a>
							<span class="name">배송비 쿠폰</span>
							<p>
								<span>${op:numberFormat(avilablePoint2)}</span>
								<span>장</span>
							</p>
						</a>
					</li> 
				</ul>
			</div> <!--// info_list E-->
			<a href="javascript:self.close();" class="btn_close"><img src="/content/opmanager/images/btn/btn_close.png" alt="닫기"></a>
		</div> <!--// pop_header E-->
	<div class="popup_contents">
		<div id="tab_nav" class="tab_nav col6 pt10" style="clear: both;">
			<ul style="margin-bottom:0px">
				<li id="details"><a href="/opmanager/user/popup/details/${userId}">요약보기</a></li>
				<li id="modify"><a href="/opmanager/user/popup/edit/${userId}">${op:message('M00829')}</a></li> <!-- 회원정보수정 -->
				<li id="sns-user"><a href="/opmanager/user/popup/sns-user/${userId}">SNS연결상태</a></li>
				<li id="delivery"><a href="/opmanager/user/popup/delivery-list/${userId}">배송지관리</a></li> <!-- 나의 배송지 관리 -->
				<li id="point"><a href="/opmanager/user/popup/point/point/${userId}">${op:message('M00246')} 관리</a></li> <!-- 포인트 관리 -->
				<li id="shipping"><a href="/opmanager/user/popup/point/shipping/${userId}">배송비쿠폰관리</a></li>
				<li id="coupon"><a href="/opmanager/user/popup/coupon/${userId}">쿠폰조회</a></li>
				<li id="memo"><a href="/opmanager/user/popup/claim-memo-list/${userId}">${op:message('M01664')}</a></li> <!-- 상담내역 -->
				<li id="sendMail"><a href="/opmanager/user/popup/send-mail-log-list/${userId}">${op:message('M00301')}</a></li> <!-- 메일발송내역 -->
				<li id="sendSms"><a href="/opmanager/user/popup/send-sms-log-list/${userId}">${op:message('M01667')}</a></li> <!-- 상담내역 -->
				<li id="secede"><a href="/opmanager/user/popup/delete/${userId}">${op:message('M00830')}</a></li> <!-- 회원탈퇴 -->
			</ul>
		</div>
		<div id="activity3" class="tab_content on">
			<tiles:insertAttribute name="content"></tiles:insertAttribute>
		</div>		
	</div>
</div>

<page:javascript>
<script type="text/javascript">
$(function() {
	$("#passwordChange").on("click", function(){
		Common.popup('/opmanager/user/popup/password/${user.userId}', 'donation', 420, 400);
	});
});
</script>
</page:javascript>

<tiles:insertAttribute name="common"></tiles:insertAttribute>
<tiles:insertAttribute name="script"></tiles:insertAttribute>
</body>
</html>