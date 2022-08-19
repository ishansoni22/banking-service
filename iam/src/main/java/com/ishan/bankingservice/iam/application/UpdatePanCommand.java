package com.ishan.bankingservice.iam.application;

import java.util.Objects;
import lombok.Getter;

@Getter
public class UpdatePanCommand {

  private String userId;
  private String newPan;

  public UpdatePanCommand(String userId, String newPan) {
    this.userId = Objects.requireNonNull(userId);
    this.newPan = Objects.requireNonNull(newPan);
  }

}
