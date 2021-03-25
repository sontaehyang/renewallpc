package saleson.shop.mall;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import saleson.shop.item.ItemMapper;
import saleson.shop.item.ItemService;
import saleson.shop.item.domain.Item;
import saleson.shop.item.domain.ItemOption;
import saleson.shop.mall.auction.AuctionService;
import saleson.shop.mall.domain.MallConfig;
import saleson.shop.mall.domain.MallOrder;
import saleson.shop.mall.domain.MallOrderCancel;
import saleson.shop.mall.domain.MallOrderExchange;
import saleson.shop.mall.domain.MallOrderReturn;
import saleson.shop.mall.est.EstService;
import saleson.shop.mall.support.MallConfigParam;
import saleson.shop.mall.support.MallException;
import saleson.shop.mall.support.MallOrderParam;

import com.onlinepowers.framework.sequence.service.SequenceService;
import com.onlinepowers.framework.util.ArrayUtils;
import com.onlinepowers.framework.util.CommonUtils;
import com.onlinepowers.framework.web.pagination.Pagination;

@Service("mallService")
public class MallServiceImpl implements MallService {

	@Autowired
	private MallMapper mallMapper;
	
	@Autowired
	private EstService estService;
	
	@Autowired
	private AuctionService auctionService;
	
	@Autowired
	private SequenceService sequenceService;

	@Autowired
	private ItemService itemService;

	@Autowired
	private ItemMapper itemMapper;
	
	@Override
	public MallOrder getMallOrderDetailByParam(MallOrderParam mallOrderParam) {
		return mallMapper.getMallOrderDetailByParam(mallOrderParam);
	}
	
	/**
	 * 재고없음?
	 * @param item
	 * @param mallOrder
	 * @return
	 */
	private boolean isSoldOut(Item item, MallOrder mallOrder) {
		
		if ("Y".equals(item.getSoldOut())) {
			return true;
		} else {
		
			// 재고량 검증
			if ("Y".equals(item.getItemOptionFlag())) {
				if (!StringUtils.isEmpty(mallOrder.getMatchedOptions())) {
					
					String[] ids = StringUtils.delimitedListToStringArray(mallOrder.getMatchedOptions(), "^^^");
					
					for(String optionId : ids) {
						int itemOptionId = Integer.parseInt(optionId);
						for(ItemOption itemOption : item.getItemOptions()) {
							if (itemOptionId == itemOption.getItemOptionId()) {
								if ("T".equals(itemOption.getOptionType())) {
									if ("Y".equals(item.getStockFlag())) {
										int stockQuantity = item.getStockQuantity();
										if (stockQuantity != -1 && stockQuantity < mallOrder.getQuantity() - mallOrder.getCancelQuantity()) {
											return true;
										}
									}
								} else {
									if ("Y".equals(itemOption.getOptionStockFlag())) {
										int stockQuantity = itemOption.getOptionStockQuantity();
										if (stockQuantity != -1 && stockQuantity < mallOrder.getQuantity() - mallOrder.getCancelQuantity()) {
											return true;
										}
									}
								}
							}
						}
					}
				}
			} else {
				if ("Y".equals(item.getStockFlag())) {

					int stockQuantity = item.getStockQuantity();
					if (stockQuantity != -1 && stockQuantity < mallOrder.getQuantity() - mallOrder.getCancelQuantity()) {
						return true;
					}
					
				}
			}
		}
		
		
		return false;
	}
	
	/**
	 * 상품 매칭?
	 * @param item
	 * @param mallOrder
	 * @return
	 */
	private boolean isMatched(Item item, MallOrder mallOrder) {
		
		if (mallOrder.getItemId() == 0) {
			return false;
		}

		if ("Y".equals(item.getItemOptionFlag())) {
			if (item.getItemOptions().size() > 0) {
				if (StringUtils.isEmpty(mallOrder.getMatchedOptions())) {
					return false;
				} else {
					boolean isMatched = false;
					
					String[] ids = StringUtils.delimitedListToStringArray(mallOrder.getMatchedOptions(), "^^^");
					int successCount = 0;
					for(ItemOption itemOption : item.getItemOptions()) {
						
						for(String id : ids) {
							int itemOptionId = Integer.parseInt(id);
							if (itemOptionId == itemOption.getItemOptionId()) {
								successCount++;
							}
						}
					}
					
					if (ids.length == successCount) {
						isMatched = true;
					}
					
					return isMatched;
				}
			}
		}
		
		return true;
	}
	
