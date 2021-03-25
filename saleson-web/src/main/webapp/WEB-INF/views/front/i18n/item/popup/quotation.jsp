<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" 		uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions"%>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page"%>
<%@ taglib prefix="shop"	uri="/WEB-INF/tlds/shop" %>

<style type="text/css">
    * {box-sizing:border-box;}
    body {margin:0;padding:0;background:#fff;font-family:'Dotum', '돋움', 'Malgun Gothic', '맑은고딕', sans-serif;font-size:12px;color:#666;line-height:20px;letter-spacing:-0.5px;}
    table {width:100%;border-collapse:collapse;table-layout:fixed;}
    td, th {word-break:break-all;}
    img {border:0;}

    .print_wrap {margin:0 auto;}
    .lay_tb > thead > tr {border-bottom:solid 2px #1f1f1f;}
    .lay_tb > thead td {padding:30px 0 15px 20px;}
    .lay_tb > thead td.top_r {padding:30px 20px 15px;color:#999;text-align:right;line-height:13px;}
    .lay_tb > thead td.top_r > p {display:block;margin-top:5px;}
    .lay_tb > thead td.top_r > button {display:inline-block;height:35px;padding:0 10px;background:#ff7101;border:none;font-weight:bold;color:#fff;text-align:center;}
    .logo {margin:0;text-align:left;}

    .lay_tb > tfoot td {padding:20px 20px 30px;background:#f7f7f7;color:#959595;text-align:center;line-height:18px;}
    .lay_tb > tfoot td > span {display:inline-block;margin-left:10px;}
    .copy {display:block;margin-top:10px;}

    .lay_tb > tbody > tr > td {padding:0 20px;vertical-align:top;}
    .top_tit {display:block;margin:40px 0;font-size:38px;font-weight:bold;color:#1f1f1f;text-align:center;line-height:45px;}

    .tb_ty {border-top:solid 2px #1f1f1f;}
    .tb_ty th {padding:10px;background:#f7f7f7;border-bottom:solid 1px #d9d9d9;font-weight:bold;color:#333;text-align:center;}
    .tb_ty td {padding:10px;border-bottom:solid 1px #d9d9d9;}
    .tb_ty td.stamp {text-align:center;vertical-align:middle;}
    .tb_ty td.stamp > img {width:100%;}
    .btm_txt {display:block;margin:10px 0 0;}

    .list {margin:30px 0 40px;border-top:solid 1px #1f1f1f;font-size:13px;text-align:center;}
    .list thead th {padding:10px 5px;background:#f7f7f7;border-right:solid 1px #d9d9d9;border-bottom:solid 1px #d9d9d9;font-weight:bold;color:#333;}
    .list tbody td {padding:10px;border-right:solid 1px #d9d9d9;border-bottom:solid 1px #d9d9d9;color:#333;}
    .list tbody td.tr {text-align:right;}
    .list tbody td.total {font-weight:bold;text-align:right;}
    .list tr > th:last-child, .list tr > td:last-child {border-right:none;}
    .list .prd {position:relative;padding:10px 10px 10px 80px;text-align:left;height:80px;}
    .list .prd .img {position:absolute;left:10px;top:10px;width:60px;height:60px;border:solid 1px #e6e6e6;}
    .list .prd .img > img {position:absolute;left:0;right:0;top:0;bottom:0;width:auto;max-width:100%;height:auto;max-height:100%;margin:auto;}
    .list .prd .info {margin:0;}
    .list .prd .info > dt {margin-bottom:5px;font-weight:bold;}
    .list .prd .info > dd {margin:0;font-size:12px;}
    .list .prd .info > dd > span {color:#ff7101;}
</style>

<div class="print_wrap">
    <table class="lay_tb">
        <colgroup><col span="1" style="width:35%;"><col span="1" style="width:*%;"></colgroup>
        <thead>
        <tr>
            <td>
                <h1 class="logo"><img src="/content/images/common/renewallpc_logo_eng.png" alt="Re.New.All PC 리뉴올 PC 되살리다.새것처럼.모든것을."></h1>
            </td>
            <td class="top_r">
                <button type="button" onclick="window.print()">인쇄하기</button>
                <p>https://renewallpc.co.kr</p>
            </td>
        </tr>
        </thead>
            <tfoot>
                <tr>
                    <td colspan="2">
                        ${shopContext.config.companyName}
                        <span>대표이사. ${shopContext.config.bossName}</span>
                        <span>대표전화. ${shopContext.config.telNumber}</span>
                        <span>팩스. ${shopContext.config.faxNumber}</span><br>
                        주소. ${shopContext.config.address}&nbsp;${shopContext.config.addressDetail}
                        <span>사업자등록번호. ${shopContext.config.companyNumber}</span><span>통신판매업신고. ${shopContext.config.mailOrderNumber}</span><br>
                        개인정보관리책임자. ${shopContext.config.adminName}<span>이메일. ${shopContext.config.adminEmail}</span>
                        <p class="copy">COPYRIGHT © RE.NEW.ALL PC. ALL RIGHT RESERVED.</p>
                    </td>
                </tr>
            </tfoot>
        <tbody>
        <tr>
            <td colspan="2">
                <h2 class="top_tit">견 적 서</h2>
            </td>
        </tr>
        <tr>
            <td style="padding:0 10px 0 20px;">
                <table class="tb_ty">
                    <colgroup><col style="width:90px;"><col style="width:*;"></colgroup>
                    <tbody>
                        <tr>
                            <th scope="row">담당자</th>
                            <td>
                                <c:if test="${requestContext.userLogin}">${requestContext.user.userName} 님 귀하</c:if>
                                <c:if test="${!requestContext.userLogin}">-</c:if>
                            </td>
                        </tr>
                        <tr>
                            <th scope="row">견적일</th>
                            <td>${op:date(today)}</td>
                        </tr>
                        <tr>
                            <th scope="row">결제방법</th>
                            <td>현금 / 신용카드</td>
                        </tr>
                        <tr>
                            <th scope="row">비고</th>
                            <td>&nbsp;</td>
                        </tr>
                        <tr>
                            <th scope="row">견적금액</th>
                            <td>${op:numberFormat(buy.orderPrice.orderPayAmount)}원</td>
                        </tr>
                    </tbody>
                </table>
                <p class="btm_txt">아래와 같이 견적합니다.</p>
            </td>
            <td style="padding:0 20px 0 10px;">
                <table class="tb_ty">
                    <colgroup>
                        <col style="width:40px;">
                        <col style="width:90px;">
                        <col style="width:*;">
                        <col style="width:90px;">
                        <col style="width:*;">
                        <col style="width:90px;">
                    </colgroup>
                    <tbody>
                        <tr>
                            <th scope="row" rowspan="5" style="border-right:solid 1px #e1e1e1;">공급자</th>
                            <th scope="row">상호</th>
                            <td>${shopContext.config.companyName}</td>
                            <th scope="row">등록번호</th>
                            <td colspan="2">${shopContext.config.companyNumber}</td>
                        </tr>
                        <tr>
                            <th scope="row">대표자</th>
                            <td colspan="3">${shopContext.config.bossName}</td>
                            <td rowspan="2" class="stamp"><img src="/content/images/common/renewallpc_stamp.png" alt="리뉴올PC 직인"></td>
                        </tr>
                        <tr>
                            <th scope="row">주소</th>
                            <td colspan="3">${shopContext.config.address}&nbsp;${shopContext.config.addressDetail}</td>
                        </tr>
                        <tr>
                            <th scope="row">업태</th>
                            <td>${shopContext.config.categoryType}</td>
                            <th scope="row">종목</th>
                            <td colspan="2">${shopContext.config.businessType}</td>
                        </tr>
                        <tr>
                            <th scope="row">전화</th>
                            <td>${shopContext.config.counselTelNumber}</td>
                            <th scope="row">팩스</th>
                            <td colspan="2">${shopContext.config.faxNumber}</td>
                        </tr>
                    </tbody>
                </table>
            </td>
        </tr>
        <tr>
            <td colspan="2">
                <table class="list">
                    <colgroup>
                        <col style="width:5%;">
                        <col style="width:12%;">
                        <col style="width:*%;">
                        <col style="width:8%;">
                        <col span="3" style="width:12%;">
                    </colgroup>
                    <thead>
                        <tr>
                            <th scope="col">No</th>
                            <th scope="col">상품코드</th>
                            <th scope="col">상품명/옵션정보</th>
                            <th scope="col">수량</th>
                            <th scope="col">가격</th>
                            <th scope="col">합계금액</th>
                            <th scope="col">비고</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${buy.receivers}" var="receiver">
                            <c:forEach items="${receiver.items}" var="buyItem" varStatus="i">
                                <c:set var="totalItemSaleAmount" value="${buyItem.itemPrice.itemSaleAmount}" />
                                <c:set var="totalSaleAmount" value="${buyItem.itemPrice.saleAmount}" />

                                <c:forEach items="${buyItem.additionItemList}" var="addition">
                                    <c:set var="totalItemSaleAmount">${totalItemSaleAmount + addition.itemPrice.itemSaleAmount}</c:set>
                                    <c:set var="totalSaleAmount">${totalSaleAmount + addition.itemPrice.saleAmount}</c:set>
                                </c:forEach>

                                <tr>
                                    <td>${i.count}</td>
                                    <td>${buyItem.item.itemUserCode}</td>
                                    <td class="prd">
                                        <div class="img">
                                            <img src="${shop:loadImageBySrc(buyItem.item.imageSrc, 'XS')}" alt="${buyItem.item.itemName}">
                                        </div>
                                        <dl class="info">
                                            <dt>${buyItem.item.itemName}</dt>
                                            <c:set var="options" value="${fn:split(buyItem.options, '^^^') }"/>
                                            <c:forEach items="${options}" var="option">
                                                <dd>
                                                    <c:set var="optionTexts" value="${op:delimitedListToStringArray(option, '||') }"/>
                                                    <c:forEach items="${optionTexts}" var="optionText" varStatus="j">
                                                        <c:if test="${j.index > 0}">
                                                            <c:choose>
                                                                <c:when test="${fn:length(optionTexts) == j.index + 4}">
                                                                    <c:if test="${optionText != 0}">
                                                                        (${optionText > 0 ? "+" : ""}${op:numberFormat(optionText)}원)
                                                                    </c:if>
                                                                </c:when>
                                                                <c:when test="${fn:length(optionTexts) - 3 > j.index}">
                                                                    <c:choose>
                                                                        <c:when test="${j.index % 2 == 0}">
                                                                            ${optionText}
                                                                            <c:if test="${fn:length(optionTexts) > j.index + 5}"> / </c:if>
                                                                        </c:when>
                                                                        <c:otherwise>
                                                                            <span>${optionText} :</span>
                                                                        </c:otherwise>
                                                                    </c:choose>
                                                                </c:when>
                                                            </c:choose>
                                                        </c:if>
                                                    </c:forEach>
                                                </dd>
                                            </c:forEach>
                                            <c:forEach items="${buyItem.additionItemList}" var="additionItem">
                                                <dd>
                                                    <span>추가상품 :</span> ${additionItem.item.itemName}
                                                    ${additionItem.itemPrice.quantity}개
                                                    <c:if test="${additionItem.itemPrice.salePrice > 0}">
                                                        (+${op:numberFormat(additionItem.itemPrice.salePrice * additionItem.itemPrice.quantity)}원)
                                                    </c:if>
                                                </dd>
                                            </c:forEach>
                                        </dl>
                                    </td>
                                    <td>${buyItem.itemPrice.quantity}</td>
                                    <td class="tr">${op:numberFormat(totalItemSaleAmount)}원</td>
                                    <td class="total">${op:numberFormat(totalSaleAmount)}원</td>
                                    <td>&nbsp;</td>
                                </tr>
                            </c:forEach>
                        </c:forEach>
                    </tbody>
                </table>
            </td>
        </tr>
        </tbody>
    </table>
</div><!--// print_wrap -->