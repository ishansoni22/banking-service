package com.ishan.bankingservice.iam.domain;

import com.ishan.bankingservice.common.AggregateId;
import java.util.Objects;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Setter;

@Setter(AccessLevel.PRIVATE)
public class UserId implements AggregateId {

  private String id;

  private UserId() {}

  public static UserId generate() { return new UserId(UUID.randomUUID().toString()); }

  public UserId(String id) {
    this.id = Objects.requireNonNull(id);
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
    UserId userId = (UserId) o;
    return id.equals(userId.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }

}
