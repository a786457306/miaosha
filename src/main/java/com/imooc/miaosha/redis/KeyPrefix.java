package com.imooc.miaosha.redis;

/**
 * Prefix
 *
 * @author Daydreamer
 * @date 2018/8/17 21:03
 */
public interface KeyPrefix {

    public int expireSeconds();

    public String getPrefix();
}
