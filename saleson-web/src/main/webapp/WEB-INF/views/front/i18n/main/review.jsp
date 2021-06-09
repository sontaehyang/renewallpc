<%@page contentType="text/html;charset=UTF-8"%>
<%@page pageEncoding="UTF-8"%>
<%@ page session="false" %>
<%@ taglib prefix="c" 	uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" 	uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="op" 	uri="/WEB-INF/tlds/functions" %>
<%@ taglib prefix="fn" 	uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="board" 	tagdir="/WEB-INF/tags/board"%>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page" %>
<%@ taglib prefix="shop" 	uri="/WEB-INF/tlds/shop" %>

<c:if test="${!empty reviewList}">
    <section class="msec06">
        <div class="inner">
            <p class="sec_tit">고객님들의 솔직한 리뷰</p>
            <ul class="mreview_list">
                <c:forEach items="${reviewList}" var="review" varStatus="i">
                        <li class="${i.index} review_list">
                            <a href="${review.item.link}" class="imgs">
                                <p><img src="${shop:loadImageBySrc(review.item.imageSrc, 'XS')}" alt="${review.item.itemName}"></p>
                                <span>상품보기</span>
                            </a>
                            <div class="info">
                                <div class="star_rating">
                                    <span style="width:${review.score * 20}%;"></span>
                                    <span class="point">${review.score}</span>
                                </div><!--// star_rating -->
                                <a href="${review.item.link}" class="tit">${review.subject}</a>
                                <p class="txt">${op:nl2br(review.content)}</p>
                                <dl class="btm">
                                    <dt>${review.maskUsername}</dt>
                                    <dd>${op:date(review.createdDate)}</dd>
                                </dl>
                            </div><!--// info -->
                        </li>
                </c:forEach>
            </ul><!--// mreview_list -->
            <button type="button" id="review_more" class="btn_mreview_more">더보기</button>
        </div><!--// inner -->
    </section><!--// msec06 -->
</c:if>

<script>
    $(document).ready(function (){
        $('.review_list').hide();
        // var review_count = $('.review_list').length;
        // 최초 4개만 보이게 설정
        var review_count = 4;
        for( i=0; i < review_count; i++ ) {
            $('.'+ i ).show();
        }
    });

    $('#review_more').click(function(){
        $('.review_list').show();
        $('#review_more').hide();
    });
</script>
