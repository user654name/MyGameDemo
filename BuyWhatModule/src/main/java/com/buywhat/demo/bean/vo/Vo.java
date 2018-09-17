package com.buywhat.demo.bean.vo;

import com.buywhat.demo.bean.News;
import com.buywhat.demo.bean.User;

public class Vo {

   private News news;
   private User user;
   private Integer like;

    public News getNews() {
        return news;
    }

    public void setNews(News news) {
        this.news = news;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Integer getLike() {
        return like;
    }

    public void setLike(Integer like) {
        this.like = like;
    }
}
