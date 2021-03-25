package saleson.shop.mailconfig;

import java.util.ArrayList;

import saleson.shop.mailconfig.domain.MailConfig;
import saleson.shop.mailconfig.support.MailConfigSearchParam;

import com.onlinepowers.framework.orm.mybatis.annotation.Mapper;

@Mapper("mailConfigMapper")
public interface MailConfigMapper {
	
	/**
	 * 메일설정 관리 카운터
	 * @param mailConfigSearchParam
	 * @return
	 */
	int getMailConfigCount(MailConfigSearchParam mailConfigSearchParam);
	
	/**
	 * 메일설정 관리 리스트
	 * @param mailConfigSearchParam
	 * @return
	 */
	ArrayList<MailConfig> getMailConfigList(MailConfigSearchParam mailConfigSearchParam);
	
	/**
	 * 메일설정 관리 상세정보
	 * @param mailConfigSearchParam
	 * @return
	 */
	MailConfig getMailConfigDetailsById(MailConfigSearchParam mailConfigSearchParam);
	
	/**
	 * 템플릿 ID로 메일 설정 상세정보 조회
	 * @param templateId
	 * @return
	 */
	MailConfig getMailConfigByTemplateId(String templateId);
	
	/**
	 * 메일설정 관리 등록
	 * @param mailConfig
	 */
	void insertMailConfig(MailConfig mailConfig);
	
	/**
	 * 메일설정 관리 수정
	 * @param mailConfig
	 */
	void updateMailConfigById(MailConfig mailConfig);
	
	/**
	 * 메일설정 관리 삭제
	 * @param mailConfig
	 */
	void deleteMailConfigById(MailConfig mailConfig);
	
}
