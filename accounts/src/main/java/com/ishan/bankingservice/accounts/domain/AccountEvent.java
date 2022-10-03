package com.ishan.bankingservice.accounts.domain;

import com.ishan.bankingservice.common.Event;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter(AccessLevel.PRIVATE)
public abstract class AccountEvent implements Event<Account> {

  public static final String TYPE = "accounts";
  private AccountId aggregateId;
  private int revision;
  private int version = 1;
  private String eventType = AccountEvent.TYPE;

  protected AccountEvent() {}

  AccountEvent(AccountId accountId, int revision) {
    this.aggregateId = accountId;
    this.revision = revision;
  }

  @Override
  public String getAggregateType() {
    return AccountEvent.TYPE;
  }

}
