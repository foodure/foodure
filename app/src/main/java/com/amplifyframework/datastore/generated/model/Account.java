package com.amplifyframework.datastore.generated.model;

import com.amplifyframework.core.model.temporal.Temporal;

import java.util.List;
import java.util.UUID;
import java.util.Objects;

import androidx.core.util.ObjectsCompat;

import com.amplifyframework.core.model.Model;
import com.amplifyframework.core.model.annotations.Index;
import com.amplifyframework.core.model.annotations.ModelConfig;
import com.amplifyframework.core.model.annotations.ModelField;
import com.amplifyframework.core.model.query.predicate.QueryField;

import static com.amplifyframework.core.model.query.predicate.QueryField.field;

/** This is an auto generated class representing the Account type in your schema. */
@SuppressWarnings("all")
@ModelConfig(pluralName = "Accounts")
public final class Account implements Model {
  public static final QueryField ID = field("Account", "id");
  public static final QueryField EMAIL = field("Account", "email");
  public static final QueryField FIRST_NAME = field("Account", "firstName");
  public static final QueryField SECOND_NAME = field("Account", "secondName");
  public static final QueryField USERNAME = field("Account", "username");
  public static final QueryField MARITAL_STATUS = field("Account", "maritalStatus");
  public static final QueryField AGE = field("Account", "age");
  public static final QueryField LOCATION = field("Account", "location");
  private final @ModelField(targetType="ID", isRequired = true) String id;
  private final @ModelField(targetType="String", isRequired = true) String email;
  private final @ModelField(targetType="String") String firstName;
  private final @ModelField(targetType="String") String secondName;
  private final @ModelField(targetType="String", isRequired = true) String username;
  private final @ModelField(targetType="String", isRequired = true) String maritalStatus;
  private final @ModelField(targetType="Int") Integer age;
  private final @ModelField(targetType="String", isRequired = true) String location;
  private @ModelField(targetType="AWSDateTime", isReadOnly = true) Temporal.DateTime createdAt;
  private @ModelField(targetType="AWSDateTime", isReadOnly = true) Temporal.DateTime updatedAt;
  public String getId() {
      return id;
  }
  
  public String getEmail() {
      return email;
  }
  
  public String getFirstName() {
      return firstName;
  }
  
  public String getSecondName() {
      return secondName;
  }
  
  public String getUsername() {
      return username;
  }
  
  public String getMaritalStatus() {
      return maritalStatus;
  }
  
  public Integer getAge() {
      return age;
  }
  
  public String getLocation() {
      return location;
  }
  
  public Temporal.DateTime getCreatedAt() {
      return createdAt;
  }
  
  public Temporal.DateTime getUpdatedAt() {
      return updatedAt;
  }
  
  private Account(String id, String email, String firstName, String secondName, String username, String maritalStatus, Integer age, String location) {
    this.id = id;
    this.email = email;
    this.firstName = firstName;
    this.secondName = secondName;
    this.username = username;
    this.maritalStatus = maritalStatus;
    this.age = age;
    this.location = location;
  }
  
