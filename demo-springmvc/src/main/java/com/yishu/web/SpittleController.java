package com.yishu.web;

import com.yishu.Spittle;
import com.yishu.data.SpittleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;
import java.util.Map;

/**
 * Created by WindSpring on 2018/3/19.
 */
@Controller
@RequestMapping("/spittles")
public class SpittleController {
    private SpittleRepository spittleRepository;
    @Autowired
    public SpittleController(SpittleRepository spittleRepository) {
        this.spittleRepository = spittleRepository;
    }

    @RequestMapping(method = RequestMethod.GET)
    public String spittles(Model model){
//        model.addAttribute(spittleRepository.findSpittle(Long.MAX_VALUE,20));//Model为key-value的Map,其key值未指定将根据值的对象类型判定,根据类型List<Spittle>推断key为spittleList.
        model.addAttribute("spittleList",spittleRepository.findSpittles(Long.MAX_VALUE,20));
        return "spittles";
    }
    @RequestMapping(method = RequestMethod.GET)
    public String spittles(Map model){//使用java.util.Map代替Model,功能一样.
        model.put("spittleList",spittleRepository.findSpittles(Long.MAX_VALUE,20));
        return "spittles";
    }
    @RequestMapping(method = RequestMethod.GET)
    public List<Spittle> spittles(){
        spittleRepository.findSpittles(Long.MAX_VALUE,20);
    }
}
