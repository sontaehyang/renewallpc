<%@page contentType="text/html;charset=UTF-8"%>
<%@page pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 	uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="op" 	uri="/WEB-INF/tlds/functions" %>
<%@ taglib prefix="fn" 	uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="page" tagdir="/WEB-INF/tags/page" %>
<%@ taglib prefix="module" 	tagdir="/WEB-INF/tags/modules"%>

<div class="btn_all list-footer">
    <div class="btn_left mb0">

    </div>
    <div class="btn_right mb0">
        <select class="select-pages">
            <option value="1">1</option>
        </select>
        /<span class="total-page-label">0</span>
        <button type="button" class="btn btn-default btn-sm list-prev"><</button>
        <button type="button" class="btn btn-default btn-sm list-next">></button>
    </div>
</div>