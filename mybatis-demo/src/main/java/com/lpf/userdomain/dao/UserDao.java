package com.lpf.userdomain.dao;


import com.lpf.userdomain.model.UserDomain;

import java.util.List;

public interface UserDao {


    int insert(UserDomain record);



    List<UserDomain> selectUsers();
}