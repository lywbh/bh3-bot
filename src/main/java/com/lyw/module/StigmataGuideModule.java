package com.lyw.module;

import com.lyw.dmo.StigmataInfo;
import org.nutz.dao.Dao;
import org.nutz.dao.util.DaoUp;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by yiweiliang.
 * User: yiweiliang1
 * Date: 2018/4/2
 */
public class StigmataGuideModule {

    public static String getStigmataUrl(String stiKey) {
        Dao dao = DaoUp.me().dao();
        StigmataInfo stigmataInfo = dao.fetch(StigmataInfo.class, stiKey);
        if (stigmataInfo != null) {
            return "[CQ:share,url=" + stigmataInfo.getValue() + ",title=" + stiKey + "]";
        } else {
            return null;
        }
    }

}
