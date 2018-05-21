package org.twitterobserver.model.event;

import org.springframework.context.ApplicationEvent;

public class ConsumptionEndedEvent extends ApplicationEvent {

	public ConsumptionEndedEvent(Object source) {
		super(source);
	}
}
