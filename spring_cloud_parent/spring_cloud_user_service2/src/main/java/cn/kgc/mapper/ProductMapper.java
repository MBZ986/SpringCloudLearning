package cn.kgc.mapper;

import cn.kgc.entity.Product;

import java.util.List;

public interface ProductMapper {
    Product queryById(Integer id);
    List<Product> queryAll();
}
