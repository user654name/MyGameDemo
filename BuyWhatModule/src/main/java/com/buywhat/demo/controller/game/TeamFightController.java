package com.buywhat.demo.controller.game;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
public class TeamFightController {
    Integer player1Hp;
    Integer player2Hp;


    @RequestMapping("teamfight")
    public String toTeamFight(Model model, HttpSession session) {
        //初始化HP
        player1Hp=100;
        player2Hp=100;
        //初始化 创建敌我各三只PM信息
        model.addAttribute("player1Hp",player1Hp);
        model.addAttribute("player2Hp",player2Hp);

        return "teamFight";
    }

    @RequestMapping("teamfightgogogo")
    public String doTeamFight(Model model, HttpSession session) {


        return "teamFight";
    }


}
