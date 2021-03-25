package saleson.common.utils;

import com.onlinepowers.framework.context.RequestContext;
import com.onlinepowers.framework.context.ThreadContext;
import com.onlinepowers.framework.context.util.RequestContextUtils;
import com.onlinepowers.framework.repository.Code;
import com.onlinepowers.framework.repository.CodeInfo;
import com.onlinepowers.framework.util.*;
import com.onlinepowers.framework.web.pagination.Pagination;
import net.coobird.thumbnailator.Thumbnails;
import net.sf.json.JSONException;
import org.apache.commons.lang.ArrayUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import saleson.common.Const;
import saleson.common.context.ShopContext;
import saleson.common.enumeration.DeviceType;
import saleson.model.GiftItem;
import saleson.model.OrderGiftItem;
import saleson.shop.cart.domain.OrderQuantity;
import saleson.shop.categories.domain.*;
import saleson.shop.config.domain.Config;
import saleson.shop.coupon.domain.Coupon;
import saleson.shop.coupon.domain.OrderCoupon;
import saleson.shop.giftitem.domain.GiftItemInfo;
import saleson.shop.item.domain.Item;
import saleson.shop.item.domain.ItemOption;
import saleson.shop.order.domain.BuyItem;
import saleson.shop.order.domain.OrderItem;
import saleson.shop.order.support.OrderException;
import saleson.shop.point.domain.PointPolicy;
import saleson.shop.seo.domain.Seo;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Component
public class ShopUtils {

	private static String MOBILE_PREFIX;
	private static String NOIMAGE_PATH;
	private static String BANK_POSTFIX = "_BANK_LIST";

	private ShopUtils() {
	}

	@Value("${mobile.prefix}")
	public void setMobilePrefix(String mobilePrefix) {
		MOBILE_PREFIX = mobilePrefix;
	}
	@Value("${noimage.path}")
	public void setNoimagePath(String noimagePath) {
		NOIMAGE_PATH = noimagePath;
	}

	/**
	 * 적용 포인트를 반환
	 * @param sumItemPrice
	 * @param pointPolicy
	 * @return
	 */
	public static int getEarnPoint(int sumItemPrice, PointPolicy pointPolicy) {
		if (pointPolicy.getPointType().equals("1")) { // 비율 ((반올림)(상품금액) * 0.01(1%)) * 수량
			return new BigDecimal(sumItemPrice)
					.multiply(BigDecimal.valueOf(pointPolicy.getPoint()).divide(new BigDecimal("100")))
					.setScale(0, BigDecimal.ROUND_DOWN).intValue();
		} else { // 금액
			return (int) pointPolicy.getPoint();
		}
	}

	/**
	 * 24 시간대를 리턴한다.
	 * 00 ~ 23 
	 * @return
	 */
	public static List<Code> getHours() {
		List<Code> hours = new ArrayList<>();
		for (int i = 0; i < 24; i++) {
			String hour = StringUtils.lPad(Integer.toString(i), 2, '0');
			hours.add(new Code(hour, hour));
		}
		return hours;
	}




	/**
	 * ITEM 정보로 이미지 URL을 가져온다.
	 * opshop.tld에서 사용
	 * @param position 위치 ('list', 'catalog', 'details')
	 * @param itemId
	 * @param filename
	 * @param detailsType 상세이미지 옵션 ('', 'thumb', 'big')
	 * @return
	 */
	public static String image(String position, String itemId, String filename, String detailsType) {
		if (filename == null || filename.indexOf("/") > -1) {
			return filename;
		}

		if (!detailsType.equals("")) {
			detailsType = "/" + detailsType;
		}

		//return "/upload/item/" + itemId + "/" + position + detailsType + "/" + filename;
		return "/upload/item/" + itemId + "/" + filename;
	}

	public static String image(String position, String itemId, String filename) {
		return image(position, itemId, filename, "");
	}

	/**
	 * 카탈로그 이미지 경로를 가져온다.
	 * @param itemId
	 * @param filename
	 * @return
	 */
	public static String catalogImage(String itemId, String filename) {
		return image("catalog", itemId, filename);
	}


	/**
	 * 목록 이미지 경로를 가져온다. 
	 * (shop.tld에서 사용)
	 *
	 * @param itemId
	 * @param filename
	 * @return
	 */
	public static String listImage(String itemId, String filename) {
		return image("list", itemId, filename);
	}


	/**
	 * 상세 이미지 경로를 가져온다.
	 * (shop.tld에서 사용)
	 * @param itemId
	 * @param filename
	 * @return
	 */
	public static String detailsImage(String itemId, String filename) {
		return image("details", itemId, filename);
	}


	/**
	 * 상세이미지 섬네일 경로를 가져온다.
	 * (shop.tld에서 사용)
	 * @param itemId
	 * @param filename
	 * @return
	 */
	public static String detailsThumbnail(String itemId, String filename) {
		return image("details", itemId, filename, "thumb");
	}


	/**
	 * 상세이미지의 확대이미지 경로를 가져온다.
	 * (shop.tld에서 사용)
	 * @param itemId
	 * @param filename
	 * @return
	 */
	public static String detailsBigImage(String itemId, String filename) {
		return image("details", itemId, filename, "big");
	}

	/**
	 * Text를 구분자로 구분하여 HashMap으로 만듬. (\n 으로 목록 구분)
	 * @param body
	 * @param c
	 * @return
	 */
	public static HashMap<String, Object> textToHashMapForReadToassign(String body, String c) {
		HashMap<String, Object> map = new HashMap<>();
		if (c == null) c = "->";

		String[] array = StringUtils.delimitedListToStringArray(body, "\n");

		if (array.length > 0) {
			for(String s : array) {
				String[] t = StringUtils.delimitedListToStringArray(s, c);

				if (t.length == 2) {
					String key = t[0];
					key = key.replace(" ", "");
					key = key.replace("-", "_").trim();

					String value = t[1];
					value = value.replace(" ", "").trim();
					map.put(key, value);
				}
			}
		}

		return map;
	}


	/**
	 * SEO 정보를 설정한다.
	 * @param seo
	 */
	public static void setSeo(Seo seo) {
		ShopContext shopContext = (ShopContext) ThreadContext.get(ShopContext.REQUEST_NAME);
		shopContext.setSeo(seo);
		ThreadContext.put(ShopContext.REQUEST_NAME, shopContext);
	}


	/**
	 * 상점 설정 정보를 리턴한다.
	 */
	public static Config getConfig() {
		ShopContext shopContext = (ShopContext) ThreadContext.get(ShopContext.REQUEST_NAME);
		return shopContext.getConfig();
	}

	/**
	 * 옵션의 재고량
	 * @param stockMap
	 * @param option
	 * @param maxQuantity
	 * @param sellerId
	 */
	public static void getItemOptionStockMap(HashMap<String, HashMap<String, Integer>> stockMap, ItemOption option, int maxQuantity, int buyQuantity, long sellerId) {

		int stockQuantity = option.getOptionStockQuantity();

		String stockKey = getItemOptionStockKey(option, stockQuantity, sellerId);

		if (!"".equals(stockKey)) {

			// 최대 구매 가능 수량이 재고량보다 작은경우 재고량을 최대 구매 가능 수량으로 변경함
			// 단, 최대 구매 가능수량 재한이 없는경우 (-1) 무시
			if (maxQuantity >= 0 && stockQuantity > maxQuantity) {
				stockQuantity = maxQuantity;
			}

			HashMap<String, Integer> info = stockMap.get(stockKey);
			if (info == null) {
				info = new HashMap<>();
				info.put("totalBuyQuantity", 0);
			}

			int totalBuyQuantity = (int) info.get("totalBuyQuantity");

			info.put("totalBuyQuantity", totalBuyQuantity + buyQuantity);
			info.put("stockQuantity", stockQuantity);
			stockMap.put(stockKey, info);
		}
	}

	/**
	 * 상품의 재고량
	 * @param stockMap
	 * @param item
	 * @param maxQuantity
	 * @return
	 */
	public static void getItemStockMap(HashMap<String, HashMap<String, Integer>> stockMap, Item item, int maxQuantity, int buyQuantity) {

		int stockQuantity = item.getStockQuantity();
		String stockKey = getItemStockKey(item, stockQuantity);

		if (!"".equals(stockKey)) {
			// 최대 구매 가능 수량이 재고량보다 작은경우 재고량을 최대 구매 가능 수량으로 변경함
			// 단, 최대 구매 가능수량 재한이 없는경우 (-1) 무시
			if (maxQuantity >= 0 && stockQuantity > maxQuantity) {
				stockQuantity = maxQuantity;
			}

			HashMap<String, Integer> info = stockMap.get(stockKey);
			if (info == null) {
				info = new HashMap<>();
				info.put("totalBuyQuantity", 0);
			}

			int totalBuyQuantity = (int) info.get("totalBuyQuantity");

			info.put("totalBuyQuantity", totalBuyQuantity + buyQuantity);
			info.put("stockQuantity", stockQuantity);
			stockMap.put(stockKey, info);
		}
	}

	/**
	 * 상품의 재고량 연동 코드
	 * @param item
	 * @param stockQuantity
	 * @return
	 */
	public static String getItemStockKey(Item item, int stockQuantity) {

		// 재고량 연동 안함 - 무제한
		if ("N".equals(item.getStockFlag()) || stockQuantity == -1) {
			return "";
		}

		String stockKey = "ITEM||" + item.getItemId();
		if (StringUtils.isNotEmpty(item.getStockCode())) {
			stockKey = "STOCK||" + item.getSellerId() + "||" + item.getStockCode();
		}

		return stockKey;
	}

	public static String getItemOptionStockKey(ItemOption option, int stockQuantity, long sellerId) {
		// 재고량 연동 안함 - 무제한
		if ("N".equals(option.getOptionStockFlag()) || stockQuantity == -1) {
			return "";
		}

		String stockKey = "OPTION||" + option.getItemOptionId();
		if (StringUtils.isNotEmpty(option.getOptionStockCode())) {
			stockKey = "STOCK||" + sellerId + "||" + option.getOptionStockCode();
		}

		return stockKey;
	}

	/**
	 * 구매 상품의 재고량을 최대 구매 가능 수량과 연동하여 결정함
	 * @param buyItems
	 * @return
	 */
	public static HashMap<String, HashMap<String, Integer>> makeStockMap(List<BuyItem> buyItems) {
		HashMap<String, HashMap<String, Integer>> stockMap = new HashMap<String, HashMap<String, Integer>>();

		if (buyItems == null) {
			return null;
		}

		for(BuyItem buyItem : buyItems) {
			Item item = buyItem.getItem();
			int buyQuantity = buyItem.getItemPrice().getQuantity();
			int maxQuantity = item.getOrderMaxQuantity();

			if ("N".equals(item.getItemOptionFlag())) {

				getItemStockMap(stockMap, item, maxQuantity, buyQuantity);

			} else {
				List<ItemOption> itemOptions = buyItem.getOptionList();
				if (ValidationUtils.isNotNull(itemOptions)) {
					for(ItemOption itemOption : itemOptions) {
						if ("T".equals(itemOption.getOptionType())) {

							getItemStockMap(stockMap, item, maxQuantity, buyQuantity);

						} else {

							getItemOptionStockMap(stockMap, itemOption, maxQuantity, buyQuantity, item.getSellerId());

						}

					}
				}
			}
		}

		return stockMap;
	}

