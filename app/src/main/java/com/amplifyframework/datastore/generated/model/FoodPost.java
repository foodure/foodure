package com.amplifyframework.datastore.generated.model;

import com.amplifyframework.core.model.annotations.BelongsTo;
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

/**
 * This is an auto generated class representing the FoodPost type in your schema.
 */
@SuppressWarnings("all")
@ModelConfig(pluralName = "FoodPosts")
@Index(name = "foodItem", fields = {"foodID"})
public final class FoodPost implements Model {
  public static final QueryField ID = field("FoodPost", "id");
  public static final QueryField TITLE = field("FoodPost", "title");
  public static final QueryField QUANTITY = field("FoodPost", "quantity");
  public static final QueryField TYPE = field("FoodPost", "type");
  public static final QueryField LOCATION = field("FoodPost", "location");
  public static final QueryField FILE_NAME = field("FoodPost", "fileName");
  public static final QueryField RESTAURANT = field("FoodPost", "foodID");
  private final @ModelField(targetType = "ID", isRequired = true)
  String id;
  private final @ModelField(targetType = "String", isRequired = true)
  String title;
  private final @ModelField(targetType = "String", isRequired = true)
  String quantity;
  private final @ModelField(targetType = "String", isRequired = true)
  String type;
  private final @ModelField(targetType = "String", isRequired = true)
  String location;
  private final @ModelField(targetType = "String")
  String fileName;
  private final @ModelField(targetType = "Restaurant", isRequired = true)
  @BelongsTo(targetName = "foodID", type = Restaurant.class)
  Restaurant restaurant;
  private @ModelField(targetType = "AWSDateTime", isReadOnly = true)
  Temporal.DateTime createdAt;
  private @ModelField(targetType = "AWSDateTime", isReadOnly = true)
  Temporal.DateTime updatedAt;

  public String getId() {
    return id;
  }

  public String getTitle() {
    return title;
  }

  public String getQuantity() {
    return quantity;
  }

  public String getType() {
    return type;
  }

  public String getLocation() {
    return location;
  }

  public String getFileName() {
    return fileName;
  }

  public Restaurant getRestaurant() {
    return restaurant;
  }

  public Temporal.DateTime getCreatedAt() {
    return createdAt;
  }

  public Temporal.DateTime getUpdatedAt() {
    return updatedAt;
  }

