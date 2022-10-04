package com.ishan.bankingservice.accounts.domain;

import java.math.BigDecimal;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter(AccessLevel.PRIVATE)
public class MoneyWithdrawn extends AccountEvent {

  private BigDecimal amount;
  private String reference;
  private String eventType = "MoneyWithdrawn";

  private MoneyWithdrawn() {}

  public MoneyWithdrawn(AccountId accountId, int revision, BigDecimal amount, String reference) {
    super(accountId, revision);
    this.amount = amount;
    this.reference = reference;
  }

  @Override
  public void applyOn(Account account) {
    account.apply(this);
  }

}
