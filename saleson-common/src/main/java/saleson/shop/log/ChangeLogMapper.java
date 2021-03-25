package saleson.shop.log;

import com.onlinepowers.framework.orm.mybatis.annotation.Mapper;
import saleson.shop.log.domain.ChangeLog;

@Mapper("changeLogMapper")
public interface ChangeLogMapper {

    void insertUserChangeLog(ChangeLog changelog);

    void insertManagerChangeLog(ChangeLog changeLog);

}
