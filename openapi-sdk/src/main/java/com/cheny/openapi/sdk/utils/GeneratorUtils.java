package com.cheny.openapi.sdk.utils;


import cn.hutool.crypto.digest.DigestAlgorithm;
import cn.hutool.crypto.digest.Digester;


/**
 * @author chen_y
 * @date 2024-06-29 13:03
 */
public class GeneratorUtils {
    public static String generateSign(String body,String secret){
        Digester md5 = new Digester(DigestAlgorithm.MD5);
        return md5.digestHex(body+secret);
    }
}
