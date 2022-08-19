package com.ishan.bankingservice.common;

import java.util.List;

public interface EventStore<T extends Event> {

  List<T> getAllEvents(AggregateId aggregateId);

  void saveAllEvents(AggregateId aggregateId, List<T> changes);

}