	@Override
	public List<MallOrder> getNewOrderListByParam(MallOrderParam mallOrderParam) {
		
		int totalCount = mallMapper.getNewOrderCountByParam(mallOrderParam);
		
		if (mallOrderParam.getItemsPerPage() == 10) {
			mallOrderParam.setItemsPerPage(50);
		}
		
		Pagination pagination = Pagination.getInstance(totalCount, mallOrderParam.getItemsPerPage());
		mallOrderParam.setPagination(pagination);
		mallOrderParam.setLanguage(CommonUtils.getLanguage());
		
		List<MallOrder> list = mallMapper.getNewOrderListByParam(mallOrderParam);
		
		if (list == null) {
			return null;
		}

		for(MallOrder mallOrder : list) {
			Item item = mallOrder.getItem();
			if (item == null) {
				continue;
			}
			
			if ("Y".equals(item.getItemOptionFlag())) {
				item.setItemOptions(itemService.getItemOptionList(item, false));
			}
			
			// 매칭?
			mallOrder.setMatched(this.isMatched(item, mallOrder));
			
			// 품절?
			mallOrder.setSoldOut(this.isSoldOut(item, mallOrder));
		}
		
		return list;
		
	}
	
	@Override
	public MallOrder getNewOrderDetailByParam(MallOrderParam mallOrderParam) {
		return mallMapper.getNewOrderDetailByParam(mallOrderParam);
	}
	
