package com.buywhat.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Tianqi.Zhang
 * @date 2019/2/24
 * @time 22:38
 * @package com.buywhat.demo.controller
 * @project 0912BuyWhat
 * @description
 */
@Controller
public class testController {

    @RequestMapping("abc")
    public void abcd (){
        System.out.println("false = " + false);

        return;
    }
}
