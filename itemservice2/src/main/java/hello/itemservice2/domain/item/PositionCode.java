package hello.itemservice2.domain.item;

import lombok.Data;

@Data
public class PositionCode {

  private String code;
  private String displayName;

  public PositionCode() {
  }

  public PositionCode(String code) {
    this.code = code;
  }

  public PositionCode(String code, String displayName) {
    this.code = code;
    this.displayName = displayName;
  }



}
