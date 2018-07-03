package com.lyw.module;

import com.lyw.util.CqpHttpApi;
import com.lyw.util.RandomUtils;
import com.lyw.vo.CqpPostMsg;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by yiweiliang.
 * User: yiweiliang1
 * Date: 2018/4/2
 *
 * @author yiweiliang1
 */
@Slf4j
public class GroupRepeatModule {

    private static CqpHttpApi api = CqpHttpApi.getInstance();

    private static Map<Long, String> newestWord = new HashMap<>();
    private static Map<Long, Long> newestSender = new HashMap<>();
    private static Map<Long, String> lastRepeated = new HashMap<>();

    public static synchronized Boolean triggerRepeat(CqpPostMsg cqpPostMsg) {
        String message = cqpPostMsg.getMessage();
        Long groupId = cqpPostMsg.getGroup_id();
        Long senderId = cqpPostMsg.getUser_id();
        Boolean trigger = false;
        try {
            trigger = newestWord.containsKey(groupId) && message.equals(newestWord.get(groupId))
                    && newestSender.containsKey(groupId) && !senderId.equals(newestSender.get(groupId))
                    && (!lastRepeated.containsKey(groupId) || !message.equals(lastRepeated.get(groupId)));
            if (trigger) {
                lastRepeated.put(groupId, message);
            }
            newestWord.put(groupId, message);
            newestSender.put(groupId, senderId);
        } catch (Throwable e) {
            log.error("触发复读异常，消息内容：" + message, e);
        }
        return trigger;
    }

    private static Map<Long, Queue<CqpPostMsg>> groupMsgQueue = new HashMap<>();
    private static int triggerLine = 3;
    public static synchronized void triggerBan(CqpPostMsg cqpPostMsg) {
        Long groupId = cqpPostMsg.getGroup_id();
        Queue<CqpPostMsg> gQueue;
        if (groupMsgQueue.containsKey(groupId)) {
            gQueue = groupMsgQueue.get(groupId);
            gQueue.offer(cqpPostMsg);
            while (gQueue.size() > triggerLine) {
                gQueue.poll();
            }
        } else {
            gQueue = new LinkedBlockingQueue<>();
            gQueue.offer(cqpPostMsg);
            groupMsgQueue.put(groupId, gQueue);
        }
        if (hasTrigger(gQueue)) {
            api.sendGroupMsg(groupId, "发现复读，哪位幸运复读机能吃到禁言套餐呢~？(*￣︶￣)");
            List<Long> rollList = new ArrayList<>();
            CqpPostMsg msg;
            while ((msg = gQueue.poll()) != null) {
                rollList.add(msg.getUser_id());
            }
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    long luckySender = rollList.get(RandomUtils.nextIntByRange(1, rollList.size()));
                    api.setGroupBan(luckySender, groupId, 60);
                }
            }, 4000);
        }
    }

    private static boolean hasTrigger(Queue<CqpPostMsg> gQueue) {
        if (gQueue.size() < triggerLine) {
            return false;
        }
        Set<String> set = new HashSet<>();
        gQueue.forEach(msg -> set.add(msg.getMessage()));
        return set.size() == 1;
    }

}
