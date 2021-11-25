package hello.itemservice2.domain.item;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;

public class MemoryItemRepository implements ItemRepository{

  private static final Map<Long, Item> store = new HashMap<>();
  private static long sequence = 0L;

  public Item save(Item item) {
    item.setId(++sequence);
    store.put(item.getId(), item);
    return item;
  }

  public Item findById(Long id) {
    return store.get(id);
  }

  public List<Item> findAll() {
    return new ArrayList<Item>(store.values());
  }

  public void update(Long itemId, Item updateParam) {
    Item findItem= findById(itemId);
    findItem.setItemName(updateParam.getItemName());
    findItem.setPrice(updateParam.getPrice());
    findItem.setQuantity(updateParam.getQuantity());
    findItem.setItemType(updateParam.getItemType());
    findItem.setOpen(updateParam.getOpen());
    findItem.setDeliveryCode(updateParam.getDeliveryCode());
    findItem.setRegions(updateParam.getRegions());
  }

  @Override
  public Long delete(Long itemId) {
    Item removeItem = store.remove(itemId);
    return removeItem.getId();
  }

  public void clearStore() {
    store.clear();
  }

}
