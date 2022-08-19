package com.ishan.bankingservice.iam.adapters;

import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
class Fact {

  @Id
  private String id = UUID.randomUUID().toString();

  private String aggregate;

  private String aggregateId;

  private int revision;

  private String factType;

  private int version;

  @Column(columnDefinition = "text")
  private String fact;

  @Enumerated(EnumType.STRING)
  private Status status;

  public enum Status {
    PENDING, PROCESSED, FAILURE;
  }

}
