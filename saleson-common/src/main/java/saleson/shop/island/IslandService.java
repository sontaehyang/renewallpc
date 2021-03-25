package saleson.shop.island;

import com.onlinepowers.framework.web.domain.ListParam;
import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import saleson.model.Island;

import java.util.Optional;

public interface IslandService {

	int getSellerCount(IslandDto islandDto);

	// jpa query start
	/**
	 * 관리자 제주/도서산간 리스트 검색
	 * @param predicate
	 * @param pageable
	 * @return
	 */
	Page<Island> findAll(Predicate predicate, Pageable pageable);

	/**
	 * 관리자 제주/도서산간 등록
	 * @param island
	 */
	void save(Island island);

	/**
	 * 관리자 제주/도서산간 특정 아이디 검색
	 * @param id
	 * @return
	 */
	Optional<Island> findById(Long id);

	/**
	 * 관리자 제주/도서산간 특정 아이디 삭제
	 * @param listParam
	 */
	void deleteById(ListParam listParam);
	// jpa query end

}
