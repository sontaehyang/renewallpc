<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" 		uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" 	uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions"%>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page"%>
<%@ taglib prefix="module" 	tagdir="/WEB-INF/tags/modules"%>

		<div class="breadcrumbs">
			<a href="/" class="home">エステ用品通販HOME</a>  &gt; <span>カテゴリ一覧</span>	
		</div>
		
		<div class="category">
		
			<c:forEach items="${shopContext.gnbCategories}" var="team" varStatus="i">
				<c:forEach items="${team.groups}" var="group" varStatus="j">	
				
					<div class="title title02"><p>${group.name}</p></div>
					<table>
						<caption>${group.name}</caption>
						<colgroup>
							<col style="width:199px;">
							<col style="width:auto;">
						</colgroup>
						<tbody>
						
							<c:forEach items="${group.categories}" var="category" varStatus="k">
								<tr>
									<th><a href="/categories/index/${category.url}">${category.name}</a></th>
									<td>
								
										<c:if test="${!empty category.childCategories}">
											<ul>
											
												<c:forEach items="${category.childCategories}" var="childCategories">
													<li><a href="/categories/index/${childCategories.url}">${childCategories.name}</a></li>
												</c:forEach>
											
											</ul>
										</c:if>
										
									</td>
								</tr>
							</c:forEach>
							
						</tbody>
					</table>

				</c:forEach>

			</c:forEach>
		</div>