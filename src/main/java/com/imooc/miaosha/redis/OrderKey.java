package com.imooc.miaosha.redis;

/**
 * OrderKey
 *
 * @author Daydreamer
 * @date 2018/8/17 21:48
 */
public class OrderKey extends BasePrefix{
    public OrderKey(int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }
}
