package org.twitterobserver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.event.EventListener;
import org.twitterobserver.model.event.OutputProducedEvent;
import org.twitterobserver.service.StreamConsumerService;

@SpringBootApplication
public class SpringReactiveApp {

	private static ApplicationContext applicationContext;

	@Autowired
	private StreamConsumerService service;

	public static void main(String[] args) {
		applicationContext = SpringApplication.run(SpringReactiveApp.class, args);
	}

	@EventListener
	public void onApplicationEvent(OutputProducedEvent event) {
		SpringApplication.exit(applicationContext, () -> 0);
	}

	@EventListener
	public void onApplicationEvent(ApplicationStartedEvent event) {
		service.startConsumption();
	}
}