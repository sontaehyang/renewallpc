package saleson.shop.item;


import com.onlinepowers.framework.sequence.service.SequenceService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import saleson.common.SalesonTest;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.IntStream;

public class ItemMapperTest extends SalesonTest {

	@Autowired
	ItemMapper itemMapper;

	@Autowired
	@Qualifier("sequenceService")
	SequenceService sequenceService;


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
		ForkJoinPool myPool = new ForkJoinPool(100);
		List<Long> ids = new ArrayList<>();
		myPool.submit(() -> {
			IntStream.range(0, 10000).parallel().forEach(i -> {
				ids.add(sequenceService.getLong("DUP5"));
			/*itemMapper.mergeItemHits(1);
			System.out.println("Starting " + Thread.currentThread().getName() + ",    index=" + i + ", " + LocalDate.now());
*/
			});
		}).get();

		ids.stream().forEach(i -> System.out.println(i));

	}
}