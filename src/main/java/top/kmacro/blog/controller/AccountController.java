package top.kmacro.blog.controller;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import top.kmacro.blog.model.vo.Response;
import top.kmacro.blog.model.vo.user.LoginResultVo;
import top.kmacro.blog.model.vo.user.LoginVo;
import top.kmacro.blog.model.vo.user.ShowVo;
import top.kmacro.blog.security.IgnoreSecurity;
import top.kmacro.blog.security.TokenManager;
import top.kmacro.blog.service.UserService;
import top.kmacro.blog.utils.CommonUtils;
import top.kmacro.blog.model.User;

import javax.validation.Valid;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

/**
 * Created by Zhangkh on 2017-08-27.
 */
@RestController
public class AccountController extends BaseController {

    @Autowired
    private UserService userService;

    @Autowired
    private TokenManager tokenManager;

    @IgnoreSecurity
    @PostMapping("/login")
    public Response login(@RequestBody @Valid LoginVo loginVo, BindingResult result) {
        if (result.hasErrors()) {
            return FormatValidMessage(result);
        }

        User user = userService.findByPhoneAndCode(loginVo.getPhone(), loginVo.getCode());
        if (user == null) {
            return new Response(-20,"短信验证码错误");
        } else if (user.getOverTime().before(new Date())) {
            return new Response(-21,"短信验证码超时");
        } else {
            Response response = new Response();
            if (user.getAuth() == false) {
                user.setAuth(true);
                response.setCode(-30);
                response.setMsg("新注册用户");
            } else {
                response.setCode(0);
                response.setMsg("登录成功");
            }

            // 清除原Token
            if(user.getToken() != null){
                tokenManager.clearToken(user.getToken());
            }
            String token = tokenManager.createToken(user.getId());
            user.setToken(token);
            // 短信验证码失效
            user.setOverTime(new Date());
            userService.save(user);

            LoginResultVo loginResultVo = new LoginResultVo();
            BeanUtils.copyProperties(user, loginResultVo);

            response.setData(loginResultVo);
            return response;
        }
    }

    @IgnoreSecurity
    @GetMapping("/send_code/{phone}")
    public Response sendCode(@PathVariable("phone") String phone) {
        if (StringUtils.isEmpty(phone) || !Pattern.matches("^1[3|5|7|8]\\d{9}$", phone)) {
            return new Response(-10,"手机号错误");
        } else {
            String code = CommonUtils.uuid().substring(0, 6);

            //TODO:发送短信验证码

            User user = userService.findByPhone(phone);
            Date currentTime = new Date();
            if (user == null) {
                user = new User();
                user.setPhone(phone);
                user.setAuth(false);
                user.setSignTime(currentTime);
            }

            currentTime.setTime(new Date().getTime() + 30 * 60 * 1000);
            user.setOverTime(currentTime);
            user.setCode(code);
            userService.save(user);

            //TODO:便于调试，发布应用时删除响应信息中的验证码
//            return new Response(0,"验证码发送成功");
            return new Response(0,"验证码发送成功", code);
        }
    }

    @IgnoreSecurity
    @GetMapping("/get_user/{phone}")
    public Response getUser(@PathVariable("phone") String phone) {
        ShowVo showVo = userService.getUserByPhone(phone);
        if(showVo == null){
            return new Response(-10,"手机号不存在");
        }
        return new Response(0,"success", showVo);
    }
}
