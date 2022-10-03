package com.ishan.bankingservice.iam.adapters.http;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
class UpdateNameRequest {

  private String newFirstName;
  private String newLastName;

}
