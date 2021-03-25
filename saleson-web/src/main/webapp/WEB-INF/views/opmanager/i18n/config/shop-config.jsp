<%@page contentType="text/html;charset=UTF-8"%>
<%@page pageEncoding="UTF-8"%>
<%@ page session="false" %>
<%@ taglib prefix="c" 	uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="op" 	uri="/WEB-INF/tlds/functions" %>
<%@ taglib prefix="fn" 	uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="page" tagdir="/WEB-INF/tags/page" %>
<%@ taglib prefix="daum"	tagdir="/WEB-INF/tags/daum" %>

<style>
.sub_title {
	display: inline-block;
	width: 60px;
}
</style>

		<div class="location">
			<a href="#"></a> &gt;  <a href="#"></a> &gt; <a href="#" class="on"></a>
		</div>

		<h3><span>${op:message('M00103')}</span></h3>
		<div class="board_write">
		<form:form modelAttribute="config" method="post" enctype="multipart/form-data">
		
			<table class="board_write_table">
				<caption>운영자 등록/수정</caption>
				<colgroup>
					<col style="width:150px;" />
					<col style="width:auto;" />
					<col style="width:150px;" />
					<col style="width:auto;" />
				</colgroup>
				<tbody>
					<tr>
					 	<td class="label">${op:message('M00091')} (${op:message('M00092')} )</td>
					 	<td colspan="3">
					 		<div>
					 			<form:input path="shopName" cssClass="form-half required" title="${op:message('M00091')} (${op:message('M00092')} )" maxlength="50" />
						 	</div>
					 	</td>	
					 </tr>
					<tr>
						<td class="label">${op:message('M00104')} (${op:message('M00105')} )</td>
						<td>
							<div>
								<form:input path="companyName" title="${op:message('M00104')} (${op:message('M00105')} )" cssClass="form-block required" maxlength="20" />
						    </div>
						</td>
						<td class="label">${op:message('M00106')} </td>
						<td>
							<div>
								<form:input path="companyNumber1" title="${op:message('M00106')}  ${op:message('M00107')} " cssClass="phone-number required _number" maxlength="3" /> - 
								<form:input path="companyNumber2" title="${op:message('M00106')}  ${op:message('M00108')} " cssClass="form-xs text-center required _number" maxlength="2" /> -
								<form:input path="companyNumber3" title="${op:message('M00106')}  ${op:message('M00109')} " cssClass="amount text-center required _number" maxlength="5" />
						    </div>
						</td>
					</tr>
					<tr>
						<td class="label">${op:message('M00110')} </td>
						<td>
							<div>
								<form:input path="categoryType" title="${op:message('M00110')} " cssClass="form-block required" maxlength="30" />
						    </div>
						</td>
						<td class="label">${op:message('M00111')} </td>
						<td>
							<div>
								<form:input path="businessType" title="${op:message('M00111')} " cssClass="form-block required" maxlength="30" />
						    </div>
						</td>
					</tr>
					<tr>
						<td class="label">${op:message('M00112')} </td>
						<td>
							<div>
								<form:input path="bossName" title="${op:message('M00112')} " cssClass="form-block required"  maxlength="20" />
						    </div>
						</td>
						<td class="label">${op:message('M00113')} </td>
						<td>
							<div>
								<form:input path="mailOrderNumber" title="${op:message('M00113')} " cssClass="w90" maxlength="20" />
						    </div>
						</td>
					</tr>
					<tr>
						<td class="label">${op:message('M00114')} </td>
						<td colspan="3">
							<%-- <div>
								${op:message('M00115')} 
								<form:input path="post1" title="${op:message('M00115')} ${op:message('M00107')} " maxlength="3" cssClass="one required" /> -
								<form:input path="post2" title="${op:message('M00115')} ${op:message('M00109')} " maxlength="4" cssClass="one required" />
								<a href="#javascript:;" onclick="findZipcode();" class="btn_write gray">${op:message('M01086')} <!-- 주소 자동 입력 --></a>
								<span>〒${op:message('M00117')} </span>	
								<p class="mt5">
									도도부 현 <form:select path="dodobuhyun" style="width:18%" class="required" title="${op:message('M00063')}">
	 									 <form:option value="" label="${op:message('M00431')}" /> <!-- 선택 --> 
	 									<form:options items="${dodobuhyunList}" />
	 								</form:select>&nbsp;
								</p>
								<p class="mt5">
									도시 <form:input path="address" title="${op:message('M00118')} " maxlength="70" cssClass="pt5 required" style="width:80%"/>
								</p>
								<p class="mt5">
									이후 주소 <form:input path="addressDetail" maxlength="50" title="${op:message('M00119')} " style="width:80%"/>
								</p>	
							</div> --%>  
							
							<div>
								<span class="sub_title">${op:message('M00115')} </span>
								<input type="hidden" name="newPost" value=""> 
								<input type="text" name="post" id="post" value="${config.post}" class="required" title="${op:message('M00115')}  ${op:message('M00107')}" maxlength="5" class="one" readonly="readonly">
								<a href="#javascript:;" onclick="openDaumPostcode()" class="table_btn">${op:message('M00117')}</a>  
								<input type="hidden" name="dodobuhyun" title="지역" maxlength="8" class="required" readonly="true" />
 								<!--  p class="mt5">
	 								<span class="sub_title">지역</span> <form:input path="dodobuhyun" title="지역" maxlength="8" class="required" readonly="true" />
								</p--> 
								<p class="mt5">
									<span class="sub_title">주소</span> <form:input path="address" title="${op:message('M00118')} " maxlength="70" cssClass="pt5 required" style="width:80%" readonly="true" />
								</p>
								<p class="mt5">
									<span class="sub_title">상세주소</span> <form:input path="addressDetail" maxlength="50" title="${op:message('M00119')} " style="width:80%" />
								</p>	
						    </div>
						</td>							
					</tr>
					<tr> 
						<td class="label">${op:message('M00120')} </td>
						<td>
							<div>
								<form:input path="telNumber1" maxlength="4" title="${op:message('M00120')}  ${op:message('M00107')} " cssClass="form-phone _number" /> -
								<form:input path="telNumber2" maxlength="4" title="${op:message('M00120')}  ${op:message('M00108')} " cssClass="form-phone _number" /> -
								<form:input path="telNumber3" maxlength="4" title="${op:message('M00120')}  ${op:message('M00109')} " cssClass="form-phone _number" />
						    </div> 
						</td>
						<td class="label">${op:message('M00121')} </td>
						<td>
							<div>
								<form:input path="faxNumber1" maxlength="4" title="${op:message('M00121')}  ${op:message('M00107')} " cssClass="form-phone _number" /> -
								<form:input path="faxNumber2" maxlength="4" title="${op:message('M00121')}  ${op:message('M00108')} " cssClass="form-phone _number" /> -
								<form:input path="faxNumber3" maxlength="4" title="${op:message('M00121')}  ${op:message('M00109')} " cssClass="form-phone _number" />
						    </div>
						</td>
					</tr>
					<tr>
						<td class="label">${op:message('M00122')} </td>
						<td>
							<div>
								<form:input path="counselTelNumber1" maxlength="4" title="${op:message('M00122')}  ${op:message('M00107')} " cssClass="form-phone _number" /> -
								<form:input path="counselTelNumber2" maxlength="4" title="${op:message('M00122')}  ${op:message('M00108')} " cssClass="form-phone _number" /> -
								<form:input path="counselTelNumber3" maxlength="4" title="${op:message('M00122')}  ${op:message('M00109')} " cssClass="form-phone _number" />
						    </div>
						</td>
						<td class="label">${op:message('M00123')} </td>
						<td>
							<div>
								<form:input path="adminTelNumber1" maxlength="4" title="${op:message('M00123')}  ${op:message('M00107')} " cssClass="form-phone _number" /> -
								<form:input path="adminTelNumber2" maxlength="4" title="${op:message('M00123')}  ${op:message('M00108')} " cssClass="form-phone _number" /> -
								<form:input path="adminTelNumber3" maxlength="4" title="${op:message('M00123')}  ${op:message('M00109')} " cssClass="form-phone _number" />
						    </div>
						</td>
					</tr>
					<tr>
						<td class="label">${op:message('M00124')} </td>
						<td>
							<div>
								<form:input path="adminName" title="${op:message('M00124')} " maxlength="20" cssClass="w90"/>
						    </div>
						</td>
						<td class="label">${op:message('M00124')}  ${op:message('M00125')} </td>
						<td>
							<div>
								<form:input path="email" title="${op:message('M00124')}  ${op:message('M00125')}" maxlength="50" cssClass="w90"/>
						    </div>
						</td>
					</tr>	
					<tr>
					 	<td class="label">관리자 수신 이메일</td>
					 	<td colspan="3">
					 		<div>
					 			<form:input path="adminEmail" title="관리자 이메일" maxlength="50" cssClass="form-half  optional _email" />
						 	</div>
					 	</td>	
					 </tr>
					 <tr>
					 	<td class="label">${op:message('M00095')} </td>
					 	<td colspan="3">
					 		<div>
					 			<form:radiobutton path="sourceFlag" value="Y" label="${op:message('M00096')} " title="${op:message('M00095')} " cssClass="optional"/>
					 			<form:radiobutton path="sourceFlag" value="N" label="${op:message('M00097')} "/>
								<span class="text-info text-sm">(* ${op:message('M00098')} )</span>
							</div>
					 	</td>
					 </tr>
				</tbody>					 
			</table>	
			
			<h3 class="mt30"><span>불량단어필터링</span></h3>
			
			<table class="board_write_table" summary="사이트 기본설정">
				<caption>사이트 기본설정</caption>
				<colgroup>
					<col style="width:210px;" />
				</colgroup>
				<tbody>
				 	
					 <tr>
					 	<td class="label">불량단어필터링</td>
					 	<td>
					 		<div>
					 			<p class="text-info text-sm">
					 				* 금지어 (,)콤마로 구분하여 저장.
					 			</p>
					 			<form:textarea path="banWord" rows="3"  cssClass="form-block optional" title="${op:message('M00094')} " maxlength="255" />
							</div>
					 	</td>	
					 </tr>
					
				</tbody>					 
			</table>
			
			<h3 class="mt30"><span>썸네일 설정</span></h3>
			<div class="board_write">
				<table class="board_write_table" summary="상품사이즈등록">
					<caption>사이트 기본설정</caption>
					<colgroup>
						<col style="width:210px;" />
					</colgroup>
					<tbody>
						 <tr>
						 	<td class="label">썸네일 설정</td>
						 	<td>
						 		<div>
						 			<form:radiobutton path="thumbnailType" value="1" label="사이즈별 썸네일 저장" title="썸네일 저장 설정" cssClass="optional"/>
						 			<form:radiobutton path="thumbnailType" value="2" label="호출시 썸네일 리사이즈" title="썸네일 저장 설정" cssClass="optional"/>
								</div>
						 	</td>	
						 </tr>
					</tbody>
				</table>
				<div class="option-wrapper" style="padding-top: 20px;">
					<div class="item-option-table">
						<table class="inner-table tbl-option active">
							<colgroup>
								<col style="" />
								<col style="" />
							</colgroup>
							<thead>
								<tr>
									<th>사이즈명</th>
									<th>사이즈</th>
									<th>삭제</th>
								</tr>
							</thead>
							<tbody id="item-options">
								<c:if test="${jsonSize == null }">
								<tr>
									<td><form:input path="sizeName" title="사이즈명" /></td>
									<td><form:input path="size" title="사이즈" class="_number"/></td>
									<td><a href="" class="btn btn-dark-gray btn-sm delete-option">삭제</a></td>
								</tr>
								</c:if>
							</tbody>
						</table>
						<div class="item-options-info">
							<button type="button" class="btn btn-gradient btn-sm add-option"><span>+ 사이즈추가</span></button>
						</div>
					</div>
				</div>
			</div> <!-- // board_write -->


			<h3 class="mt30"><span>SEO 설정</span></h3>

			<div class="board_write">
				<table class="board_write_table" summary="사이트 기본설정">
					<caption>사이트 기본설정</caption>
					<colgroup>
						<col style="width:210px;" />
						<col style="*" />
					</colgroup>
					<tbody>
						<tr>
							<td class="label">${op:message('M00090')} </td>
							<td>
								<div>
									<form:input path="seoTitle" cssClass="form-block required" title="${op:message('M00090')} " maxlength="255" />
								</div>
							</td>
						 </tr>
						<tr>
							<td class="label">${op:message('M00093')} </td>
							<td>
								<div>
									<form:input path="seoKeywords" cssClass="form-block optional" title="${op:message('M00093')} " maxlength="255" />
								</div>
							</td>
						</tr>
						<tr>
							<td class="label">${op:message('M00094')} </td>
							<td>
								<div>
									<form:textarea path="seoDescription" rows="3"  cssClass="form-block optional" title="${op:message('M00094')} " maxlength="255" />
								</div>
							</td>
						</tr>
						<tr>
							<td class="label">${op:message('M00999')}</td> <!-- H1태그 -->
							<td>
								<div>
									<form:input path="seoHeaderContents1" cssClass="form-block optional" title="H1" maxlength="255" />
								</div>
							</td>
						</tr>
						 <!-- tr>
							<td class="label">${op:message('M01000')}</td>
							<td>
								<div>
									<form:input path="seoThemawordTitle" cssClass="form-block optional" title="테마워드용 타이틀" maxlength="255" />
								</div>
							</td>
						 </tr>
						 <tr>
							<td class="label">${op:message('M01001')}</td>
							<td>
								<div>
									<form:textarea path="seoThemawordDescription" rows="3"  cssClass="form-block optional" title="테마 워드용 기술서" maxlength="255" />
								</div>
							</td>
						 </tr>
						  <tr>
							<td class="label">${op:message('M01074')}</td>
							<td>
								<div>
									<form:input path="loginText"  cssClass="form-block optional" title="테마 워드용 기술서" maxlength="255" />
								</div>
							</td>
						 </tr-->
					</tbody>
				</table>
			</div>
			
			<!-- 버튼시작 -->
				<div class="tex_c mt20">
				<button type="submit" class="btn btn-active"><span>${op:message('M00101')}</span></button> <!-- 저장 -->	
			</div>			 
			<!-- 버튼 끝-->
		</form:form>					 							
		</div> <!-- // board_write -->
		
		 
		
		<!-- div class="board_guide ml10">
			<p class="tip">Tip</p>
			<p class="tip">- ${op:message('M00126')} </p>
			<p class="tip">- ${op:message('M00127')} </p>
		</div -->
		
		
