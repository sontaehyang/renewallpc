package com.onlinepowers.demo;

import java.util.List;
import java.util.concurrent.Future;

import com.onlinepowers.demo.domain.*;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.onlinepowers.framework.file.domain.TempFiles;

public interface DemoService {

	public Survey getSurveyBySurveyId(int surveyId);
	
	public void execute();

	public Future<String> send(String string);

	public void send2(String string);

	public Message makeMessageCode(Message message);

	
	public void updateUserAddress(MultipartFile multipartFile);
	
	@Transactional(propagation=Propagation.NOT_SUPPORTED)
	void insertTransaction();
	
	
	void getTransaction();

	public void insertAllowIp(CommonCode commonCode);

	void deleteAllowIp(CommonCode commonCode);
}
