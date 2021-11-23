package hello.itemservice2;

import hello.itemservice2.web.argumentresolver.LoginMemberArgumentResolver;
import hello.itemservice2.web.interceptor.LoginCheckInterceptor;
import hello.itemservice2.web.typeconverter.IpPortToStringConverter;
import hello.itemservice2.web.typeconverter.StringToIpPortConverter;
import java.util.List;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(new LoginCheckInterceptor())
            .order(1)
            .addPathPatterns("/**")
            .excludePathPatterns("/","/members/add", "/login", "/logout",
                                  "/css/**", "/*.ico","/error","/api/**","/formatter/**",
                                  "/convert/**", "/spring/upload/**");
  }

  @Override
  public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
    resolvers.add(new LoginMemberArgumentResolver());
  }

  @Override
  public void addFormatters(FormatterRegistry registry) {
    registry.addConverter(new StringToIpPortConverter());
    registry.addConverter(new IpPortToStringConverter());
  }
}
