package saleson.common.opmanager.count;

public class OpmanagerCount {
	private String id;
	private String label;
	private int count;
	
	//지연일
	private String shippingDelay;
	private String exchangeDelay;
	private String returnDelay;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public String getExchangeDelay() {
		return exchangeDelay;
	}
	public void setExchangeDelay(String exchangeDelay) {
		this.exchangeDelay = exchangeDelay;
	}
	public String getReturnDelay() {
		return returnDelay;
	}
	public void setReturnDelay(String returnDelay) {
		this.returnDelay = returnDelay;
	}
	public String getShippingDelay() {
		return shippingDelay;
	}
	public void setShippingDelay(String shippingDelay) {
		this.shippingDelay = shippingDelay;
	}
	
}
