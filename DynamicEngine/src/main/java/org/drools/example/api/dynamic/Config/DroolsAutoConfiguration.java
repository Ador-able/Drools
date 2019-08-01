package org.drools.example.api.dynamic.Config;

import org.drools.example.api.dynamic.Util.KieUtils;
import org.kie.api.KieBase;
import org.kie.api.KieServices;
import org.kie.api.builder.KieBuilder;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.KieRepository;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.spring.KModuleBeanFactoryPostProcessor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import java.io.IOException;

/**
 * @author 雷新宇
 * @version 1.0
 * @date 2019/7/31 14:27
 */
@Configuration
public class DroolsAutoConfiguration {
    private static final String RULES_PATH = "rules/";

    @Bean
    @ConditionalOnMissingBean(KieFileSystem.class)
    public KieFileSystem kieFileSystem() throws IOException {
        KieFileSystem kieFileSystem = getKieServices().newKieFileSystem();
//        从项目的本地文件写入KieFileSystem(内存文件系统)，我们这里不写入规则，而是从数据库动态加载
//        for (Resource file : getRuleFiles()) {
//            kieFileSystem.write(ResourceFactory.newClassPathResource(RULES_PATH + file.getFilename(), "UTF-8"));
//        }
        // 放到静态对象中便于获取，供其他使用的地方获取和更新
        KieUtils.setKieFileSystem(kieFileSystem);
        return kieFileSystem;
    }

    private Resource[] getRuleFiles() throws IOException {
        ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
        return resourcePatternResolver.getResources("classpath*:" + RULES_PATH + "**/*.*");
    }

    @Bean
    @ConditionalOnMissingBean(KieContainer.class)
    public KieContainer kieContainer() throws IOException {
        final KieRepository kieRepository = getKieServices().getRepository();

        kieRepository.addKieModule(() -> kieRepository.getDefaultReleaseId());

        KieBuilder kieBuilder = getKieServices().newKieBuilder(kieFileSystem());
        kieBuilder.buildAll();

        // 放到静态对象中便于获取，供其他使用的地方获取和更新
        KieContainer kieContainer = getKieServices().newKieContainer(kieRepository.getDefaultReleaseId());
        KieUtils.setKieContainer(kieContainer);
        return kieContainer;
    }

    private KieServices getKieServices() {
        return KieServices.Factory.get();
    }

    @Bean
    @ConditionalOnMissingBean(KieBase.class)
    public KieBase kieBase() throws IOException {
        return kieContainer().getKieBase();
    }

    @Bean
    @ConditionalOnMissingBean(KieSession.class)
    public KieSession kieSession() throws IOException {
        KieSession kieSession = kieContainer().newKieSession();
        // 放到静态对象中便于获取
        KieUtils.setKieSession(kieSession);
        return kieSession;
    }

    @Bean
    @ConditionalOnMissingBean(KModuleBeanFactoryPostProcessor.class)
    public KModuleBeanFactoryPostProcessor kiePostProcessor() {
        return new KModuleBeanFactoryPostProcessor();
    }
}