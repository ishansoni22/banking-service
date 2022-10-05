package com.ishan.bankingservice.accounts.domain;

import java.math.BigDecimal;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter(AccessLevel.PRIVATE)
public class MoneyDeposited extends AccountEvent {

  private BigDecimal amount;
  private String reference;
  private String eventType = "MoneyDeposited";

  private MoneyDeposited() {}

  MoneyDeposited(AccountId accountId, int revision, BigDecimal amount, String reference) {
    super(accountId, revision);
    this.amount = amount;
    this.reference = reference;
  }

  @Override
  public void applyOn(Account account) {
    account.apply(this);
  }

}
