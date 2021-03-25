<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions" %>
<%@ taglib prefix="shop"	uri="/WEB-INF/tlds/shop" %>

<div>
	<p class="text-info text-sm">
		* 타입이 '웹링크(WL)' 경우 링크 1 -> MOBILE 링크, 링크 2 -> PC 링크 입니다.<br/>
		* 타입이 '앱링크(AL)' 경우 링크 1 -> IOS, 링크 2 -> ANDROID 입니다.<br/>
	</p>

	<table class="board_list_table">
		<colgroup>
			<col style="width: 50px;" />
			<col style="width: 200px;" />
			<col style="width: 20%;" />
			<col style="width: auto;" />
			<col style="width: auto;" />
			<col style="width: 50px;" />
		</colgroup>
		<thead>
			<tr>
				<th>No</th>
				<th>타입</th>
				<th>이름</th>
				<th>링크1</th>
				<th>링크2</th>
				<th></th>
			</tr>
		</thead>
		<tbody id="alimTalkTemplateButtonArea">
			<c:forEach var="button" items="${alimTalkButtons}" varStatus="i">
				<tr>
					<td>
						${i.count}
					</td>
					<td>
						<c:forEach var="code" items="${templateButtonTypeList}">
							<c:if test="${code.code eq button.type}">
								[${code.code}] ${code.title}
							</c:if>
						</c:forEach>
					</td>
					<td>
						${button.name}
					</td>
					<td>
						<c:set var="link1" value=""/>
						<c:choose>
							<c:when test='${button.type eq "WL"}'>
								<c:set var="link1" value="${button.linkMobile}"/>
							</c:when>
							<c:when test='${button.type eq "AL"}'>
								<c:set var="link1" value="${button.schemeIos}"/>
							</c:when>
						</c:choose>
						${link1}
					</td>
					<td>
						<c:set var="link2" value=""/>
						<c:choose>
							<c:when test='${button.type eq "WL"}'>
								<c:set var="link2" value="${button.linkPc}"/>
							</c:when>
							<c:when test='${button.type eq "AL"}'>
								<c:set var="link2" value="${button.schemeAndroid}"/>
							</c:when>
						</c:choose>
						${link2}
					</td>
				</tr>

			</c:forEach>
		</tbody>
	</table>
</div>