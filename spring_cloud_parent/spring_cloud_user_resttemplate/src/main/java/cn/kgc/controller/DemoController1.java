package cn.kgc.controller;

import cn.kgc.dto.Msg;
import cn.kgc.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;

/**
 * RestTemplate结合Eureka服务注册与发现发起远程过程调用
 */
@RestController
@RequestMapping("/provider1")
public class DemoController1 {
    @Autowired
    private RestTemplate restTemplate;

//    @Autowired
    private DiscoveryClient discoveryClient;

    private static final String API_PATH = "product/";
    private static String PROVIDER_PATH = null;

    public DemoController1(DiscoveryClient discoveryClient) {
        this.discoveryClient = discoveryClient;
        List<ServiceInstance> instances = discoveryClient.getInstances("USER_SERVICE");
        ServiceInstance serviceInstance = instances.get(0);
        PROVIDER_PATH = String.format("http://%s:%s/%s", serviceInstance.getHost(), serviceInstance.getPort(), API_PATH);
    }

    @RequestMapping("/queryById/{id}")
    public Msg queryById(@PathVariable("id") Integer id) {
        System.out.println(id);
        System.out.println(PROVIDER_PATH);
        return restTemplate.getForObject(PROVIDER_PATH + "queryById/" + id, Msg.class);
    }

    @RequestMapping("/queryAll")
    public Msg queryAll() {
        System.out.println(PROVIDER_PATH);
        return restTemplate.getForObject(PROVIDER_PATH + "queryAll", Msg.class);
    }
}
