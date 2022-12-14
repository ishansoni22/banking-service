package com.ishan.bankingservice.iam.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter(AccessLevel.PRIVATE)
public class UserCreated extends UserEvent {

  private String firstName;
  private String lastName;
  private String email;
  private String pan;
  private String eventType = "UserCreated";

  private UserCreated() {}

  public UserCreated(UserId aggregateId, int revision,
      String firstName, String lastName, String email, String pan) {
    super(aggregateId, revision);
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
    this.pan = pan;
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
