<%@ tag pageEncoding="utf-8" %>
<%@ taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="op" 		uri="/WEB-INF/tlds/functions"%>

<!-- [KakaoPay] Parameters - START -->
<div style="display: none">
<%-- 
결제 변수 목록(매뉴얼 참조)
--%>

<input type="text" name="PayMethod" value="KAKAOPAY" />							<%-- (*)결제수단 : KAKAOPAY 고정 --%>
<input type="text" name="GoodsName" class="kakaopay-productName" />				<%-- (*)상품명 --%>
<input type="text" name="Amt" class="kakaopay-amount" />						<%-- (*)상품가격 --%>
<input type="text" name="SupplyAmt" class="kakaopay-supplyAmt" value="0"/>		<%-- 공급가액 --%>
<input type="text" name="GoodsVat" class="kakaopay-goodsVat" value="0"/>		<%-- 부가세 --%>
<input type="text" name="ServiceAmt" class="kakaopay-serviceAmt" value="0"/>	<%-- 봉사료 --%>
<input type="text" name="GoodsCnt" />											<%-- (*)상품갯수 --%>
<input type="text" name="MID" class="kakaopay-merchantID" />					<%-- (*)가맹점ID --%>
<input type="text" name="AuthFlg" value="10"/>									<%-- (*)인증플래그 : 10 고정 --%>
<input type="text" name="EdiDate" id="EdiDate" value=""/>						<%-- (*)EdiDate --%>
<input type="text" name="EncryptData" id="EncryptData" value=""/>				<%-- (*)EncryptData --%>
<input type="text" name="BuyerEmail" />											<%-- 구매자 이메일 --%>
<input type="text" name="BuyerName" />											<%-- (*)구매자명 --%>

<%-- 
인증 변수 목록(매뉴얼 참조)
--%>
<input type="text" name="offerPeriodFlag" />	<%-- 상품제공기간 플래그 --%>
<input type="text" name="offerPeriod" />	<%-- 상품제공기간 --%>
<input type="text" name="certifiedFlag" value="CN"/>	<%-- (*)인증구분 : CN 고정 --%>
<input type="text" name="currency" value="KRW"/>	<%-- (*)거래통화 : KRW 고정 --%>
<input type="text" name="merchantEncKey" value="${op:property('kakaopay.merchant.enc.key')}"/>	<%-- (*)가맹점 암호화키 --%>
<input type="text" name="merchantHashKey" value="${op:property('kakaopay.merchant.hash.key')}"/>	<%-- (*)가맹점 해쉬키 --%>
<input type="text" name="requestDealApproveUrl" value=""/>	<%-- (*)TXN_ID 요청URL --%>
<input type="text" name="prType" />	<%-- (*)결제요청타입 : MPM : 모바일결제, WPM : PC결제 --%>
<input type="text" name="channelType" />	<%-- (*)채널타입 : 2: 모바일결제, 4: PC결제 --%>
<input type="text" name="merchantTxnNum" />	<%-- (*)가맹점 거래번호 --%>


<%-- 
인증 파라미터 중 할부결제시 사용하는 파라미터 목록
파라미터 입력형태는 매뉴얼 참조
--%>
<input type="text" name="possiCard" value="" />		<%-- 카드선택 : ex) 06--%>
<input type="text" name="fixedInt" value="" />		<%-- 할부개월 : ex) 03--%>
<input type="text" name="maxInt" value="" />		<%-- 최대할부개월 : ex) 24 --%>
<input type="text" name="noIntYN" value="N" />		<%-- 무이자 사용여부 --%>
<input type="text" name="noIntOpt" value="" />		<%-- 무이자옵션 : ex) CC01-02:03:05 --%>
<input type="text" name="pointUseYn" class="kakaopay-pointUseYN" value="N" />	<%-- 카드사포인트사용여부 --%>
<input type="text" name="blockCard" value="" />		<%-- 금지카드설정 : ex) 01:04:11 --%>
<input type="text" name="blockBin" value="" />		<%-- 특정제한카드 BIN --%>

<%--
	getTxnId 응답
--%>
<input id="resultCode" type="text" />				<%-- resultcode--%>
<input id="resultMsg" type="text" />				<%-- resultmsg--%>
<input id="txnId" type="text" />					<%-- txnId--%>
<input id="prDt" type="text" />						<%-- prDt--%>

   
<%--   
	DLP호출에 대한 응답
--%>
<input type="text" name="SPU" value=""/>			<%-- SPU--%>
<input type="text" name="SPU_SIGN_TOKEN" value=""/>	<%-- SPU_SIGN_TOKEN--%>
<input type="text" name="MPAY_PUB" value=""/>		<%-- MPAY_PUB--%>
<input type="text" name="NON_REP_TOKEN" value=""/>	<%-- NON_REP_TOKEN--%>

