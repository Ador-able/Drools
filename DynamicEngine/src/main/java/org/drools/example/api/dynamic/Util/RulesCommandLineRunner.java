package org.drools.example.api.dynamic.Util;

import org.drools.example.api.dynamic.Service.ReloadDroolsRules;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * @author 雷新宇
 * @version 1.0
 * @date 2019/7/31 14:45
 */
@Component
public class RulesCommandLineRunner implements CommandLineRunner {

    @Autowired
    private ReloadDroolsRules reloadDroolsRules;

    @Override
    public void run(String... args) throws Exception {
        reloadDroolsRules.reloadAll();
    }
}
