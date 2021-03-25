package saleson.shop.faq;

import com.onlinepowers.framework.exception.UserException;
import com.onlinepowers.framework.web.domain.ListParam;
import com.querydsl.core.types.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import saleson.model.Faq;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service("faqService")
public class FaqServiceImpl implements FaqService {

	@Autowired
	private FaqRepository faqRepository;


	@Override
	public Page<Faq> findAll(Predicate predicate, Pageable pageable) {
		return faqRepository.findAll(predicate, pageable);
	}


	@Override
	public Optional<Faq> findById(Long id) {
		return faqRepository.findById(id);
	}

	@Override
	public Faq save(Faq faq) {
		return faqRepository.save(faq);
	}

	@Override
	public void deleteListData(ListParam listParam) {

		if (listParam.getId() == null) {
			throw new UserException("처리할 데이터가 없습니다.");
		}

		// 1. Paramter 설정.
		List<Long> ids = Arrays.stream(listParam.getId())
				.map(i -> Long.parseLong(i))
				.collect(Collectors.toList());

		// 2. 삭제 대상 조회
		List<Faq> faqList = faqRepository.findAllById(ids);


		// 3. 삭제
		faqRepository.deleteAll(faqList);
	}
}
