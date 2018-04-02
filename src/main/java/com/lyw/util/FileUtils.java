package com.lyw.util;

import lombok.extern.slf4j.Slf4j;

import java.io.*;

/**
 * Created by CreditEase.
 * User: yiweiliang1
 * Date: 2018/4/2
 */
@Slf4j
public class FileUtils {

    public static String readToString(String fileName) {
        String encoding = "UTF-8";
        File file = new File(fileName);
        Long filelength = file.length();
        byte[] filecontent = new byte[filelength.intValue()];
        try {
            FileInputStream in = new FileInputStream(file);
            in.read(filecontent);
            in.close();
        } catch (IOException e) {
            log.error("readToString error", e);
        }
        try {
            return new String(filecontent, encoding);
        } catch (UnsupportedEncodingException e) {
            log.error("The OS does not support " + encoding, e);
            return null;
        }
    }

    public static void clearFile(String fileName) {
        File file = new File(fileName);
        try {
            if (!file.exists()) {
                return;
            }
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write("");
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