<script src="http://dmaps.daum.net/map_js_init/postcode.js"></script>

<!-- 다음 주소검색 -->
<daum:address />

<script type="text/javascript">

$(function() {

	//썸네일 설정 추가[2017-06-09]minae.yun
	var array = ${jsonSize};
	var html = '';
	
	//json으로 받아온 데이터로 tr append
	for (var i=0; i<array.list.length;i++) {
		var readonlyFlag = '';
		
		//고정값은 사이즈명 수정안됨.
		if (array.list[i].sizeName == "XS" || array.list[i].sizeName == "S" || array.list[i].sizeName == "M" || array.list[i].sizeName == "L" ){
			readonlyFlag = 'readonly'
		}
		html += '<tr>';
		html += '    <td><input name="sizeName" id="sizeName" title="사이즈명" value="'+array.list[i].sizeName+'" '+readonlyFlag+' /></td>';
		html += '    <td><input name="size" title="사이즈" class="_number" value="'+array.list[i].size+'"/></td>';
		html += '    <td><a href="" class="btn btn-dark-gray btn-sm delete-option">삭제</a></td>';
		html += '</tr>';
		
	}
	$('#item-options').append(html);
	
	//썸네일 사이즈 추가 버튼 클릭 이벤트
	$('.add-option').on('click', function() {
		var newHtml = '';
		newHtml += '<tr>';
		newHtml += '    <td><input name="sizeName" title="사이즈명"/></td>';
		newHtml += '    <td><input name="size" title="사이즈" class="_number" /></td>';
		newHtml += '    <td><a href="" class="btn btn-dark-gray btn-sm delete-option">삭제</a></td>';
		newHtml += '</tr>';
		     
		$('#item-options').append(newHtml);
	});
	
	// validator
	$('#config').validator(function() {	});
	
	//썸네일 사이즈 삭제
	deleteOption();
	
});

function findZipcode(){

	 var params = {
		'post1' : $("#post1").val(),
		'post2' : $("#post2").val()
	 };
	
	 if (params.post1 == '' && params.post2 == '') {
		 alert("우편 번호를 입력 후 자동 주소 입력 버튼을 누르십시오.");
		 return;
	 }
	 
	$.post('/zipcode/find-address', params, function(resp) {
		if (resp.zipcode) {
			$("#address").val(resp.zipcode.c5);
			$("#addressDetail").val(resp.zipcode.c6);
		} else {
			alert("${op:message('M00128')}");
			$("#address").val("");
			$("#addressDetail").val("");
		}
	});
}

function openDaumPostcode() {
	
	var tagNames = {
		'newZipcode'			: 'post',
		/* 'zipcode' 				: 'post', */
		'zipcode1' 				: 'post1',
		'zipcode2' 				: 'post2',
	}
	
	openDaumAddress(tagNames);

}

function deleteOption() {
	// 썸네일 사이즈 삭제 버튼 클릭 이벤트
	$('#item-options').on('click', '.delete-option', function(e) {
		e.preventDefault();
		$(this).closest('tr').remove();
	});
}


</script>		