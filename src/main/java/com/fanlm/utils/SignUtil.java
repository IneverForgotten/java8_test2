package com.fanlm.utils;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.DigestAlgorithm;
import cn.hutool.crypto.digest.Digester;
import cn.hutool.crypto.digest.HMac;
import cn.hutool.crypto.digest.HmacAlgorithm;
import com.alibaba.fastjson.JSON;
import org.apache.commons.collections.MapUtils;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import java.util.zip.DataFormatException;

/**
 * 生成接口签名的工具类
 */
public class SignUtil {


    public static void main(String[] args) {
        String format = LocalDateTime.now().format(DateTimeFormatter.ofPattern(("yyyy-MM-dd HH:mm:ss")));
        System.out.println(format);
        String newSign = SignUtil.sign("123456", "",
                format, null, null, "{\"orderId\":1234567}");
        System.out.println(newSign);
    }
    /**
     *
     * 例如： hmac_sha256(appsecret+X+Y+X+timestamp+appsecret)
     * @param appsecret
     * @param signMethod 默认为：HMAC_SHA256
     * @param paths 对应@PathVariable
     * @param params 对应@RequestParam
     * @param body 对应@RequestBody
     * @return
     */
    public static String sign(String appsecret, String signMethod, String timestamp, String[] paths,
            Map<String, String[]> params, String body) {
        StringBuilder sb = new StringBuilder(appsecret);

        // path variable（对应@PathVariable）
        if (paths != null && paths.length>0) {
            String pathValues = String.join("", Arrays.stream(paths).sorted().toArray(String[]::new));
            sb.append(pathValues);
        }

        // parameters（对应@RequestParam）
        if (CollUtil.isNotEmpty(params)) {
            params.entrySet().stream().filter(entry -> Objects.nonNull(entry.getValue())) // 为空的不计入
                    .sorted(Map.Entry.comparingByKey()).forEach(paramEntry -> {
                        String paramValue = String.join("",
                                Arrays.stream(paramEntry.getValue()).sorted().toArray(String[]::new));
                        sb.append(paramEntry.getKey()).append(paramValue);
                    });
        }

        // body（对应@RequestBody)
        if (StrUtil.isNotBlank(body)) {
            Map<String, Object> map = JSON.parseObject(body, Map.class);
            map.entrySet().stream().filter(entry -> Objects.nonNull(entry.getValue())) // 为空的不计入
                    .sorted(Map.Entry.comparingByKey()).forEach(paramEntry -> {
                        sb.append(paramEntry.getKey()).append(paramEntry.getValue());
                    });
        }
        sb.append(timestamp).append(appsecret);
        String sign = new String();
        if (StrUtil.isBlank(signMethod) || StrUtil.equalsIgnoreCase(signMethod, "HMAC-SHA256")) {
            sign = new HMac(HmacAlgorithm.HmacSHA256, appsecret.getBytes()).digestHex(sb.toString());
        }
        else if (StrUtil.equalsIgnoreCase(signMethod, "HMAC")) {
            sign = new HMac(HmacAlgorithm.HmacMD5, appsecret.getBytes()).digestHex(sb.toString());
        }
        else {
            Digester md5 = new Digester(DigestAlgorithm.MD5);
            sign = md5.digestHex(sb.toString());
        }
        return sign.toUpperCase();
    }


    String privateKey = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBALIzaIRRl9fg2jit1gU3mNNFyhypQEmyUVQ6H04mOozPdGzs5+Pbvaz/9ozfHjLtInuwjtSALnpJz6wdR1QeLa4oiuyEPWBFGiCndIcWdeKVI3OQLRWnV9YRYpnLVvjZ48sYqgTMZ4U1YcV7Sl9G0AzSyaFVfdFXfnwjmS4j6D29AgMBAAECgYEAmyBrfLx2vaPs8+hIZlRGwqx/TEH+R+lmKTdLp0FaONgjlusI1u+kh6RvIaTdaiHKofhJ7i0DyMrWcRMv08dNpVeiE+ainggSTtsdJdrVEmpseRQUA94kCrZQExVyVtqh5OjAi7PJ60hhqDlij43ZVlw5d9Onp6e6+29wCKkzFSECQQDs6NbEpitqR0U0Oh16QX3jpNDvUoElKmyl2o0WJpMqHi476G2N5JeFMjf81zOGh9/EuR3VjxwoTZxRujtk2BJZAkEAwI96g7lzK4wO7O9RtPYAg5q8hqunMCWlJKzA1evfIzzvLWchiwdy8rjwgjPA3kr76tzzbHpsukKf/ebc/J6yBQJAZlop/4ezFhV4hpndBmapFuKsCdlhRkdP7U/AyKMdzYKAgw1l13m9JKSPn8Lx1dt6B6nag9tyVM9DC+QjqOvY8QJAFAJUvr9Ugl/pZSFxIha18vbvRCcuFkizIl55I0GBTE4WpGclCydZAHPLOhxanD66cqtG+Cy4g5pMubt1lyJ+aQJAf58hvv0J/haXO5cdSIiDbWihx/F2Un1HSevo0QeYyY8HxdvD3L4KXEPtFmT5Xb1agloozTBejtY6t/XAymEdWA==";
    String publicKeyBase64 = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCyM2iEUZfX4No4rdYFN5jTRcocqUBJslFUOh9OJjqMz3Rs7Ofj272s//aM3x4y7SJ7sI7UgC56Sc+sHUdUHi2uKIrshD1gRRogp3SHFnXilSNzkC0Vp1fWEWKZy1b42ePLGKoEzGeFNWHFe0pfRtAM0smhVX3RV358I5kuI+g9vQIDAQAB";
    //System.out.println(privateKey);


    //RSA priRsa = new RSA(privateKey, null);
    //// 初始化RSA工具并设置公钥
    //RSA pubRsa = new RSA(null, publicKeyBase64);
    //
    //String testString = "test string";
    //// 公钥加密
    //String encodeStr = pubRsa.encryptBase64(testString, KeyType.PublicKey);
    //    System.out.println("encodeStr by public key:" + encodeStr);
    //// 私钥解密
    //String decodeStr = priRsa.decryptStr(encodeStr, KeyType.PrivateKey);
    //    System.out.println("decodeStr by private key:" + decodeStr);
    //
    //// 私钥加密
    //String encodeStr1 = priRsa.encryptBase64(testString, KeyType.PrivateKey);
    //    System.out.println("encodeStr1 by private key:" + encodeStr1);
    //// 公钥解密
    //String decodeStr1 = pubRsa.decryptStr(encodeStr1, KeyType.PublicKey);
    //    System.out.println("decodeStr1 by public key:" + decodeStr1);
}