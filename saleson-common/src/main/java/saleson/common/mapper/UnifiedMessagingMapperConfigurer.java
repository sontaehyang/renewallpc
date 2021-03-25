package saleson.common.mapper;

import com.onlinepowers.framework.orm.mybatis.annotation.Mapper;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import saleson.common.mapper.annotation.UnifiedMessagingMapper;

public class UnifiedMessagingMapperConfigurer extends MapperScannerConfigurer {

    public UnifiedMessagingMapperConfigurer() {
        //super.setAnnotationClass(UnifiedMessagingMapper.class);
        //super.setSqlSessionFactoryBeanName("unifiedMessagingSqlSessionFactory");
        super.setAnnotationClass(Mapper.class);
        super.setSqlSessionFactoryBeanName("sqlSessionFactory");
    }
}
