package com.lyw.vo;

import com.lyw.util.JsonUtils;
import lombok.Data;

/**
 * @Description
 * @ProjectName maintain-robot
 * @Package com.lyw.vo
 * @Author yangjh5
 * @CreateDate 2017/11/30
 */
@Data
public class CqpPostMsg {

    /**
     * 请求类型：
     * message	收到消息
     * event	群、讨论组变动等非消息类事件
     * request	加好友请求、加群请求／邀请
     */
    private String post_type;

    /**
     * 消息类型：
     * private 私聊消息
     * discuss 讨论组消息
     * group 群消息
     */
    private String message_type;

    /**
     * 私聊消息："friend"、"group"、"discuss"、"other"
     * 群消息："normal"、"anonymous"、"notice"
     */
    private String sub_type;

    /**
     * 消息ID
     */
    private int message_id;

    /**
     * 消息内容
     */
    private String message;

    /**
     * 来源QQ
     */
    private long user_id;

    /**
     * 字体
     */
    private long font;

    /**
     * 群号
     */
    private long group_id;

    /**
     * 匿名用户名
     */
    private String anonymous;

    /**
     * 匿名用户 flag，在调用禁言 API 时需要传入
     */
    private String anonymous_flag;

    /**
     * 讨论组ID
     */
    private long discuss_id;

    /**
     * 文件信息
     */
    private Object file;

    private String event;

    private long operator_id;

    private String request_type;

    private String flag;

    @Override
    public String toString() {
        return JsonUtils.toJson(this);
    }

}
