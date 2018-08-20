package com.imooc.miaosha.util;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * MD5Util
 *
 * @author Daydreamer
 * @date 2018/8/20 15:59
 */
public class MD5Util {
    public static String md5(String src){
        return DigestUtils.md5Hex(src);
    }
}
