package com.ishan.bankingservice.common;

/*
Concept of an Event - Anything that happens in the domain
 */
public interface Event<T> {

  /*
  Event version - Not to be confused with revision. Used mainly for deserialization.
   */
  int getVersion();

  /*
  Which Aggregate this event happened to (users, orders, ...)
   */
  String getAggregateType();

  /*
  Id of the aggregate on which this event happened (order-12345, user-12345, ...)
   */
  AggregateId getAggregateId();

  /*
  Revision no of this aggregate. Mainly used for optimistic concurrency/locking.
   */
  int getRevision();

  /*
  Type of the event (OrderCreated, UserCreated, ...)
   */
  String getEventType();

  void applyOn(T aggregate);

}
