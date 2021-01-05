package cn.kgc.service.hystrix;

import cn.kgc.dto.Msg;
import cn.kgc.service.IProductService;

/**
 * Feign的降级会走这个实现
 */
public class IProductServiceHystrix implements IProductService {
    @Override
    public Msg queryById(Integer id) {
        return Msg.fail();
    }

    @Override
    public Msg queryAll() {
        return Msg.fail();
    }
}
