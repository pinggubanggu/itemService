package hello.itemservice2.domain.item;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcTemplateItemRepository implements ItemRepository{

  private final JdbcTemplate jdbcTemplate;

  public JdbcTemplateItemRepository(DataSource dataSource) {
    this.jdbcTemplate = new JdbcTemplate(dataSource);
  }

  @Override
  public Item save(Item item) {
    SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
    jdbcInsert.withTableName("itm_tb").usingGeneratedKeyColumns("ITM_ID");

    Map<String, Object> parameters1 = new HashMap<>();
    parameters1.put("ITM_NM", item.getItemName());
    parameters1.put("ITM_PR", item.getPrice());
    parameters1.put("ITM_QTY", item.getQuantity());
    parameters1.put("ITM_OP", item.getOpen());
    parameters1.put("ITM_RG", item.getRegions().get(0));
    parameters1.put("ITM_TYP", item.getItemType().getTypeCode());
    parameters1.put("ITM_DEC", item.getDeliveryCode().getCode());

    long id = jdbcInsert.executeAndReturnKey(parameters1).longValue();
    item.setId(id);

    return item;
  }

  @Override
  public Item findById(Long id) {
    List<Item> result = jdbcTemplate
        .query("select * from itm_tb where ITM_ID = ?", itemRowMapper(), id);
    return result.get(0);
  }

  private RowMapper<Item> itemRowMapper() {
    return (rs, rowNum) -> {
      Item item = new Item();
      item.setId(rs.getLong("ITM_ID"));
      item.setItemName(rs.getString("ITM_NM"));
      item.setPrice(rs.getInt("ITM_PR"));
      item.setQuantity(rs.getInt("ITM_QTY"));
      item.setOpen(rs.getBoolean("ITM_OP"));

      String region = rs.getString("ITM_RG");
      List<String> regions = new ArrayList<>();
      regions.add(region);
      item.setRegions(regions);

      item.setItemType(new TypeItem(rs.getString("ITM_TYP")));

//      item.setDeliveryCode(rs.getObject("ITM_DEC", DeliveryCode.class)); //TODO rs.getObject를 어떻게 쓰는거지?
      item.setDeliveryCode(new DeliveryCode(rs.getString("ITM_DEC")));

      return item;
    };
  }

  @Override
  public List<Item> findAll() {
    return jdbcTemplate.query("select * from itm_tb", itemRowMapper());
  }

  @Override
  public void update(Long itemId, Item updateParam) {

    jdbcTemplate.update("update itm_tb set ITM_NM =?, ITM_PR =?, ITM_QTY =?, "
        + "ITM_OP=?, ITM_RG=?, ITM_TYP=?, ITM_DEC=? where ITM_ID = ?",
        updateParam.getItemName(), updateParam.getPrice(), updateParam.getQuantity(), updateParam.getOpen()
        , updateParam.getRegions().get(0), updateParam.getItemType().getTypeCode() ,updateParam.getDeliveryCode().getCode(), itemId);

  }
}
