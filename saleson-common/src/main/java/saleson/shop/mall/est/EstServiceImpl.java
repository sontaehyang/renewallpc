package saleson.shop.mall.est;

import com.onlinepowers.framework.sequence.service.SequenceService;
import com.onlinepowers.framework.util.DateUtils;
import com.onlinepowers.framework.util.StringUtils;
import org.apache.commons.lang.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import saleson.shop.item.ItemService;
import saleson.shop.item.domain.Item;
import saleson.shop.item.domain.ItemOption;
import saleson.shop.mall.MallMapper;
import saleson.shop.mall.domain.*;
import saleson.shop.mall.est.converter.EstXmlConverter;
import saleson.shop.mall.est.domain.Orders;
import saleson.shop.mall.est.domain.Product;
import saleson.shop.mall.est.domain.ResultOrder;
import saleson.shop.mall.support.MallException;
import saleson.shop.mall.support.MallOrderParam;
import saleson.shop.order.OrderService;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service("estService")
public class EstServiceImpl implements EstService {
	private static final Logger log = LoggerFactory.getLogger(EstServiceImpl.class);

	@Autowired
	private EstXmlConverter estXmlConverter;
	
	@Autowired
	private EstMapper estMapper;
	
	@Autowired
	private SequenceService sequenceService;
	
	@Autowired
	private MallMapper mallMapper;
	
	@Autowired
	private ItemService itemService;
	
	@Autowired
	private OrderService orderService;
	
	/**
	 * 클레임 접수된 상태인가?
	 * @param status
	 * @return
	 */
	private boolean isClaimCode(String status) {

		String[] array = new String[]{"701", "B01", "601"};
		if (ArrayUtils.contains(array, status)) {
			return true;
		}
		
		
		return false;
	}
	
	/**
	 * 11번가 옵션을 변환
	 * @param optionText
	 * @return
	 */
	private String[] getMarketOptionText(String optionText) {
		
		if (StringUtils.isEmpty(optionText)) {
			return new String[0];
		}
		
		// 추가 금액 삭제
		String regex = "\\((\\S{1,})\\)$";
		Pattern pattern = Pattern.compile(regex);
		Matcher match = pattern.matcher(optionText);
		
		if (match.find()) {
			String addPriceText = match.group(1);
			
			optionText = optionText.replaceAll(regex, "");
			
			regex = "(.)(\\d{1,})";
			pattern = Pattern.compile(regex);
			match = pattern.matcher(addPriceText);
			if (match.find()) {
				//addPrice = Integer.parseInt(("-".equals(match.group(1)) ? "-" : "") + match.group(2));
			}
		}

		optionText = optionText.trim();
		
		// 수량 삭제
		regex = "\\-(\\d{1,})개$";
		pattern = Pattern.compile(regex);
		match = pattern.matcher(optionText);
		if (match.find()) {
			//quantity = Integer.parseInt(match.group(1));
			optionText = optionText.replaceAll(regex, "");
		}
		
		String[] temp = StringUtils.delimitedListToStringArray(optionText, ",");
		List<String> list = new ArrayList<>();
		for(int i = 0; i < temp.length; i++) {
			String[] o = StringUtils.delimitedListToStringArray(temp[i], ":");
			if (o.length < 2) {
				continue;
			}
			
			list.add(o[0].trim() + ":" + o[1].trim());
		}
		
		return StringUtils.toStringArray(list);
	}
	
	/**
	 * 상점 옵션을 11번가 페턴에 맞게 만듬
	 * @param item
	 * @param itemOption
	 * @return
	 */
	private String[] getMallOptionText(Item item, ItemOption itemOption) {
		List<String> list = new ArrayList<>();
		
		// 작성형은 무시해도 될듯??
		if (!"T".equals(itemOption.getOptionType())) {
			if ("S".equals(itemOption.getOptionType())) {
				list.add(itemOption.getOptionName1() + ":" + itemOption.getOptionName2());
			} else {
				list.add(item.getItemOptionTitle1() + ":" + itemOption.getOptionName1());
				if ("S2".equals(itemOption.getOptionType()) || "S3".equals(itemOption.getOptionType())) {
					list.add(item.getItemOptionTitle2() + ":" + itemOption.getOptionName2());
					
					if ("S3".equals(itemOption.getOptionType())) {
						list.add(item.getItemOptionTitle3() + ":" + itemOption.getOptionName3());
					}
				}
			}
		}
		
		return StringUtils.toStringArray(list);
	}
	
