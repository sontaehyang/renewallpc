<%@ tag pageEncoding="utf-8" %>

<div class="mobile-nicepay-input-area">
    <!-- 결제방법 (CARD, BANK, VBANK, CELLPHONE, SSG_BANK) -->
    <input type="hidden" name="PayMethod" />

    <!-- 할부개월 (00, 02, 03...) -->
    <input type="hidden" name="SelectQuota" />

    <!-- 카드사 선택 (00, 01, 02, 03...) -->
    <input type="hidden" name="SelectCardCode" />

    <input type="hidden" name="GoodsName" />
    <input type="hidden" name="GoodsCnt" value="1" />
    <input type="hidden" name="Amt" />
    <input type="hidden" name="BuyerName" />
    <input type="hidden" name="BuyerTel" />
    <input type="hidden" name="Moid" />
    <input type="hidden" name="MID" />

    <!-- IP -->
    <input type="hidden" name="MallIP" />                                        <!-- 상점서버IP -->

    <!-- 옵션 -->
    <input type="hidden" name="VbankExpDate" id="vExp" />                        <!-- 가상계좌입금만료일 -->
    <input type="hidden" name="BuyerEmail" />                                    <!-- 구매자 이메일 -->
    <input type="hidden" name="GoodsCl" value="1" />                             <!-- 상품구분(실물(1),컨텐츠(0)) -->
    <input type="hidden" name="CharSet" />                                       <!-- 인코딩 설정 -->
    <input type="hidden" name="ReturnURL" />                                     <!-- Return URL -->

    <!-- 변경 불가능 -->
    <input type="hidden" name="EdiDate" />                                       <!-- 전문 생성일시 -->
    <input type="hidden" name="EncryptData" />                                   <!-- 해쉬값 -->
    <input type="hidden" name="TrKey" value="" />                                <!-- 필드만 필요 -->
    <input type="hidden" name="AcsNoIframe" value="Y"/>						     <!-- 나이스페이 결제창 프레임 옵션 (변경불가) -->
</div>