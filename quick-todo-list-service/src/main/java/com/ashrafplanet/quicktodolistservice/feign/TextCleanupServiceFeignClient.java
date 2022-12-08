package com.ashrafplanet.quicktodolistservice.feign;

import java.util.Map;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import feign.HeaderMap;
import feign.Headers;
import feign.Param;
import feign.RequestLine;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

@FeignClient(name = "text-cleanup-service")
public interface TextCleanupServiceFeignClient {

	// original prototype:
	// @PostMapping("/rest/api/v1/texts")
	// public ResponseEntity<MappingJacksonValue> store(@Valid @RequestBody(required
	// = true) TextCreateDTO textCreateDTO);

	// feign + springframework:
	/*
	 * @PostMapping("/rest/api/v1/texts") public ResponseEntity<TextDTO> store(
	 * 
	 * @RequestHeader(value = "Authorization", required = true) String
	 * authorizationHeader,
	 * 
	 * @RequestBody(required = true) TextDTO textDTO);
	 * 
	 * @PostMapping("/rest/api/v1/texts") public ResponseEntity<TextDTO>
	 * store(@RequestHeader Map<String, String> mapHeaders,
	 * 
	 * @RequestBody(required = true) TextDTO textDTO);
	 * 
	 * // best option for me!
	 * 
	 * @PostMapping("/rest/api/v1/texts") public ResponseEntity<TextDTO>
	 * store(@RequestHeader HttpHeaders httpHeaders,
	 * 
	 * @RequestBody(required = true) TextDTO textDTO);
	 */

	// feign only + resilience4j:
	@RequestLine("POST /rest/api/v1/texts")
	@CircuitBreaker(name = "default", fallbackMethod = "storeFallbackMethod")
	public ResponseEntity<TextDTO> store(@HeaderMap Map<String, String> headerMap,
			@RequestBody(required = true) TextDTO textDTO);

	@Headers({ "Authorization: {headerAuthorization}", "Content-Type: application/json", "Accept: application/json",
			"Accept-Language: en" })
	// "X-Auth-Token: {access_token}"
	@RequestLine("POST /rest/api/v1/texts")
	@CircuitBreaker(name = "default", fallbackMethod = "storeFallbackMethod")
	public ResponseEntity<TextDTO> store(@Param("headerAuthorization") String headerAuthorization,
			@RequestBody(required = true) TextDTO textDTO);

	default public ResponseEntity<TextDTO> storeFallbackMethod(Exception exception) {
		return new ResponseEntity<TextDTO>(new TextDTO(), HttpStatus.SERVICE_UNAVAILABLE);
	}
}
