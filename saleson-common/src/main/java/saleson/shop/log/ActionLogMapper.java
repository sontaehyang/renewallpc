package saleson.shop.log;

import com.onlinepowers.framework.orm.mybatis.annotation.Mapper;
import saleson.shop.log.domain.ActionLog;

@Mapper("actionLogMapper")
public interface ActionLogMapper {

    void insertManagerActionLog(ActionLog actionLog);

}
