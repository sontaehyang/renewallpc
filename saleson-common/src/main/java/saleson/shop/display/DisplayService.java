package saleson.shop.display;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.ui.Model;
import saleson.shop.display.domain.*;
import saleson.shop.display.support.DisplayItemParam;
import saleson.shop.display.support.DisplayParam;
import saleson.shop.display.support.DisplaySnsParam;
import saleson.shop.group.domain.Group;
import saleson.shop.item.domain.Item;
import saleson.shop.item.domain.ItemSpot;
import saleson.shop.item.support.ItemListParam;
import saleson.shop.item.support.ItemParam;


public interface DisplayService {
	
	/**
	 * 전시 상품 조회 - 관리자용
	 * @param param
	 * @return
	 */
	public List<DisplayItem> getDisplayItemListByParamForManager(DisplayItemParam param);
	
	
	/**
	 * 그룹 코드별 전시 상품 조회
	 * @param displayGroupCode
	 * @return
	 */
	public List<DisplayItem> getDisplayItemList(String displayGroupCode);

	/**
	 * 그룹 코드별 전시 상품 조회
	 * @param displayGroupCode
	 * @param displaySubCode
	 * @return
	 */
	public List<DisplayItem> getDisplayItemList(String displayGroupCode, String displaySubCode);

	
	/**
	 * 전시 상품 조회
	 * @param param
	 * @return
	 */
	public List<DisplayItem> getDisplayItemListByParam(DisplayItemParam param);
	
	/**
	 * 전시 상품 조회  COUNT
	 * @param param
	 * @return
	 */
	public int getDisplayItemListCountByParam(DisplayItemParam param);
	
	/**
	 * 그룹코드에 존재하는 하위 코드 개수
	 * @param displayGroupCode
	 * @return
	 */
	public int getDisplayItemSubCodeCountByGroupCode(String displayGroupCode);
	
	/**
	 * 전시 상품 등록
	 * @param param
	 */
	public void insertDisplayItem(DisplayItemParam param);
	
	/**
	 * 스팟 상품 카운트 
	 * @param itemParam
	 * @return
	 */
	public int getItemCountForSpot(ItemParam itemParam);
	
	/**
	 * 메인 스팟 상품 목록
	 * @param limit
	 * @return
	 */
	public List<Item> getMainSpotItems(int limit);

	/**
	 * 스팟 상품 목록
	 * @param itemParam
	 * @return
	 */
	public List<Item> getItemListForSpot(ItemParam itemParam);

	/**
	 * 스팟 상품 업데이트.
	 * @param item
	 * @param itemSpot
	 */
	public void updateItemSpot(Item item, ItemSpot itemSpot);

	/**
	 * 스팟 상품 선택 삭제.
	 * @param itemListParam
	 */
	public void deleteItemSpotFromListData(ItemListParam itemListParam);

	/**
	 * 스팟 회원 그룹 조회.
	 */
	public List<Group> getSpotApplyGroup();
	
	/**
	 * 스팟 전체 일괄 할인률 변경.
	 * @param itemSpot
	 */
	public void updateAllSpotDiscount(ItemSpot itemSpot);
	
	/**
	 * 스팟 한 항목 일괄 할인률 변경.
	 * @param itemSpot
	 */
	public void updateOneSpotDiscount(ItemSpot itemSpot);
	
	/**
	 * 해당 코드에 따른 화면관리 정보 목록 조회
	 * @param displayGroupCode
	 * @return
	 */
	public DisplayTemplate getDisplayTemplateByGroupCode(String displayGroupCode);
	
	/**
	 * 해당 코드 정보 조회
	 * @param displayGroupCode
	 * @return
	 */
	public DisplayGroupCode getDisplayGroupCodeByGroupCode(String displayGroupCode);
	
	/**
	 *  해당 코드에 따른 화면 전시 정보 데이터 업데이트
	 * @param sendData
	 */
	public void updateDisplayDataByGroupCode(DisplaySendData sendData);
	
	/**
	 * 전시 이미지 조회
	 * @param displayParam
	 * @return
	 */
	public List<DisplayImage> getDisplayImageListByParam(DisplayParam displayParam);
	
	/**
	 * 전시 에디터 조회
	 * @param displayParam
	 * @return
	 */
	public List<DisplayEditor> getDisplayEditorListByParam(DisplayParam displayParam);
	

	/**
	 * 전시 이미지 파일 삭제
	 * @param displayImage
	 */
	void deleteDisplayImageFile(DisplayImage displayImage);
	
	/**
	 * 그룹코드별 전시정보 가져오기
	 * @param displayGroupCode
	 * @param viewTarget
	 * @return
	 */
	public Display getDisplayByGroupCode(String displayGroupCode ,String viewTarget);
	
	/**
	 * 등록된 상품수 조회
	 * @param groupCode
	 * @param subCode
	 * @return
	 */
	public HashMap<String,String> getTotalDisplayItemCountForSubCode(String groupCode, String subCode);
	
	/**
	 * 등록된 상품중 품절 상품수 조회
	 * @param groupCode
	 * @param subCode
	 * @return
	 */
	public HashMap<String,String> getSoldOutDisplayItemCountForSubCode(String groupCode, String subCode);
	
	/**
	 * 등록된 상품중 미전시 상품수 조회
	 * @param groupCode
	 * @param subCode
	 * @return
	 */
	public HashMap<String,String> getNotDisplayDisplayItemCountForSubCode(String groupCode, String subCode);

	/**
	 * 전시용 SNS 등록
	 * @param displaySns
	 */
	public void insertDisplaySns(DisplaySns displaySns);

	/**
	 * 전시용 SNS 수정
	 * @param displaySns
	 */
	public void updateDisplaySns(DisplaySns displaySns);

	/**
	 * 전시용 SNS 선택 삭제
	 * @param displaySnsParam
	 */
	public void deleteDisplaySnsByIds(DisplaySnsParam displaySnsParam);

	/**
	 * 전시용 SNS 카운트
	 * @param displaySnsParam
	 * @return
	 */
	public int getDisplaySnsCount(DisplaySnsParam displaySnsParam);

	/**
	 * 전시용 SNS 목록 조회
	 * @param displaySnsParam
	 * @return
	 */
	public List<DisplaySns> getDisplaySnsList(DisplaySnsParam displaySnsParam);

	/**
	 * 전시용 SNS 조회
	 * @param snsId
	 * @return
	 */
	public DisplaySns getDisplaySnsById(int snsId);

	/**
	 * 전시용 SNS 노출 순서 설정
	 * @param displaySnsParam
	 */
	public void updateDisplaySnsOrdering(DisplaySnsParam displaySnsParam);

	public void setMainDisplayByGroupCode(Model model, String... groupCodes);

	/**
	 * FRONT 용 상품 조회 (상품 기본 정보만)
	 * @param param
	 * @return
	 */
	public List<DisplayItem> getFrontDisplayItemListByParam(DisplayItemParam param);
}