	@Override
	public void newOrderListUpdateTx(String mode, MallOrderParam mallOrderParam) {
		
		if (mallOrderParam.getId() == null) {
			throw new MallException();
		}
		
		if ("shipping-ready".equals(mode)) {
			
			for(String id : mallOrderParam.getId()) {
				int mallOrderId = Integer.parseInt(id);
				
				mallOrderParam.setMallOrderId(mallOrderId);
				MallOrder mallOrder = mallMapper.getNewOrderDetailByParam(mallOrderParam);
				
				HashMap<String, Integer> stockMap = null;
				
				if (mallOrder == null) {
					continue;
				}
				
				if (mallOrder.getItemId() > 0) {
					Item item = itemMapper.getItemById(mallOrder.getItemId());
					
					if (item == null) {
						continue;
					}
					
					if ("Y".equals(item.getItemOptionFlag())) {
						item.setItemOptions(itemService.getItemOptionList(item, false));
					}
					
					boolean isMatched = this.isMatched(item, mallOrder);
					if (isMatched) {
						// 판매 종료??
						boolean isSoldOut = this.isSoldOut(item, mallOrder);
						if (isSoldOut) {
							continue;
						} else {
							
							stockMap = new HashMap<>();
							// 옵션?
							String key = "";
							
							if ("Y".equals(item.getItemOptionFlag())) {
								
								String[] optionIds = StringUtils.delimitedListToStringArray(mallOrder.getMatchedOptions(), "^^^");
								
								for(ItemOption itemOption : item.getItemOptions()) {
									
									for(String optionId : optionIds) {
										int matchedOptionId = Integer.parseInt(optionId);
										if (matchedOptionId == itemOption.getItemOptionId()) {
											if ("T".equals(itemOption.getOptionType())) {
												if (StringUtils.isEmpty(item.getStockCode())) {
													key = "ITEM||" + item.getItemId();
												} else {
													key = "STOCK||" + item.getSellerId() + "||" + item.getStockCode();
												}
											} else {
												if (StringUtils.isEmpty(itemOption.getOptionStockCode())) {
													key = "OPTION||" + itemOption.getItemOptionId();
												} else {
													key = "STOCK||" + item.getSellerId() + "||" + itemOption.getOptionStockCode();
												}
											}
											
											stockMap.put(key, mallOrder.getQuantity() - mallOrder.getCancelQuantity());
										}
									}
								}
							} else {
								if ("Y".equals(item.getStockFlag())) {
									if (StringUtils.isEmpty(item.getStockCode())) {
										key = "ITEM||" + item.getItemId();
									} else {
										key = "STOCK||" + item.getSellerId() + "||" + item.getStockCode();
									}
									
									stockMap.put(key, mallOrder.getQuantity() - mallOrder.getCancelQuantity());
								}
							}
							
							
						}
					}

					mallOrder.setItem(item);
				}
				
				if ("11st".equals(mallOrder.getMallType())) {
					estService.packaging(mallOrder.getMallApiKey(), mallOrder, stockMap);
				} else if ("auction".equals(mallOrder.getMallType())) {
					auctionService.packaging(mallOrder.getMallApiKey(), mallOrder, stockMap);
				} else {
					throw new MallException();
				}
				
			}
			
		} else if ("matching-save".equals(mode)) {
			
			int i = 0;
			for(String id : mallOrderParam.getId()) {
				int mallOrderId = Integer.parseInt(id);
				String itemUserCode = ArrayUtils.get(mallOrderParam.getItemUserCodes(), i);
				mallOrderParam.setMallOrderId(mallOrderId);
				
				// 상품번호가 없으면 그냥 초기화
				if (StringUtils.isEmpty(itemUserCode)) {
					mallOrderParam.setItemUserCode("");
					mallOrderParam.setMatchedOptions("");
					mallMapper.updateMallOrderMatchingItemUserCode(mallOrderParam);
					i++;
					continue;
				}
				
				
				Item item = itemMapper.getItemByItemUserCode(itemUserCode);
				
				if (item == null) {
					continue;
				}
				
				if ("Y".equals(item.getItemOptionFlag())) {
					
					if (mallOrderParam.getMatchedOptionIds() != null) {
						int[] matchedOptionIdArray = mallOrderParam.getMatchedOptionIds().get(i);
						if (matchedOptionIdArray != null) {
							item.setItemOptions(itemService.getItemOptionList(item, false));
							
							String matchedOptionIds = "";
							
							int optionCount = 0;
							int successCount = 0;
							if ("S".equals(item.getItemOptionType())) {
								
								HashMap<String, String> map = new HashMap<>();
								for(ItemOption itemOption : item.getItemOptions()) {
									
									if ("T".equals(itemOption.getOptionType())) {
										continue;
									}
									
									if (map.get(itemOption.getOptionName1()) == null) {
										map.put(itemOption.getOptionName1(), itemOption.getOptionName1());
									}
								}
								 
								optionCount = map.keySet().size();
		
								for(int matchedOptionId : matchedOptionIdArray) {
									for(ItemOption itemOption : item.getItemOptions()) {
										
										if (matchedOptionId == itemOption.getItemOptionId()) {
											matchedOptionIds += (StringUtils.isEmpty(matchedOptionIds) ? "" : "^^^") + Integer.toString(matchedOptionId);
											successCount++;
										}
									}
								}
								
							} else {
								optionCount = 1;
								for(int matchedOptionId : matchedOptionIdArray) {
									for(ItemOption itemOption : item.getItemOptions()) {
										if (matchedOptionId == itemOption.getItemOptionId()) {
											matchedOptionIds = Integer.toString(matchedOptionId);
											successCount++;
											break;
										}
									}
								}
							}
							
							if (optionCount == successCount) {
								mallOrderParam.setMatchedOptions(matchedOptionIds);
							}
						}
					}
				}
			
				mallOrderParam.setItemUserCode(itemUserCode);
				mallMapper.updateMallOrderMatchingItemUserCode(mallOrderParam);
				
				i++;
			}
			
		} else {
			throw new MallException();
		}
		
	}
	
