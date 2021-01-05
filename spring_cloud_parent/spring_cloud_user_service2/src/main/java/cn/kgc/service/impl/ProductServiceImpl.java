package cn.kgc.service.impl;

import cn.kgc.entity.Product;
import cn.kgc.mapper.ProductMapper;
import cn.kgc.service.ProductService;
import org.hibernate.validator.internal.util.logging.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {


    @Autowired
    private ProductMapper productMapper;

    @Override
    public Product queryById(Integer id) {
        return productMapper.queryById(id);
    }

    @Override
    public List<Product> queryAll() {
        return productMapper.queryAll();
    }
}
