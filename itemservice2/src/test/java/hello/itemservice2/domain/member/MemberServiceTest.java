package hello.itemservice2.domain.member;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MemberServiceTest {

  MemberService memberService;
  MemoryMemberRepository memberRepository;

  @BeforeEach
  public void beforeEach() {
    memberRepository = new MemoryMemberRepository();
    memberService = new MemberService(memberRepository);
  }

  @AfterEach
  public void afterEach() {
    memberRepository.clearStore();
  }

  @Test
  public void 회원가입() throws Exception{
    //given
    Member member = new Member();
    member.setLoginId("spring");
    memberService.join(member);

    //when
    Member result = memberService.findByLoginId("spring").get();

    //then
    Assertions.assertThat(result).isEqualTo(member);
  }

  @Test
  public void 중복_회원_예외() throws Exception {
    //given
    Member member1 = new Member();
    member1.setLoginId("spring");

    Member member2 = new Member();
    member2.setLoginId("spring");

    //when
    memberService.join(member1);

    IllegalStateException e = assertThrows(IllegalStateException.class,
        () -> memberService.join(member2)); // 예외가 발생해야 한다.

    //then

    Assertions.assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");

  }

}
