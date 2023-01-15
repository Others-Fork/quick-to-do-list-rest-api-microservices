# quick-to-do-list-rest-api-microservices

REST API Microservices

Has 2 REST API Service Applications
• text-cleanup-service
• quick-todo-list-service

pattern: Microservice Architecture

• Developed in Java and Spring Boot

Servers: (used for local deployment)
---------
• Spring Cloud Config server as Centralized Configuration
• Spring Cloud Discovery server (Eureka) as Naming Server or Service Registry
• RabbitMQ server as Messaging Middleware
• Zipkin & Sleuth servers as Distributed Tracing
• Spring Cloud Routing server as API Gateway

Some tech used:
---------------
• Java • Spring Boot • Maven • Tomcat • Spring Web • Spring DevTools

• MySQL • SQL • Spring Data JPA • JPA • Hibernate • Entity • DTO • Validation • ModelMapper • Pagination

• REST API • RestController • Feign • Resilience4j • Circuit Breaker
• Documentation • Open API • Swagger
• Content Negotiation • Response Format • jackson • i18n
• Versioning
• Filtering
• HATEOAS • HAL Explorer • Actuator

• Spring Security • Basic Authentication

• Docker • docker-compose.yaml + .env file • Docker Registry: https://hub.docker.com/u/ashrafplanet

• Kubernetes • yaml config file (secret, configMap, pv, pvc, mysql, deployment, service, ...)
• Multiple Instances and Load Balancer (by using ReplicaSet, LoadBalancer and strategies)
• Auto Scaling (by using >> kubectl autoscale deployment << with some conditions to create horizontalpodautoscaler hpa)
• Health Check • Self-Healing • Zero Downtime (by using readiness and liveness probes with actuator)




