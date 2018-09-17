package com.buywhat.demo.dao;

import com.buywhat.demo.bean.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface UserMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    User selectByName(String name);


    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);


}