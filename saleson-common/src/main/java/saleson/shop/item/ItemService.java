package saleson.shop.item;

import org.springframework.web.multipart.MultipartFile;
import saleson.shop.coupon.domain.ChosenItem;
import saleson.shop.coupon.support.UserCouponParam;
import saleson.shop.item.domain.*;
import saleson.shop.item.support.AsyncReport;
import saleson.shop.item.support.ItemListParam;
import saleson.shop.item.support.ItemParam;
import saleson.shop.item.support.ItemSaleEditParam;
import saleson.shop.order.domain.OrderItem;
import saleson.shop.shipment.domain.Shipment;
import saleson.shop.shipmentreturn.domain.ShipmentReturn;
import saleson.shop.wishlist.domain.WishlistGroup;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Future;

public interface ItemService {
	
	/**
	 * 아이템 변경내역 총 개수 조회
	 */
	public int getItemLogCountById(ItemParam itemParam);
	
	/**
	 * 아이템 변경내역 로그 조회
	 * @param itemParam
	 * @return
	 */
	public List<Item> getItemLogListById(ItemParam itemParam);
	
	/**
	 * 쇼핑하우 파일 생성
	 */
	public void makeShoppingHowFile(String fileName);
	
	/**
	 * 상품등록
	 * @param item
	 */
	public void insertItem(Item item);
	
	
	/**
	 * 상품주문옵션 등록
	 * @param itemOption
	 */
	public void insertItemOption(ItemOption itemOption);


	/**
	 * 상품 ID로 조회 (OP_ITEM 테이블만)
	 * @param itemId
	 * @return
	 */
	public Item getItemBy(int itemId);
	
	
	/**
	 * 상품번호로 상품 조회 (OP_ITEM 테이블만)
	 * @param itemUserCode
	 * @return
	 */
	public Item getItemBy(String itemUserCode);
	
	
	/**
	 * 상품ID로 상품을 조회
	 * @param itemId
	 * @return
	 */
	public Item getItemById(int itemId);
	
	
	/**
	 * 상품ID로 상품을 조회 (관리자페이지 용)
	 * @param itemId
	 * @return
	 */
	public Item getItemByIdForManager(int itemId);
	
	/**
	 * 상품코드로 미리보기 상품 정보를 조회함.
	 * @param itemUserCode
	 * @return
	 */
	public Item getItemByItemUserCodeForPreview(String itemUserCode);
	
	/**
	 * 상품코드로 상품을 조회한다.
	 * @param itemUserCode
	 * @return
	 */
	public Item getItemByItemUserCode(String itemUserCode);


	/**
	 * 판매자 상품코드로 상품 카운트 조회
	 * @param itemUserCode
	 * @return
	 */
	public int getItemCountByItemUserCode(String itemUserCode);


	/**
	 * 조건에 해당하는 상품 카운트
	 * @param itemParam
	 * @return
	 */
	public int getItemCount(ItemParam itemParam);


	/**
	 * 조건에 해당하는 상품 카운트
	 * @param itemParam
	 * @return
	 */
	public List<Item> getItemList(ItemParam itemParam);

	/**
	 * 전시상품 조회 - 더보기 페이지용
	 * @param itemParam
	 * @return
	 */
	public List<Item> getMainDisplayItemList(ItemParam itemParam);
	
	/**
	 * 메인 전시상품 조회
	 * @param templateId
	 * @param limit
	 * @return
	 */
	public List<Item> getMainDisplayItemListForMain(String templateId, String team, int limit);

	/**
	 * 상품 노출 순서를 등록한다.
	 * @param itemListParam
	 */
	public void updateItemOrdering(ItemListParam itemListParam);


	/**
	 * 목록데이터 중 선택한 데이터를 삭제 처리한다.
	 * @param itemListParam
	 */
	public void deleteListData(ItemListParam itemListParam);
	
	/**
	 * 상품 승인
	 * @param item
	 */
	public void updateItemApproval(Item item);
	
	/**
	 * 목록데이터 중 선택한 데이터를 수정 처리한다.
	 * @param itemListParam
	 */
	public void updateListData(ItemListParam itemListParam);
	
	
	/**
	 * 목록데이터 중 선택한 데이터의 공개여부를 수정한다.
	 * @param itemListParam
	 */
	public void updateListDataByDisplay(ItemListParam itemListParam);

	
	/**
	 * 목록데이터 중 선택한 데이터의 상품라벨을 수정한다.
	 * @param itemListParam
	 */
	public void updateListDataByLabel(ItemListParam itemListParam);
	

