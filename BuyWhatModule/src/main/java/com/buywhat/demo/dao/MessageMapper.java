package com.buywhat.demo.dao;

import com.buywhat.demo.bean.Message;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface MessageMapper {
    int deleteByPrimaryKey(Integer id);

    /**
     * 向表中插入新消息
     *
     * @param record
     * @return
     */
    Integer insert(Message record);

    int insertSelective(Message record);

    Message selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Message record);

    int updateByPrimaryKey(Message record);

    /**
     * 查询【这个用户】和【每个用户】往来信息的【最新一条消息列表】
     *
     * @param params 用户id的两种位置【id_XX】和【id_XX】
     * @return 【最新一条消息列表】
     */
    List<Message> selectConversationByUserId(Map params);

    /**
     * 根据当前message的会话编号 查询对应的消息数目
     *
     * @param conversationId
     * @return
     */
    Integer countMessageByConversationId(String conversationId);

    /**
     * 查询此次会话Message中未读消息数量
     *
     * @param conversationId
     * @return
     */
    Integer countMessageUnreadByConversationId(String conversationId);



}