package saleson.shop.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import saleson.common.enumeration.LogType;
import saleson.shop.config.domain.ConfigLog;

import java.util.List;
import java.util.Optional;

@Service
public class ConfigLogService {

    private static final Logger log = LoggerFactory.getLogger(ConfigLogService.class);

    @Autowired
    private ConfigLogRepository configLogRepository;

    public List<ConfigLog> findConfigLogs() throws Exception{
        return configLogRepository.findAll();
    }

    public List<ConfigLog> findConfigLogsByLogType (LogType logType) throws Exception{
        return configLogRepository.findConfigLogsByLogType(logType);
    }

    public void updateAllConfigLogs (List<ConfigLog> list) throws Exception{
        configLogRepository.saveAll(list);
    }

    public void insertConfigLog (ConfigLog configLog) throws Exception{
        configLogRepository.save(configLog);
    }

    public boolean isUsedConfigLogs (LogType logType, String key) {
        try {
            Optional<ConfigLog> optional = configLogRepository.findConfigLogByLogTypeAndKey(logType, key);
            return optional.isPresent() && optional.get().isUsed();
        } catch (Exception e) {
            log.error("ERROR: {}", e.getMessage(), e);
            return false;
        }
    }
}
