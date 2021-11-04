package hello.itemservice2.web.typeconverter;

import lombok.Data;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ConverterController {

  @GetMapping("/convert/view")
  public String converterView(Model model) {
    model.addAttribute("number", 10000);
    model.addAttribute("ipPort", new IpPort("127.0.0.1", 8080));
    return "formatter/convertView";
  }

  @GetMapping("/convert/edit")
  public String convertForm(Model model) {
    IpPort ipPort = new IpPort("127.0.0.1", 8080);
    model.addAttribute("form", new Form(ipPort));
    return "formatter/converterForm";
  }

  @PostMapping("/convert/edit")
  public String convertEdit(@ModelAttribute Form form, Model model) {
    IpPort ipPort = form.getIpPort();
    model.addAttribute("ipPort", ipPort);
    return "formatter/convertView";
  }

  @Data
  static class Form {
    private IpPort ipPort;

    public Form(IpPort ipPort) {
      this.ipPort = ipPort;
    }

  }
}
