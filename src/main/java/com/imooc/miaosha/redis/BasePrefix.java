package com.imooc.miaosha.redis;

/**
 * BasePrefix
 *
 * @author Daydreamer
 * @date 2018/8/17 21:04
 */
public abstract class BasePrefix implements KeyPrefix {

    private int expireSeconds;

    private String prefix;

    //永不过期。public，因为是abstract。
    public BasePrefix(String prefix) {
        this(0, prefix);
    }

    public BasePrefix(int expireSeconds, String prefix){
        this.expireSeconds = expireSeconds;
        this.prefix = prefix;
    }

    public int expireSeconds(){//默认0代表永不过期
        return 0;
    }

    //类名拼prefix
    public String getPrefix(){
        String className = getClass().getSimpleName();
        return className + ":" + prefix;
    }
}
