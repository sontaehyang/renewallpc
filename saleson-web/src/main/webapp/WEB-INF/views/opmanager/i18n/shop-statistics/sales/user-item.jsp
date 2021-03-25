<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" 	uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions" %>

<div class="location">
	<a href="#">통계</a> &gt;  <a href="#">매출통계</a> &gt; <a href="#" class="on">회원별 매출</a>
</div>
<div class="statistics_web">
	<h3><span>${op:message('M01407')}</span></h3> <!-- 회원별 매출 -->
	
	<div class="board_write">						
		<table class="board_write_table" summary="${op:message('M01407')}"><!-- 회원별 매출 -->
			<caption>${op:message('M01407')}</caption><!-- 회원별 매출 -->
			<colgroup>
				<col style="width: 20%;">
				<col style="width: auto;"> 
			</colgroup>
			<tbody>
				<tr>
					<td class="label">${op:message('M01347')}</td> <!-- 기간 -->
					<td>
				 		<div>
							<span class="datepicker"><input type="text" class="term hasDatepicker" title="${op:message('M00507')}" id="dp28"><button type="button" class="ui-datepicker-trigger"><span class="icon_calendar">주문일자 시작일 달력 선택</span></button></span> <!-- 시작일 -->
							<span class="wave">~</span>
							<span class="datepicker"><input type="text" class="term hasDatepicker" title="${op:message('M00509')}" id="dp29"><button type="button" class="ui-datepicker-trigger"><span class="icon_calendar">주문일자 종료일 달력 선택</span></button></span> <!-- 종료일 -->
								<span class="day_btns"> 
								<a href="#" class="table_btn">1개월</a>  
								<a href="#" class="table_btn">2개월</a> 
								<a href="#" class="table_btn">3개월</a> 
							</span>
						</div> 
			 		</td>
				</tr>
				<tr>
					<td class="label">검색어</td>
					<td>
						<div>
							<select title="검색어 선택">
								<option value="">회원이름</option>
								<option value="">선택하세요</option>
								<option value="">선택하세요</option>
								<option value="">선택하세요</option>
							</select>
							<input type="text" class="three" title="상세검색 입력">
						</div>
					</td>
				</tr>
				<tr>
					<td class="label">구분</td>
					<td>
						<div>
							<p>
								<input type="radio" name="qq" id="out_all" checked="checked"> <label for="out_all">매출별</label>
								<input type="radio" name="qq" id="out"> <label for="out">상품별</label>
							</p>
						</div>
					</td>
				</tr>
			</tbody>
		</table>
		
	</div> <!-- // board_write -->
	
	
	
	<div class="btn_all">
		<!-- <div class="btn_left">
			<button type="button" class="btn btn-dark-gray btn-sm"><span>초기화</span></button>
		</div> -->
		<div class="btn_right">
			<button type="button" class="btn btn-dark-gray btn-sm"><span>검색</span></button>
		</div>
	</div>
	
	
	
	<div class="sort_area mt30">
		<div class="left">
			<span>매출별 총 건수 : <span class="font_b">280</span>건 (결제건수 : 246건, 취소/반품 : 34건)</span> | <span>총 매출합계 : <span class="font_b">70,125,230</span>원</span>
		</div>
	</div>
	
	<div class="board_list">
		
		<!-- 매출별 -->
		<table class="board_list_table">
			<thead>
				<tr>
					<th rowspan="2">N</th>
					<th rowspan="2" class="border_left">회원이름<br/>(아이디)</th>
					<th colspan="5" class="border_left">결제</th>
					<th colspan="5" class="border_left">취소</th>
					<th colspan="4" class="border_left">소계</th>
				</tr>
				<tr>
					<th class="border_left">건수</th>
					<th class="border_left">상품금액</th>
					<th class="border_left">${op:message('M00246')}</th>
					<th class="border_left">배송비</th>
					<th class="border_left">매출액</th>
					
					<th class="border_left">건수</th>
					<th class="border_left">상품금액</th>
					<th class="border_left">${op:message('M00246')}</th>
					<th class="border_left">배송비</th>
					<th class="border_left">취소액</th>
					
					<th class="border_left">상품금액</th>
					<th class="border_left">${op:message('M00246')}</th>
					<th class="border_left">배송비</th>
					<th class="border_left">매출액</th>
					
				</tr>
			</thead>
			<tbody>
				<tr>
					<td>1</td>
					<td class="border_left under_line"><a href="#">홍길동<br/>(Abc123)</a></td>
					<td class="border_left">5</td>
					<td class="border_left">1,852,000</td>
					<td class="border_left">-30,000</td>
					<td class="border_left">0</td>
					<td class="border_left">1,822,000</td>
					<td class="border_left">2</td>
					<td class="border_left">-95,000</td>
					<td class="border_left">0</td>
					<td class="border_left">0</td>
					<td class="border_left">-95,000</td>
					<td class="border_left">1,787,000</td>
					<td class="border_left">-30,000</td>
					<td class="border_left">0</td>
					<td class="border_left">1,757,000</td>
				</tr>
				<tr>
					<td>2</td>
					<td class="border_left under_line"><a href="#">홍길동<br/>(Abc123)</a></td>
					<td class="border_left">5</td>
					<td class="border_left">1,852,000</td>
					<td class="border_left">-30,000</td>
					<td class="border_left">0</td>
					<td class="border_left">1,822,000</td>
					<td class="border_left">2</td>
					<td class="border_left">-95,000</td>
					<td class="border_left">0</td>
					<td class="border_left">0</td>
					<td class="border_left">-95,000</td>
					<td class="border_left">1,787,000</td>
					<td class="border_left">-30,000</td>
					<td class="border_left">0</td>
					<td class="border_left">1,757,000</td>
				</tr>
				
				<tr class="subtotal1">
					<td colspan="2">합계</td>
					<td class="border_left">10</td>
					<td class="border_left">1,852,000</td>
					<td class="border_left">-30,000</td>
					<td class="border_left">0</td>
					<td class="border_left">1,822,000</td>
					<td class="border_left">2</td>
					<td class="border_left">-95,000</td>
					<td class="border_left">0</td>
					<td class="border_left">0</td>
					<td class="border_left">-95,000</td>
					<td class="border_left">1,787,000</td>
					<td class="border_left">-30,000</td>
					<td class="border_left">0</td>
					<td class="border_left">1,757,000</td>
				</tr>
			</tbody>
		</table>
		<!-- // 매출별 -->
		
		<!-- 상품별 -->
		<table class="board_list_table" style="display: none;">
			<thead>
				<tr>
					<th rowspan="2">N</th>
					<th rowspan="2" class="border_left">회원이름<br/>(아이디)</th>
					<th colspan="5" class="border_left">결제</th>
					<th colspan="5" class="border_left">취소</th>
					<th colspan="4" class="border_left">소계</th>
				</tr>
				<tr>
					<th class="border_left">건수</th>
					<th class="border_left">상품금액</th>
					<th class="border_left">${op:message('M00246')}</th>
					<th class="border_left">배송비</th>
					<th class="border_left">매출액</th>
					
					<th class="border_left">건수</th>
					<th class="border_left">상품금액</th>
					<th class="border_left">${op:message('M00246')}</th>
					<th class="border_left">배송비</th>
					<th class="border_left">취소액</th>
					
					<th class="border_left">상품금액</th>
					<th class="border_left">${op:message('M00246')}</th>
					<th class="border_left">배송비</th>
					<th class="border_left">매출액</th>
					
				</tr>
			</thead>
			<tbody>
				<tr>
					<td>1</td>
					<td class="border_left under_line"><a href="#">홍길동<br/>(Abc123)</a></td>
					<td class="border_left">5</td>
					<td class="border_left">1,852,000</td>
					<td class="border_left">-30,000</td>
					<td class="border_left">0</td>
					<td class="border_left">1,822,000</td>
					<td class="border_left">2</td>
					<td class="border_left">-95,000</td>
					<td class="border_left">0</td>
					<td class="border_left">0</td>
					<td class="border_left">-95,000</td>
					<td class="border_left">1,787,000</td>
					<td class="border_left">-30,000</td>
					<td class="border_left">0</td>
					<td class="border_left">1,757,000</td>
				</tr>
				<tr>
					<td>2</td>
					<td class="border_left under_line"><a href="#">홍길동<br/>(Abc123)</a></td>
					<td class="border_left">5</td>
					<td class="border_left">1,852,000</td>
					<td class="border_left">-30,000</td>
					<td class="border_left">0</td>
					<td class="border_left">1,822,000</td>
					<td class="border_left">2</td>
					<td class="border_left">-95,000</td>
					<td class="border_left">0</td>
					<td class="border_left">0</td>
					<td class="border_left">-95,000</td>
					<td class="border_left">1,787,000</td>
					<td class="border_left">-30,000</td>
					<td class="border_left">0</td>
					<td class="border_left">1,757,000</td>
				</tr>
				
				<tr class="subtotal">
					<td colspan="2">소계</td>
					<td class="border_left">10</td>
					<td class="border_left">1,852,000</td>
					<td class="border_left">-30,000</td>
					<td class="border_left">0</td>
					<td class="border_left">1,822,000</td>
					<td class="border_left">2</td>
					<td class="border_left">-95,000</td>
					<td class="border_left">0</td>
					<td class="border_left">0</td>
					<td class="border_left">-95,000</td>
					<td class="border_left">1,787,000</td>
					<td class="border_left">-30,000</td>
					<td class="border_left">0</td>
					<td class="border_left">1,757,000</td>
				</tr>
				
				<tr class="subtotal1">
					<td colspan="2">합계</td>
					<td class="border_left">10</td>
					<td class="border_left">1,852,000</td>
					<td class="border_left">-30,000</td>
					<td class="border_left">0</td>
					<td class="border_left">1,822,000</td>
					<td class="border_left">2</td>
					<td class="border_left">-95,000</td>
					<td class="border_left">0</td>
					<td class="border_left">0</td>
					<td class="border_left">-95,000</td>
					<td class="border_left">1,787,000</td>
					<td class="border_left">-30,000</td>
					<td class="border_left">0</td>
					<td class="border_left">1,757,000</td>
				</tr>
			</tbody>
		</table>
		<!-- // 상품별 -->
		
		<div class="sort_area">
			<div class="right">
				<a href="#" class="btn_write gray_small"><img src="/content/opmanager/images/icon/icon_excel.png" alt=""><span>엑셀 다운로드</span> </a>
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
		
		<div class="board_guide">
			<p class="tip">Tip</p>
			<p class="tip">- 회원이름(아이디)클릭하시면 상세내역을 조회하실 수 있습니다.</p>
				<p class="tip">- 조회기간은 3개월까지만 가능합니다.</p>
		</div>
		
	</div>
	
</div>