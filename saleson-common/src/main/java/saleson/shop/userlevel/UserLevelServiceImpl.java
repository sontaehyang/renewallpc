package saleson.shop.userlevel;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import saleson.common.Const;
import saleson.shop.user.domain.UserDetail;
import saleson.shop.userlevel.domain.UserLevel;
import saleson.shop.userlevel.domain.UserLevelLog;
import saleson.shop.userlevel.support.UserLevelSearchParam;

import com.onlinepowers.framework.file.service.FileService;
import com.onlinepowers.framework.file.service.strategy.UploadTypeFactory;
import com.onlinepowers.framework.security.userdetails.User;
import com.onlinepowers.framework.sequence.service.SequenceService;
import com.onlinepowers.framework.util.DateUtils;
import com.onlinepowers.framework.util.FileUtils;
import com.onlinepowers.framework.util.StringUtils;

@Service("userLevelService")
public class UserLevelServiceImpl implements UserLevelService {
	private static final Logger log = LoggerFactory.getLogger(UserLevelServiceImpl.class);
	
	@Autowired
	UserLevelMapper userLevelMapper;
	
	@Autowired
	SequenceService sequenceService;
	
	@Autowired
	FileService fileService;

	@Override
	public List<UserLevel> getUserLevelListByGroupCode(String groupCode) {
		return userLevelMapper.getUserLevelListByGroupCode(groupCode);
	}

	@Override
	public int getUserLevelCount(UserLevelSearchParam userLevelSearchParam) {

		return userLevelMapper.getUserLevelCount(userLevelSearchParam);
	}

	@Override
	public List<UserLevel> getUserLevelList(
			UserLevelSearchParam userLevelSearchParam) {

		return userLevelMapper.getUserLevelList(userLevelSearchParam);
	}

	@Override
	public UserLevel getUserLevelDetail(
			UserLevelSearchParam userLevelSearchParam) {

		return userLevelMapper.getUserLevelDetail(userLevelSearchParam);
	}
	
	@Override
	public void insertUserLevel(UserLevel userLevel, MultipartFile[] multipartFiles) {

		
		int sequnce = sequenceService.getId("OP_USER_LEVEL");
		
		for (MultipartFile multipartFile : multipartFiles) {
			if (multipartFile.getSize() > 0) {
				
				String uploadPath = FileUtils.getDefaultUploadPath() + File.separator + "user_level" + File.separator + sequnce;
				fileService.makeUploadPath(uploadPath);
				
				String newFileName = "";
				newFileName = FileUtils.getNewFileName(uploadPath, multipartFile.getOriginalFilename());
		    	userLevel.setFileName(newFileName);
		    	File saveFile = new File(uploadPath + File.separator + newFileName);
		    	
		    	try {
		    		UploadTypeFactory.INSTANCE.getType("").getHandler().upload(multipartFile.getInputStream(), new FileOutputStream(saveFile));
		        } catch (IOException e) {
		            throw new RuntimeException(e);
		        }
			}
		}
		
		
		
		userLevel.setLevelId(sequnce);
		userLevelMapper.insertUserLevel(userLevel);
	}

	@Override
	public void updateUserLevel(UserLevel userLevel,MultipartFile[] multipartFiles) {

		
		for (MultipartFile multipartFile : multipartFiles) {
			if (multipartFile.getSize() > 0) {
				String uploadPath = FileUtils.getDefaultUploadPath() + File.separator + "user_level" + File.separator + userLevel.getLevelId();
				fileService.makeUploadPath(uploadPath);
				
				String newFileName = "";
				newFileName = FileUtils.getNewFileName(uploadPath, multipartFile.getOriginalFilename());
		    	userLevel.setFileName(newFileName);
		    	File saveFile = new File(uploadPath + File.separator + newFileName);
		    	
		    	try {
		    		UploadTypeFactory.INSTANCE.getType("").getHandler().upload(multipartFile.getInputStream(), new FileOutputStream(saveFile));
		        } catch (IOException e) {
		            throw new RuntimeException(e);
		        }
			}
		}
		
		userLevelMapper.updateUserLevel(userLevel);
	}

	@Override
	public void deleteUserLevel(int levelId) {

		userLevelMapper.deleteUserLevel(levelId);
		FileUtils.delete("/user_level/" + levelId);
	}

	@Override
	public void updateUserLevelFileDelete(int levelId) {

		userLevelMapper.updateUserLevelFileDelete(levelId);
		FileUtils.delete("/user_level/" + levelId);
	}

	@Override
	public HashMap<String, List<UserLevel>> getUserLevelGroupList(String groupCode) {
		
		UserLevelSearchParam param = new UserLevelSearchParam();
		if (!StringUtils.isEmpty(groupCode)) {
			param.setGroupCode(groupCode);
		}
		
		List<UserLevel> userLevelList = userLevelMapper.getUserLevelList(param);
		HashMap<String, List<UserLevel>> userLevelMap = new HashMap<String, List<UserLevel>>();
		if (userLevelList != null) {
			for(UserLevel userLevel : userLevelList) {
				if (userLevelMap.get(userLevel.getGroupCode()) == null) {
					List<UserLevel> newList = new ArrayList<>();
					newList.add(userLevel);
					
					userLevelMap.put(userLevel.getGroupCode(), newList);
				} else {
					List<UserLevel> newList = userLevelMap.get(userLevel.getGroupCode());
					newList.add(userLevel);
				}
			}
		}
		
		return userLevelMap;
	}

	@Override
	public void setUserLevel(User user) {
		
		if (user == null) {
			return;
		}
		
		UserDetail userDetail = (UserDetail) user.getUserDetail();
		
		UserLevelSearchParam param = new UserLevelSearchParam();
		param.setUserId(user.getUserId());
		param.setGroupCode(userDetail.getGroupCode());
		
		UserLevel userLevel = userLevelMapper.getLevelToBeSettingToUser(param);
		String expirationDate = "";
		int levelId = 0;
		
		if (userLevel != null) {
			levelId = userLevel.getLevelId();
			userDetail.setGroupCode(userLevel.getGroupCode());
			expirationDate = DateUtils.addMonth(DateUtils.getToday(Const.DATE_FORMAT), userLevel.getRetentionPeriod());
		} else {
			if (userDetail.getLevelId() == 0) {
				return;
			}
		}
		
		userDetail.setLevelId(levelId);
		userDetail.setUserLevelExpirationDate(expirationDate);
		
		if (userLevelMapper.updateUser(userDetail) > 0) {
		
			UserLevelLog log = new UserLevelLog();
			log.setUserId(user.getUserId());
			log.setGroupCode(userDetail.getGroupCode());
			log.setLevelId(levelId);
			log.setLevelName(levelId == 0 ? "등급없음" : userLevel.getLevelName());
			log.setAdminUserName("system");
			userLevelMapper.insertUserLevelLog(log);
		}
		
	}
}
