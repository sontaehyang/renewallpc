package saleson.shop.mall.est.domain;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="ResultOrder")
@XmlAccessorType (XmlAccessType.FIELD)
public class ResultOrder {
	
	@XmlElement(name="result_code")
	private String resultCode;
	
	@XmlElement(name="result_text")
	private String resultText;
	
	public String getResultCode() {
		return resultCode;
	}
	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}
	public String getResultText() {
		return resultText;
	}
	public void setResultText(String resultText) {
		this.resultText = resultText;
	}

	

}
