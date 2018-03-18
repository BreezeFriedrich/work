package com.yishu.web;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;

import org.junit.Test;
import org.springframework.test.web.servlet.MockMvc;

/** 
* HomeController Tester. 
* 
* @author <Authors name> 
* @since <pre>三月 18, 2018</pre> 
* @version 1.0 
*/ 
public class HomeControllerTest {

@Test
public void testHomePagePojo() throws Exception {
//TODO: Test goes here...
    HomeController controller=new HomeController();
    assertEquals("home",controller.home());
}

    @Test
    public void testHomePage() throws Exception {
        HomeController controller=new HomeController();
        MockMvc mockMvc=standaloneSetup(controller).build();
        mockMvc.perform(get("/"))
                .andExpect(view().name("home"));
    }

} 
