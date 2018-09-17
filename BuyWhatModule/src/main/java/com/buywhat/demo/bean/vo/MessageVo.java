package com.buywhat.demo.bean.vo;

import com.buywhat.demo.bean.Message;

public class MessageVo {

    Message message;//这条消息
    Integer userId;//这条消息的发信人
    String headUrl;//
    String  name;

    @Override
    public String toString() {
        return "MessageVo{" +
                "message=" + message +
                ", userId=" + userId +
                ", headUrl='" + headUrl + '\'' +
                ", name='" + name + '\'' +
                '}';
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getHeadUrl() {
        return headUrl;
    }

    public void setHeadUrl(String headUrl) {
        this.headUrl = headUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
