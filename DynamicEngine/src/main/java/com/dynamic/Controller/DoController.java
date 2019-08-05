package com.dynamic.Controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dynamic.Service.DoService;
import com.zyc.fact.model.SysHead;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;


@RestController
public class DoController {

    public static final String RULENO = "ruleNo";
    public static final String PHEAD = "sysHead";
    public static final String QUESTBODY = "request";
    public static final String RESPOSEBODY = "resposebody";
    public static final String PackPatch = "com.zyc.fact.model";
    private static final Logger logger = LoggerFactory.getLogger(DoController.class);
    @Autowired
    private DoService doService;

    @GetMapping("/zyc/{sysid}")
    public Map<String, Object> dohander2(@RequestBody Map<String, Object> map, @PathVariable("sysid") String sysid) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        logger.info("开始触发交易");
        Map<String, Object> Pheader = (Map<String, Object>) map.get(this.PHEAD);
        Map<String, Object> rquestBody = (Map<String, Object>) map.get(this.QUESTBODY);
        String ruleNo = (String) Pheader.get(this.RULENO);
        Class zlassbody = null;
        Class reulst = null;
        zlassbody = Class.forName(this.PackPatch + "." + ruleNo);
        reulst = Class.forName(this.PackPatch + "." + ruleNo + "Result");
        Object bodyObject = JSON.parseObject(JSONObject.toJSONString(rquestBody), zlassbody);

        SysHead sysHead = JSON.parseObject(JSONObject.toJSONString(Pheader), SysHead.class);

        Object respose = null;

        respose = reulst.newInstance();

        //触发规则
        doService.execute2(sysHead, bodyObject, respose, sysid);
        Map<String, Object> respostMap = new HashMap<String, Object>();
        respostMap.put(this.PHEAD, sysHead);
        respostMap.put(this.RESPOSEBODY, respose);
        return respostMap;
    }
}
