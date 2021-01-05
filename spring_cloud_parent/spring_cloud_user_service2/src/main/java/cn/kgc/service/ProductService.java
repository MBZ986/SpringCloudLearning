package cn.kgc.service;

import cn.kgc.entity.Product;

import java.util.List;

public interface ProductService {
    Product queryById(Integer id);
    List<Product> queryAll();
}
