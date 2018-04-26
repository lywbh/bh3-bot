package com.lyw.module;

import com.lyw.dmo.Chengyu;
import com.lyw.util.CollectionUtils;
import lombok.extern.slf4j.Slf4j;
import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.nutz.dao.util.DaoUp;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by yiweiliang.
 * User: yiweiliang1
 * Date: 2018/4/3
 */
@Slf4j
public class IdiomGameModule {

    private static Map<Long, List<Chengyu>> gameMap = new HashMap<>();

    public static Boolean checkIdiom(Long groupId, String phrase) {
        List<Chengyu> groupGaming;
        if (gameMap.containsKey(groupId)) {
            groupGaming = gameMap.get(groupId);
        } else {
            groupGaming = new ArrayList<>();
            gameMap.put(groupId, groupGaming);
        }
        if (groupGaming.stream()
                .map(Chengyu::getChengYu).collect(Collectors.toList())
                .contains(phrase)) {
            return false;
        }
        Cnd condition;
        if (groupGaming.isEmpty()) {
            condition = null;
        } else {
            String[] pyTab = groupGaming.get(groupGaming.size() - 1).getPingYin().split(" ");
            String lastPy = pyTab[pyTab.length - 1];
            condition = Cnd.where("PingYin", "like", lastPy + " %");
        }
        List<Chengyu> resultList = DaoUp.me().dao().query(Chengyu.class, condition);
        Optional<Chengyu> foundCy = resultList.stream()
                .filter(cy -> phrase.equals(cy.getChengYu())).findAny();
        foundCy.ifPresent(groupGaming::add);
        return foundCy.isPresent();
    }

    public static String pickIdiom(Long groupId, String lastWord) {
        List<Chengyu> groupGaming;
        if (gameMap.containsKey(groupId)) {
            groupGaming = gameMap.get(groupId);
        } else {
            groupGaming = new ArrayList<>();
            gameMap.put(groupId, groupGaming);
        }
        Dao dao = DaoUp.me().dao();
        Cnd condition;
        if (lastWord != null) {
            Chengyu cy = dao.fetch(Chengyu.class, lastWord);
            String[] pyArr = cy.getPingYin().split(" ");
            String lastPy = pyArr[pyArr.length - 1];
            condition = Cnd.where("PingYin", "like", lastPy + " %");
        } else {
            condition = null;
        }
        List<Chengyu> resultList = dao.query(Chengyu.class, condition);
        List<Chengyu> filterResult = resultList.stream()
                .filter(chengyu -> !groupGaming.stream().map(Chengyu::getChengYu)
                        .collect(Collectors.toList()).contains(chengyu.getChengYu()))
                .collect(Collectors.toList());
        if (!filterResult.isEmpty()) {
            Chengyu cy = CollectionUtils.random(filterResult);
            groupGaming.add(cy);
            return cy.getChengYu();
        } else {
            return null;
        }
    }

    public static synchronized String gameStep(Long groupId, String message) {
        Boolean cyValid = checkIdiom(groupId, message);
        if (!cyValid) {
            return "成语不对或者已经被用过啦！";
        }
        String picked = pickIdiom(groupId, message);
        if (picked == null) {
            gameMap.remove(groupId);
            return "嘤嘤嘤..我找不到词惹，游戏结束";
        } else {
            return picked;
        }
    }

}
