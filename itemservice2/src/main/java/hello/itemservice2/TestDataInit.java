package hello.itemservice2;

import hello.itemservice2.domain.item.Item;
import hello.itemservice2.domain.item.ItemRepository;
import hello.itemservice2.domain.member.Member;
import hello.itemservice2.domain.member.MemberRepository;
import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TestDataInit {

  private final ItemRepository itemRepository;
  private final MemberRepository memberRepository;

  @PostConstruct
  public void init() {
    itemRepository.save(new Item("test1", 10000, 100));
    itemRepository.save(new Item("test2", 20000, 200));

    Member member = new Member();
    member.setLoginId("test");
    member.setPassword("test!");
    member.setName("테스터");
    memberRepository.save(member);
  }
}
