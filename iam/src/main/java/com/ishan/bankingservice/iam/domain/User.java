package com.ishan.bankingservice.iam.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/*
The User Aggregate
 */
public class User {

  private UserId userId;
  private String fullName;
  private String email;
  private String pan;
  private int revision;
  private List<UserEvent> changes = new ArrayList<>();

  public UserId getUserId() { return this.userId; }

  public String getFullName() { return this.fullName; }

  public String getEmail() {
    return this.email;
  }

  public String getPan() { return this.pan; }

  public List<UserEvent> getChanges() { return this.changes; }

  public UserId create(String fullName, String email, String pan) {
    this.userId = UserId.generate();
    this.fullName = Objects.requireNonNull(fullName);
    this.email = email;
    this.pan = Objects.requireNonNull(pan);
    this.revision = 1;
    this.changes.add(
        new UserCreated(this.userId, this.revision, this.fullName, this.email, this.pan)
    );
    return this.userId;
  }

  public void updateFullName(String newFullName) {
    this.fullName = newFullName;
    ++this.revision;
    this.changes.add(
        new UserNameUpdated(this.userId, this.revision, this.fullName)
    );
  }

  public void updateEmail(String newEmail) {
    this.email = newEmail;
    ++this.revision;
    this.changes.add(
        new UserEmailUpdated(this.userId, this.revision, this.email)
    );
  }

  public void updatePan(String newPan) {
    this.pan = newPan;
    ++this.revision;
    this.changes.add(
        new UserPanUpdated(this.userId, this.revision, this.pan)
    );
  }

  public void apply(UserCreated userCreated) {
    this.userId = userCreated.getAggregateId();
    this.fullName = userCreated.getFullName();
    this.email = userCreated.getEmail();
    this.pan = userCreated.getPan();
    this.revision = userCreated.getRevision();
  }

  public void apply(UserNameUpdated userNameUpdated) {
    this.fullName = userNameUpdated.getNewFullName();
    this.revision = userNameUpdated.getRevision();
  }

  public void apply(UserEmailUpdated userEmailUpdated) {
    this.email = userEmailUpdated.getNewEmail();
    this.revision = userEmailUpdated.getRevision();
  }

  public void apply(UserPanUpdated userPanUpdated) {
    this.pan = userPanUpdated.getNewPan();
    this.revision = userPanUpdated.getRevision();
  }

}
