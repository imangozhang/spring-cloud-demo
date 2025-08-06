package com.greenbook.gateway.predicate;

import jakarta.validation.constraints.NotEmpty;
import org.springframework.cloud.gateway.handler.predicate.AbstractRoutePredicateFactory;
import org.springframework.cloud.gateway.handler.predicate.GatewayPredicate;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.server.ServerWebExchange;

import java.util.List;
import java.util.function.Predicate;

@Component
public class GreyRoutePredicateFactory extends AbstractRoutePredicateFactory<GreyRoutePredicateFactory.Config> {

    public GreyRoutePredicateFactory() {
        super(Config.class);
    }

    @Override
    public List<String> shortcutFieldOrder() {
        return List.of("param", "value");
    }

    @Override
    public Predicate<ServerWebExchange> apply(Config config) {
        return new GatewayPredicate() {
            @Override
            public boolean test(ServerWebExchange serverWebExchange) {
                // localhost/s?wd=springboot&grey=user,zhangsan
                ServerHttpRequest request = serverWebExchange.getRequest();
                String first = request.getQueryParams().getFirst(config.getParam());
                return StringUtils.hasText(first) && first.equals(config.getValue());
            }
        };
    }

    @Validated
    public static class Config {
        @NotEmpty
        private String param;

        @NotEmpty
        private String value;

        public String getParam() {
            return param;
        }

        public String getValue() {
            return value;
        }

        public void setParam(String param) {
            this.param = param;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }
}
