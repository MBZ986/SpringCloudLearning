package cn.kgc.controller;

import cn.kgc.dto.Msg;
import cn.kgc.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestController
@RequestMapping("/product")
public class ProductController {
    @Autowired
    private ProductService productService;

    @RequestMapping("/queryById/{id}")
    public Msg queryById(@PathVariable("id") Integer id){
        System.out.println(id);
        return Msg.data(productService.queryById(id));
    }

    @RequestMapping("/queryAll")
    public Msg queryAll(){
        return Msg.data(productService.queryAll());
    }

}
