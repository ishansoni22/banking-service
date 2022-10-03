package com.ishan.bankingservice.iam.application;

import java.util.Objects;
import lombok.Getter;

@Getter
public class CreateUserCommand {

  private String firstName;
  private String lastName;
  private String email;
  private String pan;

  public CreateUserCommand(String firstName, String lastName, String email, String pan) {
    this.firstName = Objects.requireNonNull(firstName);
    this.lastName = Objects.requireNonNull(lastName);
    this.email = Objects.requireNonNull(email);
    this.pan = Objects.requireNonNull(pan);
  }

}
