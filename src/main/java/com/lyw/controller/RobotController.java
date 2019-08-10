package com.lyw.controller;

import com.lyw.module.*;
import com.lyw.util.CqpHttpApi;
import com.lyw.vo.CqpPostMsg;
import com.lyw.vo.MusicItem;
import lombok.extern.slf4j.Slf4j;
import org.nutz.mvc.adaptor.JsonAdaptor;
import org.nutz.mvc.annotation.AdaptBy;
import org.nutz.mvc.annotation.At;

@Slf4j
@At("/robot")
public class RobotController {

    private static final long myQQ = 3125754795L;

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
                        break;
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
        return null;
    }

    private Object processMessage(CqpPostMsg cqpPostMsg) {
        switch (cqpPostMsg.getMessage_type()) {
            case "private":
                return null;
            case "group":
                processGroupMessage(cqpPostMsg);
                return null;
            case "discuss":
                return null;
            default:
                return null;
        }
    }

    private void processGroupMessage(CqpPostMsg cqpPostMsg) {
        if (cqpPostMsg.getUser_id() == myQQ) {
            return;
        }
        String message = cqpPostMsg.getMessage();
        long groupId = cqpPostMsg.getGroup_id();
        if (message.startsWith("!pic")) {
            String picUrl = YandereModule.randomPic();
            CqpHttpApi.getInstance().sendGroupMsg(groupId, "[CQ:image,file=" + picUrl + "]");
        } else if (message.startsWith("!music")) {
            String keyword = message.split(" ")[1];
            MusicItem musicInfo = MusicModule.search(keyword);
            if (musicInfo == null) {
                CqpHttpApi.getInstance().sendGroupMsg(groupId, "没有检索到曲目：" + keyword);
                return;
            }
            CqpHttpApi.getInstance().sendGroupMsg(groupId, "[CQ:music,type=custom" +
                    ",audio=" + musicInfo.getMusicUrl() +
                    ",title=" + musicInfo.getName() +
                    ",image=" + musicInfo.getImageUrl() + "]");
        }
    }

}
