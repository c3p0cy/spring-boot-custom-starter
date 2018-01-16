package tw.c3p0cy.practice.book.springbootcustomstarterdemo.springbootcuststater;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "hello") // 設定 properties 中的前綴
@Data
public class HelloServiceProperties {
  private static final String DEFAULT_MSG = "world";

  private String msg = DEFAULT_MSG;
}
