package saleson.shop.userdelivery;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.onlinepowers.framework.sequence.service.SequenceService;
import com.onlinepowers.framework.util.StringUtils;
import com.onlinepowers.framework.util.ValidationUtils;

import saleson.common.utils.UserUtils;
import saleson.shop.userdelivery.domain.UserDelivery;
import saleson.shop.userdelivery.support.UserDeliveryParam;

@Service("userDeliveryService")
public class UserDeliveryServiceImpl  implements UserDeliveryService {

	@Autowired
	private UserDeliveryMapper userDeliveryMapper;
	
	@Autowired
	private SequenceService sequenceService;
	
	@Override
	public List<UserDelivery> getUserDeliveryList(long userId) {
		
		if (userId == 0) {
			return null;
		}
		
		UserDeliveryParam param = new UserDeliveryParam();
		param.setUserId(userId);
		
		return userDeliveryMapper.getUserDeliveryListByParam(param);
	}
	
	@Override
	public UserDelivery getDefaultUserDelivery() {
		UserDeliveryParam param = new UserDeliveryParam();
		
		if (!UserUtils.isUserLogin()) {
			return null;
		}
		
		param.setUserId(UserUtils.getUserId());
		return userDeliveryMapper.getDefaultUserDelivery(param);
	}
	
	@Override
	public UserDelivery getUserDeliveryById(long userId, int userDeliveryId) {
		UserDeliveryParam param = new UserDeliveryParam();
		
		if (userId == 0) {
			return null;
		}
		
		param.setUserId(userId);
		param.setUserDeliveryId(userDeliveryId);
		return userDeliveryMapper.getUserDeliveryById(param);
	}
	
	@Override
	public void insertUserDelivery(UserDelivery userDelivery) {
		
		if (userDelivery.getUserId() == 0) {
			return;
		}
		
		userDelivery.setUserDeliveryId(sequenceService.getId("OP_USER_DELIVERY"));
		
		
		if (StringUtils.isEmpty(userDelivery.getDefaultFlag())) {
			userDelivery.setDefaultFlag("N");
		} else {
			if ("Y".equals(userDelivery.getDefaultFlag())) {

				// 기본 배송지는 1개만 유지 하기 위해 회원의 전체 데이터를 초기화함
				userDeliveryMapper.initializationDefaultFlag(userDelivery.getUserId());
				
			}
		}
		
		userDeliveryMapper.insertUserDelivery(userDelivery);
	}

	@Override
	public void updateUserDelivery(UserDelivery userDelivery) {
		if (userDelivery.getUserId() == 0) {
			return;
		}
		
		if (StringUtils.isEmpty(userDelivery.getDefaultFlag())) {
			userDelivery.setDefaultFlag("N");
		} else {
			if ("Y".equals(userDelivery.getDefaultFlag())) {

				// 기본 배송지는 1개만 유지 하기 위해 회원의 전체 데이터를 초기화함
				userDeliveryMapper.initializationDefaultFlag(userDelivery.getUserId());
				
			}
		}

		userDeliveryMapper.updateUserDelivery(userDelivery);
	}
	
	@Override
	public void listAction(UserDeliveryParam param) {
		
		if (param.getUserId() == 0) {
			return;
		}
		
		
		if ("del".equals(param.getMode())) {
			
			userDeliveryMapper.deleteUserDeliveryByParam(param);
				
		} else if ("mod".equals(param.getMode())) {
				
			// 기본 배송지는 1개만 유지 하기 위해 회원의 전체 데이터를 초기화함
			userDeliveryMapper.initializationDefaultFlag(param.getUserId());
	
			userDeliveryMapper.updateDefaultFlagByParam(param);

		}
		
	}

	@Override
	public int getDeliveryCount(long userId) {

		UserDeliveryParam param = new UserDeliveryParam();
		param.setUserId(userId);

		return userDeliveryMapper.getDeliveryCount(param);
	}

	@Override
	public List<UserDelivery> getUserApiDeliveryList(UserDeliveryParam param) {
		return userDeliveryMapper.getUserDeliveryListByParam(param);
	}
}
