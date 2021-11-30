package hello.itemservice2.web.item.basic;

import hello.itemservice2.domain.item.DeliveryCode;
import hello.itemservice2.domain.item.Item;
import hello.itemservice2.domain.item.ItemRepository;
import hello.itemservice2.domain.item.MemoryItemRepository;
import hello.itemservice2.domain.item.ItemType;
import hello.itemservice2.domain.item.TypeItem;
import hello.itemservice2.web.validation.form.ItemSaveForm;
import hello.itemservice2.web.validation.form.ItemUpdateForm;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Slf4j
@Controller
@RequestMapping("/basic/items")
@RequiredArgsConstructor
public class BasicItemController {

  private final ItemRepository itemRepository;

  @ModelAttribute("regions")
  public Map<String, String> regions() {
    Map<String, String> regions = new HashMap<>();
    regions.put("SEOUL", "서울");
    regions.put("BUSAN", "부산");
    regions.put("JEJU", "제주");
    return regions;
  }
// Enum을 model값으로 넘겨주기
//  @ModelAttribute("itemTypes")
//  public ItemType[] itemTypes() {
//    return ItemType.values();
//  }

  // Enum을 Object로 변경해서 model값으로 넘겨주기
  @ModelAttribute("itemTypes")
  public List<TypeItem> itemTypes() {
    List<TypeItem> typeItems = new ArrayList<>();
    typeItems.add(new TypeItem("BOOK", "도서"));
    typeItems.add(new TypeItem("FOOD", "식품"));
    typeItems.add(new TypeItem("ETC", "기타"));
    return typeItems;
  }

  @ModelAttribute("deliveryCodes")
  public List<DeliveryCode> deliveryCodes() {
    List<DeliveryCode> deliveryCodes = new ArrayList<>();
    deliveryCodes.add(new DeliveryCode("FAST", "빠른 배송"));
    deliveryCodes.add(new DeliveryCode("NORMAL", "일반 배송"));
    deliveryCodes.add(new DeliveryCode("SLOW", "느린 배송"));
    return deliveryCodes;
  }


  @GetMapping
  public String items(Model model) {
    List<Item> items = itemRepository.findAll();
    model.addAttribute("items", items);
    return "basic/items";
  }

  @GetMapping("/{itemId}")
  public String item(@PathVariable Long itemId, Model model) {
    Item item = itemRepository.findById(itemId);
    model.addAttribute("item", item);
    return "basic/item";
  }

//  @GetMapping("/add")
  public String addForm() {
    return "basic/addForm";
  }

  @GetMapping("/add")
  public String addForm2(Model model) {
    model.addAttribute("item", new Item());
    return "form/addForm";
  }

//  @PostMapping("/add")
  public String addItem(@ModelAttribute Item item) {
    itemRepository.save(item);
    return "redirect:/basic/items/" + item.getId();
  }

  // 김영한 강사님 원래 버전
//  @PostMapping("/add")
  public String addItem2(@Valid @ModelAttribute("item") ItemSaveForm form, BindingResult result, RedirectAttributes redirectAttributes) {
    
    // 특정 필드 예외가 아닌 전체 예외
    if(form.getPrice() != null && form.getQuantity() != null) {
      int resultPrice = form.getPrice() * form.getQuantity();
      if(resultPrice < 10000) {
        result.reject("totalPriceMin", new Object[]{ 10000,resultPrice}, null);
      }
    }


    if(result.hasErrors()) {
      log.info("errors={}", result);
      return "form/addForm";
    }
    
    log.info("item.open={}", form.getOpen());
    log.info("item.regions={}", form.getRegions());
    log.info("item.itemType={}", form.getItemType());

    // 성공 로직
    Item item = new Item();
    item.setItemName(form.getItemName());
    item.setPrice(form.getPrice());
    item.setQuantity(form.getQuantity());
    item.setOpen(form.getOpen());
    item.setRegions(form.getRegions());
    item.setItemType(form.getItemType());
    item.setDeliveryCode(form.getDeliveryCode());
    
    Item saveItem = itemRepository.save(item);
    redirectAttributes.addAttribute("itemId", saveItem.getId());
    redirectAttributes.addAttribute("status", true);
    return "redirect:/basic/items/{itemId}";
  }

