package cn.kgc.controller;

import cn.kgc.dto.Msg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * 结合Ribbon进行均衡负载，hostname为服务Id
 * Ribbon自动拦截RestTemplate发起的请求，将服务Id替换为服务提供方的ip和端口
 */
@RestController
@RequestMapping("/provider2")
public class DemoController2 {
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private DiscoveryClient discoveryClient;

    private static final String API_PATH = "product/";
    private static String PROVIDER_PATH = null;

    public DemoController2() {
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