  private FoodPost(String id, String title, String quantity, String type, String location, String fileName, Restaurant restaurant) {
    this.id = id;
    this.title = title;
    this.quantity = quantity;
    this.type = type;
    this.location = location;
    this.fileName = fileName;
    this.restaurant = restaurant;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    } else if (obj == null || getClass() != obj.getClass()) {
      return false;
    } else {
      FoodPost foodPost = (FoodPost) obj;
      return ObjectsCompat.equals(getId(), foodPost.getId()) &&
          ObjectsCompat.equals(getTitle(), foodPost.getTitle()) &&
          ObjectsCompat.equals(getQuantity(), foodPost.getQuantity()) &&
          ObjectsCompat.equals(getType(), foodPost.getType()) &&
          ObjectsCompat.equals(getLocation(), foodPost.getLocation()) &&
          ObjectsCompat.equals(getFileName(), foodPost.getFileName()) &&
          ObjectsCompat.equals(getRestaurant(), foodPost.getRestaurant()) &&
          ObjectsCompat.equals(getCreatedAt(), foodPost.getCreatedAt()) &&
          ObjectsCompat.equals(getUpdatedAt(), foodPost.getUpdatedAt());
    }
  }

  @Override
  public int hashCode() {
    return new StringBuilder()
        .append(getId())
        .append(getTitle())
        .append(getQuantity())
        .append(getType())
        .append(getLocation())
        .append(getFileName())
        .append(getRestaurant())
        .append(getCreatedAt())
        .append(getUpdatedAt())
        .toString()
        .hashCode();
  }

  @Override
  public String toString() {
    return new StringBuilder()
        .append("FoodPost {")
        .append("id=" + String.valueOf(getId()) + ", ")
        .append("title=" + String.valueOf(getTitle()) + ", ")
        .append("quantity=" + String.valueOf(getQuantity()) + ", ")
        .append("type=" + String.valueOf(getType()) + ", ")
        .append("location=" + String.valueOf(getLocation()) + ", ")
        .append("fileName=" + String.valueOf(getFileName()) + ", ")
        .append("restaurant=" + String.valueOf(getRestaurant()) + ", ")
        .append("createdAt=" + String.valueOf(getCreatedAt()) + ", ")
        .append("updatedAt=" + String.valueOf(getUpdatedAt()))
        .append("}")
        .toString();
  }

  public static TitleStep builder() {
    return new Builder();
  }

  /**
   * WARNING: This method should not be used to build an instance of this object for a CREATE mutation.
   * This is a convenience method to return an instance of the object with only its ID populated
   * to be used in the context of a parameter in a delete mutation or referencing a foreign key
   * in a relationship.
   *
   * @param id the id of the existing item this instance will represent
   * @return an instance of this model with only ID populated
   * @throws IllegalArgumentException Checks that ID is in the proper format
   */
  public static FoodPost justId(String id) {
    try {
      UUID.fromString(id); // Check that ID is in the UUID format - if not an exception is thrown
    } catch (Exception exception) {
      throw new IllegalArgumentException(
          "Model IDs must be unique in the format of UUID. This method is for creating instances " +
              "of an existing object with only its ID field for sending as a mutation parameter. When " +
              "creating a new object, use the standard builder method and leave the ID field blank."
      );
    }
    return new FoodPost(
        id,
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
        title,
        quantity,
        type,
        location,
        fileName,
        restaurant);
  }

  public interface TitleStep {
    QuantityStep title(String title);
  }


  public interface QuantityStep {
    TypeStep quantity(String quantity);
  }


  public interface TypeStep {
    LocationStep type(String type);
  }


  public interface LocationStep {
    RestaurantStep location(String location);
  }


  public interface RestaurantStep {
    BuildStep restaurant(Restaurant restaurant);
  }


  public interface BuildStep {
    FoodPost build();

    BuildStep id(String id) throws IllegalArgumentException;

    BuildStep fileName(String fileName);
  }


  public static class Builder implements TitleStep, QuantityStep, TypeStep, LocationStep, RestaurantStep, BuildStep {
    private String id;
    private String title;
    private String quantity;
    private String type;
    private String location;
    private Restaurant restaurant;
    private String fileName;

    @Override
    public FoodPost build() {
      String id = this.id != null ? this.id : UUID.randomUUID().toString();

      return new FoodPost(
          id,
          title,
          quantity,
          type,
          location,
          fileName,
          restaurant);
    }

    @Override
    public QuantityStep title(String title) {
      Objects.requireNonNull(title);
      this.title = title;
      return this;
    }

    @Override
    public TypeStep quantity(String quantity) {
      Objects.requireNonNull(quantity);
      this.quantity = quantity;
      return this;
    }

    @Override
    public LocationStep type(String type) {
      Objects.requireNonNull(type);
      this.type = type;
      return this;
    }

    @Override
    public RestaurantStep location(String location) {
      Objects.requireNonNull(location);
      this.location = location;
      return this;
    }

    @Override
    public BuildStep restaurant(Restaurant restaurant) {
      Objects.requireNonNull(restaurant);
      this.restaurant = restaurant;
      return this;
    }

    @Override
    public BuildStep fileName(String fileName) {
      this.fileName = fileName;
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
    private CopyOfBuilder(String id, String title, String quantity, String type, String location, String fileName, Restaurant restaurant) {
      super.id(id);
      super.title(title)
          .quantity(quantity)
          .type(type)
          .location(location)
          .restaurant(restaurant)
          .fileName(fileName);
    }

    @Override
    public CopyOfBuilder title(String title) {
      return (CopyOfBuilder) super.title(title);
    }

    @Override
    public CopyOfBuilder quantity(String quantity) {
      return (CopyOfBuilder) super.quantity(quantity);
    }

    @Override
    public CopyOfBuilder type(String type) {
      return (CopyOfBuilder) super.type(type);
    }

    @Override
    public CopyOfBuilder location(String location) {
      return (CopyOfBuilder) super.location(location);
    }

    @Override
    public CopyOfBuilder restaurant(Restaurant restaurant) {
      return (CopyOfBuilder) super.restaurant(restaurant);
    }

    @Override
    public CopyOfBuilder fileName(String fileName) {
      return (CopyOfBuilder) super.fileName(fileName);
    }
  }

}
