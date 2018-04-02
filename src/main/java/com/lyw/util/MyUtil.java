package com.lyw.util;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.CharArrayWriter;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @Description
 * @ProjectName maintain-robot
 * @Package com.lyw.util
 * @Author yangjh5
 * @CreateDate 2017/11/30
 */
public class MyUtil {

    /**
     * 读取请求流
     * @param request
     * @return
     * @throws IOException
     */
    public static String binaryReader(HttpServletRequest request) throws IOException {
        String charset = request.getCharacterEncoding();
        if (charset == null) {
            charset = "utf-8";
        }
        BufferedReader in = new BufferedReader(new InputStreamReader(request
                .getInputStream(), charset));

        // Read the request
        CharArrayWriter data = new CharArrayWriter();
        char[] buf = new char[8192];
        int ret;
        while ((ret = in.read(buf, 0, 8192)) != -1) {
            data.write(buf, 0, ret);
        }
        return data.toString();
    }
}
