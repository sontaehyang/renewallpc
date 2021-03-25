package saleson.shop.statistics;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.onlinepowers.framework.security.userdetails.User;
import com.onlinepowers.framework.util.JsonViewUtils;
import com.onlinepowers.framework.util.StringUtils;

import saleson.shop.item.domain.ItemOption;
import saleson.shop.order.domain.OrderShippingInfo;
import saleson.shop.statistics.domain.*;
import saleson.shop.statistics.support.StatisticsParam;

@Service("ShopStatisticsService")
public class ShopStatisticsServiceImpl implements ShopStatisticsService {
	
	@Autowired
	ShopStatisticsMapper shopStatisticsMapper;

	@Override
	public List<PaymentStatistics> getPaymentStatisticsListByParam(StatisticsParam statisticsParam) {
		return shopStatisticsMapper.getPaymentStatisticsListByParam(statisticsParam);
	}
	
	
	private boolean hasOrderCode(List<String> shippingOrderCodes, String orderCode) {
		for (String shippingOrderCode : shippingOrderCodes) {
			if (shippingOrderCode.equals(orderCode)) {
				return true;
			}
		}
		return false;
	}
	@Override
	public List<RevenueBaseForDate> getRevenueDetailListForDateByParam(StatisticsParam statisticsParam) {
		
 		List<RevenueBaseForDate> list = shopStatisticsMapper.getRevenueDetailListForDateByParam(statisticsParam);
		List<RevenueDetail> shippingList = shopStatisticsMapper.getShippingDetailListForDateByParam(statisticsParam);
		if (list == null) {
			return null;
		}
		
		
		List<String> shippingOrderCodes = new ArrayList<>();
	

		for(RevenueBaseForDate revenueBaseForDate : list) {

			if (revenueBaseForDate.getList() == null) {
				continue;
			}
			
			for (RevenueDetail detail : revenueBaseForDate.getList()) {
				
				if (detail.getItems() == null) {
					continue;
				}

				// list에 ITEM_SEQUENCE로 조인해서 shippingList 정보 add[2017-04-06]minae.yun
				// [2017-04-28] 판매자별 보기에서는 배송비 노출하지 않음.
				for(RevenueDetail shippingDetail : shippingList) {
					if (detail.getOrderCode().equals(shippingDetail.getOrderCode()) && detail.getOrderType().equals(shippingDetail.getOrderType()) 
							&& revenueBaseForDate.getKey().equals(shippingDetail.getDate()) ) {
						
						detail.setPrice(shippingDetail.getPrice());
						detail.setCartCouponDiscountAmount(shippingDetail.getCartCouponDiscountAmount());
						
						if (statisticsParam.getSellerId() > 0) {
							detail.getItems().get(0).setOrderShipping(0);
                            detail.getItems().get(0).setSubTotal(shippingDetail.getItemAmount());
						} else {
							String shippingKey = revenueBaseForDate.getKey() + ":" + detail.getOrderType() + ":" + detail.getOrderCode();
						
							if (!hasOrderCode(shippingOrderCodes, shippingKey) && detail.getItems().size() > 0) {
								
								detail.getItems().get(0).setOrderShipping(shippingDetail.getSumDeliveryPrice());
                                detail.getItems().get(0).setSubTotal(shippingDetail.getItemAmount() + shippingDetail.getSumDeliveryPrice());
								shippingOrderCodes.add(shippingKey);
							}
						}
						
					}
				}
				
				for(RevenueDetailItem orderItem : detail.getItems()) {
					
					
					// 필수 옵션
					if (!StringUtils.isEmpty(orderItem.getRequiredOptions())) {
						if (orderItem.getRequiredOptions().startsWith("[")) {
							List<ItemOption> requiredOptionsList = (List<ItemOption>) JsonViewUtils.jsonToObject(orderItem.getRequiredOptions(), new TypeReference<List<ItemOption>>(){});
							
							if (!requiredOptionsList.isEmpty()) {
								orderItem.setRequiredOptionsList(requiredOptionsList);
							}
						} else {
							orderItem.setOpenMarektOption(orderItem.getRequiredOptions());
						}
					}
				}
			}
		}
		
		return list;
	}
	
