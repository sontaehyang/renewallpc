package saleson.shop.ums.dto;

import com.onlinepowers.framework.util.StringUtils;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import saleson.common.hibenate.converter.BooleanYnConverter;
import saleson.common.web.Param;
import saleson.model.QUms;
import saleson.model.UmsDetail;

import javax.persistence.Convert;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UmsDto extends Param {

	private static final Logger logger = LoggerFactory.getLogger(UmsDto.class);

	private Long id;

	private String templateCode;
	private String templateName;
	@Convert(converter = BooleanYnConverter.class)
	private boolean usedFlag;
	@Convert(converter = BooleanYnConverter.class)
	private boolean nightSendFlag;
	private List<UmsDetail> detailList = new ArrayList<>();

	public Predicate getPredicate() {
		BooleanBuilder builder = new BooleanBuilder();
		QUms ums = QUms.ums;

		if (!StringUtils.isEmpty(getQuery())) {
			logger.debug("ums.templateName: " + ums.templateName);
			if ("templateName".equalsIgnoreCase(getWhere())) {
				builder.and(
						ums.templateName.contains(getQuery())
				);
			}
		}

		return builder;
	}

}
