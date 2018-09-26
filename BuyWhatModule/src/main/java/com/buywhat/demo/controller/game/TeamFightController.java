package com.buywhat.demo.controller.game;

import com.buywhat.demo.bean.User;
import com.buywhat.demo.bean.game.BattleInfo;
import com.buywhat.demo.bean.game.HurtInfo;
import com.buywhat.demo.bean.game.Pokemon2;
import com.buywhat.demo.bean.game.TeamGameRecord;
import com.buywhat.demo.dao.Pokemon2Mapper;
import com.buywhat.demo.dao.TeamGameRecordMapper;
import com.buywhat.demo.service.GameServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.Map;

@Controller
public class TeamFightController {

    @Autowired
    GameServiceImpl service;
    @Autowired
    Pokemon2Mapper pokemon2Mapper;
    @Autowired
    TeamGameRecordMapper recordMapper;

    Integer player1Hp;
    Integer player2Hp;


    /**
     * 开发时临时方法
     * 临时方法 为了不用进行游戏就可以【方便进入游戏结束界面】
     *
     * @param model   M
     * @param session session
     * @return 游戏结束页面
     */
    @RequestMapping("gameover")
    public String toGameOverPage(Model model, HttpSession session) {

        model.addAttribute("gameover", "你挂了,充钱立即复活");

        return "gameover";
    }

    /**
     * 微游戏支付页面 目前没有介入支付宝API
     * 【2018年9月25日 13:22:04】
     *
     * @param model   M
     * @param session session
     * @return 进行支付页面
     */
    @RequestMapping("payForGame")
    public String toPayForGame(Model model, HttpSession session) {

//        model.addAttribute("gameover", "你挂了,充钱立即复活");

        return "/pay/payForGame";
    }


    /**
     * 【2018年9月25日 13:22:50】
     * 设置游戏难度页面
     *
     * @param model
     * @param session
     * @return 应该跳转到游戏初始化方法中
     */
    @RequestMapping("setDifficulty")
    public String setDifficulty(Model model, HttpSession session) {


//        model.addAttribute("gameover", "你挂了,充钱立即复活");

        return "setDifficulty";
    }

    /**
     * 【2018年9月25日 13:26:36】
     * 根据游戏难度 初始化战斗信息
     * @param model
     * @param session
     * @param difficulty 游戏难度代号
     * @return
     */
    @RequestMapping("teamfight")
    public String toTeamFight(Model model, HttpSession session,
                              Integer difficulty) {
        //先创建玩家的PM信息
        Integer[] initP2Pm;
        Integer[] initP1Pm;


        //初始化玩家1PM信息（通常不变）
        initP1Pm = new Integer[]{1, 4, 7};
        initP2Pm = new Integer[]{1, 4, 7};


        if (difficulty!=null&&difficulty+""!="") {
            //初始化玩家2PM信息（需要更改）
            switch (difficulty) {
                case 1://入门难度
                    break;
                case 2://普通难度
                    break;
                case 3://AI卡牌强度增加
                    break;
                case 4://AI策略改善1
                    break;
                case 5://AL策略改善2
                    break;
                case 6://策略+强力增加
                    break;
                default://难度代号都不匹配 默认的难度是普通
            }

        }

        initP2Pm = new Integer[]{1, 4, 7};



//        //初始化HP（目前这还是个成员变量）
//        player1Hp = 100;
//        player2Hp = 100;

        //将HP加入model
        setHp(model, 100, 100);


        /* 初始化  创建敌我各三只PM信息
           playerNumber==1代表player1
           playerNumber==2代表player2 */
        initP1Pm(model, 1, initP1Pm);

        initP1Pm(model, 2, initP2Pm);

        return "teamFight";
    }

    /**
     * 【2018年9月25日 13:35:41】
     * 设置双方玩家主要HP值 目前只设置为100 不做改变
     *
     * @param model     M
     * @param player1Hp 玩家1Hp
     * @param player2Hp 玩家2Hp
     */
    private void setHp(Model model, Integer player1Hp, Integer player2Hp) {

        model.addAttribute("player1Hp", player1Hp);
        model.addAttribute("player2Hp", player2Hp);
    }

