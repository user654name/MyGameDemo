package com.buywhat.demo.bean.Game;

public class BattleInfo {

    //对战Pm情况
    Integer p1Id;
    Integer p2Id;
    Integer p3Id;
    Integer p4Id;
    Integer p5Id;
    Integer p6Id;

    //玩家血量
    Integer player1Hp;
    Integer player2Hp;

    //玩家选择战斗的精灵ID
    Integer chooseId;

    //战斗信息
    String battleMsg;

    public String getBattleMsg() {
        return battleMsg;
    }

    public void setBattleMsg(String battleMsg) {
        this.battleMsg = battleMsg;
    }

    public Integer getP1Id() {
        return p1Id;
    }

    public void setP1Id(Integer p1Id) {
        this.p1Id = p1Id;
    }

    public Integer getP2Id() {
        return p2Id;
    }

    public void setP2Id(Integer p2Id) {
        this.p2Id = p2Id;
    }

    public Integer getP3Id() {
        return p3Id;
    }

    public void setP3Id(Integer p3Id) {
        this.p3Id = p3Id;
    }

    public Integer getP4Id() {
        return p4Id;
    }

    public void setP4Id(Integer p4Id) {
        this.p4Id = p4Id;
    }

    public Integer getP5Id() {
        return p5Id;
    }

    public void setP5Id(Integer p5Id) {
        this.p5Id = p5Id;
    }

    public Integer getP6Id() {
        return p6Id;
    }

    public void setP6Id(Integer p6Id) {
        this.p6Id = p6Id;
    }

    public Integer getPlayer1Hp() {
        return player1Hp;
    }

    public void setPlayer1Hp(Integer player1Hp) {
        this.player1Hp = player1Hp;
    }

    public Integer getPlayer2Hp() {
        return player2Hp;
    }

    public void setPlayer2Hp(Integer player2Hp) {
        this.player2Hp = player2Hp;
    }

    public Integer getChooseId() {
        return chooseId;
    }

    public void setChooseId(Integer chooseId) {
        this.chooseId = chooseId;
    }

    @Override
    public String toString() {
        return "BattleInfo{" +
                "p1Id=" + p1Id +
                ", p2Id=" + p2Id +
                ", p3Id=" + p3Id +
                ", p4Id=" + p4Id +
                ", p5Id=" + p5Id +
                ", p6Id=" + p6Id +
                ", player1Hp=" + player1Hp +
                ", player2Hp=" + player2Hp +
                ", chooseId=" + chooseId +
                '}';
    }
}
