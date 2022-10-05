package com.ishan.bankingservice.accounts.adapters.http;

import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MoneyTransferRequest {

  private String toAccountId;
  private BigDecimal amount;
  private String reference;

}
