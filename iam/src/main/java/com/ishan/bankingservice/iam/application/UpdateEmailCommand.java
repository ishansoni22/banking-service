package com.ishan.bankingservice.iam.application;

import java.util.Objects;
import lombok.Getter;

@Getter
public class UpdateEmailCommand {

  private String userId;
  private String newEmail;

  public UpdateEmailCommand(String userId, String newEmail) {
    this.userId = Objects.requireNonNull(userId);
    this.newEmail = Objects.requireNonNull(newEmail);
  }

}
