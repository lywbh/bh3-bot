package com.lyw.job;

import com.lyw.module.YandereModule;
import com.lyw.util.CqpHttpApi;
import lombok.extern.slf4j.Slf4j;
import org.nutz.ioc.loader.annotation.IocBean;
import org.quartz.Job;
import org.quartz.JobExecutionContext;

import java.util.List;

@Slf4j
@IocBean
public class HentaiPush implements Job {

    @Override
    public void execute(JobExecutionContext jobExecutionContext) {
        String url = YandereModule.randomPic("safe");
        GroupConfig.getJobsList().forEach(groupId ->
                CqpHttpApi.getInstance().sendGroupMsg(groupId, "[CQ:image,file=" + url + "]"));
    }

}
