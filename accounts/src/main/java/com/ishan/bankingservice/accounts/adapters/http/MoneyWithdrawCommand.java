package com.ishan.bankingservice.accounts.adapters.http;

import java.math.BigDecimal;
import java.util.Objects;
import lombok.Getter;

@Getter
public class MoneyWithdrawCommand {

  private String accountId;
  private BigDecimal amount;

  public MoneyWithdrawCommand(String accountId, BigDecimal amount) {
    this.accountId = Objects.requireNonNull(accountId);
    this.amount = Objects.requireNonNull(amount);
  }

}