	@Override
	public TotalRevenueStatistics getUserTotalRevenueStatisticsByParam(StatisticsParam statisticsParam) {
		return shopStatisticsMapper.getUserTotalRevenueStatisticsByParam(statisticsParam);
	}
	
	@Override
	public List<ShopItemDetailStatistics> getShopItemDetailList(StatisticsParam statisticsParam) {
		return shopStatisticsMapper.getShopItemDetailList(statisticsParam);
	}

	@Override
	public List<ShopOrderStatistics> getOrderListByParam(StatisticsParam statisticsParam) {
		return shopStatisticsMapper.getOrderListByParam(statisticsParam);
	}

	@Override
	public List<ShopItemDetailStatistics> getAreaDetailList(StatisticsParam statisticsParam) {
		return shopStatisticsMapper.getAreaDetailList(statisticsParam);
	}

	@Override
	public List<ShopItemDetailStatistics> getUserStatisticsListByParam(StatisticsParam statisticsParam) {
		return shopStatisticsMapper.getUserStatisticsListByParam(statisticsParam);
	}

	@Override
	public int getUserStatisticsCountByParam(StatisticsParam statisticsParam) {
		return shopStatisticsMapper.getUserStatisticsCountByParam(statisticsParam);
	}

	@Override
	public List<ShopItemStatistics> getUserOrderItemListByParam(StatisticsParam statisticsParam) {
		return shopStatisticsMapper.getUserOrderItemListByParam(statisticsParam);
	}

	@Override
	public OrderShippingInfo getUsetOrderTotalDetailById(StatisticsParam statisticsParam) {
		return shopStatisticsMapper.getUsetOrderTotalDetailById(statisticsParam);
	}

	@Override
	public List<ShopOrderStatistics> getUserOrderListByParam(StatisticsParam statisticsParam) {
		return shopStatisticsMapper.getUserOrderListByParam(statisticsParam);
	}

	@Override
	public List<DoNotSellItem> getDoNotSellItemListByParam(StatisticsParam statisticsParam) {
		return shopStatisticsMapper.getDoNotSellItemListByParam(statisticsParam);
	}

	@Override
	public List<User> getNotUserList(StatisticsParam statisticsParam) {
		return shopStatisticsMapper.getNotUserList(statisticsParam);
	}

	@Override
	public int getNotUserCount(StatisticsParam statisticsParam) {
		return shopStatisticsMapper.getNotUserCount(statisticsParam);
	}

	@Override
	public ShopItemStatistics getAreaCountParam(StatisticsParam statisticsParam) {
		return shopStatisticsMapper.getAreaCountParam(statisticsParam);
	}

	@Override
	public int getDoNotSellItemCountByParam(StatisticsParam statisticsParam) {
		return shopStatisticsMapper.getDoNotSellItemCountByParam(statisticsParam);
	}

	@Override
	public List<ShopItemDateStatistics> getItemDateListByParam(
			StatisticsParam statisticsParam) {
		return shopStatisticsMapper.getItemDateListByParam(statisticsParam);
	}
	
	@Override
	public List<ShopBrandStatistics> getBrandStatisticsListByParam(StatisticsParam statisticsParam) {
		return shopStatisticsMapper.getBrandStatisticsListByParam(statisticsParam);
	}
	
	@Override
	public List<RevenueBaseForDate> getBrandStatisticsDetailByParam(StatisticsParam statisticsParam) {
		return shopStatisticsMapper.getBrandStatisticsDetailByParam(statisticsParam);	
	}

	@Override
	public StatsSummary getSellerStatsSummary(StatisticsParam statisticsParam) {
		List<BaseStats> baseStats =  shopStatisticsMapper.getSellerStats(statisticsParam);

		return new StatsSummary(baseStats);
	}

