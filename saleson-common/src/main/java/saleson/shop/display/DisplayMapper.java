package saleson.shop.display;

import java.util.HashMap;
import java.util.List;

import saleson.shop.display.domain.*;
import saleson.shop.display.support.DisplayItemParam;
import saleson.shop.display.support.DisplayParam;
import saleson.shop.display.support.DisplaySnsParam;
import saleson.shop.group.domain.Group;
import saleson.shop.item.domain.Item;
import saleson.shop.item.domain.ItemSpot;
import saleson.shop.item.support.ItemParam;

import com.onlinepowers.framework.orm.mybatis.annotation.Mapper;

@Mapper("displayMapper")
public interface DisplayMapper {
	
	/**
	 * 전시 상품 삭제
	 * @param param
	 */
	public void deleteDisplayItemByParam(DisplayItemParam param);
	
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
	 * 스팟 상품 카운트 
	 * @param itemParam
	 * @return
	 */
	public int getItemCountForSpot(ItemParam itemParam);

	/**
	 * 스팟 상품 목록
	 * @param itemParam
	 * @return
	 */
	public List<Item> getItemListForSpot(ItemParam itemParam);

	/**
	 * 스팟 상품 정보 업데이트.
	 * @param item
	 */
	public void updateItemSpot(Item item);
	
	/**
	 * 스팟 전체 할인률 변경
	 * @param itemSpot
	 */
	public void updateAllSpotDiscount(ItemSpot itemSpot);

	/**
	 * 스팟 한 항목 할인률 변경
	 * @param itemSpot
	 */
	public void updateOneSpotDiscount(ItemSpot itemSpot);

	/**
	 * 스팟 회원 그룹 조회
	 * @return
	 */
	public List<Group> getSpotApplyGroup();
	
	/**
	 * 전시 상품 등록
	 * @param displayItem
	 */
	public void insertDisplayItem(DisplayItem displayItem);

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
	 * 전시 이미지 조회
	 * @param displayParam
	 * @return
	 */
	public List<DisplayImage> getDisplayImageListByParam(DisplayParam displayParam);
	
	/**
	 * 전시 이미지 조회 (1개)
	 * @param displayParam
	 * @return
	 */
	public DisplayImage getDisplayImageByParam(DisplayParam displayParam);
	
	/**
	 * 전시 에디터 조회
	 * @param displayParam
	 * @return
	 */
	public List<DisplayEditor> getDisplayEditorListByParam(DisplayParam displayParam);
	
	/**
	 * 전시 에디터 삭제
	 * @param displayParam
	 */
	public void deleteDisplayEditorByParam(DisplayParam displayParam);
	
	/**
	 * 전시 에디터 등록
	 * @param displayEditor
	 */
	public void insertDisplayEditor(DisplayEditor displayEditor);
	
	/**
	 * 전시 이미지 삭제
	 * @param displayParam
	 */
	public void deleteDisplayImageByParam(DisplayParam displayParam);
	
	/**
	 * 전시 이미지 등록
	 * @param displayImage
	 */
	public void insertDisplayImage(DisplayImage displayImage);
	
	/**
	 * 전시 이미지 파일 삭제
	 * @param displayImage
	 */
	public void deleteDisplayImageFile(DisplayImage displayImage);
	
	/**
	 * 하위코드별 전시 상품 정보 조회
	 * @param displayParam
	 * @return
	 */
	public List<HashMap<String,String>> getDisplayItemInfoForSubCodeByParam(DisplayItemParam displayParam);

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
	 * @param displaySns
	 */
	public void updateDisplaySnsOrdering(DisplaySns displaySns);

	/**
	 * FRONT 용 상품 조회 (상품 기본 정보만)
	 * @param param
	 * @return
	 */
	public List<DisplayItem> getFrontDisplayItemListByParam(DisplayItemParam param);
}
