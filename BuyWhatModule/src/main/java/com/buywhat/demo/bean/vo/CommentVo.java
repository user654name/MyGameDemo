package com.buywhat.demo.bean.vo;

import com.buywhat.demo.bean.Comment;
import com.buywhat.demo.bean.User;

public class CommentVo {
    User user;
    Comment comment;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Comment getComment() {
        return comment;
    }

    public void setComment(Comment comment) {
        this.comment = comment;
    }
}
