package saleson.user.domain;

import com.onlinepowers.framework.orm.mybatis.annotation.Mapper;

@Mapper
public interface MigrationMapper {
	long getMaxUserId();

	void insertUser();

	void insertUserDetail();

	void insertUserRole();

	void insertPoint();

	void insertUserSns();

	void deleteUser();

	void deleteUserRole();

	void deletePoint();

	void deleteUserSns();
}

