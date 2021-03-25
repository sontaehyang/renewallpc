package saleson.shop.store;

import com.querydsl.core.types.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import saleson.model.Store;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service("storeService")
public class StoreServiceImpl implements StoreService {

    @Autowired
    private StoreRepository storeRepository;

    @Override
    public Page<Store> findAll(Predicate predicate, Pageable pageable) {
        return storeRepository.findAll(predicate, pageable);
    }

    @Override
    public Optional<Store> findById(Long id) {
        return storeRepository.findById(id);
    }

    @Override
    public void save(Store store) {
        storeRepository.save(store);
    }

    @Override
    public void deleteByIds(String[] idArray) throws Exception {
        if (idArray == null || idArray.length == 0) {
            throw new Exception("잘못된 접근입니다.");
        }

        List<Long> ids = Arrays.stream(idArray).map(i -> Long.parseLong(i)).collect(Collectors.toList());
        storeRepository.deleteByIds(ids);
    }

}
