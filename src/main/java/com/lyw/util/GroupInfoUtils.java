package com.lyw.util;

import com.lyw.vo.CqpHttpApiResp;

public class GroupInfoUtils {

    public static String getRole(long groupId, long qqNo) {
        CqpHttpApiResp resp = CqpHttpApi.getInstance().getGroupMemberInfo(groupId, qqNo);
        if (resp.getRetcode() == 0) {
            return (String) resp.getData().getOrDefault("role", "unknown");
        } else {
            return "unknown";
        }
    }

}
