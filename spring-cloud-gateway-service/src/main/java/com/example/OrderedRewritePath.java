package com.example;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.OrderedGatewayFilter;
import org.springframework.cloud.gateway.filter.factory.RewritePathGatewayFilterFactory;
import org.springframework.stereotype.Component;

@Component
public class OrderedRewritePath extends RewritePathGatewayFilterFactory {
    private final int order = 100;

    public GatewayFilter apply(Config config) {
        return new OrderedGatewayFilter(super.apply(config), this.order);
    }
}