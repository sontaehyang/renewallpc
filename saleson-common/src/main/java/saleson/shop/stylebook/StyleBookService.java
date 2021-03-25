package saleson.shop.stylebook;

import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import saleson.model.stylebook.StyleBook;

public interface StyleBookService {

    /**
     * 스타일북 등록
     * @param styleBook
     */
    void insertStyleBook(StyleBook styleBook) throws Exception;

    /**
     * 스타일북 수정
     * @param styleBook
     */
    void updateStyleBook(StyleBook styleBook) throws Exception;

    /**
     * 스타일북 삭제
     * @param id
     */
    void deleteStyleBookById(long id) throws Exception;

    /**
     * 스타일북 목록 조회 (페이징)
     * @param predicate
     * @param pageable
     * @return
     */
    Page<StyleBook> getStyleBookList(Predicate predicate, Pageable pageable);

    /**
     * 스타일북 조회
     * @param id
     * @return
     */
    StyleBook getStyleBookById(long id);

    /**
     * 스타일 북의 관련 상품 목록 셋팅
     * @param styleBook
     * @return
     */
    void setItemList(StyleBook styleBook);
}
