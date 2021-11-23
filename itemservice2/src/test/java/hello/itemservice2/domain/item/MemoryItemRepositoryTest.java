package hello.itemservice2.domain.item;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

public class MemoryItemRepositoryTest {

  MemoryItemRepository itemRepository = new MemoryItemRepository();

  @AfterEach
  public void afterEach() {
    itemRepository.clearStore();
  }

  @Test
  void Save() {
    //given
    Item item = new Item("spring", 10000, 100);
    //when
    Item saveItem = itemRepository.save(item);
    Item findItem = itemRepository.findById(saveItem.getId());
    //then
    assertThat(findItem).isEqualTo(item);
  }

  @Test
  void findAll() {
    //given
    Item item1 = new Item("spring", 10000, 100);
    Item item2 = new Item("spring2", 20000, 200);
    //when
    Item saveItem1 = itemRepository.save(item1);
    Item saveItem2 = itemRepository.save(item2);
    //then
    assertThat(itemRepository.findAll()).contains(saveItem1);
    assertThat(itemRepository.findAll()).contains(saveItem2);
//    assertThat(itemRepository.findAll()).containsAll(saveItem1, saveItem2); -> 왜 안되는지 확인해보기(-)
  }

  @Test
  void updateItem() {
    //given
    Item item1 = new Item("spring", 10000, 100);
    Item saveItem = itemRepository.save(item1);
    //when
    Item item2 = new Item("spring2", 20000, 200);
    itemRepository.update(saveItem.getId(), item2);
    //then
    Item updateItem = itemRepository.findById(saveItem.getId());
    assertThat(updateItem.getItemName()).isEqualTo(item2.getItemName());
    assertThat(updateItem.getPrice()).isEqualTo(item2.getPrice());
    assertThat(updateItem.getQuantity()).isEqualTo(item2.getQuantity());
  }



}
