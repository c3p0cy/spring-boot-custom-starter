Demo project for Spring Boot with custom starter
----------------------------------------------------
## Creating Custom Starter
1. Dependency
  ```xml
  <dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-autoconfigure</artifactId>
  </dependency>
  ```
2. Add Service
  ```java
  // Just a normal class
  @Data
  public class HelloService {
    private String msg;
    public String sayHello() {
      return "Hello " + msg;
    }
  }
  ```
3. Add Service Properties
  ```java
  import lombok.Data;
  import org.springframework.boot.context.properties.ConfigurationProperties;

  @ConfigurationProperties(prefix = "hello") // 設定 properties 中的前綴
  @Data
  public class HelloServiceProperties {
    private static final String DEFAULT_MSG = "world";

    private String msg = DEFAULT_MSG;
  }
  ```
4. Add Service Auto Configuration
```java
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
  public HelloService helloService() { ... }
}
```
5. Add spring.factories to resources/META-INF
  ```
  # Set HelloServiceAutoConfiguration.class is EnableAutoConfiguration
  org.springframework.boot.autoconfigure.EnableAutoConfiguration=\
  tw.c3p0cy.practice.book.springbootcuststater.HelloServiceAutoConfiguration
  ```
----------------------------------------------------


----------------------------------------------------
## References:
* ​