package com.lyw.job;

import com.lyw.module.KonachanModule;
import com.lyw.util.CqpHttpApi;
import lombok.extern.slf4j.Slf4j;
import org.nutz.ioc.loader.annotation.IocBean;
import org.quartz.Job;
import org.quartz.JobExecutionContext;

import java.util.List;

@Slf4j
@IocBean
public class HentaiPush implements Job {

    private static final int oncePush = 5;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) {
        List<String> picUrls = KonachanModule.randomPic(oncePush);
        for (String url : picUrls) {
            GroupConfig.getJobsList().forEach(groupId ->
                    CqpHttpApi.getInstance().sendGroupMsg(groupId, "[CQ:image,file=" + url + "]"));
        }
    }
}
