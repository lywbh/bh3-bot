package com.lyw.controller;

import com.lyw.module.*;
import com.lyw.util.CqpHttpApi;
import com.lyw.vo.CqpPostMsg;
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
        long groupId = cqpPostMsg.getGroup_id();
        Object response = "";
        if (message.contains("有色图吗")
                || message.contains("要一张色图")
                || message.contains("来点色图")
                || message.contains("来一点色图")
                || message.contains("来张色图")
                || message.contains("来一张色图")
                || message.contains("想看色图")
                || message.contains("要看色图")) {
            String picUrl = KonachanModule.randomPic();
            response = CqpHttpApi.getInstance().sendGroupMsg(groupId, "[CQ:image,file=" + picUrl + "]");
        }
        return response;
    }

}
