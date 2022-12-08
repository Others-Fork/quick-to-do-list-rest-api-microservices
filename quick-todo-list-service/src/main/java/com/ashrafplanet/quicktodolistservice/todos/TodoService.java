package com.ashrafplanet.quicktodolistservice.todos;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.sleuth.Tracer;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.ashrafplanet.quicktodolistservice.QuickTodoListServiceConfiguration;
import com.ashrafplanet.quicktodolistservice.exception.TodoNotFoundException;
import com.netflix.discovery.EurekaClient;

@Service
public class TodoService {

	@Autowired
	private TodoRepository todoRepository;

	@Autowired
	private QuickTodoListServiceConfiguration quickTodoListServiceConfiguration;

	@Autowired
	private Environment environment;

	@Autowired
	private EurekaClient eurekaClient;

	@Autowired
	private Tracer tracer;

	@Autowired
	private ModelMapper modelMapper;

	public List<TodoReadDTO> getAllTodos() {
		if (!quickTodoListServiceConfiguration.isEnabled()) {
			return null;
		}
		return this.todoRepository.findAll().stream().map(todoEntity -> modelMapper.map(todoEntity, TodoReadDTO.class))
				.collect(Collectors.toList());
	}

	public List<TodoReadDTO> getTodosByUserIdCreator(Long userIdCreator) {
		if (!quickTodoListServiceConfiguration.isEnabled()) {
			return null;
		}
		return this.todoRepository.findByUserIdCreatorOrderById(userIdCreator).stream()
				.map(todoEntity -> modelMapper.map(todoEntity, TodoReadDTO.class)).collect(Collectors.toList());
	}

	public Page<TodoReadDTO> getTodosByUserIdCreatorWithPagination(Long userIdCreator, int page, int size) {
		if (!quickTodoListServiceConfiguration.isEnabled()) {
			return null;
		}
		page = (page < 0) ? 0 : page;
		size = (size < 1) ? 5 : 5;

		Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
		Page<TodoEntity> pageTodoEntity = this.todoRepository.findAll(pageable);
		Page<TodoReadDTO> pageTodoReadDTO = pageTodoEntity
				.map(todoEntity -> modelMapper.map(todoEntity, TodoReadDTO.class));
		return pageTodoReadDTO;
	}

	public TodoReadDTO createTodo(TodoCreateDTO todoCreateDTO) {
		if (!quickTodoListServiceConfiguration.isEnabled()) {
			return null;
		}
		TodoEntity todoEntity = new TodoEntity();
		todoEntity.setTodoItem(todoCreateDTO.getTodoItem());
		todoEntity.setUserIdCreator(todoCreateDTO.getUserIdCreator());
		todoEntity.setChecked(todoCreateDTO.getChecked());
		todoEntity.setColor(todoCreateDTO.getColor());
		todoEntity.setEnvironment(environment.getProperty("local.server.port") + " "
				+ eurekaClient.getApplicationInfoManager().getInfo().getInstanceId() + " "
				+ tracer.currentSpan().context().traceId());
		this.todoRepository.save(todoEntity);
		return this.modelMapper.map(todoEntity, TodoReadDTO.class);
	}

	public TodoReadDTO getTodoById(Long id) {
		if (!quickTodoListServiceConfiguration.isEnabled()) {
			return null;
		}
		TodoEntity todoEntity = this.todoRepository.findById(id).orElse(null);
		if (todoEntity == null) {
			throw new TodoNotFoundException("id:" + id);
		}
		return this.modelMapper.map(todoEntity, TodoReadDTO.class);
	}

	public TodoReadDTO updateTodo(TodoUpdateDTO todoUpdateDTO) {
		if (!quickTodoListServiceConfiguration.isEnabled()) {
			return null;
		}
		TodoEntity todoEntity = this.todoRepository.findById(todoUpdateDTO.getId()).orElse(null);
		if (todoEntity == null) {
			throw new TodoNotFoundException("id:" + todoUpdateDTO.getId());
		}
		todoEntity.setTodoItem(todoUpdateDTO.getTodoItem());
		todoEntity.setChecked(todoUpdateDTO.getChecked());
		this.todoRepository.save(todoEntity);
		return this.modelMapper.map(todoEntity, TodoReadDTO.class);
	}

	public TodoReadDTO updateTodo(TodoUpdateCheckedDTO todoUpdateCheckedDTO) {
		if (!quickTodoListServiceConfiguration.isEnabled()) {
			return null;
		}
		TodoEntity todoEntity = this.todoRepository.findById(todoUpdateCheckedDTO.getId()).orElse(null);
		if (todoEntity == null) {
			throw new TodoNotFoundException("id:" + todoUpdateCheckedDTO.getId());
		}
		todoEntity.setChecked(todoUpdateCheckedDTO.getChecked());
		this.todoRepository.save(todoEntity);
		return this.modelMapper.map(todoEntity, TodoReadDTO.class);
	}

	public void deleteTodoById(Long id) {
		if (quickTodoListServiceConfiguration.isEnabled()) {
			TodoEntity todoEntity = this.todoRepository.findById(id).orElse(null);
			if (todoEntity == null) {
				throw new TodoNotFoundException("id:" + id);
			}
			this.todoRepository.deleteById(id);
		}
	}
}
