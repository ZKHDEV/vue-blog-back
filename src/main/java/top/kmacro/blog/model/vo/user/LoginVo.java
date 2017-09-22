package top.kmacro.blog.model.vo.user;

import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Pattern;

/**
 * Created by Zhangkh on 2017-09-02.
 */
public class LoginVo {
//    @NotEmpty(message = "-10:手机号错误")
//    @Pattern(regexp = "^1[3|5|7|8]\\d{9}$", message = "-10:手机号错误")
    private String phone;
//    @NotEmpty(message = "-20:短信验证码错误")
    private String code;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
