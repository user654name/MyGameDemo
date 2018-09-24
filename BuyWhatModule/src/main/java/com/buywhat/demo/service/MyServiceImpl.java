package com.buywhat.demo.service;


import com.buywhat.demo.bean.*;
import com.buywhat.demo.bean.vo.CommentVo;
import com.buywhat.demo.bean.vo.ConversationVo;
import com.buywhat.demo.bean.vo.MessageVo;
import com.buywhat.demo.bean.vo.Vo;
import com.buywhat.demo.dao.CommentMapper;
import com.buywhat.demo.dao.MessageMapper;
import com.buywhat.demo.dao.NewsMapper;
import com.buywhat.demo.dao.UserMapper;
import com.sun.org.apache.bcel.internal.generic.NEW;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MyServiceImpl implements MyService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private NewsMapper newsMapper;

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private MessageMapper messageMapper;


    /**
     * 注册新用户
     *
     * @param userReg
     * @return
     */
    @Override
    public User doRegister(User userReg) {

        User userSelected = null;
        //查库 看用户名是否可用
        User userSelect = userMapper.selectByName(userReg.getName());

        if (userSelect == null) {//如果用户名可用 就执行插入返回true

            int insert = userMapper.insert(userReg); //执行插入

            //true表示插入成功
            boolean regOk = insert > 0 ? true : false;
            if (regOk) {//如果插入成功，再执行查询【为了获取它的id】
                userSelected = userMapper.selectByName(userReg.getName());
            }
        }

        //如果用户名不可用 就返回 userSelected = null
        return userSelected;
    }

    /**
     * 执行登录操作
     *
     * @param userLogin 用户提交的等阿鲁信息
     * @return 返回null代表密码不匹配 发挥
     */
    @Override
    public User doLogin(User userLogin) {

        //查库 看看用户信息是否匹配 查询信息为空则说明【用户名不存在】
        User userSelect = userMapper.selectByName(userLogin.getName());

        if (userSelect != null) {//查询信息非空 说明 【用户名存在】 再继续验证密码即可

            if (userLogin.getPassword().equals(userSelect.getPassword())) {//如果密码匹配
                //暂时什么都不做
            } else {//如果不匹配 返回空 代表密码不对
                userSelect = null;
            }
        } else {//代表【用户名不存在】
            userSelect = null;
        }
        return userSelect;
    }

    /**
     * 插入新闻
     *
     * @param news
     * @return
     */
    @Override
    public Integer addNews(News news) {
        return newsMapper.insert(news);
    }


    /**
     * 获取所有消息 存在VoList中
     *
     * @return
     */
    @Override
    public List<Vo> getAllNewsToVoList() {
        List<Vo> vos = new ArrayList<>();

        List<News> newsList = newsMapper.selectAll();
        for (News news : newsList) {
            Vo vo = new Vo();
            User user = userMapper.selectByPrimaryKey(news.getUserId());
            //查询并设置点赞数目2018年9月24日 17:01:00
            Jedis jedis = new Jedis();
            Long likeCount = jedis.scard("newsLike" + news.getId());
            news.setLikeCount(Integer.parseInt(likeCount+""));

            vo.setNews(news);
            vo.setUser(user);
            vos.add(vo);
        }


        return vos;
    }

    /**
     * 返回这条消息信息
     *
     * @param newsId
     * @return
     */
    @Override
    public News findNewsById(Integer newsId) {
        return newsMapper.selectByPrimaryKey(newsId);
    }

    /**
     * 根据id查询用户信息
     *
     * @param userId
     * @return
     */
    @Override
    public User findAuthorById(Integer userId) {
        return userMapper.selectByPrimaryKey(userId);
    }

    /**
     * 新增评论 插入到数据库
     *
     * @param comment
     * @return
     */
    @Override
    public Integer addComment(Comment comment) {

        //插入评论
        int insert = commentMapper.insert(comment);

        if (insert == 1) {//如果插入评论成功 更新评论数目 即更新newstable中comment_count的值
            //先确定要更新的消息的newsid
            Integer newsId = comment.getNewsId();
            //再去查询这个id对应了多少评论
            Integer commentCount = commentMapper.countByNewsId(newsId);
            //将这个数字update到newstable中
            News record = new News();
            record.setId(newsId);//主键
            record.setCommentCount(commentCount);//
            Integer update = newsMapper.updateByPrimaryKeySelective(record);

            String result = update == 1 ? "更新成功" : "更新失败";
            /*
                以后可以设置4个返回值，分别代表增加评论和更新评论数 成功的情况
                这个版本暂时简单处理
            */
        }


        return insert;
    }

    /**
     * 返回这条消息 对应的所有评论
     *
     * @param newsId
     * @return
     */
    @Override
    public List<CommentVo> findCommentVoByNewsId(Integer newsId) {
        List<CommentVo> comments = new ArrayList<>();
        List<Comment> commentList = commentMapper.selectByNewsId(newsId);
        for (Comment comment : commentList) {
            CommentVo commentVo = new CommentVo();
            commentVo.setComment(comment);
            commentVo.setUser(findAuthorById(comment.getUserId()));
            comments.add(commentVo);
        }

        return comments;
    }

    /**
     * 更改当前用户头像
     *
     * @param newHeadUrl
     * @return
     */
    @Override
    public boolean changeHeadUrl(String newHeadUrl, Integer userId) {

        User record = new User();
        record.setId(userId);
        record.setHeadUrl(newHeadUrl);

        Integer result = userMapper.updateByPrimaryKeySelective(record);
        return result == 1 ? true : false;
    }

    /**
     * 根据用户名找用户
     *
     * @param toName
     * @return
     */
    @Override
    public User findUserByName(String toName) {
        User user = userMapper.selectByName(toName);
        return user;
    }

    /**
     * 用户发站内信
     *
     * @param message
     * @return
     */
    @Override
    public boolean sendMessage(Message message) {
        Integer insert = messageMapper.insert(message);
        return insert == 1 ? true : false;
    }

    /**
     * 查询这个用户所有会话信息 的集合
     * （Message是每个会话的最新一条信息）
     *
     * @param id id是当前发送请求的用户的ID （session中读出的）
     * @return
     */
    @Override
    public List<ConversationVo> getAllConversationVoByUserId(Integer id) {

        //根据UserId 设置查询的map参数
        Map params = new HashMap();
        params.put("before", id + "|_%");
        params.put("after", "%|_" + id);

        //查询获得和每个人的最新消息列表（作为会话信息）
        List<Message> messages = messageMapper.selectConversationByUserId(params);

        //创建会话对象集合
        List<ConversationVo> conversationVos = new ArrayList<>();

        for (Message message : messages) {
            //创建会话对象
            ConversationVo conversationVo = new ConversationVo();
            Integer fromId = message.getFromId();//发信人ID
            Integer toId = message.getToId();//收信人ID
            Integer otherUserId = null;//对方的ID
            //根据自己的ID 判断发件人和收件人中哪个是对方ID
            if (fromId.equals(id)) {//如果自己是发件人
                otherUserId = toId;//则收件人是对方
            } else if (toId.equals(id)) {//如果自己是收件人
                otherUserId = fromId;//则发件人是对方
            } else {
                System.out.println("不可能走到这里！");
            }//已经确认对方ID
            //根据对方ID查到对方资料
            User otherUser = userMapper.selectByPrimaryKey(otherUserId);

            //根据当前message的会话编号 查询消息数目
            Integer messageCount = messageMapper.countMessageByConversationId(message.getConversationId());

            //查询此次会话Message中未读消息数量
            Integer unread = messageMapper.countMessageUnreadByConversationId(message.getConversationId());

            //设置首页显示的该条会话的所有信息
            conversationVo.setConversation(message);
            conversationVo.setUser(otherUser);
            conversationVo.setMessageCount(messageCount);
            conversationVo.setUnread(unread);

            //本条conversationVo已配置完毕 装入List
            conversationVos.add(conversationVo);
        }


        return conversationVos;
    }

    @Override
    public List<MessageVo> findMessageByConversationId(String conversationId) {

        List<MessageVo> messageVos = new ArrayList<>();
        List<Message> messages = messageMapper.selectMessageByConversationId(conversationId);

        for (Message message : messages) {
            MessageVo messageVo = new MessageVo();
            Integer senderId = message.getFromId();//根据信息获得作者id
            User sender = userMapper.selectByPrimaryKey(senderId);//将作者放入vo

            messageVo.setMessage(message);//将信息放入vo
            messageVo.setHeadUrl(sender.getHeadUrl());
            messageVo.setName(sender.getName());
            messageVo.setUserId(sender.getId());


            messageVos.add(messageVo);//将这次循环的vo对象放入VOList
        }

        return messageVos;
    }


}

