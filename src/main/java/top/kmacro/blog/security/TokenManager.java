package top.kmacro.blog.security;

/**
 * Created by Zhangkh on 2017-09-06.
 */
public interface TokenManager {
    String createToken(String userId);
    Boolean checkToken(String token);
    void flushToken(String token);
    void clearToken(String token);
    String currentToken();
}
