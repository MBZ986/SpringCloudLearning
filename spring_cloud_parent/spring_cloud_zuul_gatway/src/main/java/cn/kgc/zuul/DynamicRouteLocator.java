package cn.kgc.zuul;

import ch.qos.logback.core.joran.util.beans.BeanUtil;
import com.netflix.zuul.context.RequestContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.boot.autoconfigure.jdbc.JdbcTemplateAutoConfiguration;
import org.springframework.cloud.netflix.zuul.filters.RefreshableRouteLocator;
import org.springframework.cloud.netflix.zuul.filters.SimpleRouteLocator;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Slf4j
public class DynamicRouteLocator extends SimpleRouteLocator implements RefreshableRouteLocator {
    private ZuulProperties zuulProperties;
    private JdbcTemplate jdbcTemplate;

    public DynamicRouteLocator(String servletPath, ZuulProperties properties) {
        super(servletPath, properties);
        this.zuulProperties = properties;
    }

    public void setRestTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void refresh() {
        super.doRefresh();
    }

    @Override
    public Map<String, ZuulProperties.ZuulRoute> locateRoutes() {
        HashMap<String, ZuulProperties.ZuulRoute> routeMap = new HashMap<>();

        routeMap.putAll(super.locateRoutes());
        routeMap.putAll(this.locateRoutesForDB());

        HashMap<String, ZuulProperties.ZuulRoute> values = new HashMap<>();
        Set<Map.Entry<String, ZuulProperties.ZuulRoute>> entries = routeMap.entrySet();
        for (Map.Entry<String, ZuulProperties.ZuulRoute> route : entries) {
            String path = route.getKey();
            if (!path.startsWith("/")) {
                path += "/" + path;
            }
            if (StringUtils.hasText(zuulProperties.getPrefix())) {
                path = zuulProperties.getPrefix() + path;
                if (!path.startsWith("/")) {
                    path = "/" + path;
                }
            }
            values.put(path, route.getValue());
        }
        log.info("当前路由路径有：");
        for (Map.Entry<String, ZuulProperties.ZuulRoute> route : routeMap.entrySet()) {
            log.info(route.getKey());
            log.info(route.getValue().getServiceId());
//            log.info(route.getValue().getUrl() + "\n");
        }
        log.info("-----------------");


        return routeMap;
    }

    public Map<String, ZuulProperties.ZuulRoute> locateRoutesForDB() {
        HashMap<String, ZuulProperties.ZuulRoute> routeMap = new HashMap<>();

        List<ZuulProperties.ZuulRoute> query = jdbcTemplate.query("SELECT * FROM t_route", new BeanPropertyRowMapper<>(ZuulProperties.ZuulRoute.class));

        for (ZuulProperties.ZuulRoute zuulRoute : query) {
            if (StringUtils.hasText(zuulRoute.getPath()) && StringUtils.hasText(zuulRoute.getServiceId()) && StringUtils.hasText(zuulRoute.getUrl())) {
                routeMap.put(zuulRoute.getPath(), zuulRoute);
            }
        }

        return routeMap;
    }

}
