package com.lyw.util;

import com.xiaoleilu.hutool.http.HttpUtil;
import com.lyw.vo.CqpHttpApiResp;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description
 * @ProjectName maintain-robot
 * @Package com.lyw.util
 * @Author yangjh5
 * @CreateDate 2017/12/2
 */
public class CqpHttpApi implements CqpApi {

    private static CqpHttpApi instance = new CqpHttpApi();

    private CqpHttpApi(){}

    public static CqpHttpApi getInstance() {
        return instance;
    }

    /**
     * 发送私聊消息
     *
     * @param qq      QQ号
     * @param message 消息
     * @return
     */
    @Override
    public CqpHttpApiResp sendPrivateMsg(long qq, String message) {
        Map<String, Object> map = new HashMap<>();
        map.put("user_id", qq);
        map.put("message", message);
        String resp = HttpUtil.post(HTTP_SERVER_HOST + SEND_PRIVATE_MSG, map, timeOut);
        return JsonUtils.toObject(resp, CqpHttpApiResp.class);
    }

    /**
     * 发送群聊消息
     *
     * @param groupId 群ID
     * @param message 消息
     * @return
     */
    @Override
    public CqpHttpApiResp sendGroupMsg(long groupId, String message) {
        Map<String, Object> map = new HashMap<>();
        map.put("group_id", groupId);
        map.put("message", message);
        String resp = HttpUtil.post(HTTP_SERVER_HOST + SEND_GROUP_MSG, map, timeOut);
        return JsonUtils.toObject(resp, CqpHttpApiResp.class);
    }

    /**
     * 发送讨论组消息
     *
     * @param groupId 讨论组ID
     * @param message 消息
     * @return
     */
    @Override
    public CqpHttpApiResp sendDisCussMsg(long groupId, String message) {
        Map<String, Object> map = new HashMap<>();
        map.put("discuss_id", groupId);
        map.put("message", message);
        String resp = HttpUtil.post(HTTP_SERVER_HOST + SEND_DISCUSS_MSG, map, timeOut);
        return JsonUtils.toObject(resp, CqpHttpApiResp.class);
    }

    /**
     * 撤回消息
     *
     * @param messageId 消息ID
     * @return
     */
    @Override
    public CqpHttpApiResp deleteMsg(long messageId) {
        Map<String, Object> map = new HashMap<>();
        map.put("message_id", messageId);
        String resp = HttpUtil.post(HTTP_SERVER_HOST + DELETE_MSG, map, timeOut);
        return JsonUtils.toObject(resp, CqpHttpApiResp.class);
    }

    /**
     * 发送好友赞
     *
     * @param qq    QQ号
     * @param times 赞的次数，每个好友每天最多 10 次
     * @return
     */
    @Override
    public CqpHttpApiResp sendLike(long qq, long times) {
        Map<String, Object> map = new HashMap<>();
        map.put("user_id", qq);
        map.put("times", times);
        String resp = HttpUtil.post(HTTP_SERVER_HOST + SEND_LIKE, map, timeOut);
        return JsonUtils.toObject(resp, CqpHttpApiResp.class);
    }

    /**
     * 群组踢人
     *
     * @param qq      QQ
     * @param groupId 群号
     * @return
     */
    @Override
    public CqpHttpApiResp setGroupKick(long qq, long groupId) {
        Map<String, Object> map = new HashMap<>();
        map.put("user_id", qq);
        map.put("group_id", groupId);
        String resp = HttpUtil.post(HTTP_SERVER_HOST + SET_GROUP_KICK, map, timeOut);
        return JsonUtils.toObject(resp, CqpHttpApiResp.class);
    }

    /**
     * 群组单人禁言
     *
     * @param qq       QQ
     * @param groupId  群号
     * @param duration 禁言时长，单位秒，0 表示取消禁言
     * @return
     */

    @Override
    public CqpHttpApiResp setGroupBan(long qq, long groupId, long duration) {
        Map<String, Object> map = new HashMap<>();
        map.put("user_id", qq);
        map.put("group_id", groupId);
        map.put("duration", duration);
        String resp = HttpUtil.post(HTTP_SERVER_HOST + SET_GROUP_BAN, map, timeOut);
        return JsonUtils.toObject(resp, CqpHttpApiResp.class);
    }

