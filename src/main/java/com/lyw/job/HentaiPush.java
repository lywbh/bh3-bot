package com.lyw.job;

import com.lyw.module.KonachanModule;
import com.lyw.util.CqpHttpApi;
import lombok.extern.slf4j.Slf4j;
import org.nutz.ioc.loader.annotation.IocBean;
import org.quartz.Job;
import org.quartz.JobExecutionContext;

@Slf4j
@IocBean
public class HentaiPush implements Job {

    private static final int oncePush = 5;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) {
        for (int i = 0; i < oncePush; ++i) {
            String picUrl = KonachanModule.randomPic("");
            GroupConfig.getJobsList().forEach(groupId ->
                    CqpHttpApi.getInstance().sendGroupMsg(groupId, "[CQ:image,file=" + picUrl + "]"));
        }
    }
}
