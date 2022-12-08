package com.ashrafplanet.quicktodolistservice.todos;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class TodoCreateDTO {

	@NotBlank
	@Size(min = 1, max = 2000)
	private String todoItem;

	@NotNull
	@Min(value = 1)
	private Long userIdCreator;

	@Min(value = 0)
	@Max(value = 1)
	private byte checked = 0;

	@Size(min = 0, max = 50)
	private String color = "success";

	public TodoCreateDTO() {
		super();
	}

	public TodoCreateDTO(@NotBlank @Size(min = 1, max = 2000) String todoItem, @NotNull @Min(1) Long userIdCreator,
			@Min(0) @Max(1) byte checked, @Size(min = 0, max = 50) String color) {
		super();
		this.todoItem = todoItem;
		this.userIdCreator = userIdCreator;
		this.checked = checked;
		this.color = color;
	}

	public String getTodoItem() {
		return todoItem;
	}

	public void setTodoItem(String todoItem) {
		this.todoItem = todoItem;
	}

	public Long getUserIdCreator() {
		return userIdCreator;
	}

	public void setUserIdCreator(Long userIdCreator) {
		this.userIdCreator = userIdCreator;
	}

	public byte getChecked() {
		return checked;
	}

	public void setChecked(byte checked) {
		this.checked = checked;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}
}
