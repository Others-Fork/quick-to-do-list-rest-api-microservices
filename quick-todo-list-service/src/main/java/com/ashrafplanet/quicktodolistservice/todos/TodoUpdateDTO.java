package com.ashrafplanet.quicktodolistservice.todos;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class TodoUpdateDTO {

	private Long id;

	@NotBlank
	@Size(min = 1, max = 2000)
	private String todoItem;

	@Min(value = 0)
	@Max(value = 1)
	private byte checked = 0;

	public TodoUpdateDTO() {
		super();
	}

	public TodoUpdateDTO(Long id, @NotBlank @Size(min = 1, max = 2000) String todoItem, @Min(0) @Max(1) byte checked) {
		super();
		this.id = id;
		this.todoItem = todoItem;
		this.checked = checked;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTodoItem() {
		return todoItem;
	}

	public void setTodoItem(String todoItem) {
		this.todoItem = todoItem;
	}

	public byte getChecked() {
		return checked;
	}

	public void setChecked(byte checked) {
		this.checked = checked;
	}
}
