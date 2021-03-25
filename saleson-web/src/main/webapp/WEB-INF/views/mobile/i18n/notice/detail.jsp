<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" 	uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sec" 	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="op" 	uri="/WEB-INF/tlds/functions" %>
<%@ taglib prefix="fn" 	uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="page" tagdir="/WEB-INF/tags/page" %>
<%@ taglib prefix="shop"	uri="/WEB-INF/tlds/shop" %>

		<div id="container">
			<div class="title">
				<h2>고객센터</h2>
				<span class="his_back"><a href="javascript:void(0);" class="ir_pm" onclick="history.back();">뒤로가기</a></span>
				<ul class="tab_list01">
					<li class="on"><a href="/m/notice/list" class="on">공지사항</a></li>
					<li><a href="/m/faq">FAQ</a></li>
					<li><a href="/m/qna">1:1문의</a></li>
				</ul>
			</div><!--// title E -->
			<div class="con">
				<div class="customer_wrap">
					<div class="notice_view">
						<div class="content">
							<div class="tit_wrap">
								<p class="tit">${notice.subject}</p>
								<p class="info">
									<span class="date">${op:date(notice.createdDate)}</span>
									<span calss="hit"></span>
								</p>
							</div><!--// tit_wrap E -->
							<div class="con">
								${notice.content}
							</div><!--// con E -->
						</div><!--// content E -->
						<%--
						<div class="comment_wrap">
						<div class="comment_write">
							<p class="comment_total">댓글 총 <span>1</span>개</p>
							<div class="write_wrap">
								<div class="comment_profile_area">
									<div class="comment_box_name">
										<span class="comment_write_name">리뉴올PC맨</span>
									</div>
								</div>
								<div class="comment_write_area">
									<div class="comment_inbox">
										<textarea id="comment_write_textarea" class="comment_text" rows="3" cols="30"></textarea>
										<label for="comment_write_textarea" class="comment_guide">댓글을 입력해주세요</label>
									</div>
								</div>
								<div class="comment_count_area">
									<p class="comment_count"><span class="comment_count_num">0</span>/<span class="comment_write_total">1000</span></p>
								</div>
								<div class="comment_btn">
									<button type="button" class="comment_btn_reset">취소</button>
									<button type="submit" class="comment_btn_submit">등록</button>
								</div>
							</div>
						</div>
						<!-- //comment_write -->
						
						<div class="comment_list">
							<ul>
								<li>
									<div class="comment_info_area">
										<p class="comment_info_name">댓글작성자</p>
										<p class="comment_info_date"><span class="date">댓글작성일</span><span class="time">작성시간</span></p>
									</div>
									<div class="comment_con_area">
										<p>내용</p>
									</div>
									<div class="comment_btn">
											<button type="button" class="btn_st3 t_small t_lgray b_white s_small">수정</button>
										<button type="button" class="btn_st3 t_small t_lgray b_white s_small">삭제</button>
									</div>
								</li>
							</ul>
							<div class="load_more">
								<button type="button" class="btn_st2"><span>더보기</span></button>
							</div>
						</div>
						<!-- //comment_list -->
					 --%>
						<div class="btn_wrap">
							<button class="btn_st1 back" onClick="location.href='/m/notice/list'">목록</button>
						</div><!--// btn_wrap -->
					</div><!--// notice_view E -->
				</div><!--// customer_wrap E -->
			</div><!--// con E -->
		</div><!--// container E -->
