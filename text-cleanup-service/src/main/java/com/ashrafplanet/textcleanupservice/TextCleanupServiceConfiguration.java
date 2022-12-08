package com.ashrafplanet.textcleanupservice;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("text-cleanup-service")
public class TextCleanupServiceConfiguration {
	private boolean clean;
	private boolean trim;

	public TextCleanupServiceConfiguration() {
		super();
	}

	public TextCleanupServiceConfiguration(boolean clean, boolean trim) {
		super();
		this.clean = clean;
		this.trim = trim;
	}

	public boolean isClean() {
		return clean;
	}

	public void setClean(boolean clean) {
		this.clean = clean;
	}

	public boolean isTrim() {
		return trim;
	}

	public void setTrim(boolean trim) {
		this.trim = trim;
	}
}
