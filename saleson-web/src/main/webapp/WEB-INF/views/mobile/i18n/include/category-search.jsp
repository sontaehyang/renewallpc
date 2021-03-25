<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" 		uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" 	uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions"%>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page"%>
<%@ taglib prefix="module" 	tagdir="/WEB-INF/tags/modules"%>


			<div class="category_search">
				<p class="category_search_title">${op:message('M01450')}</p> <!-- 카테고리에서 찾기 -->
				<ul> 
					<li><a href="/m/categories/index/esthetic"><img src="/content/mobile/images/common/category01.gif" alt="エステ" /></a></li>
					<li><a href="/m/categories/index/nail"><img src="/content/mobile/images/common/category02.gif" alt="ネイル" /></a></li>
					<li><a href="/m/categories/index/matsuge_extension"><img src="/content/mobile/images/common/category03.gif" alt="まつげエクステ" /></a></li>
					<li><a href="/m/categories/index/hair"><img src="/content/mobile/images/common/category04.gif" alt="理美容" /></a></li>
				</ul>
			</div>

