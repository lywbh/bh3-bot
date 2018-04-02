package com.lyw;

import org.nutz.dao.Dao;
import org.nutz.dao.util.Daos;
import org.nutz.integration.quartz.NutQuartzCronJobFactory;
import org.nutz.ioc.Ioc;
import org.nutz.mvc.NutConfig;
import org.nutz.mvc.Setup;

public class MainSetup implements Setup {
    @Override
    public void init(NutConfig nc) {
        Ioc ioc = nc.getIoc();
        ioc.get(NutQuartzCronJobFactory.class);
        // 暂时用不到数据库
        /*Dao dao = ioc.get(Dao.class);
        Daos.createTablesInPackage(dao, "com.lyw", false);*/
    }

    @Override
    public void destroy(NutConfig nc) {

    }
}
