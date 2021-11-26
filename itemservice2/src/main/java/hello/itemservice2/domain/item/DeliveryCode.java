package hello.itemservice2.domain.item;

import lombok.AllArgsConstructor;
import lombok.Data;

/** 위치로 변경 : PositionCode(-)
 * FAST : 빠른 배송   -> A : 생활용품
 * NORMAL : 일반 배송 -> B : 식품
 * SLOW : 느린 배송   -> C : 도서 및 잡화
 */
@Data
@AllArgsConstructor
public class DeliveryCode {

  private String code;
  private String displayName;

  public DeliveryCode() {
  }

  public DeliveryCode(String code) {
    this.code = code;
  }
}
