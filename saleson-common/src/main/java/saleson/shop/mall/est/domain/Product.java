package saleson.shop.mall.est.domain;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import saleson.shop.mall.domain.MallBase;

@XmlRootElement(name="order", namespace="http://skt.tmall.business.openapi.spring.service.client.domain/")
@XmlAccessorType (XmlAccessType.FIELD)
public class Product extends MallBase {
	
	private int addPrdNo;  // 0 : 추가 구성품 아님, 0이 아닐경우 추가 구성품의 원상품 번호
	private String addPrdYn; // 추가구성품 유무 (Y: 있음, N: 없음)
	private int bndlDlvSeq; // 묶음배송 일련번호
	private String bndlDlvYN; // 묶음배송 유무 (Y:묶음, N:개별)
	private String custGrdNm; // 고객등급
	private int dlvCst; // 배송비
	private String dlvCstType; // 배송비 착불 (01 : 선불, 02 : 착불, 03 : 무료)
	private int bmDlvCst; // 도서산간배송비
	private String bmDlvCstType; // 도서산간배송비 착불여부 (01:선불, 02:착불, 04:도서산간배송비 청구 필요 - 선물하기 주문??)
	private int dlvNo; // 배송번호
	private String gblDlvYn; // 전세계배송여부
	private String giftCd;
	private String memID; // 회원 ID
	private int memNo; // 회원번호
	private int ordAmt; // 주문총액: 판매단가*수량(주문 -취소 -반품)+옵션가
	private String ordBaseAddr; // 주문자 기본주소
	private String ordDlvReqCont; // 주문시 요청사항
	private String ordDt; // 주문일자
	private String ordDtlsAddr; // 구매자 상세주소
	private String ordMailNo; // 구매자 우편번호
	private String ordNm; // 구매자 이름
	private String ordNo; // 11번가 주문번호
	private int ordOptWonStl; // 주문상품옵션결제금액
	private int ordPayAmt; // 결제금액 : 주문금액 + 배송비 - 판매자 할인금액 - mo쿠폰
	private int ordPrdSeq; // 주문순번
	private String ordPrtblTel; // 구매자 휴대폰번호
	private int ordQty; // 수량
	private String ordStlEndDt; // 결제완료일시
	private String ordTlphnNo; // 주문자전화번호
	private String plcodrCnfDt; // 발주 확인일시
	private String prdNm; // 상품명 
	private int prdNo; // 11번가상품번호
	private int prdStckNo; // 주문상품 옵션코드
	private String rcvrBaseAddr; // 배송기본주소
	private String rcvrDtlsAddr; // 배송상세주소
	private String rcvrMailNo; // 배송지우편번호
	private String rcvrMailNoSeq; // 배송지우편번호순번
	private String rcvrNm; // 수령자명
	private String rcvrPrtblNo; // 수령자핸드폰번호
	private String rcvrTlphn; // 수령자전화번호
	private int selPrc; // 판매가(객단가)
	private int sellerDscPrc; // 판매자 할인금액
	private String sellerPrdCd; // 판매자상품번호
	private String slctPrdOptNm; // 주문상품옵션명
	private int tmallDscPrc; // 11번가 할인금액 
	private String typeAdd; // 주소 유형 (01:지번명, 02:도로명)
	private String typeBilNo; // 건물관리번호
	private int lstTmallDscPrc; // 11번가 할인금액-각상품별 
	private int lstSellerDscPrc; // 판매자 할인금액-각상품별 
	
	private int lstDlvCst; // 배송비.. 왜이렇게 Api마다 항목명이 다른걸까..
	private int ordCnQty; // 주문취소 요청수량
	private String ordPrdStatNm; // 주문상태
	
	/**
	 * 202 : 결제완
	 * 301 : 배송준비중
	 * 401 : 배송중
	 * 701 : 취소처리중
	 * B01 : 주문취소(완료)
	 * 601 : 반품신청, 교환 신청
	 */
	private String ordPrdStat; // 주문상태코드
	
	// 취소 클레임 관련
	private String createDt; // 클레임 요청 일시
	private String ordCnDtlsRsn; // 사유코드에 대한 상세내역
	private String ordCnMnbdCd; // 클레임 등록 주체
	private String ordCnRsnCd; // 클레임 사유 코드
	private String ordCnStatCd; // 클레임 상태 (01 : 취소요청, 02 : 취소 완료)
	private String ordPrdCnSeq; // 클레임 번호
	
