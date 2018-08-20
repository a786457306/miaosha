package com.imooc.miaosha.service;

import com.imooc.miaosha.dao.UserDao;
import com.imooc.miaosha.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * UserService
 *
 * @author Daydreamer
 * @date 2018/8/17 15:55
 */

@Service
public class UserService {

    @Autowired
    UserDao userDao;

    public User getById(int id){
        return userDao.getById(id);
    }

    //事务，插入重复id的数据。如果事务起作用，则u1是无法插入的，如果不起作用，u1可以插入。
    @Transactional
    public boolean tx(){
        User u1 = new User();
        u1.setId(2);
        u1.setName("2222");
        userDao.insert(u1);

        User u2 = new User();
        u1.setId(1);
        u1.setName("1111");
        userDao.insert(u2);

        return true;
    }
}
