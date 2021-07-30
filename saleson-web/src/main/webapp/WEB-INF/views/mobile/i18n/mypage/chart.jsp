<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" 		uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="sec" 	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions"%>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page"%>
<%@ taglib prefix="shop"	uri="/WEB-INF/tlds/shop" %>

<div id="wrap">
    <div class="title">
        <h2>매입단가표</h2>
        <span class="his_back"><a href="/" class="ir_pm">뒤로가기</a></span>
    </div>
    <!-- //title -->

    <div class="sort-product">
        <div class="chart-slide swiper-container">
            <div class="swiper-wrapper">
                <a onclick="location.href='/mypage/chart?itemLevel1=30000'" class="swiper-slide ico01 30000_level1">애플전기종</a>
                <a onclick="location.href='/mypage/chart?itemLevel1=50000'" class="swiper-slide ico02 50000_level1">CPU</a>
                <a onclick="location.href='/mypage/chart?itemLevel1=52000'" class="swiper-slide ico03 52000_level1">RAM</a>
                <a onclick="location.href='/mypage/chart?itemLevel1=56000'" class="swiper-slide ico04 56000_level1 58000_level1">HDD/SDD</a>
                <a onclick="location.href='/mypage/chart?itemLevel1=54000'" class="swiper-slide ico05 54000_level1">VGA</a>
                <a onclick="location.href='/mypage/chart?itemLevel1=60000'" class="swiper-slide ico06 60000_level1">BOARD</a>
                <a onclick="location.href='/mypage/chart?itemLevel1=70000'" class="swiper-slide ico07 70000_level1">LCD/LED</a>
            </div>
        </div>
        <div class="swiper-button-prev"></div>
        <div class="swiper-button-next"></div>
    </div> <!-- sort-product End -->
    <div class="relation-box">
        <c:if test="${!empty chartCategory2}">
            <c:forEach items="${chartCategory2}" var="category2" varStatus="i">
                <a class="${category2.itemLevel2}_level2" onclick="location.href='/mypage/chart?itemLevel2=${category2.itemLevel2}'" >${category2.levelName2}</a>
            </c:forEach>
        </c:if>
        <c:if test="${!empty HDD_SSDCategory1}">
            <c:forEach items="${HDD_SSDCategory1}" var="category2" varStatus="i">
                <a class="${category2.itemLevel1}_level2" onclick="location.href='/mypage/chart?itemLevel1=${category2.itemLevel1}'" >${category2.levelName1}</a>
            </c:forEach>
        </c:if>
    </div>
    <div class="item-wrap">
        <div class="item-box">
            <c:if test="${!empty chartCategory3}">
                <c:forEach items="${chartCategory3}" var="category3" varStatus="i">
                    <a onclick="location.href='/mypage/chart?itemLevel3=${category3.itemLevel3}'" class="${category3.itemLevel3}_level3">${category3.levelName3}</a>
                </c:forEach>
            </c:if>
            <c:if test="${!empty HDD_SSDCategory2}">
                <c:forEach items="${HDD_SSDCategory2}" var="category3" varStatus="i">
                    <c:choose>
                        <c:when test="${fn:substring(category3.itemLevel2, 0, 2) == '56'}">
                            <span><a  onclick="location.href='/mypage/chart?itemLevel1=56000&itemLevel2=${category3.itemLevel2}'" >${category3.levelName2}</a></span>
                        </c:when>
                        <c:otherwise>
                            <span><a  onclick="location.href='/mypage/chart?itemLevel1=58000&itemLevel2=${category3.itemLevel2}'" >${category3.levelName2}</a></span>
                        </c:otherwise>
                    </c:choose>
                </c:forEach>
            </c:if>
        </div>
    </div>
    <div class="purchase">
        <ul>
            <c:forEach items="${chartList}" var="chart" varStatus="i">
                <li>
                    <span class="txt">${chart.itemName}</span>
                    <span class="price">${chart.itemP}원</span>
                </li>
            </c:forEach>
        </ul>
    </div>
    <div class="search-wrap">
        <select>
            <option>상품명</option>
        </select>
        <input type="text" id="searchName">
        <button type="button" onclick="goSearch();">검색</button>
    </div>
</div>
<!-- //#wrap -->
<script>
    $(document).ready(function(){
        var itemLevel1 = ${itemLevel1} + '_level1';
        var itemLevel2 = ${itemLevel2} + '_level2';
        var itemLevel3 = ${itemLevel3} + '_level3';
        $('.'+itemLevel1).addClass("active");
        $('.'+itemLevel2).addClass("active");
        $('.'+itemLevel3).addClass("active");
    })

    function goSearch() {
        location.href="/mypage/chart?searchName=" + $('#searchName').val() + "";
    }

</script>