    /**
     * 群组匿名用户禁言
     *
     * @param flag     要禁言的匿名用户的 flag（需从群消息上报的数据中获得）
     * @param groupId  群号
     * @param duration 禁言时长，单位秒，无法取消匿名用户禁言
     * @return
     */
    @Override
    public CqpHttpApiResp setGroupAnonymousBan(String flag, long groupId, long duration) {
        Map<String, Object> map = new HashMap<>();
        map.put("flag", flag);
        map.put("group_id", groupId);
        map.put("duration", duration);
        String resp = HttpUtil.post(HTTP_SERVER_HOST + SET_GROUP_ANONYMOUS_BAN, map, timeOut);
        return JsonUtils.toObject(resp, CqpHttpApiResp.class);
    }

    /**
     * 群组全员禁言
     *
     * @param groupId 群号
     * @param enable  是否禁言
     * @return
     */
    @Override
    public CqpHttpApiResp setGroupWholeBan(long groupId, boolean enable) {
        Map<String, Object> map = new HashMap<>();
        map.put("group_id", groupId);
        map.put("enable", enable);
        String resp = HttpUtil.post(HTTP_SERVER_HOST + SET_GROUP_WHOLE_BAN, map, timeOut);
        return JsonUtils.toObject(resp, CqpHttpApiResp.class);
    }

    /**
     * 群组设置管理员
     *
     * @param groupId 群号
     * @param qq      要设置管理员的 QQ 号
     * @param enable  true 为设置，false 为取消
     * @return
     */
    @Override
    public CqpHttpApiResp setGroupAdmin(long groupId, long qq, boolean enable) {
        Map<String, Object> map = new HashMap<>();
        map.put("group_id", groupId);
        map.put("user_id", qq);
        map.put("enable", enable);
        String resp = HttpUtil.post(HTTP_SERVER_HOST + SET_GROUP_WHOLE_BAN, map, timeOut);
        return JsonUtils.toObject(resp, CqpHttpApiResp.class);
    }

    /**
     * 获取群列表
     *
     * @return
     */
    @Override
    public CqpHttpApiResp getGroupList() {
        String resp = HttpUtil.get(HTTP_SERVER_HOST + GET_GROUP_LIST);
        return JsonUtils.toObject(resp, CqpHttpApiResp.class);
    }

    /**
     * 获取群成员信息
     *
     * @param groupId 群号
     * @param qq      QQ 号（不可以是登录号）
     * @return
     */
    @Override
    public CqpHttpApiResp getGroupMemberInfo(long groupId, long qq) {
        Map<String, Object> map = new HashMap<>();
        map.put("group_id", groupId);
        map.put("user_id", qq);
        String resp = HttpUtil.post(HTTP_SERVER_HOST + GET_GROUP_MEMBER_INFO, map, timeOut);
        return JsonUtils.toObject(resp, CqpHttpApiResp.class);
    }

    /**
     * 获取群成员列表
     *
     * @param groupId 群号
     * @return
     */
    @Override
    public CqpHttpApiResp getGroupMemberList(long groupId) {
        Map<String, Object> map = new HashMap<>();
        map.put("group_id", groupId);
        String resp = HttpUtil.post(HTTP_SERVER_HOST + GET_GROUP_MEMBER_LIST, map, timeOut);
        return JsonUtils.toObject(resp, CqpHttpApiResp.class);
    }

    /**
     * 获取酷 Q 及 HTTP API 插件的版本信息
     *
     * @return
     */
    @Override
    public CqpHttpApiResp getVersionInfo() {
        String resp = HttpUtil.get(HTTP_SERVER_HOST + GET_VERSION_INFO, timeOut);
        return JsonUtils.toObject(resp, CqpHttpApiResp.class);
    }

    /**
     * 重启酷 Q，并以当前登录号自动登录（需勾选快速登录）
     *
     * @return
     */
    @Override
    public CqpHttpApiResp setRestart() {
        String resp = HttpUtil.get(HTTP_SERVER_HOST + SET_RESTART, timeOut);
        return JsonUtils.toObject(resp, CqpHttpApiResp.class);
    }

    /**
     * 重启 HTTP API 插件
     *
     * @return
     */
    @Override
    public CqpHttpApiResp setRestartPlugin() {
        String resp = HttpUtil.get(HTTP_SERVER_HOST + SET_RESTART_PLUGIN, timeOut);
        return JsonUtils.toObject(resp, CqpHttpApiResp.class);
    }
}
