package com.ishan.bankingservice.iam.application;

import java.util.Objects;
import lombok.Getter;

@Getter
public class UpdateNameCommand {

  private String userId;
  private String newFirstName;
  private String newLastName;

  public UpdateNameCommand(String userId, String newFirstName, String newLastName) {
    this.userId = Objects.requireNonNull(userId);
    this.newFirstName = Objects.requireNonNull(newFirstName);
    this.newLastName = Objects.requireNonNull(newLastName);
  }

}