	/**
	 * 최대 구매 가능 수량
	 * @param minQuantity
	 * @param maxQuantity
	 * @param buyQuantity
	 * @param stockInfo
	 * @return
	 */
	public static int getMaxQuantity(int minQuantity, int maxQuantity, int buyQuantity, HashMap<String, Integer> stockInfo) {

		// 재고 정보가 없는경우 무제한 이지만 최대 구매 가능 수량으로 체크
		if (stockInfo == null) {

			return maxQuantity == -1 ? -1 : maxQuantity;

		} else {

			int stockQuantity = (int) stockInfo.get("stockQuantity");
			int totalBuyQuantity = (int) stockInfo.get("totalBuyQuantity");

			if (stockQuantity == 0) {
				return 0;
			}

			// 구매 수량 + 재고 잔여 수량 (전체 재고 - 전체 구매 수량)
			stockQuantity = buyQuantity + (stockQuantity - totalBuyQuantity < 0 ? 0 : stockQuantity - totalBuyQuantity);

			// 재고 수량보다 최대 구매 가능 수량이 작으면 최대 구매 가능 수량으로..
			if (stockQuantity > maxQuantity && maxQuantity != -1) {

				return maxQuantity;

			}

			return stockQuantity;
		}
	}

	/**
	 * 해당 함수를 사용하기 전에 반드시 getStockQuantity를 이용해서 구매 리스트의 재고량 데이터를 만들어 넘겨야 한다.
	 * @param buyItem
	 * @param stockMap
	 * @param buyQuantityMap
	 */
	public static void getItemMaxQuantity(BuyItem buyItem, HashMap<String, HashMap<String, Integer>> stockMap, HashMap<String, Integer> buyQuantityMap) {
		OrderQuantity orderQuantity = new OrderQuantity();

		Item item = buyItem.getItem();
		int buyQuantity = buyItem.getItemPrice().getQuantity();
		int minQuantity = item.getOrderMinQuantity() <= 0 ? 1 : item.getOrderMinQuantity();

		int maxQuantity = !"0".equals(item.getSoldOut()) ? 0 : item.getOrderMaxQuantity();
		orderQuantity.setItemId(item.getItemId());
		orderQuantity.setMinQuantity(minQuantity);
		orderQuantity.setMaxQuantity(maxQuantity);

		// 품절?
		if (maxQuantity == 0) {
			buyItem.setOrderQuantity(orderQuantity);
			return;
		}

		if ("N".equals(item.getItemOptionFlag())) {

			String stockKey = getItemStockKey(item, item.getStockQuantity());
			HashMap<String, Integer> stockInfo = !"".equals(stockKey) ? stockMap.get(stockKey) : null;

			maxQuantity = getMaxQuantity(minQuantity, maxQuantity, buyQuantity, stockInfo);

			if (buyQuantityMap != null) {
				if(buyQuantityMap.get(stockKey) != null) {
					int totalBuyQuantity = (Integer) buyQuantityMap.get(stockKey);

					buyQuantityMap.put(stockKey, totalBuyQuantity + buyQuantity);
				} else {
					buyQuantityMap.put(stockKey, buyQuantity);
				}
			}

			if (orderQuantity.getMaxQuantity() == -1 || orderQuantity.getMaxQuantity() > maxQuantity) {
				orderQuantity.setMaxQuantity(maxQuantity);
			}

		} else {
			List<ItemOption> itemOptions = buyItem.getOptionList();
			int optionIndex = 0;
			if (ValidationUtils.isNotNull(itemOptions)) {
				for(ItemOption itemOption : itemOptions) {
					if ("T".equals(itemOption.getOptionType())) {

						int stockQuantity = item.getStockQuantity();
						String stockKey = getItemStockKey(item, stockQuantity);
						HashMap<String, Integer> stockInfo = !"".equals(stockKey) ? stockMap.get(stockKey) : null;
						maxQuantity = getMaxQuantity(minQuantity, maxQuantity, buyQuantity, stockInfo);

						// 옵션 구성품 중에 재고가 없는게 있으면 종료해버려!
						if (maxQuantity == 0) {
							orderQuantity.setMaxQuantity(0);
							break;
						}

						// 처음 도는 옵션은 무조건 일단 넣고 보자~
						if (optionIndex == 0) {
							orderQuantity.setMaxQuantity(maxQuantity);
						} else {

							if (orderQuantity.getMaxQuantity() == -1 || orderQuantity.getMaxQuantity() > maxQuantity) {
								orderQuantity.setMaxQuantity(maxQuantity);
							}

						}

						if (buyQuantityMap != null) {
							if(buyQuantityMap.get(stockKey) != null) {
								int totalBuyQuantity = (Integer) buyQuantityMap.get(stockKey);

								buyQuantityMap.put(stockKey, totalBuyQuantity + buyQuantity);
							} else {
								buyQuantityMap.put(stockKey, buyQuantity);
							}
						}

					} else {

						int stockQuantity = itemOption.getOptionStockQuantity();
						String stockKey = getItemOptionStockKey(itemOption, stockQuantity, item.getSellerId());
						HashMap<String, Integer> stockInfo = !"".equals(stockKey) ? stockMap.get(stockKey) : null;
						maxQuantity = getMaxQuantity(minQuantity, maxQuantity, buyQuantity, stockInfo);

						// 옵션 구성품 중에 재고가 없는게 있으면 종료해버려!
						if (maxQuantity == 0) {
							orderQuantity.setMaxQuantity(0);
							break;
						}

						// 처음 도는 옵션은 무조건 일단 넣고 보자~
						if (optionIndex == 0) {
							orderQuantity.setMaxQuantity(maxQuantity);
						} else {

							if (orderQuantity.getMaxQuantity() == -1 || orderQuantity.getMaxQuantity() > maxQuantity) {
								orderQuantity.setMaxQuantity(maxQuantity);
							}

						}

						if (buyQuantityMap != null) {
							if(buyQuantityMap.get(stockKey) != null) {
								int totalBuyQuantity = (Integer) buyQuantityMap.get(stockKey);

								buyQuantityMap.put(stockKey, totalBuyQuantity + buyQuantity);
							} else {
								buyQuantityMap.put(stockKey, buyQuantity);
							}
						}

					}

					optionIndex++;
				}
			}
		}

		buyItem.setOrderQuantity(orderQuantity);
	}

	/**
	 * 상품의 옵션 구성품 정보를 구성
	 * @param item
	 * @param options
	 * @return
	 */
	public static List<ItemOption> getRequiredItemOptions(Item item, String options) {

		if ("N".equals(item.getItemOptionFlag())) {
			return null;
		}

		if (item.getItemOptions() == null) {
			return null;
		}

		int optionCount = 0;
		if (item.getItemOptions().size() > 0) {
			optionCount = 1;

			/// S(선택형), C(옵션조합형) 인 경우 복합(셋트) 상품인지 체크
			if ("S".equals(item.getItemOptionType()) || "C".equals(item.getItemOptionType())) {

				HashMap<String, String> map = new HashMap<>();
				for(ItemOption itemOption : item.getItemOptions()) {

					if (map.get(itemOption.getOptionName1()) == null) {
						map.put(itemOption.getOptionName1(), itemOption.getOptionName1());
					}
				}

				optionCount = map.keySet().size();
			} else if ("T".equals(item.getItemOptionType())) {
				optionCount = item.getItemOptions().size();
			} else {
				for(ItemOption itemOption : item.getItemOptions()) {

					// 옵션에 택스트형이 추가된경우 택스트형만큼 옵션수 증가
					if ("T".equals(itemOption.getOptionType())) {
						optionCount++;
					}
				}
			}
		}

		List<ItemOption> returnList = new ArrayList<>();
		if (StringUtils.isNotEmpty(options)) {

			String[] optionList = StringUtils.delimitedListToStringArray(options, "^^^");
			for(String itemOptionText : optionList) {

				for(ItemOption itemOption : item.getItemOptions()) {
					String option = ShopUtils.makeOptionText(item, itemOption, "");

					if ("T".equals(itemOption.getOptionType())) {
						if (itemOptionText.startsWith(option)) {

							returnList.add(itemOption);
							break;
						}
					} else {
						if (itemOptionText.equals(option)) {
							returnList.add(itemOption);
							break;
						}
					}
				}

			}
		}

		if (optionCount != returnList.size()) {
			return null;
		}

		return returnList;
	}

	/**
	 * 옵션 구성별 문자열을 생성
	 * @param item
	 * @param itemOption
	 * @param optionText
	 * @return
	 */
	public static String makeOptionText(Item item, ItemOption itemOption, String optionText) {
		String option = "";
		boolean addPrice = true;
		if ("S".equals(itemOption.getOptionType())) {
			option = "S||" + itemOption.getOptionName1() + "||" + itemOption.getOptionName2();
		} else if ("S2".equals(itemOption.getOptionType())) {
			option = "S2||" + item.getItemOptionTitle1() + "||" + itemOption.getOptionName1() + "||" + item.getItemOptionTitle2() + "||" + itemOption.getOptionName2();
		} else if ("S3".equals(itemOption.getOptionType())) {
			option = "S3||" + item.getItemOptionTitle1() + "||" + itemOption.getOptionName1() + "||" + item.getItemOptionTitle2() + "||" + itemOption.getOptionName2() + "||" + item.getItemOptionTitle3() + "||" + itemOption.getOptionName3();
		} else if ("T".equals(itemOption.getOptionType())) {
			option = "T||" + itemOption.getOptionName1();
			if (StringUtils.isNotEmpty(optionText)) {
				option += "||" + optionText;
			} else {
				addPrice = false;
			}
		} else if ("C".equals(itemOption.getOptionType())) {
			option = "C||" + itemOption.getOptionName1() + "||" + itemOption.getOptionName2();
		}

		if (addPrice) {
			option += "||" + itemOption.getExtraPrice();
		}

		option += "||" + itemOption.getOptionStockCode();
		option += "||" + itemOption.getOptionCostPrice();
		option += "||" + itemOption.getOptionQuantity();

		return option;
	}

