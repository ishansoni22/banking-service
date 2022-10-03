package com.ishan.bankingservice.accounts.domain;

import java.math.BigDecimal;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter(AccessLevel.PRIVATE)
public class MoneyDeposited extends AccountEvent {

  private BigDecimal amount;
  private String eventType = "MoneyDeposited";

  private MoneyDeposited() {}

  MoneyDeposited(AccountId accountId, int revision, BigDecimal amount) {
    super(accountId, revision);
    this.amount = amount;
  }

  @Override
  public void applyOn(Account account) {
    account.apply(this);
  }

}
