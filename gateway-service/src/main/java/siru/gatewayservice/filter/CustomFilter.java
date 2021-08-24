package siru.gatewayservice.filter;


import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class CustomFilter extends AbstractGatewayFilterFactory<CustomFilter.Config> {

    public CustomFilter() {
        super(Config.class);
        log.info("Custom Filter Construct");
    }

    @Override
    public GatewayFilter apply(Config config) {
        // Custom Pre Filter
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            ServerHttpResponse response = exchange.getResponse();

            log.info("Custom PRE Filter Request ID -> {}, Path -> {}", request.getId(), request.getPath());

            // Custom POST Filter
            return chain.filter(exchange).then(Mono.fromRunnable(()->{
                log.info("Custom POST Filter: Response Code -> {}", response.getStatusCode());
            }));

        };
    }

    public static class Config {
        // Put The Configuration
    }
}


