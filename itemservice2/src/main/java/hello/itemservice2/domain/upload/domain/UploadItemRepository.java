package hello.itemservice2.domain.upload.domain;

import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Repository;

@Repository
public class UploadItemRepository {

  private final Map<Long, Item> store = new HashMap<>();
  private long sequence = 0L;

  public Item save(Item item) {
    item.setId(++sequence);
    store.put(item.getId(), item);
    return item;
  }

  public Item findById(Long id) {
    return store.get(id);
  }
}
