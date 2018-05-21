package org.twitterobserver.model.event;

import org.springframework.context.ApplicationEvent;

public class ConsumptionFinishedPersistedEvent extends ApplicationEvent {

	public ConsumptionFinishedPersistedEvent(Object source) {
		super(source);
	}
}