	/**
	 * 목록데이터 중 선택한 상품을 특정 카테고리에 추가한다.
	 * @param itemListParam
	 */
	public void insertItemCategoryByItemListParam(ItemListParam itemListParam);


	/**
	 * 상품 정보를 수정한다.
	 * @param item
	 */
	public void updateItem(Item item);


	/**
	 * 상품 대표이미지를 삭제한다.
	 * @param itemId
	 */
	public void deleteItemImageByItemId(int itemId);

	/**
	 * 상품 상세 이미지를 삭제한다.
	 * @param itemImageId
	 */
	public void deleteItemImageById(int itemImageId);

	/**
	 * 관련상품 5개 - 임의 출력 (동일 카테고리)
	 * @param itemParam
	 * @return
	 */
	public List<ItemRelation> getItemRelationRandomList(ItemParam itemParam);

	/**
	 * 상품 리뷰 카운트
	 * @param itemParam
	 * @return
	 */
	public int getItemReviewCountByParam(ItemParam itemParam);
	
	/**
	 * 상품 리뷰 목록
	 * @param itemParam
	 * @return
	 */
	public List<ItemReview> getItemReviewListByParam(ItemParam itemParam);


	/**
	 * 상품 리뷰 등록
	 * @param itemReview
	 */
	public int insertItemReview(ItemReview itemReview);

	
	
	/**
	 * 같이 구매한 상품 목록 6개 
	 * @param itemId
	 * @return
	 */
	public List<ItemOther> getItemOtherList(int itemId);


	
	/**
	 * 상품 옵션을 조회함.
	 * @param itemOptionId
	 * @return
	 */
	public ItemOption getItemOptionById(int itemOptionId);

	/**
	 * 상품 재고량을 변경한다
	 * @param stockCode
	 * @param quantity
	 * @param sign
	 */
	public void updateItemStockQuantityByItemUserCodeNoTx(String stockCode, int quantity, String sign);
	
	
	/**
	 * 상품 옵션 재고량을 변경한다
	 * @param stockCode
	 * @param quantity
	 * @param sign
	 */
	public void updateItemOptionStockQuantityByOptionCodeNoTx(String stockCode, int quantity, String sign);
	
	
	/**
	 * 상품 재고량을 변경한다
	 * @param itemId
	 * @param quantity
	 * @param sign
	 */
	public void updateItemStockQuantityByItemStockQuantityParamNoTx(int itemId, int quantity, String sign);
	
	
	/**
	 * 상품 옵션 재고량을 변경한다
	 * @param optionIds
	 * @param quantity
	 * @param sign
	 */
	public void updateItemOptionStockQuantityByItemStockQuantityParamNoTx(List<Integer> optionIds, int quantity, String sign);
	
	
	/**
	 * 팀, 그룹, 카테고리의 신상품을 조회한다.
	 * <pre>
	 * 팀 상품 조회 		: itemParam.setCategoryTeamCode(팀 코드)
	 * 그룹 상품 조회 		: itemParam.setCategoryGroupCode(그룹 코드)
	 * 카테고리 상품 조회 	: itemParam.setCategoryUrl(카테고리 코드)
	 * +
	 * 조회 데이터 수 		: itemParam.setLimit(데이터 수)
	 * </pre>
	 * @param itemParam
	 * @return
	 */
	public List<Item> getNewArrivalItemList(ItemParam itemParam);
	
	
	/**
	 * 팀, 그룹, 카테고리의 신상품을 조회한다. (메인페이지용)
	 * <pre>
	 * - 조회 데이터 수 : itemParam.setLimit(데이터 수)
	 * </pre>
	 * @param itemParam
	 * @return
	 */
	public List<Item> getNewArrivalItemListForMain(ItemParam itemParam);


	/**
	 * 관심상품 그룹에 포함된 상품 목록을 조회한다.
	 * @param wishlistGroup
	 * @return
	 */
	public List<Item> getWishlistItemList(WishlistGroup wishlistGroup);

	public int getSearchNewArrivalItemCount(ItemParam itemParam);
	
	public List<Item> getSearchNewArrivalItemList(ItemParam itemParam);


	/**
	 * 해당 리뷰 조회
	 * @param itemReviewId
	 * @return
	 */
	public ItemReview getItemReviewById(int itemReviewId);
	
	/**
	 * 리뷰 수정처리
	 * @param itemReview
	 */
	void updateItemReview(ItemReview itemReview);
	
