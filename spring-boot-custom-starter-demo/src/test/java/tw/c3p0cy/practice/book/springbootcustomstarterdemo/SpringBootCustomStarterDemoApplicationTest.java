package tw.c3p0cy.practice.book.springbootcustomstarterdemo;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = SpringBootCustomStarterDemoApplication.class)
public class SpringBootCustomStarterDemoApplicationTest {

  @Autowired
  private WebApplicationContext context;

  private MockMvc mockMvc;

  @Before
  public void setUp() {
    mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
  }

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
}