<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions" %>
<%@ taglib prefix="shop"	uri="/WEB-INF/tlds/shop" %>

	<!-- 본문 -->
<div class="popup_wrap">
	
	<h1 class="popup_title">${op:message('M01190')}</h1> <!-- 쿠폰 발급하기 -->
	
	<div class="popup_contents">
		<p class="pop_tit">${op:message('M01191')}</p> <!-- 회원검색 -->
		<form:form modelAttribute="coupon" method="get">
			<form:hidden path="searchCouponTargetUser.addType" value="search" />
			<div class="board_write">			
				<table class="board_write_table" summary="${op:message('M01190')}">
					<caption>${op:message('M01190')}</caption> <!-- 쿠폰 발급하기 -->
					<colgroup>
						<col style="width: 20%;" />
						<col style="width: 30%;" />
						<col style="width: 20%;" />
						<col style="width: 30%;" />
					</colgroup>
					<tbody>
						<tr>
							<td class="label">${op:message('M00081')}</td>	<!-- 아이디 -->
							<td>
								<div>
									<form:input path="searchCouponTargetUser.loginId" title="아이디" id="loginId"/>
								</div>
							</td>
							<td class="label">${op:message('M00005')}</td>	<!-- 이름 -->
							<td>
								<div>
									<form:input path="searchCouponTargetUser.userName" title="이름"/>
								</div>
							</td>
						</tr>
						<tr>
							<td class="label">${op:message('M00125')}</td>	<!-- 이메일 -->
							<td>
								<div>
									<form:input path="searchCouponTargetUser.email" title="이메일"/>
								</div>
							</td>
							<td class="label">수신여부</td>	
							<td>
								<div>
									<form:checkbox path="searchCouponTargetUser.searchReceiveEmail" value="0"/> 이메일
									<form:checkbox path="searchCouponTargetUser.searchReceiveSms" value="0"/> SMS
								</div>
								
							</td>
						</tr>
					</tbody>
				</table>
				
			</div> <!--// board_write -->
			<div class="btn_all">
				<div class="btn_left">
					<a href="/opmanager/coupon/find-user" class="btn btn-dark-gray btn-sm"><span class="glyphicon glyphicon-repeat"></span> ${op:message('M00047')}</a> <!-- 초기화 -->
				</div>
				<div class="btn_right">
					<button type="submit" class="btn btn-dark-gray btn-sm"><span class="glyphicon glyphicon-search"></span> ${op:message('M00048')}</button> <!-- 검색 -->
				</div>
			</div>
			
			<div class="btn_all">
				<div class="btn_right mb0">
					<a href="#" id="addAllUsers" class="btn btn-active btn-sm"><span class="glyphicon glyphicon-plus"></span> 검색회원 전체추가</a>
				</div>
			</div>
			
			<div class="count_title mt20">
				<h5>전체 회원수 : <span id="userCount"style="display:inline-block;color:black;">${totalCount}</span> /&nbsp;<span class="userCount" style="font-weight:bold;display:inline-block;">체크한 회원수 : 0명</span></h5>
				
				<span style="float:right;">
					<form:select path="itemsPerPage" title="${op:message('M00054')}${op:message('M00052')}"
						onchange="$('form#coupon').submit();"> <!-- 화면 출력수 -->
						<form:option value="10" label="10${op:message('M00053')}" /> <!-- 개 출력 -->
						<form:option value="30" label="30${op:message('M00053')}" /> <!-- 개 출력 -->
						<form:option value="50" label="50${op:message('M00053')}" /> <!-- 개 출력 -->
						<form:option value="100" label="100${op:message('M00053')}" /> <!-- 개 출력 -->
					</form:select>
				</span>
			</div>
		</form:form>	
		
		<div class="board_write mt20">
			<table class="board_list_table mt0" summary="${op:message('M00210')}">
				<caption>${op:message('M00210')}</caption>
				<colgroup>
					<col style="width: 8%;">
					<col style="width: 15%;">
					<col style="width: 13%;">
					<col style="width: 12%;">
					<col style="width: 12%;">
					<col style="width: 15%;">
				</colgroup>
				<thead>
					<tr>
						<th><input type="checkbox" id="allCheck" title="${op:message('M00431')}"></th>
						<th>${op:message('M00081')}</th> <!-- 아이디 -->
						<th>${op:message('M01192')}</th> <!-- 고객명 -->
						<th>${op:message('M00212')}</th> <!-- 회원등급 -->
						<th>${op:message('M01193')}</th> <!-- 회원그룹 -->
						<th>${op:message('M00125')}</th> <!-- 이메일 -->			
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${list}" var="user" varStatus="i">
						<tr>
							<td><input type="checkbox"  name="userId" value="${user.userId}" title="${op:message('M00431')}"></td> <!-- 선택 --> 
							<td class="loginId">${user.loginId}</td>
							<td>${user.userName}</td>
							<td>${user.userDetail.levelName}</td> <!-- 회원등급 -->
							<td>${user.userDetail.groupName}</td>
							<td>${user.email}</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
		<c:if test="${empty list}">
			<div class="no_content">
				${op:message('M00251')} 
			</div>
		</c:if>
		
		<div class="btn_all">
			<div class="btn_left mb0">
				<a href="javascript:self.close();" class="btn btn-default btn-sm">${op:message('M00037')}</a> <!-- 취소 -->
			</div>
			<div class="btn_right mb0">
				<a href="#" id="addUsers" class="btn btn-active btn-sm"><span class="glyphicon glyphicon-plus"></span> 선택회원 추가</a> <!-- 선택회원 추가 -->
			</div>
		</div>
		
		<page:pagination-manager />
		
	</div> <!-- // popup_contents -->
	
	<a href="#" class="popup_close">${op:message('M00353')}</a> <!-- 창 닫기 --> 
