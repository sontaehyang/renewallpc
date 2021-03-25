package saleson.shop.item;

import java.util.List;

import saleson.shop.item.domain.*;
import saleson.shop.item.support.ItemListParam;
import saleson.shop.point.domain.PointConfig;

import com.onlinepowers.framework.orm.mybatis.annotation.MapperBatch;

@MapperBatch("itemMapperBatch")
public interface ItemMapperBatch {

	/**
	 * 카테고리ID로 등록된 모든 데이터를 삭제한다.
	 * @param categoryId
	 */
	void deleteItemOrderingByCategoryId(int categoryId);

	
	/**
	 * ITEM 노출 순서 정보를 저장한다.
	 * @param itemOrdering
	 */
	void insertItemOrdering(ItemOrdering itemOrdering);


	void insertItemOrderingByListParam(ItemListParam itemListParam);


	/**
	 * 상품 카테고리 데이터를 삭제한다. (itemListParam)
	 * @param itemListParam
	 */
	void deleteItemCategoryByListParam(ItemListParam itemListParam);
	
	
	
	/**
	 * 상품 카테고리 데이터를 추가한다. (itemListParam)
	 * @param itemListParam
	 */
	void insertItemCategoryByListParam(ItemListParam itemListParam);
	
	
	/**
	 * 상품 입하예정일 수정
	 * @param item
	 */
	public void updateItemStockScheduleDate(Item item);


	/**
	 * 상품 옵션 입하예정일 수정
	 * @param itemOption
	 */
	public void updateItemOptionStockScheduleDate(ItemOption itemOption);
	

	/**
	 * [엑셀업로드] 상품코드로 상품을 삭제한다. (DATA_STATUS_CODE = '2') 
	 * @param item
	 */
	public void deleteItemByItemUserCode(Item item);


	/**
	 * [엑셀업로드] 상품수정
	 * @param item
	 */
	void updateItemForExcel(Item item);

	
	/**
	 * [엑셀업로드] 상품List로 일괄등록
	 * @param insertItems
	 */
	void insertItemListForExcel(List<Item> insertItems);


	/**
	 * [엑셀업로드] 상품 ID에 해당되는 모든 옵션을 삭제한다.
	 * @param itemId
	 */
	void deleteItemOptionByItemId(int itemId);


	/**
	 * [엑셀업로드] 상품 옵션을 등록한다.
	 * @param itemOption
	 */
	void insertItemOption(ItemOption itemOption);
	
	
	/**
	 * [엑셀업로드] 상품 옵션을 등록한다.
	 * @param itemOption
	 */
	void insertItemOptionListForExcel(List<ItemOption> itemOptions);


	/**
	 * [엑셀업로드] 상품 옵션을 처리 후 상품(ITEM)의 ITEM_OPTION_FLAG를 업데이트 한다..
	 * @param itemOption
	 */
	void updateItemOptionFlag(Item item);


	/**
	 * [엑셀업로드] 상품 ID에 해당되는 모든 이미지를 삭제한다.
	 * @param itemId
	 */
	void deleteItemImageByItemId(int itemId);


	/**
	 * [엑셀업로드] 상품 이미지를 등록한다. (일괄등록)
	 * @param itemOption
	 */
	void insertItemImageListForExcel(List<ItemImage> itemImages);


	/**
	 * [엑셀업로드] 상품 이미지 중 첫번째 항목은 상품 목록 이미지로 업데이트
	 * @param itemImage
	 */
	void updateItemImageInfoByItemImage(ItemImage itemImage);
	
	
	/**
	 * [엑셀업로드] 상품 ID에 해당되는 모든 카테고리 정보를 삭제한다.
	 * @param itemId
	 */
	void deleteItemCategoryByItemId(int itemId);


	/**
	 * [엑셀업로드] 상품 카테고리를 등록한다. (일괄등록)
	 * @param itemCategories
	 */
	void insertItemCategoryListForExcel(List<ItemCategory> itemCategories);


