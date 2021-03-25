package com.onlinepowers.demo;

import com.onlinepowers.demo.domain.*;
import com.onlinepowers.framework.exception.OpRuntimeException;
import com.onlinepowers.framework.exception.UserException;
import com.onlinepowers.framework.util.FileUtils;
import com.onlinepowers.framework.util.MessageUtils;
import com.onlinepowers.framework.util.PoiUtils;
import com.onlinepowers.framework.util.StringUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import saleson.common.utils.ShopUtils;

import java.io.IOException;
import java.util.concurrent.Future;

@Service("demoService")
public class DemoServiceImpl implements DemoService {
	private static final Logger log = LoggerFactory.getLogger(DemoServiceImpl.class);
	
	@Autowired
	private DemoMapper demoMapper;


	@Override
	public Survey getSurveyBySurveyId(int surveyId) {
		return demoMapper.getSurveyBySurveyId(surveyId);
	}

	@Async
	@Override
	public void execute() {
		for (int i = 0; i < 10; i++) {
			try {
				Thread.sleep(1000);
				demoMapper.getSurveyBySurveyId(1);
				System.out.println("Async TEST");
			} catch (InterruptedException e) {
				log.error(e.getMessage(), e);
				Thread.currentThread().interrupt();
			}
		}
	}


	@Async
	public Future<String> send(String message) {

		long sTime = System.currentTimeMillis();
		System.out.println(System.currentTimeMillis() + " : " + "MessagesSenderImpl.send(String message) start. ");
		for (int i = 0; i < 10; i++) {
			try {
				Thread.sleep(1000);
				//demoMapper.getSurveyBySurveyId(1);
				System.out.println("Async TEST");
			} catch (InterruptedException e) {
				log.error(e.getMessage(), e);
				Thread.currentThread().interrupt();
			}
		}
		long eTime = System.currentTimeMillis();
		System.out.println(System.currentTimeMillis() + " : " +"MessagesSenderImpl.send(String message) end. ");
		return new AsyncResult<String>("[echo]" + message);
	}

	@Async
	public void send2(String message) {

		long sTime = System.currentTimeMillis();
		System.out.println(System.currentTimeMillis() + " : " + "MessagesSenderImpl.send2(String message) start. ");
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			log.error(e.getMessage(), e);
			Thread.currentThread().interrupt();
		}
		long eTime = System.currentTimeMillis();
		System.out.println(System.currentTimeMillis() + " : " + "MessagesSenderImpl.send2(String message) end. ");
	}

	@Override
	public Message makeMessageCode(Message message) {
		
		String newLine = System.getProperty("line.separator");
		
		String[] messages = message.getContent().split(newLine);
		
		String result = "";
		String resultController = "";
		String resultScript = "";
		
		for (String string : messages) {
			String msg = string.trim();
			
			if (!msg.equals("")) {
				String code = demoMapper.getMessage(msg);
				
				if (code == null) {
					Demo demo = new Demo();
					demo.setEmail(msg);
					
					demoMapper.insertMessage(demo);
					code = demo.getContent();
				
				}
				
				result += "${op:message('" + code + "')} &lt%-- " + msg + " --%&gt;" + newLine;
				
				
				
				resultController += "MessageUtils.getMessage(\"" + code + "\");	// " + msg + newLine;
				resultScript += "alert(Message.get(\"" + code + "\"));	// " + msg + newLine;
				//resultScript += "Message.get(\"" + code + "\");	// " + msg + newLine;
				
			} else {
				result += newLine;
				resultController += newLine;
				resultScript += newLine;
			}

		}
		
		
		message.setResult(result + "|" + resultController  + "|" + resultScript);

		return message;
	}

	@Override
	public void updateUserAddress(MultipartFile multipartFile) {
		if (multipartFile == null) {
			throw new UserException(MessageUtils.getMessage("M01532")); // 파일을 선택해 주세요. 
		}
		
		String fileName = multipartFile.getOriginalFilename();
		String fileExtension = FileUtils.getExtension(fileName);
		
		// 확장자 체크
		if (!(fileExtension.equalsIgnoreCase("xlsx"))) {
			throw new UserException(MessageUtils.getMessage("M01533"));	// 엑셀 파일(.xlsx)만 업로드가 가능합니다. 
		}
		
		// 용량체크 
		String maxUploadFileSize = "20";
		Long maxUploadSize = Long.parseLong(maxUploadFileSize) * 1000 * 1000;
		
		if (multipartFile.getSize() > maxUploadSize) {
			throw new UserException("Maximum upload file Size : " + maxUploadFileSize + "MB");
		}
		
		XSSFWorkbook wb = null;

		try {
			wb = new XSSFWorkbook(multipartFile.getInputStream());
			
			for (Row row : wb.getSheetAt(0)) {
				
				if (row.getRowNum() < 1) {
		    		continue;
		    	}
		    	
		    	// 해당 로우의 셀 값이 전부 비어있는 경우는 SKIP
		    	if (PoiUtils.isEmptyAllCell(row)) {
		    		continue;
		    	}
		    	
		    	String email = ShopUtils.getString(row.getCell(PoiUtils.convertCellStringToIndex("B")));
		    	
		    	UserAddress userAddress = new UserAddress();
		    	userAddress.setEmail(email);
		    	
		    	String fullAddress = ShopUtils.getString(row.getCell(PoiUtils.convertCellStringToIndex("E")));
		    	String sido = ShopUtils.getSido(fullAddress);
		    	String sigungu = ShopUtils.getSigungu(fullAddress);
		    	String eupmyeondong = ShopUtils.getEupmyeondong(fullAddress);
		    	String dongri = ShopUtils.getDongri(fullAddress);
		    	
		    	String address = sido;
		    	if (StringUtils.isNotEmpty(sigungu)) {
		    		address += " " + sigungu;
		    	}
		    	
		    	if (StringUtils.isNotEmpty(eupmyeondong)) {
		    		address += " " + eupmyeondong;
		    	}
		    	
		    	if (StringUtils.isNotEmpty(dongri)) {
		    		address += " " + dongri;
		    	}

		    	String addressDetail = fullAddress.replace(address, "").trim();
		    	
		    	userAddress.setAddress(address.trim());
		    	userAddress.setAddressDetail(addressDetail);
		    	
		    	demoMapper.updateUserAddress(userAddress);
			}
			
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}
	
	@Transactional(propagation=Propagation.NOT_SUPPORTED)
	public void insertTransaction() {
		demoMapper.tran1();
		demoMapper.tran2();
		tran3();
		
	}
	
	
	public void getTransaction() {
		demoMapper.tran1();
		demoMapper.tran2();
		tran3();
		
	}
	
	public void tran3() {
		demoMapper.tran3();
	}

	@Override
	public void insertAllowIp(CommonCode commonCode) {
		try {
			demoMapper.insertAllowIp(commonCode);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new OpRuntimeException("이미 등록된 아이피입니다.");
		}
	}

	@Override
	public void deleteAllowIp(CommonCode commonCode) {
		demoMapper.deleteAllowIp(commonCode);
	}
}
