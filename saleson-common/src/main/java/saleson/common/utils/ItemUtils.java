package saleson.common.utils;

import java.util.ArrayList;
import java.util.List;

import com.onlinepowers.framework.util.StringUtils;
import saleson.common.context.ShopContext;
import saleson.shop.group.domain.Group;

import com.onlinepowers.framework.context.ThreadContext;
import com.onlinepowers.framework.repository.Code;
import com.onlinepowers.framework.util.SecurityUtils;
import saleson.shop.item.support.ItemParam;

public class ItemUtils {
	private ItemUtils() {
	}

	private static List<Code> ITEM_PRIVATE_TYPES = new ArrayList<>();
	private static String PREFIX_ROLE = "U";			// 전용상품 코드 prefix - 'U'로 시작되는 코드는 회원 ROLE 과 비교됨.
	private static String PREFIX_CUSTOM_GROUP = "CG";	// 전용상품 코드 prefix = 'CG'로 시작되는 코드는 회원 커스텀 그룹과 비교됨.
	public static final String PREFIX_GROUP = "G";			// 전용상품 코드 prefix - 'G'로 시작되는 코드는 회원 그룹과 비교됨.

	
	static {
		// 기본설정.
		ITEM_PRIVATE_TYPES.add(new Code("일반회원", 	"000", 					""));					// 구분명, 전용상품코드(privateType), 비교 구분값 
		ITEM_PRIVATE_TYPES.add(new Code("로그인회원", 	PREFIX_ROLE + "01", 	"ROLE_USER"));			// 구분명, 전용상품코드(privateType), 비교 구분값
		
		// 커스텀.
		// ITEM_PRIVATE_TYPES.add(new Code("테스트 커스텀",PREFIX_CUSTOM_GROUP + "01", 	"새로운그룹, default"));
	}
	
	/**
	 * 전용구분 항목
	 * 상품 등록 / 수정 시 전용상품 항목을 가져옴.
	 * select box option 구성용  
	 * @return
	 */
	public static List<Code> getPrivateTypeCodes() {
		List<Code> privateTypeCodes = new ArrayList<>();
		
		// 기본 데이터.
		for (Code code : ITEM_PRIVATE_TYPES) {
			privateTypeCodes.add(code);
		}
		
		// 회원 그룹 정보
		ShopContext shopContext = (ShopContext) ThreadContext.get(ShopContext.REQUEST_NAME);

		if(shopContext != null){
			for (Group group : shopContext.getUserGroups()) {
				privateTypeCodes.add(new Code(group.getGroupName(), PREFIX_GROUP + group.getGroupCode(), group.getGroupCode()));
			}
		}

		// 기타 추가 항목 (DB데이터)

		return privateTypeCodes;
	}
	
	
	
	/**
	 * 전용구분 상품 조회용 코드 (상품 목록 조회 시 검색 조건으로 포함)
	 * 
	 * 업체별 설정에 따라 변경이 필요함.
	 * @return
	 */
	public static List<String> getPrivateTypes() {
		List<String> privateTypes = new ArrayList<>();
		
		privateTypes.add("000");		// 00:일반상품
		
		
		List<Code> itemPrivateTypes = getPrivateTypeCodes();
		
		for (Code code : itemPrivateTypes) {
			
			if (code.getValue() == null || code.getDetail() == null) {
				continue;
			}
			
			// 1. 회원 ROLE 체크 
			List<String> userRoles =SecurityUtils.getAuthorities();
			
			if (code.getValue().startsWith(PREFIX_ROLE) && userRoles != null) {
				for (String role : userRoles) {
					if (code.getDetail().indexOf(role) > -1) {
						privateTypes.add(code.getValue());
					}
				}
			}
			
			// 2. 회원 커스텀 그룹 체크
			if (code.getValue().startsWith(PREFIX_CUSTOM_GROUP)
					&& UserUtils.getUserDetail() != null 
					&& !StringUtils.isEmpty(UserUtils.getUserDetail().getGroupCode())
					&& code.getDetail().indexOf(UserUtils.getUserDetail().getGroupCode()) > -1) {
				privateTypes.add(code.getValue());
			}

			// 3. 회원 그룹 체크
			if (code.getValue().startsWith(PREFIX_GROUP)
					&& UserUtils.getUserDetail() != null
					&& !StringUtils.isEmpty(UserUtils.getUserDetail().getGroupCode())
					&& code.getDetail().indexOf(UserUtils.getUserDetail().getGroupCode()) > -1) {
				privateTypes.add(code.getValue());
			}
			
			
			// 4. 추가 검증 로직
		}
		
		return privateTypes;
	}

	/**
	 * 전용등급 이름 조회용 코드
	 * @return
	 */
	public static String getPrivateTypeName(String privateTypeCode) {
		List<Code> itemPrivateTypes = getPrivateTypeCodes();
		String privateTypeName = "";

		for (Code code : itemPrivateTypes) {
			if (code.getValue().equals(privateTypeCode)) {
				privateTypeName = code.getLabel();
				break;
			}
		}

		return privateTypeName;
	}

	/**
	 * 원하는 길이만큼 상품 이름 및 건수 리턴
	 * @param itemName
	 * @param length
	 * @param itemCount
	 * @param isFullItemName
	 * @return
	 */
	public static String getSubstringItemName(String itemName, int length, int itemCount, boolean isFullItemName) {

		if (StringUtils.isNotEmpty(itemName)) {
			String otherItemStr = itemCount > 1 ? " 외" + (itemCount - 1) + "건" : "";

			if (isFullItemName) {
				itemName = itemName + otherItemStr;
			} else if (!isFullItemName && itemName.length() > length){
				itemName = new StringBuilder(itemName.substring(0, length)).append(otherItemStr).toString();
			}
		}

		return itemName;
	}

	/**
	 * 사용자단에 노출될 상품 조회에 필요한 기본적인 itemParam bind
	 * @param itemParam
	 * @return
	 */
	public static ItemParam bindItemParam(ItemParam itemParam) {

		// 공개 상품만
		itemParam.setConditionType("FRONT_DISPLAY_ITEM");

		// 기본 정렬 조건
		if (itemParam.getOrderBy() == null || itemParam.getOrderBy().equals("")) {
			itemParam.setOrderBy("ORDERING");
			itemParam.setSort("ASC");
		}

		// 리스트 타입.
		if (itemParam.getListType() == null || itemParam.getListType().equals("")) {
			itemParam.setListType("a");
		}

		itemParam.setDataStatusCode("1");
		if (itemParam.getItemsPerPage() < 10) {
			itemParam.setItemsPerPage(10);
		}

		if (!UserUtils.isManagerLogin()) {
			// 전용상품 조회
			itemParam.setPrivateTypes(getPrivateTypes());
		}

		return itemParam;
	}
}
