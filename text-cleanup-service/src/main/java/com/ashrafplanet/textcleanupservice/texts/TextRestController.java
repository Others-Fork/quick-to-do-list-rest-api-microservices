package com.ashrafplanet.textcleanupservice.texts;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

@RestController
@RequestMapping(path = "/rest/api")
public class TextRestController {

	@Autowired
	private TextService textService;

	Logger logger = LoggerFactory.getLogger(TextRestController.class);

	@GetMapping("/v1/texts")
	public ResponseEntity<MappingJacksonValue> index() {

		CollectionModel<Text> collectionModel = CollectionModel.of(this.textService.getAllTexts());
		collectionModel.add(linkTo(methodOn(this.getClass()).index()).withSelfRel());

		MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(collectionModel);
		mappingJacksonValue.setFilters(
				new SimpleFilterProvider().addFilter("TextJsonFilter", SimpleBeanPropertyFilter.filterOutAllExcept("id",
						"textBeforeCleanup", "textAfterCleanup_result", "environment", "createdAt", "updatedAt")));

		return new ResponseEntity<MappingJacksonValue>(mappingJacksonValue, HttpStatus.OK);
	}

	@PostMapping("/v1/texts")
	public ResponseEntity<MappingJacksonValue> store(@Valid @RequestBody(required = true) TextCreateDTO textCreateDTO) {

		logger.info("store called");

		EntityModel<Text> entityModel = EntityModel.of(this.textService.createText(textCreateDTO));
		entityModel.add(linkTo(methodOn(this.getClass()).index()).withRel("allTexts"));
		entityModel
				.add(linkTo(methodOn(this.getClass()).show(entityModel.getContent().getId())).withRel("createdText"));

		MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(entityModel);
		mappingJacksonValue.setFilters(new SimpleFilterProvider().addFilter("TextJsonFilter", SimpleBeanPropertyFilter
				.filterOutAllExcept("id", "textBeforeCleanup", "textAfterCleanup_result", "environment", "createdAt")));

		return new ResponseEntity<MappingJacksonValue>(mappingJacksonValue, HttpStatus.CREATED);
	}

	@GetMapping("/v1/texts/{id}")
	public ResponseEntity<MappingJacksonValue> show(@PathVariable("id") Long id) {

		EntityModel<Text> entityModel = EntityModel.of(this.textService.getTextById(id));
		entityModel.add(linkTo(methodOn(this.getClass()).index()).withRel("allTexts"));
		entityModel.add(linkTo(methodOn(this.getClass()).show(id)).withSelfRel());

		MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(entityModel);
		mappingJacksonValue.setFilters(
				new SimpleFilterProvider().addFilter("TextJsonFilter", SimpleBeanPropertyFilter.filterOutAllExcept("id",
						"textBeforeCleanup", "textAfterCleanup_result", "environment", "createdAt", "updatedAt")));

		return new ResponseEntity<MappingJacksonValue>(mappingJacksonValue, HttpStatus.OK);
	}

	@DeleteMapping("/v1/texts/{id}")
	public ResponseEntity<?> destroy(@PathVariable("id") Long id) {

		this.textService.deleteTextById(id);

		return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
	}
}
