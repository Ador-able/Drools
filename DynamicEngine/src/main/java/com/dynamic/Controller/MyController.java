package com.dynamic.Controller;

import com.dynamic.Service.MyService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.drools.example.api.model.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class MyController {

	@Autowired
	private MyService myService;

	private static Logger logger= LoggerFactory.getLogger(MyController.class);

	@GetMapping("/dynamic-rule")
	public Message test() {
		return myService.run();
	}

//	@Autowired
//	private ReloadDroolsRules rules;
//
//	@GetMapping("/reload/{ruleId}")
//	public String reload(@PathVariable("ruleId") Integer ruleId) {
//		rules.reload(ruleId);
//		return "ok";
//	}

	@PostMapping("/run")
	public void loadModel(@RequestBody String json) {
		ObjectMapper mapper = new ObjectMapper();
		JsonNode jsonNode = null;
		try {
			jsonNode = mapper.readTree(json);
		} catch (IOException e) {
			e.printStackTrace();
		}
		myService.run(jsonNode);
	}
}
