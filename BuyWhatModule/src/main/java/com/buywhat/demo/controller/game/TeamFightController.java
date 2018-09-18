package com.buywhat.demo.controller.game;

import com.buywhat.demo.bean.Game.Pokemon2;
import com.buywhat.demo.service.GameServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@Controller
public class TeamFightController {

    @Autowired
    GameServiceImpl service;

    Integer player1Hp;
    Integer player2Hp;


    @RequestMapping("teamfight")
    public String toTeamFight(Model model, HttpSession session) {
        //初始化HP
        player1Hp = 100;
        player2Hp = 100;
        //将HP加入
        model.addAttribute("player1Hp", player1Hp);
        model.addAttribute("player2Hp", player2Hp);
        //初始化 创建敌我各三只PM信息
        Map pokeMap = service.findPmBeforeGame();

        //初始化我方三只
        model.addAttribute("p1",pokeMap.get(1));
        model.addAttribute("p2",pokeMap.get(4));
        model.addAttribute("p3",pokeMap.get(7));

        //初始化敌方三只
        model.addAttribute("p4",pokeMap.get(1));
        model.addAttribute("p5",pokeMap.get(4));
        model.addAttribute("p6",pokeMap.get(7));

        return "teamFight";
    }

    @RequestMapping("teamfightgogogo")
    public String doTeamFight(Model model, HttpSession session) {


        return "teamFight";
    }


}
