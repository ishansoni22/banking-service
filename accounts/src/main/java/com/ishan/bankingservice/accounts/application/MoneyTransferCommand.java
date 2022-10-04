package com.ishan.bankingservice.accounts.application;

import java.math.BigDecimal;
import java.util.Objects;
import lombok.Getter;

@Getter
public class MoneyTransferCommand {

  private String fromAccountId;
  private String toAccountId;
  private boolean withinSameBank;
  private BigDecimal amount;
  private String reference;

  public MoneyTransferCommand(String fromAccountId, String toAccountId, boolean withinSameBank,
      BigDecimal amount, String reference) {
    this.fromAccountId = Objects.requireNonNull(fromAccountId);
    this.toAccountId = Objects.requireNonNull(toAccountId);
    this.withinSameBank = withinSameBank;
    this.amount = Objects.requireNonNull(amount);
    this.reference = reference;
  }

}
