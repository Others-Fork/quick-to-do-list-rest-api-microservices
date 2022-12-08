package com.ashrafplanet.quicktodolistservice.todos;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

public class TodoUpdateCheckedDTO {

	private Long id;

	@Min(value = 0)
	@Max(value = 1)
	private byte checked = 0;

	public TodoUpdateCheckedDTO() {
		super();
	}

	public TodoUpdateCheckedDTO(Long id, @Min(0) @Max(1) byte checked) {
		super();
		this.id = id;
		this.checked = checked;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public byte getChecked() {
		return checked;
	}

	public void setChecked(byte checked) {
		this.checked = checked;
	}
}