	@Override
	public List<MallOrder> getShippingReadyListByParam(MallOrderParam mallOrderParam) {
		
		int totalCount = mallMapper.getShippingReadyCountByParam(mallOrderParam);
		
		if (mallOrderParam.getItemsPerPage() == 10) {
			mallOrderParam.setItemsPerPage(50);
		}
		
		Pagination pagination = Pagination.getInstance(totalCount, mallOrderParam.getItemsPerPage());
		mallOrderParam.setPagination(pagination);
		mallOrderParam.setLanguage(CommonUtils.getLanguage());
		
		return mallMapper.getShippingReadyListByParam(mallOrderParam);
		
	}
	
	@Override
	public void shippingReadyListUpdate(String mode, MallOrderParam mallOrderParam) {

		if (mallOrderParam == null) {
			throw new MallException();
		}

		if (mallOrderParam.getId() == null) {
			throw new MallException();
		}
		
		if ("shipping-start".equals(mode)) {
			for(String id : mallOrderParam.getId()) {
				int mallOrderId = Integer.parseInt(id);
				
				String deliveryCompanyCode = "";
				String deliveryNumber = "";
				for(int i = 0; i < mallOrderParam.getMallOrderIds().length; i++) {
					
					int checkMallOrderId = Integer.parseInt(mallOrderParam.getMallOrderIds()[i]);
					
					if (checkMallOrderId == mallOrderId) {
						deliveryCompanyCode = mallOrderParam.getDeliveryCompanyCodes()[i];
						deliveryNumber = mallOrderParam.getDeliveryNumbers()[i];
						break;
					}
				}
				
				if (StringUtils.isEmpty(deliveryCompanyCode) || StringUtils.isEmpty(deliveryNumber)) {
					continue;
				}
				
				
				mallOrderParam.setMallOrderId(mallOrderId);
				MallOrder mallOrder = mallMapper.getShippingReadyDetailByParam(mallOrderParam);
				
				if (mallOrder == null) {
					continue;
				}
				
				mallOrder.setDeliveryCompanyCode(deliveryCompanyCode);
				mallOrder.setDeliveryNumber(deliveryNumber);

				if ("11st".equals(mallOrder.getMallType())) {
					estService.shipping(mallOrder.getMallApiKey(), mallOrder);
				} else if ("auction".equals(mallOrder.getMallType())) {
					auctionService.shipping(mallOrder.getMallApiKey(), mallOrder);
				} else {
					throw new MallException();
				}
			}
		} else {
			throw new MallException();
		}
	}
	
	@Override
	public List<MallOrder> getShippingListByParam(MallOrderParam mallOrderParam) {
		int totalCount = mallMapper.getShippingCountByParam(mallOrderParam);
		
		if (mallOrderParam.getItemsPerPage() == 10) {
			mallOrderParam.setItemsPerPage(50);
		}
		
		Pagination pagination = Pagination.getInstance(totalCount, mallOrderParam.getItemsPerPage());
		mallOrderParam.setPagination(pagination);
		mallOrderParam.setLanguage(CommonUtils.getLanguage());
		
		return mallMapper.getShippingListByParam(mallOrderParam);
	}
	
	@Override
	public List<MallOrderCancel> getCancelRequestListByParam(MallOrderParam mallOrderParam) {
		int totalCount = mallMapper.getCancelRequestCountByParam(mallOrderParam);
		
		if (mallOrderParam.getItemsPerPage() == 10) {
			mallOrderParam.setItemsPerPage(50);
		}
		
		Pagination pagination = Pagination.getInstance(totalCount, mallOrderParam.getItemsPerPage());
		mallOrderParam.setPagination(pagination);
		mallOrderParam.setLanguage(CommonUtils.getLanguage());
		
		return mallMapper.getCancelRequestListByParam(mallOrderParam);
	}
	
	@Override
	public MallOrderCancel getCancelRequestDetailByParam(MallOrderParam mallOrderParam) {
		return mallMapper.getCancelRequestDetailByParam(mallOrderParam);
	}
	
