package com.ishan.bankingservice.iam.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter(AccessLevel.PRIVATE)
public class UserEmailUpdated extends UserEvent {

  private String newEmail;
  private String eventType = "UserEmailUpdated";

  private UserEmailUpdated() {}

  public UserEmailUpdated(UserId userId, int revision, String newEmail) {
    super(userId, revision);
    this.newEmail = newEmail;
  }

  @Override
  public void applyOn(User user) {
    user.apply(this);
  }

}
