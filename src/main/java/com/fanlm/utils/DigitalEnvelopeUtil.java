package com.fanlm.utils;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.util.HexUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.asymmetric.AsymmetricAlgorithm;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;
import cn.hutool.crypto.symmetric.SymmetricAlgorithm;
import cn.hutool.crypto.symmetric.SymmetricCrypto;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.SecretKey;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class DigitalEnvelopeUtil {

    public final static String KEY = "key";
    public final static String DATA = "data";
    private static String rsaPublic = "";

    public static void main(String[] args) throws Exception {
        Map<String, Object> stringObjectMap = initKey();
        String publicKey = getPublicKey(stringObjectMap);
        System.out.println(publicKey);
        String privateKey = getPrivateKey(stringObjectMap);
        System.out.println(privateKey);
//        Map<String, String> encrypt = encrypt("123445566");


        Map<String, String> map = new HashMap<>();
        map.put("channelUserId","123");
//        map.put("userName","1234");
        map.put("siteId","S20211916000001");
        map.put("productCode","zjbxjj_cps_xjg_yxcy");
        map.put("planCode","hkyxcyzssx_xjg");
        map.put("channelCode","RYL02957");
        String s = cn.hutool.json.JSONUtil.toJsonStr(map);
        Map<String, String> encrypt = encrypt("{\"orderNo\":\"210112141100005\",\"payResult\":\"0\",\"adviceCode\":\"1234\",\"policyNo\":\"11060000000216\",\"policyUrl\":\"http://xxxxxxx\",\"effectTime\":\"2020-01-21\",\"payTime\":\"2020-01-20 15:12:12\",\"channelUserId\":\"US20210200003495\",\"backReserved\":\"xxxxxxxx\",\"accountInfo\":[{\"accountAttribute\":\"F\",\"accountCode\":\"AC0011\",\"accountValue\":2400,\"policyInitFee\":24},{\"accountAttribute\":\"D\",\"accountCode\":\"AC0012\",\"accountValue\":7500,\"policyInitFee\":76},{\"accountAttribute\":\"M\",\"accountCode\":\"AC001\",\"accountValue\":10000,\"policyInitFee\":100}]}","");
        encrypt.forEach((k,v) -> {
            try {
                System.out.println(k+" "+ " "+ URLEncoder.encode(v, "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        });

        String key = URLEncoder.encode(encrypt.get("key"), "UTF-8");
        String data = URLEncoder.encode(encrypt.get("data"), "UTF-8");

        String json = decrypt(encrypt.get("data"), encrypt.get("key"));
        System.out.println(json);
    }

    public static Map<String, String> encrypt(String data ,String rsaPub) throws Exception {
        rsaPublic = rsaPub;
        log.info("【数字信封加密】需要加密的数据:" + data);
        Map<String, String> resMap = new HashMap<String, String>(3);
        SecretKey secretKey = SecureUtil.generateKey(SymmetricAlgorithm.AES.getValue());
        byte[] key = secretKey.getEncoded();
        RSA rsa = getRsaByStr();
        // 使用AES对明文进行加密
        String encryptStr = aesEncrypt(data, key);
        // 使用RSA对AES-KEY进行加密
        String encryptKey = rsaEncrypt(rsa, key);
        resMap.put(DATA, encryptStr);
        resMap.put(KEY, encryptKey);
        log.info("【数字信封加密】加密后的数据:" + JSON.toJSONString(resMap));
        return resMap;
    }

    public static String decrypt(String data, String key) {
//         1进行解密
        RSA rsa = getRsaByStr();
//         1.1使用RSA解密AES-KEY
        byte[] decryptKey = rsaDecrypt(rsa, key);
//         1.2使用AES解密报文
        String decryptStr = aesDecrypt(data, decryptKey);
        return decryptStr;
    }

    private static String aesDecrypt(String data, byte[] decryptKey) {
        //构建
        SymmetricCrypto aes = new SymmetricCrypto(SymmetricAlgorithm.AES, decryptKey);
        //解密
        byte[] encrypt = Base64.decode(data);
        String decryptStr = aes.decryptStr(encrypt);
        log.info("AES解密后的明文：" + decryptStr);
        return decryptStr;
    }

    private static byte[] rsaDecrypt(RSA rsa, String encryptKey) {
        //私钥解密
        byte[] encrypt = Base64.decode(encryptKey);
        byte[] decrypt = rsa.decrypt(encrypt, KeyType.PrivateKey);
        log.info("RSA私钥解密：" + HexUtil.encodeHexStr(decrypt));
        return decrypt;
    }
    private static String rsaEncrypt(RSA rsa, byte[] key) throws UnsupportedEncodingException {
        //公钥加密
        byte[] encrypt = rsa.encrypt(key, KeyType.PublicKey);
        String encrStr = Base64.encode(encrypt);
        log.info("RSA公钥加密:" + encrStr);
        log.info("RSA公钥加密encode：" + URLEncoder.encode(encrStr, "UTF-8"));
        return encrStr;
    }

    private static String aesEncrypt(String data, byte[] key) throws UnsupportedEncodingException {
        //构建
        SymmetricCrypto aes = new SymmetricCrypto(SymmetricAlgorithm.AES, key);
        //加密
        byte[] encrypt = aes.encrypt(data);
        String encryptStr = Base64.encode(encrypt);
        log.info("AES加密后的密文：" + encryptStr);
        log.info("AES加密后的密文encode：" + URLEncoder.encode(encryptStr, "UTF-8"));
        return encryptStr;
    }

    private static RSA getRsaByStr() {
        String PUBLIC_KEY = null;
        String PRIVATE_KEY = null;
//        if ("prod".equals(SpringUtil.getActiveProfile())) {
//            PUBLIC_KEY = RSAUtil.PUBLIC_KEY;
//            PRIVATE_KEY = RSAUtil.PRIVATE_KEY;
//        } else {
//        PUBLIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC5KlF855Yq8rpM/CNQDeoS4hyVO8q" +
//                "FQ6dpb+SvXC0CqU70myDzYoIqEaRtsNscFTvo0cLTHDsT0JiMkx0A/HWL3tVkcDar4POZW2a6HDK6cjUIOD" +
//                "2nNf2UY5aIe6c7S25NuHLz5N9dDCSd2iI/1xMmFdhYiEKlf68XXfdSRWE3MwIDAQAB";
        PUBLIC_KEY = rsaPublic;
        PRIVATE_KEY = "MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBALkqUXznliryukz8I1AN6hLiH" +
                "JU7yoVDp2lv5K9cLQKpTvSbIPNigioRpG2w2xwVO+jRwtMcOxPQmIyTHQD8dYve1WRwNqvg85lbZrocMrpyNQg4Pa" +
                "c1/ZRjloh7pztLbk24cvPk310MJJ3aIj/XEyYV2FiIQqV/rxdd91JFYTczAgMBAAECfylF3pNp8bScVaWX0i+7KnRz+rKs" +
                "ZUcLB8w0OXmcEDvQC/M//flXWu9/widdVVc1kE1ZTdHDDLvVV5CDF7jWkdtivWvf/IQFU/7bo2MlpvC2P0A5PKDkIlG25KX" +
                "GpuOwGC4JClaVfMQDh9On1XnynJyYJ8MvhpY5/wqr75EAmk0CQQD4EXpRrYNjs4cR5GoImQzrqmoet+VmiVFqeCMaHzcL0F7" +
                "FLwXUb7Up5Kdn6Pjx6lz4ovMdoQ7l6Z2CDTBI0jwlAkEAvxX1ZVg0FZKW/BtaCGuiVHfki0dIi5lqybKr/4bUJzMvZ9ccfvYa" +
                "XIUt/dASxgE2bQ1TaN81VECLWladj8WadwJBALCxAkcbJ/Lj9NkxsLTfDuztPRkUMzmNpYgIejgo87RefqJElAp0Zr2oN/UzY9" +
                "4r6HzQ1AnOpiXs+FuhuTqlRGECQCA7pQRuZ4LIEn3+YyaOeXiELOItqRgbTf8uC4N0C+98299JUv47p2C5+nMZGUGbTMICgHJtT" +
                "IKkzJz1hWiOLasCQQD2ygyl1zrZpja5euShkP1yx2IfKhOWLm9bvqRLUAZo2JfmuRn1QVwJA7NjaW3sAMc0pwCAtWPAJBJSX9TTSyNr";
        return new RSA(AsymmetricAlgorithm.RSA_ECB_PKCS1.getValue(), PRIVATE_KEY, PUBLIC_KEY);

    }

    public static final String KEY_ALGORITHM = "RSA";
    //public static final String SIGNATURE_ALGORITHM = "MD5withRSA";
    private static final String PUBLIC_KEY = "RSAPublicKey";
    private static final String PRIVATE_KEY = "RSAPrivateKey";

    //获得公钥
    public static String getPublicKey(Map<String, Object> keyMap) throws Exception {
        //获得map中的公钥对象 转为key对象
        Key key = (Key) keyMap.get(PUBLIC_KEY);
        //byte[] publicKey = key.getEncoded();
        //编码返回字符串
        return encryptBASE64(key.getEncoded());
    }

    //获得私钥
    public static String getPrivateKey(Map<String, Object> keyMap) throws Exception {
        //获得map中的私钥对象 转为key对象
        Key key = (Key) keyMap.get(PRIVATE_KEY);
        //byte[] privateKey = key.getEncoded();
        //编码返回字符串
        return encryptBASE64(key.getEncoded());
    }

    //解码返回byte
    public static byte[] decryptBASE64(String key) throws Exception {
        return (new BASE64Decoder()).decodeBuffer(key);
    }

    //编码返回字符串
    public static String encryptBASE64(byte[] key) throws Exception {
        return (new BASE64Encoder()).encodeBuffer(key);
    }

    //map对象中存放公私钥
    public static Map<String, Object> initKey() throws Exception {
        //获得对象 KeyPairGenerator 参数 RSA 1024个字节
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(KEY_ALGORITHM);
        keyPairGen.initialize(2048);
        //通过对象 KeyPairGenerator 获取对象KeyPair
        KeyPair keyPair = keyPairGen.generateKeyPair();

        //通过对象 KeyPair 获取RSA公私钥对象RSAPublicKey RSAPrivateKey
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        //公私钥对象存入map中
        Map<String, Object> keyMap = new HashMap<String, Object>(2);
        keyMap.put(PUBLIC_KEY, publicKey);
        keyMap.put(PRIVATE_KEY, privateKey);
        return keyMap;
    }

}