	@Override
	public void cancelRequestListUpdate(String mode, MallOrderParam mallOrderParam) {
		if (mallOrderParam.getId() == null) {
			throw new MallException();
		}
		
		if ("cancel-confirm".equals(mode)) {

			for(String id : mallOrderParam.getId()) {
				String[] temp = StringUtils.delimitedListToStringArray(id, "||");
				int mallOrderId = Integer.parseInt(temp[0]);
				String claimCode = temp[1];
				
				mallOrderParam.setMallOrderId(mallOrderId);
				mallOrderParam.setClaimCode(claimCode);
				MallOrderCancel cancelApply = mallMapper.getCancelRequestDetailByParam(mallOrderParam);
				
				if (cancelApply == null) {
					continue;
				}
				
				MallOrder mallOrder = cancelApply.getMallOrder();
				if ("11st".equals(mallOrder.getMallType())) {
					estService.cancelConfirm(mallOrder.getMallApiKey(), cancelApply);
				} else if ("auction".equals(mallOrder.getMallType())) {
					auctionService.cancelConfirm(mallOrder.getMallApiKey(), cancelApply);
				} else {
					throw new MallException();
				}
			}
		} else {
			throw new MallException();
		}
	}
	
	@Override
	public List<MallOrderCancel> getCancelFinishListByParam(MallOrderParam mallOrderParam) {
		int totalCount = mallMapper.getCancelFinishCountByParam(mallOrderParam);
		
		if (mallOrderParam.getItemsPerPage() == 10) {
			mallOrderParam.setItemsPerPage(50);
		}
		
		Pagination pagination = Pagination.getInstance(totalCount, mallOrderParam.getItemsPerPage());
		mallOrderParam.setPagination(pagination);
		mallOrderParam.setLanguage(CommonUtils.getLanguage());
		
		return mallMapper.getCancelFinishListByParam(mallOrderParam);
	}
	
	@Override
	public List<MallOrderReturn> getReturnRequestListByParam(MallOrderParam mallOrderParam) {
		int totalCount = mallMapper.getReturnRequestCountByParam(mallOrderParam);
		
		if (mallOrderParam.getItemsPerPage() == 10) {
			mallOrderParam.setItemsPerPage(50);
		}
		
		Pagination pagination = Pagination.getInstance(totalCount, mallOrderParam.getItemsPerPage());
		mallOrderParam.setPagination(pagination);
		mallOrderParam.setLanguage(CommonUtils.getLanguage());
		
		return mallMapper.getReturnRequestListByParam(mallOrderParam);
	}
	
	@Override
	public MallOrderReturn getReturnRequestDetailByParam(MallOrderParam mallOrderParam) {
		return mallMapper.getReturnRequestDetailByParam(mallOrderParam);
	}
	
	@Override
	public List<MallOrderReturn> getReturnFinishListByParam(MallOrderParam mallOrderParam) {
		int totalCount = mallMapper.getReturnFinishCountByParam(mallOrderParam);
		
		if (mallOrderParam.getItemsPerPage() == 10) {
			mallOrderParam.setItemsPerPage(50);
		}
		
		Pagination pagination = Pagination.getInstance(totalCount, mallOrderParam.getItemsPerPage());
		mallOrderParam.setPagination(pagination);
		mallOrderParam.setLanguage(CommonUtils.getLanguage());
		
		return mallMapper.getReturnFinishListByParam(mallOrderParam);
	}
	
	@Override
	public void returnRequestListUpdate(String mode, MallOrderParam mallOrderParam) {
		if (mallOrderParam.getId() == null) {
			throw new MallException();
		}
		
		if ("return-confirm".equals(mode)) {

			for(String id : mallOrderParam.getId()) {
				String[] temp = StringUtils.delimitedListToStringArray(id, "||");
				int mallOrderId = Integer.parseInt(temp[0]);
				String claimCode = temp[1];
				
				mallOrderParam.setMallOrderId(mallOrderId);
				mallOrderParam.setClaimCode(claimCode);
				MallOrderReturn returnApply = mallMapper.getReturnRequestDetailByParam(mallOrderParam);
				
				if (returnApply == null) {
					continue;
				}
				
				MallOrder mallOrder = returnApply.getMallOrder();
				if ("11st".equals(mallOrder.getMallType())) {
					estService.returnConfirm(mallOrder.getMallApiKey(), returnApply);
				} else if ("auction".equals(mallOrder.getMallType())) {
					auctionService.returnConfirm(mallOrder.getMallApiKey(), returnApply);
				} else {
					throw new MallException();
				}
			}
		} else {
			throw new MallException();
		}
	}
	
