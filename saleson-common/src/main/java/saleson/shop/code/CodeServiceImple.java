package saleson.shop.code;

import com.onlinepowers.framework.repository.support.EarlyLoadingCodeInfoRepository;
import com.onlinepowers.framework.repository.support.EarlyLoadingRepositoryEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import saleson.shop.code.domain.Code;
import saleson.shop.code.domain.CodeType;
import saleson.shop.code.support.CodeParam;

import java.util.List;
import java.util.Map;


@Service("codeService1")
public class CodeServiceImple implements CodeService {

	@Autowired
	private CodeMapper codeMapper;

	@Autowired
	private EarlyLoadingCodeInfoRepository codeInfoRepository;

	@Override
	public int getCodeTypeCount(CodeParam codeParam) {
		return codeMapper.getCodeTypeCount(codeParam);
	}

	@Override
	public int getCodeCount(CodeParam codeParam) {
		return codeMapper.getCodeCount(codeParam);
	}


	@Override
	public List<CodeType> getCodeTypeList(CodeParam codeParam) {
		return codeMapper.getCodeTypeList(codeParam);
	}
	
	@Override
	public List<Code> getCodeList(CodeParam codeParam) {
		return codeMapper.getCodeList(codeParam);
	}

	@Override
	public Code getCodeById(Map<String, Object> params) {
		return codeMapper.getCodeById(params);
	}

	@Override
	public void insertCode(Code code) {
		codeMapper.insertCode(code);
		reload();
	}
	
	@Override
	public void updateCode(Code code) {
		codeMapper.updateCode(code);
		reload();
	}
	
	@Override
	public void deleteCode(Code code) {
		codeMapper.deleteCode(code);
		reload();
	}

	@Override
	public void reload() {
		// Code reload
		EarlyLoadingRepositoryEvent codeReloadEvent = new EarlyLoadingRepositoryEvent("codeInfoRepository",EarlyLoadingRepositoryEvent.Action.ReloadAll);
		codeInfoRepository.onApplicationEvent(codeReloadEvent);
	}

	@Override
	public List<Code> getCodeChildList(CodeParam codeParam) {
		return codeMapper.getCodeChildList(codeParam);
	}

}