	// 반품 클레임 관련
	private String reqDt;
	private String trtEndDt;
	/** 클레임 처리 상태
		103	: 재결제대기중 
		104	: 반품보류 
		105	: 반품신청	
		106	: 반품완료 
		107	: 반품거부 
		108	: 반품철회 
		109	: 반품완료보류	
		201	: 교환신청 
		212	: 교환승인	
		214	: 교환보류 
		221	: 교환발송완료 
		232	: 교환거부	
		233	: 교환철회	
		301	: 재배송접수	
		302	: 재배송완료
	 */
	private String clmStat; // 클레임 처리 상태
	private String clmReqSeq; // 반품 클레임 번호
	private int clmReqQty; // 클레임 수량
	
	/** 반품
	 	101 : 구매자 - 상품에 이상 없으나 구매 의사 없어짐
		105 : 판매자 - 상품이 상품상세 정보와 틀림
		108 : 판매자 - 다른 상품이 잘못 배송됨
		110 : 구매자 - 사이즈, 색상 등을 잘못 선택함
		111 : 판매자 - 배송된 상품의 파손/하자/포장 불량
		112 : 판매자 - 상품이 도착하고 있지 않음
		113 : 구매자 - 기타(구매자 책임사유)
		114 : 구매자 - 구매자 귀책으로 교환을 반품으로 전환
		115 : 판매자 - 판매자 귀책으로 교환을 반품으로 전환
		116 : 판매자 - 기타(판매자 책임사유)
		117 : 판매자 - 전세계배송 국내통관 거부
		118 : 판매자 - 전세계배송 30kg 초과
		119 : 구매자 - 전세계배송(추가 해외배송비 미결제)
		198 : 판매자 - 구매확정후 직권취소(판매자 책임)
		199 : 구매자 - 구매확정후 직권취소(구매자 책임)
		
		교환
		206 : 구매자 - 사이즈 또는 색상 등을 잘못 선택함
		207 : 판매자 - 배송된 상품의 파손/하자/포장 불량
		208 : 판매자 - 다른 상품이 잘못 배송됨
		209 : 판매자 - 품절 등의 사유로 판매자 협의 후 교환
		210 : 판매자 - 상품이 상품상세 정보와 틀림
		212 : 구매자 - 구매자 귀책으로 반품을 교환으로 전환
		213 : 판매자 - 판매자 귀책으로 반품을 교환으로 전환
		211 : 구매자 - 기타(구매자 책임사유)
		214 : 판매자 - 기타(판매자 책임사유)
	 */
	private String clmReqRsn; // 클레임 사유코드(반품)
	private String clmReqCont; // 사유코드에 대한 상세내역
	private String rcvrTypeAdd; // 수거지 주소 유형
	private String rcvrTypeBilNo; // 수거지 건물관리번호
	private String twMthd; // 반품, 교환 상품 발송방법
	private int clmLstDlvCst; // 반품배송비
	private int appmtDlvCst; // 11번가 지정반품 택배비
	
	/**
		01 : 신용카드
		02 : 포인트
		03 : 박스에 동봉
		04 : 판매자에게 직접송금
		null : 클레임 사유가 판매자일 경우
	 */
	private String clmDlvCstMthd; // 결제방법
	private String twPrdInvcNo; // 수거 송장번호
	
	/**
		00001 : 동부익스프레스
		00002 : 로젠택배
		00006 : 옐로우캡
		00007 : 우체국택배
		00008 : 우편등기
		00011 : 한진택배
		00012 : 현대택배
		00014 : KGB택배
		00019 : 이노지스택배
		00021 : 대신택배
		00022 : 일양로지스
		00023 : ACI
		00025 : WIZWA
		00026 : 경동택배
		00027 : 천일택배
		00028 : KGL
		00031 : OCS Korea
		00033 : GTX택배
		00034 : CJ대한통운
		00035 : 합동택배
		00037 : 건영택배
		00099 : 기타
	 */
	private String dlvEtprsCd; // 수거택배사코드
	
