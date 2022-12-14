package com.ishan.bankingservice.iam.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter(AccessLevel.PRIVATE)
public class UserNameUpdated extends UserEvent {

  private String newFirstName;
  private String newLastName;
  private String eventType = "UserNameUpdated";

  private UserNameUpdated() {}

  public UserNameUpdated(UserId userId, int revision, String newFirstName, String newLastName) {
    super(userId, revision);
    this.newFirstName = newFirstName;
    this.newLastName = newLastName;
  }

  @Override
  public void applyOn(User user) {
    user.apply(this);
  }

  @Override
  public int getVersion() {
    return 2;
  }

}
