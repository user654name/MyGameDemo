package com.buywhat.demo.service;

import com.buywhat.demo.bean.Game.Pokemon2;
import com.buywhat.demo.dao.Pokemon2Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class GameServiceImpl implements GameService {

    @Autowired
    Pokemon2Mapper pokemon2Mapper;

    /**
     * 战斗前初始化PM
     *
     * @return 返回1 4 7 号PM
     */
    @Override
    public Map findPmBeforeGame() {

        //初始的PM规定为1 4 7 号
        Map map = new HashMap();
        Pokemon2 pm1 = pokemon2Mapper.selectByPrimaryKey(1);
        Pokemon2 pm4 = pokemon2Mapper.selectByPrimaryKey(4);
        Pokemon2 pm7 = pokemon2Mapper.selectByPrimaryKey(7);
        map.put(1, pm1);
        map.put(4, pm4);
        map.put(7, pm7);


        return map;
    }

    @Override
    public Map hitEachOther(Integer comPmId, Integer playerPmId) {
        Map map = new HashMap();

        /**
         * winner表示这回合的游戏赢家
         * 0平局
         * 1玩家赢
         * -1电脑赢
         */
        Integer winner = null;


        //电脑和玩家各自选择的PM
        Pokemon2 comPm = pokemon2Mapper.selectByPrimaryKey(comPmId);
        Pokemon2 playerPm = pokemon2Mapper.selectByPrimaryKey(playerPmId);

        //战斗信息
        String battleMsg = "";

        //PM各自攻击力
        Integer playerAtt = Integer.parseInt(playerPm.getAtt());//玩家的攻击力
        Integer comAtt = Integer.parseInt(comPm.getAtt());//电脑的攻击力

        //获取PM属性
        String playerType = playerPm.getType();
        String comType = comPm.getType();

        //各自承受的伤害
        Integer comHurt = 0;
        Integer playerHurt = 0;

        //判断属性
        if (playerType.equals(comType)) {//PM属性一样
            //平局或均势局
            winner = 0;
            //互相造成相等的 等同于PM攻击力的伤害
            comHurt = playerAtt * -1;
            playerHurt = comAtt * -1;


        } else {//他们的精灵不同
            switch (playerType) {
                case "G"://玩家是草属性
                    if (comType.equals("F")) {
                        //玩家【G草】电脑【F火】→【电脑赢】
                        winner = -1;

                    } else if (comType.equals("W")) {
                        //玩家【G草】电脑【W水】→【玩家赢】
                        winner = 1;
                    } else {
                        System.out.println("不可能走到这里");
                        battleMsg = "严重错误！";
                    }


                    break;
                case "F":

                    if (comType.equals("G")) {
                        //玩家【F火】电脑【G草】→【玩家赢】
                        winner = 1;

                    } else if (comType.equals("W")) {
                        //玩家【F火】电脑【W水】→【电脑赢】
                        winner = -1;

                    } else {
                        System.out.println("不可能走到这里");
                        battleMsg = "严重错误！";
                    }


                    break;
                case "W":

                    if (comType.equals("G")) {
                        //玩家【W水】电脑【G草】→【电脑赢】
                        winner = -1;

                    } else if (comType.equals("F")) {
                        //玩家【W水】电脑【F火】→【玩家赢】
                        winner = 1;
                    } else {
                        System.out.println("不可能走到这里");
                        battleMsg = "严重错误！";
                    }


                    break;


                default:
            }


            if (winner == 1) {//玩家赢
                //计算伤害
                comHurt = playerAtt * -2;//电脑双倍伤害
                playerHurt = 0;//玩家不受伤

            } else if (winner == -1) {//电脑赢
                //计算伤害
                comHurt = 0;//电脑不受伤
                playerHurt = comAtt * -2;//玩家双倍伤害
            } else {
                System.out.println("不可能走到这里 严重错误!—— winner = " + winner);
            }

        }
        //设置战斗信息
        battleMsg = battleMsg + "玩家的【" + playerPm.getName() + "】对电脑的【" + comPm.getName() + "】造成【" + comHurt * -1 + "】点伤害\n" +
                "电脑的【" + comPm.getName() + "】对玩家的【" + playerPm.getName() + "】造成【" + playerHurt * -1 + "】点伤害";


        map.put("comHurt", comHurt);
        map.put("playerHurt", playerHurt);
        map.put("battleMsg", battleMsg);
        map.put("winner", winner);


        System.out.println("map = " + map);
        return map;
    }


}
