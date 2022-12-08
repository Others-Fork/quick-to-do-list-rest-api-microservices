package com.ashrafplanet.quicktodolistservice;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("quick-todo-list-service")
public class QuickTodoListServiceConfiguration {
	private boolean enabled;

	public QuickTodoListServiceConfiguration() {
		super();
	}

	public QuickTodoListServiceConfiguration(boolean enabled) {
		super();
		this.enabled = enabled;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
}
