package com.ishan.bankingservice.accounts.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter(AccessLevel.PRIVATE)
public class AccountCreated extends AccountEvent {

  private String ownerId;
  private String eventType = "AccountCreated";

  private AccountCreated() {}

  AccountCreated(AccountId accountId, int revision, String ownerId) {
    super(accountId, revision);
    this.ownerId = ownerId;
  }

  @Override
  public void applyOn(Account account) {
    account.apply(this);
  }

}