	@Override
	public MallConfig newOrderCollect(String apiKey, String searchStartDate, String searchEndDate, MallConfig mallConfig) {
		
		// 11번가는 결제 확인된 일자 기준으로 신규주문을 가져옴
		String url = "https://api.11st.co.kr/rest/ordservices/complete/"+ searchStartDate +"/"+ searchEndDate;
		Orders result = (Orders) estXmlConverter.convertXmlUrlToObject(url, apiKey, Orders.class);
		
		mallConfig.setLastSearchStartDate("");
		mallConfig.setLastSearchEndDate("");
		
		String collectStatusCode = "1";
		if (result != null) {
			
			// 정상 조회시에만 일자를 기록
			mallConfig.setLastSearchStartDate(searchStartDate);
			mallConfig.setLastSearchEndDate(searchEndDate);
			
			MallOrderParam mallOrderParam = new MallOrderParam();
			mallOrderParam.setMallConfigId(mallConfig.getMallConfigId());
			
			if (result.getList().size() > 0) {
				for(Product product : result.getList()) {
					
					// 이미 등록?
					product.setMallConfigId(mallConfig.getMallConfigId());
					product.setMallType(mallConfig.getMallType());
					if (estMapper.getMallOrderCount(product) > 0) {
						continue;
					}
					
					try {
						
						mallOrderParam.setProductCode(Integer.toString(product.getPrdNo()));
						Item item = mallMapper.getItemByMallConfig(mallOrderParam);
						if (item != null) {
							
							boolean isSet = true;
							
							// 1. 몰에 옵션은 사용안하는데 11번가에서는 옵션이 넘어옴??
							if ("N".equals(item.getItemOptionFlag()) && StringUtils.isNotEmpty(product.getSlctPrdOptNm())) {
								isSet = false;
							} else {
								List<ItemOption> options = itemService.getItemOptionList(item, false);
								
								if (!options.isEmpty()) {
									String[] marketOptionArray = this.getMarketOptionText(product.getSlctPrdOptNm());
									
									
									int successCount = 0;
									String matchedOptions = "";

									if ("S".equals(item.getItemOptionType())) {
										
										int optionCount = 0;
										HashMap<String, String> map = new HashMap<>();
										for(ItemOption itemOption : options) {
											
											// 작성형은 무시
											if (!"T".equals(itemOption.getOptionType())) {
												if (map.get(itemOption.getOptionName1()) == null) {
													map.put(itemOption.getOptionName1(), itemOption.getOptionName1());
												}
											}
										}
										optionCount = map.keySet().size();
										
										for(ItemOption itemOption : options) {
											String[] optionArray = this.getMallOptionText(item, itemOption);
											for(int i = 0; i < marketOptionArray.length; i++) {
												if (ArrayUtils.contains(optionArray, marketOptionArray[i])) {
													matchedOptions += (StringUtils.isEmpty(matchedOptions) ? "" : "^^^") + Integer.toString(itemOption.getItemOptionId());
													successCount++;
												}
											}
										}
										
										if (optionCount == successCount) {
											isSet = true;
											product.setMatchedOptions(matchedOptions);
										}
									} else {
										for(ItemOption itemOption : options) {
											String[] optionArray = this.getMallOptionText(item, itemOption);
												
											for(int i = 0; i < marketOptionArray.length; i++) {
												if (ArrayUtils.contains(optionArray, marketOptionArray[i])) {
													matchedOptions = Integer.toString(itemOption.getItemOptionId());
													successCount++;
												}
											}
											
											if (optionArray.length == successCount && successCount > 0) {
												isSet = true;
												product.setMatchedOptions(matchedOptions);
												break;
											}
										}
									}
								}
							}
							
							if (isSet) {
								product.setItemId(item.getItemId());
							}
						}
						
						
						product.setOrdPrdStatNm("결제완료");
						product.setMallOrderId(sequenceService.getId("OP_MALL_ORDER"));
						estMapper.insertMallOrder(product);
						
					} catch(Exception e) {
						
						log.error("ERROR: {}", e.getMessage(), e);
						collectStatusCode = "2";
					}
					
				}
			}
		}
		
		mallConfig.setStatusCode(collectStatusCode);
		return mallConfig;

	}
	
