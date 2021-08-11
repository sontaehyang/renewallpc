<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" 		uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="sec" 	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions"%>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page"%>
<%@ taglib prefix="shop"	uri="/WEB-INF/tlds/shop" %>

<div class="inner">
<%--    <div id="locate"></div>--%>
    <div class="img-wrap">
        <img src="/content/images/common/purchase_banner.png" alt="정직한 매입으로 최선을 다하겠습니다.">
    </div>
    <ul class="sort-category">
        <li class="30000" onclick="location.href='/mypage/chart?itemLevel1=30000'">
            <a href="/mypage/chart?itemLevel1=30000">
                <img src="/content/images/common/com_icon8_01.png">
                <span>애플전기종</span>
            </a>
        </li>
        <li class="50000" onclick="location.href='/mypage/chart?itemLevel1=50000'"/>
            <a href="/mypage/chart?itemLevel1=50000">
                <img src="/content/images/common/com_icon3.png">
                <span class="selected">CPU</span>
            </a>
        </li>
        <li class="52000" onclick="location.href='/mypage/chart?itemLevel1=52000'">
            <a href="/mypage/chart?itemLevel1=52000">
                <img src="/content/images/common/com_icon4.png">
                <span>RAM</span>
            </a>
        </li>
        <li class="56000 58000" onclick="location.href='/mypage/chart?itemLevel1=56000'">
            <a href="/mypage/chart?itemLevel1=56000">
                <img src="/content/images/common/com_icon5.png">
                <span>HDD/SSD</span>
            </a>
        </li>
        <li class="54000" onclick="location.href='/mypage/chart?itemLevel1=54000'">
            <a href="/mypage/chart?itemLevel1=54000">
                <img src="/content/images/common/com_icon6.png">
                <span>VGA</span>
            </a>
        </li>
        <li  class="60000" onclick="location.href='/mypage/chart?itemLevel1=60000'">
            <a href="/mypage/chart?itemLevel1=60000">
                <img src="/content/images/common/com_icon7.png">
                <span>BOARD</span>
            </a>
        </li>
        <li class="70000" onclick="location.href='/mypage/chart?itemLevel1=70000'">
            <a href="/chart?itemLevel1=70000">
                <img src="/content/images/common/com_icon2.png">
                <span>LCD/LED</span>
            </a>
        </li>
    </ul>
    <div class="sort-product">
        <c:if test="${!empty chartCategory2}">
            <c:forEach items="${chartCategory2}" var="category2" varStatus="i">
                <a class="${category2.itemLevel2}" onclick="location.href='/mypage/chart?itemLevel2=${category2.itemLevel2}'" >${category2.levelName2}</a>
            </c:forEach>
        </c:if>
        <c:if test="${!empty HDD_SSDCategory1}">
            <c:forEach items="${HDD_SSDCategory1}" var="category2" varStatus="i">
                <a class="${category2.itemLevel1}" onclick="location.href='/mypage/chart?itemLevel1=${category2.itemLevel1}'" >${category2.levelName1}</a>
            </c:forEach>
        </c:if>


    </div>
    <div class="product-list">
        <c:if test="${!empty chartCategory3}">
            <c:forEach items="${chartCategory3}" var="category3" varStatus="i">
                <span><a href="/mypage/chart?itemLevel3=${category3.itemLevel3}">${category3.levelName3}</a></span>
            </c:forEach>
        </c:if>
        <c:if test="${!empty HDD_SSDCategory2}">
            <c:forEach items="${HDD_SSDCategory2}" var="category3" varStatus="i">
                <c:choose>
                    <c:when test="${fn:substring(category3.itemLevel2, 0, 2) == '56'}">
                        <span><a  href="/mypage/chart?itemLevel1=56000&itemLevel2=${category3.itemLevel2}" >${category3.levelName2}</a></span>
                    </c:when>
                    <c:otherwise>
                        <span><a  href="/mypage/chart?itemLevel1=58000&itemLevel2=${category3.itemLevel2}" >${category3.levelName2}</a></span>
                    </c:otherwise>
                </c:choose>
            </c:forEach>
        </c:if>
    </div>
    <div class="price-table-wrap">
        <table>
            <colgroup>
                <col width="160px">
                <col width="*">
                <col width="150px">
            </colgroup>
            <tbody>
            <tr>
                <th>분류</th>
                <th>상품</th>
                <th>가격</th>
            </tr>
            <c:forEach items="${chartList}" var="chart" varStatus="i">
                <c:if test="${i.index%2 == 0}">
                    <tr class="table-even">
                </c:if>
                <c:if test="${i.index%2 == 1}">
                    <tr class="table-odd">
                </c:if>
                    <c:choose>
                        <c:when test="${!empty chart.levelName3}">
                            <td class="ta-center">[<span>${chart.levelName3}</span>]</td>
                        </c:when>
                        <c:otherwise>
                            <td class="ta-center">[<span>${chart.levelName2}</span>]</td>
                        </c:otherwise>
                    </c:choose>
                    <td class="txt">${chart.itemName}</td>
                    <td class="ta-right"><span>${op:numberFormat(chart.itemP)} </span>원</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
        <c:if test="${fn:length(chartList) == 0}">
            <p style="text-align: center;font-size:medium;margin:30px">조회된 검색 결과가 없습니다.</p>
        </c:if>

    </div>
    <div class="search-wrap">
        <select name="" id="">
            <option value="">상품명</option>
        </select>
        <input type="text" id="searchName">
        <button type="button" onclick="goSearch();">검색</button>
    </div>
</div>

<script>
    $(document).ready(function(){
        var itemLevel1 = ${itemLevel1};
        var itemLevel2 = ${itemLevel2};
        $('.'+itemLevel1).addClass("active");
        $('.'+itemLevel2).addClass("active");
    })

    function goSearch() {
        location.href="/mypage/chart?searchName=" + $('#searchName').val() + "";
    }

</script>

