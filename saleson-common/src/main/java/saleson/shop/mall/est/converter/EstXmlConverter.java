package saleson.shop.mall.est.converter;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.transform.stream.StreamSource;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

import com.onlinepowers.framework.common.ServiceType;
import com.onlinepowers.framework.notification.exception.NotificationException;

public class EstXmlConverter {

	public Object convertXmlUrlToObject(String url, String apiKey, Class<?>... classesToBeBound) {
		
		Jaxb2Marshaller unmarshaller = new Jaxb2Marshaller();
		unmarshaller.setClassesToBeBound(classesToBeBound);
		
		if (ServiceType.LOCAL == true) {
			//url = url.replace("https://", "http://");
		}
		
		HttpGet httpGet = new HttpGet(url);

        httpGet.setHeader("openapikey", apiKey);

        
        try (CloseableHttpClient httpClient = HttpClients.createDefault()){

        	HttpResponse response = httpClient.execute(httpGet);
        	int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode != 200) {
				return null;
			}
        	
            HttpEntity entity = response.getEntity();
            
            
            if (entity != null) {
	            InputStream inputStream = null;
	            
	            try {
	
	            	//String responseBody = EntityUtils.toString(response.getEntity(), "UTF-8");	
	            	//System.out.println(responseBody + "\n" + statusCode);	
	            	
	            	inputStream = entity.getContent();
	            	return unmarshaller.unmarshal(new StreamSource(inputStream));
	            	
	            } catch (IOException ex) {
	            	throw new NotificationException("검색 결과 파싱 오류", ex);
	
	            } catch (RuntimeException ex) {
	            	httpGet.abort();
	            	
	            	// throw ex;
	            	throw new NotificationException("검색 결과 파싱 오류", ex);
	
	            } finally {
	            	if (inputStream != null) {
	            		try {
	            			inputStream.close();
						} catch (IOException e) {
							
						}
	            	}
	            }
	            
	        }
	        
	        return null;
		} catch (Exception e) {
			throw new NotificationException("검색 결과 파싱 오류", e);
		}
		
	}
}
