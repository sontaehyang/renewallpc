<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>

<h3><span>안전인형극 신청 관리</span></h3>
<div class="board_list">				 
	<div class="board_view">
	<form:form modelAttribute="marionette" method="post"> 
		<form:hidden path="marionetteId"/>
		<div class="info">
			<table class="detail_01">
				<colgroup>
					<col style="width:10%" />
					<col style="width:10%" />
					<col style="width:10%" />
					<col style="width:30%" />
					<col style="width:10%" />
					<col style="width:auto" />
				</colgroup>
				<tbody>
					<tr>
						<th class="label" rowspan="7">어린이집 기본정보</th>
						<th colspan="2" scope="col" class="label" ><label for="application01">어린이집명</label></th>
						<td><form:input path="nurseryName" cssClass="required" title="어린이집명" style="width:300px;"/></td>
						<th scope="row" class="label">시설유형</th>
						<td>
							<form:select path="nurseryType" cssClass="required choice" title="시설유형" style="width:300px;">
								<form:option value="">선택하세요</form:option>
								<form:option value="1">국공립</form:option>
								<form:option value="2">사회복지법인</form:option>
								<form:option value="3">법인단체등</form:option>
								<form:option value="4">직장</form:option>
								<form:option value="5">가정</form:option>
								<form:option value="6">부모협동</form:option>
								<form:option value="7">민간</form:option>
							</form:select>
						</td>
					</tr>
						<tr>
						<th rowspan="2" scope="row" class="label">주소</th>
						<th scope="row" class="label">시도</th>
						<td>
							<form:select path="sido" cssClass="required choice2" title="시도" style="width:300px;">
								<form:option value="">선택하세요</form:option>
								<form:option value="서울특별시">서울특별시</form:option>
								<form:option value="인천광역시">인천광역시</form:option>
								<form:option value="부산광역시">부산광역시</form:option>
								<form:option value="대구광역시">대구광역시</form:option>
								<form:option value="광주광역시">광주광역시</form:option>
								<form:option value="대전광역시">대전광역시</form:option>
								<form:option value="울산광역시">울산광역시</form:option>
								<form:option value="경기도">경기도</form:option>
								<form:option value="강원도">강원도</form:option>
								<form:option value="충청북도">충청북도</form:option>
								<form:option value="충청남도">충청남도</form:option>
								<form:option value="전라북도">전라북도</form:option>
								<form:option value="전라남도">전라남도</form:option>
								<form:option value="경상북도">경상북도</form:option>
								<form:option value="경상남도">경상남도</form:option>
								<form:option value="제주도">제주도</form:option>
							</form:select>
						</td>
						<th scope="row" class="label">시군구</th>
						<td>
							<form:select path="sigungu" cssClass="required choice" title="시군구" style="width:300px;">
								<form:option value="">선택하세요</form:option>
							</form:select>
						</td>
					</tr>
					<tr>
						<th scope="row"><label for="application05">상세주소</label></th>
						<td colspan="3"><form:input path="address" cssClass="required w468" title="상세주소" style="width:90%;" /></td>
					</tr>
					<tr>
						<th scope="row">신청자</th>
						<th scope="row">신청인</th>
						<td>
							<form:select path="subscriber" cssClass="required choice2" title="신청인" style="width:300px;">
								<form:option value="">선택하세요</form:option>
								<form:option value="1">원장</form:option>
								<form:option value="2">원감교사</form:option>
								<form:option value="3">주임</form:option>
								<form:option value="4">기타</form:option>
							</form:select>
						</td>
						<th scope="row"><label for="application06">성명</label></th>
						<td><form:input path="subscriberName" cssClass="required w138" title="성명" /></td>
					</tr>
					<tr>
						<th rowspan="3" scope="row">연락처</th>
						<th scope="row">유선전화번호</th>
						<td colspan="3">
							<form:select path="tel1" cssClass="required choice3" title="유선전화번호 첫번째자리" style="width:100px;">
								<form:option value="">선택하세요</form:option>
								<form:option value="02">02</form:option>
								<form:option value="032">032</form:option>
							</form:select> - 
							<form:input path="tel2" cssClass="required _number" maxlength="4" title="유선전화번호 가운데자리" /> -
							<form:input path="tel3" cssClass="required _number" maxlength="4" title="유선전화번호 마지막자리" />
						</td>
					</tr>
					<tr>
						<th scope="row">휴대전화번호</th>
						<td colspan="3">
							<form:select path="phone1" cssClass="required choice3" title="휴대전화번호 첫번째자리" style="width:100px;">
								<form:option value="">선택하세요</form:option>
								<form:option value="010">010</form:option>
								<form:option value="011">011</form:option>
							</form:select> - 
							<form:input path="phone2" cssClass="required _number" maxlength="4" title="휴대전화번호 가운데자리" /> - 
							<form:input path="phone3" cssClass="required _number" maxlength="4" title="휴대전화번호 마지막자리" />
						</td>
					</tr>
					<tr>
						<th scope="row">이메일</th>
						<td colspan="3">
							<form:input path="email1" cssClass="required" maxlength="50" title="이메일 아이디" /> @
							<form:input path="email2" cssClass="required" maxlength="50" title="이메일 도메인" />
							<select class="choice" name="email3" id="email3" style="width:200px;">
								<option value="">직접입력</option>
								<option value="naver.com" <c:if test="${marionette.email2=='naver.com'}">selected="selected"</c:if>>naver.com</option>
									<option value="nate.com" <c:if test="${marionette.email2=='nate.com'}">selected="selected"</c:if>>nate.com</option>
									<option value="hotmail.com" <c:if test="${marionette.email2=='hotmail.com'}">selected="selected"</c:if>>hotmail.com</option>
									<option value="lycos.co.kr" <c:if test="${marionette.email2=='lycos.co.kr'}">selected="selected"</c:if>>lycos.co.kr</option>
									<option value="msn.com" <c:if test="${marionette.email2=='msn.com'}">selected="selected"</c:if>>msn.com</option>
									<option value="hanmail.net" <c:if test="${marionette.email2=='hanmail.net'}">selected="selected"</c:if>>hanmail.net</option>
									<option value="yahoo.co.kr" <c:if test="${marionette.email2=='yahoo.co.kr'}">selected="selected"</c:if>>yahoo.co.kr</option>
									<option value="paran.com" <c:if test="${marionette.email2=='paran.com'}">selected="selected"</c:if>>paran.com</option>
							</select>
						</td>
					</tr>
				</tbody>
			</table>	
			
			<!-- 공동신청 여부 게시판 write 시작 -->
			<table  class="detail_01">							 
				<colgroup>
					<col style="width:100px" />
					<col style="width:100px" />
					<col style="width:150px" />
					<col style="width:auto" />
				</colgroup>
				<tbody>
					<tr>
						<th rowspan="5" scope="row">공동신청 여부</th>
						<th colspan="2" scope="row">관람형태</th>
						<td colspan="3">
						<c:if test="${modeTitle == '등록'}">
							<p class="text2">
								<span class="etc1"> 
									<form:radiobutton path="viewType" value="1" label="단독관람" cssClass="in_br0" title="단독관람" checked="true"/>
								</span>
						 		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						 		<form:radiobutton path="viewType" value="2" label="공동관람" cssClass="in_br0" title="공동관람"/>
							<br />
								공동관람 선택시 공공관람원명이 5개 이상일 경우 반드시 어린이집 안전공제회 담당자에게 유선으로 연락 부탁드립니다.
							</p>
						</c:if>
						<c:if test="${modeTitle == '수정'}">
							<p class="text2">
								<span class="etc1"> 
									<form:radiobutton path="viewType" value="1" label="단독관람" cssClass="in_br0" title="단독관람" disabled="true" />
								</span>
						 		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						 		<form:radiobutton path="viewType" value="2" label="공동관람" cssClass="in_br0" title="공동관람" disabled="true" />
							<br />
								공동관람 선택시 공공관람원명이 5개 이상일 경우 반드시 어린이집 안전공제회 담당자에게 유선으로 연락 부탁드립니다.
							</p>
						</c:if>
						</td>
					</tr>
					<!-- <tr>						
						<th colspan="2" scope="row">신청원명</th>
						<td colspan="3">
							<input type="text" id="application17" title="신청원명"  style="width:600px;" />  										 
						</td>
					</tr> -->
					<tr>
						<th rowspan="3" scope="row">영유아 현원</th>
						<th scope="row"><label for="application10">전체</label></th>
						<td colspan="3">
							<form:input path="allCount" cssClass="required _number" title="영유아현원 전체" />  명
						</td>
					</tr>
					<tr>
						<th scope="row"><label for="application11">만2세 이하</label></th>
						<td colspan="3">
							<form:input path="count1" cssClass="required _number" title="영유아현원 만2세 이하" />  명
						</td>
					</tr>
					<tr>
						<th scope="row"><label for="application12">만3세 이상</label></th>
						<td colspan="3">
							<form:input path="count2" cssClass="required _number" title="영유아현원 만3세 이상" />  명
						</td>
					</tr>
				</tbody>	
			</table>
			<!-- 공동신청 여부 게시판 write 끝-->
			
			<c:if test="${modeTitle == '등록'}">
				<!-- 공동관람일 경우 -->
				<div id="join_area">
					<div class="btns">
						<img id="join_add" src="/content/images/btn/btn_plus.gif" class="pointer" alt="공동관람 신청추가" title="공동관람 신청추가" />
						<img id="join_del" src="/content/images/btn/btn_minus.gif" class="pointer" alt="공동관람 신청삭제" title="공동관람 신청삭제" />
					</div>
					<table class="detail_01">
						<colgroup>
							<col style="width:200px" />
							<col style="width:150px" />
							<col style="width:auto" />
						</colgroup>
						<tbody>
							<tr>
								<th scope="row">공동관람원명</th>
								<td colspan="2"><form:input path="marionetteJoinList[0].viewerName" style="width:90%;" title="공동관람원명" /></td>
							</tr>
							<tr>
								<th rowspan="3" scope="row">영유아 현원</th>
								<th scope="row"><label for="application14">전체</label></th>
								<td colspan="3">
									<form:input path="marionetteJoinList[0].addAllCount" title="영유아현원 전체" /> 명
								</td>
							</tr>
							<tr>
								<th scope="row"><label for="application15">만2세 이하</label></th>
								<td colspan="3">
									<form:input path="marionetteJoinList[0].addCount1" title="영유아현원 만2세" /> 명
								</td>
							</tr>
							<tr>
								<th scope="row"><label for="application16">만3세 이상</label></th>
								<td colspan="3">
									<form:input path="marionetteJoinList[0].addCount2" title="영유아현원 만3세" /> 명
								</td>
							</tr>
						</tbody>
						<input type="hidden" name="item_cnt" id="item_cnt" value="1" />
					</table><!-- 공동관람일 경우 끝-->
				</div>
			</c:if>	
			
			<c:if test="${modeTitle == '수정'}">
				<form:hidden path="viewType"/>
				<!-- 공동관람일 경우 -->
				<div id="join_area">
					<div class="btns">
						<img id="join_add" src="/content/images/btn/btn_plus.gif" class="pointer" alt="공동관람 신청추가" title="공동관람 신청추가" />
						<img id="join_del" src="/content/images/btn/btn_minus.gif" class="pointer" alt="공동관람 신청삭제" title="공동관람 신청삭제" />
					</div>
					<c:forEach items="${marionette.marionetteJoinList}" var="rs" varStatus="i">
						<table class="detail_01">
							<colgroup>
								<col style="width:200px" />
								<col style="width:150px" />
								<col style="width:auto" />
							</colgroup>
							<tbody>
								<tr>
									<th scope="row">공동관람원명</th>
									<td colspan="2">
										<form:hidden path="marionetteJoinList[${i.index}].marionetteJoinId"/>
										<form:input path="marionetteJoinList[${i.index}].viewerName" style="width:90%;" title="공동관람원명" />
									</td>
								</tr>
								<tr>
									<th rowspan="3" scope="row">영유아 현원</th>
									<th scope="row"><label for="application14">전체</label></th>
									<td colspan="3">
										<form:input path="marionetteJoinList[${i.index}].addAllCount" title="영유아현원 전체" /> 명
									</td>
								</tr>
								<tr>
									<th scope="row"><label for="application15">만2세 이하</label></th>
									<td colspan="3">
										<form:input path="marionetteJoinList[${i.index}].addCount1" title="영유아현원 만2세" /> 명
									</td>
								</tr>
								<tr>
									<th scope="row"><label for="application16">만3세 이상</label></th>
									<td colspan="3">
										<form:input path="marionetteJoinList[${i.index}].addCount2" title="영유아현원 만3세" /> 명
									</td>
								</tr>
							</tbody>
						</table>
						<c:set var="item_cnt" value="${i.index}"/>
					</c:forEach>
					<input type="hidden" name="item_cnt" id="item_cnt" value="${item_cnt + 1}" />
				</div>
				<!-- 공동관람일 경우 끝-->
			</c:if>	
			
			
			
			<!-- 안전교육 인형극 신청 설문조사 게시판 write 시작 -->
			<table class="detail_01">
				<colgroup>
					<col style="width:75%" />
					<col style="width:*" />
				</colgroup>
				<tbody>
					<tr>
						<th class="tleft" colspan="2" scope="row">인형극은 어떠한 경로를 통해 알게 되었는지 해당하는 항목에 <span class="b_check">&nbsp;</span>체크해 주십시오. (복수응답 가능)</th>
					</tr>

					<tr>
						<td><label for="check1">어린이집안전공제회 홍보 (홈페이지, 페이스북 및 뉴스레터 등)</label></td>
						<td class="txt_c">				 
							<form:checkbox path="check1" value="1" cssClass="in_br0" title="체크해주세요." />
						</td>
					</tr>

					<tr>
						<td> <label for="check2">아이사랑보육포털</label></td>
						<td class="txt_c">
							<form:checkbox path="check2" value="1" cssClass="in_br0" title="체크해주세요." />
						</td>
					</tr>

					<tr>
						<td> <label for="check3">육아종합지원센터 홈페이지</label></td>
						<td class="txt_c">
							<form:checkbox path="check3" value="1" cssClass="in_br0" title="체크해주세요." />
						</td>
					</tr>

					<tr>
						<td><label for="check4">한국보육진흥원 홈페이지</label></td>
						<td class="txt_c">				 
							<form:checkbox path="check4" value="1" cssClass="in_br0" title="체크해주세요." />	 
						</td>
					</tr>

					<tr>
						<td colspan="2" >
							<span class="etc1">기타</span> &nbsp;&nbsp;
							<form:textarea path="etc" cols="80" rows="4" cssClass="txt_box01" />
						</td>
					</tr>
				</tbody>
			</table>
			<!-- 안전교육 인형극 신청 설문조사 게시판 write 끝 --> 

			<!-- 인형극 신청동기 작성 게시판 write 시작 -->
			<table class="detail_01">
				<colgroup>
					<col style="width:214px" />
					<col style="width:493px" />
				</colgroup>
				<tr>
					<th class="txt_c" scope="row"><label for="application13">기타</label></th>
					<td>
						 인형극을 신청하게 된 동기를 작성해 주십시오. (1000자 이하) 
						 <form:textarea path="motive" cols="70" rows="4" cssClass="txt_box02" />
					</td>
				</tr>

			</table>
			<!-- 인형극 신청동기 작성 게시판 write 끝 --> 
			
								
		</div><!--//info-->
		
		 
		
	 
		 
		<div class="not_count" style="text-align: right;"> </div>
		<p class="btns">
			<c:if test="${modeTitle == '등록'}">
				<button type="submit" class="btn orange">등록</button>
			</c:if>	
			<c:if test="${modeTitle == '수정'}">
				<button type="submit" class="btn white">수정</button>&nbsp;
				<a href="javascript:;" onClick="fn_delete();" class="btn white">삭제</a>		&nbsp;
			</c:if>	
			<a href="/opmanager/marionette/list" class="btn gray">목록</a> &nbsp;
		</p>
	</form:form>
	</div>
	
	<form id="deleteForm" method="post" action="/opmanager/marionette/delete">
		<input type="hidden" name="marionetteId" value="${marionette.marionetteId }" />
	</form>
	
	 
	
	<!-- <div class="board_guide">
		<p class="total">전체 : <em>125</em></p>
	</div> -->
 
