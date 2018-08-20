package com.imooc.miaosha.redis;

/**
 * UserKey
 *
 * @author Daydreamer
 * @date 2018/8/17 21:47
 */
public class UserKey extends BasePrefix{
    //永不过期
    private UserKey(String prefix) {
        super(0, prefix);
    }

    public UserKey(int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }

    public static UserKey getById = new UserKey("id");
}
