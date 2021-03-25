package saleson.shop.mall.auction.domain;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="GetShippingDetailResult", namespace="http://www.auction.co.kr/APIv1/AuctionService")
@XmlAccessorType(XmlAccessType.FIELD)
public class GetShippingDetailResult {

	@XmlAttribute(name="OptionContent")
	private String optionContent;
	
	@XmlAttribute(name="BundleDeliveryNo")
	private String bundleDeliveryNo;
	
	@XmlAttribute(name="Remark")
	private String remark;
	
	@XmlAttribute(name="ReturnApproveDate")
	private String returnApproveDate;
	
	@XmlAttribute(name="AddressRoadName")
	private String addressRoadName;
	
	@XmlElement(name="Order", namespace="http://schema.auction.co.kr/Arche.APISvc.xsd")
	private Order order;
	
	@XmlElement(name="Buyer", namespace="http://schema.auction.co.kr/Arche.APISvc.xsd")
	private Buyer buyer;
	
	@XmlElement(name="Reciever", namespace="http://schema.auction.co.kr/Arche.APISvc.xsd")
	private Reciever reciever;
	
	@XmlElement(name="TransFee", namespace="http://schema.auction.co.kr/Arche.APISvc.xsd")
	private TransFee transFee;
	
	@XmlElement(name="Processes", namespace="http://schema.auction.co.kr/Arche.APISvc.xsd")
	private List<Processes> processes;
	
	@XmlElement(name="ProdOption", namespace="http://schema.auction.co.kr/Arche.APISvc.xsd")
	private List<ProdOption> prodOption;
	
	@XmlElement(name="PaymentExchange", namespace="http://schema.auction.co.kr/Arche.APISvc.xsd")
	private List<PaymentExchange> paymentExchange;

	public String getOptionContent() {
		return optionContent;
	}

	public void setOptionContent(String optionContent) {
		this.optionContent = optionContent;
	}

	public String getBundleDeliveryNo() {
		return bundleDeliveryNo;
	}

	public void setBundleDeliveryNo(String bundleDeliveryNo) {
		this.bundleDeliveryNo = bundleDeliveryNo;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getReturnApproveDate() {
		return returnApproveDate;
	}

	public void setReturnApproveDate(String returnApproveDate) {
		this.returnApproveDate = returnApproveDate;
	}

	public String getAddressRoadName() {
		return addressRoadName;
	}

	public void setAddressRoadName(String addressRoadName) {
		this.addressRoadName = addressRoadName;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public Buyer getBuyer() {
		return buyer;
	}

	public void setBuyer(Buyer buyer) {
		this.buyer = buyer;
	}

	public Reciever getReciever() {
		return reciever;
	}

	public void setReciever(Reciever reciever) {
		this.reciever = reciever;
	}

	public TransFee getTransFee() {
		return transFee;
	}

	public void setTransFee(TransFee transFee) {
		this.transFee = transFee;
	}

	public List<Processes> getProcesses() {
		return processes;
	}

	public void setProcesses(List<Processes> processes) {
		this.processes = processes;
	}

	public List<ProdOption> getProdOption() {
		return prodOption;
	}

	public void setProdOption(List<ProdOption> prodOption) {
		this.prodOption = prodOption;
	}

	public List<PaymentExchange> getPaymentExchange() {
		return paymentExchange;
	}

	public void setPaymentExchange(List<PaymentExchange> paymentExchange) {
		this.paymentExchange = paymentExchange;
	}
}
