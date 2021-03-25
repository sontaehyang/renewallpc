<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions" %>
<%@ taglib prefix="module" 	tagdir="/WEB-INF/tags/modules" %>
<%@ taglib prefix="shop"	uri="/WEB-INF/tlds/shop" %>

												<%-- 
												판매가에서 <form:input path="sellerGivePoint" class="_number" /> P 적립
												--%>


												
												<input type="hidden" name="pointStartDate" />
												<input type="hidden" name="pointStartTime" />
												<input type="hidden" name="pointEndDate" />
												<input type="hidden" name="pointEndTime" />
												<input type="hidden" name="pointRepeatDay" />
												
												판매가에서 
												<input type="text" name="point" maxlength="6" value="${pointConfig.point}" class="required-seller-point _min_0 _number_comma amount" title="${op:message('M00246')}" />
												<select name="pointType">
													<option value="2" ${op:selected('2',  pointConfig.pointType)}>P</option>
													<option value="1" ${op:selected('1',  pointConfig.pointType)}>%</option>
												</select>
												적립 