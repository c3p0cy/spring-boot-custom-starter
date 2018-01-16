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
// Set HelloServiceAutoConfiguration.class is EnableAutoConfiguration
org.springframework.boot.autoconfigure.EnableAutoConfiguration=\
tw.c3p0cy.practice.book.springbootcuststater.HelloServiceAutoConfiguration
```
----------------------------------------------------
## Create project from the custom starter
1. Dependencies
```xml
<dependency>
  <groupId>tw.c3p0cy.practice.book</groupId>
  <artifactId>spring-boot-custom-starter</artifactId>
  <version>${project.version}</version>
</dependency>
<dependency>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-starter-web</artifactId>
</dependency>
<dependency>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-starter-test</artifactId>
  <scope>test</scope>
</dependency>
```
2. application.properties
```
hello.msg=SBCSD
```
3. Controller
```java
import tw.c3p0cy.practice.book.springbootcustomstarterdemo.springbootcuststater.HelloService;

@RestController
public class SpringBootCustomStarterDemoApplication {

  @Autowired
  private HelloService helloService;

  @RequestMapping("/")
  public String index() {
    return helloService.sayHello();
  }
}
```
4. Unit Test
```java
  @Test
  public void index() throws Exception {
    mockMvc.perform(
        MockMvcRequestBuilders.get("/")
            .accept(MediaType.APPLICATION_JSON_UTF8)
    )
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(content().string(equalTo("Hello SBCSD")))
        .andDo(MockMvcResultHandlers.print())
        .andReturn();
  }
```
5. Unit Test via profile
  1. Add application-custom.properties
  2. Use @ActiveProfiles to set active profile
  ```java
  import org.springframework.test.context.ActiveProfiles;

  @ActiveProfiles(profiles = "custom")
  public class SpringBootCustomStarterDemoApplicationTestForCustom { ... }
  ```

----------------------------------------------------
## Using Spring Boot without the parent POM
```xml
<properties>
  <spring.boot.version>1.5.9.RELEASE</spring.boot.version>
  <spring-data-releasetrain.version>Fowler-SR2</spring-data-releasetrain.version>
</properties>


<dependencyManagement>
  <dependencies>
    <!-- Using Spring Boot without the parent POM -->
    <dependency>
      <!-- Override Spring Data release train provided by Spring Boot -->
      <groupId>org.springframework.data</groupId>
      <artifactId>spring-data-releasetrain</artifactId>
      <version>${spring-data-releasetrain.version}</version>
      <scope>import</scope>
      <type>pom</type>
    </dependency>
    <dependency>
      <!-- Import dependency management from Spring Boot -->
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-dependencies</artifactId>
      <version>${spring.boot.version}</version>
      <type>pom</type>
      <scope>import</scope>
    </dependency>
</dependencyManagement>
```
----------------------------------------------------
## References:
* [Using Spring Boot - Build systems](https://docs.spring.io/spring-boot/docs/current/reference/html/using-boot-build-systems.html)
* [Spring Boot – Profile based properties and yaml example](https://www.mkyong.com/spring-boot/spring-boot-profile-based-properties-and-yaml-example/)
* [Spring profiles and Testing](https://stackoverflow.com/questions/13364112/spring-profiles-and-testing)