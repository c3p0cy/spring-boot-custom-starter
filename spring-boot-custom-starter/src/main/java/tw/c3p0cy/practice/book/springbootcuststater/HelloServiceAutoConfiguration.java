package tw.c3p0cy.practice.book.springbootcuststater;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(HelloServiceProperties.class)
@ConditionalOnClass(HelloService.class) // 判斷 HelloService 類別路徑是否存在
@ConditionalOnProperty(prefix = "hello", value = "enabled", matchIfMissing = true)
public class HelloServiceAutoConfiguration {

  @Autowired
  private HelloServiceProperties properties;

  @Bean
  @ConditionalOnMissingBean(HelloService.class) // 在容器中沒有此 Bean 時自動配置
  public HelloService helloService() {
    HelloService helloService = new HelloService();
    helloService.setMsg(properties.getMsg());
    return helloService;
  }
}
