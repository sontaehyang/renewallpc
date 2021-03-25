<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" 		uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" 	uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions"%>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page"%>
<%@ taglib prefix="module" 	tagdir="/WEB-INF/tags/modules"%>

Form
<form:form modelAttribute="demoComplex" action="/demo/formComplex" method="post" enctype="multipart/form-data">

- 일련번호 : <form:input path="id"/> <br />
- 데모이름 : <form:input path="demoName"/> <br />

<h2>데모 객체</h2>
- 데모 아이디 : <form:input path="demo.demoId"/> <br />
- 데모 타이틀 : <form:input path="demo.title"/> <br />
- 데모 컨텐츠 : <form:input path="demo.content"/> <br />

<h2>데모 리스트 객체 []</h2>
<ul>
	<c:forEach items="${demoComplex.demos}" var="demoItem" varStatus="i">
	
	<li>
		<form:input path="demos[${i.index}].demoId"/>
		<form:input path="demos[${i.index}].title"/>
		<form:input path="demos[${i.index}].content"/>
	
	</li>
	</c:forEach>
	
	
</ul>


사진 : <a href="javascript:File.open('notice', 'asdfasdfasdf', 0, 3)" class="btn">이미지업로드</a><br />
<div id="TEMP_IMAGE_FILES">


</div>

<input type="submit" value="전송" />
</form:form>