	@Override
	public MallConfig claimCollect(String apiKey, String searchStartDate, String searchEndDate, MallConfig mallConfig) {
		
		HashMap<String, String> orderMap = new HashMap<>();
		
		// 취소
		String url = "http://api.11st.co.kr/rest/claimservice/cancelorders/"+ searchStartDate +"/"+ searchEndDate;
		Orders result = (Orders) estXmlConverter.convertXmlUrlToObject(url, apiKey, Orders.class);
		
		mallConfig.setLastClaimSearchStartDate("");
		mallConfig.setLastClaimSearchEndDate("");
		
		String claimCollectStatusCode = "1";
		if (result != null) {
			
			claimCollectStatusCode = "1";
			
			// 정상 조회시에만 일자를 기록
			mallConfig.setLastClaimSearchStartDate(searchStartDate);
			mallConfig.setLastClaimSearchEndDate(searchEndDate);
			
			if (result.getList().size() > 0) {

				for(Product product : result.getList()) {
					
					try {
						product.setMallConfigId(mallConfig.getMallConfigId());
						product.setMallType(mallConfig.getMallType());
						
						MallOrderCancel cancelApply = estMapper.getMallOrderCancel(product);
						if (cancelApply == null) {
							continue;
						}
						
						// 주문번호 셋팅
						product.setMallOrderId(cancelApply.getMallOrderId());
						
						// 클레임번호가 같은경우 새로 등록 안되야함
						if (StringUtils.isEmpty(cancelApply.getClaimCode())) {
							estMapper.insertMallOrderCancel(product);
						} else {
							estMapper.updateMallOrderCancel(product);
						}
						
						// 밑에서 마스터 테이블 수정
						orderMap.put(cancelApply.getOrderCode(), product.getOrdPrdSeq() + "||" + cancelApply.getMallOrderId());
						
					} catch(Exception e) {
						
						log.error("ERROR: {}", e.getMessage(), e);
						claimCollectStatusCode = "2";
					}
					
				}
			}
		}
		
		// 취소완료
		url = "http://api.11st.co.kr/rest/claimservice/canceledorders/"+ searchStartDate +"/"+ searchEndDate;
		result = (Orders) estXmlConverter.convertXmlUrlToObject(url, apiKey, Orders.class);
		
		mallConfig.setLastClaimSearchStartDate("");
		mallConfig.setLastClaimSearchEndDate("");
		
		if (result != null) {
			
			claimCollectStatusCode = "1";
			
			// 정상 조회시에만 일자를 기록
			mallConfig.setLastClaimSearchStartDate(searchStartDate);
			mallConfig.setLastClaimSearchEndDate(searchEndDate);
			
			if (result.getList().size() > 0) {
				for(Product product : result.getList()) {
					
					try {
						
						product.setMallConfigId(mallConfig.getMallConfigId());
						product.setMallType(mallConfig.getMallType());
						
						MallOrderCancel cancelApply = estMapper.getMallOrderCancel(product);
						if (cancelApply == null) {
							continue;
						}
						
						// 주문번호 셋팅
						product.setMallOrderId(cancelApply.getMallOrderId());
						
						// 클레임번호가 같은경우 새로 등록 안되야함
						if (StringUtils.isEmpty(cancelApply.getClaimCode())) {
							estMapper.insertMallOrderCancel(product);
						} else {
							estMapper.updateMallOrderCancel(product);
						}
						
						// 밑에서 마스터 테이블 수정
						orderMap.put(cancelApply.getOrderCode(), product.getOrdPrdSeq() + "||" + cancelApply.getMallOrderId());
						
					} catch(Exception e) {
						
						log.error("ERROR: {}", e.getMessage(), e);
						claimCollectStatusCode = "2";
					}
					
				}
			}
		}
		
		// 취소철회
		url = "http://api.11st.co.kr/rest/claimservice/withdrawcanceledorders/"+ searchStartDate +"/"+ searchEndDate;
		result = (Orders) estXmlConverter.convertXmlUrlToObject(url, apiKey, Orders.class);
		
		mallConfig.setLastClaimSearchStartDate("");
		mallConfig.setLastClaimSearchEndDate("");
		
		if (result != null) {
			
			claimCollectStatusCode = "1";
			
			// 정상 조회시에만 일자를 기록
			mallConfig.setLastClaimSearchStartDate(searchStartDate);
			mallConfig.setLastClaimSearchEndDate(searchEndDate);
			
			if (result.getList().size() > 0) {
				for(Product product : result.getList()) {
					
					try {
						
						product.setMallConfigId(mallConfig.getMallConfigId());
						product.setMallType(mallConfig.getMallType());
						
						MallOrderCancel cancelApply = estMapper.getMallOrderCancel(product);
						if (cancelApply == null) {
							continue;
						}
						
						// 주문번호 셋팅
						product.setMallOrderId(cancelApply.getMallOrderId());
						
						// 클레임번호가 같은경우 새로 등록 안되야함
						if (StringUtils.isEmpty(cancelApply.getClaimCode())) {
							estMapper.insertMallOrderCancel(product);
						} else {
							estMapper.updateMallOrderCancel(product);
						}
						
						// 밑에서 마스터 테이블 수정
						orderMap.put(cancelApply.getOrderCode(), product.getOrdPrdSeq() + "||" + cancelApply.getMallOrderId());
						
					} catch(Exception e) {
						
						log.error("ERROR: {}", e.getMessage(), e);
						claimCollectStatusCode = "2";
					}
					
				}
			}
		}
		
		// 반품신청
		url = "http://api.11st.co.kr/rest/claimservice/returnorders/"+ searchStartDate +"/"+ searchEndDate;
		result = (Orders) estXmlConverter.convertXmlUrlToObject(url, apiKey, Orders.class);
		
		mallConfig.setLastClaimSearchStartDate("");
		mallConfig.setLastClaimSearchEndDate("");
		
		if (result != null) {
			
			claimCollectStatusCode = "1";
			
			// 정상 조회시에만 일자를 기록
			mallConfig.setLastClaimSearchStartDate(searchStartDate);
			mallConfig.setLastClaimSearchEndDate(searchEndDate);
			
			if (result.getList().size() > 0) {
				for(Product product : result.getList()) {
					
					try {
						
						product.setMallConfigId(mallConfig.getMallConfigId());
						product.setMallType(mallConfig.getMallType());
						
						MallOrderReturn returnApply = estMapper.getMallOrderReturn(product);
						if (returnApply == null) {
							continue;
						}
						
						// 주문번호 셋팅
						product.setMallOrderId(returnApply.getMallOrderId());
						
						// 클레임번호가 같은경우 새로 등록 안되야함
						if (StringUtils.isEmpty(returnApply.getClaimCode())) {
							estMapper.insertMallOrderReturn(product);
						} else {
							estMapper.updateMallOrderReturn(product);
						}
						
						// 밑에서 마스터 테이블 수정
						orderMap.put(returnApply.getOrderCode(), product.getOrdPrdSeq() + "||" + returnApply.getMallOrderId());
						
					} catch(Exception e) {
						
						log.error("ERROR: {}", e.getMessage(), e);
						claimCollectStatusCode = "2";
					}
					
				}
			}
		}
		
		// 반품완료
		url = "http://api.11st.co.kr/rest/claimservice/returnedorders/"+ searchStartDate +"/"+ searchEndDate;
		result = (Orders) estXmlConverter.convertXmlUrlToObject(url, apiKey, Orders.class);
		
		mallConfig.setLastClaimSearchStartDate("");
		mallConfig.setLastClaimSearchEndDate("");
		
		if (result != null) {
			
			claimCollectStatusCode = "1";
			
			// 정상 조회시에만 일자를 기록
			mallConfig.setLastClaimSearchStartDate(searchStartDate);
			mallConfig.setLastClaimSearchEndDate(searchEndDate);
			
			if (result.getList().size() > 0) {
				for(Product product : result.getList()) {
					
					try {
						
						product.setMallConfigId(mallConfig.getMallConfigId());
						product.setMallType(mallConfig.getMallType());
						
						MallOrderReturn returnApply = estMapper.getMallOrderReturn(product);
						if (returnApply == null) {
							continue;
						}
						
						// 주문번호 셋팅
						product.setMallOrderId(returnApply.getMallOrderId());
						
						// 클레임번호가 같은경우 새로 등록 안되야함
						if (StringUtils.isEmpty(returnApply.getClaimCode())) {
							estMapper.insertMallOrderReturn(product);
						} else {
							estMapper.updateMallOrderReturn(product);
						}
						
						// 밑에서 마스터 테이블 수정
						orderMap.put(returnApply.getOrderCode(), product.getOrdPrdSeq() + "||" + returnApply.getMallOrderId());
						
					} catch(Exception e) {
						
						log.error("ERROR: {}", e.getMessage(), e);
						claimCollectStatusCode = "2";
					}
					
				}
			}
		}
		
		// 반품철회
		url = "http://api.11st.co.kr/rest/claimservice/retractretorders/"+ searchStartDate +"/"+ searchEndDate;
		result = (Orders) estXmlConverter.convertXmlUrlToObject(url, apiKey, Orders.class);
		
		mallConfig.setLastClaimSearchStartDate("");
		mallConfig.setLastClaimSearchEndDate("");
		
		if (result != null) {
			
			claimCollectStatusCode = "1";
			
			// 정상 조회시에만 일자를 기록
			mallConfig.setLastClaimSearchStartDate(searchStartDate);
			mallConfig.setLastClaimSearchEndDate(searchEndDate);
			
			if (result.getList().size() > 0) {
				for(Product product : result.getList()) {
					
					try {
						
						product.setMallConfigId(mallConfig.getMallConfigId());
						product.setMallType(mallConfig.getMallType());
						
						MallOrderReturn returnApply = estMapper.getMallOrderReturn(product);
						if (returnApply == null) {
							continue;
						}
						
						// 주문번호 셋팅
						product.setMallOrderId(returnApply.getMallOrderId());
						
						// 클레임번호가 같은경우 새로 등록 안되야함
						if (StringUtils.isEmpty(returnApply.getClaimCode())) {
							estMapper.insertMallOrderReturn(product);
						} else {
							estMapper.updateMallOrderReturn(product);
						}
						
						// 밑에서 마스터 테이블 수정
						orderMap.put(returnApply.getOrderCode(), product.getOrdPrdSeq() + "||" + returnApply.getMallOrderId());
						
					} catch(Exception e) {
						
						log.error("ERROR: {}", e.getMessage(), e);
						claimCollectStatusCode = "2";
					}
					
				}
			}
		}
		
		// 교환요청
		url = "http://api.11st.co.kr/rest/claimservice/exchangeorders/"+ searchStartDate +"/"+ searchEndDate;
		result = (Orders) estXmlConverter.convertXmlUrlToObject(url, apiKey, Orders.class);
		
		mallConfig.setLastClaimSearchStartDate("");
		mallConfig.setLastClaimSearchEndDate("");
		
		if (result != null) {
			
			claimCollectStatusCode = "1";
			
			// 정상 조회시에만 일자를 기록
			mallConfig.setLastClaimSearchStartDate(searchStartDate);
			mallConfig.setLastClaimSearchEndDate(searchEndDate);
			
			if (result.getList().size() > 0) {
				for(Product product : result.getList()) {
					
					try {
						
						product.setMallConfigId(mallConfig.getMallConfigId());
						product.setMallType(mallConfig.getMallType());
						
						MallOrderExchange exchangeApply = estMapper.getMallOrderExchange(product);
						if (exchangeApply == null) {
							continue;
						}
						
						// 주문번호 셋팅
						product.setMallOrderId(exchangeApply.getMallOrderId());
						
						// 클레임번호가 같은경우 새로 등록 안되야함
						if (StringUtils.isEmpty(exchangeApply.getClaimCode())) {
							estMapper.insertMallOrderExchange(product);
						} else {
							estMapper.updateMallOrderExchange(product);
						}
						
						// 밑에서 마스터 테이블 수정
						orderMap.put(exchangeApply.getOrderCode(), product.getOrdPrdSeq() + "||" + exchangeApply.getMallOrderId());
						
					} catch(Exception e) {
						
						log.error("ERROR: {}", e.getMessage(), e);
						claimCollectStatusCode = "2";
					}
					
				}
			}
		}
		
		// 교환완료
		url = "http://api.11st.co.kr/rest/claimservice/exchangedorders/"+ searchStartDate +"/"+ searchEndDate;
		result = (Orders) estXmlConverter.convertXmlUrlToObject(url, apiKey, Orders.class);
		
		mallConfig.setLastClaimSearchStartDate("");
		mallConfig.setLastClaimSearchEndDate("");
		
		if (result != null) {
			
			claimCollectStatusCode = "1";
			
			// 정상 조회시에만 일자를 기록
			mallConfig.setLastClaimSearchStartDate(searchStartDate);
			mallConfig.setLastClaimSearchEndDate(searchEndDate);
			
			if (result.getList().size() > 0) {
				for(Product product : result.getList()) {
					
					try {
						
						product.setMallConfigId(mallConfig.getMallConfigId());
						product.setMallType(mallConfig.getMallType());
						
						MallOrderExchange exchangeApply = estMapper.getMallOrderExchange(product);
						if (exchangeApply == null) {
							continue;
						}
						
						// 주문번호 셋팅
						product.setMallOrderId(exchangeApply.getMallOrderId());
						
						// 클레임번호가 같은경우 새로 등록 안되야함
						if (StringUtils.isEmpty(exchangeApply.getClaimCode())) {
							estMapper.insertMallOrderExchange(product);
						} else {
							estMapper.updateMallOrderExchange(product);
						}
						
						// 밑에서 마스터 테이블 수정
						orderMap.put(exchangeApply.getOrderCode(), product.getOrdPrdSeq() + "||" + exchangeApply.getMallOrderId());
						
					} catch(Exception e) {
						
						log.error("ERROR: {}", e.getMessage(), e);
						claimCollectStatusCode = "2";
					}
					
				}
			}
		}
		
		// 교환철회
		url = "http://api.11st.co.kr/rest/claimservice/retractexcorders/"+ searchStartDate +"/"+ searchEndDate;
		result = (Orders) estXmlConverter.convertXmlUrlToObject(url, apiKey, Orders.class);
		
		mallConfig.setLastClaimSearchStartDate("");
		mallConfig.setLastClaimSearchEndDate("");
		
		if (result != null) {
			
			claimCollectStatusCode = "1";
			
			// 정상 조회시에만 일자를 기록
			mallConfig.setLastClaimSearchStartDate(searchStartDate);
			mallConfig.setLastClaimSearchEndDate(searchEndDate);
			
			if (result.getList().size() > 0) {
				for(Product product : result.getList()) {
					
					try {
						
						product.setMallConfigId(mallConfig.getMallConfigId());
						product.setMallType(mallConfig.getMallType());
						
						MallOrderExchange exchangeApply = estMapper.getMallOrderExchange(product);
						if (exchangeApply == null) {
							continue;
						}
						
						// 주문번호 셋팅
						product.setMallOrderId(exchangeApply.getMallOrderId());
						
						// 클레임번호가 같은경우 새로 등록 안되야함
						if (StringUtils.isEmpty(exchangeApply.getClaimCode())) {
							estMapper.insertMallOrderExchange(product);
						} else {
							estMapper.updateMallOrderExchange(product);
						}
						
						// 밑에서 마스터 테이블 수정
						orderMap.put(exchangeApply.getOrderCode(), product.getOrdPrdSeq() + "||" + exchangeApply.getMallOrderId());
						
					} catch(Exception e) {
						
						log.error("ERROR: {}", e.getMessage(), e);
						claimCollectStatusCode = "2";
					}
					
				}
			}
		}
		
		if (orderMap.keySet().size() > 0) {
			
			HashMap<String, Orders> ordersMap = new HashMap<String, Orders>();
			for(String orderCode : orderMap.keySet()) {
				
				String[] temp = StringUtils.delimitedListToStringArray(orderMap.get(orderCode), "||");
				
				Orders orders = ordersMap.get(orderCode);
				if (orders == null) {
					orders = this.getOrderStatus(apiKey, orderCode);
					ordersMap.put(orderCode, orders);
				}
				
				Product nowProduct = this.getNowProductInfoForOrders(orders, Integer.parseInt(temp[0]), orderCode);
				if (nowProduct == null) {
					continue;
				}
				
				nowProduct.setMallOrderId(Integer.parseInt(temp[1]));
				estMapper.updateMallOrder(nowProduct);
			}
		}
		
		mallConfig.setClaimStatusCode(claimCollectStatusCode);
		return mallConfig;
	}
	