	@Override
	public StatsSummary getItemStatsSummary(StatisticsParam statisticsParam) {
		List<BaseStats> baseStats =  shopStatisticsMapper.getItemStats(statisticsParam);

		return new StatsSummary(baseStats);
	}

	@Override
	public List<DateStatsSummary> getDateStatsList(StatisticsParam statisticsParam) {

		statisticsParam.setExtra("DATE");

		return shopStatisticsMapper.getDateStatsList(statisticsParam);
	}

	@Override
	public List<SellerStatsSummary> getSellerStatsList(StatisticsParam statisticsParam) {
		statisticsParam.setExtra("SELLER");

		return shopStatisticsMapper.getSellerStatsList(statisticsParam);
	}

	@Override
	public List<ItemStatsSummary> getItemStatsList(StatisticsParam statisticsParam) {
		statisticsParam.setExtra("ITEM");

		List<ItemStatsSummary> itemStatsList = shopStatisticsMapper.getItemStatsList(statisticsParam);

		if (!StringUtils.isEmpty(statisticsParam.getOrderBy())) {
			if ("QUANTITY".equals(statisticsParam.getOrderBy())) {
				if ("ASC".equals(statisticsParam.getSort())) {
					itemStatsList = itemStatsList.stream().sorted(Comparator.comparing((ItemStatsSummary::getSubTotalCount))).collect(Collectors.toList());
				} else {
					itemStatsList = itemStatsList.stream().sorted(Comparator.comparing((ItemStatsSummary::getSubTotalCount), Comparator.reverseOrder())).collect(Collectors.toList());
				}
			} else {
				if ("ASC".equals(statisticsParam.getSort())) {
					itemStatsList = itemStatsList.stream().sorted(Comparator.comparing((ItemStatsSummary::getSubTotalAmount))).collect(Collectors.toList());
				} else {
					itemStatsList = itemStatsList.stream().sorted(Comparator.comparing((ItemStatsSummary::getSubTotalAmount), Comparator.reverseOrder())).collect(Collectors.toList());
				}
			}
		}

		return itemStatsList;
	}

	@Override
	public List<CategoryStatsSummary> getCategoryStatsList(StatisticsParam statisticsParam) {
		statisticsParam.setExtra("CATEGORY");

		return shopStatisticsMapper.getCategoryStatsList(statisticsParam)
				.stream().sorted(Comparator.comparing((CategoryStatsSummary::getSubTotalAmount), Comparator.reverseOrder())).collect(Collectors.toList());
	}

	@Override
	public List<AreaStatsSummary> getAreaStatsList(StatisticsParam statisticsParam) {
		statisticsParam.setExtra("AREA");

		List<AreaStatsSummary> areaStatsList = shopStatisticsMapper.getAreaStatsList(statisticsParam);

		if (!StringUtils.isEmpty(statisticsParam.getOrderBy())) {
			if ("QUANTITY".equals(statisticsParam.getOrderBy())) {
				if ("ASC".equals(statisticsParam.getSort())) {
					areaStatsList = areaStatsList.stream().sorted(Comparator.comparing((AreaStatsSummary::getSubTotalCount))).collect(Collectors.toList());
				} else {
					areaStatsList = areaStatsList.stream().sorted(Comparator.comparing((AreaStatsSummary::getSubTotalCount), Comparator.reverseOrder())).collect(Collectors.toList());
				}
			} else {
				if ("ASC".equals(statisticsParam.getSort())) {
					areaStatsList = areaStatsList.stream().sorted(Comparator.comparing((AreaStatsSummary::getSubTotalAmount))).collect(Collectors.toList());
				} else {
					areaStatsList = areaStatsList.stream().sorted(Comparator.comparing((AreaStatsSummary::getSubTotalAmount), Comparator.reverseOrder())).collect(Collectors.toList());
				}
			}
		}

		return areaStatsList;
	}
}
