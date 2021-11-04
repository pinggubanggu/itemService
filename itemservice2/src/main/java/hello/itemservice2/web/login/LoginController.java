package hello.itemservice2.web.login;

import hello.itemservice2.domain.login.LoginService;
import hello.itemservice2.domain.member.Member;
import hello.itemservice2.domain.member.MemberRepository;
import hello.itemservice2.web.SessionConst;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@Slf4j
@RequiredArgsConstructor
public class LoginController {

  private final LoginService loginService;

  @GetMapping("/login")
  public String loginForm(@ModelAttribute("loginForm") LoginForm loginForm) {
    return "login/loginForm";
  }

  @PostMapping("/login")
  public String login(@Valid @ModelAttribute LoginForm loginForm, BindingResult result, HttpServletRequest request) {
    if(result.hasErrors()) {
      return "login/loginForm";
    }

    Member loginMember = loginService.login(loginForm.getLoginId(), loginForm.getPassword());

    log.info("loginMember={}", loginMember);

    if(loginMember == null) {
      result.reject("loginFail", "아이디와 비밀번호가 맞지 않습니다.");
      return "login/loginForm";
    }

    // 로그인 성공처리

    // 세션이 있으면 있는 세션 반환, 없으면 신규 세션 생성
    HttpSession session = request.getSession();
    // 세션에 로그인 회원 정보 보관
    session.setAttribute(SessionConst.LOGIN_MEMBER, loginMember);

    return "redirect:/";
  }

  @PostMapping("/logout")
  public String logout(HttpServletRequest request) {
    HttpSession session = request.getSession(false);
    if( session != null) {
      session.invalidate();
    }
    return "redirect:/";
  }
}
