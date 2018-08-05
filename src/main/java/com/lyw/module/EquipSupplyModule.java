package com.lyw.module;

import com.lyw.util.PicUtils;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by yiweiliang.
 * User: yiweiliang1
 * Date: 2018/3/30
 */
public class EquipSupplyModule {

    private static String basePath = "F:/software/酷Q Pro/data/image/";

    private static String randomPoolPath = "equip-normal";

    public static String supply() {
        List<String> dirs = new ArrayList<>();
        boolean hasRare = false;
        for (int i = 0; i < 10; ++i) {
            String rollRes = rollLeft();
            if (!hasRare && (StringUtils.equals(rollRes, "4-weapon") || StringUtils.equals(rollRes, "4-stigmata"))) {
                hasRare = true;
            }
            if (i == 9 && !hasRare) {
                dirs.add(Math.random() < 0.4 ? "4-weapon" : "4-stigmata");
            } else {
                dirs.add(rollRes);
            }
            String rightRes = rollRight();
            dirs.add(rightRes);
        }
        dirs = sortResult(dirs);
        List<String> imagePaths = new ArrayList<>();
        for (String dir : dirs) {
            File[] images = new File(basePath + randomPoolPath, dir).listFiles();
            if (images != null && images.length != 0) {
                File selected = images[RandomUtils.nextInt(0, images.length)];
                imagePaths.add(basePath + randomPoolPath + "/" + dir + "/" + selected.getName());
            } else {
                throw new RuntimeException("可能缺少抽卡素材文件");
            }
        }
        String newFileName = "equipSupply." + RandomUtils.nextInt(0, 1000) + System.currentTimeMillis();
        File mergedFile = new File(basePath, newFileName);
        PicUtils.mergeImage(imagePaths, mergedFile);
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                if (mergedFile.exists()) {
                    mergedFile.delete();
                }
            }
        }, 10000);
        return newFileName;
    }

    private static List<String> sortResult(List<String> result) {
        List<String> sortedList = new ArrayList<>();
        for (String r : result) {
            if (StringUtils.equals(r, "4-weapon")
                    || StringUtils.equals(r, "4-stigmata")) {
                sortedList.add(0, r);
            } else {
                sortedList.add(r);
            }
        }
        return sortedList;
    }

    private static String rollLeft() {
        double r = Math.random();
        if (r < 0.0328) {
            return "4-weapon";
        } else if (r < 0.082) {
            return "4-stigmata";
        } else if (r < 0.199) {
            return "3-weapon";
        } else if (r < 0.55) {
            return "3-stigmata";
        } else if (r < 0.7279) {
            return "evo-item";
        } else if (r < 0.9057) {
            return "equip-exp";
        } else {
            return "gold-item";
        }
    }

    private static String rollRight() {
        double r = Math.random();
        if (r < 0.2) {
            return "2-weapon";
        } else if (r < 0.4) {
            return "2-stigmata";
        } else if (r < 0.6) {
            return "evo-item";
        } else if (r < 0.8) {
            return "equip-exp";
        } else {
            return "gold-item";
        }
    }

}
