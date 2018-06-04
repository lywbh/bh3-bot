package com.lyw.module;

import com.lyw.util.PicUtils;
import com.xiaoleilu.hutool.io.FileUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Optional;

@Slf4j
public class ImageWordModule {

    private static final String imagePath = "F:/software/酷Q Pro/data/image/";

    public static String wordAdd(String fileName, String content) {
        List<String> fileLines = FileUtil.readLines(imagePath + fileName + ".cqimg", "utf-8");
        Optional<String> targetLine = fileLines.stream().filter(line -> line.startsWith("url")).findAny();
        if (targetLine.isPresent()) {
            String fileUrl = StringUtils.substringAfter(targetLine.get(), "url=");
            String targetName = System.currentTimeMillis() + fileName;
            PicUtils.addWord(fileUrl, imagePath + targetName, content);
            return targetName;
        }
        return null;
    }

}
