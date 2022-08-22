package com.ishan.bankingservice.iam.adapters.http;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
class CreateUserRequest {

  private String firstName;
  private String lastName;
  private String email;
  private String pan;

}
