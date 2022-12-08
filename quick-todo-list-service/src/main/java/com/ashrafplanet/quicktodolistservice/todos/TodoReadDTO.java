package com.ashrafplanet.quicktodolistservice.todos;

import java.util.Date;

public class TodoReadDTO {

	private Long id;
	private String todoItem;
	private Long userIdCreator;
	private byte checked = 0;
	private String color = "success";
	private String environment;
	private Date createdAt;
	private Date updatedAt;

	public TodoReadDTO() {
		super();
	}

	public TodoReadDTO(Long id, String todoItem, Long userIdCreator, byte checked, String color, String environment,
			Date createdAt, Date updatedAt) {
		super();
		this.id = id;
		this.todoItem = todoItem;
		this.userIdCreator = userIdCreator;
		this.checked = checked;
		this.color = color;
		this.environment = environment;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
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

	public String getEnvironment() {
		return environment;
	}

	public void setEnvironment(String environment) {
		this.environment = environment;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}
}
