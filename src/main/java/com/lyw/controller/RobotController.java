package com.lyw.controller;

import com.lyw.module.*;
import com.lyw.util.CqpHttpApi;
import com.lyw.vo.CqpPostMsg;
import lombok.extern.slf4j.Slf4j;
import org.nutz.mvc.adaptor.JsonAdaptor;
import org.nutz.mvc.annotation.AdaptBy;
import org.nutz.mvc.annotation.At;

/**
 * @Description
 * @ProjectName maintain-robot
 * @Package com.lyw.module
 * @Author yangjh5
 * @CreateDate 2017/11/30
 */
@Slf4j
@At("/robot")
public class RobotController {

    private static long myQQ = 3125754795L;

    @At("/accept")
    @AdaptBy(type = JsonAdaptor.class)
    public Object accept(CqpPostMsg cqpPostMsg) {
        log.info(cqpPostMsg.getMessage());
        CqpHttpApi api = CqpHttpApi.getInstance();
        switch (cqpPostMsg.getPost_type()) {
            case "message":
                return processMessage(cqpPostMsg);
            case "event":
                switch (cqpPostMsg.getEvent()) {
                    case "group_increase":
                        return api.sendGroupMsg(cqpPostMsg.getGroup_id(), "欢迎新人~ 您已经是群大佬了，和萌新们打个招呼吧！");
                    case "group_decrease":
                        return api.sendGroupMsg(cqpPostMsg.getGroup_id(), "有..有人被提了吗？");
                    case "group_admin":
                        break;
                    default:
                        break;
                }
            case "request":
                break;
            default:
                break;
        }
        return "";
    }

    private Object processMessage(CqpPostMsg cqpPostMsg) {
        switch (cqpPostMsg.getMessage_type()) {
            case "private":
                return "";
            case "group":
                return processGroupMessage(cqpPostMsg);
            case "discuss":
                return "";
            default:
                return "";
        }
    }

    private Object processGroupMessage(CqpPostMsg cqpPostMsg) {
        if (cqpPostMsg.getUser_id() == myQQ) {
            return "";
        }
        String message = cqpPostMsg.getMessage();
        Long groupId = cqpPostMsg.getGroup_id();
        CqpHttpApi api = CqpHttpApi.getInstance();
        Object response = "";
        if (message.startsWith("!help")) {
            StringBuilder resp = new StringBuilder();
            resp.append("!learn A B: 学习A对应的回答B\n");
            resp.append("!forget A B: 忘记A对应的回答B\n");
            resp.append("!roll xxx: roll点\n");
            resp.append("!translate xxx: 翻译\n");
            resp.append("@我 武器圣痕名称: 查询崩崩崩攻略");
            response = api.sendGroupMsg(groupId, resp.toString());
        } else if (message.startsWith("!learn ")) {
            /* 学习 */
            String learnResp = LearningModule.learn(message);
            response = api.sendGroupMsg(groupId, learnResp);
        } else if (message.startsWith("!forget ")) {
            /* 忘记 */
            String forgetResp = LearningModule.forget(message);
            response = api.sendGroupMsg(groupId, forgetResp);
        } else if (message.startsWith("!roll")) {
            /* roll点 */
            int result = RollModule.roll();
            response = api.sendGroupMsg(groupId, String.valueOf(result));
        } else if (message.startsWith("!translate")) {
            String result = TranslateModule.translate(message);
            response = api.sendGroupMsg(groupId, String.valueOf(result));
        } else if (message.startsWith("[CQ:at,qq=" + myQQ + "]")) {
            /* @我 */
            String[] msgArr = message.split(" ");
            String actualMsg;
            if (msgArr.length == 2) {
                actualMsg = msgArr[1];
            } else {
                actualMsg = "";
            }
            String weaponPic = WeaponGuideModule.getWeaponImg(actualMsg);
            String stigmaUrl = StigmataGuideModule.getStigmataUrl(actualMsg);
            if (stigmaUrl != null) {
                response = api.sendGroupMsg(groupId, stigmaUrl);
            } else {
                response = api.sendGroupMsg(groupId, weaponPic);
            }
        } else {
            if (GroupRepeatModule.triggerRepeat(cqpPostMsg)) {
                /* 复读 */
                response = api.sendGroupMsg(groupId, String.valueOf(message));
            } else {
                /* 其他消息 */
                String picked = LearningModule.pickResponse(message);
                if (picked != null) {
                    response = api.sendGroupMsg(groupId, picked);
                }
            }
        }
        return response;
    }

}
