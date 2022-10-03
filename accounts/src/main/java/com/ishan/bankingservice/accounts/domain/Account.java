package com.ishan.bankingservice.accounts.domain;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/*
The account aggregate
 */
public class Account {

  private AccountId accountId;
  /* For now, we'll just support a single account owner */
  private String ownerId;

  private BigDecimal balance;

  private int revision;
  private List<AccountEvent> changes = new ArrayList<>();

  public void create(String ownerId) {
    this.accountId = AccountId.generate();
    this.ownerId = ownerId;
    this.balance = BigDecimal.ZERO;
    this.revision = 1;

    this.changes.add(new AccountCreated(this.accountId, this.revision, this.ownerId));
  }

  public void deposit(BigDecimal amount) {
    this.balance = this.balance.add(amount);
    ++this.revision;
    this.changes.add(new MoneyDeposited(this.accountId, this.revision, amount));
  }

  void apply(AccountCreated accountCreated) {
    this.accountId = accountCreated.getAggregateId();
    this.ownerId = accountCreated.getOwnerId();
    this.balance = BigDecimal.ZERO;
    this.revision = accountCreated.getRevision();
  }

  void apply(MoneyDeposited moneyDeposited) {
    this.balance = this.balance.add(moneyDeposited.getAmount());
    this.revision = moneyDeposited.getRevision();
  }

  public AccountId getAccountId() {
    return this.accountId;
  }

  public List<AccountEvent> getChanges() {
    return this.changes;
  }

  public String getOwnerId() {
    return this.ownerId;
  }

  public BigDecimal getBalance() {
    return this.balance;
  }

}
