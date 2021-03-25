<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page"%>
<%@ taglib prefix="fmt" 	uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="op" 	uri="/WEB-INF/tlds/functions.tld"%>

<table class="opmanager-list-table margin-top-5" cellspacing="0" cellpadding="0">
	<caption>리스트 테이블</caption>
	<thead>
		<tr>
			<th width="60">순번</th>
			<th width="120"> <a href="javascript:fn_Sort('REMOTE_IP')">IP</a></th>
			<th> <a href="javascript:fn_Sort('REFERER')">접속경로</a></th>
			<th width="70">브라우저</th>
			<th width="70">OS</th>
			<th width="120"> <a href="javascript:fn_Sort('VISIT_DATE')">일시</a></th>
		</tr>
	</thead>
	<tbody>
	
	<c:if test="${!empty list}">
		<c:forEach items="${list}" var="visit" varStatus="i">
        <tr>
            <td>${pagination.itemNumber - i.count}</td>
            <td style="font-size:9pt">${visit.remoteIp}</td>
            <td style="text-align:left; padding-left:10px"><a href="${visit.referer}" target="_blank">${visit.referer}</a></td>
            <td>${visit.browser}</td>
 
            <td style="font-size:9pt">${visit.os}</td>
            <td>${visit.visitDate} ${visit.visitTime}</td>
        </tr>
 		</c:forEach>
 	</c:if>
	</tbody>
</table>
<br />
<page:pagination />
