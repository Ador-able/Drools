package com.dynamic.Util;

import org.kie.api.builder.KieFileSystem;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

/**
 * @author 雷新宇
 * @version 1.0
 * @date 2019/7/31 14:33
 */
public class KieUtils {


        private static KieFileSystem kieFileSystem;

        private static KieContainer kieContainer;

        private static KieSession kieSession;

        public static KieFileSystem getKieFileSystem() {
            return kieFileSystem;
        }

        public static void setKieFileSystem(KieFileSystem kieFileSystem) {
            KieUtils.kieFileSystem = kieFileSystem;
        }

        public static KieContainer getKieContainer() {
            return kieContainer;
        }

        public static void setKieContainer(KieContainer kieContainer) {
            KieUtils.kieContainer = kieContainer;
        }

        public static KieSession getKieSession() {
            return kieSession;
        }

        public static void setKieSession(KieSession kieSession) {
            KieUtils.kieSession = kieSession;
        }

}
