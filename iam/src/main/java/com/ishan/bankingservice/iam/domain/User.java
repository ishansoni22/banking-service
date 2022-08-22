package com.ishan.bankingservice.iam.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/*
The User Aggregate
 */
public class User {

  private UserId userId;
  private String firstName;
  private String lastName;
  private String email;
  private String pan;
  private int revision;
  private List<UserEvent> changes = new ArrayList<>();

  public UserId getUserId() { return this.userId; }

  public String getFirstName() { return this.firstName; }

  public String getLastName() { return this.lastName; }

  public String getEmail() {
    return this.email;
  }

  public String getPan() { return this.pan; }

  public List<UserEvent> getChanges() { return this.changes; }

  public UserId create(String firstName, String lastName, String email, String pan) {
    this.userId = UserId.generate();
    this.firstName = Objects.requireNonNull(firstName);
    this.lastName = Objects.requireNonNull(lastName);
    this.email = email;
    this.pan = Objects.requireNonNull(pan);
    this.revision = 1;
    this.changes.add(
        new UserCreated(this.userId, this.revision, this.firstName, this.lastName, this.email, this.pan)
    );
    return this.userId;
  }

  public void updateFullName(String newFirstName, String newLastName) {
    this.firstName = newFirstName;
    this.lastName = newLastName;
    ++this.revision;
    this.changes.add(
        new UserNameUpdated(this.userId, this.revision, this.firstName, this.lastName)
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
    this.firstName = userCreated.getFirstName();
    this.lastName = userCreated.getLastName();
    this.email = userCreated.getEmail();
    this.pan = userCreated.getPan();
    this.revision = userCreated.getRevision();
  }

  public void apply(UserNameUpdated userNameUpdated) {
    this.firstName = userNameUpdated.getNewFirstName();
    this.lastName = userNameUpdated.getNewLastName();
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

  public void apply(UserCreatedV1 userCreated) {
    this.userId = userCreated.getAggregateId();

    String fullName = userCreated.getFullName();
    String[] name = fullName.split(" ");
    this.firstName = name[0];
    this.lastName = name.length > 1 ? name[1] : null;

    this.email = userCreated.getEmail();
    this.pan = userCreated.getPan();
    this.revision = userCreated.getRevision();
  }

  public void apply(UserNameUpdatedV1 userNameUpdated) {
    String newFullName = userNameUpdated.getNewFullName();
    String[] name = newFullName.split(" ");
    this.firstName = name[0];
    this.lastName = name.length > 1 ? name[1] : null;

    this.revision = userNameUpdated.getRevision();
  }

}