	@Override
	public List<MallOrderExchange> getExchangeRequestListByParam(MallOrderParam mallOrderParam) {
		int totalCount = mallMapper.getExchangeRequestCountByParam(mallOrderParam);
		
		if (mallOrderParam.getItemsPerPage() == 10) {
			mallOrderParam.setItemsPerPage(50);
		}
		
		Pagination pagination = Pagination.getInstance(totalCount, mallOrderParam.getItemsPerPage());
		mallOrderParam.setPagination(pagination);
		mallOrderParam.setLanguage(CommonUtils.getLanguage());
		
		return mallMapper.getExchangeRequestListByParam(mallOrderParam);
	}
	
	@Override
	public void exchangeRequestListUpdate(String mode, MallOrderParam mallOrderParam) {
		if (mallOrderParam.getId() == null) {
			throw new MallException();
		}
		
		if ("exchange-confirm".equals(mode)) {

			for(String id : mallOrderParam.getId()) {
				String[] temp = StringUtils.delimitedListToStringArray(id, "||");
				
				String deliveryCompanyCode = "";
				String deliveryNumber = "";
				for(int i = 0; i < mallOrderParam.getExchangeKeys().length; i++) {
					
					String checkExchangeKey = mallOrderParam.getExchangeKeys()[i];
					if (checkExchangeKey.equals(id)) {
						deliveryCompanyCode = mallOrderParam.getDeliveryCompanyCodes()[i];
						deliveryNumber = mallOrderParam.getDeliveryNumbers()[i];
						break;
					}
				}
				
				if (StringUtils.isEmpty(deliveryCompanyCode) || StringUtils.isEmpty(deliveryNumber)) {
					continue;
				}
				
				
				int mallOrderId = Integer.parseInt(temp[0]);
				String claimCode = temp[1];
				
				mallOrderParam.setMallOrderId(mallOrderId);
				mallOrderParam.setClaimCode(claimCode);
				MallOrderExchange exchangeApply = mallMapper.getExchangeRequestDetailByParam(mallOrderParam);
				
				if (exchangeApply == null) {
					continue;
				}
				
				exchangeApply.setResendDeliveryCompanyCode(deliveryCompanyCode);
				exchangeApply.setResendDeliveryNumber(deliveryNumber);
				
				MallOrder mallOrder = exchangeApply.getMallOrder();
				if ("11st".equals(mallOrder.getMallType())) {
					estService.exchangeConfirm(mallOrder.getMallApiKey(), exchangeApply);
				} else if ("auction".equals(mallOrder.getMallType())) {
					auctionService.exchangeConfirm(mallOrder.getMallApiKey(), exchangeApply);
				} else {
					throw new MallException();
				}
			}
		} else {
			throw new MallException();
		}
	}
	
	@Override
	public MallOrderExchange getExchangeRequestDetailByParam(MallOrderParam mallOrderParam) {
		return mallMapper.getExchangeRequestDetailByParam(mallOrderParam);
	}
	
	@Override
	public List<MallOrderExchange> getExchangeFinishListByParam(MallOrderParam mallOrderParam) {
		int totalCount = mallMapper.getExchangeFinishCountByParam(mallOrderParam);
		
		if (mallOrderParam.getItemsPerPage() == 10) {
			mallOrderParam.setItemsPerPage(50);
		}
		
		Pagination pagination = Pagination.getInstance(totalCount, mallOrderParam.getItemsPerPage());
		mallOrderParam.setPagination(pagination);
		mallOrderParam.setLanguage(CommonUtils.getLanguage());
		
		return mallMapper.getExchangeFinishListByParam(mallOrderParam);
	}
	
	@Override
	public List<MallConfig> getMallConfigListByParam(MallConfigParam mallConfigParam) {
		return mallMapper.getMallConfigListByParam(mallConfigParam);
	}
	
