package saleson.shop.order.pg.config;

import org.springframework.cache.annotation.Cacheable;
import saleson.model.ConfigPg;

public interface ConfigPgService {

    void saveConfigPg(ConfigPg configPg);

    ConfigPg getConfigPg();

}
