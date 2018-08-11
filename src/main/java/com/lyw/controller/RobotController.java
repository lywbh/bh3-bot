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

    public static Map<Long, Integer> armorSupplyTimes = new HashMap<>();
    public static Map<Long, Integer> equipSupplyTimes = new HashMap<>();
    public static Map<Long, Integer> extendSupplyTimes = new HashMap<>();

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
        Long qqNo = cqpPostMsg.getUser_id();
        Long groupId = cqpPostMsg.getGroup_id();
        CqpHttpApi api = CqpHttpApi.getInstance();
        Object response = "";
        if (message.startsWith("!help")) {
            String resp = "!learn on/off: 学习模块开关\n" +
                    "!learn A B: 学习A对应的回答B\n" +
                    "!forget A B: 忘记A对应的回答B\n" +
                    "!roll xxx: roll点\n" +
                    "!translate xxx: 翻译\n" +
                    "!repeat on/off: 复读开关\n" +
                    "!image xxx [图片]: 拼接文字\n" +
                    "!hentai xxx: 你又要社保了吧？\n" +
                    "!标配补给/装备补给: 十连\n" +
                    "!sleep 1: ？\n" +
                    "@我 女武神/武器/圣痕: 查询崩崩崩攻略\n" +
                    "!game start 成语接龙: 开始游戏\n" +
                    "!game end: 结束游戏";
            response = api.sendGroupMsg(groupId, resp);
        } else if (message.startsWith("!learn ")) {
            /* 学习 */
            String learnResp = LearningModule.learn(qqNo, groupId, message);
            response = api.sendGroupMsg(groupId, learnResp);
        } else if (message.startsWith("!forget ")) {
            /* 忘记 */
            String forgetResp = LearningModule.forget(groupId, message);
            response = api.sendGroupMsg(groupId, forgetResp);
        } else if (message.startsWith("!roll")) {
            /* roll点 */
            int result = RollModule.roll();
            response = api.sendGroupMsg(groupId, String.valueOf(result));
        } else if (message.startsWith("!translate ")) {
            /* 翻译 */
            String result = TranslateModule.translate(message);
            response = api.sendGroupMsg(groupId, String.valueOf(result));
        } else if (message.startsWith("!repeat ")) {
            String[] msgArr = message.split(" ");
            if (msgArr.length == 2) {
                if (StringUtils.equals(msgArr[1], "on")) {
                    String resp = GroupRepeatModule.repeatSwitch(true, groupId);
                    response = api.sendGroupMsg(groupId, resp);
                } else if (StringUtils.equals(msgArr[1], "off")) {
                    String resp = GroupRepeatModule.repeatSwitch(false, groupId);
                    response = api.sendGroupMsg(groupId, resp);
                }
            }
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
        } else if (message.startsWith("!sleep ")) {
            String[] msgArr = message.split(" ");
            if (msgArr.length == 2) {
                api.setGroupBan(qqNo, groupId, Long.valueOf(msgArr[1]) * 60);
            }
        } else if (message.startsWith("!image ")) {
            String[] msgArr = message.split(" ");
            if (msgArr.length == 3) {
                String content = msgArr[1];
                String cqImg = msgArr[2];
                String inputName = StringUtils.substringBetween(cqImg, "[CQ:image,file=", ",");
                if (StringUtils.isEmpty(inputName)) {
                    inputName = StringUtils.substringBetween(cqImg, "[CQ:image,file=", "]");
                }
                String outputName = ImageWordModule.wordAdd(inputName, content);
                response = api.sendGroupMsg(groupId, "[CQ:image,file=" + outputName + "]");
            }
        } else if (message.startsWith("!hentai")) {
            String[] msgArr = message.split(" ");
            String picUrl;
            if (msgArr.length == 2) {
                String content = msgArr[1];
                picUrl = KonachanModule.randomPic(content);
            } else {
                picUrl = KonachanModule.randomPic("");
            }
            response = api.sendGroupMsg(groupId, "[CQ:image,file=" + picUrl + "]");
        } else if (message.startsWith("!标配补给")) {
            Integer currentTimes = armorSupplyTimes.getOrDefault(qqNo, 0);
            if (currentTimes < 3) {
                currentTimes += 1;
                armorSupplyTimes.put(qqNo, currentTimes);
                String supplyResult = StandardSupplyModule.supply();
                response = api.sendGroupMsg(groupId, "[CQ:image,file=" + supplyResult + "]");
            } else {
                response = api.sendGroupMsg(groupId, "您今天的标配补给次数已经用完咯~");
            }
        } else if (message.startsWith("!装备补给")) {
            Integer currentTimes = equipSupplyTimes.getOrDefault(qqNo, 0);
            if (currentTimes < 3) {
                currentTimes += 1;
                equipSupplyTimes.put(qqNo, currentTimes);
                String supplyResult = EquipSupplyModule.supply();
                response = api.sendGroupMsg(groupId, "[CQ:image,file=" + supplyResult + "]");
            } else {
                response = api.sendGroupMsg(groupId, "您今天的装备补给次数已经用完咯~");
            }
        } else if (message.startsWith("!扩充补给")) {
            Integer currentTimes = extendSupplyTimes.getOrDefault(qqNo, 0);
            if (currentTimes < 3) {
                currentTimes += 1;
                extendSupplyTimes.put(qqNo, currentTimes);
                String supplyResult = ArmorExtendSupplyModule.supply();
                response = api.sendGroupMsg(groupId, "[CQ:image,file=" + supplyResult + "]");
            } else {
                response = api.sendGroupMsg(groupId, "您今天的扩充补给次数已经用完咯~");
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
            //GroupRepeatModule.triggerBan(cqpPostMsg);
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
