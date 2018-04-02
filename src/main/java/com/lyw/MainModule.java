package com.lyw;

import org.nutz.mvc.annotation.*;
import org.nutz.mvc.ioc.provider.ComboIocProvider;

@Ok("json:compact")
@Fail("json:compact")
@SetupBy(value = MainSetup.class)
@IocBy(type = ComboIocProvider.class, args = {
        "*js", "ioc/",
        "*anno", "com.lyw",
        "*tx",
        "*async",
        "*quartz"})
@Modules
public class MainModule {
}
