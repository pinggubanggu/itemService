package hello.itemservice2.domain.member;

import javax.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class Member {

  private Long id;

  @NotEmpty
  private String loginId;
  @NotEmpty
  private String name;
  @NotEmpty
  private String password;

  public Member() {
  }

  public Member(Long id, String loginId, String name, String password) {
    this.id = id;
    this.loginId = loginId;
    this.name = name;
    this.password = password;
  }
}
