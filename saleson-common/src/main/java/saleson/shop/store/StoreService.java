package saleson.shop.store;

import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import saleson.model.Store;

import java.util.Optional;

public interface StoreService {

    /**
     * 판매처 목록 조회
     * @param predicate
     * @param pageable
     * @return
     */
    Page<Store> findAll(Predicate predicate, Pageable pageable);

    /**
     * 판매처 정보 조회
     * @param id
     * @return
     */
    Optional<Store> findById(Long id);

    /**
     * 판매처 저장
     * @param store
     */
    void save(Store store);

    /**
     * 판매처 삭제
     * @param idArray
     * @throws Exception
     */
    void deleteByIds(String[] idArray) throws Exception;

}
