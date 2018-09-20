package com.buywhat.demo.bean.game;

import java.util.Date;

public class TeamGameRecord {
    private Integer id;

    private Integer userId;


    private String name;//玩家名字 根据ID查出来的

    private Double winRate;

    private Integer win;

    private Integer total;

    private Date firstGameDate;

    private Date lastGameDate;


    public Double getWinRate() {
        return winRate;
    }

    public void setWinRate(Double winRate) {
        this.winRate = winRate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getWin() {
        return win;
    }

    public void setWin(Integer win) {
        this.win = win;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Date getFirstGameDate() {
        return firstGameDate;
    }

    public void setFirstGameDate(Date firstGameDate) {
        this.firstGameDate = firstGameDate;
    }

    public Date getLastGameDate() {
        return lastGameDate;
    }

    public void setLastGameDate(Date lastGameDate) {
        this.lastGameDate = lastGameDate;
    }

    @Override
    public String toString() {
        return "TeamGameRecord{" +
                "id=" + id +
                ", userId=" + userId +
                ", name='" + name + '\'' +
                ", winRate=" + winRate +
                ", win=" + win +
                ", total=" + total +
                ", firstGameDate=" + firstGameDate +
                ", lastGameDate=" + lastGameDate +
                '}';
    }
}