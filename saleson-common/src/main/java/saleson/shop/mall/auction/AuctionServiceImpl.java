package saleson.shop.mall.auction;

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
import saleson.shop.mall.auction.converter.AuctionXmlConverter;
import saleson.shop.mall.auction.domain.*;
import saleson.shop.mall.domain.*;
import saleson.shop.mall.support.MallException;
import saleson.shop.mall.support.MallOrderParam;
import saleson.shop.order.OrderService;

import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPMessage;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service("auctionService")
public class AuctionServiceImpl implements AuctionService {
	private static final Logger log = LoggerFactory.getLogger(AuctionServiceImpl.class);

	@Autowired
	private AuctionXmlConverter auctionXmlConverter;
	
	@Autowired
	private AuctionMapper auctionMapper;
	
	@Autowired
	private SequenceService sequenceService;
	
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private MallMapper mallMapper;

	@Autowired
	private ItemService itemService;

	/**
	 * 클레임 접수된 상태인가?
	 * @param status
	 * @return
	 */
	private boolean isClaimCode(String status) {

		String[] array = new String[]{"995"};
		if (ArrayUtils.contains(array, status)) {
			return true;
		}
		
		
		return false;
	}
	
	/**
	 * 옥션 옵션을 변환
	 * @param optionText
	 * @return
	 */
	private String[] getMarketOptionText(String optionText) {
		if (StringUtils.isEmpty(optionText)) {
			return new String[0];
		}
		
		String[] temp = StringUtils.delimitedListToStringArray(optionText, "/");
		
		List<String> list = new ArrayList<>();
		
		String[] titleArray = StringUtils.delimitedListToStringArray(temp[0].trim(), "_");
		String[] optionArray = StringUtils.delimitedListToStringArray(temp[1].trim(), "_");
		for(int i = 0; i < titleArray.length; i++) {
			
			if (titleArray.length <= i || optionArray.length <= i) {
				continue;
			}
			
			if (StringUtils.isEmpty(titleArray[i]) || StringUtils.isEmpty(optionArray[i])) {
				continue;
			}
			
			String text = titleArray[i].trim() + "_" + optionArray[i].trim();
			list.add(text);
			
		}
		
		return StringUtils.toStringArray(list);
	}
	

	/**
	 * 상점 옵션을 옥션 페턴에 맞게 만듬
	 * @param item
	 * @param itemOption
	 * @return
	 */
	private String[] getMallOptionText(Item item, ItemOption itemOption) {
		List<String> list = new ArrayList<>();
		
		// 작성형은 무시해도 될듯??
		if (!"T".equals(itemOption.getOptionType())) {
			if ("S".equals(itemOption.getOptionType())) {
				list.add(itemOption.getOptionName1() + "_" + itemOption.getOptionName2());
			} else {
				list.add(item.getItemOptionTitle1() + "_" + itemOption.getOptionName1());
				if ("S2".equals(itemOption.getOptionType()) || "S3".equals(itemOption.getOptionType())) {
					list.add(item.getItemOptionTitle2() + "_" + itemOption.getOptionName2());
					
					if ("S3".equals(itemOption.getOptionType())) {
						list.add(item.getItemOptionTitle3() + "_" + itemOption.getOptionName3());
					}
				}
			}
		}
		
		return StringUtils.toStringArray(list);
	}
	
	/**
	 * 최초 주문내역 조회시 배송비 가져오기
	 * @param apiKey
	 * @param groupOrderSeqno
	 * @return
	 */
	private int getPayShipping(String apiKey, PaidOrder paidOrder) {
		GetShippingDetailInfoListResult result = null;
		try {
			String function = "GetShippingDetailInfoList";
			
			SOAPMessage message = auctionXmlConverter.getDefaultSoapMessage(apiKey, function);
	        SOAPElement bodyReqElement = auctionXmlConverter.getBodyReqElement(message.getSOAPBody(), function);
	        
	        bodyReqElement.setAttribute("SearchType", "GroupOrderSeqno");
	        bodyReqElement.setAttribute("SearchValue", Integer.toString(paidOrder.getGroupOrderSeqno()));
	
	        message.saveChanges();
	
	        result = (GetShippingDetailInfoListResult) auctionXmlConverter.convertXmlUrlToObject(message, function, GetShippingDetailInfoListResult.class);
		} catch(Exception e) {
			log.error("ERROR: {}", e.getMessage(), e);
		}
		
		if (result == null) {
			return 0;
		}
		
		int amount = 0;
		for(ShippingDetailInfo shippingDetailInfo : result.getShippingDetailInfo()) {
			//paidOrder.setShippingSeqno(shippingDetailInfo.getShippingSeqno());
			amount += shippingDetailInfo.getAmount().intValue();
		}
		
		return amount;
	}
	
	/**
	 * 판매자 계좌 정보 조회
	 * @param apiKey
	 * @return
	 */
	private MyAccount getMyAccount(String apiKey) {
		
		GetMyAccountResult result = null;
		
		try {
			String function = "GetMyAccount";
			String elementName = function + "Result";
			
			SOAPMessage message = auctionXmlConverter.getDefaultSoapMessage(apiKey, function);
	        SOAPElement bodyReqElement = auctionXmlConverter.getBodyReqElement(message.getSOAPBody(), function);
	
	        message.saveChanges();
	        
	        result = (GetMyAccountResult) auctionXmlConverter.convertXmlUrlToObject(message, function, elementName, GetMyAccountResult.class);
		} catch(Exception e) {
			log.error("ERROR: {}", e.getMessage(), e);
		}
		
		if (result == null) {
			return null;
		}
		
		return result.getMyAccount();
	}
	
	/**
	 * 주문 상세 조회
	 * @param apiKey
	 * @param orderNo
	 * @return
	 */
	private GetShippingDetailResult getOrderDetail(String apiKey, int orderNo) {
		GetShippingDetailResult result = null;
		
		try {
			String function = "GetShippingDetail";
			String elementName = function + "Result";
			
			SOAPMessage message = auctionXmlConverter.getDefaultSoapMessage(apiKey, function);
	        SOAPElement bodyReqElement = auctionXmlConverter.getBodyReqElement(message.getSOAPBody(), function);
	        
	        bodyReqElement.setAttribute("OrderNo", Integer.toString(orderNo));
	
	        message.saveChanges();
	        
	        result = (GetShippingDetailResult) auctionXmlConverter.convertXmlUrlToObject(message, function, elementName, GetShippingDetailResult.class);
		} catch(Exception e) {
			log.error("Error ", e);
		}
		
		if (result == null) {
			return null;
		}
		
		return result;
	}
	
	/**
	 * 현재 주문상태 조회
	 * @param apiKey
	 * @param mallOrder
	 * @return
	 */
	private OrderInfo getOrderInfo(String apiKey, MallOrder mallOrder) {
		
		GetOrderInfoListResult result = null;
		try {
			String function = "GetOrderInfoList";
			
			SOAPMessage message = auctionXmlConverter.getDefaultSoapMessage(apiKey, function);
	        SOAPElement bodyReqElement = auctionXmlConverter.getBodyReqElement(message.getSOAPBody(), function, "request");
	        
	        SOAPElement element = bodyReqElement.addChildElement(auctionXmlConverter.createElementName("SearchType", "", "http://schema.auction.co.kr/Arche.APISvc.xsd"));
	        element.addTextNode("OrderNo");

	        SOAPElement element1 = bodyReqElement.addChildElement(auctionXmlConverter.createElementName("SearchValue", "", "http://schema.auction.co.kr/Arche.APISvc.xsd"));
	        element1.addTextNode(Integer.toString(mallOrder.getOrderIndex()));
	        
	        SOAPElement element2 = bodyReqElement.addChildElement(auctionXmlConverter.createElementName("PageNo", "", "http://schema.auction.co.kr/Arche.APISvc.xsd"));
	        element2.addTextNode("1");
	
	        message.saveChanges();
	        
	        result = (GetOrderInfoListResult) auctionXmlConverter.convertXmlUrlToObject(message, function, GetOrderInfoListResult.class);
		} catch(Exception e) {
			
			log.error("ERROR: {}", e.getMessage(), e);
		}
		
		if (result == null) {
			return null;
		}
		
		for(OrderInfo orderInfo : result.getList()) {
			if (mallOrder.getOrderIndex() == orderInfo.getOrderNo() && mallOrder.getProductCode().equals(orderInfo.getItemNo())) {
				return orderInfo;
			}
		}
		
		return null;
	}
	
