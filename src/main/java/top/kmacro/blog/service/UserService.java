package top.kmacro.blog.service;

import top.kmacro.blog.model.User;

/**
 * Created by Zhangkh on 2017-09-01.
 */
public interface UserService {
    User findByPhoneAndCode(String phone, String code);
    User findByPhone(String phone);
    User findByToken(String token);
    User save(User user);
}