	@Async
	@Override
	public void orderCollectTx(MallConfigParam mallConfigParam) {
		
		if (mallConfigParam.getId() == null) {
			return;
		}
		
		// 거래중인 오픈마켓만 조회
		mallConfigParam.setDataStatusCode("1");
		List<MallConfig> configList = mallMapper.getMallConfigListByParam(mallConfigParam);
		if (configList == null) {
			return;
		}
		
		String searchStartDate = mallConfigParam.getSearchStartDate() + mallConfigParam.getSearchStartDateTime() + "00";
		String searchEndDate = mallConfigParam.getSearchEndDate() + mallConfigParam.getSearchEndDateTime() + "59";
		
		for(MallConfig config : configList) {
			// 일반주문
			if ("1".equals(mallConfigParam.getCollectTargetDefault())) {
				
				// 수집중이라면 처리 안함
				if ("3".equals(config.getStatusCode())) {
					continue;
				}
			
				// 수집중으로 업데이트
				config.setStatusCode("3");
				mallMapper.updateCollectStatusCode(config);
			
				if ("11st".equals(config.getMallType())) {
					config = estService.newOrderCollect(config.getMallApiKey(), searchStartDate, searchEndDate, config);
				} else if ("auction".equals(config.getMallType())) {
					config = auctionService.newOrderCollect(config.getMallApiKey(), searchStartDate, searchEndDate, config);
				}
				
				// 수집결과 업데이트
				mallMapper.updateMallConfig(config);
			}
			
			// 클레임
			if ("1".equals(mallConfigParam.getCollectTargetClaim())) {
				
				if ("3".equals(config.getClaimStatusCode())) {
					continue;
				}
				
				// 수집중으로 업데이트
				config.setClaimStatusCode("3");
				mallMapper.updateClaimCollectStatusCode(config);
				
				if ("11st".equals(config.getMallType())) {
					config = estService.claimCollect(config.getMallApiKey(), searchStartDate, searchEndDate, config);
				} else if ("auction".equals(config.getMallType())) {
					config = auctionService.claimCollect(config.getMallApiKey(), searchStartDate, searchEndDate, config);
				}
				
				// 수집결과 업데이트
				mallMapper.updateMallConfigForClaim(config);
			}
		}
		
	}
	
	@Override
	public void cancelAction(String mallType, MallOrderCancel postCancelApply) {
		MallOrderParam mallOrderParam = new MallOrderParam();
		mallOrderParam.setMallOrderId(postCancelApply.getMallOrderId());
		
		MallOrder mallOrder = mallMapper.getMallOrderDetailByParam(mallOrderParam);
		if (mallOrder == null) {
			throw new MallException();
		}
		
		MallOrderCancel cancelApply = new MallOrderCancel();
		
		// 취소 사유
		cancelApply.setCancelReason(postCancelApply.getCancelReason());
		cancelApply.setCancelReasonText(postCancelApply.getCancelReasonText());
		
		cancelApply.setMallOrder(mallOrder);
		
		if ("11st".equals(mallOrder.getMallType())) {
			estService.cancelApply(mallOrder.getMallApiKey(), cancelApply);
		} else if ("auction".equals(mallOrder.getMallType())) {
			auctionService.cancel(mallOrder.getMallApiKey(), cancelApply);
		} else {
			throw new MallException();
		}
	}
	