</div><!--//board_list-->

<script type="text/javascript">
 $(function(){
	 if('${modeTitle}' == '수정'){
		 getSigungu('${marionette.sido}');
	 }
	// validator
	$('#marionette').validator(function() {
		
	});
	
	$('#email3').on("change", function(){
		$('#email2').val($(this).val());
	});
	
	 item_cnt = $("#item_cnt").val();
	 $("#join_add").click(function(){
		 if(item_cnt < 5){
		 	$("#join_area").append('<table class="detail_01 joinarea'+item_cnt+'">'+
									'<colgroup><col style="width:200px" /><col style="width:150px" /><col style="width:auto" /></colgroup>' +
								'<tbody><tr><th scope="row">공동관람원명</th><td colspan="2"><input type="text" name="marionetteJoinList['+item_cnt+'].viewerName" id="application19" title="공동관람원명" style="width:90%;" /></td></tr>'+
								'<tr><th rowspan="3" scope="row">영유아 현원</th><th scope="row"><label for="application14">전체</label></th><td colspan="3"><input type="text" name="marionetteJoinList['+item_cnt+'].addAllCount" /> 명'+
								'</td></tr><tr><th scope="row"><label for="application15">만2세 이하</label></th><td colspan="3"><input type="text" name="marionetteJoinList['+item_cnt+'].addCount1" /> 명</td></tr>'+
								'<tr><th scope="row"><label for="application16">만3세 이상</label></th><td colspan="3"><input type="text" name="marionetteJoinList['+item_cnt+'].addCount2" /> 명'+
								'</td></tr></tbody></table>');
		 	item_cnt++;
		 	setHeight();
		 }else{
			alert("공동관람 추가는 5개까지 가능합니다.");
		}
	});
	
	 $("#sido").change(function(){
		 if($("#sido").val() != ""){
			 getSigungu($("#sido").val());
		 }
	 });
	 
	 function getSigungu(sido){
		 var params = {
			'sido' : sido
		};
		
		 $.post('/marionette/sigunguSearch', params, function(resp) {
			if (resp.isSuccess) {
				$("#sigungu option").remove();
				$("#sigungu").append('<option value="">선택하세요</option>');
				for(var i=0; i < resp.data.length; i++){
					if(resp.data[i] == '${marionette.sigungu}'){
						$("#sigungu").append('<option value="'+resp.data[i]+'" selected="selected">'+resp.data[i]+'</option>');
					}else{
						$("#sigungu").append('<option value="'+resp.data[i]+'">'+resp.data[i]+'</option>');
					}
					
				}
			} else {
				alert(resp.errorMessage);
			}
		});
		 
	 }
	 
	$("#join_del").click(function(){
		if(item_cnt > $("#item_cnt").val()){
			item_cnt--;
			setHeight();
		}
		$(".joinarea"+item_cnt).remove();
	});
 });
 
 function fn_delete(){
	 if(confirm("인형극 신청을 삭제하시겠습니까?")){
		 $("#deleteForm").submit();
	 }
 }
 </script>
 
 