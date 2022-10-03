package com.ishan.bankingservice.iam.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter(AccessLevel.PRIVATE)
public class UserNameUpdatedV1 extends UserEvent {

  private String newFullName;
  private String eventType = "UserNameUpdated";

  private UserNameUpdatedV1() {}

  public UserNameUpdatedV1(UserId userId, int revision, String newFullName) {
    super(userId, revision);
    this.newFullName = newFullName;
  }

  @Override
  public void applyOn(User user) {
    user.apply(this);
  }

  @Override
  public int getVersion() {
    return 1;
  }

}
