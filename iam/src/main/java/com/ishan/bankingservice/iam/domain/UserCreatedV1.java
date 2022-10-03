package com.ishan.bankingservice.iam.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter(AccessLevel.PRIVATE)
public class UserCreatedV1 extends UserEvent {

  private String fullName;
  private String email;
  private String pan;
  private String eventType = "UserCreated";

  private UserCreatedV1() {}

  public UserCreatedV1(UserId aggregateId, int revision, String fullName, String email, String pan) {
    super(aggregateId, revision);
    this.fullName = fullName;
    this.email = email;
    this.pan = pan;
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
