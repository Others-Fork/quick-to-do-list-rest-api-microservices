package com.ashrafplanet.microservicesapigateway;

// import org.springframework.cloud.gateway.route.RouteLocator;
// import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
// import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MicroservicesApiGatewayConfiguration {

	/*
	 * we can remove: spring.cloud.gateway.discovery.locator.enabled=true
	 * spring.cloud.gateway.discovery.locator.lowerCaseServiceId=true and use next
	 * bean to create custom routes for all microservices urls via gateway
	 */

	/*
	 * @Bean public RouteLocator gatewayRouter(RouteLocatorBuilder builder) { return
	 * builder.routes() .route(p -> p.path("/get") .filters(f ->
	 * f.addRequestHeader("MyHeader", "MyURI").addRequestParameter("Param",
	 * "MyValue")) .uri("http://httpbin.org:80")) .route(p ->
	 * p.path("/text-cleanup-service/**").uri("lb://text-cleanup-service")) .route(p ->
	 * p.path("/quick-todo-list-service/**").uri("lb://quick-todo-list-service")) .route(p
	 * -> p.path("/quick-todo-list-service-feign/**").uri("lb://quick-todo-list-service")).
	 * route( p -> p.path("/quick-todo-list-service-new/**") .filters(f ->
	 * f.rewritePath("/quick-todo-list-service-new/(?<segment>.*)",
	 * "/quick-todo-list-service-feign/${segment}")) .uri("lb://quick-todo-list-service"))
	 * .build(); }
	 */
}
