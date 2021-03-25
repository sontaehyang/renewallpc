package saleson.shop.code;

import saleson.shop.code.domain.Code;
import saleson.shop.code.domain.CodeType;
import saleson.shop.code.support.CodeParam;

import java.util.List;
import java.util.Map;


public interface CodeService {

	/**
	 * CODE_TYPE 수량 조회
	 * @param codeParam
	 * @return
	 */
	int getCodeTypeCount(CodeParam codeParam);

	/**
	 * CODE 수량 조회
	 * @param codeParam
	 * @return
	 */
	int getCodeCount(CodeParam codeParam);

	/**
	 * CodeType 리스트 조회
	 * @param codeParam
	 * @return
	 */
	List<CodeType> getCodeTypeList(CodeParam codeParam);
	/**
	 * CODE 리스트 조회
	 * @param codeParam
	 * @return
	 */
	List<Code> getCodeList(CodeParam codeParam);

	/**
	 * Code 수정
	 * @param params
	 * @return
	 */
	Code getCodeById(Map<String, Object> params);
	
	/**
	 * CODE 공통코드 등록
	 * @param code
	 */
	void insertCode(Code code);
	
	/**
	 * CODE 수정처리
	 * @param code
	 */
	void updateCode(Code code);
	
	/**
	 * CODE 삭제
	 * @param code
	 */
	void deleteCode(Code code);

	/**
	 * Code Reload
	 * @throws Exception
	 */
	void reload();
	
	/**
	 * CODE 자식 목록 조회
	 * @param codeParam
	 * @return
	 */
	public List<Code> getCodeChildList(CodeParam codeParam);

}
