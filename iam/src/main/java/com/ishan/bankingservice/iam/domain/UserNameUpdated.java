package com.ishan.bankingservice.iam.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter(AccessLevel.PRIVATE)
public class UserNameUpdated extends UserEvent {

  private String newFullName;
  private String eventType = "UserNameUpdated";

  private UserNameUpdated() {}

  public UserNameUpdated(UserId userId, int revision, String newFullName) {
    super(userId, revision);
    this.newFullName = newFullName;
  }

  @Override
  public void applyOn(User user) {
    user.apply(this);
  }

}