	@Override
	public void cancelRefusalAction(String mallType, MallOrderCancel postCancelApply) {
		MallOrderParam mallOrderParam = new MallOrderParam();
		mallOrderParam.setMallOrderId(postCancelApply.getMallOrderId());
		mallOrderParam.setClaimCode(postCancelApply.getClaimCode());
		
		MallOrderCancel cancelApply = mallMapper.getCancelRequestDetailByParam(mallOrderParam);
		if (cancelApply == null) {
			throw new MallException();
		}
		
		// 취소 사유
		cancelApply.setCancelRefusalReson(postCancelApply.getCancelRefusalReson());
		cancelApply.setCancelRefusalResonText(postCancelApply.getCancelRefusalResonText());
		
		// 배송 정보
		cancelApply.setCancelDeliveryDate(postCancelApply.getCancelDeliveryDate());
		cancelApply.setCancelDeliveryCompanyCode(postCancelApply.getCancelDeliveryCompanyCode());
		cancelApply.setCancelDeliveryNumber(postCancelApply.getCancelDeliveryNumber());
		
		MallOrder mallOrder = cancelApply.getMallOrder();
		if ("11st".equals(mallOrder.getMallType())) {
			estService.cancelRefusal(mallOrder.getMallApiKey(), cancelApply);
		} else if ("auction".equals(mallOrder.getMallType())) {
			auctionService.cancelRefusal(mallOrder.getMallApiKey(), cancelApply);
		} else {
			throw new MallException();
		}
	}
	
	@Override
	public void returnHoldAction(String mallType, MallOrderReturn postReturnApply) {
		MallOrderParam mallOrderParam = new MallOrderParam();
		mallOrderParam.setMallOrderId(postReturnApply.getMallOrderId());
		mallOrderParam.setClaimCode(postReturnApply.getClaimCode());
		
		MallOrderReturn returnApply = mallMapper.getReturnRequestDetailByParam(mallOrderParam);
		if (returnApply == null) {
			throw new MallException();
		}
		
		// 보류 사유
		returnApply.setRtHoldReson(postReturnApply.getRtHoldReson());
		returnApply.setRtHoldResonText(postReturnApply.getRtHoldResonText());
		
		MallOrder mallOrder = returnApply.getMallOrder();
		if ("11st".equals(mallOrder.getMallType())) {
			estService.returnHold(mallOrder.getMallApiKey(), returnApply);
		} else if ("auction".equals(mallOrder.getMallType())) {
			auctionService.returnHold(mallOrder.getMallApiKey(), returnApply);
		} else {
			throw new MallException();
		}
	}
	
	@Override
	public void returnRefusalAction(String mallType, MallOrderReturn postReturnApply) {
		MallOrderParam mallOrderParam = new MallOrderParam();
		mallOrderParam.setMallOrderId(postReturnApply.getMallOrderId());
		mallOrderParam.setClaimCode(postReturnApply.getClaimCode());
		
		MallOrderReturn returnApply = mallMapper.getReturnRequestDetailByParam(mallOrderParam);
		if (returnApply == null) {
			throw new MallException();
		}
		
		// 거절 사유
		returnApply.setRtRefusalReson(postReturnApply.getRtRefusalReson());
		returnApply.setRtRefusalResonText(postReturnApply.getRtRefusalResonText());
		
		MallOrder mallOrder = returnApply.getMallOrder();
		if ("11st".equals(mallOrder.getMallType())) {
			estService.returnRefusal(mallOrder.getMallApiKey(), returnApply);
		} else if ("auction".equals(mallOrder.getMallType())) {
			// 옥션에서 반품 거부 재공 안함
			//auctionService.returnRefusal(mallOrder.getMallApiKey(), returnApply);
		} else {
			throw new MallException();
		}
	}
	
	@Override
	public void exchangeRefusalAction(String mallType, MallOrderExchange postExchangeApply) {
		MallOrderParam mallOrderParam = new MallOrderParam();
		mallOrderParam.setMallOrderId(postExchangeApply.getMallOrderId());
		mallOrderParam.setClaimCode(postExchangeApply.getClaimCode());
		
		MallOrderExchange exchangeApply = mallMapper.getExchangeRequestDetailByParam(mallOrderParam);
		if (exchangeApply == null) {
			throw new MallException();
		}
		
		// 보류 사유
		exchangeApply.setExRefusalReson(postExchangeApply.getExRefusalReson());
		exchangeApply.setExRefusalResonText(postExchangeApply.getExRefusalResonText());
		
		MallOrder mallOrder = exchangeApply.getMallOrder();
		if ("11st".equals(mallOrder.getMallType())) {
			estService.exchangeRefusal(mallOrder.getMallApiKey(), exchangeApply);
		} else {
			throw new MallException();
		}
	}
}
