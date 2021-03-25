package saleson.shop.remittance;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import saleson.common.opmanager.count.OpmanagerCount;
import saleson.seller.main.SellerService;
import saleson.seller.main.domain.Seller;
import saleson.shop.order.addpayment.domain.OrderAddPayment;
import saleson.shop.order.domain.OrderItem;
import saleson.shop.order.domain.OrderShipping;
import saleson.shop.remittance.domain.Remittance;
import saleson.shop.remittance.domain.RemittanceConfirm;
import saleson.shop.remittance.domain.RemittanceConfirmDetail;
import saleson.shop.remittance.domain.RemittanceDetail;
import saleson.shop.remittance.domain.RemittanceExpected;
import saleson.shop.remittance.support.EditAddPaymentRemittance;
import saleson.shop.remittance.support.EditItemRemittance;
import saleson.shop.remittance.support.EditShippingRemittance;
import saleson.shop.remittance.support.RemittanceException;
import saleson.shop.remittance.support.RemittanceParam;

import com.onlinepowers.framework.sequence.domain.Sequence;
import com.onlinepowers.framework.sequence.service.SequenceService;
import com.onlinepowers.framework.util.DateUtils;
import com.onlinepowers.framework.web.pagination.Pagination;

@Service("remittanceService")
public class RemittanceServiceImpl implements RemittanceService{

	@Autowired
	private RemittanceMapper remittanceMapper;
	
	@Autowired
	private SellerService sellerService;
	
	@Autowired
	private SequenceService sequenceService;
	
	@Override
	public List<Remittance> getRemittanceFinishingListByParam(RemittanceParam param) {
		int totalCount = remittanceMapper.getRemittanceFinishingCountByParam(param);
		
		Pagination pagination = Pagination.getInstance(totalCount, param.getItemsPerPage());
		param.setPagination(pagination);
		
		return remittanceMapper.getRemittanceFinishingListByParam(param);
	}

	@Override
	public List<RemittanceDetail> getRemittanceFinishingDetailListByParam(RemittanceParam param) {
		
		int totalCount = remittanceMapper.getRemittanceFinishingDetailCountByParam(param);
		
		Pagination pagination = Pagination.getInstance(totalCount, param.getItemsPerPage());
		param.setPagination(pagination);
		
		return remittanceMapper.getRemittanceFinishingDetailListByParam(param);
		
	}
	
	@Override
	public List<RemittanceExpected> getRemittanceExpectedListByParam(RemittanceParam param) {
		
		int totalCount = remittanceMapper.getRemittanceExpectedCountByParam(param);
		
		Pagination pagination = Pagination.getInstance(totalCount, param.getItemsPerPage());
		param.setPagination(pagination);
		
		return remittanceMapper.getRemittanceExpectedListByParam(param);
	}
	
	@Override
	public RemittanceConfirm getRemittanceConfirmByParam(RemittanceParam param) {
		param.setConditionType("DETAIL");
		param.setPagination(null);
		
		List<RemittanceConfirm> list =  remittanceMapper.getRemittanceConfirmListByParam(param);
		if (list == null) {
			return null;
		}
		
		if (list.size() > 1) {
			throw new RemittanceException("");
		}
		
		return list.get(0);
	}
	
	@Override
	public void remittanceFinishingProcess(RemittanceParam param) {
		
		Seller seller = sellerService.getSellerById(param.getSellerId());
		if (seller == null) {
			throw new RemittanceException("");
		}
		
		// 정산 마감 
		int remittanceId = sequenceService.getId("OP_REMITTANCE");
		remittanceMapper.insertRemittanceMaster(new Remittance(seller, param, remittanceId));
		
		// 정산 마감 상세 등록
		param.setRemittanceId(remittanceId);
		param.setConditionType("NO_CANCEL");
		remittanceMapper.insertRemittanceDetail(param);

		
		// 주문 상품정보 마감처리
		remittanceMapper.updateItemRemittanceFinishingByParam(param);
		
		// 배송비 마감 처리
		remittanceMapper.updateShippingRemittanceFinishingByParam(param);
		
		// 배송비 마감 처리
		remittanceMapper.updateAddPaymentRemittanceFinishingByParam(param);
		
		// 마감 정보 금액 검증 - 검증 실패시 마감 롤백
		int amount = remittanceMapper.getRemittanceFinishingAmountValidateByParam(param);
		
		System.out.println("..." + amount);
		System.out.println("..." + (int) param.getConfirmAmount());
		if ((int) param.getConfirmAmount() != amount) {
			throw new RemittanceException("");
		}
		
	}
	
