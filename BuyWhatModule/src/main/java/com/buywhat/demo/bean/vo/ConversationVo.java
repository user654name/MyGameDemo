package com.buywhat.demo.bean.vo;

import com.buywhat.demo.bean.Message;
import com.buywhat.demo.bean.User;

/**
 * 这个类创建的对象代表某个用户的一个会话
 */
public class ConversationVo {

    //最新消息对应的信息
    private Message conversation;//查message表
    //未读消息数目
    private Integer unread;//查message表
    //该对话交互的对象
    private User user;//查user表
    //该会话的消息总数
    private Integer messageCount;//查message表


    public Message getConversation() {
        return conversation;
    }

    public void setConversation(Message conversation) {
        this.conversation = conversation;
    }

    public Integer getUnread() {
        return unread;
    }

    public void setUnread(Integer unread) {
        this.unread = unread;
    }

    @Override
    public String toString() {
        return "ConversationVo{" +
                "conversation=" + conversation +
                ", unread=" + unread +
                ", user=" + user +
                ", messageCount=" + messageCount +
                '}';
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Integer getMessageCount() {
        return messageCount;
    }

    public void setMessageCount(Integer messageCount) {
        this.messageCount = messageCount;
    }

}
