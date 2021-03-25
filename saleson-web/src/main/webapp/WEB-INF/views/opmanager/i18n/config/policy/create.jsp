<%@page contentType="text/html;charset=UTF-8"%>
<%@page pageEncoding="UTF-8"%>
<%@ page session="false" %>
<%@ taglib prefix="c" 	uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="op" 	uri="/WEB-INF/tlds/functions" %>
<%@ taglib prefix="fn" 	uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="page" tagdir="/WEB-INF/tags/page" %>
<%@ taglib prefix="module" 	tagdir="/WEB-INF/tags/modules"%>


<div class="location">
	<a href="#"></a> &gt;  <a href="#"></a> &gt; <a href="#" class="on"></a>
</div>

<c:set var="typeString" value=""/>

<c:choose>
	<c:when test="${type eq 'agreement'}">
		<c:set var="typeString" value="${op:message('M00207')}"/>
	</c:when>
	<c:when test="${type eq 'protect-policy'}">
		<c:set var="typeString" value="${op:message('M00209')}"/>
	</c:when>
	<c:when test="${type eq 'trader-raw'}">
		<form:textarea path="protectPolicy" cols="100" rows="20" style="width:100%;height:300px;" class="" title="${op:message('M00208')}"/>
	</c:when>
</c:choose>

<h3><span>${typeString}</span></h3>
<ul class="mail_list">
	<li><a href="/opmanager/config/policy/edit/agreement">${op:message('M00207')}</a></li>
	<li><a href="/opmanager/config/policy/edit/protect-policy">${op:message('M00209')} </a></li>


	<!-- li><a href="/opmanager/config/policy/edit/user-html-edit">${op:message('M01479')}</a></li -->
</ul>

<c:set var="editScript"></c:set>



<h3><span>${typeString}</span></h3>
<div class="board_write mt20">
	<form:form modelAttribute="policy" method="post">
		<table class="board_write_table" cellpadding="0" cellspacing="0" summary="${op:message('M00206')}">
			<caption>${op:message('M00206')}</caption>
			<colgroup>
				<col style="width: 200px;">
				<col style="width: auto;">
			</colgroup>
			<tbody>
			<tr>
				<td class="label">
					<div>
						<select id="policyList">
							<c:forEach var="p" items="${policyList}">
								<option value="${p.policyId}" ${op:selected(p.policyId, policy.policyId)}>${op:datetime(p.createdDate)}</option>
							</c:forEach>
						</select>
					</div>
				</td>
				<td>
					<div>
						<form:textarea path="content" cols="100" rows="20" style="width:100%;height:300px;" class="" title="내용" />
					</div>
				</td>
			</tr>
			</tbody>
		</table>
		<p class="btn_center">
			<button type="submit" class="btn btn-active">${op:message('M00101')}</button>
		</p>
	</form:form>
</div><!--//board_write E-->

<module:smarteditorInit />
<module:smarteditor id="content" />

<script type="text/javascript">
    $(function() {
        // validator
        $('#policy').validator(
            function() {
                return Common.getEditorContent("content", true);
            });

        $('#policyList').on('change', function() {
            location.href = '/opmanager/config/policy/edit/${type}?id='+$(this).val();
        });
    });

</script>		
		