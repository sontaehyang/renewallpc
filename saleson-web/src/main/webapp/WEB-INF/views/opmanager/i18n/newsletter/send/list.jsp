<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions" %>
<%@ taglib prefix="shop"	uri="/WEB-INF/tlds/shop" %>


		<div class="location">
			<a href="#"></a> &gt;  <a href="#"></a> &gt; <a href="#" class="on"></a>
		</div>

		<h3><span>메일 매거진 리스트</span></h3>
			<div class="board_write">
				<table class="board_write_table" summary="메일 매거진 리스트">
					<caption>메일 매거진 리스트</caption>
					<colgroup>
						<col style="width:15%;" />
						<col style="width:85%;" />
					</colgroup>
					<tbody>
						<tr>
							<td class="label">메일명</td>
							<td>
								<div>
									<input type="text" class="seven" title="메일명">
								</div>
							</td>
						</tr>
					</tbody>
				</table>						 
			</div> <!--// board_write E-->
			
			<div class="btn_all">
				<div class="btn_left">
					<button type="button" class="btn btn-dark-gray btn-sm"><span>초기화</span></button>
				</div>
				<div class="btn_right">
					<button type="button" class="btn btn-dark-gray btn-sm"><span>검색</span></button>
				</div>
			</div><!--//btn_all E-->
					
			<div class="sort_area mt30">
				<div class="left">
					<span>검색결과 : 20개의 메일 메거진이 검색되었습니다. </span>  			 
				</div>
				<div class="right">
					<span>출력수 : </span>
					<select title="화면출력">
						<option value="10개 출력">10개 출력</option>
						<option value="20개 출력">20개 출력</option>
						<option value="50개 출력">50개 출력</option>
						<option value="100개 출력">100개 출력</option>
					</select>
				</div>
			</div><!--//sort_area E-->
			
			<div class="board_write">
				<table class="board_list_table" summary="주문내역 리스트">
					<caption>주문내역 리스트</caption>
					<colgroup>
						<col style="width:3%;" />
						<col style="width:*" />					
						<col style="width:15%;" />
						<col style="width:10%;" />			
						<col style="width:10%;" />
						<col style="width:15%;" />
						<col style="width:15%;" />
					</colgroup>
					<thead>
						<tr>
							<th scope="col"><input type="checkbox" name="tempId2" id="tempId2" title="체크박스"></th>							 
							<th scope="col">메일명</th>
							<th scope="col">발송날짜</th>
							<th scope="col">발송건수</th>
							<th scope="col">메일 클릭수</th>
							<th scope="col">메일 발송</th>
							<th scope="col">수정/삭제</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td><input type="checkbox" name="tempId24" id="tempId24" title="체크박스"></td>							 							 
							<td>ホン·ギルドン</td>
							<td>2014-03-31 14:02:58</td>
							<td>10</td>
							<td>5</td>
							<td>
								<div>
									<a href="#" class="table_btn">발송하기</a>
								</div>
							</td>
							<td>
								<div>
									<a href="#" class="table_btn">수정</a>
									<a href="#" class="table_btn">삭제</a>
								</div>
							</td>
						</tr>
						<tr>
							<td><input type="checkbox" name="tempId24" id="tempId24" title="체크박스"></td>							 							 
							<td>ホン·ギルドン</td>
							<td>2014-03-31 14:02:58</td>
							<td>10</td>
							<td>5</td>
							<td>
								<div>
									발송완료
								</div>
							</td>
							<td>
								<div>
									<a href="#" class="table_btn">수정</a>
									<a href="#" class="table_btn">삭제</a>
								</div>
							</td>
						</tr>
						
						<tr>
							<td><input type="checkbox" name="tempId24" id="tempId24" title="체크박스"></td>							 							 
							<td>ホン·ギルドン</td>
							<td>2014-03-31 14:02:58</td>
							<td>10</td>
							<td>5</td>
							<td>
								<div>
									<a href="#" class="table_btn">발송하기</a>
								</div>
							</td>
							<td>
								<div>
									<a href="#" class="table_btn">수정</a>
									<a href="#" class="table_btn">삭제</a>
								</div>
							</td>
						</tr>
						
					</tbody>
				</table>				 
			</div>
			
			
			<div class="btn_all pt20">
				<div class="btn_left">
					<button type="button" class="btn btn-dark-gray btn-sm"><span>삭제</span></button>
					<button type="button" class="btn btn-dark-gray btn-sm"><span>발송하기</span></button>
				</div>
				<div class="btn_right">
					<button type="button" class="btn btn-dark-gray btn-sm"><span>등록</span></button>
				</div>
			</div>
			
			<p class="pagination">
				<a href="#" class="first">&lt;&lt;</a>
				<a href="#" class="prev">&lt;</a>
				<a href="#">1</a>
				<a href="#">2</a>
				<a href="#">3</a>
				<a href="#">4</a>
				<a href="#">5</a>
				<strong>6</strong>
				<a href="#">7</a>
				<a href="#">8</a>
				<a href="#">9</a>
				<a href="#">10</a>
				<a href="#" class="next">&gt;</a>
				<a href="#" class="last">&gt;&gt;</a>
			</p>

