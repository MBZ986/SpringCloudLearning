package cn.kgc.controller;

import cn.kgc.dto.Msg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * RestTemplate
 */
@RestController
@RequestMapping("/provider3")
public class DemoController3 {
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private DiscoveryClient discoveryClient;

    private static final String API_PATH = "product/";
    private static String PROVIDER_PATH = null;

    public DemoController3() {
        PROVIDER_PATH = String.format("http://USER-SERVICE/%s", API_PATH);
        System.out.println(PROVIDER_PATH);
    }

    @RequestMapping("/queryById/{id}")
    public Msg queryById(@PathVariable("id") Integer id) {
        System.out.println(id);
        return restTemplate.getForObject(PROVIDER_PATH + "queryById/" + id, Msg.class);
    }

    @RequestMapping("/queryAll")
    public Msg queryAll() {

        return restTemplate.getForObject(PROVIDER_PATH + "queryAll", Msg.class);
    }
}
