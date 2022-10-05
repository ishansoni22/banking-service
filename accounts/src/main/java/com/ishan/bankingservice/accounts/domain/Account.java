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

  public void deposit(BigDecimal amount, String reference) {
    this.balance = this.balance.add(amount);
    ++this.revision;
    this.changes.add(new MoneyDeposited(this.accountId, this.revision, amount, reference));
  }

  public void withdraw(BigDecimal amount, String reference) {
    if (this.balance.compareTo(amount) < 0) {
      throw new IllegalStateException("Cannot withdraw more than the current balance");
    }
    this.balance = this.balance.subtract(amount);
    ++this.revision;
    this.changes.add(new MoneyWithdrawn(this.accountId, this.revision, amount, reference));
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

  public void apply(MoneyWithdrawn moneyWithdrawn) {
    this.balance = this.balance.subtract(moneyWithdrawn.getAmount());
    this.revision = moneyWithdrawn.getRevision();
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
