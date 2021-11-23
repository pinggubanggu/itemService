package hello.itemservice2.domain.item;

import static org.assertj.core.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class JdbcTemplateItemRepositoryTest {

  @Autowired ItemService itemService;
  @Autowired ItemRepository itemRepository;

  @Test
  public void 상품_저장() {

    //given
    Item item = new Item();
    item.setItemName("장난감");
    item.setPrice(20000);
    item.setQuantity(10);
    item.setOpen(true);

    List<String> region = new ArrayList<>();
    region.add("SEOUL");

    item.setRegions(region);
    item.setItemType(new TypeItem("BOOK"));
    item.setDeliveryCode(new DeliveryCode("FAST"));

    //when
    Item saveItem = itemRepository.save(item);

    //then
    assertThat(saveItem).isEqualTo(itemRepository.findById(saveItem.getId()));
  }

  @Test
  public void 상품_수정() {
    //given
    Item item = new Item();
    item.setItemName("장난감");
    item.setPrice(20000);
    item.setQuantity(10);
    item.setOpen(true);

    List<String> region = new ArrayList<>();
    region.add("SEOUL");

    item.setRegions(region);
    item.setItemType(new TypeItem("BOOK"));
    item.setDeliveryCode(new DeliveryCode("FAST"));

    Item saveItem = itemRepository.save(item);
    saveItem.setItemName("곰인형");

    //when
    itemRepository.update(saveItem.getId(), saveItem);

    //then
    Item updateItem = itemRepository.findById(saveItem.getId());
    assertThat(updateItem.getItemName()).isEqualTo("곰인형");
  }

}
