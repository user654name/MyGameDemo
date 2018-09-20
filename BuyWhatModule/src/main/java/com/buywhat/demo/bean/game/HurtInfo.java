package com.buywhat.demo.bean.game;

public class HurtInfo {
    String info1;
    String color1;
    String info2;
    String color2;

    @Override
    public String toString() {
        return "HurtInfo{" +
                "info1='" + info1 + '\'' +
                ", color1='" + color1 + '\'' +
                ", info2='" + info2 + '\'' +
                ", color2='" + color2 + '\'' +
                '}';
    }

    public String getInfo1() {
        return info1;
    }

    public void setInfo1(String info1) {
        this.info1 = info1;
    }

    public String getColor1() {
        return color1;
    }

    public void setColor1(String color1) {
        this.color1 = color1;
    }

    public String getInfo2() {
        return info2;
    }

    public void setInfo2(String info2) {
        this.info2 = info2;
    }

    public String getColor2() {
        return color2;
    }

    public void setColor2(String color2) {
        this.color2 = color2;
    }
}
