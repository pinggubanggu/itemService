package hello.itemservice2.domain.item;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class TypeItem {

  private String typeCode;
  private String typeDescription;

  public TypeItem() {
  }

  public TypeItem(String typeCode) {
    this.typeCode = typeCode;
  }

  public TypeItem(String typeCode, String typeDescription) {
    this.typeCode = typeCode;
    this.typeDescription = typeDescription;
  }
}
