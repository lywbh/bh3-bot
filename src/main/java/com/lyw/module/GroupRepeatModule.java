package com.lyw.module;

import com.lyw.vo.CqpPostMsg;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by CreditEase.
 * User: yiweiliang1
 * Date: 2018/4/2
 * @author yiweiliang1
 */
@Slf4j
public class GroupRepeatModule {

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

}