	/**
	 * 상품의 동시 구매 가능 상태를 체크 (재고, 동시 구매 가능 상품 여부등)
	 * @param list
	 * @param inventoryVerificationCount
	 * @return
	 */
	public static void buyVerification(List<BuyItem> list, int inventoryVerificationCount) {
		if (list.isEmpty()) {
			throw new OrderException(MessageUtils.getMessage("M00419")); // 장바구니에 담긴 상품이 없습니다.
		}

		// 상품 타입을 검사
		String itemType = "";
		int index = 0;
		for(BuyItem buyItem : list) {
			Item item = buyItem.getItem();

			if (index == 0) {
				itemType = item.getItemType();
			}

			if (!itemType.equals(item.getItemType())) {
				throw new OrderException(MessageUtils.getMessage("M00482"));	// 배송방법이 다른상품이 장바구니에 들어 있기 때문에 장바구니내의 상품을 전부 결제하십시오.
			}

			index++;
		}

		// 재고량을 검사
		int successCount = 0;
		for(BuyItem buyItem : list) {

			Item item = buyItem.getItem();
			OrderQuantity orderQuantity = buyItem.getOrderQuantity();
			int buyQuantity = buyItem.getItemPrice().getQuantity();

			// 판매 불가
			if ("N".equals(buyItem.getAvailableForSaleFlag())) {
				throw new OrderException(buyItem.getItem().getItemName() + MessageUtils.getMessage("M00484"));	// 상품의 정보가 변경되어 구매가 불가능합니다.
			}

			// 상품에 옵션이 있고 상품 옵션 구성이 null인경우 상품의 구성이 변경됨..
			if ("Y".equals(item.getItemOptionFlag())) {
				if (item.getItemOptions() != null && buyItem.getOptionList() == null) {
					throw new OrderException(item.getItemName() + "상품의 옵션 구성이 변경되었습니다.");
				}
			}

			if (StringUtils.isNotEmpty(buyItem.getOptions())) {
				// 장바구니에는 상품의 옵션정보가 선택되어있으나 상품의 구성이 변경됨.. ex) 상품의 옵션 삭제등..
				if (buyItem.getOptionList() == null) {
					throw new OrderException(item.getItemName() + "상품의 옵션 구성이 변경되었습니다.");
				}
			}

			// 최대 구매가능 수량이 0이면 재고가 없거나 품절된 상품임
			if (orderQuantity.getMaxQuantity() == 0) {
				throw new OrderException(MessageUtils.getMessage("M00483")); // 이쪽의 상품은 재고가 없습니다.
			}

			// 최대 구매가능 수량이 -1인경우 무제한 상품
			if (orderQuantity.getMaxQuantity() != -1) {

				// 옵션이 없는 상품 검사 - 상품 ID가 같은 상품의 주문 수량을 더하여 해당 상품의 주문 가능 상태를 채크함
				if ("N".equals(item.getItemOptionFlag())) {

					if ("Y".equals(item.getStockFlag())) {

						int sumBuyQuantity = 0;
						for(BuyItem checkBuyItem : list) {

							Item checkItem = checkBuyItem.getItem();
							if ("N".equals(checkItem.getItemOptionFlag())) {
								if (buyItem.getItemId() == checkBuyItem.getItemId()) {
									sumBuyQuantity += checkBuyItem.getItemPrice().getQuantity();
								}
							}

						}

						if (sumBuyQuantity > item.getStockQuantity()) {
							throw new OrderException(MessageUtils.getMessage("M00483")); // 이쪽의 상품은 재고가 없습니다.
						}
					} else {
						if (buyQuantity > orderQuantity.getMaxQuantity()) {
							throw new OrderException(item.getItemName() + "상품의 최대 구매가능수량을 확인해주세요.");
						}
					}

				} else {
					// 옵션이 있는 상품 검사 - 옵션 ID가 같은 상품들의 재고량을 더하여 해당 상품의 주문 가능 상태를 채크

					for(ItemOption option : buyItem.getOptionList()) {

						if ("T".equals(option.getOptionType())) {
							if ("Y".equals(item.getStockFlag())) {

								int sumBuyQuantity = 0;
								for(BuyItem checkBuyItem : list) {

									Item checkItem = checkBuyItem.getItem();
									if ("N".equals(checkItem.getItemOptionFlag())) {
										if (buyItem.getItemId() == checkBuyItem.getItemId()) {
											sumBuyQuantity += checkBuyItem.getItemPrice().getQuantity();
										}
									}

								}

								if (sumBuyQuantity > item.getStockQuantity()) {
									throw new OrderException(MessageUtils.getMessage("M00483")); // 이쪽의 상품은 재고가 없습니다.
								}
							} else {
								if (buyQuantity > orderQuantity.getMaxQuantity()) {
									throw new OrderException(item.getItemName() + "상품의 최대 구매가능수량을 확인해주세요.");
								}
							}
						} else {
							if ("Y".equals(option.getOptionStockFlag())) {

								int sumBuyQuantity = 0;
								for(BuyItem checkBuyItem : list) {

									Item checkItem = checkBuyItem.getItem();
									if ("Y".equals(checkItem.getItemOptionFlag())) {
										for(ItemOption checkOption : checkBuyItem.getOptionList()) {
											if (checkOption.getItemOptionId() == option.getItemOptionId()) {
												sumBuyQuantity = sumBuyQuantity + checkBuyItem.getItemPrice().getQuantity();
											}
										}
									}
								}

								if (sumBuyQuantity > option.getOptionStockQuantity()) {
									throw new OrderException(MessageUtils.getMessage("M00483")); // 이쪽의 상품은 재고가 없습니다.
								}
							} else {
								if (buyQuantity > orderQuantity.getMaxQuantity()) {
									throw new OrderException(item.getItemName() + "상품의 최대 구매가능수량을 확인해주세요.");
								}
							}
						}
					}
				}
			}

			successCount++;
		}

		if (!(inventoryVerificationCount == successCount)) {
			throw new OrderException(MessageUtils.getMessage("M00486"));	// 주문 상품 처리중 오류가 발생 하였습니다. \n주문을 다시 시도해 주세요.
		}
	}

	/**
	 * 상품에 적용되는 쿠폰들 쿠폰 사용 가능한지 체크
	 * @param orderCouponUser
	 * @param buyItem
	 * @return
	 */
	public static int getCouponDiscountPriceForItemCoupon(OrderCoupon orderCouponUser, BuyItem buyItem) {

		int discountPrice = 0;

		// CJH 2016.04.05 - 개당 상품 가격을 할인 기준금액으로 설정
		int discountTargetAmount = buyItem.getItemPrice().getSumPrice();

		// 적용가능 상품 개당가격
		if (orderCouponUser.getCouponPayRestriction() > 0) {
			if (discountTargetAmount < orderCouponUser.getCouponPayRestriction()) {
				return 0;
			}
		}

		if (orderCouponUser.getCouponPayType().equals("1")) { // 원
			discountPrice = orderCouponUser.getCouponPay();
		} else { // %
			discountPrice = (new BigDecimal(discountTargetAmount).multiply(new BigDecimal(orderCouponUser.getCouponPay()).divide(new BigDecimal("100")))).intValue();

			if (orderCouponUser.getCouponDiscountLimitPrice() > 0) {
				if (discountPrice > orderCouponUser.getCouponDiscountLimitPrice()) {
					discountPrice = orderCouponUser.getCouponDiscountLimitPrice();
				}
			}
		}

		if (discountPrice > discountTargetAmount) {
			discountPrice = 0;
		}

		return discountPrice;
	}

	/**
	 * 장바구니 쿠폰 사용 가능여부 체크
	 * @param orderCouponUser
	 * @param totalPayAmount
	 * @return
	 */
	public static int getCouponDiscountAmountForCartCoupon(OrderCoupon orderCouponUser, int totalPayAmount) {
		int discountAmount = 0;

		if (orderCouponUser.getCouponPayType().equals("1")) { //원
			discountAmount = orderCouponUser.getCouponPay();
		} else {
			discountAmount = (new BigDecimal(totalPayAmount).multiply(new BigDecimal(orderCouponUser.getCouponPay()).divide(new BigDecimal("100")))).intValue();
		}

		if (!StringUtils.isEmpty(orderCouponUser.getCouponPayRestriction())) {
			if (orderCouponUser.getCouponPayRestriction() > totalPayAmount) {
				discountAmount = 0;
			}
		}

		if (discountAmount > totalPayAmount) {
			discountAmount = 0;
		}

		return discountAmount;
	}


	/**
	 * 이메일 주소로 HP 메일 (휴대메일?)인지 확인한다.
	 * @param email
	 * @return
	 */
	public static boolean isHpMail(String email) {
		List<Code> hpMailList = CodeUtils.getCodeList("HPMAIL_DOMAIN");
		if (ValidationUtils.isNotNull(hpMailList)) {
			for(Code c : hpMailList) {
				if (email.endsWith(c.getId())) {
					return true;
				}
			}
		}

		return false;
	}


	/**
	 * 빈 값인 경우 디폴트 값을 리턴함.
	 * @param value
	 * @param defaultValue
	 * @return
	 */
	public static String emptyToValue(String value, String defaultValue) {
		if (ValidationUtils.isEmpty(value)) {
			return defaultValue;
		}

		return value;
	}


	/**
	 * 빈 값인 경우 -1을 리턴.
	 * @param value
	 * @return
	 */
	public static int emptyToNegativeNumber(String value) {
		return Integer.parseInt(emptyToValue(value, "-1"));
	}

	/**
	 * 전화번호 000-00-0000 문자열을 String[3] 으로 리턴
	 * @param s
	 * @return
	 */
	public static String[] phoneNumberForDelimitedToStringArray(String s) {

		String[] value = new String[3];

		try {
			if (StringUtils.isEmpty(s)) {
				return value;
			}

			if (s.indexOf('-') != -1) {
				String[] tArr = StringUtils.delimitedListToStringArray(s, "-");
				if (tArr.length == 3) {
					return StringUtils.delimitedListToStringArray(s, "-");
				}
			}

			s = s.replace("-", "");

			int cut = 4;
			int index = 0;
			for(int i = 0; i < s.length(); i++) {
				if (s.length() <= (i + cut) || index + 1 >= value.length) {
					value[index] = s.substring(i, s.length());
					break;
				} else {
					value[index] = s.substring(i, (i + (index == 0 ? cut-1 : cut)));
					i = i + ((index == 0 ? cut-1 : cut) - 1);
				}

				index++;
			}

			return value;

		} catch(Exception e) {
			return value;
		}
	}

	/**
	 * 전화번호에 하이픈 넣기 (01012341234 -> 010-1234-1234)
	 * @param phoneNumber
	 * @return
	 */
	public static String phoneNumberPattern(String phoneNumber) {
		if (phoneNumber.length() == 10) {
			return phoneNumber.substring(0, 3) + "-" + phoneNumber.substring(3, 6) + "-" + phoneNumber.substring(6, 10);
		} else if (phoneNumber.length() == 11) {
			return phoneNumber.substring(0, 3) + "-" + phoneNumber.substring(3, 7) + "-" + phoneNumber.substring(7, 11);
		} else {
			return phoneNumber.length() == 12 ? phoneNumber.substring(0, 4) + "-" + phoneNumber.substring(4, 8) + "-" + phoneNumber.substring(8, 12) : phoneNumber;
		}
	}

