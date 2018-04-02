package com.lyw.job;

import com.alibaba.fastjson.JSON;
import com.lyw.module.LearningModule;
import com.lyw.util.FileUtils;
import lombok.extern.slf4j.Slf4j;
import org.nutz.ioc.loader.annotation.IocBean;
import org.quartz.Job;
import org.quartz.JobExecutionContext;

import java.io.*;

import static com.lyw.module.LearningModule.filePath;

/**
 * @Description
 * @ProjectName maintain-robot
 * @Package com.lyw.job
 * @Author yiweiliang
 * @CreateDate 2018/4/2
 */
@Slf4j
@IocBean
public class StoreLearned implements Job {

    @Override
    public void execute(JobExecutionContext jobExecutionContext) {
        try {
            log.info("定时导出学习内容：" + filePath);
            FileUtils.clearFile(filePath);
            String storeStr = JSON.toJSONString(LearningModule.learnMap);
            FileOutputStream writerStream = new FileOutputStream(filePath);
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(writerStream, "UTF-8"));
            writer.write(storeStr);
            writer.close();
        } catch (Throwable e) {
            log.error("定时导出学习内容异常：" + filePath, e);
        }
    }
}
