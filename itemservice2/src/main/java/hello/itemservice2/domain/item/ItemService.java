package hello.itemservice2.domain.item;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

// TODO ItemService에 비즈니스 로직 할 것이 있을까?(-)
@Service
public class ItemService {

  private final ItemRepository itemRepository;

  @Autowired
  public ItemService(ItemRepository itemRepository) {
    this.itemRepository = itemRepository;
  }

  /**
   * 상품 저장
   */
  public Long save(Item item) {
    itemRepository.save(item);
    return item.getId();
  }
}