	@Override
	public List<RemittanceConfirmDetail> getRemittanceConfirmDetailListByParam(RemittanceParam param) {
		
		param.setConditionType("DETAIL");
		int totalCount = remittanceMapper.getRemittanceConfirmDetailCountByParam(param);
		
		Pagination pagination = Pagination.getInstance(totalCount, param.getItemsPerPage());
		param.setPagination(pagination);
		
		return remittanceMapper.getRemittanceConfirmDetailListByParam(param);
	}
	
	@Override
	public List<RemittanceConfirm> getRemittanceConfirmListByParam(RemittanceParam param) {
		
		param.setConditionType("LIST");
		
		int totalCount = remittanceMapper.getRemittanceConfirmCountByParam(param);
		
		Pagination pagination = Pagination.getInstance(totalCount, param.getItemsPerPage());
		param.setPagination(pagination);
		
		return remittanceMapper.getRemittanceConfirmListByParam(param);
		
	}
	
	@Override
	public List<OrderItem> getRemittanceItemExpectedDetailListByParam(RemittanceParam param) {
		int totalCount = remittanceMapper.getRemittanceItemExpectedDetailCountByParam(param);
		
		Pagination pagination = Pagination.getInstance(totalCount, param.getItemsPerPage());
		param.setPagination(pagination);
		
		List<OrderItem> list = remittanceMapper.getRemittanceItemExpectedDetailListByParam(param);
		
		return list;
	}
	
	@Override
	public void updateRemittanceItemExpectedForList(RemittanceParam param) {
		String errorUrl = "/opmanager/remittance/expected/list";
		
		if (param.getId() != null) {
			
			for(String key : param.getId()) {
				String[] temp = StringUtils.delimitedListToStringArray(key, "^^^");
				if (temp.length != 3) {
					throw new RemittanceException(errorUrl);
				}
				
				EditItemRemittance edit = param.getEditItemRemittanceMap().get(key);
				if (edit == null) {
					throw new RemittanceException(errorUrl);
				}
				
				edit.setSellerId(Integer.parseInt(temp[0]));
				edit.setStartDate(temp[1]);
				edit.setEndDate(temp[2]);
				
				if (DateUtils.validDate(edit.getRemittanceExpectedDate())) {
					edit.setConditionType("LIST");
					remittanceMapper.updateRemittanceItemExpected(edit);
				}
				
			}
		}
	}
	
	@Override
	public void updateRemittanceShippingExpectedForList(RemittanceParam param) {
		String errorUrl = "/opmanager/remittance/expected/list";
		
		if (param.getId() != null) {
			
			for(String key : param.getId()) {
				
				String[] temp = StringUtils.delimitedListToStringArray(key, "^^^");
				if (temp.length != 3) {
					throw new RemittanceException(errorUrl);
				}
				
				EditItemRemittance editTemp = param.getEditItemRemittanceMap().get(key);
				if (editTemp == null) {
					throw new RemittanceException(errorUrl);
				}
				
				EditShippingRemittance edit = new EditShippingRemittance();
				edit.setRemittanceExpectedDate(editTemp.getRemittanceExpectedDate());
				edit.setSellerId(Integer.parseInt(temp[0]));
				edit.setStartDate(temp[1]);
				edit.setEndDate(temp[2]);
				
				if (DateUtils.validDate(edit.getRemittanceExpectedDate())) {
					edit.setConditionType("LIST");
					remittanceMapper.updateRemittanceShippingExpected(edit);
				}
				
			}
		}
	}
	
	@Override
	public void updateRemittanceAddPaymentExpectedForList(RemittanceParam param) {
		String errorUrl = "/opmanager/remittance/expected/list";
		
		if (param.getId() != null) {
			
			for(String key : param.getId()) {
				
				String[] temp = StringUtils.delimitedListToStringArray(key, "^^^");
				if (temp.length != 3) {
					throw new RemittanceException(errorUrl);
				}
				
				EditItemRemittance editTemp = param.getEditItemRemittanceMap().get(key);
				if (editTemp == null) {
					throw new RemittanceException(errorUrl);
				}
				
				EditAddPaymentRemittance edit = new EditAddPaymentRemittance();
				edit.setRemittanceExpectedDate(editTemp.getRemittanceExpectedDate());
				edit.setSellerId(Integer.parseInt(temp[0]));
				edit.setStartDate(temp[1]);
				edit.setEndDate(temp[2]);
				
				if (DateUtils.validDate(edit.getRemittanceExpectedDate())) {
					edit.setConditionType("LIST");
					remittanceMapper.updateRemittanceAddPaymentExpected(edit);
				}
				
			}
		}
	}
	
