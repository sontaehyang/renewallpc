<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page" %>

<div class="inner">
	<div class="location_area">
		<div class="breadcrumbs">
			<a href="#" class="home"><span class="hide">home</span></a>
			<a href="#">회사소개</a>  
			<span>회사소개</span>
		</div>
	</div><!-- // location_area E -->  
 	
 	<div id="contents"> 
	 	<jsp:include page="/WEB-INF/views/layouts/front/inc_lnb_intro.jsp" />
	 	<div class="contents_inner mt15">
			<img src="/content/images/common/company.jpg" alt="회사소개" class="company_img">
	</div>	<!-- // contents E -->	
</div><!-- // inner E -->

<page:javascript> 
		
<script type="text/javascript" src="http://openapi.map.naver.com/openapi/v2/maps.js?clientId=C0Iz8a7z762tKPQR1FoP"></script>
<script type="text/javascript">
	var oPoint = new nhn.api.map.LatLng(37.5099791, 127.041632);
	nhn.api.map.setDefaultPoint('LatLng');
	oMap = new nhn.api.map.Map('testMap' ,{
		point : oPoint,
		zoom : 20,
		enableWheelZoom : true,
		enableDragPan : true,
		enableDblClickZoom : false,
		mapMode : 0,
		activateTrafficMap : false,
		activateBicycleMap : false,
		minMaxLevel : [ 1, 12 ],
		size : new nhn.api.map.Size(1022, 488)
	});
	// 줌 컨트롤러
	var oSlider = new nhn.api.map.ZoomControl();
	oMap.addControl(oSlider);
	oSlider.setPosition({ top:15, left:15 });
	
	//아래는 위에서 지정한 좌표에 마커를 표시하는 소스 입니다.
	var oSize = new nhn.api.map.Size(28, 37);
	var oOffset = new nhn.api.map.Size(14, 37);
	var oIcon = new nhn.api.map.Icon('http://static.naver.com/maps2/icons/pin_spot2.png', oSize, oOffset);
	
	//icon 이미지를 바꿔서 사용할 수 있습니다.
	var oMarker = new nhn.api.map.Marker(oIcon, { title : '온라인파워스' }); 
	oMarker.setPoint(oPoint);
	oMap.addOverlay(oMarker);
	
	// 마커라벨 표시
	var oLabel1 = new nhn.api.map.MarkerLabel(); // 마커 라벨 선언
	oMap.addOverlay(oLabel1);// 마커 라벨 지도에 추가. 기본은 라벨이 보이지 않는 상태로 추가됨
	oLabel1.setVisible(true, oMarker);// 마커 라벨 보이기 
</script>
</page:javascript>