    /**
     * 在游戏前 初始化Pm的方法
     *
     * @param model        model
     * @param playerNumber 初始化p1 还是p2
     * @param pmIds        Pm的id集合
     */
    private void initP1Pm(Model model, Integer playerNumber, Integer[] pmIds) {


        String[] pms = null;
        if (playerNumber == 1) {//准备初始化player1
            //Player1的三只Pm(位置代号,也是用于前端显示的Vo)
            pms = new String[]{"p1", "p2", "p3"};

        } else if (playerNumber == 2) {//准备初始化player2
            //Player2的三只Pm(位置代号,也是用于前端显示的Vo`  X  )
            pms = new String[]{"p4", "p5", "p6"};
        }


        //初始指定的三只Pm
        model.addAttribute(pms[0], pokemon2Mapper.selectByPrimaryKey(pmIds[0]));
        model.addAttribute(pms[1], pokemon2Mapper.selectByPrimaryKey(pmIds[1]));
        model.addAttribute(pms[2], pokemon2Mapper.selectByPrimaryKey(pmIds[2]));
    }


    /**
     * 开始战斗之后 执行的方法
     *
     * @param model
     * @param session
     * @param battleInfo
     * @return
     */
    @RequestMapping("teamfighting")
    public String doTeamFight(Model model, HttpSession session,
                              BattleInfo battleInfo) {
        //首先设置玩家双方的ID值
        //获取玩家的身份信息
        User player1 = (User) session.getAttribute("user");

        //设置双方玩家的ID
        Integer player1Id;//玩家1ID
        Integer player2Id = -2;//玩家2ID

        //设置根据玩家1登录情况，读取信息
        if (player1 != null) {//若用户已经登录
            //读取用户id
            player1Id = player1.getId();

        } else {//若用户未登录
            //userId为-1的游客账户 用来作为临时账号储存战绩
            player1Id = -1;
        }


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
        //计算这回合对战情况
        /**
         *      这是方法中的map信息
         *      map.put("comHurt", comHurt);
         *      map.put("playerHurt", playerHurt);
         *      map.put("battleMsg", battleMsg);
         *      map.put("winner", winner);
         */
        Map battleMap = service.hitEachOther(comPmId, playerPmId);


        /**
         *
         *    【游戏结束】
         * playerWin代表游戏结果
         * 值为1 代表玩家1胜利 值为2 代表玩家2胜利 值为0 代表极少出现的平局
         * （在之后的版本中 应该避免平局的出现？）
         */
        Integer playerWin;//游戏结束的结果（胜利情况）

        //【游戏没有结束】读取受伤信息
        Integer comHurt = (Integer) battleMap.get("comHurt");
        Integer playerHurt = (Integer) battleMap.get("playerHurt");

        //【首先】计算战斗后剩余生命值
        player1Hp = battleInfo.getPlayer1Hp() + playerHurt;
        player2Hp = battleInfo.getPlayer2Hp() + comHurt;


        //读取本次战斗信息
        String battleMsg = (String) battleMap.get("battleMsg");
        battleMsg = battleMsg + battleInfo.getBattleMsg();

        //放入战斗详情【文本】信息
        model.addAttribute("battleMsg", battleMsg);

        /**
         * 根据Hp正负值判断游戏是否可结束
         * 【游戏结束条件是任一方Hp不大于0】
         */
        if (isGameOver(model, player1Id, player2Id)) {
            return "gameover";
        }


        HurtInfo p1HurtInfo = (HurtInfo) battleMap.get("p1HurtInfo");
        HurtInfo p2HurtInfo = (HurtInfo) battleMap.get("p2HurtInfo");

        /**
         *        如果【游戏没有结束】
         *
         *        将信息封装给前端战斗页面
         *        前端需要信息——6个精灵的对象信息
         *        两个玩家的血量信息
         *        战斗详情信息
         *
         */
        //读取【【本回合】】获胜信息
        Integer winner = (Integer) battleMap.get("winner");
        //根据获胜信息判断是否可以进化
        if (winner == 1) {//玩家赢了
            if (playerPmId != 3 && playerPmId != 6 && playerPmId != 9) {
                //玩家Pm不是最高进化型，可以进化
                playerPmId = playerPmId + 1;//Pm进化后ID
                p1HurtInfo.setInfo1("进化");
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
                p2HurtInfo.setInfo1("进化");
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


        } else if (winner == 0) {
            System.out.println("平局或者均势局");
        } else {
            System.out.println("不可能走到这里！！读取winner信息出错！！");
        }


        //初始化 创建敌我各三只PM信息
//        Map pokeMap = service.findPmBeforeGame();//方法暂时不用


        //初始化我方三只
        model.addAttribute("p1", pokemon2Mapper.selectByPrimaryKey(battleInfo.getP1Id()));
        model.addAttribute("p2", pokemon2Mapper.selectByPrimaryKey(battleInfo.getP2Id()));
        model.addAttribute("p3", pokemon2Mapper.selectByPrimaryKey(battleInfo.getP3Id()));

        //初始化敌方三只
        model.addAttribute("p4", pokemon2Mapper.selectByPrimaryKey(battleInfo.getP4Id()));
        model.addAttribute("p5", pokemon2Mapper.selectByPrimaryKey(battleInfo.getP5Id()));
        model.addAttribute("p6", pokemon2Mapper.selectByPrimaryKey(battleInfo.getP6Id()));


        //将HP加入
        model.addAttribute("player1Hp", player1Hp);
        model.addAttribute("player2Hp", player2Hp);



        //玩家和电脑各自出战的PM
        Pokemon2 plPmFighting = pokemon2Mapper.selectByPrimaryKey(playerPmId);
        Pokemon2 p2PmFighting = pokemon2Mapper.selectByPrimaryKey(comPmId);

        //放入两个出战的精灵图片（以及资料）
        model.addAttribute("plPmFighting", plPmFighting);
        model.addAttribute("p2PmFighting", p2PmFighting);


        model.addAttribute("p1HurtInfo", p1HurtInfo);
        model.addAttribute("p2HurtInfo", p2HurtInfo);

        System.out.println(battleMsg);

        return "teamFight";
    }

    private boolean isGameOver(Model model, Integer player1Id, Integer player2Id) {
        Integer playerWin;
        if (player1Hp <= 0 && player2Hp > 0) {//玩家1挂了,玩家2活着
            playerWin = 2;
            model.addAttribute("gameover", "你挂了,充钱立即复活");

            //更新战绩信息
            updateRecord(model, player1Id, player2Id, playerWin);


            return true;

        } else if (player2Hp <= 0 && player1Hp > 0) {//玩家1活着,但是玩家2挂了
            playerWin = 1;

            //更新战绩信息
            updateRecord(model, player1Id, player2Id, playerWin);

            model.addAttribute("gameover", "你赢了,嘻嘻");
            return true;

        } else if (player1Hp <= 0 && player2Hp <= 0) {//难得一见的同时挂掉→平局
            playerWin = 0;

            //更新战绩信息
            updateRecord(model, player1Id, player2Id, playerWin);

            model.addAttribute("gameover", "哇,你们一起挂掉了,平局很少见的哦！");

            return true;
        }
        return false;
    }


    private void updateRecord(Model model, Integer player1Id, Integer player2Id, Integer playerWin) {
        //更新【玩家&电脑】战绩
        boolean updateSuccess = service.updateTeamGameRecord(player1Id, player2Id, playerWin);

        System.out.println("插入成功updateSuccess = " + updateSuccess);
        //将最新的战绩信息返回
        TeamGameRecord player1Record = service.findTeamGameRecordByUserId(player1Id);
        TeamGameRecord player2Record = service.findTeamGameRecordByUserId(player2Id);

        //将战绩信息放入Model中
        model.addAttribute("player1Record", player1Record);
        model.addAttribute("player2Record", player2Record);
    }


}
