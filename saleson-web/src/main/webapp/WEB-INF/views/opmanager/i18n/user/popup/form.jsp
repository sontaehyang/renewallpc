<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions" %>
<%@ taglib prefix="daum"	tagdir="/WEB-INF/tags/daum" %>

	<h2><span>${op:message('M00829')} <!-- 회원정보 수정 --></span></h2>
	<form:form modelAttribute="user" method="post" enctype="multipart/form-data">
		<table cellpadding="0" cellspacing="0" summary="" class="board_list_table">
		<input type="hidden" id="currentEmail" value="${user.email}" />
			<caption>${op:message('M00246')} 지급/차감</caption>
			<colgroup>
				<col style="width:120px;" />
				<col style="width:*" />
			</colgroup>
			<tbody>
				<%-- <tr>
					<th scope="row">ID</th> <!-- 이메일 -->
					<td class="tleft">
						<div>${user.loginId}</div>
					</td>
				</tr> --%>
				<tr>
					<th scope="row">아이디(${op:message('M00125')})</th> <!-- 이메일 -->
					<td class="tleft">
						<div>
							<%-- <input type="text" name="email" class="half required _email" title="${op:message('M00125')}" value="${user.email}" maxlength="100"/> --%>
							${user.loginId}
							<input type="hidden" name="loginId" value="${user.loginId}"/>
							<input type="hidden" name="email" value="${user.email}"/>
						</div>
					</td>
				</tr>
				
				<tr>
					<th scope="row">회원 그룹</th> <!-- 이메일 -->
					<td class="tleft">
						<div>
							<select id="groupCode" name="groupCode" title="회원 그룹">
								<option value="default">그룹 미지정</option>
								<c:forEach items="${groupList}" var="group">
									<option value="${group.groupCode}" ${op:selected(user.userDetail.groupCode, group.groupCode)}>${group.groupName}</option>
								</c:forEach>
							</select>
						</div>
					</td>
				</tr>
				
				<tr>
					<th scope="row">회원 Level</th> <!-- 이메일 -->
					<td class="tleft">
						<div>
							<select id="levelId" name="levelId" title="회원 Level">
								<option value="0">Level 미지정</option>
								<c:forEach items="${userLevelGroup}" var="levelGroup">
									<c:set var="groupLabel">${levelGroup.key}</c:set>
									<c:forEach items="${groupList}" var="group">
										<c:if test="${groupLabel == group.groupCode}">
											<c:set var="groupLabel">${group.groupName}</c:set>
										</c:if>
									</c:forEach>
									
									<optgroup label="${groupLabel}">
										<c:forEach items="${levelGroup.value}" var="userLevel">
											<option value="${userLevel.levelId}" data-group-code="${userLevel.groupCode}" ${user.userDetail.levelId == userLevel.levelId ? "selected" : ""}>${userLevel.levelName}</option>
										</c:forEach>
									</optgroup> 
								</c:forEach>
							</select>
						</div>
					</td>
				</tr>
				
				<%-- <tr>
					<th scope="row">${op:message('M00175')}</th> <!-- 닉네임 -->
					<td class="tleft">
						<div>
							<input name="nickname" type="text" class="half" title="${op:message('M00175')}" value="${user.userDetail.nickname}" maxlength="20" />
						</div>
					</td>
				</tr> --%>
				<%-- <tr>
					<th scope="row">${op:message('M00005')}(${op:message('M00312')})</th> <!-- 이름 --> <!-- 한자 -->
					<td class="tleft">
						<div>
							<input name="userName" type="text" class="half required" title="${op:message('M00005')}(${op:message('M00312')})" value="${user.userName}" maxlength="50" />
					 	</div>
					</td>
				</tr> --%>
				<%-- <tr>
					<th scope="row">${op:message('M00005')}(${op:message('M00834')})</th> <!-- 이름 --> <!-- 카타카나 -->
					<td class="tleft">
						<div>
							<input name="userNameKatakana" type="text" class="half required" title="이름(가나)" value="${user.userDetail.userNameKatakana}" maxlength="50" />
					 	</div>
					</td>
				</tr> --%>
				
				<%-- <tr>
					<th scope="row">${op:message('M00105')}/${op:message('M00833')}<br/>(${op:message('M00834')})</th> <!-- 카타카나 -->
					<td class="tleft">
						<div>
							<input name="companyNameKatakana" type="text" class="half required" title="${op:message('M00105')}/${op:message('M00833')}" value="${user.userDetail.companyNameKatakana}" maxlength="50" />
					 	</div>
					</td>
				</tr> 
				<tr>
					<th scope="row">${op:message('M00302')}</th> <!-- 직종 -->
					<td class="tleft">
						<div>
							<ul class="join_check">
								<c:forEach items="${positionTypeCode}" var="positionType" varStatus="i">
									<li>
										<label for="positionType_${i.index}" class="checkbox-inline"><input type="checkbox" name="positionType" id="positionType_${i.index}" class="required" title="${op:message('M00181')}" ${op:checkedArray(positionCodeList,positionType.key.id) } value="${positionType.key.id}" />${positionType.label}</lable>
									</li>
									<c:if test="${i.last}">
										</br>
										<li>
											<div style="float:left;text-align:right;padding-top:3px;">기타 :&nbsp;</div>
											<div style="float:left;"><input type="text" name="positionEtc" id="positionEtc" value="${user.userDetail.positionEtc}" maxlength="100" /></div>
										</li>
									</c:if>
				 				</c:forEach>
			 				</ul>
						</div>
					</td>
				</tr>--%>
				<tr>
					<th scope="row" rowspan="3">${op:message('M00118')} <!-- 주소 --></th>
					<td class="tleft">
						<div>								 
							${op:message('M00115')} <!-- 우편번호 -->
							<input type="hidden" name="post" value="${user.userDetail.post}"> 
							<input type="text" name="newPost" id="newPost" value="${user.userDetail.newPost}" class="required" title="${op:message('M00115')}  ${op:message('M00107')}" maxlength="5" class="one" readonly="readonly">
							<a href="javascript:;" onclick="openDaumPostcode()" class="btn btn-dark-gray btn-sm"><span class="glyphicon glyphicon-search"></span> ${op:message('M00117')}</a>
					 	</div>
					</td>
				</tr>
				<%-- <tr>				 
					<td class="tleft">
						<div>	
							지역 <input type="text" name="dodobuhyun" id="dodobuhyun" value="${user.userDetail.dodobuhyun}" title="지역" maxlength="8" class="required">
					 	</div>
					</td>
				</tr> --%>
				<tr>				 
					<td class="tleft">
						<div>	
							주소 <input type="text" name="address" id="address" value="${user.userDetail.address}" class="seven required" title="주소" maxlength="100" readonly="readonly" >							 
					 	</div>
					</td>
				</tr>
				<tr>				 
					<td class="tleft">
						<div>	
							상세주소 <input type="text" name="addressDetail" id="addressDetail" value="${user.userDetail.addressDetail}" class="seven required" title="상세주소" maxlength="100" />
					 	</div>
					</td>
				</tr>
				<tr>
					<th>${op:message('M00016')} <!-- 전화번호 --></th>
					<td class="tleft">
						<div>
							<%-- <input type="text" name="telNumber1" title="${op:message('M00016')}" value="${user.userDetail.telNumber1}" class="one required _number " maxlength="4" /> --%> 
							<select title="전화번호 앞자리" id="telNumber1" name="telNumber1">
								<option value="" label="-선택-">-선택-</option>
								<c:forEach items="${op:getCodeInfoList('TEL')}" var="codes">
									<option value="${codes.detail}"  ${op:selected(user.userDetail.telNumber1, codes.detail)}> ${codes.label }</option>
								</c:forEach>
							</select>
							- <input type="text" name="telNumber2" title="${op:message('M00016')}" value="${user.userDetail.telNumber2}" class="one _number" maxlength="4" />
							- <input type="text" name="telNumber3" title="${op:message('M00016')}" value="${user.userDetail.telNumber3}" class="one _number" maxlength="4" />
					 	</div>
					</td>
				</tr>
				<%-- <tr>
					<th>${op:message('M00184')}</th> <!-- FAX번호 -->
					<td class="tleft">
						<div>
							<input type="text" name="faxNumber1" title="${op:message('M00184')}" value="${user.userDetail.faxNumber1}" class="one _number" maxlength="4" />
							 - <input type="text" name="faxNumber2" title="${op:message('M00184')}" value="${user.userDetail.faxNumber2}" class="one _number" maxlength="4" />
							 - <input type="text" name="faxNumber3" title="${op:message('M00184')}" value="${user.userDetail.faxNumber3}" class="one _number" maxlength="4" />
					 	</div>
					</td>
				</tr> --%>
				<tr>
					<th>${op:message('M00155')}</th> <!-- 휴대폰번호 -->
					<td class="tleft">
						<div>
							<select class="required" title="전화번호 앞자리" id="phoneNumber1" name="phoneNumber1">
								<option value="" label="-선택-">-선택-</option>
								<c:forEach items="${op:getCodeInfoList('PHONE')}" var="codes">
									<option value="${codes.detail}"  ${op:selected(user.userDetail.phoneNumber1, codes.detail)}> ${codes.label }</option>
								</c:forEach>
							</select>
							<%-- <input type="text" name="phoneNumber1" title="${op:message('M00155')}" value="${user.userDetail.phoneNumber1}" class="one _number" maxlength="4" /> --%>
							 - <input type="text" name="phoneNumber2" title="${op:message('M00155')}" value="${user.userDetail.phoneNumber2}" class="one _number" maxlength="4" />
							 - <input type="text" name="phoneNumber3" title="${op:message('M00155')}" value="${user.userDetail.phoneNumber3}" class="one _number" maxlength="4" />
					 	</div>
					</td>
				</tr>
				<%-- <tr>
					<th scope="row">${op:message('M00183')}</th> <!-- 영업형태 -->
					<td class="tleft">
						<div>
							<ul class="join_check">
								<c:forEach items="${businessCodes}" var="businessCode" varStatus="i">
									<li>
										<lable for="businessType_${i.index}" class="checkbox-inline"><input type="checkbox" name="businessType" id="businessType_${i.index}" value="${businessCode.businessCodeId}" ${op:checkedArray(businessCodeList,businessCode.businessCodeId) }  />${businessCode.id}</lable>
									</li> 
									<c:if test="${i.last}">
										</br>
										<li> 
											<div style="float:left;text-align:right;padding-top:3px;">기타 :&nbsp;</div>
											<div style="float:left;width:90%"><input type="text" name="businessTypeEtc" id="businessTypeEtc" class="full" title="기타" value="" maxlength="100" /></div>
										</li>
									</c:if>
				 				</c:forEach>
							</ul>
						</div>
					</td>
				</tr> --%>
				<tr>
					<th scope="row">SMS 수신동의</th>
					<td class="tleft">
						<div>
 							<input type="radio" name="receiveSms" id="receiveSms1" value="0" title="${op:message('M00250')}" ${op:checked(user.userDetail.receiveSms,'0')} class="required" ><label for="receiveSms1">${op:message('M00233')}</label> 
							<input type="radio" name="receiveSms" id="receiveSms2" value="1" title="${op:message('M00250')}" ${op:checked(user.userDetail.receiveSms,'1')} class="required" ><label for="receiveSms2">${op:message('M00234')}</label>
							<p class="mt5">
								쇼핑몰에서 제공하는 다양한 정보 sms를 받아보실 수 있습니다.<br/>
								(거래정보-결제/교환/환불 등과 관련된 내용은 고객님의 거래안전을 위하여 수신동의 여부와 관계없이 발송됩니다.)
							</p>
 						</div>
					</td>
				</tr>
				<tr>
					<th scope="row">Email 수신동의</th>
					<td class="tleft">
						<div>
							<input type="radio" name="receiveEmail" id="receiveEmail1" value="0" title="${op:message('M00250')}" ${op:checked(user.userDetail.receiveEmail,'0')} class="required" /><label for="receiveEmail1">${op:message('M00233')}</label> 
							<input type="radio" name="receiveEmail" id="receiveEmail2" value="1" title="${op:message('M00250')}" ${op:checked(user.userDetail.receiveEmail,'1')} class="required" /><label for="receiveEmail2">${op:message('M00234')}</label>
							<p class="mt5">
								쇼핑몰에서 제공하는 다양한 정보 E-mail를 받아보실 수 있습니다.<br/>
								(거래정보-결제/교환/환불 등과 관련된 내용은 고객님의 거래안전을 위하여 수신동의 여부와 관계없이 발송됩니다.)
							</p>
						</div>
					</td>
				</tr>
				<tr>
					<th scope="row">PUSH 수신동의</th>
					<td class="tleft">
						<div>
							<input type="radio" name="receivePush" id="receivePush1" value="0" title="${op:message('M00250')}" ${op:checked(user.userDetail.receivePush,'0')} class="required" ><label for="receivePush1">${op:message('M00233')}</label>
							<input type="radio" name="receivePush" id="receivePush2" value="1" title="${op:message('M00250')}" ${op:checked(user.userDetail.receivePush,'1')} class="required" ><label for="receivePush2">${op:message('M00234')}</label>
							<p class="mt5">
								쇼핑몰에서 제공하는 다양한 정보 Push를 받아보실 수 있습니다.<br/>
								(거래정보-결제/교환/환불 등과 관련된 내용은 고객님의 거래안전을 위하여 수신동의 여부와 관계없이 발송됩니다.)
							</p>
						</div>
					</td>
				</tr>
				<tr>
					<th scope="row">${op:message('M00008')}</th> <!-- 성별 -->
					<td class="tleft">
						<div>
							<input type="radio" name="gender" id="gender1" value="M" title="${op:message('M00008')}" class="${required}" ${op:checked(user.userDetail.gender,'M')} /><label for="gender1">${op:message('M00332')}</label> 
							<input type="radio" name="gender" id="gender2" value="F" title="${op:message('M00008')}" class="${required}" ${op:checked(user.userDetail.gender,'F')} /><label for="gender2">${op:message('M00331')}</label>
						</div>
					</td>
				</tr>
				<%-- 
				<tr>
					<th scope="row">${op:message('M00867')}</th> <!-- 연령 -->
					<td class="tleft">
						<div>
							<input type="radio" name="age" id="age1" value="10" title="${op:message('M00007')}"  class="${required}" ${op:checked(user.userDetail.age,'10')} /><label for="age1">${op:message('M00333')}</label> 
							<input type="radio" name="age" id="age2" value="20" title="${op:message('M00007')}"  class="${required}" ${op:checked(user.userDetail.age,'20')} /><label for="age2">${op:message('M00334')}</label> 
							<input type="radio" name="age" id="age3" value="30" title="${op:message('M00007')}"  class="${required}" ${op:checked(user.userDetail.age,'30')} /><label for="age3">${op:message('M00335')}</label> 
							<input type="radio" name="age" id="age4" value="40" title="${op:message('M00007')}"  class="${required}" ${op:checked(user.userDetail.age,'40')} /><label for="age4">${op:message('M00336')}</label> 
							<input type="radio" name="age" id="age5" value="50" title="${op:message('M00007')}"  class="${required}" ${op:checked(user.userDetail.age,'50')} /><label for="age5">${op:message('M00337')}</label> 
							<input type="radio" name="age" id="age6" value="60" title="${op:message('M00007')}"  class="${required}" ${op:checked(user.userDetail.age,'60')} /><label for="age6">${op:message('M00338')}</label> 
							<input type="radio" name="age" id="age7" value="70" title="${op:message('M00007')}"  class="${required}" ${op:checked(user.userDetail.age,'70')} /><label for="age7">${op:message('M00339')}</label>	 
						</div>
					</td>
				</tr> --%>
				<tr>
 					<th scope="row">생년월일</th>
 					<td class="tleft">
 						<div>
 							<p class="mt5">
 								<input type="radio" name="birthdayType" id="birthdayType1" value="1" title="생년월일" ${op:checked(user.userDetail.birthdayType,'1')}><label for="birthdayType1">양력</label> 
								<input type="radio" name="birthdayType" id="birthdayType2" value="2" title="생년월일" ${op:checked(user.userDetail.birthdayType,'2')}><label for="birthdayType2">음력</label>
								&nbsp;
								<select name="birthdayYear" class="">
									<option value="">선택</option>
									<c:forEach begin="0" end="100" step="1" var="index">
										<option value="${years - index}" label="${years - index}" ${op:selected(user.userDetail.birthdayYear, years - index)}>${years - index}</option>
									</c:forEach>
								</select>년
								<select name="birthdayMonth" class="">
									<option value="">선택</option>
									<c:forEach begin="1" end="12" step="1" var="index">
										<option value="${index}" label="${index}" ${op:selected(user.userDetail.birthdayMonth, index)}>${index}</option>
									</c:forEach>
								</select>월
								<select name="birthdayDay" class="">
									<option value="">선택</option>
									<c:forEach begin="1" end="31" step="1" var="index">
										<option value="${index}" label="${index}" ${op:selected(user.userDetail.birthdayDay, index)}>${index}</option>
									</c:forEach>
								</select>일
							</p>
 						</div>
 					</td>
 				</tr>
 				<%-- <tr>
					<th scope="row">회원구분</th>
					<td class="tleft">
						<div>
							<input type="radio" name="businessFlag" id="businessFlag1" value="N" title="${op:message('M00250')}" ${op:checked(user.userDetail.businessFlag,'N')} class="required" /><label for="businessFlag1">일반</label> 
							<input type="radio" name="businessFlag" id="businessFlag2" value="Y" title="${op:message('M00250')}" ${op:checked(user.userDetail.businessFlag,'Y')} class="required" /><label for="businessFlag2">사업자</label>
						</div>
					</td>
				</tr>
				<tr>
					<th scope="row">${op:message('M00105')}</th> <!-- 회사명 -->
					<td class="tleft">
						<div>
							<input name="companyName" type="text" class="half" title="${op:message('M00105')}" value="${user.userDetail.companyName}" maxlength="50" />
					 	</div>
					</td>
				</tr>
				<tr>
					<th scope="row">사업자등록번호</th>
					<td class="tleft">
						<div>
							<input type="text" name="businessNumber1" title="사업자번호" class="_number" maxlength="3" value="${user.userDetail.businessNumber1}" /> - 
 							<input type="text" name="businessNumber2" title="사업자번호" class="_number" maxlength="2" value="${user.userDetail.businessNumber2}" /> -
 							<input type="text" name="businessNumber3" title="사업자번호" class="_number" maxlength="5" value="${user.userDetail.businessNumber3}" />
							<p class="mt5">
								(사업자에게 발행하는 세금계산서 정보이며 개인은 입력하지 않습니다.)
							</p>
					 	</div>
					</td>
				</tr>
				<tr>
 					<th scope="row">사업자업태</th>
 					<td class="tleft">
 						<div>
							<input type="text" name="businessStatus" title="사업자업태       _" class="full" maxlength="50"  value="${user.userDetail.businessStatus}"/>
					 	</div>
					</td>
 				</tr>
 				<tr>
 					<th scope="row">사업자업태 종목</th>
 					<td class="tleft">
 						<div>
							<input type="text" name="businessStatusType" title="사업자업태 종목" class="full" maxlength="50"  value="${user.userDetail.businessStatusType}"/>
					 	</div>
					</td>
 				</tr> --%> 
			</tbody>
		</table> <!-- // 기본정보 끝 -->
		
		<div class="popup_btns">
			<button type="submit" class="btn btn-active">${op:message('M00101')}</button> <!-- 저장 -->
		</div>
	</form:form>		

