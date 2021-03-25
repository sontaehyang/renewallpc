package saleson.shop.remittance.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import saleson.common.utils.UserUtils;
import saleson.seller.main.domain.Seller;
import saleson.shop.remittance.support.RemittanceParam;

@SuppressWarnings("serial")
public class Remittance extends Seller {
	private static final Logger log = LoggerFactory.getLogger(Remittance.class);

	public Remittance() {}
	public Remittance(Seller seller, RemittanceParam param, int id) {
		
		setRemittanceId(id);
		
		setSellerId(seller.getSellerId());
		setBankName(seller.getBankName());
		setBankInName(seller.getBankInName());
		setBankAccountNumber(seller.getBankAccountNumber());
		
		setFinishingAmount((int) param.getConfirmAmount());
		setConfirmDate(param.getStartDate());
		
		try {
			setFinishingManagerName(UserUtils.getManagerName());
		} catch(Exception e) {
			log.error("ERROR: {}", e.getMessage(), e);
		}
	}
	
	private int remittanceId;
	private String confirmDate;
	private String finishingDate;
	
	private int finishingAmount;
	private String finishingManagerName;
		
	public int getRemittanceId() {
		return remittanceId;
	}
	public void setRemittanceId(int remittanceId) {
		this.remittanceId = remittanceId;
	}
	public String getFinishingDate() {
		return finishingDate;
	}
	public void setFinishingDate(String finishingDate) {
		this.finishingDate = finishingDate;
	}
	
	public int getFinishingAmount() {
		return finishingAmount;
	}
	public void setFinishingAmount(int finishingAmount) {
		this.finishingAmount = finishingAmount;
	}
	public String getFinishingManagerName() {
		return finishingManagerName;
	}
	public void setFinishingManagerName(String finishingManagerName) {
		this.finishingManagerName = finishingManagerName;
	}
	public String getConfirmDate() {
		return confirmDate;
	}
	public void setConfirmDate(String confirmDate) {
		this.confirmDate = confirmDate;
	}
	
}
