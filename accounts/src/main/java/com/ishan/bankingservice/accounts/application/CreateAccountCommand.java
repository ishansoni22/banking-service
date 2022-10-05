package com.ishan.bankingservice.accounts.application;

import java.util.Objects;
import lombok.Getter;

@Getter
public class CreateAccountCommand {

  private String ownerId;

  public CreateAccountCommand(String ownerId) {
    this.ownerId = Objects.requireNonNull(ownerId);
  }

}
