package cn.kgc.config;

import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RoundRobinRule;
import org.apache.catalina.servlets.CGIServlet;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.util.Collection;

@Configuration
public class CommConfig {

    /**
     * 需要使用Ribbon+RestTemplate的方式进行服务远程调用，因此需要注入RestTemplatee
     *
     * @LoadBalanced 开启Ribbon负载均衡
     * @return
     */
    @Bean
    @LoadBalanced
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }

    /**
     * 采用轮询的负载均衡策略
     * @return
     */
    @Bean
    public IRule iRule(){
        return new RoundRobinRule();
    }

//    public ServletRegistrationBean servletRegistrationBean(){
////        ServletRegistrationBean<CGIServlet> cgiServletServletRegistrationBean = new ServletRegistrationBean<>();
////        cgiServletServletRegistrationBean.setUrl
//
//    }
}
