package com.lyw;

import com.lyw.module.TranslateModule;
import org.junit.Test;

/**
 * Created by yiweiliang.
 * User: yiweiliang1
 * Date: 2018/4/3
 */
public class TestTranslate {

    @Test
    public void testCqpSendPrivateMsg() {
        String resp = TranslateModule.translate("how are you?");
        System.out.println(resp);
    }

}
