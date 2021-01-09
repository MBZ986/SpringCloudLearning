package cn.kgc.controller;

import cn.kgc.dto.Msg;
import com.netflix.hystrix.HystrixCommandProperties;
import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.netflix.hystrix.contrib.javanica.conf.HystrixPropertiesManager;
import com.netflix.ribbon.proxy.annotation.Hystrix;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * RestTemplate + Robbin + Hystrix
 * Rest方式调用服务，通过Robbin均衡负载，Hystrix熔断降级
 */
@Slf4j
@RestController
@RequestMapping("/provider3")
//指定熔断器默认属性
@DefaultProperties(defaultFallback = "defaultFallback")
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

    @HystrixCommand(fallbackMethod = "hystrix_queryById",groupKey = "product",commandProperties = {
            @HystrixProperty(name = HystrixPropertiesManager.CIRCUIT_BREAKER_ERROR_THRESHOLD_PERCENTAGE,value = "20")
    })
    @RequestMapping("/queryById/{id}")
    public Msg queryById(@PathVariable("id") Integer id) {
        log.debug("查询ID："+id);
        return restTemplate.getForObject(PROVIDER_PATH + "queryById/" + id, Msg.class);
    }

    @HystrixCommand(fallbackMethod = "hystrix_queryAll")
    @RequestMapping("/queryAll")
    public Msg queryAll() {

        return restTemplate.getForObject(PROVIDER_PATH + "queryAll", Msg.class);
    }

    /**
     * 降级的方法必须和业务方法有相同的参数和返回值
     * @param id
     * @return
     */

    public Msg hystrix_queryById(@PathVariable("id") Integer id) {
        log.warn(API_PATH+"服务发生降级");
        System.out.println(id);
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        log.warn("当前调用降级方法"+stackTrace[1].getMethodName());
        return Msg.hystrix();
    }

    public Msg hystrix_queryAll() {
        log.warn(API_PATH+"服务发生降级");
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        log.warn("当前调用降级方法"+stackTrace[1].getMethodName());
        return Msg.hystrix();
    }

    /**
     * 默认的降级方法，没有为业务方法指定降级方法时会调用该方法
     * 该方法不能具备参数，但返回值必须与业务方法的返回值相同
     * @return
     */
    public Msg defaultFallback(){
        log.warn(API_PATH+"服务发生降级");
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        log.warn("当前调用降级方法"+stackTrace[1].getMethodName());
        return Msg.hystrix();
    }
}
