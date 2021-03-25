package saleson.shop.mall.auction.converter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import saleson.shop.mall.support.MallException;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.soap.*;
import javax.xml.transform.dom.DOMSource;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class AuctionXmlConverter {
	private static final Logger log = LoggerFactory.getLogger(AuctionXmlConverter.class);

	private String apiUrl;
	private String soapUrl;
	private String serviceUrl;
	private String auctionSiteUrl = "http://www.auction.co.kr"; // 옥션 사이트 Url
	
	public AuctionXmlConverter() {
		
		this.apiUrl = "https://api.auction.co.kr";
		this.soapUrl = this.apiUrl + "/APIv1/AuctionService.asmx";
		this.serviceUrl = this.auctionSiteUrl + "/APIv1/AuctionService";
		
	}
	
	public Object convertXmlUrlToObject(SOAPMessage message, String function, Class<?> classesToBeBound) {
		return convertXmlUrlToObject(message, function, function + "Response", classesToBeBound);
	}
	
	public Object convertXmlUrlToObject(SOAPMessage message, String function, String elementName, Class<?> classesToBeBound) {
		SOAPConnection soapConnection = null;

		// Create SOAP Connection
		try {
			// Create SOAP Connection
	        SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
	        soapConnection = soapConnectionFactory.createConnection();
	
	        // Send SOAP Message to SOAP Server
	        SOAPMessage soapResponse = soapConnection.call(message, this.soapUrl);
	        soapConnection.close();
	        
	        ByteArrayOutputStream stream = new ByteArrayOutputStream(); 
	        soapResponse.writeTo(stream); 
	        String string = new String(stream.toByteArray(), "utf-8"); 

	        System.out.println("string-->" + string);
	        
	        DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
			// disable external entities
			domFactory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
			domFactory.setNamespaceAware(false);

	        //domFactory.setNamespaceAware(true);
	        
	        DocumentBuilder documentBuilder = domFactory.newDocumentBuilder();
	        Document document = documentBuilder.parse(new InputSource(new ByteArrayInputStream(string.getBytes("utf-8"))));
	        NodeList nodeList = document.getElementsByTagName(elementName);
	        
	        if (nodeList == null) {
	        	throw new MallException();
	        }
	        
	        Node node = nodeList.item(0);
	        if (node == null) {
	        	throw new MallException();
	        }
	        
	        if (classesToBeBound == null) {
	        	return null;
	        }

	        DOMSource source = new DOMSource(node);

	        JAXBContext jc = JAXBContext.newInstance(classesToBeBound);
	        Unmarshaller unmarshaller = jc.createUnmarshaller();
	        return unmarshaller.unmarshal(source);

		} catch (UnsupportedOperationException e) {
			log.error("UnsupportedOperationException : {}", e.getMessage());
		} catch (SOAPException e) {
			log.error("SOAPException : {}", e.getMessage());
		} catch (IOException e) {
			log.error("IOException : {}", e.getMessage());
		} catch (ParserConfigurationException e) {
			log.error("ParserConfigurationException : {}", e.getMessage());
		} catch (JAXBException e) {
			log.error("JAXBException : {}", e.getMessage());
		} catch(Exception e) {
			log.error("ERROR: {}", e.getMessage(), e);
		} finally {
			if (soapConnection != null) {
				try {
					soapConnection.close();
				} catch (SOAPException e) {
					log.error("soapConnection.close() : {}", e.getMessage());
				}
			}
		}
		
		return null;
	}
	
	/**
	 * Soap Request Message 생성
	 * @param apiKey
	 * @param function
	 * @return
	 * @throws Exception
	 */
	public SOAPMessage getDefaultSoapMessage(String apiKey, String function) throws Exception {
		MessageFactory messageFactory = MessageFactory.newInstance();
        SOAPMessage soapMessage = messageFactory.createMessage();
        SOAPPart soapPart = soapMessage.getSOAPPart();

        // SOAP Envelope
        SOAPEnvelope envelope = soapPart.getEnvelope();
        envelope.addNamespaceDeclaration("soap", "http://schemas.xmlsoap.org/soap/envelope/");

        // SOAP Action
        MimeHeaders headers = soapMessage.getMimeHeaders();
        headers.addHeader("SOAPAction", this.serviceUrl + "/" + function);
        
        // 인증토큰 셋팅
        makeEncryptedTicket(soapMessage.getSOAPHeader(), apiKey);
        
        soapMessage.saveChanges();
        return soapMessage;
	}
	
	/**
	 * Soap Body req를 생성
	 * @param body
	 * @param function
	 * @return
	 * @throws SOAPException
	 */
	public SOAPElement getBodyReqElement(SOAPBody body, String function) throws SOAPException {
        SOAPElement soapBodyElement = body.addChildElement(createElementName(function, "", this.serviceUrl));
        return soapBodyElement.addChildElement("req");
	}
	
	/**
	 * Soap Body req를 생성
	 * @param body
	 * @param function
	 * @return
	 * @throws SOAPException
	 */
	public SOAPElement getBodyReqElement(SOAPBody body, String function, String elementName) throws SOAPException {
        SOAPElement soapBodyElement = body.addChildElement(createElementName(function, "", this.serviceUrl));
        return soapBodyElement.addChildElement(elementName);
	}
	
	/**
	 * Soap Header에 인증토큰값을 셋팅
	 * @param soapHeader
	 * @param apiKey
	 * @throws SOAPException
	 */
	private void makeEncryptedTicket(SOAPHeader soapHeader, String apiKey) throws SOAPException {
        SOAPHeaderElement element = soapHeader.addHeaderElement(this.createElementName("EncryptedTicket", "", this.auctionSiteUrl + "/Security"));
        element.addChildElement("Value").addTextNode(apiKey);
	}
	
	/**
	 * Element를 생성
	 * @param name
	 * @param prefix
	 * @param value
	 * @return
	 * @throws SOAPException
	 */
	public Name createElementName(String name, String prefix, String value) throws SOAPException {
		SOAPFactory soapFactory = SOAPFactory.newInstance();
        return soapFactory.createName(name, prefix, value);
	}
}
