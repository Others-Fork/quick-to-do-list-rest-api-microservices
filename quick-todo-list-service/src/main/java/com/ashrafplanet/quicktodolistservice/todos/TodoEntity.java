package com.ashrafplanet.quicktodolistservice.todos;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "todos")
public class TodoEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "todo_item", nullable = false)
	private String todoItem;

	@Column(name = "user_id_creator", nullable = false)
	private Long userIdCreator;

	@Column(name = "checked", nullable = false)
	private byte checked = 0;

	@Column(name = "color", nullable = true)
	private String color = "success";

	@Column(name = "environment", nullable = true)
	private String environment;

	@Column(name = "created_at", nullable = true, updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	@CreationTimestamp
	private Date createdAt;

	@Column(name = "updated_at", nullable = true)
	@Temporal(TemporalType.TIMESTAMP)
	@UpdateTimestamp
	private Date updatedAt;

	public TodoEntity() {
		super();
	}

	public TodoEntity(Long id, String todoItem, Long userIdCreator, byte checked, String color, String environment,
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
