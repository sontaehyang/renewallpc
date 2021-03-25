package com.onlinepowers.demo;

import com.onlinepowers.demo.domain.CommonCode;
import com.onlinepowers.demo.domain.Demo;
import com.onlinepowers.demo.domain.Survey;
import com.onlinepowers.demo.domain.UserAddress;
import com.onlinepowers.framework.orm.mybatis.annotation.Mapper;

@Mapper("demoMapper")
public interface DemoMapper {

	Survey getSurveyBySurveyId(int surveyId);

	String getMessage(String message);

	void insertMessage(Demo demo);
	
	void updateUserAddress(UserAddress userAddress);
	
	void tran1();
	void tran2();
	void tran3();

	void insertAllowIp(CommonCode commonCode);

	void deleteAllowIp(CommonCode commonCode);
}