	@Override
	public Orders getOrderStatus(String apiKey, String orderCode) {
		
		String url = "https://api.11st.co.kr/rest/ordservices/complete/"+ orderCode;
		return (Orders) estXmlConverter.convertXmlUrlToObject(url, apiKey, Orders.class);
		
	}
	
	@Override
	public void packaging(String apiKey, MallOrder mallOrder, HashMap<String, Integer> stockMap) {
			
		String url = "https://api.11st.co.kr/rest/ordservices/reqpackaging";
		url += "/" + mallOrder.getOrderCode();
		url += "/" + mallOrder.getOrderIndex();
		url += "/" + mallOrder.getAdditionItemFlag();
		url += "/" + mallOrder.getParentProductCode();
		url += "/" + mallOrder.getShippingCode();

		ResultOrder resultOrder = (ResultOrder) estXmlConverter.convertXmlUrlToObject(url, apiKey, ResultOrder.class);
		
		if (resultOrder == null) {
			
			throw new MallException();
			
		}
			
		// 정상
		boolean isSuccess = false;
		String systemMessage = "";
		if ("0".equals(resultOrder.getResultCode())) {
			
			isSuccess = true;

			
			
		} else {
			
			// Api - 11번가 업데이트 실패
			systemMessage = "발주확인 처리에 실패 하였습니다. - " + resultOrder.getResultText();
			
		}
		
		Orders orders = this.getOrderStatus(apiKey, mallOrder.getOrderCode());
		Product nowProduct = this.getNowProductInfoForOrders(orders, mallOrder.getOrderIndex(), mallOrder.getOrderCode());
		if (nowProduct == null) {
			throw new MallException();
		}

		// 클레임 접수된상태이면 상태를 변경하지 않음
		if (isSuccess == false) {
			if (this.isClaimCode(nowProduct.getOrdPrdStat())) {
				nowProduct.setOrdPrdStat("");
			}
		} else {
			
			// 주문취소??
			int cancelCount = mallOrder.getQuantity() - nowProduct.getOrdQty();
			if (cancelCount > 0) {
				systemMessage = "주문취소 요청이 있습니다. 주문수량을 확인해주세요.";
				
				if (stockMap != null) {
					// 주문취소 요청수량 빼기~
					for(String key : stockMap.keySet()) {
						stockMap.put(key, (stockMap.get(key) - cancelCount));
					}
				}
				
			}
			
			if (stockMap != null) {
				
				// 재고량 차감
				orderService.updateStockDeduction(stockMap);
			}
			
		}
		
		nowProduct.setMallOrderId(mallOrder.getMallOrderId());
		nowProduct.setSystemMessage(systemMessage);
		estMapper.updateMallOrder(nowProduct);
	}
	
