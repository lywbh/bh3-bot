package com.lyw.controller;

import com.lyw.module.*;
import com.lyw.util.CqpHttpApi;
import com.lyw.vo.CqpPostMsg;
import com.lyw.vo.MusicItem;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.nutz.mvc.adaptor.JsonAdaptor;
import org.nutz.mvc.annotation.AdaptBy;
import org.nutz.mvc.annotation.At;

import java.util.HashSet;
import java.util.Set;

@Slf4j
@At("/robot")
public class RobotController {

    private static final long myQQ = 3125754795L;

    private static final Set<Long> managerQQ = new HashSet<>();
    static {
        managerQQ.add(550271106L);
    }

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
            String rating = StringUtils.substringAfter(message, " ");
            if (StringUtils.equals(rating, "explicit") && !checkAuth(cqpPostMsg.getUser_id())) {
                CqpHttpApi.getInstance().sendGroupMsg(groupId, "权限不足");
                return;
            }
            String picUrl = YandereModule.randomPic(rating);
            CqpHttpApi.getInstance().sendGroupMsg(groupId, "[CQ:image,file=" + picUrl + "]");
        } else if (message.startsWith("!music")) {
            String keyword = StringUtils.substringAfter(message, " ");
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

    private boolean checkAuth(long qqNo) {
        return managerQQ.contains(qqNo);
    }

}
