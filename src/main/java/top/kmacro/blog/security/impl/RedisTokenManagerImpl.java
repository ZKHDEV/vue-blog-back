package top.kmacro.blog.security.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;
import top.kmacro.blog.security.TokenManager;
import top.kmacro.blog.utils.CommonUtils;

import java.util.concurrent.TimeUnit;

/**
 * Created by Zhangkh on 2017-09-07.
 */
@Component
public class RedisTokenManagerImpl implements TokenManager {

    private static ThreadLocal<String> tokenCache = new ThreadLocal<String>();

    @Autowired
    private ValueOperations<String, Object> valueOperations;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public String createToken(String userId) {
        String token = CommonUtils.uuid();
        valueOperations.set(token, userId, 7, TimeUnit.DAYS);
        return token;
    }

    @Override
    public Boolean checkToken(String token) {
        return redisTemplate.hasKey(token);
    }

    @Override
    public void flushToken(String token) {
        redisTemplate.expire(token, 7, TimeUnit.DAYS);
        tokenCache.set(token);
    }

    @Override
    public void clearToken(String token) {
        if(checkToken(token)){
            redisTemplate.delete(token);
        }
    }

    @Override
    public String currentToken() {
        return tokenCache.get();
    }
}