	/**
	 * 모바일 디바이스 처리
	 * @param request
	 * @return
	 */
	public static boolean isMobile(HttpServletRequest request) {
		HttpSession session = request.getSession();

		String siteReference = request.getParameter("SITE_REFERENCE");
		String sessionSiteReference = (String) session.getAttribute("SITE_REFERENCE");

		if (DeviceUtils.isMobile(request)) {
			if (siteReference != null && siteReference.equals(DeviceUtils.NORMAL)) {
				session.setAttribute("SITE_REFERENCE", DeviceUtils.NORMAL);
				return false;
			} else if (sessionSiteReference != null && sessionSiteReference.equals(DeviceUtils.NORMAL)) {
				return false;
			} else {
				return true;
			}
		}
		return false;
	}

	/**
	 * 해당 팀의 하위 그룹 목록을 가져옴.
	 * @param teamCode
	 * @return
	 */
	public static List<Group> getChildCategoriesForTeam(String teamCode) {
		ShopContext shopContext = (ShopContext) ThreadContext.get(ShopContext.REQUEST_NAME);

		List<Team> teamList = shopContext.getGnbCategories();

		for (Team team : teamList) {
			if (team.getUrl().equals(teamCode)) {
				return team.getGroups();
			}
		}
		return new ArrayList<Group>();
	}

	/**
	 * 해당 그룹의 하위 카테고리 목록을 가져옴.
	 * @param groupCode
	 * @return
	 */
	public static List<Category> getChildCategoriesForGroup(String groupCode) {
		ShopContext shopContext = (ShopContext) ThreadContext.get(ShopContext.REQUEST_NAME);

		List<Team> teamList = shopContext.getGnbCategories();

		for (Team team : teamList) {
			for (Group group : team.getGroups()) {
				if (group.getUrl().equals(groupCode)) {
					return group.getCategories();
				}
			}
		}

		return new ArrayList<Category>();
	}

	/**
	 * 해당 카테고리의 하위 카테고리 목록을 가져옴.
	 * @param categoryCode
	 * @return
	 */
	public static List<Category> getChildCategoriesForCategory(String categoryCode) {
		ShopContext shopContext = (ShopContext) ThreadContext.get(ShopContext.REQUEST_NAME);

		List<Team> teamList = shopContext.getGnbCategories();

		for (Team team : teamList) {
			for (Group group : team.getGroups()) {
				for (Category category1 : group.getCategories()) {
					if (category1.getUrl().equals(categoryCode)) {
						return category1.getChildCategories();
					}
					for (Category category2 : category1.getChildCategories()) {
						if (categoryCode.equals(category2.getUrl())) {
							return category2.getChildCategories();
						}
						for (Category category3 : category2.getChildCategories()) {
							if (category3.getUrl().equals(categoryCode)) {
								return category3.getChildCategories();
							}
							for (Category category4 : category3.getChildCategories()) {
								if (categoryCode.equals(category4.getUrl())) {
									return category4.getChildCategories();
								}
							}
						}
					}
				}
			}
		}

		return new ArrayList<Category>();
	}


	/**
	 * 현재 카테고리의 부모 카테고리 정보를 가져옴.
	 * @param categoryCode
	 * @return
	 */
	public static Category getParentCategory(String categoryCode) {
		ShopContext shopContext = (ShopContext) ThreadContext.get(ShopContext.REQUEST_NAME);

		List<Team> teamList = shopContext.getGnbCategories();

		for (Team team : teamList) {
			for (Group group : team.getGroups()) {
				for (Category category1 : group.getCategories()) {
					if (category1.getUrl().equals(categoryCode)) {
						Category category = new Category();
						category.setUrl(group.getUrl());
						category.setName(group.getName());
						return category;
					}
					for (Category category2 : category1.getChildCategories()) {
						if (categoryCode.equals(category2.getUrl())) {
							return category1;
						}
						for (Category category3 : category2.getChildCategories()) {
							if (category3.getUrl().equals(categoryCode)) {
								return category2;
							}
							for (Category category4 : category3.getChildCategories()) {
								if (categoryCode.equals(category4.getUrl())) {
									return category3;
								}
							}
						}
					}
				}
			}
		}
		return null;
	}


	/**
	 * 현재 카테고리의 형제 카테고리 목록을 가져옴.
	 * @param categoryCode
	 * @return
	 */
	public static List<Category> getSiblingCategories(String categoryCode) {
		ShopContext shopContext = (ShopContext) ThreadContext.get(ShopContext.REQUEST_NAME);

		List<Team> teamList = shopContext.getGnbCategories();

		for (Team team : teamList) {
			for (Group group : team.getGroups()) {
				for (Category category1 : group.getCategories()) {
					if (categoryCode.equals(category1.getUrl())) {
						Category category = new Category();
						category.setUrl(group.getUrl());
						category.setName(group.getName());
						return group.getCategories();
					}
					for (Category category2 : category1.getChildCategories()) {
						if (categoryCode.equals(category2.getUrl())) {
							return category1.getChildCategories();
						}
						for (Category category3 : category2.getChildCategories()) {
							if (categoryCode.equals(category3.getUrl())) {
								return category2.getChildCategories();
							}
							for (Category category4 : category3.getChildCategories()) {
								if (categoryCode.equals(category4.getUrl())) {
									return category3.getChildCategories();
								}
							}
						}
					}
				}
			}
		}
		return new ArrayList<Category>();
	}

	public static List<List<Code>> getBreadcrumbsForSelectbox(Breadcrumb breadcrumb) {
		String teamUrl = breadcrumb.getTeamUrl();
		String groupUrl = breadcrumb.getGroupUrl();

		List<List<Code>> breadcrumbsForSelectbox = new ArrayList<>();

		// 팀.
		//ShopContext shopContext = (ShopContext) ThreadContext.get(ShopContext.REQUEST_NAME);
		//List<Team> teamList = shopContext.getGnbCategories();

		/* CJH 2017.03.23 카테고리 TEAM은 FRONT에서 사용하지 않음
		List<Code> teams = new ArrayList<>();

		for (Team team : teamList) {
			String detail = team.getUrl().equals(teamUrl) ? "1" : "0";
			teams.add(new Code(team.getName(), team.getUrl(), detail));
		}
		breadcrumbsForSelectbox.add(teams);
		*/

		// 그룹
		List<Code> groups = new ArrayList<>();
		for (Group group : getChildCategoriesForTeam(teamUrl)) {
			String detail = group.getUrl().equals(groupUrl) ? "1" : "0";
			groups.add(new Code(group.getName(), group.getUrl(), detail));
		}
		breadcrumbsForSelectbox.add(groups);

		// 카테고리
		for (BreadcrumbCategory category : breadcrumb.getBreadcrumbCategories()) {
			String categoryUrl = category.getCategoryUrl();

			List<Code> categories = new ArrayList<>();

			for (Category cate : getSiblingCategories(categoryUrl)) {
				String detail = cate.getUrl().equals(categoryUrl) ? "1" : "0";
				categories.add(new Code(cate.getName(), cate.getUrl(), detail));
			}
			breadcrumbsForSelectbox.add(categories);
		}

		return breadcrumbsForSelectbox;
	}

	/**
	 * 모바일에서 특정일 검색용
	 * 시작일과 종료일을 반환함
	 * @param type
	 * @return (start, end)
	 */
	public static String[] getSearchDateForType(String type) {
		String s = "";
		String e = "";
		if ("today".equals(type)) {
			e = DateUtils.getToday();
			s = e;
		} else if ("all".equals(type)) {
			s = "";
			e = "";
		} else {
			if (type != null) {
				String today = DateUtils.getToday();
				if (type.endsWith("months")) { // 개월
					String[] t = StringUtils.delimitedListToStringArray(type, "-");
					if (t.length == 2) {
						s = DateUtils.addYearMonthDay(today, 0, -(Integer.parseInt(t[0])), 0);
						e = today;
					}
				} else if (type.endsWith("month")) { // 특정월
					String[] t = StringUtils.delimitedListToStringArray(type, "-");
					if (t.length == 2) {
						String year = today.substring(0, 4);
						int month = Integer.parseInt(t[0]);

						String yearMonth = year + (month <= 9 ? "0"+ Integer.toString(month) : Integer.toString(month));
						s = yearMonth + "01";
						e = DateUtils.getLastDateOfMonth(s);
					}
				}
			}
		}

		return new String[]{s, e};
	}

	/**
	 * 오늘날짜 조회
	 * @return
	 */
	public static String getToday() {
		return DateUtils.getToday();
	}


	/**
	 * 모바일에서 더보기가 있는 경우 페이지 로드를 체크하기 위해 쿠키를 저장한다.
	 * @param response
	 */
	public static void setCookieWhenServerLoad(HttpServletResponse response) {
		Cookie cookie = new Cookie("SERVER_LOAD", "YES");
		cookie.setHttpOnly(true);
		cookie.setMaxAge(60*60*24);				// 쿠키 유지 기간 - 1일
		cookie.setPath("/");					// 모든 경로에서 접근 가능하도록
		response.addCookie(cookie);				// 쿠키저장
	}

	/**
	 * 쿠폰기능 사용 유무
	 * @return
	 */
	public static boolean useCoupon() {
		return "Y".equals(ShopUtils.getConfig().getUseCouponFlag()) ? true : false;
	}

	/**
	 * 요일 목록을 가져옴.
	 * @param dayOfWeekValues
	 * @return
	 */
	public static List<Code> getDayOfWeekList(String dayOfWeekValues) {
		String[] days = new String[] {
				"일요일", "월요일", "화요일", "수요일", "목요일", "금요일", "토요일"
		};

		List<Code> codes = new ArrayList<>();
		int i = 0;
		for (String day : days) {
			String detail = "";
			if (dayOfWeekValues.indexOf("" + i) > -1) {
				detail = "1";
			}
			codes.add(new Code(day, "" + i, detail));
			i++;
		}
		return codes;
	}

	/**
	 * 주소에서 시도정보를 가져옴
	 * @param address
	 * @return
	 */
	public static String getSido(String address) {

		String str = "";
		if (address == null) {
			return "";
		}

		if (address.length() > 0) {
			String[] pattern = new String[]{"시", "도"};
			String[] wordPattern = new String[]{"서울", "부산", "대구", "인천", "광주", "대전", "울산", "세종", "경기","강원", "충북", "충남", "경북", "경남", "전북", "전남", "제주"};
			String[] splitAddress = StringUtils.delimitedListToStringArray(address, " ");

			if (splitAddress.length > 0) {
				if (StringUtils.isNotEmpty(splitAddress[0])) {
					for (int i = 0; i < pattern.length; i++) {
						if (splitAddress[0].endsWith(pattern[i])) {
							return splitAddress[0];
						}
					}

					for (int i = 0; i < wordPattern.length; i++) {
						if (splitAddress[0].startsWith(wordPattern[i])) {
							return splitAddress[0];
						}
					}

				}
			}
		}

		return str;

	}

