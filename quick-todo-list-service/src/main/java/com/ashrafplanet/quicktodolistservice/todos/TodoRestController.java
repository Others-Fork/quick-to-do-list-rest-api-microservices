package com.ashrafplanet.quicktodolistservice.todos;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;

// import java.net.URI;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
// import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
// import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
// import org.springframework.web.client.RestTemplate;
// import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.ashrafplanet.quicktodolistservice.feign.TextCleanupServiceFeignClient;
import com.ashrafplanet.quicktodolistservice.feign.TextDTO;

@RestController
@RequestMapping(path = "/rest/api")
public class TodoRestController {

	@Autowired
	private TodoService todoService;

	@Autowired
	private Environment environment;

	@Autowired
	private TextCleanupServiceFeignClient textCleanupServiceFeignClient;

	@GetMapping("/v1/todos")
	public ResponseEntity<CollectionModel<TodoReadDTO>> index() {

		CollectionModel<TodoReadDTO> collectionModel = CollectionModel.of(this.todoService.getAllTodos());
		collectionModel.add(linkTo(methodOn(this.getClass()).index()).withSelfRel());

		return new ResponseEntity<CollectionModel<TodoReadDTO>>(collectionModel, HttpStatus.OK);
	}

	/*
	 * @GetMapping("/v1/users/{userId}/todos") public
	 * ResponseEntity<CollectionModel<TodoReadDTO>>
	 * indexByUserId(@PathVariable("userId") Long userId) {
	 * 
	 * CollectionModel<TodoReadDTO> collectionModel = CollectionModel
	 * .of(this.todoService.getTodosByUserIdCreator(userId)); WebMvcLinkBuilder
	 * webMvcLinkBuilder = linkTo(methodOn(this.getClass()).index());
	 * collectionModel.add(webMvcLinkBuilder.withRel("allTodos"));
	 * collectionModel.add(linkTo(methodOn(this.getClass()).indexByUserId(userId)).
	 * withSelfRel());
	 * 
	 * return new ResponseEntity<CollectionModel<TodoReadDTO>>(collectionModel,
	 * HttpStatus.OK); }
	 */

	@GetMapping("/v1/users/{userId}/todos")
	public ResponseEntity<List<TodoReadDTO>> indexByUserId(@PathVariable("userId") Long userId) {

		return new ResponseEntity<List<TodoReadDTO>>(this.todoService.getTodosByUserIdCreator(userId), HttpStatus.OK);
	}

	@GetMapping("/v2/users/{userId}/todos")
	public ResponseEntity<Page<TodoReadDTO>> indexByUserIdPaginated(@PathVariable("userId") Long userId,
			@RequestParam(name = "page", defaultValue = "0", required = false) int page,
			@RequestParam(name = "size", defaultValue = "5", required = false) int size) {

		return new ResponseEntity<Page<TodoReadDTO>>(
				this.todoService.getTodosByUserIdCreatorWithPagination(userId, page, size), HttpStatus.OK);
	}

	@PostMapping("/v1/todos")
	public ResponseEntity<EntityModel<TodoReadDTO>> store(
			@Valid @RequestBody(required = true) TodoCreateDTO todoCreateDTO) throws MethodArgumentNotValidException {

		HttpHeaders httpHeaders = new HttpHeaders();
		// String notEncoded = "user:password";
		// String encodedAuth =
		// Base64.getEncoder().encodeToString(notEncoded.getBytes());
		// httpHeaders.set("Authorization", "Basic ");
		// httpHeaders.setBasicAuth("Basic ");
		httpHeaders.setBasicAuth(environment.getProperty("textCleanupService.credentials.username"),
				environment.getProperty("textCleanupService.credentials.password"));
		httpHeaders.set("Content-Type", "application/json");
		// httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		httpHeaders.set("Accept", "application/json");
		// httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		httpHeaders.set("Accept-Language", "en");

		TextDTO textDTO = new TextDTO();
		textDTO.setTextBeforeCleanup(todoCreateDTO.getTodoItem());

		// use RestTemplate (deprecated):
		// String requestPath =
		// environment.getProperty("textCleanupService.rest.api.request.path");
		// HttpEntity<?> httpEntity = new HttpEntity<Object>(httpHeaders);
		// HttpEntity<TextDTO> httpEntity = new HttpEntity<TextDTO>(textDTO,
		// httpHeaders);
		// RestTemplate restTemplate = new RestTemplate();
		// restTemplate.postForEntity(requestPath, httpEntity, TextDTO.class);
		// ResponseEntity<TextDTO> responseEntity = restTemplate.exchange(requestPath,
		// HttpMethod.POST, httpEntity, TextDTO.class);

		// use WebClient:
		// ...

		// use Feign (Recommended for loadbalancing):
		ResponseEntity<TextDTO> responseEntity = textCleanupServiceFeignClient.store(httpHeaders.toSingleValueMap(),
				textDTO);

		if (responseEntity.getStatusCode() == HttpStatus.CREATED && responseEntity.hasBody()) {
			todoCreateDTO.setTodoItem(responseEntity.getBody().getTextAfterCleanup_result());
		}

		EntityModel<TodoReadDTO> entityModel = EntityModel.of(this.todoService.createTodo(todoCreateDTO));
		entityModel.add(linkTo(methodOn(this.getClass()).index()).withRel("allTodos"));
		entityModel.add(linkTo(methodOn(this.getClass()).indexByUserId(entityModel.getContent().getUserIdCreator()))
				.withRel("allTodosOfUser"));
		entityModel
				.add(linkTo(methodOn(this.getClass()).show(entityModel.getContent().getId())).withRel("createdTodo"));

		return new ResponseEntity<EntityModel<TodoReadDTO>>(entityModel, HttpStatus.CREATED);
	}

