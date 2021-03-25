<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" 		uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" 	uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" 	uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" 	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions"%>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page"%>
<%@ taglib prefix="module" 	tagdir="/WEB-INF/tags/modules"%>
<h3><span>콘텐츠관리</span></h3>
			 
<div class="board_write">
	<table class="board_write_table">
		<colgroup>
			<col style="width: 100px;">
			<col style="width: 500px;">

		</colgroup>
		
		
		<tbody> 

		<tr>
			<td class="label" rowspan="2"> 공제상품안내</td>
			<td>
				<div>
					<ul>
						<li><input id="subject1" name="statusCode" title="상태" type="radio" value="1000"> <label for="subject1">영유아생명신체</label></li>								 						 							 
						<li><input id="subject2" name="statusCode" title="상태" type="radio" value="1001"> <label for="subject2">화재(건물/집기)</label></li>							  							 								 							 							 
						<li><input id="subject3" name="statusCode" title="상태" type="radio" value="1002"> <label for="subject3">놀이시설배상책임</label></li>
						<li><input id="subject4" name="statusCode" title="상태" type="radio" value="1003"> <label for="subject4">가스사고배상책임</label></li>
					</ul>
				</div>
			</td>  
		</tr>
			<tr>						 
			<td>
				<div>
					<ul>
													  	 
						<li><input id="subject5" name="statusCode" title="상태" type="radio" value="1004"> <label for="subject5">보육교직원상해</label></li>							  	
						<li><input id="subject6" name="statusCode" title="상태" type="radio" value="1005"> <label for="subject6">보증공제</label></li>
						<li><input id="subject7" name="statusCode" title="상태" type="radio" value="1006"> <label for="subject7">공제회 회원가입 및 상품가입안내</label> </li>					 
						<li><input id="subject8" name="statusCode" title="상태" type="radio" value="1007"> <label for="subject8">사고처리절차</label></li>
					</ul>
				</div>
			</td> 
		</tr>

		<tr>
			<td class="label"> 안전사고 예방사업</td>
			<td>
				<div>
					<ul>
						<li><input id="subject11" name="statusCode" title="상태" type="radio" value="2000"> <label for="subject11">영유아</label> </li>		 
						<li><input id="subject12" name="statusCode" title="상태" type="radio" value="2001"> <label for="subject12">보육교직원</label> </li>					 							 
						<li><input id="subject13" name="statusCode" title="상태" type="radio" value="2002"> <label for="subject13">어린이집 안전관리</label></li>
						<li><input id="subject14" name="statusCode" title="상태" type="radio" value="2003"> <label for="subject14">우수안전사례</label></li>
					</ul>		
				 </div>
			</td>
		</tr>
			
			<tr>
			<td class="label" rowspan="2"> 공제회소개</td>
			<td>
				<div>
					<ul>
						<li><input id="subject21" name="statusCode" title="상태" type="radio" value="5000"> <label for="subject21">인사말</label></li>								 						 							 
						<li><input id="subject22" name="statusCode" title="상태" type="radio" value="5001"> <label for="subject22">설립배경</label></li>							  							 								 							 							 
						<li><input id="subject23" name="statusCode" title="상태" type="radio" value="5002"> <label for="subject23">사업추진방향</label></li>
					</ul>
				</div>
			</td>  
		</tr>
			<tr>
			 
			<td>
				<div>
					<ul>
						<li><input id="subject24" name="statusCode" title="상태" type="radio" value="5003"> <label for="subject24">주요연혁</label></li>							  	 
						<li><input id="subject25" name="statusCode" title="상태" type="radio" value="5004"> <label for="subject25">주요업무</label></li>							  	
						<li><input id="subject26" name="statusCode" title="상태" type="radio" value="5005"> <label for="subject26">조직도</label></li>
						<li><input id="subject27" name="statusCode" title="상태" type="radio" value="5007"> <label for="subject27">오시는길</label> </li>	
					</ul>
				</div>
			</td> 
		</tr>
		
		<tr>
			<td class="label"> 이용안내</td>
			<td>
				<div>
					<ul>
						<li><input id="subject31" name="statusCode" title="상태" type="radio" value="6001"> <label for="subject31">개인정보처리방침</label></li>								 						 							 
						<li><input id="subject32" name="statusCode" title="상태" type="radio" value="6002"> <label for="subject32">저작권정책</label></li>							  							 								 							 							 
						<li><input id="subject33" name="statusCode" title="상태" type="radio" value="6003"> <label for="subject33">이메일무단수집거부</label></li>
						<li><input id="subject34" name="statusCode" title="상태" type="radio" value="6004"> <label for="subject34">콘텐츠 내 개인정보처리방침</label></li>
					</ul>
				</div>
			</td>  
		</tr>
			
			
	</tbody>
</table>	
<p class="btns">
	<a href="javascript:;" onClick="fn_contentSel();" class="btn orange">선택</a>	&nbsp;								
</p>
<div class="comment"> </div>
<form:form modelAttribute="cmsContent" method="post">
	<table class="board_write_table">
		<colgroup>
			<col style="width: 100px;">
			<col style="width: 500px;">
		</colgroup>
		<tbody> 
			<tr>
				<td class="label"><span class="required_mark">*</span>에디터</td>
				<td > 
					<div class="tt">
						<input type="hidden" name="contentId" id="contentId" />
						<textarea id="content" name="content" title="컨텐츠내용" class="_filter" rows="10" cols="30"></textarea>
					</div> 
				</td>
			</tr> 
		</tbody> 
	</table>
	<p class="btns">
		<button type="submit" class="btn white">수정</button>
	</p>
</form:form>
	
</div>

<module:smarteditorInit />
<module:smarteditor id="content" />
	
	
	
	
<script type="text/javascript">

$(function() {
	// validator
	$('#cmsContent').validator(function() {
		if($("input[type=radio]:checked").size() == 0){
			alert("수정하려는 콘텐츠를 선택해주세요.");
			return false;
		}
		
		// 에디터 내용 검증 (내용 입력 여부 / 필터)
		try {
			if ($.validator.validateEditor(editors, "content") == false) return false;
		} catch(e) {}
		
		return Common.confirm("컨텐츠를 수정하시겠습니까?");
	});
});

function fn_contentSel(){
	if($("input[type=radio]:checked").size() == 0){
		alert("수정하려는 콘텐츠를 선택해주세요.");
		return;
	}
	$("#contentId").val($("input[type=radio]:checked").val());
	var params = {
		'contentId' : $("input[type=radio]:checked").val()
	};
	
	 $.post('/opmanager/cms/getContent', params, function(resp) {
		if (resp.isSuccess) {
			if(resp.data != null){
				editors.getById['content'].exec("SET_CONTENTS", [resp.data]);
			}else{
				editors.getById['content'].exec("SET_CONTENTS", ['']);
			}
		} else {
			alert(resp.errorMessage);
		}
	});
}

</script>