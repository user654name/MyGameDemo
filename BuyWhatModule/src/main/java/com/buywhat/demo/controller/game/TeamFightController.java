package com.buywhat.demo.controller.game;

import com.buywhat.demo.bean.Game.BattleInfo;
import com.buywhat.demo.bean.Game.Pokemon2;
import com.buywhat.demo.dao.Pokemon2Mapper;
import com.buywhat.demo.service.GameServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.Map;

@Controller
public class TeamFightController {

    @Autowired
    GameServiceImpl service;
    @Autowired
    Pokemon2Mapper pokemon2Mapper;

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
        model.addAttribute("p1", pokeMap.get(1));
        model.addAttribute("p2", pokeMap.get(4));
        model.addAttribute("p3", pokeMap.get(7));

        //初始化敌方三只
        model.addAttribute("p4", pokeMap.get(1));
        model.addAttribute("p5", pokeMap.get(4));
        model.addAttribute("p6", pokeMap.get(7));

        return "teamFight";
    }


    @RequestMapping("teamfightgogogo")
    public String doTeamFight(Model model, HttpSession session, BattleInfo battleInfo) {


        //生成随机数 模拟电脑的选择
        Integer comPmNum = (int) (1 + Math.random() * 3);

        //电脑选择战斗的PM的位置（1，2，3）
        Integer comPmId = null;
        switch (comPmNum) {
            case 1:
                comPmId = battleInfo.getP4Id();
                break;
            case 2:
                comPmId = battleInfo.getP5Id();
                break;
            case 3:
                comPmId = battleInfo.getP6Id();
                break;

        }

        //玩家出战的PM
        Integer playerPmId = battleInfo.getChooseId();
        System.out.println("电脑选择的是：" + comPmId + ",玩家选择的是：" + playerPmId);

        //计算玩家出战PM的位置
        Integer playerPmNum = 0;
        playerPmNum = playerPmId > 3 ? (playerPmId > 6 ? 3 : 2) : 1;

        //计算对战结果
        //计算谁赢了
        /**
         *      这是方法中的map信息
         *      map.put("comHurt", comHurt);
         *      map.put("playerHurt", playerHurt);
         *      map.put("battleMsg", battleMsg);
         *      map.put("winner", winner);
         */
        Map battleMap = service.hitEachOther(comPmId, playerPmId);

        //读取受伤信息
        Integer comHurt = (Integer) battleMap.get("comHurt");
        Integer playerHurt = (Integer) battleMap.get("playerHurt");


        /**
         *        将信息封装给前端战斗页面
         *        前端需要信息——6个精灵的对象信息
         *        两个玩家的血量信息
         *        战斗详情信息
         *
         */
        //【首先】计算战斗后剩余生命值
        player1Hp = battleInfo.getPlayer1Hp() - playerHurt;
        player2Hp = battleInfo.getPlayer2Hp() - comHurt;
        if (player1Hp <= 0 && player2Hp > 0) {//玩家挂了,电脑活着
            model.addAttribute("gameover", "你挂了,充钱立即复活");
            return "结果页面";

        } else if (player2Hp <= 0 && player1Hp > 0) {//你活着,但是电脑挂了
            model.addAttribute("gameover", "你赢了,嘻嘻");
            return "结果页面";

        } else if (player1Hp <= 0 && player2Hp <= 0) {
            model.addAttribute("gameover", "哇,你们一起挂掉了,平局很少见的哦！");
            return "结果页面";

        }


        //读取获胜信息
        Integer winner = (Integer) battleMap.get("winner");
        //根据获胜信息判断是否可以进化
        if (winner == 1) {//玩家赢了
            if (playerPmId != 3 && playerPmId != 6 && playerPmId != 9) {
                //玩家Pm不是最高进化型，可以进化
                playerPmId = playerPmId + 1;//Pm进化后ID
                //根据Pm位置更新他的ID
                switch (playerPmNum) {//playerPmNum 精灵位置
                    case 1:
                        battleInfo.setP1Id(playerPmId);
                        break;
                    case 2:
                        battleInfo.setP2Id(playerPmId);
                        break;
                    case 3:
                        battleInfo.setP3Id(playerPmId);
                        break;

                    default:
                        System.out.println("严重错误 玩家Pm进化出错！");
                }
            }
            System.out.println("玩家Pm已经是最高进化型，不能再进化 PmId=" + playerPmId);

        } else if (winner == -1) {

            if (comPmId != 3 && comPmId != 6 && comPmId != 9) {
                //电脑Pm不是最高进化型，可以进化
                comPmId = comPmId + 1;//Pm进化后ID
                //根据Pm位置更新他的ID
                switch (comPmNum) {//playerPmNum 精灵位置
                    case 1:
                        battleInfo.setP4Id(comPmId);
                        break;
                    case 2:
                        battleInfo.setP5Id(comPmId);
                        break;
                    case 3:
                        battleInfo.setP6Id(comPmId);
                        break;

                    default:
                        System.out.println("严重错误 电脑Pm进化出错！");
                }
                System.out.println("电脑Pm已经是最高进化型，不能再进化 PmId=" + comPmId);
            }


        }


        //读取战斗信息
        String battleMsg = (String) battleMap.get("battleMsg");


        //初始化 创建敌我各三只PM信息
        Map pokeMap = service.findPmBeforeGame();

        //初始化我方三只
        model.addAttribute("p1", pokeMap.get(battleInfo.getP1Id()));
        model.addAttribute("p2", pokeMap.get(battleInfo.getP2Id()));
        model.addAttribute("p3", pokeMap.get(battleInfo.getP3Id()));

        //初始化敌方三只
        model.addAttribute("p4", pokeMap.get(battleInfo.getP4Id()));
        model.addAttribute("p5", pokeMap.get(battleInfo.getP5Id()));
        model.addAttribute("p6", pokeMap.get(battleInfo.getP6Id()));


        //将HP加入
        model.addAttribute("player1Hp", player1Hp);
        model.addAttribute("player2Hp", player2Hp);

        //放入战斗详情信息
        model.addAttribute("battleMsg", battleMsg);


        return "teamFight";
    }


}
