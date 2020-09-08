package com.buywhat.demo.bean.game;

public class Pokemon {
    private Integer pid;

    private Integer hp;


    @Override
    public String toString() {
        return "Pokemon{" +
                "pid=" + pid +
                ", hp=" + hp +
                '}';
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public Integer getHp() {
        return hp;
    }

    public void setHp(Integer hp) {
        this.hp = hp;
    }
}