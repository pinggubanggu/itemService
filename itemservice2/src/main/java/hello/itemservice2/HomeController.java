package hello.itemservice2;

import hello.itemservice2.domain.member.Member;
import hello.itemservice2.web.SessionConst;
import hello.itemservice2.web.argumentresolver.Login;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

@Controller
public class HomeController {

//  @GetMapping("/")
  public String home() {
    return "home";
  }

//  @GetMapping("/")
  public String homeLogin(
      @SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false)Member loginMember, Model model) {

    // 세션에 회원 데이터가 없으면 home
    if(loginMember == null) {
      return "home";
    }

    // 세션이 유지되면 로그인으로 이동
    model.addAttribute("member", loginMember);
    return "loginHome";
  }

  @GetMapping("/")
  public String homeLoginArgumentResolver(
      @Login Member loginMember, Model model) {

    // 세션에 회원 데이터가 없으면 home
    if(loginMember == null) {
      return "home";
    }

    // 세션이 유지되면 로그인으로 이동
    model.addAttribute("member", loginMember);
    return "loginHome";
  }
}