  // Regions 값을 하나의 값만 넘기기
  // TODO (나중에 Regions 값을 여러개 넘겨서 데이터베이스에 담는거 해보기)
  @PostMapping("/add")
  public String addItem2_1(@Valid @ModelAttribute("item") ItemSaveForm form, BindingResult result, RedirectAttributes redirectAttributes) {

    // 특정 필드 예외가 아닌 전체 예외
    if(form.getPrice() != null && form.getQuantity() != null) {
      int resultPrice = form.getPrice() * form.getQuantity();
      if(resultPrice < 10000) {
        result.reject("totalPriceMin", new Object[]{ 10000,resultPrice}, null);
      }
    }


    if(result.hasErrors()) {
      log.info("errors={}", result);
      return "form/addForm";
    }

    log.info("item.open={}", form.getOpen());
    log.info("item.regions={}", form.getRegions());
    log.info("item.itemType={}", form.getItemType());

    // 성공 로직
    Item item = new Item();
    item.setItemName(form.getItemName());
    item.setPrice(form.getPrice());
    item.setQuantity(form.getQuantity());
    item.setOpen(form.getOpen());
    item.setRegions(form.getRegions());
    item.setItemType(form.getItemType());
    item.setDeliveryCode(form.getDeliveryCode());

    Item saveItem = itemRepository.save(item);
    redirectAttributes.addAttribute("itemId", saveItem.getId());
    redirectAttributes.addAttribute("status", true);
    return "redirect:/basic/items/{itemId}";
  }

//  @GetMapping("/{itemId}/edit")
  public String editForm(@PathVariable Long itemId, Model model) {
    Item item = itemRepository.findById(itemId);
    model.addAttribute("item", item);
    return "basic/editForm";
  }

  @GetMapping("/{itemId}/edit")
  public String editForm2(@PathVariable Long itemId, Model model) {
    Item item = itemRepository.findById(itemId);
    model.addAttribute("item", item);
    return "form/editForm";
  }

//  @PostMapping("/{itemId}/edit")
  public String edit(@PathVariable Long itemId, @ModelAttribute Item item) {

    itemRepository.update(itemId, item);
    return "redirect:/basic/items/{itemId}";
  }

  @PostMapping("/{itemId}/edit")
  public String edit2(@PathVariable Long itemId,@Valid @ModelAttribute("item") ItemUpdateForm form, BindingResult result) {

    // 특정 필드 예외가 아닌 전체 예외
    if(form.getPrice() != null && form.getQuantity() != null) {
      int resultPrice = form.getPrice() * form.getQuantity();
      if(resultPrice < 10000) {
        result.reject("totalPriceMin", new Object[]{ 10000,resultPrice}, null);
      }
    }


    if(result.hasErrors()) {
      log.info("errors={}", result);
      return "form/editForm";
    }
    
    // 성공 로직
    Item item = new Item();
    item.setItemName(form.getItemName());
    item.setPrice(form.getPrice());
    item.setQuantity(form.getQuantity());
    item.setOpen(form.getOpen());
    item.setRegions(form.getRegions());
    item.setItemType(form.getItemType());
    item.setDeliveryCode(form.getDeliveryCode());

    itemRepository.update(itemId, item);
    return "redirect:/basic/items/{itemId}";
  }

  @PostMapping("/{itemId}/delete")
  public String delete(@PathVariable Long itemId, Model model) {
    Item findItem = itemRepository.findById(itemId);
    String itemName = findItem.getItemName();
    itemRepository.delete(itemId);
    log.info("상품삭제 완료={}", itemId);

    model.addAttribute("itemName", itemName);
    return "basic/deleteForm.html";
  }



}
