package top.kmacro.blog.dao;

import org.springframework.data.repository.CrudRepository;
import top.kmacro.blog.model.User;

/**
 * Created by ms-zk on 2017-08-20.
 */
public interface UserDao extends CrudRepository<User,String> {
    User findByPhone(String phone);
    User findByPhoneAndCode(String phone,String code);
    User findByToken(String token);
}