	/**
	 * 주소에서 시군구정보를 가져옴
	 * @param address
	 * @return
	 */
	public static String getSigungu(String address) {
		String si = ShopUtils.getSigungu(address, 1);
		String gu = ShopUtils.getSigungu(address, 2);
		if (StringUtils.isNotEmpty(gu)) {
			si = si + " " + gu;
		}

		return si;
	}

	/**
	 * 주소에서 시군구정보를 가져옴
	 * @param address
	 * @return
	 */
	public static String getSigungu(String address, int index) {

		String str = "";
		if (address == null) {
			return "";
		}


		if (address.length() > 0) {
			String[] pattern = new String[]{"시", "군", "구"};
			String[] splitAddress = StringUtils.delimitedListToStringArray(address, " ");

			if (splitAddress.length > index) {
				if (ValidationUtils.isNotNull(splitAddress[index])) {
					for (int i = 0; i < pattern.length; i++) {
						if (splitAddress[index].endsWith(pattern[i])) {
							return splitAddress[index];
						}
					}
				}
			}
		}

		return str;

	}

	/**
	 * 주소에서 읍면동정보를 가져옴
	 * @param address
	 * @return
	 */
	public static String getEupmyeondong(String address) {
		int index = 2;
		String gu = ShopUtils.getSigungu(address, index);
		if (StringUtils.isNotEmpty(gu)) {

			index = 3;
		} else {

			// 주소에 시군구 정보가 1번인덱스에 없는경우 읍이 오는 경우도 있음 ex) 세종시 조치원읍 신흥리
			String eup = ShopUtils.getEupmyeondong(address, 1);
			if (StringUtils.isNotEmpty(eup)) {
				return eup;
			}
		}

		return ShopUtils.getEupmyeondong(address, index);
	}

	/**
	 * 주소에서 읍면동정보를 가져옴
	 * @param address
	 * @return
	 */
	public static String getEupmyeondong(String address, int index) {

		String str = "";
		if (address == null) {
			return "";
		}

		if (address.length() > 0) {
			String[] pattern = new String[]{"읍", "면", "동"};
			String[] splitAddress = StringUtils.delimitedListToStringArray(address, " ");

			if (splitAddress.length > index) {
				if (StringUtils.isNotEmpty(splitAddress[index])) {
					for (int i = 0; i < pattern.length; i++) {
						if (splitAddress[index].endsWith(pattern[i])) {
							return splitAddress[index];
						}
					}
				}
			}
		}

		return str;

	}

	public static String getDongri(String address) {

		int index = 2;
		String eupmyeondong = ShopUtils.getEupmyeondong(address);
		if (StringUtils.isNotEmpty(eupmyeondong)) {

			if (eupmyeondong.endsWith("동")) {
				return "";
			}

			index = 3;

			// ex) 경상남도 창원시 마산회원구 내서읍 상곡리 - 기본주소 참 길다..
			eupmyeondong = ShopUtils.getEupmyeondong(address, index);
			if (StringUtils.isNotEmpty(eupmyeondong)) {
				if (eupmyeondong.endsWith("읍")) {
					index = 4;
				}
			}
		}

		return ShopUtils.getDongri(address, index);
	}

	/**
	 * 주소에서 동리정보를 가져옴
	 * @param address
	 * @return
	 */
	public static String getDongri(String address, int index) {

		String str = "";
		if (address == null) {
			return "";
		}

		if (address.length() > 0) {
			String[] pattern = new String[]{"동", "리"};
			String[] splitAddress = StringUtils.delimitedListToStringArray(address, " ");

			if (splitAddress.length > index) {
				if (StringUtils.isNotEmpty(splitAddress[index])) {
					for (int i = 0; i < pattern.length; i++) {
						if (splitAddress[index].endsWith(pattern[i])) {
							return splitAddress[index];
						}
					}
				}
			}
		}

		return str;

	}

	/**
	 * 시간 출력 형식.
	 * @param time
	 * @return
	 */
	public static String getTimeFormat(String time) {
		if (time.length() < 6) {
			return "";
		}
		return time.substring(0, 2) + ":" + time.substring(2, 4) + ":" + time.substring(4, 6);
	}

