package saleson.shop.ums;

import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import saleson.model.Ums;

import java.util.HashMap;
import java.util.Optional;

public interface UmsService {

    /**
     * UMS 설정 조회
     * @param templateCode
     * @return
     */
    Ums getUms(String templateCode);

    /**
     * UMS 대체 코드 조회
     * @param templateCode
     * @return
     */
    HashMap<String, String> getUmsChangeCodes(String templateCode);

    /**
     * UMS 조건 검사
     * @param ums
     * @return
     */
    boolean isValidUms(Ums ums);

    /**
     * 데이터 조회
     * @param id
     * @return
     */
    Optional<Ums> findById(Long id);

    /**
     * 목록 조회
     * @param predicate
     * @param pageable
     * @return
     */
    Page<Ums> findAll(Predicate predicate, Pageable pageable);

    /**
     * 데이터 수정
     * @param ums
     */
    void saveUms(Ums ums);

    /**
     * 데이터 수정 로직
     * @param ums
     */
    void updateUms(Ums ums);
}
