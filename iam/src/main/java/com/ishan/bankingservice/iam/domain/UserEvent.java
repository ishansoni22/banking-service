package com.ishan.bankingservice.iam.domain;

import com.ishan.bankingservice.common.Event;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter(AccessLevel.PRIVATE)
public abstract class UserEvent implements Event<User> {

  public static final String TYPE = "users";

  private UserId aggregateId;
  private int revision;
  private int version = 1;
  private String eventType = UserEvent.TYPE;

  protected UserEvent() {}

  UserEvent(UserId aggregateId, int revision) {
    this.aggregateId = aggregateId;
    this.revision = revision;
  }

  @Override
  public String getAggregateType() {
    return UserEvent.TYPE;
  }

}
