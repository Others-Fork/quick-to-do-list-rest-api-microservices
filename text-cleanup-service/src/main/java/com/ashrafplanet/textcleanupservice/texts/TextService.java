package com.ashrafplanet.textcleanupservice.texts;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.sleuth.Tracer;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.ashrafplanet.textcleanupservice.TextCleanupServiceConfiguration;
import com.ashrafplanet.textcleanupservice.exception.TextNotFoundException;
import com.netflix.discovery.EurekaClient;

@Service
public class TextService {

	@Autowired
	private TextRepository textRepository;

	@Autowired
	private TextCleanupServiceConfiguration textCleanupServiceConfiguration;

	@Autowired
	private Environment environment;

	@Autowired
	private EurekaClient eurekaClient;

	@Autowired
	private Tracer tracer;

	public List<Text> getAllTexts() {
		return this.textRepository.findAll();
	}

	public Text createText(TextCreateDTO textCreateDTO) {
		String textAfterCleanup = textCreateDTO.getTextBeforeCleanup();
		if (textCleanupServiceConfiguration.isClean()) {
			if (textCleanupServiceConfiguration.isTrim()) {
				textAfterCleanup = textAfterCleanup.trim();
				textAfterCleanup = textAfterCleanup.replaceAll("\\s+", " ");
			}
		}
		Text text = new Text();
		text.setTextBeforeCleanup(textCreateDTO.getTextBeforeCleanup());
		text.setTextAfterCleanup(textAfterCleanup);
		text.setEnvironment(environment.getProperty("local.server.port") + " "
				+ eurekaClient.getApplicationInfoManager().getInfo().getInstanceId() + " "
				+ tracer.currentSpan().context().traceId());
		return this.textRepository.save(text);
	}

	public Text getTextById(Long id) {
		Text text = this.textRepository.findById(id).orElse(null);
		if (text == null) {
			throw new TextNotFoundException("id:" + id);
		}
		return text;
	}

	public void deleteTextById(Long id) {
		Text text = this.textRepository.findById(id).orElse(null);
		if (text == null) {
			throw new TextNotFoundException("id:" + id);
		}
		this.textRepository.deleteById(id);
	}
}
