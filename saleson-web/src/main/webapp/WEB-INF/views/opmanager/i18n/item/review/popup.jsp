<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions" %>
<%@ taglib prefix="module" 	tagdir="/WEB-INF/tags/modules" %>

		<!-- 본문 -->
		<div class="popup_wrap">
			<h1 class="popup_title">${op:message('M00760')}</h1> <!-- 이용후기(리뷰) 추천 -->
			<div class="popup_contents">
				<h2>¶ ${op:message('M00760')}</h2>
				<table cellpadding="0" cellspacing="0" summary="" class="board_list_table">
					<caption>${op:message('M00760')}</caption>
					<colgroup>
						<col style="width:35%;" />
						<col style="width:*" />
					</colgroup>
					<thead>
						<tr>
							<th colspan="2">${op:message('M00761')}</th> <!-- 이용후기(리뷰) 추천상태 변경 --> 
						</tr>
					</thead>
					<tbody>
						<tr>
							<th scope="row">${op:message('M00431')}</th> <!-- 선택 --> 
							<td class="tleft">
								<div>
									<input type="radio" name="aa" id="review_choice" checked="checked" /> <label for="review_choice">${op:message('M00431')}</label>
									<input type="radio" name="aa" id="review_nochoice" /> <label for="review_nochoice">${op:message('M00762')}</label> <!-- 선택하지 않음 --> 
								</div>
							</td>
						</tr>
					</tbody>
				</table>

				<div class="board_guide">
					<p class="tip">- ${op:message('M00763')}</p> <!-- 채택 즉시 포인트가 지급됩니다. --> 
	 				<p class="tip">- ${op:message('M00764')}</p> <!-- 중복 채택시 중복으로 포인트가 지급 않습니다. --> 
				</div>
				 
				<p class="popup_btns">
					<a href="#" class="btn btn-active">${op:message('M00101')}</a> <!-- 저장 --> 
					<a href="#" class="btn btn-default">${op:message('M00037')}</a> <!-- 취소 --> 
				</p>	 
			</div>
			<a href="#" class="popup_close">${op:message('M00353')}</a> <!-- 창 닫기 --> 
		</div>
