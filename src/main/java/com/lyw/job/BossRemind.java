package com.lyw.job;

import com.lyw.util.CqpHttpApi;
import lombok.extern.slf4j.Slf4j;
import org.nutz.ioc.loader.annotation.IocBean;
import org.quartz.Job;
import org.quartz.JobExecutionContext;

/**
 * @Description
 * @ProjectName maintain-robot
 * @Package com.lyw.job
 * @Author yiweiliang
 * @CreateDate 2018/4/2
 */
@Slf4j
@IocBean
public class BossRemind implements Job {

    @Override
    public void execute(JobExecutionContext jobExecutionContext) {
        String msg = "崩崩崩，舰团boss启动~不打boss的都提了！";
        GroupConfig.getJobsList().forEach(groupId -> CqpHttpApi.getInstance().sendGroupMsg(groupId, msg));
    }
}
