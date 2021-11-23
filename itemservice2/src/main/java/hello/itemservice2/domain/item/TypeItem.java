package hello.itemservice2.domain.item;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TypeItem {

  private String typeCode;
  private String typeDescription;

  public TypeItem() {
  }

  public TypeItem(String typeCode) {
    this.typeCode = typeCode;
  }

}
