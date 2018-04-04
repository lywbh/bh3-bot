package com.lyw.module;

import com.lyw.dmo.WeaponInfo;
import org.nutz.dao.Dao;
import org.nutz.dao.util.DaoUp;

/**
 * Created by yiweiliang.
 * User: yiweiliang1
 * Date: 2018/4/2
 */
public class WeaponGuideModule {

    public static String getWeaponImg(String weaponKey) {
        Dao dao = DaoUp.me().dao();
        WeaponInfo weaponInfo = dao.fetch(WeaponInfo.class, weaponKey);
        if (weaponInfo != null) {
            return "[CQ:image,file=" + weaponInfo.getValue() + "]";
        } else {
            return null;
        }
    }

}
