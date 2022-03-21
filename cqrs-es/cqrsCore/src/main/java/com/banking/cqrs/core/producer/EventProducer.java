package com.banking.cqrs.core.producer;

import com.banking.cqrs.core.events.BaseEvent;

public interface EventProducer {

    void produce(String topic, BaseEvent event);

}
