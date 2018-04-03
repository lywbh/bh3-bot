package com.lyw.util;

/**
 * Created by IntelliJ IDEA
 * User: yiweiliang
 * Date: 2018/4/3
 */
public class RandomUtils extends org.apache.commons.lang3.RandomUtils {

    /**
     * 生成range方式的随机数 range(0,10)
     */
    public static int nextIntByRange(int start, int end) {
        return nextInt(start, end);
    }

    public static int nextIntByRange(int end) {
        return nextIntByRange(0, end);
    }

    /**
     * 从指定字符集中取随机字符
     */
    public static char randomCharBySet(String charSet) {
        int i = nextIntByRange(charSet.length());
        return charSet.charAt(i);
    }

    /**
     * 从指定字符集中取随机字符串
     */
    public static String randomStringBySet(int length, String charSet) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; ++i) {
            sb.append(randomCharBySet(charSet));
        }
        return sb.toString();
    }

    /**
     * 生成指定长度(length)的随机数字符串
     */
    public static String getRandomNum(int length) {
        String charSet = "0123456789";
        return randomStringBySet(length, charSet);
    }

    public static void main(String[] args) {
        System.out.println(getRandomNum(4));
        System.out.println(nextIntByRange(99));
        System.out.println(randomCharBySet("abc"));
    }
}
