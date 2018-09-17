package com.buywhat.demo.dao;

import com.buywhat.demo.bean.Comment;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;


@Mapper
@Repository
public interface CommentMapper {
    int deleteByPrimaryKey(Integer id);

    /**
     * 插入评论信息
     *
     * @param record
     * @return
     */
    int insert(Comment record);

    int insertSelective(Comment record);


    Comment selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Comment record);

    int updateByPrimaryKey(Comment record);

    /**
     * 根据消息编号返回相关评论信息
     *
     * @param newsId
     * @return
     */
    List<Comment> selectByNewsId(Integer newsId);

    /**
     * 查询指定消息 内的评论数目
     *
     * @param newsId 消息的编号
     * @return 消息当前的 评论数目
     */
    Integer countByNewsId(Integer newsId);
}