	@Override
	public Product getNowProductInfoForOrders(Orders orders, int orderIndex, String orderCode) {
		
		if (orders == null) {
			return null;
		}
		
		// 주문번호와 주문순번이 같으면 같은 주문?
		for(Product product : orders.getList()) {
			if (product.getOrdPrdSeq() == orderIndex && product.getOrdNo().equals(orderCode)) {
				return product;
			}
		}
		
		return null;
	}
	
	@Override
	public void shipping(String apiKey, MallOrder mallOrder) {
		
		
		String url = "https://api.11st.co.kr/rest/ordservices/reqdelivery";
		url += "/" + DateUtils.getToday("yyyyMMddHHmm");
		
		/**
		 * 배송 방식
		 * 01 : 택배, 02 : 우편, 03 : 직접(화물배달), 04 : 퀵, 05 : 배송없음 (배송업체와 송장번호는 null입력)
		 * 택배로 고정?
		 */
		url += "/01";
		
		/** 택배사 코드
		 * 00001 : 동부익스프레스
		 * 00002 : 로젠택배
		 * 00006 : 옐로우캡
		 * 00007 : 우체국택배
		 * 00008 : 우편등기
		 * 00011 : 한진택배
		 * 00012 : 현대택배
		 * 00014 : KGB택배
		 * 00019 : 이노지스택배
		 * 00021 : 대신택배
		 * 00022 : 일양로지스
		 * 00023 : ACI
		 * 00025 : WIZWA
		 * 00026 : 경동택배
		 * 00027 : 천일택배
		 * 00028 : KGL
		 * 00031 : OCS Korea
		 * 00033 : GTX택배
		 * 00034 : CJ대한통운
		 * 00035 : 합동택배
		 * 00037 : 건영택배
		 * 00099 : 기타
		 */
		url += "/" + mallOrder.getDeliveryCompanyCode();
		url += "/" + mallOrder.getDeliveryNumber();
		url += "/" + mallOrder.getShippingCode();

		ResultOrder resultOrder = (ResultOrder) estXmlConverter.convertXmlUrlToObject(url, apiKey, ResultOrder.class);
		
		if (resultOrder == null) {
			throw new MallException();
		}
		
		// 정상
		boolean isSuccess = false;
		String systemMessage = "";
		if ("0".equals(resultOrder.getResultCode())) {
			
			isSuccess = true;
			
		} else {
			
			// Api - 11번가 업데이트 실패
			systemMessage = "배송처리에 실패 하였습니다. - " + resultOrder.getResultText();
			
		}
		
		Orders orders = this.getOrderStatus(apiKey, mallOrder.getOrderCode());
		Product nowProduct = this.getNowProductInfoForOrders(orders, mallOrder.getOrderIndex(), mallOrder.getOrderCode());
		if (nowProduct == null) {
			throw new MallException();
		}

		// 클레임 접수된상태이면 상태를 변경하지 않음
		if (isSuccess == false) {
			if (this.isClaimCode(nowProduct.getOrdPrdStat())) {
				nowProduct.setOrdPrdStat("");
			}
		}
		
		nowProduct.setMallOrderId(mallOrder.getMallOrderId());
		nowProduct.setSystemMessage(systemMessage);
		estMapper.updateMallOrder(nowProduct);
		
	}
	
