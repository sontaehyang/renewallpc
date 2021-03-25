<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="op" 	uri="/WEB-INF/tlds/functions" %>
<div class="content_top">
	<div class="breadcrumbs">
		<a href="#" class="home"><span class="hide">home</span></a>
		<a href="#">회사소개</a>
		<span>쇼핑가이드</span> 
	</div>	<!--// breadcrumbs E--> 
</div><!--// content_top E-->

<div class="company mt15">  
	<jsp:include page="/WEB-INF/views/layouts/front/inc_lnb_pages.jsp" /> 
	<div id="sub_contents_min"> 
		<h2 class="mt15">쇼핑가이드</h2>  
	 	<div class="guide_menu">
	 		<ul>
	 			<li><a href="#guide_01">상품찾기</a></li>
	 			<li><a href="#guide_02">주문하기</a></li>
	 			<li><a href="#guide_03">문의사항</a></li>
	 			<li><a href="#guide_04">장바구니 넣기/장바구니</a></li>
	 			<li><a href="#guide_05">주문확인</a></li>
	 			<li><a href="#guide_06">결제에 관하여</a></li>
	 			<li><a href="#guide_07">배송에 관하여</a></li>
	 			<li><a href="#guide_08">배송료에 관하여</a></li>
	 			<li><a href="#guide_09">주문취소, 교환 및 환불</a></li>
	 			<li><a href="#guide_10">${op:message('M00246')}에 관하여</a></li>
	 			<li><a href="#guide_10">${op:message('M00246')} 사용방법</a></li>
	 			<li><a href="#guide_11">이용후기 쓰는 방법</a></li>
	 			<li><a href="#guide_11">마이페이지 사용방법</a></li>
	 		</ul> 
	 	</div> <!--//guide_menu E-->
		
		<div class="shopping_guide" id="guide_01">	
			<div class="guide_wrap">
				<span class="subject"><img src="/content/images/common/shopping_title01.jpg" alt="상품찾기"></span>
				<div class="guide_cont">
					<p> 상품 카테고리 리스트, 상품검색 등을 이용하여 사이트 내를 이동, 검색할 수 있습니다. 
					각 페이지의 상단에는 상품검색 박스가 있습니다. 각 페이지의 왼쪽 상단에는 상품 카테고리 리스트가 있습니다. </p>
					<p> 상품을 찾을 때에는, 상품검색란에 상품명을 입력하여 [검색] 버튼을 클릭해 주세요. 상품이 검색되면 그 상품의 이미지 사진이나
					상품명의 링크를 클릭해주세요. 상품의 상세 페이지로 넘어갑니다. </p>
					<p>상세페이지에는 상품가격과 재고상황, 상품상세설명, 사진, 이용후기 등이 표시되어 있습니다. </p>
				</div>
			</div><!--//guide_wrap E-->
			
			<div class="guide_wrap"id="guide_02">
				<span class="subject"><img src="/content/images/common/shopping_title02.jpg" alt="주문하기"></span>
				<div class="guide_cont type02">
					<p>리뉴올PC는 인터넷, 전화, FAX, 모바일로 주문 가능합니다.</p>
					<p class="title02">인터넷 주문</p> 
					<a href="">http://mall.dev.onlinepowers.com</a>
					<p class="title02">전화로 주문</p> 
					<ul>
						<li>대표전화　    : 02-6737-9200</li>
						<li>전화접수시간 : 평일10：00 ~ 18：00　토요일 10：00 ~ 14：00</li>
					</ul>
					<p class="title02">FAX로 주문</p> 
					<ul>
						<li>FAX : 02-3446-5339</li> 
					</ul>
					<p class="title02">모바일로 주문</p> 
					<ul>
						<li>http://mall.dev.onlinepowers.com/sp</li> 
					</ul>
				</div>
			</div><!--//guide_wrap E-->
			
			<div class="guide_wrap" id="guide_03">
				<span class="subject"><img src="/content/images/common/shopping_title03.jpg" alt="문의하기"></span>
				<div class="guide_cont type02">
					<p>문의에 관한 시스템, 메일, 전용전화가 개설되어 있습니다.</p>
					<p class="title02">문의 시스템으로 문의하기</p> 
					<a href="">문의 시스템 이용하기(로그인 후 이용 가능)</a>
					<p class="title02">메일로 문의하기</p> 
					<ul>
						<li>E-mail : wjb1588@naver.com</li> 
					</ul>
					<p class="title02">전용전화로 문의하기</p> 
					<ul>
						<li>대표전화　    : 02-6737-9200</li> 
						<li>전화응대시간 : 평일10：00 ~ 18：00　토요일 10：00 ~ 14：00 (일요일, 공휴일은 휴무)</li>
					</ul>
					<p class="title02">팩스로 문의하기</p> 
					<ul>
						<li>FAX : 02-3446-5339</li> 
					</ul>
				</div>
			</div><!--//guide_wrap E-->
			
			<div class="guide_wrap" id="guide_04">
				<span class="subject"><img src="/content/images/common/shopping_title04.jpg" alt="장바구니 넣기,장바구니보기"></span>
				<div class="guide_cont">
					<p>희망하는 상품이 있으시면 [장바구니] 버튼을 눌러, 장바구니에 넣어주세요.</p>
					<p>각 페이지 상단에 있는 [장바구니]버튼이나 우측 퀵 메뉴에 있는 장바구니 아이콘을 클릭하면 언제든지 장바구니 내용을 볼 수 있습니다.</p>
					<p>장바구니에는 상품의 수량을 변경하거나, 상품 삭제도 가능합니다.</p>
				</div>
			</div><!--//guide_wrap E-->
			
			<div class="guide_wrap" id="guide_05">
				<span class="subject"><img src="/content/images/common/shopping_title05.jpg" alt="주문확인"></span>
				<div class="guide_cont">
					<ol>
						<li>장바구니를 확인한 후, <span>[선택 상품주문]</span>버튼이나 <span>[전체 상품주문]</span>을 눌러 진행해주세요.</li>
						<li>고객님의 정보를 입력한 후, <span>[결제하기]</span>버튼을 눌러주세요.</li>
						<li>모든 내용을 확인한 후 <span>[주문하기]</span>버튼을 눌러주세요.</li>
						<li>확인 메세지가 나오면 주문 완료입니다.</li>
						<li>주문확인메일이 자동으로 송신되므로 확인해주세요.</li>
						<li>24시간동안 메일이 가지 않을 때에는, 번거로우시더라도 한번 더 구입을 해주시거나, 전화, FAX, 메일로 연락주시기 바랍니다.</li>
						<li>주문해주셔서 감사합니다.</li>
					</ol>
				</div>
			</div><!--//guide_wrap E-->
			
			<div class="guide_wrap" id="guide_06">
				<span class="subject"><img src="/content/images/common/shopping_title06.jpg" alt="결제에 관하여"></span>
				<div class="guide_cont type02">
					<p class="title02">안전한 대금 결제 시스템</p> 
					<p>
						저희 리뉴올PC는 무통장입금과 신용카드의 두 가지 결제방법을 제공하여 드립니다.
						무통장 입금은 상품 구매 대금을 PC뱅킹, 인터넷뱅킹, 텔레뱅킹 혹은 가까운 은행에서 직접 입금하시면 되고, 신용카드 결제는 
						INICIS 전자결제를 이용하므로 보안문제는 걱정하지 않으셔도 되며, 고객님의 이용내역서에는 (주)INICIS로 기록됩니다.
					</p>
					<p class="title02">이용가능한 국내 발행 신용카드</p>	
					<ul>
						<li>국내발행 신한, 롯데, 씨디, NH채움, KB국민, 현대, 삼성, 외환, BC, 하나SK 등</li>  
					</ul>
					<p class="strong">해외발행카드는 사용하실 수 없습니다. </p>
					<p class="title02">무통장 입금</p>	
					<ul>
						<li>- 은행 : 국민은행</li>
						<li>- 계좌번호 : 999 666 666 43</li>
						<li>- 예금주 : 월드제이비(주)</li> 
					</ul>
					<p class="strong">무통장 입금 시 송금자 이름은 주문 시 입력하신 주문자의 실명과 동일인이어야 합니다.</p>
				</div>
			</div><!--//guide_wrap E-->
			
			<div class="guide_wrap" id="guide_07">
				<span class="subject"><img src="/content/images/common/shopping_title07.jpg" alt="배송기간 및 배송방법"></span>
				<div class="guide_cont type02">
					<p class="title02 type02">
						고객님께서 무통장 입금으로 주문하신 경우에는 입금하신 날로부터, 신용카드로 구매하신 경우에는 구매하신 날로부터 4-5일 
						이내에(최장 7일이내) 입력하신 배송처로 주문상품이 도착하게 됩니다. 단, 주문당일 은행 마감시간내에 입금을 하셔야 합니다.
						(입금확인후, 주문품 발송업무진행가능) 주문하신 상품에 따라 배송기간이 조금 상이할 수 있습니다. 
					</p>	
					<p class="title02 type02">주문하실 때 희망 배송일자를 넉넉히 잡아주시면(5일이상) 원하시는 날자에 배송할 수 있도록 최선을 다하겠습니다.</p>	
					<p class="title02 type02">저희 리뉴올PC에서는 구입하신 상품의 배송 방법을 CJ택배/경동택배를 원칙으로 하고 있습니다.
						(배송방법은 상품 종류에 따라 상이할 수 있습니다.)
					</p>	 
					<p class="strong02">저희 리뉴올PC는 99% CJ택배를 이용하고 있지만, 물류창고(부산)가 사무실과 떨어져 있는 관계로 침대와 같은 무게가 나가고,
						부피가 큰 상품은 경동택배를 이용하고 있습니다
					</p>
				</div>
			</div><!--//guide_wrap E--> 
			
			<div class="guide_wrap" id="guide_08">
				<span class="subject"><img src="/content/images/common/shopping_title08.jpg" alt="배송료에 관하여"></span>
				<div class="guide_cont type02">
					<p class="title02">5만원 미만 구입시 : <span class="normal">2,500원</span></p>
					<p class="title02">5만원 이상 구입시 : <span class="normal">무료</span></p>
					<p class="title02">제주도의 경우 </p>
					<ul>
						<li>- 10만원 미만 구입시 : 5,000원</li>
						<li>- 10만원 이상 구입시 : 무료</li>
					</ul>  
					<br>
					<p class="strong02">지역이나 상품의 무게, 부피에 따른 추가 배송비는 일절 발생하지 않습니다.</p>
				</div>
			</div><!--//guide_wrap E-->
			
			<div class="guide_wrap" id="guide_09">
				<span class="subject"><img src="/content/images/common/shopping_title09.jpg" alt="주문취소 교환 및 환불"></span>
				<div class="guide_cont type03">
					<p>리뉴올PC는 소비자의 보호를 위해서 규정한 제반 법규를 준수합니다.</p>
					<p>
						주문 취소의 경우, 미결재인 상태에서는 고객님이 직접 사이트에서 취소하실 수가 있습니다. 
						은행 마감시간후 취소는 당일 오후 6시까지 저희 고객센타 (02-3446-5338)로 문의해 주시기 바랍니다. 
					</p> 
					<p>
						무통장 입금주문의 경우 당일 은행 마감시간내에 송금을 하지 않으면 자동 주문 취소가 되고, 
						송금이나 이체를 하신 경우에는 환불조치 해드립니다. 	
					</p>
					<p>
						카드로 결제하신 경우, 승인 취소가 가능하면 취소을 해드리지만 승인 취소가 불가능한 경우 해당 금액에서 수수료를 뺀 금액으로 
						송금해 드립니다(카드사수수료는 차감함). 
					</p>
					<p>반송을 하실 때에는 주문번호, 회원번호를 메모하여 보내주시면 보다 신속한 처리에 도움이 됩니다. (문의전화 02-3446-5338) </p>
					<p>
						우송된 상품이 주문상품과 주문번호가 동일하나 원하는 상품이 아닌 경우는 우송 받으신 날부터 7일 이내에 아래 주소로 
						주문번호, 회원번호, 이름, 반품사유 등을 메모하여 반송해 주시면 상품 대금은 회원번호로 예치 하여 차후 다른 상품 주문시 
						사용하실 수 있습니다. 이 때 발생되는 모든 비용과 부담금, 주문시 우송된 비용은 주문자 부담입니다. 
					</p>
					<p class="title02">교환 및 반품 </p>
					<ol class="type02"> 
						<li>1. 상품 청약철회 가능기간은 상품 수령일로 부터 7일 이내 입니다. </li>
						<li>2. 상품의 오배송, 하자 시에는 100% 무료로 교환해 드립니다. (단, 7일이 경과된 이후에는 불가) </li>
						<li>3. 상품 택(tag)제거 또는 개봉으로 상품 가치 훼손 시에는 7일 이내라도 교환 및 반품이 불가능합니다.  
							 <span>- 저단가 상품, 일부 특가 상품은 고객 변심에 의한 교환, 반품은 고객께서 배송비를 부담하셔야 합니다.</span> 
							 <span>- 조립 상품의 경우 조립을 진행한 이후에는 단순 교환, 반품은 불가능합니다. </span> 
						</li>	
						<li>4. 모니터의 해상도에 따른 색상 차이나 측정방법에 따라 명시된 제품의 치수와 실제 치수의 1~3cm 정도의 차이로 인한 
						    <span>교환 및 반품은 불가능합니다. </span>
						</li>
						<li>5. 일부 상품은 신모델 출시, 부품가격 변동 등 제조사 사정으로 가격이 변동될 수 있습니다. </li>
						<li>6. 수입,명품 제품의 경우, 제품 및 본 상품의 박스 훼손, 분실 등으로 인한 상품 가치 훼손 시 교환 및 반품이 불가능 하오니, 
						   <span>양해 바랍니다.</span> 
						</li>
						<li>7. 교환 및 반품은 반드시 CJ택배를 이용해주세요. </li>
					</ol>
					<p class="strong02">반송주소 : 부산광역시 기장군 정관면 달산리 1039-8</p>
				</div>
			</div><!--//guide_wrap E--> 
			
			<div class="guide_wrap" id="guide_10">
				<span class="subject"><img src="/content/images/common/shopping_title10.jpg" alt="${op:message('M00246')}에 관하여"></span>
				<div class="guide_cont type02">
					<p class="title02">${op:message('M00246')} 환원이란</p>
					<ul>
						<li>- 리뉴올PC의 ${op:message('M00246')} 환원이란 고객님들만의 특전입니다.</li>
						<li>- 리뉴올PC를 이용하시면 ${op:message('M00246')}를 환원=${op:message('M00246')}가 적립됩니다.</li>
						<li>- 리뉴올PC의 ${op:message('M00246')}는 「100${op:message('M00246')} = 100원」입니다.</li>
						<li>- 적립된 ${op:message('M00246')}는 언제든지 간단히 사용할 수 있습니다.</li>
					</ul> 
					<p class="title02">${op:message('M00246')} 적립하기</p>
					<p>3개의 방법이 있습니다.</p>
					<ul>
						<li>1．신규회원등록 → 1,000${op:message('M00246')}를 드립니다.<br>(회원등록 후 로그인하시면 마이페이지에 고객님의 ${op:message('M00246')}가 표시됩니다.) </li>
						<li>2．상품을 구입 → 제품 가격의 1%에 해당하는 ${op:message('M00246')}가 적립됩니다.</li>
						<li>3．상품 이용후기(리뷰) 적기 → 100${op:message('M00246')}를 드립니다. </li>
					</ul>  
					<p>※ 이용후기(리뷰)의 내용에 따라서는 ${op:message('M00246')} 적립이 되지 않을 경우도 있으므로 양해바랍니다.</p>
					<p class="title02">${op:message('M00246')} 사용방법</p>
					<div class="photo_guide"><img src="/content/images/common/guide_point.jpg" alt=""></div>
					<p>
						${op:message('M00246')}는 상품 구입시에 사용할 수 있습니다. 주문/결제의 「할인혜택 사용」에서 「${op:message('M00246')} 할인」란이 있습니다.<br>
						「${op:message('M00246')} 할인」란 우측에 고객님의 보유 ${op:message('M00246')} 및 최소 사용 가능 ${op:message('M00246')}가 표시되어있으니 확인해주세요. <br>
						  사용할 ${op:message('M00246')}를 입력해 주시거나 모두사용을 체크해주시면 ${op:message('M00246')}를 사용하실 수 있습니다.<br>
						  마지막으로 주문 최종확인화면에서 사용되는 ${op:message('M00246')}가 표시됩니다.
					</p>
				</div>
			</div><!--//guide_wrap E-->
			
			<div class="guide_wrap" id="guide_11">
				<span class="subject"><img src="/content/images/common/shopping_title11.jpg" alt="이용후기 적기"></span>
				<div class="guide_cont type02">
					<div class="photo_guide"><img src="/content/images/common/guide_reviews.jpg" alt=""></div>
					<p><strong>로그인한 후, 상품 페이지의 「이용후기 작성」버튼을 누릅니다.</strong></p>	 
					<div class="photo_guide"><img src="/content/images/common/guide_arrow.jpg" alt=""></div>
					<div class="photo_guide"><img src="/content/images/common/guide_reviews02.jpg" alt=""></div>	
					
					<p><strong>이 상품의「이용후기」화면이 나옵니다.</strong></p>
					<ul> 
						<li>- 이름 : 자신의 이름이 자동표시됩니다.（선택항목）<li>
						<li>- 평가 : (☆1개 - 5개)를 클릭해서 선택해주세요.（필수항목）<li>
						<li>- 제목 : 상품의 이름, 감상을 간단히 제목에 입력해주세요.（필수항목）<li>
						<li>- 내용 : 상품의 평가내용(리뷰)를 기입해주세요.（필수항목） <li>
						<li>- 마지막으로 「등록」버튼을 눌러서 내용을 저장해주세요.<li>
					</ul>
					<br>
					<p class="strong"><strong>주의</strong></p> 
					<ul class="strong"> 
						<li>- 올려주신 이용후기는 본 사이트에서 검토한 후에 사이트 내에 개재됩니다. 바로 공개되지 않는 점 양해바랍니다. </li>
						<li>- 이용후기의 내용에 따라서는 본 사이트의 판단에 의해 개재되지 않을 경우도 있습니다.  
						  	<span>이용후기 ${op:message('M00246')}는 이용후기 내용을 본 사이트에서 검토한 후에 부여됩니다. 바로 ${op:message('M00246')}가 올라가지 않습니다.</span>
						</li>
						<li>- 이용후기 내용에 따라서는 본 사이트의 판단에 의하여 이용후기 ${op:message('M00246')}가 부여되지 않을 경우도 있습니다. </li>
						<li>- 이용후기 ${op:message('M00246')}는 한명당 동일 상품에 한번에 한합니다. 동일 상품에 복수의 이용후기를 올리셔도 이용후기 ${op:message('M00246')}는 1회밖에 부여되지
							  <span>않으므로 양해바랍니다. </span>
							  <span>또한, 올려주신 이용후기에 관해서는 본 사이트에서 겸허히 받아들여 이후의 상품개량/개발, 사이트 운영 등에 
							  반영할 예정입니다. 고객님의 적극적인 이용후기를 기다립니다.</span>
						</li>  
					</ul>
				</div>
			</div><!--//guide_wrap E-->  
		</div><!--//shopping_guide E--> 
	</div><!--// sub_contents_min E-->
</div><!--// company E--> 	