package saleson.common.userauth;


import com.onlinepowers.framework.orm.mybatis.annotation.Mapper;

import saleson.common.userauth.domain.UserAuth;

@Mapper("userAuthMapper")
public interface UserAuthMapper {

	public UserAuth getUserAuth(UserAuth userAuth);
	
	public int getCountUserAuthTemp(UserAuth userAuth);
	
	public void insertUserAuth(UserAuth userAuth);
	
	public void updateUserAuth(UserAuth userAuth);

}