	// 반품 완료
	private int addDlvCst; // 추가 배송비
	private String dlvCstRespnClf; // 배송비 부담여부(01:구매자, 02:판매자) 
	private int stlDlvCst; // 구매자 차감배송비
	
	
	// 교환 클레임 관련
	private String exchNm; // 교환상품 수령지 이름
	private String exchTlphnNo; // 교환상품 수령지 전화번호
	private String exchPrtblTel; // 교환상품 수령지 휴대폰번호
	private String exchMailNo; // 교환상품 수령지 우편번호
	private String exchMailNoSeq; // 교환상품 수령지 우편번호 순번 
	private String exchBaseAddr; // 교환상품 수령지 기본주소
	private String exchDtlsAddr; // 교환상품 수령지 상세주소
	private String exchTypeAdd; // 교환상품 수령지 주소 유형
	private String exchTypeBilNo; // 교환상품 수령지 건물관리번호
	
	public int getAddDlvCst() {
		return addDlvCst;
	}
	public void setAddDlvCst(int addDlvCst) {
		this.addDlvCst = addDlvCst;
	}
	public String getDlvCstRespnClf() {
		return dlvCstRespnClf;
	}
	public void setDlvCstRespnClf(String dlvCstRespnClf) {
		this.dlvCstRespnClf = dlvCstRespnClf;
	}
	public int getStlDlvCst() {
		return stlDlvCst;
	}
	public void setStlDlvCst(int stlDlvCst) {
		this.stlDlvCst = stlDlvCst;
	}
	public String getExchNm() {
		return exchNm;
	}
	public void setExchNm(String exchNm) {
		this.exchNm = exchNm;
	}
	public String getExchTlphnNo() {
		return exchTlphnNo;
	}
	public void setExchTlphnNo(String exchTlphnNo) {
		this.exchTlphnNo = exchTlphnNo;
	}
	public String getExchPrtblTel() {
		return exchPrtblTel;
	}
	public void setExchPrtblTel(String exchPrtblTel) {
		this.exchPrtblTel = exchPrtblTel;
	}
	public String getExchMailNo() {
		return exchMailNo;
	}
	public void setExchMailNo(String exchMailNo) {
		this.exchMailNo = exchMailNo;
	}
	public String getExchMailNoSeq() {
		return exchMailNoSeq;
	}
	public void setExchMailNoSeq(String exchMailNoSeq) {
		this.exchMailNoSeq = exchMailNoSeq;
	}
	public String getExchBaseAddr() {
		return exchBaseAddr;
	}
	public void setExchBaseAddr(String exchBaseAddr) {
		this.exchBaseAddr = exchBaseAddr;
	}
	public String getExchDtlsAddr() {
		return exchDtlsAddr;
	}
	public void setExchDtlsAddr(String exchDtlsAddr) {
		this.exchDtlsAddr = exchDtlsAddr;
	}
	public String getExchTypeAdd() {
		return exchTypeAdd;
	}
	public void setExchTypeAdd(String exchTypeAdd) {
		this.exchTypeAdd = exchTypeAdd;
	}
	public String getExchTypeBilNo() {
		return exchTypeBilNo;
	}
	public void setExchTypeBilNo(String exchTypeBilNo) {
		this.exchTypeBilNo = exchTypeBilNo;
	}
	public String getTrtEndDt() {
		return trtEndDt;
	}
	public void setTrtEndDt(String trtEndDt) {
		this.trtEndDt = trtEndDt;
	}
	public String getClmStat() {
		return clmStat;
	}
	public void setClmStat(String clmStat) {
		this.clmStat = clmStat;
	}
	public int getLstDlvCst() {
		return lstDlvCst;
	}
	public void setLstDlvCst(int lstDlvCst) {
		this.lstDlvCst = lstDlvCst;
	}
	public String getReqDt() {
		return reqDt;
	}
	public void setReqDt(String reqDt) {
		this.reqDt = reqDt;
	}
	public int getClmReqQty() {
		return clmReqQty;
	}
	public void setClmReqQty(int clmReqQty) {
		this.clmReqQty = clmReqQty;
	}
	public String getClmReqRsn() {
		return clmReqRsn;
	}
	public void setClmReqRsn(String clmReqRsn) {
		this.clmReqRsn = clmReqRsn;
	}
	public String getClmReqCont() {
		return clmReqCont;
	}
	public void setClmReqCont(String clmReqCont) {
		this.clmReqCont = clmReqCont;
	}
	public String getRcvrTypeAdd() {
		return rcvrTypeAdd;
	}
	public void setRcvrTypeAdd(String rcvrTypeAdd) {
		this.rcvrTypeAdd = rcvrTypeAdd;
	}
	public String getRcvrTypeBilNo() {
		return rcvrTypeBilNo;
	}
	public void setRcvrTypeBilNo(String rcvrTypeBilNo) {
		this.rcvrTypeBilNo = rcvrTypeBilNo;
	}
	public String getTwMthd() {
		return twMthd;
	}
	public void setTwMthd(String twMthd) {
		this.twMthd = twMthd;
	}
	public int getClmLstDlvCst() {
		return clmLstDlvCst;
	}
	public void setClmLstDlvCst(int clmLstDlvCst) {
		this.clmLstDlvCst = clmLstDlvCst;
	}
	public int getAppmtDlvCst() {
		return appmtDlvCst;
	}
	public void setAppmtDlvCst(int appmtDlvCst) {
		this.appmtDlvCst = appmtDlvCst;
	}
	public String getClmDlvCstMthd() {
		return clmDlvCstMthd;
	}
	public void setClmDlvCstMthd(String clmDlvCstMthd) {
		this.clmDlvCstMthd = clmDlvCstMthd;
	}
	public String getTwPrdInvcNo() {
		return twPrdInvcNo;
	}
	public void setTwPrdInvcNo(String twPrdInvcNo) {
		this.twPrdInvcNo = twPrdInvcNo;
	}
	public String getDlvEtprsCd() {
		return dlvEtprsCd;
	}
	public void setDlvEtprsCd(String dlvEtprsCd) {
		this.dlvEtprsCd = dlvEtprsCd;
	}
	public String getClmReqSeq() {
		return clmReqSeq;
	}
	public void setClmReqSeq(String clmReqSeq) {
		this.clmReqSeq = clmReqSeq;
	}
	public String getCreateDt() {
		return createDt;
	}
	public void setCreateDt(String createDt) {
		this.createDt = createDt;
	}
	public String getOrdCnDtlsRsn() {
		return ordCnDtlsRsn;
	}
	public void setOrdCnDtlsRsn(String ordCnDtlsRsn) {
		this.ordCnDtlsRsn = ordCnDtlsRsn;
	}
	public String getOrdCnMnbdCd() {
		return ordCnMnbdCd;
	}
	public void setOrdCnMnbdCd(String ordCnMnbdCd) {
		this.ordCnMnbdCd = ordCnMnbdCd;
	}
	public String getOrdCnRsnCd() {
		return ordCnRsnCd;
	}
	public void setOrdCnRsnCd(String ordCnRsnCd) {
		this.ordCnRsnCd = ordCnRsnCd;
	}
	public String getOrdCnStatCd() {
		return ordCnStatCd;
	}
	public void setOrdCnStatCd(String ordCnStatCd) {
		this.ordCnStatCd = ordCnStatCd;
	}
	public String getOrdPrdCnSeq() {
		return ordPrdCnSeq;
	}
	public void setOrdPrdCnSeq(String ordPrdCnSeq) {
		this.ordPrdCnSeq = ordPrdCnSeq;
	}
	public int getAddPrdNo() {
		return addPrdNo;
	}
	public void setAddPrdNo(int addPrdNo) {
		this.addPrdNo = addPrdNo;
	}
	public String getAddPrdYn() {
		return addPrdYn;
	}
	public void setAddPrdYn(String addPrdYn) {
		this.addPrdYn = addPrdYn;
	}
	public int getBndlDlvSeq() {
		return bndlDlvSeq;
	}
	public void setBndlDlvSeq(int bndlDlvSeq) {
		this.bndlDlvSeq = bndlDlvSeq;
	}
	public String getBndlDlvYN() {
		return bndlDlvYN;
	}
	public void setBndlDlvYN(String bndlDlvYN) {
		this.bndlDlvYN = bndlDlvYN;
	}
	public String getCustGrdNm() {
		return custGrdNm;
	}
	public void setCustGrdNm(String custGrdNm) {
		this.custGrdNm = custGrdNm;
	}
	public int getDlvCst() {
		return dlvCst;
	}
	public void setDlvCst(int dlvCst) {
		this.dlvCst = dlvCst;
	}
	public String getDlvCstType() {
		return dlvCstType;
	}
	public void setDlvCstType(String dlvCstType) {
		this.dlvCstType = dlvCstType;
	}
	public int getBmDlvCst() {
		return bmDlvCst;
	}
	public void setBmDlvCst(int bmDlvCst) {
		this.bmDlvCst = bmDlvCst;
	}
	public String getBmDlvCstType() {
		return bmDlvCstType;
	}
	public void setBmDlvCstType(String bmDlvCstType) {
		this.bmDlvCstType = bmDlvCstType;
	}
	public int getDlvNo() {
		return dlvNo;
	}
	public void setDlvNo(int dlvNo) {
		this.dlvNo = dlvNo;
	}
	public String getGblDlvYn() {
		return gblDlvYn;
	}
	public void setGblDlvYn(String gblDlvYn) {
		this.gblDlvYn = gblDlvYn;
	}
	public String getGiftCd() {
		return giftCd;
	}
	public void setGiftCd(String giftCd) {
		this.giftCd = giftCd;
	}
	public String getMemID() {
		return memID;
	}
	public void setMemID(String memID) {
		this.memID = memID;
	}
	public int getMemNo() {
		return memNo;
	}
	public void setMemNo(int memNo) {
		this.memNo = memNo;
	}
	public int getOrdAmt() {
		return ordAmt;
	}
	public void setOrdAmt(int ordAmt) {
		this.ordAmt = ordAmt;
	}
	public String getOrdBaseAddr() {
		return ordBaseAddr;
	}
	public void setOrdBaseAddr(String ordBaseAddr) {
		this.ordBaseAddr = ordBaseAddr;
	}
	public String getOrdDlvReqCont() {
		return ordDlvReqCont;
	}
	public void setOrdDlvReqCont(String ordDlvReqCont) {
		this.ordDlvReqCont = ordDlvReqCont;
	}
	public String getOrdDt() {
		return ordDt;
	}
	public void setOrdDt(String ordDt) {
		this.ordDt = ordDt;
	}
	public String getOrdDtlsAddr() {
		return ordDtlsAddr;
	}
	public void setOrdDtlsAddr(String ordDtlsAddr) {
		this.ordDtlsAddr = ordDtlsAddr;
	}
	public String getOrdMailNo() {
		return ordMailNo;
	}
	public void setOrdMailNo(String ordMailNo) {
		this.ordMailNo = ordMailNo;
	}
	public String getOrdNm() {
		return ordNm;
	}
	public void setOrdNm(String ordNm) {
		this.ordNm = ordNm;
	}
	public String getOrdNo() {
		return ordNo;
	}
	public void setOrdNo(String ordNo) {
		this.ordNo = ordNo;
	}
	public int getOrdOptWonStl() {
		return ordOptWonStl;
	}
	public void setOrdOptWonStl(int ordOptWonStl) {
		this.ordOptWonStl = ordOptWonStl;
	}
	public int getOrdPayAmt() {
		return ordPayAmt;
	}
	public void setOrdPayAmt(int ordPayAmt) {
		this.ordPayAmt = ordPayAmt;
	}
	public int getOrdPrdSeq() {
		return ordPrdSeq;
	}
	public void setOrdPrdSeq(int ordPrdSeq) {
		this.ordPrdSeq = ordPrdSeq;
	}
	public String getOrdPrtblTel() {
		return ordPrtblTel;
	}
	public void setOrdPrtblTel(String ordPrtblTel) {
		this.ordPrtblTel = ordPrtblTel;
	}
	public int getOrdQty() {
		return ordQty;
	}
	public void setOrdQty(int ordQty) {
		this.ordQty = ordQty;
	}
	public String getOrdStlEndDt() {
		return ordStlEndDt;
	}
	public void setOrdStlEndDt(String ordStlEndDt) {
		this.ordStlEndDt = ordStlEndDt;
	}
	public String getOrdTlphnNo() {
		return ordTlphnNo;
	}
	public void setOrdTlphnNo(String ordTlphnNo) {
		this.ordTlphnNo = ordTlphnNo;
	}
	public String getPlcodrCnfDt() {
		return plcodrCnfDt;
	}
	public void setPlcodrCnfDt(String plcodrCnfDt) {
		this.plcodrCnfDt = plcodrCnfDt;
	}
	public String getPrdNm() {
		return prdNm;
	}
	public void setPrdNm(String prdNm) {
		this.prdNm = prdNm;
	}
	public int getPrdNo() {
		return prdNo;
	}
	public void setPrdNo(int prdNo) {
		this.prdNo = prdNo;
	}
	public int getPrdStckNo() {
		return prdStckNo;
	}
	public void setPrdStckNo(int prdStckNo) {
		this.prdStckNo = prdStckNo;
	}
	public String getRcvrBaseAddr() {
		return rcvrBaseAddr;
	}
	public void setRcvrBaseAddr(String rcvrBaseAddr) {
		this.rcvrBaseAddr = rcvrBaseAddr;
	}
	public String getRcvrDtlsAddr() {
		return rcvrDtlsAddr;
	}
	public void setRcvrDtlsAddr(String rcvrDtlsAddr) {
		this.rcvrDtlsAddr = rcvrDtlsAddr;
	}
	public String getRcvrMailNo() {
		return rcvrMailNo;
	}
	public void setRcvrMailNo(String rcvrMailNo) {
		this.rcvrMailNo = rcvrMailNo;
	}
	public String getRcvrMailNoSeq() {
		return rcvrMailNoSeq;
	}
	public void setRcvrMailNoSeq(String rcvrMailNoSeq) {
		this.rcvrMailNoSeq = rcvrMailNoSeq;
	}
	public String getRcvrNm() {
		return rcvrNm;
	}
	public void setRcvrNm(String rcvrNm) {
		this.rcvrNm = rcvrNm;
	}
	public String getRcvrPrtblNo() {
		return rcvrPrtblNo;
	}
	public void setRcvrPrtblNo(String rcvrPrtblNo) {
		this.rcvrPrtblNo = rcvrPrtblNo;
	}
	public String getRcvrTlphn() {
		return rcvrTlphn;
	}
	public void setRcvrTlphn(String rcvrTlphn) {
		this.rcvrTlphn = rcvrTlphn;
	}
	public int getSelPrc() {
		return selPrc;
	}
	public void setSelPrc(int selPrc) {
		this.selPrc = selPrc;
	}
	public int getSellerDscPrc() {
		return sellerDscPrc;
	}
	public void setSellerDscPrc(int sellerDscPrc) {
		this.sellerDscPrc = sellerDscPrc;
	}
	public String getSellerPrdCd() {
		return sellerPrdCd;
	}
	public void setSellerPrdCd(String sellerPrdCd) {
		this.sellerPrdCd = sellerPrdCd;
	}
	public String getSlctPrdOptNm() {
		return slctPrdOptNm;
	}
	public void setSlctPrdOptNm(String slctPrdOptNm) {
		this.slctPrdOptNm = slctPrdOptNm;
	}
	public int getTmallDscPrc() {
		return tmallDscPrc;
	}
	public void setTmallDscPrc(int tmallDscPrc) {
		this.tmallDscPrc = tmallDscPrc;
	}
	public String getTypeAdd() {
		return typeAdd;
	}
	public void setTypeAdd(String typeAdd) {
		this.typeAdd = typeAdd;
	}
	public String getTypeBilNo() {
		return typeBilNo;
	}
	public void setTypeBilNo(String typeBilNo) {
		this.typeBilNo = typeBilNo;
	}
	public int getLstTmallDscPrc() {
		return lstTmallDscPrc;
	}
	public void setLstTmallDscPrc(int lstTmallDscPrc) {
		this.lstTmallDscPrc = lstTmallDscPrc;
	}
	public int getLstSellerDscPrc() {
		return lstSellerDscPrc;
	}
	public void setLstSellerDscPrc(int lstSellerDscPrc) {
		this.lstSellerDscPrc = lstSellerDscPrc;
	}
	public int getOrdCnQty() {
		return ordCnQty;
	}
	public void setOrdCnQty(int ordCnQty) {
		this.ordCnQty = ordCnQty;
	}
	public String getOrdPrdStatNm() {
		return ordPrdStatNm;
	}
	public void setOrdPrdStatNm(String ordPrdStatNm) {
		this.ordPrdStatNm = ordPrdStatNm;
	}
	public String getOrdPrdStat() {
		return ordPrdStat;
	}
	public void setOrdPrdStat(String ordPrdStat) {
		this.ordPrdStat = ordPrdStat;
	}

}
