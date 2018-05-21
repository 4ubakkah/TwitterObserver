package org.twitterobserver.model.event;

import org.springframework.context.ApplicationEvent;

public class OutputProducedEvent extends ApplicationEvent {

	public OutputProducedEvent(Object source) {
		super(source);
	}
}