</div>

<div id="search-target">
	<input type="hidden" name="loginId" value="${coupon.searchCouponTargetUser.loginId}" class="search-query" title="아이디" />
	<input type="hidden" name="userName" value="${coupon.searchCouponTargetUser.userName}" class="search-query" title="이름" />
	<input type="hidden" name="email" value="${coupon.searchCouponTargetUser.email}" class="search-query" title="이메일" />
	<input type="hidden" name="searchReceiveEmail" value="${coupon.searchCouponTargetUser.searchReceiveEmail}" class="search-query" title="이메일수신" />
	<input type="hidden" name="searchReceiveSms" value="${coupon.searchCouponTargetUser.searchReceiveSms}" class="search-query" title="문자수신" />
	<input type="hidden" name="userCount" value="${totalCount}" />
</div>
	
<script type="text/javascript">

	function getUniqueIndex() {
		var maxIndex = 0;
		$.each(opener.$('ul#op-chosen-user-list > li.op-chose-user'), function(){
			maxIndex = Number($(this).data('index')) >= maxIndex ? Number($(this).data('index')) + 1 : maxIndex;
		})
			 
		return maxIndex;
	}

	$(function(){
		$("input[name='userId']").on('click', function(){
			$('.userCount').html('체크한 회원수 : ' + Number($("input[name='userId']:checked").size()) + '명');
		});
		
		$("#allCheck").on("click",function() {
			$("input[name='userId']").prop('checked',$(this).prop('checked'));
			$('.userCount').html('체크한 회원수 : ' + Number($("input[name='userId']:checked").size()) + '명');
		});
		
		var selectAddMaxLength = 500;
		$('#addUsers').on("click",function() {
			
			if ($("input[name='userId']:checked").size() > selectAddMaxLength) {
				alert('선택 회원 추가는 한번에 최대' + Number(selectAddMaxLength) + '명씩 가능합니다.');
				return;
			}
			
			Common.confirm("${op:message('M01195')}", function() { // 선택하신 회원을 추가 하시겠습니까?
				
				var maxIndex = getUniqueIndex();
				var id = "op-chose-user-" + maxIndex;
				var index = opener.$('ul#op-chosen-user-list > li.op-chose-user').size();	
			
				var title = "선택 회원 추가 [총 "+ Number($("input[name='userId']:checked").size()) +"명]";
				var data = "<li class=\"click op-chose-user\" id=\"" + id + "\" data-index=\""+ maxIndex +"\">" + title;
				
				data += "<input type=\"hidden\" name=\"couponTargetUsers["+ index +"].addType\" value=\"selected\" />";
				data += "<input type=\"hidden\" name=\"couponTargetUsers["+ index +"].title\" value=\""+ title + "\" />";
				$("input[name='userId']:checked").each(function() {
					data += "<input type=\"hidden\" name=\"couponTargetUsers["+ index +"].userIds\" value=\""+ $(this).val() +"\" />";
				})
				
				data += "&nbsp;<a href=\"javascript:;\" class=\"fix_btn\" style=\"right: 0;padding:2px 10px;\" onclick=\"chosenUserDelete('"+id+"')\">${op:message('M00074')}</a>";

				data += "</li>";
				opener.$("#op-chosen-user-list").append(data);
				self.close();
			});
		});
		
		$("#addAllUsers").on("click",function(){
			
			var searchUserCount = Number($('#userCount').text());
			
			if (searchUserCount == 0) {
				alert('검색된 회원이 없습니다.');
				return;
			}
			
			if (confirm("검색된 모든 사용자를 추가하시겠습니까?")) {

				var title = '';
				$.each($('#search-target').find('.search-query'), function() {
					if ($.trim($(this).val()) != '') {
						if(title != '') {
							title += ' / ';
						}
						
						title += $(this).attr('title') + ' : ' + $(this).val();
					}
				})
				
				if (title == '') {
					title = "전체 회원 추가 [총 "+ Number(searchUserCount) +"명]&nbsp;";
				} else {
					title = "검색된 회원 추가 ["+ title +"] [총 "+ Number(searchUserCount) +"명]&nbsp;";
				}
				
				var sCount = 0;
				
				var maxIndex = getUniqueIndex();
				var id = "op-chose-user-" + maxIndex;
				var index = opener.$('ul#op-chosen-user-list > li.op-chose-user').size();
				
				var data = "<li class=\"click op-chose-user\" id=\"" + id + "\" data-index=\""+ maxIndex +"\">" + title;
				
				data += "<input type=\"hidden\" name=\"couponTargetUsers["+ index +"].addType\" value=\"search\" />";
				data += "<input type=\"hidden\" name=\"couponTargetUsers["+ index +"].title\" value=\""+ title + "\" />";
				$.each($('#search-target').find('.search-query'), function() {
					var inputName = "couponTargetUsers[" + index + "]."+ $(this).attr('name');
					data += "<input type=\"hidden\" name=\""+ inputName +"\" value=\""+ $(this).val() +"\" />"; 

					sCount++;
				})

				var isInsert = true;
				$.each(opener.$('ul#op-chosen-user-list > li.op-chose-user'), function() {
					
					var eqCount = 0;	
					$.each($(this).find('input'), function() {
						var inputName = $(this).attr('name').replace(/\[.*\]/gi, '[0]').replaceAll('couponTargetUsers[0].', '');
						if ($('#search-target').find('input[name="' + inputName + '"][value="'+ $(this).val() +'"]').size() > 0) {
							eqCount++;
						}
					})
					
					if (eqCount == sCount) {
						isInsert = false;
					}
				});

				if (isInsert == false) {
					alert('이미 추가된 검색조건입니다.');
					return;
				}
				
				data += "&nbsp;<a href=\"javascript:;\" class=\"fix_btn\" style=\"right: 0;padding:2px 10px;\" onclick=\"chosenUserDelete('"+id+"')\">${op:message('M00074')}</a>";
				
				data += '</li>';
				opener.$("#op-chosen-user-list").append(data);
				self.close();
			}
		});
	});
</script>
