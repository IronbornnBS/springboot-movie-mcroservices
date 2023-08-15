package io.alchemia.moviegatewayservice.util;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Predicate;

@Component
public class RouteValidatorUtility {

    public static final List<String> endPoints = List.of(
            "/authentication/**"
    );

    public Predicate<ServerHttpRequest> isSecured =
            request -> endPoints
                    .stream()
                    .noneMatch(uri -> request.getURI().getPath().contains(uri));
}
