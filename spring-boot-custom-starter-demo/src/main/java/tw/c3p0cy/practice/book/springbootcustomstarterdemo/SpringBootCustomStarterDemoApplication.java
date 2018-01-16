package tw.c3p0cy.practice.book.springbootcustomstarterdemo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tw.c3p0cy.practice.book.springbootcustomstarterdemo.springbootcuststater.HelloService;

@SpringBootApplication
@RestController
public class SpringBootCustomStarterDemoApplication {

  @Autowired
  private HelloService helloService;

  @RequestMapping("/")
  public String index() {
    return helloService.sayHello();
  }

  public static void main(String[] args) {
    SpringApplication.run(SpringBootCustomStarterDemoApplication.class, args);
  }
}
