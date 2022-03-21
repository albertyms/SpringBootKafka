package com.banking.cqrs.core.handler;

import com.banking.cqrs.core.domain.AggregateRoot;

public interface EventSourcingHandler<T> {

    void save(AggregateRoot aggregate);

    T getById(String id);

}
