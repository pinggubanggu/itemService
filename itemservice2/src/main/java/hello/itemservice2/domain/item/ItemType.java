package hello.itemservice2.domain.item;

public enum ItemType {  //TODO ENUM으로 데이터베이스에 저장해보기, 왜 ENUM을 쓰는걸 지양하는지 알아보기

  BOOK("도서"), FOOD("식품"), ETC("기타");

  private final String description;

  ItemType(String description) {
    this.description = description;
  }

  public String getDescription() {
    return description;
  }
}
