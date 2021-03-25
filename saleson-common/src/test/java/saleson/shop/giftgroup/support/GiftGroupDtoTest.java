package saleson.shop.giftgroup.support;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
//import org.junit.Test;
import org.modelmapper.ModelMapper;
import saleson.common.enumeration.GiftGroupType;
import saleson.model.GiftGroup;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
public class GiftGroupDtoTest {


	@Test
	public void enumTest() {
		ModelMapper modelMapper = new ModelMapper();
		GiftGroupDto dto = new GiftGroupDto();

		dto.setGroupType(GiftGroupType.ORDER);

		log.info("Group : {}", dto.getGroupType());

		GiftGroup giftGroup = modelMapper.map(dto, GiftGroup.class);

		log.info("Group : {}", giftGroup.getGroupType());

		assertThat(giftGroup.getGroupType()).isEqualTo(GiftGroupType.ORDER);
	}

}