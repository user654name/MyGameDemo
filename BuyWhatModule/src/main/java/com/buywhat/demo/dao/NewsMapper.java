package com.buywhat.demo.dao;

import com.buywhat.demo.bean.News;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface NewsMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(News record);

    int insertSelective(News record);

    News selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(News record);

    int updateByPrimaryKey(News record);

    /**
     * 返回所有消息列表
     *
     * @return
     */
    List<News> selectAll();
}