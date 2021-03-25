package saleson.shop.log;

import com.onlinepowers.framework.orm.mybatis.annotation.Mapper;

@Mapper("logManageMapper")
public interface LogManageMapper {

    int getTodayLogCount();

    void insertTodayLog();

    void deleteManagerActionLog(String date);

    void deleteQueryLog(String date);

    void deleteManagerChangeLog(String date);

    void deleteUserChangeLog(String date);

    void deleteUserLoginLog(String date);

    void deleteLoginLog(String date);
}
