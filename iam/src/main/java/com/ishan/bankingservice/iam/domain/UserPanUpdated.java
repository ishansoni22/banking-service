package com.ishan.bankingservice.iam.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter(AccessLevel.PRIVATE)
public class UserPanUpdated extends UserEvent {

  private String newPan;
  private String eventType = "UserPanUpdated";

  private UserPanUpdated() { }

  public UserPanUpdated(UserId userId, int revision, String newPan) {
    super(userId, revision);
    this.newPan = newPan;
  }

  @Override
  public void applyOn(User user) {
    user.apply(this);
  }

}