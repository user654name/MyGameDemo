package com.buywhat.demo.service;

import com.buywhat.demo.bean.game.HurtInfo;
import com.buywhat.demo.bean.game.Pokemon2;
import com.buywhat.demo.bean.game.TeamGameRecord;
import com.buywhat.demo.dao.Pokemon2Mapper;
import com.buywhat.demo.dao.TeamGameRecordMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.security.pkcs11.P11TlsKeyMaterialGenerator;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class GameServiceImpl implements GameService {

    @Autowired
    Pokemon2Mapper pokemon2Mapper;

    @Autowired
    TeamGameRecordMapper recordMapper;

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

    /**
     * @param comPmId    电脑选择的精灵ID
     * @param playerPmId 玩家选择的精灵ID
     * @return
     */
    @Override
    public Map hitEachOther(Integer comPmId, Integer playerPmId) {
        Map map = new HashMap();

        /**
         * winner表示这回合的游戏赢家
         * 0平局
         * 1玩家1赢
         * -1电脑(玩家2)赢
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
        HurtInfo p1HurtInfo = new HurtInfo();
        HurtInfo p2HurtInfo = new HurtInfo();

        //根据战况 设置 伤害信息
        setHurtInfo(winner, comHurt, playerHurt, p1HurtInfo, p2HurtInfo);

        //将伤害信息放入
        map.put("p1HurtInfo", p1HurtInfo);
        map.put("p2HurtInfo", p2HurtInfo);


        //设置战斗信息
        battleMsg = battleMsg + "玩家的【" + playerPm.getName() + "】对电脑的【" + comPm.getName() + "】造成【" + comHurt * -1 + "】点伤害\n" +
                "电脑的【" + comPm.getName() + "】对玩家的【" + playerPm.getName() + "】造成【" + playerHurt * -1 + "】点伤害\n";


        map.put("comHurt", comHurt);
        map.put("playerHurt", playerHurt);
        map.put("battleMsg", battleMsg);
        map.put("winner", winner);


        System.out.println("map = " + map);
        return map;
    }

    /**
     * 根据胜负情况设置伤害信息
     *
     * @param winner     战况
     * @param comHurt    P2 HP损伤
     * @param playerHurt P1 HP损伤
     * @param p1HurtInfo P1 伤害信息
     * @param p2HurtInfo P2 伤害信息
     */
    private void setHurtInfo(Integer winner, Integer comHurt, Integer playerHurt, HurtInfo p1HurtInfo, HurtInfo p2HurtInfo) {

        String midColor = "blue";
        String goodColor = "green";
        String badColor = "red";

        switch (winner) {
            case 0://平局，均势局
                p1HurtInfo.setInfo1("均势局");
                p1HurtInfo.setColor1(midColor);

                p1HurtInfo.setInfo2("HP" + playerHurt);
                p1HurtInfo.setColor2(midColor);


                p2HurtInfo.setInfo1("均势局");
                p2HurtInfo.setColor1(midColor);

                p2HurtInfo.setInfo2("HP" + comHurt);
                p2HurtInfo.setColor2(midColor);

                break;
            case 1:// P1回合胜
                p1HurtInfo.setInfo1("GOOD");
                p1HurtInfo.setColor1(goodColor);

                p1HurtInfo.setInfo2("无伤");
                p1HurtInfo.setColor2(goodColor);


                if (comHurt == -10) {
                    p2HurtInfo.setInfo1("重伤");
                } else {
                    p2HurtInfo.setInfo1("轻伤");
                }
                p2HurtInfo.setColor1(badColor);

                p2HurtInfo.setInfo2("HP" + comHurt);
                p2HurtInfo.setColor2(badColor);

                break;

            case -1://P2回合胜
                p2HurtInfo.setInfo1("GOOD");
                p2HurtInfo.setColor1(goodColor);

                p2HurtInfo.setInfo2("无伤");
                p2HurtInfo.setColor2(goodColor);


                if (playerHurt == -10) {
                    p1HurtInfo.setInfo1("重伤");
                } else {
                    p1HurtInfo.setInfo1("轻伤");
                }
                p1HurtInfo.setColor1(badColor);

                p1HurtInfo.setInfo2("HP" + playerHurt);
                p1HurtInfo.setColor2(badColor);


                break;
            default:
                System.out.println("不可能走到这里");
                break;
        }

    }

    /**
     * 根据玩家获胜结果
     * 在数据库里更新各自战绩
     *
     * @param player1Id 玩家1 id
     * @param player2Id 玩家2 id 通常是电脑
     * @param playerWin 玩家1 是否获胜
     * @return
     */
    @Override
    public boolean updateTeamGameRecord(Integer player1Id, Integer player2Id, Integer playerWin) {
        boolean winnerRecord = false;
        boolean loserRecord = false;
        //读取游戏结束的信息
        if (playerWin == 1) {//玩家1获胜
            winnerRecord = updateTeamGameRecord(player1Id, true);
            loserRecord = updateTeamGameRecord(player2Id, false);
        } else if (playerWin == 2) {//玩家2获胜
            winnerRecord = updateTeamGameRecord(player2Id, true);
            loserRecord = updateTeamGameRecord(player1Id, false);
        } else if (playerWin == 0) {//平局→即为玩家都失败
            winnerRecord = updateTeamGameRecord(player2Id, false);
            loserRecord = updateTeamGameRecord(player1Id, false);
        } else {
            System.out.println("不可能走到这个方法的！【updateTeamGameRecord】");
        }

        //返回插入结果 两次操作都成功则为true
        return winnerRecord && loserRecord;
    }


    /**
     * 根据玩家ID查新对应的战绩
     *
     * @param playerId
     * @return
     */
    @Override
    public TeamGameRecord findTeamGameRecordByUserId(Integer playerId) {

        return recordMapper.selectByPrimaryKey(playerId);
    }

    /**
     * 根据胜利结果 更新指定玩家的战绩
     *
     * @param playerId 玩家的用户ID
     * @param isWinner 玩家获胜的情况
     * @return 信息是否插入成功
     */
    private boolean updateTeamGameRecord(Integer playerId, Boolean isWinner) {
        //对于胜利者的操作
        //先查一下有没有记录
        TeamGameRecord playerRecord = recordMapper.selectByUserId(playerId);

        Integer result = null;//查询的结果（更新或插入的条数）

        if (playerRecord != null) {//如果有记录就进行更改

            playerRecord.setLastGameDate(new Date());//保存玩家最后一局游戏时间
            playerRecord.setTotal(playerRecord.getTotal() + 1);//总场次+1

            if (isWinner) {//这个玩家是胜者
                playerRecord.setWin(playerRecord.getWin() + 1);//胜场+1
            }//如果不是胜者 一切继续

            result = recordMapper.updateByPrimaryKeySelective(playerRecord);//将新的信息插入

            if (result != 1) {//记录更新信息是否成功
                System.out.println("update战绩时发生错误！！！");
            }

        } else {//如果没有记录就插入一条新的
            //配置战绩的基本信息

            Date timeNow = new Date();
            playerRecord = new TeamGameRecord();
            playerRecord.setTotal(1);
            playerRecord.setFirstGameDate(timeNow);//2018年9月20日 14:32:22出错
            playerRecord.setLastGameDate(timeNow);
            playerRecord.setUserId(playerId);

            if (isWinner) {//如果玩家是胜者
                playerRecord.setWin(1);//增加一场胜场
            } else {//这个玩家第一局就输了
                playerRecord.setWin(0);//胜场为0
            }

            //插入数据
            result = recordMapper.insert(playerRecord);
            if (result != 1) {//记录插入信息是否成功
                System.out.println("insert战绩时发生错误！！！");
            }

        }
        //返回是否更新成功
        return result == 1 ? true : false;
    }


}
