package hello.itemservice2.web.member;

import hello.itemservice2.domain.member.Member;
import hello.itemservice2.domain.member.MemberRepository;
import hello.itemservice2.domain.member.MemberService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {

  private final MemberService memberService;

  @GetMapping("/add")
  public String addForm(@ModelAttribute("member") Member member) {
    return "members/addMemberForm.html";
  }

  @PostMapping("/add")
  public String save(@Valid @ModelAttribute Member member, BindingResult result) {

    if(result.hasErrors()) {
      return "members/addMemberForm.html";
    }

    memberService.join(member);
    // TODO ***님 회원가입이 완료되었습니다. 하나 만들기(-)
    return "redirect:/";
  }
}