	/**
	 * 리뷰 삭제
	 * @param itemReviewId
	 */
	void deleteItemReview(int itemReviewId);
	

	/**
	 * 상품리뷰 이미지 삭제
	 * @param itemReview
	 * @param itemReviewImage
	 */
	void deleteItemReviewImage(ItemReview itemReview, ItemReviewImage itemReviewImage);
	
	
	public Future<AsyncReport> uploadCsv(MultipartFile[] multipartFiles);


	/**
	 * 상품 EXCEL 등록 처리
	 * @param multipartFile
	 */
	public String insertExcelData(MultipartFile multipartFile);

	
	/**
	 * 상품별 카테고리 목록 (엑셀 다운로드 용) 
	 * @param itemParam
	 * @return
	 */
	public List<ExcelItemCategory> getItemCategoryListForExcel(ItemParam itemParam);

	/**
	 * 상품별 추가구성상품 목록 (엑셀 다운로드 용)
	 * @param itemParam
	 * @return
	 */
	List<ExcelItemAddition> getItemAdditionListForExcel(ItemParam itemParam);
	
	/**
	 * 상품별 관련상품 목록 (엑셀 다운로드 용) 
	 * @param itemParam
	 * @return
	 */
	public List<ExcelItemRelation> getItemRelationListForExcel(ItemParam itemParam);
	
	/**
	 * 상품별 포인트 목록 (엑셀 다운로드 용) 
	 * @param itemParam
	 * @return
	 */
	public List<ExcelItemPointConfig> getItemPointListForExcel(ItemParam itemParam);

	/**
	 * 상품 기본 정보 목록 (엑셀 다운로드 용)
	 * @param itemParam
	 * @return
	 */
	public List<ItemInfo> getItemInfoListForExcel(ItemParam itemParam);
	
	
	/**
	 * 상품 기본 정보 목록 (엑셀 다운로드(모바일) 용)
	 * @param itemParam
	 * @return
	 */
	public List<ItemInfo> getItemInfoMobileListForExcel(ItemParam itemParam);


	/**
	 * 상품별 검색어 목록 (엑셀다운로드)
	 * @param itemParam
	 * @return
	 */
	public List<Item> getItemKeywordListForExcel(ItemParam itemParam);
	
	
	/**
	 * 색인 검색 - 인덱스 카운트 목록
	 * @param searchIndexParam
	 * @return
	 */
	public List<HashMap<String, Object>> getIndexList(
			SearchIndexParam searchIndexParam);
	
	
	/**
	 * 색인 검색 - 서브 인덱스 카운트 목록
	 * @param searchIndexParam
	 * @return
	 */
	public List<HashMap<String, Object>> getSubIndexList(
			SearchIndexParam searchIndexParam);


	/**
	 * 색인 검색 - 상품목록
	 * @param searchIndexParam
	 * @return
	 */
	public List<ItemIndex> getItemIndexList(SearchIndexParam searchIndexParam);


	/**
	 * 오늘 본 상품 목록
	 * @param itemParam
	 * @return
	 */
	public List<Item> getTodayItemList(ItemParam itemParam);

	/**
	 * 상품 조회 수 증가.
	 * @param itemId
	 */
	public void updateItemHitsByItemId(int itemId);
	

	/**
	 * 그룹베너에 설정된 상품 목록
	 * @param value
	 * @return
	 */
	public List<Item> getItemListForGroupBanner(String value);
	
	/**
	 * 그룹 배너에 설정된 상품 목록 (그룹별 한방 쿼리로 조회)
	 * @param shopCategoryGroups
	 * @return
	 */
	public List<saleson.shop.categories.domain.Group> getGroupItemsForGroupBanner(
			List<saleson.shop.categories.domain.Group> shopCategoryGroups);

	
	/**
	 * 상품 고시 유형 목록.
	 * @return
	 */
	public List<ItemNotice> getItemNoticeCodes();

	/**
	 * 상품 유형 코드에 해당하는 상품 고시 목록을 가져옴.
	 * @param itemNoticeCode
	 */
	public List<ItemNotice> getItemNoticeListByCode(String itemNoticeCode);

	/**
	 * 출고지 배송금액 수정
	 * @param shipment
	 */
	public void updateShipmentPrice(Shipment shipment);
	
	/**
	 * 출고지 배송정보 수정
	 * @param shipment
	 */
	public void updateShipment(Shipment shipment);
	
	/**
	 * 교환/반품 배송금액 수정
	 * @param shipmentReturn
	 */
	public void updateShipmentReturn(ShipmentReturn shipmentReturn);
	
