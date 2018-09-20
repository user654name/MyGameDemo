package com.buywhat.demo.bean.game;

import java.util.Date;

public class TeamGameRecord {
    private Integer id;

    private Integer userId;

    private Integer win;

    private Integer total;

    private Date firstGameDate;

    private Date lastGameDate;

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
}