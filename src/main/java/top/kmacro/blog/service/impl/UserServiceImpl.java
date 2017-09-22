package top.kmacro.blog.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.kmacro.blog.dao.UserDao;
import top.kmacro.blog.model.User;
import top.kmacro.blog.service.UserService;

/**
 * Created by Zhangkh on 2017-09-01.
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public User findByPhoneAndCode(String phone, String code) {
        return userDao.findByPhoneAndCode(phone,code);
    }

    @Override
    public User findByPhone(String phone) {
        return userDao.findByPhone(phone);
    }

    @Override
    public User findByToken(String token) {
        return userDao.findByToken(token);
    }

    @Override
    public User save(User user) {
        return userDao.save(user);
    }
}