	@Override
	public void cancelApply(String apiKey, MallOrderCancel cancelApply) {
		
		MallOrder mallOrder = cancelApply.getMallOrder();
		
		String reasonText = "";
		if (StringUtils.isNotEmpty(cancelApply.getCancelReasonText())) {
			try {
				reasonText = URLEncoder.encode(cancelApply.getCancelReasonText(), "UTF-8");
			} catch (UnsupportedEncodingException e) {
				log.error("ERROR: {}", e.getMessage(), e);
			}
		}
		
		String url = "https://api.11st.co.kr/rest/claimservice/reqrejectorder";
		url += "/" + mallOrder.getOrderCode();
		url += "/" + mallOrder.getOrderIndex();
		url += "/" + cancelApply.getCancelReason();
		url += "/" + reasonText;
		
		ResultOrder resultOrder = (ResultOrder) estXmlConverter.convertXmlUrlToObject(url, apiKey, ResultOrder.class);
		
		if (resultOrder == null) {
			throw new MallException();
		}
		
		// 정상
		boolean isSuccess = false;
		String systemMessage = "";
		if ("0".equals(resultOrder.getResultCode())) {
			isSuccess = true;
		} else {
			systemMessage = "취소 처리에 실패 하였습니다. - " + resultOrder.getResultText();
		}
		
		Orders orders = this.getOrderStatus(apiKey, mallOrder.getOrderCode());
		Product nowProduct = this.getNowProductInfoForOrders(orders, mallOrder.getOrderIndex(), mallOrder.getOrderCode());
		if (nowProduct == null) {
			throw new MallException();
		}

		// 클레임 접수된상태이면 상태를 변경하지 않음
		if (isSuccess == false) {
			if (this.isClaimCode(nowProduct.getOrdPrdStat())) {
				nowProduct.setOrdPrdStat("");
			}
		}
		
		nowProduct.setMallOrderId(mallOrder.getMallOrderId());
		nowProduct.setSystemMessage(systemMessage);
		estMapper.updateMallOrder(nowProduct);
	}
	
	@Override
	public void cancelConfirm(String apiKey, MallOrderCancel cancelApply) {
		
		MallOrder mallOrder = cancelApply.getMallOrder();
		
		String url = "http://api.11st.co.kr/rest/claimservice/cancelreqconf";
		url += "/" + cancelApply.getClaimCode();
		url += "/" + mallOrder.getOrderCode();
		url += "/" + mallOrder.getOrderIndex();
		
		ResultOrder resultOrder = (ResultOrder) estXmlConverter.convertXmlUrlToObject(url, apiKey, ResultOrder.class);
		
		if (resultOrder == null) {
			throw new MallException();
		}
		
		// 정상
		boolean isSuccess = false;
		String systemMessage = "";
		if ("0".equals(resultOrder.getResultCode())) {
			
			// 주문서 변경 - 배송준비증으로 변경
			isSuccess = true;
			
		} else {
			
			// Api - 11번가 업데이트 실패
			systemMessage = "취소 처리에 실패 하였습니다. - " + resultOrder.getResultText();
			
		}
		
		Orders orders = this.getOrderStatus(apiKey, cancelApply.getOrderCode());
		Product nowProduct = this.getNowProductInfoForOrders(orders, mallOrder.getOrderIndex(), mallOrder.getOrderCode());
		if (nowProduct == null) {
			throw new MallException();
		}

		// 클레임 접수된상태이면 상태를 변경하지 않음
		if (isSuccess == false) {
			if (this.isClaimCode(nowProduct.getOrdPrdStat())) {
				nowProduct.setOrdPrdStat("");
			}
		}
		
		nowProduct.setMallOrderId(cancelApply.getMallOrderId());
		nowProduct.setSystemMessage(systemMessage);
		estMapper.updateMallOrder(nowProduct);
	}
	
	@Override
	public void returnConfirm(String apiKey, MallOrderReturn returnApply) {
		MallOrder mallOrder = returnApply.getMallOrder();
		
		String url = "http://api.11st.co.kr/rest/claimservice/returnreqconf";
		url += "/" + returnApply.getClaimCode();
		url += "/" + mallOrder.getOrderCode();
		url += "/" + mallOrder.getOrderIndex();
		
		ResultOrder resultOrder = (ResultOrder) estXmlConverter.convertXmlUrlToObject(url, apiKey, ResultOrder.class);
		
		if (resultOrder == null) {
			throw new MallException();
		}
		
		// 정상
		boolean isSuccess = false;
		String systemMessage = "";
		if ("0".equals(resultOrder.getResultCode())) {
			
			// 주문서 변경 - 배송준비증으로 변경
			isSuccess = true;
			
		} else {
			
			// Api - 11번가 업데이트 실패
			systemMessage = "반품 승인 처리에 실패 하였습니다. - " + resultOrder.getResultText();
			
		}
		
		Orders orders = this.getOrderStatus(apiKey, returnApply.getOrderCode());
		Product nowProduct = this.getNowProductInfoForOrders(orders, mallOrder.getOrderIndex(), mallOrder.getOrderCode());
		if (nowProduct == null) {
			throw new MallException();
		}
		
		nowProduct.setMallOrderId(returnApply.getMallOrderId());
		
		// 클레임 접수된상태이면 상태를 변경하지 않음
		if (isSuccess == false) {
			if (this.isClaimCode(nowProduct.getOrdPrdStat())) {
				nowProduct.setOrdPrdStat("");
			}
		} else {
			// 반품완료.. Api가 없다..
			nowProduct.setClmReqSeq(returnApply.getClaimCode());
			nowProduct.setClmStat("106");
			estMapper.updateReturnConfirmForApply(nowProduct);
		}
		
		
		nowProduct.setSystemMessage(systemMessage);
		estMapper.updateMallOrder(nowProduct);
	}
	
