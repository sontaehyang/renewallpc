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


	<div class="location">
		<a href="#"></a> &gt;  <a href="#"></a> &gt; <a href="#" class="on"></a>
	</div>

	<h3><span>화면관리</span></h3>
			
	<div class="cnt_area mt30">		
		<div id="tab_nav" class="tab_nav col6">
			<ul>
				<li><a href="/opmanager/categories-edit/index?type=main">${op:message('M01078')}</a></li> <!-- 메인 화면 -->
				<li><a href="/opmanager/categories-edit/index?type=categories">${op:message('M01079')}</a></li> <!-- 카테고리 화면 -->
				<li class="on"><a href="/opmanager/categories-edit/index?type=etc">${op:message('M00172')}</a></li> <!-- 기타 -->
			</ul>
		</div>
		
		<!-- 메인화면 시작-->
		<div id="activity3" class="tab_content on">
			<h4>| ${op:message('M01080')} <!-- 메인 구분 --></h4>
			<p> 
				<select name="code" title="메인 구분" >
					<option value="user" label="${op:message('M01512')}" rel="1" ${op:selected('user',categoriesEditParam.code)} /> <!-- 회원등록완료 -->
					<option value="order" label="${op:message('M00380')}" rel="1" ${op:selected('order',categoriesEditParam.code)} /><!-- 주문완료 -->
				</select>
			</p>
			
			<div class="screen_main" >						
				
				<div class="m_main_right">
					
					<div class="m_main_con_t" style="float: none; margin: auto;">
						<a href="/opmanager/categories-edit/popup/html-edit?editPosition=html1" class="edit_popup" rel="800*600" title="${op:message('M01554')}">
							<div class="m_con01_t m_con ${categoryEditPosition.html1}">${op:message('M01554')} <!-- 프리 HTML 영역 --></div>
						</a> 
					</div> <!-- // m_main_con_t  --> 
					
				</div> <!-- // m_main_right -->
				
			</div> <!-- // screen_main -->
				
		</div>
		<!-- 메인화면 끝-->
		
	</div><!--//cnt_area E-->
<script type="text/javascript">

	var code = '${categoriesEditParam.code}' != '' ? '${categoriesEditParam.code}' : 'user' ;
	var editKind = '${categoriesEditParam.editKind}' != '' ? '${categoriesEditParam.editKind}' : '1';
	
	
	
	$(function(){
		 
		$("select[name='code']").on("change",function(){
			location.href="/opmanager/categories-edit/index?type=etc&code="+$(this).val()+"&editKind="+$(this).find("option:selected").attr("rel");
		});
		
		$(".edit_popup").on("click",function(){
			var popupSize = $(this).attr("rel").split("*");
			Common.popup($(this).attr("href")+"&editTitle="+$(this).attr("title")+"&code="+code+"&editKind="+editKind,"promotion-banner",popupSize[0],popupSize[1]);
			return false;
		});
	});
	
	function categoryLayout(){
		location.reload();
	}
</script>
