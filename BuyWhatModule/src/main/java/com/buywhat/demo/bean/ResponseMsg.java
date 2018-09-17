package com.buywhat.demo.bean;

import javax.servlet.http.HttpSession;

public class ResponseMsg {

    Integer code;
    String msgpwd;
    String msgname;


    @Override
    public String toString() {
        return "ResponseMsg{" +
                "code=" + code +
                ", msgpwd='" + msgpwd + '\'' +
                ", msgname='" + msgname + '\'' +
                '}';
    }

    public ResponseMsg(Integer code, String msgpwd, String msgname, HttpSession session) {

        this.code = code;
        this.msgpwd = msgpwd;
        this.msgname = msgname;

    }

    public ResponseMsg() {
    }

    public ResponseMsg(Integer code, String msgpwd, String msgname) {

        this.code = code;
        this.msgpwd = msgpwd;
        this.msgname = msgname;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsgpwd() {
        return msgpwd;
    }

    public void setMsgpwd(String msgpwd) {
        this.msgpwd = msgpwd;
    }

    public String getMsgname() {
        return msgname;
    }

    public void setMsgname(String msgname) {
        this.msgname = msgname;
    }
}
