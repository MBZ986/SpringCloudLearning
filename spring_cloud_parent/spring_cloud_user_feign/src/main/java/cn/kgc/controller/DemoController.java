package cn.kgc.controller;

import cn.kgc.dto.Msg;
import cn.kgc.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/feign")
public class DemoController {

    @Autowired
    private IProductService productService;

    @RequestMapping("/queryById/{id}")
    public Msg queryById(@PathVariable("id") Integer id) {
        System.out.println(id);
        return productService.queryById(id);
    }

    @RequestMapping("/queryAll")
    public Msg queryAll() {
        return productService.queryAll();
    }
}
