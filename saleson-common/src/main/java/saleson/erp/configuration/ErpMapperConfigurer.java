package saleson.erp.configuration;

import org.mybatis.spring.mapper.MapperScannerConfigurer;
import saleson.erp.configuration.annotation.MapperErp;

public class ErpMapperConfigurer extends MapperScannerConfigurer {

    public ErpMapperConfigurer() {
        super.setAnnotationClass(MapperErp.class);
        super.setSqlSessionFactoryBeanName("erpSqlSessionFactory");
    }
}
