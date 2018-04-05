package com.lyw.module;

import com.lyw.dmo.ArmorInfo;
import org.nutz.dao.Dao;
import org.nutz.dao.util.DaoUp;

/**
 * Created by yiweiliang.
 * User: yiweiliang1
 * Date: 2018/4/5
 */
public class ArmorGuideModule {

    public static String getArmorImg(String armorKey) {
        Dao dao = DaoUp.me().dao();
        ArmorInfo armorInfo = dao.fetch(ArmorInfo.class, armorKey);
        if (armorInfo != null) {
            return "[CQ:image,file=" + armorInfo.getValue() + "]";
        } else {
            return null;
        }
    }

}
