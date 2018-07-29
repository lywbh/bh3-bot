package com.lyw.job;

import com.alibaba.fastjson.JSON;
import com.lyw.module.GroupRepeatModule;
import com.lyw.module.LearningModule;
import com.lyw.util.FileUtils;
import lombok.extern.slf4j.Slf4j;
import org.nutz.ioc.loader.annotation.IocBean;
import org.quartz.Job;
import org.quartz.JobExecutionContext;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;

import static com.lyw.module.LearningModule.switchPath;

/**
 * @Description
 * @ProjectName maintain-robot
 * @Package com.lyw.job
 * @Author yiweiliang
 * @CreateDate 2018/7/9
s */
@Slf4j
@IocBean
public class StoreLearnSwitch implements Job {

    @Override
    public void execute(JobExecutionContext jobExecutionContext) {
        try {
            log.info("定时导出学习开关：" + switchPath);
            FileUtils.clearFile(switchPath);
            String storeStr = JSON.toJSONString(LearningModule.learnSwitch);
            FileOutputStream writerStream = new FileOutputStream(switchPath);
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(writerStream, "UTF-8"));
            writer.write(storeStr);
            writer.close();
        } catch (Throwable e) {
            log.error("定时导出学学习开关异常：" + switchPath, e);
        }
    }
}
