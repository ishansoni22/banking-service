package com.ishan.bankingservice.accounts.application;

import java.math.BigDecimal;
import java.util.Objects;
import lombok.Getter;

@Getter
public class MoneyDepositCommand {

  private String accountId;
  private BigDecimal amount;

  public MoneyDepositCommand(String accountId, BigDecimal amount) {
    this.accountId = Objects.requireNonNull(accountId);
    this.amount = Objects.requireNonNull(amount);
  }

}
