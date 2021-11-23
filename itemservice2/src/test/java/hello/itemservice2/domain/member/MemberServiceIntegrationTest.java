package hello.itemservice2.domain.member;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class MemberServiceIntegrationTest {

  @Autowired MemberService memberService;
  @Autowired MemberRepository memberRepository;

  @Test
  public void 회원가입() {
    //given
    Member member = new Member();
    member.setLoginId("spring");
    member.setPassword("1234");
    member.setName("스프링");

    //when
    Long id = memberService.join(member);

    //then
    Member findMember = memberRepository.findByLoginId("spring").get();
    assertThat(member.getName()).isEqualTo(findMember.getName());
  }

  @Test
  public void 중복_회원_예외() {
    //given
    Member member1 = new Member();
    member1.setLoginId("spring");
    member1.setPassword("1234");
    member1.setName("스프링");

    Member member2 = new Member();
    member2.setLoginId("spring");
    member1.setPassword("1234");
    member1.setName("스프링");

    //when
    memberService.join(member1);

    //then
    IllegalStateException e = assertThrows(IllegalStateException.class,
        () -> memberService.join(member2));

    assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
  }
}
