package com.ishan.bankingservice.accounts.application;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountResource {

  private String accountId;

  private String ownerId;

  private BigDecimal balance;

}
