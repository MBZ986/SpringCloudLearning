package cn.kgc.tesk;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.RoutesRefreshedEvent;
import org.springframework.cloud.netflix.zuul.filters.RouteLocator;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
public class RouteRefreshTask {

    @Autowired
    private RouteLocator routeLocator;

    @Autowired
    private ApplicationEventPublisher eventPublisher;

//    @Scheduled(fixedRate = 2000)
    public void onRefresh(){
        RoutesRefreshedEvent routesRefreshedEvent = new RoutesRefreshedEvent(routeLocator);
        eventPublisher.publishEvent(routesRefreshedEvent);
    }

}