	/**
	 * 모바일 페이지 여부.
	 * @return
	 */
	public static boolean isMobilePage() {
		try {
			RequestContext requestContext = RequestContextUtils.getRequestContext();
			return isMobilePage(requestContext.getRequestUri());
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * 모바일 페이지 여부.
	 * @param requestUri
	 * @return
	 */
	public static boolean isMobilePage(String requestUri) {
		if (requestUri.equals(MOBILE_PREFIX) || requestUri.startsWith(MOBILE_PREFIX + "/")) {
			return true;
		}
		return false;
	}

	/**
	 * 모바일 페이지 Prefix (설정값)
	 * @return
	 */
	public static String getMobilePrefix() {
		return MOBILE_PREFIX;
	}

	/**
	 * 모바일 페이지 Prefix
	 * @return
	 */
	public static String getMobilePrefixByPage() {
		if (isMobilePage()) {
			return MOBILE_PREFIX;
		}
		return "";
	}

	/**
	 * 오픈마켓 옵션 코드를 가져옴
	 * @param option
	 * @return
	 */
	public static String getOpenMarketOptionCode(String option) {
		return ShopUtils.getOpenMarketOptionCode(option, ":");
	}

	public static String getOpenMarketOptionCode(String option, String key) {
		if (StringUtils.isNotEmpty(option)) {
			if (option.indexOf(key) != -1) {
				option = option.substring(option.indexOf(key) + key.length());
			}

			if (option.indexOf(".") != -1) {
				String[] temp = StringUtils.delimitedListToStringArray(option, ".");

				if (temp[0] != null) {
					return temp[0].trim();
				}
			}
		}

		return "";
	}

	/**
	 * 이익률 계산
	 * @return
	 */
	public static String getRevenuePercent(String p1, String p2) {
		try {
			return Integer.toString((int) (((double) (Double.parseDouble(p1) - Double.parseDouble(p2)) / Double.parseDouble(p1)) * 100)) + "%";
		} catch(Exception e) {
			return "";
		}
	}

	/**
	 * 신장률 계산
	 * @return
	 */
	public static String getGrowthPercent(String p1, String p2) {
		try {
			return Integer.toString((int) (((double) (Double.parseDouble(p1) - Double.parseDouble(p2)) / Double.parseDouble(p2)) * 100)) + "%";
		} catch(Exception e) {
			return "";
		}
	}

	/**
	 * 쇼핑몰 타입 - 몰인몰 (입점) 형태인가?
	 * @return
	 */
	public static boolean isMallInMall() {
		return true;
	}

	/**
	 * 비밀번호로 사용되는 전화번호를 Shuffle 처리.
	 * @param phoneNumber
	 * @return
	 */
	public static String shufflePhoneNumber(String phoneNumber) {

		String shuffledPhoneNumber = phoneNumber;
		String[] phoneNumbers = StringUtils.delimitedListToStringArray(phoneNumber, "-");


		if (phoneNumbers.length == 3) {
			shuffledPhoneNumber = phoneNumbers[2] + "-" + phoneNumbers[1] + "-" + phoneNumbers[0];
		}
		return shuffledPhoneNumber;
	}

	/**
	 * 상품 상태 코드 값.
	 * @param statusCode
	 * @return
	 */
	public static String getItemStatusText(String statusCode) {
		String statusText = "기타";
		if ("1".equals(statusCode)) {
			statusText = "정상";
		} else if ("20".equals(statusCode)) {
			statusText = "등록신청";
		} else if ("21".equals(statusCode)) {
			statusText = "등록반려";
		} else if ("30".equals(statusCode)) {
			statusText = "수정신청";
		} else if ("31".equals(statusCode)) {
			statusText = "수정반려";
		} else if ("40".equals(statusCode)) {
			statusText = "삭제신청";
		} else if ("41".equals(statusCode)) {
			statusText = "삭제반려";
		}

		return statusText;
	}

	/**
	 * 상품 상태 메세지.
	 * @param statusCode
	 * @param message
	 * @return
	 */
	public static String getItemStatusMessage(String statusCode, String message) {
		return "[" + getItemStatusText(statusCode) + " " + DateUtils.getToday("yyyy-MM-dd HH:mm:ss") + "] " + message + StringUtils.getNewline();
	}


	/**
	 * 현재 페이지가 판매관리자 페이지 인가? (URI로 구분함 /seller..로 시작)
	 * @return
	 */

	public static boolean isSellerPage() {
		try {
			return RequestContextUtils.getRequestUri().startsWith("/seller") || RequestContextUtils.getRequestUri().startsWith("/common/seller") ? true : false;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * 입점업체 페이지 접속인지 여부를 판단한다.
	 * <pre>
	 * URI에 "/seller"로 시작되는  경우 관리자 페이지 접속이라 판단함.
	 * </pre>
	 * @param requestUri 접속 URI
	 * @return boolean
	 */
	public static boolean isSellerPage(String requestUri) {

		if (requestUri.startsWith("/seller")) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 현재 페이지가 관리자 페이지 인가? (URI로 구분함 /opmanager..로 시작)
	 * common으로 시작하는 페이지도 추가!
	 * @return
	 */
	public static boolean isOpmanagerPage() {
		return RequestContextUtils.getRequestUri().startsWith("/opmanager") || RequestContextUtils.getRequestUri().startsWith("/common/opmanager") ? true : false;
	}

	/**
	 * 사용자 화면에 보여줄 옵션을 구성함
	 * @param optionText
	 * @return
	 */
	public static String viewOptionText(String optionText) {

		if (StringUtils.isEmpty(optionText)) {
			return "";
		}

		String[] options = StringUtils.delimitedListToStringArray(optionText, "^^^");

		StringBuffer sb = new StringBuffer();

		sb.append("<ul class=\"option\">");

		for (String option : options) {

			String[] temp = StringUtils.delimitedListToStringArray(option, "||");
			int index = 0;
			String t = "";

			if (temp.length > 1) {

				for(String s : temp) {

					if (index == 0) {
						index++;
						continue;
					}

					if (temp.length == index + 4) {
						int addPrice = Integer.parseInt(s);
						if (addPrice != 0) {
							t += " (";
							t += addPrice > 0 ? "+" : "-";
							t += NumberUtils.formatNumber(Math.abs(addPrice), "#,###") + "원)";
						}

					} else if (index < temp.length - 3) {

						if (index % 2 == 0) {
							t += "<span>" + s + "</span>";
							if (temp.length > index + 5) {
								t += " / ";
							}
						} else {
							t += "<span class=\"choice\">" + s + " : </span>";
						}
					}

					index++;
				}

				sb.append("<li> " + t + "</li>");

			}else{
				sb.append("<li> 옵션 : " + optionText + "</li>");
			}
		}

		sb.append("</ul>");
		return sb.toString();
	}


	/**
	 * 사용자 화면에 보여줄 옵션을 구성함
	 * @param optionText
	 * @return
	 */
	public static String buyViewOptionText(String optionText) {

		if (StringUtils.isEmpty(optionText)) {
			return "";
		}

		String[] options = StringUtils.delimitedListToStringArray(optionText, "^^^");

		StringBuffer sb = new StringBuffer();

		for(String option : options) {

			String[] temp = StringUtils.delimitedListToStringArray(option, "||");
			int index = 0;
			String t = "";

			if (temp.length > 1) {

				for(String s : temp) {

					if (index == 0) {
						index++;
						continue;
					}

					if (temp.length == index + 4) {
						int addPrice = Integer.parseInt(s);
						if (addPrice != 0) {
							t += " (";
							t += addPrice > 0 ? "+" : "-";
							t += NumberUtils.formatNumber(Math.abs(addPrice), "#,###") + "원)";
						}

					} else if (index < temp.length - 3) {

						if (index % 2 == 0) {
							t += " : " + s;
							if (temp.length > index + 5) {
								t += " / ";
							}
						} else {
							t += s;
						}
					}

					index++;
				}

				sb.append(t + "<br/>");

			} else {
				sb.append("옵션 : " + optionText);
			}
		}
		return sb.toString();
	}

	/**
	 * 사용자 화면에 보여줄 옵션을 구성함(ul 생성 없음)
	 * @param optionText
	 * @return
	 */
	public static String viewOptionTextNoUl(String optionText) {

		if (StringUtils.isEmpty(optionText)) {
			return "";
		}

		String[] options = StringUtils.delimitedListToStringArray(optionText, "^^^");

		StringBuffer sb = new StringBuffer();

		//현대아웃렛 옵션 정보 수정 2017-01-18 yulsun.yoo
		//sb.append("<dd class=\"option\">");

		int i = 1;
		for(String option : options) {
			int optionCount = options.length;

			String[] temp = StringUtils.delimitedListToStringArray(option, "||");
			int index = 0;
			String t = "";

			if (temp.length > 1) {

				for(String s : temp) {

					if (index == 0) {
						index++;
						continue;
					}

					// 7 == 5
					if (temp.length == index + 4) {
						int addPrice = Integer.parseInt(s);
						if (addPrice != 0) {
							t += " (";
							t += addPrice > 0 ? "+" : "-";
							t += NumberUtils.formatNumber(Math.abs(addPrice), "#,###") + "원)";
						}

					} else if (index < temp.length - 3) {	// 1 < 4

						if (index % 2 == 0) {
							t +=  s ;
							if (temp.length > index + 5) { // 7 > 2+5
								t += " / ";
							}
						} else {
							t +=  s + " : ";
						}
					}

					// 1. 그룹명 :
					// 2. 옵션명 (원)

					index++;
				}

				if (i == optionCount) {
					sb.append(t);
				} else {
					sb.append(t + ", "); //현대아웃렛 옵션 정보 수정 2017-01-18 yulsun.yoo
				}

			} else {
				sb.append(optionText); //현대아웃렛 옵션 정보 수정 2017-01-18 yulsun.yoo
			}

			i++;
		}

		return sb.toString();
	}

	/**
	 * 상품 옵션을 구성
	 * @param optionText
	 * @return
	 */
	public static List<Map<String, Object>> getOptions(String optionText) {

		List<Map<String, Object>> list = new ArrayList<>();

		if (StringUtils.isEmpty(optionText)) {
			return new ArrayList<>();
		}

		try {
			String[] options = StringUtils.delimitedListToStringArray(optionText, "^^^");

			for(String option : options) {

				Map<String, Object> map = new LinkedHashMap<>();

				String[] temp = StringUtils.delimitedListToStringArray(option, "||");
				int index = 0;
				String t = "";

				if(temp.length > 1){

					int addPrice = 0;
					List<Map<String, Object>> subList = new ArrayList<>();
					Map<String, Object> subMap = new LinkedHashMap<>();

					for(String s : temp) {

						if (index == 0) {
							index++;
							continue;
						}

						if (temp.length == index + 1) {
							addPrice = Integer.parseInt(s);
							map.put("addPrice",addPrice);
						} else {
							if (index % 2 == 0) {
								subMap.put("value",s);
								if (temp.length > index + 2) {
									subList.add(subMap);
									subMap = new LinkedHashMap<>();
								}
							} else {
								subMap.put("label",s);
							}
						}

						if (index == (temp.length -1)) {
							subList.add(subMap);
						}

						index++;
					}

					map.put("options",subList);


				}else{

					Map<String, Object> subMap = new LinkedHashMap<>();
					List<Map<String, Object>> subList = new ArrayList<>();

					subMap.put("label","옵션");
					subMap.put("value",optionText);

					subList.add(subMap);

					map.put("options",subList);
					map.put("addPrice",0);
				}

				list.add(map);
			}

		} catch (Exception ignore) {}

		return list;
	}


	/**
	 * Mall Type별
	 * @param mallType
	 * @return
	 */
	public static List<CodeInfo> getMallDeliveryCompanyListByMallType(String mallType) {

		return CodeUtils.getCodeInfoList(mallType.toUpperCase() + "_DELIVERY_COMPANY");

	}

	/**
	 * 은행 목록
	 * @param key
	 * @return
	 */
	public static List<CodeInfo> getBankListByKey(String key) {
		if (!StringUtils.isEmpty(key)) {
			key = key.toUpperCase() + BANK_POSTFIX;
		}

		List<CodeInfo> codeInfos = CodeUtils.getCodeInfoList(key);
		if (codeInfos == null || codeInfos.isEmpty()) {
			// 결과가 없을 경우, Default
			codeInfos = CodeUtils.getCodeInfoList("DEFAULT" + BANK_POSTFIX);
		}

		return codeInfos;
	}

	/**
	 * 은행코드로 은행명 가져오기 2017-05-24 yulsun.yoo
	 * @param key
	 * @return
	 */
	public static String getBankName(String pgType, String key) {
		Code code = null;
		String returnKey = key;
		try {
			code = CodeUtils.getCode(pgType.toUpperCase() + BANK_POSTFIX, key);
			returnKey = code.getLabel();
		} catch (Exception e) {

		}

		return returnKey;
	}

	/**
	 * 은행 명으로 은행코드 가져오기 2017-10-02 juneu.son
	 * @param key
	 * @return
	 */
	public static String getBankCode(String pgType, String key) {
		List<CodeInfo> list = getBankListByKey(pgType);

		String bankCode = "";

		for(CodeInfo info : list){
			if(key.equals(info.getLabel())){
				bankCode = info.getKey().getId();
			}
		}

		return bankCode;
	}

	/**
	 * 주문 상태 변경 가능여부를 체크
	 * @param mode
	 * @param orderStatus
	 * @return
	 */
	public static boolean checkOrderStatusChange(String mode, String orderStatus) {

		// 주문취소
		if ("payment-verification-cancel".equals(mode)) {

			/**
			 * 취소
			 * 0 : 입금대기,
			 * 10 : 결제완료,
			 */
			String[] checkArray = new String[]{"0", "10"};
			if (ArrayUtils.contains(checkArray, orderStatus)) {
				return true;
			}

		} else if ("cancel".equals(mode)) {

			/**
			 * 취소
			 * 0 : 입금대기,
			 * 10 : 결제완료,
			 * 20 : 배송준비중
			 */
			String[] checkArray = new String[]{"0", "10", "20"};
			if (ArrayUtils.contains(checkArray, orderStatus)) {
				return true;
			}

		} else if ("exchange".equals(mode) || "return".equals(mode)) {

			/**
			 * 교환, 환불
			 * 30 : 배송중,
			 * 35 : 배송완료,
			 * 40 : 구매확정,
			 * 55 : 교환배송시작
			 * 59 : 교환거절
			 * 69 : 반품거절
			 */
			String[] checkArray = new String[]{"30", "35", "40", "55", "59", "69"};
			if (ArrayUtils.contains(checkArray, orderStatus)) {
				return true;
			}
		} else if ("confirm".equals(mode)) {

			/**
			 * 구매확정
			 * 30 : 배송중,
			 * 35 : 배송완료,
			 * 55 : 교환배송시작
			 * 59 : 교환거절
			 * 69 : 반품거절
			 */
			String[] checkArray = new String[]{"30", "35", "55", "59", "69"};
			if (ArrayUtils.contains(checkArray, orderStatus)) {
				return true;
			}
		}

		return false;
	}


	/**
	 * 최저가 쿠폰
	 * @param couponList
	 * @param item
	 * @return
	 */
	public static Coupon getLargestRateCoupon(List<Coupon> couponList, Item item) {
		Coupon retCoupon = null;

		if (couponList == null) {
			return retCoupon;
		}

		int targetAmount = item.getPresentPrice();
		for (Coupon coupon : couponList) {

			if (coupon.getCouponPayRestriction() != -1) {
				if (item.getPresentPrice() <  coupon.getCouponPayRestriction()) {
					continue;
				}
			}

			int discountPrice = 0;
			int couponPay = Integer.parseInt(coupon.getCouponPay());
			if ("1".equals(coupon.getCouponPayType())) { // 원
				discountPrice = couponPay;
			} else { // %
				discountPrice = (new BigDecimal(targetAmount).multiply(new BigDecimal(couponPay).divide(new BigDecimal("100")))).intValue();

				// [2020-01-08 KSH] %할인 최대 할인금액 제한
				if (coupon.getCouponDiscountLimitPrice() > -1 && discountPrice > coupon.getCouponDiscountLimitPrice()) {
					discountPrice = coupon.getCouponDiscountLimitPrice();
				}
			}

			if (retCoupon == null) {
				retCoupon = coupon;
				retCoupon.setDiscountPrice(discountPrice);
			} else {
				if (retCoupon.getDiscountPrice() < discountPrice) {
					retCoupon = coupon;
					retCoupon.setDiscountPrice(discountPrice);
				}
			}
		}

		return retCoupon;
	}

	public static String getSearchStartDate(String key) {

		try {
			String today = DateUtils.getToday(Const.DATE_FORMAT);

			if (StringUtils.isEmpty(key) == false) {

				if (key.equals("today")) {
					return today;
				} else if (key.startsWith("week")) {

					String[] temp = StringUtils.delimitedListToStringArray(key, "-");
					return DateUtils.addYearMonthDay(today, 0, 0, -(7 * Integer.parseInt(temp[1])));

				} else if (key.startsWith("month")) {

					String[] temp = StringUtils.delimitedListToStringArray(key, "-");
					return DateUtils.addYearMonthDay(today, 0, -Integer.parseInt(temp[1]), 0);

				} else if (key.startsWith("year")) {

					String[] temp = StringUtils.delimitedListToStringArray(key, "-");
					return DateUtils.addYearMonthDay(today, -Integer.parseInt(temp[1]), 0, 0);

				}


			}

			return today;

		} catch(Exception e) {
			return "";
		}

	}

	/**
	 * 모바일 Page 세팅용 기본 conditionType
	 * CONDITION_TYPE -> DEFAULT_LIST
	 */
	public static final String PAGE_MOBILE_DEFAULT_CONDITION_TYPE = "DEFAULT_LIST";

	/**
	 * 모바일 Page셋팅
	 * @param pagination
	 * @param conditionType
	 * @param page
	 */
	public static void setPaginationInfo(Pagination pagination, String conditionType, int page){
		if (ShopUtils.isMobilePage() && PAGE_MOBILE_DEFAULT_CONDITION_TYPE.equals(conditionType)) {
			if (page > 1) {
				pagination.setStartRownum((int) 0);
				pagination.setEndRownum((int) pagination.getItemsPerPage() * page);
			}
		}
	}

	/**
	 * 이미지 양 쪽이 한계치 보다 크면 true 한 쪽이라도 작으면 false
	 * @author 이상우 [2017-03-31]
	 * @param width
	 * @param height
	 * @param limit
	 * @return
	 */
	public static boolean checkImageSize2(int width, int height, int limit) {

		int basisSize = width < height ? width : height;

		if (basisSize > limit) {
			return true;
		}

		return false;
	}

	// 음수 표시여부 설정.
	public static final int DISPLAY_STATS_NEGATIVE_NUMBER = -1;    // -1: 음수표시, 1: 음수표시 안함.

	/**
	 * 매출/판매 통계에서 할인 및 취소항목에 음수 표시 여부 설정.
	 * @param number
	 * @return
	 */
	public static double statsNegativeNnumber(double number) {
		if (number == 0) {
			return number;
		}
		return  number * DISPLAY_STATS_NEGATIVE_NUMBER;
	}

	/*public static final int XS = 150;
	public static final int S = 300;
	public static final int M = 500;
	public static final int L = 1200;*/
	//제일 큰 썸네일 사이즈
	public static final int L = 1200;


	/**
	 * 관리자 썸네일 설정에서 입력한 값으로 이미지 사이즈를 리턴
	 * @author [2017-06-09]minae.yun
	 * @param sizeName
	 * @return
	 */
	public static int getImageSize(String sizeName) {

		//호출한 사이즈가 존재하는지 확인
		boolean type = false;
		for (String inputSize : getThumbnailType()) {
			if (sizeName.equals(inputSize)) type = true;
		}

		//제일 큰 사이즈 default
		if (!type) return L;

		int imageSize = 0;
		Config shopConfig = getConfig();
		String jsonSize = shopConfig.getThumbnailSize(); //json 데이터

		if (jsonSize.isEmpty()) {
			return L;
		}

		JSONObject jsonObject = (JSONObject) JSONValue.parse(jsonSize);  //json 파싱
		JSONArray infoArray = (JSONArray) jsonObject.get("list"); //list의 배열을 추출

		try {
			for(int i=0; i<infoArray.size(); i++){
				//배열 안에 있는것도 JSON형식 이기 때문에 JSON Object 로 추출
				JSONObject listObject = (JSONObject) infoArray.get(i);

				//list배열 안에 데이터으로 추출
				String listSizeName = (String) listObject.get("sizeName");
				int listSize = Integer.parseInt((String)listObject.get("size"));

				if (sizeName.equals(listSizeName)) imageSize = listSize;
			}

		} catch (JSONException e) {
			imageSize = L; //제일 큰 사이즈 default
		}

		return imageSize;
	}


	//public static final String[] THUMBNAIL_TYPE = new String[] {"XS", "S", "M", "L"};
	/**
	 * 관리자 썸네일 설정에서 저장한 이미지 사이즈를 리턴
	 * @author [2017-06-09]minae.yun
	 * @return
	 */
	public static String[] getThumbnailType() {

		Config shopConfig = getConfig();
		String jsonSize = shopConfig.getThumbnailSize(); //json 데이터

		if (jsonSize == null) {
			return  new String[] {"L"};
		}

		JSONObject jsonObject = (JSONObject) JSONValue.parse(jsonSize);  //json 파싱
		JSONArray infoArray = (JSONArray) jsonObject.get("list"); //list의 배열을 추출
		String[] THUMBNAIL_TYPE = new String[infoArray.size()];

		try {
			for(int i=0; i<infoArray.size(); i++){
				//배열 안에 있는것도 JSON형식 이기 때문에 JSON Object 로 추출
				JSONObject listObject = (JSONObject) infoArray.get(i);

				//list배열 안에 데이터으로 추출
				String listSizeName = (String) listObject.get("sizeName");

				THUMBNAIL_TYPE[i] = listSizeName;
			}

		} catch (JSONException e) {
			//default 고정 이미지 사이즈
			THUMBNAIL_TYPE = new String[] {"XS", "S", "M", "L"};
		}

		return THUMBNAIL_TYPE;
	}


	/**
	 * 상품 썸네일 호출
	 * @author [2017-06-01] minae.yun
	 * @param itemUserCode
	 * @param fileName
	 * @param sizeName
	 * @return
	 */
	public static String loadImage(String itemUserCode, String fileName, String sizeName) {

		//상품에 등록된 이미지가 없을경우
		if (fileName == null || "".equals(fileName) || fileName.indexOf("/") > -1 || !fileName.contains("."))
			return ShopUtils.getNoImagePath();

		//호출한 사이즈가 존재하는지 확인
		boolean type = false;
		for (String inputSize : getThumbnailType()) {
			if (sizeName.equals(inputSize)) type = true;
		}

		if (!type) return ShopUtils.getNoImagePath();

		Config shopConfig = getConfig();
		int pos = fileName.lastIndexOf( "." );
		String ext = fileName.substring( pos + 1 );
		String reFileName = fileName.substring(0, pos);
		String imageSrc = "";
		int imageSize = 0;

		if ("1".equals(shopConfig.getThumbnailType())) {
			//관리자 설정이 사이즈별 썸네일 저장일때
			if (reFileName.contains("(") && reFileName.contains(")")) {
				//이미지 이름에 ()괄호가 있을경우
				pos = fileName.lastIndexOf( "(" );
				ext = fileName.substring( pos, fileName.length());
				reFileName = reFileName.substring(0, 14) + sizeName + ext;
				imageSrc = "/upload/item/" + itemUserCode + "/" + reFileName;
			} else {
				if (reFileName.length()>14) reFileName = reFileName.substring(0, 14) + sizeName;
				imageSrc = "/upload/item/" + itemUserCode + "/" + reFileName + "." + ext;
			}
		} else {
			//호출시 썸네일 리사이즈일때
			imageSize = getImageSize(sizeName);

			//이미지 이름에 ()괄호가 있을경우
			if (reFileName.contains("(") && reFileName.contains(")")) {
				pos = fileName.lastIndexOf( "(" );
				ext = fileName.substring( pos, fileName.length());
				reFileName = reFileName.substring(0, 14) + "L" + ext;
			} else {
				if (reFileName.length()>14) {
					reFileName = reFileName.substring(0, 14) + "L" + "." + ext;
				} else {
					//이미지에 사이즈가 붙지않은 예전이름 형식일 경우
					reFileName = reFileName + "." + ext;
				}
			}

			imageSrc = "/thumbnail?src=/upload/item/"+ itemUserCode +"/"+reFileName + "&size=" + imageSize;
		}

		return imageSrc;
	}


	/**
	 * 상품 썸네일 호출
	 * @author [2017-06-01] minae.yun
	 * @param imageSrc
	 * @param sizeName
	 * @return
	 */
	public static String loadImage(String imageSrc, String sizeName) {

		//상품에 등록된 이미지가 없을경우
		if (imageSrc == null || "".equals(imageSrc) || !imageSrc.contains(".")) return imageSrc;

		//호출한 사이즈가 존재하는지 확인
		boolean type = false;
		for (String inputSize : getThumbnailType()) {
			if (sizeName.equals(inputSize)) type = true;
		}

		if (!type) return ShopUtils.getNoImagePath();

		Config shopConfig = getConfig();
		int pos = imageSrc.lastIndexOf( "." );
		int tag = imageSrc.lastIndexOf( "/" );
		String ext = imageSrc.substring( pos + 1 );
		String fileName = imageSrc.substring(tag + 1, pos);
		String fileRoot = imageSrc.substring(0, tag);
		String targetImageSrc = "";
		int imageSize = 0;

		if ("1".equals(shopConfig.getThumbnailType())) {
			//관리자 설정이 사이즈별 썸네일 저장일때
			if (fileName.length()>14) fileName = fileName.substring(0, 14) + sizeName;
			imageSrc = fileRoot + "/" + fileName + "." + ext;
			targetImageSrc = imageSrc;

		} else {
			//호출시 썸네일 리사이즈일때
			imageSize = getImageSize(sizeName);
			targetImageSrc = "/thumbnail?src=" + imageSrc + "&size=" + imageSize;
		}

		return targetImageSrc;
	}


	/**
	 * 가로, 세로 둘다 한계치보다 큰 이미지를 가로,세로중 작은 쪽을 한계치에 맞추고 그 비율에 따라 썸네일 생성
	 * @author 이상우 [2017-03-31] 
	 * @param image
	 * @param limit
	 * @return
	 * @throws IOException
	 */
	public static BufferedImage getThumbnailImage(BufferedImage image, int limit) throws IOException {

		image = Thumbnails.of(image).size(limit, limit).asBufferedImage();

		return image;
	}


	/**
	 * PoiUtils에서 숫자형 오류 발생으로 임시로 ShopUtils로 사용 [2017-07-10 최정아]
	 * 엑셀의 셀 값을 String 타입으로 가져온다.
	 * @param cell
	 * @return
	 */
	public static String getString(Cell cell) {
		String cellValue = "";

		if (cell == null) {
			return "";
		}

		switch (cell.getCellType()) {
			case Cell.CELL_TYPE_STRING:
				cellValue = cell.getRichStringCellValue().getString().trim();
				break;
			case Cell.CELL_TYPE_NUMERIC:
				// 숫자형 텍스트 입력 시 11300000000000  --> 1.13E+13 으로 변경된 컬럼도 오리지널 스트링으로 읽어냄. - skc20140917
				//cell.setCellType(Cell.CELL_TYPE_STRING);
				//cellValue = cell.getRichStringCellValue().getString().trim();
				cellValue = Integer.toString((int) cell.getNumericCellValue());
				break;
			case Cell.CELL_TYPE_BOOLEAN:
				//System.out.println(cell.getBooleanCellValue());
				break;
			case Cell.CELL_TYPE_FORMULA:
				//System.out.println(cell.getCellFormula());
				break;
		}

		// 스크립트 태그 replace
		return stripTags(cellValue);
	}


	/**
	 * PoiUtils에서 숫자형 오류 발생으로 임시로 ShopUtils로 사용 [2017-07-10 최정아]
	 * 엑셀의 셀 값을 int 타입으로 가져온다.
	 * @param cell
	 * @return
	 */
	public static int getInt(Cell cell) {
		String cellValue = getString(cell);
		if (cellValue.trim().equals("")) {
			return 0;
		}
		return Integer.parseInt(cellValue);
	}

	//범용 고유 식별자 생성
	public static String getUUID() {
		String radomUUID = UUID.randomUUID().toString();
		return radomUUID;
	}

	/**
	 * 이미지 없을때 보이는 이미지경로
	 * @return
	 */
	public static String getNoImagePath() {
		return NOIMAGE_PATH;
	}

	public static LocalDate getLocalDate(String pattern, String date) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern)
				.withLocale(Locale.getDefault())
				.withZone(ZoneId.systemDefault());

		return LocalDate.parse(date, formatter);
	}


	public static LocalDate getLocalDate(String date) {
		return getLocalDate(Const.DATE_FORMAT, date);
	}

	public static String localDateToString(LocalDate date, String pattern) {
		if (date == null) {
			return "";
		}
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern)
				.withLocale(Locale.getDefault())
				.withZone(ZoneId.systemDefault());
		return formatter.format(date);
	}

	public static String localDateToString(LocalDate date) {
		if (date == null) {
			return "";
		}
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(Const.DATE_FORMAT)
				.withLocale(Locale.getDefault())
				.withZone(ZoneId.systemDefault());
		return formatter.format(date);
	}

	/**
	 * 주문 상태 코드
	 * @param orderStatus
	 * @return
	 */
	public static String getOrderStatusLabel(String orderStatus) {

		if ("0".equals(orderStatus)) {
			return "입금대기";
		} else if ("10".equals(orderStatus)) {
			return "결제완료";
		} else if ("20".equals(orderStatus)) {
			return "배송준비중";
		} else if ("30".equals(orderStatus)) {
			return "배송중";
		} else if ("35".equals(orderStatus)) {
			return "배송완료";
		} else if ("40".equals(orderStatus)) {
			return "구매확정";
		} else if ("50".equals(orderStatus)) {
			return "교환처리중";
		} else if ("55".equals(orderStatus)) {
			return "교환배송중";
		} else if ("59".equals(orderStatus)) {
			return "교환거절";
		} else if ("60".equals(orderStatus)) {
			return "반품처리중";
		} else if ("65".equals(orderStatus)) {
			return "반품완료";
		} else if ("69".equals(orderStatus)) {
			return "반품거절";
		} else if ("70".equals(orderStatus)) {
			return "취소처리중";
		} else if ("75".equals(orderStatus)) {
			return "취소완료";
		}

		return "-";
	}

	/**
	 * 개월 수 표기
	 * @param prifix
	 * @param month
	 * @return
	 */
	public static String getMonthString(String prifix, String month) {

		if ("1".equals(month)) {
			return prifix + "한달";
		}

		return prifix + month +"개월";
	}

	/**
	 * 사은품 텍스트
	 * @param giftItems
	 * @return
	 */
	public static String makeGiftItemText(List<GiftItem> giftItems) {

		String text = "";

		try {

			if (giftItems != null && !giftItems.isEmpty()) {

				String firstName = "";
				int size = 0;
				boolean isFirst = true;

				for (GiftItem giftItem : giftItems) {

					if (!giftItem.isValid()) {
						continue;
					}

					if (isFirst) {
						firstName = giftItem.getName();
						isFirst = false;
					}

					size++;
				}


				text = firstName;

				if (size > 1) {

					text += "외 ("+(size - 1)+")개";
				}

			}

		} catch (Exception e) {}

		return text;
	}

	/**
	 * 주문 사은품 텍스트
	 * @param giftItems
	 * @return
	 */
	public static String makeOrderGiftItemText(List<OrderGiftItem> giftItems)  {

		String text = "";

		try {

			if (giftItems != null && !giftItems.isEmpty()) {

				String firstName = "";
				int size = 0;
				boolean isFirst = true;

				for (OrderGiftItem orderGiftItem : giftItems) {


					if (isFirst) {
						firstName = orderGiftItem.getGiftItemName();
						isFirst = false;
					}

					size++;
				}

				text = firstName;

				if (size > 1) {

					text += "외 ("+(size - 1)+")개";
				}

			}

		} catch (Exception e) {}

		return text;

	}

	public static String viewOrderGiftItemList(List<OrderGiftItem> giftItems) {

		StringBuffer sb = new StringBuffer();

		if (giftItems == null || giftItems.isEmpty()) {
			return "";
		}

		try {

			if (giftItems != null && !giftItems.isEmpty()) {

				sb.append("<ul class=\"option\">");

				boolean isFirst = true;

				StringBuffer subSb = new StringBuffer();
				String separator = " / ";

				for (OrderGiftItem orderGiftItem : giftItems) {

					if (isFirst) {

						subSb.append("<span class=\"choice\">사은품 : </span>");

						isFirst = false;
					}

					subSb.append("<span>"+orderGiftItem.getGiftItemName()+"</span>");


					subSb.append(separator);


				}

				String text = subSb.toString();

				sb.append("<li>"+text.substring(0, text.length() - separator.length() )+"</li>");

				sb.append("</ul>");
			}

			return sb.toString();

		} catch (Exception e) {
			return "";
		}
	}

	/**
	 * 사은품 데이터를 프론트 화면 데이터로 변경
	 * @param giftItemList
	 * @return
	 */
	public static List<GiftItemInfo> conventGiftItemInfoList(List<GiftItem> giftItemList) {

		List<GiftItemInfo> infoList = new ArrayList<>();

		if (giftItemList != null && !giftItemList.isEmpty()) {
			giftItemList.stream().forEach(giftItem -> {
				infoList.add(new GiftItemInfo(giftItem));
			});
		}

		return infoList;
	}

	/**
	 * 주문 사은품 데이터를 프론트 화면 데이터로 변경
	 * @param giftItemList
	 * @return
	 */
	public static List<GiftItemInfo> conventOrderGiftItemInfoList(List<OrderGiftItem> giftItemList) {

		List<GiftItemInfo> infoList = new ArrayList<>();

		if (giftItemList != null && !giftItemList.isEmpty()) {
			giftItemList.stream().forEach(giftItem -> {
				infoList.add(new GiftItemInfo(giftItem));
			});
		}

		return infoList;
	}

	/**
	 * 현재 접속하고 있는 장비
	 * @param request
	 * @return
	 */
	public static DeviceType getDeviceType(HttpServletRequest request) {

		if (isMobile(request)) {

			if (AgentUtil.isAndroid(request)) {
				return DeviceType.ANDROID;
			}

			if (AgentUtil.isIos(request)) {
				return DeviceType.IOS;
			}

			return DeviceType.MOBILE;

		} else {
			return DeviceType.PC;
		}
	}

	/**
	 * Application(IOS/ANDROID) 여부
	 * @param request
	 * @return
	 */
	public static boolean isApplication(HttpServletRequest request) {

		DeviceType deviceType = getDeviceType(request);

		if (deviceType == DeviceType.ANDROID) {
			return true;
		}

		if (deviceType == DeviceType.IOS) {
			return true;
		}

		return false;
	}

	/**
	 * Application(IOS/ANDROID) 여부
	 * @return
	 */
	public static boolean isApplication() {

		RequestContext requestContext = RequestContextUtils.getRequestContext();

		if (requestContext != null) {
			HttpServletRequest request = requestContext.getRequest();

			return isApplication(request);
		}

		return false;
	}

	/**
	 * HTML 특수문자를 표현문자로 변경
	 * @param value
	 * @return
	 */
	public static String unescapeHtml(String value) {

		if (StringUtils.isNotEmpty(value)) {
			value = value.replaceAll("&#40;", "\\(").replaceAll("&#41;", "\\)");
			value = value.replaceAll("&#39;", "'");
			value = value.replaceAll("&lt;", "<");
			value = value.replaceAll("&gt;", ">");
			value = value.replaceAll("&quot;", "\"");
			value = value.replaceAll("&#39;", "\\'");
		}

		return value;
	}

	public static String stripTags(String value) {

		if (StringUtils.isNotEmpty(value)) {
			value = value.replaceAll("<(/)?([a-zA-Z1-9]*)(\\s[a-zA-Z1-9]*=[^>]*)?(\\s)*(/)?>", "");
			value = value.replaceAll("<", "&lt;");
			value = value.replaceAll(">", "&gt;");
			value = value.replaceAll("\"", "&quot;");
			value = value.replaceAll("'", "&#39;");
		}

		return value;
	}

	public static String getSalesOnIdByHeader(HttpServletRequest request) {

		String id = request.getHeader("SALESONID");

		if (StringUtils.isEmpty(id) || "null".equals(id)) {
			return "";
		}

		return id;
	}

	/**
	 * 추가구성상품 텍스트 (장바구니, 주문서)
	 * @param additionItems
	 * @return
	 */
	public static String viewAdditionItemList(List<BuyItem> additionItems) {

		StringBuffer sb = new StringBuffer();

		try {

			if (additionItems != null && !additionItems.isEmpty()) {

				sb.append("<ul class=\"option\">");

				for (BuyItem additionItem : additionItems) {
					int quantity = additionItem.getItemPrice().getQuantity();
					int addPrice = additionItem.getItemPrice().getItemSalePrice() * quantity;

					sb.append("<input type=\"checkbox\" name=\"id\" value=\"" + additionItem.getCartId() + "\" data-item-id=\"" + additionItem.getItemId() + "\" data-parent-item-options=\"" + additionItem.getParentItemOptions() + "\" data-parent-item-id=\"" + additionItem.getParentItemId() + "\" data-addition-item-flag=\"Y\" title=\"" + additionItem.getItemName() + " 체크박스\" class=\"op-available-item\" style=\"display:none\">");
					sb.append("<li><span class=\"choice\">추가구성품 : </span>");
					sb.append("<span>" + additionItem.getItemName()).append(" ").append(quantity).append("개</span>");

					if (addPrice > 0) {
						sb.append(" (");
						sb.append(addPrice > 0 ? "+" : "-");
						sb.append(NumberUtils.formatNumber(addPrice, "#,###"));
						sb.append("원)");
					}

					sb.append("</li>");
				}

				sb.append("</ul>");

			}

		} catch (Exception e) {}

		return sb.toString();
	}

	/**
	 * 추가구성상품 텍스트 (주문완료, 마이페이지 주문/배송조회)
	 * @param additionItems
	 * @return
	 */
	public static String viewAdditionOrderItemList(List<OrderItem> additionItems) {

		StringBuffer sb = new StringBuffer();

		try {

			if (additionItems != null && !additionItems.isEmpty()) {

				sb.append("<ul class=\"option\">");

				for (OrderItem additionItem : additionItems) {
					int quantity = additionItem.getQuantity();
					int addPrice = additionItem.getItemAmount();

					sb.append("<li><span class=\"choice\">추가구성품 : </span>");
					sb.append("<span>" + additionItem.getItemName()).append(" ").append(quantity).append("개</span>");

					if (addPrice > 0) {
						sb.append(" (");
						sb.append(addPrice > 0 ? "+" : "-");
						sb.append(NumberUtils.formatNumber(addPrice, "#,###"));
						sb.append("원)");
					}

					sb.append("</li>");
				}

				sb.append("</ul>");

			}

		} catch (Exception e) {}

		return sb.toString();
	}

}
	
	