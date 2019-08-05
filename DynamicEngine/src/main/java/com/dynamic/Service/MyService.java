package com.dynamic.Service;

import com.dynamic.Util.CustomCL;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.drools.core.ClassObjectFilter;
import org.drools.example.api.model.Message;
import org.kie.api.KieServices;
import org.kie.api.builder.KieScanner;
import org.kie.api.builder.ReleaseId;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

/**
 * @author lxy
 * @version 1.0
 * @date 2019/7/27 16:47
 */
@Service
public class MyService {

	private static Logger logger= LoggerFactory.getLogger(MyService.class);
	private KieContainer kContainer;

	@PostConstruct
	public void setUp() {
		KieServices ks = KieServices.Factory.get();

		ReleaseId releaseId = ks.newReleaseId("com.lxy", "Kjar", "0.0.1-SNAPSHOT");

		kContainer = ks.newKieContainer(releaseId);
		KieScanner kScanner = ks.newKieScanner(kContainer);

		// Start the KieScanner polling the Maven repository every 10 seconds
		kScanner.start(10000L);
	}

	public Message run() {

		//KieHelper kieHelper=new KieHelper();

		KieSession kSession = kContainer.newKieSession("ksession1");
		kSession.setGlobal("out", System.out);

		kSession.insert(new Message("Dave", "Hello, HAL. Do you read me, HAL?"));
		kSession.fireAllRules();

		Message result = null;

		for (Object msg : kSession.getObjects(new ClassObjectFilter(Message.class))) {
			if (((Message) msg).getName().equals("HAL")) {
				result=(Message)msg;
			}
		}
		return result;
	}

	public void run(JsonNode jsonNode)
	{
		JsonNode rule_no = jsonNode.findValue("rule_NO");
		String model = rule_no.asText();
		ObjectMapper mapper = new ObjectMapper();
		CustomCL customCL = new CustomCL("E:\\IdeaProjects\\Drools\\Model\\target\\classes\\org\\drools\\example\\api\\model", new String[]{model});
		logger.info(customCL.getParent().toString());
		try {
			Class<?> aClass = customCL.loadClass("Hello");
			//获取构造函数类的对象
			Constructor<?>[] constructors = aClass.getConstructors();
			// 使用构造器对象的newInstance方法初始化对象
			Object obj = constructors[0].newInstance("你好");
			Method m = obj.getClass().getMethod("getInfo");
			System.out.println(m.invoke(obj));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
