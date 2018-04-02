package com.lyw.util;

import java.util.Collection;

/**
 * Created by CreditEase.
 * User: yiweiliang1
 * Date: 2018/4/2
 */
public class CollectionUtils {

    public static <T> T random(Collection<T> collection) {
        if (collection == null || collection.isEmpty()) {
            return null;
        }
        int sel = (int) (Math.random() * collection.size());
        int i = 0;
        for (T s : collection) {
            if (i == sel) {
                return s;
            }
            i++;
        }
        return null;
    }

}
