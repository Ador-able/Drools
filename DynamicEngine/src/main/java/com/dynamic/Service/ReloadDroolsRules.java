package com.dynamic.Service;

import com.dynamic.Dao.RulesMapper;
import org.drools.example.api.model.Rules;
import com.dynamic.Util.KieUtils;
import org.kie.api.KieServices;
import org.kie.api.builder.KieBuilder;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.Message;
import org.kie.api.builder.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 雷新宇
 * @version 1.0
 * @date 2019/7/31 14:47
 */
@Service
public class ReloadDroolsRules {

    @Autowired
    private RulesMapper rulesMapper;


    private KieServices kieServices = KieServices.Factory.get();

    /**
     * 刷新某条规则
     *
     * @param ruleId
     */
    public void reload(Integer ruleId) {
        // 从数据库加载的规则
        Rules rules = rulesMapper.selectByPrimaryKey(ruleId);
        if (rules != null) {

            KieFileSystem kfs = KieUtils.getKieFileSystem();

            System.out.println(">>>>>" + kfs);
            kfs.delete("src/main/resources/rules/" + rules.getName() + ".drl");
            kfs.write("src/main/resources/rules/" + rules.getName() + ".drl", rules.getContent());
            KieBuilder kieBuilder = kieServices.newKieBuilder(kfs).buildAll();
            Results results = kieBuilder.getResults();
            if (results.hasMessages(Message.Level.ERROR)) {
                System.out.println(results.getMessages());
                throw new IllegalStateException("### errors ###");
            }

            KieUtils.setKieContainer(kieServices.newKieContainer(getKieServices().getRepository().getDefaultReleaseId()));
            System.out.println("新规则重载成功" + rules.getContent());
        }


    }

    /**
     * 加载所有规则
     */
    public void reloadAll() {
        List<Rules> rules = rulesMapper.selectAll();
        KieFileSystem kfs = KieUtils.getKieFileSystem();
        for (Rules rule : rules) {
            System.out.println(">>>>>" + kfs);
            kfs.delete("src/main/resources/rules/" + rule.getName() + ".drl");
            kfs.write("src/main/resources/rules/" + rule.getName() + ".drl", rule.getContent());
        }

        KieBuilder kieBuilder = kieServices.newKieBuilder(kfs).buildAll();
        Results results = kieBuilder.getResults();
        if (results.hasMessages(Message.Level.ERROR)) {
            System.out.println(results.getMessages());
            throw new IllegalStateException("### errors ###");
        }

        KieUtils.setKieContainer(kieServices.newKieContainer(getKieServices().getRepository().getDefaultReleaseId()));
        System.out.println("初始化规则成功");
    }

    private KieServices getKieServices() {
        return KieServices.Factory.get();
    }
}
