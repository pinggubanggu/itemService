package hello.itemservice2.domain.item;

import java.util.List;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import lombok.Data;

@Data
public class Item { //TODO 관계형 데이터베이스 만들어서 JOIN 써보기, 정규화 법칙 한번 더 숙지하고 테이블, 관계 만들기

  private Long id;
  @NotEmpty
  private String itemName;
  @DecimalMin(value = "1000")
  private Integer price;
  @NotNull
  private Integer quantity;
  
  private Boolean open; //판매 여부
  private List<String> regions; //등록 지역

  private TypeItem itemType; //상품 종류
  private DeliveryCode deliveryCode; //배송 방식
  
  public Item(String itemName, Integer price, Integer quantity) {
    this.itemName = itemName;
    this.price = price;
    this.quantity = quantity;
  }

  public Item() {
  }
}
