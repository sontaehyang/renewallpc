package saleson.shop.order.pg.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import saleson.model.ConfigPg;

import java.util.List;

@Service("configPgService")
public class ConfigPgServiceImpl implements ConfigPgService{

    @Autowired
    private ConfigPgRepository configPgRepository;

    @Override
    public void saveConfigPg(ConfigPg configPg) {

        ConfigPg storeConfigPg = getConfigPg();

        if (storeConfigPg == null) {
            storeConfigPg = new ConfigPg();
        }

        storeConfigPg.setUpdateData(configPg);
        configPgRepository.save(storeConfigPg);
    }

    @Override
    public ConfigPg getConfigPg() {

        List<ConfigPg> configPgList = configPgRepository.findAll();

        if (!configPgList.isEmpty()) {
            return configPgList.get(0);
        }

        return null;
    }
}
