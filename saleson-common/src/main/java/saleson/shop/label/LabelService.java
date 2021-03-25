package saleson.shop.label;

import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;
import saleson.model.Label;
import saleson.shop.item.support.ItemParam;
import saleson.shop.label.support.LabelDto;

import java.util.List;
import java.util.Optional;

public interface LabelService {

    /**
     * 라벨 목록 조회
     * @param predicate
     * @param pageable
     * @return
     */
    Page<Label> findAll(Predicate predicate, Pageable pageable);

    /**
     * 라벨 목록 조회 (전체)
     * @param predicate
     * @return
     */
    List<Label> findAll(Predicate predicate);

    /**
     * 라벨 상세정보 조회
     * @param id
     * @return
     */
    Optional<Label> findById(Long id);

    /**
     * 라벨 정보 저장
     * @param label
     * @param itemParam
     * @throws Exception
     */
    void save(Label label, ItemParam itemParam) throws Exception;

    /**
     * 라벨 정보 삭제
     * @param idArray
     * @throws Exception
     */
    void deleteByIds(String[] idArray) throws Exception;

    /**
     * 라벨 이미지 삭제
     * @param label
     */
    void deleteImage(Label label);

    /**
     * 라벨 Json Value 생성
     * @param idArray
     * @return
     */
    String getJsonValue(String[] idArray);
    String getJsonValue(Label label);
}
