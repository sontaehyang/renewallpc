package saleson.shop.faq;

import com.onlinepowers.framework.web.domain.ListParam;
import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import saleson.model.Faq;

import java.util.Optional;


public interface FaqService {

	/**
	 * 목록 조회
	 * @param predicate
	 * @param pageable
	 * @return
	 */
	Page<Faq> findAll(Predicate predicate, Pageable pageable);


	/**
	 * 데이터 등록 / 수정
	 * @param faq
	 * @return
	 */
	Faq save(Faq faq);


	/**
	 * 데이터 일괄 삭제
	 * @param listParam
	 */
	void deleteListData(ListParam listParam);

	/**
	 * 데이터 조회
	 * @param id
	 * @return
	 */
	Optional<Faq> findById(Long id);
}
