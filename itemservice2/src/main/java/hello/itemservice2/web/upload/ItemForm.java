package hello.itemservice2.web.upload;

import java.util.List;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

/**
 * 상품 저장용 폼
 */
@Data
public class ItemForm {
  
  private Long itemId;
  private String itemName;
  private List<MultipartFile> imageFiles;
  private MultipartFile attachFile;
}
