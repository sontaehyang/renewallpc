<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" 		uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" 	uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="spring" 	uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions"%>
<%@ taglib prefix="shop"	uri="/WEB-INF/tlds/shop" %>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>

<div class="board_write sortable_display_image">

	<h2>${template.title} / 롤링여부 (${template.rollingYN})</h2>

	<c:set var="displayImageSize" value="0"/>
	<c:set var="displayImageIndex" value="0"/>
	<c:set var="imageSize" value="(${template.width} x ${template.height})" />

	<c:forEach items="${displayImageList}" var="image" varStatus="i">
		<c:if test="${templateIndex == image.displaySubCode}">
			<table class="board_write_table" summary="">
				<caption></caption>
				<colgroup>
					<col style="width: 20%;" />
					<col style="width: auto;" />
				</colgroup>
				<tbody>
				<tr class="board_write_table">
					<td class="label">${op:message('M01130')} <!-- 연결 URL(링크) --> ${displayImageIndex + 1}</td>
					<td>
						<div>
							<input name="displayUrls[${templateIndex}]" type="text" class="w478" title="연결 URL(링크)" value="${image.displayUrl}" />
						</div>
					</td>
				</tr>
				<tr>
					<td class="label">내용 ${displayImageSize + 1} </td>
					<td>
						<div>
							<input name="displayContents[${templateIndex}]" type="text" class="w478" title="이미지 ALT" value="${image.displayContent}" />
						</div>
					</td>
				</tr>
				<tr>
					<td class="label">${op:message('M00752')} <!-- 이미지 -->${displayImageIndex + 1} ${op:message('M01297')} <!-- 업로드 --> <br>${imageSize}</td>
					<td>
						<div>
							<c:choose>
								<c:when test="${empty image.displayImage}">
									<input name="displayImages[${templateIndex}]" type="file" title="이미지 업로드" />
									<br />
									<input name="fileNames[${templateIndex}]" type="hidden" value="" />
								</c:when>
								<c:otherwise>
									<img src="${requestContext.uploadBaseFolder}/display/${image.displayGroupCode}/${image.displaySubCode}/thumb/${image.displayImage}" alt="banner" style="width: ${template.thumbWidth }px; height: ${template.thumbHeight}px;"/>
									${image.displayImage} <a href="javascript:;" class="table_btn file_delete" id="image-${image.displaySubCode}-${image.viewTarget}-${image.ordering}" title="파일 삭제">${op:message('M01511')}</a> <!-- 파일 삭제 -->
									<br />
									<input name="displayImages[${templateIndex}]" type="file" title="이미지${i.count} 업로드" />
									<input name="fileNames[${templateIndex}]" type="hidden" value="${image.displayImage}" />
								</c:otherwise>
							</c:choose>

						</div>
					</td>
				</tr>
				<tr>
					<td class="label">배경색 ${displayImageIndex + 1}</td>
					<td>
						<div>
							<input name="displayColors[${templateIndex}]" type="text" class="colorpicker" title="배경색" value="${image.displayColor}" maxlength="7" />
						</div>
					</td>
				</tr>

				</tbody>
			</table>

			<c:set var="displayImageIndex" value="${displayImageIndex + 1}"/>
			<c:set var="displayImageSize" value="${displayImageSize + 1}"/>
		</c:if>
	</c:forEach>

	<c:if test="${displayImageSize < template.count}">
		<c:forEach begin="0" end="${(template.count - 1) - displayImageSize}"  var="list" varStatus="i">
			<c:set var="addTemplateIndex" value="${(i.index + displayImageSize)}"/>

			<table class="board_write_table" summary="">
				<caption></caption>
				<colgroup>
					<col style="width: 20%;" />
					<col style="width: auto;" />
				</colgroup>
				<tbody>

				<tr class="board_write_table">
					<td class="label">${op:message('M01130')} <!-- 연결 URL(링크) --> ${addTemplateIndex + 1} </td>
					<td>
						<div>
							<input name="displayUrls[${templateIndex}]" type="text" class="w478" title="연결 URL(링크)" value="" />
						</div>
					</td>
				</tr>
				<tr>
					<td class="label">내용 ${addTemplateIndex + 1}</td>
					<td>
						<div>
							<input name="displayContents[${templateIndex}]" type="text" class="w478" title="이미지 ALT" value="" />
						</div>
					</td>
				</tr>
				<tr>
					<td class="label">${op:message('M00752')} <!-- 이미지 -->${addTemplateIndex + 1} ${op:message('M01297')} <!-- 업로드 --> <br>${imageSize}</td>
					<td>
						<div>
							<input name="displayImages[${templateIndex}]" type="file" title="이미지 업로드" />
							<br />
							<input name="fileNames[${templateIndex}]" type="hidden" value="" />
						</div>
					</td>
				</tr>
				<tr>
					<td class="label">배경색 ${addTemplateIndex + 1}</td>
					<td>
						<div>
							<input name="displayColors[${templateIndex}]" type="text" class="colorpicker" title="배경색" maxlength="7" />
						</div>
					</td>

				</tbody>
			</table>

		</c:forEach>
	</c:if>
</div>