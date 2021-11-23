package hello.itemservice2.domain.member;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.sql.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcTemplateMemberRepository implements MemberRepository{

  private final JdbcTemplate jdbcTemplate;

  public JdbcTemplateMemberRepository(DataSource dataSource) {
    this.jdbcTemplate = new JdbcTemplate(dataSource);
  }

  @Override
  public Member save(Member member) {
    SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
    jdbcInsert.withTableName("mbr_account_tb").usingGeneratedKeyColumns("MBR_SEQ");

    Map<String, String> parameters = new HashMap<>();
    parameters.put("MBR_ID", member.getLoginId());
    parameters.put("MBR_PWD", member.getPassword());
    parameters.put("MBR_NM", member.getName());

    long id = jdbcInsert.executeAndReturnKey(parameters).longValue();
    member.setId(id);

    return member;
  }

  @Override
  public Member findById(Long id) {
    List<Member> result = jdbcTemplate
        .query("select * from mbr_account_tb where mbr_seq = ?", memberRowMapper(), id);
    return result.get(0);
  }

  private RowMapper<Member> memberRowMapper() {
    return (rs, rowNum) -> {
      Member member = new Member(
          rs.getLong("mbr_seq"),
          rs.getString("mbr_id"),
          rs.getString("mbr_nm"),
          rs.getString("mbr_pwd")
      );
      return member;
    };
  }

  @Override
  public Optional<Member> findByLoginId(String loginId) {
    List<Member> result = jdbcTemplate
        .query("select * from mbr_account_tb where mbr_id = ?", memberRowMapper(), loginId);
    return result.stream().findAny();
  }

  @Override
  public List<Member> findAll() {
    return jdbcTemplate.query("select * from mbr_account_tb", memberRowMapper());
  }

}
