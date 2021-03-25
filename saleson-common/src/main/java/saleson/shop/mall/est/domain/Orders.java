package saleson.shop.mall.est.domain;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="orders", namespace="http://skt.tmall.business.openapi.spring.service.client.domain/")
@XmlAccessorType (XmlAccessType.FIELD)
public class Orders {
	
	@XmlElement(name="result_code", required=false, namespace="http://skt.tmall.business.openapi.spring.service.client.domain/")
	private String resultCode;
	
	@XmlElement(name="result_text", required=false, namespace="http://skt.tmall.business.openapi.spring.service.client.domain/")
	private String resultText;

	@XmlElement(name="order", required=false, namespace="http://skt.tmall.business.openapi.spring.service.client.domain/")
	private List<Product> list = new ArrayList<>();

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

	public List<Product> getList() {
		return list;
	}

	public void setList(List<Product> list) {
		this.list = list;
	}
	
	
}
