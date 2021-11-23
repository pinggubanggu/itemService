package hello.itemservice2.web.validation.form;

import hello.itemservice2.domain.item.DeliveryCode;
import hello.itemservice2.domain.item.ItemType;
import hello.itemservice2.domain.item.TypeItem;
import java.util.List;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

@Data
public class ItemSaveForm {

  @NotBlank
  private String itemName;

  @NotNull
  @Range(min = 1000, max = 1000000)
  private Integer price;

  @NotNull
  @Max(value = 9999)
  private Integer quantity;

  @NotNull
  private Boolean open; //판매 여부
  @NotNull
  private List<String> regions; //등록 지역
  @NotNull
  private TypeItem itemType; //상품 종류
  @NotNull
  private DeliveryCode deliveryCode; //배송 방식
}
