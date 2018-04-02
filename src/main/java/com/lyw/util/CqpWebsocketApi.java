package com.lyw.util;

import com.lyw.controller.WebSocketController;
import com.lyw.vo.CqpHttpApiResp;
import org.nutz.lang.util.NutMap;

import javax.websocket.Session;
import java.io.IOException;

/**
 * @Description
 * @ProjectName maintain-robot
 * @Package com.lyw.util
 * @Author yangjh5
 * @CreateDate 2017/12/2
 */
public class CqpWebsocketApi implements CqpApi {

    private static CqpWebsocketApi instance = new CqpWebsocketApi();

    private CqpWebsocketApi(){}

    public static CqpWebsocketApi getInstance() {
        return instance;
    }

    @Override
    public CqpHttpApiResp sendPrivateMsg(long qq, String message) {
        NutMap nutMap = new NutMap("action", SEND_PRIVATE_MSG)
                .setv("params", new NutMap("user_id", qq)
                        .setv("message", message)
                );
        sendMsg(JsonUtils.toJson(nutMap));
        return null;
    }

    @Override
    public CqpHttpApiResp sendGroupMsg(long groupId, String message) {
        NutMap nutMap = new NutMap("action", SEND_GROUP_MSG)
                .setv("params", new NutMap("group_id", groupId)
                        .setv("message", message)
                );
        sendMsg(JsonUtils.toJson(nutMap));
        return null;
    }

    @Override
    public CqpHttpApiResp sendDisCussMsg(long groupId, String message) {
        NutMap nutMap = new NutMap("action", SEND_DISCUSS_MSG)
                .setv("params", new NutMap("discuss_id", groupId)
                        .setv("message", message)
                );
        sendMsg(JsonUtils.toJson(nutMap));
        return null;
    }

    // 以下内容，我懒，就不实现了
    @Override
    public CqpHttpApiResp deleteMsg(long messageId) {
        return null;
    }

    @Override
    public CqpHttpApiResp sendLike(long qq, long times) {
        return null;
    }

    @Override
    public CqpHttpApiResp setGroupKick(long qq, long groupId) {
        return null;
    }

    @Override
    public CqpHttpApiResp setGroupBan(long qq, long groupId, long duration) {
        return null;
    }

    @Override
    public CqpHttpApiResp setGroupAnonymousBan(String flag, long groupId, long duration) {
        return null;
    }

    @Override
    public CqpHttpApiResp setGroupWholeBan(long groupId, boolean enable) {
        return null;
    }

    @Override
    public CqpHttpApiResp setGroupAdmin(long groupId, long qq, boolean enable) {
        return null;
    }

    @Override
    public CqpHttpApiResp getGroupList() {
        return null;
    }

    @Override
    public CqpHttpApiResp getGroupMemberInfo(long groupId, long qq) {
        return null;
    }

    @Override
    public CqpHttpApiResp getGroupMemberList(long groupId) {
        return null;
    }

    @Override
    public CqpHttpApiResp getVersionInfo() {
        return null;
    }

    @Override
    public CqpHttpApiResp setRestart() {
        return null;
    }

    @Override
    public CqpHttpApiResp setRestartPlugin() {
        return null;
    }

    private static void sendMsg(String msg) {
        Session session = WebSocketController.sessionMap.get(WebSocketController.CQP_USER);
        if (null != session) {
            try {
                session.getBasicRemote().sendText(msg);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
