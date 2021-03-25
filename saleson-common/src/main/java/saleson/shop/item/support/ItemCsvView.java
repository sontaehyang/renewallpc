package saleson.shop.item.support;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.supercsv.cellprocessor.Optional;
import org.supercsv.cellprocessor.ParseInt;
import org.supercsv.cellprocessor.constraint.NotNull;
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.io.ICsvBeanWriter;

import saleson.shop.item.domain.Item;

import com.onlinepowers.framework.web.servlet.view.AbstractSuperCsvView;

public class ItemCsvView extends AbstractSuperCsvView {
	
	public ItemCsvView() {}
	
	public ItemCsvView(String fileName) {
		setFileName(fileName);
	}

	@Override
	public void buildCsvDocument(ICsvBeanWriter beanWriter,
			Map<String, Object> model) throws IOException {
		
		List<Item> items = (List<Item>) model.get("list");

		
        final String[] header = new String[]{"itemUserCode","itemName","salePrice", "itemLabel", "itemType1", "itemType2"};
        
        final String[] title = new String[]{"상품코드(*)","상품명(*)\n1:update\n2:save\n3:delete","판매가격", "상품라벨(*)\n1:신상품\n2:좋은상품", "무료배송\n1:선택\n0:선택안함", "추천상품\n1:선택\n0:선택안함"};
        final CellProcessor[] processors = getProcessors();
         
        // write the header
        beanWriter.writeHeader(header);
        beanWriter.writeHeader(header);
        beanWriter.writeHeader(title);
        beanWriter.writeComment("dasfasdf");
         
        //write the beans data
        
        for (Item item : items) {
        	beanWriter.write(item, header);
		}
	}
	
	
	private static CellProcessor[] getProcessors() {
        
        final CellProcessor[] processors = new CellProcessor[] { 
                new ParseInt(), // ID (must be unique)
                new NotNull(), // Name
                new Optional(), // Role
                new NotNull() // Salary
        };
        return processors;
    }
}
