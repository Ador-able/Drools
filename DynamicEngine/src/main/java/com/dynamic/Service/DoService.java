package com.dynamic.Service;

import com.dynamic.Util.KieCacheUtils;
import com.dynamic.Util.KieUtils;
import com.zyc.fact.model.SysHead;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class DoService {
    private static final Logger logger = LoggerFactory.getLogger(DoService.class);
    public void execute(SysHead sysHead, Object questBody, Object resposeBody){
        KieContainer kieContainer=KieUtils.getKieContainer();

        KieSession kieSession=kieContainer.newKieSession();
        kieSession.insert(sysHead);
        kieSession.insert(questBody);
        kieSession.insert(resposeBody);

        int ruleFiredCount = kieSession.fireAllRules();
        logger.info("触发了" + ruleFiredCount + "条规则");

        kieSession.dispose();
    }

    public void execute2(SysHead sysHead, Object questBody, Object resposeBody,String sysid){

        KieSession kieSession= KieCacheUtils.getInstance().getDrlSessionCache(sysid);
        kieSession.insert(sysHead);
        kieSession.insert(questBody);
        kieSession.insert(resposeBody);
        int ruleFiredCount = kieSession.fireAllRules();

        logger.info("触发了" + ruleFiredCount + "条规则");

        kieSession.dispose();
    }
}
