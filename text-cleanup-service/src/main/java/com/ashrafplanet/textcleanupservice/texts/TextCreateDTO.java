package com.ashrafplanet.textcleanupservice.texts;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class TextCreateDTO {

	@NotBlank
	@Size(min = 1, max = 2000)
	private String textBeforeCleanup;

	public TextCreateDTO() {
		super();
	}

	public TextCreateDTO(@NotBlank @Size(min = 1, max = 2000) String textBeforeCleanup) {
		super();
		this.textBeforeCleanup = textBeforeCleanup;
	}

	public String getTextBeforeCleanup() {
		return textBeforeCleanup;
	}

	public void setTextBeforeCleanup(String textBeforeCleanup) {
		this.textBeforeCleanup = textBeforeCleanup;
	}
}
