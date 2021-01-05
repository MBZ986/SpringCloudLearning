package cn.kgc.service;

import cn.kgc.dto.Msg;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Feign方式调用服务
 */
@FeignClient("user-service")
@RequestMapping("/product")
public interface IProductService {
    @RequestMapping("/queryById/{id}")
    Msg queryById(@PathVariable("id") Integer id);
    @RequestMapping("/queryAll")
    Msg queryAll();
}
