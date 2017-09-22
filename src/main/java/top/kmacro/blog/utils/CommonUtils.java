package top.kmacro.blog.utils;

import java.util.UUID;

/**
 * Created by Zhangkh on 2017-08-27.
 */
public class CommonUtils {

    public static String uuid(){
        return UUID.randomUUID().toString().replace("-", "").toUpperCase();
    }
}
