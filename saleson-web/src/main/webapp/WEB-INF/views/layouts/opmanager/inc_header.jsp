<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" 	uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="op" uri="/WEB-INF/tlds/functions" %>

<fmt:formatDate value="<%=new java.util.Date()%>" pattern="yyyy/MM/dd (EEE)" var="today"/>

	<div id="header">
		<div class="topMenu">
			<h2 class="top_logo"><a href="<c:url value="/opmanager"/>" title="SALESON"><%--<img src="/content/opmanager/images/saleson_logo.gif" alt="SALESON">--%></a></h2>
			<ul class="tnb clear_fix">
				<%--<li class="erp"><a href="javascript:alert('서비스를 신청해 주세요.')" title="ISM">ISM</a></li>--%>
				<li class="home"><a href="${op:property('saleson.url.shoppingmall')}" title="홈페이지">홈페이지</a></li>
				<li class="info"><span>${requestContext.user.userName}</span>로 접속중입니다.</li>
				<li class="login"><a class="btn_logout" href="/op_security_logout?target=/opmanager" title="LOGOUT">LOGOUT</a></li>
			</ul><!--// topMenu E-->
		</div><!--// topMenu E-->
		<div class="clear_fix">
			<h1 class="logo"><a href="/opmanager" title="${shopContext.config.shopName } 관리자">${shopContext.config.shopName }<span>관리자</span></a></h1>
			<ul class="gnb clear_fix">
				<c:forEach items="${requestContext.opmanagerMenu.firstMenuList}" var="firstMenu">
					<c:set var="menuName">MENU_${firstMenu.menuId}</c:set>
					<c:choose>
						<c:when test="${firstMenu.menuId == requestContext.opmanagerMenu.firstMenuId}">
							<li class="on"><a href="${op:url(firstMenu.menuUrl)}">${op:message(menuName)}</a></li>
						</c:when>
						<c:otherwise>
							<li><a href="${op:url(firstMenu.menuUrl)}">${op:message(menuName)}</a></li>
						</c:otherwise>
					</c:choose>
				</c:forEach>
				<li><a href="http://help.homenbiz.co.kr/customer/?C_CD=TXRLC-94243-HNQLI-28612" target="_blank">유지보수</a></li>

			</ul><!--// gnb E-->
		</div>
	</div><!--// header E-->
	<%-- 	<h1 class="text"><a href="<c:url value="/opmanager" />">SALESON<span>관리자</span></a></h1>
		
		<!-- tnb -->
		<div class="tnb">
			<ul>
				<li><a href="${op:property('saleson.url.shoppingmall')}"><img src="/content/opmanager_image/icon/icon_home.png" alt="" /> ${op:message('M00779')}</a></li> <!-- 홈페이지 -->
				
				<li><a href="/op_security_logout?target=/opmanager"><img src="/content/opmanager_image/icon/icon_logout.png" alt="" /> ${op:message('M00780')}</a></li> <!-- 로그아웃 -->
			</ul>
			<p><em>${requestContext.user.userName}</em>${op:message('M00781')}</p> <!-- 님 환영합니다. -->
			<!-- 
			<span>최근접속시간 - 2013.08.07(Wed) <span id="clock"></span> KST</span></p>
			-->
		</div>
		
		<!-- gnb -->
		<ul class="gnb">
			<c:forEach items="${requestContext.opmanagerMenu.firstMenuList}" var="firstMenu">
				<c:set var="menuName">MENU_${firstMenu.menuId}</c:set>
				<c:choose>
					<c:when test="${firstMenu.menuId == requestContext.opmanagerMenu.firstMenuId}">
						<!-- 
						<li class="on"><a href="${op:url(firstMenu.menuUrl)}">${firstMenu.menuName}</a></li>
						-->
						<li class="on"><a href="${op:url(firstMenu.menuUrl)}">${op:message(menuName)}</a></li>
					</c:when>
					<c:otherwise>
						<li><a href="${op:url(firstMenu.menuUrl)}">${op:message(menuName)}</a></li>
					</c:otherwise>
				</c:choose>
			</c:forEach>
			
			


		</ul>
	</div>
	--%>
 	<!-- 내용 -->
	<div id="container">
		<c:if test="${main != 'main'}">
			<!-- lnb -->
			<div class="lnb">
				
				<h2 id="first-menu-name"></h2>
				<ul class="lnbs">
			
					<!-- MYSQL -->
					<c:forEach items="${requestContext.opmanagerMenu.secondAndThirdMenuList}" var="secondMenu" varStatus="i">
						<c:set var="menuName">MENU_${secondMenu.menuId}</c:set>
						<c:if test="${secondMenu.displayFlag == 'Y'}">
							<li class="menu"><!--<a>${secondMenu.menuName}</a> -->
								<a class="on">${op:message(menuName)}</a>
								<ul class="depth2">
									<c:forEach items="${secondMenu.childMenu}" var="thirdMenu">
										<c:if test="${thirdMenu.displayFlag == 'Y'}">
									
											<c:if test="${op:url(thirdMenu.menuUrl) != '/opmanager/shop-statistics/sales/user'}">
												<c:set var="menuName">MENU_${thirdMenu.menuId}</c:set>
												<c:choose>
													<c:when test="${thirdMenu.menuCode == requestContext.opmanagerMenu.menuCode}">
														<li class="sub_menu on"><a href="${op:url(thirdMenu.menuUrl)}" class="on">- ${op:message(menuName)}</a></li> <!--  ${thirdMenu.menuName} -->
													</c:when>
													<c:otherwise>
														<li class="sub_menu"><a href="${op:url(thirdMenu.menuUrl)}">- ${op:message(menuName)}</a></li>	<!--  ${thirdMenu.menuName} -->
													</c:otherwise>
												</c:choose>
											</c:if>
										</c:if>
									</c:forEach>
								</ul>
							</li>
						</c:if>
					</c:forEach>
					<%--게시판 목록 --%>
					<c:if test="${!empty boardContext.boardCfgList}">
						<li class="menu">
							<a class="on">게시판 관리</a>
							<ul class="depth2">
								<c:forEach items="${boardContext.boardCfgList}" var="boardCfg">
									<li class="sub_menu ${boardCfg.boardCode == boardContext.boardCode ? 'on' : ''}"><a href="/opmanager/board/${boardCfg.boardCode}">- ${boardCfg.subject}</a></li>
								</c:forEach>
							</ul>
						</li>
					</c:if> 
					
				</ul>
				<!-- ORACLE 
				<c:forEach items="${requestContext.opmanagerMenu.secondAndThirdMenuList}" var="secondMenu" varStatus="i">
					<c:if test="${secondMenu.menuType == 2}">
						<c:if test="${i.count > 1}">
						</ul>
						</c:if>
						
						<li><a >${secondMenu.menuName}</a>
							<a href="#" class="handle close">서브메뉴 닫기</a>
							<ul>
							
					</c:if>
					
					<c:if test="${secondMenu.menuType == 3}">
						<c:choose>
							<c:when test="${secondMenu.menuCode == requestContext.opmanagerMenu.menuCode}">
								<li class="on"><a href="${secondMenu.menuUrl}">${secondMenu.menuName}</a></li>
								
							</c:when>
							<c:otherwise>
								<li><a href="${secondMenu.menuUrl}">${secondMenu.menuName}</a></li>
								
							</c:otherwise>
						</c:choose>
						
						
					</c:if>
				</c:forEach>
					</li>
				</ul>
				
				-->			
			</div>
			<a href="#" class="lnb_handle close">메뉴 닫기</a>
			
			<script>
				function Lnb(){
					var menu_a  = $('.menu > a'); 
					
					menu_a.click(function(e) {
						e.preventDefault();
						if(!$(this).hasClass('on')) {
							$(this).addClass('on').next().stop(true,true).slideDown('800');
						} else {
							$(this).removeClass('on');
							$(this).next().stop(true,true).slideUp('800');
						}
					}); 
				}
				Lnb();

			</script>
			
			<div class="contents">
				<div class="contents_inner">
		</c:if>
	
