package com.ashrafplanet.textcleanupservice.texts;

import java.text.SimpleDateFormat;
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

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "texts")
// @JsonIgnoreProperties({ "environment", "updatedAt" })
@JsonFilter("TextJsonFilter")
public class Text {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "text_before_cleanup", nullable = true)
	private String textBeforeCleanup;

	@Column(name = "text_after_cleanup", nullable = true)
	@JsonProperty(value = "textAfterCleanup_result")
	private String textAfterCleanup;

	@Column(name = "environment", nullable = true)
	private String environment;

	@Column(name = "created_at", nullable = true, updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	@CreationTimestamp
	private Date createdAt;

	@Column(name = "updated_at", nullable = true)
	@Temporal(TemporalType.TIMESTAMP)
	@UpdateTimestamp
	// @JsonIgnore
	private Date updatedAt;

	public Text() {
		super();
	}

	public Text(Long id, String textBeforeCleanup, String textAfterCleanup, String environment, Date createdAt,
			Date updatedAt) {
		super();
		this.id = id;
		this.textBeforeCleanup = textBeforeCleanup;
		this.textAfterCleanup = textAfterCleanup;
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

	public String getTextAfterCleanup() {
		return textAfterCleanup;
	}

	public void setTextAfterCleanup(String textAfterCleanup) {
		this.textAfterCleanup = textAfterCleanup;
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

	public String getCreatedAtFormated() {
		if (this.createdAt == null) {
			return null;
		}
		return new SimpleDateFormat("yyyy-MM-dd HH:mm").format(this.createdAt);
	}

	public String getUpdatedAtFormated() {
		if (this.updatedAt == null) {
			return null;
		}
		return new SimpleDateFormat("yyyy-MM-dd HH:mm").format(this.updatedAt);
	}
}
