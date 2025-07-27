package com.fanlm.arhthmetic;


public class BigNumberAdd {

    public static void main(String[] args) {
        String str1 = "123789546";
        String str2 = "3257896321155";
        System.out.println(bigNumberAdd(str1, str2));

    }

    /**
     * 大数加法
     * @param str1
     * @param str2
     * @return
     */
    public static String bigNumberAdd(String str1, String str2){
        char[] charArray1 = new StringBuffer(str1).reverse().toString().toCharArray();
        char[] charArray2 = new StringBuffer(str2).reverse().toString().toCharArray();

        int maxLength = charArray1.length > charArray2.length ? charArray1.length : charArray2.length;
        int[] result = new int[maxLength + 1];
        // 累加
        for (int i = 0; i < maxLength; i++) {
            int temp = result[i];
            if (i < charArray1.length){
                temp += charArray1[i] - '0';
            }
            if (i < charArray2.length){
                temp += charArray2[i] - '0';
            }

            if (temp >= 10){
                temp = temp - 10;
                result[i + 1] = 1;
            }

            result[i] = temp;
        }

        StringBuffer sb = new StringBuffer();
        boolean flag = true;
        // 翻转
        for (int i = result.length - 1; i >= 0; i--){
            // 第一次
            if (result[i] == 0 && flag){
                continue;
            }
            flag = false;
            sb.append(result[i]);
        }

        return sb.toString();
    }
}
