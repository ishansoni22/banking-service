package com.ishan.bankingservice.iam.application;

import java.util.Objects;
import lombok.Getter;

@Getter
public class CreateUserCommand {

  private String fullName;
  private String email;
  private String pan;

  public CreateUserCommand(String fullName, String email, String pan) {
    this.fullName = Objects.requireNonNull(fullName);
    this.email = Objects.requireNonNull(email);
    this.pan = Objects.requireNonNull(pan);
  }

}