	/*
	 * public ResponseEntity<EntityModel<TodoReadDTO>> storeFallbackMethod(Exception
	 * exception) { EntityModel<TodoReadDTO> entityModel = EntityModel.of(new
	 * TodoReadDTO());
	 * entityModel.add(linkTo(methodOn(this.getClass()).index()).withRel("allTodos")
	 * ); return new ResponseEntity<EntityModel<TodoReadDTO>>(entityModel,
	 * HttpStatus.SERVICE_UNAVAILABLE); }
	 */

	@GetMapping("/v1/todos/{id}")
	public ResponseEntity<EntityModel<TodoReadDTO>> show(@PathVariable("id") Long id) {

		EntityModel<TodoReadDTO> entityModel = EntityModel.of(this.todoService.getTodoById(id));
		entityModel.add(linkTo(methodOn(this.getClass()).index()).withRel("allTodos"));
		entityModel.add(linkTo(methodOn(this.getClass()).indexByUserId(entityModel.getContent().getUserIdCreator()))
				.withRel("allTodosOfUser"));
		entityModel.add(linkTo(methodOn(this.getClass()).show(id)).withSelfRel());

		return new ResponseEntity<EntityModel<TodoReadDTO>>(entityModel, HttpStatus.OK);
	}

	@PutMapping("/v1/todos/{id}")
	public ResponseEntity<EntityModel<TodoReadDTO>> update(@PathVariable("id") Long id,
			@Valid @RequestBody(required = true) TodoUpdateDTO todoUpdateDTO) throws MethodArgumentNotValidException {

		EntityModel<TodoReadDTO> entityModel = EntityModel.of(this.todoService.updateTodo(todoUpdateDTO));
		entityModel.add(linkTo(methodOn(this.getClass()).index()).withRel("allTodos"));
		entityModel.add(linkTo(methodOn(this.getClass()).indexByUserId(entityModel.getContent().getUserIdCreator()))
				.withRel("allTodosOfUser"));
		entityModel
				.add(linkTo(methodOn(this.getClass()).show(entityModel.getContent().getId())).withRel("updatedTodo"));

		return new ResponseEntity<EntityModel<TodoReadDTO>>(entityModel, HttpStatus.OK);
	}

	@PatchMapping("/v1/todos/{id}")
	public ResponseEntity<EntityModel<TodoReadDTO>> update(@PathVariable("id") Long id,
			@Valid @RequestBody(required = true) TodoUpdateCheckedDTO todoUpdateCheckedDTO)
			throws MethodArgumentNotValidException {

		EntityModel<TodoReadDTO> entityModel = EntityModel.of(this.todoService.updateTodo(todoUpdateCheckedDTO));
		entityModel.add(linkTo(methodOn(this.getClass()).index()).withRel("allTodos"));
		entityModel.add(linkTo(methodOn(this.getClass()).indexByUserId(entityModel.getContent().getUserIdCreator()))
				.withRel("allTodosOfUser"));
		entityModel
				.add(linkTo(methodOn(this.getClass()).show(entityModel.getContent().getId())).withRel("updatedTodo"));

		return new ResponseEntity<EntityModel<TodoReadDTO>>(entityModel, HttpStatus.OK);
	}

	@DeleteMapping("/v1/todos/{id}")
	public ResponseEntity<?> destroy(@PathVariable("id") Long id) {

		this.todoService.deleteTodoById(id);

		return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);

		// URI location =
		// ServletUriComponentsBuilder.fromCurrentRequest().path("/v1/todos/{id}").buildAndExpand("6").toUri();
		// URI location =
		// ServletUriComponentsBuilder.fromCurrentRequest().path("/v1/todos").build().toUri();
		// return ResponseEntity.noContent().location(location).build();
	}
}
