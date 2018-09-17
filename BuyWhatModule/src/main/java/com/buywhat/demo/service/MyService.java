package com.buywhat.demo.service;

import com.buywhat.demo.bean.*;
import com.buywhat.demo.bean.vo.CommentVo;
import com.buywhat.demo.bean.vo.ConversationVo;
import com.buywhat.demo.bean.vo.MessageVo;
import com.buywhat.demo.bean.vo.Vo;

import java.util.List;

public interface MyService {


    /**
     * 执行注册
     *
     * @param user
     * @return
     */
    User doRegister(User user);

    /**
     * 执行登录
     *
     * @param user
     * @return
     */
    User doLogin(User user);

    /**
     * 增加一条消息
     *
     * @param news
     * @return
     */
    Integer addNews(News news);

    /**
     * 获取所有消息 存放在VoList中
     *
     * @return 所有消息
     */
    List<Vo> getAllNewsToVoList();

    /**
     * 返回指定消息
     *
     * @param newsId
     * @return
     */
    News findNewsById(Integer newsId);

    /**
     * 根据消息的作者id 返回作者的用户信息
     *
     * @param userId
     * @return
     */
    User findAuthorById(Integer userId);

    /**
     * 添加用户评论的操作
     *
     * @param comment
     * @return
     */
    Integer addComment(Comment comment);

    /**
     * 查找这条消息对应的所有评论内容
     *
     * @param newsId
     * @return
     */
    List<CommentVo> findCommentVoByNewsId(Integer newsId);

    /**
     * （在个人信息页面）更改用户头像
     *
     * @param newHeadUrl 头像链接
     * @param userId     用户ID
     * @return 更改结果
     */
    boolean changeHeadUrl(String newHeadUrl, Integer userId);

    /**
     * 根据用户名找用户
     *
     * @param toName
     * @return
     */
    User findUserByName(String toName);

    /**
     * 用户发站内信
     *
     * @param message
     * @return
     */
    boolean sendMessage(Message message);

    /**
     * 查找这个用户的所有对话消息
     *
     * @param id
     * @return
     */
    List<ConversationVo> getAllConversationVoByUserId(Integer id);

    /**
     * 将指定的一组对话的所有【消息】和【发送者信息】查找到
     * 封装到Vo中返回给Controller
     *
     * @param conversationId 对话的ID
     * @return Vo信息 这组对话的所有条信息
     */
    List<MessageVo> findMessageByConversationId(String conversationId);
}