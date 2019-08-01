package com.dynamic.Controller;

import com.dynamic.Service.ReloadDroolsRules;
import com.dynamic.Service.MyService;
import org.drools.example.api.model.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MyController {

	@Autowired
	private MyService myService;

	@GetMapping("/dynamic-rule")
	public Message test() {
		return myService.run();
	}

	@Autowired
	private ReloadDroolsRules rules;

	@RequestMapping("/reload/{ruleId}")
	public String reload(@PathVariable("ruleId") Integer ruleId) {
		rules.reload(ruleId);
		return "ok";
	}
}
