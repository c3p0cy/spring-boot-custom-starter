package tw.c3p0cy.practice.book.springbootcuststater;

import lombok.Data;

@Data
public class HelloService {
  private String msg;
  public String sayHello() {
    return "Hello " + msg;
  }
}

