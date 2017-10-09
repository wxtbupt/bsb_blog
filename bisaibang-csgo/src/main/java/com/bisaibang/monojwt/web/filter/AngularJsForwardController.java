package com.bisaibang.monojwt.web.filter;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by szzz on 16/12/21.
 */

@Controller
public class AngularJsForwardController {
    @RequestMapping(value = "/**/{path:(?!websocket)[^\\.]*}")
    public String redirect(@PathVariable String path) {
        return "forward:/";
    }
}
