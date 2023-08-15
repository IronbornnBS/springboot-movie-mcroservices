package io.alchemia.moviegatewayservice.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationFilter extends AbstractGatewayFilterFactory<Config> {

    @Value("${identity-service.validate-token}")
    private String validateTokenUrl;

    private final RouteValidatorUtility validator;

    private final WebClientHelper webClientHelper;

    public AuthenticationFilter(final RouteValidatorUtility validator,
                                final WebClientHelper webClientHelper) {
        this.validator = validator;
        this.webClientHelper = webClientHelper;
    }

    @Override
    public GatewayFilter apply(Config config) {
        return ((exchange, chain) -> {

            if (validator.isSecured.test(exchange.getRequest())) {
                if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                    throw new RuntimeException("missing authorization header");
                }

                String authHeader = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
                if (authHeader != null && authHeader.startsWith("Bearer ")) {
                    authHeader = authHeader.substring(7);
                    try {
                        webClientHelper.getWebClientBuilder(
                                HttpMethod.GET,
                                validateTokenUrl.concat(authHeader),
                                String.class);

                    } catch (Exception e) {
                        System.out.println("invalid access...!");
                        throw new RuntimeException("unauthorized access to application");
                    }
                }
            }

            return chain.filter(exchange);
        });
    }
}
