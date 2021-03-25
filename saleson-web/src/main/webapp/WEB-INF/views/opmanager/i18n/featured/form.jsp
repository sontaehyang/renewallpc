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
<%@ taglib prefix="shop"	uri="/WEB-INF/tlds/shop" %>
<style>
p.input_info {
	display: inline;
	padding-top: 3px;
	color: #e84700;
	font-size: 11px;
	margin-left: 10px;
}

.groupDiv {
	border-radius: 25px;
	border: 2px solid #73AD21;
	padding: 20px;
	margin: 10px;
}

.delete_item {
	position: absolute;
	top: 5px;
	right: 5px;
}

.groupPlaceHolder {
	width: 100%;
	height: 300px;
	background: #d6eafd	url("/content/styles/ui-lightness/images/ui-bg_diagonals-thick_20_666666_40x40.png") 50% 50% repeat;
	opacity: .5;
}

.text-info {
	padding-top: 8px;
}

p.item_image_main a.delete_item_image {
	right: 225px;
}
p.item_image_main a.delete_item_image.mobile {
	right: 10px;
}
</style>	
	
	<div class="location">
		<a href="#"></a> &gt;  <a href="#"></a> &gt; <a href="#" class="on"></a>
	</div>	
	
	<div class="item_list">
	
		<h3 class="mt20"><span><span class="mainPart">${mainPart}</span> 페이지 설정</span></h3>
		
		<form:form modelAttribute="featured" method="post" enctype="multipart/form-data">
			<input type="hidden" name="prodString" id="prodString" />
			<form:hidden path="featuredId" />
			<input type="hidden" name="featuredCheck" value="${featuredCheck}" />
			<form:hidden path="featuredType"/>
			<div class="board_write">						
				<table class="board_write_table" summary="특집페이지 정보">
					<caption>특집페이지 정보</caption>
					<colgroup>
						<col style="width: 220px;" />
						<col style="width: auto;" />
						<col style="width: 220px;" />
						<col style="width: auto;" />
					</colgroup>
					<tbody>
						<tr class="hidden">
							<td class="label">페이지 미리보기</td>
							<td>
								<div>
									<a href="/event/${menuName}" target="_blank"><img src="/content/opmanager/images/icon/icon_preview_pc.gif" id="pcPreview"/><label for="pcPreview">PC 미리보기</label></a>
									&nbsp; &nbsp; 
									<a href="/m/event/${menuName}" target="_blank"><img src="/content/opmanager_imager/icon/icon_preview_mobile.gif" id="mobilePreview"/><label for="mobilePreview">모바일 미리보기</label></a>
								</div>
							</td>
						</tr>
						<tr>
							<td class="label">구분</td>
							<td>
								<div>
									<form:radiobutton path="featuredClass" value="1"
										checked="checked" label="기획전" class="required requirement"
										title="구분" />
									<form:radiobutton path="featuredClass" value="2" label="이벤트"
										class="required requirement" title="구분" />
								</div>
							</td>
							<td class="label">목록 노출여부</td>
							<td>
								<div>
									<form:radiobutton path="displayListFlag" value="Y" checked="checked" label="${op:message('M00096')}" class="required requirement" title="${op:message('M00096')}" /> <!-- 공개 -->
									<form:radiobutton path="displayListFlag" value="N" label="${op:message('M00097')}" class="required requirement" title="${op:message('M00097')}" />     <!-- 비공개 -->
								</div>
							</td>
						</tr>
						<tr>
							<td class="label"><span class="mainPart">${mainPart}</span>명<span class="require">*</span><br/>(리스트에는 13자까지 노출됩니다.)</td>
							<td >
								<div>
									<form:input path="featuredName" class="full required" title="${mainPart}명" style="width : 73%;"/>
								</div>
							</td>
							<td class="label">URL <span class="require">*</span></td>
							<td>
								<div>
									<span style="font-size: 18px; color: #000;">${featured.featuredType == 2 ? '/m' : ''}/pages/</span>
									<form:input path="featuredUrl" class="required" title="URL" style="width : 15%;" />
									<input type="hidden" id="originalFeaturedUrl" value="${featured.featuredUrl}">
									<a href="javascript:;" id="featuredUrlSearch" class="table_btn">${op:message('M00148')}</a> <!-- 중복검사 -->
								</div>
							</td>
						</tr>
						<tr class="hidden">
							<td class="label"><span class="mainPart">${mainPart}</span>페이지 설명</td> <!-- 페이지 설명 -->
							<td>
								<div>
									<form:textarea path="featuredSimpleContent" cols="30" rows="3" title="${op:message('M01084')}"></form:textarea>
								</div>
							</td>
						</tr>
						<tr style="display:none;">
							<td class="label">구분</td>
							<td>
								<div>
									<input name="featuredCodeChecked" type="checkbox" value="all" ${op:checkedArray(featured.featuredCodes,'all') } id="all"  title="특집 팀설정" checked="checked"/> <label for="all">전체</label>
								</div>
							</td>
						</tr>
						<tr>
							<td class="label">${op:message('M00278')}</td>
							<td>
								<div>
									<form:input path="link" class="three optional _filter" maxlength="100" title="${op:message('M00278')}" style="width: 73%;"/> &nbsp;&nbsp;
									<form:checkbox path="linkTargetFlag" label="${op:message('M01035')}" value="Y" /> &nbsp;&nbsp;
									<input type="hidden" name="!linkTargetFlag" value="N" />
									<input type="hidden" name="!linkRelFlag" value="N" />
									<br/>
									<p class="text-info text-sm">
										※ 외부 사이트로 링크할 경우 작성합니다. (http:// 반드시 입력)
									</p>
								</div>
							</td>
							<td class="label">전용등급</td>
							<td>
								<div>
									<p>
										<form:select path="privateType">
											<form:options items="${privateTypes}" itemLabel="label" itemValue="value" />
										</form:select>
									</p>
								</div>
							</td>
							<!-- halla 카테고리 메인에 기획전 노출 기능 disable 2017-05-18_seungil.lee -->
							<%-- <td class="label select-category" style="display: none;">카테고리</td>
							<td class="select-category" style="display: none;">
								<div>
									<form:select path="categoryGroupId" class="category">
										<option value="0" label="선택안함" />
										<c:forEach items="${categoryGroupList}" var="categoriesGroup">
											<form:option value="${categoriesGroup.categoryGroupId}" label="${categoriesGroup.name}" />
										</c:forEach>
									</form:select>
								</div>
							</td> --%>
						</tr>
						<tr class="hidden">
							<td class="label">사용유무</td> 
							<td>
								<div>
									<form:radiobutton path="featuredFlag" value="Y" checked="checked" label="사용" class="required requirement" title="사용유무" />
									<form:radiobutton path="featuredFlag" value="N" label="사용안함" class="required requirement" title="사용유무" />
								</div>
							</td>
						</tr>
						<tr>
							<td class="label">댓글 사용 여부</td>
							<td>
								<div>
									<form:radiobutton path="replyUsedFlag" label="사용" value="Y" /> &nbsp;&nbsp;
									<form:radiobutton path="replyUsedFlag" label="미사용" value="N" /> &nbsp;&nbsp;
									<a href="javascript:fnReplyManagement();" class="table_btn">댓글 관리</a>
								</div>
							</td>
							<td class="label">이벤트 코드 URL</td>
							<td>
								<div>
									<c:choose>
										<c:when test="${mode eq 'edit'}">

											<c:choose>
												<c:when test="${not empty featured.eventCode}">
													${featured.eventViewUrl}
												</c:when>
												<c:otherwise>
													<button type="button" class="btn btn-active btn-sm" onclick="updateEventCode()">생성</button>
												</c:otherwise>
											</c:choose>

										</c:when>
										<c:otherwise>
											등록 후 생성 가능합니다.
										</c:otherwise>
									</c:choose>
								</div>
							</td>
						</tr>
						<tr>
							<td class="label">기간설정</td>
							<td colspan="3">
								<div>
									<span class="datepicker">
										<input type="text" name="startDate" id="startDate" maxlengt="8" class="datepicker" title="기획전 시작일자" value="${featured.startDate != '99999999' ? featured.startDate : '' }"/>
										<%-- <form:input path="startDate" maxlength="8" class="datepicker" title="기획전 시작일자" /> --%>
									</span>

									<form:select path="startTime" title="${op:message('M00508')}"> <!-- 시간 선택 -->
										<c:forEach items="${hours}" var="code">
											<form:option value="${code.value}" label="${code.label}"/>
										</c:forEach>
									</form:select>시
									<span class="wave">~</span>
									<span class="datepicker">
										<input type="text" name="endDate" id="endDate" maxlengt="8" class="datepicker" title="기획전 종료일자" value="${featured.endDate != '99999999' ? featured.endDate : '' }"/>
										<%-- <form:input path="endDate" maxlength="8" class="datepicker" title="기획전 종료일자" /> --%>
									</span>
									<form:select path="endTime" title="${op:message('M00508')}">
										<c:forEach items="${hours}" var="code">
											<form:option value="${code.value}" label="${code.label}"/>
										</c:forEach>
									</form:select>시
									<p class="text-info text-sm">
										※ 입력하지 않을시 상시게시 됩니다.
									</p>
								</div>
							</td>
						</tr>
						<tr>
							<td class="label">이미지(PC/MOBILE)</td>
							<td colspan="3">
								<div class="imageAdd">
									<span class="imageArea-1">
									<!-- 메인페이지 대표 이미지 -->
										<c:if test="${empty featured.featuredImage}">
											<input name="featuredFile" type="file" title="대표이미지" />
											<span class="f11">※ 메인페이지에 노출되는 <span class="mainPart">${mainPart}</span> 대표이미지로 권장크기는 612px * 242px 입니다.</span>
										</c:if>
										<c:if test="${!empty featured.featuredImage}">
											<p class="item_image_main" style="width: 829px; height: 237px;">
												<input type="hidden" class="fileType" value="2"/>
												<img src="${requestContext.uploadBaseFolder}/featured/${featured.featuredId}/featured/${featured.featuredImage}" class="item_image size-100" alt="" style="width: 612px; height: 242px" />
												<a href="javascript:;" class="delete_item_image"><img src="/content/opmanager/images/icon/icon_x.gif" alt="" /></a>
											</p>
											<br/><span class="f11">※ 메인페이지에 노출되는 <span class="mainPart">${mainPart}</span>대표이미지로 권장크기는 612px * 242px 입니다.</span>
										</c:if>
									</span>	
									
									<%-- 사용하지 않는 이미지로 주석 처리 2017-05-25 yulsun.yoo  
									<span class="imageArea-2">	
										<!-- 기획전 대표 배너 이미지 -->
										<c:if test="${empty featured.thumbnailImage}">
											<input name="thumbnailFile" type="file" title="${op:message('M01085')}" />
											<span class="f11">※ <span class="mainPart">${mainPart}</span> 목록 배너이미지로 권장크기는 1,100px * 150px 입니다.</span>
										</c:if>
										<c:if test="${!empty featured.thumbnailImage}">
											<p class="item_image_main" style="width: 450px; height: 100px;"> 
												<input type="hidden" class="fileType" value="1"/>
												<img src="${requestContext.uploadBaseFolder}/featured/${featured.featuredId}/featured/${featured.thumbnailImage}" class="item_image size-100" alt="" style="width: 235px;"/>
												<a href="javascript:;" class="delete_item_image"><img src="/content/opmanager/images/icon/icon_x.gif" alt="" /></a>
											</p>
											<br/><span class="f11">※<span class="mainPart">${mainPart}</span> 목록 배너이미지로 권장크기는 1,100px * 150px 입니다.</span>
										</c:if>						
									</span>	 --%>
								</div>
							</td>

							<%--<td class="label">이미지(모바일)</td>
							<td>
								<div class="imageAdd">
									<span class="imageArea-3">
										<!-- 모바일 메인페이지 대표 이미지 -->
										<c:if test="${empty featured.featuredImageMobile}">
											<input name="featuredFileMobile" type="file" title="모바일 대표이미지" />
											<span class="f11">※ 모바일 메인페이지 대표이미지로 기기에 따라 자동 조정됩니다. 권장크기는 480px * 250px 입니다.</span>
										</c:if>
										<c:if test="${!empty featured.featuredImageMobile}">
											<p class="item_image_main" style="width: 450px; height: 100px;"> 
												<input type="hidden" class="fileType" value="3"/>
												<img src="${requestContext.uploadBaseFolder}/featured/${featured.featuredId}/featured/${featured.featuredImageMobile}" class="item_image size-100" alt="" style="width: 450px;" />
												<a href="javascript:;" class="delete_item_image mobile"><img src="/content/opmanager/images/icon/icon_x.gif" alt="" /></a>
											</p>
											<br/><span class="f11">※ 모바일 메인페이지 <span class="mainPart">${mainPart}</span> 대표이미지로 기기에 따라 자동 조정됩니다. 권장크기는 480px * 250px 입니다</span>
										</c:if>		
									</span>
									
									&lt;%&ndash;	사용하지 않는 이미지로 주석 처리 2017-05-25 yulsun.yoo
									 <span class="imageArea-4">		
										<!-- 모바일 기획전 대표 배너 이미지 -->
										<c:if test="${empty featured.thumbnailImageMobile}">
											<input name="thumbnailFileMobile" type="file" title="${op:message('M01085')}" />
											<span class="f11">※ 모바일 <span class="mainPart">${mainPart}</span>목록 배너이미지로 권장크기는  456px * 150px 입니다.</span>
										</c:if>
										<c:if test="${!empty featured.thumbnailImageMobile}">
											<p class="item_image_main" style="width: 450px; height: 100px;"> 
												<input type="hidden" class="fileType" value="4"/>
												<img src="${requestContext.uploadBaseFolder}/featured/${featured.featuredId}/featured/${featured.thumbnailImageMobile}" class="item_image size-100" alt="" style="width: 235px;"/>
												<a href="javascript:;" class="delete_item_image"><img src="/content/opmanager/images/icon/icon_x.gif" alt="" /></a>
											</p>
											<br/><span class="f11">※ 모바일 <span class="mainPart">${mainPart}</span> 목록 배너이미지로 권장크기는  456px * 150px입니다.<!-- </span> --></span>
										</c:if>	
									</span>						 &ndash;%&gt;
								</div>
							</td>--%>

						</tr>
					</tbody>
				</table>
			</div>
					
			<h3 class="mt20"><span class="mainPart">${mainPart}</span> 설명</h3>
			<form:textarea path="featuredContent" cols="30" rows="25" title="상품상세설명" style="width: 1085px" ></form:textarea>
		
			<h3 class="mt20"><span><span class="mainPart">${mainPart}</span> 상품 관리</span></h3>
					
			<div class="board_write">						
				<table class="board_write_table" summary="특집페이지 정보">
					<caption>특집페이지 정보</caption>
					<colgroup>
						<col style="width: 220px;" />
						<col style="width: auto;" />
					</colgroup>
					<tbody>	
					<tr>
						<td class="label">리스트 형태</td>
						<td>
							<div>
								<form:radiobutton path="prodState" value="1" label="기본선택" checked="checked" class="required requirement" title="제품그룹 선택" />
								<form:radiobutton path="prodState" value="2" label="제품카테고리 정렬" class="required requirement" title="제품그룹 선택" />
								<form:radiobutton path="prodState" value="3" label="사용자그룹 정렬" class="required requirement" title="제품그룹 선택" />
								<br/>
								<span class="f11" style="line">※ 상품을 선택하지 않으려면 [기본선택]을 선택하세요.</span>
							</div>
						</td>
					</tr>
					<tr class="prodTr" id="prodTr1">
						<td class="label">선택 상품</td>
						<td>
							<div>
								<p class="mb10">
									<button type="button" id="button_add_relation_item" class="table_btn" onclick="findItem('prod${status.index}')"><span>${op:message('M00582')} <!-- 상품 추가 --> </span></button>
									<button type="button" class="table_btn" onclick="if(confirm('모든 상품을 삭제하시겠습니까?')){Shop.deleteRelationItemAll('prod${status.index}');}"><span>${op:message('M00411')} <!-- 전체삭제 --> </span></button>
								</p>
								
								<ul id="prod${status.index}" class="sortable_item_relation">
									<!-- <li style="display: none;"></li> -->
									<c:if test="${featured.prodState eq '2' || featured.prodState eq '1'}">
										<c:forEach items="${list}" var="item" varStatus="i">
											<c:if test="${!empty item.itemId}">
												<li id="prod${status.index}_item_${item.itemId}">
													<input type="hidden" name="prod${status.index}ItemIds" value="${item.itemId}" />
													<p class="image"><img src="${shop:loadImage(item.itemCode, item.itemImage, 'XS')}" class="item_image size-100 none" alt="상품이미지" /></p>
													<p class="title">[${item.itemUserCode}] <label style="color:red">[${op:numberFormat(item.salePrice)}]</label><br />${item.itemName}</p>
													
													<span class="ordering">${i.count}</span>
													<a href="javascript:javascript:deleteItem();" class="delete_item"><img src="/content/opmanager/images/icon/icon_x.gif" alt="" /></a>
												</li>
											</c:if>
										</c:forEach>
									</c:if>
								</ul>
							</div>
						</td>
					</tr>
					<tr class="prodTr add-group-item-area" id="prodTr2">
						<td class="label" valign="top">
							<p>선택 상품</p>
							<p style="padding-top:15px;">
								<button type="button" class="btn btn-dark-gray btn-xs" onclick="addItem();"><span>그룹추가</span></button>
								<button type="button" class="btn btn-gradient btn-xs fold-item-groups"><span>그룹접기</span></button>
								<button type="button" class="btn btn-gradient btn-xs show-item-groups"><span>그룹펼치기</span></button>
							</p>
						</td>
						<td id="activeArea">
							<!-- 사용자 그룹정렬과 일반 정렬 구분 - 사용자 그룹정렬일 경우는 초기화 되는것이 일반적 -->
							<c:if test="${featured.prodState eq '3'}">
								<c:forEach items="${userGroupList}" var="group" varStatus="status">
									<div class="groupDiv">
										<p class="mb10">
											<input type="text" class="userGroup" name="userGroup" class="full" title="사용자 그룹명" value="${group}"/>
											<button type="button" id="button_add_relation_item" class="table_btn" onclick="findItem('prod${status.index}')"><span>상품추가</span></button>
											<button type="button" class="table_btn" onclick="if(confirm('그룹내 모든 상품을 삭제하시겠습니까?')){Shop.deleteRelationItemAll('prod${status.index}');}"><span>전체삭제</span></button>
											<button type="button" class="table_btn" onclick="if(confirm('그룹을 삭제하시면 추가된 상품도 모두 삭제됩니다.\n삭제하시겠습니까?')){$(this).parent().parent().remove();}"><span>그룹삭제</span></button>
										</p>
										
										<ul id="prod${status.index}" class="sortable_item_relation">
											<!-- <li style="display: none;"></li> -->
											<c:set var="cnt" value="0"/>
											<c:forEach items="${list}" var="item" varStatus="i">
												<c:if test="${!empty item.itemId && group eq item.userDefGroup}">
													<c:set var="cnt" value="${cnt+1}"/>
													<li id="prod${status.index}_item_${item.itemId}">
														<input type="hidden" name="prod${status.index}ItemIds" value="${item.itemId}" />
														<p class="image"><img src="${shop:loadImageBySrc(item.imageSrc, 'XS')}" class="item_image size-100 none" alt="상품이미지" /></p>
														<p class="title">[${item.itemUserCode}] <label style="color:red">[${op:numberFormat(item.salePrice)}]</label><br />${item.itemName}</p>
														
														<span class="ordering">${cnt}</span>
														<a href="javascript:deleteItem();" class="delete_item"><img src="/content/opmanager/images/icon/icon_x.gif" alt="" /></a>
													</li>
												</c:if>
											</c:forEach>
										</ul>
									</div>
								</c:forEach>
							</c:if>
							</td>
						</tr>
					</tbody>
				</table>
			</div>
						
			<h3 class="mt20"><span>SEO 관리</span></h3>
			
			<div class="board_write">						
				<table class="board_write_table" summary="특집페이지 정보">
					<caption>특집페이지 정보</caption>
					<colgroup>
						<col style="width: 220px;" />
						<col style="width: auto;" />
					</colgroup>
					<tbody>
						<tr>
							<td class="label">${op:message('M00090')}</td> <!-- 브라우저 타이틀 -->
							<td>
								<div>
									<p class="text-info text-sm">
										* 제목은 브라우저의 상단과 검색 엔진에 페이지 제목으로 나타납니다. <br />
										* 제목은 최대 55-60자 까지 가능합니다.  <br />
										* 2-5 단어로 페이지를 설명할 수 있는 제목을 선택하세요.<br />
										* (예: 상품명, 페이지명 등)
										* (예: 상품페이지의 경우 상품명을 입력해 주시면 됩니다.)
									</p> <!-- (100자/한글50자) -->
									
									<form:input path="seo.title" class="full form-half" title="${op:message('M00090')}" /><br/>
									<span>※ ${op:message('M01516')}</span> <!-- 페이지 파일에 제목이 설정되어있는 경우 초기 값으로 입력합니다. --> 
								</div>
							</td>
						</tr>
						<tr>
							<td class="label">${op:message('M00997')}</td> <!-- Meta 키워드 -->
							<td>
								<div>
									<p class="text-info text-sm">
										* 최대 10 키워드로 페이지를 홍보할 수 있습니다. <br />
										* 키워드는 웹사이트 콘텐츠를 설명하는 단어나 짧은 구문이며 검색 사이트에서 사용자가 해당 페이지를 검색할 때 사용할 만한 단어를 포함해야 합니다.<br />
										* 태그란에 키워드를 입력할 때는 쉼표로 키워드들을 구분합니다.
									</p>
									<form:input path="seo.keywords" class="full form-half" title="${op:message('M00997')}" />
								</div>
							</td>
						</tr>
						<tr>
							<td class="label">${op:message('M00998')}</td> <!-- Meta용 기술서 -->
							<td>
								<div>
									<p class="text-info text-sm">
										* 해당 문구는 검색 엔진 목록의 사이트 제목 아래에 표시됩니다. <br />
										* 페이지 설명은 최대 250자 까지 사용 가능합니다. (또는 약  15-20 단어) <br />
										* (예: 페이지 제목이 Google 지도인 경우, 사이트 설명은 "웹에서 지도와 운전 경로를 보고 지역 정보를 검색하세요!"가 될 수 있습니다.)
									</p>
									<form:textarea path="seo.description" cols="30" rows="3" title="${op:message('M00998')}"></form:textarea>
								</div>
							</td>
						</tr>
						<tr>
							<td class="label">${op:message('M00999')}</td> <!-- H1태그 -->
							<td>
								<div>
									<p class="text-info text-sm">
									※ ${op:message('M01040')} <!-- 페이지 상단의 <H1> 태그에 배포됩니다. -->
									</p>
									<form:input path="seo.headerContents1" class="full form-half" title="${op:message('M00999')}" />
								</div>
							</td>
						</tr>
					</tbody>
				</table>
			</div>
	
	
	
	<div class="btn_center" style="padding-right: 190px;">
		<button type="submit" class="btn btn-active">${op:message('M00088')}</button>	 <!-- 등록 -->
		<a href="javascript:Link.list('${requestContext.managerUri}/${featuredTypeUri}/list')" class="btn btn-default">${op:message('M00037')}</a>	 <!-- 취소 -->
	</div>
</form:form>		
</div> <!-- // item_list02 -->
	
	
	
	<module:smarteditorInit />
	<module:smarteditor id="featuredContent" />		
	
<script type="text/javascript">

var patterns = /^[a-zA-Z0-9_-]+$/;

	$(function(){
		
		// 기획전 상품 그룹 펼치기
		showItemGroups();
		// 기획전 상품 그룹 접기
		hideItemGroups();
		
		$('input[name=featuredClass]').on('click', function() {
			if ($(this).val() == 1) {
				$('.mainPart').text('기획전');
				$('input[name=featuredName]').attr('title', '기획전명');
				showAndHideImages();
			} else {
				$('.mainPart').text('이벤트');
				$('input[name=featuredName]').attr('title', '이벤트명');
				showAndHideImages();
				
			}
		});
		
		$("#activeArea").sortable({
			placeholder : "groupPlaceHolder"
		});
		
		$(".delete_item").click(function(){
			if (confirm("삭제하시겠습니까?")) {
				$(this).parent().remove();
			}
		});
		
		/* $("input[name='featuredCodeChecked']").on("click",function(){
			if($(this).val() == 'all'){
				if($(this).prop("checked")){
					$("input[name='featuredCodeChecked']").prop("checked",false);
					$("input[name='featuredCodeChecked']").attr("disabled", true);
					$(this).attr("disabled", false);
					$(this).prop("checked",true);
				} else {
					$("input[name='featuredCodeChecked']").attr("disabled", false);
				}
			}
		}); */
		
		$("input[name='displayListFlag']").on("change",function(){
			if($(this).val()== "N") {
				$(".requirement").removeClass("required");
			}
			else {
				$(".requirement").addClass("required");
			}
		});
		
		$("input[name='displayListFlag']").trigger("change");
		
		$("#featured").validator(function() {
			if($("input[name='displayListFlag']:checked").val()== "Y") {
				var value = $("input[name='featuredUrl']").val();
				
				if(!patterns.test(value)){
					alert(Message.get("M01332")); // 영문, 숫자, 하이픈, 언더바 만 입력가능합니다. 
					$("#categroyUrlSearch").focus();
					return false;
				}
			<c:if test="${isEvent ne 'Y'}">
				var outSideLink = $("input[name='link']").val();
				if ( !checkedOutSideLink(outSideLink) ){
					alert("※ 외부 사이트 링크인 경우 반드시 http:// (또는 https://)로 시작하도록 작성하셔야 합니다.");
					return false;
				}
			</c:if>
			
			
				var $startDate = $('#startDate').val();
				var $endDate = $('#endDate').val();
				
				if(($startDate != "" && $startDate != null) && ($endDate != "" && $endDate != null)) {
					if ( checkedDate( $startDate, $('#startTime').val(), $endDate, $('#endTime').val() ) ) {
						alert('사용기간의 종료기간이 시작기간보다 빠를 수 없습니다.');
						$('#startDate').focus();
						return false;
					}
				}
				
				if(($startDate == "" || $startDate == null) && ($endDate != "" && $endDate != null)) {
					alert('사용기간의 종료기간은 시작기간 선택없이 설정할 수 없습니다.');
					$('#startDate').focus();
					return false;
				}
				
				if (!($('#featured input:checkbox[name=featuredCodeChecked]').is(":checked"))) {
					alert("구분이 선택되지 않았습니다.");
					$('#featured input:checkbox[name=featuredCodeChecked]').eq(0).focus();
					return false;
				}
				
				
				
				/* if ($('#featured #featuredContent').val() == '') {
					alert("내용 작성해주세요.");
					$('#featured #featuredContent').focus();
					return false;
				} */
				
			}
			
			Common.getEditorContent("featuredContent");
			
			if( $("input[name='featuredCheck']").val() != '1' ){ 
				var message = Message.get("M01087");	// 특집페이지URL의 중복 여부가 체크되지 않았습니다.
				alert(message);
				$("#categroyUrlSearch").focus();
				
				return false;
			}
			
			//이상우 [2017-03-16 수정] 사용자그룹 정렬 방식 일때만 그룹명 체크
			var prodState = $("input[name=prodState]:checked").val();
			if (prodState == '3') {
				var groupChk = 0;
				$("input[name=userGroup]").each(function(){
					if ($(this).val()=="") {
						alert("그룹명을 입력해주세요.");
						groupChk = 1;
						$(this).focus();
						return false;
					}				
				});
				
				if (groupChk == 1) {
					return false;
				}
			}
			var stateFlag = $("input[name=featuredClass]:checked").val();
			if (stateFlag == '1' && prodState == '3') {
				if ($("#prodTr2").find(".groupDiv").length<1) {
					alert("현재 기획전에 추가된 그룹명이 없습니다. 그룹을 추가해주세요.");
					return false;
				}
				
				if ($("#prodTr2").find("li").length<1) {
					alert("현재 기획전에 추가된 상품이 없습니다. 상품을 추가하시거나\n 제품그룹선택 항목의 '기본선택'을 선택해주세요.");
					return false;
				}
				
				var itemChk = 0;
				var groupName=[];
				$(".groupDiv").each(function(index){
					var groupEl = $(this).find(".userGroup");
					groupEl.val($.trim(groupEl.val()));
					groupName[index] = groupEl.val();
					if ($(this).find("li").length == 0) {
						alert("\""+groupEl.val()+"\"그룹에 등록된 제품이 없습니다.");
						itemChk = 1;
						groupEl.focus();
						return false;
					}
					$(this).find("ul").attr("id","prod"+index);
					$(this).find("ul li input").attr("name","prod"+index+"ItemIds");
				});
				
				if (itemChk == 0) {
					for (var i=0; i<groupName.length; i++) {
						for (var j=i; j<groupName.length; j++) {
							if (i != j && groupName[i].toLowerCase() === groupName[j].toLowerCase()) {
								alert("그룹명이 중복됩니다. 변경 해주세요.");
								$(".userGroup").eq(j).focus();
								itemChk = 1;
								return false;
							}
						}
						if (itemChk == 1) {return false;}
					}
				}
				
				
				if (itemChk == 1) {
					return false;
				}
			}
			else {
				/* if ($("#prodTr1").find("li").length<1 && $("input[name='displayListFlag']:checked").val()== "Y") {
					alert("현재 기획전에 추가된 상품이 없습니다. 상품을 추가해주십시오.");
					return false;
				} */
			}
			makeProdString();
			
			
			
		});
		
		$("#featuredUrlSearch").on("click",function(){
			
			var value = $("input[name='featuredUrl']").val();
			
			if(value == '') {
				alert("URL을 입력하세요.")
				$("#categroyUrlSearch").focus();
				return false;
			}
			
			if(!patterns.test(value)){
				alert(Message.get("M01332")); // 영문, 숫자, 하이픈, 언더바 만 입력가능합니다. 
				$("#categroyUrlSearch").focus();
				return false;
			}
			
			var originalValue = $("#originalFeaturedUrl").val();
			if (value == originalValue) {
				alert("사용 가능한 페이지명입니다.");	// 사용가능
				$("input[name='featuredCheck']").val("1");
				return false;
			}
			
			var param = {
				"featuredUrl" : $("input[name='featuredUrl']").val()	
			};
			
			$.post("/opmanager/featured/url-search",param,function(resp){
				Common.responseHandler(resp, function(){
					alert("사용 가능한 페이지명입니다.");	// 사용가능
					$("input[name='featuredCheck']").val("1");
				},function(){
					alert("사용중인 페이지명입니다.");	// 사용불가
					$("input[name='featuredCheck']").val("0");
				});
			});
			
		});
		
		$(".delete_item_image").on("click",function(){
			var fileType=$(this).parent().find(".fileType").val();
			var fileAdd = $(this).parent().parent();
			var desc = "";
			var label = "";
			var message = Message.get("M00196");	// 삭제하시겠습니까?

			if($('input[name="featuredClass"]:checked').val() == '1') {
				label = "기획전";
			} else {
				label = "이벤트";
			}
			
			
			Common.confirm(message, function(){
				$.post("/opmanager/featured/delete-image", "featuredId="+$("input[name='featuredId']").val()+"&fileType="+fileType , function(resp){
					Common.responseHandler(resp, function(){
						//$(".item_image_main").remove();
						$(this).parent().remove();
						 var message = "";
						var nm = "";
						if(fileType=="1") {
							message=Message.get("M01085"); // 섬네일이미지
							nm="thumbnailFile";
							desc="<span class=\"f11\">※ <span class=\"mainPart\">" + label +" </span>목록 배너이미지로 권장크기는 1,100px * 150px 입니다.</span>";
						}
						else if(fileType=="2") {
							message=Message.get("M00982"); // 대표이미지
							nm="featuredFile";
							desc="<span class=\"f11\">※  메인페이지에 노출되는 <span class=\"mainPart\">" + label + "</span>대표이미지로 권장크기는 258px * 220px 입니다.</span>";
						}
						else if(fileType=="3") {
							message="모바일 대표이미지"; // 모바일 대표이미지
							nm="featuredFileMobile";
							desc="<span class=\"f11\">※ 모바일 메인페이지 <span class=\"mainPart\">" + label +"</span>대표이미지로 기기에 따라 자동 조정됩니다. 권장크기는 480px * 250px 입니다.</span>";
						}
						else if(fileType=="4") {
							message="모바일 대표이미지"; // 모바일 섬네일이미지
							nm="thumbnailFileMobile";
							desc="<span class=\"f11\">※ 모바일 <span class=\"mainPart\">" + label +"</span> 목록 기기에 따라 자동 조정됩니다. 권장크기는 400px * 85px 입니다.</span>";
						}
						fileAdd.html('<input name="'+ nm +'" type="file" title="' + message + '" />'+desc);	 
					});
				});
			});
		});
		
		// 관련상품 드레그
	    $(".sortable_item_relation").sortable({
	        placeholder: "sortable_item_relation_placeholder"
	    });
		
		$("input[name=prodState]").change(function(){
			var stateFlag = $("input[name=prodState]:checked").val();
			if(stateFlag == '1' || stateFlag == "2") {
				$("#prodTr1").show();
				$("#prodTr2").hide();
			}
			else {
				$("#prodTr2").show();
				$("#prodTr1").hide();
			}
		});
		
		$("input[name=prodState]").trigger("change");
		
		showAndHideImages();
	});
	
	function checkedOutSideLink (link){
		
		var pattern1 = new RegExp("http://");
		var pattern2 = new RegExp("https://");
		
		if (link == "") {
			return true;
		}
		
		if( (pattern1.test(link)) ){
			return  true;
		}
		
		if( (pattern2.test(link)) ){
			return  true;
		}

		return false;
		
	}
	
	function findItem(dest) {

		var featuredType = '${featured.featuredType}';
		var conditionType = "";
		if (featuredType == '8' || featuredType == '9') {
			conditionType = "FIND_ITEM_POPUP_FOR_PLANNER";
		}
		
		Shop.findItem(dest, conditionType);
	}
	
	function prodDrag()	{
		$(".sortable_item_relation").sortable({
			placeholder : "sortable_item_relation_placeholder"
		});
		$("#activeArea").sortable({
			placeholder : "groupPlaceHolder"
		});
		
	}

	var itemNo;
	if ("${featured.prodState}"=="2") {
		itemNo = 0;
	}
	else {
		itemNo = "${fn:length(userGroupList)}";
	}
	
	function addItem() {
		$("#activeArea")
				.prepend(
						'<div class="groupDiv"><p class="mb10"><input type="text" class="userGroup" name="userGroup" class="full required requirement" title="사용자 그룹명" />&nbsp;<button type="button" id="button_add_relation_item" class="table_btn" onclick="findItem(\'prod'
								+ itemNo
								+ '\')"><span>상품추가</span></button>&nbsp;<button type="button" class="table_btn" onclick="Shop.deleteRelationItemAll(\'prod'
								+ itemNo
								+ '\')"><span>전체삭제</span></button>&nbsp;<button type="button" class="table_btn" onclick="if(confirm(\'그룹을 삭제하시면 추가된 상품도 모두 삭제됩니다.\\n삭제하시겠습니까?\')){$(this).parent().parent().remove();}"><span>그룹삭제</span></button></p><ul id="prod'+itemNo+'" class="sortable_item_relation"><!--<li style="display: none;">--></li></ul></div>');
		itemNo++;
		prodDrag();
	}
	
	function makeProdString() {
		var stateFlag = $("input[name=prodState]:checked").val();
		if (stateFlag == '1' || stateFlag == '2') {
			$("#prodTr2").remove();
		}
		else {
			$("#prodTr1").remove();
		}
		
		var prod=[""];
		if (stateFlag == '1' || stateFlag == '2') {
			prod[0]="";
			$("input[name=prod${dest}ItemIds]").each(function(index) {
				if(index!=0){prod[0]+="~";}
				//alert($(this).val());
				prod[0]=prod[0]+$(this).val();
			});
		}
		else {
			var j = 0;
			for(var i=0; i<itemNo; i++)	{
				prod[j]="";
				if ($("input[name=prod"+i+"ItemIds]").length>0) {
					$("input[name=prod"+i+"ItemIds]").each(function(index) {
						if(index!=0){prod[j]+="~";}
						prod[j]+=$(this).val();
					});
					j++;
				}
			}
		}
		
		$("#prodString").val(prod);
		return false;
	}
	
	function  checkedDate(startDate,startTime, endDate, endTime){
		
		return (startDate+startTime+'00') > (endDate+endTime+'59' );
		
	}
	
	/* function unfoldItemGroup() {
		$('.add-group-item-area').removeClass('fold');
		$('.add-group-item-area .groupDiv').removeClass('fold');
	}
	function foldItemGroup() {
		$('.add-group-item-area').addClass('fold');
		$('.add-group-item-area .groupDiv').addClass('fold');
	} */
	
	function showAndHideImages() {
		/*
	    if($("input[name=featuredClass]:checked").val() == '1') {
			$('.imageArea-1').show();
			$('.imageArea-3').show();
// 			$('.select-category').show();
		} else {
			$('.imageArea-1').hide();
			$('.imageArea-3').hide();
// 			$('.select-category').hide();
		}
		*/
	}
	
	function showItemGroups() {
		$('.fold-item-groups').on('click', function() {
			$('.groupDiv').find('ul').hide();
		});
	}
	
	function hideItemGroups() {
		$('.show-item-groups').on('click', function() {
			$('.groupDiv').find('ul').show();
		});
	}

	function fnReplyManagement() {
	    Common.popup('/opmanager/featured/manage-event-reply?featuredId=' + $('#featuredId').val(), 'create', 1024, 800, 1);
	}

	function updateEventCode() {
		var id = $('#featuredId').val();
		$.post('/opmanager/featured/update-event-code/'+id, {}, function(resp){
			Common.responseHandler(resp, function(){
				location.reload();
			});
		});
	}

</script>