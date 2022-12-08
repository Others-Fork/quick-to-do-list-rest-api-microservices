package com.ashrafplanet.quicktodolistservice.feign;

import java.util.Date;

public class TextDTO {

	private Long id;
	private String textBeforeCleanup;
	private String textAfterCleanup_result;
	private String environment;
	private Date createdAt;
	private Date updatedAt;

	public TextDTO() {
		super();
	}

	public TextDTO(Long id, String textBeforeCleanup, String textAfterCleanup_result, String environment,
			Date createdAt, Date updatedAt) {
		super();
		this.id = id;
		this.textBeforeCleanup = textBeforeCleanup;
		this.textAfterCleanup_result = textAfterCleanup_result;
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

	public String getTextBeforeCleanup() {
		return textBeforeCleanup;
	}

	public void setTextBeforeCleanup(String textBeforeCleanup) {
		this.textBeforeCleanup = textBeforeCleanup;
	}

	public String getTextAfterCleanup_result() {
		return textAfterCleanup_result;
	}

	public void setTextAfterCleanup_result(String textAfterCleanup_result) {
		this.textAfterCleanup_result = textAfterCleanup_result;
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
