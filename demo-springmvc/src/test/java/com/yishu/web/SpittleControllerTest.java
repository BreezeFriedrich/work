package com.yishu.web; 

import com.yishu.Spittle;
import com.yishu.data.SpittleRepository;
import org.junit.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.servlet.view.InternalResourceView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.matchers.JUnitMatchers.hasItems;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

/** 
* SpittleController Tester. 
* 
* @author <Authors name> 
* @since <pre>三月 19, 2018</pre> 
* @version 1.0 
*/ 
public class SpittleControllerTest {

@Test
public void shouldShowRecentSpittles() throws Exception {
    List<Spittle> expectedSpittles=createSpittleList(20);
    SpittleRepository mockRepository=mock(SpittleRepository.class);
    when(mockRepository.findSpittles(Long.MAX_VALUE,20))
            .thenReturn(expectedSpittles);

    SpittleController controller=new SpittleController(mockRepository);
    MockMvc mockMvc=standaloneSetup(controller)
            .setSingleView(new InternalResourceView("/WEB-INF/views/spittles.jsp"))
            .build();
    mockMvc.perform(get("/spittles"))//模拟对"/spittles"发起GET请求,
            .andExpect(view().name("spittles"))//断言视图名称为spittles
            .andExpect(model().attributeExists("spittleList"))//断言模型中包含名为spittleList的属性.
            .andExpect(model().attribute("spittleList",hasItems(expectedSpittles.toArray())));//断言模型中key=spittleList的值符合预期.
}

private List<Spittle> createSpittleList(int count){
    List<Spittle> spittles=new ArrayList<Spittle>();
    for(int i=0;i<count;i++){
        spittles.add(new Spittle("Spittle "+i,new Date()));
    }
    return spittles;
}


} 
