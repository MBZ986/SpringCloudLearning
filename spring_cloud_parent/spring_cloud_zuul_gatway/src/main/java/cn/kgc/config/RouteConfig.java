package cn.kgc.config;

import cn.kgc.zuul.DynamicRouteLocator;
import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
public class RouteConfig {

    @Autowired
    private ZuulProperties zuulProperties;

    @Autowired
    private ServerProperties serverProperties;

    @Autowired
    public JdbcTemplate jdbcTemplate;

    @Bean
    public DynamicRouteLocator dynamicRouteLocator(){
        String contextPath = this.serverProperties.getServlet().getContextPath();
        System.out.println(contextPath);
        DynamicRouteLocator dynamicRouteLocator = new DynamicRouteLocator(contextPath,zuulProperties);
        dynamicRouteLocator.setRestTemplate(this.jdbcTemplate);
        return dynamicRouteLocator;
    }
}
