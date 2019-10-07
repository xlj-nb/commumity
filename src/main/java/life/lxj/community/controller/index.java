package life.lxj.community.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Created by asus on 2019/10/7.
 */
@Controller
public class index {
    @GetMapping("/")
    public String index(){return "index";}
}
