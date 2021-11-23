package hello.itemservice2.domain.member;

import static org.assertj.core.api.Assertions.assertThat;

import hello.itemservice2.domain.member.Member;
import hello.itemservice2.domain.member.MemoryMemberRepository;
import java.util.List;
import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

public class MemoryMemberRepositoryTest {

  MemoryMemberRepository repository = new MemoryMemberRepository();

  @AfterEach
  public void afterEach() {
    repository.clearStore();
  }

  @Test
  public void save() {
    //given
    Member member = new Member();
    member.setLoginId("spring");
    member.setName("summer");

    //when
    repository.save(member);

    //then
    Member result = repository.findById(member.getId());
    assertThat(result).isEqualTo(member);
  }

  @Test
  public void findByLoginId() {
    //given
    Member member1 = new Member();
    member1.setLoginId("spring");
    repository.save(member1);

    Member member2 = new Member();
    member2.setLoginId("summer");
    repository.save(member2);
    //when
    Member result = repository.findByLoginId("spring").get();

    //then
    assertThat(result).isEqualTo(member1);
  }
  @Test
  public void findAll() {
    //given
    Member member1 = new Member();
    member1.setName("spring");
    repository.save(member1);

    Member member2 = new Member();
    member2.setName("summer");
    repository.save(member2);

    //when
    List<Member> result = repository.findAll();

    //then
    assertThat(result.size()).isEqualTo(2);
  }

}
