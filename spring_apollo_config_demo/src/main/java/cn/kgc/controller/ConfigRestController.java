package cn.kgc.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class ConfigRestController {

    @Value("${key1}")
    private String key1;

    @RequestMapping("/apollo")
    public String getConfig(){
        return "key1 = " + key1;
    }
}
