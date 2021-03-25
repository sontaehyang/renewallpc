package saleson.common.mapper.annotation;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface UnifiedMessagingMapper {

    /**
     * The value may indicate a suggestion for a logical mapper name, to be turned into a Spring bean in case of an autodetected component.
     *
     * @return the suggested mapper name, if any
     */
    String value() default "";
}
