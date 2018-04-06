package com.lyw.module;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.lyw.util.CollectionUtils;
import com.lyw.util.FileUtils;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by yiweiliang.
 * User: yiweiliang1
 * Date: 2018/3/30
 *
 * @author yiweiliang1
 */
@Slf4j
public class LearningModule {

    public static String filePath = "C:\\Users\\kamimi\\IdeaProjects\\bh3-bot\\learn.txt";

    public static Map<Long, Map<String, Set<String>>> learnMap = JSON.parseObject(
            FileUtils.readToString(filePath),
            new TypeReference<Map<Long, Map<String, Set<String>>>>() {
            }
    );

    public static synchronized String learn(Long groupId, String message) {
        Map<String, Set<String>> groupMap;
        if (learnMap.containsKey(groupId)) {
            groupMap = learnMap.get(groupId);
        } else {
            groupMap = new HashMap<>();
            learnMap.put(groupId, groupMap);
        }
        try {
            String[] learnTab = message.split(" ");
            if (learnTab.length != 3) {
                return "格式不对哟！";
            }
            String key = learnTab[1];
            String value = learnTab[2];
            if (key.length() <= 1) {
                return "关键词太短了，教我复杂点的吧~";
            }
            if (groupMap.containsKey(key)) {
                if (groupMap.get(key).contains(value)) {
                    return "我已经学过这个了~";
                } else {
                    groupMap.get(key).add(value);
                }
            } else {
                Set<String> newRes = new HashSet<>();
                newRes.add(value);
                groupMap.put(key, newRes);
            }
            return "学习成功，对我说" + key + "试试吧~";
        } catch (Throwable e) {
            log.error("学习异常，消息内容：" + message, e);
            return "呜呜..学习回路出现了异常";
        }
    }

    public static synchronized String forget(Long groupId, String message) {
        Map<String, Set<String>> groupMap = learnMap.getOrDefault(groupId, new HashMap<>());
        try {
            String[] learnTab = message.split(" ");
            if (learnTab.length != 3) {
                return "格式不对哟！";
            }
            String key = learnTab[1];
            String value = learnTab[2];
            if (groupMap.containsKey(key) && groupMap.get(key).contains(value)) {
                groupMap.get(key).remove(value);
                if (groupMap.get(key).isEmpty()) {
                    groupMap.remove(key);
                }
                return "呜呜...我再也不说" + value + "了";
            } else {
                return "我不记得我有学过这个..";
            }
        } catch (Throwable e) {
            log.error("忘记异常，消息内容：" + message, e);
            return "呜呜..学习回路出现了异常";
        }
    }

    public static String pickResponse(Long groupId, String message) {
        Map<String, Set<String>> groupMap = learnMap.getOrDefault(groupId, new HashMap<>());
        try {
            Set<String> responseSet = new HashSet<>();
            for (String key : groupMap.keySet()) {
                if (message.contains(key)) {
                    String s = CollectionUtils.random(groupMap.get(key));
                    responseSet.add(s);
                }
            }
            if (!responseSet.isEmpty()) {
                return CollectionUtils.random(responseSet);
            }
            return null;
        } catch (Throwable e) {
            log.error("应答异常，消息内容：" + message, e);
            return "呜呜..学习回路出现了异常";
        }
    }

}
