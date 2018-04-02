package com.lyw.module;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.lyw.util.CollectionUtils;
import com.lyw.util.FileUtils;
import lombok.extern.slf4j.Slf4j;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by CreditEase.
 * User: yiweiliang1
 * Date: 2018/3/30
 *
 * @author yiweiliang1
 */
@Slf4j
public class LearningModule {

    public static String filePath = "C:\\Users\\kamimi\\IdeaProjects\\bh3-bot\\learn.txt";

    public static Map<String, Set<String>> learnMap = JSON.parseObject(FileUtils.readToString(filePath), new TypeReference<Map<String, Set<String>>>() {});

    public static synchronized String learn(String message) {
        try {
            String[] learnTab = message.split(" ");
            if (learnTab.length == 3) {
                String key = learnTab[1];
                String value = learnTab[2];
                if (learnMap.containsKey(key)) {
                    if (learnMap.get(key).contains(value)) {
                        return "我已经学过这个了~";
                    } else {
                        learnMap.get(key).add(value);
                    }
                } else {
                    Set<String> newRes = new HashSet<>();
                    newRes.add(value);
                    learnMap.put(key, newRes);
                }
                return "学习成功，对我说" + key + "试试吧~";
            } else {
                return "格式不对哟！";
            }
        } catch (Throwable e) {
            log.error("学习异常，消息内容：" + message, e);
            return "呜呜..学习回路出现了异常";
        }
    }

    public static synchronized String forget(String message) {
        try {
            String[] learnTab = message.split(" ");
            if (learnTab.length == 3) {
                String key = learnTab[1];
                String value = learnTab[2];
                if (learnMap.containsKey(key) && learnMap.get(key).contains(value)) {
                    learnMap.get(key).remove(value);
                    if (learnMap.get(key).isEmpty()) {
                        learnMap.remove(key);
                    }
                    return "呜呜...我再也不说" + value + "了";
                } else {
                    return "我不记得我有学过这个..";
                }
            } else {
                return "格式不对哟！";
            }
        } catch (Throwable e) {
            log.error("忘记异常，消息内容：" + message, e);
            return "呜呜..学习回路出现了异常";
        }
    }

    public static String pickResponse(String message) {
        try {
            Set<String> responseSet = new HashSet<>();
            for (String key : learnMap.keySet()) {
                if (message.contains(key)) {
                    String s = CollectionUtils.random(learnMap.get(key));
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