	public List<Item> getItemCountForMain(long sellerId);
		
	/**
	 * 가격변경요청
	 * @param itemSaleEdit
	 */
	public void insertItemSaleEdit(ItemSaleEdit itemSaleEdit);
	
	/**
	 * 가격변경요청리스트
	 * @param itemSaleEditParam
	 * @return
	 */
	public List<ItemSaleEdit> getItemSaleEdit(ItemSaleEditParam itemSaleEditParam);
	
	/**
	 * 가격변경요청카운트
	 * @param itemSaleEditParam
	 * @return
	 */
	public int getItemSaleEditCountByParam(ItemSaleEditParam itemSaleEditParam);
	
	/**
	 * 가격변경요청삭제
	 * @param itemSaleEdit
	 */
	public void deleteItemSaleEdit(ItemSaleEdit itemSaleEdit);
	
	/**
	 * 가격변경요청수정
	 * @param itemSaleEdit
	 */
	public void updateSaleEdit(ItemSaleEdit itemSaleEdit);
	
	/**
	 * 가격변경요청상태변경
	 * @param itemSaleEdit
	 */
	public void updateSaleEditStatus(ItemSaleEdit itemSaleEdit);
	
	/**
	 * 가격요청가져오기
	 * @param itemSaleEditParam
	 * @return
	 */
	public ItemSaleEdit getItemSaleEditByParam(ItemSaleEditParam itemSaleEditParam);
	
	/**
	 * 상품가격변경
	 * @param itemSaleEdit
	 * @return
	 */
	public void updateItemPrice(ItemSaleEdit itemSaleEdit);
	
	/**
	 * 아이템 가격정보 가져오기
	 * @param itemSaleEdit
	 * @return
	 */
	public Item getItemByItemSaleEdit(ItemSaleEdit itemSaleEdit);


	
	public List<ChosenItem> getChosenItemList(List<String> list);
	public List<ChosenItem> getSearchItemList(ChosenItem chosenItem);
	
	
	 /**
	 * [엑셀업로드] 상품코드로 ITEM_ID를 조회함.
	 * @param itemUserCode
	 * @return
	 */
	 public Integer getItemIdByItemUserCode(String itemUserCode);

	/** kye 추가
	 * 상품 미등록 리뷰 카운트
	 * @param searchParam
	 * @return
	 */
	public int getItemNonregisteredReviewCount(ItemParam searchParam);
	
	/** kye 추가
	 * 상품 미등록 리뷰 리스트
	 * @param itemParam
	 * @return
	 */
	public List<OrderItem> getItemNonregisteredReviewList(ItemParam itemParam);
	
	/**
	 * 상품 옵션 품절 정보 업데이트.
	 */
	public void updateItemOptionSoldout();

	/**
	 * 관련상품 임의 출력인 경우 동일 카테고리 상품 5개 조회
	 * @param relationItemDisplayType
	 * @param itemId
	 * @return
	 */
	List<ItemRelation> getItemRelationsByItemId(String relationItemDisplayType, int itemId);

	/**
	 * 추가구성상품 조회
	 * @param itemId
	 * @return
	 */
	List<ItemAddition> getItemAdditionsByItemId(int itemId);

	/**
	 * 상품별 혜택 정보 조회
	 * @param itemId
	 * @return
	 */
	BenefitInfo getBenefitInfoByItemId(int itemId) throws Exception;

	/**
	 * 상품별 고객 활동정보 조회
	 * @param itemId
	 * @return
	 */
	CustomerInfo getCustomerInfoByItemId(int itemId) throws Exception;

	/**
	 * 다운로드 가능한 쿠폰 목록 페이징 처리
	 * @param userCouponParam
	 */
    public void setDownloadableCouponListPagination(UserCouponParam userCouponParam);

	/**
	 * 도움이된 상품 리뷰 업데이트
	 * @param request
	 * @param itemReviewId
	 * @return
	 */
	boolean saveItemReviewLike(HttpServletRequest request, int itemReviewId);

	/**
	 * 상품 라벨값 수정
	 * @param itemParam
	 */
	void updateItemLabelValue(ItemParam itemParam);

	/**
	 * 상품 라벨값 삭제
	 * @param itemParam
	 */
	void deleteItemLabelValue(ItemParam itemParam);

	/**
	 * 상품 옵션 목록 조회
	 * @param item
	 * @param isManager
	 * @return
	 */
	List<ItemOption> getItemOptionList(Item item, boolean isManager);
}