	@Override
	public void exchangeConfirm(String apiKey, MallOrderExchange exchangeApply) {
		MallOrder mallOrder = exchangeApply.getMallOrder();
		
		String url = "http://api.11st.co.kr/rest/claimservice/exchangereqconf";
		url += "/" + exchangeApply.getClaimCode();
		url += "/" + mallOrder.getOrderCode();
		url += "/" + mallOrder.getOrderIndex();
		url += "/" + exchangeApply.getResendDeliveryCompanyCode();
		url += "/" + exchangeApply.getResendDeliveryNumber();
		
		ResultOrder resultOrder = (ResultOrder) estXmlConverter.convertXmlUrlToObject(url, apiKey, ResultOrder.class);
		
		if (resultOrder == null) {
			throw new MallException();
		}
		
		// 정상
		boolean isSuccess = false;
		String systemMessage = "";
		if ("0".equals(resultOrder.getResultCode())) {
			
			// 주문서 변경 - 배송준비증으로 변경
			isSuccess = true;
			
		} else {
			
			// Api - 11번가 업데이트 실패
			systemMessage = "교환 완료 처리에 실패 하였습니다. - " + resultOrder.getResultText();
			
		}
		
		Orders orders = this.getOrderStatus(apiKey, exchangeApply.getOrderCode());
		Product nowProduct = this.getNowProductInfoForOrders(orders, mallOrder.getOrderIndex(), mallOrder.getOrderCode());
		if (nowProduct == null) {
			throw new MallException();
		}
		
		nowProduct.setMallOrderId(exchangeApply.getMallOrderId());
		
		// 클레임 접수된상태이면 상태를 변경하지 않음
		if (isSuccess == false) {
			if (this.isClaimCode(nowProduct.getOrdPrdStat())) {
				nowProduct.setOrdPrdStat("");
			}
		} else {
			// 교환완료.. Api가 없다..
			nowProduct.setClmReqSeq(exchangeApply.getClaimCode());
			nowProduct.setClmStat("221");
			nowProduct.setResendDeliveryCompanyCode(exchangeApply.getResendDeliveryCompanyCode());
			nowProduct.setResendDeliveryNumber(exchangeApply.getResendDeliveryNumber());
			estMapper.updateExchangeConfirmForApply(nowProduct);
		}
		
		
		nowProduct.setSystemMessage(systemMessage);
		estMapper.updateMallOrder(nowProduct);
	}
	
	@Override
	public void cancelRefusal(String apiKey, MallOrderCancel cancelApply) {

		MallOrder mallOrder = cancelApply.getMallOrder();
		
		String reasonText = "";
		if (StringUtils.isNotEmpty(cancelApply.getCancelRefusalResonText())) {
			try {
				reasonText = URLEncoder.encode(cancelApply.getCancelRefusalResonText(), "UTF-8");
			} catch (UnsupportedEncodingException e) {
				log.error("ERROR: {}", e.getMessage(), e);
			}
		}
		
		String url = "https://api.11st.co.kr/rest/claimservice/cancelreqrejectNEW";
		url += "/" + mallOrder.getOrderCode();
		url += "/" + mallOrder.getOrderIndex();
		url += "/" + cancelApply.getClaimCode();
		/**
		 * 배송 방식
		 * 01 : 택배, 02 : 우편, 03 : 직접(화물배달), 04 : 퀵, 05 : 배송없음 (배송업체와 송장번호는 null입력)
		 * 택배로 고정?
		 */
		url += "/01";
		url += "/" + cancelApply.getCancelDeliveryDate();
		url += "/" + cancelApply.getCancelDeliveryCompanyCode();
		url += "/" + cancelApply.getCancelDeliveryNumber();
		
		/**
		 * 01 : 취소책임사유 입력 오류
		 * 02 : 상품 발송처리 완료
		 */
		url += "/" + cancelApply.getCancelRefusalReson();
		url += "/" + reasonText;
		
		ResultOrder resultOrder = (ResultOrder) estXmlConverter.convertXmlUrlToObject(url, apiKey, ResultOrder.class);
		if (resultOrder == null) {
			throw new MallException();
		}
			
		// 정상
		boolean isSuccess = false;
		String systemMessage = "";
		if ("0".equals(resultOrder.getResultCode())) {
			
			isSuccess = true;
			
		} else {
			
			// Api - 11번가 업데이트 실패
			systemMessage = "취소 거부처리에 실패 하였습니다. - " + resultOrder.getResultText();
			
		}
		
		Orders orders = this.getOrderStatus(apiKey, cancelApply.getOrderCode());
		Product nowProduct = this.getNowProductInfoForOrders(orders, mallOrder.getOrderIndex(), mallOrder.getOrderCode());
		if (nowProduct == null) {
			throw new MallException();
		}
		
		// 클레임 접수된상태이면 상태를 변경하지 않음
		if (isSuccess == false) {
			if (this.isClaimCode(nowProduct.getOrdPrdStat())) {
				nowProduct.setOrdPrdStat("");
			}
		}
		
		nowProduct.setMallOrderId(cancelApply.getMallOrderId());
		nowProduct.setSystemMessage(systemMessage);
		estMapper.updateMallOrder(nowProduct);
	}
	
	@Override
	public void returnHold(String apiKey, MallOrderReturn returnApply) {
		MallOrder mallOrder = returnApply.getMallOrder();
		
		String reasonText = "";
		if (StringUtils.isNotEmpty(returnApply.getRtHoldResonText())) {
			try {
				reasonText = URLEncoder.encode(returnApply.getRtHoldResonText(), "UTF-8");
			} catch (UnsupportedEncodingException e) {
				log.error("ERROR: {}", e.getMessage(), e);
			}
		}
		
		String url = "http://api.11st.co.kr/rest/claimservice/returnclaimdefer";
		url += "/" + mallOrder.getOrderCode();
		url += "/" + mallOrder.getOrderIndex();
		url += "/" + returnApply.getClaimCode();
		url += "/" + returnApply.getRtHoldReson();
		url += "/" + reasonText;
		
		ResultOrder resultOrder = (ResultOrder) estXmlConverter.convertXmlUrlToObject(url, apiKey, ResultOrder.class);
		if (resultOrder == null) {
			throw new MallException();
		}
			
		// 정상
		boolean isSuccess = false;
		String systemMessage = "";
		if ("0".equals(resultOrder.getResultCode())) {
			
			isSuccess = true;
			
		} else {
			
			// Api - 11번가 업데이트 실패
			systemMessage = "반품 보류 처리에 실패 하였습니다. - " + resultOrder.getResultText();
			
		}
		
		Orders orders = this.getOrderStatus(apiKey, returnApply.getOrderCode());
		Product nowProduct = this.getNowProductInfoForOrders(orders, mallOrder.getOrderIndex(), mallOrder.getOrderCode());
		if (nowProduct == null) {
			throw new MallException();
		}
		
		nowProduct.setMallOrderId(returnApply.getMallOrderId());
		
		// 클레임 접수된상태이면 상태를 변경하지 않음
		if (isSuccess == false) {
			if (this.isClaimCode(nowProduct.getOrdPrdStat())) {
				nowProduct.setOrdPrdStat("");
			}
		} else {
			
			// 반품보류.. Api가 없다..
			nowProduct.setClmReqSeq(returnApply.getClaimCode());
			nowProduct.setClmStat("104");
			nowProduct.setReturnHoldReson(returnApply.getRtHoldReson());
			nowProduct.setReturnHoldResonText(returnApply.getRtHoldResonText());
			estMapper.updateReturnHoldForApply(nowProduct);
			
		}
		
		
		nowProduct.setSystemMessage(systemMessage);
		estMapper.updateMallOrder(nowProduct);
	}
	