	/**
	 * [엑셀업로드] 상품 ID에 해당되는 모든 추가구성 상품 정보를 삭제한다.
	 * @param itemId
	 */
	void deleteItemAdditionByItemId(int itemId);


	/**
	 * [엑셀업로드] 추가구성 상품을 등록한다. (일괄등록)
	 * @param itemAdditions
	 */
	void insertItemAdditionListForExcel(List<ItemAddition> itemAdditions);


	/**
	 * [엑셀업로드] 추가구성상품을 처리 후 상품(ITEM)의 ITEM_ADDITION_FLAG를 업데이트 한다..
	 * @param item
	 */
	void updateAdditionItemFlag(Item item);


	/**
	 * [엑셀업로드] 상품 ID에 해당되는 모든 관련 상품 정보를 삭제한다.
	 * @param itemId
	 */
	void deleteItemRelationByItemId(int itemId);
	
	
	/**
	 * [엑셀업로드] 관련 상품을 등록한다. (일괄등록)
	 * @param itemRelations
	 */
	void insertItemRelationListForExcel(List<ItemRelation> itemRelations);

	/**
	 * [엑셀업로드] 상품 ID에 해당되는 포인트 설정 정보를 삭제한다.
	 * @param itemId
	 */
	void deleteItemPointConfigByItemId(int itemId);
	
	
	/**
	 * [엑셀업로드] 포인트 설정 정보를 등록한다. (일괄등록)
	 * @param pointConfigures
	 */
	void insertItemPointConfigListForExcel(List<PointConfig> pointConfigures);

	
	/**
	 * [엑셀업로드] 관련상품을 처리 후 상품(ITEM)의 RELATION_ITEM_DISPLAY_TYPE를 업데이트 한다..
	 * @param item
	 */
	void updateRelationItemDisplayType(Item item);


	/**
	 * [엑셀업로드] 상품 ID에 해당되는 상품상세정보(테이블)를 삭제한다.
	 * @param itemId
	 */
	void deleteItemInfoByItemId(int itemId);
	
	
	/**
	 * [엑셀업로드] 상품상세 정보를 등록한다. (일괄등록)
	 * @param itemInfos
	 */
	void insertItemInfoListForExcel(List<ItemInfo> itemInfo);
	
	void insertItemInfo(ItemInfo itemInfo);
	
	/**
	 * [엑셀업로드] 상품 ID에 해당되는 상품상세정보(테이블)를 삭제한다.
	 * @param itemId
	 */
	void deleteItemInfoMobileByItemId(int itemId);



	/**
	 * [엑셀업로드] 상품상세 정보를 등록한다. (일괄등록)
	 * @param itemInfos
	 */
	void insertItemInfoMobileListForExcel(List<ItemInfo> itemInfos);
	
	


	/**
	 * [엑셀업로드] 상품정보 (SUB) 정보를 등록한다. (일괄등록)
	 * @param itemOption
	 */
	void updateItemSubForExcel(ExcelItemSub item);


	/**
	 * [엑셀업로드] 상품정보 (ITEM_CHECK) 정보를 등록한다. (일괄등록)
	 * @param itemOption
	 */
	void updateItemCheckForExcel(ExcelItemCheck item);


	/**
	 * [엑셀업로드] 상품 컨텐츠 정보를 업데이트 한다. (DETAIL_CONTENT_TOP, DETAIL_CONTENT)
	 * @param item
	 */
	void updateItemContentByItemIdForExcel(Item item);


	/**
	 * [엑셀업로드] 모바일용 상품 컨텐츠 정보를 업데이트 한다. (DETAIL_CONTENT_TOP_MOBILE, DETAIL_CONTENT_MOBILE)
	 * @param item
	 */
	void updateItemContentMobileByItemIdForExcel(Item item);


	/**
	 * [엑셀업로드] 상품 사이트내 검색어 정보를 수정한다. (ITEM_KEYWORD)
	 * @param item
	 */
	void updateItemKeywordForExcel(ExcelItemKeyword item);

	
}
