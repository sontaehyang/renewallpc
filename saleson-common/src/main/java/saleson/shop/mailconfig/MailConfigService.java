package saleson.shop.mailconfig;

import java.util.ArrayList;
import java.util.HashMap;

import saleson.shop.mailconfig.domain.MailConfig;
import saleson.shop.mailconfig.support.MailConfigSearchParam;




public interface MailConfigService {
	
	/**
	 * 메일 치환코드를 조회
	 * @param templateId
	 * @return
	 */
	public HashMap<String, String> getMailChangeCodes(String templateId);
	
	/**
	 * 메일설정 관리 카운터
	 * @param mailConfigSearchParam
	 * @return
	 */
	public int getMailConfigCount(MailConfigSearchParam mailConfigSearchParam);
	
	/**
	 * 메일설정 관리 리스트
	 * @param mailConfigSearchParam
	 * @return
	 */
	public ArrayList<MailConfig> getMailConfigList(MailConfigSearchParam mailConfigSearchParam);
	
	/**
	 * 메일설정 관리 상세정보
	 * @param mailConfigSearchParam
	 * @return
	 */
	public MailConfig getMailConfigDetailsById(MailConfigSearchParam mailConfigSearchParam);
	
	/**
	 * 템플릿 ID로 메일 설정 상세정보 조회
	 * @param templateId
	 * @return
	 */
	public MailConfig getMailConfigByTemplateId(String templateId);
	
	/**
	 * 메일설정 관리 등록
	 * @param mailConfig
	 */
	public void insertMailConfig(MailConfig mailConfig);
	
	/**
	 * 메일설정 관리 수정
	 * @param mailConfig
	 */
	public void updateMailConfigById(MailConfig mailConfig);
	
	/**
	 * 메일설정 관리 삭제
	 * @param mailConfig
	 */
	public void deleteMailConfigById(MailConfig mailConfig);
	
}
