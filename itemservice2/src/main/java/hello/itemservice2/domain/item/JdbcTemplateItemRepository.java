package hello.itemservice2.domain.item;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

@Slf4j
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
    parameters1.put("ITM_TYP", item.getItemType().getTypeCode());
    parameters1.put("ITM_PSC", item.getPositionCode().getCode());

    long id = jdbcInsert.executeAndReturnKey(parameters1).longValue();
    item.setId(id);

    // ITM_RG_TB에 멀티체크박스로 들어오는 데이터 저장
    regionsInsert(item, id);

    return item;
  }

  private void regionsInsert(Item item, long id) {
    SimpleJdbcInsert jdbcInsert2 = new SimpleJdbcInsert(jdbcTemplate);
    jdbcInsert2.withTableName("itm_rg_tb").usingColumns("ITM_RG_ID", "RG_EN_KEY");
    Map<String, Object> parameters2 = new HashMap<>();

    for (int i = 0; i < item.getRegions().size(); i++) {
      parameters2.put("ITM_RG_ID", id);
      parameters2.put("RG_EN_KEY", item.getRegions().get(i));
      jdbcInsert2.execute(parameters2);
    }
  }

  @Override
  public Item findById(Long id) {
    List<Item> result = jdbcTemplate
        .query("select i.* , r.RG_EN_KEY "
            + "from itm_tb i, itm_rg_tb r "
            + "where i.ITM_ID = ? and i.ITM_ID = r.ITM_RG_ID;", itemRowMapper(), id);
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
      item.setItemType(new TypeItem(rs.getString("ITM_TYP")));
      item.setPositionCode(new PositionCode(rs.getString("ITM_PSC")));


      // 한개일지 두개일지 어떻게 알아?
      List<String> regions = new ArrayList<>();

      for(int i=0; i < rs.getRow(); i++) {
        regions.add(rs.getString("RG_EN_KEY"));
        if(rs.next()) {
          if(item.getId() != rs.getLong("ITM_ID")) {
            break;
          }
        }
      }
      item.setRegions(regions);
      return item;

      //      item.setDeliveryCode(rs.getObject("ITM_DEC", DeliveryCode.class)); //TODO rs.getObject를 어떻게 쓰는거지?
    };
  }

  @Override
  public List<Item> findAll() {
    return jdbcTemplate.query("select i.* , r.RG_EN_KEY "
        + "from itm_tb i, itm_rg_tb r "
        + "where i.ITM_ID = r.ITM_RG_ID", itemRowMapper());
  }

  @Override
  public void update(Long itemId, Item updateParam) {

    jdbcTemplate.update("update itm_tb set ITM_NM =?, ITM_PR =?, ITM_QTY =?, "
        + "ITM_OP=?, ITM_TYP=?, ITM_PSC=? where ITM_ID = ?",
        updateParam.getItemName(), updateParam.getPrice(), updateParam.getQuantity(), updateParam.getOpen()
        , updateParam.getItemType().getTypeCode() ,updateParam.getPositionCode().getCode(), itemId);

    jdbcTemplate.update("delete from itm_rg_tb where ITM_RG_ID =?", itemId);
    regionsInsert(updateParam, itemId);
  }

  @Override
  public Long delete(Long itemId) {
    jdbcTemplate.update("Delete from itm_tb where ITM_ID = ?", itemId);
    return itemId;
  }
}