  @Override
   public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      } else if(obj == null || getClass() != obj.getClass()) {
        return false;
      } else {
      Account account = (Account) obj;
      return ObjectsCompat.equals(getId(), account.getId()) &&
              ObjectsCompat.equals(getEmail(), account.getEmail()) &&
              ObjectsCompat.equals(getFirstName(), account.getFirstName()) &&
              ObjectsCompat.equals(getSecondName(), account.getSecondName()) &&
              ObjectsCompat.equals(getUsername(), account.getUsername()) &&
              ObjectsCompat.equals(getMaritalStatus(), account.getMaritalStatus()) &&
              ObjectsCompat.equals(getAge(), account.getAge()) &&
              ObjectsCompat.equals(getLocation(), account.getLocation()) &&
              ObjectsCompat.equals(getCreatedAt(), account.getCreatedAt()) &&
              ObjectsCompat.equals(getUpdatedAt(), account.getUpdatedAt());
      }
  }
  
  @Override
   public int hashCode() {
    return new StringBuilder()
      .append(getId())
      .append(getEmail())
      .append(getFirstName())
      .append(getSecondName())
      .append(getUsername())
      .append(getMaritalStatus())
      .append(getAge())
      .append(getLocation())
      .append(getCreatedAt())
      .append(getUpdatedAt())
      .toString()
      .hashCode();
  }
  
  @Override
   public String toString() {
    return new StringBuilder()
      .append("Account {")
      .append("id=" + String.valueOf(getId()) + ", ")
      .append("email=" + String.valueOf(getEmail()) + ", ")
      .append("firstName=" + String.valueOf(getFirstName()) + ", ")
      .append("secondName=" + String.valueOf(getSecondName()) + ", ")
      .append("username=" + String.valueOf(getUsername()) + ", ")
      .append("maritalStatus=" + String.valueOf(getMaritalStatus()) + ", ")
      .append("age=" + String.valueOf(getAge()) + ", ")
      .append("location=" + String.valueOf(getLocation()) + ", ")
      .append("createdAt=" + String.valueOf(getCreatedAt()) + ", ")
      .append("updatedAt=" + String.valueOf(getUpdatedAt()))
      .append("}")
      .toString();
  }
  
  public static EmailStep builder() {
      return new Builder();
  }
  
  /** 
   * WARNING: This method should not be used to build an instance of this object for a CREATE mutation.
   * This is a convenience method to return an instance of the object with only its ID populated
   * to be used in the context of a parameter in a delete mutation or referencing a foreign key
   * in a relationship.
   * @param id the id of the existing item this instance will represent
   * @return an instance of this model with only ID populated
   * @throws IllegalArgumentException Checks that ID is in the proper format
   */
  public static Account justId(String id) {
    try {
      UUID.fromString(id); // Check that ID is in the UUID format - if not an exception is thrown
    } catch (Exception exception) {
      throw new IllegalArgumentException(
              "Model IDs must be unique in the format of UUID. This method is for creating instances " +
              "of an existing object with only its ID field for sending as a mutation parameter. When " +
              "creating a new object, use the standard builder method and leave the ID field blank."
      );
    }
    return new Account(
      id,
      null,
      null,
      null,
      null,
      null,
      null,
      null
    );
  }
  
  public CopyOfBuilder copyOfBuilder() {
    return new CopyOfBuilder(id,
      email,
      firstName,
      secondName,
      username,
      maritalStatus,
      age,
      location);
  }
  public interface EmailStep {
    UsernameStep email(String email);
  }
  

  public interface UsernameStep {
    MaritalStatusStep username(String username);
  }
  

  public interface MaritalStatusStep {
    LocationStep maritalStatus(String maritalStatus);
  }
  

  public interface LocationStep {
    BuildStep location(String location);
  }
  

  public interface BuildStep {
    Account build();
    BuildStep id(String id) throws IllegalArgumentException;
    BuildStep firstName(String firstName);
    BuildStep secondName(String secondName);
    BuildStep age(Integer age);
  }
  

  public static class Builder implements EmailStep, UsernameStep, MaritalStatusStep, LocationStep, BuildStep {
    private String id;
    private String email;
    private String username;
    private String maritalStatus;
    private String location;
    private String firstName;
    private String secondName;
    private Integer age;
    @Override
     public Account build() {
        String id = this.id != null ? this.id : UUID.randomUUID().toString();
        
        return new Account(
          id,
          email,
          firstName,
          secondName,
          username,
          maritalStatus,
          age,
          location);
    }
    
    @Override
     public UsernameStep email(String email) {
        Objects.requireNonNull(email);
        this.email = email;
        return this;
    }
    
    @Override
     public MaritalStatusStep username(String username) {
        Objects.requireNonNull(username);
        this.username = username;
        return this;
    }
    
    @Override
     public LocationStep maritalStatus(String maritalStatus) {
        Objects.requireNonNull(maritalStatus);
        this.maritalStatus = maritalStatus;
        return this;
    }
    
    @Override
     public BuildStep location(String location) {
        Objects.requireNonNull(location);
        this.location = location;
        return this;
    }
    
    @Override
     public BuildStep firstName(String firstName) {
        this.firstName = firstName;
        return this;
    }
    
    @Override
     public BuildStep secondName(String secondName) {
        this.secondName = secondName;
        return this;
    }
    
    @Override
     public BuildStep age(Integer age) {
        this.age = age;
        return this;
    }
    
    /** 
     * @param id id
     * @return Current Builder instance, for fluent method chaining
     */
    public BuildStep id(String id) {
        this.id = id;
        return this;
    }
  }
  

  public final class CopyOfBuilder extends Builder {
    private CopyOfBuilder(String id, String email, String firstName, String secondName, String username, String maritalStatus, Integer age, String location) {
      super.id(id);
      super.email(email)
        .username(username)
        .maritalStatus(maritalStatus)
        .location(location)
        .firstName(firstName)
        .secondName(secondName)
        .age(age);
    }
    
    @Override
     public CopyOfBuilder email(String email) {
      return (CopyOfBuilder) super.email(email);
    }
    
    @Override
     public CopyOfBuilder username(String username) {
      return (CopyOfBuilder) super.username(username);
    }
    
    @Override
     public CopyOfBuilder maritalStatus(String maritalStatus) {
      return (CopyOfBuilder) super.maritalStatus(maritalStatus);
    }
    
    @Override
     public CopyOfBuilder location(String location) {
      return (CopyOfBuilder) super.location(location);
    }
    
    @Override
     public CopyOfBuilder firstName(String firstName) {
      return (CopyOfBuilder) super.firstName(firstName);
    }
    
    @Override
     public CopyOfBuilder secondName(String secondName) {
      return (CopyOfBuilder) super.secondName(secondName);
    }
    
    @Override
     public CopyOfBuilder age(Integer age) {
      return (CopyOfBuilder) super.age(age);
    }
  }
  
}
