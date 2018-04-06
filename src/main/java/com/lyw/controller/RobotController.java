package com.lyw.controller;

import com.lyw.module.*;
import com.lyw.util.CqpHttpApi;
import com.lyw.vo.CqpPostMsg;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.nutz.mvc.adaptor.JsonAdaptor;
import org.nutz.mvc.annotation.AdaptBy;
import org.nutz.mvc.annotation.At;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

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

    private static int NORMAL_MODE = 0;
    private static int CHENGYU_GAMEING = 1;
    private static Map<Long, Integer> robotStatus = new HashMap<>();

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
            String resp = "!learn A B: 学习A对应的回答B\n" +
                    "!forget A B: 忘记A对应的回答B\n" +
                    "!roll xxx: roll点\n" +
                    "!translate xxx: 翻译\n" +
                    "@我 女武神/武器/圣痕: 查询崩崩崩攻略\n" +
                    "!game start 成语接龙: 开始游戏\n" +
                    "!game end: 结束游戏";
            response = api.sendGroupMsg(groupId, resp);
        } else if (message.startsWith("!learn ")) {
            /* 学习 */
            String learnResp = LearningModule.learn(groupId, message);
            response = api.sendGroupMsg(groupId, learnResp);
        } else if (message.startsWith("!forget ")) {
            /* 忘记 */
            String forgetResp = LearningModule.forget(groupId, message);
            response = api.sendGroupMsg(groupId, forgetResp);
        } else if (message.startsWith("!roll")) {
            /* roll点 */
            int result = RollModule.roll();
            response = api.sendGroupMsg(groupId, String.valueOf(result));
        } else if (message.startsWith("!translate")) {
            /* 翻译 */
            String result = TranslateModule.translate(message);
            response = api.sendGroupMsg(groupId, String.valueOf(result));
        } else if (message.startsWith("!game start")) {
            if (robotStatus.getOrDefault(groupId, NORMAL_MODE) == NORMAL_MODE) {
                if (message.endsWith("成语接龙")) {
                    /* 成语接龙开始 */
                    robotStatus.put(groupId, CHENGYU_GAMEING);
                    String firstPhrase = IdiomGameModule.pickIdiom(groupId, null);
                    response = api.sendGroupMsg(groupId, firstPhrase);
                }
            } else {
                response = api.sendGroupMsg(groupId, "已经在游戏模式中");
            }
        } else if (message.startsWith("!game end")) {
            /* 结束所有游戏 */
            if (robotStatus.getOrDefault(groupId, NORMAL_MODE) != NORMAL_MODE) {
                robotStatus.put(groupId, NORMAL_MODE);
                response = api.sendGroupMsg(groupId, "游戏模式已关闭");
            }
        } else if (message.startsWith("[CQ:at,qq=" + myQQ + "]")) {
            /* @我 */
            String[] msgArr = message.split(" ");
            String actualMsg;
            if (msgArr.length == 2) {
                actualMsg = msgArr[1];
            } else {
                actualMsg = "";
            }
            String armorPic = ArmorGuideModule.getArmorImg(actualMsg);
            String weaponPic = WeaponGuideModule.getWeaponImg(actualMsg);
            String stigmaUrl = StigmataGuideModule.getStigmataUrl(actualMsg);
            if (armorPic != null) {
                response = api.sendGroupMsg(groupId, armorPic);
            } else if (weaponPic != null) {
                response = api.sendGroupMsg(groupId, weaponPic);
            } else {
                response = api.sendGroupMsg(groupId, stigmaUrl);
            }
        } else {
            if (GroupRepeatModule.triggerRepeat(cqpPostMsg)) {
                /* 复读 */
                response = api.sendGroupMsg(groupId, String.valueOf(message));
            } else {
                /* 其他消息 */
                if (robotStatus.getOrDefault(groupId, NORMAL_MODE) == CHENGYU_GAMEING) {
                    String nextPhrase = IdiomGameModule.gameStep(groupId, message);
                    if (StringUtils.equals(nextPhrase, "嘤嘤嘤..我找不到词惹，游戏结束")) {
                        robotStatus.put(groupId, NORMAL_MODE);
                    }
                    response = api.sendGroupMsg(groupId, nextPhrase);
                } else {
                    String picked = LearningModule.pickResponse(groupId, message);
                    if (picked != null) {
                        response = api.sendGroupMsg(groupId, picked);
                    }
                }
            }
        }
        return response;
    }

}