	@Override
	public void updateRemittanceShippingExpected(RemittanceParam param) {
		
		String errorUrl = "/opmanager/remittance/expected/detail/shipping/" + param.getSellerId() + "/" + param.getStartDate() + "/" + param.getEndDate();
		
		if (param.getId() != null) {
			
			for(String key : param.getId()) {
				String[] temp = StringUtils.delimitedListToStringArray(key, "^^^");
				if (temp.length != 3) {
					throw new RemittanceException(errorUrl);
				}
				
				EditShippingRemittance edit = param.getEditShippingRemittanceMap().get(key);
				if (edit == null) {
					throw new RemittanceException(errorUrl);
				}
				
				edit.setOrderCode(temp[0]);
				edit.setOrderSequence(Integer.parseInt(temp[1]));
				edit.setShippingSequence(Integer.parseInt(temp[2]));
					
				if (DateUtils.validDate(edit.getRemittanceExpectedDate())) {
					edit.setConditionType(param.getConditionType().toUpperCase());
					remittanceMapper.updateRemittanceShippingExpected(edit);
				}
			
			}
			
		}
		
	}
	
	@Override
	public void updateRemittanceAddPaymentExpected(RemittanceParam param) {
		
		String errorUrl = "/opmanager/remittance/expected/detail/add-payment/" + param.getSellerId() + "/" + param.getStartDate() + "/" + param.getEndDate();
		
		if (param.getId() != null) {
			
			for(String key : param.getId()) {
				
				EditAddPaymentRemittance edit = param.getEditAddPaymentRemittanceMap().get(key);
				if (edit == null) {
					throw new RemittanceException(errorUrl);
				}
				
				edit.setAddPaymentId(Integer.parseInt(key));
					
				if (DateUtils.validDate(edit.getRemittanceExpectedDate())) {
					edit.setConditionType(param.getConditionType().toUpperCase());
					remittanceMapper.updateRemittanceAddPaymentExpected(edit);
				}
			
			}
			
		}
		
	}
	
	@Override
	public void updateRemittanceItemExpected(RemittanceParam param) {
		
		String errorUrl = "/opmanager/remittance/expected/detail/item/" + param.getSellerId() + "/" + param.getStartDate() + "/" + param.getEndDate();
		
		if (param.getId() != null) {
			
			for(String key : param.getId()) {
				String[] temp = StringUtils.delimitedListToStringArray(key, "^^^");
				if (temp.length != 3) {
					throw new RemittanceException(errorUrl);
				}
				
				EditItemRemittance edit = param.getEditItemRemittanceMap().get(key);
				if (edit == null) {
					throw new RemittanceException(errorUrl);
				}
				
				edit.setOrderCode(temp[0]);
				edit.setOrderSequence(Integer.parseInt(temp[1]));
				edit.setItemSequence(Integer.parseInt(temp[2]));
					
				if (DateUtils.validDate(edit.getRemittanceExpectedDate())) {
					edit.setConditionType(param.getConditionType().toUpperCase());
					remittanceMapper.updateRemittanceItemExpected(edit);
				}
			
			}
			
		}
		
	}
	
	@Override
	public List<OrderShipping> getRemittanceShippingExpectedDetailListByParam(RemittanceParam param) {
		int totalCount = remittanceMapper.getRemittanceShippingExpectedDetailCountByParam(param);
		
		Pagination pagination = Pagination.getInstance(totalCount, param.getItemsPerPage());
		param.setPagination(pagination);
		
		List<OrderShipping> list = remittanceMapper.getRemittanceShippingExpectedDetailListByParam(param);
		
		return list;
	}
	
	@Override
	public List<OrderAddPayment> getRemittanceAddPaymentExpectedDetailListByParam(RemittanceParam param) {
		int totalCount = remittanceMapper.getRemittanceAddPaymentExpectedDetailCountByParam(param);
		
		Pagination pagination = Pagination.getInstance(totalCount, param.getItemsPerPage());
		param.setPagination(pagination);
		
		List<OrderAddPayment> list = remittanceMapper.getRemittanceAddPaymentExpectedDetailListByParam(param);
		
		return list;
	}

	@Override
	public List<OpmanagerCount> getOpmanagerRemittanceCountAll(RemittanceParam remittanceParam) {

		return remittanceMapper.getOpmanagerRemittanceCountAll(remittanceParam);
	}
}
