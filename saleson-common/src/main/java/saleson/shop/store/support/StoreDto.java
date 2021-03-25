package saleson.shop.store.support;

import com.onlinepowers.framework.util.StringUtils;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.Expressions;
import lombok.*;
import saleson.common.enumeration.StoreType;
import saleson.common.web.Param;
import saleson.model.QStore;

import javax.validation.constraints.NotEmpty;
import java.util.stream.Stream;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class StoreDto extends Param {

    @NotEmpty
    private String name;

    private StoreType storeType;

    @NotEmpty
    private String sido;

    private String newPost;
    private String post;

    @NotEmpty
    private String address;

    private String addressDetail;

    private String telNumber1;
    private String telNumber2;
    private String telNumber3;
    private String telNumber;

    private String startTime;
    private String endTime;

    public String getTelNumber() {
        this.telNumber = this.telNumber1 + '-' + this.telNumber2 + '-' + this.telNumber3;
        return telNumber;
    }
    public void setTelNumber(String telNumber) {
        String[] telNumbers = StringUtils.delimitedListToStringArray(telNumber, "-");

        this.telNumber1 = telNumbers.length > 0 ? telNumbers[0] : "";
        this.telNumber2 = telNumbers.length > 1 ? telNumbers[1] : "";
        this.telNumber3 = telNumbers.length > 2 ? telNumbers[2] : "";

        this.telNumber = telNumber;
    }

    public Predicate getPredicate() {
        QStore store = QStore.store;
        BooleanBuilder builder = new BooleanBuilder();

        if (getQuery() != null && !getQuery().isEmpty()) {
            if ("NAME".equalsIgnoreCase(getWhere())) {
                builder.and(store.name.contains(getQuery()));
            } else if ("ADDRESS".equalsIgnoreCase(getWhere())) {
                builder.and(store.address.contains(getQuery()));
            } else if ("TEL_NUMBER".equalsIgnoreCase(getWhere())) {
                builder.and(Expressions.stringTemplate("REPLACE({0}, '-', '')", store.telNumber).contains(getQuery().replace("-", "")));
            }
        }

        if (!StringUtils.isEmpty(getSido())) {
            builder.and(store.sido.eq(getSido()));
        }

        if (getStoreType() != null) {
            Stream.of(StoreType.values())
                    .filter(storeType -> storeType == getStoreType())
                    .forEach(storeType ->
                            builder.and(store.storeType.eq(storeType))
                    );
        }

        return builder;
    }
}
