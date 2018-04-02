package com.lyw.util;

import com.lyw.vo.CqpHttpApiResp;

/**
 * @Description
 * @ProjectName maintain-robot
 * @Package com.lyw.util
 * @Author yangjh5
 * @CreateDate 2017/12/2
 */
interface CqpApi {

    String HTTP_SERVER_HOST = "http://127.0.0.1:5700/";
    String SEND_PRIVATE_MSG = "send_private_msg";
    String SEND_GROUP_MSG = "send_group_msg";
    String SEND_DISCUSS_MSG = "send_discuss_msg";
    String SEND_MSG = "send_msg";
    String DELETE_MSG = "delete_msg";
    String SEND_LIKE = "send_like";
    String SET_GROUP_KICK = "set_group_kick";
    String SET_GROUP_BAN = "set_group_ban";
    String SET_GROUP_ANONYMOUS_BAN = "set_group_anonymous_ban";
    String SET_GROUP_WHOLE_BAN = "set_group_whole_ban";
    String SET_GROUP_ADMIN = "set_group_admin";
    String SET_GROUP_ANONYMOUS = "set_group_anonymous";
    String SET_GROUP_CARD = "set_group_card";
    String SET_GROUP_LEAVE = "set_group_leave";
    String SET_GROUP_SPECIAL_TITLE = "set_group_special_title";
    String SET_DISCUSS_LEAVE = "set_discuss_leave";
    String SET_FRIEND_ADD_REQUEST = "set_friend_add_request";
    String SET_GROUP_ADD_REQUEST = "set_group_add_request";
    String GET_LOGIN_INFO = "get_login_info";
    String GET_STRANGER_INFO = "get_stranger_info";
    String GET_GROUP_LIST = "get_group_list";
    String GET_GROUP_MEMBER_INFO = "get_group_member_info";
    String GET_GROUP_MEMBER_LIST = "get_group_member_list";
    String GET_VERSION_INFO = "get_version_info";
    String SET_RESTART = "set_restart";
    String SET_RESTART_PLUGIN = "set_restart_plugin";

    int timeOut = 3000;

    /**
     * 发送私聊消息
     *
     * @param qq      QQ号
     * @param message 消息
     * @return
     */
    CqpHttpApiResp sendPrivateMsg(long qq, String message);

    /**
     * 发送群聊消息
     *
     * @param groupId 群ID
     * @param message 消息
     * @return
     */
    CqpHttpApiResp sendGroupMsg(long groupId, String message);

    /**
     * 发送讨论组消息
     *
     * @param groupId 讨论组ID
     * @param message 消息
     * @return
     */
    CqpHttpApiResp sendDisCussMsg(long groupId, String message);

    /**
     * 撤回消息
     *
     * @param messageId 消息ID
     * @return
     */
    CqpHttpApiResp deleteMsg(long messageId);

    /**
     * 发送好友赞
     *
     * @param qq    QQ号
     * @param times 赞的次数，每个好友每天最多 10 次
     * @return
     */
    CqpHttpApiResp sendLike(long qq, long times);

    /**
     * 群组踢人
     *
     * @param qq      QQ
     * @param groupId 群号
     * @return
     */
    CqpHttpApiResp setGroupKick(long qq, long groupId);

    /**
     * 群组单人禁言
     *
     * @param qq       QQ
     * @param groupId  群号
     * @param duration 禁言时长，单位秒，0 表示取消禁言
     * @return
     */
    CqpHttpApiResp setGroupBan(long qq, long groupId, long duration);

    /**
     * 群组匿名用户禁言
     *
     * @param flag     要禁言的匿名用户的 flag（需从群消息上报的数据中获得）
     * @param groupId  群号
     * @param duration 禁言时长，单位秒，无法取消匿名用户禁言
     * @return
     */
    CqpHttpApiResp setGroupAnonymousBan(String flag, long groupId, long duration);

    /**
     * 群组全员禁言
     *
     * @param groupId 群号
     * @param enable  是否禁言
     * @return
     */
    CqpHttpApiResp setGroupWholeBan(long groupId, boolean enable);

    /**
     * 群组设置管理员
     *
     * @param groupId 群号
     * @param qq      要设置管理员的 QQ 号
     * @param enable  true 为设置，false 为取消
     * @return
     */
    CqpHttpApiResp setGroupAdmin(long groupId, long qq, boolean enable);

    /**
     * 获取群列表
     *
     * @return
     */
    CqpHttpApiResp getGroupList();

    /**
     * 获取群成员信息
     *
     * @param groupId 群号
     * @param qq      QQ 号（不可以是登录号）
     * @return
     */
    CqpHttpApiResp getGroupMemberInfo(long groupId, long qq);

    /**
     * 获取群成员列表
     *
     * @param groupId 群号
     * @return
     */
    CqpHttpApiResp getGroupMemberList(long groupId);

    /**
     * 获取酷 Q 及 HTTP API 插件的版本信息
     *
     * @return
     */
    CqpHttpApiResp getVersionInfo();

    /**
     * 重启酷 Q，并以当前登录号自动登录（需勾选快速登录）
     *
     * @return
     */
    CqpHttpApiResp setRestart();

    /**
     * 重启 HTTP API 插件
     *
     * @return
     */
    CqpHttpApiResp setRestartPlugin();
}
