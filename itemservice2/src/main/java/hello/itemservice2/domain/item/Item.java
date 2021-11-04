package hello.itemservice2.domain.item;

import java.util.List;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class Item {

  private Long id;
  @NotEmpty
  private String itemName;
  @DecimalMin(value = "1000")
  private Integer price;
  @NotNull
  private Integer quantity;
  
  private Boolean open; //판매 여부
  private List<String> regions; //등록 지역
  private ItemType itemType; //상품 종류
  private String deliveryCode; //배송 방식
  
  public Item(String itemName, Integer price, Integer quantity) {
    this.itemName = itemName;
    this.price = price;
    this.quantity = quantity;
  }

  public Item() {
  }
}
