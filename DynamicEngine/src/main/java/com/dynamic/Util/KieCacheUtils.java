package com.dynamic.Util;

import org.kie.api.KieBase;
import org.kie.api.KieServices;
import org.kie.api.builder.*;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.internal.io.ResourceFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class KieCacheUtils {

    private Logger logger = LoggerFactory.getLogger(KieCacheUtils.class);
    private static final String RULES_PATH = "rules"+ File.separator;
    //KnowledgeBase 缓存(key：系统ID)
    private static Map<String, KieBase> ruleMap = new ConcurrentHashMap<>();
    private static KieCacheUtils instance = new KieCacheUtils();

    private KieCacheUtils() {
    }

    public static KieCacheUtils getInstance() {
        return instance;
    }

    public KieSession getDrlSessionCache(final String scene) {
        try {
            KieBase kbase = ruleMap.get(scene);
            if (kbase == null) {
                return getDrlSessionFile(scene);
            } else {
                return kbase.newKieSession();
            }
        } catch (Exception e) {
            logger.error("获取 KieBase 信息错误:{}", e.getMessage());
        }
        return null;
    }

    private KieSession getDrlSessionFile( final String scene) {
        try {
            KieServices kieServices = getKieServices();
            final KieRepository kieRepository = kieServices.getRepository();

            kieRepository.addKieModule(new KieModule() {
                @Override
                public ReleaseId getReleaseId() {
                    return kieRepository.getDefaultReleaseId();
                }
            });
            KieBuilder kieBuilder = kieServices.newKieBuilder(kieFileSystem(scene));
            Results results = kieBuilder.getResults();
            if (results.hasMessages(Message.Level.ERROR)) {
                System.out.println(results.getMessages());
                throw new IllegalStateException("### errors ###");
            }
            kieBuilder.buildAll();

            KieContainer kieContainer = kieServices.newKieContainer(kieRepository.getDefaultReleaseId());
            KieBase kBase = kieContainer.getKieBase();
            //放入缓存
            ruleMap.put(scene, kBase);
            return kBase.newKieSession();
        } catch (Exception e) {
            logger.error("规则引擎初始化失败，请查看错误信息:{}", e.getMessage());
        }
        return  null;
    }


    private KieFileSystem kieFileSystem(String sence) throws IOException {
        KieFileSystem kieFileSystem = getKieServices().newKieFileSystem();
        String filepath="",filename="";
        for (Resource file : getRuleFiles(sence)) {
            filepath=file.getFile().getPath();
            filename=filepath.substring(filepath.lastIndexOf(RULES_PATH)+6);
            kieFileSystem.write(ResourceFactory.newClassPathResource(RULES_PATH + filename, "UTF-8"));

        }
        return kieFileSystem;
    }

    private Resource[] getRuleFiles(String sence) throws IOException {
        ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
        return resourcePatternResolver.getResources("classpath*:" + RULES_PATH + sence+ File.separator+"*.*");
    }

    private KieServices getKieServices() {
        return KieServices.Factory.get();
    }
}