	/**
	 * 해당 주문번호가 취소 요청 들어왔는가?
	 * @param apiKey
	 * @param mallOrder
	 * @return
	 */
	private CancelApproval getCancelApprovalByOrderCode(String apiKey, MallOrder mallOrder) {
		
		GetCancelApprovalListResult result = null;
		try {
			String function = "GetCancelApprovalList";
			
			SOAPMessage message = auctionXmlConverter.getDefaultSoapMessage(apiKey, function);
	        SOAPElement bodyReqElement = auctionXmlConverter.getBodyReqElement(message.getSOAPBody(), function);
	        
	        bodyReqElement.setAttribute("SearchType", "OrderNo");
	        bodyReqElement.setAttribute("SearchString", Integer.toString(mallOrder.getOrderIndex()));
	
	        message.saveChanges();
	        
	        result = (GetCancelApprovalListResult) auctionXmlConverter.convertXmlUrlToObject(message, function, GetCancelApprovalListResult.class);
		} catch(Exception e) {
			log.error("ERROR: {}", e.getMessage(), e);
		}
		
		if (result == null) {
			return null;
		}
		
		for(CancelApproval cancelApproval : result.getList()) {
			
			if (cancelApproval.getItemNo().equals(mallOrder.getProductCode())) {
				return cancelApproval;
			}
			
		}
		
		return null;
	}
	
