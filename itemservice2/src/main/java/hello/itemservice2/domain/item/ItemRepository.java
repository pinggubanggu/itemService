package hello.itemservice2.domain.item;

import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository {

  Item save(Item item);

  Item findById(Long id);

  List<Item> findAll();

  void update(Long itemId, Item updateParam);

  Long delete(Long itemId);

}