<%--
결제 요청 샘플 페이지 
<!-- 결제 파라미터 목록 -->
        <b>결제 변수 목록(매뉴얼 참조)</b><br  />
        (*) 필수
        <ul>
            <li>(*)결제수단 : <input type="checkbox" name="PayMethod" value="KAKAOPAY" checked="checked"/>KAKAOPAY 고정</li>
            <li>(*)상품명 : <input name="GoodsName" type="text" value="곰인형"/></li>
            <li>(*)상품가격 : <input name="Amt" type="text" value="<%=Amt%>"/></li>
            <li>공급가액 : <input name="SupplyAmt" type="text" value="0"/></li>
            <li>부가세 : <input name="GoodsVat" type="text" value="0"/></li>
            <li>봉사료 : <input name="ServiceAmt" type="text" value="0"/></li>
            <li>(*)상품갯수 : <input name="GoodsCnt" type="text"  value="1" readonly="readonly" style="background-color: #e2e2e2;" />고정</li>
            <li>(*)가맹점ID : <input name="MID" type="text" value="<%=MID%>" /></li>
            <li>(*)인증플래그 : <input name="AuthFlg" type="text" value="10" readonly="readonly" style="background-color: #e2e2e2;" /> 고정</li>
            <li>(*)EdiDate : <input name="EdiDate" type="text" value="<%=EdiDate %>" readonly="readonly" style="background-color: #e2e2e2;"/></li>
            <li>(*)EncryptData : <input name="EncryptData" type="text" value="<%=hash_String %>" readonly="readonly" style="background-color: #e2e2e2;"/></li>
            <li>구매자 이메일 : <input name="BuyerEmail" type="text" value="test@abc.com"/></li>
            <li>(*)구매자명 : <input name="BuyerName" type="text" value="테스터"/></li>
        </ul>
        <br />
        
        
        <!-- 인증 파라미터 목록 -->
        <b>인증 변수 목록(매뉴얼 참조)</b><br />
        <ul>
            <li>상품제공기간 플래그 : <input name="offerPeriodFlag" type="text" value="Y"/></li>
            <li>상품제공기간 : <input name="offerPeriod" type="text" value="제품표시일까지"/></li>
            <li>(*)인증구분 : <input type="text" name="certifiedFlag" value="CN" readonly="readonly" style="background-color: #e2e2e2;" /> 고정</li>
            <li>(*)거래통화 : <input type="text" name="currency" value="KRW" readonly="readonly" style="background-color: #e2e2e2;" /> 고정</li>
            <li>(*)가맹점 암호화키 : <input type="text" name="merchantEncKey" value="<%=merchantEncKey%>" /></li>
            <li>(*)가맹점 해쉬키 : <input type="text" name="merchantHashKey" value="<%=merchantHashKey%>" /></li>
            <li>(*)TXN_ID 요청URL : <input type="text" name="requestDealApproveUrl" value="<%=webPath + msgName%>" /></li>
            <li>
            (*)결제요청타입 :  
            <select name ="prType">
                <option value="MPM">MPM</option>
                <option value="WPM" selected="selected">WPM</option>
            </select>
            <br />MPM : 모바일결제, WPM : PC결제
            </li>
            <li>
            (*)채널타입 :  
            <select name ="channelType">
                <option value="2">2</option>
                <option value="4" selected="selected">4</option>
            </select>
            <br />2: 모바일결제, 4: PC결제
            </li>
            <li>(*)가맹점 거래번호 : <input type="text" name="merchantTxnNum" value="<%=System.nanoTime() %>" /></li>
            
        </ul>
        <br />
        
        <!-- 인증 파라미터 중 할부결제시 사용하는 파라미터 목록 -->
        <!-- 파라미터 입력형태는 매뉴얼 참조  -->
        <b>할부결제시 선택변수 목록</b><br />
        - 옳은 값들을 넣지 않으면 무이자를 사용하지 않는것으로 한다.<br />
        
        <b>카드코드(매뉴얼 참조)</b><br />
        - 비씨:01, 국민:02, 외환:03, 삼성:04, 신한:06, 현대:07, 롯데:08, 한미:11, 씨티:11, <br /> 
        NH채움(농협):12, 수협:13, 신협:13, 우리:15, 하나SK:16, 주택:18, 조흥(강원):19, <br />
        광주:21, 전북:22, 제주:23, 해외비자:25, 해외마스터:26, 해외다이너스:27, <br />
        해외AMX:28, 해외JCB:29, 해외디스커버:30, 은련:34
        <ul>
            <li>카드선택 : <input type="text" name="possiCard" value="" /> ex) 06</li>
            <li>할부개월 : <input type="text" name="fixedInt" value="" /> ex) 03</li>
            <li>최대할부개월 : <input type="text" name="maxInt" value="" /> ex) 24</li>
            <li>
            무이자 사용여부 :
            <select class="require" name="noIntYN" onchange="javascript:noIntYNonChange();">
                <option value="N">사용안함</option>
                <option value="Y">사용</option>
            </select>
            </li>
            <!-- 결제수단코드 + 카드코드 + - + 무이자 개월 ex) CC01-02:03:05:09 -->
            <li>무이자옵션 : <input type="text" name="noIntOpt" value="" /> ex) CC01-02:03:05</li>
            <li>
            카드사포인트사용여부 : 
            <select name ="pointUseYn">
                <option value="N">카드사 포인트 사용안함</option>
                <option value="Y">카드사 포인트 사용</option>
            </select>
            </li>
            <li>금지카드설정 : <input type="text" name="blockCard" value=""/> ex) 01:04:11</li>
            <li>특정제한카드 BIN : <input type="text" name="blockBin" value=""/></li>
        </ul>
        
        
        
        <b>getTxnId 응답</b><br />
        resultcode<input id="resultCode" type="text" value=""/><br/>
        resultmsg<input id="resultMsg" type="text" value=""/><br/>
        txnId<input id="txnId" type="text" value=""/><br/>
        prDt<input id="prDt" type="text" value=""/><br/>
        <br/>
        <br/>
        <!-- DLP호출에 대한 응답 -->
        <b>DLP 응답</b><br />
        SPU : <input type="text" name="SPU" value=""/><br/>
        SPU_SIGN_TOKEN : <input type="text" name="SPU_SIGN_TOKEN" value=""/><br/>
        MPAY_PUB : <input type="text" name="MPAY_PUB" value=""/><br/>
        NON_REP_TOKEN : <input type="text" name="NON_REP_TOKEN" value=""/><br/>    
            
             --%>
</div>
<!-- [KakaoPay] Parameters - END -->      