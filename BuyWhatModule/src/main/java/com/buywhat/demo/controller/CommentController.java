package com.buywhat.demo.controller;

import com.buywhat.demo.bean.Comment;
import com.buywhat.demo.bean.User;
import com.buywhat.demo.service.MyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.Date;

//评论相关的Controller
@Controller
public class CommentController {

    //自动注入业务对象
    @Autowired
    private MyService service;


    /**
     * 填加用户提交的评论
     *
     * @param content
     * @param newsId
     * @return
     */
    @RequestMapping("addComment")
    public String addComment(HttpSession session, String content, Integer newsId) {

        //先获取作者的用户信息信息
        User author = (User) session.getAttribute("user");

        if (author != null) {//如果用户信息存在
            //建一个bean
            Comment comment = new Comment();
            //将信息放入
            comment.setContent(content);
            comment.setNewsId(newsId);
            comment.setCreatedDate(new Date());
            comment.setUserId(author.getId());
            //执行填加评论操作
            Integer result = service.addComment(comment);
        }

        return "redirect:news/"+newsId;//重定向的方式 地址栏内容改变 刷新就不会重复提交评论！
    }
}