<!-- 다음 주소검색 -->
<daum:address />

<script type="text/javascript">
$(function() {

	// 메뉴 활성화
	Manager.activeUserDetails("modify");

    // [SKC] GroupCode와 LevelId 관계 맺기..
    handleGroupCodeAndLevelIdEvent();


	$('#user').validator(function() {
		
		// 이메일 중복 확인
		var $email = $('input[name=email]');
		var isDuplicationEmail = false;
		if ($email.val() != $('#currentEmail').val()) {

			
			var params = {
				'loginId' : $email.val()
			};
			
			$.ajaxSetup({
				async: false
			});
			
			$.post("/users/find-user",params,function(response){
				Common.loading.hide();
	
				if (response.userCount > 0) {
					isDuplicationEmail = true;
				} 
			});
		}
		
		if (isDuplicationEmail) {
			alert("${op:message('M00160')}");
			$email.focus();
			return false;
		}
		
		Common.confirm("${op:message('M00159')} ", function(form) {
			
			$('#user').submit();
		});
		return false;
	});
});

// [SKC] GroupCode와 LevelId 관계 맺기..
function handleGroupCodeAndLevelIdEvent() {
    var $groupCode = $('#groupCode');
    var $levelId = $('#levelId');

    $groupCode.on('change', function(e) {
        $levelId.val('0');  // 레벨 초기화 (미지정)
    });


    $levelId.on('change', function(e) {
        var groupCode = $levelId.find('option:selected').data('groupCode');
        if (groupCode == undefined) {
            groupCode = "default";
        }
        $groupCode.val(groupCode);
    });
}
	
function openDaumPostcode() {
	
		var tagNames = {
			'newZipcode'			: 'newPost',
			'zipcode' 				: 'post',
			'zipcode1' 				: 'post1',
			'zipcode2' 				: 'post2',
		}
		
		openDaumAddress(tagNames);
	
}

</script>
