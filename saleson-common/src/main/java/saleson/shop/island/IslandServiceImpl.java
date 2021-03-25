package saleson.shop.island;

import com.onlinepowers.framework.exception.UserException;
import com.onlinepowers.framework.sequence.service.SequenceService;
import com.onlinepowers.framework.web.domain.ListParam;
import com.querydsl.core.types.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import saleson.common.enumeration.IslandType;
import saleson.model.Island;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service("islandService")
public class IslandServiceImpl implements IslandService {
	@Autowired
	private IslandMapper islandMapper;

	@Autowired
	private SequenceService sequenceService;

	@Autowired
	private IslandRepository islandRepository;

	@Override
	public int getSellerCount(IslandDto islandDto) {
		return islandMapper.getSellerCount(islandDto);
	}

	@Override
	public Page<Island> findAll(Predicate predicate, Pageable pageable) {
		return islandRepository.findAll(predicate, pageable);
	}

	@Override
	public void save(Island island) {
		if (!IslandType.QUICK.equals(island.getIslandType())) {
			island.setExtraCharge(0);
		}

		islandRepository.save(island);
	}

	@Override
	public Optional<Island> findById(Long id) {
		return islandRepository.findById(id);
	}

	@Override
	public void deleteById(ListParam listParam) {
	    if (listParam.getId() == null) {
	        throw new UserException("처리할 데이터가 없습니다.");
        }

        List<Long> ids = Arrays.stream(listParam.getId()).map(i -> Long.parseLong(i)).collect(Collectors.toList());

        List<Island> islandList = islandRepository.findAllById(ids);

		islandRepository.deleteAll(islandList);
	}
}
