package saleson.common;


import com.onlinepowers.framework.sequence.service.SequenceService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import saleson.shop.item.ItemMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
public class SequenceServiceImplTest extends SalesonTest {

	@Autowired
	ItemMapper itemMapper;

	@Autowired
	@Qualifier("sequenceService")
	SequenceService sequenceService;

	@Test
	void test() {
		log.debug("xxxx {}", itemMapper);
	}


	//@Test
	public void mergeItemHitsTest() {
		List<Long> ids = new ArrayList<>();
		IntStream.range(0, 10000).parallel().forEach(i -> {
			ids.add(sequenceService.getLong("DUP"));
			/*itemMapper.mergeItemHits(1);
			System.out.println("Starting " + Thread.currentThread().getName() + ",    index=" + i + ", " + LocalDate.now());
*/
		});

		System.out.println("------- Result --------------------");
		ids.stream().forEach(i -> System.out.println(i));


	}


	//@Test
	public void sequenceTest() throws ExecutionException, InterruptedException {
		ForkJoinPool myPool = new ForkJoinPool(10);
		List<Long> ids = new ArrayList<>();
		myPool.submit(() -> {
			IntStream.range(0, 10000).parallel().forEach(i -> {
				ids.add(sequenceService.getLong("TEST10"));
			});
		}).get();

		boolean isDuplicated = ids.stream().distinct().count() != ids.size();

		assertThat(isDuplicated).isFalse();


		ids.stream().forEach(System.out::println);
	}
}