	@Override
	public MallConfig newOrderCollect(String apiKey, String searchStartDate, String searchEndDate, MallConfig mallConfig) {
		
		mallConfig.setLastSearchStartDate("");
		mallConfig.setLastSearchEndDate("");
		
		String collectStatusCode = "2";
		
		searchStartDate = searchStartDate.substring(0, 8);
		searchEndDate = searchEndDate.substring(0, 8);
		
		try {
			
			String function = "GetPaidOrderList";
			
			SOAPMessage message = auctionXmlConverter.getDefaultSoapMessage(apiKey, function);
	        SOAPElement bodyReqElement = auctionXmlConverter.getBodyReqElement(message.getSOAPBody(), function);
	        
	        bodyReqElement.setAttribute("DurationType", "ReceiptDate");
	        bodyReqElement.setAttribute("SearchType", "Nothing");
	        
	        SOAPElement element = bodyReqElement.addChildElement(auctionXmlConverter.createElementName("SearchDuration", "", "http://schema.auction.co.kr/Arche.APISvc.xsd"));
	        element.setAttribute("StartDate", DateUtils.formatDate(searchStartDate, "-"));
	        element.setAttribute("EndDate", DateUtils.formatDate(searchEndDate, "-"));
	        message.saveChanges();

	        GetPaidOrderListResult result = (GetPaidOrderListResult) auctionXmlConverter.convertXmlUrlToObject(message, function, GetPaidOrderListResult.class);
	        collectStatusCode = "1";
	        
	       
	        if (result != null) {
	        	
	        	// 정상 조회시에만 일자를 기록
				mallConfig.setLastSearchStartDate(searchStartDate);
				mallConfig.setLastSearchEndDate(searchEndDate);
	        	
	        	if (result.getPaidOrder().size() > 0) {
	        		
	        		String[] groupOrderSeqno = new String[result.getPaidOrder().size()];
	        		int index = 0;
	        		for(PaidOrder paidOrder : result.getPaidOrder()) {
	        			
	        			MallOrderParam mallOrderParam = new MallOrderParam();
		    			mallOrderParam.setMallConfigId(mallConfig.getMallConfigId());
	        			
		    			// 이미 등록?
		    			paidOrder.setMallConfigId(mallConfig.getMallConfigId());
		    			paidOrder.setMallType(mallConfig.getMallType());
						if (auctionMapper.getMallOrderCount(paidOrder) > 0) {
							continue;
						}
						
						OrderBase orderBase = paidOrder.getOrderBase();
						GetShippingDetailResult detail = this.getOrderDetail(apiKey, orderBase.getOrderNo());
						if (detail == null) {
							continue;
						}
						
						// 배송비
						if (ArrayUtils.contains(groupOrderSeqno, paidOrder.getGroupOrderSeqno())) {
							detail.getTransFee().setPreTransAmount(new BigDecimal("0"));
						}
						
						groupOrderSeqno[index] = Integer.toString(paidOrder.getGroupOrderSeqno());
						
						// 주문 상세 정보를 셋팅
						paidOrder.setDetail(detail);
						
						int count = 0;
						for(PaidOrder order : result.getPaidOrder()) {
							if (order.getGroupOrderSeqno() == paidOrder.getGroupOrderSeqno()) {
								count++;
							}
						}
						
						String shippingGroupFlag = count > 1 ? "Y" : "N";						
						paidOrder.setShippingGroupFlag(shippingGroupFlag);
						
						mallOrderParam.setProductCode(orderBase.getItemId());
						Item item = mallMapper.getItemByMallConfig(mallOrderParam);
						if (item != null) {
							boolean isSet = true;
							
							// 1. 몰에 옵션은 사용안하는데 11번가에서는 옵션이 넘어옴??
							if ("N".equals(item.getItemOptionFlag()) && StringUtils.isNotEmpty(paidOrder.getRequestOption())) {
								isSet = false;
							} else {
								List<ItemOption> options = itemService.getItemOptionList(item, false);
								
								if (!options.isEmpty()) {
									String[] marketOptionArray = this.getMarketOptionText(paidOrder.getRequestOption());
									
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
											paidOrder.setMatchedOptions(matchedOptions);
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
												paidOrder.setMatchedOptions(matchedOptions);
												break;
											}
											
										}
									}
								}
							}
							
							if (isSet) {
								paidOrder.setItemId(item.getItemId());
							}
							
						}
						
						
						paidOrder.setOrdPrdStat("170");
						paidOrder.setMallOrderId(sequenceService.getId("OP_MALL_ORDER"));
						auctionMapper.insertMallOrder(paidOrder);
						
	        		}
	        	}
	        }
	        
		} catch (Exception e) {
			log.error("ERROR: {}", e.getMessage(), e);
			collectStatusCode = "2";
		}
		
		mallConfig.setStatusCode(collectStatusCode);
		return mallConfig;
	}
	
	@Override
	public MallConfig claimCollect(String apiKey, String searchStartDate, String searchEndDate, MallConfig mallConfig) {
	
		HashMap<String, String> orderMap = new HashMap<>();
		
		String claimCollectStatusCode = "1";
		
		searchStartDate = searchStartDate.substring(0, 8);
		searchEndDate = searchEndDate.substring(0, 8);
		
		// 정상 조회시에만 일자를 기록
		mallConfig.setLastClaimSearchStartDate(searchStartDate);
		mallConfig.setLastClaimSearchEndDate(searchEndDate);
		
		// 취소요청
		GetCancelApprovalListResult cancelAppResult = null;
		try {
			String function = "GetCancelApprovalList";
			
			SOAPMessage message = auctionXmlConverter.getDefaultSoapMessage(apiKey, function);
	        SOAPElement bodyReqElement = auctionXmlConverter.getBodyReqElement(message.getSOAPBody(), function);
	        
	        bodyReqElement.setAttribute("SearchDateType", "CancelRequestDate");
	        bodyReqElement.setAttribute("FromDate", DateUtils.formatDate(searchStartDate, "-"));
	        bodyReqElement.setAttribute("ToDate", DateUtils.formatDate(searchEndDate, "-"));
	        
	        message.saveChanges();
	        
	        cancelAppResult = (GetCancelApprovalListResult) auctionXmlConverter.convertXmlUrlToObject(message, function, GetCancelApprovalListResult.class);
		} catch(Exception e) {
			
			log.error("ERROR: {}", e.getMessage(), e);
			claimCollectStatusCode = "2";
			
			mallConfig.setLastClaimSearchStartDate("");
			mallConfig.setLastClaimSearchEndDate("");
		}
		
		
		if (cancelAppResult != null) {
			for(CancelApproval cancelApproval : cancelAppResult.getList()) {
				try {
					cancelApproval.setMallConfigId(mallConfig.getMallConfigId());
					cancelApproval.setMallType(mallConfig.getMallType());
					
					MallOrderCancel cancelApply = auctionMapper.getMallOrderCancel(cancelApproval);
					if (cancelApply == null) {
						continue;
					}
					
					// 주문번호 셋팅
					cancelApproval.setMallOrderId(cancelApply.getMallOrderId());
					cancelApproval.setClaimStatus("Requested");
					
					// 클레임번호가 같은경우 새로 등록 안되야함
					if (StringUtils.isEmpty(cancelApply.getClaimCode())) {
						auctionMapper.insertMallOrderCancel(cancelApproval);
					} else {
						auctionMapper.updateMallOrderCancel(cancelApproval);
					}
					
					// systemMessage 초기화
					MallOrder mallOrder = new MallOrder();
					mallOrder.setMallOrderId(cancelApply.getMallOrderId());
					auctionMapper.updateMallOrder(mallOrder);
					
				} catch(Exception e) {
					
					log.error("ERROR: {}", e.getMessage(), e);
					claimCollectStatusCode = "2";
				}
			}
		}
		
		// 취소완료
		GetOrderCanceledListResult cancelEndResult = null;
		try {
			String function = "GetOrderCanceledList";
			
			SOAPMessage message = auctionXmlConverter.getDefaultSoapMessage(apiKey, function);
	        SOAPElement bodyReqElement = auctionXmlConverter.getBodyReqElement(message.getSOAPBody(), function);
	        
	        bodyReqElement.setAttribute("SearchType", "Nothing");
	        
	        SOAPElement element = bodyReqElement.addChildElement(auctionXmlConverter.createElementName("SearchDuration", "", "http://schema.auction.co.kr/Arche.APISvc.xsd"));
	        element.setAttribute("StartDate", DateUtils.formatDate(searchStartDate, "-"));
	        element.setAttribute("EndDate", DateUtils.formatDate(searchEndDate, "-"));
	        
	        message.saveChanges();
	        
	        cancelEndResult = (GetOrderCanceledListResult) auctionXmlConverter.convertXmlUrlToObject(message, function, GetOrderCanceledListResult.class);
		} catch(Exception e) {
			
			log.error("ERROR: {}", e.getMessage(), e);
			claimCollectStatusCode = "2";
			
			mallConfig.setLastClaimSearchStartDate("");
			mallConfig.setLastClaimSearchEndDate("");
		}
		
		
		if (cancelEndResult != null) {
			for(OrderCanceled orderCanceled : cancelEndResult.getList()) {
				try {
					
					int mallOrderId = 0;

					OrderBase orderBase = orderCanceled.getOrderBase();
					
					// 반품으로 종료된것도 같이 조회됨
					if ("반품승인".equals(orderCanceled.getCancelType())) {
						
						ReturnList returnList = new ReturnList();
						
						Order order = new Order();
						order.setOrderNo(orderBase.getOrderNo());
						returnList.setMallConfigId(mallConfig.getMallConfigId());
						returnList.setMallType(mallConfig.getMallType());
						returnList.setOrder(order);

						MallOrderReturn returnApply = auctionMapper.getMallOrderReturn(returnList);
						if (returnApply == null) {
							continue;
						}
						
						mallOrderId = returnApply.getMallOrderId();
						
					} else {
						CancelApproval cancelApproval = new CancelApproval();
						
						cancelApproval.setRequestDate(orderCanceled.getCancelDate());
						cancelApproval.setAwardQty(orderBase.getAwardQty());
						cancelApproval.setAwardAmount(orderBase.getAwardAmount());
						cancelApproval.setMallConfigId(mallConfig.getMallConfigId());
						cancelApproval.setMallType(mallConfig.getMallType());
						cancelApproval.setOrderNo(orderBase.getOrderNo());
						cancelApproval.setClaimStatus("Finish");
						cancelApproval.setCancReason(orderCanceled.getCancelType());
						
						MallOrderCancel cancelApply = auctionMapper.getMallOrderCancel(cancelApproval);
						if (cancelApply == null) {
							continue;
						}
						
						// 주문번호 셋팅
						cancelApproval.setMallOrderId(cancelApply.getMallOrderId());
						
						// 클레임번호가 같은경우 새로 등록 안되야함
						if (StringUtils.isEmpty(cancelApply.getClaimCode())) {
							auctionMapper.insertMallOrderCancel(cancelApproval);
						} else {
							auctionMapper.updateMallOrderCancel(cancelApproval);
						}
						
						mallOrderId = cancelApply.getMallOrderId();
					}
					
					// systemMessage 초기화
					MallOrder mallOrder = new MallOrder();
					mallOrder.setMallOrderId(mallOrderId);
					mallOrder.setOrderItemStatus("995");
					auctionMapper.updateMallOrder(mallOrder);
					
				} catch(Exception e) {
					
					log.error("ERROR: {}", e.getMessage(), e);
					claimCollectStatusCode = "2";
				}
			}
		}
		
		// 취소 철회
		MallOrderParam mallOrderParam = new MallOrderParam();
		mallOrderParam.setSearchStartDate(searchStartDate);
		mallOrderParam.setSearchEndDate(searchEndDate);
		List<MallOrderCancel> mallOrderCancelList = mallMapper.getCancelRequestListByParam(mallOrderParam);
		if (mallOrderCancelList != null) {
			
			for(MallOrderCancel cancelApply : mallOrderCancelList) {
				GetCancelApprovalListResult cancelWithdrawResult = null;

				try {
					String function = "GetCancelApprovalList";
					
					SOAPMessage message = auctionXmlConverter.getDefaultSoapMessage(apiKey, function);
			        SOAPElement bodyReqElement = auctionXmlConverter.getBodyReqElement(message.getSOAPBody(), function);
			        
			        bodyReqElement.setAttribute("SearchType", "OrderNo");
			        bodyReqElement.setAttribute("SearchString", cancelApply.getClaimCode());
			        
			        message.saveChanges();
			        
			        cancelWithdrawResult = (GetCancelApprovalListResult) auctionXmlConverter.convertXmlUrlToObject(message, function, GetCancelApprovalListResult.class);

					if (cancelWithdrawResult != null
						&& cancelWithdrawResult.getList() != null
						&& cancelWithdrawResult.getList().isEmpty()) {
			        	
			        	// 취소 완료 상태인지 조회
			        	function = "GetOrderCanceledList";
						
						message = auctionXmlConverter.getDefaultSoapMessage(apiKey, function);
				        bodyReqElement = auctionXmlConverter.getBodyReqElement(message.getSOAPBody(), function);
				        
				        bodyReqElement.setAttribute("SearchType", "OrderNo");
				        bodyReqElement.setAttribute("SearchString", cancelApply.getClaimCode());
				        
				        message.saveChanges();
				        
				        cancelEndResult = (GetOrderCanceledListResult) auctionXmlConverter.convertXmlUrlToObject(message, function, GetOrderCanceledListResult.class);

						if (cancelEndResult.getList() == null || cancelEndResult.getList().isEmpty()) {
				        	CancelApproval cancelApproval = new CancelApproval();
							cancelApproval.setAwardQty(cancelApply.getClaimQuantity());
							cancelApproval.setMallConfigId(mallConfig.getMallConfigId());
							cancelApproval.setMallType(mallConfig.getMallType());
							cancelApproval.setOrderNo(Integer.parseInt(cancelApply.getClaimCode()));
							cancelApproval.setClaimStatus("Withdraw");
							cancelApproval.setCancReason(cancelApply.getCancelReason());
							cancelApproval.setMallOrderId(cancelApply.getMallOrderId());
				        	auctionMapper.updateMallOrderCancel(cancelApproval);
				        	
				        	// systemMessage 초기화
							MallOrder mallOrder = new MallOrder();
							mallOrder.setMallOrderId(cancelApply.getMallOrderId());
							auctionMapper.updateMallOrder(mallOrder);
				        }
			        }
				} catch(Exception e) {
					log.error("ERROR: {}", e.getMessage(), e);
				}
			}
		}
		
		// 반품 신청
		GetReturnListResult returnRequestResult = null;
		try {
			String function = "GetReturnList";
			
			SOAPMessage message = auctionXmlConverter.getDefaultSoapMessage(apiKey, function);
	        SOAPElement bodyReqElement = auctionXmlConverter.getBodyReqElement(message.getSOAPBody(), function);
	        
	        bodyReqElement.setAttribute("SearchType", "None");
	        bodyReqElement.setAttribute("SearchDateType", "None");
	        bodyReqElement.setAttribute("PageSize", "500");
	        
	        SOAPElement element = bodyReqElement.addChildElement(auctionXmlConverter.createElementName("SearchDuration", "", "http://schema.auction.co.kr/Arche.APISvc.xsd"));
	        element.setAttribute("StartDate", DateUtils.formatDate(searchStartDate, "-"));
	        element.setAttribute("EndDate", DateUtils.formatDate(searchEndDate, "-"));
	        
	        SOAPElement element1 = bodyReqElement.addChildElement(auctionXmlConverter.createElementName("SearchFlags", "", "http://schema.auction.co.kr/Arche.APISvc.xsd"));
	        element1.addTextNode("Requested");
	        
	        message.saveChanges();
	        
	        returnRequestResult = (GetReturnListResult) auctionXmlConverter.convertXmlUrlToObject(message, function, GetReturnListResult.class);
		} catch(Exception e) {
			
			log.error("ERROR: {}", e.getMessage(), e);
			claimCollectStatusCode = "2";
			
			mallConfig.setLastClaimSearchStartDate("");
			mallConfig.setLastClaimSearchEndDate("");
		}
		
		
		if (returnRequestResult != null) {
			for(ReturnList returnList : returnRequestResult.getList()) {
				try {
					
					returnList.setMallConfigId(mallConfig.getMallConfigId());
					returnList.setMallType(mallConfig.getMallType());
					
					MallOrderReturn returnApply = auctionMapper.getMallOrderReturn(returnList);
					if (returnApply == null) {
						continue;
					}
					
					// 주문번호 셋팅
					returnList.setMallOrderId(returnApply.getMallOrderId());
					
					// 클레임번호가 같은경우 새로 등록 안되야함
					if (StringUtils.isEmpty(returnApply.getClaimCode())) {
						auctionMapper.insertMallOrderReturn(returnList);
					} else {
						auctionMapper.updateMallOrderReturn(returnList);
					}
					
					// systemMessage 초기화
					MallOrder mallOrder = new MallOrder();
					mallOrder.setMallOrderId(returnList.getMallOrderId());
					//mallOrder.setOrderItemStatus("995");
					auctionMapper.updateMallOrder(mallOrder);
					
				} catch(Exception e) {
					
					log.error("ERROR: {}", e.getMessage(), e);
					claimCollectStatusCode = "2";
				}
			}
		}
		
		// 반품 보류
		GetReturnListResult returnHoldResult = null;
		try {
			String function = "GetReturnList";
			
			SOAPMessage message = auctionXmlConverter.getDefaultSoapMessage(apiKey, function);
	        SOAPElement bodyReqElement = auctionXmlConverter.getBodyReqElement(message.getSOAPBody(), function);
	        
	        bodyReqElement.setAttribute("SearchType", "None");
	        bodyReqElement.setAttribute("SearchDateType", "None");
	        bodyReqElement.setAttribute("PageSize", "500");
	        
	        SOAPElement element = bodyReqElement.addChildElement(auctionXmlConverter.createElementName("SearchDuration", "", "http://schema.auction.co.kr/Arche.APISvc.xsd"));
	        element.setAttribute("StartDate", DateUtils.formatDate(searchStartDate, "-"));
	        element.setAttribute("EndDate", DateUtils.formatDate(searchEndDate, "-"));
	        
	        SOAPElement element1 = bodyReqElement.addChildElement(auctionXmlConverter.createElementName("SearchFlags", "", "http://schema.auction.co.kr/Arche.APISvc.xsd"));
	        element1.addTextNode("Hold");
	        
	        message.saveChanges();
	        
	        returnHoldResult = (GetReturnListResult) auctionXmlConverter.convertXmlUrlToObject(message, function, GetReturnListResult.class);
		} catch(Exception e) {
			
			log.error("ERROR: {}", e.getMessage(), e);
			claimCollectStatusCode = "2";
			
			mallConfig.setLastClaimSearchStartDate("");
			mallConfig.setLastClaimSearchEndDate("");
		}
		
		
		if (returnHoldResult != null) {
			for(ReturnList returnList : returnHoldResult.getList()) {
				try {
					
					MallOrderReturn returnApply = auctionMapper.getMallOrderReturn(returnList);
					if (returnApply == null) {
						continue;
					}
					
					// 주문번호 셋팅
					returnList.setMallOrderId(returnApply.getMallOrderId());
					
					// 클레임번호가 같은경우 새로 등록 안되야함
					if (StringUtils.isEmpty(returnApply.getClaimCode())) {
						auctionMapper.insertMallOrderReturn(returnList);
					} else {
						auctionMapper.updateMallOrderReturn(returnList);
					}
					
					// systemMessage 초기화
					MallOrder mallOrder = new MallOrder();
					mallOrder.setMallOrderId(returnList.getMallOrderId());
					//mallOrder.setOrderItemStatus("995");
					auctionMapper.updateMallOrder(mallOrder);
					
				} catch(Exception e) {
					
					log.error("ERROR: {}", e.getMessage(), e);
					claimCollectStatusCode = "2";
				}
			}
		}
		
		// 교환 신청
		GetExchangeRequestListBySearchConditionResult exchangeRequestResult = null;
		try {
			String function = "GetExchangeRequestListBySearchCondition";
			
			SOAPMessage message = auctionXmlConverter.getDefaultSoapMessage(apiKey, function);
	        SOAPElement bodyReqElement = auctionXmlConverter.getBodyReqElement(message.getSOAPBody(), function);
	        
	        bodyReqElement.setAttribute("SearchType", "None");
	        bodyReqElement.setAttribute("SearchDateType", "None");
	        bodyReqElement.setAttribute("PageIndex", "500");
	        
	        SOAPElement element = bodyReqElement.addChildElement(auctionXmlConverter.createElementName("SearchDuration", "", "http://schema.auction.co.kr/Arche.APISvc.xsd"));
	        element.setAttribute("StartDate", DateUtils.formatDate(searchStartDate, "-"));
	        element.setAttribute("EndDate", DateUtils.formatDate(searchEndDate, "-"));
	        
	        SOAPElement element1 = bodyReqElement.addChildElement(auctionXmlConverter.createElementName("SearchFlags", "", "http://schema.auction.co.kr/Arche.APISvc.xsd"));
	        element1.addTextNode("Requested");
	        
	        message.saveChanges();
	        
	        exchangeRequestResult = (GetExchangeRequestListBySearchConditionResult) auctionXmlConverter.convertXmlUrlToObject(message, function, GetExchangeRequestListBySearchConditionResult.class);
		} catch(Exception e) {
			
			log.error("ERROR: {}", e.getMessage(), e);
			claimCollectStatusCode = "2";
			
			mallConfig.setLastClaimSearchStartDate("");
			mallConfig.setLastClaimSearchEndDate("");
		}
		
		
		if (exchangeRequestResult != null) {
			for(ExchangeBase exchangeBase : exchangeRequestResult.getList()) {
				try {
					
					MallOrderExchange exchangeApply = auctionMapper.getMallOrderExchange(exchangeBase);
					if (exchangeApply == null) {
						continue;
					}
					
					// 주문번호 셋팅
					exchangeBase.setMallOrderId(exchangeApply.getMallOrderId());
					
					// 클레임번호가 같은경우 새로 등록 안되야함
					if (StringUtils.isEmpty(exchangeApply.getClaimCode())) {
						auctionMapper.insertMallOrderExchange(exchangeBase);
					} else {
						auctionMapper.updateMallOrderExchange(exchangeBase);
					}
					
					// systemMessage 초기화
					MallOrder mallOrder = new MallOrder();
					mallOrder.setMallOrderId(exchangeBase.getMallOrderId());
					//mallOrder.setOrderItemStatus("995");
					auctionMapper.updateMallOrder(mallOrder);
					
				} catch(Exception e) {
					
					log.error("ERROR: {}", e.getMessage(), e);
					claimCollectStatusCode = "2";
				}
			}
		}
		
		// 교환 보류
		GetExchangeRequestListBySearchConditionResult exchangeHoldResult = null;
		try {
			String function = "GetExchangeRequestListBySearchCondition";
			
			SOAPMessage message = auctionXmlConverter.getDefaultSoapMessage(apiKey, function);
	        SOAPElement bodyReqElement = auctionXmlConverter.getBodyReqElement(message.getSOAPBody(), function);
	        
	        bodyReqElement.setAttribute("SearchType", "None");
	        bodyReqElement.setAttribute("SearchDateType", "None");
	        bodyReqElement.setAttribute("PageIndex", "500");
	        
	        SOAPElement element = bodyReqElement.addChildElement(auctionXmlConverter.createElementName("SearchDuration", "", "http://schema.auction.co.kr/Arche.APISvc.xsd"));
	        element.setAttribute("StartDate", DateUtils.formatDate(searchStartDate, "-"));
	        element.setAttribute("EndDate", DateUtils.formatDate(searchEndDate, "-"));
	        
	        SOAPElement element1 = bodyReqElement.addChildElement(auctionXmlConverter.createElementName("SearchFlags", "", "http://schema.auction.co.kr/Arche.APISvc.xsd"));
	        element1.addTextNode("Hold");
	        
	        message.saveChanges();
	        
	        exchangeHoldResult = (GetExchangeRequestListBySearchConditionResult) auctionXmlConverter.convertXmlUrlToObject(message, function, GetExchangeRequestListBySearchConditionResult.class);
		} catch(Exception e) {
			
			log.error("ERROR: {}", e.getMessage(), e);
			claimCollectStatusCode = "2";
			
			mallConfig.setLastClaimSearchStartDate("");
			mallConfig.setLastClaimSearchEndDate("");
		}
		
		
		if (exchangeHoldResult != null) {
			for(ExchangeBase exchangeBase : exchangeHoldResult.getList()) {
				try {
					
					MallOrderExchange exchangeApply = auctionMapper.getMallOrderExchange(exchangeBase);
					if (exchangeApply == null) {
						continue;
					}
					
					// 주문번호 셋팅
					exchangeBase.setMallOrderId(exchangeApply.getMallOrderId());
					
					// 클레임번호가 같은경우 새로 등록 안되야함
					if (StringUtils.isEmpty(exchangeApply.getClaimCode())) {
						auctionMapper.insertMallOrderExchange(exchangeBase);
					} else {
						auctionMapper.updateMallOrderExchange(exchangeBase);
					}
					
					// systemMessage 초기화
					MallOrder mallOrder = new MallOrder();
					mallOrder.setMallOrderId(exchangeBase.getMallOrderId());
					//mallOrder.setOrderItemStatus("995");
					auctionMapper.updateMallOrder(mallOrder);
					
				} catch(Exception e) {
					
					log.error("ERROR: {}", e.getMessage(), e);
					claimCollectStatusCode = "2";
				}
			}
		}
		
		mallConfig.setClaimStatusCode(claimCollectStatusCode);
		return mallConfig;
	}
	
	@Override
	public void packaging(String apiKey, MallOrder mallOrder, HashMap<String, Integer> stockMap) {
		
		boolean isSuccess = false;
		
		try {
			
			String function = "ConfirmReceivingOrder";
			
			SOAPMessage message = auctionXmlConverter.getDefaultSoapMessage(apiKey, function);
	        SOAPElement bodyReqElement = auctionXmlConverter.getBodyReqElement(message.getSOAPBody(), function);
	        
	        bodyReqElement.setAttribute("OrderNo", Integer.toString(mallOrder.getOrderIndex()));
	        message.saveChanges();
	        
	        auctionXmlConverter.convertXmlUrlToObject(message, function, ConfirmReceivingOrderResponse.class);
	        isSuccess = true;
	        
		} catch (Exception e) {
			log.error("XmlConverter : {}", e.getMessage());
			
		}
        
        String systemMessage = "";
        if (isSuccess == true) {
        	isSuccess = true;
        } else {
        	// Api - 11번가 업데이트 실패
			systemMessage = "발주확인 처리에 실패 하였습니다.";
        }
        
        OrderInfo nowProduct = this.getOrderInfo(apiKey, mallOrder);
		if (nowProduct == null) {
			throw new MallException();
		}
        
        if (isSuccess == true) {
        	
        	mallOrder.setOrderItemStatus(nowProduct.getOrderStatus());
        	if (stockMap != null) {
				
				// 재고량 차감
				orderService.updateStockDeduction(stockMap);
			}
        } else {
        	
        	// 클래임 접수 상태이면 주문 상태를 변경하지 않음
        	if (this.isClaimCode(nowProduct.getOrderStatus())) {
        		mallOrder.setOrderItemStatus("");
			} else {
				mallOrder.setOrderItemStatus(nowProduct.getOrderStatus());
			}
        	
        }
		
        mallOrder.setMallOrderId(mallOrder.getMallOrderId());
        mallOrder.setSystemMessage(systemMessage);
        auctionMapper.updateMallOrder(mallOrder);
	}
	
	@Override
	public void shipping(String apiKey, MallOrder mallOrder) {
		
		CancelApproval cancelApproval = this.getCancelApprovalByOrderCode(apiKey, mallOrder);
		String systemMessage = "";
		if (cancelApproval != null) {
			systemMessage = "배송처리에 실패 하였습니다. - 주문취소 요청이 확인되었습니다.";
		} else {
			boolean isSuccess = false;
			
			String errorReason = "";
			try {
				
				String function = "DoShippingGeneral";
				String elementName = function + "Result";
				
				SOAPMessage message = auctionXmlConverter.getDefaultSoapMessage(apiKey, function);
		        SOAPElement bodyReqElement = auctionXmlConverter.getBodyReqElement(message.getSOAPBody(), function);
		        
		        bodyReqElement.setAttribute("OrderNo", Integer.toString(mallOrder.getOrderIndex()));
		        
		        // 정산 받을 수단
		        
		        SOAPElement element1 = bodyReqElement.addChildElement(auctionXmlConverter.createElementName("RemittanceMethod", "", "http://schema.auction.co.kr/Arche.APISvc.xsd"));
		        String remittanceMethodType = "Emoney";
		        
		        MyAccount myAccount = this.getMyAccount(apiKey);
		        if (myAccount != null) {
		        	if (StringUtils.isNotEmpty(myAccount.getAccountNumber())) {
			        	remittanceMethodType = "Cash";
			        	element1.setAttribute("RemittanceAccountName", myAccount.getAccountName());
			        	element1.setAttribute("RemittanceAccountNumber", myAccount.getAccountNumber());
			        	element1.setAttribute("RemittanceBankCode", myAccount.getBankCode());
		        	}
		        }
		        element1.setAttribute("RemittanceMethodType", remittanceMethodType);
		        
		        SOAPElement element = bodyReqElement.addChildElement(auctionXmlConverter.createElementName("ShippingMethod", "", "http://schema.auction.co.kr/Arche.APISvc.xsd"));
		        element.setAttribute("SendDate", DateUtils.getToday("yyyy-MM-dd"));
		        element.setAttribute("InvoiceNo", mallOrder.getDeliveryNumber());
		        
		        /**
		         * Door2Door : 택배
		         * Etc : 기타발송
		         * Parcel : 소포/등기
		         */
		        element.setAttribute("ShippingMethodClassficationType", "Door2Door");
		        element.setAttribute("DeliveryAgency", mallOrder.getDeliveryCompanyCode());
		        element.setAttribute("DeliveryAgencyName", ""); // 기타 택배사명
		        element.setAttribute("ShippingEtcAgencyName", ""); // 기타 발송 방법
		        
		        /**
		         * Direct : 직접 전달
		         * Etc : 기타
		         * Nothing : 선택 없음
		         * Post : 일반우편
		         * QuickService : 퀵서비스
		         * Truck : 화물 배달
		         */
		        element.setAttribute("ShippingEtcMethod", "Nothing");
		        element.setAttribute("MessageForBuyer", "");
		        message.saveChanges();
        
		        DoShippingGeneralResult result = (DoShippingGeneralResult) auctionXmlConverter.convertXmlUrlToObject(message, function, elementName, DoShippingGeneralResult.class);

				if (result != null){
					if ("Success".equals(result.getSuccess())) {
						isSuccess = true;
					} else {
						errorReason = result.getFailReason();
					}
				}
		        
			} catch (Exception e) {
				log.error("AuctionServiceImpl : {}", e.getMessage());
				
			}

	        if (isSuccess == true) {
	        	isSuccess = true;
	        } else {
	        	
	        	// Api - 11번가 업데이트 실패
				systemMessage = "배송 처리에 실패 하였습니다.";
				
				if (StringUtils.isNotEmpty(errorReason)) {
					systemMessage += " - " + errorReason;
				}
	        }
	        
	        OrderInfo nowProduct = this.getOrderInfo(apiKey, mallOrder);
			if (nowProduct == null) {
				throw new MallException();
			}
	        
	        if (isSuccess == true) {
	        	
	        	mallOrder.setOrderItemStatus(nowProduct.getOrderStatus());

	        } else {
	        	
	        	// 클래임 접수 상태이면 주문 상태를 변경하지 않음
	        	if (this.isClaimCode(nowProduct.getOrderStatus())) {
	        		mallOrder.setOrderItemStatus("");
				} else {
					mallOrder.setOrderItemStatus(nowProduct.getOrderStatus());
				}
	        	
	        }
		}
		
		
		mallOrder.setMallOrderId(mallOrder.getMallOrderId());
        mallOrder.setSystemMessage(systemMessage);
        auctionMapper.updateMallOrder(mallOrder);
	}
	
	@Override
	public void cancelConfirm(String apiKey, MallOrderCancel cancelApply) {
		String responseMessage = "";
		
		MallOrder mallOrder = cancelApply.getMallOrder();
		
		try {
			
			String function = "ConfirmCancelApprovalList";
			String elementName = function + "Result";
			
			SOAPMessage message = auctionXmlConverter.getDefaultSoapMessage(apiKey, function);
	        SOAPElement bodyReqElement = auctionXmlConverter.getBodyReqElement(message.getSOAPBody(), function);
	        
	        bodyReqElement.setAttribute("OrderNo", cancelApply.getClaimCode());
	        message.saveChanges();
	        
	        ConfirmCancelApprovalListResult result = (ConfirmCancelApprovalListResult) auctionXmlConverter.convertXmlUrlToObject(message, function, elementName, ConfirmCancelApprovalListResult.class);

			if (result != null && result.getSuccess() != null) {
				responseMessage = result.getSuccess().startsWith("Success") ? "Success" : result.getSuccess();
			}
		} catch (Exception e) {
			log.error("AuctionServiceImpl : {}", e.getMessage());
			
		}
        
		boolean isSuccess = false;
        String systemMessage = "";
        if ("Success".equals(responseMessage)) {
        	isSuccess = true;
        } else {
        	// Api - 11번가 업데이트 실패
			systemMessage = "취소 처리에 실패 하였습니다. - " + responseMessage;
        }
        
        OrderInfo nowProduct = this.getOrderInfo(apiKey, mallOrder);
		if (nowProduct == null) {
			throw new MallException();
		}
        
        if (isSuccess == true) {
        	
        	mallOrder.setOrderItemStatus(nowProduct.getOrderStatus());

        	// 클레임 - 취소 처리 완료
        	cancelApply.setClaimStatus("02");
        	auctionMapper.updateMallORderCancelFinish(cancelApply);
        	
        } else {
        	
        	// 클래임 접수 상태이면 주문 상태를 변경하지 않음
        	if (this.isClaimCode(nowProduct.getOrderStatus())) {
        		mallOrder.setOrderItemStatus("");
			} else {
				mallOrder.setOrderItemStatus(nowProduct.getOrderStatus());
			}
        	
        }
		
        mallOrder.setMallOrderId(mallOrder.getMallOrderId());
        mallOrder.setSystemMessage(systemMessage);
        auctionMapper.updateMallOrder(mallOrder);
	}
	
	@Override
	public void cancelRefusal(String apiKey, MallOrderCancel cancelApply) {
		
		boolean isSuccess = false;
		MallOrder mallOrder = cancelApply.getMallOrder();
		
		String errorReason = "";
		try {
			
			String function = "DoShippingGeneral";
			String elementName = function + "Result";
			
			SOAPMessage message = auctionXmlConverter.getDefaultSoapMessage(apiKey, function);
	        SOAPElement bodyReqElement = auctionXmlConverter.getBodyReqElement(message.getSOAPBody(), function);
	        
	        bodyReqElement.setAttribute("OrderNo", Integer.toString(mallOrder.getOrderIndex()));
	        
	        SOAPElement element1 = bodyReqElement.addChildElement(auctionXmlConverter.createElementName("RemittanceMethod", "", "http://schema.auction.co.kr/Arche.APISvc.xsd"));
	        String remittanceMethodType = "Emoney";
	        
	        MyAccount myAccount = this.getMyAccount(apiKey);
	        if (myAccount != null) {
	        	if (StringUtils.isNotEmpty(myAccount.getAccountNumber())) {
		        	remittanceMethodType = "Cash";
		        	element1.setAttribute("RemittanceAccountName", myAccount.getAccountName());
		        	element1.setAttribute("RemittanceAccountNumber", myAccount.getAccountNumber());
		        	element1.setAttribute("RemittanceBankCode", myAccount.getBankCode());
	        	}
	        }
	        element1.setAttribute("RemittanceMethodType", remittanceMethodType);
	        
	        SOAPElement element = bodyReqElement.addChildElement(auctionXmlConverter.createElementName("ShippingMethod", "", "http://schema.auction.co.kr/Arche.APISvc.xsd"));
	        String sendDate = DateUtils.getToday("yyyy-MM-dd");
	        
	        try {
	        	sendDate = DateUtils.date(cancelApply.getCancelDeliveryDate());
			} catch(Exception e) {
				log.error("ERROR: {}", e.getMessage(), e);
			}
	        
	        element.setAttribute("SendDate", sendDate);
	        element.setAttribute("InvoiceNo", cancelApply.getCancelDeliveryNumber());
	        
	        /**
	         * Door2Door : 택배
	         * Etc : 기타발송
	         * Parcel : 소포/등기
	         */
	        element.setAttribute("ShippingMethodClassficationType", "Door2Door");
	        element.setAttribute("DeliveryAgency", cancelApply.getCancelDeliveryCompanyCode());
	        element.setAttribute("DeliveryAgencyName", ""); // 기타 택배사명
	        element.setAttribute("ShippingEtcAgencyName", ""); // 기타 발송 방법
	        
	        /**
	         * Direct : 직접 전달
	         * Etc : 기타
	         * Nothing : 선택 없음
	         * Post : 일반우편
	         * QuickService : 퀵서비스
	         * Truck : 화물 배달
	         */
	        element.setAttribute("ShippingEtcMethod", "Nothing");
	        
	        message.saveChanges();
	        
	        DoShippingGeneralResult result = (DoShippingGeneralResult) auctionXmlConverter.convertXmlUrlToObject(message, function, elementName, DoShippingGeneralResult.class);

			if (result != null) {
				if ("Success".equals(result.getSuccess())) {
					isSuccess = true;
				} else {
					errorReason = result.getFailReason();
				}
			}

		} catch (Exception e) {
			log.error("AuctionServiceImpl : {}", e.getMessage());
			
		}
        
        String systemMessage = "";
        if (isSuccess == true) {
        	isSuccess = true;
        } else {
        	// Api - 11번가 업데이트 실패
			systemMessage = "배송 처리에 실패 하였습니다.";
			
			if (StringUtils.isNotEmpty(errorReason)) {
				systemMessage += " - " + errorReason;
			}
        }
        
        OrderInfo nowProduct = this.getOrderInfo(apiKey, mallOrder);
		if (nowProduct == null) {
			throw new MallException();
		}
        
        if (isSuccess == true) {
        	
        	mallOrder.setOrderItemStatus(nowProduct.getOrderStatus());
        	
        } else {
        	
        	// 클래임 접수 상태이면 주문 상태를 변경하지 않음
        	if (this.isClaimCode(nowProduct.getOrderStatus())) {
        		mallOrder.setOrderItemStatus("");
			} else {
				mallOrder.setOrderItemStatus(nowProduct.getOrderStatus());
			}
        	
        }
		
        mallOrder.setMallOrderId(mallOrder.getMallOrderId());
        mallOrder.setSystemMessage(systemMessage);
        auctionMapper.updateMallOrder(mallOrder);
		
	}
	
	@Override
	public void cancel(String apiKey, MallOrderCancel cancelApply) {
		
		boolean isSuccess = false;
		MallOrder mallOrder = cancelApply.getMallOrder();
		
		try {
			
			String function = "DenySell";
			String elementName = function + "Result";
			
			SOAPMessage message = auctionXmlConverter.getDefaultSoapMessage(apiKey, function);
	        SOAPElement bodyReqElement = auctionXmlConverter.getBodyReqElement(message.getSOAPBody(), function);
	        
	        bodyReqElement.setAttribute("ItemID", mallOrder.getProductCode());
	        bodyReqElement.setAttribute("OrderNo", Integer.toString(mallOrder.getOrderIndex()));
	        bodyReqElement.setAttribute("DenySellReason", cancelApply.getCancelReason());

	        message.saveChanges();
	        
	        DenySellResult result = (DenySellResult) auctionXmlConverter.convertXmlUrlToObject(message, function, elementName, DenySellResult.class);
			if (result != null) {
				if ("Success".equals(result.getSuccess())) {
					isSuccess = true;
				}
			}
	        
		} catch (Exception e) {
			log.error("AuctionServiceImpl : {}", e.getMessage());
			
		}
        
        String systemMessage = "";
        if (isSuccess == true) {
        	isSuccess = true;
        } else {
        	// Api - 11번가 업데이트 실패
			systemMessage = "주문 취소 처리에 실패 하였습니다.";

        }
        
        OrderInfo nowProduct = this.getOrderInfo(apiKey, mallOrder);
		if (nowProduct == null) {
			throw new MallException();
		}
        
        if (isSuccess == true) {
        	
        	mallOrder.setOrderItemStatus(nowProduct.getOrderStatus());
        	
        } else {
        	
        	// 클래임 접수 상태이면 주문 상태를 변경하지 않음
        	if (this.isClaimCode(nowProduct.getOrderStatus())) {
        		mallOrder.setOrderItemStatus("");
			} else {
				mallOrder.setOrderItemStatus(nowProduct.getOrderStatus());
			}
        	
        }
		
        mallOrder.setMallOrderId(mallOrder.getMallOrderId());
        mallOrder.setSystemMessage(systemMessage);
        auctionMapper.updateMallOrder(mallOrder);
	}
	
	@Override
	public void returnConfirm(String apiKey, MallOrderReturn returnApply) {
		boolean isSuccess = false;
		MallOrder mallOrder = returnApply.getMallOrder();
		
		String errorMessage = "";
		try {
			
			String function = "DoReturnApproval";
			String elementName = function + "Result";
			
			SOAPMessage message = auctionXmlConverter.getDefaultSoapMessage(apiKey, function);
	        SOAPElement bodyReqElement = auctionXmlConverter.getBodyReqElement(message.getSOAPBody(), function);

	        bodyReqElement.setAttribute("OrderNo", Integer.toString(mallOrder.getOrderIndex()));
	        
	        message.saveChanges();
	        
	        DoReturnApprovalResult result = (DoReturnApprovalResult) auctionXmlConverter.convertXmlUrlToObject(message, function, elementName, DoReturnApprovalResult.class);

			if (result != null) {
				if ("Success".equals(result.getSuccess())) {
					isSuccess = true;
				}

				errorMessage = result.getErrorMessage();
			}
		} catch (Exception e) {
			log.error("AuctionServiceImpl : {}", e.getMessage());
			
		}
        
        String systemMessage = "";
        if (isSuccess == true) {
        	isSuccess = true;
        } else {

        	systemMessage = "반품 승인 처리에 실패 하였습니다.";
        	
        	if (StringUtils.isNotEmpty(errorMessage)) {
        		systemMessage += " - " + errorMessage;
        	}
        	
        }
        
        OrderInfo nowProduct = this.getOrderInfo(apiKey, mallOrder);
		if (nowProduct == null) {
			throw new MallException();
		}
        
        if (isSuccess == true) {
        	
			// 반품완료.. Api가 없다..
			returnApply.setClaimCode(returnApply.getClaimCode());
			returnApply.setClaimStatus("Finish");
			auctionMapper.updateReturnConfirm(returnApply);
        	
        	mallOrder.setOrderItemStatus(nowProduct.getOrderStatus());
        	
        } else {
        	
        	// 클래임 접수 상태이면 주문 상태를 변경하지 않음
        	if (this.isClaimCode(nowProduct.getOrderStatus())) {
        		mallOrder.setOrderItemStatus("");
			} else {
				mallOrder.setOrderItemStatus(nowProduct.getOrderStatus());
			}
        	
        }
		
        mallOrder.setMallOrderId(mallOrder.getMallOrderId());
        mallOrder.setSystemMessage(systemMessage);
        auctionMapper.updateMallOrder(mallOrder);
	}
	
	@Override
	public void returnHold(String apiKey, MallOrderReturn returnApply) {
		boolean isSuccess = false;
		MallOrder mallOrder = returnApply.getMallOrder();
		
		String errorMessage = "";
		try {
			
			String function = "DoReturnHold";
			String elementName = function + "Result";
			
			SOAPMessage message = auctionXmlConverter.getDefaultSoapMessage(apiKey, function);
	        SOAPElement bodyReqElement = auctionXmlConverter.getBodyReqElement(message.getSOAPBody(), function);

	        bodyReqElement.setAttribute("OrderNo", Integer.toString(mallOrder.getOrderIndex()));
	        bodyReqElement.setAttribute("ReturnHoldType", returnApply.getRtHoldReson());
	        bodyReqElement.setAttribute("ReturnHoldDetail", returnApply.getRtHoldResonText());
	        
	        // 사유가 반품 추가비용 청구가 아니면 0원
	        if (!"ReturnDemandFee".equals(returnApply.getRtHoldReson())) {
	        	returnApply.setRtShippingAmount(0);
	        }
	        
	        bodyReqElement.setAttribute("AddReturnFee", Integer.toString(returnApply.getRtShippingAmount()));
	        
	        message.saveChanges();
	        
	        DoReturnHoldResult result = (DoReturnHoldResult) auctionXmlConverter.convertXmlUrlToObject(message, function, elementName, DoReturnHoldResult.class);

			if (result != null) {
				if ("Success".equals(result.getSuccess())) {
					isSuccess = true;
				}

				errorMessage = result.getErrorMessage();
			}
		} catch (Exception e) {
			log.error("AuctionServiceImpl : {}", e.getMessage());
			
		}
        
        String systemMessage = "";
        if (isSuccess == true) {
        	isSuccess = true;
        } else {
        	// Api - 11번가 업데이트 실패
			systemMessage = "반품 보류 처리에 실패 하였습니다.";

        	if (StringUtils.isNotEmpty(errorMessage)) {
        		systemMessage += " - " + errorMessage;
        	}
        }
        
        OrderInfo nowProduct = this.getOrderInfo(apiKey, mallOrder);
		if (nowProduct == null) {
			throw new MallException();
		}
        
        if (isSuccess == true) {
        	
        	// 반품보류.. Api가 없다..
        	returnApply.setClaimCode(returnApply.getClaimCode());
        	returnApply.setClaimStatus("Hold");
        	returnApply.setRtHoldReson(returnApply.getRtHoldReson());
        	returnApply.setRtHoldResonText(returnApply.getRtHoldResonText());
			auctionMapper.updateReturnHold(returnApply);
        	
        	mallOrder.setOrderItemStatus(nowProduct.getOrderStatus());
        	
        } else {
        	
        	// 클래임 접수 상태이면 주문 상태를 변경하지 않음
        	if (this.isClaimCode(nowProduct.getOrderStatus())) {
        		mallOrder.setOrderItemStatus("");
			} else {
				mallOrder.setOrderItemStatus(nowProduct.getOrderStatus());
			}
        	
        }
		
        mallOrder.setMallOrderId(mallOrder.getMallOrderId());
        mallOrder.setSystemMessage(systemMessage);
        auctionMapper.updateMallOrder(mallOrder);
	}
	
	@Override
	public void returnRefusal(String apiKey, MallOrderReturn returnApply) {
		// 반품거부 없음
	}
	
	@Override
	public void exchangeConfirm(String apiKey, MallOrderExchange exchangeApply) {
		boolean isSuccess = false;
		MallOrder mallOrder = exchangeApply.getMallOrder();
		
		String errorMessage = "";
		try {
			
			String function = "DoExchangeSwitch";
			String elementName = function + "Result";
			
			SOAPMessage message = auctionXmlConverter.getDefaultSoapMessage(apiKey, function);
	        SOAPElement bodyReqElement = auctionXmlConverter.getBodyReqElement(message.getSOAPBody(), function);
	        bodyReqElement.setAttribute("OrderNo", Integer.toString(mallOrder.getOrderIndex()));
	        
	        SOAPElement element = bodyReqElement.addChildElement(auctionXmlConverter.createElementName("ShippingMethod", "", "http://schema.auction.co.kr/Arche.APISvc.xsd"));
	        element.setAttribute("SendDate", DateUtils.getToday("yyyy-MM-dd"));
	        element.setAttribute("InvoiceNo", exchangeApply.getExShippingNumber());
	        element.setAttribute("ShippingMethodClassficationType", "Door2Door");
	        element.setAttribute("DeliveryAgency", exchangeApply.getExShippingCompanyCode());
	        element.setAttribute("ShippingEtcMethod", "Nothing");
	        
	        message.saveChanges();
	        
	        DoExchangeSwitchResult result = (DoExchangeSwitchResult) auctionXmlConverter.convertXmlUrlToObject(message, function, elementName, DoExchangeSwitchResult.class);

			if (result != null) {
				if ("Success".equals(result.getSuccess())) {
					isSuccess = true;
				}

				errorMessage = result.getErrorMessage();
			}
		} catch (Exception e) {
			log.error("AuctionServiceImpl : {}", e.getMessage());
			
		}
        
        String systemMessage = "";
        if (isSuccess == true) {
        	isSuccess = true;
        } else {
        	// Api - 업데이트 실패
			systemMessage = "교환 완료 처리에 실패 하였습니다.";

        	if (StringUtils.isNotEmpty(errorMessage)) {
        		systemMessage += " - " + errorMessage;
        	}
        }
        
        OrderInfo nowProduct = this.getOrderInfo(apiKey, mallOrder);
		if (nowProduct == null) {
			throw new MallException();
		}
        
        if (isSuccess == true) {
        	
			// 교환완료.. Api가 없다..
        	exchangeApply.setClaimCode(exchangeApply.getClaimCode());
        	exchangeApply.setClaimStatus("Finish");
        	exchangeApply.setResendDeliveryCompanyCode(exchangeApply.getResendDeliveryCompanyCode());
        	exchangeApply.setResendDeliveryNumber(exchangeApply.getResendDeliveryNumber());
			auctionMapper.updateExchangeConfirm(exchangeApply);
        
        	mallOrder.setOrderItemStatus(nowProduct.getOrderStatus());
        	
        } else {
        	
        	// 클래임 접수 상태이면 주문 상태를 변경하지 않음
        	if (this.isClaimCode(nowProduct.getOrderStatus())) {
        		mallOrder.setOrderItemStatus("");
			} else {
				mallOrder.setOrderItemStatus(nowProduct.getOrderStatus());
			}
        	
        }
		
        mallOrder.setMallOrderId(mallOrder.getMallOrderId());
        mallOrder.setSystemMessage(systemMessage);
        auctionMapper.updateMallOrder(mallOrder);
	}
}
