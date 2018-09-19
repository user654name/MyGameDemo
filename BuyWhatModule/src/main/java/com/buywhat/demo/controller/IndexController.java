package com.buywhat.demo.controller;

import com.buywhat.demo.bean.User;
import com.buywhat.demo.bean.vo.Vo;
import com.buywhat.demo.service.MyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.List;


@Controller
public class IndexController {

    @Autowired
    private MyService service;


    /**
     * 对index的访问 都转到home.html
     *
     * @param session
     * @param model
     * @return
     */
    @RequestMapping(value = {"/index"})
    public String toHome(HttpSession session, Model model) {

        //处理session信息
        User user = (User) session.getAttribute("user");
        if (user != null) {
            model.addAttribute("user", user);
        }


        //查询所有news信息,以 Vo List 的形式返回
        List<Vo> vos = service.getAllNewsToVoList();

        model.addAttribute("vos", vos);

        return "home";
    }


}


