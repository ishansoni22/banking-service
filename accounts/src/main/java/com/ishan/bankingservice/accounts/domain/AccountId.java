package com.ishan.bankingservice.accounts.domain;

import com.ishan.bankingservice.common.AggregateId;
import java.util.Objects;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Setter;

@Setter(AccessLevel.PRIVATE)
public class AccountId implements AggregateId {

  private String id;

  private AccountId() {}

  public AccountId(String id) {
   this.id = Objects.requireNonNull(id);
  }

  public static AccountId generate() {
    return new AccountId(UUID.randomUUID().toString());
  }

  @Override
  public String getId() {
    return this.id;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AccountId accountId = (AccountId) o;
    return id.equals(accountId.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }

}