	@Override
	public void returnRefusal(String apiKey, MallOrderReturn returnApply) {
		MallOrder mallOrder = returnApply.getMallOrder();
		
		String reasonText = "";
		if (StringUtils.isNotEmpty(returnApply.getRtRefusalResonText())) {
			try {
				reasonText = URLEncoder.encode(returnApply.getRtRefusalResonText(), "UTF-8");
			} catch (UnsupportedEncodingException e) {
				log.error("ERROR: {}", e.getMessage(), e);
			}
		}
		
		String url = "http://api.11st.co.kr/rest/claimservice/returnreqreject";
		url += "/" + mallOrder.getOrderCode();
		url += "/" + mallOrder.getOrderIndex();
		url += "/" + returnApply.getClaimCode();
		url += "/" + returnApply.getRtRefusalReson();
		url += "/" + reasonText;
		
		ResultOrder resultOrder = (ResultOrder) estXmlConverter.convertXmlUrlToObject(url, apiKey, ResultOrder.class);
		if (resultOrder == null) {
			throw new MallException();
		}
			
		// 정상
		boolean isSuccess = false;
		String systemMessage = "";
		if ("0".equals(resultOrder.getResultCode())) {
			
			isSuccess = true;
			
		} else {
			
			// Api - 11번가 업데이트 실패
			systemMessage = "반품 거부 처리에 실패 하였습니다. - " + resultOrder.getResultText();
			
		}
		
		Orders orders = this.getOrderStatus(apiKey, returnApply.getOrderCode());
		Product nowProduct = this.getNowProductInfoForOrders(orders, mallOrder.getOrderIndex(), mallOrder.getOrderCode());
		if (nowProduct == null) {
			throw new MallException();
		}
		
		nowProduct.setMallOrderId(returnApply.getMallOrderId());
		
		// 클레임 접수된상태이면 상태를 변경하지 않음
		if (isSuccess == false) {
			if (this.isClaimCode(nowProduct.getOrdPrdStat())) {
				nowProduct.setOrdPrdStat("");
			}
		} else {
			
			// 반품보류.. Api가 없다..
			nowProduct.setClmReqSeq(returnApply.getClaimCode());
			nowProduct.setClmStat("107");
			nowProduct.setReturnRefusalReson(returnApply.getRtRefusalReson());
			nowProduct.setReturnRefusalResonText(returnApply.getRtRefusalResonText());
			estMapper.updateReturnRefusalForApply(nowProduct);
			
		}
		
		
		nowProduct.setSystemMessage(systemMessage);
		estMapper.updateMallOrder(nowProduct);
	}
	
	@Override
	public void exchangeRefusal(String apiKey, MallOrderExchange exchangeApply) {
		MallOrder mallOrder = exchangeApply.getMallOrder();
		
		String reasonText = "";
		if (StringUtils.isNotEmpty(exchangeApply.getExRefusalResonText())) {
			try {
				reasonText = URLEncoder.encode(exchangeApply.getExRefusalResonText(), "UTF-8");
			} catch (UnsupportedEncodingException e) {
				log.error("ERROR: {}", e.getMessage(), e);
			}
		}
		
		String url = "http://api.11st.co.kr/rest/claimservice/exchangereqreject";
		url += "/" + mallOrder.getOrderCode();
		url += "/" + mallOrder.getOrderIndex();
		url += "/" + exchangeApply.getClaimCode();
		url += "/" + exchangeApply.getExRefusalReson();
		url += "/" + reasonText;
		
		ResultOrder resultOrder = (ResultOrder) estXmlConverter.convertXmlUrlToObject(url, apiKey, ResultOrder.class);
		if (resultOrder == null) {
			throw new MallException();
		}
			
		// 정상
		boolean isSuccess = false;
		String systemMessage = "";
		if ("0".equals(resultOrder.getResultCode())) {
			
			isSuccess = true;
			
		} else {
			
			// Api - 11번가 업데이트 실패
			systemMessage = "교환 거부 처리에 실패 하였습니다. - " + resultOrder.getResultText();
			
		}
		
		Orders orders = this.getOrderStatus(apiKey, exchangeApply.getOrderCode());
		Product nowProduct = this.getNowProductInfoForOrders(orders, mallOrder.getOrderIndex(), mallOrder.getOrderCode());
		if (nowProduct == null) {
			throw new MallException();
		}
		
		nowProduct.setMallOrderId(exchangeApply.getMallOrderId());
		
		// 클레임 접수된상태이면 상태를 변경하지 않음
		if (isSuccess == false) {
			if (this.isClaimCode(nowProduct.getOrdPrdStat())) {
				nowProduct.setOrdPrdStat("");
			}
		} else {
			
			// 반품보류.. Api가 없다..
			nowProduct.setClmReqSeq(exchangeApply.getClaimCode());
			nowProduct.setClmStat("232");
			nowProduct.setExchangeRefusalReson(exchangeApply.getExRefusalReson());
			nowProduct.setExchangeRefusalResonText(exchangeApply.getExRefusalResonText());
			estMapper.updateExchangeRefusalForApply(nowProduct);
			
		}
		
		
		nowProduct.setSystemMessage(systemMessage);
		estMapper.updateMallOrder(nowProduct);
	}
}
