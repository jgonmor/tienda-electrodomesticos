//package com.jgonmor.api_gateway.config;
//
//import org.springdoc.core.models.GroupedOpenApi;
//import org.springdoc.core.properties.SwaggerUiConfigParameters;
//import org.springframework.cloud.gateway.route.RouteDefinition;
//import org.springframework.cloud.gateway.route.RouteDefinitionLocator;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Lazy;
//
//import java.util.ArrayList;
//import java.util.List;
//
//
//@Configuration
//public class OpenApiConfig {
//
//    @Bean
//    @Lazy(false)
//    public List<GroupedOpenApi> apis(SwaggerUiConfigParameters swaggerUiConfigParameters,
//                                     RouteDefinitionLocator locator) {
//        List<GroupedOpenApi> apis = new ArrayList<>();
//
//        List<RouteDefinition> definitions = locator.getRouteDefinitions().collectList().block();
//
//        definitions.stream()
//                   .filter(routeDefinition -> routeDefinition.getId().matches("-service"))
//                   .forEach(routeDefinition -> {
//                       apis.add(GroupedOpenApi.builder()
//                                              .group(routeDefinition.getId())
//                                              .pathsToMatch("/"+routeDefinition.getId()+"/**")
//                                              .build());
//                   });
//
//        return apis;
//    }
//
//}
