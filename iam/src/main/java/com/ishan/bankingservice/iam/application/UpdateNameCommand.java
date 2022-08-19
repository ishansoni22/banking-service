package com.ishan.bankingservice.iam.application;

import java.util.Objects;
import lombok.Getter;

@Getter
public class UpdateNameCommand {

  private String userId;
  private String newName;

  public UpdateNameCommand(String userId, String newName) {
    this.userId = Objects.requireNonNull(userId);
    this.newName = Objects.requireNonNull(newName);
  }

}
