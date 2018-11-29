package com.lyw;

import org.nutz.integration.quartz.NutQuartzCronJobFactory;
import org.nutz.ioc.Ioc;
import org.nutz.mvc.NutConfig;
import org.nutz.mvc.Setup;

public class MainSetup implements Setup {
    @Override
    public void init(NutConfig nc) {
        Ioc ioc = nc.getIoc();
        ioc.get(NutQuartzCronJobFactory.class);
    }

    @Override
    public void destroy(NutConfig nc) {

    }
}
